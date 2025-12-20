"""API Canary Service - Monitor all MCP channel endpoints.

FastAPI server for monitoring availability and health of all MCP channels:
- WhatsApp, Telegram, VK, MAX, Avito

Provides:
- Periodic health checks (every 1 min)
- API endpoint checks (every 5 min)
- Alerts on failures (Telegram + n8n)
- Status dashboard (/status)
"""

import logging
from contextlib import asynccontextmanager
from datetime import datetime
from typing import Dict, Optional

from fastapi import FastAPI, HTTPException
from fastapi.responses import JSONResponse

from config import settings
from alerter import Alerter, Alert, AlertSeverity
from scheduler import CanaryScheduler
from monitors import (
    WhatsAppMonitor,
    TelegramMonitor,
    VKMonitor,
    MaxMonitor,
    AvitoMonitor,
    CheckStatus,
)

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# Initialize components
alerter = Alerter(
    telegram_bot_token=settings.ALERT_TELEGRAM_BOT_TOKEN,
    telegram_chat_id=settings.ALERT_TELEGRAM_CHAT_ID,
    n8n_webhook_url=settings.ALERT_N8N_WEBHOOK_URL,
    cooldown_seconds=settings.ALERT_COOLDOWN,
)

scheduler = CanaryScheduler()

# Initialize monitors
monitors: Dict[str, object] = {}


def init_monitors():
    """Initialize all channel monitors."""
    global monitors

    # WhatsApp
    monitors["whatsapp"] = WhatsAppMonitor(
        base_url=settings.WHATSAPP_URL,
        api_key=settings.WHATSAPP_API_KEY,
        session_id=settings.WA_SERVICE_SESSION_ID,
    )

    # Telegram
    monitors["telegram"] = TelegramMonitor(
        base_url=settings.TELEGRAM_URL,
        api_key=settings.TELEGRAM_API_KEY,
        bot_token=settings.TG_SERVICE_BOT_TOKEN,
        chat_id=settings.TG_SERVICE_CHAT_ID,
    )

    # VK (with optional proxy for external API)
    monitors["vk"] = VKMonitor(
        base_url=settings.VK_URL,
        api_key=settings.VK_API_KEY,
        group_hash=settings.VK_SERVICE_GROUP_HASH,
        proxy_url=settings.PROXY_URL if settings.USE_PROXY_FOR_EXTERNAL else None,
    )

    # MAX
    monitors["max"] = MaxMonitor(
        base_url=settings.MAX_URL,
        api_key=settings.MAX_API_KEY,
        access_token=settings.MAX_SERVICE_ACCESS_TOKEN,
    )

    # Avito (with optional proxy for external API)
    monitors["avito"] = AvitoMonitor(
        base_url=settings.AVITO_URL,
        api_key=settings.AVITO_API_KEY,
        user_hash=settings.AVITO_SERVICE_USER_HASH,
        proxy_url=settings.PROXY_URL if settings.USE_PROXY_FOR_EXTERNAL else None,
    )

    logger.info(f"Initialized {len(monitors)} channel monitors")


# Track previous statuses for recovery detection
_previous_statuses: Dict[str, CheckStatus] = {}


async def run_health_checks():
    """Run health checks on all channels."""
    logger.info("Running health checks...")

    for name, monitor in monitors.items():
        try:
            status = await monitor.run_health_check()

            # Check for problems
            if status.status in [CheckStatus.ERROR, CheckStatus.TIMEOUT]:
                await alerter.alert_endpoint_down(
                    channel=name,
                    endpoint="/health/extended",
                    error=f"Status: {status.status.value}"
                )
            elif status.health_score < 50:
                await alerter.alert_health_degraded(name, status.health_score)

            # Check for recovery
            prev_status = _previous_statuses.get(name)
            if prev_status in [CheckStatus.ERROR, CheckStatus.TIMEOUT] and status.status == CheckStatus.OK:
                await alerter.alert_channel_recovered(name)

            _previous_statuses[name] = status.status

        except Exception as e:
            logger.error(f"Health check failed for {name}: {e}")
            await alerter.alert_endpoint_down(name, "/health/extended", str(e))


async def run_api_checks():
    """Run API checks on all channels."""
    logger.info("Running API checks...")

    for name, monitor in monitors.items():
        try:
            status = await monitor.run_api_checks()

            # Check for consecutive failures
            if status.consecutive_failures >= settings.CONSECUTIVE_FAILURES_THRESHOLD:
                # Find the failing endpoint
                for endpoint, result in status.endpoints.items():
                    if result.status in [CheckStatus.ERROR, CheckStatus.TIMEOUT]:
                        await alerter.alert_consecutive_failures(
                            name, endpoint, status.consecutive_failures
                        )
                        break

            # Check for API errors
            for endpoint, result in status.endpoints.items():
                if result.status == CheckStatus.ERROR and result.status_code:
                    await alerter.alert_api_error(
                        name, endpoint, result.status_code, result.error or "Unknown error"
                    )

        except Exception as e:
            logger.error(f"API check failed for {name}: {e}")


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan manager."""
    logger.info("Starting API Canary Service...")

    # Initialize monitors
    init_monitors()

    # Schedule health checks
    scheduler.add_health_check(
        name="all_channels",
        func=run_health_checks,
        interval_seconds=settings.HEALTH_CHECK_INTERVAL,
    )

    # Schedule API checks
    scheduler.add_api_check(
        name="all_channels",
        func=run_api_checks,
        interval_seconds=settings.API_CHECK_INTERVAL,
    )

    # Start scheduler
    scheduler.start()

    # Run initial checks
    await run_health_checks()

    logger.info("API Canary Service started")

    yield

    # Cleanup
    scheduler.stop()
    logger.info("API Canary Service stopped")


app = FastAPI(
    title="API Canary Service",
    description="Monitor availability and health of all MCP channels",
    version="1.0.0",
    lifespan=lifespan,
)


# ==================== Endpoints ====================

@app.get("/")
async def root():
    """Root endpoint."""
    return {
        "service": "api-canary",
        "version": "1.0.0",
        "status": "running",
    }


@app.get("/health")
async def health():
    """Health check for the canary service itself."""
    return {
        "status": "healthy",
        "service": "api-canary",
        "monitors": len(monitors),
        "timestamp": datetime.now().isoformat(),
    }


@app.get("/status")
async def get_status():
    """Get status of all monitored channels."""
    overall_status = CheckStatus.OK

    channels = {}
    for name, monitor in monitors.items():
        status = monitor.status
        channels[name] = status.to_dict()

        if status.status in [CheckStatus.ERROR, CheckStatus.TIMEOUT]:
            overall_status = CheckStatus.ERROR
        elif status.status == CheckStatus.WARNING and overall_status != CheckStatus.ERROR:
            overall_status = CheckStatus.WARNING

    return {
        "timestamp": datetime.now().isoformat(),
        "overall_status": overall_status.value,
        "channels": channels,
    }


@app.get("/status/{channel}")
async def get_channel_status(channel: str):
    """Get detailed status for a specific channel."""
    if channel not in monitors:
        raise HTTPException(status_code=404, detail=f"Channel '{channel}' not found")

    monitor = monitors[channel]
    return monitor.status.to_dict()


@app.post("/test/{channel}")
async def test_channel(channel: str):
    """Force immediate test of a specific channel."""
    if channel not in monitors:
        raise HTTPException(status_code=404, detail=f"Channel '{channel}' not found")

    monitor = monitors[channel]
    status = await monitor.run_api_checks()

    return {
        "success": True,
        "channel": channel,
        "status": status.to_dict(),
    }


@app.post("/test/all")
async def test_all():
    """Force immediate test of all channels."""
    await run_api_checks()

    return {
        "success": True,
        "message": "All channels tested",
        "channels": {name: m.status.to_dict() for name, m in monitors.items()},
    }


@app.get("/history")
async def get_history(channel: Optional[str] = None, limit: int = 100):
    """Get check history."""
    if channel:
        if channel not in monitors:
            raise HTTPException(status_code=404, detail=f"Channel '{channel}' not found")
        history = monitors[channel].check_history[-limit:]
        return {
            "channel": channel,
            "count": len(history),
            "history": [r.to_dict() for r in history],
        }

    # All channels
    all_history = {}
    for name, monitor in monitors.items():
        all_history[name] = [r.to_dict() for r in monitor.check_history[-limit:]]

    return {
        "channels": list(monitors.keys()),
        "history": all_history,
    }


@app.get("/jobs")
async def get_jobs():
    """Get scheduled jobs."""
    return {
        "jobs": scheduler.get_jobs(),
    }


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host=settings.CANARY_HOST,
        port=settings.CANARY_PORT,
        reload=True,
    )
