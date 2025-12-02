"""MAX.ru Messenger REST API Server (via Wappi.pro).

FastAPI server providing HTTP endpoints for MAX.ru Messenger integration via Wappi.pro.
Includes webhook handler for incoming messages and REST API for operations.
"""

import logging
from typing import Optional, List
from datetime import datetime
from contextlib import asynccontextmanager

import httpx
from fastapi import FastAPI, Request, HTTPException, Depends, Header, Query
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

from config import settings
from max_client import MaxClient, MaxAPIError

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# Global client instance
max_client: Optional[MaxClient] = None


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan - init and cleanup."""
    global max_client
    max_client = MaxClient(
        api_token=settings.wappi_api_token,
        profile_id=settings.wappi_profile_id
    )
    await max_client.connect()
    logger.info("MAX client initialized")
    yield
    await max_client.close()
    logger.info("MAX client closed")


app = FastAPI(
    title="MAX.ru Messenger API (Wappi.pro)",
    description="REST API for MAX.ru Messenger integration via Wappi.pro with n8n",
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
    to: str = Field(..., description="Recipient user ID or phone")
    text: str = Field(..., description="Message text")


class SendImageRequest(BaseModel):
    to: str = Field(..., description="Recipient")
    image_url: str = Field(..., description="Image URL")
    caption: Optional[str] = Field(None, description="Image caption")


class SendDocumentRequest(BaseModel):
    to: str = Field(..., description="Recipient")
    document_url: str = Field(..., description="Document URL")
    filename: Optional[str] = Field(None, description="Filename")


class SendVoiceRequest(BaseModel):
    to: str = Field(..., description="Recipient")
    voice_url: str = Field(..., description="Voice message URL")


class BulkMessageRequest(BaseModel):
    recipients: List[str] = Field(..., description="List of recipients")
    text: str = Field(..., description="Message text")
    delay_min: int = Field(10, description="Min delay between messages (sec)")
    delay_max: int = Field(30, description="Max delay between messages (sec)")


class WebhookSetRequest(BaseModel):
    url: str = Field(..., description="Webhook URL")


# ========== Auth Dependency ==========

async def verify_api_key(x_api_key: Optional[str] = Header(None)):
    """Verify API key if configured."""
    if settings.api_key and settings.api_key != x_api_key:
        raise HTTPException(status_code=401, detail="Invalid API key")
    return True


# ========== Error Handler ==========

@app.exception_handler(MaxAPIError)
async def max_error_handler(request: Request, exc: MaxAPIError):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "success": False,
            "error": exc.message,
            "details": exc.response
        }
    )


# ========== Health & Info ==========

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": "healthy",
        "service": "max-api-wappi",
        "timestamp": datetime.now().isoformat()
    }


@app.get("/info")
async def server_info():
    """Server configuration info."""
    return {
        "service": "max-api-wappi",
        "version": "1.0.0",
        "profile_id": settings.wappi_profile_id[:8] + "..." if settings.wappi_profile_id else None
    }


# ========== Status Endpoints ==========

@app.get("/api/status", dependencies=[Depends(verify_api_key)])
async def get_status():
    """Get MAX connection status."""
    status = await max_client.get_status()
    return {
        "success": True,
        "status": status
    }


@app.get("/api/profile", dependencies=[Depends(verify_api_key)])
async def get_profile():
    """Get MAX profile info."""
    profile = await max_client.get_profile()
    return {
        "success": True,
        "profile": profile
    }


# ========== Message Endpoints ==========

@app.post("/api/messages/send", dependencies=[Depends(verify_api_key)])
async def send_message(request: SendMessageRequest):
    """Send text message."""
    result = await max_client.send_message(
        to=request.to,
        text=request.text
    )
    return {
        "success": True,
        "to": request.to,
        "message": "Message sent",
        "response": result
    }


@app.post("/api/messages/send/image", dependencies=[Depends(verify_api_key)])
async def send_image(request: SendImageRequest):
    """Send image message."""
    result = await max_client.send_image(
        to=request.to,
        image_url=request.image_url,
        caption=request.caption
    )
    return {
        "success": True,
        "to": request.to,
        "message": "Image sent",
        "response": result
    }


@app.post("/api/messages/send/document", dependencies=[Depends(verify_api_key)])
async def send_document(request: SendDocumentRequest):
    """Send document message."""
    result = await max_client.send_document(
        to=request.to,
        document_url=request.document_url,
        filename=request.filename
    )
    return {
        "success": True,
        "to": request.to,
        "message": "Document sent",
        "response": result
    }


@app.post("/api/messages/send/voice", dependencies=[Depends(verify_api_key)])
async def send_voice(request: SendVoiceRequest):
    """Send voice message."""
    result = await max_client.send_voice(
        to=request.to,
        voice_url=request.voice_url
    )
    return {
        "success": True,
        "to": request.to,
        "message": "Voice sent",
        "response": result
    }


@app.post("/api/messages/send/bulk", dependencies=[Depends(verify_api_key)])
async def send_bulk(request: BulkMessageRequest):
    """Start bulk messaging campaign."""
    result = await max_client.start_mailing(
        recipients=request.recipients,
        text=request.text,
        delay_min=request.delay_min,
        delay_max=request.delay_max
    )
    return {
        "success": True,
        "recipients_count": len(request.recipients),
        "message": "Mailing started",
        "response": result
    }


@app.get("/api/mailing/{mailing_id}/status", dependencies=[Depends(verify_api_key)])
async def get_mailing_status(mailing_id: str):
    """Get mailing campaign status."""
    result = await max_client.get_mailing_status(mailing_id)
    return {
        "success": True,
        "mailing_id": mailing_id,
        "status": result
    }


@app.post("/api/mailing/{mailing_id}/stop", dependencies=[Depends(verify_api_key)])
async def stop_mailing(mailing_id: str):
    """Stop mailing campaign."""
    result = await max_client.stop_mailing(mailing_id)
    return {
        "success": True,
        "mailing_id": mailing_id,
        "message": "Mailing stopped",
        "response": result
    }


# ========== Chat Endpoints ==========

@app.get("/api/chats", dependencies=[Depends(verify_api_key)])
async def get_chats(
    limit: int = Query(100, ge=1, le=500),
    offset: int = Query(0, ge=0)
):
    """Get list of chats."""
    chats = await max_client.get_chats(limit=limit, offset=offset)
    return {
        "success": True,
        "count": len(chats),
        "chats": chats
    }


@app.get("/api/chats/{chat_id}/messages", dependencies=[Depends(verify_api_key)])
async def get_chat_messages(
    chat_id: str,
    limit: int = Query(100, ge=1, le=500)
):
    """Get messages from chat."""
    messages = await max_client.get_chat_messages(chat_id, limit=limit)
    return {
        "success": True,
        "chat_id": chat_id,
        "count": len(messages),
        "messages": messages
    }


# ========== Webhook Management ==========

@app.post("/api/webhook/set", dependencies=[Depends(verify_api_key)])
async def set_webhook(request: WebhookSetRequest):
    """Set webhook URL for incoming messages."""
    result = await max_client.set_webhook(request.url)
    return {
        "success": True,
        "url": request.url,
        "message": "Webhook set",
        "response": result
    }


@app.get("/api/webhook", dependencies=[Depends(verify_api_key)])
async def get_webhook():
    """Get current webhook settings."""
    result = await max_client.get_webhook()
    return {
        "success": True,
        "webhook": result
    }


@app.delete("/api/webhook", dependencies=[Depends(verify_api_key)])
async def delete_webhook():
    """Delete webhook."""
    result = await max_client.delete_webhook()
    return {
        "success": True,
        "message": "Webhook deleted",
        "response": result
    }


# ========== Incoming Webhook from Wappi ==========

@app.post("/webhook/max")
async def max_webhook(request: Request):
    """Handle incoming Wappi webhook notifications for MAX.

    This endpoint receives messages from Wappi MAX and forwards them to n8n.
    """
    try:
        body = await request.json()
        logger.info(f"Received MAX webhook: {str(body)[:500]}")

        # Normalize message
        normalized = _normalize_webhook_message(body)

        # Skip if not valid
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

    except Exception as e:
        logger.error(f"Webhook error: {e}")
        return JSONResponse(
            status_code=500,
            content={"ok": False, "error": str(e)}
        )


def _normalize_webhook_message(webhook_data: dict) -> dict:
    """Normalize Wappi MAX webhook payload to standard format."""
    msg_type = webhook_data.get("type", "")

    # Skip outgoing messages
    if msg_type == "outgoing_message_phone":
        return {"skip": True, "reason": "outgoing_message"}

    # Only process incoming messages
    if msg_type != "incoming_message":
        return {"skip": True, "reason": f"unknown_type_{msg_type}"}

    from_user = webhook_data.get("from", "")
    chat_id = webhook_data.get("chat_id", "")
    message_text = webhook_data.get("body", "")
    timestamp = webhook_data.get("timestamp", 0)

    # Detect media
    content_type = webhook_data.get("content_type", "text")
    media_url = webhook_data.get("url") or webhook_data.get("media_url")

    has_image = content_type in ["image", "sticker"]
    has_voice = content_type in ["voice", "ptt", "audio"]
    has_video = content_type == "video"
    has_document = content_type in ["document", "file"]

    # Standard normalized format
    normalized = {
        "skip": False,
        "channel": "max",
        "external_user_id": from_user,
        "external_chat_id": chat_id,

        "text": message_text,
        "timestamp": datetime.fromtimestamp(timestamp).isoformat() if timestamp else datetime.now().isoformat(),

        "client_phone": from_user if from_user.isdigit() else None,
        "client_name": webhook_data.get("sender_name"),
        "client_email": None,

        "media": {
            "has_voice": has_voice,
            "voice_url": media_url if has_voice else None,
            "voice_transcribed_text": None,
            "has_images": has_image,
            "images": [{"url": media_url}] if has_image else [],
            "has_video": has_video,
            "videos": [{"url": media_url}] if has_video else [],
            "has_document": has_document
        },

        "meta": {
            "external_message_id": webhook_data.get("message_id"),
            "ad_channel": "max",
            "ad_id": None,
            "content_type": content_type,
            "profile_id": webhook_data.get("profile_id"),
            "raw": webhook_data
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
