"""
Telegram User Client - Pyrogram wrapper
Allows sending/receiving messages as a real user account
"""

import asyncio
import logging
from typing import Optional, Callable, List, Dict, Any
from pathlib import Path

from pyrogram import Client, filters
from pyrogram.types import Message
from pyrogram.errors import SessionPasswordNeeded, PhoneCodeInvalid

import config

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


class TelegramUserClient:
    def __init__(
        self,
        api_id: str = None,
        api_hash: str = None,
        phone_number: str = None,
        session_name: str = None,
        session_dir: str = None
    ):
        self.api_id = api_id or config.API_ID
        self.api_hash = api_hash or config.API_HASH
        self.phone_number = phone_number or config.PHONE_NUMBER
        self.session_name = session_name or config.SESSION_NAME
        self.session_dir = Path(session_dir or config.SESSION_DIR)

        self.session_dir.mkdir(parents=True, exist_ok=True)

        self.client: Optional[Client] = None
        self.message_handler: Optional[Callable] = None
        self._is_authorized = False

    async def start(self) -> bool:
        """Initialize and start the client"""
        session_path = str(self.session_dir / self.session_name)

        self.client = Client(
            name=session_path,
            api_id=self.api_id,
            api_hash=self.api_hash,
            phone_number=self.phone_number
        )

        # Register message handler
        @self.client.on_message(filters.private | filters.group)
        async def handle_message(client: Client, message: Message):
            if self.message_handler:
                await self.message_handler(self._format_message(message))

        try:
            await self.client.start()
            self._is_authorized = True
            me = await self.client.get_me()
            logger.info(f"Logged in as: {me.first_name} (@{me.username})")
            return True
        except Exception as e:
            logger.error(f"Failed to start client: {e}")
            return False

    async def stop(self):
        """Stop the client"""
        if self.client:
            await self.client.stop()
            self._is_authorized = False

    @property
    def is_authorized(self) -> bool:
        return self._is_authorized

    def set_message_handler(self, handler: Callable):
        """Set callback for incoming messages"""
        self.message_handler = handler

    # === Dialog methods ===

    async def get_dialogs(self, limit: int = 100) -> List[Dict[str, Any]]:
        """Get list of dialogs (chats)"""
        dialogs = []
        async for dialog in self.client.get_dialogs(limit=limit):
            dialogs.append({
                "id": dialog.chat.id,
                "type": dialog.chat.type.value,
                "title": dialog.chat.title or dialog.chat.first_name or "Unknown",
                "username": dialog.chat.username,
                "unread_count": dialog.unread_messages_count,
                "last_message": self._format_message(dialog.top_message) if dialog.top_message else None
            })
        return dialogs

    async def get_chat_history(
        self,
        chat_id: int | str,
        limit: int = 50,
        offset_id: int = 0
    ) -> List[Dict[str, Any]]:
        """Get message history for a chat"""
        messages = []
        async for message in self.client.get_chat_history(
            chat_id=chat_id,
            limit=limit,
            offset_id=offset_id
        ):
            messages.append(self._format_message(message))
        return messages

    # === Send methods ===

    async def send_message(
        self,
        chat_id: int | str,
        text: str,
        reply_to_message_id: int = None
    ) -> Dict[str, Any]:
        """Send text message"""
        message = await self.client.send_message(
            chat_id=chat_id,
            text=text,
            reply_to_message_id=reply_to_message_id
        )
        return self._format_message(message)

    async def send_photo(
        self,
        chat_id: int | str,
        photo: str,  # file path or URL
        caption: str = None,
        reply_to_message_id: int = None
    ) -> Dict[str, Any]:
        """Send photo"""
        message = await self.client.send_photo(
            chat_id=chat_id,
            photo=photo,
            caption=caption,
            reply_to_message_id=reply_to_message_id
        )
        return self._format_message(message)

    async def send_video(
        self,
        chat_id: int | str,
        video: str,
        caption: str = None,
        reply_to_message_id: int = None
    ) -> Dict[str, Any]:
        """Send video"""
        message = await self.client.send_video(
            chat_id=chat_id,
            video=video,
            caption=caption,
            reply_to_message_id=reply_to_message_id
        )
        return self._format_message(message)

    async def send_document(
        self,
        chat_id: int | str,
        document: str,
        caption: str = None,
        reply_to_message_id: int = None
    ) -> Dict[str, Any]:
        """Send document/file"""
        message = await self.client.send_document(
            chat_id=chat_id,
            document=document,
            caption=caption,
            reply_to_message_id=reply_to_message_id
        )
        return self._format_message(message)

    async def send_voice(
        self,
        chat_id: int | str,
        voice: str,
        caption: str = None,
        reply_to_message_id: int = None
    ) -> Dict[str, Any]:
        """Send voice message"""
        message = await self.client.send_voice(
            chat_id=chat_id,
            voice=voice,
            caption=caption,
            reply_to_message_id=reply_to_message_id
        )
        return self._format_message(message)

    async def send_audio(
        self,
        chat_id: int | str,
        audio: str,
        caption: str = None,
        reply_to_message_id: int = None
    ) -> Dict[str, Any]:
        """Send audio file"""
        message = await self.client.send_audio(
            chat_id=chat_id,
            audio=audio,
            caption=caption,
            reply_to_message_id=reply_to_message_id
        )
        return self._format_message(message)

    # === Utility methods ===

    async def get_me(self) -> Dict[str, Any]:
        """Get current user info"""
        me = await self.client.get_me()
        return {
            "id": me.id,
            "first_name": me.first_name,
            "last_name": me.last_name,
            "username": me.username,
            "phone_number": me.phone_number
        }

    async def get_chat(self, chat_id: int | str) -> Dict[str, Any]:
        """Get chat info"""
        chat = await self.client.get_chat(chat_id)
        return {
            "id": chat.id,
            "type": chat.type.value,
            "title": chat.title or chat.first_name,
            "username": chat.username,
            "members_count": getattr(chat, 'members_count', None)
        }

    async def download_media(self, message_id: int, chat_id: int | str, path: str = None) -> str:
        """Download media from message"""
        message = await self.client.get_messages(chat_id, message_id)
        if message.media:
            return await self.client.download_media(message, file_name=path)
        return None

    # === Private methods ===

    def _format_message(self, message: Message) -> Dict[str, Any]:
        """Format Pyrogram message to dict"""
        if not message:
            return None

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
        elif message.sticker:
            media_type = "sticker"
            media_id = message.sticker.file_id

        return {
            "id": message.id,
            "chat_id": message.chat.id,
            "from_user": {
                "id": message.from_user.id if message.from_user else None,
                "first_name": message.from_user.first_name if message.from_user else None,
                "username": message.from_user.username if message.from_user else None
            } if message.from_user else None,
            "date": message.date.isoformat() if message.date else None,
            "text": message.text or message.caption or "",
            "media_type": media_type,
            "media_id": media_id,
            "reply_to_message_id": message.reply_to_message_id
        }


# Singleton instance
_client_instance: Optional[TelegramUserClient] = None


def get_client() -> TelegramUserClient:
    """Get or create client instance"""
    global _client_instance
    if _client_instance is None:
        _client_instance = TelegramUserClient()
    return _client_instance
