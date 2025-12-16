"""
Webhook Receiver - Receives webhooks from external systems
"""

import logging
from typing import Optional, Dict, Any

from fastapi import APIRouter, HTTPException, Request, Header

logger = logging.getLogger("Webhooks")

router = APIRouter(prefix="/webhook", tags=["Webhooks"])


# === Webhook Handler Reference ===
# Set by main.py to connect to pipeline

_webhook_handler = None


def set_webhook_handler(handler):
    """Set handler for incoming webhooks"""
    global _webhook_handler
    _webhook_handler = handler


# === Routes ===

@router.post("/n8n")
async def n8n_webhook(request: Request):
    """
    Receive webhook from n8n.
    Used for outgoing messages initiated by n8n workflows.
    """
    body = await request.json()

    logger.info(f"n8n webhook received: {body.get('action', 'unknown')}")

    if _webhook_handler:
        await _webhook_handler("n8n", body)

    return {"status": "received"}


@router.post("/external/{source}")
async def external_webhook(
    source: str,
    request: Request,
    x_webhook_secret: Optional[str] = Header(default=None)
):
    """
    Generic webhook endpoint for external systems.
    Source can be: avito, vk, telegram, etc.
    """
    body = await request.json()

    logger.info(f"External webhook from {source}")

    # TODO: Validate webhook secret per source

    if _webhook_handler:
        await _webhook_handler(source, body)

    return {"status": "received"}


@router.post("/operator/send")
async def operator_send(request: Request):
    """
    Webhook for operator-initiated messages.
    Called by Android app or web interface.
    """
    body = await request.json()

    required_fields = ["tenant_id", "channel", "chat_id", "text"]
    for field in required_fields:
        if field not in body:
            raise HTTPException(400, f"Missing field: {field}")

    logger.info(f"Operator send: {body.get('channel')} -> {body.get('chat_id')}")

    if _webhook_handler:
        await _webhook_handler("operator", body)

    return {"status": "queued"}


@router.get("/health")
async def webhook_health():
    """Health check for webhook endpoint"""
    return {"status": "ok", "handler_set": _webhook_handler is not None}
