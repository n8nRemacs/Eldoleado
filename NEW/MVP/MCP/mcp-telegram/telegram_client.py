"""Telegram Bot API Client."""

import logging
from typing import Optional, List, Dict, Any
import httpx

logger = logging.getLogger(__name__)


class TelegramAPIError(Exception):
    """Telegram API error."""

    def __init__(self, status_code: int, message: str, error_code: Optional[int] = None):
        self.status_code = status_code
        self.message = message
        self.error_code = error_code
        super().__init__(message)


class TelegramClient:
    """Async client for Telegram Bot API."""

    BASE_URL = "https://api.telegram.org/bot{token}"

    def __init__(self, token: str):
        self.token = token
        self.base_url = self.BASE_URL.format(token=token)
        self._client: Optional[httpx.AsyncClient] = None

    async def connect(self):
        """Initialize HTTP client."""
        self._client = httpx.AsyncClient(timeout=30.0)

    async def close(self):
        """Close HTTP client."""
        if self._client:
            await self._client.aclose()
            self._client = None

    async def _request(
        self,
        method: str,
        endpoint: str,
        data: Optional[Dict] = None,
        files: Optional[Dict] = None
    ) -> Dict[str, Any]:
        """Make API request."""
        if not self._client:
            await self.connect()

        url = f"{self.base_url}/{endpoint}"

        try:
            if method == "GET":
                response = await self._client.get(url, params=data)
            else:
                if files:
                    response = await self._client.post(url, data=data, files=files)
                else:
                    response = await self._client.post(url, json=data)

            result = response.json()

            if not result.get("ok"):
                raise TelegramAPIError(
                    status_code=response.status_code,
                    message=result.get("description", "Unknown error"),
                    error_code=result.get("error_code")
                )

            return result.get("result", {})

        except httpx.RequestError as e:
            logger.error(f"Request error: {e}")
            raise TelegramAPIError(500, str(e))

    # ==================== Bot Methods ====================

    async def get_me(self) -> Dict[str, Any]:
        """Get bot information."""
        return await self._request("GET", "getMe")

    async def set_webhook(
        self,
        url: str,
        certificate: Optional[bytes] = None,
        max_connections: int = 40,
        allowed_updates: Optional[List[str]] = None,
        secret_token: Optional[str] = None
    ) -> bool:
        """Set webhook URL."""
        data = {
            "url": url,
            "max_connections": max_connections
        }
        if allowed_updates:
            data["allowed_updates"] = allowed_updates
        if secret_token:
            data["secret_token"] = secret_token

        files = None
        if certificate:
            files = {"certificate": certificate}

        return await self._request("POST", "setWebhook", data, files)

    async def delete_webhook(self, drop_pending_updates: bool = False) -> bool:
        """Delete webhook."""
        return await self._request("POST", "deleteWebhook", {
            "drop_pending_updates": drop_pending_updates
        })

    async def get_webhook_info(self) -> Dict[str, Any]:
        """Get webhook info."""
        return await self._request("GET", "getWebhookInfo")

    # ==================== Message Methods ====================

    async def send_message(
        self,
        chat_id: int,
        text: str,
        parse_mode: Optional[str] = None,
        reply_to_message_id: Optional[int] = None,
        reply_markup: Optional[Dict] = None,
        disable_notification: bool = False,
        disable_web_page_preview: bool = False
    ) -> Dict[str, Any]:
        """Send text message."""
        data = {
            "chat_id": chat_id,
            "text": text,
            "disable_notification": disable_notification,
            "disable_web_page_preview": disable_web_page_preview
        }
        if parse_mode:
            data["parse_mode"] = parse_mode
        if reply_to_message_id:
            data["reply_to_message_id"] = reply_to_message_id
        if reply_markup:
            data["reply_markup"] = reply_markup

        return await self._request("POST", "sendMessage", data)

    async def edit_message_text(
        self,
        chat_id: int,
        message_id: int,
        text: str,
        parse_mode: Optional[str] = None,
        reply_markup: Optional[Dict] = None
    ) -> Dict[str, Any]:
        """Edit message text."""
        data = {
            "chat_id": chat_id,
            "message_id": message_id,
            "text": text
        }
        if parse_mode:
            data["parse_mode"] = parse_mode
        if reply_markup:
            data["reply_markup"] = reply_markup

        return await self._request("POST", "editMessageText", data)

    async def delete_message(self, chat_id: int, message_id: int) -> bool:
        """Delete message."""
        return await self._request("POST", "deleteMessage", {
            "chat_id": chat_id,
            "message_id": message_id
        })

    async def send_photo(
        self,
        chat_id: int,
        photo: str,
        caption: Optional[str] = None,
        parse_mode: Optional[str] = None,
        reply_to_message_id: Optional[int] = None,
        reply_markup: Optional[Dict] = None
    ) -> Dict[str, Any]:
        """Send photo."""
        data = {"chat_id": chat_id, "photo": photo}
        if caption:
            data["caption"] = caption
        if parse_mode:
            data["parse_mode"] = parse_mode
        if reply_to_message_id:
            data["reply_to_message_id"] = reply_to_message_id
        if reply_markup:
            data["reply_markup"] = reply_markup

        return await self._request("POST", "sendPhoto", data)

    async def send_document(
        self,
        chat_id: int,
        document: str,
        caption: Optional[str] = None,
        parse_mode: Optional[str] = None,
        reply_to_message_id: Optional[int] = None
    ) -> Dict[str, Any]:
        """Send document."""
        data = {"chat_id": chat_id, "document": document}
        if caption:
            data["caption"] = caption
        if parse_mode:
            data["parse_mode"] = parse_mode
        if reply_to_message_id:
            data["reply_to_message_id"] = reply_to_message_id

        return await self._request("POST", "sendDocument", data)

    async def send_voice(
        self,
        chat_id: int,
        voice: str,
        caption: Optional[str] = None,
        parse_mode: Optional[str] = None,
        reply_to_message_id: Optional[int] = None
    ) -> Dict[str, Any]:
        """Send voice message."""
        data = {"chat_id": chat_id, "voice": voice}
        if caption:
            data["caption"] = caption
        if parse_mode:
            data["parse_mode"] = parse_mode
        if reply_to_message_id:
            data["reply_to_message_id"] = reply_to_message_id

        return await self._request("POST", "sendVoice", data)

    async def send_video(
        self,
        chat_id: int,
        video: str,
        caption: Optional[str] = None,
        parse_mode: Optional[str] = None,
        reply_to_message_id: Optional[int] = None
    ) -> Dict[str, Any]:
        """Send video."""
        data = {"chat_id": chat_id, "video": video}
        if caption:
            data["caption"] = caption
        if parse_mode:
            data["parse_mode"] = parse_mode
        if reply_to_message_id:
            data["reply_to_message_id"] = reply_to_message_id

        return await self._request("POST", "sendVideo", data)

    async def send_chat_action(self, chat_id: int, action: str = "typing") -> bool:
        """Send chat action (typing indicator)."""
        return await self._request("POST", "sendChatAction", {
            "chat_id": chat_id,
            "action": action
        })

    async def answer_callback_query(
        self,
        callback_query_id: str,
        text: Optional[str] = None,
        show_alert: bool = False
    ) -> bool:
        """Answer callback query."""
        data = {"callback_query_id": callback_query_id, "show_alert": show_alert}
        if text:
            data["text"] = text

        return await self._request("POST", "answerCallbackQuery", data)

    # ==================== File Methods ====================

    async def get_file(self, file_id: str) -> Dict[str, Any]:
        """Get file info for download."""
        return await self._request("GET", "getFile", {"file_id": file_id})

    def get_file_url(self, file_path: str) -> str:
        """Get full URL to download file."""
        return f"https://api.telegram.org/file/bot{self.token}/{file_path}"

    async def download_file(self, file_id: str) -> bytes:
        """Download file by file_id."""
        file_info = await self.get_file(file_id)
        file_path = file_info.get("file_path")
        if not file_path:
            raise TelegramAPIError(400, "File path not available")

        url = self.get_file_url(file_path)
        response = await self._client.get(url)
        if response.status_code != 200:
            raise TelegramAPIError(response.status_code, "Failed to download file")

        return response.content

    # ==================== Chat Methods ====================

    async def get_chat(self, chat_id: int) -> Dict[str, Any]:
        """Get chat info."""
        return await self._request("GET", "getChat", {"chat_id": chat_id})

    async def get_chat_member(self, chat_id: int, user_id: int) -> Dict[str, Any]:
        """Get chat member info."""
        return await self._request("GET", "getChatMember", {
            "chat_id": chat_id,
            "user_id": user_id
        })
