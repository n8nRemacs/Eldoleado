"""MAX.ru Bot API Client (Direct).

Client for interacting with MAX.ru Bot API directly.
Documentation: https://dev.max.ru/docs-api
API Base: https://platform-api.max.ru
"""

import logging
from typing import Optional, List, Dict, Any
from dataclasses import dataclass
import httpx

logger = logging.getLogger(__name__)


class MaxAPIError(Exception):
    """MAX API error."""
    def __init__(self, message: str, code: str = "", status_code: int = 500):
        self.message = message
        self.code = code
        self.status_code = status_code
        super().__init__(self.message)


@dataclass
class MaxChat:
    """MAX chat data."""
    chat_id: int
    type: str  # dialog, chat, channel
    status: str
    title: str = None
    description: str = None

    def to_dict(self) -> dict:
        return {
            "chat_id": self.chat_id,
            "type": self.type,
            "status": self.status,
            "title": self.title,
            "description": self.description
        }


@dataclass
class MaxMessage:
    """MAX message data."""
    message_id: str
    timestamp: int
    sender: dict
    body: dict
    recipient: dict = None

    def to_dict(self) -> dict:
        return {
            "message_id": self.message_id,
            "timestamp": self.timestamp,
            "sender": self.sender,
            "body": self.body,
            "recipient": self.recipient
        }


class MaxClient:
    """MAX.ru Bot API Client (direct API).

    Uses access_token from MasterBot.
    API: https://platform-api.max.ru
    """

    BASE_URL = "https://platform-api.max.ru"

    def __init__(self, access_token: str):
        """Initialize MAX client.

        Args:
            access_token: Bot access token from MasterBot
        """
        self.access_token = access_token
        self._client: Optional[httpx.AsyncClient] = None

    async def connect(self):
        """Initialize HTTP client."""
        self._client = httpx.AsyncClient(
            timeout=30.0,
            headers={
                "Authorization": self.access_token,
                "Content-Type": "application/json"
            }
        )
        logger.info("MAX client connected")

    async def close(self):
        """Close HTTP client."""
        if self._client:
            await self._client.aclose()
            logger.info("MAX client closed")

    async def _request(
        self,
        method: str,
        endpoint: str,
        params: dict = None,
        json_data: dict = None
    ) -> dict:
        """Make API request to MAX.

        Args:
            method: HTTP method
            endpoint: API endpoint (e.g., '/messages')
            params: Query parameters
            json_data: JSON body for POST/PUT

        Returns:
            Response data
        """
        if not self._client:
            await self.connect()

        url = f"{self.BASE_URL}{endpoint}"

        # Add access_token to params
        if params is None:
            params = {}
        params["access_token"] = self.access_token

        try:
            if method.upper() == "GET":
                response = await self._client.get(url, params=params)
            elif method.upper() == "POST":
                response = await self._client.post(url, params=params, json=json_data)
            elif method.upper() == "PUT":
                response = await self._client.put(url, params=params, json=json_data)
            elif method.upper() == "DELETE":
                response = await self._client.delete(url, params=params)
            elif method.upper() == "PATCH":
                response = await self._client.patch(url, params=params, json=json_data)
            else:
                raise ValueError(f"Unsupported method: {method}")

            if response.status_code >= 400:
                error_data = response.json() if response.text else {}
                raise MaxAPIError(
                    message=error_data.get("message", f"HTTP {response.status_code}"),
                    code=error_data.get("code", ""),
                    status_code=response.status_code
                )

            return response.json() if response.text else {}

        except httpx.RequestError as e:
            logger.error(f"MAX request error: {e}")
            raise MaxAPIError(f"Request failed: {e}")

    # ========== Bot Info ==========

    async def get_me(self) -> dict:
        """Get bot info."""
        return await self._request("GET", "/me")

    # ========== Messages ==========

    async def send_message(
        self,
        chat_id: int = None,
        user_id: int = None,
        text: str = None,
        attachments: List[dict] = None,
        link: dict = None,
        notify: bool = True,
        format: str = None,
        disable_link_preview: bool = False
    ) -> dict:
        """Send message to chat or user.

        Args:
            chat_id: Chat ID (for group chats)
            user_id: User ID (for direct messages)
            text: Message text
            attachments: List of attachments (keyboards, images, etc.)
            link: Link preview object
            notify: Enable notifications
            format: Text format ('markdown' or 'html')
            disable_link_preview: Disable link preview

        Returns:
            SendMessageResult with message info
        """
        params = {"disable_link_preview": disable_link_preview}
        if chat_id:
            params["chat_id"] = chat_id
        if user_id:
            params["user_id"] = user_id

        body = {}
        if text:
            body["text"] = text
        if attachments:
            body["attachments"] = attachments
        if link:
            body["link"] = link
        if not notify:
            body["notify"] = False
        if format:
            body["format"] = format

        return await self._request("POST", "/messages", params=params, json_data=body)

    async def edit_message(
        self,
        message_id: str,
        text: str = None,
        attachments: List[dict] = None,
        link: dict = None,
        format: str = None
    ) -> dict:
        """Edit message."""
        body = {}
        if text:
            body["text"] = text
        if attachments:
            body["attachments"] = attachments
        if link:
            body["link"] = link
        if format:
            body["format"] = format

        return await self._request("PUT", f"/messages/{message_id}", json_data=body)

    async def delete_message(self, message_id: str) -> dict:
        """Delete message."""
        return await self._request("DELETE", f"/messages/{message_id}")

    async def get_message(self, message_id: str) -> dict:
        """Get message by ID."""
        return await self._request("GET", f"/messages/{message_id}")

    # ========== Chats ==========

    async def get_chats(self, count: int = 50, marker: int = None) -> dict:
        """Get list of chats.

        Args:
            count: Max chats to return (default 50, max 100)
            marker: Pagination marker

        Returns:
            Dict with 'chats' list and 'marker' for pagination
        """
        params = {"count": count}
        if marker:
            params["marker"] = marker

        return await self._request("GET", "/chats", params=params)

    async def get_chat(self, chat_id: int) -> dict:
        """Get chat by ID."""
        return await self._request("GET", f"/chats/{chat_id}")

    async def get_chat_by_link(self, link: str) -> dict:
        """Get chat by link."""
        return await self._request("GET", f"/chats/{link}")

    async def send_action(self, chat_id: int, action: str = "typing_on") -> dict:
        """Send action (typing indicator).

        Args:
            action: typing_on, sending_photo, sending_video, sending_audio,
                   sending_file, mark_seen
        """
        body = {"action": action}
        return await self._request("POST", f"/chats/{chat_id}/actions", json_data=body)

    async def get_chat_members(
        self,
        chat_id: int,
        count: int = 20,
        marker: int = None,
        user_ids: List[int] = None
    ) -> dict:
        """Get chat members."""
        params = {"count": count}
        if marker:
            params["marker"] = marker
        if user_ids:
            params["user_ids"] = ",".join(map(str, user_ids))

        return await self._request("GET", f"/chats/{chat_id}/members", params=params)

    async def leave_chat(self, chat_id: int) -> dict:
        """Leave chat."""
        return await self._request("DELETE", f"/chats/{chat_id}/members/me")

    # ========== Subscriptions (Webhooks) ==========

    async def subscribe(
        self,
        url: str,
        update_types: List[str] = None,
        version: str = None,
        secret: str = None
    ) -> dict:
        """Subscribe to webhook updates.

        Args:
            url: Webhook URL (ports: 80, 8080, 443, 8443, 16384-32383)
            update_types: List of update types to receive
            version: API version
            secret: Secret for request verification

        Returns:
            Success response
        """
        body = {"url": url}
        if update_types:
            body["update_types"] = update_types
        if version:
            body["version"] = version
        if secret:
            body["secret"] = secret

        return await self._request("POST", "/subscriptions", json_data=body)

    async def unsubscribe(self, url: str) -> dict:
        """Unsubscribe from webhook."""
        return await self._request("DELETE", "/subscriptions", params={"url": url})

    async def get_subscriptions(self) -> dict:
        """Get current subscriptions."""
        return await self._request("GET", "/subscriptions")

    # ========== Updates (Long Polling) ==========

    async def get_updates(
        self,
        limit: int = 100,
        timeout: int = 30,
        marker: int = None,
        types: List[str] = None
    ) -> dict:
        """Get updates via long polling.

        Args:
            limit: Max updates (1-1000, default 100)
            timeout: Long polling timeout (0-90, default 30)
            marker: Continue from this marker
            types: Filter by update types

        Returns:
            Dict with 'updates' list and 'marker'
        """
        params = {
            "limit": limit,
            "timeout": timeout
        }
        if marker:
            params["marker"] = marker
        if types:
            params["types"] = ",".join(types)

        return await self._request("GET", "/updates", params=params)

    # ========== Uploads ==========

    async def get_upload_url(self, type: str = "photo") -> dict:
        """Get upload URL for attachments.

        Args:
            type: photo, video, audio, file

        Returns:
            Dict with 'url' for uploading
        """
        return await self._request("POST", "/uploads", params={"type": type})

    # ========== Answers (for inline keyboards) ==========

    async def answer_callback(
        self,
        callback_id: str,
        message: dict = None,
        notification: str = None
    ) -> dict:
        """Answer to callback button click.

        Args:
            callback_id: Callback ID from update
            message: New message to edit
            notification: Toast notification text
        """
        body = {}
        if message:
            body["message"] = message
        if notification:
            body["notification"] = notification

        return await self._request(
            "POST", "/answers",
            params={"callback_id": callback_id},
            json_data=body
        )
