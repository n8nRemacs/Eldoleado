# Avito Messenger Reverse Engineering Specification

## Project Goal

Create a Baileys-like library for Avito Messenger that enables:
- Receiving incoming messages in real-time
- Sending text messages in response
- Managing multiple accounts (multi-tenant)

**NOT included:** Voice/video calls, stories, ads posting, payments.

## Target Use Case

```
User writes to Avito ad → Our service receives message → Operator responds → Message delivered to user
```

100% legitimate business communication. No spam, no automation of outbound messages.

## Phase 1: Reconnaissance

### 1.1 APK Analysis
- [ ] Download latest Avito APK from official source
- [ ] Decompile with JADX: `jadx -d ./decompiled avito.apk`
- [ ] Analyze with apktool: `apktool d avito.apk -o ./unpacked`
- [ ] Document package structure

### 1.2 Network Traffic Analysis
- [ ] Setup mitmproxy/Charles on rooted device
- [ ] Install Avito with SSL pinning bypass (Frida)
- [ ] Capture authentication flow
- [ ] Capture message send/receive flow
- [ ] Document all API endpoints

### 1.3 Key Questions to Answer
- [ ] What protocol for real-time messaging? (WebSocket / XMPP / gRPC / Long-polling)
- [ ] How authentication works? (OAuth / JWT / Session tokens)
- [ ] How SSL pinning is implemented?
- [ ] Is there certificate transparency?
- [ ] What protobuf/JSON schemas are used?

## Phase 2: Authentication Flow

### 2.1 Login Methods to Analyze
- [ ] Phone number + SMS code
- [ ] Email + password (if available)
- [ ] Social login (VK, Google, Apple)

### 2.2 Session Management
- [ ] Token format and lifetime
- [ ] Refresh token mechanism
- [ ] Device fingerprinting
- [ ] Multi-device support

### 2.3 Expected Deliverables
```python
class AvitoAuth:
    async def login_phone(phone: str) -> AuthSession
    async def verify_sms(session: AuthSession, code: str) -> Credentials
    async def refresh_token(credentials: Credentials) -> Credentials
    async def logout(credentials: Credentials) -> bool
```

## Phase 3: Messaging Protocol

### 3.1 Message Types to Support
- [ ] Text messages
- [ ] Images (receive only initially)
- [ ] Read receipts
- [ ] Typing indicators (optional)

### 3.2 Real-time Connection
- [ ] Identify connection protocol
- [ ] Document handshake sequence
- [ ] Map message event types
- [ ] Handle reconnection logic

### 3.3 Expected Deliverables
```python
class AvitoMessenger:
    async def connect(credentials: Credentials) -> Connection
    async def on_message(callback: Callable[[Message], None])
    async def send_message(chat_id: str, text: str) -> MessageResult
    async def get_conversations() -> List[Conversation]
    async def get_messages(chat_id: str, limit: int) -> List[Message]
```

## Phase 4: Anti-Detection

### 4.1 Behavioral Analysis
- [ ] Normal user activity patterns
- [ ] Rate limits per action type
- [ ] Cooldown periods
- [ ] Account age requirements

### 4.2 Device Fingerprinting
- [ ] Device ID generation algorithm
- [ ] Required device parameters
- [ ] Android ID / Advertising ID usage

### 4.3 Mitigation Strategies
- [ ] Human-like delays between actions
- [ ] Proper User-Agent rotation
- [ ] Session persistence
- [ ] IP reputation management

## Phase 5: Library Architecture

### 5.1 Core Components
```
avito-lib/
├── auth/
│   ├── login.py
│   ├── session.py
│   └── tokens.py
├── messaging/
│   ├── connection.py
│   ├── handlers.py
│   └── types.py
├── api/
│   ├── client.py
│   ├── endpoints.py
│   └── models.py
├── crypto/
│   ├── ssl_bypass.py
│   └── signatures.py
└── utils/
    ├── fingerprint.py
    └── delays.py
```

### 5.2 Integration Interface
```python
from avito_lib import AvitoClient

client = AvitoClient()
await client.login(phone="+79001234567")
await client.verify_code("123456")

@client.on_message
async def handle(msg: Message):
    if msg.text == "Здравствуйте":
        await client.send(msg.chat_id, "Добрый день! Чем могу помочь?")

await client.connect()
```

## Risk Assessment

| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|------------|
| SSL Pinning updates | High | Medium | Monitor app updates, maintain bypass |
| API changes | Medium | High | Version detection, graceful degradation |
| Account bans | Low | Medium | Human-like behavior, no spam |
| Legal issues | Low | Low | Only legitimate business use |

## Success Criteria

1. **Authentication**: Login with phone, maintain session 24h+
2. **Receive**: Get incoming messages within 5 seconds
3. **Send**: Deliver responses within 3 seconds
4. **Stability**: 99% uptime over 7 days
5. **Stealth**: No account warnings after 30 days normal use

## Timeline Estimate

| Phase | Duration | Dependencies |
|-------|----------|--------------|
| Phase 1: Recon | 3-5 days | Rooted device ready |
| Phase 2: Auth | 5-7 days | Phase 1 complete |
| Phase 3: Messaging | 7-10 days | Phase 2 complete |
| Phase 4: Anti-detect | 3-5 days | Phase 3 complete |
| Phase 5: Library | 5-7 days | Phase 4 complete |
| **Total** | **23-34 days** | |

## Files & Artifacts

| Artifact | Location | Description |
|----------|----------|-------------|
| APK | `./apk/` | Original APK files |
| Decompiled | `./decompiled/` | JADX output |
| Traffic | `./traffic/` | pcap/har captures |
| Frida scripts | `./frida/` | SSL bypass, hooks |
| Documentation | `./docs/` | API docs, schemas |
| Library | `./lib/` | Final Python library |

## Notes

- Start with traffic analysis before deep code analysis
- Focus on messaging, ignore ads/payments/other features
- Document everything - protocols change frequently
- Keep backup of working APK versions
