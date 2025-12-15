import json
import uuid
from dataclasses import dataclass
from typing import Optional

import redis.asyncio as redis

import config
import db


@dataclass
class DialogResolutionResult:
    dialog_id: str
    status: str
    vertical_id: Optional[int]
    is_new: bool = False


def _cache_key(client_id: str, channel_id: int) -> str:
    """Generate cache key for active dialog."""
    return f"cache:dialog:{client_id}:{channel_id}"


async def resolve_dialog(
    redis_client: redis.Redis,
    tenant_id: str,
    client_id: str,
    channel_id: int,
) -> DialogResolutionResult:
    """
    Resolve or create dialog.
    1. Check Redis cache for active dialog
    2. Find active/waiting dialog in PostgreSQL
    3. Create new dialog if not found
    """
    cache_key = _cache_key(client_id, channel_id)

    # 1. Check Redis cache
    cached = await redis_client.get(cache_key)
    if cached:
        data = json.loads(cached)
        # Verify status is still active
        if data.get("status") in ("active", "waiting"):
            return DialogResolutionResult(**data)
        # If not active, invalidate cache and continue
        await redis_client.delete(cache_key)

    pool = await db.get_pool()
    async with pool.acquire() as conn:
        # 2. Find active/waiting dialog
        row = await conn.fetchrow(
            """
            SELECT id as dialog_id, status, vertical_id
            FROM elo_dialogs
            WHERE client_id = $1
              AND channel_id = $2
              AND status IN ('active', 'waiting')
            ORDER BY updated_at DESC
            LIMIT 1
            """,
            uuid.UUID(client_id),
            channel_id,
        )

        if row:
            result = DialogResolutionResult(
                dialog_id=str(row["dialog_id"]),
                status=row["status"],
                vertical_id=row["vertical_id"],
                is_new=False,
            )
            await _cache_dialog(redis_client, cache_key, result)
            return result

        # 3. Create new dialog
        new_dialog_id = await conn.fetchval(
            """
            INSERT INTO elo_dialogs (tenant_id, client_id, channel_id, status)
            VALUES ($1, $2, $3, 'active')
            RETURNING id
            """,
            uuid.UUID(tenant_id),
            uuid.UUID(client_id),
            channel_id,
        )

    result = DialogResolutionResult(
        dialog_id=str(new_dialog_id),
        status="active",
        vertical_id=None,
        is_new=True,
    )

    # Cache new dialog
    await _cache_dialog(redis_client, cache_key, result)

    return result


async def _cache_dialog(
    redis_client: redis.Redis,
    cache_key: str,
    result: DialogResolutionResult,
):
    """Cache dialog data in Redis."""
    await redis_client.setex(
        cache_key,
        config.CACHE_TTL_DIALOG,
        json.dumps({
            "dialog_id": result.dialog_id,
            "status": result.status,
            "vertical_id": result.vertical_id,
            "is_new": False,  # Once cached, not new anymore
        }),
    )


async def invalidate_dialog_cache(
    redis_client: redis.Redis,
    client_id: str,
    channel_id: int,
):
    """Invalidate dialog cache (call when dialog status changes)."""
    cache_key = _cache_key(client_id, channel_id)
    await redis_client.delete(cache_key)


async def update_dialog_status(
    redis_client: redis.Redis,
    dialog_id: str,
    new_status: str,
):
    """Update dialog status in DB and invalidate cache."""
    pool = await db.get_pool()
    async with pool.acquire() as conn:
        # Get client_id and channel_id for cache invalidation
        row = await conn.fetchrow(
            """
            UPDATE elo_dialogs
            SET status = $1, updated_at = NOW()
            WHERE id = $2
            RETURNING client_id, channel_id
            """,
            new_status,
            uuid.UUID(dialog_id),
        )

        if row:
            # Invalidate cache
            cache_key = _cache_key(str(row["client_id"]), row["channel_id"])
            await redis_client.delete(cache_key)

