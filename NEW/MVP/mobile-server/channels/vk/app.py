"""
VK User Channel - VK API with User Token
Port: 3005

Uses VK User access token (not community token).
"""

import asyncio
import logging
import os
from typing import Optional, Dict, Any, List

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import httpx
from dotenv import load_dotenv

load_dotenv()

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("VKChannel")

# Config
PORT = int(os.getenv("PORT", 3005))
HOST = os.getenv("HOST", "0.0.0.0")
WEBHOOK_URL = os.getenv("WEBHOOK_URL", "http://localhost:8000/webhook")

# VK API
VK_API_URL = "https://api.vk.com/method"
VK_API_VERSION = "5.199"


# === Pydantic Models ===

class SendMessageRequest(BaseModel):
    peer_id: int  # VK uses peer_id
    text: str
    random_id: Optional[int] = None


class VKSession(BaseModel):
    """VK User access token"""
    access_token: str


# === VK Client ===

class VKChannel:
    def __init__(self):
        self.http_client: Optional[httpx.AsyncClient] = None
        self.access_token: Optional[str] = None
        self.is_authorized = False
        self.user_id: Optional[int] = None

    async def start(self):
        """Initialize HTTP client"""
        self.http_client = httpx.AsyncClient(timeout=30)

        # Try to load token from env
        token = os.getenv("VK_ACCESS_TOKEN")
        if token:
            await self.set_token(token)

    async def stop(self):
        """Close HTTP client"""
        if self.http_client:
            await self.http_client.aclose()

    async def set_token(self, token: str):
        """Set access token"""
        self.access_token = token

        # Verify token
        try:
            response = await self._call("users.get")
            users = response.get("response", [])
            if users:
                self.user_id = users[0].get("id")
                self.is_authorized = True
                logger.info(f"VK authorized: user_id={self.user_id}")
        except Exception as e:
            logger.error(f"Token invalid: {e}")
            self.is_authorized = False

    async def _call(self, method: str, params: Dict = None) -> Dict:
        """Call VK API method"""
        if not self.access_token:
            raise HTTPException(401, "Not authorized")

        url = f"{VK_API_URL}/{method}"

        request_params = {
            "access_token": self.access_token,
            "v": VK_API_VERSION,
            **(params or {})
        }

        response = await self.http_client.get(url, params=request_params)
        data = response.json()

        if "error" in data:
            error = data["error"]
            error_code = error.get("error_code")
            error_msg = error.get("error_msg")

            if error_code == 5:  # Token expired
                self.is_authorized = False
                raise HTTPException(401, "Token expired")

            raise HTTPException(500, f"VK Error {error_code}: {error_msg}")

        return data

    # === API Methods ===

    async def get_dialogs(self, count: int = 50) -> List[Dict]:
        """Get list of conversations"""
        data = await self._call(
            "messages.getConversations",
            {"count": count, "extended": 1}
        )

        response = data.get("response", {})
        items = response.get("items", [])
        profiles = {p["id"]: p for p in response.get("profiles", [])}
        groups = {-g["id"]: g for g in response.get("groups", [])}

        dialogs = []
        for item in items:
            conv = item.get("conversation", {})
            peer = conv.get("peer", {})
            peer_id = peer.get("id")

            # Get title
            if peer_id > 0:
                profile = profiles.get(peer_id, {})
                title = f"{profile.get('first_name', '')} {profile.get('last_name', '')}".strip()
            elif peer_id < 0:
                group = groups.get(peer_id, {})
                title = group.get("name", "")
            else:
                title = conv.get("chat_settings", {}).get("title", "")

            dialogs.append({
                "peer_id": peer_id,
                "type": peer.get("type"),
                "title": title,
                "unread_count": conv.get("unread_count", 0),
                "last_message": item.get("last_message")
            })

        return dialogs

    async def get_history(self, peer_id: int, count: int = 50) -> List[Dict]:
        """Get chat message history"""
        data = await self._call(
            "messages.getHistory",
            {"peer_id": peer_id, "count": count}
        )
        return data.get("response", {}).get("items", [])

    async def send_message(self, peer_id: int, text: str, random_id: int = None) -> Dict:
        """Send text message"""
        import random as rnd

        data = await self._call(
            "messages.send",
            {
                "peer_id": peer_id,
                "message": text,
                "random_id": random_id or rnd.randint(1, 2147483647)
            }
        )
        return {"message_id": data.get("response")}

    async def send_photo(self, peer_id: int, photo_url: str, text: str = "") -> Dict:
        """Send photo (requires upload first)"""
        # TODO: Implement photo upload
        raise HTTPException(501, "Photo sending not implemented")


# === FastAPI ===

channel = VKChannel()
app = FastAPI(title="VK User Channel", version="1.0.0")


@app.on_event("startup")
async def startup():
    await channel.start()


@app.on_event("shutdown")
async def shutdown():
    await channel.stop()


@app.get("/health")
async def health():
    return {
        "status": "ok",
        "authorized": channel.is_authorized,
        "user_id": channel.user_id
    }


@app.post("/token")
async def set_token(session: VKSession):
    """Set VK access token"""
    await channel.set_token(session.access_token)
    return {"success": channel.is_authorized}


@app.get("/dialogs")
async def get_dialogs(count: int = 50):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.get_dialogs(count)


@app.get("/history/{peer_id}")
async def get_history(peer_id: int, count: int = 50):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.get_history(peer_id, count)


@app.post("/send")
async def send_message(req: SendMessageRequest):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.send_message(req.peer_id, req.text, req.random_id)


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=HOST, port=PORT)
