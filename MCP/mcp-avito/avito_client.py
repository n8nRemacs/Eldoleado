"""Avito Messenger API Client with connection pooling, rate limiting, and retry logic."""

import asyncio
import time
import logging
from typing import Any, Optional
from dataclasses import dataclass, field
from enum import Enum
from contextlib import asynccontextmanager

import httpx
import redis.asyncio as redis
from tenacity import (
    retry,
    stop_after_attempt,
    wait_exponential,
    retry_if_exception_type,
)

from config import settings

logger = logging.getLogger(__name__)


class MessageType(str, Enum):
    """Avito message types."""
    TEXT = "text"
    IMAGE = "image"
    VOICE = "voice"
    VIDEO = "video"
    FILE = "file"
    LINK = "link"
    CALL = "call"


@dataclass
class AvitoMessage:
    """Represents an Avito message."""
    id: str
    chat_id: str
    author_id: int
    type: MessageType
    created: int
    content: dict = field(default_factory=dict)
    direction: str = "in"
    read: bool = False

    @property
    def text(self) -> Optional[str]:
        return self.content.get("text")

    @property
    def url(self) -> Optional[str]:
        return self.content.get("url")

    def to_dict(self) -> dict:
        return {
            "id": self.id,
            "chat_id": self.chat_id,
            "author_id": self.author_id,
            "type": self.type.value,
            "created": self.created,
            "content": self.content,
            "direction": self.direction,
            "read": self.read,
            "text": self.text,
            "url": self.url,
        }


@dataclass
class AvitoChat:
    """Represents an Avito chat."""
    id: str
    user_id: int
    item_id: Optional[int] = None
    created: Optional[int] = None
    updated: Optional[int] = None
    context: dict = field(default_factory=dict)
    last_message: Optional[AvitoMessage] = None
    unread_count: int = 0

    def to_dict(self) -> dict:
        return {
            "id": self.id,
            "user_id": self.user_id,
            "item_id": self.item_id,
            "created": self.created,
            "updated": self.updated,
            "context": self.context,
            "unread_count": self.unread_count,
            "last_message": self.last_message.to_dict() if self.last_message else None,
        }


class RateLimitExceeded(Exception):
    """Raised when rate limit is exceeded."""
    pass


class AvitoAPIError(Exception):
    """Raised when Avito API returns an error."""
    def __init__(self, status_code: int, message: str, response: dict = None):
        self.status_code = status_code
        self.message = message
        self.response = response or {}
        super().__init__(f"Avito API Error {status_code}: {message}")


class RateLimiter:
    """Token bucket rate limiter using Redis."""

    def __init__(
        self,
        redis_client: redis.Redis,
        key: str = "avito_rate_limit",
        max_requests: int = 100,
        window_seconds: int = 60
    ):
        self.redis = redis_client
        self.key = key
        self.max_requests = max_requests
        self.window_seconds = window_seconds
        self._local_counter = 0
        self._local_reset_time = 0

    async def acquire(self) -> bool:
        """Try to acquire a rate limit token. Returns True if allowed."""
        now = time.time()

        # Fallback to local counter if Redis unavailable
        if not self.redis:
            if now > self._local_reset_time:
                self._local_counter = 0
                self._local_reset_time = now + self.window_seconds
            if self._local_counter < self.max_requests:
                self._local_counter += 1
                return True
            return False

        try:
            pipe = self.redis.pipeline()
            pipe.incr(self.key)
            pipe.expire(self.key, self.window_seconds)
            results = await pipe.execute()
            current = results[0]
            return current <= self.max_requests
        except Exception as e:
            logger.warning(f"Rate limiter Redis error: {e}, using local fallback")
            if now > self._local_reset_time:
                self._local_counter = 0
                self._local_reset_time = now + self.window_seconds
            if self._local_counter < self.max_requests:
                self._local_counter += 1
                return True
            return False

    async def wait_and_acquire(self, timeout: float = 30.0) -> bool:
        """Wait until rate limit token is available or timeout."""
        start = time.time()
        while time.time() - start < timeout:
            if await self.acquire():
                return True
            await asyncio.sleep(0.5)
        return False


class AvitoClient:
    """Async client for Avito Messenger API with pooling and rate limiting."""

    def __init__(
        self,
        client_id: str = None,
        client_secret: str = None,
        user_id: str = None,
        base_url: str = None,
        redis_url: str = None,
        redis_token_key: str = None,
        max_connections: int = None,
        connection_timeout: float = None,
        rate_limit_requests: int = None,
        rate_limit_window: int = None,
        max_retries: int = None,
        retry_min_wait: float = None,
        retry_max_wait: float = None,
    ):
        # Use settings as defaults
        self.client_id = client_id or settings.avito_client_id
        self.client_secret = client_secret or settings.avito_client_secret
        self.user_id = user_id or settings.avito_user_id
        self.base_url = base_url or settings.avito_api_base_url
        self.redis_url = redis_url or settings.redis_url
        self.redis_token_key = redis_token_key or settings.redis_token_key
        self.max_connections = max_connections or settings.max_connections
        self.connection_timeout = connection_timeout or settings.connection_timeout
        self.rate_limit_requests = rate_limit_requests or settings.rate_limit_requests
        self.rate_limit_window = rate_limit_window or settings.rate_limit_window
        self.max_retries = max_retries or settings.max_retries
        self.retry_min_wait = retry_min_wait or settings.retry_min_wait
        self.retry_max_wait = retry_max_wait or settings.retry_max_wait

        self._http_client: Optional[httpx.AsyncClient] = None
        self._redis_client: Optional[redis.Redis] = None
        self._rate_limiter: Optional[RateLimiter] = None
        self._access_token: Optional[str] = None
        self._token_expires_at: float = 0
        self._semaphore: Optional[asyncio.Semaphore] = None

    async def __aenter__(self):
        await self.connect()
        return self

    async def __aexit__(self, exc_type, exc_val, exc_tb):
        await self.close()

    async def connect(self):
        """Initialize connections with pooling."""
        # HTTP client with connection limits
        limits = httpx.Limits(
            max_connections=self.max_connections,
            max_keepalive_connections=self.max_connections // 2
        )
        self._http_client = httpx.AsyncClient(
            timeout=httpx.Timeout(self.connection_timeout),
            limits=limits,
            headers={"Content-Type": "application/json"}
        )

        # Semaphore for concurrent request limiting
        self._semaphore = asyncio.Semaphore(self.max_connections)

        # Redis connection
        try:
            self._redis_client = redis.from_url(
                self.redis_url,
                decode_responses=True
            )
            await self._redis_client.ping()
            logger.info("Redis connected successfully")
        except Exception as e:
            logger.warning(f"Redis connection failed: {e}, using local fallback")
            self._redis_client = None

        # Rate limiter - PER-TENANT to avoid global bottleneck
        # Each tenant gets their own rate limit bucket
        rate_limit_key = f"avito_rate_limit:{self.user_id}"
        self._rate_limiter = RateLimiter(
            self._redis_client,
            key=rate_limit_key,
            max_requests=self.rate_limit_requests,
            window_seconds=self.rate_limit_window
        )
        logger.info(f"Rate limiter initialized: {rate_limit_key} ({self.rate_limit_requests} req/{self.rate_limit_window}s)")

    async def close(self):
        """Close all connections."""
        if self._http_client:
            await self._http_client.aclose()
        if self._redis_client:
            await self._redis_client.aclose()

    # ========== Authentication ==========

    async def get_access_token(self, force_refresh: bool = False) -> str:
        """Get access token from Redis or request new one."""
        # Try Redis first
        if self._redis_client and not force_refresh:
            try:
                token = await self._redis_client.get(self.redis_token_key)
                if token:
                    self._access_token = token
                    return self._access_token
            except Exception as e:
                logger.warning(f"Redis get token error: {e}")

        # Check in-memory cache
        if not force_refresh and self._access_token and time.time() < self._token_expires_at:
            return self._access_token

        # Request new token
        return await self.refresh_token()

    async def refresh_token(self) -> str:
        """Request new access token from Avito."""
        response = await self._http_client.post(
            f"{self.base_url}/token",
            auth=(self.client_id, self.client_secret),
            data={"grant_type": "client_credentials"},
            headers={"Content-Type": "application/x-www-form-urlencoded"}
        )
        response.raise_for_status()

        data = response.json()
        self._access_token = data["access_token"]
        expires_in = data.get("expires_in", 86400)
        self._token_expires_at = time.time() + expires_in - 60

        # Save to Redis
        if self._redis_client:
            try:
                await self._redis_client.setex(
                    self.redis_token_key,
                    expires_in,
                    self._access_token
                )
                logger.info("Token saved to Redis")
            except Exception as e:
                logger.warning(f"Redis save token error: {e}")

        return self._access_token

    async def _request(
        self,
        method: str,
        endpoint: str,
        **kwargs
    ) -> dict:
        """Make authenticated request with rate limiting and retry."""
        # Wait for rate limit
        if not await self._rate_limiter.wait_and_acquire(timeout=30.0):
            raise RateLimitExceeded("Rate limit exceeded, try again later")

        async with self._semaphore:
            return await self._request_with_retry(method, endpoint, **kwargs)

    async def _request_with_retry(
        self,
        method: str,
        endpoint: str,
        _retry_count: int = 0,
        **kwargs
    ) -> dict:
        """Execute request with retry logic."""
        token = await self.get_access_token()
        headers = kwargs.pop("headers", {})
        headers["Authorization"] = f"Bearer {token}"

        url = f"{self.base_url}{endpoint}"

        try:
            response = await self._http_client.request(
                method,
                url,
                headers=headers,
                **kwargs
            )

            # Handle token expiration
            if response.status_code == 401:
                logger.info("Token expired, refreshing...")
                token = await self.refresh_token()
                headers["Authorization"] = f"Bearer {token}"
                response = await self._http_client.request(
                    method,
                    url,
                    headers=headers,
                    **kwargs
                )

            # Handle rate limiting from Avito
            if response.status_code == 429:
                if _retry_count < self.max_retries:
                    wait_time = min(
                        self.retry_min_wait * (2 ** _retry_count),
                        self.retry_max_wait
                    )
                    logger.warning(f"Avito rate limit hit, waiting {wait_time}s...")
                    await asyncio.sleep(wait_time)
                    return await self._request_with_retry(
                        method, endpoint,
                        _retry_count=_retry_count + 1,
                        headers=headers,
                        **kwargs
                    )
                raise RateLimitExceeded("Avito API rate limit exceeded")

            # Handle server errors with retry
            if response.status_code >= 500:
                if _retry_count < self.max_retries:
                    wait_time = min(
                        self.retry_min_wait * (2 ** _retry_count),
                        self.retry_max_wait
                    )
                    logger.warning(f"Avito server error {response.status_code}, retrying in {wait_time}s...")
                    await asyncio.sleep(wait_time)
                    return await self._request_with_retry(
                        method, endpoint,
                        _retry_count=_retry_count + 1,
                        headers=headers,
                        **kwargs
                    )

            response.raise_for_status()
            return response.json()

        except httpx.HTTPStatusError as e:
            error_body = {}
            try:
                error_body = e.response.json()
            except Exception:
                pass
            raise AvitoAPIError(
                e.response.status_code,
                str(e),
                error_body
            )
        except httpx.RequestError as e:
            if _retry_count < self.max_retries:
                wait_time = min(
                    self.retry_min_wait * (2 ** _retry_count),
                    self.retry_max_wait
                )
                logger.warning(f"Request error: {e}, retrying in {wait_time}s...")
                await asyncio.sleep(wait_time)
                return await self._request_with_retry(
                    method, endpoint,
                    _retry_count=_retry_count + 1,
                    **kwargs
                )
            raise

    # ========== Chats ==========

    async def get_chats(
        self,
        item_ids: Optional[list[int]] = None,
        unread_only: bool = False,
        limit: int = 100,
        offset: int = 0
    ) -> list[AvitoChat]:
        """Get list of chats."""
        params = {
            "limit": min(limit, 100),
            "offset": offset
        }
        if item_ids:
            params["item_ids"] = ",".join(map(str, item_ids))
        if unread_only:
            params["unread_only"] = "true"

        data = await self._request(
            "GET",
            f"/messenger/v1/accounts/{self.user_id}/chats",
            params=params
        )

        chats = []
        for chat_data in data.get("chats", []):
            chat = AvitoChat(
                id=chat_data["id"],
                user_id=chat_data.get("user_id", 0),
                item_id=chat_data.get("item_id"),
                created=chat_data.get("created"),
                updated=chat_data.get("updated"),
                context=chat_data.get("context", {}),
                unread_count=chat_data.get("unread_count", 0)
            )

            if "last_message" in chat_data:
                lm = chat_data["last_message"]
                chat.last_message = AvitoMessage(
                    id=lm["id"],
                    chat_id=chat_data["id"],
                    author_id=lm.get("author_id", 0),
                    type=MessageType(lm.get("type", "text")),
                    created=lm.get("created", 0),
                    content=lm.get("content", {}),
                    direction=lm.get("direction", "in"),
                    read=lm.get("read", False)
                )
            chats.append(chat)

        return chats

    async def get_chat(self, chat_id: str) -> AvitoChat:
        """Get chat by ID."""
        data = await self._request(
            "GET",
            f"/messenger/v1/accounts/{self.user_id}/chats/{chat_id}"
        )

        chat_data = data.get("chat", data)
        return AvitoChat(
            id=chat_data["id"],
            user_id=chat_data.get("user_id", 0),
            item_id=chat_data.get("item_id"),
            created=chat_data.get("created"),
            updated=chat_data.get("updated"),
            context=chat_data.get("context", {}),
            unread_count=chat_data.get("unread_count", 0)
        )

    async def mark_chat_read(self, chat_id: str) -> bool:
        """Mark chat as read."""
        await self._request(
            "POST",
            f"/messenger/v1/accounts/{self.user_id}/chats/{chat_id}/read"
        )
        return True

    # ========== Messages ==========

    async def get_messages(
        self,
        chat_id: str,
        limit: int = 100,
        offset: int = 0
    ) -> list[AvitoMessage]:
        """Get messages from chat (v1)."""
        data = await self._request(
            "GET",
            f"/messenger/v1/accounts/{self.user_id}/chats/{chat_id}/messages/",
            params={"limit": min(limit, 100), "offset": offset}
        )

        messages = []
        for msg_data in data.get("messages", []):
            messages.append(AvitoMessage(
                id=msg_data["id"],
                chat_id=chat_id,
                author_id=msg_data.get("author_id", 0),
                type=MessageType(msg_data.get("type", "text")),
                created=msg_data.get("created", 0),
                content=msg_data.get("content", {}),
                direction=msg_data.get("direction", "in"),
                read=msg_data.get("read", False)
            ))

        return messages

    async def get_messages_v2(
        self,
        chat_id: str,
        limit: int = 100,
        offset: int = 0
    ) -> list[AvitoMessage]:
        """Get messages from chat (v2)."""
        data = await self._request(
            "GET",
            f"/messenger/v2/accounts/{self.user_id}/chats/{chat_id}/messages/",
            params={"limit": min(limit, 100), "offset": offset}
        )

        messages = []
        for msg_data in data.get("messages", []):
            messages.append(AvitoMessage(
                id=msg_data["id"],
                chat_id=chat_id,
                author_id=msg_data.get("author_id", 0),
                type=MessageType(msg_data.get("type", "text")),
                created=msg_data.get("created", 0),
                content=msg_data.get("content", {}),
                direction=msg_data.get("direction", "in"),
                read=msg_data.get("read", False)
            ))

        return messages

    async def send_message(
        self,
        chat_id: str,
        text: str,
        message_type: MessageType = MessageType.TEXT
    ) -> dict:
        """Send text message to chat."""
        payload = {
            "message": {
                "type": message_type.value,
                "text": text
            }
        }

        return await self._request(
            "POST",
            f"/messenger/v3/accounts/{self.user_id}/chats/{chat_id}/messages",
            json=payload
        )

    async def send_image(
        self,
        chat_id: str,
        image_url: str
    ) -> dict:
        """Send image message."""
        payload = {
            "message": {
                "type": "image",
                "image": {"url": image_url}
            }
        }

        return await self._request(
            "POST",
            f"/messenger/v3/accounts/{self.user_id}/chats/{chat_id}/messages",
            json=payload
        )

    async def send_link(
        self,
        chat_id: str,
        url: str,
        text: Optional[str] = None
    ) -> dict:
        """Send link message."""
        payload = {
            "message": {
                "type": "link",
                "link": {"url": url}
            }
        }
        if text:
            payload["message"]["text"] = text

        return await self._request(
            "POST",
            f"/messenger/v3/accounts/{self.user_id}/chats/{chat_id}/messages",
            json=payload
        )

    async def delete_message(
        self,
        chat_id: str,
        message_id: str
    ) -> bool:
        """Delete message from chat."""
        await self._request(
            "POST",
            f"/messenger/v1/accounts/{self.user_id}/chats/{chat_id}/messages/{message_id}"
        )
        return True

    # ========== Blacklist ==========

    async def add_to_blacklist(self, blocked_user_id: int) -> bool:
        """Add user to blacklist."""
        await self._request(
            "POST",
            f"/messenger/v1/accounts/{self.user_id}/blacklist",
            json={"user_id": blocked_user_id}
        )
        return True

    # ========== Webhooks ==========

    async def subscribe_webhook(
        self,
        url: str,
        version: int = 2
    ) -> dict:
        """Subscribe to webhook notifications."""
        endpoint = "/messenger/v1/webhook" if version == 1 else "/messenger/v2/webhook"

        return await self._request(
            "POST",
            endpoint,
            json={"url": url}
        )

    async def unsubscribe_webhook(self) -> bool:
        """Unsubscribe from webhook notifications."""
        await self._request(
            "POST",
            "/messenger/v1/webhook/unsubscribe"
        )
        return True

    # ========== Batch Operations ==========

    async def get_all_unread_messages(self) -> list[dict]:
        """Get all unread messages from all chats."""
        chats = await self.get_chats(unread_only=True)

        async def fetch_chat_messages(chat: AvitoChat):
            messages = await self.get_messages(chat.id, limit=chat.unread_count or 10)
            return {
                "chat": chat.to_dict(),
                "messages": [m.to_dict() for m in messages]
            }

        tasks = [fetch_chat_messages(chat) for chat in chats]
        results = await asyncio.gather(*tasks, return_exceptions=True)

        # Filter out errors
        return [r for r in results if isinstance(r, dict)]

    async def send_bulk_messages(
        self,
        messages: list[dict]
    ) -> list[dict]:
        """Send multiple messages in parallel.

        Args:
            messages: List of dicts with 'chat_id' and 'text' keys
        """
        async def send_one(msg: dict):
            try:
                result = await self.send_message(
                    chat_id=msg["chat_id"],
                    text=msg["text"]
                )
                return {"success": True, "chat_id": msg["chat_id"], "result": result}
            except Exception as e:
                return {"success": False, "chat_id": msg["chat_id"], "error": str(e)}

        tasks = [send_one(msg) for msg in messages]
        return await asyncio.gather(*tasks)
