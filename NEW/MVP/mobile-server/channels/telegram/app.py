"""
Telegram User Channel - Pyrogram
Port: 3002
"""

import asyncio
import logging
from contextlib import asynccontextmanager
from typing import Optional, List, Dict, Any
from pathlib import Path

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import httpx
from pyrogram import Client, filters
from pyrogram.types import Message

import config

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("TelegramChannel")


# === Pydantic Models ===

class SendMessageRequest(BaseModel):
    chat_id: int | str
    text: str
    reply_to_message_id: Optional[int] = None


class SendMediaRequest(BaseModel):
    chat_id: int | str
    url: str
    caption: Optional[str] = None
    reply_to_message_id: Optional[int] = None


class GetHistoryRequest(BaseModel):
    chat_id: int | str
    limit: int = 50
    offset_id: int = 0


# === Telegram Client ===

class TelegramChannel:
    def __init__(self):
        self.client: Optional[Client] = None
        self.is_authorized = False

        Path(config.SESSION_DIR).mkdir(parents=True, exist_ok=True)

    async def start(self):
        """Start Pyrogram client"""
        session_path = f"{config.SESSION_DIR}/{config.SESSION_NAME}"

        self.client = Client(
            name=session_path,
            api_id=config.API_ID,
            api_hash=config.API_HASH,
            phone_number=config.PHONE_NUMBER
        )

        # Register message handler
        @self.client.on_message(filters.private | filters.group)
        async def on_message(client: Client, message: Message):
            await self._handle_incoming(message)

        try:
            await self.client.start()
            self.is_authorized = True
            me = await self.client.get_me()
            logger.info(f"Logged in as: {me.first_name} (@{me.username})")
        except Exception as e:
            logger.error(f"Failed to start: {e}")
            self.is_authorized = False

    async def stop(self):
        """Stop client"""
        if self.client:
            await self.client.stop()
        self.is_authorized = False

    async def _handle_incoming(self, message: Message):
        """Handle incoming message, forward to webhook"""
        data = self._format_message(message)

        try:
            async with httpx.AsyncClient() as client:
                await client.post(
                    config.WEBHOOK_URL,
                    json={
                        "service": "telegram",
                        "event": "message",
                        "data": data
                    },
                    timeout=10
                )
        except Exception as e:
            logger.error(f"Webhook error: {e}")

    def _format_message(self, message: Message) -> Dict[str, Any]:
        """Format Pyrogram message to dict"""
        media_type = None
        media_id = None

        if message.photo:
            media_type = "photo"
            media_id = message.photo.file_id
        elif message.video:
            media_type = "video"
            media_id = message.video.file_id
        elif message.document:
            media_type = "document"
            media_id = message.document.file_id
        elif message.voice:
            media_type = "voice"
            media_id = message.voice.file_id
        elif message.audio:
            media_type = "audio"
            media_id = message.audio.file_id

        return {
            "id": message.id,
            "chat_id": message.chat.id,
            "from_user": {
                "id": message.from_user.id if message.from_user else None,
                "first_name": message.from_user.first_name if message.from_user else None,
                "username": message.from_user.username if message.from_user else None
            } if message.from_user else None,
            "text": message.text or message.caption or "",
            "media_type": media_type,
            "media_id": media_id,
            "date": message.date.isoformat() if message.date else None
        }

    # === API Methods ===

    async def get_dialogs(self, limit: int = 100) -> List[Dict]:
        """Get dialogs list"""
        dialogs = []
        async for dialog in self.client.get_dialogs(limit=limit):
            dialogs.append({
                "id": dialog.chat.id,
                "type": dialog.chat.type.value,
                "title": dialog.chat.title or dialog.chat.first_name or "Unknown",
                "username": dialog.chat.username,
                "unread_count": dialog.unread_messages_count
            })
        return dialogs

    async def get_history(self, chat_id: int | str, limit: int = 50, offset_id: int = 0) -> List[Dict]:
        """Get chat history"""
        messages = []
        async for msg in self.client.get_chat_history(chat_id, limit=limit, offset_id=offset_id):
            messages.append(self._format_message(msg))
        return messages

    async def send_message(self, chat_id: int | str, text: str, reply_to: int = None) -> Dict:
        """Send text message"""
        msg = await self.client.send_message(chat_id, text, reply_to_message_id=reply_to)
        return self._format_message(msg)

    async def send_photo(self, chat_id: int | str, url: str, caption: str = None) -> Dict:
        """Send photo"""
        msg = await self.client.send_photo(chat_id, url, caption=caption)
        return self._format_message(msg)

    async def send_video(self, chat_id: int | str, url: str, caption: str = None) -> Dict:
        """Send video"""
        msg = await self.client.send_video(chat_id, url, caption=caption)
        return self._format_message(msg)

    async def send_document(self, chat_id: int | str, url: str, caption: str = None) -> Dict:
        """Send document"""
        msg = await self.client.send_document(chat_id, url, caption=caption)
        return self._format_message(msg)

    async def send_voice(self, chat_id: int | str, url: str) -> Dict:
        """Send voice"""
        msg = await self.client.send_voice(chat_id, url)
        return self._format_message(msg)


# === FastAPI ===

channel = TelegramChannel()


@asynccontextmanager
async def lifespan(app: FastAPI):
    await channel.start()
    yield
    await channel.stop()


app = FastAPI(
    title="Telegram User Channel",
    version="1.0.0",
    lifespan=lifespan
)


@app.get("/health")
async def health():
    return {
        "status": "ok",
        "authorized": channel.is_authorized
    }


@app.get("/dialogs")
async def get_dialogs(limit: int = 100):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.get_dialogs(limit)


@app.post("/history")
async def get_history(req: GetHistoryRequest):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.get_history(req.chat_id, req.limit, req.offset_id)


@app.post("/send")
async def send_message(req: SendMessageRequest):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.send_message(req.chat_id, req.text, req.reply_to_message_id)


@app.post("/send/photo")
async def send_photo(req: SendMediaRequest):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.send_photo(req.chat_id, req.url, req.caption)


@app.post("/send/video")
async def send_video(req: SendMediaRequest):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.send_video(req.chat_id, req.url, req.caption)


@app.post("/send/document")
async def send_document(req: SendMediaRequest):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.send_document(req.chat_id, req.url, req.caption)


@app.post("/send/voice")
async def send_voice(req: SendMediaRequest):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.send_voice(req.chat_id, req.url)


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=config.HOST, port=config.PORT)
