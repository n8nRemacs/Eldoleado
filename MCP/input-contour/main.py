"""Input Contour MCP Service.

Receives messages from MCP adapters, debounces, aggregates, and forwards to Client Contour.

Flow:
1. /ingest receives normalized message from adapters
2. Message queued to Redis with debounce deadline
3. Worker aggregates messages after silence period
4. Aggregated batch sent to Client Contour for resolution
"""

import asyncio
import json
import logging
import os
import time
import uuid
from typing import Any, Dict, List, Optional

import httpx
import redis.asyncio as redis
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)


# ==================== Models ====================

class IngestPayload(BaseModel):
    """Incoming message from MCP adapters."""
    channel: str
    external_user_id: str
    external_chat_id: str
    text: str = ""
    timestamp: str
    client_phone: Optional[str] = None
    client_name: Optional[str] = None
    bot_token: Optional[str] = None
    profile_id: Optional[str] = None  # WhatsApp
    group_id: Optional[str] = None    # VK
    meta: Dict[str, Any] = Field(default_factory=dict)
    media: Dict[str, Any] = Field(default_factory=dict)
    message_id: Optional[str] = None
    trace_id: Optional[str] = None
    debounce_seconds: Optional[int] = None


class ClientContourPayload(BaseModel):
    """Payload sent to Client Contour for resolution."""
    channel: str
    bot_token: Optional[str] = None
    profile_id: Optional[str] = None
    group_id: Optional[str] = None
    external_user_id: str
    external_chat_id: str
    client_name: Optional[str] = None
    client_phone: Optional[str] = None
    text: str
    timestamp: str
    message_ids: List[str]
    trace_id: str
    media: Dict[str, Any] = Field(default_factory=dict)
    meta: Dict[str, Any] = Field(default_factory=dict)


# ==================== Configuration ====================

REDIS_URL = os.getenv("REDIS_URL", "redis://localhost:6379/0")

# Next hop: Client Contour (resolves tenant, client, dialog)
CLIENT_CONTOUR_URL = os.getenv("CLIENT_CONTOUR_URL", "http://localhost:8772/resolve")

# Debounce settings
DEBOUNCE_SECONDS_DEFAULT = int(os.getenv("DEBOUNCE_SECONDS", "10"))
MAX_WAIT_SECONDS = int(os.getenv("MAX_WAIT_SECONDS", "300"))

# Worker settings
POLL_INTERVAL = float(os.getenv("POLL_INTERVAL", "1.0"))
BATCH_SIZE = int(os.getenv("BATCH_SIZE", "100"))

# Idempotency
IDEMPOTENCY_TTL = int(os.getenv("IDEMPOTENCY_TTL", "3600"))  # 1 hour

# Retry settings
MAX_RETRIES = int(os.getenv("MAX_RETRIES", "3"))
RETRY_BACKOFF_BASE = float(os.getenv("RETRY_BACKOFF_BASE", "2.0"))

# Redis keys
QUEUE_INCOMING = "queue:incoming"
QUEUE_BATCH_PREFIX = "queue:batch:"
DEADLINES_KEY = "batch:deadlines"
FIRST_MESSAGE_PREFIX = "batch:first:"
SEEN_PREFIX = "seen:"
DLQ_KEY = "dlq:input_contour"


# ==================== App ====================

app = FastAPI(
    title="Input Contour MCP",
    description="Message batching and debouncing service",
    version="1.0.0"
)

redis_client: Optional[redis.Redis] = None
stop_event = asyncio.Event()


def get_batch_key(item: Dict[str, Any]) -> str:
    """Generate batch key: channel:external_chat_id (tenant-agnostic at this stage)."""
    return f"{item.get('channel')}:{item.get('external_chat_id')}"


def get_credential(item: Dict[str, Any]) -> Optional[str]:
    """Extract credential for tenant resolution."""
    return (
        item.get("bot_token") or
        item.get("profile_id") or
        item.get("group_id")
    )


# ==================== Lifecycle ====================

@app.on_event("startup")
async def on_startup():
    global redis_client
    redis_client = redis.from_url(REDIS_URL, decode_responses=True)
    logger.info(f"Connected to Redis: {REDIS_URL}")
    logger.info(f"Client Contour URL: {CLIENT_CONTOUR_URL}")
    logger.info(f"Debounce: {DEBOUNCE_SECONDS_DEFAULT}s, Max wait: {MAX_WAIT_SECONDS}s")
    app.state.worker_task = asyncio.create_task(worker_loop())


@app.on_event("shutdown")
async def on_shutdown():
    stop_event.set()
    task = getattr(app.state, "worker_task", None)
    if task:
        task.cancel()
        try:
            await task
        except asyncio.CancelledError:
            pass
    if redis_client:
        await redis_client.close()
    logger.info("Input Contour stopped")


# ==================== Ingest Endpoint ====================

@app.post("/ingest")
async def ingest(payload: IngestPayload):
    """
    Receive message from MCP adapter.

    - Generates message_id and trace_id if not provided
    - Checks idempotency (skip duplicates)
    - Queues message for batching
    """
    # Generate IDs if missing
    if not payload.message_id:
        payload.message_id = f"msg_{uuid.uuid4()}"
    if not payload.trace_id:
        payload.trace_id = f"trace_{uuid.uuid4()}"

    # Idempotency check
    seen_key = f"{SEEN_PREFIX}{payload.channel}:{payload.message_id}"
    already_seen = await redis_client.exists(seen_key)
    if already_seen:
        logger.info(f"Duplicate message skipped: {payload.message_id}")
        return {
            "accepted": False,
            "duplicate": True,
            "message_id": payload.message_id,
            "trace_id": payload.trace_id
        }

    # Mark as seen
    await redis_client.setex(seen_key, IDEMPOTENCY_TTL, "1")

    # Queue for processing
    data = payload.model_dump()
    data["received_at"] = time.time()  # Track first message time for max_wait
    await redis_client.rpush(QUEUE_INCOMING, json.dumps(data))

    logger.info(f"Ingested: {payload.channel}:{payload.external_chat_id} msg={payload.message_id[:20]}...")

    return {
        "accepted": True,
        "message_id": payload.message_id,
        "trace_id": payload.trace_id
    }


# ==================== Worker ====================

async def worker_loop():
    """Main worker loop: process incoming queue and check deadlines."""
    logger.info("Worker started")
    while not stop_event.is_set():
        try:
            await process_incoming_queue()
            await process_deadlines()
        except Exception as exc:
            logger.error(f"Worker error: {exc}", exc_info=True)
        await asyncio.sleep(POLL_INTERVAL)


async def process_incoming_queue():
    """Pop messages from incoming queue, distribute to batch queues."""
    items = []
    for _ in range(BATCH_SIZE):
        raw = await redis_client.lpop(QUEUE_INCOMING)
        if not raw:
            break
        try:
            items.append(json.loads(raw))
        except json.JSONDecodeError:
            logger.warning("Invalid JSON in queue, skipping")
            continue

    if not items:
        return

    now = time.time()

    async with redis_client.pipeline(transaction=False) as pipe:
        for item in items:
            batch_key = get_batch_key(item)
            queue_key = f"{QUEUE_BATCH_PREFIX}{batch_key}"
            first_key = f"{FIRST_MESSAGE_PREFIX}{batch_key}"

            # Get debounce for this message
            debounce_secs = item.get("debounce_seconds") or DEBOUNCE_SECONDS_DEFAULT

            # Check if this is first message in batch
            first_time = await redis_client.get(first_key)
            if not first_time:
                # First message - record time
                await redis_client.setex(first_key, MAX_WAIT_SECONDS + 60, str(now))
                first_time = now
            else:
                first_time = float(first_time)

            # Calculate deadline: min(debounce, max_wait from first message)
            debounce_deadline = now + debounce_secs
            max_wait_deadline = first_time + MAX_WAIT_SECONDS
            deadline = min(debounce_deadline, max_wait_deadline)

            # Determine batch_reason for when it fires
            batch_reason = "silence_reached" if debounce_deadline <= max_wait_deadline else "max_wait_reached"
            item["_batch_reason"] = batch_reason

            # Queue message and set deadline
            await pipe.rpush(queue_key, json.dumps(item))
            await pipe.zadd(DEADLINES_KEY, {batch_key: deadline})

        await pipe.execute()


async def process_deadlines():
    """Check for batches that are ready to dispatch."""
    now = time.time()
    due_keys = await redis_client.zrangebyscore(DEADLINES_KEY, "-inf", now)

    if not due_keys:
        return

    for batch_key in due_keys:
        try:
            await aggregate_and_dispatch(batch_key)
        except Exception as exc:
            logger.error(f"Failed to dispatch batch {batch_key}: {exc}", exc_info=True)
        finally:
            # Always remove from deadlines
            await redis_client.zrem(DEADLINES_KEY, batch_key)
            # Clean up first message timestamp
            await redis_client.delete(f"{FIRST_MESSAGE_PREFIX}{batch_key}")


async def aggregate_and_dispatch(batch_key: str):
    """Aggregate messages in batch and dispatch to Client Contour."""
    queue_key = f"{QUEUE_BATCH_PREFIX}{batch_key}"

    # Get all messages atomically
    raw_messages = await redis_client.lrange(queue_key, 0, -1)
    await redis_client.delete(queue_key)

    if not raw_messages:
        return

    # Parse messages
    messages: List[Dict[str, Any]] = []
    for raw in raw_messages:
        try:
            messages.append(json.loads(raw))
        except json.JSONDecodeError:
            continue

    if not messages:
        return

    # Aggregate
    texts = [m.get("text", "") for m in messages if m.get("text")]
    message_ids = [m.get("message_id") for m in messages if m.get("message_id")]

    # Use last message as sample for metadata
    sample = messages[-1]
    first_msg = messages[0]

    # Determine batch reason from last message
    batch_reason = sample.get("_batch_reason", "silence_reached")

    # Merge media (collect all)
    merged_media = {
        "has_voice": any(m.get("media", {}).get("has_voice") for m in messages),
        "has_images": any(m.get("media", {}).get("has_images") for m in messages),
        "voice_urls": [
            m.get("media", {}).get("voice_url")
            for m in messages
            if m.get("media", {}).get("voice_url")
        ],
        "images": [
            img
            for m in messages
            for img in m.get("media", {}).get("images", [])
        ]
    }

    # Build payload for Client Contour
    payload = ClientContourPayload(
        channel=sample.get("channel"),
        bot_token=sample.get("bot_token"),
        profile_id=sample.get("profile_id"),
        group_id=sample.get("group_id"),
        external_user_id=sample.get("external_user_id"),
        external_chat_id=sample.get("external_chat_id"),
        client_name=sample.get("client_name") or first_msg.get("client_name"),
        client_phone=sample.get("client_phone") or first_msg.get("client_phone"),
        text="\n\n".join(texts),
        timestamp=sample.get("timestamp"),
        message_ids=message_ids,
        trace_id=sample.get("trace_id") or first_msg.get("trace_id"),
        media=merged_media,
        meta={
            "batched": len(messages) > 1,
            "batch_size": len(messages),
            "batch_reason": batch_reason,
            "first_message_at": first_msg.get("received_at"),
            "last_message_at": sample.get("received_at"),
        }
    )

    # Dispatch with retries
    success = await dispatch_with_retry(payload)

    if success:
        logger.info(
            f"Dispatched batch: {batch_key}, "
            f"size={len(messages)}, reason={batch_reason}"
        )
    else:
        logger.error(f"Failed to dispatch batch after retries: {batch_key}")


async def dispatch_with_retry(payload: ClientContourPayload) -> bool:
    """Dispatch to Client Contour with exponential backoff retry."""
    for attempt in range(MAX_RETRIES):
        try:
            async with httpx.AsyncClient(timeout=30.0) as client:
                response = await client.post(
                    CLIENT_CONTOUR_URL,
                    json=payload.model_dump()
                )

                if response.status_code == 200:
                    return True

                if response.status_code >= 500:
                    # Server error - retry
                    wait_time = RETRY_BACKOFF_BASE ** attempt
                    logger.warning(
                        f"Client Contour returned {response.status_code}, "
                        f"retry {attempt + 1}/{MAX_RETRIES} in {wait_time}s"
                    )
                    await asyncio.sleep(wait_time)
                    continue

                # 4xx - don't retry, log error
                logger.error(
                    f"Client Contour rejected request: {response.status_code} - "
                    f"{response.text[:200]}"
                )
                await send_to_dlq(payload, f"rejected_{response.status_code}")
                return False

        except httpx.TimeoutException:
            wait_time = RETRY_BACKOFF_BASE ** attempt
            logger.warning(
                f"Client Contour timeout, retry {attempt + 1}/{MAX_RETRIES} in {wait_time}s"
            )
            await asyncio.sleep(wait_time)

        except Exception as exc:
            logger.error(f"Dispatch error: {exc}")
            await asyncio.sleep(RETRY_BACKOFF_BASE ** attempt)

    # All retries exhausted - send to DLQ
    await send_to_dlq(payload, "max_retries_exceeded")
    return False


async def send_to_dlq(payload: ClientContourPayload, reason: str):
    """Send failed message to Dead Letter Queue."""
    dlq_entry = {
        "payload": payload.model_dump(),
        "reason": reason,
        "timestamp": time.time()
    }
    await redis_client.rpush(DLQ_KEY, json.dumps(dlq_entry))
    logger.warning(f"Message sent to DLQ: {reason}, trace={payload.trace_id}")


# ==================== Health ====================

@app.get("/health")
async def health():
    """Health check endpoint."""
    try:
        pong = await redis_client.ping()
        redis_ok = bool(pong)
    except Exception:
        redis_ok = False

    # Get queue stats
    incoming_len = await redis_client.llen(QUEUE_INCOMING) if redis_ok else 0
    deadlines_count = await redis_client.zcard(DEADLINES_KEY) if redis_ok else 0
    dlq_len = await redis_client.llen(DLQ_KEY) if redis_ok else 0

    return {
        "status": "ok" if redis_ok else "degraded",
        "redis": redis_ok,
        "queues": {
            "incoming": incoming_len,
            "pending_batches": deadlines_count,
            "dlq": dlq_len
        },
        "config": {
            "debounce_seconds": DEBOUNCE_SECONDS_DEFAULT,
            "max_wait_seconds": MAX_WAIT_SECONDS,
            "client_contour_url": CLIENT_CONTOUR_URL
        }
    }


@app.get("/dlq")
async def get_dlq(limit: int = 100):
    """Get messages from Dead Letter Queue."""
    raw_entries = await redis_client.lrange(DLQ_KEY, 0, limit - 1)
    entries = []
    for raw in raw_entries:
        try:
            entries.append(json.loads(raw))
        except json.JSONDecodeError:
            continue
    return {"count": len(entries), "entries": entries}


@app.delete("/dlq")
async def clear_dlq():
    """Clear Dead Letter Queue."""
    count = await redis_client.llen(DLQ_KEY)
    await redis_client.delete(DLQ_KEY)
    return {"cleared": count}


# ==================== Run ====================

if __name__ == "__main__":
    import uvicorn
    host = os.getenv("HOST", "0.0.0.0")
    port = int(os.getenv("PORT", "8771"))
    uvicorn.run("main:app", host=host, port=port, reload=True)
