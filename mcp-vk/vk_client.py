"""VK API Client (Direct).

Client for interacting with VK API directly for community messages.
Documentation: https://dev.vk.com/ru/api/bots/getting-started
"""

import logging
from typing import Optional, List
from dataclasses import dataclass
import random
import httpx

logger = logging.getLogger(__name__)


class VKAPIError(Exception):
    """VK API error."""
    def __init__(self, message: str, error_code: int = 0, status_code: int = 500):
        self.message = message
        self.error_code = error_code
        self.status_code = status_code
        super().__init__(self.message)


@dataclass
class VKChat:
    """VK chat/conversation data."""
    peer_id: int
    local_id: int
    last_message_id: int
    unread_count: int
    can_write: bool
    chat_settings: dict = None

    def to_dict(self) -> dict:
        return {
            "peer_id": self.peer_id,
            "local_id": self.local_id,
            "last_message_id": self.last_message_id,
            "unread_count": self.unread_count,
            "can_write": self.can_write,
            "chat_settings": self.chat_settings
        }


@dataclass
class VKMessage:
    """VK message data."""
    id: int
    from_id: int
    peer_id: int
    text: str
    date: int
    attachments: List[dict] = None

    def to_dict(self) -> dict:
        return {
            "id": self.id,
            "from_id": self.from_id,
            "peer_id": self.peer_id,
            "text": self.text,
            "date": self.date,
            "attachments": self.attachments or []
        }


class VKClient:
    """VK API Client for community bots (direct API)."""

    API_VERSION = "5.199"
    BASE_URL = "https://api.vk.com/method"

    def __init__(self, access_token: str, group_id: int):
        """Initialize VK client.

        Args:
            access_token: VK Community access token with messages permission
            group_id: VK Community ID (numeric)
        """
        self.access_token = access_token
        self.group_id = group_id
        self._client: Optional[httpx.AsyncClient] = None

    async def connect(self):
        """Initialize HTTP client."""
        self._client = httpx.AsyncClient(timeout=30.0)
        logger.info(f"VK client connected for group {self.group_id}")

    async def close(self):
        """Close HTTP client."""
        if self._client:
            await self._client.aclose()
            logger.info("VK client closed")

    async def _request(self, method: str, **params) -> dict:
        """Make API request to VK.

        Args:
            method: VK API method name (e.g., 'messages.send')
            **params: Method parameters

        Returns:
            Response data from 'response' field
        """
        if not self._client:
            await self.connect()

        url = f"{self.BASE_URL}/{method}"

        # Add required params
        params["access_token"] = self.access_token
        params["v"] = self.API_VERSION

        try:
            response = await self._client.post(url, data=params)
            data = response.json()

            if "error" in data:
                error = data["error"]
                logger.error(f"VK API error: {error}")
                raise VKAPIError(
                    message=error.get("error_msg", "Unknown error"),
                    error_code=error.get("error_code", 0)
                )

            return data.get("response", data)

        except httpx.RequestError as e:
            logger.error(f"VK request error: {e}")
            raise VKAPIError(f"Request failed: {e}")

    def _random_id(self) -> int:
        """Generate random_id for messages (required by VK)."""
        return random.randint(1, 2147483647)

    # ========== Community Info ==========

    async def get_group_info(self) -> dict:
        """Get community info."""
        result = await self._request(
            "groups.getById",
            group_id=self.group_id
        )
        return result["groups"][0] if result.get("groups") else result[0] if isinstance(result, list) else result

    async def get_long_poll_server(self) -> dict:
        """Get Long Poll server info for receiving updates."""
        return await self._request(
            "groups.getLongPollServer",
            group_id=self.group_id
        )

    async def get_long_poll_settings(self) -> dict:
        """Get Long Poll settings."""
        return await self._request(
            "groups.getLongPollSettings",
            group_id=self.group_id
        )

    async def set_long_poll_settings(
        self,
        enabled: bool = True,
        message_new: bool = True,
        message_reply: bool = False,
        message_allow: bool = True,
        message_deny: bool = True
    ) -> int:
        """Set Long Poll settings."""
        return await self._request(
            "groups.setLongPollSettings",
            group_id=self.group_id,
            enabled=1 if enabled else 0,
            api_version=self.API_VERSION,
            message_new=1 if message_new else 0,
            message_reply=1 if message_reply else 0,
            message_allow=1 if message_allow else 0,
            message_deny=1 if message_deny else 0
        )

    # ========== Send Messages ==========

    async def send_message(
        self,
        peer_id: int,
        message: str = None,
        keyboard: dict = None,
        attachment: str = None,
        reply_to: int = None,
        forward_messages: str = None,
        sticker_id: int = None,
        template: dict = None
    ) -> int:
        """Send message to user/chat.

        Args:
            peer_id: User ID or chat ID (2000000000 + chat_id for group chats)
            message: Message text (optional if attachment/sticker provided)
            keyboard: VK keyboard JSON (optional)
            attachment: Attachment string like 'photo123_456' (optional)
            reply_to: Message ID to reply to (optional)
            forward_messages: Comma-separated message IDs to forward (optional)
            sticker_id: Sticker ID to send (optional)
            template: Message template (optional)

        Returns:
            Sent message ID
        """
        params = {
            "peer_id": peer_id,
            "random_id": self._random_id()
        }

        if message:
            params["message"] = message

        if keyboard:
            import json
            params["keyboard"] = json.dumps(keyboard, ensure_ascii=False)

        if attachment:
            params["attachment"] = attachment

        if reply_to:
            params["reply_to"] = reply_to

        if forward_messages:
            params["forward_messages"] = forward_messages

        if sticker_id:
            params["sticker_id"] = sticker_id

        if template:
            import json
            params["template"] = json.dumps(template, ensure_ascii=False)

        return await self._request("messages.send", **params)

    async def send_message_event_answer(
        self,
        event_id: str,
        user_id: int,
        peer_id: int,
        event_data: dict = None
    ) -> dict:
        """Send answer to message event (callback button click)."""
        import json
        params = {
            "event_id": event_id,
            "user_id": user_id,
            "peer_id": peer_id
        }
        if event_data:
            params["event_data"] = json.dumps(event_data, ensure_ascii=False)

        return await self._request("messages.sendMessageEventAnswer", **params)

    async def edit_message(
        self,
        peer_id: int,
        message_id: int,
        message: str = None,
        keyboard: dict = None,
        attachment: str = None
    ) -> int:
        """Edit sent message."""
        params = {
            "peer_id": peer_id,
            "message_id": message_id
        }

        if message:
            params["message"] = message

        if keyboard:
            import json
            params["keyboard"] = json.dumps(keyboard, ensure_ascii=False)

        if attachment:
            params["attachment"] = attachment

        return await self._request("messages.edit", **params)

    async def delete_message(
        self,
        message_ids: List[int],
        delete_for_all: bool = True
    ) -> dict:
        """Delete messages."""
        return await self._request(
            "messages.delete",
            message_ids=",".join(map(str, message_ids)),
            delete_for_all=1 if delete_for_all else 0,
            group_id=self.group_id
        )

    # ========== Conversations ==========

    async def get_conversations(
        self,
        count: int = 20,
        offset: int = 0,
        filter: str = "all",
        extended: bool = True
    ) -> dict:
        """Get conversations list.

        Args:
            count: Number of conversations (max 200)
            offset: Offset for pagination
            filter: all, unread, important, unanswered
            extended: Include user/group profiles

        Returns:
            Dict with 'items', 'count', and optionally 'profiles', 'groups'
        """
        return await self._request(
            "messages.getConversations",
            count=count,
            offset=offset,
            filter=filter,
            extended=1 if extended else 0,
            group_id=self.group_id
        )

    async def get_conversations_by_id(self, peer_ids: List[int], extended: bool = True) -> dict:
        """Get conversations by peer IDs."""
        return await self._request(
            "messages.getConversationsById",
            peer_ids=",".join(map(str, peer_ids)),
            extended=1 if extended else 0,
            group_id=self.group_id
        )

    async def get_history(
        self,
        peer_id: int,
        count: int = 20,
        offset: int = 0,
        start_message_id: int = None,
        rev: bool = False,
        extended: bool = True
    ) -> dict:
        """Get message history from conversation.

        Args:
            peer_id: Peer ID
            count: Number of messages (max 200)
            offset: Offset
            start_message_id: Start from this message
            rev: Reverse order (oldest first)
            extended: Include profiles

        Returns:
            Dict with 'items', 'count', and optionally 'profiles'
        """
        params = {
            "peer_id": peer_id,
            "count": count,
            "offset": offset,
            "extended": 1 if extended else 0,
            "group_id": self.group_id
        }

        if start_message_id:
            params["start_message_id"] = start_message_id

        if rev:
            params["rev"] = 1

        return await self._request("messages.getHistory", **params)

    async def mark_as_read(self, peer_id: int, start_message_id: int = None) -> int:
        """Mark messages as read."""
        params = {
            "peer_id": peer_id,
            "group_id": self.group_id
        }
        if start_message_id:
            params["start_message_id"] = start_message_id

        return await self._request("messages.markAsRead", **params)

    # ========== Users ==========

    async def get_users(
        self,
        user_ids: List[int],
        fields: str = "photo_200,city,bdate,contacts"
    ) -> List[dict]:
        """Get user info by IDs."""
        result = await self._request(
            "users.get",
            user_ids=",".join(map(str, user_ids)),
            fields=fields
        )
        return result if isinstance(result, list) else []

    # ========== Uploads ==========

    async def get_messages_upload_server(self, peer_id: int, type: str = "doc") -> dict:
        """Get upload server for message attachments.

        Args:
            type: doc, audio_message
        """
        return await self._request(
            "docs.getMessagesUploadServer",
            peer_id=peer_id,
            type=type,
            group_id=self.group_id
        )

    async def get_photo_upload_server(self, peer_id: int) -> dict:
        """Get upload server for photos."""
        return await self._request(
            "photos.getMessagesUploadServer",
            peer_id=peer_id,
            group_id=self.group_id
        )

    async def save_doc(self, file: str, title: str = None) -> dict:
        """Save uploaded document."""
        params = {"file": file}
        if title:
            params["title"] = title
        return await self._request("docs.save", **params)

    async def save_messages_photo(self, photo: str, server: int, hash: str) -> List[dict]:
        """Save uploaded photo for messages."""
        return await self._request(
            "photos.saveMessagesPhoto",
            photo=photo,
            server=server,
            hash=hash
        )

    # ========== Callback API ==========

    async def get_callback_servers(self) -> dict:
        """Get callback servers list."""
        return await self._request(
            "groups.getCallbackServers",
            group_id=self.group_id
        )

    async def add_callback_server(
        self,
        url: str,
        title: str,
        secret_key: str = None
    ) -> dict:
        """Add callback server for webhooks.

        Args:
            url: Webhook URL (must respond to confirmation request)
            title: Server title
            secret_key: Secret key for request verification (optional)

        Returns:
            Dict with 'server_id'
        """
        params = {
            "group_id": self.group_id,
            "url": url,
            "title": title
        }
        if secret_key:
            params["secret_key"] = secret_key

        return await self._request("groups.addCallbackServer", **params)

    async def delete_callback_server(self, server_id: int) -> int:
        """Delete callback server."""
        return await self._request(
            "groups.deleteCallbackServer",
            group_id=self.group_id,
            server_id=server_id
        )

    async def set_callback_settings(
        self,
        server_id: int,
        message_new: bool = True,
        message_reply: bool = False,
        message_allow: bool = True,
        message_deny: bool = True,
        message_typing_state: bool = False,
        message_event: bool = True
    ) -> int:
        """Set callback events for server."""
        return await self._request(
            "groups.setCallbackSettings",
            group_id=self.group_id,
            server_id=server_id,
            api_version=self.API_VERSION,
            message_new=1 if message_new else 0,
            message_reply=1 if message_reply else 0,
            message_allow=1 if message_allow else 0,
            message_deny=1 if message_deny else 0,
            message_typing_state=1 if message_typing_state else 0,
            message_event=1 if message_event else 0
        )

    async def get_callback_confirmation_code(self) -> str:
        """Get confirmation code for callback server verification."""
        result = await self._request(
            "groups.getCallbackConfirmationCode",
            group_id=self.group_id
        )
        return result.get("code", "")
