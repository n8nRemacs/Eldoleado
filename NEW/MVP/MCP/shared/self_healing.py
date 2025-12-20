"""Self-healing mixin for MCP channel clients.

Provides exponential backoff reconnection and error handling strategies.
"""

import asyncio
import random
import logging
from datetime import datetime
from enum import Enum
from typing import Callable, Optional, Awaitable, Dict, Any

logger = logging.getLogger(__name__)


class ConnectionStatus(str, Enum):
    """Connection status enum."""
    DISCONNECTED = "disconnected"
    CONNECTING = "connecting"
    CONNECTED = "connected"
    RECONNECTING = "reconnecting"
    FAILED = "failed"


class RecoveryStrategy(str, Enum):
    """Recovery strategy for different error types."""
    RECONNECT_IMMEDIATELY = "reconnect_immediately"
    RECONNECT_WITH_BACKOFF = "reconnect_with_backoff"
    DELETE_SESSION = "delete_session"
    ALERT_AND_STOP = "alert_and_stop"
    REFRESH_TOKEN = "refresh_token"


# Default error code strategies (can be overridden per channel)
DEFAULT_ERROR_STRATEGIES: Dict[int, RecoveryStrategy] = {
    401: RecoveryStrategy.REFRESH_TOKEN,       # Unauthorized
    403: RecoveryStrategy.ALERT_AND_STOP,      # Forbidden (ban)
    408: RecoveryStrategy.RECONNECT_WITH_BACKOFF,  # Timeout
    429: RecoveryStrategy.RECONNECT_WITH_BACKOFF,  # Rate limit
    500: RecoveryStrategy.RECONNECT_WITH_BACKOFF,  # Server error
    502: RecoveryStrategy.RECONNECT_WITH_BACKOFF,  # Bad gateway
    503: RecoveryStrategy.RECONNECT_WITH_BACKOFF,  # Service unavailable
}


class SelfHealingMixin:
    """Mixin class for self-healing connection management.

    Add this mixin to your channel client class to enable:
    - Exponential backoff reconnection
    - Error code based recovery strategies
    - Status tracking with callbacks
    - Metrics integration

    Usage:
        class MyClient(SelfHealingMixin):
            def __init__(self):
                super().__init__()
                self.init_self_healing()

            async def connect(self):
                # Your connection logic
                pass

            async def cleanup(self):
                # Your cleanup logic
                pass
    """

    # Configuration (can be overridden in subclass)
    max_reconnect_attempts: int = 10
    initial_backoff: float = 1.0
    max_backoff: float = 60.0
    backoff_multiplier: float = 2.0
    jitter_factor: float = 0.2  # ±20% jitter

    # Error strategies (can be overridden in subclass)
    error_strategies: Dict[int, RecoveryStrategy] = DEFAULT_ERROR_STRATEGIES

    def init_self_healing(
        self,
        on_status_change: Optional[Callable[[ConnectionStatus, ConnectionStatus], Awaitable[None]]] = None,
        on_max_retries: Optional[Callable[[], Awaitable[None]]] = None,
    ):
        """Initialize self-healing state."""
        self._reconnect_attempts: int = 0
        self._last_activity: datetime = datetime.now()
        self._status: ConnectionStatus = ConnectionStatus.DISCONNECTED
        self._on_status_change = on_status_change
        self._on_max_retries = on_max_retries
        self._reconnect_task: Optional[asyncio.Task] = None

    @property
    def status(self) -> ConnectionStatus:
        """Get current connection status."""
        return self._status

    @status.setter
    def status(self, value: ConnectionStatus):
        """Set connection status with callback."""
        old_status = self._status
        self._status = value
        if self._on_status_change and old_status != value:
            asyncio.create_task(self._safe_callback(
                self._on_status_change, old_status, value
            ))

    @property
    def reconnect_attempts(self) -> int:
        """Get current reconnect attempt count."""
        return self._reconnect_attempts

    @property
    def last_activity(self) -> datetime:
        """Get last activity timestamp."""
        return self._last_activity

    def update_activity(self):
        """Update last activity timestamp. Call on any successful operation."""
        self._last_activity = datetime.now()

    def reset_reconnect_counter(self):
        """Reset reconnect counter after successful connection."""
        self._reconnect_attempts = 0
        self.status = ConnectionStatus.CONNECTED
        self.update_activity()

    def calculate_backoff(self) -> float:
        """Calculate exponential backoff delay with jitter."""
        delay = min(
            self.initial_backoff * (self.backoff_multiplier ** self._reconnect_attempts),
            self.max_backoff
        )
        # Add jitter ±20%
        jitter = delay * self.jitter_factor * (random.random() * 2 - 1)
        return max(0.1, delay + jitter)

    def get_recovery_strategy(self, error_code: Optional[int]) -> RecoveryStrategy:
        """Get recovery strategy for error code. Override for channel-specific handling."""
        if error_code is None:
            return RecoveryStrategy.RECONNECT_WITH_BACKOFF
        return self.error_strategies.get(error_code, RecoveryStrategy.RECONNECT_WITH_BACKOFF)

    async def reconnect_with_backoff(self) -> bool:
        """Attempt reconnection with exponential backoff.

        Returns:
            True if reconnection succeeded, False otherwise
        """
        if self._reconnect_attempts >= self.max_reconnect_attempts:
            logger.error(
                f"Max reconnect attempts reached ({self._reconnect_attempts})"
            )
            self.status = ConnectionStatus.FAILED
            if self._on_max_retries:
                await self._safe_callback(self._on_max_retries)
            return False

        self._reconnect_attempts += 1
        self.status = ConnectionStatus.RECONNECTING

        delay = self.calculate_backoff()
        logger.info(
            f"Reconnecting... attempt {self._reconnect_attempts}/{self.max_reconnect_attempts}, "
            f"delay {delay:.1f}s"
        )

        await asyncio.sleep(delay)

        try:
            await self.connect()  # Must be implemented by subclass
            self.reset_reconnect_counter()
            logger.info("Reconnection successful")
            return True
        except Exception as e:
            logger.error(f"Reconnect failed: {e}")
            return await self.reconnect_with_backoff()

    async def handle_disconnect(
        self,
        reason: str,
        error_code: Optional[int] = None
    ) -> None:
        """Handle disconnection with appropriate recovery strategy.

        Args:
            reason: Human-readable disconnect reason
            error_code: HTTP/protocol error code if available
        """
        logger.warning(f"Disconnected: {reason} (code: {error_code})")

        strategy = self.get_recovery_strategy(error_code)

        if strategy == RecoveryStrategy.DELETE_SESSION:
            self.status = ConnectionStatus.FAILED
            await self.cleanup()
        elif strategy == RecoveryStrategy.ALERT_AND_STOP:
            self.status = ConnectionStatus.FAILED
            # Alert should be sent by the caller
        elif strategy == RecoveryStrategy.REFRESH_TOKEN:
            # Try token refresh first, then reconnect
            try:
                await self.refresh_token()  # Must be implemented if using this strategy
                await self.reconnect_with_backoff()
            except Exception as e:
                logger.error(f"Token refresh failed: {e}")
                self.status = ConnectionStatus.FAILED
        elif strategy == RecoveryStrategy.RECONNECT_IMMEDIATELY:
            self._reconnect_attempts = 0  # Reset for immediate retry
            await self.reconnect_with_backoff()
        else:  # RECONNECT_WITH_BACKOFF
            await self.reconnect_with_backoff()

    def start_reconnect_task(self):
        """Start background reconnection task."""
        if self._reconnect_task and not self._reconnect_task.done():
            return  # Already running

        self._reconnect_task = asyncio.create_task(self.reconnect_with_backoff())

    def cancel_reconnect_task(self):
        """Cancel ongoing reconnection task."""
        if self._reconnect_task and not self._reconnect_task.done():
            self._reconnect_task.cancel()
            self._reconnect_task = None

    # Abstract methods to be implemented by subclass
    async def connect(self):
        """Connect to the channel. Must be implemented by subclass."""
        raise NotImplementedError("Subclass must implement connect()")

    async def cleanup(self):
        """Cleanup resources on permanent failure. Override if needed."""
        pass

    async def refresh_token(self):
        """Refresh authentication token. Override if using REFRESH_TOKEN strategy."""
        raise NotImplementedError(
            "Subclass must implement refresh_token() when using REFRESH_TOKEN strategy"
        )

    async def _safe_callback(self, callback: Callable, *args, **kwargs):
        """Execute callback safely, catching any exceptions."""
        try:
            result = callback(*args, **kwargs)
            if asyncio.iscoroutine(result):
                await result
        except Exception as e:
            logger.error(f"Callback error: {e}")


# Channel-specific error strategies
WHATSAPP_ERROR_STRATEGIES: Dict[int, RecoveryStrategy] = {
    401: RecoveryStrategy.DELETE_SESSION,      # loggedOut
    403: RecoveryStrategy.ALERT_AND_STOP,      # banned
    408: RecoveryStrategy.RECONNECT_WITH_BACKOFF,
    428: RecoveryStrategy.RECONNECT_IMMEDIATELY,  # connectionClosed
    440: RecoveryStrategy.DELETE_SESSION,      # connectionReplaced
    515: RecoveryStrategy.RECONNECT_IMMEDIATELY,  # restartRequired
}

TELEGRAM_ERROR_STRATEGIES: Dict[int, RecoveryStrategy] = {
    401: RecoveryStrategy.REFRESH_TOKEN,       # Unauthorized
    403: RecoveryStrategy.ALERT_AND_STOP,      # Forbidden
    409: RecoveryStrategy.RECONNECT_IMMEDIATELY,  # Conflict
    429: RecoveryStrategy.RECONNECT_WITH_BACKOFF,  # Too Many Requests
}

AVITO_ERROR_STRATEGIES: Dict[int, RecoveryStrategy] = {
    401: RecoveryStrategy.REFRESH_TOKEN,       # sessid expired
    403: RecoveryStrategy.ALERT_AND_STOP,      # Blocked
    429: RecoveryStrategy.RECONNECT_WITH_BACKOFF,  # Rate limit
}

VK_ERROR_STRATEGIES: Dict[int, RecoveryStrategy] = {
    5: RecoveryStrategy.REFRESH_TOKEN,         # token_expired
    14: RecoveryStrategy.ALERT_AND_STOP,       # captcha_needed
    17: RecoveryStrategy.ALERT_AND_STOP,       # validation_required
    29: RecoveryStrategy.RECONNECT_WITH_BACKOFF,  # rate_limit
}

MAX_ERROR_STRATEGIES: Dict[int, RecoveryStrategy] = {
    401: RecoveryStrategy.REFRESH_TOKEN,
    403: RecoveryStrategy.ALERT_AND_STOP,
}
