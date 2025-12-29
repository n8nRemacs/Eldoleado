"""
MCP MAX Server - Multi-tenant User API Proxy

FastAPI server for MAX messenger User API integration.
Supports multiple accounts via phone+SMS or saved token auth.
Normalizes messages and forwards to n8n.

Architecture:
- Each account identified by phone number hash
- Sessions stored in Redis with tokens
- Messages normalized and forwarded to n8n webhook
- Real-time events via WebSocket connection
"""

import logging
import hashlib
import json
from datetime import datetime
from typing import Optional, Dict, List, Any
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, BackgroundTasks, Query
from fastapi.responses import JSONResponse
from pydantic import BaseModel, Field
import httpx
import redis.asyncio as redis

import sys
import os

from config import settings
from max_client import MaxClient, MaxAPIError, Opcodes, AttachType

# Add shared module to path
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..'))
try:
    from shared.health import get_health_checker
    from shared.alerts import get_alert_service, AlertConfig
    HAS_SHARED = True
except ImportError:
    HAS_SHARED = False

# Logging
logging.basicConfig(
    level=getattr(logging, settings.LOG_LEVEL),
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Health checker
if HAS_SHARED:
    health_checker = get_health_checker("max", "1.0.0")
    alert_service = get_alert_service(AlertConfig(
        telegram_bot_token=settings.ALERT_TELEGRAM_BOT_TOKEN or None,
        telegram_chat_id=settings.ALERT_TELEGRAM_CHAT_ID or None,
        n8n_webhook_url=settings.ALERT_N8N_WEBHOOK_URL or None,
    ))

# Account registry: phone_hash -> {client, phone, profile, ...}
account_registry: Dict[str, dict] = {}

# Redis client for session persistence
redis_client: Optional[redis.Redis] = None

# HTTP client for webhooks
http_client: Optional[httpx.AsyncClient] = None


def get_phone_hash(phone: str) -> str:
    """Generate short hash from phone number."""
    return hashlib.sha256(phone.encode()).hexdigest()[:16]


async def get_redis() -> redis.Redis:
    """Get Redis client."""
    global redis_client
    if redis_client is None:
        redis_client = redis.from_url(settings.REDIS_URL, decode_responses=True)
    return redis_client


async def save_session(phone: str, token: str, device_id: str, profile: dict) -> None:
    """Save session to Redis."""
    try:
        r = await get_redis()
        key = f"max:session:{get_phone_hash(phone)}"
        data = {
            "phone": phone,
            "token": token,
            "device_id": device_id,
            "profile": json.dumps(profile),
            "updated_at": datetime.utcnow().isoformat()
        }
        await r.hset(key, mapping=data)
        await r.expire(key, 86400 * 30)  # 30 days
        logger.info(f"Session saved for {phone[:4]}***")
    except Exception as e:
        logger.error(f"Failed to save session: {e}")


async def load_session(phone: str) -> Optional[dict]:
    """Load session from Redis."""
    try:
        r = await get_redis()
        key = f"max:session:{get_phone_hash(phone)}"
        data = await r.hgetall(key)
        if data and "token" in data:
            if "profile" in data:
                data["profile"] = json.loads(data["profile"])
            return data
    except Exception as e:
        logger.error(f"Failed to load session: {e}")
    return None


async def delete_session(phone: str) -> None:
    """Delete session from Redis."""
    try:
        r = await get_redis()
        key = f"max:session:{get_phone_hash(phone)}"
        await r.delete(key)
    except Exception as e:
        logger.error(f"Failed to delete session: {e}")


async def forward_to_n8n(event_type: str, data: dict, phone: str) -> None:
    """Forward event to n8n webhook."""
    if not settings.N8N_WEBHOOK_URL:
        return

    try:
        payload = {
            "channel": "max",
            "event_type": event_type,
            "phone": phone,
            "phone_hash": get_phone_hash(phone),
            "timestamp": datetime.utcnow().isoformat(),
            "data": data
        }

        async with httpx.AsyncClient() as client:
            response = await client.post(
                settings.N8N_WEBHOOK_URL,
                json=payload,
                timeout=10.0
            )
            logger.debug(f"n8n webhook response: {response.status_code}")

    except Exception as e:
        logger.error(f"Failed to forward to n8n: {e}")


def normalize_message(payload: dict, phone: str) -> dict:
    """Normalize MAX message to standard format."""
    message = payload.get("message", {})
    chat = payload.get("chat", {})
    sender = message.get("sender", {})

    return {
        "channel": "max",
        "channel_account": phone,
        "message_id": message.get("messageId"),
        "chat_id": payload.get("chatId") or chat.get("chatId"),
        "chat_type": chat.get("chatType", "DIALOG"),
        "chat_title": chat.get("title"),
        "sender_id": sender.get("userId"),
        "sender_name": f"{sender.get('firstName', '')} {sender.get('lastName', '')}".strip(),
        "sender_username": sender.get("link"),
        "text": message.get("text", ""),
        "attaches": message.get("attaches", []),
        "reply_to": message.get("link", {}).get("messageId") if message.get("link", {}).get("type") == "REPLY" else None,
        "timestamp": message.get("timestamp"),
        "is_outgoing": sender.get("userId") == account_registry.get(get_phone_hash(phone), {}).get("profile", {}).get("userId")
    }


async def on_message(phone: str):
    """Create message handler for specific account."""
    async def handler(payload: dict):
        logger.info(f"New message for {phone[:4]}***: {payload.get('message', {}).get('text', '')[:50]}")
        normalized = normalize_message(payload, phone)
        await forward_to_n8n("message", normalized, phone)
    return handler


async def on_typing(phone: str):
    """Create typing handler for specific account."""
    async def handler(payload: dict):
        logger.debug(f"Typing in chat {payload.get('chatId')} for {phone[:4]}***")
        await forward_to_n8n("typing", payload, phone)
    return handler


async def connect_account(phone: str, token: str, device_id: str) -> dict:
    """Connect account using saved token."""
    phone_hash = get_phone_hash(phone)

    # Check if already connected
    if phone_hash in account_registry:
        existing = account_registry[phone_hash]
        if existing["client"].connected and existing["client"].authenticated:
            return {
                "status": "already_connected",
                "phone_hash": phone_hash,
                "profile": existing.get("profile")
            }
        # Close old connection
        try:
            await existing["client"].close()
        except:
            pass

    # Create new client
    client = MaxClient(
        on_message=await on_message(phone),
        on_typing=await on_typing(phone)
    )

    try:
        await client.connect()
        response = await client.login_by_token(token, device_id)

        profile = response.get("profile", {})

        # Store in registry
        account_registry[phone_hash] = {
            "phone": phone,
            "client": client,
            "profile": profile,
            "token": token,
            "device_id": device_id,
            "connected_at": datetime.utcnow().isoformat()
        }

        # Save to Redis
        await save_session(phone, token, device_id, profile)

        logger.info(f"Account connected: {phone[:4]}*** ({profile.get('firstName', 'Unknown')})")

        return {
            "status": "connected",
            "phone_hash": phone_hash,
            "profile": profile
        }

    except MaxAPIError as e:
        await client.close()
        raise HTTPException(status_code=400, detail=f"Auth failed: {e.message}")
    except Exception as e:
        await client.close()
        raise HTTPException(status_code=500, detail=f"Connection failed: {str(e)}")


async def disconnect_account(phone: str) -> dict:
    """Disconnect account."""
    phone_hash = get_phone_hash(phone)

    if phone_hash not in account_registry:
        raise HTTPException(status_code=404, detail="Account not connected")

    try:
        await account_registry[phone_hash]["client"].close()
    except:
        pass

    del account_registry[phone_hash]

    return {"status": "disconnected", "phone_hash": phone_hash}


def get_client(phone: str) -> MaxClient:
    """Get client by phone number."""
    phone_hash = get_phone_hash(phone)
    if phone_hash not in account_registry:
        raise HTTPException(status_code=404, detail="Account not connected")

    client = account_registry[phone_hash]["client"]
    if not client.connected:
        raise HTTPException(status_code=503, detail="Client disconnected")

    return client


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan manager."""
    logger.info("Starting MCP MAX Server...")

    # Try to restore sessions from Redis
    try:
        r = await get_redis()
        keys = await r.keys("max:session:*")
        for key in keys:
            session = await r.hgetall(key)
            if session and "phone" in session and "token" in session:
                try:
                    await connect_account(
                        session["phone"],
                        session["token"],
                        session.get("device_id", "")
                    )
                except Exception as e:
                    logger.warning(f"Failed to restore session {key}: {e}")
    except Exception as e:
        logger.warning(f"Redis not available: {e}")

    yield

    # Cleanup
    for phone_hash, data in list(account_registry.items()):
        try:
            await data["client"].close()
        except Exception as e:
            logger.error(f"Error closing client {phone_hash}: {e}")

    if redis_client:
        await redis_client.close()

    logger.info("MCP MAX Server stopped")


app = FastAPI(
    title="MCP MAX Server",
    description="Multi-tenant User API integration for MAX messenger",
    version="1.0.0",
    lifespan=lifespan
)


# ==================== MODELS ====================

class StartAuthRequest(BaseModel):
    phone: str = Field(..., description="Phone number with country code (+79001234567)")
    language: str = Field(default="ru", description="Language code")


class VerifyCodeRequest(BaseModel):
    phone: str
    token: str = Field(..., description="Token from start_auth")
    code: str = Field(..., description="SMS code (6 digits)")


class Verify2FARequest(BaseModel):
    phone: str
    token: str
    password: str = Field(..., description="2FA password")


class LoginTokenRequest(BaseModel):
    phone: str
    token: str = Field(..., description="Login token from previous auth")
    device_id: str = Field(..., description="Device ID used during auth")


class SendMessageRequest(BaseModel):
    phone: str
    chat_id: int
    text: str = ""
    attaches: List[dict] = Field(default_factory=list)
    reply_to: str = None
    notify: bool = True


class EditMessageRequest(BaseModel):
    phone: str
    chat_id: int
    message_id: str
    text: str


class DeleteMessageRequest(BaseModel):
    phone: str
    chat_id: int
    message_ids: List[str]
    for_me: bool = False


class SetTypingRequest(BaseModel):
    phone: str
    chat_id: int
    typing: bool = True


class CreateChatRequest(BaseModel):
    phone: str
    user_id: int = None
    title: str = None
    user_ids: List[int] = None
    chat_type: str = "CHAT"


# ==================== HEALTH ====================

@app.get("/health")
async def health():
    """Health check endpoint."""
    connected_accounts = len([
        a for a in account_registry.values()
        if a["client"].connected
    ])

    return {
        "status": "healthy",
        "service": "mcp-max",
        "version": "1.0.0",
        "connected_accounts": connected_accounts,
        "timestamp": datetime.utcnow().isoformat()
    }


# ==================== AUTH ====================

@app.post("/v1/auth/qr")
async def create_qr_auth():
    """
    Create QR auth track (like web.max.ru).

    This is the preferred auth method - works without GeoIP restrictions.
    Returns trackId and qr_data to display as QR code.
    User scans QR with MAX mobile app to authenticate.

    Flow:
    1. Call this endpoint -> get trackId
    2. Display trackId as QR code
    3. User scans with MAX app
    4. Poll /v1/auth/qr/status?track_id=XXX for completion
    """
    client = MaxClient()
    try:
        await client.connect()
        response = await client.create_auth_track()

        track_id = response.get("trackId")

        # Store client for later polling
        if track_id:
            # Store pending auth
            qr_auth_pending[track_id] = {
                "client": client,
                "created_at": datetime.utcnow().isoformat()
            }

        return {
            "status": "qr_created",
            "track_id": track_id,
            "qr_data": f"max://auth/{track_id}",  # Data for QR code
            "expires_in": 300  # 5 minutes
        }

    except MaxAPIError as e:
        await client.close()
        raise HTTPException(status_code=400, detail=f"QR auth failed: {e.message}")
    except Exception as e:
        await client.close()
        raise HTTPException(status_code=500, detail=str(e))


@app.get("/v1/auth/qr/status")
async def get_qr_auth_status(track_id: str = Query(...)):
    """
    Check QR auth status.

    Poll this endpoint after creating QR to check if user scanned it.
    """
    if track_id not in qr_auth_pending:
        raise HTTPException(status_code=404, detail="Track not found or expired")

    pending = qr_auth_pending[track_id]
    client = pending["client"]

    # Check if authenticated (client received OK_TOKEN)
    if client.authenticated:
        profile = client.profile
        login_token = client.login_token
        device_id = client.device_id

        # Get phone from profile
        phone = profile.get("phone", f"qr_{track_id[:8]}")
        phone_hash = get_phone_hash(phone)

        # Store in registry
        account_registry[phone_hash] = {
            "phone": phone,
            "client": client,
            "profile": profile,
            "token": login_token,
            "device_id": device_id,
            "connected_at": datetime.utcnow().isoformat()
        }

        # Setup handlers
        client.on_message = await on_message(phone)
        client.on_typing = await on_typing(phone)

        # Save session
        await save_session(phone, login_token, device_id, profile)

        # Remove from pending
        del qr_auth_pending[track_id]

        return {
            "status": "authenticated",
            "phone_hash": phone_hash,
            "profile": profile,
            "login_token": login_token,
            "device_id": device_id
        }

    return {
        "status": "pending",
        "track_id": track_id
    }


# Pending QR authentications
qr_auth_pending: Dict[str, dict] = {}


@app.post("/v1/auth/start")
async def start_auth(request: StartAuthRequest):
    """
    Start SMS authentication.

    NOTE: May be blocked by GeoIP. Use /v1/auth/qr instead.
    Returns token for verify_code step.
    """
    phone_hash = get_phone_hash(request.phone)

    # Create temporary client
    client = MaxClient()
    try:
        await client.connect()
        response = await client.start_auth(request.phone, request.language)
        await client.close()

        return {
            "status": "sms_sent",
            "phone_hash": phone_hash,
            "token": response.get("token"),
            "phone_masked": f"{request.phone[:4]}***{request.phone[-2:]}"
        }

    except MaxAPIError as e:
        await client.close()
        raise HTTPException(status_code=400, detail=f"Auth failed: {e.message}")
    except Exception as e:
        await client.close()
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/v1/auth/verify")
async def verify_code(request: VerifyCodeRequest):
    """
    Verify SMS code.

    Returns profile and login token for future logins.
    """
    client = MaxClient()
    try:
        await client.connect()
        response = await client.verify_code(request.token, request.code)

        profile = response.get("profile", {})
        login_token = response.get("tokenAttrs", {}).get("LOGIN", {}).get("token", "")
        device_id = client.device_id

        # Connect and store
        phone_hash = get_phone_hash(request.phone)
        account_registry[phone_hash] = {
            "phone": request.phone,
            "client": client,
            "profile": profile,
            "token": login_token,
            "device_id": device_id,
            "connected_at": datetime.utcnow().isoformat()
        }

        # Setup handlers
        client.on_message = await on_message(request.phone)
        client.on_typing = await on_typing(request.phone)

        # Save session
        await save_session(request.phone, login_token, device_id, profile)

        # Check if 2FA required
        if response.get("require2FA"):
            return {
                "status": "2fa_required",
                "phone_hash": phone_hash,
                "token": request.token
            }

        return {
            "status": "authenticated",
            "phone_hash": phone_hash,
            "profile": profile,
            "login_token": login_token,
            "device_id": device_id
        }

    except MaxAPIError as e:
        await client.close()
        raise HTTPException(status_code=400, detail=f"Verification failed: {e.message}")
    except Exception as e:
        await client.close()
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/v1/auth/verify-2fa")
async def verify_2fa(request: Verify2FARequest):
    """Verify 2FA password."""
    phone_hash = get_phone_hash(request.phone)

    if phone_hash not in account_registry:
        raise HTTPException(status_code=400, detail="Start auth first")

    client = account_registry[phone_hash]["client"]

    try:
        response = await client.verify_2fa(request.token, request.password)

        profile = response.get("profile", {})
        login_token = response.get("tokenAttrs", {}).get("LOGIN", {}).get("token", "")

        # Update registry
        account_registry[phone_hash]["profile"] = profile
        account_registry[phone_hash]["token"] = login_token

        # Save session
        await save_session(request.phone, login_token, client.device_id, profile)

        return {
            "status": "authenticated",
            "phone_hash": phone_hash,
            "profile": profile,
            "login_token": login_token,
            "device_id": client.device_id
        }

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=f"2FA failed: {e.message}")


@app.post("/v1/auth/login")
async def login_by_token(request: LoginTokenRequest):
    """
    Login using saved token.

    Use this to reconnect after restart.
    """
    result = await connect_account(request.phone, request.token, request.device_id)
    return result


@app.post("/v1/auth/logout")
async def logout(phone: str):
    """Logout and disconnect account."""
    client = get_client(phone)

    try:
        await client.logout()
    except:
        pass

    await disconnect_account(phone)
    await delete_session(phone)

    return {"status": "logged_out"}


@app.get("/v1/accounts")
async def list_accounts():
    """List connected accounts."""
    accounts = []
    for phone_hash, data in account_registry.items():
        accounts.append({
            "phone_hash": phone_hash,
            "phone_masked": f"{data['phone'][:4]}***{data['phone'][-2:]}",
            "profile": data.get("profile"),
            "connected": data["client"].connected,
            "authenticated": data["client"].authenticated,
            "connected_at": data.get("connected_at")
        })
    return {"accounts": accounts}


@app.delete("/v1/accounts/{phone}")
async def remove_account(phone: str):
    """Disconnect and remove account."""
    await disconnect_account(phone)
    await delete_session(phone)
    return {"status": "removed"}


# ==================== MESSAGES ====================

@app.post("/v1/messages/send")
async def send_message(request: SendMessageRequest):
    """Send message to chat."""
    client = get_client(request.phone)

    try:
        result = await client.send_message(
            chat_id=request.chat_id,
            text=request.text,
            attaches=request.attaches,
            reply_to=request.reply_to,
            notify=request.notify
        )
        return {"status": "sent", "message": result}

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


@app.put("/v1/messages/edit")
async def edit_message(request: EditMessageRequest):
    """Edit message."""
    client = get_client(request.phone)

    try:
        result = await client.edit_message(
            chat_id=request.chat_id,
            message_id=request.message_id,
            text=request.text
        )
        return {"status": "edited", "message": result}

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


@app.delete("/v1/messages")
async def delete_messages(request: DeleteMessageRequest):
    """Delete messages."""
    client = get_client(request.phone)

    try:
        result = await client.delete_message(
            chat_id=request.chat_id,
            message_ids=request.message_ids,
            for_me=request.for_me
        )
        return {"status": "deleted", "result": result}

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


@app.post("/v1/messages/typing")
async def set_typing(request: SetTypingRequest):
    """Set typing indicator."""
    client = get_client(request.phone)

    try:
        await client.set_typing(request.chat_id, request.typing)
        return {"status": "ok"}

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


@app.post("/v1/messages/read")
async def read_messages(
    phone: str,
    chat_id: int,
    message_id: str = None
):
    """Mark messages as read."""
    client = get_client(phone)

    try:
        await client.read_message(chat_id, message_id)
        return {"status": "ok"}

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


# ==================== CHATS ====================

@app.get("/v1/chats")
async def get_chats(
    phone: str,
    count: int = Query(default=40, le=100),
    offset: int = Query(default=0)
):
    """Get list of chats."""
    client = get_client(phone)

    try:
        result = await client.get_chats(count, offset)
        return result

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


@app.get("/v1/chats/{chat_id}")
async def get_chat(
    chat_id: int,
    phone: str,
    count: int = Query(default=50, le=100)
):
    """Get chat with message history."""
    client = get_client(phone)

    try:
        result = await client.get_chat(chat_id, count)
        return result

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


@app.post("/v1/chats")
async def create_chat(request: CreateChatRequest):
    """Create new chat."""
    client = get_client(request.phone)

    try:
        result = await client.create_chat(
            user_id=request.user_id,
            title=request.title,
            user_ids=request.user_ids,
            chat_type=request.chat_type
        )
        return {"status": "created", "chat": result}

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


@app.delete("/v1/chats/{chat_id}")
async def delete_chat(
    chat_id: int,
    phone: str,
    for_all: bool = False
):
    """Delete chat."""
    client = get_client(phone)

    try:
        result = await client.delete_chat(chat_id, for_all)
        return {"status": "deleted", "result": result}

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


@app.get("/v1/chats/{chat_id}/members")
async def get_chat_members(
    chat_id: int,
    phone: str,
    count: int = Query(default=100, le=200),
    offset: int = Query(default=0)
):
    """Get chat members."""
    client = get_client(phone)

    try:
        result = await client.get_chat_members(chat_id, count, offset)
        return result

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


# ==================== CONTACTS ====================

@app.get("/v1/contacts")
async def get_contacts(
    phone: str,
    count: int = Query(default=100, le=200),
    offset: int = Query(default=0)
):
    """Get contact list."""
    client = get_client(phone)

    try:
        result = await client.get_contact_list(count, offset)
        return result

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


@app.get("/v1/contacts/search")
async def search_contacts(
    phone: str,
    query: str,
    count: int = Query(default=20, le=50)
):
    """Search contacts."""
    client = get_client(phone)

    try:
        result = await client.search_contacts(query, count)
        return result

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


# ==================== PROFILE ====================

@app.get("/v1/profile")
async def get_profile(phone: str):
    """Get current profile."""
    phone_hash = get_phone_hash(phone)

    if phone_hash not in account_registry:
        raise HTTPException(status_code=404, detail="Account not connected")

    return {
        "profile": account_registry[phone_hash].get("profile"),
        "connected": account_registry[phone_hash]["client"].connected
    }


@app.put("/v1/profile")
async def update_profile(
    phone: str,
    first_name: str = None,
    last_name: str = None,
    description: str = None
):
    """Update profile."""
    client = get_client(phone)

    try:
        result = await client.update_profile(
            first_name=first_name,
            last_name=last_name,
            description=description
        )
        return {"status": "updated", "profile": result}

    except MaxAPIError as e:
        raise HTTPException(status_code=400, detail=e.message)


# ==================== MAIN ====================

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=settings.HOST, port=settings.PORT)
