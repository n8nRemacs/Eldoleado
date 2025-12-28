"""
Browser Service Client - клиент для MCP серверов

Использование в MCP серверах:
    from browser_client import BrowserClient

    client = BrowserClient(tenant_id="remaks")

    # Avito
    await client.avito.get_chats()
    await client.avito.send_message(chat_id, text)

    # WhatsApp
    qr = await client.whatsapp.get_qr()
    await client.whatsapp.send_message(chat_id, text)
"""

import httpx
from typing import Optional, Dict, Any, List
from dataclasses import dataclass


@dataclass
class BrowserServiceConfig:
    base_url: str = "http://155.212.221.189:8792"
    timeout: float = 30.0


class ChannelClient:
    """Base class for channel clients"""

    def __init__(self, http: httpx.AsyncClient, tenant_id: str, channel: str):
        self._http = http
        self._tenant_id = tenant_id
        self._channel = channel

    def _url(self, path: str) -> str:
        return f"/session/{self._tenant_id}/channel/{self._channel}/{path}"

    async def open(self) -> Dict[str, Any]:
        """Open channel page"""
        resp = await self._http.post(self._url("open"))
        resp.raise_for_status()
        return resp.json()

    async def is_logged_in(self) -> bool:
        """Check if logged in"""
        resp = await self._http.get(self._url("status"))
        resp.raise_for_status()
        return resp.json().get("logged_in", False)

    async def get_chats(self, limit: int = 50) -> List[Dict[str, Any]]:
        """Get list of chats"""
        resp = await self._http.get(self._url("chats"), params={"limit": limit})
        resp.raise_for_status()
        return resp.json().get("chats", [])

    async def get_messages(self, chat_id: str, limit: int = 50) -> List[Dict[str, Any]]:
        """Get messages from chat"""
        resp = await self._http.get(self._url(f"messages/{chat_id}"), params={"limit": limit})
        resp.raise_for_status()
        return resp.json().get("messages", [])

    async def send_message(self, chat_id: str, text: str) -> Dict[str, Any]:
        """Send text message"""
        resp = await self._http.post(
            self._url("send"),
            json={"chat_id": chat_id, "text": text}
        )
        resp.raise_for_status()
        return resp.json()

    async def start_listening(self, webhook_url: str) -> Dict[str, Any]:
        """Start listening for new messages"""
        resp = await self._http.post(
            self._url("listen/start"),
            json={"webhook_url": webhook_url}
        )
        resp.raise_for_status()
        return resp.json()


class AvitoClient(ChannelClient):
    """Avito-specific methods"""

    def __init__(self, http: httpx.AsyncClient, tenant_id: str):
        super().__init__(http, tenant_id, "avito")

    async def login(self, wait_timeout: int = 300) -> Dict[str, Any]:
        """
        Start login process.
        Browser will show login form - user needs to enter credentials.
        """
        resp = await self._http.post(
            self._url("login"),
            json={"wait_timeout": wait_timeout},
            timeout=wait_timeout + 10
        )
        resp.raise_for_status()
        return resp.json()

    async def get_profile(self) -> Dict[str, Any]:
        """Get current user profile"""
        resp = await self._http.get(f"/session/{self._tenant_id}/avito/profile")
        resp.raise_for_status()
        return resp.json()


class WhatsAppClient(ChannelClient):
    """WhatsApp-specific methods"""

    def __init__(self, http: httpx.AsyncClient, tenant_id: str):
        super().__init__(http, tenant_id, "whatsapp")

    async def get_qr(self) -> Dict[str, Any]:
        """Get QR code for login"""
        resp = await self._http.get(self._url("qr"))
        resp.raise_for_status()
        return resp.json()

    async def wait_for_login(self, timeout: int = 120) -> bool:
        """Wait until QR is scanned and logged in"""
        resp = await self._http.post(
            self._url("wait-login"),
            json={"timeout": timeout},
            timeout=timeout + 10
        )
        resp.raise_for_status()
        return resp.json().get("logged_in", False)


class MaxClient(ChannelClient):
    """MAX (VK Teams) specific methods"""

    def __init__(self, http: httpx.AsyncClient, tenant_id: str):
        super().__init__(http, tenant_id, "max")

    async def get_qr(self) -> Dict[str, Any]:
        """Get QR code for login"""
        resp = await self._http.get(self._url("qr"))
        resp.raise_for_status()
        return resp.json()

    async def wait_for_login(self, timeout: int = 120) -> bool:
        """Wait until QR is scanned and logged in"""
        resp = await self._http.post(
            self._url("wait-login"),
            json={"timeout": timeout},
            timeout=timeout + 10
        )
        resp.raise_for_status()
        return resp.json().get("logged_in", False)


class BrowserClient:
    """
    Main client for Browser Service.

    Usage:
        async with BrowserClient(tenant_id="remaks") as client:
            # Create session (once)
            await client.create_session()

            # Avito
            await client.avito.open()
            await client.avito.login()
            chats = await client.avito.get_chats()
            await client.avito.send_message(chats[0]["id"], "Hello!")

            # WhatsApp
            await client.whatsapp.open()
            qr = await client.whatsapp.get_qr()  # Show to user
            await client.whatsapp.wait_for_login()
            await client.whatsapp.send_message(chat_id, "Hello!")
    """

    def __init__(
        self,
        tenant_id: str,
        base_url: str = "http://155.212.221.189:8792",
        timeout: float = 30.0
    ):
        self.tenant_id = tenant_id
        self.base_url = base_url
        self._http = httpx.AsyncClient(base_url=base_url, timeout=timeout)

        # Channel clients
        self.avito = AvitoClient(self._http, tenant_id)
        self.whatsapp = WhatsAppClient(self._http, tenant_id)
        self.max = MaxClient(self._http, tenant_id)

    async def __aenter__(self):
        return self

    async def __aexit__(self, exc_type, exc_val, exc_tb):
        await self._http.aclose()

    async def create_session(self) -> Dict[str, Any]:
        """Create browser session for tenant"""
        resp = await self._http.post(f"/session/{self.tenant_id}/create")
        resp.raise_for_status()
        return resp.json()

    async def close_session(self) -> Dict[str, Any]:
        """Close browser session"""
        resp = await self._http.delete(f"/session/{self.tenant_id}")
        resp.raise_for_status()
        return resp.json()

    async def get_status(self) -> Dict[str, Any]:
        """Get session status"""
        resp = await self._http.get(f"/session/{self.tenant_id}/status")
        resp.raise_for_status()
        return resp.json()

    async def health(self) -> Dict[str, Any]:
        """Check service health"""
        resp = await self._http.get("/health")
        resp.raise_for_status()
        return resp.json()


# Sync wrapper for non-async code
class BrowserClientSync:
    """
    Sync wrapper for BrowserClient.

    Usage:
        client = BrowserClientSync(tenant_id="remaks")
        client.create_session()
        chats = client.avito.get_chats()
    """

    def __init__(self, tenant_id: str, base_url: str = "http://155.212.221.189:8792"):
        self.tenant_id = tenant_id
        self.base_url = base_url
        self._http = httpx.Client(base_url=base_url, timeout=30.0)

    def create_session(self) -> Dict[str, Any]:
        resp = self._http.post(f"/session/{self.tenant_id}/create")
        resp.raise_for_status()
        return resp.json()

    def close_session(self) -> Dict[str, Any]:
        resp = self._http.delete(f"/session/{self.tenant_id}")
        resp.raise_for_status()
        return resp.json()

    @property
    def avito(self):
        return _SyncChannelWrapper(self._http, self.tenant_id, "avito")

    @property
    def whatsapp(self):
        return _SyncChannelWrapper(self._http, self.tenant_id, "whatsapp")

    @property
    def max(self):
        return _SyncChannelWrapper(self._http, self.tenant_id, "max")


class _SyncChannelWrapper:
    def __init__(self, http: httpx.Client, tenant_id: str, channel: str):
        self._http = http
        self._tenant_id = tenant_id
        self._channel = channel

    def _url(self, path: str) -> str:
        return f"/session/{self._tenant_id}/channel/{self._channel}/{path}"

    def open(self) -> Dict[str, Any]:
        resp = self._http.post(self._url("open"))
        resp.raise_for_status()
        return resp.json()

    def get_chats(self, limit: int = 50) -> List[Dict[str, Any]]:
        resp = self._http.get(self._url("chats"), params={"limit": limit})
        resp.raise_for_status()
        return resp.json().get("chats", [])

    def get_messages(self, chat_id: str, limit: int = 50) -> List[Dict[str, Any]]:
        resp = self._http.get(self._url(f"messages/{chat_id}"), params={"limit": limit})
        resp.raise_for_status()
        return resp.json().get("messages", [])

    def send_message(self, chat_id: str, text: str) -> Dict[str, Any]:
        resp = self._http.post(self._url("send"), json={"chat_id": chat_id, "text": text})
        resp.raise_for_status()
        return resp.json()

    def login(self, wait_timeout: int = 300) -> Dict[str, Any]:
        """For Avito"""
        resp = self._http.post(self._url("login"), json={"wait_timeout": wait_timeout}, timeout=wait_timeout + 10)
        resp.raise_for_status()
        return resp.json()

    def get_qr(self) -> Dict[str, Any]:
        """For WhatsApp/MAX"""
        resp = self._http.get(self._url("qr"))
        resp.raise_for_status()
        return resp.json()
