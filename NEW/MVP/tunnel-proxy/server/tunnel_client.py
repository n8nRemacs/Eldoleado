#!/usr/bin/env python3
"""
Server Tunnel Client - runs on server
Provides local HTTP proxy that forwards requests through mobile tunnel.
MCP servers use this proxy to route traffic through mobile IP.
"""

import asyncio
import json
import logging
import os
import uuid
from typing import Dict, Optional
from asyncio import Future

import aiohttp
from aiohttp import web, WSMsgType

# Config
PROXY_PORT = int(os.getenv("PROXY_PORT", "8080"))
MOBILE_WS_URL = os.getenv("MOBILE_WS_URL", "ws://localhost:8765/ws")
TUNNEL_SECRET = os.getenv("TUNNEL_SECRET", "change_me_in_production")
RECONNECT_DELAY = int(os.getenv("RECONNECT_DELAY", "5"))
LOG_LEVEL = os.getenv("LOG_LEVEL", "INFO")

logging.basicConfig(
    level=getattr(logging, LOG_LEVEL),
    format="%(asctime)s [%(levelname)s] %(message)s"
)
log = logging.getLogger("tunnel-client")

# WebSocket connection to mobile
ws_connection: Optional[aiohttp.ClientWebSocketResponse] = None
ws_session: Optional[aiohttp.ClientSession] = None

# Pending requests waiting for response
pending_requests: Dict[str, Future] = {}

# Connection state
connected = False
reconnecting = False


async def connect_to_mobile():
    """Establish WebSocket connection to mobile tunnel."""
    global ws_connection, ws_session, connected

    if ws_session is None:
        ws_session = aiohttp.ClientSession()

    headers = {"Authorization": f"Bearer {TUNNEL_SECRET}"}

    try:
        log.info(f"Connecting to mobile tunnel: {MOBILE_WS_URL}")
        ws_connection = await ws_session.ws_connect(
            MOBILE_WS_URL,
            headers=headers,
            heartbeat=30
        )
        connected = True
        log.info("Connected to mobile tunnel")
        return True
    except Exception as e:
        log.error(f"Failed to connect: {e}")
        connected = False
        return False


async def ws_receiver():
    """Receive messages from mobile tunnel."""
    global connected, reconnecting

    while True:
        if not connected or ws_connection is None:
            if not reconnecting:
                reconnecting = True
                log.info(f"Reconnecting in {RECONNECT_DELAY}s...")
                await asyncio.sleep(RECONNECT_DELAY)
                await connect_to_mobile()
                reconnecting = False
            else:
                await asyncio.sleep(1)
            continue

        try:
            msg = await ws_connection.receive()

            if msg.type == WSMsgType.TEXT:
                data = json.loads(msg.data)
                req_id = data.get("id")

                if req_id and req_id in pending_requests:
                    pending_requests[req_id].set_result(data)
                    del pending_requests[req_id]

            elif msg.type in (WSMsgType.CLOSED, WSMsgType.ERROR):
                log.warning("WebSocket closed/error")
                connected = False
                ws_connection = None

        except Exception as e:
            log.error(f"Receiver error: {e}")
            connected = False
            ws_connection = None


async def send_through_tunnel(request_data: dict, timeout: float = 30) -> dict:
    """Send HTTP request through tunnel and wait for response."""
    global ws_connection, connected

    if not connected or ws_connection is None:
        return {"error": "Tunnel not connected", "status": 503}

    req_id = str(uuid.uuid4())
    request_data["id"] = req_id
    request_data["action"] = "http"

    # Create future for response
    future = asyncio.get_event_loop().create_future()
    pending_requests[req_id] = future

    try:
        await ws_connection.send_json(request_data)
        response = await asyncio.wait_for(future, timeout=timeout)
        return response
    except asyncio.TimeoutError:
        pending_requests.pop(req_id, None)
        return {"error": "Tunnel timeout", "status": 504}
    except Exception as e:
        pending_requests.pop(req_id, None)
        return {"error": str(e), "status": 502}


async def proxy_handler(request: web.Request):
    """Handle incoming proxy requests."""
    # Build full URL
    url = str(request.url)

    # Read body if present
    body = None
    if request.body_exists:
        body = await request.read()
        try:
            body = body.decode("utf-8")
        except:
            import base64
            body = base64.b64encode(body).decode("ascii")

    # Prepare headers (skip hop-by-hop)
    skip_headers = {"host", "connection", "keep-alive", "proxy-connection",
                    "te", "trailers", "transfer-encoding", "upgrade"}
    headers = {k: v for k, v in request.headers.items()
               if k.lower() not in skip_headers}

    # Send through tunnel
    response = await send_through_tunnel({
        "method": request.method,
        "url": url,
        "headers": headers,
        "body": body,
        "timeout": 60
    })

    # Handle error
    if "error" in response:
        return web.json_response(
            {"error": response["error"]},
            status=response.get("status", 502)
        )

    # Decode body if needed
    body_data = response.get("body", "")
    if response.get("body_encoded"):
        import base64
        body_data = base64.b64decode(body_data)
    else:
        body_data = body_data.encode("utf-8")

    # Build response
    resp_headers = response.get("headers", {})
    skip_resp_headers = {"content-encoding", "transfer-encoding", "content-length"}
    resp_headers = {k: v for k, v in resp_headers.items()
                    if k.lower() not in skip_resp_headers}

    return web.Response(
        status=response.get("status", 200),
        headers=resp_headers,
        body=body_data
    )


async def connect_handler(request: web.Request):
    """Handle CONNECT method for HTTPS tunneling."""
    # For HTTPS, we need raw TCP tunneling
    # This is more complex - for now return error
    # TODO: Implement raw TCP tunnel for HTTPS CONNECT
    return web.Response(status=501, text="CONNECT not implemented yet")


async def health_handler(request):
    """Health check endpoint."""
    return web.json_response({
        "status": "ok" if connected else "disconnected",
        "service": "tunnel-client",
        "mobile_connected": connected,
        "pending_requests": len(pending_requests)
    })


async def init_app():
    """Initialize the proxy application."""
    app = web.Application()

    # Health endpoint
    app.router.add_get("/health", health_handler)

    # Proxy all other requests
    app.router.add_route("*", "/{path:.*}", proxy_handler)

    return app


async def main():
    log.info(f"Starting Tunnel Client Proxy on port {PROXY_PORT}")
    log.info(f"Mobile tunnel: {MOBILE_WS_URL}")

    # Start WebSocket receiver task
    asyncio.create_task(ws_receiver())

    # Initial connection
    await connect_to_mobile()

    # Start proxy server
    app = await init_app()
    runner = web.AppRunner(app)
    await runner.setup()

    site = web.TCPSite(runner, "0.0.0.0", PROXY_PORT)
    await site.start()

    log.info(f"Proxy ready at http://0.0.0.0:{PROXY_PORT}")
    log.info(f"Use HTTP_PROXY=http://localhost:{PROXY_PORT}")

    # Keep running
    while True:
        await asyncio.sleep(3600)


if __name__ == "__main__":
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        log.info("Shutting down")
