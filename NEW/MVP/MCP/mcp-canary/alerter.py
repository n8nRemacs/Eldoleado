"""Alert service for API Canary."""

import asyncio
import logging
from dataclasses import dataclass
from datetime import datetime, timedelta
from enum import Enum
from typing import Dict, Optional

import httpx

logger = logging.getLogger(__name__)


class AlertSeverity(str, Enum):
    """Alert severity levels."""
    INFO = "info"
    WARNING = "warning"
    ERROR = "error"
    CRITICAL = "critical"


@dataclass
class Alert:
    """Alert data."""
    channel: str
    severity: AlertSeverity
    title: str
    message: str
    endpoint: Optional[str] = None
    error_code: Optional[int] = None
    timestamp: datetime = None

    def __post_init__(self):
        if self.timestamp is None:
            self.timestamp = datetime.now()


class Alerter:
    """Alert service for sending notifications."""

    SEVERITY_EMOJI = {
        AlertSeverity.INFO: "ℹ️",
        AlertSeverity.WARNING: "⚠️",
        AlertSeverity.ERROR: "❌",
        AlertSeverity.CRITICAL: "🚨",
    }

    def __init__(
        self,
        telegram_bot_token: str = "",
        telegram_chat_id: str = "",
        n8n_webhook_url: str = "",
        cooldown_seconds: int = 300,
    ):
        self.telegram_bot_token = telegram_bot_token
        self.telegram_chat_id = telegram_chat_id
        self.n8n_webhook_url = n8n_webhook_url
        self.cooldown = timedelta(seconds=cooldown_seconds)

        # Track last alert time per channel+endpoint to prevent spam
        self._last_alerts: Dict[str, datetime] = {}

    def _get_alert_key(self, alert: Alert) -> str:
        """Get unique key for alert deduplication."""
        return f"{alert.channel}:{alert.endpoint or 'general'}:{alert.severity.value}"

    def _is_on_cooldown(self, alert: Alert) -> bool:
        """Check if alert is on cooldown."""
        key = self._get_alert_key(alert)
        last_time = self._last_alerts.get(key)
        if last_time and datetime.now() - last_time < self.cooldown:
            return True
        return False

    def _mark_sent(self, alert: Alert):
        """Mark alert as sent for cooldown tracking."""
        key = self._get_alert_key(alert)
        self._last_alerts[key] = datetime.now()

    def _format_telegram_message(self, alert: Alert) -> str:
        """Format alert for Telegram."""
        emoji = self.SEVERITY_EMOJI.get(alert.severity, "❓")
        lines = [
            f"{emoji} *API Canary Alert*",
            "",
            f"*Channel:* `{alert.channel}`",
            f"*Severity:* {alert.severity.value.upper()}",
            f"*Title:* {alert.title}",
        ]

        if alert.endpoint:
            lines.append(f"*Endpoint:* `{alert.endpoint}`")

        if alert.error_code:
            lines.append(f"*Error Code:* {alert.error_code}")

        lines.extend([
            "",
            f"*Message:* {alert.message}",
            "",
            f"_Time: {alert.timestamp.strftime('%Y-%m-%d %H:%M:%S')}_",
        ])

        return "\n".join(lines)

    async def send_telegram(self, alert: Alert) -> bool:
        """Send alert to Telegram."""
        if not self.telegram_bot_token or not self.telegram_chat_id:
            return False

        url = f"https://api.telegram.org/bot{self.telegram_bot_token}/sendMessage"
        payload = {
            "chat_id": self.telegram_chat_id,
            "text": self._format_telegram_message(alert),
            "parse_mode": "Markdown",
        }

        try:
            async with httpx.AsyncClient(timeout=10.0) as client:
                response = await client.post(url, json=payload)
                if response.status_code == 200:
                    logger.info(f"Alert sent to Telegram: {alert.title}")
                    return True
                else:
                    logger.error(f"Telegram API error: {response.status_code} - {response.text}")
                    return False
        except Exception as e:
            logger.error(f"Failed to send Telegram alert: {e}")
            return False

    async def send_n8n(self, alert: Alert) -> bool:
        """Send alert to n8n webhook."""
        if not self.n8n_webhook_url:
            return False

        payload = {
            "source": "api-canary",
            "channel": alert.channel,
            "severity": alert.severity.value,
            "title": alert.title,
            "message": alert.message,
            "endpoint": alert.endpoint,
            "error_code": alert.error_code,
            "timestamp": alert.timestamp.isoformat(),
        }

        try:
            async with httpx.AsyncClient(timeout=10.0) as client:
                response = await client.post(self.n8n_webhook_url, json=payload)
                if response.status_code < 400:
                    logger.info(f"Alert sent to n8n: {alert.title}")
                    return True
                else:
                    logger.error(f"n8n webhook error: {response.status_code}")
                    return False
        except Exception as e:
            logger.error(f"Failed to send n8n alert: {e}")
            return False

    async def send(self, alert: Alert, force: bool = False) -> bool:
        """Send alert to all configured channels."""
        # Check cooldown unless forced
        if not force and self._is_on_cooldown(alert):
            logger.debug(f"Alert on cooldown: {alert.title}")
            return False

        # Send to all channels
        tasks = []
        if self.telegram_bot_token and self.telegram_chat_id:
            tasks.append(self.send_telegram(alert))
        if self.n8n_webhook_url:
            tasks.append(self.send_n8n(alert))

        if not tasks:
            logger.warning("No alert channels configured")
            return False

        results = await asyncio.gather(*tasks, return_exceptions=True)
        success = any(r is True for r in results)

        if success:
            self._mark_sent(alert)

        return success

    # Convenience methods

    async def alert_endpoint_down(self, channel: str, endpoint: str, error: str):
        """Alert when endpoint is down."""
        await self.send(Alert(
            channel=channel,
            severity=AlertSeverity.CRITICAL,
            title="Endpoint Down",
            message=error,
            endpoint=endpoint,
        ))

    async def alert_health_degraded(self, channel: str, health_score: int):
        """Alert when health score is low."""
        await self.send(Alert(
            channel=channel,
            severity=AlertSeverity.WARNING,
            title="Health Degraded",
            message=f"Health score dropped to {health_score}%",
        ))

    async def alert_consecutive_failures(self, channel: str, endpoint: str, count: int):
        """Alert when endpoint has consecutive failures."""
        await self.send(Alert(
            channel=channel,
            severity=AlertSeverity.CRITICAL,
            title="Consecutive Failures",
            message=f"{count} consecutive failures on {endpoint}",
            endpoint=endpoint,
        ))

    async def alert_api_error(self, channel: str, endpoint: str, status_code: int, error: str):
        """Alert when API returns error."""
        severity = AlertSeverity.CRITICAL if status_code >= 500 else AlertSeverity.ERROR
        await self.send(Alert(
            channel=channel,
            severity=severity,
            title=f"API Error {status_code}",
            message=error,
            endpoint=endpoint,
            error_code=status_code,
        ))

    async def alert_channel_recovered(self, channel: str):
        """Alert when channel recovers."""
        await self.send(Alert(
            channel=channel,
            severity=AlertSeverity.INFO,
            title="Channel Recovered",
            message=f"{channel} is now healthy",
        ), force=True)
