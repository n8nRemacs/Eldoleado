"""Instagram MCP Server - Graph API.

FastAPI server for Instagram Graph API integration.
Provides REST endpoints and webhook handler for incoming messages.
"""

import logging
import hashlib
import hmac
from datetime import datetime
from typing import Optional, List
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, Header, Request, Query, BackgroundTasks
from fastapi.responses import JSONResponse, PlainTextResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field
import httpx

from config import settings
from instagram_client import InstagramClient, InstagramAPIError

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Global client instance
instagram_client: Optional[InstagramClient] = None


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan manager."""
    global instagram_client

    # Startup
    logger.info("Starting Instagram MCP Server...")
    instagram_client = InstagramClient(
        access_token=settings.INSTAGRAM_ACCESS_TOKEN,
        instagram_account_id=settings.INSTAGRAM_ACCOUNT_ID
    )
    await instagram_client.connect()

    # Test connection
    try:
        account_info = await instagram_client.get_account_info()
        logger.info(f"Connected as: @{account_info.get('username', 'Unknown')}")
    except Exception as e:
        logger.warning(f"Could not get account info: {e}")

    yield

    # Shutdown
    if instagram_client:
        await instagram_client.close()
    logger.info("Instagram MCP Server stopped")


app = FastAPI(
    title="Instagram MCP Server",
    description="REST API for Instagram Graph API integration",
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


# ==================== Models ====================

class SendMessageRequest(BaseModel):
    """Request to send a message."""
    recipient_id: str = Field(..., description="Instagram-scoped user ID")
    text: Optional[str] = None
    image_url: Optional[str] = None
    video_url: Optional[str] = None
    audio_url: Optional[str] = None


class SendQuickRepliesRequest(BaseModel):
    """Request to send quick replies."""
    recipient_id: str
    text: str
    quick_replies: List[dict] = Field(..., description="Quick reply buttons")


class ReplyCommentRequest(BaseModel):
    """Request to reply to comment."""
    comment_id: str
    message: str


class SetIceBreakersRequest(BaseModel):
    """Request to set ice breakers."""
    ice_breakers: List[dict] = Field(..., description="Ice breaker questions")


class NormalizedMessage(BaseModel):
    """Normalized message format for n8n."""
    channel: str = "instagram"
    message_id: str
    sender_id: str
    recipient_id: str
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


def normalize_message(entry: dict) -> Optional[NormalizedMessage]:
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
    return {"status": "ok", "service": "instagram-mcp-server"}


@app.get("/info")
async def get_info(api_key: str = Header(None, alias="X-API-Key")):
    """Get account information."""
    check_api_key(api_key)

    try:
        account_info = await instagram_client.get_account_info()
        return {
            "status": "ok",
            "account": account_info
        }
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Messages ====================

@app.post("/api/send")
async def send_message(
    request: SendMessageRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Send message to Instagram user."""
    check_api_key(api_key)

    if not request.text and not request.image_url and not request.video_url:
        raise HTTPException(status_code=400, detail="text, image_url, or video_url required")

    try:
        if request.image_url:
            result = await instagram_client.send_image(
                recipient_id=request.recipient_id,
                image_url=request.image_url
            )
        elif request.video_url:
            result = await instagram_client.send_video(
                recipient_id=request.recipient_id,
                video_url=request.video_url
            )
        elif request.audio_url:
            result = await instagram_client.send_audio(
                recipient_id=request.recipient_id,
                audio_url=request.audio_url
            )
        else:
            result = await instagram_client.send_message(
                recipient_id=request.recipient_id,
                text=request.text
            )

        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/send/quick-replies")
async def send_quick_replies(
    request: SendQuickRepliesRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Send message with quick reply buttons."""
    check_api_key(api_key)

    try:
        result = await instagram_client.send_quick_replies(
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
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get list of conversations."""
    check_api_key(api_key)

    try:
        result = await instagram_client.get_conversations(limit=limit)
        return {"status": "ok", "conversations": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/conversations/{conversation_id}/messages")
async def get_conversation_messages(
    conversation_id: str,
    limit: int = Query(20, ge=1, le=100),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get messages from a conversation."""
    check_api_key(api_key)

    try:
        result = await instagram_client.get_conversation_messages(
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
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get user info by Instagram-scoped ID."""
    check_api_key(api_key)

    try:
        result = await instagram_client.get_user_info(user_id)
        return {"status": "ok", "user": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Ice Breakers ====================

@app.post("/api/ice-breakers")
async def set_ice_breakers(
    request: SetIceBreakersRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Set conversation ice breakers."""
    check_api_key(api_key)

    try:
        result = await instagram_client.set_ice_breakers(request.ice_breakers)
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/ice-breakers")
async def get_ice_breakers(
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get current ice breakers."""
    check_api_key(api_key)

    try:
        result = await instagram_client.get_ice_breakers()
        return {"status": "ok", "ice_breakers": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.delete("/api/ice-breakers")
async def delete_ice_breakers(
    api_key: str = Header(None, alias="X-API-Key")
):
    """Delete ice breakers."""
    check_api_key(api_key)

    try:
        result = await instagram_client.delete_ice_breakers()
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Comments ====================

@app.get("/api/media/{media_id}/comments")
async def get_media_comments(
    media_id: str,
    limit: int = Query(50, ge=1, le=100),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get comments on a media item."""
    check_api_key(api_key)

    try:
        result = await instagram_client.get_media_comments(media_id, limit=limit)
        return {"status": "ok", "comments": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/comments/{comment_id}/reply")
async def reply_to_comment(
    comment_id: str,
    request: ReplyCommentRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Reply to a comment."""
    check_api_key(api_key)

    try:
        result = await instagram_client.reply_to_comment(comment_id, request.message)
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/comments/{comment_id}/hide")
async def hide_comment(
    comment_id: str,
    hide: bool = Query(True),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Hide or unhide a comment."""
    check_api_key(api_key)

    try:
        result = await instagram_client.hide_comment(comment_id, hide=hide)
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.delete("/api/comments/{comment_id}")
async def delete_comment(
    comment_id: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Delete a comment."""
    check_api_key(api_key)

    try:
        result = await instagram_client.delete_comment(comment_id)
        return {"status": "ok", "result": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Media ====================

@app.get("/api/media")
async def get_media(
    limit: int = Query(25, ge=1, le=100),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get account media (posts)."""
    check_api_key(api_key)

    try:
        result = await instagram_client.get_media(limit=limit)
        return {"status": "ok", "media": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/media/{media_id}")
async def get_media_item(
    media_id: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get specific media item."""
    check_api_key(api_key)

    try:
        result = await instagram_client.get_media_item(media_id)
        return {"status": "ok", "media": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Mentions ====================

@app.get("/api/mentions")
async def get_mentions(
    limit: int = Query(20, ge=1, le=100),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get media where account is mentioned."""
    check_api_key(api_key)

    try:
        result = await instagram_client.get_mentions(limit=limit)
        return {"status": "ok", "mentions": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Stories ====================

@app.get("/api/stories")
async def get_stories(
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get account stories."""
    check_api_key(api_key)

    try:
        result = await instagram_client.get_stories()
        return {"status": "ok", "stories": result}
    except InstagramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Webhook Handler ====================

@app.get("/webhook/instagram")
async def webhook_verify(
    mode: str = Query(None, alias="hub.mode"),
    token: str = Query(None, alias="hub.verify_token"),
    challenge: str = Query(None, alias="hub.challenge")
):
    """Verify webhook subscription (Facebook/Instagram verification)."""
    if mode == "subscribe" and token == settings.WEBHOOK_VERIFY_TOKEN:
        logger.info("Webhook verified successfully")
        return PlainTextResponse(challenge)

    logger.warning(f"Webhook verification failed: mode={mode}, token={token}")
    raise HTTPException(status_code=403, detail="Verification failed")


@app.post("/webhook/instagram")
async def webhook_handler(
    request: Request,
    background_tasks: BackgroundTasks,
    x_hub_signature_256: Optional[str] = Header(None, alias="X-Hub-Signature-256")
):
    """Handle incoming Instagram webhook events."""
    body = await request.body()

    # Verify signature if app secret is configured
    if settings.FACEBOOK_APP_SECRET:
        if not verify_signature(body, x_hub_signature_256 or "", settings.FACEBOOK_APP_SECRET):
            logger.warning("Invalid webhook signature")
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

    logger.info(f"Received Instagram webhook")

    # Process entries
    for entry in data.get("entry", []):
        # Handle messaging events
        if "messaging" in entry:
            normalized = normalize_message(entry)
            if normalized:
                background_tasks.add_task(forward_to_n8n, normalized)

        # Handle changes (comments, mentions, etc.)
        for change in entry.get("changes", []):
            field = change.get("field")
            value = change.get("value", {})

            if field == "comments":
                logger.info(f"New comment: {value}")
                # Could forward comments to n8n too
            elif field == "mentions":
                logger.info(f"New mention: {value}")
            elif field == "story_insights":
                logger.info(f"Story insights: {value}")

    # Always return 200 to Facebook
    return {"status": "ok"}


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host=settings.SERVER_HOST,
        port=settings.SERVER_PORT,
        reload=True
    )
