import json
import uuid
from dataclasses import dataclass
from typing import Optional

import httpx
import redis.asyncio as redis

import config
import db


@dataclass
class ClientResolutionResult:
    client_id: str
    name: Optional[str]
    phone: Optional[str]
    is_new: bool = False


def _cache_key(channel_id: int, external_chat_id: str) -> str:
    """Generate cache key for client."""
    return f"cache:client:{channel_id}:{external_chat_id}"


async def resolve_client(
    redis_client: redis.Redis,
    tenant_id: str,
    channel_id: int,
    external_chat_id: str,
    phone: Optional[str] = None,
    name: Optional[str] = None,
) -> ClientResolutionResult:
    """
    Resolve or create client.
    1. Check Redis cache
    2. Check by external_chat_id (elo_client_channels)
    3. Check by phone (elo_clients)
    4. Create new client if not found
    """
    cache_key = _cache_key(channel_id, external_chat_id)

    # 1. Check Redis cache
    cached = await redis_client.get(cache_key)
    if cached:
        data = json.loads(cached)
        return ClientResolutionResult(**data)

    pool = await db.get_pool()
    async with pool.acquire() as conn:
        # 2. Find by external_chat_id
        row = await conn.fetchrow(
            """
            SELECT c.id as client_id, c.name, c.phone
            FROM elo_clients c
            JOIN elo_client_channels cc ON cc.client_id = c.id
            WHERE cc.channel_id = $1
              AND cc.external_id = $2
              AND c.tenant_id = $3
            LIMIT 1
            """,
            channel_id,
            external_chat_id,
            uuid.UUID(tenant_id),
        )

        if row:
            result = ClientResolutionResult(
                client_id=str(row["client_id"]),
                name=row["name"],
                phone=row["phone"],
                is_new=False,
            )
            await _cache_client(redis_client, cache_key, result)
            return result

        # 3. Find by phone (if provided)
        if phone:
            row = await conn.fetchrow(
                """
                SELECT id as client_id, name, phone
                FROM elo_clients
                WHERE tenant_id = $1 AND phone = $2
                LIMIT 1
                """,
                uuid.UUID(tenant_id),
                phone,
            )

            if row:
                # Link existing client to this channel
                await conn.execute(
                    """
                    INSERT INTO elo_client_channels (client_id, channel_id, external_id)
                    VALUES ($1, $2, $3)
                    ON CONFLICT (channel_id, external_id) DO NOTHING
                    """,
                    row["client_id"],
                    channel_id,
                    external_chat_id,
                )
                result = ClientResolutionResult(
                    client_id=str(row["client_id"]),
                    name=row["name"],
                    phone=row["phone"],
                    is_new=False,
                )
                await _cache_client(redis_client, cache_key, result)
                return result

        # 4. Create new client
        new_client_id = await conn.fetchval(
            """
            INSERT INTO elo_clients (tenant_id, name, phone)
            VALUES ($1, $2, $3)
            RETURNING id
            """,
            uuid.UUID(tenant_id),
            name,
            phone,
        )

        # Link to channel
        await conn.execute(
            """
            INSERT INTO elo_client_channels (client_id, channel_id, external_id)
            VALUES ($1, $2, $3)
            """,
            new_client_id,
            channel_id,
            external_chat_id,
        )

    result = ClientResolutionResult(
        client_id=str(new_client_id),
        name=name,
        phone=phone,
        is_new=True,
    )

    # Sync to Neo4j (fire-and-forget)
    await _sync_to_neo4j(tenant_id, result)

    # Cache new client
    await _cache_client(redis_client, cache_key, result)

    return result


async def _cache_client(
    redis_client: redis.Redis,
    cache_key: str,
    result: ClientResolutionResult,
):
    """Cache client data in Redis."""
    await redis_client.setex(
        cache_key,
        config.CACHE_TTL_CLIENT,
        json.dumps({
            "client_id": result.client_id,
            "name": result.name,
            "phone": result.phone,
            "is_new": False,  # Once cached, not new anymore
        }),
    )


async def _sync_to_neo4j(tenant_id: str, client: ClientResolutionResult):
    """Sync new client to Neo4j graph."""
    try:
        async with httpx.AsyncClient(timeout=5.0) as http:
            await http.post(
                config.GRAPH_URL,
                json={
                    "query_code": "create_client",
                    "params": {
                        "client_id": client.client_id,
                        "tenant_id": tenant_id,
                        "name": client.name or "",
                        "phone": client.phone or "",
                    },
                },
            )
    except Exception:
        pass  # Fire-and-forget, don't fail the request


async def invalidate_client_cache(
    redis_client: redis.Redis,
    channel_id: int,
    external_chat_id: str,
):
    """Invalidate client cache."""
    cache_key = _cache_key(channel_id, external_chat_id)
    await redis_client.delete(cache_key)

