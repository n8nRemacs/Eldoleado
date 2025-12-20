"""Avito Messenger REST API Server (Multi-tenant).

FastAPI server providing HTTP endpoints for Avito Messenger API integration.
Supports multiple Avito accounts with dynamic registration.
Version 2.0.0 - Multi-tenant architecture with Redis + PostgreSQL storage.
"""

import sys
import hashlib
import logging
from typing import Optional, List, Dict, Any
from datetime import datetime
from contextlib import asynccontextmanager

import httpx
from fastapi import FastAPI, Request, HTTPException, Depends, Header, Query, Body
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

from config import settings
from avito_client import AvitoClient, AvitoAPIError, RateLimitExceeded

# Add shared module to path
sys.path.insert(0, str(__file__).replace("\\", "/").rsplit("/", 2)[0])
from shared.storage import (
    init_storage, close_storage, get_credentials_hash,
    save_account, load_accounts, get_account, delete_account
)
from shared.health import get_health_checker, HealthStatus
from shared.alerts import get_alert_service, AlertConfig

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# Channel name for storage
CHANNEL_NAME = "avito"

# Initialize health checker and alert service
health_checker = get_health_checker("avito", "2.1.0")
alert_service = get_alert_service(AlertConfig(
    telegram_bot_token=getattr(settings, 'alert_telegram_bot_token', None),
    telegram_chat_id=getattr(settings, 'alert_telegram_chat_id', None),
    n8n_webhook_url=getattr(settings, 'alert_n8n_webhook_url', None),
))

# Multi-tenant registries
client_cache: Dict[str, AvitoClient] = {}  # user_hash -> AvitoClient
account_cache: Dict[str, dict] = {}         # user_hash -> account data


def get_user_hash(user_id: str) -> str:
    """Generate 16-char hash from user_id for webhook URL."""
    return hashlib.sha256(user_id.encode()).hexdigest()[:16]


async def get_or_create_client(
    client_id: str,
    client_secret: str,
    user_id: str
) -> tuple[AvitoClient, str]:
    """Get existing client or create new one with auto-registration."""
    user_hash = get_user_hash(user_id)

    if user_hash in client_cache:
        return client_cache[user_hash], user_hash

    # Create new client with per-account Redis token key
    redis_token_key = f"avito_token:{user_id}"

    client = AvitoClient(
        client_id=client_id,
        client_secret=client_secret,
        user_id=user_id,
        redis_token_key=redis_token_key
    )
    await client.connect()

    # Save to storage
    credentials = {
        "client_id": client_id,
        "client_secret": client_secret,
        "user_id": user_id
    }

    metadata = {
        "redis_token_key": redis_token_key,
        "registered_at": datetime.utcnow().isoformat()
    }

    await save_account(CHANNEL_NAME, user_hash, credentials, metadata)

    # Cache
    client_cache[user_hash] = client
    account_cache[user_hash] = {
        "credentials": credentials,
        "metadata": metadata,
        "user_id": user_id
    }

    logger.info(f"Registered Avito user {user_id[:8]}... with hash {user_hash}")
    return client, user_hash


def get_client_by_hash(user_hash: str) -> Optional[AvitoClient]:
    """Get client by user hash."""
    return client_cache.get(user_hash)


def get_account_by_hash(user_hash: str) -> Optional[dict]:
    """Get account data by hash."""
    return account_cache.get(user_hash)


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan - init and cleanup."""
    # Initialize storage
    await init_storage()
    logger.info("Storage initialized")

    # Load existing accounts
    try:
        accounts = await load_accounts(CHANNEL_NAME)
        for acc in accounts:
            try:
                creds = acc.get("credentials", {})
                client_id = creds.get("client_id")
                client_secret = creds.get("client_secret")
                user_id = creds.get("user_id")

                if client_id and client_secret and user_id:
                    user_hash = acc.get("hash") or get_user_hash(user_id)
                    redis_token_key = acc.get("metadata", {}).get("redis_token_key", f"avito_token:{user_id}")

                    client = AvitoClient(
                        client_id=client_id,
                        client_secret=client_secret,
                        user_id=user_id,
                        redis_token_key=redis_token_key
                    )
                    await client.connect()

                    client_cache[user_hash] = client
                    account_cache[user_hash] = {
                        "credentials": creds,
                        "metadata": acc.get("metadata", {}),
                        "user_id": user_id
                    }

                    logger.info(f"Loaded Avito user {user_id[:8]}... ({user_hash})")
            except Exception as e:
                logger.error(f"Failed to load account: {e}")

        logger.info(f"Loaded {len(client_cache)} Avito accounts from storage")
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
    logger.info("Avito multi-tenant server stopped")


app = FastAPI(
    title="Avito Messenger API (Multi-tenant)",
    description="REST API for multiple Avito accounts with dynamic registration",
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


# ========== Pydantic Models ==========

class RegisterAccountRequest(BaseModel):
    client_id: str = Field(..., description="Avito API Client ID")
    client_secret: str = Field(..., description="Avito API Client Secret")
    user_id: str = Field(..., description="Avito User ID")


class SendMessageRequest(BaseModel):
    client_id: Optional[str] = Field(None, description="Avito Client ID (for auto-registration)")
    client_secret: Optional[str] = Field(None, description="Avito Client Secret (for auto-registration)")
    user_id: Optional[str] = Field(None, description="Avito User ID (for auto-registration)")
    chat_id: str = Field(..., description="Chat ID")
    text: str = Field(..., description="Message text")


class SendImageRequest(BaseModel):
    client_id: Optional[str] = None
    client_secret: Optional[str] = None
    user_id: Optional[str] = None
    chat_id: str = Field(..., description="Chat ID")
    image_url: str = Field(..., description="Image URL")


class SendLinkRequest(BaseModel):
    client_id: Optional[str] = None
    client_secret: Optional[str] = None
    user_id: Optional[str] = None
    chat_id: str = Field(..., description="Chat ID")
    url: str = Field(..., description="Link URL")
    text: Optional[str] = Field(None, description="Optional text")


class BulkMessageRequest(BaseModel):
    client_id: Optional[str] = None
    client_secret: Optional[str] = None
    user_id: Optional[str] = None
    messages: List[dict] = Field(..., description="List of messages with chat_id and text")


class WebhookSubscribeRequest(BaseModel):
    client_id: Optional[str] = None
    client_secret: Optional[str] = None
    user_id: Optional[str] = None
    url: str = Field(..., description="Webhook URL")
    version: int = Field(2, description="API version (1 or 2)")


class BlacklistRequest(BaseModel):
    client_id: Optional[str] = None
    client_secret: Optional[str] = None
    user_id: Optional[str] = None
    blocked_user_id: int = Field(..., description="User ID to block")


class ApiResponse(BaseModel):
    success: bool
    data: Optional[dict] = None
    error: Optional[str] = None


# ========== Auth Dependency ==========

async def verify_api_key(x_api_key: Optional[str] = Header(None)):
    """Verify API key if configured."""
    if settings.api_key and settings.api_key != x_api_key:
        raise HTTPException(status_code=401, detail="Invalid API key")
    return True


# ========== Error Handler ==========

@app.exception_handler(AvitoAPIError)
async def avito_error_handler(request: Request, exc: AvitoAPIError):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "success": False,
            "error": exc.message,
            "details": exc.response
        }
    )


@app.exception_handler(RateLimitExceeded)
async def rate_limit_handler(request: Request, exc: RateLimitExceeded):
    return JSONResponse(
        status_code=429,
        content={
            "success": False,
            "error": str(exc)
        }
    )


# ========== Health & Info ==========

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": health_checker.get_status().value,
        "service": "avito-messenger-api",
        "version": "2.1.0",
        "mode": "multi-tenant",
        "accounts_loaded": len(client_cache),
        "health_score": health_checker.calculate_health_score(),
        "timestamp": datetime.now().isoformat()
    }


@app.get("/health/extended")
async def health_extended():
    """Extended health check with metrics."""
    return health_checker.to_dict()


@app.get("/info")
async def server_info():
    """Server configuration info."""
    return {
        "service": "avito-messenger-api",
        "version": "2.0.0",
        "mode": "multi-tenant",
        "accounts_count": len(client_cache),
        "rate_limit_requests": settings.rate_limit_requests,
        "rate_limit_window": settings.rate_limit_window,
        "max_connections": settings.max_connections
    }


# ========== Account Management ==========

@app.post("/accounts/register", dependencies=[Depends(verify_api_key)])
async def register_account(request: RegisterAccountRequest):
    """Register a new Avito account."""
    client, user_hash = await get_or_create_client(
        client_id=request.client_id,
        client_secret=request.client_secret,
        user_id=request.user_id
    )

    # Verify credentials by getting token
    try:
        await client.get_access_token()
    except Exception as e:
        # Remove failed registration
        if user_hash in client_cache:
            await client_cache.pop(user_hash).close()
            account_cache.pop(user_hash, None)
            await delete_account(CHANNEL_NAME, user_hash)
        raise HTTPException(status_code=400, detail=f"Failed to authenticate: {e}")

    return {
        "success": True,
        "status": "registered",
        "user_hash": user_hash,
        "user_id": request.user_id[:8] + "...",
        "webhook_url": f"/webhook/avito/{user_hash}"
    }


@app.get("/accounts", dependencies=[Depends(verify_api_key)])
async def list_accounts():
    """List all registered Avito accounts."""
    accounts = []
    for user_hash, account in account_cache.items():
        user_id = account.get("user_id", "")
        accounts.append({
            "user_hash": user_hash,
            "user_id": user_id[:8] + "..." if user_id else "",
            "webhook_url": f"/webhook/avito/{user_hash}",
            "registered_at": account.get("metadata", {}).get("registered_at")
        })

    return {
        "success": True,
        "count": len(accounts),
        "accounts": accounts
    }


@app.get("/accounts/{user_hash}", dependencies=[Depends(verify_api_key)])
async def get_account_info(user_hash: str):
    """Get account info by hash."""
    account = get_account_by_hash(user_hash)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    user_id = account.get("user_id", "")
    return {
        "success": True,
        "user_hash": user_hash,
        "user_id": user_id[:8] + "..." if user_id else "",
        "webhook_url": f"/webhook/avito/{user_hash}",
        "registered_at": account.get("metadata", {}).get("registered_at")
    }


@app.delete("/accounts/{user_hash}", dependencies=[Depends(verify_api_key)])
async def unregister_account(user_hash: str):
    """Unregister an Avito account."""
    if user_hash not in client_cache:
        raise HTTPException(status_code=404, detail="Account not found")

    # Close client
    client = client_cache.pop(user_hash)
    account = account_cache.pop(user_hash, {})
    await client.close()

    # Remove from storage
    await delete_account(CHANNEL_NAME, user_hash)

    user_id = account.get("user_id", "")
    return {
        "success": True,
        "status": "unregistered",
        "user_hash": user_hash,
        "user_id": user_id[:8] + "..." if user_id else ""
    }


# ========== Auth Endpoints ==========

@app.get("/api/token", dependencies=[Depends(verify_api_key)])
async def get_token(
    force_refresh: bool = False,
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Get or refresh access token."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    token = await client.get_access_token(force_refresh=force_refresh)
    return {
        "success": True,
        "token": token[:20] + "..." if len(token) > 20 else token,
        "message": "Token retrieved"
    }


@app.post("/api/token/refresh", dependencies=[Depends(verify_api_key)])
async def refresh_token(
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Force refresh access token."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    token = await client.refresh_token()
    return {
        "success": True,
        "token": token[:20] + "..." if len(token) > 20 else token,
        "message": "Token refreshed"
    }


# ========== Chat Endpoints ==========

@app.get("/api/chats", dependencies=[Depends(verify_api_key)])
async def get_chats(
    item_ids: Optional[str] = Query(None, description="Comma-separated item IDs"),
    unread_only: bool = Query(False, description="Return only unread chats"),
    limit: int = Query(100, ge=1, le=100),
    offset: int = Query(0, ge=0),
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Get list of chats."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    item_ids_list = [int(x) for x in item_ids.split(",")] if item_ids else None
    chats = await client.get_chats(
        item_ids=item_ids_list,
        unread_only=unread_only,
        limit=limit,
        offset=offset
    )
    return {
        "success": True,
        "count": len(chats),
        "chats": [c.to_dict() for c in chats]
    }


@app.get("/api/chats/{chat_id}", dependencies=[Depends(verify_api_key)])
async def get_chat(
    chat_id: str,
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Get chat by ID."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    chat = await client.get_chat(chat_id)
    return {
        "success": True,
        "chat": chat.to_dict()
    }


@app.post("/api/chats/{chat_id}/read", dependencies=[Depends(verify_api_key)])
async def mark_chat_read(
    chat_id: str,
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Mark chat as read."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    await client.mark_chat_read(chat_id)
    return {
        "success": True,
        "chat_id": chat_id,
        "message": "Chat marked as read"
    }


@app.get("/api/chats/unread/count", dependencies=[Depends(verify_api_key)])
async def get_unread_count(
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Get total unread message count."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    chats = await client.get_chats(unread_only=True)
    total_unread = sum(c.unread_count for c in chats)
    return {
        "success": True,
        "total_unread": total_unread,
        "unread_chats": len(chats)
    }


# ========== Message Endpoints ==========

@app.get("/api/chats/{chat_id}/messages", dependencies=[Depends(verify_api_key)])
async def get_messages(
    chat_id: str,
    limit: int = Query(100, ge=1, le=100),
    offset: int = Query(0, ge=0),
    version: int = Query(1, ge=1, le=2),
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Get messages from chat."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    if version == 2:
        messages = await client.get_messages_v2(chat_id, limit, offset)
    else:
        messages = await client.get_messages(chat_id, limit, offset)

    return {
        "success": True,
        "chat_id": chat_id,
        "count": len(messages),
        "messages": [m.to_dict() for m in messages]
    }


@app.post("/api/messages/send", dependencies=[Depends(verify_api_key)])
async def send_message(request: SendMessageRequest, user_hash: Optional[str] = Query(None)):
    """Send text message."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.client_id and request.client_secret and request.user_id:
        client, user_hash = await get_or_create_client(request.client_id, request.client_secret, request.user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    try:
        result = await client.send_message(
            chat_id=request.chat_id,
            text=request.text
        )
        health_checker.record_message_sent()
        return {
            "success": True,
            "chat_id": request.chat_id,
            "message": "Message sent",
            "response": result
        }
    except Exception as e:
        health_checker.record_message_failed()
        health_checker.record_error(str(e))
        raise


@app.post("/api/messages/send/image", dependencies=[Depends(verify_api_key)])
async def send_image(request: SendImageRequest, user_hash: Optional[str] = Query(None)):
    """Send image message."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.client_id and request.client_secret and request.user_id:
        client, _ = await get_or_create_client(request.client_id, request.client_secret, request.user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    result = await client.send_image(
        chat_id=request.chat_id,
        image_url=request.image_url
    )
    return {
        "success": True,
        "chat_id": request.chat_id,
        "message": "Image sent",
        "response": result
    }


@app.post("/api/messages/send/link", dependencies=[Depends(verify_api_key)])
async def send_link(request: SendLinkRequest, user_hash: Optional[str] = Query(None)):
    """Send link message."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.client_id and request.client_secret and request.user_id:
        client, _ = await get_or_create_client(request.client_id, request.client_secret, request.user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    result = await client.send_link(
        chat_id=request.chat_id,
        url=request.url,
        text=request.text
    )
    return {
        "success": True,
        "chat_id": request.chat_id,
        "message": "Link sent",
        "response": result
    }


@app.post("/api/messages/send/bulk", dependencies=[Depends(verify_api_key)])
async def send_bulk_messages(request: BulkMessageRequest, user_hash: Optional[str] = Query(None)):
    """Send multiple messages in parallel."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.client_id and request.client_secret and request.user_id:
        client, _ = await get_or_create_client(request.client_id, request.client_secret, request.user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    results = await client.send_bulk_messages(request.messages)

    success_count = sum(1 for r in results if r.get("success"))
    return {
        "success": True,
        "total": len(results),
        "sent": success_count,
        "failed": len(results) - success_count,
        "results": results
    }


@app.delete("/api/chats/{chat_id}/messages/{message_id}", dependencies=[Depends(verify_api_key)])
async def delete_message(
    chat_id: str,
    message_id: str,
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Delete message."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    await client.delete_message(chat_id, message_id)
    return {
        "success": True,
        "chat_id": chat_id,
        "message_id": message_id,
        "message": "Message deleted"
    }


# ========== Batch Endpoints ==========

@app.get("/api/messages/unread/all", dependencies=[Depends(verify_api_key)])
async def get_all_unread(
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Get all unread messages from all chats."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    results = await client.get_all_unread_messages()
    return {
        "success": True,
        "chats_with_unread": len(results),
        "data": results
    }


# ========== Webhook Endpoints ==========

@app.post("/api/webhook/subscribe", dependencies=[Depends(verify_api_key)])
async def subscribe_webhook(request: WebhookSubscribeRequest, user_hash: Optional[str] = Query(None)):
    """Subscribe to webhook notifications."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.client_id and request.client_secret and request.user_id:
        client, _ = await get_or_create_client(request.client_id, request.client_secret, request.user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    result = await client.subscribe_webhook(
        url=request.url,
        version=request.version
    )
    return {
        "success": True,
        "url": request.url,
        "version": request.version,
        "message": "Webhook subscribed",
        "response": result
    }


@app.post("/api/webhook/unsubscribe", dependencies=[Depends(verify_api_key)])
async def unsubscribe_webhook(
    user_hash: Optional[str] = Query(None),
    client_id: Optional[str] = Query(None),
    client_secret: Optional[str] = Query(None),
    user_id: Optional[str] = Query(None)
):
    """Unsubscribe from webhook notifications."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif client_id and client_secret and user_id:
        client, _ = await get_or_create_client(client_id, client_secret, user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    await client.unsubscribe_webhook()
    return {
        "success": True,
        "message": "Webhook unsubscribed"
    }


# ========== Blacklist Endpoints ==========

@app.post("/api/blacklist", dependencies=[Depends(verify_api_key)])
async def add_to_blacklist(request: BlacklistRequest, user_hash: Optional[str] = Query(None)):
    """Add user to blacklist."""
    if user_hash:
        client = get_client_by_hash(user_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.client_id and request.client_secret and request.user_id:
        client, _ = await get_or_create_client(request.client_id, request.client_secret, request.user_id)
    else:
        raise HTTPException(status_code=400, detail="Provide user_hash or credentials")

    await client.add_to_blacklist(request.blocked_user_id)
    return {
        "success": True,
        "user_id": request.blocked_user_id,
        "message": "User added to blacklist"
    }


# ========== Incoming Webhook from Avito (Multi-tenant) ==========

@app.post("/webhook/avito/{user_hash}")
async def avito_webhook_multitenant(user_hash: str, request: Request):
    """Handle incoming Avito webhook notifications for specific account."""
    account = get_account_by_hash(user_hash)
    if not account:
        logger.warning(f"Webhook for unknown user_hash: {user_hash}")
        raise HTTPException(status_code=404, detail="Account not found")

    try:
        body = await request.json()
        logger.info(f"Received Avito webhook [{user_hash}]: {str(body)[:500]}")

        # Validate basic structure
        if not body.get("id") or not body.get("timestamp"):
            raise HTTPException(status_code=400, detail="Invalid webhook format")

        # Normalize message with tenant info
        normalized = _normalize_webhook_message(body, account)

        # Skip system messages
        if normalized.get("skip"):
            logger.info(f"Skipping message: {normalized.get('reason')}")
            return {"ok": True, "skipped": True, "reason": normalized.get("reason")}

        # Record incoming message
        health_checker.record_message_received()

        # Forward to n8n
        forwarded = await _forward_to_n8n(normalized)

        return {
            "ok": True,
            "forwarded": forwarded,
            "message_id": normalized.get("meta", {}).get("external_message_id")
        }

    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"Webhook error [{user_hash}]: {e}")
        return JSONResponse(
            status_code=500,
            content={"ok": False, "error": str(e)}
        )


@app.post("/webhook/avito")
async def avito_webhook_legacy(request: Request):
    """Legacy webhook handler - tries to match by user_id in payload."""
    try:
        body = await request.json()
        logger.info(f"Received legacy Avito webhook: {str(body)[:500]}")

        # Validate basic structure
        if not body.get("id") or not body.get("timestamp"):
            raise HTTPException(status_code=400, detail="Invalid webhook format")

        # Try to find matching account by user_id in payload
        payload = body.get("payload", {})
        msg = payload.get("value", {})
        avito_user_id = str(msg.get("user_id", ""))

        matched_account = None
        for acc in account_cache.values():
            if acc.get("user_id") == avito_user_id:
                matched_account = acc
                break

        if not matched_account:
            logger.warning(f"Legacy webhook: no account for user_id {avito_user_id}")
            # Still process with empty account
            matched_account = {"user_id": avito_user_id}

        normalized = _normalize_webhook_message(body, matched_account)

        if normalized.get("skip"):
            return {"ok": True, "skipped": True, "reason": normalized.get("reason")}

        forwarded = await _forward_to_n8n(normalized)
        return {"ok": True, "forwarded": forwarded}

    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"Legacy webhook error: {e}")
        return JSONResponse(
            status_code=500,
            content={"ok": False, "error": str(e)}
        )


def _normalize_webhook_message(webhook_data: dict, account: dict) -> dict:
    """Normalize Avito webhook payload to standard format with tenant info."""
    payload = webhook_data.get("payload", {})
    msg = payload.get("value", {})

    if not msg:
        return {"skip": True, "reason": "no_message_data"}

    # Extract message info
    message_text = ""
    if isinstance(msg.get("content"), dict):
        message_text = msg["content"].get("text", "")
    elif isinstance(msg.get("content"), str):
        message_text = msg["content"]

    msg_type = msg.get("type", "text")
    author_id = msg.get("author_id", 0)
    webhook_user_id = msg.get("user_id", 0)

    # Check if system message
    is_system = (
        author_id == webhook_user_id or
        author_id == 0 or
        not author_id
    )

    if is_system:
        return {"skip": True, "reason": "system_message"}

    # Extract media info
    content = msg.get("content", {}) if isinstance(msg.get("content"), dict) else {}
    media_url = content.get("url")

    has_photo = msg_type == "image"
    has_voice = msg_type == "voice"
    has_video = msg_type == "video"
    has_document = msg_type == "file"

    # Get tenant user_id from account
    tenant_user_id = account.get("user_id", "")

    # Build normalized format with tenant info
    normalized = {
        "skip": False,
        "channel": "avito",
        "external_user_id": str(author_id),
        "external_chat_id": msg.get("chat_id"),

        # Tenant identifier for n8n resolution
        "user_id": tenant_user_id,

        "text": message_text,
        "timestamp": datetime.fromtimestamp(msg.get("created", 0)).isoformat() if msg.get("created") else datetime.now().isoformat(),

        "client_phone": None,
        "client_name": msg.get("author", {}).get("name") if isinstance(msg.get("author"), dict) else None,
        "client_email": None,

        "media": {
            "has_voice": has_voice,
            "voice_url": media_url if has_voice else None,
            "voice_transcribed_text": None,
            "has_images": has_photo,
            "images": [{"url": media_url}] if has_photo else [],
            "has_video": has_video,
            "videos": [{"url": media_url}] if has_video else [],
            "has_document": has_document
        },

        "meta": {
            "external_message_id": msg.get("id"),
            "ad_channel": "avito",
            "ad_id": str(msg.get("item_id")) if msg.get("item_id") else None,
            "original_media_type": msg_type,
            "raw": msg,
            "chat_type": msg.get("chat_type"),
            "user_id": tenant_user_id,
            "webhook_id": webhook_data.get("id"),
            "webhook_timestamp": webhook_data.get("timestamp")
        },

        "prefilled_data": {
            "model": None,
            "parts_owner": None
        }
    }

    return normalized


async def _forward_to_n8n(normalized_data: dict) -> bool:
    """Forward normalized message to n8n webhook."""
    if not settings.n8n_webhook_url:
        logger.warning("N8N_WEBHOOK_URL not configured, skipping forward")
        return False

    async with httpx.AsyncClient(timeout=30.0) as client:
        try:
            response = await client.post(
                settings.n8n_webhook_url,
                json=normalized_data
            )
            response.raise_for_status()
            logger.info(f"Message forwarded to n8n: {response.status_code}")
            return True
        except Exception as e:
            logger.error(f"Failed to forward to n8n: {e}")
            return False


# ========== Run Server ==========

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host=settings.server_host,
        port=settings.server_port,
        reload=True,
        workers=1
    )
