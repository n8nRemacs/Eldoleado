"""MAX.ru MCP Server - Direct Bot API.

FastAPI server for MAX.ru Bot API integration.
Provides REST endpoints and webhook handler for incoming messages.
"""

import logging
import hashlib
import hmac
from datetime import datetime
from typing import Optional, List
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, Header, Request, Query, BackgroundTasks
from fastapi.responses import JSONResponse
from pydantic import BaseModel, Field
import httpx

from config import settings
from max_client import MaxClient, MaxAPIError

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Global client instance
max_client: Optional[MaxClient] = None


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan manager."""
    global max_client

    # Startup
    logger.info("Starting MAX MCP Server...")
    max_client = MaxClient(settings.MAX_ACCESS_TOKEN)
    await max_client.connect()

    # Test connection
    try:
        bot_info = await max_client.get_me()
        logger.info(f"Connected as bot: {bot_info.get('name', 'Unknown')}")
    except Exception as e:
        logger.warning(f"Could not get bot info: {e}")

    yield

    # Shutdown
    if max_client:
        await max_client.close()
    logger.info("MAX MCP Server stopped")


app = FastAPI(
    title="MAX.ru MCP Server",
    description="Direct Bot API integration for MAX.ru messenger",
    version="1.0.0",
    lifespan=lifespan
)


# ==================== Models ====================

class SendMessageRequest(BaseModel):
    """Request to send a message."""
    chat_id: Optional[int] = None
    user_id: Optional[int] = None
    text: Optional[str] = None
    attachments: Optional[List[dict]] = None
    format: Optional[str] = None  # 'markdown' or 'html'
    notify: bool = True
    disable_link_preview: bool = False


class EditMessageRequest(BaseModel):
    """Request to edit a message."""
    message_id: str
    text: Optional[str] = None
    attachments: Optional[List[dict]] = None
    format: Optional[str] = None


class SubscribeRequest(BaseModel):
    """Request to subscribe to webhook."""
    url: str
    update_types: Optional[List[str]] = None
    version: Optional[str] = None
    secret: Optional[str] = None


class AnswerCallbackRequest(BaseModel):
    """Request to answer callback."""
    callback_id: str
    notification: Optional[str] = None
    message: Optional[dict] = None


class NormalizedMessage(BaseModel):
    """Normalized message format for n8n."""
    channel: str = "max"
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
        return True  # No secret configured

    expected = hmac.new(
        secret.encode(),
        body,
        hashlib.sha256
    ).hexdigest()

    return hmac.compare_digest(expected, signature)


def normalize_update(update: dict) -> Optional[NormalizedMessage]:
    """Convert MAX update to normalized format."""
    update_type = update.get("update_type")

    if update_type == "message_created":
        message = update.get("message", {})
        sender = message.get("sender", {})
        body = message.get("body", {})
        recipient = message.get("recipient", {})

        # Determine chat_id
        chat_id = recipient.get("chat_id") or sender.get("user_id")

        # Extract attachments
        attachments = []
        if body.get("attachments"):
            for att in body["attachments"]:
                att_type = att.get("type")
                if att_type == "image":
                    attachments.append({
                        "type": "photo",
                        "url": att.get("payload", {}).get("url")
                    })
                elif att_type == "video":
                    attachments.append({
                        "type": "video",
                        "url": att.get("payload", {}).get("url")
                    })
                elif att_type == "audio":
                    attachments.append({
                        "type": "audio",
                        "url": att.get("payload", {}).get("url")
                    })
                elif att_type == "file":
                    attachments.append({
                        "type": "file",
                        "url": att.get("payload", {}).get("url"),
                        "filename": att.get("payload", {}).get("filename")
                    })

        return NormalizedMessage(
            channel="max",
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
            message_id=callback.get("callback_id", ""),
            chat_id=message.get("recipient", {}).get("chat_id", 0),
            user_id=sender.get("user_id", 0),
            user_name=sender.get("name"),
            text=callback.get("payload"),  # Button payload
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


# ==================== Health & Info ====================

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {"status": "ok", "service": "max-mcp-server"}


@app.get("/info")
async def get_info(api_key: str = Header(None, alias="X-API-Key")):
    """Get bot information."""
    check_api_key(api_key)

    try:
        bot_info = await max_client.get_me()
        return {
            "status": "ok",
            "bot": bot_info
        }
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

    if not request.chat_id and not request.user_id:
        raise HTTPException(status_code=400, detail="chat_id or user_id required")

    if not request.text and not request.attachments:
        raise HTTPException(status_code=400, detail="text or attachments required")

    try:
        result = await max_client.send_message(
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

    try:
        result = await max_client.edit_message(
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
    api_key: str = Header(None, alias="X-API-Key")
):
    """Delete message."""
    check_api_key(api_key)

    try:
        result = await max_client.delete_message(message_id)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/message/{message_id}")
async def get_message(
    message_id: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get message by ID."""
    check_api_key(api_key)

    try:
        result = await max_client.get_message(message_id)
        return {"status": "ok", "message": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Chats ====================

@app.get("/api/chats")
async def get_chats(
    count: int = Query(50, ge=1, le=100),
    marker: Optional[int] = None,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get list of chats."""
    check_api_key(api_key)

    try:
        result = await max_client.get_chats(count=count, marker=marker)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/chat/{chat_id}")
async def get_chat(
    chat_id: int,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get chat by ID."""
    check_api_key(api_key)

    try:
        result = await max_client.get_chat(chat_id)
        return {"status": "ok", "chat": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/chat/{chat_id}/action")
async def send_action(
    chat_id: int,
    action: str = Query("typing_on"),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Send typing action to chat."""
    check_api_key(api_key)

    try:
        result = await max_client.send_action(chat_id, action)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/chat/{chat_id}/members")
async def get_chat_members(
    chat_id: int,
    count: int = Query(20, ge=1, le=100),
    marker: Optional[int] = None,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get chat members."""
    check_api_key(api_key)

    try:
        result = await max_client.get_chat_members(chat_id, count=count, marker=marker)
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

    try:
        result = await max_client.subscribe(
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
    url: str = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Unsubscribe from webhook."""
    check_api_key(api_key)

    try:
        result = await max_client.unsubscribe(url)
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/subscriptions")
async def get_subscriptions(
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get current subscriptions."""
    check_api_key(api_key)

    try:
        result = await max_client.get_subscriptions()
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

    try:
        result = await max_client.answer_callback(
            callback_id=request.callback_id,
            notification=request.notification,
            message=request.message
        )
        return {"status": "ok", "result": result}
    except MaxAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Webhook Handler ====================

@app.post("/webhook/max")
async def webhook_handler(
    request: Request,
    background_tasks: BackgroundTasks,
    x_max_signature: Optional[str] = Header(None, alias="X-Max-Signature")
):
    """Handle incoming MAX webhook updates.

    MAX sends updates here when events occur (new messages, callbacks, etc.)
    """
    body = await request.body()

    # Verify signature if secret is configured
    if settings.MAX_WEBHOOK_SECRET:
        if not verify_signature(body, x_max_signature or "", settings.MAX_WEBHOOK_SECRET):
            logger.warning("Invalid webhook signature")
            raise HTTPException(status_code=403, detail="Invalid signature")

    try:
        data = await request.json()
    except Exception as e:
        logger.error(f"Invalid JSON in webhook: {e}")
        raise HTTPException(status_code=400, detail="Invalid JSON")

    logger.info(f"Received webhook: {data.get('update_type', 'unknown')}")

    # Normalize and forward to n8n
    normalized = normalize_update(data)
    if normalized:
        background_tasks.add_task(forward_to_n8n, normalized)

    # Always return success to MAX
    return {"success": True}


# ==================== Polling Mode ====================

@app.get("/api/updates")
async def get_updates(
    limit: int = Query(100, ge=1, le=1000),
    timeout: int = Query(30, ge=0, le=90),
    marker: Optional[int] = None,
    types: Optional[str] = None,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get updates via long polling (alternative to webhooks)."""
    check_api_key(api_key)

    types_list = types.split(",") if types else None

    try:
        result = await max_client.get_updates(
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
    type: str = Query("photo"),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get URL for uploading attachments."""
    check_api_key(api_key)

    if type not in ["photo", "video", "audio", "file"]:
        raise HTTPException(status_code=400, detail="Invalid upload type")

    try:
        result = await max_client.get_upload_url(type)
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
