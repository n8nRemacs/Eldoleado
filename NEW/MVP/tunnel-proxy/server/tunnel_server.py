#!/usr/bin/env python3
"""
Tunnel Server - WebSocket server for mobile HTTP proxy.

Accepts WebSocket connections from Android apps (TunnelService),
provides HTTP proxy API for MCP servers.

Architecture:
- Android app connects via WebSocket
- MCP servers send HTTP requests to this server
- This server forwards requests to Android via WebSocket
- Android executes requests with mobile IP + TLS fingerprint
- Response comes back through the same path

Security:
- Bearer token authentication for WebSocket
- API key authentication for HTTP API
- Device tracking and rate limiting
"""

import asyncio
import json
import logging
import os
import uuid
import time
from typing import Dict, Optional, Set
from dataclasses import dataclass, field
from asyncio import Future

from aiohttp import web, WSMsgType
import aiohttp

# Config
WS_PORT = int(os.getenv("WS_PORT", "8765"))
HTTP_PORT = int(os.getenv("HTTP_PORT", "8080"))
TUNNEL_SECRET = os.getenv("TUNNEL_SECRET", "change_me_in_production")
API_KEY = os.getenv("API_KEY", "change_me_in_production")
LOG_LEVEL = os.getenv("LOG_LEVEL", "INFO")
REQUEST_TIMEOUT = int(os.getenv("REQUEST_TIMEOUT", "60"))

logging.basicConfig(
    level=getattr(logging, LOG_LEVEL),
    format="%(asctime)s [%(levelname)s] %(name)s: %(message)s"
)
log = logging.getLogger("tunnel-server")


@dataclass
class Device:
    """Connected mobile device."""
    device_id: str
    operator_id: str
    websocket: web.WebSocketResponse
    device_model: str = ""
    android_version: str = ""
    connected_at: float = field(default_factory=time.time)
    total_requests: int = 0
    active_requests: int = 0
    last_activity: float = field(default_factory=time.time)


@dataclass
class PendingRequest:
    """Request waiting for response from device."""
    request_id: str
    device_id: str
    future: Future
    created_at: float = field(default_factory=time.time)


# Global state
devices: Dict[str, Device] = {}  # device_id -> Device
pending_requests: Dict[str, PendingRequest] = {}  # request_id -> PendingRequest


# ============ WebSocket Handler (for Android apps) ============

async def websocket_handler(request: web.Request) -> web.WebSocketResponse:
    """Handle WebSocket connection from Android TunnelService."""

    # Check authorization
    auth_header = request.headers.get("Authorization", "")
    if not auth_header.startswith("Bearer ") or auth_header[7:] != TUNNEL_SECRET:
        log.warning(f"Unauthorized WebSocket connection from {request.remote}")
        return web.Response(status=401, text="Unauthorized")

    # Get device info from query params
    device_id = request.query.get("device_id", str(uuid.uuid4()))
    operator_id = request.query.get("operator_id", "unknown")
    device_model = request.query.get("device_model", "unknown")
    android_version = request.query.get("android_version", "unknown")

    # Create WebSocket
    ws = web.WebSocketResponse(heartbeat=30)
    await ws.prepare(request)

    # Register device
    device = Device(
        device_id=device_id,
        operator_id=operator_id,
        websocket=ws,
        device_model=device_model,
        android_version=android_version
    )
    devices[device_id] = device

    log.info(f"Device connected: {device_id} ({device_model}, Android {android_version})")

    try:
        async for msg in ws:
            if msg.type == WSMsgType.TEXT:
                await handle_device_message(device, msg.data)
            elif msg.type == WSMsgType.ERROR:
                log.error(f"WebSocket error from {device_id}: {ws.exception()}")
                break
    except Exception as e:
        log.exception(f"Error handling device {device_id}: {e}")
    finally:
        # Cleanup
        devices.pop(device_id, None)

        # Cancel pending requests for this device
        for req_id, req in list(pending_requests.items()):
            if req.device_id == device_id:
                req.future.set_exception(Exception("Device disconnected"))
                pending_requests.pop(req_id, None)

        log.info(f"Device disconnected: {device_id}")

    return ws


async def handle_device_message(device: Device, data: str):
    """Handle message from Android device."""
    try:
        msg = json.loads(data)
        action = msg.get("action", "")

        # Device info update
        if action == "device_info":
            device.device_model = msg.get("device_model", device.device_model)
            device.android_version = msg.get("android_version", device.android_version)
            log.debug(f"Device info updated: {device.device_id}")
            return

        # Pong response
        if action == "pong":
            device.last_activity = time.time()
            return

        # Status response
        if action == "status":
            device.last_activity = time.time()
            return

        # HTTP response
        request_id = msg.get("id")
        if request_id and request_id in pending_requests:
            pending = pending_requests.pop(request_id)
            device.active_requests -= 1
            device.last_activity = time.time()

            if "error" in msg:
                pending.future.set_exception(Exception(msg["error"]))
            else:
                pending.future.set_result(msg)
            return

        log.warning(f"Unknown message from {device.device_id}: {action}")

    except json.JSONDecodeError:
        log.error(f"Invalid JSON from {device.device_id}")
    except Exception as e:
        log.exception(f"Error handling message from {device.device_id}: {e}")


# ============ HTTP Proxy API (for MCP servers) ============

async def proxy_handler(request: web.Request) -> web.Response:
    """
    HTTP Proxy endpoint for MCP servers.

    Usage:
        POST /proxy
        {
            "url": "https://api.avito.ru/...",
            "method": "GET",
            "headers": {...},
            "body": "...",
            "device_id": "optional - specific device",
            "timeout": 30
        }

    Or as actual proxy:
        GET /proxy/https://api.avito.ru/...
    """

    # Check API key
    api_key = request.headers.get("X-API-Key", "")
    if api_key != API_KEY:
        return web.json_response({"error": "Invalid API key"}, status=401)

    # No devices connected
    if not devices:
        return web.json_response(
            {"error": "No devices connected"},
            status=503
        )

    try:
        data = await request.json()
    except:
        return web.json_response({"error": "Invalid JSON"}, status=400)

    url = data.get("url")
    if not url:
        return web.json_response({"error": "Missing URL"}, status=400)

    method = data.get("method", "GET").upper()
    headers = data.get("headers", {})
    body = data.get("body", "")
    body_base64 = data.get("body_base64", False)
    timeout = data.get("timeout", REQUEST_TIMEOUT)
    device_id = data.get("device_id")

    # Select device
    if device_id and device_id in devices:
        device = devices[device_id]
    else:
        # Select device with least active requests
        device = min(devices.values(), key=lambda d: d.active_requests)

    # Send request to device
    try:
        response = await send_request_to_device(
            device=device,
            url=url,
            method=method,
            headers=headers,
            body=body,
            body_base64=body_base64,
            timeout=timeout
        )

        return web.json_response(response)

    except asyncio.TimeoutError:
        return web.json_response(
            {"error": "Request timeout", "status": 504},
            status=504
        )
    except Exception as e:
        return web.json_response(
            {"error": str(e), "status": 502},
            status=502
        )


async def send_request_to_device(
    device: Device,
    url: str,
    method: str,
    headers: dict,
    body: str,
    body_base64: bool,
    timeout: int
) -> dict:
    """Send HTTP request to device and wait for response."""

    request_id = str(uuid.uuid4())

    # Create pending request
    future = asyncio.get_event_loop().create_future()
    pending = PendingRequest(
        request_id=request_id,
        device_id=device.device_id,
        future=future
    )
    pending_requests[request_id] = pending

    # Update device stats
    device.total_requests += 1
    device.active_requests += 1

    # Build request message
    message = {
        "id": request_id,
        "action": "http",
        "method": method,
        "url": url,
        "headers": headers,
        "body": body,
        "body_base64": body_base64,
        "timeout": timeout
    }

    try:
        # Send to device
        await device.websocket.send_json(message)

        # Wait for response
        response = await asyncio.wait_for(future, timeout=timeout + 5)
        return response

    except asyncio.TimeoutError:
        pending_requests.pop(request_id, None)
        device.active_requests -= 1
        raise

    except Exception as e:
        pending_requests.pop(request_id, None)
        device.active_requests -= 1
        raise


# ============ Status & Health Endpoints ============

async def health_handler(request: web.Request) -> web.Response:
    """Health check endpoint."""
    return web.json_response({
        "status": "ok",
        "service": "tunnel-server",
        "devices_connected": len(devices),
        "pending_requests": len(pending_requests)
    })


async def status_handler(request: web.Request) -> web.Response:
    """Detailed status endpoint."""

    # Check API key for detailed status
    api_key = request.headers.get("X-API-Key", "")
    if api_key != API_KEY:
        return web.json_response({"error": "Invalid API key"}, status=401)

    devices_info = []
    for device in devices.values():
        devices_info.append({
            "device_id": device.device_id,
            "operator_id": device.operator_id,
            "device_model": device.device_model,
            "android_version": device.android_version,
            "connected_at": device.connected_at,
            "total_requests": device.total_requests,
            "active_requests": device.active_requests,
            "last_activity": device.last_activity
        })

    return web.json_response({
        "status": "ok",
        "devices": devices_info,
        "pending_requests": len(pending_requests)
    })


async def devices_handler(request: web.Request) -> web.Response:
    """List connected devices."""
    return web.json_response({
        "devices": [
            {
                "device_id": d.device_id,
                "device_model": d.device_model,
                "active_requests": d.active_requests
            }
            for d in devices.values()
        ]
    })


# ============ Cleanup Task ============

async def cleanup_task():
    """Periodic cleanup of stale requests."""
    while True:
        await asyncio.sleep(30)

        now = time.time()
        stale_timeout = REQUEST_TIMEOUT + 30

        # Cleanup stale pending requests
        for req_id, req in list(pending_requests.items()):
            if now - req.created_at > stale_timeout:
                log.warning(f"Cleaning up stale request: {req_id}")
                req.future.set_exception(Exception("Request timeout (cleanup)"))
                pending_requests.pop(req_id, None)

                # Update device stats
                if req.device_id in devices:
                    devices[req.device_id].active_requests -= 1


# ============ Application Setup ============

async def init_app() -> web.Application:
    """Initialize the application."""
    app = web.Application()

    # WebSocket endpoint for Android devices
    app.router.add_get("/ws", websocket_handler)

    # HTTP Proxy API for MCP servers
    app.router.add_post("/proxy", proxy_handler)

    # Status endpoints
    app.router.add_get("/health", health_handler)
    app.router.add_get("/status", status_handler)
    app.router.add_get("/devices", devices_handler)

    return app


async def main():
    log.info(f"Starting Tunnel Server")
    log.info(f"  WebSocket: ws://0.0.0.0:{WS_PORT}/ws")
    log.info(f"  HTTP API:  http://0.0.0.0:{WS_PORT}/proxy")

    # Start cleanup task
    asyncio.create_task(cleanup_task())

    # Start server
    app = await init_app()
    runner = web.AppRunner(app)
    await runner.setup()

    site = web.TCPSite(runner, "0.0.0.0", WS_PORT)
    await site.start()

    log.info("Tunnel Server ready")

    # Keep running
    while True:
        await asyncio.sleep(3600)


if __name__ == "__main__":
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        log.info("Shutting down")
