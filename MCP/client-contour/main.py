import json
import os
from typing import Any, Dict, Optional

import httpx
import redis.asyncio as redis
from fastapi import FastAPI
from pydantic import BaseModel, Field

import config
from resolvers.client import resolve_client_mock
from resolvers.dialog import resolve_dialog_mock
from resolvers.tenant import resolve_tenant_mock

app = FastAPI(title="Client Contour MCP (mock)")
redis_client: Optional[redis.Redis] = None
DLQ_UNKNOWN_TENANT = "dlq:unknown_tenant"


class ResolveRequest(BaseModel):
    channel: str
    bot_token: Optional[str] = None
    external_user_id: str
    external_chat_id: str
    client_name: Optional[str] = None
    client_phone: Optional[str] = None
    text: str
    timestamp: str
    message_ids: list[str]
    trace_id: Optional[str] = None
    media: Dict[str, Any] = Field(default_factory=dict)
    meta: Dict[str, Any] = Field(default_factory=dict)


class ResolveResponse(BaseModel):
    accepted: bool
    tenant_id: Optional[str] = None
    client_id: Optional[str] = None
    dialog_id: Optional[str] = None
    trace_id: Optional[str] = None
    reason: Optional[str] = None


@app.get("/health")
async def health():
    return {"status": "ok", "postgres": False, "neo4j": False}


@app.get("/dlq")
async def dlq_get():
    raw_items = await redis_client.lrange(DLQ_UNKNOWN_TENANT, 0, -1)
    items = []
    for raw in raw_items:
        try:
            items.append(json.loads(raw))
        except json.JSONDecodeError:
            items.append({"raw": raw})
    return {"count": len(items), "items": items}


@app.delete("/dlq")
async def dlq_clear():
    await redis_client.delete(DLQ_UNKNOWN_TENANT)
    return {"cleared": True}


@app.on_event("startup")
async def on_startup():
    global redis_client
    redis_client = redis.from_url(config.REDIS_URL, decode_responses=True)


@app.on_event("shutdown")
async def on_shutdown():
    if redis_client:
        await redis_client.close()


@app.post("/resolve", response_model=ResolveResponse)
async def resolve(req: ResolveRequest):
    # Tenant resolve (mock)
    tenant = None
    try:
        tenant = await resolve_tenant_mock(req.channel, req.bot_token)
    except Exception as exc:  # noqa: BLE001
        await redis_client.rpush(
            DLQ_UNKNOWN_TENANT,
            json.dumps(
                {
                    "error": "unknown_tenant",
                    "channel": req.channel,
                    "credential": req.bot_token,
                    "trace_id": req.trace_id,
                    "detail": str(exc),
                }
            ),
        )
        return ResolveResponse(accepted=False, reason="unknown_tenant", trace_id=req.trace_id)
    if tenant is None:
        await redis_client.rpush(
            DLQ_UNKNOWN_TENANT,
            json.dumps(
                {
                    "error": "unknown_tenant",
                    "channel": req.channel,
                    "credential": req.bot_token,
                    "trace_id": req.trace_id,
                }
            ),
        )
        return ResolveResponse(accepted=False, reason="unknown_tenant", trace_id=req.trace_id)

    # Client resolve (mock)
    client = await resolve_client_mock(
        tenant_id=tenant.tenant_id,
        channel_id=tenant.channel_id,
        external_chat_id=req.external_chat_id,
        phone=req.client_phone,
        name=req.client_name,
    )

    # Dialog resolve (mock)
    dialog = await resolve_dialog_mock(
        tenant_id=tenant.tenant_id,
        client_id=client.client_id,
        channel_id=tenant.channel_id,
    )

    # Forward to Core (fire-and-forget)
    payload_core = {
        "tenant_id": tenant.tenant_id,
        "domain_id": tenant.domain_id,
        "client_id": client.client_id,
        "dialog_id": dialog.dialog_id,
        "channel_id": tenant.channel_id,
        "external_chat_id": req.external_chat_id,
        "text": req.text,
        "timestamp": req.timestamp,
        "message_ids": req.message_ids,
        "trace_id": req.trace_id,
        "media": req.media,
        "meta": {
            **req.meta,
            "is_new_client": client.is_new,
            "is_new_dialog": dialog.is_new,
        },
    }
    await post_core(payload_core)

    return ResolveResponse(
        accepted=True,
        tenant_id=tenant.tenant_id,
        client_id=client.client_id,
        dialog_id=dialog.dialog_id,
        trace_id=req.trace_id,
    )


async def post_core(payload: Dict[str, Any]):
    async with httpx.AsyncClient(timeout=10.0, verify=False) as client:
        try:
            await client.post(config.CORE_URL, json=payload)
        except Exception as exc:  # noqa: BLE001
            # For MVP mock, just print
            print(f"[core] post failed: {exc}")

