import json
from dataclasses import dataclass
from typing import Optional

import redis.asyncio as redis

import config
import db


@dataclass
class TenantResolutionResult:
    tenant_id: str
    domain_id: int
    channel_id: int
    channel_account_id: int


def _cache_key(channel: str, credential: str) -> str:
    """Generate cache key for tenant."""
    return f"cache:tenant:{channel}:{credential}"


async def resolve_tenant(
    redis_client: redis.Redis,
    channel: str,
    credential: Optional[str],
) -> Optional[TenantResolutionResult]:
    """
    Resolve tenant by channel and credential (bot_token, profile_id, etc.)
    Uses Redis cache with PostgreSQL fallback.
    """
    if not credential:
        return None

    cache_key = _cache_key(channel, credential)

    # 1. Check Redis cache
    cached = await redis_client.get(cache_key)
    if cached:
        data = json.loads(cached)
        return TenantResolutionResult(**data)

    # 2. Query PostgreSQL
    pool = await db.get_pool()
    async with pool.acquire() as conn:
        row = await conn.fetchrow(
            """
            SELECT
                t.id as tenant_id,
                t.domain_id,
                ca.id as channel_account_id,
                ca.channel_id
            FROM elo_tenants t
            JOIN elo_channel_accounts ca ON ca.tenant_id = t.id
            WHERE ca.credential = $1
              AND ca.channel_code = $2
              AND ca.is_active = true
              AND t.is_active = true
            LIMIT 1
            """,
            credential,
            channel,
        )

    if not row:
        return None

    # 3. Build result
    result = TenantResolutionResult(
        tenant_id=str(row["tenant_id"]),
        domain_id=row["domain_id"],
        channel_id=row["channel_id"],
        channel_account_id=row["channel_account_id"],
    )

    # 4. Cache in Redis
    await redis_client.setex(
        cache_key,
        config.CACHE_TTL_TENANT,
        json.dumps({
            "tenant_id": result.tenant_id,
            "domain_id": result.domain_id,
            "channel_id": result.channel_id,
            "channel_account_id": result.channel_account_id,
        }),
    )

    return result


async def invalidate_tenant_cache(
    redis_client: redis.Redis,
    channel: str,
    credential: str,
):
    """Invalidate tenant cache (call when tenant settings change)."""
    cache_key = _cache_key(channel, credential)
    await redis_client.delete(cache_key)

