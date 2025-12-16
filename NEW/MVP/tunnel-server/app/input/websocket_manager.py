"""
WebSocket Manager - Manages tunnel connections from phones
"""

import asyncio
import json
import logging
import uuid
from datetime import datetime
from typing import Dict, Optional, Callable, Awaitable

from fastapi import WebSocket, WebSocketDisconnect
from pydantic import ValidationError

from app.models import TunnelCommand, TunnelResponse, TunnelIncoming, TunnelStatus

logger = logging.getLogger("WebSocketManager")


class TunnelConnection:
    """Represents a single tunnel connection (phone)"""

    def __init__(self, websocket: WebSocket, server_id: str, secret: str):
        self.websocket = websocket
        self.server_id = server_id
        self.secret = secret
        self.connected_at = datetime.utcnow()
        self.services: list[str] = []
        self.stats = {
            "messages_received": 0,
            "messages_sent": 0,
            "errors": 0
        }
        self._pending_requests: Dict[str, asyncio.Future] = {}

    async def send(self, data: dict) -> None:
        """Send data to phone"""
        await self.websocket.send_json(data)
        self.stats["messages_sent"] += 1

    async def send_command(self, command: TunnelCommand, timeout: float = 30.0) -> TunnelResponse:
        """Send command and wait for response"""
        future = asyncio.get_event_loop().create_future()
        self._pending_requests[command.id] = future

        try:
            await self.send(command.model_dump())
            response = await asyncio.wait_for(future, timeout=timeout)
            return TunnelResponse(**response)
        except asyncio.TimeoutError:
            self._pending_requests.pop(command.id, None)
            raise TimeoutError(f"Command {command.id} timed out")

    def handle_response(self, data: dict) -> bool:
        """Handle response for pending request"""
        msg_id = data.get("id")
        if msg_id and msg_id in self._pending_requests:
            future = self._pending_requests.pop(msg_id)
            if not future.done():
                future.set_result(data)
            return True
        return False

    @property
    def status(self) -> TunnelStatus:
        return TunnelStatus(
            server_id=self.server_id,
            connected=True,
            connected_at=self.connected_at,
            services=self.services,
            stats=self.stats
        )


class WebSocketManager:
    """
    Manages all WebSocket tunnel connections.
    Routes messages to/from phones.
    """

    def __init__(self):
        self.connections: Dict[str, TunnelConnection] = {}
        self._incoming_handler: Optional[Callable[[str, dict], Awaitable[None]]] = None

    def set_incoming_handler(self, handler: Callable[[str, dict], Awaitable[None]]):
        """Set handler for incoming messages from phones"""
        self._incoming_handler = handler

    async def connect(self, websocket: WebSocket, server_id: str, secret: str) -> TunnelConnection:
        """Accept new tunnel connection"""
        await websocket.accept()

        # Close existing connection if any
        if server_id in self.connections:
            old = self.connections[server_id]
            try:
                await old.websocket.close(code=1000, reason="New connection")
            except:
                pass

        conn = TunnelConnection(websocket, server_id, secret)
        self.connections[server_id] = conn

        logger.info(f"Tunnel connected: {server_id}")
        return conn

    def disconnect(self, server_id: str):
        """Remove tunnel connection"""
        if server_id in self.connections:
            del self.connections[server_id]
            logger.info(f"Tunnel disconnected: {server_id}")

    def get_connection(self, server_id: str) -> Optional[TunnelConnection]:
        """Get connection by server_id"""
        return self.connections.get(server_id)

    def get_all_connections(self) -> Dict[str, TunnelStatus]:
        """Get status of all connections"""
        return {
            server_id: conn.status
            for server_id, conn in self.connections.items()
        }

    async def handle_message(self, server_id: str, data: dict):
        """Handle incoming message from phone"""
        conn = self.connections.get(server_id)
        if not conn:
            return

        conn.stats["messages_received"] += 1

        # Check if it's a response to pending request
        if conn.handle_response(data):
            return

        # Handle specific actions
        action = data.get("action", "")

        if action == "hello":
            # Registration message
            conn.services = data.get("services", [])
            logger.info(f"Tunnel {server_id} registered with services: {conn.services}")

        elif action == "incoming":
            # Incoming message from client
            if self._incoming_handler:
                await self._incoming_handler(server_id, data)

        elif action == "pong":
            # Ping response, ignore
            pass

        else:
            logger.warning(f"Unknown action from {server_id}: {action}")

    async def send_to_tunnel(
        self,
        server_id: str,
        service: str,
        method: str,
        path: str,
        body: dict = None,
        headers: dict = None,
        timeout: float = 30.0
    ) -> TunnelResponse:
        """Send HTTP request through tunnel"""
        conn = self.connections.get(server_id)
        if not conn:
            raise ConnectionError(f"Tunnel {server_id} not connected")

        command = TunnelCommand(
            id=str(uuid.uuid4()),
            action="http_request",
            service=service,
            method=method,
            path=path,
            headers=headers or {},
            body=body
        )

        return await conn.send_command(command, timeout)

    async def broadcast(self, data: dict):
        """Send to all connected tunnels"""
        for conn in self.connections.values():
            try:
                await conn.send(data)
            except Exception as e:
                logger.error(f"Broadcast error to {conn.server_id}: {e}")

    async def ping_all(self):
        """Ping all connections"""
        for server_id, conn in list(self.connections.items()):
            try:
                command = TunnelCommand(
                    id=str(uuid.uuid4()),
                    action="ping"
                )
                await conn.send_command(command, timeout=10.0)
            except Exception as e:
                logger.warning(f"Ping failed for {server_id}: {e}")


# Global manager instance
manager = WebSocketManager()
