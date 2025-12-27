"""
Message Router - routes messages to webhooks.

Sends incoming Avito messages to n8n webhook for processing.
"""

import asyncio
import logging
from datetime import datetime
from typing import Optional, Dict, Any

import aiohttp

from .models import AvitoMessage, WebhookPayload

logger = logging.getLogger(__name__)


class MessageRouter:
    """Routes Avito messages to webhooks."""

    def __init__(self, webhook_url: Optional[str] = None):
        self.webhook_url = webhook_url
        self.session: Optional[aiohttp.ClientSession] = None
        self._queue: asyncio.Queue = asyncio.Queue()
        self._worker_task: Optional[asyncio.Task] = None

    async def start(self) -> None:
        """Start message router."""
        self.session = aiohttp.ClientSession()
        self._worker_task = asyncio.create_task(self._worker())
        logger.info("Message router started")

    async def stop(self) -> None:
        """Stop message router."""
        if self._worker_task:
            self._worker_task.cancel()
            try:
                await self._worker_task
            except asyncio.CancelledError:
                pass

        if self.session:
            await self.session.close()

        logger.info("Message router stopped")

    async def route_message(self, account_id: str, message: AvitoMessage) -> None:
        """Route message to webhook."""
        payload = WebhookPayload(
            event="new_message",
            account_id=account_id,
            timestamp=datetime.now(),
            data={
                "message_id": message.id,
                "chat_id": message.chat_id,
                "author_id": message.author_id,
                "text": message.text,
                "type": message.type,
                "direction": message.direction,
                "created": message.created.isoformat(),
            }
        )

        await self._queue.put(payload)

    async def send_auth_required(self, account_id: str, reason: str = "") -> None:
        """Notify that account needs re-authentication."""
        payload = WebhookPayload(
            event="auth_required",
            account_id=account_id,
            data={"reason": reason}
        )

        await self._queue.put(payload)

    async def send_error(self, account_id: str, error: str) -> None:
        """Send error notification."""
        payload = WebhookPayload(
            event="error",
            account_id=account_id,
            data={"error": error}
        )

        await self._queue.put(payload)

    async def _worker(self) -> None:
        """Background worker that sends webhooks."""
        while True:
            try:
                payload = await self._queue.get()
                await self._send_webhook(payload)
                self._queue.task_done()
            except asyncio.CancelledError:
                break
            except Exception as e:
                logger.error(f"Error in message router worker: {e}")

    async def _send_webhook(self, payload: WebhookPayload) -> None:
        """Send webhook to configured URL."""
        if not self.webhook_url or not self.session:
            logger.debug(f"Webhook not configured, skipping: {payload.event}")
            return

        try:
            async with self.session.post(
                self.webhook_url,
                json=payload.model_dump(mode="json"),
                timeout=aiohttp.ClientTimeout(total=10)
            ) as response:
                if response.status == 200:
                    logger.info(f"Webhook sent: {payload.event} for {payload.account_id}")
                else:
                    logger.warning(f"Webhook failed: {response.status}")

        except asyncio.TimeoutError:
            logger.warning(f"Webhook timeout: {self.webhook_url}")
        except Exception as e:
            logger.error(f"Webhook error: {e}")
