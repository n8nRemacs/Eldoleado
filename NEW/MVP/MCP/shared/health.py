"""Health check models and utilities for MCP servers.

Provides unified health response format for all channels.
"""

from datetime import datetime
from enum import Enum
from typing import Dict, Optional, List, Any
from pydantic import BaseModel, Field


class HealthStatus(str, Enum):
    """Health status enum."""
    HEALTHY = "healthy"
    DEGRADED = "degraded"
    UNHEALTHY = "unhealthy"


class SessionStats(BaseModel):
    """Session statistics."""
    total: int = 0
    connected: int = 0
    disconnected: int = 0
    connecting: int = 0
    failed: int = 0


class MessageMetrics(BaseModel):
    """Message metrics for last 24h."""
    sent: int = 0
    received: int = 0
    failed: int = 0


class HealthMetrics(BaseModel):
    """Health metrics."""
    messages_24h: MessageMetrics = Field(default_factory=MessageMetrics)
    errors_24h: int = 0
    reconnects_24h: int = 0
    last_activity: Optional[str] = None
    last_error: Optional[str] = None


class HealthResponse(BaseModel):
    """Unified health response for all MCP servers."""
    status: HealthStatus
    channel: str
    version: str = "1.0.0"
    uptime: int = 0  # seconds
    timestamp: str = Field(default_factory=lambda: datetime.utcnow().isoformat())

    # Session stats
    sessions: SessionStats = Field(default_factory=SessionStats)

    # Metrics
    metrics: HealthMetrics = Field(default_factory=HealthMetrics)

    # Health score (0-100)
    health_score: int = 100


class HealthChecker:
    """Health checker for MCP servers."""

    def __init__(self, channel: str, version: str = "1.0.0"):
        """Initialize health checker.

        Args:
            channel: Channel name (telegram, whatsapp, avito, etc.)
            version: Server version
        """
        self.channel = channel
        self.version = version
        self._start_time = datetime.now()

        # Metrics tracking
        self._messages_sent = 0
        self._messages_received = 0
        self._messages_failed = 0
        self._errors = 0
        self._reconnects = 0
        self._last_activity: Optional[datetime] = None
        self._last_error: Optional[str] = None

        # Session tracking
        self._sessions: Dict[str, str] = {}  # session_id -> status

    @property
    def uptime_seconds(self) -> int:
        """Get server uptime in seconds."""
        return int((datetime.now() - self._start_time).total_seconds())

    def register_session(self, session_id: str, status: str = "disconnected"):
        """Register a session for tracking."""
        self._sessions[session_id] = status

    def unregister_session(self, session_id: str):
        """Unregister a session."""
        self._sessions.pop(session_id, None)

    def update_session_status(self, session_id: str, status: str):
        """Update session status."""
        if session_id in self._sessions:
            self._sessions[session_id] = status

    def record_message_sent(self):
        """Record a sent message."""
        self._messages_sent += 1
        self._last_activity = datetime.now()

    def record_message_received(self):
        """Record a received message."""
        self._messages_received += 1
        self._last_activity = datetime.now()

    def record_message_failed(self):
        """Record a failed message."""
        self._messages_failed += 1

    def record_error(self, error: str):
        """Record an error."""
        self._errors += 1
        self._last_error = error

    def record_reconnect(self):
        """Record a reconnection attempt."""
        self._reconnects += 1

    def get_session_stats(self) -> SessionStats:
        """Get session statistics."""
        stats = SessionStats(total=len(self._sessions))

        for status in self._sessions.values():
            if status == "connected":
                stats.connected += 1
            elif status == "disconnected":
                stats.disconnected += 1
            elif status == "connecting":
                stats.connecting += 1
            elif status == "failed":
                stats.failed += 1

        return stats

    def calculate_health_score(self) -> int:
        """Calculate health score (0-100)."""
        score = 100
        stats = self.get_session_stats()

        if stats.total > 0:
            # Deduct for disconnected/failed sessions
            unhealthy_ratio = (stats.disconnected + stats.failed) / stats.total
            score -= int(unhealthy_ratio * 50)

            # Deduct for errors
            total_messages = self._messages_sent + self._messages_received
            if total_messages > 0:
                error_ratio = self._messages_failed / total_messages
                score -= int(error_ratio * 30)

            # Deduct for excessive reconnects
            if self._reconnects > stats.total * 5:
                score -= 20
            elif self._reconnects > stats.total * 2:
                score -= 10

        return max(0, min(100, score))

    def get_status(self) -> HealthStatus:
        """Get overall health status."""
        score = self.calculate_health_score()

        if score >= 80:
            return HealthStatus.HEALTHY
        elif score >= 50:
            return HealthStatus.DEGRADED
        else:
            return HealthStatus.UNHEALTHY

    def get_health_response(self) -> HealthResponse:
        """Get full health response."""
        return HealthResponse(
            status=self.get_status(),
            channel=self.channel,
            version=self.version,
            uptime=self.uptime_seconds,
            sessions=self.get_session_stats(),
            metrics=HealthMetrics(
                messages_24h=MessageMetrics(
                    sent=self._messages_sent,
                    received=self._messages_received,
                    failed=self._messages_failed,
                ),
                errors_24h=self._errors,
                reconnects_24h=self._reconnects,
                last_activity=self._last_activity.isoformat() if self._last_activity else None,
                last_error=self._last_error,
            ),
            health_score=self.calculate_health_score(),
        )

    def to_dict(self) -> Dict[str, Any]:
        """Get health response as dictionary."""
        return self.get_health_response().model_dump()


# Singleton health checkers per channel
_health_checkers: Dict[str, HealthChecker] = {}


def get_health_checker(channel: str, version: str = "1.0.0") -> HealthChecker:
    """Get or create health checker for channel."""
    if channel not in _health_checkers:
        _health_checkers[channel] = HealthChecker(channel, version)
    return _health_checkers[channel]
