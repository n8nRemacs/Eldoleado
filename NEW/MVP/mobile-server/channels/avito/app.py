"""
Avito User Channel - HTTP Reverse
Port: 3003

Uses session cookies from logged-in Avito account.
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
logger = logging.getLogger("AvitoChannel")

# Config
PORT = int(os.getenv("PORT", 3003))
HOST = os.getenv("HOST", "0.0.0.0")
WEBHOOK_URL = os.getenv("WEBHOOK_URL", "http://localhost:8000/webhook")

# Avito API
AVITO_BASE_URL = "https://www.avito.ru/api"
AVITO_MESSENGER_URL = "https://www.avito.ru/web/1"


# === Pydantic Models ===

class SendMessageRequest(BaseModel):
    chat_id: str
    text: str


class AvitoSession(BaseModel):
    """Session data from browser cookies"""
    session_id: str  # __cfduid or similar
    cookies: Dict[str, str]
    user_agent: str = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"


# === Avito Client ===

class AvitoChannel:
    def __init__(self):
        self.http_client: Optional[httpx.AsyncClient] = None
        self.session: Optional[AvitoSession] = None
        self.is_authorized = False
        self.user_id: Optional[str] = None

    async def start(self):
        """Initialize HTTP client"""
        self.http_client = httpx.AsyncClient(
            timeout=30,
            follow_redirects=True
        )

        # Try to load session from env
        session_cookie = os.getenv("AVITO_SESSION_COOKIE")
        if session_cookie:
            await self.set_session({"session": session_cookie})

    async def stop(self):
        """Close HTTP client"""
        if self.http_client:
            await self.http_client.aclose()

    async def set_session(self, cookies: Dict[str, str]):
        """Set session cookies"""
        self.session = AvitoSession(
            session_id=cookies.get("session", ""),
            cookies=cookies
        )

        # Verify session
        try:
            user_info = await self._request("GET", "/users/me")
            self.user_id = user_info.get("id")
            self.is_authorized = True
            logger.info(f"Avito authorized: user_id={self.user_id}")
        except Exception as e:
            logger.error(f"Session invalid: {e}")
            self.is_authorized = False

    async def _request(
        self,
        method: str,
        path: str,
        json: Dict = None,
        params: Dict = None
    ) -> Dict:
        """Make authenticated request to Avito API"""
        if not self.session:
            raise HTTPException(401, "Not authorized")

        url = f"{AVITO_BASE_URL}{path}"

        headers = {
            "User-Agent": self.session.user_agent,
            "Accept": "application/json",
            "Origin": "https://www.avito.ru",
            "Referer": "https://www.avito.ru/",
        }

        response = await self.http_client.request(
            method=method,
            url=url,
            headers=headers,
            cookies=self.session.cookies,
            json=json,
            params=params
        )

        if response.status_code == 401:
            self.is_authorized = False
            raise HTTPException(401, "Session expired")

        response.raise_for_status()
        return response.json()

    async def _messenger_request(
        self,
        method: str,
        path: str,
        json: Dict = None
    ) -> Dict:
        """Make request to Avito Messenger API"""
        if not self.session:
            raise HTTPException(401, "Not authorized")

        url = f"{AVITO_MESSENGER_URL}{path}"

        headers = {
            "User-Agent": self.session.user_agent,
            "Accept": "application/json",
            "Content-Type": "application/json",
            "Origin": "https://www.avito.ru",
            "Referer": "https://www.avito.ru/",
        }

        response = await self.http_client.request(
            method=method,
            url=url,
            headers=headers,
            cookies=self.session.cookies,
            json=json
        )

        response.raise_for_status()
        return response.json()

    # === API Methods ===

    async def get_dialogs(self, limit: int = 50) -> List[Dict]:
        """Get list of conversations"""
        # TODO: Find actual endpoint via reverse engineering
        # This is a placeholder structure
        data = await self._messenger_request(
            "GET",
            f"/messenger/v2/chats?limit={limit}"
        )
        return data.get("chats", [])

    async def get_history(self, chat_id: str, limit: int = 50) -> List[Dict]:
        """Get chat message history"""
        data = await self._messenger_request(
            "GET",
            f"/messenger/v2/chats/{chat_id}/messages?limit={limit}"
        )
        return data.get("messages", [])

    async def send_message(self, chat_id: str, text: str) -> Dict:
        """Send text message"""
        data = await self._messenger_request(
            "POST",
            f"/messenger/v2/chats/{chat_id}/messages",
            json={"text": text}
        )
        return data

    async def send_image(self, chat_id: str, image_url: str) -> Dict:
        """Send image"""
        # TODO: Upload image first, then send
        raise HTTPException(501, "Image sending not implemented")


# === FastAPI ===

channel = AvitoChannel()
app = FastAPI(title="Avito User Channel", version="1.0.0")


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


@app.post("/session")
async def set_session(session: AvitoSession):
    """Set session cookies from browser"""
    await channel.set_session(session.cookies)
    return {"success": channel.is_authorized}


@app.get("/dialogs")
async def get_dialogs(limit: int = 50):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.get_dialogs(limit)


@app.get("/history/{chat_id}")
async def get_history(chat_id: str, limit: int = 50):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.get_history(chat_id, limit)


@app.post("/send")
async def send_message(req: SendMessageRequest):
    if not channel.is_authorized:
        raise HTTPException(503, "Not authorized")
    return await channel.send_message(req.chat_id, req.text)


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=HOST, port=PORT)
