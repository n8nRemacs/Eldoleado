"""Connection watchdog for MCP channel clients.

Monitors connection health and triggers reconnection when needed.
"""

import asyncio
import logging
from datetime import datetime, timedelta
from typing import Callable, Optional, Awaitable, List

logger = logging.getLogger(__name__)


class ConnectionWatchdog:
    """Watches connection health and triggers recovery actions.

    Features:
    - Periodic health checks
    - Stale connection detection
    - Automatic reconnection trigger
    - Configurable timeouts and intervals

    Usage:
        watchdog = ConnectionWatchdog(
            health_check=my_health_check_func,
            on_unhealthy=my_reconnect_func,
        )
        await watchdog.start()
        # ... later ...
        await watchdog.stop()
    """

    def __init__(
        self,
        health_check: Callable[[], Awaitable[bool]],
        on_unhealthy: Callable[[], Awaitable[None]],
        on_stale: Optional[Callable[[], Awaitable[None]]] = None,
        ping_interval: float = 30.0,
        ping_timeout: float = 10.0,
        max_inactivity: float = 300.0,
        max_consecutive_failures: int = 3,
    ):
        """Initialize watchdog.

        Args:
            health_check: Async function that returns True if healthy
            on_unhealthy: Async function to call when health check fails
            on_stale: Async function to call when connection is stale
            ping_interval: Seconds between health checks
            ping_timeout: Timeout for each health check
            max_inactivity: Max seconds without activity before considered stale
            max_consecutive_failures: Failures before triggering recovery
        """
        self.health_check = health_check
        self.on_unhealthy = on_unhealthy
        self.on_stale = on_stale or on_unhealthy
        self.ping_interval = ping_interval
        self.ping_timeout = ping_timeout
        self.max_inactivity = max_inactivity
        self.max_consecutive_failures = max_consecutive_failures

        self._task: Optional[asyncio.Task] = None
        self._running = False
        self._consecutive_failures = 0
        self._last_success: Optional[datetime] = None
        self._last_activity: datetime = datetime.now()

    @property
    def is_running(self) -> bool:
        """Check if watchdog is running."""
        return self._running

    @property
    def consecutive_failures(self) -> int:
        """Get count of consecutive health check failures."""
        return self._consecutive_failures

    def update_activity(self):
        """Update last activity timestamp. Call on any successful operation."""
        self._last_activity = datetime.now()

    async def start(self):
        """Start the watchdog monitoring loop."""
        if self._running:
            logger.warning("Watchdog already running")
            return

        self._running = True
        self._task = asyncio.create_task(self._monitor_loop())
        logger.info("Watchdog started")

    async def stop(self):
        """Stop the watchdog monitoring loop."""
        self._running = False
        if self._task and not self._task.done():
            self._task.cancel()
            try:
                await self._task
            except asyncio.CancelledError:
                pass
        self._task = None
        logger.info("Watchdog stopped")

    async def _monitor_loop(self):
        """Main monitoring loop."""
        while self._running:
            try:
                await asyncio.sleep(self.ping_interval)

                if not self._running:
                    break

                # Check for stale connection
                inactive_time = (datetime.now() - self._last_activity).total_seconds()
                if inactive_time > self.max_inactivity:
                    logger.warning(
                        f"Connection stale: {inactive_time:.0f}s since last activity"
                    )
                    await self._safe_callback(self.on_stale)
                    continue

                # Perform health check
                try:
                    is_healthy = await asyncio.wait_for(
                        self.health_check(),
                        timeout=self.ping_timeout
                    )

                    if is_healthy:
                        self._consecutive_failures = 0
                        self._last_success = datetime.now()
                    else:
                        await self._handle_failure("Health check returned False")

                except asyncio.TimeoutError:
                    await self._handle_failure("Health check timed out")
                except Exception as e:
                    await self._handle_failure(f"Health check error: {e}")

            except asyncio.CancelledError:
                break
            except Exception as e:
                logger.error(f"Watchdog loop error: {e}")
                await asyncio.sleep(self.ping_interval)

    async def _handle_failure(self, reason: str):
        """Handle a health check failure."""
        self._consecutive_failures += 1
        logger.warning(
            f"Health check failed ({self._consecutive_failures}/{self.max_consecutive_failures}): {reason}"
        )

        if self._consecutive_failures >= self.max_consecutive_failures:
            logger.error(
                f"Max consecutive failures reached, triggering recovery"
            )
            self._consecutive_failures = 0
            await self._safe_callback(self.on_unhealthy)

    async def _safe_callback(self, callback: Callable[[], Awaitable[None]]):
        """Execute callback safely."""
        try:
            await callback()
        except Exception as e:
            logger.error(f"Watchdog callback error: {e}")


class MultiSessionWatchdog:
    """Watches multiple sessions and their health.

    Useful for monitoring all accounts in a multi-tenant server.
    """

    def __init__(
        self,
        check_interval: float = 60.0,
        on_session_unhealthy: Optional[Callable[[str], Awaitable[None]]] = None,
    ):
        """Initialize multi-session watchdog.

        Args:
            check_interval: Seconds between full session checks
            on_session_unhealthy: Callback when a session is unhealthy
        """
        self.check_interval = check_interval
        self.on_session_unhealthy = on_session_unhealthy

        self._sessions: dict = {}  # session_id -> health_check_func
        self._task: Optional[asyncio.Task] = None
        self._running = False

    def register_session(
        self,
        session_id: str,
        health_check: Callable[[], Awaitable[bool]]
    ):
        """Register a session to monitor."""
        self._sessions[session_id] = health_check
        logger.info(f"Session registered for monitoring: {session_id}")

    def unregister_session(self, session_id: str):
        """Unregister a session from monitoring."""
        if session_id in self._sessions:
            del self._sessions[session_id]
            logger.info(f"Session unregistered from monitoring: {session_id}")

    async def start(self):
        """Start monitoring all sessions."""
        if self._running:
            return

        self._running = True
        self._task = asyncio.create_task(self._monitor_loop())
        logger.info(f"Multi-session watchdog started with {len(self._sessions)} sessions")

    async def stop(self):
        """Stop monitoring."""
        self._running = False
        if self._task:
            self._task.cancel()
            try:
                await self._task
            except asyncio.CancelledError:
                pass
        logger.info("Multi-session watchdog stopped")

    async def _monitor_loop(self):
        """Monitor all registered sessions."""
        while self._running:
            try:
                await asyncio.sleep(self.check_interval)

                if not self._running:
                    break

                unhealthy_sessions: List[str] = []

                for session_id, health_check in list(self._sessions.items()):
                    try:
                        is_healthy = await asyncio.wait_for(
                            health_check(),
                            timeout=10.0
                        )
                        if not is_healthy:
                            unhealthy_sessions.append(session_id)
                    except Exception as e:
                        logger.warning(f"Session {session_id} health check failed: {e}")
                        unhealthy_sessions.append(session_id)

                # Report unhealthy sessions
                if unhealthy_sessions:
                    logger.warning(
                        f"Unhealthy sessions: {len(unhealthy_sessions)}/{len(self._sessions)}"
                    )
                    if self.on_session_unhealthy:
                        for session_id in unhealthy_sessions:
                            try:
                                await self.on_session_unhealthy(session_id)
                            except Exception as e:
                                logger.error(f"Unhealthy callback error for {session_id}: {e}")

            except asyncio.CancelledError:
                break
            except Exception as e:
                logger.error(f"Multi-session watchdog error: {e}")

    async def get_health_summary(self) -> dict:
        """Get health summary of all sessions."""
        healthy = 0
        unhealthy = 0
        errors = []

        for session_id, health_check in self._sessions.items():
            try:
                is_healthy = await asyncio.wait_for(health_check(), timeout=5.0)
                if is_healthy:
                    healthy += 1
                else:
                    unhealthy += 1
            except Exception as e:
                unhealthy += 1
                errors.append(f"{session_id}: {e}")

        return {
            "total": len(self._sessions),
            "healthy": healthy,
            "unhealthy": unhealthy,
            "errors": errors,
        }
