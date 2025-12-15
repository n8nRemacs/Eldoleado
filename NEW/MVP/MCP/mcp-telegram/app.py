"""Telegram MCP Server - Multi-tenant Bot API Proxy.

FastAPI server for Telegram Bot API integration.
Supports multiple bots - each bot registers its own webhook endpoint.
Normalizes messages and forwards to n8n with bot_token for tenant resolution.

Architecture:
- Each bot has unique webhook: /webhook/telegram/{bot_token_hash}
- Bot clients are cached in memory for performance
- Messages are normalized and forwarded to n8n with bot_token
- n8n's Tenant Resolver uses bot_token to identify tenant
"""

import logging
import hashlib
from datetime import datetime
from typing import Optional, List, Dict
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, Header, Request, Query, BackgroundTasks, Path
from fastapi.responses import JSONResponse
from pydantic import BaseModel, Field
import httpx

from config import settings
from telegram_client import TelegramClient, TelegramAPIError

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Bot registry: token_hash -> {token, client, bot_info}
bot_registry: Dict[str, dict] = {}


def get_token_hash(token: str) -> str:
    """Generate short hash from bot token for URL."""
    return hashlib.sha256(token.encode()).hexdigest()[:16]


def get_bot_client(token: str) -> TelegramClient:
    """Get or create bot client from registry."""
    token_hash = get_token_hash(token)
    if token_hash not in bot_registry:
        raise HTTPException(status_code=404, detail="Bot not registered")
    return bot_registry[token_hash]["client"]


async def register_bot(token: str) -> dict:
    """Register a new bot or update existing."""
    token_hash = get_token_hash(token)

    # Check if already registered
    if token_hash in bot_registry:
        return {
            "status": "already_registered",
            "token_hash": token_hash,
            "bot_info": bot_registry[token_hash].get("bot_info"),
            "webhook_path": f"/webhook/telegram/{token_hash}"
        }

    # Create new client
    client = TelegramClient(token)
    await client.connect()

    # Get bot info
    try:
        bot_info = await client.get_me()
        logger.info(f"Registered bot: @{bot_info.get('username', 'Unknown')}")
    except TelegramAPIError as e:
        await client.close()
        raise HTTPException(status_code=400, detail=f"Invalid bot token: {e.message}")

    # Store in registry
    bot_registry[token_hash] = {
        "token": token,
        "client": client,
        "bot_info": bot_info
    }

    return {
        "status": "registered",
        "token_hash": token_hash,
        "bot_info": bot_info,
        "webhook_path": f"/webhook/telegram/{token_hash}"
    }


async def unregister_bot(token: str) -> dict:
    """Unregister a bot."""
    token_hash = get_token_hash(token)

    if token_hash not in bot_registry:
        raise HTTPException(status_code=404, detail="Bot not registered")

    # Close client
    client = bot_registry[token_hash]["client"]
    await client.close()

    # Remove from registry
    del bot_registry[token_hash]

    return {"status": "unregistered", "token_hash": token_hash}


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan manager."""
    logger.info("Starting Telegram MCP Server (Multi-tenant)...")
    yield

    # Cleanup: close all bot clients
    for token_hash, data in bot_registry.items():
        try:
            await data["client"].close()
        except Exception as e:
            logger.error(f"Error closing client {token_hash}: {e}")

    logger.info("Telegram MCP Server stopped")


app = FastAPI(
    title="Telegram MCP Server (Multi-tenant)",
    description="Multi-tenant Bot API integration for Telegram messenger",
    version="2.0.0",
    lifespan=lifespan
)


# ==================== Models ====================

class RegisterBotRequest(BaseModel):
    """Request to register a bot."""
    token: str


class SendMessageRequest(BaseModel):
    """Request to send a message."""
    token: str  # Bot token
    chat_id: str  # Chat ID as string (will be converted)
    text: str
    parse_mode: Optional[str] = None
    reply_to_message_id: Optional[int] = None
    reply_markup: Optional[dict] = None
    disable_notification: bool = False
    disable_web_page_preview: bool = False


class EditMessageRequest(BaseModel):
    """Request to edit a message."""
    token: str
    chat_id: int
    message_id: int
    text: str
    parse_mode: Optional[str] = None
    reply_markup: Optional[dict] = None


class SendPhotoRequest(BaseModel):
    """Request to send a photo."""
    token: str
    chat_id: int
    photo: str
    caption: Optional[str] = None
    parse_mode: Optional[str] = None
    reply_to_message_id: Optional[int] = None


class SendDocumentRequest(BaseModel):
    """Request to send a document."""
    token: str
    chat_id: int
    document: str
    caption: Optional[str] = None
    parse_mode: Optional[str] = None
    reply_to_message_id: Optional[int] = None


class AnswerCallbackRequest(BaseModel):
    """Request to answer callback query."""
    token: str
    callback_query_id: str
    text: Optional[str] = None
    show_alert: bool = False


class SetWebhookRequest(BaseModel):
    """Request to set webhook."""
    token: str
    url: str
    max_connections: int = 40
    allowed_updates: Optional[List[str]] = None
    secret_token: Optional[str] = None


class NormalizedMessage(BaseModel):
    """Normalized message format for n8n."""
    channel: str = "telegram"
    bot_token: str  # IMPORTANT: for tenant resolution
    message_id: int
    chat_id: int
    user_id: int
    user_name: Optional[str] = None
    first_name: Optional[str] = None
    last_name: Optional[str] = None
    username: Optional[str] = None
    text: Optional[str] = None
    voice_text: Optional[str] = None
    attachments: List[dict] = Field(default_factory=list)
    timestamp: datetime
    is_callback: bool = False
    callback_data: Optional[str] = None
    raw_data: dict


# ==================== Helper Functions ====================

def check_api_key(api_key: str = Header(None, alias="X-API-Key")):
    """Validate API key for protected endpoints."""
    if settings.API_KEY and api_key != settings.API_KEY:
        raise HTTPException(status_code=401, detail="Invalid API key")


async def transcribe_voice(file_content: bytes, filename: str = "voice.ogg") -> Optional[str]:
    """Transcribe voice message using OpenAI Whisper."""
    if not settings.OPENAI_API_KEY:
        logger.warning("OPENAI_API_KEY not configured, skipping transcription")
        return None

    try:
        async with httpx.AsyncClient(timeout=60.0) as client:
            response = await client.post(
                "https://api.openai.com/v1/audio/transcriptions",
                headers={"Authorization": f"Bearer {settings.OPENAI_API_KEY}"},
                files={"file": (filename, file_content, "audio/ogg")},
                data={"model": "whisper-1", "language": "ru"}
            )
            if response.status_code == 200:
                result = response.json()
                return result.get("text")
            else:
                logger.error(f"Transcription failed: {response.status_code} - {response.text}")
                return None
    except Exception as e:
        logger.error(f"Transcription error: {e}")
        return None


async def normalize_update(update: dict, bot_token: str, client: TelegramClient) -> Optional[NormalizedMessage]:
    """Convert Telegram update to normalized format."""

    message = update.get("message") or update.get("edited_message")
    callback_query = update.get("callback_query")

    if callback_query:
        user = callback_query.get("from", {})
        message_data = callback_query.get("message", {})
        chat = message_data.get("chat", {})

        return NormalizedMessage(
            channel="telegram",
            bot_token=bot_token,
            message_id=message_data.get("message_id", 0),
            chat_id=chat.get("id", 0),
            user_id=user.get("id", 0),
            user_name=user.get("username"),
            first_name=user.get("first_name"),
            last_name=user.get("last_name"),
            username=user.get("username"),
            text=callback_query.get("data"),
            attachments=[{"type": "callback", "callback_query_id": callback_query.get("id")}],
            timestamp=datetime.now(),
            is_callback=True,
            callback_data=callback_query.get("data"),
            raw_data=update
        )

    if message:
        user = message.get("from", {})
        chat = message.get("chat", {})
        text = message.get("text") or message.get("caption")

        attachments = []
        voice_text = None

        # Photo
        if message.get("photo"):
            photo = max(message["photo"], key=lambda p: p.get("file_size", 0))
            attachments.append({
                "type": "photo",
                "file_id": photo.get("file_id"),
                "file_unique_id": photo.get("file_unique_id"),
                "width": photo.get("width"),
                "height": photo.get("height")
            })

        # Video
        if message.get("video"):
            video = message["video"]
            attachments.append({
                "type": "video",
                "file_id": video.get("file_id"),
                "file_unique_id": video.get("file_unique_id"),
                "duration": video.get("duration"),
                "width": video.get("width"),
                "height": video.get("height")
            })

        # Voice - with transcription
        if message.get("voice"):
            voice = message["voice"]
            attachments.append({
                "type": "voice",
                "file_id": voice.get("file_id"),
                "file_unique_id": voice.get("file_unique_id"),
                "duration": voice.get("duration")
            })

            try:
                file_content = await client.download_file(voice.get("file_id"))
                voice_text = await transcribe_voice(file_content)
                if voice_text:
                    logger.info(f"Transcribed voice: {voice_text[:50]}...")
            except Exception as e:
                logger.error(f"Voice download/transcription error: {e}")

        # Audio
        if message.get("audio"):
            audio = message["audio"]
            attachments.append({
                "type": "audio",
                "file_id": audio.get("file_id"),
                "file_unique_id": audio.get("file_unique_id"),
                "duration": audio.get("duration"),
                "title": audio.get("title"),
                "performer": audio.get("performer")
            })

        # Document
        if message.get("document"):
            doc = message["document"]
            attachments.append({
                "type": "document",
                "file_id": doc.get("file_id"),
                "file_unique_id": doc.get("file_unique_id"),
                "file_name": doc.get("file_name"),
                "mime_type": doc.get("mime_type")
            })

        # Video note
        if message.get("video_note"):
            vn = message["video_note"]
            attachments.append({
                "type": "video_note",
                "file_id": vn.get("file_id"),
                "file_unique_id": vn.get("file_unique_id"),
                "duration": vn.get("duration"),
                "length": vn.get("length")
            })

        # Sticker
        if message.get("sticker"):
            sticker = message["sticker"]
            attachments.append({
                "type": "sticker",
                "file_id": sticker.get("file_id"),
                "file_unique_id": sticker.get("file_unique_id"),
                "emoji": sticker.get("emoji"),
                "set_name": sticker.get("set_name")
            })

        # Location
        if message.get("location"):
            loc = message["location"]
            attachments.append({
                "type": "location",
                "latitude": loc.get("latitude"),
                "longitude": loc.get("longitude")
            })

        # Contact
        if message.get("contact"):
            contact = message["contact"]
            attachments.append({
                "type": "contact",
                "phone_number": contact.get("phone_number"),
                "first_name": contact.get("first_name"),
                "last_name": contact.get("last_name"),
                "user_id": contact.get("user_id")
            })

        return NormalizedMessage(
            channel="telegram",
            bot_token=bot_token,
            message_id=message.get("message_id", 0),
            chat_id=chat.get("id", 0),
            user_id=user.get("id", 0),
            user_name=user.get("username"),
            first_name=user.get("first_name"),
            last_name=user.get("last_name"),
            username=user.get("username"),
            text=text,
            voice_text=voice_text,
            attachments=attachments,
            timestamp=datetime.fromtimestamp(message.get("date", 0)),
            is_callback=False,
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
                logger.info(f"Forwarded to n8n: chat_id={normalized.chat_id}, bot={normalized.bot_token[:20]}...")
    except Exception as e:
        logger.error(f"Failed to forward to n8n: {e}")


# ==================== Health & Info ====================

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": "ok",
        "service": "telegram-mcp-server",
        "mode": "multi-tenant",
        "registered_bots": len(bot_registry)
    }


@app.get("/bots")
async def list_bots(api_key: str = Header(None, alias="X-API-Key")):
    """List all registered bots."""
    check_api_key(api_key)

    return {
        "status": "ok",
        "bots": [
            {
                "token_hash": token_hash,
                "username": data["bot_info"].get("username"),
                "first_name": data["bot_info"].get("first_name"),
                "webhook_path": f"/webhook/telegram/{token_hash}"
            }
            for token_hash, data in bot_registry.items()
        ]
    }


# ==================== Bot Registration ====================

@app.post("/bots/register")
async def api_register_bot(
    request: RegisterBotRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Register a new bot."""
    check_api_key(api_key)
    return await register_bot(request.token)


@app.delete("/bots/unregister")
async def api_unregister_bot(
    request: RegisterBotRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Unregister a bot."""
    check_api_key(api_key)
    return await unregister_bot(request.token)


# ==================== Messages ====================

@app.post("/api/send")
async def send_message(
    request: SendMessageRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Send text message via specific bot."""
    check_api_key(api_key)

    # Auto-register bot if not registered
    token_hash = get_token_hash(request.token)
    if token_hash not in bot_registry:
        try:
            await register_bot(request.token)
        except HTTPException as e:
            return {"success": False, "error": e.detail}

    client = get_bot_client(request.token)

    try:
        # Convert chat_id to int
        chat_id = int(request.chat_id)

        result = await client.send_message(
            chat_id=chat_id,
            text=request.text,
            parse_mode=request.parse_mode,
            reply_to_message_id=request.reply_to_message_id,
            reply_markup=request.reply_markup,
            disable_notification=request.disable_notification,
            disable_web_page_preview=request.disable_web_page_preview
        )
        return {
            "success": True,
            "message_id": result.get("message_id"),
            "chat_id": result.get("chat", {}).get("id"),
            "result": result
        }
    except ValueError:
        return {"success": False, "error": f"Invalid chat_id: {request.chat_id}"}
    except TelegramAPIError as e:
        return {"success": False, "error": e.message, "error_code": e.status_code}


@app.put("/api/message")
async def edit_message(
    request: EditMessageRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Edit message text."""
    check_api_key(api_key)
    client = get_bot_client(request.token)

    try:
        result = await client.edit_message_text(
            chat_id=request.chat_id,
            message_id=request.message_id,
            text=request.text,
            parse_mode=request.parse_mode,
            reply_markup=request.reply_markup
        )
        return {"status": "ok", "result": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.delete("/api/message")
async def delete_message(
    token: str = Query(...),
    chat_id: int = Query(...),
    message_id: int = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Delete message."""
    check_api_key(api_key)
    client = get_bot_client(token)

    try:
        result = await client.delete_message(chat_id, message_id)
        return {"status": "ok", "result": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/photo")
async def send_photo(
    request: SendPhotoRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Send photo."""
    check_api_key(api_key)
    client = get_bot_client(request.token)

    try:
        result = await client.send_photo(
            chat_id=request.chat_id,
            photo=request.photo,
            caption=request.caption,
            parse_mode=request.parse_mode,
            reply_to_message_id=request.reply_to_message_id
        )
        return {"status": "ok", "result": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/document")
async def send_document(
    request: SendDocumentRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Send document."""
    check_api_key(api_key)
    client = get_bot_client(request.token)

    try:
        result = await client.send_document(
            chat_id=request.chat_id,
            document=request.document,
            caption=request.caption,
            parse_mode=request.parse_mode,
            reply_to_message_id=request.reply_to_message_id
        )
        return {"status": "ok", "result": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/action")
async def send_action(
    token: str = Query(...),
    chat_id: int = Query(...),
    action: str = Query("typing"),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Send typing action."""
    check_api_key(api_key)
    client = get_bot_client(token)

    try:
        result = await client.send_chat_action(chat_id, action)
        return {"status": "ok", "result": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.post("/api/answer")
async def answer_callback(
    request: AnswerCallbackRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Answer callback query."""
    check_api_key(api_key)
    client = get_bot_client(request.token)

    try:
        result = await client.answer_callback_query(
            callback_query_id=request.callback_query_id,
            text=request.text,
            show_alert=request.show_alert
        )
        return {"status": "ok", "result": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Webhook Management ====================

@app.post("/api/webhook")
async def set_webhook(
    request: SetWebhookRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Set webhook URL for specific bot."""
    check_api_key(api_key)
    client = get_bot_client(request.token)

    try:
        result = await client.set_webhook(
            url=request.url,
            max_connections=request.max_connections,
            allowed_updates=request.allowed_updates,
            secret_token=request.secret_token
        )
        return {"status": "ok", "result": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.delete("/api/webhook")
async def delete_webhook(
    token: str = Query(...),
    drop_pending: bool = Query(False),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Delete webhook."""
    check_api_key(api_key)
    client = get_bot_client(token)

    try:
        result = await client.delete_webhook(drop_pending)
        return {"status": "ok", "result": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


@app.get("/api/webhook")
async def get_webhook_info(
    token: str = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get webhook info."""
    check_api_key(api_key)
    client = get_bot_client(token)

    try:
        result = await client.get_webhook_info()
        return {"status": "ok", "webhook": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== File Download ====================

@app.get("/api/file")
async def get_file(
    token: str = Query(...),
    file_id: str = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get file download URL."""
    check_api_key(api_key)
    client = get_bot_client(token)

    try:
        file_info = await client.get_file(file_id)
        file_path = file_info.get("file_path")
        download_url = client.get_file_url(file_path) if file_path else None

        return {
            "status": "ok",
            "file": file_info,
            "download_url": download_url
        }
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Chat ====================

@app.get("/api/chat")
async def get_chat(
    token: str = Query(...),
    chat_id: int = Query(...),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get chat info."""
    check_api_key(api_key)
    client = get_bot_client(token)

    try:
        result = await client.get_chat(chat_id)
        return {"status": "ok", "chat": result}
    except TelegramAPIError as e:
        raise HTTPException(status_code=e.status_code, detail=e.message)


# ==================== Webhook Handler (Multi-tenant) ====================

@app.post("/webhook/telegram/{token_hash}")
async def webhook_handler(
    token_hash: str,
    request: Request,
    background_tasks: BackgroundTasks,
    x_telegram_bot_api_secret_token: Optional[str] = Header(None)
):
    """Handle incoming Telegram webhook updates.

    Each bot has its own webhook endpoint: /webhook/telegram/{token_hash}
    This allows multi-tenant operation with proper tenant identification.
    """
    # Find bot by token_hash
    if token_hash not in bot_registry:
        logger.warning(f"Received webhook for unknown bot: {token_hash}")
        raise HTTPException(status_code=404, detail="Bot not registered")

    bot_data = bot_registry[token_hash]
    bot_token = bot_data["token"]
    client = bot_data["client"]

    try:
        data = await request.json()
    except Exception as e:
        logger.error(f"Invalid JSON in webhook: {e}")
        raise HTTPException(status_code=400, detail="Invalid JSON")

    update_id = data.get("update_id", "unknown")
    logger.info(f"Received webhook update_id={update_id} for bot {token_hash}")

    # Normalize and forward to n8n
    normalized = await normalize_update(data, bot_token, client)
    if normalized:
        background_tasks.add_task(forward_to_n8n, normalized)

    return {"ok": True}


# ==================== Auto-setup Endpoint ====================

@app.post("/setup")
async def setup_bot(
    request: RegisterBotRequest,
    base_url: str = Query(..., description="Base URL for webhook (e.g., https://mcp.example.com)"),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Register bot and automatically set up its webhook.

    This is a convenience endpoint that:
    1. Registers the bot
    2. Sets the webhook URL to this server
    """
    check_api_key(api_key)

    # Register bot
    result = await register_bot(request.token)
    token_hash = result["token_hash"]

    # Set webhook
    webhook_url = f"{base_url.rstrip('/')}/webhook/telegram/{token_hash}"
    client = get_bot_client(request.token)

    try:
        await client.set_webhook(url=webhook_url)
        result["webhook_url"] = webhook_url
        result["webhook_set"] = True
    except TelegramAPIError as e:
        result["webhook_error"] = e.message
        result["webhook_set"] = False

    return result


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host=settings.SERVER_HOST,
        port=settings.SERVER_PORT,
        reload=True
    )
