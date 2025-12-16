"""
Telegram User MCP - FastAPI wrapper for Pyrogram client
Port: 3002
"""

import asyncio
import logging
from contextlib import asynccontextmanager
from typing import Optional

import httpx
from fastapi import FastAPI, HTTPException, BackgroundTasks
from pydantic import BaseModel

import config
from telegram_user_client import TelegramUserClient, get_client

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


# === Pydantic models ===

class SendMessageRequest(BaseModel):
    chat_id: int | str
    text: str
    reply_to_message_id: Optional[int] = None


class SendMediaRequest(BaseModel):
    chat_id: int | str
    file: str  # path or URL
    caption: Optional[str] = None
    reply_to_message_id: Optional[int] = None


class GetHistoryRequest(BaseModel):
    chat_id: int | str
    limit: int = 50
    offset_id: int = 0


class WebhookMessage(BaseModel):
    channel: str = "telegram_user"
    message: dict


# === Webhook sender ===

async def send_to_webhook(message: dict):
    """Send incoming message to webhook"""
    if not config.WEBHOOK_URL:
        logger.debug("No webhook URL configured, skipping")
        return

    try:
        async with httpx.AsyncClient() as client:
            payload = WebhookMessage(message=message)
            response = await client.post(
                config.WEBHOOK_URL,
                json=payload.model_dump(),
                timeout=10.0
            )
            logger.info(f"Webhook sent: {response.status_code}")
    except Exception as e:
        logger.error(f"Webhook error: {e}")


# === Lifespan ===

@asynccontextmanager
async def lifespan(app: FastAPI):
    """Startup and shutdown"""
    client = get_client()

    # Set message handler
    client.set_message_handler(send_to_webhook)

    # Start client
    success = await client.start()
    if not success:
        logger.warning("Client not started - may need authorization")

    yield

    # Shutdown
    await client.stop()


# === FastAPI app ===

app = FastAPI(
    title="Telegram User MCP",
    description="Pyrogram-based Telegram user account API",
    version="1.0.0",
    lifespan=lifespan
)


# === Routes ===

@app.get("/health")
async def health():
    """Health check"""
    client = get_client()
    return {
        "status": "ok",
        "authorized": client.is_authorized
    }


@app.get("/me")
async def get_me():
    """Get current user info"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")
    return await client.get_me()


@app.get("/dialogs")
async def get_dialogs(limit: int = 100):
    """Get list of dialogs"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")
    return await client.get_dialogs(limit=limit)


@app.post("/history")
async def get_history(request: GetHistoryRequest):
    """Get chat history"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")
    return await client.get_chat_history(
        chat_id=request.chat_id,
        limit=request.limit,
        offset_id=request.offset_id
    )


@app.get("/chat/{chat_id}")
async def get_chat(chat_id: str):
    """Get chat info"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")

    # Try to parse as int, otherwise use as username
    try:
        chat_id_parsed = int(chat_id)
    except ValueError:
        chat_id_parsed = chat_id

    return await client.get_chat(chat_id_parsed)


@app.post("/send")
async def send_message(request: SendMessageRequest):
    """Send text message"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")

    return await client.send_message(
        chat_id=request.chat_id,
        text=request.text,
        reply_to_message_id=request.reply_to_message_id
    )


@app.post("/send/photo")
async def send_photo(request: SendMediaRequest):
    """Send photo"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")

    return await client.send_photo(
        chat_id=request.chat_id,
        photo=request.file,
        caption=request.caption,
        reply_to_message_id=request.reply_to_message_id
    )


@app.post("/send/video")
async def send_video(request: SendMediaRequest):
    """Send video"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")

    return await client.send_video(
        chat_id=request.chat_id,
        video=request.file,
        caption=request.caption,
        reply_to_message_id=request.reply_to_message_id
    )


@app.post("/send/document")
async def send_document(request: SendMediaRequest):
    """Send document"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")

    return await client.send_document(
        chat_id=request.chat_id,
        document=request.file,
        caption=request.caption,
        reply_to_message_id=request.reply_to_message_id
    )


@app.post("/send/voice")
async def send_voice(request: SendMediaRequest):
    """Send voice message"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")

    return await client.send_voice(
        chat_id=request.chat_id,
        voice=request.file,
        caption=request.caption,
        reply_to_message_id=request.reply_to_message_id
    )


@app.post("/send/audio")
async def send_audio(request: SendMediaRequest):
    """Send audio"""
    client = get_client()
    if not client.is_authorized:
        raise HTTPException(status_code=401, detail="Not authorized")

    return await client.send_audio(
        chat_id=request.chat_id,
        audio=request.file,
        caption=request.caption,
        reply_to_message_id=request.reply_to_message_id
    )


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=config.HOST, port=config.PORT)
