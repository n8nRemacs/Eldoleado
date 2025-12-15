"""MAX.ru MCP Server - Multi-tenant Direct Bot API.

FastAPI server for MAX.ru Bot API integration.
Supports multiple bot accounts with dynamic registration.
"""

import sys
import os
import logging
import hashlib
import hmac
from datetime import datetime
from typing import Optional, List, Dict
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, Header, Request, Query, BackgroundTasks, Depends
from fastapi.responses import JSONResponse
from pydantic import BaseModel, Field
import httpx

from config import settings
from max_client import MaxClient, MaxAPIError

# Add shared module to path
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..'))
from shared import storage

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Channel name for storage
CHANNEL_NAME = "max"

# In-memory client cache: token_hash -> MaxClient
client_cache: Dict[str, MaxClient] = {}
# Account data cache: token_hash -> {credentials, metadata}
account_cache: Dict[str, dict] = {}


def get_token_hash(access_token: str) -> str:
    """Generate 16-char hash from access token for webhook URL."""
    return hashlib.sha256(access_token.encode()).hexdigest()[:16]


async def get_or_create_client(access_token: str) -> tuple[MaxClient, str]:
    """Get existing client or create and register new one."""
    token_hash = get_token_hash(access_token)

    if token_hash in client_cache:
        return client_cache[token_hash], token_hash

    # Auto-register new account
    client = MaxClient(access_token)
    await client.connect()

    # Get bot info
    try:
        bot_info = await client.get_me()
    except Exception as e:
        await client.close()
        raise HTTPException(status_code=401, detail=f"Invalid access token: {e}")

    # Save to storage
    credentials = {"access_token": access_token}
    metadata = {"bot_info": bot_info}
    await storage.save_account(CHANNEL_NAME, token_hash, credentials, metadata)

    # Cache
    client_cache[token_hash] = client
    account_cache[token_hash] = {"credentials": credentials, "metadata": metadata}

    logger.info(f"Auto-registered MAX account: {token_hash} ({bot_info.get('name', 'Unknown')})")
    return client, token_hash


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan manager."""
    logger.info("Starting MAX MCP Server (Multi-tenant)...")

    # Initialize storage
    await storage.init_storage()

    # Load existing accounts from Redis
    accounts = await storage.load_accounts(CHANNEL_NAME)
    for acc in accounts:
        try:
            token_hash = acc["hash"]
            access_token = acc["credentials"]["access_token"]

            client = MaxClient(access_token)
            await client.connect()

            client_cache[token_hash] = client
            account_cache[token_hash] = acc

            bot_name = acc.get("metadata", {}).get("bot_info", {}).get("name", "Unknown")
            logger.info(f"Loaded MAX account: {token_hash} ({bot_name})")
        except Exception as e:
            logger.error(f"Failed to load account {acc.get('hash', '?')}: {e}")

    logger.info(f"Loaded {len(client_cache)} MAX accounts")

    yield

    # Cleanup: close all clients
    for token_hash, client in client_cache.items():
        try:
            await client.close()
        except Exception as e:
            logger.error(f"Error closing client {token_hash}: {e}")

    await storage.close_storage()
    logger.info("MAX MCP Server stopped")


app = FastAPI(
    title="MAX.ru MCP Server",
    description="Multi-tenant Direct Bot API integration for MAX.ru messenger",
    version="2.0.0",
    lifespan=lifespan
)


# ==================== Models ====================

class RegisterAccountRequest(BaseModel):
    """Request to register a new account."""
    access_token: str = Field(..., description="MAX Bot access token")


class SendMessageRequest(BaseModel):
    """Request to send a message."""
    access_token: Optional[str] = None  # For auto-register
    chat_id: Optional[int] = None
    user_id: Optional[int] = None
    text: Optional[str] = None
    attachments: Optional[List[dict]] = None
    format: Optional[str] = None
    notify: bool = True
    disable_link_preview: bool = False


class EditMessageRequest(BaseModel):
    """Request to edit a message."""
    access_token: Optional[str] = None
    message_id: str
    text: Optional[str] = None
    attachments: Optional[List[dict]] = None
    format: Optional[str] = None


class SubscribeRequest(BaseModel):
    """Request to subscribe to webhook."""
    access_token: Optional[str] = None
    url: str
    update_types: Optional[List[str]] = None
    version: Optional[str] = None
    secret: Optional[str] = None


class AnswerCallbackRequest(BaseModel):
    """Request to answer callback."""
    access_token: Optional[str] = None
    callback_id: str
    notification: Optional[str] = None
    message: Optional[dict] = None


class NormalizedMessage(BaseModel):
    """Normalized message format for n8n."""
    channel: str = "max"
    access_token: str  # For tenant resolution
    token_hash: str
    message_id: str
    chat_id: int
    user_id: int
    user_name: Optional[str] = None
    text: Optional[str] = None
    attachments: List[dict] = Field(default_factory=list)
    timestamp: datetime
    raw_data: dict


# ==================== Helper Functions ====================

def verify_signature(body: bytes, signature: str, secret: str) -> bool:
    """Verify MAX webhook signature."""
    if not secret or not signature:
        return True
    expected = hmac.new(secret.encode(), body, hashlib.sha256).hexdigest()
    return hmac.compare_digest(expected, signature)


def normalize_update(update: dict, access_token: str, token_hash: str) -> Optional[NormalizedMessage]:
    """Convert MAX update to normalized format with tenant info."""
    update_type = update.get("update_type")

    if update_type == "message_created":
        message = update.get("message", {})
        sender = message.get("sender", {})
        body = message.get("body", {})
        recipient = message.get("recipient", {})
        chat_id = recipient.get("chat_id") or sender.get("user_id")

        attachments = []
        if body.get("attachments"):
            for att in body["attachments"]:
                att_type = att.get("type")
                payload = att.get("payload", {})
                if att_type in ["image", "video", "audio", "file"]:
                    attachments.append({
                        "type": "photo" if att_type == "image" else att_type,
                        "url": payload.get("url"),
                        "filename": payload.get("filename")
                    })

        return NormalizedMessage(
            channel="max",
            access_token=access_token,
            token_hash=token_hash,
            message_id=message.get("mid", ""),
            chat_id=chat_id,
            user_id=sender.get("user_id", 0),
            user_name=sender.get("name"),
            text=body.get("text"),
            attachments=attachments,
            timestamp=datetime.fromtimestamp(message.get("timestamp", 0) / 1000),
            raw_data=update
        )

    elif update_type == "message_callback":
        callback = update.get("callback", {})
        sender = callback.get("user", {})
        message = callback.get("message", {})

        return NormalizedMessage(
            channel="max",
            access_token=access_token,
            token_hash=token_hash,
            message_id=callback.get("callback_id", ""),
            chat_id=message.get("recipient", {}).get("chat_id", 0),
            user_id=sender.get("user_id", 0),
            user_name=sender.get("name"),
            text=callback.get("payload"),
            attachments=[{"type": "callback", "callback_id": callback.get("callback_id")}],
            timestamp=datetime.fromtimestamp(callback.get("timestamp", 0) / 1000),
            raw_data=update
        )

    return None


async def forward_to_n8n(normalized: NormalizedMessage):
    """Forward normalized message to n8n webhook."""
    if not settings.N8N_WEBHOOK_URL:
        logger.warning("N8N_WEBHOOK_URL not configured")
        return

    try:
        async with httpx.AsyncClient(timeout=30.0) as client:
            response = await client.post(
                settings.N8N_WEBHOOK_URL,
                json=normalized.model_dump(mode='json')
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


# ==================== Account Management ====================

@app.post("/accounts/register")
async def register_account(
    request: RegisterAccountRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Register a new MAX bot account."""
    check_api_key(api_key)

    token_hash = get_token_hash(request.access_token)

    # Check if already registered
    if token_hash in client_cache:
        account = account_cache.get(token_hash, {})
        bot_info = account.get("metadata", {}).get("bot_info", {})
        return {
            "status": "already_registered",
            "token_hash": token_hash,
            "bot_info": bot_info,
            "webhook_path": f"/webhook/max/{token_hash}"
        }

    # Create and validate client
    client = MaxClient(request.access_token)
    await client.connect()

    try:
        bot_info = await client.get_me()
    except Exception as e:
        await client.close()
        raise HTTPException(status_code=401, detail=f"Invalid access token: {e}")

    # Save to storage
    credentials = {"access_token": request.access_token}
    metadata = {"bot_info": bot_info}
    await storage.save_account(CHANNEL_NAME, token_hash, credentials, metadata)

    # Cache
    client_cache[token_hash] = client
    account_cache[token_hash] = {"credentials": credentials, "metadata": metadata}

    logger.info(f"Registered MAX account: {token_hash} ({bot_info.get('name', 'Unknown')})")

    return {
        "status": "registered",
        "token_hash": token_hash,
        "bot_info": bot_info,
        "webhook_path": f"/webhook/max/{token_hash}"
    }


@app.get("/accounts")
async def list_accounts(api_key: str = Header(None, alias="X-API-Key")):
    """List all registered accounts."""
    check_api_key(api_key)

    accounts = []
    for token_hash, data in account_cache.items():
        bot_info = data.get("metadata", {}).get("bot_info", {})
        accounts.append({
            "token_hash": token_hash,
            "bot_name": bot_info.get("name"),
            "bot_id": bot_info.get("user_id"),
            "webhook_path": f"/webhook/max/{token_hash}"
        })

    return {"accounts": accounts, "count": len(accounts)}


@app.get("/accounts/{token_hash}")
async def get_account(
    token_hash: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get account details."""
    check_api_key(api_key)

    if token_hash not in account_cache:
        raise HTTPException(status_code=404, detail="Account not found")

    data = account_cache[token_hash]
    bot_info = data.get("metadata", {}).get("bot_info", {})

    return {
        "token_hash": token_hash,
        "bot_info": bot_info,
        "webhook_path": f"/webhook/max/{token_hash}"
    }


@app.delete("/accounts/{token_hash}")
async def unregister_account(
    token_hash: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Unregister an account."""
    check_api_key(api_key)

    if token_hash not in client_cache:
        raise HTTPException(status_code=404, detail="Account not found")

    # Close client
    client = client_cache.pop(token_hash)
    await client.close()

    # Remove from cache
    account_cache.pop(token_hash, None)

    # Remove from storage
    await storage.delete_account(CHANNEL_NAME, token_hash)

    logger.info(f"Unregistered MAX account: {token_hash}")

    return {"status": "unregistered", "token_hash": token_hash}


# ==================== Health & Info ====================

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": "ok",
        "service": "max-mcp-server",
        "version": "2.0.0",
        "accounts": len(client_cache)
    }


@app.get("/info")
async def get_info(
    access_token: Optional[str] = Query(None),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get bot information."""
    check_api_key(api_key)

    if not access_token:
        raise HTTPException(status_code=400, detail="access_token required")

    client, _ = await get_or_create_client(access_token)

    try:
        bot_info = await client.get_me()
        return {"status": "ok", "bot": bot_info}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Messages ====================

@app.post("/api/send")
async def send_message(
    request: SendMessageRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Send message to chat or user."""
    check_api_key(api_key)

    if not request.access_token:
        raise HTTPException(status_code=400, detail="access_token required")

    if not request.chat_id and not request.user_id:
        raise HTTPException(status_code=400, detail="chat_id or user_id required")

    if not request.text and not request.attachments:
        raise HTTPException(status_code=400, detail="text or attachments required")

    client, _ = await get_or_create_client(request.access_token)

    try:
        result = await client.send_message(
            chat_id=request.chat_id,
            user_id=request.user_id,
            text=request.text,
            attachments=request.attachments,
            format=request.format,
            notify=request.notify,
            disable_link_preview=request.disable_link_preview
        )
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.put("/api/message/{message_id}")
async def edit_message(
    message_id: str,
    request: EditMessageRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Edit existing message."""
    check_api_key(api_key)

    if not request.access_token:
        raise HTTPException(status_code=400, detail="access_token required")

    client, _ = await get_or_create_client(request.access_token)

    try:
        result = await client.edit_message(
            message_id=message_id,
            text=request.text,
            attachments=request.attachments,
            format=request.format
        )
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.delete("/api/message/{message_id}")
async def delete_message(
    message_id: str,
    access_token: str = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Delete message."""
    check_api_key(api_key)

    client, _ = await get_or_create_client(access_token)

    try:
        result = await client.delete_message(message_id)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/message/{message_id}")
async def get_message(
    message_id: str,
    access_token: str = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get message by ID."""
    check_api_key(api_key)

    client, _ = await get_or_create_client(access_token)

    try:
        result = await client.get_message(message_id)
        return {"status": "ok", "message": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Chats ====================

@app.get("/api/chats")
async def get_chats(
    access_token: str = Query(...),
    count: int = Query(50, ge=1, le=100),
    marker: Optional[int] = None,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get list of chats."""
    check_api_key(api_key)

    client, _ = await get_or_create_client(access_token)

    try:
        result = await client.get_chats(count=count, marker=marker)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/chat/{chat_id}")
async def get_chat(
    chat_id: int,
    access_token: str = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get chat by ID."""
    check_api_key(api_key)

    client, _ = await get_or_create_client(access_token)

    try:
        result = await client.get_chat(chat_id)
        return {"status": "ok", "chat": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/chat/{chat_id}/action")
async def send_action(
    chat_id: int,
    access_token: str = Query(...),
    action: str = Query("typing_on"),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Send typing action to chat."""
    check_api_key(api_key)

    client, _ = await get_or_create_client(access_token)

    try:
        result = await client.send_action(chat_id, action)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/chat/{chat_id}/members")
async def get_chat_members(
    chat_id: int,
    access_token: str = Query(...),
    count: int = Query(20, ge=1, le=100),
    marker: Optional[int] = None,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get chat members."""
    check_api_key(api_key)

    client, _ = await get_or_create_client(access_token)

    try:
        result = await client.get_chat_members(chat_id, count=count, marker=marker)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Subscriptions ====================

@app.post("/api/subscribe")
async def subscribe(
    request: SubscribeRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Subscribe to webhook updates."""
    check_api_key(api_key)

    if not request.access_token:
        raise HTTPException(status_code=400, detail="access_token required")

    client, _ = await get_or_create_client(request.access_token)

    try:
        result = await client.subscribe(
            url=request.url,
            update_types=request.update_types,
            version=request.version,
            secret=request.secret
        )
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.delete("/api/subscribe")
async def unsubscribe(
    access_token: str = Query(...),
    url: str = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Unsubscribe from webhook."""
    check_api_key(api_key)

    client, _ = await get_or_create_client(access_token)

    try:
        result = await client.unsubscribe(url)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/subscriptions")
async def get_subscriptions(
    access_token: str = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get current subscriptions."""
    check_api_key(api_key)

    client, _ = await get_or_create_client(access_token)

    try:
        result = await client.get_subscriptions()
        return {"status": "ok", "subscriptions": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Callbacks ====================

@app.post("/api/answer")
async def answer_callback(
    request: AnswerCallbackRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Answer to callback button click."""
    check_api_key(api_key)

    if not request.access_token:
        raise HTTPException(status_code=400, detail="access_token required")

    client, _ = await get_or_create_client(request.access_token)

    try:
        result = await client.answer_callback(
            callback_id=request.callback_id,
            notification=request.notification,
            message=request.message
        )
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Webhook Handler (Multi-tenant) ====================

@app.post("/webhook/max/{token_hash}")
async def webhook_handler(
    token_hash: str,
    request: Request,
    background_tasks: BackgroundTasks,
    x_max_signature: Optional[str] = Header(None, alias="X-Max-Signature")
):
    """Handle incoming MAX webhook updates for specific account."""

    # Check if account exists
    if token_hash not in client_cache:
        logger.warning(f"Webhook for unknown account: {token_hash}")
        raise HTTPException(status_code=404, detail="Account not registered")

    account = account_cache.get(token_hash, {})
    access_token = account.get("credentials", {}).get("access_token", "")
    webhook_secret = account.get("metadata", {}).get("webhook_secret")

    body = await request.body()

    # Verify signature if secret is configured
    if webhook_secret:
        if not verify_signature(body, x_max_signature or "", webhook_secret):
            logger.warning(f"Invalid webhook signature for {token_hash}")
            raise HTTPException(status_code=403, detail="Invalid signature")

    try:
        data = await request.json()
    except Exception as e:
        logger.error(f"Invalid JSON in webhook: {e}")
        raise HTTPException(status_code=400, detail="Invalid JSON")

    logger.info(f"Received webhook for {token_hash}: {data.get('update_type', 'unknown')}")

    # Normalize with tenant info and forward to n8n
    normalized = normalize_update(data, access_token, token_hash)
    if normalized:
        background_tasks.add_task(forward_to_n8n, normalized)

    return {"success": True}


# Legacy webhook (for backward compatibility)
@app.post("/webhook/max")
async def webhook_handler_legacy(
    request: Request,
    background_tasks: BackgroundTasks,
    x_max_signature: Optional[str] = Header(None, alias="X-Max-Signature")
):
    """Legacy webhook handler - requires default account."""
    # Find first registered account as default
    if not client_cache:
        raise HTTPException(status_code=404, detail="No accounts registered")

    token_hash = next(iter(client_cache.keys()))

    # Forward to multi-tenant handler
    return await webhook_handler(
        token_hash=token_hash,
        request=request,
        background_tasks=background_tasks,
        x_max_signature=x_max_signature
    )


# ==================== Polling Mode ====================

@app.get("/api/updates")
async def get_updates(
    access_token: str = Query(...),
    limit: int = Query(100, ge=1, le=1000),
    timeout: int = Query(30, ge=0, le=90),
    marker: Optional[int] = None,
    types: Optional[str] = None,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get updates via long polling."""
    check_api_key(api_key)

    client, _ = await get_or_create_client(access_token)
    types_list = types.split(",") if types else None

    try:
        result = await client.get_updates(
            limit=limit,
            timeout=timeout,
            marker=marker,
            types=types_list
        )
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Upload ====================

@app.post("/api/upload-url")
async def get_upload_url(
    access_token: str = Query(...),
    type: str = Query("photo"),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get URL for uploading attachments."""
    check_api_key(api_key)

    if type not in ["photo", "video", "audio", "file"]:
        raise HTTPException(status_code=400, detail="Invalid upload type")

    client, _ = await get_or_create_client(access_token)

    try:
        result = await client.get_upload_url(type)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host=settings.SERVER_HOST,
        port=settings.SERVER_PORT,
        reload=True
    )
