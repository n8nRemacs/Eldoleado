"""Instagram Graph API Client.

Client for interacting with Instagram Graph API for business accounts.
Documentation: https://developers.facebook.com/docs/instagram-api
Messaging API: https://developers.facebook.com/docs/instagram-api/guides/messaging
"""

import logging
from typing import Optional, List, Dict, Any
from dataclasses import dataclass
import httpx

logger = logging.getLogger(__name__)


class InstagramAPIError(Exception):
    """Instagram API error."""
    def __init__(self, message: str, code: str = "", status_code: int = 500):
        self.message = message
        self.code = code
        self.status_code = status_code
        super().__init__(self.message)


@dataclass
class InstagramMessage:
    """Instagram message data."""
    message_id: str
    sender_id: str
    recipient_id: str
    text: Optional[str] = None
    attachments: Optional[List[dict]] = None
    timestamp: Optional[int] = None
    is_echo: bool = False

    def to_dict(self) -> dict:
        return {
            "message_id": self.message_id,
            "sender_id": self.sender_id,
            "recipient_id": self.recipient_id,
            "text": self.text,
            "attachments": self.attachments or [],
            "timestamp": self.timestamp,
            "is_echo": self.is_echo
        }


class InstagramClient:
    """Instagram Graph API Client for business messaging.

    Uses Instagram Graph API via Facebook Graph API.
    Requires:
    - Facebook App with Instagram Basic Display or Messenger Platform
    - Instagram Business/Creator Account linked to Facebook Page
    - Page Access Token with instagram_basic, instagram_manage_messages permissions
    """

    GRAPH_API_VERSION = "v18.0"
    BASE_URL = f"https://graph.facebook.com/{GRAPH_API_VERSION}"

    def __init__(self, access_token: str, instagram_account_id: str):
        """Initialize Instagram client.

        Args:
            access_token: Page Access Token with Instagram permissions
            instagram_account_id: Instagram Business Account ID (IGBA ID)
        """
        self.access_token = access_token
        self.instagram_account_id = instagram_account_id
        self._client: Optional[httpx.AsyncClient] = None

    async def connect(self):
        """Initialize HTTP client."""
        self._client = httpx.AsyncClient(timeout=30.0)
        logger.info(f"Instagram client connected for account {self.instagram_account_id}")

    async def close(self):
        """Close HTTP client."""
        if self._client:
            await self._client.aclose()
            logger.info("Instagram client closed")

    async def _request(
        self,
        method: str,
        endpoint: str,
        params: dict = None,
        json_data: dict = None
    ) -> dict:
        """Make API request to Instagram Graph API.

        Args:
            method: HTTP method
            endpoint: API endpoint
            params: Query parameters
            json_data: JSON body for POST

        Returns:
            Response data
        """
        if not self._client:
            await self.connect()

        url = f"{self.BASE_URL}/{endpoint}"

        # Add access token to params
        if params is None:
            params = {}
        params["access_token"] = self.access_token

        try:
            if method.upper() == "GET":
                response = await self._client.get(url, params=params)
            elif method.upper() == "POST":
                response = await self._client.post(url, params=params, json=json_data)
            elif method.upper() == "DELETE":
                response = await self._client.delete(url, params=params)
            else:
                raise ValueError(f"Unsupported method: {method}")

            data = response.json() if response.text else {}

            if response.status_code >= 400 or "error" in data:
                error = data.get("error", {})
                raise InstagramAPIError(
                    message=error.get("message", f"HTTP {response.status_code}"),
                    code=str(error.get("code", "")),
                    status_code=response.status_code
                )

            return data

        except httpx.RequestError as e:
            logger.error(f"Instagram request error: {e}")
            raise InstagramAPIError(f"Request failed: {e}")

    # ========== Account Info ==========

    async def get_account_info(self) -> dict:
        """Get Instagram account information."""
        return await self._request(
            "GET",
            self.instagram_account_id,
            params={
                "fields": "id,username,name,profile_picture_url,followers_count,follows_count,media_count"
            }
        )

    async def get_business_discovery(self, username: str) -> dict:
        """Get public info about another Instagram account."""
        return await self._request(
            "GET",
            self.instagram_account_id,
            params={
                "fields": f"business_discovery.username({username}){{id,username,name,profile_picture_url,followers_count,media_count}}"
            }
        )

    # ========== Conversations (Messaging) ==========

    async def get_conversations(self, limit: int = 20) -> dict:
        """Get list of conversations.

        Note: Requires instagram_manage_messages permission
        """
        return await self._request(
            "GET",
            f"{self.instagram_account_id}/conversations",
            params={
                "platform": "instagram",
                "fields": "participants,updated_time,messages{message,from,to,created_time}",
                "limit": limit
            }
        )

    async def get_conversation_messages(
        self,
        conversation_id: str,
        limit: int = 20
    ) -> dict:
        """Get messages from a conversation."""
        return await self._request(
            "GET",
            f"{conversation_id}/messages",
            params={
                "fields": "id,message,from,to,created_time,attachments",
                "limit": limit
            }
        )

    # ========== Send Messages ==========

    async def send_message(
        self,
        recipient_id: str,
        text: str = None,
        attachment: dict = None
    ) -> dict:
        """Send message to Instagram user.

        Args:
            recipient_id: Instagram-scoped ID of recipient (IGSID)
            text: Message text
            attachment: Attachment object (image, video, etc.)

        Returns:
            Message send result with message_id
        """
        message = {}
        if text:
            message["text"] = text
        if attachment:
            message["attachment"] = attachment

        return await self._request(
            "POST",
            f"{self.instagram_account_id}/messages",
            json_data={
                "recipient": {"id": recipient_id},
                "message": message
            }
        )

    async def send_image(
        self,
        recipient_id: str,
        image_url: str
    ) -> dict:
        """Send image message."""
        return await self.send_message(
            recipient_id=recipient_id,
            attachment={
                "type": "image",
                "payload": {"url": image_url}
            }
        )

    async def send_video(
        self,
        recipient_id: str,
        video_url: str
    ) -> dict:
        """Send video message."""
        return await self.send_message(
            recipient_id=recipient_id,
            attachment={
                "type": "video",
                "payload": {"url": video_url}
            }
        )

    async def send_audio(
        self,
        recipient_id: str,
        audio_url: str
    ) -> dict:
        """Send audio message."""
        return await self.send_message(
            recipient_id=recipient_id,
            attachment={
                "type": "audio",
                "payload": {"url": audio_url}
            }
        )

    async def send_file(
        self,
        recipient_id: str,
        file_url: str
    ) -> dict:
        """Send file message."""
        return await self.send_message(
            recipient_id=recipient_id,
            attachment={
                "type": "file",
                "payload": {"url": file_url}
            }
        )

    async def send_quick_replies(
        self,
        recipient_id: str,
        text: str,
        quick_replies: List[dict]
    ) -> dict:
        """Send message with quick reply buttons.

        Args:
            quick_replies: List of quick reply objects
                [{"content_type": "text", "title": "Option 1", "payload": "opt1"}]
        """
        return await self._request(
            "POST",
            f"{self.instagram_account_id}/messages",
            json_data={
                "recipient": {"id": recipient_id},
                "message": {
                    "text": text,
                    "quick_replies": quick_replies
                }
            }
        )

    async def send_generic_template(
        self,
        recipient_id: str,
        elements: List[dict]
    ) -> dict:
        """Send generic template (carousel).

        Args:
            elements: List of template elements
                [{"title": "...", "subtitle": "...", "image_url": "...", "buttons": [...]}]
        """
        return await self._request(
            "POST",
            f"{self.instagram_account_id}/messages",
            json_data={
                "recipient": {"id": recipient_id},
                "message": {
                    "attachment": {
                        "type": "template",
                        "payload": {
                            "template_type": "generic",
                            "elements": elements
                        }
                    }
                }
            }
        )

    # ========== Ice Breakers (Conversation Starters) ==========

    async def set_ice_breakers(self, ice_breakers: List[dict]) -> dict:
        """Set conversation ice breakers.

        Args:
            ice_breakers: List of ice breaker questions
                [{"question": "How can I help?", "payload": "HELP"}]
        """
        return await self._request(
            "POST",
            f"{self.instagram_account_id}/messenger_profile",
            json_data={
                "ice_breakers": ice_breakers
            }
        )

    async def get_ice_breakers(self) -> dict:
        """Get current ice breakers."""
        return await self._request(
            "GET",
            f"{self.instagram_account_id}/messenger_profile",
            params={"fields": "ice_breakers"}
        )

    async def delete_ice_breakers(self) -> dict:
        """Delete ice breakers."""
        return await self._request(
            "DELETE",
            f"{self.instagram_account_id}/messenger_profile",
            params={"fields": "ice_breakers"}
        )

    # ========== User Info ==========

    async def get_user_info(self, user_id: str) -> dict:
        """Get user info by Instagram-scoped ID."""
        return await self._request(
            "GET",
            user_id,
            params={"fields": "id,username,name,profile_pic"}
        )

    # ========== Media (Posts) ==========

    async def get_media(self, limit: int = 25) -> dict:
        """Get account media (posts)."""
        return await self._request(
            "GET",
            f"{self.instagram_account_id}/media",
            params={
                "fields": "id,caption,media_type,media_url,permalink,thumbnail_url,timestamp,username",
                "limit": limit
            }
        )

    async def get_media_item(self, media_id: str) -> dict:
        """Get specific media item."""
        return await self._request(
            "GET",
            media_id,
            params={
                "fields": "id,caption,media_type,media_url,permalink,timestamp,username,comments_count,like_count"
            }
        )

    # ========== Comments ==========

    async def get_media_comments(self, media_id: str, limit: int = 50) -> dict:
        """Get comments on a media item."""
        return await self._request(
            "GET",
            f"{media_id}/comments",
            params={
                "fields": "id,text,username,timestamp,like_count,replies{id,text,username,timestamp}",
                "limit": limit
            }
        )

    async def reply_to_comment(self, comment_id: str, message: str) -> dict:
        """Reply to a comment."""
        return await self._request(
            "POST",
            f"{comment_id}/replies",
            json_data={"message": message}
        )

    async def hide_comment(self, comment_id: str, hide: bool = True) -> dict:
        """Hide or unhide a comment."""
        return await self._request(
            "POST",
            comment_id,
            json_data={"hide": hide}
        )

    async def delete_comment(self, comment_id: str) -> dict:
        """Delete a comment."""
        return await self._request("DELETE", comment_id)

    # ========== Mentions & Tags ==========

    async def get_mentions(self, limit: int = 20) -> dict:
        """Get media where account is mentioned."""
        return await self._request(
            "GET",
            f"{self.instagram_account_id}/tags",
            params={
                "fields": "id,caption,media_type,media_url,permalink,timestamp,username",
                "limit": limit
            }
        )

    # ========== Stories ==========

    async def get_stories(self) -> dict:
        """Get account stories."""
        return await self._request(
            "GET",
            f"{self.instagram_account_id}/stories",
            params={
                "fields": "id,media_type,media_url,permalink,timestamp"
            }
        )

    # ========== Insights ==========

    async def get_insights(
        self,
        metrics: List[str] = None,
        period: str = "day"
    ) -> dict:
        """Get account insights.

        Args:
            metrics: List of metrics (impressions, reach, follower_count, etc.)
            period: day, week, days_28, month, lifetime
        """
        if metrics is None:
            metrics = ["impressions", "reach", "profile_views"]

        return await self._request(
            "GET",
            f"{self.instagram_account_id}/insights",
            params={
                "metric": ",".join(metrics),
                "period": period
            }
        )
