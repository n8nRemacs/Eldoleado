import asyncpg
from typing import Optional, Tuple, Dict, Any

import config


async def get_pool() -> asyncpg.Pool:
    return await asyncpg.create_pool(dsn=config.DATABASE_URL, min_size=1, max_size=5)


async def fetch_query(pool: asyncpg.Pool, code: str) -> Optional[Tuple[str, Optional[Dict[str, Any]]]]:
    sql = """
    SELECT query, param_schema
    FROM elo_cypher_queries
    WHERE code = $1 AND is_active = true
    LIMIT 1
    """
    row = await pool.fetchrow(sql, code)
    if not row:
        return None
    return row["query"], row.get("param_schema")


async def ping_db(pool: asyncpg.Pool) -> bool:
    try:
        async with pool.acquire() as conn:
            await conn.execute("SELECT 1;")
        return True
    except Exception:
        return False

