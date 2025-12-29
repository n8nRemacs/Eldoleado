"""
MAX User API Client (WebSocket)

Full-featured client for MAX messenger User API.
Based on reverse-engineered protocol from MAX Android APK.

WebSocket: wss://ws-api.oneme.ru/websocket
Protocol: JSON over WebSocket with opcodes
"""

import asyncio
import json
import logging
import time
import uuid
from typing import Optional, Dict, Callable, Awaitable, Any, List
from enum import IntEnum

import websockets
from websockets.exceptions import ConnectionClosed

logger = logging.getLogger(__name__)


class MaxAPIError(Exception):
    """MAX API error."""
    def __init__(self, message: str, code: str = "", payload: dict = None):
        self.message = message
        self.code = code
        self.payload = payload or {}
        super().__init__(self.message)


class Opcodes(IntEnum):
    """MAX WebSocket Protocol Opcodes (from xhb.java)"""

    # System (1-5)
    PING = 1
    DEBUG = 2
    RECONNECT = 3
    LOG = 5
    SESSION_INIT = 6  # HELLO

    # Auth (16-127)
    PROFILE = 16
    AUTH_REQUEST = 17  # Start SMS auth
    AUTH = 18  # Verify SMS code
    LOGIN = 19  # Login by token
    LOGOUT = 20
    SYNC = 21
    CONFIG = 22
    AUTH_CONFIRM = 23
    PRESET_AVATARS = 25
    SESSIONS_INFO = 96
    SESSIONS_CLOSE = 97
    PHONE_BIND_REQUEST = 98
    PHONE_BIND_CONFIRM = 99
    AUTH_LOGIN_RESTORE_PASSWORD = 101
    AUTH_2FA_DETAILS = 104
    AUTH_VALIDATE_PASSWORD = 107
    AUTH_VALIDATE_HINT = 108
    AUTH_VERIFY_EMAIL = 109
    AUTH_CHECK_EMAIL = 110
    AUTH_SET_2FA = 111
    AUTH_CREATE_TRACK = 112
    AUTH_CHECK_PASSWORD = 113
    AUTH_LOGIN_CHECK_PASSWORD = 115
    AUTH_LOGIN_PROFILE_DELETE = 116

    # Contacts (32-46)
    CONTACT_INFO = 32
    CONTACT_ADD = 33
    CONTACT_UPDATE = 34
    CONTACT_PRESENCE = 35
    CONTACT_LIST = 36
    CONTACT_SEARCH = 37
    CONTACT_MUTUAL = 38
    CONTACT_PHOTOS = 39
    CONTACT_SORT = 40
    CONTACT_VERIFY = 42
    REMOVE_CONTACT_PHOTO = 43
    CONTACT_INFO_BY_PHONE = 46

    # Assets (26-29, 259-261)
    ASSETS_GET = 26
    ASSETS_UPDATE = 27
    ASSETS_GET_BY_IDS = 28
    ASSETS_ADD = 29
    ASSETS_REMOVE = 259
    ASSETS_MOVE = 260
    ASSETS_LIST_MODIFY = 261

    # Chats (48-63)
    CHAT_INFO = 48
    CHAT_HISTORY = 49
    CHAT_MARK = 50  # Read message
    CHAT_MEDIA = 51
    CHAT_DELETE = 52
    CHATS_LIST = 53
    CHAT_CLEAR = 54
    CHAT_UPDATE = 55  # Settings
    CHAT_CHECK_LINK = 56
    CHAT_JOIN = 57
    CHAT_LEAVE = 58
    CHAT_MEMBERS = 59
    PUBLIC_SEARCH = 60
    CHAT_CLOSE = 61
    CHAT_CREATE = 63

    # Messages (64-74, 178-181)
    MSG_SEND = 64
    MSG_TYPING = 65
    MSG_DELETE = 66
    MSG_EDIT = 67
    CHAT_SEARCH = 68
    MSG_SHARE_PREVIEW = 70
    MSG_GET = 71
    MSG_SEARCH_TOUCH = 72
    MSG_SEARCH = 73
    MSG_GET_STAT = 74
    MSG_DELETE_RANGE = 92
    MSG_REACTION = 178
    MSG_CANCEL_REACTION = 179
    MSG_GET_REACTIONS = 180
    MSG_GET_DETAILED_REACTIONS = 181

    # Video calls (75-84, 195)
    CHAT_SUBSCRIBE = 75
    VIDEO_CHAT_START = 76
    CHAT_MEMBERS_UPDATE = 77
    VIDEO_CHAT_START_ACTIVE = 78
    VIDEO_CHAT_HISTORY = 79
    VIDEO_CHAT_CREATE_JOIN_LINK = 84
    VIDEO_CHAT_MEMBERS = 195

    # Files (80-90)
    PHOTO_UPLOAD = 80
    STICKER_UPLOAD = 81
    VIDEO_UPLOAD = 82
    VIDEO_PLAY = 83
    CHAT_PIN_SET_VISIBILITY = 86
    FILE_UPLOAD = 87
    FILE_DOWNLOAD = 88
    LINK_INFO = 89

    # Notifications / Events (128-159)
    NOTIF_MESSAGE = 128
    NOTIF_TYPING = 129
    NOTIF_MARK = 130
    NOTIF_CONTACT = 131
    NOTIF_PRESENCE = 132
    NOTIF_CONFIG = 134
    NOTIF_CHAT = 135
    NOTIF_ATTACH = 136
    NOTIF_CALL_START = 137
    NOTIF_CONTACT_SORT = 139
    NOTIF_MSG_DELETE_RANGE = 140
    NOTIF_MSG_DELETE = 142
    NOTIF_CALLBACK_ANSWER = 143
    NOTIF_LOCATION = 147
    NOTIF_LOCATION_REQUEST = 148
    NOTIF_ASSETS_UPDATE = 150
    NOTIF_DRAFT = 152
    NOTIF_DRAFT_DISCARD = 153
    NOTIF_MSG_DELAYED = 154
    NOTIF_MSG_REACTIONS_CHANGED = 155
    NOTIF_MSG_YOU_REACTED = 156
    OK_TOKEN = 158
    NOTIF_PROFILE = 159

    # Other
    CHAT_COMPLAIN = 117
    MSG_SEND_CALLBACK = 118
    SUSPEND_BOT = 119
    LOCATION_STOP = 124
    LOCATION_SEND = 125
    LOCATION_REQUEST = 126
    GET_LAST_MENTIONS = 127
    CHAT_BOT_COMMANDS = 144
    BOT_INFO = 145
    WEB_APP_INIT_DATA = 160
    COMPLAIN = 161
    COMPLAIN_REASONS_GET = 162
    DRAFT_SAVE = 176
    DRAFT_DISCARD = 177
    STICKER_CREATE = 193
    STICKER_SUGGEST = 194
    CHAT_HIDE = 196
    CHAT_SEARCH_COMMON_PARTICIPANTS = 198
    PROFILE_DELETE = 199
    PROFILE_DELETE_TIME = 200
    FOLDERS_GET = 272
    FOLDERS_GET_BY_ID = 273
    FOLDERS_UPDATE = 274
    FOLDERS_REORDER = 275
    FOLDERS_DELETE = 276
    NOTIF_FOLDERS = 277
    NOTIF_BANNERS = 292


class AttachType(IntEnum):
    """Attachment types (from Protos.java)"""
    UNKNOWN = 0
    PHOTO = 2
    VIDEO = 3
    AUDIO = 4
    STICKER = 5
    SHARE = 6
    APP = 7
    CALL = 8
    MUSIC = 9
    FILE = 10
    CONTACT = 11
    PRESENT = 12
    INLINE_KEYBOARD = 13
    LOCATION = 14
    DAILY_MEDIA = 15
    WIDGET = 16


class MaxClient:
    """
    MAX User API Client via WebSocket

    Usage:
        client = MaxClient(
            on_message=handle_message,
            on_typing=handle_typing,
            on_presence=handle_presence
        )
        await client.connect()

        # Login by token (recommended)
        await client.login_by_token(token, device_id)

        # Or start new auth
        result = await client.start_auth(phone="+79001234567")
        sms_token = result['token']
        await client.verify_code(sms_token, code="123456")

        # Send message
        await client.send_message(chat_id=123, text="Hello!")

        await client.close()
    """

    WS_URL = "wss://ws-api.oneme.ru/websocket"
    PROTOCOL_VERSION = 11
    KEEPALIVE_INTERVAL = 25  # seconds (max 30)

    def __init__(
        self,
        on_message: Callable[[dict], Awaitable[None]] = None,
        on_typing: Callable[[dict], Awaitable[None]] = None,
        on_presence: Callable[[dict], Awaitable[None]] = None,
        on_chat_update: Callable[[dict], Awaitable[None]] = None,
        on_disconnect: Callable[[], Awaitable[None]] = None,
    ):
        self._ws: Optional[websockets.WebSocketClientProtocol] = None
        self._seq = 0
        self._pending: Dict[int, asyncio.Future] = {}
        self._keepalive_task: Optional[asyncio.Task] = None
        self._receive_task: Optional[asyncio.Task] = None
        self._device_id: str = str(uuid.uuid4())
        self._connected = False
        self._authenticated = False

        # Callbacks
        self.on_message = on_message
        self.on_typing = on_typing
        self.on_presence = on_presence
        self.on_chat_update = on_chat_update
        self.on_disconnect = on_disconnect

        # State
        self.profile: dict = {}
        self.login_token: str = ""
        self.chats: Dict[int, dict] = {}

    @property
    def connected(self) -> bool:
        return self._connected

    @property
    def authenticated(self) -> bool:
        return self._authenticated

    @property
    def device_id(self) -> str:
        return self._device_id

    async def connect(self) -> None:
        """Connect to MAX WebSocket."""
        if self._connected:
            return

        headers = {
            "Origin": "https://web.max.ru",
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"
        }

        try:
            self._ws = await websockets.connect(
                self.WS_URL,
                additional_headers=headers,
                ping_interval=None,
                ping_timeout=None
            )
            self._connected = True

            # Start receive loop
            self._receive_task = asyncio.create_task(self._receive_loop())

            # Send Hello
            await self._hello()

            # Start keepalive
            self._keepalive_task = asyncio.create_task(self._keepalive_loop())

            logger.info("MAX client connected")

        except Exception as e:
            self._connected = False
            raise MaxAPIError(f"Connection failed: {e}")

    async def close(self) -> None:
        """Close connection."""
        self._connected = False
        self._authenticated = False

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
            self._ws = None

        logger.info("MAX client closed")

    def _next_seq(self) -> int:
        self._seq += 1
        return self._seq

    async def _send(self, opcode: int, payload: dict, timeout: float = 30.0) -> dict:
        """Send request and wait for response."""
        if not self._connected:
            raise MaxAPIError("Not connected")

        seq = self._next_seq()

        packet = {
            "ver": self.PROTOCOL_VERSION,
            "cmd": 0,  # 0 = request
            "seq": seq,
            "opcode": opcode,
            "payload": payload
        }

        future = asyncio.get_event_loop().create_future()
        self._pending[seq] = future

        try:
            await self._ws.send(json.dumps(packet))
            logger.debug(f"SEND: opcode={opcode}, seq={seq}")

            response = await asyncio.wait_for(future, timeout=timeout)
            return response

        except asyncio.TimeoutError:
            self._pending.pop(seq, None)
            raise MaxAPIError(f"Request timeout: opcode {opcode}")
        except Exception as e:
            self._pending.pop(seq, None)
            raise

    async def _receive_loop(self) -> None:
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
            self._connected = False
            if self.on_disconnect:
                await self.on_disconnect()
        except asyncio.CancelledError:
            pass

    async def _handle_packet(self, packet: dict) -> None:
        """Handle incoming packet."""
        cmd = packet.get("cmd")
        seq = packet.get("seq")
        opcode = packet.get("opcode")
        payload = packet.get("payload", {})

        logger.debug(f"RECV: cmd={cmd}, seq={seq}, opcode={opcode}")

        # Error response (cmd=3)
        if cmd == 3:
            error_msg = (
                payload.get("message") or
                payload.get("localizedMessage") or
                payload.get("description") or
                "Unknown error"
            )
            error_code = payload.get("error", "")
            logger.error(f"MAX API Error: {error_msg} (code: {error_code})")

            if seq in self._pending:
                future = self._pending.pop(seq)
                if not future.done():
                    future.set_exception(MaxAPIError(error_msg, code=error_code, payload=payload))
            return

        # Response (cmd=1)
        if cmd == 1:
            if seq in self._pending:
                future = self._pending.pop(seq)
                if not future.done():
                    future.set_result(payload)
                return

            # Unsolicited events
            await self._handle_event(opcode, payload)

    async def _handle_event(self, opcode: int, payload: dict) -> None:
        """Handle unsolicited events."""
        try:
            if opcode == Opcodes.NOTIF_MESSAGE and self.on_message:
                await self.on_message(payload)
            elif opcode == Opcodes.NOTIF_TYPING and self.on_typing:
                await self.on_typing(payload)
            elif opcode == Opcodes.NOTIF_PRESENCE and self.on_presence:
                await self.on_presence(payload)
            elif opcode == Opcodes.NOTIF_CHAT and self.on_chat_update:
                await self.on_chat_update(payload)
            else:
                logger.debug(f"Unhandled event opcode {opcode}")
        except Exception as e:
            logger.error(f"Error in event handler: {e}")

    async def _keepalive_loop(self) -> None:
        """Send keepalive packets."""
        try:
            while self._connected:
                await asyncio.sleep(self.KEEPALIVE_INTERVAL)
                if self._connected:
                    await self._send(Opcodes.PING, {"interactive": False})
                    logger.debug("Keepalive sent")
        except asyncio.CancelledError:
            pass
        except Exception as e:
            logger.error(f"Keepalive error: {e}")

    async def _hello(self) -> dict:
        """Send Hello packet to initialize session."""
        payload = {
            "userAgent": {
                "deviceType": "WEB",
                "locale": "ru_RU",
                "osVersion": "Windows 10",
                "deviceName": "Chrome",
                "appVersion": "25.12.1",
                "screen": "1920x1080",
                "timezone": "Europe/Moscow"
            },
            "deviceId": self._device_id
        }
        return await self._send(Opcodes.SESSION_INIT, payload)

    # ==================== AUTH ====================

    async def create_auth_track(self) -> dict:
        """
        Create QR auth track (like web.max.ru does).

        This is the preferred auth method for web clients.
        Returns trackId which should be encoded in QR code.
        User scans QR with MAX mobile app to authenticate.

        Returns:
            Dict with 'trackId' for QR code
        """
        return await self._send(Opcodes.AUTH_CREATE_TRACK, {})

    async def start_auth(self, phone: str, language: str = "ru") -> dict:
        """
        Start SMS authentication.

        NOTE: SMS auth may be blocked by GeoIP. Use create_auth_track()
        for QR-based auth instead (like web.max.ru does).

        Args:
            phone: Phone number with country code (+79001234567)
            language: Language code

        Returns:
            Dict with 'token' for verify_code step
        """
        payload = {
            "phone": phone,
            "type": "START_AUTH",
            "language": language
        }
        return await self._send(Opcodes.AUTH_REQUEST, payload)

    async def verify_code(self, token: str, code: str) -> dict:
        """
        Verify SMS code.

        Args:
            token: Token from start_auth
            code: SMS code (6 digits)

        Returns:
            Dict with profile and login token
        """
        payload = {
            "token": token,
            "verifyCode": code,
            "authTokenType": "CHECK_CODE"
        }
        response = await self._send(Opcodes.AUTH, payload)

        if "profile" in response:
            self.profile = response["profile"]
            self._authenticated = True
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
        response = await self._send(Opcodes.AUTH, payload)

        if "profile" in response:
            self.profile = response["profile"]
            self._authenticated = True
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
            device_id: Device ID (must match the one used during auth)
            chats_count: Number of chats to preload

        Returns:
            Dict with profile and initial chats
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
        response = await self._send(Opcodes.LOGIN, payload)

        if "profile" in response:
            self.profile = response["profile"]
            self._authenticated = True
        self.login_token = token

        # Cache initial chats
        if "chats" in response:
            for chat in response.get("chats", []):
                chat_id = chat.get("chatId")
                if chat_id:
                    self.chats[chat_id] = chat

        return response

    async def logout(self) -> dict:
        """Logout from current session."""
        response = await self._send(Opcodes.LOGOUT, {})
        self._authenticated = False
        self.profile = {}
        self.login_token = ""
        return response

    async def get_sessions(self) -> dict:
        """Get list of active sessions."""
        return await self._send(Opcodes.SESSIONS_INFO, {})

    async def close_sessions(self, session_ids: List[str]) -> dict:
        """Close specific sessions."""
        return await self._send(Opcodes.SESSIONS_CLOSE, {"sessionIds": session_ids})

    # ==================== CHATS ====================

    async def get_chats(self, count: int = 40, offset: int = 0) -> dict:
        """Get list of chats."""
        payload = {
            "count": count,
            "offset": offset
        }
        response = await self._send(Opcodes.CHATS_LIST, payload)

        # Cache chats
        for chat in response.get("chats", []):
            chat_id = chat.get("chatId")
            if chat_id:
                self.chats[chat_id] = chat

        return response

    async def get_chat(self, chat_id: int, count: int = 50) -> dict:
        """Get chat info with message history."""
        payload = {
            "chatId": chat_id,
            "count": count
        }
        return await self._send(Opcodes.CHAT_HISTORY, payload)

    async def get_chat_info(self, chat_id: int) -> dict:
        """Get chat info without messages."""
        return await self._send(Opcodes.CHAT_INFO, {"chatId": chat_id})

    async def create_chat(
        self,
        user_id: int = None,
        title: str = None,
        user_ids: List[int] = None,
        chat_type: str = "CHAT"
    ) -> dict:
        """
        Create new chat.

        Args:
            user_id: User ID for private chat
            title: Title for group chat
            user_ids: List of user IDs for group
            chat_type: "CHAT" for group, "CHANNEL" for channel
        """
        payload = {
            "chatType": chat_type,
            "cid": int(time.time() * 1000)
        }
        if user_id:
            payload["subjectId"] = user_id
            payload["subjectType"] = "USER"
        if title:
            payload["title"] = title
        if user_ids:
            payload["userIds"] = user_ids

        return await self._send(Opcodes.CHAT_CREATE, payload)

    async def delete_chat(self, chat_id: int, for_all: bool = False) -> dict:
        """Delete chat."""
        payload = {
            "chatId": chat_id,
            "forAll": for_all
        }
        return await self._send(Opcodes.CHAT_DELETE, payload)

    async def clear_chat(self, chat_id: int, for_all: bool = False) -> dict:
        """Clear chat history."""
        payload = {
            "chatId": chat_id,
            "forAll": for_all
        }
        return await self._send(Opcodes.CHAT_CLEAR, payload)

    async def update_chat(
        self,
        chat_id: int,
        title: str = None,
        description: str = None,
        pinned: bool = None,
        muted: bool = None
    ) -> dict:
        """Update chat settings."""
        payload = {"chatId": chat_id}
        if title is not None:
            payload["title"] = title
        if description is not None:
            payload["description"] = description
        if pinned is not None:
            payload["pinned"] = pinned
        if muted is not None:
            payload["muted"] = muted

        return await self._send(Opcodes.CHAT_UPDATE, payload)

    async def join_chat(self, link: str) -> dict:
        """Join chat by invite link."""
        return await self._send(Opcodes.CHAT_JOIN, {"link": link})

    async def leave_chat(self, chat_id: int) -> dict:
        """Leave chat."""
        return await self._send(Opcodes.CHAT_LEAVE, {"chatId": chat_id})

    async def get_chat_members(self, chat_id: int, count: int = 100, offset: int = 0) -> dict:
        """Get chat members."""
        payload = {
            "chatId": chat_id,
            "count": count,
            "offset": offset
        }
        return await self._send(Opcodes.CHAT_MEMBERS, payload)

    async def set_typing(self, chat_id: int, typing: bool = True) -> dict:
        """Set typing indicator."""
        payload = {
            "chatId": chat_id,
            "typing": typing
        }
        return await self._send(Opcodes.MSG_TYPING, payload)

    async def read_message(self, chat_id: int, message_id: str = None) -> dict:
        """Mark messages as read."""
        payload = {"chatId": chat_id}
        if message_id:
            payload["messageId"] = message_id
        return await self._send(Opcodes.CHAT_MARK, payload)

    # ==================== MESSAGES ====================

    async def send_message(
        self,
        chat_id: int,
        text: str = "",
        attaches: List[dict] = None,
        reply_to: str = None,
        forward_from: int = None,
        forward_messages: List[str] = None,
        notify: bool = True
    ) -> dict:
        """
        Send message to chat.

        Args:
            chat_id: Chat ID
            text: Message text
            attaches: List of attachments (see AttachType)
            reply_to: Message ID to reply to
            forward_from: Chat ID to forward from
            forward_messages: List of message IDs to forward
            notify: Enable notifications

        Returns:
            Sent message info
        """
        message = {
            "text": text,
            "cid": int(time.time() * 1000),
            "elements": [],
            "attaches": attaches or []
        }

        if reply_to:
            message["link"] = {
                "type": "REPLY",
                "messageId": reply_to
            }
        elif forward_from and forward_messages:
            message["link"] = {
                "type": "FORWARD",
                "chatId": forward_from,
                "messageIds": forward_messages
            }

        payload = {
            "chatId": chat_id,
            "message": message,
            "notify": notify
        }

        return await self._send(Opcodes.MSG_SEND, payload)

    async def edit_message(
        self,
        chat_id: int,
        message_id: str,
        text: str,
        attaches: List[dict] = None
    ) -> dict:
        """Edit message."""
        message = {"text": text}
        if attaches is not None:
            message["attaches"] = attaches

        payload = {
            "chatId": chat_id,
            "messageId": message_id,
            "message": message
        }
        return await self._send(Opcodes.MSG_EDIT, payload)

    async def delete_message(
        self,
        chat_id: int,
        message_ids: List[str],
        for_me: bool = False
    ) -> dict:
        """Delete messages."""
        payload = {
            "chatId": chat_id,
            "messageIds": message_ids,
            "forMe": for_me
        }
        return await self._send(Opcodes.MSG_DELETE, payload)

    async def get_message(self, chat_id: int, message_id: str) -> dict:
        """Get specific message."""
        payload = {
            "chatId": chat_id,
            "messageId": message_id
        }
        return await self._send(Opcodes.MSG_GET, payload)

    async def search_messages(
        self,
        query: str,
        chat_id: int = None,
        count: int = 20
    ) -> dict:
        """Search messages."""
        payload = {
            "query": query,
            "count": count
        }
        if chat_id:
            payload["chatId"] = chat_id

        return await self._send(Opcodes.MSG_SEARCH, payload)

    async def add_reaction(self, chat_id: int, message_id: str, reaction: str) -> dict:
        """Add reaction to message."""
        payload = {
            "chatId": chat_id,
            "messageId": message_id,
            "reaction": reaction,
            "reactionType": 0  # EMOJI
        }
        return await self._send(Opcodes.MSG_REACTION, payload)

    async def remove_reaction(self, chat_id: int, message_id: str, reaction: str) -> dict:
        """Remove reaction from message."""
        payload = {
            "chatId": chat_id,
            "messageId": message_id,
            "reaction": reaction
        }
        return await self._send(Opcodes.MSG_CANCEL_REACTION, payload)

    async def get_reactions(self, chat_id: int, message_id: str) -> dict:
        """Get message reactions."""
        payload = {
            "chatId": chat_id,
            "messageId": message_id
        }
        return await self._send(Opcodes.MSG_GET_REACTIONS, payload)

    # ==================== CONTACTS ====================

    async def get_contacts(self, user_ids: List[int] = None) -> dict:
        """Get contact info."""
        payload = {}
        if user_ids:
            payload["userIds"] = user_ids
        return await self._send(Opcodes.CONTACT_INFO, payload)

    async def get_contact_list(self, count: int = 100, offset: int = 0) -> dict:
        """Get contact list."""
        payload = {
            "count": count,
            "offset": offset
        }
        return await self._send(Opcodes.CONTACT_LIST, payload)

    async def search_contacts(self, query: str, count: int = 20) -> dict:
        """Search contacts."""
        payload = {
            "query": query,
            "count": count
        }
        return await self._send(Opcodes.CONTACT_SEARCH, payload)

    async def add_contact(self, user_id: int, name: str = None) -> dict:
        """Add contact."""
        payload = {"userId": user_id}
        if name:
            payload["name"] = name
        return await self._send(Opcodes.CONTACT_ADD, payload)

    # ==================== FILES ====================

    async def get_upload_url(self, attach_type: AttachType = AttachType.FILE) -> dict:
        """Get URL for file upload."""
        opcode = {
            AttachType.PHOTO: Opcodes.PHOTO_UPLOAD,
            AttachType.VIDEO: Opcodes.VIDEO_UPLOAD,
            AttachType.FILE: Opcodes.FILE_UPLOAD,
        }.get(attach_type, Opcodes.FILE_UPLOAD)

        return await self._send(opcode, {"count": 1})

    async def get_download_url(self, file_id: int) -> dict:
        """Get URL for file download."""
        return await self._send(Opcodes.FILE_DOWNLOAD, {"fileId": file_id})

    # ==================== PROFILE ====================

    async def update_profile(
        self,
        first_name: str = None,
        last_name: str = None,
        description: str = None
    ) -> dict:
        """Update user profile."""
        payload = {}
        if first_name is not None:
            payload["firstName"] = first_name
        if last_name is not None:
            payload["lastName"] = last_name
        if description is not None:
            payload["description"] = description

        return await self._send(Opcodes.PROFILE, payload)

    async def get_config(self) -> dict:
        """Get server configuration."""
        return await self._send(Opcodes.CONFIG, {})
