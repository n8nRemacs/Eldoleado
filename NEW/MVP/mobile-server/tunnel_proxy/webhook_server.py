"""
Webhook Server - Receives incoming messages from local services
and forwards them to the tunnel.

Local services POST to this server when they receive new messages.
"""

import logging
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import Any, Dict, Optional

logger = logging.getLogger("WebhookServer")

app = FastAPI(title="Tunnel Webhook Server", version="1.0.0")

# Reference to tunnel proxy (set by main.py)
tunnel_proxy = None


class IncomingMessage(BaseModel):
    """Incoming message from local service"""
    service: str  # e.g., "telegram", "whatsapp"
    event: str = "message"  # message, status, typing, etc.
    data: Dict[str, Any]


@app.post("/webhook")
async def receive_webhook(msg: IncomingMessage):
    """
    Receive incoming message from local service.
    Forward to tunnel server.
    """
    if tunnel_proxy is None:
        raise HTTPException(status_code=503, detail="Tunnel not connected")

    if not tunnel_proxy.connected:
        raise HTTPException(status_code=503, detail="Tunnel disconnected")

    await tunnel_proxy.forward_incoming(
        service=msg.service,
        data={
            "event": msg.event,
            **msg.data
        }
    )

    return {"status": "forwarded"}


@app.get("/health")
async def health():
    """Health check"""
    return {
        "status": "ok",
        "tunnel_connected": tunnel_proxy.connected if tunnel_proxy else False
    }


@app.get("/stats")
async def stats():
    """Get tunnel stats"""
    if tunnel_proxy is None:
        return {"error": "Tunnel not initialized"}

    return {
        "connected": tunnel_proxy.connected,
        "stats": tunnel_proxy.stats
    }


def set_tunnel_proxy(proxy):
    """Set tunnel proxy reference"""
    global tunnel_proxy
    tunnel_proxy = proxy
