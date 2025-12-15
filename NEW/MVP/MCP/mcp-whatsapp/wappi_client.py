"""Wappi.pro WhatsApp API Client.

Client for interacting with Wappi.pro WhatsApp API.
Documentation: https://wappi.pro/api-documentation
"""

import logging
from typing import Optional, Dict, Any, List
from dataclasses import dataclass
import httpx

logger = logging.getLogger(__name__)


class WappiAPIError(Exception):
    """Wappi API error."""
    def __init__(self, message: str, status_code: int = 500, response: dict = None):
        self.message = message
        self.status_code = status_code
        self.response = response or {}
        super().__init__(self.message)


@dataclass
class WappiMessage:
    """Wappi message data class."""
    message_id: str
    chat_id: str
    from_phone: str
    to_phone: str
    text: str
    timestamp: int
    type: str = "text"
    is_outgoing: bool = False
    media_url: Optional[str] = None
    reply_to: Optional[str] = None

    def to_dict(self) -> dict:
        return {
            "message_id": self.message_id,
            "chat_id": self.chat_id,
            "from_phone": self.from_phone,
            "to_phone": self.to_phone,
            "text": self.text,
            "timestamp": self.timestamp,
            "type": self.type,
            "is_outgoing": self.is_outgoing,
            "media_url": self.media_url,
            "reply_to": self.reply_to
        }


class WappiClient:
    """Wappi.pro WhatsApp API Client."""

    BASE_URL = "https://wappi.pro/api"

    def __init__(self, api_token: str, profile_id: str):
        """Initialize Wappi client.

        Args:
            api_token: Wappi API token
            profile_id: Wappi profile ID (linked WhatsApp number)
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
        logger.info("Wappi client connected")

    async def close(self):
        """Close HTTP client."""
        if self._client:
            await self._client.aclose()
            logger.info("Wappi client closed")

    async def _request(self, method: str, endpoint: str, **kwargs) -> dict:
        """Make API request to Wappi."""
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
                raise WappiAPIError(
                    message=error_data.get("message", f"HTTP {response.status_code}"),
                    status_code=response.status_code,
                    response=error_data
                )

            return response.json() if response.text else {}

        except httpx.RequestError as e:
            logger.error(f"Wappi request error: {e}")
            raise WappiAPIError(f"Request failed: {e}")

    # ========== Profile & Status ==========

    async def get_status(self) -> dict:
        """Get WhatsApp connection status."""
        return await self._request("GET", "sync/status")

    async def get_profile(self) -> dict:
        """Get profile information."""
        return await self._request("GET", "sync/profile")

    async def get_qr_code(self) -> dict:
        """Get QR code for WhatsApp Web authorization."""
        return await self._request("GET", "sync/qr")

    # ========== Send Messages ==========

    async def send_message(self, to: str, text: str, reply_to: str = None) -> dict:
        """Send text message.

        Args:
            to: Recipient phone number (with country code, e.g., 79991234567)
            text: Message text
            reply_to: Message ID to reply to (optional)

        Returns:
            API response with message_id
        """
        payload = {
            "recipient": to,
            "body": text
        }
        if reply_to:
            payload["reply_to"] = reply_to

        return await self._request("POST", "sync/message/send", json=payload)

    async def send_message_async(self, to: str, text: str) -> dict:
        """Send text message asynchronously (queued)."""
        payload = {
            "recipient": to,
            "body": text
        }
        return await self._request("POST", "async/message/send", json=payload)

    async def send_image(self, to: str, image_url: str, caption: str = None) -> dict:
        """Send image message.

        Args:
            to: Recipient phone number
            image_url: URL of the image to send
            caption: Image caption (optional)
        """
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

    async def send_audio(self, to: str, audio_url: str) -> dict:
        """Send audio message."""
        payload = {
            "recipient": to,
            "url": audio_url
        }
        return await self._request("POST", "sync/message/audio/send", json=payload)

    async def send_voice(self, to: str, voice_url: str) -> dict:
        """Send voice message (PTT - push to talk)."""
        payload = {
            "recipient": to,
            "url": voice_url
        }
        return await self._request("POST", "sync/message/voice/send", json=payload)

    async def send_video(self, to: str, video_url: str, caption: str = None) -> dict:
        """Send video message."""
        payload = {
            "recipient": to,
            "url": video_url
        }
        if caption:
            payload["caption"] = caption

        return await self._request("POST", "sync/message/video/send", json=payload)

    async def send_location(self, to: str, latitude: float, longitude: float, name: str = None) -> dict:
        """Send location message."""
        payload = {
            "recipient": to,
            "lat": latitude,
            "lon": longitude
        }
        if name:
            payload["name"] = name

        return await self._request("POST", "sync/message/location/send", json=payload)

    async def send_contact(self, to: str, contact_phone: str, contact_name: str) -> dict:
        """Send contact card."""
        payload = {
            "recipient": to,
            "phone": contact_phone,
            "name": contact_name
        }
        return await self._request("POST", "sync/message/contact/send", json=payload)

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

    # ========== Contacts ==========

    async def get_contacts(self) -> List[dict]:
        """Get contact list."""
        result = await self._request("GET", "sync/contacts/get")
        return result.get("contacts", [])

    async def check_phone(self, phone: str) -> dict:
        """Check if phone number is registered in WhatsApp."""
        return await self._request(
            "GET", "sync/contact/check",
            params={"phone": phone}
        )

    # ========== Groups ==========

    async def get_groups(self) -> List[dict]:
        """Get list of groups."""
        result = await self._request("GET", "sync/groups/get")
        return result.get("groups", [])

    async def create_group(self, name: str, participants: List[str]) -> dict:
        """Create a new group."""
        payload = {
            "name": name,
            "participants": participants
        }
        return await self._request("POST", "sync/group/create", json=payload)

    # ========== Webhooks ==========

    async def set_webhook(self, url: str) -> dict:
        """Set webhook URL for incoming messages.

        Args:
            url: Webhook URL to receive incoming messages
        """
        payload = {
            "url": url
        }
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
        """Start bulk mailing campaign.

        Args:
            recipients: List of phone numbers
            text: Message text
            delay_min: Minimum delay between messages (seconds)
            delay_max: Maximum delay between messages (seconds)
        """
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
