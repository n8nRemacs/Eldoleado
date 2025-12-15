"""Instagram MCP Server - Graph API (Multi-tenant).

FastAPI server for Instagram Graph API integration.
Supports multiple Instagram accounts with dynamic registration.
Version 2.0.0 - Multi-tenant architecture with Redis + PostgreSQL storage.
"""

import sys
import logging
import hashlib
import hmac
from datetime import datetime
from typing import Optional, List, Dict, Any
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, Header, Request, Query, BackgroundTasks
from fastapi.responses import JSONResponse, PlainTextResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field
import httpx

from config import settings
from instagram_client import InstagramClient, InstagramAPIError

# Add shared module to path
sys.path.insert(0, str(__file__).replace("\\", "/").rsplit("/", 2)[0])
from shared.storage import (
    init_storage, close_storage, get_credentials_hash,
    save_account, load_accounts, get_account, delete_account
)

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Channel name for storage
CHANNEL_NAME = "instagram"

# Multi-tenant registries
client_cache: Dict[str, InstagramClient] = {}  # account_hash -> InstagramClient
account_cache: Dict[str, dict] = {}             # account_hash -> account data


def get_account_hash(instagram_account_id: str) -> str:
    """Generate 16-char hash from Instagram account ID for webhook URL."""
    return hashlib.sha256(instagram_account_id.encode()).hexdigest()[:16]


async def get_or_create_client(
    access_token: str,
    instagram_account_id: str,
    app_secret: Optional[str] = None,
    verify_token: Optional[str] = None
) -> tuple[InstagramClient, str]:
    """Get existing client or create new one with auto-registration."""
    account_hash = get_account_hash(instagram_account_id)

    if account_hash in client_cache:
        return client_cache[account_hash], account_hash

    # Create new client
    client = InstagramClient(
        access_token=access_token,
        instagram_account_id=instagram_account_id
    )
    await client.connect()

    # Get account info for metadata
    try:
        account_info = await client.get_account_info()
    except Exception as e:
        logger.warning(f"Could not get account info: {e}")
        account_info = {}

    # Save to storage
    credentials = {
        "access_token": access_token,
        "instagram_account_id": instagram_account_id,
        "app_secret": app_secret,
        "verify_token": verify_token
    }

    metadata = {
        "username": account_info.get("username", ""),
        "name": account_info.get("name", ""),
        "profile_picture_url": account_info.get("profile_picture_url", ""),
        "registered_at": datetime.utcnow().isoformat()
    }

    await save_account(CHANNEL_NAME, account_hash, credentials, metadata)

    # Cache
    client_cache[account_hash] = client
    account_cache[account_hash] = {
        "credentials": credentials,
        "metadata": metadata,
        "instagram_account_id": instagram_account_id,
        "app_secret": app_secret,
        "verify_token": verify_token
    }

    logger.info(f"Registered Instagram account {instagram_account_id} (@{metadata.get('username', 'unknown')}) with hash {account_hash}")
    return client, account_hash


def get_client_by_hash(account_hash: str) -> Optional[InstagramClient]:
    """Get client by account hash."""
    return client_cache.get(account_hash)


def get_account_by_hash(account_hash: str) -> Optional[dict]:
    """Get account data by hash."""
    return account_cache.get(account_hash)


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan manager."""
    # Initialize storage
    await init_storage()
    logger.info("Storage initialized")

    # Load existing accounts
    try:
        accounts = await load_accounts(CHANNEL_NAME)
        for acc in accounts:
            try:
                creds = acc.get("credentials", {})
                access_token = creds.get("access_token")
                instagram_account_id = creds.get("instagram_account_id")
                app_secret = creds.get("app_secret")
                verify_token = creds.get("verify_token")

                if access_token and instagram_account_id:
                    account_hash = acc.get("hash") or get_account_hash(instagram_account_id)

                    client = InstagramClient(
                        access_token=access_token,
                        instagram_account_id=instagram_account_id
                    )
                    await client.connect()

                    client_cache[account_hash] = client
                    account_cache[account_hash] = {
                        "credentials": creds,
                        "metadata": acc.get("metadata", {}),
                        "instagram_account_id": instagram_account_id,
                        "app_secret": app_secret,
                        "verify_token": verify_token
                    }

                    logger.info(f"Loaded Instagram account {instagram_account_id} ({account_hash})")
            except Exception as e:
                logger.error(f"Failed to load account: {e}")

        logger.info(f"Loaded {len(client_cache)} Instagram accounts from storage")
    except Exception as e:
        logger.error(f"Failed to load accounts: {e}")

    yield

    # Cleanup
    for client in client_cache.values():
        try:
            await client.close()
        except Exception as e:
            logger.error(f"Error closing client: {e}")

    await close_storage()
    logger.info("Instagram multi-tenant server stopped")


app = FastAPI(
    title="Instagram MCP Server (Multi-tenant)",
    description="REST API for multiple Instagram accounts with dynamic registration",
    version="2.0.0",
    lifespan=lifespan
)

# CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# ==================== Models ====================

class RegisterAccountRequest(BaseModel):
    """Request to register an Instagram account."""
    access_token: str = Field(..., description="Page Access Token with Instagram permissions")
    instagram_account_id: str = Field(..., description="Instagram Business Account ID")
    app_secret: Optional[str] = Field(None, description="Facebook App Secret for webhook signature verification")
    verify_token: Optional[str] = Field(None, description="Token for webhook verification (hub.challenge)")


class SendMessageRequest(BaseModel):
    """Request to send a message."""
    access_token: Optional[str] = Field(None, description="Access token (for auto-registration)")
    instagram_account_id: Optional[str] = Field(None, description="Account ID (for auto-registration)")
    recipient_id: str = Field(..., description="Instagram-scoped user ID")
    text: Optional[str] = None
    image_url: Optional[str] = None
    video_url: Optional[str] = None
    audio_url: Optional[str] = None


class SendQuickRepliesRequest(BaseModel):
    """Request to send quick replies."""
    access_token: Optional[str] = None
    instagram_account_id: Optional[str] = None
    recipient_id: str
    text: str
    quick_replies: List[dict] = Field(..., description="Quick reply buttons")


class ReplyCommentRequest(BaseModel):
    """Request to reply to comment."""
    access_token: Optional[str] = None
    instagram_account_id: Optional[str] = None
    comment_id: str
    message: str


class SetIceBreakersRequest(BaseModel):
    """Request to set ice breakers."""
    access_token: Optional[str] = None
    instagram_account_id: Optional[str] = None
    ice_breakers: List[dict] = Field(..., description="Ice breaker questions")


class NormalizedMessage(BaseModel):
    """Normalized message format for n8n."""
    channel: str = "instagram"
    message_id: str
    sender_id: str
    recipient_id: str
    instagram_account_id: Optional[str] = None  # Tenant identifier
    text: Optional[str] = None
    attachments: List[dict] = Field(default_factory=list)
    timestamp: datetime
    raw_data: dict


# ==================== Helper Functions ====================

def verify_signature(payload: bytes, signature: str, app_secret: str) -> bool:
    """Verify Facebook webhook signature."""
    if not app_secret or not signature:
        return True

    if not signature.startswith("sha256="):
        return False

    expected = "sha256=" + hmac.new(
        app_secret.encode(),
        payload,
        hashlib.sha256
    ).hexdigest()

    return hmac.compare_digest(expected, signature)


def normalize_message(entry: dict, account: dict) -> Optional[NormalizedMessage]:
    """Convert Instagram webhook message to normalized format."""
    messaging = entry.get("messaging", [])
    if not messaging:
        return None

    msg_event = messaging[0]
    sender_id = msg_event.get("sender", {}).get("id", "")
    recipient_id = msg_event.get("recipient", {}).get("id", "")
    timestamp = msg_event.get("timestamp", 0)
    message = msg_event.get("message", {})

    # Skip echo messages (sent by us)
    if message.get("is_echo"):
        return None

    text = message.get("text")
    attachments = []

    # Process attachments
    for att in message.get("attachments", []):
        att_type = att.get("type")
        payload = att.get("payload", {})

        if att_type == "image":
            attachments.append({
                "type": "photo",
                "url": payload.get("url")
            })
        elif att_type == "video":
            attachments.append({
                "type": "video",
                "url": payload.get("url")
            })
        elif att_type == "audio":
            attachments.append({
                "type": "audio",
                "url": payload.get("url")
            })
        elif att_type == "file":
            attachments.append({
                "type": "file",
                "url": payload.get("url")
            })
        elif att_type == "story_mention":
            attachments.append({
                "type": "story_mention",
                "url": payload.get("url")
            })

    # Handle quick reply
    quick_reply = message.get("quick_reply")
    if quick_reply:
        text = quick_reply.get("payload", text)

    return NormalizedMessage(
        channel="instagram",
        message_id=message.get("mid", ""),
        sender_id=sender_id,
        recipient_id=recipient_id,
        instagram_account_id=account.get("instagram_account_id"),
        text=text,
        attachments=attachments,
        timestamp=datetime.fromtimestamp(timestamp / 1000) if timestamp else datetime.now(),
        raw_data=entry
    )


async def forward_to_n8n(normalized: NormalizedMessage):
    """Forward normalized message to n8n webhook."""
    if not settings.N8N_WEBHOOK_URL:
        logger.warning("N8N_WEBHOOK_URL not configured")
        return

    try:
        async with httpx.AsyncClient(timeout=30.0) as client:
            # Convert to dict for n8n
            payload = {
                "channel": normalized.channel,
                "external_user_id": normalized.sender_id,
                "external_chat_id": normalized.sender_id,
                "external_message_id": normalized.message_id,
                # Tenant identifier for n8n resolution
                "instagram_account_id": normalized.instagram_account_id,
                "text": normalized.text,
                "timestamp": normalized.timestamp.isoformat(),
                "client_phone": None,
                "client_name": None,
                "media": {
                    "has_voice": any(a["type"] == "audio" for a in normalized.attachments),
                    "voice_url": next((a["url"] for a in normalized.attachments if a["type"] == "audio"), None),
                    "has_images": any(a["type"] == "photo" for a in normalized.attachments),
                    "images": [a for a in normalized.attachments if a["type"] == "photo"],
                    "has_video": any(a["type"] == "video" for a in normalized.attachments),
                    "videos": [a for a in normalized.attachments if a["type"] == "video"]
                },
                "meta": {
                    "ad_channel": "instagram",
                    "instagram_account_id": normalized.instagram_account_id,
                    "raw": normalized.raw_data
                }
            }

            response = await client.post(
                settings.N8N_WEBHOOK_URL,
                json=payload
            )
            if response.status_code >= 400:
                logger.error(f"n8n webhook error: {response.status_code} - {response.text}")
            else:
                logger.info(f"Forwarded to n8n: message_id={normalized.message_id}")
    except Exception as e:
        logger.error(f"Failed to forward to n8n: {e}")


def check_api_key(api_key: str = Header(None, alias="X-API-Key")):
    """Validate API key for protected endpoints."""
    if settings.API_KEY and api_key != settings.API_KEY:
        raise HTTPException(status_code=401, detail="Invalid API key")


# ==================== Health & Info ====================

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": "ok",
        "service": "instagram-mcp-server",
        "version": "2.0.0",
        "mode": "multi-tenant",
        "accounts_loaded": len(client_cache)
    }


@app.get("/info")
async def get_info(
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None)
):
    """Get account information."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
        try:
            account_info = await client.get_account_info()
            return {"status": "ok", "account": account_info}
        except InstagramAPIError as e:
            raise HTTPException(status_code=e.status_code, detail=e.message)

    # Return summary for all accounts
    return {
        "status": "ok",
        "mode": "multi-tenant",
        "accounts_count": len(client_cache)
    }


# ==================== Account Management ====================

@app.post("/accounts/register")
async def register_account(
    request: RegisterAccountRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Register a new Instagram account."""
    check_api_key(api_key)

    client, account_hash = await get_or_create_client(
        access_token=request.access_token,
        instagram_account_id=request.instagram_account_id,
        app_secret=request.app_secret,
        verify_token=request.verify_token
    )

    account = account_cache.get(account_hash, {})

    return {
        "success": True,
        "status": "registered",
        "account_hash": account_hash,
        "instagram_account_id": request.instagram_account_id,
        "username": account.get("metadata", {}).get("username", ""),
        "webhook_url": f"/webhook/instagram/{account_hash}"
    }


@app.get("/accounts")
async def list_accounts(api_key: str = Header(None, alias="X-API-Key")):
    """List all registered Instagram accounts."""
    check_api_key(api_key)

    accounts = []
    for account_hash, account in account_cache.items():
        accounts.append({
            "account_hash": account_hash,
            "instagram_account_id": account.get("instagram_account_id"),
            "username": account.get("metadata", {}).get("username", ""),
            "name": account.get("metadata", {}).get("name", ""),
            "webhook_url": f"/webhook/instagram/{account_hash}",
            "registered_at": account.get("metadata", {}).get("registered_at")
        })

    return {
        "success": True,
        "count": len(accounts),
        "accounts": accounts
    }


@app.get("/accounts/{account_hash}")
async def get_account_info_endpoint(
    account_hash: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get account info by hash."""
    check_api_key(api_key)

    account = get_account_by_hash(account_hash)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    return {
        "success": True,
        "account_hash": account_hash,
        "instagram_account_id": account.get("instagram_account_id"),
        "username": account.get("metadata", {}).get("username", ""),
        "name": account.get("metadata", {}).get("name", ""),
        "webhook_url": f"/webhook/instagram/{account_hash}",
        "registered_at": account.get("metadata", {}).get("registered_at")
    }


@app.delete("/accounts/{account_hash}")
async def unregister_account(
    account_hash: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Unregister an Instagram account."""
    check_api_key(api_key)

    if account_hash not in client_cache:
        raise HTTPException(status_code=404, detail="Account not found")

    # Close client
    client = client_cache.pop(account_hash)
    account = account_cache.pop(account_hash, {})
    await client.close()

    # Remove from storage
    await delete_account(CHANNEL_NAME, account_hash)

    return {
        "success": True,
        "status": "unregistered",
        "account_hash": account_hash,
        "instagram_account_id": account.get("instagram_account_id")
    }


# ==================== Messages ====================

@app.post("/api/send")
async def send_message(
    request: SendMessageRequest,
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None)
):
    """Send message to Instagram user."""
    check_api_key(api_key)

    if not request.text and not request.image_url and not request.video_url:
        raise HTTPException(status_code=400, detail="text, image_url, or video_url required")

    # Determine which client to use
    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.access_token and request.instagram_account_id:
        client, _ = await get_or_create_client(request.access_token, request.instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or (access_token + instagram_account_id)")

    try:
        if request.image_url:
            result = await client.send_image(
                recipient_id=request.recipient_id,
                image_url=request.image_url
            )
        elif request.video_url:
            result = await client.send_video(
                recipient_id=request.recipient_id,
                video_url=request.video_url
            )
        elif request.audio_url:
            result = await client.send_audio(
                recipient_id=request.recipient_id,
                audio_url=request.audio_url
            )
        else:
            result = await client.send_message(
                recipient_id=request.recipient_id,
                text=request.text
            )

        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/send/quick-replies")
async def send_quick_replies(
    request: SendQuickRepliesRequest,
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None)
):
    """Send message with quick reply buttons."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.access_token and request.instagram_account_id:
        client, _ = await get_or_create_client(request.access_token, request.instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.send_quick_replies(
            recipient_id=request.recipient_id,
            text=request.text,
            quick_replies=request.quick_replies
        )
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Conversations ====================

@app.get("/api/conversations")
async def get_conversations(
    limit: int = Query(20, ge=1, le=100),
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Get list of conversations."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.get_conversations(limit=limit)
        return {"status": "ok", "conversations": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/conversations/{conversation_id}/messages")
async def get_conversation_messages(
    conversation_id: str,
    limit: int = Query(20, ge=1, le=100),
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Get messages from a conversation."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.get_conversation_messages(
            conversation_id=conversation_id,
            limit=limit
        )
        return {"status": "ok", "messages": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== User Info ====================

@app.get("/api/user/{user_id}")
async def get_user_info(
    user_id: str,
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Get user info by Instagram-scoped ID."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.get_user_info(user_id)
        return {"status": "ok", "user": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Ice Breakers ====================

@app.post("/api/ice-breakers")
async def set_ice_breakers(
    request: SetIceBreakersRequest,
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None)
):
    """Set conversation ice breakers."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.access_token and request.instagram_account_id:
        client, _ = await get_or_create_client(request.access_token, request.instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.set_ice_breakers(request.ice_breakers)
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/ice-breakers")
async def get_ice_breakers(
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Get current ice breakers."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.get_ice_breakers()
        return {"status": "ok", "ice_breakers": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.delete("/api/ice-breakers")
async def delete_ice_breakers(
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Delete ice breakers."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.delete_ice_breakers()
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Comments ====================

@app.get("/api/media/{media_id}/comments")
async def get_media_comments(
    media_id: str,
    limit: int = Query(50, ge=1, le=100),
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Get comments on a media item."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.get_media_comments(media_id, limit=limit)
        return {"status": "ok", "comments": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/comments/{comment_id}/reply")
async def reply_to_comment(
    comment_id: str,
    request: ReplyCommentRequest,
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None)
):
    """Reply to a comment."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.access_token and request.instagram_account_id:
        client, _ = await get_or_create_client(request.access_token, request.instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.reply_to_comment(comment_id, request.message)
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/comments/{comment_id}/hide")
async def hide_comment(
    comment_id: str,
    hide: bool = Query(True),
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Hide or unhide a comment."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.hide_comment(comment_id, hide=hide)
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.delete("/api/comments/{comment_id}")
async def delete_comment(
    comment_id: str,
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Delete a comment."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.delete_comment(comment_id)
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Media ====================

@app.get("/api/media")
async def get_media(
    limit: int = Query(25, ge=1, le=100),
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Get account media (posts)."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.get_media(limit=limit)
        return {"status": "ok", "media": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/media/{media_id}")
async def get_media_item(
    media_id: str,
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Get specific media item."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.get_media_item(media_id)
        return {"status": "ok", "media": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Mentions ====================

@app.get("/api/mentions")
async def get_mentions(
    limit: int = Query(20, ge=1, le=100),
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Get media where account is mentioned."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.get_mentions(limit=limit)
        return {"status": "ok", "mentions": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Stories ====================

@app.get("/api/stories")
async def get_stories(
    api_key: str = Header(None, alias="X-API-Key"),
    account_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    instagram_account_id: Optional[str] = Query(None)
):
    """Get account stories."""
    check_api_key(api_key)

    if account_hash:
        client = get_client_by_hash(account_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and instagram_account_id:
        client, _ = await get_or_create_client(access_token, instagram_account_id)
    else:
        raise HTTPException(status_code=400, detail="Provide account_hash or credentials")

    try:
        result = await client.get_stories()
        return {"status": "ok", "stories": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Webhook Handler (Multi-tenant) ====================

@app.get("/webhook/instagram/{account_hash}")
async def webhook_verify_multitenant(
    account_hash: str,
    mode: str = Query(None, alias="hub.mode"),
    token: str = Query(None, alias="hub.verify_token"),
    challenge: str = Query(None, alias="hub.challenge")
):
    """Verify webhook subscription for specific account."""
    account = get_account_by_hash(account_hash)
    if not account:
        logger.warning(f"Webhook verification for unknown account: {account_hash}")
        raise HTTPException(status_code=404, detail="Account not found")

    # Get account-specific verify token
    expected_token = account.get("verify_token") or settings.WEBHOOK_VERIFY_TOKEN

    if mode == "subscribe" and token == expected_token:
        logger.info(f"Webhook verified for account {account_hash}")
        return PlainTextResponse(challenge)

    logger.warning(f"Webhook verification failed [{account_hash}]: mode={mode}, token={token}")
    raise HTTPException(status_code=403, detail="Verification failed")


@app.post("/webhook/instagram/{account_hash}")
async def webhook_handler_multitenant(
    account_hash: str,
    request: Request,
    background_tasks: BackgroundTasks,
    x_hub_signature_256: Optional[str] = Header(None, alias="X-Hub-Signature-256")
):
    """Handle incoming Instagram webhook events for specific account."""
    account = get_account_by_hash(account_hash)
    if not account:
        logger.warning(f"Webhook for unknown account: {account_hash}")
        raise HTTPException(status_code=404, detail="Account not found")

    body = await request.body()

    # Verify signature using account-specific app_secret
    app_secret = account.get("app_secret") or settings.FACEBOOK_APP_SECRET
    if app_secret:
        if not verify_signature(body, x_hub_signature_256 or "", app_secret):
            logger.warning(f"Invalid webhook signature for account {account_hash}")
            raise HTTPException(status_code=403, detail="Invalid signature")

    try:
        data = await request.json()
    except Exception as e:
        logger.error(f"Invalid JSON in webhook: {e}")
        raise HTTPException(status_code=400, detail="Invalid JSON")

    # Must be instagram object
    if data.get("object") != "instagram":
        logger.info(f"Ignoring non-instagram webhook: {data.get('object')}")
        return {"status": "ok"}

    logger.info(f"Received Instagram webhook [{account_hash}]")

    # Process entries
    for entry in data.get("entry", []):
        # Handle messaging events
        if "messaging" in entry:
            normalized = normalize_message(entry, account)
            if normalized:
                background_tasks.add_task(forward_to_n8n, normalized)

        # Handle changes (comments, mentions, etc.)
        for change in entry.get("changes", []):
            field = change.get("field")
            value = change.get("value", {})

            if field == "comments":
                logger.info(f"New comment [{account_hash}]: {value}")
            elif field == "mentions":
                logger.info(f"New mention [{account_hash}]: {value}")
            elif field == "story_insights":
                logger.info(f"Story insights [{account_hash}]: {value}")

    # Always return 200 to Facebook
    return {"status": "ok"}


# Legacy webhooks for backward compatibility
@app.get("/webhook/instagram")
async def webhook_verify_legacy(
    mode: str = Query(None, alias="hub.mode"),
    token: str = Query(None, alias="hub.verify_token"),
    challenge: str = Query(None, alias="hub.challenge")
):
    """Legacy webhook verification - uses global verify token."""
    if mode == "subscribe" and token == settings.WEBHOOK_VERIFY_TOKEN:
        logger.info("Legacy webhook verified")
        return PlainTextResponse(challenge)

    logger.warning(f"Legacy webhook verification failed: mode={mode}")
    raise HTTPException(status_code=403, detail="Verification failed")


@app.post("/webhook/instagram")
async def webhook_handler_legacy(
    request: Request,
    background_tasks: BackgroundTasks,
    x_hub_signature_256: Optional[str] = Header(None, alias="X-Hub-Signature-256")
):
    """Legacy webhook handler - tries to match by recipient_id."""
    body = await request.body()

    # Verify signature with global secret
    if settings.FACEBOOK_APP_SECRET:
        if not verify_signature(body, x_hub_signature_256 or "", settings.FACEBOOK_APP_SECRET):
            logger.warning("Invalid webhook signature (legacy)")
            raise HTTPException(status_code=403, detail="Invalid signature")

    try:
        data = await request.json()
    except Exception as e:
        logger.error(f"Invalid JSON in webhook: {e}")
        raise HTTPException(status_code=400, detail="Invalid JSON")

    if data.get("object") != "instagram":
        return {"status": "ok"}

    logger.info("Received legacy Instagram webhook")

    # Process entries and try to match to accounts
    for entry in data.get("entry", []):
        if "messaging" in entry:
            messaging = entry.get("messaging", [])
            if messaging:
                recipient_id = messaging[0].get("recipient", {}).get("id", "")

                # Try to find matching account by instagram_account_id
                matched_account = None
                for acc in account_cache.values():
                    if acc.get("instagram_account_id") == recipient_id:
                        matched_account = acc
                        break

                if matched_account:
                    normalized = normalize_message(entry, matched_account)
                    if normalized:
                        background_tasks.add_task(forward_to_n8n, normalized)
                else:
                    logger.warning(f"Legacy webhook: no account matches recipient {recipient_id}")

    return {"status": "ok"}


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host=settings.SERVER_HOST,
        port=settings.SERVER_PORT,
        reload=True
    )
