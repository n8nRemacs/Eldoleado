#!/usr/bin/env python3
"""
Mobile Tunnel Proxy - runs on phone (Termux/Android)
Receives HTTP requests via WebSocket, executes locally, returns response.
Phone is just a dumb proxy - all logic stays on server.
"""

import asyncio
import json
import logging
import os
import signal
import sys
from typing import Optional

import aiohttp
from aiohttp import web, WSMsgType

# Config
WS_PORT = int(os.getenv("WS_PORT", "8765"))
SECRET = os.getenv("TUNNEL_SECRET", "change_me_in_production")
LOG_LEVEL = os.getenv("LOG_LEVEL", "INFO")

logging.basicConfig(
    level=getattr(logging, LOG_LEVEL),
    format="%(asctime)s [%(levelname)s] %(message)s"
)
log = logging.getLogger("tunnel")

# Connected server
server_ws: Optional[web.WebSocketResponse] = None
http_session: Optional[aiohttp.ClientSession] = None


async def handle_http_request(request_data: dict) -> dict:
    """Execute HTTP request locally and return response."""
    global http_session

    req_id = request_data.get("id", "unknown")
    method = request_data.get("method", "GET").upper()
    url = request_data.get("url")
    headers = request_data.get("headers", {})
    body = request_data.get("body")
    timeout = request_data.get("timeout", 30)

    if not url:
        return {"id": req_id, "error": "Missing URL", "status": 400}

    try:
        log.debug(f"[{req_id}] {method} {url}")

        kwargs = {
            "method": method,
            "url": url,
            "headers": headers,
            "timeout": aiohttp.ClientTimeout(total=timeout),
            "ssl": False  # Skip SSL verification for flexibility
        }

        if body:
            if isinstance(body, dict):
                kwargs["json"] = body
            else:
                kwargs["data"] = body

        async with http_session.request(**kwargs) as resp:
            response_body = await resp.read()

            # Try to decode as text, fallback to base64
            try:
                body_text = response_body.decode("utf-8")
                body_encoded = False
            except UnicodeDecodeError:
                import base64
                body_text = base64.b64encode(response_body).decode("ascii")
                body_encoded = True

            return {
                "id": req_id,
                "status": resp.status,
                "headers": dict(resp.headers),
                "body": body_text,
                "body_encoded": body_encoded
            }

    except asyncio.TimeoutError:
        return {"id": req_id, "error": "Timeout", "status": 504}
    except aiohttp.ClientError as e:
        return {"id": req_id, "error": str(e), "status": 502}
    except Exception as e:
        log.exception(f"[{req_id}] Error")
        return {"id": req_id, "error": str(e), "status": 500}


async def websocket_handler(request):
    """Handle WebSocket connection from server."""
    global server_ws

    # Check secret
    auth = request.headers.get("Authorization", "")
    if auth != f"Bearer {SECRET}":
        log.warning(f"Unauthorized connection attempt from {request.remote}")
        return web.Response(status=401, text="Unauthorized")

    ws = web.WebSocketResponse(heartbeat=30)
    await ws.prepare(request)

    log.info(f"Server connected from {request.remote}")
    server_ws = ws

    try:
        async for msg in ws:
            if msg.type == WSMsgType.TEXT:
                try:
                    data = json.loads(msg.data)
                    action = data.get("action")

                    if action == "ping":
                        await ws.send_json({"action": "pong"})

                    elif action == "http":
                        response = await handle_http_request(data)
                        await ws.send_json(response)

                    else:
                        await ws.send_json({"error": f"Unknown action: {action}"})

                except json.JSONDecodeError:
                    await ws.send_json({"error": "Invalid JSON"})

            elif msg.type == WSMsgType.ERROR:
                log.error(f"WebSocket error: {ws.exception()}")
                break

    except Exception as e:
        log.exception("WebSocket handler error")
    finally:
        server_ws = None
        log.info("Server disconnected")

    return ws


async def health_handler(request):
    """Health check endpoint."""
    return web.json_response({
        "status": "ok",
        "service": "mobile-tunnel-proxy",
        "server_connected": server_ws is not None
    })


async def init_app():
    """Initialize application."""
    global http_session

    http_session = aiohttp.ClientSession(
        connector=aiohttp.TCPConnector(ssl=False, limit=100)
    )

    app = web.Application()
    app.router.add_get("/ws", websocket_handler)
    app.router.add_get("/health", health_handler)

    return app


async def cleanup():
    """Cleanup on shutdown."""
    global http_session, server_ws

    if server_ws and not server_ws.closed:
        await server_ws.close()

    if http_session:
        await http_session.close()


def main():
    log.info(f"Starting Mobile Tunnel Proxy on port {WS_PORT}")
    log.info(f"WebSocket endpoint: ws://0.0.0.0:{WS_PORT}/ws")

    loop = asyncio.new_event_loop()
    asyncio.set_event_loop(loop)

    app = loop.run_until_complete(init_app())

    # Handle signals
    for sig in (signal.SIGTERM, signal.SIGINT):
        try:
            loop.add_signal_handler(sig, lambda: asyncio.create_task(cleanup()))
        except NotImplementedError:
            pass  # Windows

    try:
        web.run_app(app, host="0.0.0.0", port=WS_PORT, print=None)
    finally:
        loop.run_until_complete(cleanup())


if __name__ == "__main__":
    main()
