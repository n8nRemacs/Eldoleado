"""
Avito Interceptor - CDP-based traffic interception for Avito.

This module intercepts all network traffic between the browser and Avito,
including WebSocket frames, to extract hash_id and capture real-time messages.
"""

import asyncio
import json
import re
import logging
from datetime import datetime
from typing import Optional, Callable, Dict, Any, List
from pathlib import Path

from playwright.async_api import Page, CDPSession

from .models import AvitoMessage, InterceptedTraffic

logger = logging.getLogger(__name__)


class AvitoInterceptor:
    """
    CDP-based interceptor for Avito traffic.

    Captures:
    - All HTTP requests/responses
    - WebSocket creation (extracts hash_id from URL)
    - WebSocket frames (real-time messages)
    """

    def __init__(
        self,
        account_id: str,
        on_message: Optional[Callable[[AvitoMessage], None]] = None,
        on_hash_id: Optional[Callable[[str], None]] = None,
        debug: bool = False
    ):
        self.account_id = account_id
        self.on_message = on_message
        self.on_hash_id = on_hash_id
        self.debug = debug

        self.cdp: Optional[CDPSession] = None
        self.hash_id: Optional[str] = None
        self.user_id: Optional[str] = None
        self.traffic_log: List[InterceptedTraffic] = []
        self._ws_request_ids: Dict[str, str] = {}  # requestId -> url

    async def attach(self, page: Page) -> None:
        """Attach CDP session to page and start interception."""
        self.cdp = await page.context.new_cdp_session(page)

        # Enable network interception
        await self.cdp.send("Network.enable")

        # Set up event handlers
        self.cdp.on("Network.webSocketCreated", self._on_ws_created)
        self.cdp.on("Network.webSocketFrameReceived", self._on_ws_frame_received)
        self.cdp.on("Network.webSocketFrameSent", self._on_ws_frame_sent)
        self.cdp.on("Network.webSocketClosed", self._on_ws_closed)

        if self.debug:
            self.cdp.on("Network.requestWillBeSent", self._on_request)
            self.cdp.on("Network.responseReceived", self._on_response)

        logger.info(f"[{self.account_id}] CDP interception attached")

    async def detach(self) -> None:
        """Detach CDP session."""
        if self.cdp:
            try:
                await self.cdp.detach()
            except Exception as e:
                logger.warning(f"[{self.account_id}] Error detaching CDP: {e}")
            self.cdp = None

    def _on_ws_created(self, event: Dict[str, Any]) -> None:
        """Handle WebSocket creation - extract hash_id."""
        url = event.get("url", "")
        request_id = event.get("requestId", "")

        self._ws_request_ids[request_id] = url

        logger.info(f"[{self.account_id}] WebSocket created: {url[:100]}...")

        # Extract hash_id from socket.avito.ru URL
        if "socket.avito.ru" in url:
            match = re.search(r"my_hash_id=([^&]+)", url)
            if match:
                self.hash_id = match.group(1)
                logger.info(f"[{self.account_id}] Extracted hash_id: {self.hash_id}")

                if self.on_hash_id:
                    self.on_hash_id(self.hash_id)

            # Also try to extract user_id
            match = re.search(r"my_user_id=(\d+)", url)
            if match:
                self.user_id = match.group(1)
                logger.info(f"[{self.account_id}] Extracted user_id: {self.user_id}")

        self._log_traffic("ws_created", url=url)

    def _on_ws_frame_received(self, event: Dict[str, Any]) -> None:
        """Handle incoming WebSocket frame - parse messages."""
        request_id = event.get("requestId", "")
        url = self._ws_request_ids.get(request_id, "")

        response = event.get("response", {})
        payload = response.get("payloadData", "")

        # Only process Avito socket frames
        if "socket.avito.ru" not in url:
            return

        self._log_traffic("ws_frame_in", url=url, body=payload[:500] if payload else None)

        # Try to parse as JSON
        try:
            data = json.loads(payload)
            self._process_avito_message(data)
        except json.JSONDecodeError:
            # Not JSON, might be binary or ping/pong
            if self.debug:
                logger.debug(f"[{self.account_id}] Non-JSON WS frame: {payload[:100]}")

    def _on_ws_frame_sent(self, event: Dict[str, Any]) -> None:
        """Handle outgoing WebSocket frame."""
        if not self.debug:
            return

        request_id = event.get("requestId", "")
        url = self._ws_request_ids.get(request_id, "")

        response = event.get("response", {})
        payload = response.get("payloadData", "")

        self._log_traffic("ws_frame_out", url=url, body=payload[:500] if payload else None)

    def _on_ws_closed(self, event: Dict[str, Any]) -> None:
        """Handle WebSocket close."""
        request_id = event.get("requestId", "")
        url = self._ws_request_ids.pop(request_id, "unknown")

        logger.info(f"[{self.account_id}] WebSocket closed: {url[:50]}...")
        self._log_traffic("ws_closed", url=url)

    def _on_request(self, event: Dict[str, Any]) -> None:
        """Handle HTTP request (debug mode)."""
        request = event.get("request", {})
        url = request.get("url", "")
        method = request.get("method", "")

        if "avito.ru" in url:
            self._log_traffic("request", url=url, method=method)

    def _on_response(self, event: Dict[str, Any]) -> None:
        """Handle HTTP response (debug mode)."""
        response = event.get("response", {})
        url = response.get("url", "")
        status = response.get("status", 0)

        if "avito.ru" in url:
            self._log_traffic("response", url=url, status=status)

    def _process_avito_message(self, data: Dict[str, Any]) -> None:
        """Process Avito WebSocket message."""
        # Avito uses different message formats
        # Try to detect message type

        msg_type = data.get("type") or data.get("t")
        payload = data.get("payload") or data.get("p") or data

        logger.debug(f"[{self.account_id}] WS message type: {msg_type}")

        # New message notification
        if msg_type in ("message", "new_message", "msg"):
            self._handle_new_message(payload)

        # Channel update
        elif msg_type in ("channel_update", "ch_upd"):
            self._handle_channel_update(payload)

        # Typing indicator
        elif msg_type in ("typing", "user_typing"):
            pass  # Ignore typing

        # Ping/pong
        elif msg_type in ("ping", "pong", "ack"):
            pass  # Ignore heartbeat

        else:
            if self.debug:
                logger.debug(f"[{self.account_id}] Unknown WS message: {json.dumps(data)[:200]}")

    def _handle_new_message(self, payload: Dict[str, Any]) -> None:
        """Handle new message from WebSocket."""
        try:
            # Extract message fields (format may vary)
            msg_id = str(payload.get("id") or payload.get("message_id") or "")
            chat_id = str(payload.get("chat_id") or payload.get("channel_id") or "")
            author_id = str(payload.get("author_id") or payload.get("user_id") or "")

            # Get text content
            content = payload.get("content") or payload.get("text") or {}
            if isinstance(content, dict):
                text = content.get("text", "")
            else:
                text = str(content)

            msg_type = payload.get("type", "text")

            # Parse timestamp
            created_raw = payload.get("created") or payload.get("timestamp")
            if isinstance(created_raw, (int, float)):
                created = datetime.fromtimestamp(created_raw)
            else:
                created = datetime.now()

            # Determine direction
            if self.user_id and author_id == self.user_id:
                direction = "out"
            else:
                direction = "in"

            message = AvitoMessage(
                id=msg_id,
                chat_id=chat_id,
                author_id=author_id,
                text=text,
                type=msg_type,
                created=created,
                direction=direction,
                raw=payload
            )

            logger.info(
                f"[{self.account_id}] New message: chat={chat_id}, "
                f"from={author_id}, text={text[:50]}..."
            )

            if self.on_message:
                self.on_message(message)

        except Exception as e:
            logger.error(f"[{self.account_id}] Error parsing message: {e}")
            logger.debug(f"Payload: {payload}")

    def _handle_channel_update(self, payload: Dict[str, Any]) -> None:
        """Handle channel/chat update."""
        # This might contain last_message
        last_message = payload.get("last_message")
        if last_message:
            self._handle_new_message(last_message)

    def _log_traffic(
        self,
        traffic_type: str,
        url: Optional[str] = None,
        method: Optional[str] = None,
        status: Optional[int] = None,
        body: Optional[str] = None
    ) -> None:
        """Log intercepted traffic."""
        entry = InterceptedTraffic(
            timestamp=datetime.now(),
            type=traffic_type,
            url=url,
            method=method,
            status=status,
            body=body
        )
        self.traffic_log.append(entry)

        # Keep only last 1000 entries
        if len(self.traffic_log) > 1000:
            self.traffic_log = self.traffic_log[-1000:]

    def get_traffic_log(self, limit: int = 100) -> List[InterceptedTraffic]:
        """Get recent traffic log."""
        return self.traffic_log[-limit:]

    async def get_cookies(self) -> List[Dict[str, Any]]:
        """Get all cookies via CDP."""
        if not self.cdp:
            return []

        result = await self.cdp.send("Network.getAllCookies")
        return result.get("cookies", [])
