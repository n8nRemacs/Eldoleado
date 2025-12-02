"""VK Community Bot REST API Server (Direct API).

FastAPI server providing HTTP endpoints for VK Community Bot integration.
Uses direct VK API. Includes Callback API handler for incoming messages.
"""

import logging
from typing import Optional, List
from datetime import datetime
from contextlib import asynccontextmanager

import httpx
from fastapi import FastAPI, Request, HTTPException, Depends, Header, Query
from fastapi.responses import JSONResponse, PlainTextResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

from config import settings
from vk_client import VKClient, VKAPIError

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# Global instances
vk_client: Optional[VKClient] = None
confirmation_code: Optional[str] = None


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan - init and cleanup."""
    global vk_client, confirmation_code

    vk_client = VKClient(
        access_token=settings.vk_access_token,
        group_id=settings.vk_group_id
    )
    await vk_client.connect()

    # Get confirmation code for Callback API
    try:
        confirmation_code = await vk_client.get_callback_confirmation_code()
        logger.info(f"VK confirmation code obtained: {confirmation_code[:4]}...")
    except Exception as e:
        logger.warning(f"Could not get confirmation code: {e}")
        confirmation_code = settings.vk_confirmation_code

    logger.info(f"VK client initialized for group {settings.vk_group_id}")
    yield
    await vk_client.close()
    logger.info("VK client closed")


app = FastAPI(
    title="VK Community Bot API",
    description="REST API for VK Community Bot integration with n8n (Direct VK API)",
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
    peer_id: int = Field(..., description="User ID or chat ID")
    message: str = Field(..., description="Message text")
    attachment: Optional[str] = Field(None, description="Attachment string")
    reply_to: Optional[int] = Field(None, description="Message ID to reply to")
    keyboard: Optional[dict] = Field(None, description="VK keyboard object")


class BulkMessageRequest(BaseModel):
    peer_ids: List[int] = Field(..., description="List of peer IDs")
    message: str = Field(..., description="Message text")


class CallbackServerRequest(BaseModel):
    url: str = Field(..., description="Callback URL")
    title: str = Field(..., description="Server title")
    secret_key: Optional[str] = Field(None, description="Secret key")


# ========== Auth Dependency ==========

async def verify_api_key(x_api_key: Optional[str] = Header(None)):
    """Verify API key if configured."""
    if settings.api_key and settings.api_key != x_api_key:
        raise HTTPException(status_code=401, detail="Invalid API key")
    return True


# ========== Error Handler ==========

@app.exception_handler(VKAPIError)
async def vk_error_handler(request: Request, exc: VKAPIError):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "success": False,
            "error": exc.message,
            "error_code": exc.error_code
        }
    )


# ========== Health & Info ==========

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": "healthy",
        "service": "vk-community-api",
        "timestamp": datetime.now().isoformat()
    }


@app.get("/info")
async def server_info():
    """Server configuration info."""
    return {
        "service": "vk-community-api",
        "version": "1.0.0",
        "group_id": settings.vk_group_id,
        "api_version": VKClient.API_VERSION
    }


# ========== Group Info ==========

@app.get("/api/group", dependencies=[Depends(verify_api_key)])
async def get_group():
    """Get community info."""
    info = await vk_client.get_group_info()
    return {
        "success": True,
        "group": info
    }


# ========== Message Endpoints ==========

@app.post("/api/messages/send", dependencies=[Depends(verify_api_key)])
async def send_message(request: SendMessageRequest):
    """Send message to user/chat."""
    message_id = await vk_client.send_message(
        peer_id=request.peer_id,
        message=request.message,
        attachment=request.attachment,
        reply_to=request.reply_to,
        keyboard=request.keyboard
    )
    return {
        "success": True,
        "peer_id": request.peer_id,
        "message_id": message_id,
        "message": "Message sent"
    }


@app.post("/api/messages/send/bulk", dependencies=[Depends(verify_api_key)])
async def send_bulk_messages(request: BulkMessageRequest):
    """Send message to multiple users."""
    results = []
    for peer_id in request.peer_ids:
        try:
            msg_id = await vk_client.send_message(
                peer_id=peer_id,
                message=request.message
            )
            results.append({"peer_id": peer_id, "success": True, "message_id": msg_id})
        except VKAPIError as e:
            results.append({"peer_id": peer_id, "success": False, "error": e.message})
        except Exception as e:
            results.append({"peer_id": peer_id, "success": False, "error": str(e)})

    success_count = sum(1 for r in results if r["success"])
    return {
        "success": True,
        "total": len(results),
        "sent": success_count,
        "failed": len(results) - success_count,
        "results": results
    }


@app.put("/api/messages/{message_id}", dependencies=[Depends(verify_api_key)])
async def edit_message(
    message_id: int,
    peer_id: int = Query(...),
    message: str = Query(...)
):
    """Edit sent message."""
    result = await vk_client.edit_message(
        peer_id=peer_id,
        message_id=message_id,
        message=message
    )
    return {
        "success": True,
        "message_id": message_id,
        "message": "Message edited"
    }


@app.delete("/api/messages", dependencies=[Depends(verify_api_key)])
async def delete_messages(message_ids: str = Query(..., description="Comma-separated message IDs")):
    """Delete messages."""
    ids = [int(x.strip()) for x in message_ids.split(",")]
    result = await vk_client.delete_message(ids)
    return {
        "success": True,
        "deleted": ids,
        "result": result
    }


# ========== Conversation Endpoints ==========

@app.get("/api/conversations", dependencies=[Depends(verify_api_key)])
async def get_conversations(
    count: int = Query(20, ge=1, le=200),
    offset: int = Query(0, ge=0),
    filter: str = Query("all", regex="^(all|unread|important|unanswered)$")
):
    """Get conversations list."""
    result = await vk_client.get_conversations(
        count=count,
        offset=offset,
        filter=filter
    )
    return {
        "success": True,
        "data": result
    }


@app.get("/api/conversations/{peer_id}/history", dependencies=[Depends(verify_api_key)])
async def get_history(
    peer_id: int,
    count: int = Query(20, ge=1, le=200),
    offset: int = Query(0, ge=0)
):
    """Get message history from conversation."""
    result = await vk_client.get_history(
        peer_id=peer_id,
        count=count,
        offset=offset
    )
    return {
        "success": True,
        "peer_id": peer_id,
        "data": result
    }


@app.post("/api/conversations/{peer_id}/read", dependencies=[Depends(verify_api_key)])
async def mark_as_read(peer_id: int):
    """Mark conversation as read."""
    result = await vk_client.mark_as_read(peer_id)
    return {
        "success": True,
        "peer_id": peer_id,
        "message": "Marked as read"
    }


# ========== User Endpoints ==========

@app.get("/api/users/{user_ids}", dependencies=[Depends(verify_api_key)])
async def get_users(user_ids: str):
    """Get user info by IDs (comma-separated)."""
    ids = [int(x.strip()) for x in user_ids.split(",")]
    users = await vk_client.get_users(ids)
    return {
        "success": True,
        "users": users
    }


# ========== Callback Server Management ==========

@app.get("/api/callback/servers", dependencies=[Depends(verify_api_key)])
async def get_callback_servers():
    """Get callback servers list."""
    result = await vk_client.get_callback_servers()
    return {
        "success": True,
        "servers": result
    }


@app.post("/api/callback/servers", dependencies=[Depends(verify_api_key)])
async def add_callback_server(request: CallbackServerRequest):
    """Add callback server."""
    result = await vk_client.add_callback_server(
        url=request.url,
        title=request.title,
        secret_key=request.secret_key
    )
    return {
        "success": True,
        "server_id": result.get("server_id"),
        "message": "Callback server added"
    }


@app.delete("/api/callback/servers/{server_id}", dependencies=[Depends(verify_api_key)])
async def delete_callback_server(server_id: int):
    """Delete callback server."""
    await vk_client.delete_callback_server(server_id)
    return {
        "success": True,
        "message": "Callback server deleted"
    }


@app.put("/api/callback/servers/{server_id}/settings", dependencies=[Depends(verify_api_key)])
async def set_callback_settings(
    server_id: int,
    message_new: bool = True,
    message_reply: bool = False,
    message_allow: bool = True,
    message_deny: bool = True
):
    """Set callback events for server."""
    await vk_client.set_callback_settings(
        server_id=server_id,
        message_new=message_new,
        message_reply=message_reply,
        message_allow=message_allow,
        message_deny=message_deny
    )
    return {
        "success": True,
        "message": "Settings updated"
    }


@app.get("/api/callback/confirmation", dependencies=[Depends(verify_api_key)])
async def get_confirmation_code():
    """Get VK Callback API confirmation code."""
    return {
        "success": True,
        "code": confirmation_code
    }


# ========== VK Callback API Handler ==========

@app.post("/webhook/vk")
async def vk_callback(request: Request):
    """Handle VK Callback API events.

    VK sends POST requests here with events.
    We need to:
    1. Respond with confirmation code for 'confirmation' type
    2. Process message_new events
    3. Return "ok" for all processed events
    """
    try:
        body = await request.json()
        event_type = body.get("type")
        group_id = body.get("group_id")

        logger.info(f"VK callback: type={event_type}, group={group_id}")

        # Confirmation request
        if event_type == "confirmation":
            if group_id == settings.vk_group_id:
                return PlainTextResponse(confirmation_code)
            else:
                logger.warning(f"Confirmation for wrong group: {group_id}")
                return PlainTextResponse("bad group_id", status_code=400)

        # Verify secret if configured
        if settings.vk_secret_key:
            if body.get("secret") != settings.vk_secret_key:
                logger.warning("Bad secret key")
                return PlainTextResponse("bad secret", status_code=403)

        # Process events
        if event_type == "message_new":
            await _handle_message_new(body)
        elif event_type == "message_reply":
            logger.debug("message_reply event (sent by community)")
        elif event_type == "message_allow":
            user_id = body.get("object", {}).get("user_id")
            logger.info(f"User {user_id} allowed messages")
        elif event_type == "message_deny":
            user_id = body.get("object", {}).get("user_id")
            logger.info(f"User {user_id} denied messages")
        elif event_type == "message_event":
            await _handle_message_event(body)

        # Always return "ok" to VK
        return PlainTextResponse("ok")

    except Exception as e:
        logger.error(f"VK callback error: {e}")
        # Still return ok to avoid retries
        return PlainTextResponse("ok")


async def _handle_message_new(body: dict):
    """Handle new message event."""
    obj = body.get("object", {})
    message = obj.get("message", {})

    from_id = message.get("from_id", 0)
    peer_id = message.get("peer_id", 0)
    text = message.get("text", "")
    date = message.get("date", 0)
    message_id = message.get("id", 0)
    attachments = message.get("attachments", [])

    # Skip messages from community
    if from_id < 0:
        logger.debug("Skipping message from community")
        return

    # Normalize and forward
    normalized = _normalize_vk_message(message, body)
    await _forward_to_n8n(normalized)


async def _handle_message_event(body: dict):
    """Handle callback button event."""
    obj = body.get("object", {})
    # event_id = obj.get("event_id")
    # user_id = obj.get("user_id")
    # peer_id = obj.get("peer_id")
    # payload = obj.get("payload")

    # For now just log, can be extended to handle button clicks
    logger.info(f"Message event: {obj}")


def _normalize_vk_message(message: dict, callback_body: dict) -> dict:
    """Normalize VK message to standard format."""
    from_id = message.get("from_id", 0)
    peer_id = message.get("peer_id", 0)
    text = message.get("text", "")
    date = message.get("date", 0)
    message_id = message.get("id", 0)
    attachments = message.get("attachments", [])

    # Detect media types
    has_photo = any(a.get("type") == "photo" for a in attachments)
    has_voice = any(a.get("type") == "audio_message" for a in attachments)
    has_video = any(a.get("type") == "video" for a in attachments)
    has_doc = any(a.get("type") == "doc" for a in attachments)

    # Extract media URLs
    images = []
    voice_url = None
    videos = []

    for att in attachments:
        att_type = att.get("type")
        if att_type == "photo":
            photo = att.get("photo", {})
            sizes = photo.get("sizes", [])
            if sizes:
                largest = max(sizes, key=lambda s: s.get("width", 0) * s.get("height", 0))
                images.append({"url": largest.get("url")})
        elif att_type == "audio_message":
            audio = att.get("audio_message", {})
            voice_url = audio.get("link_mp3") or audio.get("link_ogg")
        elif att_type == "video":
            video = att.get("video", {})
            videos.append({
                "url": f"https://vk.com/video{video.get('owner_id')}_{video.get('id')}"
            })

    # Standard normalized format
    normalized = {
        "channel": "vk",
        "external_user_id": str(from_id),
        "external_chat_id": str(peer_id),

        "text": text,
        "timestamp": datetime.fromtimestamp(date).isoformat() if date else datetime.now().isoformat(),

        "client_phone": None,
        "client_name": None,
        "client_email": None,

        "media": {
            "has_voice": has_voice,
            "voice_url": voice_url,
            "voice_transcribed_text": None,
            "has_images": has_photo,
            "images": images,
            "has_video": has_video,
            "videos": videos,
            "has_document": has_doc
        },

        "meta": {
            "external_message_id": str(message_id),
            "ad_channel": "vk",
            "ad_id": None,
            "group_id": callback_body.get("group_id"),
            "raw": message
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
        logger.warning("N8N_WEBHOOK_URL not configured")
        return False

    async with httpx.AsyncClient(timeout=30.0) as client:
        try:
            response = await client.post(
                settings.n8n_webhook_url,
                json=normalized_data
            )
            response.raise_for_status()
            logger.info(f"Forwarded to n8n: {response.status_code}")
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
