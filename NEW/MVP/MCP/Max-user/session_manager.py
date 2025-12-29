"""
Session Manager for MAX User API
Handles multiple MAX user sessions with Redis cache and PostgreSQL persistence.
Similar to mcp-whatsapp-baileys/session.ts
"""

import asyncio
import hashlib
import logging
import uuid
from typing import Optional, Dict, Callable, Awaitable, Any
from datetime import datetime

import redis.asyncio as redis
import asyncpg
import httpx

from max_user_client import MaxUserClient, MaxUserAPIError
from humanized_client import HumanizedMaxUserClient

logger = logging.getLogger(__name__)


class SessionStatus:
    CONNECTING = "connecting"
    SMS_SENT = "sms_sent"
    CONNECTED = "connected"
    DISCONNECTED = "disconnected"
    ERROR = "error"


class SessionInfo:
    """Session information structure."""

    def __init__(
        self,
        session_id: str,
        phone: str,
        status: str = SessionStatus.CONNECTING,
        hash: str = "",
        user_id: int = 0,
        user_name: str = "",
        webhook_url: str = "",
        tenant_id: int = 0,
        created_at: datetime = None,
        updated_at: datetime = None,
    ):
        self.session_id = session_id
        self.phone = phone
        self.status = status
        self.hash = hash or self._generate_hash(session_id)
        self.user_id = user_id
        self.user_name = user_name
        self.webhook_url = webhook_url
        self.tenant_id = tenant_id
        self.created_at = created_at or datetime.utcnow()
        self.updated_at = updated_at or datetime.utcnow()

    @staticmethod
    def _generate_hash(session_id: str) -> str:
        return hashlib.sha256(session_id.encode()).hexdigest()[:16]

    def to_dict(self) -> dict:
        return {
            "session_id": self.session_id,
            "phone": self.phone,
            "status": self.status,
            "hash": self.hash,
            "user_id": self.user_id,
            "user_name": self.user_name,
            "webhook_url": self.webhook_url,
            "tenant_id": self.tenant_id,
            "created_at": self.created_at.isoformat() if self.created_at else None,
            "updated_at": self.updated_at.isoformat() if self.updated_at else None,
        }


class SessionManager:
    """
    Manages multiple MAX User sessions.

    Storage:
    - Redis: Session cache (status, hash, webhookUrl)
    - PostgreSQL: Persistent storage (login_token, device_id)
    - Memory: Active WebSocket clients
    """

    CHANNEL_ID = 6  # MAX User channel ID
    REDIS_KEY_PREFIX = "max_user:session:"
    REDIS_HASH_PREFIX = "max_user:hash:"
    REDIS_TTL = 86400 * 30  # 30 days

    def __init__(
        self,
        redis_url: str = None,
        database_url: str = None,
        ip_node_id: int = 1,
        default_webhook_url: str = None,
        humanized: bool = True,
    ):
        self.redis_url = redis_url
        self.database_url = database_url
        self.ip_node_id = ip_node_id
        self.default_webhook_url = default_webhook_url
        self.humanized = humanized

        # In-memory storage
        self.sessions: Dict[str, SessionInfo] = {}
        self.clients: Dict[str, HumanizedMaxUserClient] = {}
        self.sessions_by_hash: Dict[str, str] = {}  # hash -> session_id
        self.sms_tokens: Dict[str, str] = {}  # session_id -> sms_token

        # Connections
        self._redis: Optional[redis.Redis] = None
        self._pg: Optional[asyncpg.Pool] = None

    async def connect(self):
        """Initialize Redis and PostgreSQL connections."""
        if self.redis_url:
            self._redis = redis.from_url(self.redis_url, decode_responses=True)
            try:
                await self._redis.ping()
                logger.info("Redis connected")
            except Exception as e:
                logger.error(f"Redis connection failed: {e}")
                self._redis = None

        if self.database_url:
            try:
                self._pg = await asyncpg.create_pool(
                    self.database_url,
                    min_size=1,
                    max_size=5,
                    command_timeout=10,
                )
                logger.info("PostgreSQL connected")
            except Exception as e:
                logger.error(f"PostgreSQL connection failed: {e}")
                self._pg = None

    async def close(self):
        """Close all connections."""
        # Disconnect all clients
        for session_id, client in list(self.clients.items()):
            try:
                await client.close()
            except Exception as e:
                logger.error(f"Error closing client {session_id}: {e}")

        if self._redis:
            await self._redis.close()
            logger.info("Redis connection closed")

        if self._pg:
            await self._pg.close()
            logger.info("PostgreSQL connection closed")

    # ==================== Session Operations ====================

    async def create_session(
        self,
        phone: str,
        webhook_url: str = None,
        tenant_id: int = 0,
    ) -> SessionInfo:
        """
        Create new session and start SMS authentication.

        Args:
            phone: Phone number with country code (+79001234567)
            webhook_url: Webhook URL for incoming messages
            tenant_id: Tenant ID

        Returns:
            SessionInfo with session_id and hash
        """
        session_id = str(uuid.uuid4())
        webhook = webhook_url or self.default_webhook_url

        # Create session info
        session = SessionInfo(
            session_id=session_id,
            phone=phone,
            status=SessionStatus.CONNECTING,
            webhook_url=webhook,
            tenant_id=tenant_id,
        )

        # Create client
        client = HumanizedMaxUserClient(
            typing_indicator=self.humanized,
            typing_simulation=self.humanized,
            on_message=lambda msg: self._on_message(session_id, msg),
        )

        try:
            # Connect to MAX
            await client.connect()

            # Start SMS auth
            response = await client.start_auth(phone)
            sms_token = response.get("token")
            if not sms_token:
                raise MaxUserAPIError("No SMS token received")

            # Store SMS token
            self.sms_tokens[session_id] = sms_token
            session.status = SessionStatus.SMS_SENT

            # Store in memory
            self.sessions[session_id] = session
            self.clients[session_id] = client
            self.sessions_by_hash[session.hash] = session_id

            # Save to Redis
            await self._save_to_redis(session)

            logger.info(f"Session {session_id} created, SMS sent to {phone}")
            return session

        except Exception as e:
            await client.close()
            logger.error(f"Failed to create session: {e}")
            raise

    async def verify_code(
        self,
        session_id: str,
        code: str,
        password_2fa: str = None,
    ) -> SessionInfo:
        """
        Verify SMS code and complete authentication.

        Args:
            session_id: Session ID
            code: SMS code
            password_2fa: 2FA password if enabled

        Returns:
            Updated SessionInfo
        """
        session = self.sessions.get(session_id)
        if not session:
            raise ValueError(f"Session {session_id} not found")

        client = self.clients.get(session_id)
        if not client:
            raise ValueError(f"Client for session {session_id} not found")

        sms_token = self.sms_tokens.get(session_id)
        if not sms_token:
            raise ValueError(f"SMS token for session {session_id} not found")

        try:
            # Verify SMS code
            response = await client.verify_code(sms_token, code)

            # Check if 2FA required
            if response.get("require2FA"):
                if not password_2fa:
                    raise MaxUserAPIError("2FA password required", code="2FA_REQUIRED")
                response = await client.verify_2fa(sms_token, password_2fa)

            # Update session info
            profile = client.profile
            session.status = SessionStatus.CONNECTED
            session.user_id = profile.get("id", 0)
            session.user_name = profile.get("name", "")
            session.updated_at = datetime.utcnow()

            # Clean up SMS token
            del self.sms_tokens[session_id]

            # Save to Redis and PostgreSQL
            await self._save_to_redis(session)
            await self._save_to_postgres(session, client)

            logger.info(f"Session {session_id} verified: {session.user_name}")
            return session

        except Exception as e:
            session.status = SessionStatus.ERROR
            await self._save_to_redis(session)
            logger.error(f"Verification failed for {session_id}: {e}")
            raise

    async def reconnect_session(self, session_id: str) -> SessionInfo:
        """
        Reconnect session using stored token.

        Args:
            session_id: Session ID

        Returns:
            Updated SessionInfo
        """
        # Check if already connected
        if session_id in self.clients:
            session = self.sessions.get(session_id)
            if session and session.status == SessionStatus.CONNECTED:
                return session

        # Load credentials from PostgreSQL
        creds = await self._load_from_postgres(session_id)
        if not creds:
            raise ValueError(f"No credentials found for session {session_id}")

        login_token = creds.get("login_token")
        device_id = creds.get("device_id")
        if not login_token or not device_id:
            raise ValueError(f"Invalid credentials for session {session_id}")

        # Create client and connect
        client = HumanizedMaxUserClient(
            typing_indicator=self.humanized,
            typing_simulation=self.humanized,
            on_message=lambda msg: self._on_message(session_id, msg),
        )

        try:
            await client.connect()
            await client.login_by_token(login_token, device_id)

            # Create/update session info
            profile = client.profile
            session = SessionInfo(
                session_id=session_id,
                phone=creds.get("phone", ""),
                status=SessionStatus.CONNECTED,
                user_id=profile.get("id", 0),
                user_name=profile.get("name", ""),
                webhook_url=creds.get("webhook_url", self.default_webhook_url),
                tenant_id=creds.get("tenant_id", 0),
            )

            # Store in memory
            self.sessions[session_id] = session
            self.clients[session_id] = client
            self.sessions_by_hash[session.hash] = session_id

            # Update Redis
            await self._save_to_redis(session)

            logger.info(f"Session {session_id} reconnected: {session.user_name}")
            return session

        except Exception as e:
            await client.close()
            logger.error(f"Reconnect failed for {session_id}: {e}")
            raise

    async def delete_session(self, session_id: str):
        """
        Delete session and logout.

        Args:
            session_id: Session ID
        """
        # Close client
        client = self.clients.pop(session_id, None)
        if client:
            try:
                await client.close()
            except Exception as e:
                logger.error(f"Error closing client {session_id}: {e}")

        # Remove from memory
        session = self.sessions.pop(session_id, None)
        if session:
            self.sessions_by_hash.pop(session.hash, None)

        self.sms_tokens.pop(session_id, None)

        # Remove from Redis
        await self._delete_from_redis(session_id)

        # Update PostgreSQL (set disconnected, keep credentials)
        await self._update_postgres_status(session_id, SessionStatus.DISCONNECTED)

        logger.info(f"Session {session_id} deleted")

    async def list_sessions(self) -> list:
        """List all sessions."""
        return [s.to_dict() for s in self.sessions.values()]

    def get_session(self, session_id: str) -> Optional[SessionInfo]:
        """Get session by ID."""
        return self.sessions.get(session_id)

    def get_session_by_hash(self, hash: str) -> Optional[SessionInfo]:
        """Get session by hash."""
        session_id = self.sessions_by_hash.get(hash)
        if session_id:
            return self.sessions.get(session_id)
        return None

    def get_client(self, session_id: str) -> Optional[HumanizedMaxUserClient]:
        """Get client by session ID."""
        return self.clients.get(session_id)

    # ==================== Message Operations ====================

    async def send_message(
        self,
        session_id: str,
        chat_id: int,
        text: str,
        reply_to: str = None,
        skip_delay: bool = False,
    ) -> dict:
        """
        Send message via session.

        Args:
            session_id: Session ID
            chat_id: Chat ID
            text: Message text
            reply_to: Message ID to reply to
            skip_delay: Skip humanized delay

        Returns:
            Sent message info
        """
        client = self.clients.get(session_id)
        if not client:
            raise ValueError(f"Client for session {session_id} not found")

        return await client.send_message_humanized(
            chat_id=chat_id,
            text=text,
            reply_to=reply_to,
            skip_delay=skip_delay,
        )

    async def _on_message(self, session_id: str, payload: dict):
        """Handle incoming message from MAX."""
        session = self.sessions.get(session_id)
        if not session or not session.webhook_url:
            return

        # Normalize message
        normalized = {
            "channel": "max_user",
            "session_id": session_id,
            "session_hash": session.hash,
            "phone": session.phone,
            "chat_id": payload.get("chatId"),
            "message_id": payload.get("message", {}).get("id"),
            "user_id": payload.get("sender", {}).get("id"),
            "user_name": payload.get("sender", {}).get("name"),
            "text": payload.get("message", {}).get("text"),
            "timestamp": datetime.utcnow().isoformat(),
            "raw_data": payload,
        }

        # Forward to webhook
        try:
            async with httpx.AsyncClient(timeout=30.0) as http:
                await http.post(session.webhook_url, json=normalized)
            logger.info(f"Message forwarded to webhook: {session.webhook_url}")
        except Exception as e:
            logger.error(f"Failed to forward message: {e}")

    # ==================== Storage Operations ====================

    async def _save_to_redis(self, session: SessionInfo):
        """Save session to Redis cache."""
        if not self._redis:
            return

        try:
            key = f"{self.REDIS_KEY_PREFIX}{session.session_id}"
            await self._redis.set(
                key,
                str(session.to_dict()),
                ex=self.REDIS_TTL,
            )
            # Save hash mapping
            hash_key = f"{self.REDIS_HASH_PREFIX}{session.hash}"
            await self._redis.set(hash_key, session.session_id, ex=self.REDIS_TTL)
        except Exception as e:
            logger.error(f"Failed to save to Redis: {e}")

    async def _delete_from_redis(self, session_id: str):
        """Delete session from Redis."""
        if not self._redis:
            return

        try:
            session = self.sessions.get(session_id)
            hash_val = session.hash if session else self._generate_hash(session_id)

            key = f"{self.REDIS_KEY_PREFIX}{session_id}"
            hash_key = f"{self.REDIS_HASH_PREFIX}{hash_val}"

            await self._redis.delete(key, hash_key)
        except Exception as e:
            logger.error(f"Failed to delete from Redis: {e}")

    @staticmethod
    def _generate_hash(session_id: str) -> str:
        return hashlib.sha256(session_id.encode()).hexdigest()[:16]

    async def _save_to_postgres(self, session: SessionInfo, client: HumanizedMaxUserClient):
        """Save session credentials to PostgreSQL."""
        if not self._pg:
            return

        try:
            login_token = client.login_token
            device_id = client.client._device_id

            await self._pg.execute(
                """
                INSERT INTO elo_t_channel_accounts
                    (session_id, channel_id, phone, login_token, device_id,
                     session_status, webhook_url, tenant_id, ip_node_id, created_at, updated_at)
                VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, NOW(), NOW())
                ON CONFLICT (session_id) DO UPDATE SET
                    login_token = $4,
                    device_id = $5,
                    session_status = $6,
                    webhook_url = $7,
                    updated_at = NOW()
                """,
                session.session_id,
                self.CHANNEL_ID,
                session.phone,
                login_token,
                device_id,
                session.status,
                session.webhook_url,
                session.tenant_id,
                self.ip_node_id,
            )
            logger.info(f"Session {session.session_id} saved to PostgreSQL")
        except Exception as e:
            logger.error(f"Failed to save to PostgreSQL: {e}")

    async def _load_from_postgres(self, session_id: str) -> Optional[dict]:
        """Load session credentials from PostgreSQL."""
        if not self._pg:
            return None

        try:
            row = await self._pg.fetchrow(
                """
                SELECT session_id, phone, login_token, device_id,
                       session_status, webhook_url, tenant_id
                FROM elo_t_channel_accounts
                WHERE session_id = $1 AND channel_id = $2
                """,
                session_id,
                self.CHANNEL_ID,
            )
            if row:
                return dict(row)
            return None
        except Exception as e:
            logger.error(f"Failed to load from PostgreSQL: {e}")
            return None

    async def _update_postgres_status(self, session_id: str, status: str):
        """Update session status in PostgreSQL."""
        if not self._pg:
            return

        try:
            await self._pg.execute(
                """
                UPDATE elo_t_channel_accounts
                SET session_status = $1, updated_at = NOW()
                WHERE session_id = $2 AND channel_id = $3
                """,
                status,
                session_id,
                self.CHANNEL_ID,
            )
        except Exception as e:
            logger.error(f"Failed to update PostgreSQL status: {e}")

    # ==================== Restore on Startup ====================

    async def restore_sessions(self):
        """Restore sessions from PostgreSQL on startup."""
        if not self._pg:
            logger.info("PostgreSQL not configured, skipping session restore")
            return

        try:
            rows = await self._pg.fetch(
                """
                SELECT session_id, phone, login_token, device_id,
                       webhook_url, tenant_id
                FROM elo_t_channel_accounts
                WHERE ip_node_id = $1
                  AND channel_id = $2
                  AND session_status IN ('connected', 'disconnected')
                  AND login_token IS NOT NULL
                """,
                self.ip_node_id,
                self.CHANNEL_ID,
            )

            logger.info(f"Found {len(rows)} MAX User sessions to restore")

            for row in rows:
                session_id = row["session_id"]
                try:
                    await self.reconnect_session(session_id)
                    logger.info(f"Restored session {session_id}")
                except Exception as e:
                    logger.error(f"Failed to restore session {session_id}: {e}")

        except Exception as e:
            logger.error(f"Failed to restore sessions: {e}")
