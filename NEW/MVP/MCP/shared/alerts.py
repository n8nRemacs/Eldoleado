"""Alerting system for MCP servers.

Supports Telegram bot and n8n webhook notifications.
"""

import os
import asyncio
import logging
from datetime import datetime
from enum import Enum
from typing import Dict, Optional, Any, List
from dataclasses import dataclass, field

import httpx

logger = logging.getLogger(__name__)


class AlertType(str, Enum):
    """Alert type enum."""
    MAX_RETRIES_EXCEEDED = "max_retries_exceeded"
    DISCONNECTED = "disconnected"
    BANNED = "banned"
    ERROR = "error"
    CONNECTED = "connected"
    RECONNECTING = "reconnecting"
    TOKEN_EXPIRED = "token_expired"
    RATE_LIMITED = "rate_limited"


class AlertSeverity(str, Enum):
    """Alert severity enum."""
    INFO = "info"
    WARNING = "warning"
    CRITICAL = "critical"


@dataclass
class AlertPayload:
    """Alert payload."""
    type: AlertType
    session_id: str
    channel: str
    message: str
    severity: AlertSeverity = AlertSeverity.WARNING
    details: Dict[str, Any] = field(default_factory=dict)
    timestamp: str = field(default_factory=lambda: datetime.utcnow().isoformat())


@dataclass
class AlertConfig:
    """Alert configuration."""
    # Telegram settings
    telegram_bot_token: Optional[str] = None
    telegram_chat_id: Optional[str] = None

    # n8n webhook settings
    n8n_webhook_url: Optional[str] = None

    # General settings
    enabled: bool = True
    cooldown_seconds: float = 60.0

    # Alert filters
    alert_on_max_retries: bool = True
    alert_on_disconnect: bool = False  # Only alert on permanent disconnects
    alert_on_ban: bool = True
    alert_on_error: bool = True

    @classmethod
    def from_env(cls) -> "AlertConfig":
        """Create config from environment variables."""
        return cls(
            telegram_bot_token=os.environ.get("ALERT_TELEGRAM_BOT_TOKEN"),
            telegram_chat_id=os.environ.get("ALERT_TELEGRAM_CHAT_ID"),
            n8n_webhook_url=os.environ.get("ALERT_N8N_WEBHOOK_URL"),
            enabled=os.environ.get("ALERTS_ENABLED", "true").lower() == "true",
        )


class AlertService:
    """Service for sending alerts to Telegram and n8n."""

    def __init__(self, config: Optional[AlertConfig] = None):
        """Initialize alert service.

        Args:
            config: Alert configuration. If None, loads from environment.
        """
        self.config = config or AlertConfig.from_env()
        self._last_alerts: Dict[str, float] = {}  # key -> timestamp
        self._client: Optional[httpx.AsyncClient] = None

    async def _get_client(self) -> httpx.AsyncClient:
        """Get or create HTTP client."""
        if self._client is None or self._client.is_closed:
            self._client = httpx.AsyncClient(timeout=10.0)
        return self._client

    async def close(self):
        """Close HTTP client."""
        if self._client:
            await self._client.aclose()
            self._client = None

    def _should_alert(self, key: str) -> bool:
        """Check if alert should be sent (cooldown check)."""
        if not self.config.enabled:
            return False

        last_alert = self._last_alerts.get(key)
        if last_alert:
            elapsed = datetime.now().timestamp() - last_alert
            if elapsed < self.config.cooldown_seconds:
                return False

        return True

    def _record_alert(self, key: str):
        """Record alert timestamp for cooldown."""
        self._last_alerts[key] = datetime.now().timestamp()

    async def send_alert(self, payload: AlertPayload) -> bool:
        """Send alert to all configured channels.

        Returns:
            True if at least one channel received the alert.
        """
        alert_key = f"{payload.type}:{payload.session_id}"

        if not self._should_alert(alert_key):
            logger.debug(f"Alert skipped (cooldown): {alert_key}")
            return False

        self._record_alert(alert_key)

        # Send to all channels in parallel
        tasks = []

        if self.config.telegram_bot_token and self.config.telegram_chat_id:
            tasks.append(self._send_telegram(payload))

        if self.config.n8n_webhook_url:
            tasks.append(self._send_n8n(payload))

        if not tasks:
            logger.warning("No alert channels configured")
            return False

        results = await asyncio.gather(*tasks, return_exceptions=True)

        success = any(r is True for r in results)
        if not success:
            logger.error(f"All alert channels failed: {results}")

        return success

    async def _send_telegram(self, payload: AlertPayload) -> bool:
        """Send alert to Telegram."""
        try:
            emoji = self._get_emoji(payload.severity)
            type_label = self._get_type_label(payload.type)

            text_lines = [
                f"{emoji} *{type_label}*",
                "",
                f"📱 *Канал:* {payload.channel}",
                f"🔑 *Сессия:* `{payload.session_id}`",
                f"📝 *Сообщение:* {payload.message}",
            ]

            if payload.details:
                details_str = ", ".join(f"{k}={v}" for k, v in payload.details.items())
                text_lines.append(f"📋 *Детали:* {details_str}")

            text_lines.extend(["", f"🕐 {payload.timestamp}"])
            text = "\n".join(text_lines)

            client = await self._get_client()
            url = f"https://api.telegram.org/bot{self.config.telegram_bot_token}/sendMessage"

            response = await client.post(url, json={
                "chat_id": self.config.telegram_chat_id,
                "text": text,
                "parse_mode": "Markdown",
                "disable_notification": payload.severity == AlertSeverity.INFO,
            })

            if response.status_code == 200:
                logger.info(f"Telegram alert sent: {payload.type}")
                return True
            else:
                logger.error(f"Telegram alert failed: {response.status_code} {response.text}")
                return False

        except Exception as e:
            logger.error(f"Telegram alert error: {e}")
            return False

    async def _send_n8n(self, payload: AlertPayload) -> bool:
        """Send alert to n8n webhook."""
        try:
            client = await self._get_client()

            response = await client.post(
                self.config.n8n_webhook_url,
                json={
                    "event": payload.type,
                    "session_id": payload.session_id,
                    "channel": payload.channel,
                    "message": payload.message,
                    "severity": payload.severity,
                    "details": payload.details,
                    "timestamp": payload.timestamp,
                    "source": "mcp-self-healing",
                }
            )

            if response.status_code == 200:
                logger.info(f"n8n alert sent: {payload.type}")
                return True
            else:
                logger.error(f"n8n alert failed: {response.status_code}")
                return False

        except Exception as e:
            logger.error(f"n8n alert error: {e}")
            return False

    def _get_emoji(self, severity: AlertSeverity) -> str:
        """Get emoji for severity."""
        return {
            AlertSeverity.INFO: "ℹ️",
            AlertSeverity.WARNING: "⚠️",
            AlertSeverity.CRITICAL: "🚨",
        }.get(severity, "📢")

    def _get_type_label(self, alert_type: AlertType) -> str:
        """Get human-readable label for alert type."""
        return {
            AlertType.MAX_RETRIES_EXCEEDED: "Превышено макс. попыток",
            AlertType.DISCONNECTED: "Отключен",
            AlertType.BANNED: "Заблокирован",
            AlertType.ERROR: "Ошибка",
            AlertType.CONNECTED: "Подключен",
            AlertType.RECONNECTING: "Переподключение",
            AlertType.TOKEN_EXPIRED: "Токен истёк",
            AlertType.RATE_LIMITED: "Rate Limit",
        }.get(alert_type, alert_type.value)

    # Convenience methods

    async def alert_max_retries(self, session_id: str, channel: str, attempts: int):
        """Alert when max retries exceeded."""
        if not self.config.alert_on_max_retries:
            return

        await self.send_alert(AlertPayload(
            type=AlertType.MAX_RETRIES_EXCEEDED,
            session_id=session_id,
            channel=channel,
            message=f"Не удалось переподключиться после {attempts} попыток",
            severity=AlertSeverity.CRITICAL,
            details={"attempts": attempts},
        ))

    async def alert_disconnected(self, session_id: str, channel: str, reason: str):
        """Alert on disconnect."""
        if not self.config.alert_on_disconnect:
            return

        await self.send_alert(AlertPayload(
            type=AlertType.DISCONNECTED,
            session_id=session_id,
            channel=channel,
            message=f"Сессия отключена: {reason}",
            severity=AlertSeverity.WARNING,
            details={"reason": reason},
        ))

    async def alert_banned(self, session_id: str, channel: str):
        """Alert on account ban."""
        if not self.config.alert_on_ban:
            return

        await self.send_alert(AlertPayload(
            type=AlertType.BANNED,
            session_id=session_id,
            channel=channel,
            message="Аккаунт заблокирован!",
            severity=AlertSeverity.CRITICAL,
        ))

    async def alert_error(self, session_id: str, channel: str, error: str):
        """Alert on error."""
        if not self.config.alert_on_error:
            return

        await self.send_alert(AlertPayload(
            type=AlertType.ERROR,
            session_id=session_id,
            channel=channel,
            message=f"Ошибка: {error}",
            severity=AlertSeverity.WARNING,
            details={"error": error},
        ))

    async def alert_connected(self, session_id: str, channel: str, phone: Optional[str] = None):
        """Alert on successful connection."""
        await self.send_alert(AlertPayload(
            type=AlertType.CONNECTED,
            session_id=session_id,
            channel=channel,
            message=f"Подключен{f' как {phone}' if phone else ''}",
            severity=AlertSeverity.INFO,
            details={"phone": phone} if phone else {},
        ))

    async def alert_reconnecting(
        self,
        session_id: str,
        channel: str,
        attempt: int,
        max_attempts: int
    ):
        """Alert on reconnection attempt (first and every 3rd)."""
        if attempt != 1 and attempt % 3 != 0:
            return

        await self.send_alert(AlertPayload(
            type=AlertType.RECONNECTING,
            session_id=session_id,
            channel=channel,
            message=f"Переподключение: попытка {attempt}/{max_attempts}",
            severity=AlertSeverity.WARNING,
            details={"attempt": attempt, "max_attempts": max_attempts},
        ))


# Singleton instance
_alert_service: Optional[AlertService] = None


def get_alert_service(config: Optional[AlertConfig] = None) -> AlertService:
    """Get or create alert service singleton."""
    global _alert_service
    if _alert_service is None:
        _alert_service = AlertService(config)
    return _alert_service


def init_alert_service(config: AlertConfig) -> AlertService:
    """Initialize alert service with config."""
    global _alert_service
    _alert_service = AlertService(config)
    return _alert_service
