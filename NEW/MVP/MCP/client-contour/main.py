import json
from typing import Any, Dict, List, Optional

import httpx
import redis.asyncio as redis
from fastapi import FastAPI
from pydantic import BaseModel, Field

import config
import db
from resolvers.client import resolve_client
from resolvers.dialog import resolve_dialog
from resolvers.tenant import resolve_tenant

app = FastAPI(title="Client Contour MCP")
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
    message_ids: List[str] = Field(default_factory=list)
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
    """Health check with DB connectivity status."""
    pg_ok = await db.ping_db()
    redis_ok = False
    try:
        await redis_client.ping()
        redis_ok = True
    except Exception:
        pass

    return {
        "status": "ok" if (pg_ok and redis_ok) else "degraded",
        "postgres": pg_ok,
        "redis": redis_ok,
    }


@app.get("/dlq")
async def dlq_get():
    """Get DLQ items for unknown tenants."""
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
    """Clear DLQ."""
    await redis_client.delete(DLQ_UNKNOWN_TENANT)
    return {"cleared": True}


@app.on_event("startup")
async def on_startup():
    """Initialize connections on startup."""
    global redis_client
    redis_client = redis.from_url(config.REDIS_URL, decode_responses=True)
    await db.init_pool()


@app.on_event("shutdown")
async def on_shutdown():
    """Close connections on shutdown."""
    if redis_client:
        await redis_client.close()
    await db.close_pool()


@app.post("/resolve", response_model=ResolveResponse)
async def resolve(req: ResolveRequest):
    """
    Resolve tenant, client, dialog for incoming message.
    Uses Redis cache for performance.
    """
    # 1. Tenant resolve (with cache)
    tenant = None
    try:
        tenant = await resolve_tenant(redis_client, req.channel, req.bot_token)
    except Exception as exc:
        await _push_dlq(req, f"tenant_error: {exc}")
        return ResolveResponse(accepted=False, reason="tenant_error", trace_id=req.trace_id)

    if tenant is None:
        await _push_dlq(req, "unknown_tenant")
        return ResolveResponse(accepted=False, reason="unknown_tenant", trace_id=req.trace_id)

    # 2. Client resolve (with cache, creates if not found)
    try:
        client = await resolve_client(
            redis_client=redis_client,
            tenant_id=tenant.tenant_id,
            channel_id=tenant.channel_id,
            external_chat_id=req.external_chat_id,
            phone=req.client_phone,
            name=req.client_name,
        )
    except Exception as exc:
        await _push_dlq(req, f"client_error: {exc}")
        return ResolveResponse(accepted=False, reason="client_error", trace_id=req.trace_id)

    # 3. Dialog resolve (with cache, creates if not found)
    try:
        dialog = await resolve_dialog(
            redis_client=redis_client,
            tenant_id=tenant.tenant_id,
            client_id=client.client_id,
            channel_id=tenant.channel_id,
        )
    except Exception as exc:
        await _push_dlq(req, f"dialog_error: {exc}")
        return ResolveResponse(accepted=False, reason="dialog_error", trace_id=req.trace_id)

    # 4. Forward to Core (fire-and-forget)
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
        "client": {
            "id": client.client_id,
            "name": client.name,
            "phone": client.phone,
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


async def _push_dlq(req: ResolveRequest, error: str):
    """Push failed request to DLQ."""
    await redis_client.rpush(
        DLQ_UNKNOWN_TENANT,
        json.dumps({
            "error": error,
            "channel": req.channel,
            "credential": req.bot_token,
            "external_chat_id": req.external_chat_id,
            "trace_id": req.trace_id,
        }),
    )


async def post_core(payload: Dict[str, Any]):
    """Forward resolved message to Core (fire-and-forget)."""
    async with httpx.AsyncClient(timeout=10.0, verify=False) as client:
        try:
            await client.post(config.CORE_URL, json=payload)
        except Exception as exc:
            print(f"[core] post failed: {exc}")

