# MAX (VK Teams) Messenger Reverse Engineering Specification

## Project Goal

Create a Baileys-like library for MAX (VK Teams) Messenger that enables:
- Receiving incoming messages in real-time
- Sending text messages in response
- Managing multiple accounts (multi-tenant)

**NOT included:** Voice/video calls, conferences, file sharing, integrations.

## Target Use Case

```
User writes in MAX chat → Our service receives message → Operator responds → Message delivered to user
```

100% legitimate business communication. No spam, no automation of outbound messages.

## Background

MAX (formerly VK Teams, ICQ New) is a corporate messenger by VK. It uses a modified ICQ protocol (Oscar-based) with modern additions. Has both public API and internal mobile API.

**Known facts:**
- Uses WebSocket for real-time
- Has Bot API (limited)
- Mobile app has additional capabilities
- SSL pinning present

## Phase 1: Reconnaissance

### 1.1 APK Analysis
- [ ] Download latest MAX APK from official source / APKPure
- [ ] Decompile with JADX: `jadx -d ./decompiled max.apk`
- [ ] Analyze with apktool: `apktool d max.apk -o ./unpacked`
- [ ] Document package structure
- [ ] Identify networking libraries (OkHttp, Retrofit, etc.)

### 1.2 Network Traffic Analysis
- [ ] Setup mitmproxy/Charles on rooted device
- [ ] Install MAX with SSL pinning bypass (Frida)
- [ ] Capture authentication flow
- [ ] Capture message send/receive flow
- [ ] Document all API endpoints
- [ ] Identify WebSocket endpoints

### 1.3 Public API Research
- [ ] Study official Bot API documentation
- [ ] Compare Bot API vs Mobile API capabilities
- [ ] Identify rate limits and restrictions

### 1.4 Key Questions to Answer
- [ ] What protocol for real-time? (WebSocket / ICQ Oscar / Custom)
- [ ] How authentication works? (OAuth / Token / Session)
- [ ] Message format? (JSON / Protobuf / Binary)
- [ ] How SSL pinning is implemented?
- [ ] Encryption for messages? (E2E or transport only)

## Phase 2: Authentication Flow

### 2.1 Login Methods to Analyze
- [ ] Phone number + SMS code
- [ ] Email + password
- [ ] VK ID OAuth
- [ ] Corporate SSO (if applicable)

### 2.2 Session Management
- [ ] Token format (aimsid, sessionKey, etc.)
- [ ] Token lifetime and refresh
- [ ] Device registration
- [ ] Multi-device sync

### 2.3 Known Endpoints (from Bot API)
```
api.max.ru/bot/v1/
- self/get
- messages/sendText
- chats/getInfo
- events/get (long-polling)
```

### 2.4 Expected Deliverables
```python
class MaxAuth:
    async def login_phone(phone: str) -> AuthSession
    async def verify_sms(session: AuthSession, code: str) -> Credentials
    async def login_password(email: str, password: str) -> Credentials
    async def refresh_session(credentials: Credentials) -> Credentials
    async def logout(credentials: Credentials) -> bool
```

## Phase 3: Messaging Protocol

### 3.1 Message Types to Support
- [ ] Text messages
- [ ] Images (receive metadata)
- [ ] Read receipts (if available)
- [ ] Typing indicators
- [ ] Chat states (online/offline)

### 3.2 Real-time Connection Options

**Option A: Long-polling (Bot API style)**
```
GET /events/get?lastEventId=X&pollTime=30
```

**Option B: WebSocket (Mobile API)**
```
wss://api.max.ru/ws/...
```

### 3.3 Message Event Types (expected)
```json
{
  "type": "message",
  "chatId": "...",
  "from": {"userId": "...", "name": "..."},
  "text": "...",
  "timestamp": 1234567890
}
```

### 3.4 Expected Deliverables
```python
class MaxMessenger:
    async def connect(credentials: Credentials) -> Connection
    async def on_message(callback: Callable[[Message], None])
    async def send_message(chat_id: str, text: str) -> MessageResult
    async def get_chats() -> List[Chat]
    async def get_history(chat_id: str, limit: int) -> List[Message]
    async def set_typing(chat_id: str, typing: bool) -> None
```

## Phase 4: Anti-Detection

### 4.1 Behavioral Analysis
- [ ] Normal user patterns in MAX
- [ ] Rate limits (messages per minute)
- [ ] Flood protection mechanisms
- [ ] Account verification requirements

### 4.2 Device Fingerprinting
- [ ] Device ID format
- [ ] App version requirements
- [ ] OS version checks
- [ ] Client capabilities declaration

### 4.3 Bot vs User Distinction
- [ ] What features are bot-only?
- [ ] What features require user auth?
- [ ] Can user tokens access bot endpoints?

### 4.4 Mitigation Strategies
- [ ] Mimic official client headers
- [ ] Proper event acknowledgment
- [ ] Natural typing delays
- [ ] Session keepalive patterns

## Phase 5: Library Architecture

### 5.1 Core Components
```
max-lib/
├── auth/
│   ├── login.py
│   ├── session.py
│   ├── tokens.py
│   └── vk_oauth.py
├── messaging/
│   ├── connection.py
│   ├── websocket.py
│   ├── longpoll.py
│   └── types.py
├── api/
│   ├── client.py
│   ├── endpoints.py
│   └── models.py
├── crypto/
│   ├── ssl_bypass.py
│   └── oscar.py (if needed)
└── utils/
    ├── fingerprint.py
    └── ratelimit.py
```

### 5.2 Integration Interface
```python
from max_lib import MaxClient

client = MaxClient()
await client.login(phone="+79001234567")
await client.verify_code("123456")

@client.on_message
async def handle(msg: Message):
    if "привет" in msg.text.lower():
        await client.send(msg.chat_id, "Здравствуйте! Чем могу помочь?")

await client.connect()
```

### 5.3 MCP Integration
```python
# For use with existing MCP infrastructure
class MaxMCP:
    def __init__(self, config: MaxConfig):
        self.client = MaxClient()

    async def start(self):
        await self.client.connect()

    async def send_message(self, chat_id: str, text: str):
        return await self.client.send(chat_id, text)

    def on_incoming(self, callback):
        self.client.on_message(callback)
```

## Comparison: Bot API vs Mobile API

| Feature | Bot API | Mobile API |
|---------|---------|------------|
| Authentication | Bot token | Phone/Password |
| Message receive | Long-poll | WebSocket |
| Rate limits | Strict | Relaxed |
| Features | Limited | Full |
| Detection risk | Low | Medium |
| Documentation | Public | Reverse needed |

**Recommendation:** Start with Bot API for quick wins, then enhance with Mobile API features.

## Risk Assessment

| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|------------|
| API changes | Medium | Medium | Version detection |
| SSL Pinning updates | Medium | Medium | Maintain Frida scripts |
| Account restrictions | Low | Medium | Human-like behavior |
| VK policy changes | Low | High | Monitor announcements |

## Success Criteria

1. **Authentication**: Login with phone, maintain session 48h+
2. **Receive**: Get incoming messages within 3 seconds
3. **Send**: Deliver responses within 2 seconds
4. **Stability**: 99.5% uptime over 7 days
5. **Stealth**: No account warnings after 30 days

## Timeline Estimate

| Phase | Duration | Dependencies |
|-------|----------|--------------|
| Phase 1: Recon | 2-4 days | Rooted device ready |
| Phase 2: Auth | 4-6 days | Phase 1 complete |
| Phase 3: Messaging | 5-8 days | Phase 2 complete |
| Phase 4: Anti-detect | 2-4 days | Phase 3 complete |
| Phase 5: Library | 4-6 days | Phase 4 complete |
| **Total** | **17-28 days** | |

**Note:** MAX likely easier than Avito due to:
- Public Bot API as reference
- VK/ICQ heritage (known protocols)
- Less aggressive anti-automation

## Files & Artifacts

| Artifact | Location | Description |
|----------|----------|-------------|
| APK | `./apk/` | Original APK files |
| Decompiled | `./decompiled/` | JADX output |
| Traffic | `./traffic/` | pcap/har captures |
| Frida scripts | `./frida/` | SSL bypass, hooks |
| Bot API tests | `./bot-api/` | Bot API experiments |
| Documentation | `./docs/` | API docs, schemas |
| Library | `./lib/` | Final Python library |

## References

- MAX Bot API: https://max.ru/botapi/
- VK Teams (old docs): https://teams.vk.com/
- ICQ Protocol: https://en.wikipedia.org/wiki/OSCAR_protocol
- Existing work: Check GitHub for icq-bot, vk-teams-bot libraries

## Notes

- Start with Bot API - it's documented and low risk
- Use Bot API findings to understand Mobile API
- MAX/VK Teams has corporate focus - may have stricter monitoring
- Keep Bot API as fallback if Mobile API too risky
