"""Shared storage module for multi-tenant MCP servers.

Provides Redis (primary) + PostgreSQL (backup) storage for channel accounts.
"""

import json
import hashlib
import logging
from typing import Optional, Dict, List, Any
from datetime import datetime

import asyncpg
import redis.asyncio as aioredis

logger = logging.getLogger(__name__)

# Connection URLs - use local Redis, remote PostgreSQL
# REDIS_URL is localhost for RU server (45.144.177.128) and Finnish server (217.145.79.27)
import os
REDIS_URL = os.environ.get("REDIS_URL", "redis://redis:6379")  # Docker network name or localhost
DATABASE_URL = os.environ.get("DATABASE_URL", "postgresql://supabase_admin:Mi31415926pS@185.221.214.83:6544/postgres")

# Global connections
_redis: Optional[aioredis.Redis] = None
_db_pool: Optional[asyncpg.Pool] = None


def get_credentials_hash(primary_credential: str) -> str:
    """Generate 16-char hash from primary credential for webhook URL."""
    return hashlib.sha256(primary_credential.encode()).hexdigest()[:16]


async def init_storage():
    """Initialize Redis and PostgreSQL connections."""
    global _redis, _db_pool

    # Connect to Redis
    _redis = aioredis.from_url(REDIS_URL, decode_responses=True)
    logger.info("Redis connected")

    # Connect to PostgreSQL
    _db_pool = await asyncpg.create_pool(DATABASE_URL, min_size=2, max_size=10)
    logger.info("PostgreSQL connected")


async def close_storage():
    """Close all connections."""
    global _redis, _db_pool

    if _redis:
        await _redis.close()
        _redis = None
        logger.info("Redis disconnected")

    if _db_pool:
        await _db_pool.close()
        _db_pool = None
        logger.info("PostgreSQL disconnected")


# === Redis Keys ===

def _account_key(channel: str, account_hash: str) -> str:
    """Redis key for account data."""
    return f"channel:{channel}:account:{account_hash}"


def _accounts_index_key(channel: str) -> str:
    """Redis key for channel accounts index (SET)."""
    return f"channel:{channel}:accounts"


# === Account Operations ===

async def save_account(
    channel: str,
    account_hash: str,
    credentials: Dict[str, Any],
    metadata: Optional[Dict[str, Any]] = None
) -> None:
    """Save account to Redis (primary) and PostgreSQL (backup)."""
    data = {
        "credentials": credentials,
        "metadata": metadata or {},
        "is_active": True,
        "created_at": datetime.utcnow().isoformat(),
        "updated_at": datetime.utcnow().isoformat()
    }

    # Save to Redis (primary)
    key = _account_key(channel, account_hash)
    await _redis.set(key, json.dumps(data))
    await _redis.sadd(_accounts_index_key(channel), account_hash)
    logger.info(f"Account saved to Redis: {channel}/{account_hash}")

    # Backup to PostgreSQL
    try:
        await _db_pool.execute("""
            INSERT INTO channel_accounts (channel, account_hash, credentials, metadata)
            VALUES ($1, $2, $3, $4)
            ON CONFLICT (channel, account_hash) DO UPDATE
            SET credentials = $3, metadata = $4, updated_at = NOW()
        """, channel, account_hash, json.dumps(credentials), json.dumps(metadata or {}))
        logger.info(f"Account backed up to PostgreSQL: {channel}/{account_hash}")
    except Exception as e:
        logger.warning(f"Failed to backup to PostgreSQL: {e}")


async def load_accounts(channel: str) -> List[Dict[str, Any]]:
    """Load all accounts for channel from Redis."""
    accounts = []

    # Get all account hashes for channel
    hashes = await _redis.smembers(_accounts_index_key(channel))

    for h in hashes:
        data = await _redis.get(_account_key(channel, h))
        if data:
            acc = json.loads(data)
            acc["hash"] = h
            accounts.append(acc)

    logger.info(f"Loaded {len(accounts)} accounts for {channel} from Redis")
    return accounts


async def get_account(channel: str, account_hash: str) -> Optional[Dict[str, Any]]:
    """Get single account by hash."""
    data = await _redis.get(_account_key(channel, account_hash))
    if data:
        acc = json.loads(data)
        acc["hash"] = account_hash
        return acc
    return None


async def delete_account(channel: str, account_hash: str) -> bool:
    """Delete account from Redis and PostgreSQL."""
    key = _account_key(channel, account_hash)

    # Check if exists
    exists = await _redis.exists(key)
    if not exists:
        return False

    # Delete from Redis
    await _redis.delete(key)
    await _redis.srem(_accounts_index_key(channel), account_hash)
    logger.info(f"Account deleted from Redis: {channel}/{account_hash}")

    # Delete from PostgreSQL
    try:
        await _db_pool.execute("""
            DELETE FROM channel_accounts
            WHERE channel = $1 AND account_hash = $2
        """, channel, account_hash)
        logger.info(f"Account deleted from PostgreSQL: {channel}/{account_hash}")
    except Exception as e:
        logger.warning(f"Failed to delete from PostgreSQL: {e}")

    return True


async def update_account_metadata(
    channel: str,
    account_hash: str,
    metadata: Dict[str, Any]
) -> bool:
    """Update account metadata."""
    key = _account_key(channel, account_hash)
    data = await _redis.get(key)

    if not data:
        return False

    acc = json.loads(data)
    acc["metadata"] = {**acc.get("metadata", {}), **metadata}
    acc["updated_at"] = datetime.utcnow().isoformat()

    await _redis.set(key, json.dumps(acc))
    return True


# === Recovery from PostgreSQL ===

async def restore_from_postgresql(channel: str) -> int:
    """Restore accounts from PostgreSQL to Redis (recovery mode)."""
    rows = await _db_pool.fetch("""
        SELECT account_hash, credentials, metadata
        FROM channel_accounts
        WHERE channel = $1 AND is_active = true
    """, channel)

    count = 0
    for row in rows:
        account_hash = row["account_hash"]
        credentials = json.loads(row["credentials"]) if row["credentials"] else {}
        metadata = json.loads(row["metadata"]) if row["metadata"] else {}

        data = {
            "credentials": credentials,
            "metadata": metadata,
            "is_active": True,
            "restored_at": datetime.utcnow().isoformat()
        }

        key = _account_key(channel, account_hash)
        await _redis.set(key, json.dumps(data))
        await _redis.sadd(_accounts_index_key(channel), account_hash)
        count += 1

    logger.info(f"Restored {count} accounts for {channel} from PostgreSQL")
    return count
