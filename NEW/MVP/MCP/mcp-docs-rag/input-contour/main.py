import asyncio
import json
import os
import time
import uuid
from typing import Any, Dict, List, Optional

import httpx
import redis.asyncio as redis
from fastapi import FastAPI
from pydantic import BaseModel, Field


# --------- Models ---------
class IngestPayload(BaseModel):
    channel: str
    external_user_id: str
    external_chat_id: str
    text: str = ""
    timestamp: str
    client_phone: Optional[str] = None
    client_name: Optional[str] = None
    bot_token: Optional[str] = None
    meta: Dict[str, Any] = Field(default_factory=dict)
    media: Dict[str, Any] = Field(default_factory=dict)
    tenant_id: Optional[str] = None
    message_id: Optional[str] = None
    trace_id: Optional[str] = None
    debounce_seconds: Optional[int] = None


# --------- Config ---------
REDIS_URL = os.getenv("REDIS_URL", "redis://localhost:6379/0")
QUEUE_INCOMING = "queue:incoming"
QUEUE_BATCH_PREFIX = "queue:batch:"
DEADLINES_KEY = "batch:deadlines"
FIRST_SEEN_KEY = "batch:first_seen"
IDEMPOTENCY_PREFIX = "idempotency:message:"
IDEMPOTENCY_TTL = int(os.getenv("IDEMPOTENCY_TTL", "3600"))  # seconds
DLQ_KEY = "dlq:input_contour"

CLIENT_CONTOUR_URL = os.getenv("CLIENT_CONTOUR_URL", "http://localhost:8772/resolve")
DEBOUNCE_SECONDS_DEFAULT = int(os.getenv("DEBOUNCE_SECONDS", "10"))
MAX_WAIT_SECONDS = int(os.getenv("MAX_WAIT_SECONDS", "300"))
POLL_INTERVAL = float(os.getenv("POLL_INTERVAL", "1.0"))
BATCH_SIZE = int(os.getenv("BATCH_SIZE", "100"))
MAX_DLQ_ITEMS = int(os.getenv("MAX_DLQ_ITEMS", "200"))

# --------- App ---------
app = FastAPI(title="Input Contour MCP")
redis_client: Optional[redis.Redis] = None
stop_event = asyncio.Event()


def batch_key(item: Dict[str, Any]) -> str:
    tenant = item.get("tenant_id") or "unknown"
    return f"{tenant}:{item.get('channel')}:{item.get('external_chat_id')}"


def idempotency_key(message_id: str) -> str:
    return f"{IDEMPOTENCY_PREFIX}{message_id}"


@app.on_event("startup")
async def on_startup():
    global redis_client
    redis_client = redis.from_url(REDIS_URL, decode_responses=True)
    app.state.worker_task = asyncio.create_task(worker_loop())


@app.on_event("shutdown")
async def on_shutdown():
    stop_event.set()
    task = getattr(app.state, "worker_task", None)
    if task:
        task.cancel()
    if redis_client:
        await redis_client.close()


@app.post("/ingest")
async def ingest(payload: IngestPayload):
    if not payload.message_id:
        payload.message_id = f"msg_{uuid.uuid4()}"
    if not payload.trace_id:
        payload.trace_id = f"trace_{uuid.uuid4()}"

    data = payload.model_dump()

    # idempotency check
    idem_key = idempotency_key(payload.message_id)
    set_ok = await redis_client.set(idem_key, "1", ex=IDEMPOTENCY_TTL, nx=True)
    if not set_ok:
        return {"accepted": False, "reason": "duplicate", "message_id": payload.message_id, "trace_id": payload.trace_id}

    await redis_client.rpush(QUEUE_INCOMING, json.dumps(data))
    return {"accepted": True, "message_id": payload.message_id, "trace_id": payload.trace_id}


@app.get("/health")
async def health():
    try:
        pong = await redis_client.ping()
    except Exception:  # noqa: BLE001
        pong = False
    return {"status": "ok", "redis": pong}


@app.get("/dlq")
async def dlq_get():
    # return up to MAX_DLQ_ITEMS items
    raw_items = await redis_client.lrange(DLQ_KEY, -MAX_DLQ_ITEMS, -1)
    items = []
    for raw in raw_items:
        try:
            items.append(json.loads(raw))
        except json.JSONDecodeError:
            items.append({"raw": raw})
    return {"count": len(items), "items": items}


@app.delete("/dlq")
async def dlq_clear():
    await redis_client.delete(DLQ_KEY)
    return {"cleared": True}


# --------- Worker Loop ---------
async def worker_loop():
    while not stop_event.is_set():
        try:
            await process_batch()
            await process_deadlines()
        except Exception as exc:  # noqa: BLE001
            print(f"[worker] error: {exc}")
        await asyncio.sleep(POLL_INTERVAL)


async def process_batch():
    items = []
    for _ in range(BATCH_SIZE):
        raw = await redis_client.lpop(QUEUE_INCOMING)
        if not raw:
            break
        try:
            items.append(json.loads(raw))
        except json.JSONDecodeError:
            continue
    if not items:
        return

    now = time.time()
    async with redis_client.pipeline(transaction=False) as pipe:
        for item in items:
            key = batch_key(item)
            queue_key = f"{QUEUE_BATCH_PREFIX}{key}"

            debounce_secs = item.get("debounce_seconds") or DEBOUNCE_SECONDS_DEFAULT

            # first seen
            first_seen = await redis_client.zscore(FIRST_SEEN_KEY, key)
            if first_seen is None:
                first_seen = now
                await pipe.zadd(FIRST_SEEN_KEY, {key: first_seen})

            deadline = min(first_seen + MAX_WAIT_SECONDS, now + debounce_secs)
            await pipe.rpush(queue_key, json.dumps(item))
            await pipe.zadd(DEADLINES_KEY, {key: deadline})
        await pipe.execute()


async def process_deadlines():
    now = time.time()
    due_keys = await redis_client.zrangebyscore(DEADLINES_KEY, "-inf", now)
    if not due_keys:
        return
    for key in due_keys:
        await aggregate_and_dispatch(key)
        await redis_client.zrem(DEADLINES_KEY, key)
        await redis_client.zrem(FIRST_SEEN_KEY, key)


async def aggregate_and_dispatch(key: str):
    queue_key = f"{QUEUE_BATCH_PREFIX}{key}"
    raw_messages = await redis_client.lrange(queue_key, 0, -1)
    await redis_client.delete(queue_key)
    if not raw_messages:
        return

    messages: List[Dict[str, Any]] = []
    for raw in raw_messages:
        try:
            messages.append(json.loads(raw))
        except json.JSONDecodeError:
            continue
    if not messages:
        return

    texts = [m.get("text", "") for m in messages if m.get("text")]
    message_ids = [m.get("message_id") for m in messages if m.get("message_id")]
    last_ts = messages[-1].get("timestamp")
    sample = messages[-1]

    payload = {
        "channel": sample.get("channel"),
        "bot_token": sample.get("bot_token"),
        "external_user_id": sample.get("external_user_id"),
        "external_chat_id": sample.get("external_chat_id"),
        "client_name": sample.get("client_name"),
        "client_phone": sample.get("client_phone"),
        "text": "\n\n".join(texts),
        "timestamp": last_ts,
        "message_ids": message_ids,
        "trace_id": sample.get("trace_id"),
        "media": sample.get("media") or {},
        "meta": {
            "batched": len(messages) > 1,
            "batch_size": len(messages),
            "batch_reason": "debounce",
            "external_chat_id": sample.get("external_chat_id"),
        },
    }

    await send_with_retries(payload)


async def send_with_retries(payload: Dict[str, Any], attempts: int = 3):
    delay = 1.0
    async with httpx.AsyncClient(timeout=30.0) as client:
        for attempt in range(1, attempts + 1):
            try:
                resp = await client.post(CLIENT_CONTOUR_URL, json=payload)
                resp.raise_for_status()
                return
            except Exception as exc:  # noqa: BLE001
                print(f"[dispatcher] attempt {attempt} failed: {exc}")
                if attempt == attempts:
                    await redis_client.rpush(DLQ_KEY, json.dumps({"payload": payload, "error": str(exc)}))
                    return
                await asyncio.sleep(delay)
                delay *= 2
