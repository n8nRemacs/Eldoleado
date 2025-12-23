"""
MAX User API Client (WebSocket)

Работает через WebSocket API как пользовательский клиент.
Документация: MAX_USER_API.md

WebSocket: wss://ws-api.oneme.ru/websocket
Авторизация: телефон + SMS или сохранённый токен
"""

import asyncio
import json
import logging
import time
import uuid
from typing import Optional, Dict, Callable, Awaitable, Any

import websockets
from websockets.exceptions import ConnectionClosed

logger = logging.getLogger(__name__)


class MaxUserAPIError(Exception):
    """MAX User API error."""
    def __init__(self, message: str, code: str = "", payload: dict = None):
        self.message = message
        self.code = code
        self.payload = payload or {}
        super().__init__(self.message)


class Opcodes:
    """MAX WebSocket Protocol Opcodes"""
    # Auth & System
    KEEPALIVE = 1
    HELLO = 6
    START_AUTH = 17
    VERIFY_CODE = 18
    LOGIN_BY_TOKEN = 19

    # Contacts
    GET_CONTACTS = 32

    # Chats
    GET_CHATS = 48
    GET_CHAT = 49
    READ_MESSAGE = 50
    SET_TYPING = 51
    GET_TYPING = 52
    CHAT_SETTINGS = 55

    # Messages
    SEND_MESSAGE = 64
    DELETE_MESSAGE = 66
    EDIT_MESSAGE = 67

    # Events (incoming only)
    NEW_MESSAGE = 128
    PRESENCE_UPDATE = 129


class MaxUserClient:
    """
    MAX User API Client via WebSocket

    Usage:
        client = MaxUserClient()
        await client.connect()

        # Login by token (if you have saved token)
        await client.login_by_token(token, device_id)

        # Or login by phone
        token_response = await client.start_auth(phone="+79001234567")
        sms_token = token_response['token']
        await client.verify_code(sms_token, code="123456")

        # Send message
        await client.send_message(chat_id=123, text="Привет!")

        # Set typing
        await client.set_typing(chat_id=123, typing=True)

        await client.close()
    """

    WS_URL = "wss://ws-api.oneme.ru/websocket"
    PROTOCOL_VERSION = 11
    KEEPALIVE_INTERVAL = 25  # seconds (30 max, use 25 for safety)

    def __init__(
        self,
        on_message: Callable[[dict], Awaitable[None]] = None,
        on_presence: Callable[[dict], Awaitable[None]] = None
    ):
        """
        Args:
            on_message: Callback for incoming messages (opcode 128)
            on_presence: Callback for presence updates (opcode 129)
        """
        self._ws: Optional[websockets.WebSocketClientProtocol] = None
        self._seq = 0
        self._pending: Dict[int, asyncio.Future] = {}
        self._keepalive_task: Optional[asyncio.Task] = None
        self._receive_task: Optional[asyncio.Task] = None
        self._device_id: str = str(uuid.uuid4())

        self.on_message = on_message
        self.on_presence = on_presence
        self.profile: dict = {}
        self.login_token: str = ""

    async def connect(self):
        """Connect to MAX WebSocket."""
        headers = {
            "Origin": "https://web.max.ru",
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
        }

        self._ws = await websockets.connect(
            self.WS_URL,
            extra_headers=headers,
            ping_interval=None,  # We handle keepalive ourselves
            ping_timeout=None
        )

        # Start receive loop
        self._receive_task = asyncio.create_task(self._receive_loop())

        # Send Hello
        await self._hello()

        # Start keepalive
        self._keepalive_task = asyncio.create_task(self._keepalive_loop())

        logger.info("MAX User client connected")

    async def close(self):
        """Close connection."""
        if self._keepalive_task:
            self._keepalive_task.cancel()
            try:
                await self._keepalive_task
            except asyncio.CancelledError:
                pass

        if self._receive_task:
            self._receive_task.cancel()
            try:
                await self._receive_task
            except asyncio.CancelledError:
                pass

        if self._ws:
            await self._ws.close()

        logger.info("MAX User client closed")

    def _next_seq(self) -> int:
        """Get next sequence number."""
        self._seq += 1
        return self._seq

    async def _send(self, opcode: int, payload: dict) -> dict:
        """Send request and wait for response."""
        seq = self._next_seq()

        packet = {
            "ver": self.PROTOCOL_VERSION,
            "cmd": 0,  # 0 = request
            "seq": seq,
            "opcode": opcode,
            "payload": payload
        }

        # Create future for response
        future = asyncio.get_event_loop().create_future()
        self._pending[seq] = future

        try:
            await self._ws.send(json.dumps(packet))
            logger.debug(f"Sent opcode {opcode}, seq {seq}")

            # Wait for response (timeout 30s)
            response = await asyncio.wait_for(future, timeout=30.0)
            return response

        except asyncio.TimeoutError:
            self._pending.pop(seq, None)
            raise MaxUserAPIError(f"Request timeout: opcode {opcode}")
        except Exception as e:
            self._pending.pop(seq, None)
            raise

    async def _receive_loop(self):
        """Receive and process incoming packets."""
        try:
            async for message in self._ws:
                try:
                    packet = json.loads(message)
                    await self._handle_packet(packet)
                except json.JSONDecodeError:
                    logger.warning(f"Invalid JSON: {message[:100]}")
                except Exception as e:
                    logger.error(f"Error handling packet: {e}")

        except ConnectionClosed:
            logger.info("WebSocket connection closed")
        except asyncio.CancelledError:
            pass

    async def _handle_packet(self, packet: dict):
        """Handle incoming packet."""
        cmd = packet.get("cmd")
        seq = packet.get("seq")
        opcode = packet.get("opcode")
        payload = packet.get("payload", {})

        if cmd == 1:  # Response
            # Check if this is a response to our request
            if seq in self._pending:
                future = self._pending.pop(seq)
                if not future.done():
                    future.set_result(payload)
                return

            # Unsolicited event
            if opcode == Opcodes.NEW_MESSAGE and self.on_message:
                await self.on_message(payload)
            elif opcode == Opcodes.PRESENCE_UPDATE and self.on_presence:
                await self.on_presence(payload)
            else:
                logger.debug(f"Event opcode {opcode}: {payload}")

    async def _keepalive_loop(self):
        """Send keepalive packets every 25 seconds."""
        try:
            while True:
                await asyncio.sleep(self.KEEPALIVE_INTERVAL)
                await self._send(Opcodes.KEEPALIVE, {"interactive": False})
                logger.debug("Keepalive sent")
        except asyncio.CancelledError:
            pass
        except Exception as e:
            logger.error(f"Keepalive error: {e}")

    async def _hello(self):
        """Send Hello packet to initialize device."""
        payload = {
            "userAgent": {
                "deviceType": "WEB",
                "locale": "ru_RU",
                "osVersion": "Windows",
                "deviceName": "Eldoleado Client",
                "appVersion": "25.12.1",
                "screen": "1920x1080",
                "timezone": "Europe/Moscow"
            },
            "deviceId": self._device_id
        }
        await self._send(Opcodes.HELLO, payload)

    # ========== Auth Methods ==========

    async def start_auth(self, phone: str, language: str = "ru") -> dict:
        """
        Start SMS authentication.

        Args:
            phone: Phone number with country code (+79001234567)
            language: Language code (ru)

        Returns:
            Dict with 'token' for verify_code step
        """
        payload = {
            "phone": phone,
            "type": "START_AUTH",
            "language": language
        }
        return await self._send(Opcodes.START_AUTH, payload)

    async def verify_code(self, token: str, code: str) -> dict:
        """
        Verify SMS code.

        Args:
            token: Token from start_auth
            code: SMS code

        Returns:
            Dict with profile and login token
        """
        payload = {
            "token": token,
            "verifyCode": code,
            "authTokenType": "CHECK_CODE"
        }
        response = await self._send(Opcodes.VERIFY_CODE, payload)

        # Save profile and token
        if "profile" in response:
            self.profile = response["profile"]
        if "tokenAttrs" in response:
            self.login_token = response["tokenAttrs"].get("LOGIN", {}).get("token", "")

        return response

    async def verify_2fa(self, token: str, password: str) -> dict:
        """
        Verify 2FA password.

        Args:
            token: Token from start_auth
            password: 2FA password

        Returns:
            Dict with profile and login token
        """
        payload = {
            "token": token,
            "password": password,
            "authTokenType": "CHECK_PASSWORD"
        }
        response = await self._send(Opcodes.VERIFY_CODE, payload)

        if "profile" in response:
            self.profile = response["profile"]
        if "tokenAttrs" in response:
            self.login_token = response["tokenAttrs"].get("LOGIN", {}).get("token", "")

        return response

    async def login_by_token(
        self,
        token: str,
        device_id: str = None,
        chats_count: int = 40
    ) -> dict:
        """
        Login using saved token.

        Args:
            token: Login token from previous auth
            device_id: Device ID (same as used during auth)
            chats_count: Number of chats to preload

        Returns:
            Dict with profile
        """
        if device_id:
            self._device_id = device_id

        payload = {
            "interactive": True,
            "token": token,
            "chatsSync": 0,
            "contactsSync": 0,
            "chatsCount": chats_count
        }
        response = await self._send(Opcodes.LOGIN_BY_TOKEN, payload)

        if "profile" in response:
            self.profile = response["profile"]
        self.login_token = token

        return response

    # ========== Chat Methods ==========

    async def get_chats(self, count: int = 40, offset: int = 0) -> dict:
        """Get list of chats."""
        payload = {
            "count": count,
            "offset": offset
        }
        return await self._send(Opcodes.GET_CHATS, payload)

    async def get_chat(self, chat_id: int, count: int = 50) -> dict:
        """Get chat with message history."""
        payload = {
            "chatId": chat_id,
            "count": count
        }
        return await self._send(Opcodes.GET_CHAT, payload)

    async def set_typing(self, chat_id: int, typing: bool = True) -> dict:
        """
        Set typing indicator.

        Args:
            chat_id: Chat ID
            typing: True to show typing, False to stop
        """
        payload = {
            "chatId": chat_id,
            "typing": typing
        }
        return await self._send(Opcodes.SET_TYPING, payload)

    async def read_message(self, chat_id: int, message_id: str = None) -> dict:
        """Mark messages as read."""
        payload = {
            "chatId": chat_id
        }
        if message_id:
            payload["messageId"] = message_id

        return await self._send(Opcodes.READ_MESSAGE, payload)

    # ========== Message Methods ==========

    async def send_message(
        self,
        chat_id: int,
        text: str,
        attaches: list = None,
        reply_to: str = None,
        notify: bool = True
    ) -> dict:
        """
        Send message to chat.

        Args:
            chat_id: Chat ID
            text: Message text
            attaches: List of attachments
            reply_to: Message ID to reply to
            notify: Enable notifications

        Returns:
            Sent message info
        """
        message = {
            "text": text,
            "cid": int(time.time() * 1000),  # Client ID
            "elements": [],
            "attaches": attaches or []
        }

        if reply_to:
            message["link"] = {
                "type": "REPLY",
                "messageId": reply_to
            }

        payload = {
            "chatId": chat_id,
            "message": message,
            "notify": notify
        }

        return await self._send(Opcodes.SEND_MESSAGE, payload)

    async def edit_message(self, chat_id: int, message_id: str, text: str) -> dict:
        """Edit message."""
        payload = {
            "chatId": chat_id,
            "messageId": message_id,
            "message": {
                "text": text
            }
        }
        return await self._send(Opcodes.EDIT_MESSAGE, payload)

    async def delete_message(self, chat_id: int, message_id: str) -> dict:
        """Delete message."""
        payload = {
            "chatId": chat_id,
            "messageId": message_id
        }
        return await self._send(Opcodes.DELETE_MESSAGE, payload)

    # ========== Contacts ==========

    async def get_contacts(self, user_ids: list) -> dict:
        """Get user info by IDs."""
        payload = {
            "userIds": user_ids
        }
        return await self._send(Opcodes.GET_CONTACTS, payload)
