"""WhatsApp REST API Server (via Wappi.pro) - Multi-tenant.

FastAPI server providing HTTP endpoints for WhatsApp integration via Wappi.pro.
Supports multiple profiles with dynamic registration.
"""

import sys
import os
import logging
import hashlib
from typing import Optional, List, Dict
from datetime import datetime
from contextlib import asynccontextmanager

import httpx
from fastapi import FastAPI, Request, HTTPException, Depends, Header, Query, BackgroundTasks
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

from config import settings
from wappi_client import WappiClient, WappiAPIError

# Add shared module to path
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..'))
from shared import storage

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# Channel name for storage
CHANNEL_NAME = "whatsapp"

# In-memory client cache: profile_hash -> WappiClient
client_cache: Dict[str, WappiClient] = {}
# Account data cache: profile_hash -> {credentials, metadata}
account_cache: Dict[str, dict] = {}


def get_profile_hash(profile_id: str) -> str:
    """Generate 16-char hash from profile_id for webhook URL."""
    return hashlib.sha256(profile_id.encode()).hexdigest()[:16]


async def get_or_create_client(api_token: str, profile_id: str) -> tuple[WappiClient, str]:
    """Get existing client or create and register new one."""
    profile_hash = get_profile_hash(profile_id)

    if profile_hash in client_cache:
        return client_cache[profile_hash], profile_hash

    # Auto-register new profile
    client = WappiClient(api_token=api_token, profile_id=profile_id)
    await client.connect()

    # Get profile info
    try:
        profile_info = await client.get_profile()
    except Exception as e:
        await client.close()
        raise HTTPException(status_code=401, detail=f"Invalid credentials: {e}")

    # Save to storage
    credentials = {"api_token": api_token, "profile_id": profile_id}
    metadata = {"profile_info": profile_info}
    await storage.save_account(CHANNEL_NAME, profile_hash, credentials, metadata)

    # Cache
    client_cache[profile_hash] = client
    account_cache[profile_hash] = {"credentials": credentials, "metadata": metadata}

    logger.info(f"Auto-registered WhatsApp profile: {profile_hash}")
    return client, profile_hash


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan manager."""
    logger.info("Starting WhatsApp MCP Server (Multi-tenant)...")

    # Initialize storage
    await storage.init_storage()

    # Load existing accounts from Redis
    accounts = await storage.load_accounts(CHANNEL_NAME)
    for acc in accounts:
        try:
            profile_hash = acc["hash"]
            api_token = acc["credentials"]["api_token"]
            profile_id = acc["credentials"]["profile_id"]

            client = WappiClient(api_token=api_token, profile_id=profile_id)
            await client.connect()

            client_cache[profile_hash] = client
            account_cache[profile_hash] = acc

            logger.info(f"Loaded WhatsApp profile: {profile_hash}")
        except Exception as e:
            logger.error(f"Failed to load profile {acc.get('hash', '?')}: {e}")

    logger.info(f"Loaded {len(client_cache)} WhatsApp profiles")

    yield

    # Cleanup: close all clients
    for profile_hash, client in client_cache.items():
        try:
            await client.close()
        except Exception as e:
            logger.error(f"Error closing client {profile_hash}: {e}")

    await storage.close_storage()
    logger.info("WhatsApp MCP Server stopped")


app = FastAPI(
    title="WhatsApp API (Wappi.pro)",
    description="Multi-tenant REST API for WhatsApp integration via Wappi.pro",
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
    """Request to register a new profile."""
    api_token: str = Field(..., description="Wappi API token")
    profile_id: str = Field(..., description="Wappi profile ID")


class SendMessageRequest(BaseModel):
    api_token: Optional[str] = None
    profile_id: Optional[str] = None
    to: str = Field(..., description="Recipient phone (e.g., 79991234567)")
    text: str = Field(..., description="Message text")
    reply_to: Optional[str] = Field(None, description="Message ID to reply to")


class SendImageRequest(BaseModel):
    api_token: Optional[str] = None
    profile_id: Optional[str] = None
    to: str = Field(..., description="Recipient phone")
    image_url: str = Field(..., description="Image URL")
    caption: Optional[str] = Field(None, description="Image caption")


class SendDocumentRequest(BaseModel):
    api_token: Optional[str] = None
    profile_id: Optional[str] = None
    to: str = Field(..., description="Recipient phone")
    document_url: str = Field(..., description="Document URL")
    filename: Optional[str] = Field(None, description="Filename")


class SendVoiceRequest(BaseModel):
    api_token: Optional[str] = None
    profile_id: Optional[str] = None
    to: str = Field(..., description="Recipient phone")
    voice_url: str = Field(..., description="Voice message URL")


class BulkMessageRequest(BaseModel):
    api_token: Optional[str] = None
    profile_id: Optional[str] = None
    recipients: List[str] = Field(..., description="List of phone numbers")
    text: str = Field(..., description="Message text")
    delay_min: int = Field(10, description="Min delay between messages (sec)")
    delay_max: int = Field(30, description="Max delay between messages (sec)")


class WebhookSetRequest(BaseModel):
    api_token: Optional[str] = None
    profile_id: Optional[str] = None
    url: str = Field(..., description="Webhook URL")


# ========== Auth Dependency ==========

async def verify_api_key(x_api_key: Optional[str] = Header(None)):
    """Verify API key if configured."""
    if settings.api_key and settings.api_key != x_api_key:
        raise HTTPException(status_code=401, detail="Invalid API key")
    return True


# ========== Error Handler ==========

@app.exception_handler(WappiAPIError)
async def wappi_error_handler(request: Request, exc: WappiAPIError):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "success": False,
            "error": exc.message,
            "details": exc.response
        }
    )


# ========== Account Management ====================

@app.post("/accounts/register")
async def register_account(
    request: RegisterAccountRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Register a new WhatsApp profile."""
    verify_api_key(api_key)

    profile_hash = get_profile_hash(request.profile_id)

    # Check if already registered
    if profile_hash in client_cache:
        account = account_cache.get(profile_hash, {})
        return {
            "status": "already_registered",
            "profile_hash": profile_hash,
            "webhook_path": f"/webhook/whatsapp/{profile_hash}"
        }

    # Create and validate client
    client = WappiClient(api_token=request.api_token, profile_id=request.profile_id)
    await client.connect()

    try:
        profile_info = await client.get_profile()
    except Exception as e:
        await client.close()
        raise HTTPException(status_code=401, detail=f"Invalid credentials: {e}")

    # Save to storage
    credentials = {"api_token": request.api_token, "profile_id": request.profile_id}
    metadata = {"profile_info": profile_info}
    await storage.save_account(CHANNEL_NAME, profile_hash, credentials, metadata)

    # Cache
    client_cache[profile_hash] = client
    account_cache[profile_hash] = {"credentials": credentials, "metadata": metadata}

    logger.info(f"Registered WhatsApp profile: {profile_hash}")

    return {
        "status": "registered",
        "profile_hash": profile_hash,
        "profile_info": profile_info,
        "webhook_path": f"/webhook/whatsapp/{profile_hash}"
    }


@app.get("/accounts")
async def list_accounts(api_key: str = Header(None, alias="X-API-Key")):
    """List all registered profiles."""
    verify_api_key(api_key)

    accounts = []
    for profile_hash, data in account_cache.items():
        profile_id = data.get("credentials", {}).get("profile_id", "")
        accounts.append({
            "profile_hash": profile_hash,
            "profile_id": profile_id[:8] + "..." if profile_id else None,
            "webhook_path": f"/webhook/whatsapp/{profile_hash}"
        })

    return {"accounts": accounts, "count": len(accounts)}


@app.get("/accounts/{profile_hash}")
async def get_account(
    profile_hash: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get profile details."""
    verify_api_key(api_key)

    if profile_hash not in account_cache:
        raise HTTPException(status_code=404, detail="Profile not found")

    data = account_cache[profile_hash]
    profile_id = data.get("credentials", {}).get("profile_id", "")

    return {
        "profile_hash": profile_hash,
        "profile_id": profile_id[:8] + "...",
        "webhook_path": f"/webhook/whatsapp/{profile_hash}"
    }


@app.delete("/accounts/{profile_hash}")
async def unregister_account(
    profile_hash: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Unregister a profile."""
    verify_api_key(api_key)

    if profile_hash not in client_cache:
        raise HTTPException(status_code=404, detail="Profile not found")

    # Close client
    client = client_cache.pop(profile_hash)
    await client.close()

    # Remove from cache
    account_cache.pop(profile_hash, None)

    # Remove from storage
    await storage.delete_account(CHANNEL_NAME, profile_hash)

    logger.info(f"Unregistered WhatsApp profile: {profile_hash}")

    return {"status": "unregistered", "profile_hash": profile_hash}


# ========== Health & Info ==========

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": "healthy",
        "service": "whatsapp-api-wappi",
        "version": "2.0.0",
        "accounts": len(client_cache),
        "timestamp": datetime.now().isoformat()
    }


@app.get("/info")
async def server_info():
    """Server configuration info."""
    return {
        "service": "whatsapp-api-wappi",
        "version": "2.0.0",
        "accounts": len(client_cache)
    }


# ========== Status Endpoints ==========

@app.get("/api/status", dependencies=[Depends(verify_api_key)])
async def get_status(
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Get WhatsApp connection status."""
    client, _ = await get_or_create_client(api_token, profile_id)
    status = await client.get_status()
    return {"success": True, "status": status}


@app.get("/api/profile", dependencies=[Depends(verify_api_key)])
async def get_profile(
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Get WhatsApp profile info."""
    client, _ = await get_or_create_client(api_token, profile_id)
    profile = await client.get_profile()
    return {"success": True, "profile": profile}


@app.get("/api/qr", dependencies=[Depends(verify_api_key)])
async def get_qr(
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Get QR code for WhatsApp Web authorization."""
    client, _ = await get_or_create_client(api_token, profile_id)
    qr = await client.get_qr_code()
    return {"success": True, "qr": qr}


# ========== Message Endpoints ==========

@app.post("/api/messages/send", dependencies=[Depends(verify_api_key)])
async def send_message(request: SendMessageRequest):
    """Send text message."""
    if not request.api_token or not request.profile_id:
        raise HTTPException(status_code=400, detail="api_token and profile_id required")

    client, _ = await get_or_create_client(request.api_token, request.profile_id)
    result = await client.send_message(
        to=request.to,
        text=request.text,
        reply_to=request.reply_to
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
    if not request.api_token or not request.profile_id:
        raise HTTPException(status_code=400, detail="api_token and profile_id required")

    client, _ = await get_or_create_client(request.api_token, request.profile_id)
    result = await client.send_image(
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
    if not request.api_token or not request.profile_id:
        raise HTTPException(status_code=400, detail="api_token and profile_id required")

    client, _ = await get_or_create_client(request.api_token, request.profile_id)
    result = await client.send_document(
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
    if not request.api_token or not request.profile_id:
        raise HTTPException(status_code=400, detail="api_token and profile_id required")

    client, _ = await get_or_create_client(request.api_token, request.profile_id)
    result = await client.send_voice(
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
    if not request.api_token or not request.profile_id:
        raise HTTPException(status_code=400, detail="api_token and profile_id required")

    client, _ = await get_or_create_client(request.api_token, request.profile_id)
    result = await client.start_mailing(
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
async def get_mailing_status(
    mailing_id: str,
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Get mailing campaign status."""
    client, _ = await get_or_create_client(api_token, profile_id)
    result = await client.get_mailing_status(mailing_id)
    return {"success": True, "mailing_id": mailing_id, "status": result}


@app.post("/api/mailing/{mailing_id}/stop", dependencies=[Depends(verify_api_key)])
async def stop_mailing(
    mailing_id: str,
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Stop mailing campaign."""
    client, _ = await get_or_create_client(api_token, profile_id)
    result = await client.stop_mailing(mailing_id)
    return {"success": True, "mailing_id": mailing_id, "message": "Mailing stopped", "response": result}


# ========== Chat Endpoints ==========

@app.get("/api/chats", dependencies=[Depends(verify_api_key)])
async def get_chats(
    api_token: str = Query(...),
    profile_id: str = Query(...),
    limit: int = Query(100, ge=1, le=500),
    offset: int = Query(0, ge=0)
):
    """Get list of chats."""
    client, _ = await get_or_create_client(api_token, profile_id)
    chats = await client.get_chats(limit=limit, offset=offset)
    return {"success": True, "count": len(chats), "chats": chats}


@app.get("/api/chats/{chat_id}/messages", dependencies=[Depends(verify_api_key)])
async def get_chat_messages(
    chat_id: str,
    api_token: str = Query(...),
    profile_id: str = Query(...),
    limit: int = Query(100, ge=1, le=500)
):
    """Get messages from chat."""
    client, _ = await get_or_create_client(api_token, profile_id)
    messages = await client.get_chat_messages(chat_id, limit=limit)
    return {"success": True, "chat_id": chat_id, "count": len(messages), "messages": messages}


# ========== Contact Endpoints ==========

@app.get("/api/contacts", dependencies=[Depends(verify_api_key)])
async def get_contacts(
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Get contact list."""
    client, _ = await get_or_create_client(api_token, profile_id)
    contacts = await client.get_contacts()
    return {"success": True, "count": len(contacts), "contacts": contacts}


@app.get("/api/contacts/check/{phone}", dependencies=[Depends(verify_api_key)])
async def check_phone(
    phone: str,
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Check if phone is registered in WhatsApp."""
    client, _ = await get_or_create_client(api_token, profile_id)
    result = await client.check_phone(phone)
    return {"success": True, "phone": phone, "result": result}


# ========== Group Endpoints ==========

@app.get("/api/groups", dependencies=[Depends(verify_api_key)])
async def get_groups(
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Get list of groups."""
    client, _ = await get_or_create_client(api_token, profile_id)
    groups = await client.get_groups()
    return {"success": True, "count": len(groups), "groups": groups}


# ========== Webhook Management ==========

@app.post("/api/webhook/set", dependencies=[Depends(verify_api_key)])
async def set_webhook(request: WebhookSetRequest):
    """Set webhook URL for incoming messages."""
    if not request.api_token or not request.profile_id:
        raise HTTPException(status_code=400, detail="api_token and profile_id required")

    client, _ = await get_or_create_client(request.api_token, request.profile_id)
    result = await client.set_webhook(request.url)
    return {"success": True, "url": request.url, "message": "Webhook set", "response": result}


@app.get("/api/webhook", dependencies=[Depends(verify_api_key)])
async def get_webhook(
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Get current webhook settings."""
    client, _ = await get_or_create_client(api_token, profile_id)
    result = await client.get_webhook()
    return {"success": True, "webhook": result}


@app.delete("/api/webhook", dependencies=[Depends(verify_api_key)])
async def delete_webhook(
    api_token: str = Query(...),
    profile_id: str = Query(...)
):
    """Delete webhook."""
    client, _ = await get_or_create_client(api_token, profile_id)
    result = await client.delete_webhook()
    return {"success": True, "message": "Webhook deleted", "response": result}


# ========== Incoming Webhook (Multi-tenant) ==========

@app.post("/webhook/whatsapp/{profile_hash}")
async def whatsapp_webhook(
    profile_hash: str,
    request: Request,
    background_tasks: BackgroundTasks
):
    """Handle incoming Wappi webhook for specific profile."""

    # Check if profile exists
    if profile_hash not in client_cache:
        logger.warning(f"Webhook for unknown profile: {profile_hash}")
        raise HTTPException(status_code=404, detail="Profile not registered")

    account = account_cache.get(profile_hash, {})
    profile_id = account.get("credentials", {}).get("profile_id", "")

    try:
        body = await request.json()
        logger.info(f"Received WhatsApp webhook for {profile_hash}: {str(body)[:200]}")

        # Normalize message with tenant info
        normalized = _normalize_webhook_message(body, profile_id, profile_hash)

        # Skip if not valid
        if normalized.get("skip"):
            logger.info(f"Skipping message: {normalized.get('reason')}")
            return {"ok": True, "skipped": True, "reason": normalized.get("reason")}

        # Forward to n8n in background
        background_tasks.add_task(_forward_to_n8n, normalized)

        return {
            "ok": True,
            "message_id": normalized.get("meta", {}).get("external_message_id")
        }

    except Exception as e:
        logger.error(f"Webhook error: {e}")
        return JSONResponse(
            status_code=500,
            content={"ok": False, "error": str(e)}
        )


# Legacy webhook (for backward compatibility)
@app.post("/webhook/whatsapp")
async def whatsapp_webhook_legacy(
    request: Request,
    background_tasks: BackgroundTasks
):
    """Legacy webhook handler - routes based on profile_id in payload."""

    try:
        body = await request.json()

        # Try to find profile from payload
        payload_profile_id = body.get("profile_id", "")
        if payload_profile_id:
            profile_hash = get_profile_hash(payload_profile_id)
            if profile_hash in client_cache:
                # Forward to multi-tenant handler
                request._body = await request.body()  # Cache body for re-read
                return await whatsapp_webhook(profile_hash, request, background_tasks)

        # Fallback: use first registered profile
        if not client_cache:
            raise HTTPException(status_code=404, detail="No profiles registered")

        profile_hash = next(iter(client_cache.keys()))
        return await whatsapp_webhook(profile_hash, request, background_tasks)

    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"Legacy webhook error: {e}")
        return JSONResponse(
            status_code=500,
            content={"ok": False, "error": str(e)}
        )


def _normalize_webhook_message(webhook_data: dict, profile_id: str, profile_hash: str) -> dict:
    """Normalize Wappi webhook payload with tenant info."""
    msg_type = webhook_data.get("type", "")

    # Skip outgoing messages
    if msg_type == "outgoing_message_phone":
        return {"skip": True, "reason": "outgoing_message"}

    # Only process incoming messages
    if msg_type != "incoming_message":
        return {"skip": True, "reason": f"unknown_type_{msg_type}"}

    # Extract phone from "79991234567@c.us" format
    from_raw = webhook_data.get("from", "")
    from_phone = from_raw.replace("@c.us", "").replace("@s.whatsapp.net", "")

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

    # Standard normalized format with tenant info
    normalized = {
        "skip": False,
        "channel": "whatsapp",
        "profile_id": profile_id,  # For tenant resolution
        "profile_hash": profile_hash,
        "external_user_id": from_phone,
        "external_chat_id": chat_id,

        "text": message_text,
        "timestamp": datetime.fromtimestamp(timestamp).isoformat() if timestamp else datetime.now().isoformat(),

        "client_phone": from_phone,
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
            "ad_channel": "whatsapp",
            "ad_id": None,
            "content_type": content_type,
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
