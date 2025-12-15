"""MAX.ru Messenger API Client (via Wappi.pro).

Client for interacting with MAX.ru Messenger via Wappi.pro API.
Documentation: https://wappi.pro/max-api-documentation
"""

import logging
from typing import Optional, Dict, Any, List
from dataclasses import dataclass
import httpx

logger = logging.getLogger(__name__)


class MaxAPIError(Exception):
    """MAX API error."""
    def __init__(self, message: str, status_code: int = 500, response: dict = None):
        self.message = message
        self.status_code = status_code
        self.response = response or {}
        super().__init__(self.message)


@dataclass
class MaxMessage:
    """MAX message data class."""
    message_id: str
    chat_id: str
    from_user: str
    text: str
    timestamp: int
    type: str = "text"

    def to_dict(self) -> dict:
        return {
            "message_id": self.message_id,
            "chat_id": self.chat_id,
            "from_user": self.from_user,
            "text": self.text,
            "timestamp": self.timestamp,
            "type": self.type
        }


class MaxClient:
    """MAX.ru Messenger API Client via Wappi.pro."""

    BASE_URL = "https://wappi.pro/apimax"

    def __init__(self, api_token: str, profile_id: str):
        """Initialize MAX client.

        Args:
            api_token: Wappi API token
            profile_id: Wappi MAX profile ID
        """
        self.api_token = api_token
        self.profile_id = profile_id
        self._client: Optional[httpx.AsyncClient] = None

    async def connect(self):
        """Initialize HTTP client."""
        self._client = httpx.AsyncClient(
            timeout=30.0,
            headers={
                "Authorization": self.api_token,
                "Content-Type": "application/json"
            }
        )
        logger.info("MAX client connected")

    async def close(self):
        """Close HTTP client."""
        if self._client:
            await self._client.aclose()
            logger.info("MAX client closed")

    async def _request(self, method: str, endpoint: str, **kwargs) -> dict:
        """Make API request to MAX via Wappi."""
        if not self._client:
            await self.connect()

        url = f"{self.BASE_URL}/{endpoint}"

        # Add profile_id to params
        params = kwargs.pop("params", {})
        params["profile_id"] = self.profile_id

        try:
            response = await self._client.request(
                method,
                url,
                params=params,
                **kwargs
            )

            if response.status_code >= 400:
                error_data = response.json() if response.text else {}
                raise MaxAPIError(
                    message=error_data.get("message", f"HTTP {response.status_code}"),
                    status_code=response.status_code,
                    response=error_data
                )

            return response.json() if response.text else {}

        except httpx.RequestError as e:
            logger.error(f"MAX request error: {e}")
            raise MaxAPIError(f"Request failed: {e}")

    # ========== Profile & Status ==========

    async def get_status(self) -> dict:
        """Get MAX connection status."""
        return await self._request("GET", "sync/status")

    async def get_profile(self) -> dict:
        """Get profile information."""
        return await self._request("GET", "sync/profile")

    # ========== Send Messages ==========

    async def send_message(self, to: str, text: str) -> dict:
        """Send text message.

        Args:
            to: Recipient user ID or phone
            text: Message text

        Returns:
            API response with message_id
        """
        payload = {
            "recipient": to,
            "body": text
        }
        return await self._request("POST", "sync/message/send", json=payload)

    async def send_image(self, to: str, image_url: str, caption: str = None) -> dict:
        """Send image message."""
        payload = {
            "recipient": to,
            "url": image_url
        }
        if caption:
            payload["caption"] = caption

        return await self._request("POST", "sync/message/img/send", json=payload)

    async def send_document(self, to: str, document_url: str, filename: str = None) -> dict:
        """Send document message."""
        payload = {
            "recipient": to,
            "url": document_url
        }
        if filename:
            payload["filename"] = filename

        return await self._request("POST", "sync/message/doc/send", json=payload)

    async def send_voice(self, to: str, voice_url: str) -> dict:
        """Send voice message."""
        payload = {
            "recipient": to,
            "url": voice_url
        }
        return await self._request("POST", "sync/message/voice/send", json=payload)

    # ========== Chats ==========

    async def get_chats(self, limit: int = 100, offset: int = 0) -> List[dict]:
        """Get list of chats."""
        result = await self._request(
            "GET", "sync/chats/get",
            params={"limit": limit, "offset": offset}
        )
        return result.get("chats", [])

    async def get_chat_messages(self, chat_id: str, limit: int = 100) -> List[dict]:
        """Get messages from a specific chat."""
        result = await self._request(
            "GET", "sync/messages/get",
            params={"chat_id": chat_id, "limit": limit}
        )
        return result.get("messages", [])

    # ========== Webhooks ==========

    async def set_webhook(self, url: str) -> dict:
        """Set webhook URL for incoming messages."""
        payload = {"url": url}
        return await self._request("POST", "sync/webhook/set", json=payload)

    async def get_webhook(self) -> dict:
        """Get current webhook settings."""
        return await self._request("GET", "sync/webhook/get")

    async def delete_webhook(self) -> dict:
        """Delete webhook."""
        return await self._request("POST", "sync/webhook/delete")

    # ========== Bulk Messaging ==========

    async def start_mailing(
        self,
        recipients: List[str],
        text: str,
        delay_min: int = 10,
        delay_max: int = 30
    ) -> dict:
        """Start bulk mailing campaign."""
        payload = {
            "recipients": recipients,
            "body": text,
            "delay_min": delay_min,
            "delay_max": delay_max
        }
        return await self._request("POST", "async/mailing/start", json=payload)

    async def get_mailing_status(self, mailing_id: str) -> dict:
        """Get mailing campaign status."""
        return await self._request(
            "GET", "sync/mailing/status",
            params={"mailing_id": mailing_id}
        )

    async def stop_mailing(self, mailing_id: str) -> dict:
        """Stop mailing campaign."""
        return await self._request(
            "POST", "sync/mailing/stop",
            params={"mailing_id": mailing_id}
        )
