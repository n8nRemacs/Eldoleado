"""Avito Messenger REST API Server.

FastAPI server providing HTTP endpoints for Avito Messenger API integration.
Includes webhook handler for incoming messages and REST API for operations.
"""

import logging
from typing import Optional, List
from datetime import datetime
from contextlib import asynccontextmanager

import httpx
from fastapi import FastAPI, Request, HTTPException, Depends, Header, Query, Body
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

from config import settings
from avito_client import AvitoClient, AvitoAPIError, RateLimitExceeded

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# Global client instance
avito_client: Optional[AvitoClient] = None


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan - init and cleanup."""
    global avito_client
    avito_client = AvitoClient()
    await avito_client.connect()
    logger.info("Avito client initialized")
    yield
    await avito_client.close()
    logger.info("Avito client closed")


app = FastAPI(
    title="Avito Messenger API",
    description="REST API for Avito Messenger integration with n8n",
    version="1.0.0",
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

class SendMessageRequest(BaseModel):
    chat_id: str = Field(..., description="Chat ID")
    text: str = Field(..., description="Message text")


class SendImageRequest(BaseModel):
    chat_id: str = Field(..., description="Chat ID")
    image_url: str = Field(..., description="Image URL")


class SendLinkRequest(BaseModel):
    chat_id: str = Field(..., description="Chat ID")
    url: str = Field(..., description="Link URL")
    text: Optional[str] = Field(None, description="Optional text")


class BulkMessageRequest(BaseModel):
    messages: List[SendMessageRequest] = Field(..., description="List of messages to send")


class WebhookSubscribeRequest(BaseModel):
    url: str = Field(..., description="Webhook URL")
    version: int = Field(2, description="API version (1 or 2)")


class BlacklistRequest(BaseModel):
    user_id: int = Field(..., description="User ID to block")


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
        "status": "healthy",
        "service": "avito-messenger-api",
        "timestamp": datetime.now().isoformat()
    }


@app.get("/info")
async def server_info():
    """Server configuration info."""
    return {
        "rate_limit_requests": settings.rate_limit_requests,
        "rate_limit_window": settings.rate_limit_window,
        "max_connections": settings.max_connections,
        "avito_user_id": settings.avito_user_id[:8] + "..." if settings.avito_user_id else None
    }


# ========== Auth Endpoints ==========

@app.get("/api/token", dependencies=[Depends(verify_api_key)])
async def get_token(force_refresh: bool = False):
    """Get or refresh access token."""
    token = await avito_client.get_access_token(force_refresh=force_refresh)
    return {
        "success": True,
        "token": token[:20] + "..." if len(token) > 20 else token,
        "message": "Token retrieved"
    }


@app.post("/api/token/refresh", dependencies=[Depends(verify_api_key)])
async def refresh_token():
    """Force refresh access token."""
    token = await avito_client.refresh_token()
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
    offset: int = Query(0, ge=0)
):
    """Get list of chats."""
    item_ids_list = [int(x) for x in item_ids.split(",")] if item_ids else None
    chats = await avito_client.get_chats(
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
async def get_chat(chat_id: str):
    """Get chat by ID."""
    chat = await avito_client.get_chat(chat_id)
    return {
        "success": True,
        "chat": chat.to_dict()
    }


@app.post("/api/chats/{chat_id}/read", dependencies=[Depends(verify_api_key)])
async def mark_chat_read(chat_id: str):
    """Mark chat as read."""
    await avito_client.mark_chat_read(chat_id)
    return {
        "success": True,
        "chat_id": chat_id,
        "message": "Chat marked as read"
    }


@app.get("/api/chats/unread/count", dependencies=[Depends(verify_api_key)])
async def get_unread_count():
    """Get total unread message count."""
    chats = await avito_client.get_chats(unread_only=True)
    total_unread = sum(c.unread_count for c in chats)
    return {
        "success": True,
        "total_unread": total_unread,
        "unread_chats": len(chats)
    }


@app.get("/api/chats/search", dependencies=[Depends(verify_api_key)])
async def search_chats(item_id: int = Query(..., description="Item ID to search")):
    """Search chats by item ID."""
    chats = await avito_client.get_chats(item_ids=[item_id])
    return {
        "success": True,
        "item_id": item_id,
        "count": len(chats),
        "chats": [c.to_dict() for c in chats]
    }


# ========== Message Endpoints ==========

@app.get("/api/chats/{chat_id}/messages", dependencies=[Depends(verify_api_key)])
async def get_messages(
    chat_id: str,
    limit: int = Query(100, ge=1, le=100),
    offset: int = Query(0, ge=0),
    version: int = Query(1, ge=1, le=2)
):
    """Get messages from chat."""
    if version == 2:
        messages = await avito_client.get_messages_v2(chat_id, limit, offset)
    else:
        messages = await avito_client.get_messages(chat_id, limit, offset)

    return {
        "success": True,
        "chat_id": chat_id,
        "count": len(messages),
        "messages": [m.to_dict() for m in messages]
    }


@app.post("/api/messages/send", dependencies=[Depends(verify_api_key)])
async def send_message(request: SendMessageRequest):
    """Send text message."""
    result = await avito_client.send_message(
        chat_id=request.chat_id,
        text=request.text
    )
    return {
        "success": True,
        "chat_id": request.chat_id,
        "message": "Message sent",
        "response": result
    }


@app.post("/api/messages/send/image", dependencies=[Depends(verify_api_key)])
async def send_image(request: SendImageRequest):
    """Send image message."""
    result = await avito_client.send_image(
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
async def send_link(request: SendLinkRequest):
    """Send link message."""
    result = await avito_client.send_link(
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
async def send_bulk_messages(request: BulkMessageRequest):
    """Send multiple messages in parallel."""
    messages = [{"chat_id": m.chat_id, "text": m.text} for m in request.messages]
    results = await avito_client.send_bulk_messages(messages)

    success_count = sum(1 for r in results if r.get("success"))
    return {
        "success": True,
        "total": len(results),
        "sent": success_count,
        "failed": len(results) - success_count,
        "results": results
    }


@app.delete("/api/chats/{chat_id}/messages/{message_id}", dependencies=[Depends(verify_api_key)])
async def delete_message(chat_id: str, message_id: str):
    """Delete message."""
    await avito_client.delete_message(chat_id, message_id)
    return {
        "success": True,
        "chat_id": chat_id,
        "message_id": message_id,
        "message": "Message deleted"
    }


# ========== Batch Endpoints ==========

@app.get("/api/messages/unread/all", dependencies=[Depends(verify_api_key)])
async def get_all_unread():
    """Get all unread messages from all chats."""
    results = await avito_client.get_all_unread_messages()
    return {
        "success": True,
        "chats_with_unread": len(results),
        "data": results
    }


# ========== Webhook Endpoints ==========

@app.post("/api/webhook/subscribe", dependencies=[Depends(verify_api_key)])
async def subscribe_webhook(request: WebhookSubscribeRequest):
    """Subscribe to webhook notifications."""
    result = await avito_client.subscribe_webhook(
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
async def unsubscribe_webhook():
    """Unsubscribe from webhook notifications."""
    await avito_client.unsubscribe_webhook()
    return {
        "success": True,
        "message": "Webhook unsubscribed"
    }


# ========== Blacklist Endpoints ==========

@app.post("/api/blacklist", dependencies=[Depends(verify_api_key)])
async def add_to_blacklist(request: BlacklistRequest):
    """Add user to blacklist."""
    await avito_client.add_to_blacklist(request.user_id)
    return {
        "success": True,
        "user_id": request.user_id,
        "message": "User added to blacklist"
    }


# ========== Incoming Webhook from Avito ==========

@app.post("/webhook/avito")
async def avito_webhook(request: Request):
    """Handle incoming Avito webhook notifications.

    This endpoint receives messages from Avito and forwards them to n8n.
    """
    try:
        body = await request.json()
        logger.info(f"Received Avito webhook: {str(body)[:500]}")

        # Validate basic structure
        if not body.get("id") or not body.get("timestamp"):
            raise HTTPException(status_code=400, detail="Invalid webhook format")

        # Normalize message
        normalized = _normalize_webhook_message(body)

        # Skip system messages
        if normalized.get("skip"):
            logger.info(f"Skipping message: {normalized.get('reason')}")
            return {"ok": True, "skipped": True, "reason": normalized.get("reason")}

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
        logger.error(f"Webhook error: {e}")
        return JSONResponse(
            status_code=500,
            content={"ok": False, "error": str(e)}
        )


def _normalize_webhook_message(webhook_data: dict) -> dict:
    """Normalize Avito webhook payload to standard format."""
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
    user_id = msg.get("user_id", 0)

    # Check if system message
    is_system = (
        author_id == user_id or
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

    # Build normalized format matching n8n workflow
    normalized = {
        "skip": False,
        "channel": "avito",
        "external_user_id": str(author_id),
        "external_chat_id": msg.get("chat_id"),

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
            "user_id": user_id,
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
