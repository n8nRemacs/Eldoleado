"""
Avito WebSocket Listener Service
Maintains persistent WebSocket connection to socket.avito.ru
Forwards incoming messages to n8n webhook

Deploy: 45.144.177.128
Port: 8769
"""

import asyncio
import json
import logging
import os
from datetime import datetime
from typing import Optional, Dict, Any, List

import httpx
import websockets
from dotenv import load_dotenv

load_dotenv()

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger("AvitoWSListener")

# Config
N8N_WEBHOOK_URL = os.getenv("N8N_WEBHOOK_URL", "https://n8n.n8nsrv.ru/webhook/avito/incoming")
POSTGRES_URL = os.getenv("POSTGRES_URL")
RECONNECT_DELAY = 5  # seconds
PING_INTERVAL = 30

# Avito WebSocket
# Format: wss://socket.avito.ru/?use_seq=true&seq={seq}&id_version=v2&my_hash_id={hash}&app_name=web&app_version=7.456.1
AVITO_WS_BASE = "wss://socket.avito.ru/"
AVITO_APP_VERSION = "7.456.1"


class AvitoAccount:
    """Single Avito account WebSocket connection"""

    def __init__(self, account_id: str, tenant_id: str, cookies: str, user_hash: str = None):
        self.account_id = account_id
        self.tenant_id = tenant_id
        self.cookies = cookies  # Full cookie string for auth
        self.user_hash = user_hash  # my_hash_id from Avito
        self.ws: Optional[websockets.WebSocketClientProtocol] = None
        self.user_id: Optional[str] = None
        self.is_connected = False
        self._task: Optional[asyncio.Task] = None
        self._http_client: Optional[httpx.AsyncClient] = None
        self._seq = 0  # Sequence number for message ordering

    async def start(self):
        """Start WebSocket connection"""
        self._http_client = httpx.AsyncClient(timeout=30)
        self._task = asyncio.create_task(self._connection_loop())
        logger.info(f"[{self.account_id[:8]}] Started listener")

    async def stop(self):
        """Stop WebSocket connection"""
        if self._task:
            self._task.cancel()
        if self.ws:
            await self.ws.close()
        if self._http_client:
            await self._http_client.aclose()
        logger.info(f"[{self.account_id[:8]}] Stopped listener")

    async def _connection_loop(self):
        """Reconnection loop"""
        while True:
            try:
                await self._connect()
                await self._listen()
            except websockets.ConnectionClosed as e:
                logger.warning(f"[{self.account_id[:8]}] WebSocket closed: {e}")
            except Exception as e:
                logger.error(f"[{self.account_id[:8]}] WebSocket error: {e}")

            self.is_connected = False
            logger.info(f"[{self.account_id[:8]}] Reconnecting in {RECONNECT_DELAY}s...")
            await asyncio.sleep(RECONNECT_DELAY)

    def _extract_cookie_value(self, name: str) -> Optional[str]:
        """Extract a specific cookie value from cookies string"""
        for part in self.cookies.split(";"):
            part = part.strip()
            if part.startswith(f"{name}="):
                value = part[len(name) + 1:]
                # Remove quotes if present
                if value.startswith('"') and value.endswith('"'):
                    value = value[1:-1]
                return value
        return None

    async def _get_user_hash(self) -> Optional[str]:
        """Get user hash from cookies or API"""
        import hashlib

        # First try to extract from cookies directly
        # __upin cookie contains user identifier
        upin = self._extract_cookie_value("__upin")
        if upin:
            logger.info(f"[{self.account_id[:8]}] Using __upin from cookies: {upin}")
            return upin

        # Try to get user info from messenger API
        try:
            response = await self._http_client.get(
                "https://m.avito.ru/api/1/messenger/channel?limit=1",
                headers={
                    "Cookie": self.cookies,
                    "User-Agent": "Mozilla/5.0 (Linux; Android 10; SM-G975F) AppleWebKit/537.36",
                    "X-Requested-With": "XMLHttpRequest"
                }
            )
            if response.status_code == 200:
                data = response.json()
                # Try to extract user hash from messenger response
                channels = data.get("channels") or data.get("result", {}).get("channels") or []
                if channels and len(channels) > 0:
                    # First channel might have our user info
                    channel = channels[0]
                    my_id = channel.get("myUserId") or channel.get("context", {}).get("myUserId")
                    if my_id:
                        logger.info(f"[{self.account_id[:8]}] Got myUserId from messenger: {my_id}")
                        return my_id

                # Try to get from meta
                meta = data.get("meta") or data.get("result", {}).get("meta") or {}
                my_hash = meta.get("myHashId") or meta.get("userId")
                if my_hash:
                    logger.info(f"[{self.account_id[:8]}] Got myHashId from meta: {my_hash}")
                    return my_hash
            else:
                logger.warning(f"[{self.account_id[:8]}] Messenger API returned {response.status_code}")
        except Exception as e:
            logger.warning(f"[{self.account_id[:8]}] Failed to get user hash from messenger: {e}")

        # Fallback: use ma_cid (analytics cookie) as base for hash
        ma_cid = self._extract_cookie_value("ma_cid")
        if ma_cid:
            # Generate deterministic hash from ma_cid
            user_hash = hashlib.md5(ma_cid.encode()).hexdigest()
            logger.info(f"[{self.account_id[:8]}] Generated hash from ma_cid: {user_hash[:16]}...")
            return user_hash

        # Last fallback: f cookie
        f_cookie = self._extract_cookie_value("f")
        if f_cookie:
            user_hash = hashlib.md5(f_cookie[:32].encode()).hexdigest()
            logger.info(f"[{self.account_id[:8]}] Generated hash from f cookie: {user_hash[:16]}...")
            return user_hash

        logger.error(f"[{self.account_id[:8]}] Could not determine user hash")
        return None

    async def _connect(self):
        """Connect to Avito WebSocket"""
        # Get user hash if not set
        if not self.user_hash:
            self.user_hash = await self._get_user_hash()
            if not self.user_hash:
                raise Exception("Failed to get user hash")

        # Build WebSocket URL with parameters
        ws_url = (
            f"{AVITO_WS_BASE}?"
            f"use_seq=true&"
            f"seq={self._seq}&"
            f"id_version=v2&"
            f"my_hash_id={self.user_hash}&"
            f"app_name=web&"
            f"app_version={AVITO_APP_VERSION}"
        )

        headers = {
            "Cookie": self.cookies,
            "Origin": "https://www.avito.ru",
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
        }

        self.ws = await websockets.connect(
            ws_url,
            additional_headers=headers,
            ping_interval=PING_INTERVAL,
            ping_timeout=10
        )
        self.is_connected = True
        logger.info(f"[{self.account_id[:8]}] WebSocket connected to {ws_url[:80]}...")

    async def _listen(self):
        """Listen for incoming messages"""
        async for message in self.ws:
            try:
                data = json.loads(message)
                await self._handle_message(data)
            except json.JSONDecodeError:
                logger.warning(f"[{self.account_id[:8]}] Invalid JSON: {message[:100]}")
            except Exception as e:
                logger.error(f"[{self.account_id[:8]}] Error handling message: {e}")

    async def _handle_message(self, data: Dict[str, Any]):
        """Handle incoming WebSocket message"""
        msg_type = data.get("type")
        msg_type_v2 = data.get("type_v2", "")

        # Update sequence number
        if "seq" in data:
            self._seq = int(data["seq"])

        # Skip JSON-RPC responses (have "jsonrpc" field or "result"/"error")
        if "jsonrpc" in data or "result" in data or "error" in data:
            return

        # Handle new message: type="Message", type_v2="messenger.Message"
        if msg_type == "Message" or msg_type_v2 == "messenger.Message":
            value = data.get("value", {})
            # Skip our own messages
            if value.get("fromUid") == self.user_hash:
                logger.debug(f"[{self.account_id[:8]}] Skipping own message")
                return
            await self._forward_to_n8n(data)
            return

        # Handle typing indicator (optional, for future use)
        if msg_type == "ChatTyping":
            logger.debug(f"[{self.account_id[:8]}] User typing in {data.get('value', {}).get('channelId')}")
            return

        # Handle read receipts
        if msg_type == "ChatRead":
            logger.debug(f"[{self.account_id[:8]}] Chat read: {data.get('value', {}).get('channelId')}")
            return

        # Handle session init
        if msg_type == "session":
            value = data.get("value", {})
            self.user_id = value.get("userId")
            logger.info(f"[{self.account_id[:8]}] Session init, userId={self.user_id}")
            return

        # Log unknown message types for debugging
        logger.debug(f"[{self.account_id[:8]}] Unknown type={msg_type}: {json.dumps(data)[:200]}")

    async def _forward_to_n8n(self, message_data: Dict[str, Any]):
        """Forward message to n8n webhook"""
        value = message_data.get("value", {})
        body = value.get("body", {})

        # Determine message type and extract content
        msg_type = value.get("type", "text")
        message_text = None
        media_url = None
        media_info = {}

        if msg_type == "text":
            message_text = body.get("text")
        elif msg_type == "image":
            media_url = body.get("url") or body.get("imageUrl")
            media_info = {"image_id": body.get("imageId")}
        elif msg_type == "voice":
            media_info = {"voice_id": body.get("voiceId"), "duration": body.get("duration")}
        elif msg_type == "video":
            media_info = {"video_id": body.get("videoId")}
        elif msg_type == "file":
            media_info = {"file_id": body.get("fileId"), "file_name": body.get("name")}

        # Convert nanosecond timestamp to ISO
        created_ns = value.get("created", 0)
        created_at = datetime.fromtimestamp(created_ns / 1_000_000_000).isoformat() if created_ns else None

        payload = {
            # ELO standard fields
            "channel_account_id": self.account_id,
            "tenant_id": self.tenant_id,
            "channel_type": "avito",

            # Message identifiers
            "external_chat_id": value.get("channelId"),
            "external_message_id": value.get("id"),
            "seq": message_data.get("seq"),

            # Message content
            "message_type": msg_type,
            "message_text": message_text,
            "media_url": media_url,
            "media_info": media_info if media_info else None,

            # Sender info
            "sender_id": value.get("fromUid"),
            "chat_type": value.get("chatType"),  # u2i = user to item

            # Timestamps
            "message_date": created_at,
            "received_at": datetime.now().isoformat(),

            # Raw for debugging
            "raw": message_data
        }

        try:
            response = await self._http_client.post(
                N8N_WEBHOOK_URL,
                json=payload,
                headers={"Content-Type": "application/json"}
            )

            if response.status_code == 200:
                logger.info(f"[{self.account_id[:8]}] Forwarded message to n8n")
            else:
                logger.warning(f"[{self.account_id[:8]}] n8n webhook error: {response.status_code}")
        except Exception as e:
            logger.error(f"[{self.account_id[:8]}] Failed to forward to n8n: {e}")


class AvitoListenerService:
    """Main service managing multiple account listeners"""

    def __init__(self):
        self.accounts: Dict[str, AvitoAccount] = {}
        self._db_pool = None
        self._refresh_task: Optional[asyncio.Task] = None

    async def start(self):
        """Start the service"""
        logger.info("Starting Avito Listener Service...")

        # Initial load of accounts
        await self._load_accounts()

        # Start periodic refresh (check for new/updated accounts)
        self._refresh_task = asyncio.create_task(self._refresh_loop())

        logger.info(f"Service started with {len(self.accounts)} accounts")

    async def stop(self):
        """Stop the service"""
        if self._refresh_task:
            self._refresh_task.cancel()

        for account in self.accounts.values():
            await account.stop()

        logger.info("Service stopped")

    async def _load_accounts(self):
        """Load active Avito accounts from PostgreSQL"""
        try:
            import asyncpg

            conn = await asyncpg.connect(POSTGRES_URL)

            rows = await conn.fetch("""
                SELECT
                    id::text as account_id,
                    tenant_id::text,
                    credentials->>'cookies' as cookies
                FROM elo_t_channel_accounts
                WHERE channel_id = 3
                  AND is_active = true
                  AND session_status = 'active'
                  AND credentials->>'cookies' IS NOT NULL
            """)

            await conn.close()

            # Start new accounts, stop removed ones
            current_ids = {row['account_id'] for row in rows}
            existing_ids = set(self.accounts.keys())

            # Stop removed accounts
            for account_id in existing_ids - current_ids:
                await self.accounts[account_id].stop()
                del self.accounts[account_id]
                logger.info(f"Removed account {account_id[:8]}")

            # Start new accounts
            for row in rows:
                account_id = row['account_id']
                if account_id not in self.accounts:
                    account = AvitoAccount(
                        account_id=account_id,
                        tenant_id=row['tenant_id'],
                        cookies=row['cookies']
                    )
                    await account.start()
                    self.accounts[account_id] = account

            logger.info(f"Loaded {len(self.accounts)} active accounts")

        except ImportError:
            logger.error("asyncpg not installed. Run: pip install asyncpg")
        except Exception as e:
            logger.error(f"Failed to load accounts: {e}")

    async def _refresh_loop(self):
        """Periodically refresh account list"""
        while True:
            await asyncio.sleep(60)  # Check every minute
            await self._load_accounts()

    def get_status(self) -> Dict[str, Any]:
        """Get service status"""
        return {
            "service": "avito-listener",
            "accounts_total": len(self.accounts),
            "accounts_connected": sum(1 for a in self.accounts.values() if a.is_connected),
            "accounts": [
                {
                    "id": aid[:8],
                    "connected": a.is_connected
                }
                for aid, a in self.accounts.items()
            ]
        }


# === FastAPI for health checks ===

from fastapi import FastAPI

app = FastAPI(title="Avito WebSocket Listener", version="1.0.0")
service = AvitoListenerService()


@app.on_event("startup")
async def startup():
    await service.start()


@app.on_event("shutdown")
async def shutdown():
    await service.stop()


@app.get("/health")
async def health():
    return service.get_status()


@app.post("/reload")
async def reload_accounts():
    """Force reload accounts from database"""
    await service._load_accounts()
    return {"success": True, "accounts": len(service.accounts)}


if __name__ == "__main__":
    import uvicorn
    PORT = int(os.getenv("PORT", 8775))
    uvicorn.run(app, host="0.0.0.0", port=PORT)
