"""Database connection pool for Android API."""

import asyncpg
import logging
from typing import Optional

from config import settings

logger = logging.getLogger(__name__)

# Global connection pool
pool: Optional[asyncpg.Pool] = None


async def init_db():
    """Initialize database connection pool."""
    global pool
    try:
        pool = await asyncpg.create_pool(
            settings.database_url,
            min_size=2,
            max_size=10,
            command_timeout=30
        )
        logger.info("Database pool created")
    except Exception as e:
        logger.error(f"Failed to create database pool: {e}")
        raise


async def close_db():
    """Close database connection pool."""
    global pool
    if pool:
        await pool.close()
        logger.info("Database pool closed")


async def get_pool() -> asyncpg.Pool:
    """Get database connection pool."""
    if not pool:
        raise RuntimeError("Database pool not initialized")
    return pool
