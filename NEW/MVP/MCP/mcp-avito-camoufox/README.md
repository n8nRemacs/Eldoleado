# Avito Camoufox Service

Multi-account Avito with isolated browser fingerprints using Camoufox.

## 💡 Key Value Proposition

**Get full Avito integration WITHOUT paying 6000₽/month to Avito!**

- ✅ Sync all messages and analyze with AI
- ✅ Full conversation context and history
- ✅ Scoring, sentiment analysis, summaries
- ✅ Send messages via Camoufox API (no Avito subscription needed)
- ✅ Support up to 20 Avito accounts
- ✅ **Total cost: 0₽ for Avito integration** (only pay for ELO subscription)

**Premium Mode (6000₽/month to Avito) is optional** — only needed if you want real-time webhooks and official SLA.

## Two Integration Modes

### 🔷 Premium Mode — Official Avito API
**Requires:** Avito Business subscription (6000₽/month paid to Avito)

- Real-time webhook notifications
- Send messages from ELO Android App
- Full automation (AI auto-replies)
- Analytics & statistics
- SLA from Avito
- **For:** Large service centers (>50 Avito dialogs/day)

### 🔶 Basic Mode — Camoufox Sync ⭐ RECOMMENDED
**Requires:** Nothing (100% FREE, no Avito subscription needed)

**What you get for FREE:**
- ✅ CRON sync every 30 minutes
- ✅ Manual sync from Android App
- ✅ Full AI analysis (scoring, summary, sentiment)
- ✅ Complete conversation context and history
- ✅ Send messages via Camoufox API (no Avito subscription!)
- ✅ Support for 20+ Avito accounts
- ✅ **Save 6000₽/month** compared to Avito Business subscription

**For:** Small service centers (<20 Avito dialogs/day) or anyone who wants to save money

## Features

- **Isolated sessions**: Each account = separate Camoufox browser
- **Unique fingerprints**: Each browser has unique fingerprint (saved on disk)
- **Persistent**: Cookies and sessions survive restart
- **Scale**: Support for 20+ accounts (2 IP × 10)
- **Real browser**: No TLS fingerprint issues (QRATOR bypass)
- **Auto-maintenance**: Health checks, auto-reconnect, cookie refresh
- **Android integration**: Push notifications when re-auth needed
- **Dual mode**: Premium (webhook) + Basic (sync)

## Architecture

### Premium Mode Flow
```
┌─────────────────────────────────────────────────────────────┐
│  Avito Official API                                         │
│  (Requires Avito Business: 6000₽/month paid to Avito)       │
│  ├── Webhook → Real-time message push                      │
│  └── REST API → Send messages                              │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│  n8n: ELO_In_Avito (Premium)                                │
│  ├── Webhook receiver                                       │
│  ├── Check mode = 'premium'                                │
│  └── Forward to ELO_Input_Processor                        │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│  ELO Android App                                            │
│  ├── Real-time messages                                     │
│  ├── Send messages from app                                │
│  └── AI auto-replies                                        │
└─────────────────────────────────────────────────────────────┘
```

### Basic Mode Flow
```
┌─────────────────────────────────────────────────────────────┐
│  Operator works in Native Avito App                         │
│  ├── Fast responses                                         │
│  ├── Push notifications                                     │
│  └── Photos, voice messages                                │
└─────────────────────────────────────────────────────────────┘
                          ↓
           (Messages exchanged here)
                          ↓
┌─────────────────────────────────────────────────────────────┐
│  Sync Triggers:                                             │
│  ├── CRON: every 30 minutes                                │
│  ├── Manual: from Android App                              │
│  ├── On Dialog Open: pull fresh messages                   │
│  └── End of Day: full sync (23:00)                         │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│  Avito Camoufox Service :8793                               │
│                                                             │
│  SessionManager                                             │
│  ├── account_1/ (Camoufox + fingerprint_1)                 │
│  ├── account_2/ (Camoufox + fingerprint_2)                 │
│  └── ... up to 20 accounts                                 │
│                                                             │
│  AvitoChannel (per account)                                │
│  ├── Login with credentials                                │
│  ├── Get chats/messages (REST API via browser)             │
│  └── WebSocket (optional, for Premium upgrade)             │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│  n8n: ELO_Avito_Sync                                        │
│  ├── Check mode = 'basic'                                  │
│  ├── Compare with last_sync_timestamp                      │
│  ├── Extract new messages                                  │
│  └── Forward to ELO_Input_Processor                        │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│  ELO AI Processing                                          │
│  ├── Extract facts (symptoms, device model)                │
│  ├── Sentiment analysis (client frustrated?)               │
│  ├── Scoring (repair success probability)                  │
│  └── Summary (brief conversation overview)                 │
└─────────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────┐
│  ELO Android App: Show to Operator                         │
│                                                             │
│  📊 Dialog Card:                                           │
│  ┌─────────────────────────────────────┐                   │
│  │ Client: Ivan (Avito)                │                   │
│  │ Device: iPhone 12                   │                   │
│  │ Problem: Won't turn on              │                   │
│  │                                      │                   │
│  │ 🔴 Scoring: 45% (low)               │                   │
│  │ 😠 Sentiment: Negative              │                   │
│  │                                      │                   │
│  │ 💡 Summary:                         │                   │
│  │ "Client wants urgent repair but     │                   │
│  │  not ready to leave phone"         │                   │
│  │                                      │                   │
│  │ ✅ Suggested:                       │                   │
│  │ • Offer diagnostics for 500₽       │                   │
│  │ • Clarify symptoms (screen dark?)  │                   │
│  └─────────────────────────────────────┘                   │
│                                                             │
│  [Open in Avito App]  [Call]                               │
└─────────────────────────────────────────────────────────────┘
```

## Installation

```bash
# Install Python dependencies
pip install -r requirements.txt

# Download Camoufox Firefox build
camoufox fetch

# Create data directory
mkdir -p /data/avito-camoufox
```

## Usage

### Start server

```bash
python server.py
```

### API Endpoints

#### Account Management

```bash
# Create account (generates unique fingerprint)
POST /account/{account_id}/create

# Login with phone/password
POST /account/{account_id}/login
Body: {"phone": "+7...", "password": "..."}

# Submit SMS code (if required)
POST /account/{account_id}/sms
Body: {"code": "1234"}

# Get account status
GET /account/{account_id}/status

# Close account
DELETE /account/{account_id}

# List all accounts
GET /accounts
```

#### Avito Operations

```bash
# Get profile
GET /account/{account_id}/profile

# Get chats
GET /account/{account_id}/chats

# Get messages
GET /account/{account_id}/messages/{chat_id}

# Send message
POST /account/{account_id}/send
Body: {"chat_id": "...", "text": "Hello!"}
```

#### WebSocket

```bash
# Start real-time listener
POST /account/{account_id}/listen/start

# Stop listener
POST /account/{account_id}/listen/stop

# Get WebSocket status
GET /account/{account_id}/ws/status
```

## Directory Structure

```
/data/avito-camoufox/
├── account_1/
│   ├── fingerprint.json    # Saved fingerprint (reused)
│   ├── profile/            # Firefox profile (cookies)
│   └── state.json          # Session state
├── account_2/
│   └── ...
```

## Flow

### Initial Login
1. **Android** collects phone/password from user
2. **Android** calls `POST /account/{id}/login`
3. **Server** starts Camoufox, enters credentials on Avito
4. **Avito** sends SMS to user's phone
5. If SMS required → returns `{"status": "sms_required"}`
6. **Android** shows SMS input field
7. **User** enters SMS code received on phone
8. **Android** calls `POST /account/{id}/sms`
9. **Server** submits code, completes login
10. **Server** auto-starts WebSocket listener
11. **Server** starts session health monitoring

### Session Maintenance (automatic)
- **Every 5 min**: Health check (API call)
- **WebSocket disconnect**: Auto-reconnect with exponential backoff
- **Cookie near expiry**: Try silent refresh
- **Session dies**: Push webhook to Android

### Re-authentication
1. **Server** detects session died
2. **Server** calls webhook → n8n → Android push
3. **Android** polls `GET /account/{id}/pending-auth`
4. **Android** shows login form to user
5. Flow repeats from step 1

---

## Mode Comparison

| Feature | Premium | Basic (FREE) |
|---------|---------|--------------|
| **Avito subscription cost** | 6000₽/month (to Avito) | 0₽ (FREE) |
| **Incoming messages** | Real-time webhook | Sync every 30 min |
| **Send from ELO** | ✅ Yes (API) | ✅ Yes (via Camoufox) |
| **AI analysis** | ✅ Yes | ✅ Yes |
| **Scoring/Summary** | ✅ Yes | ✅ Yes |
| **Dialog context** | ✅ Real-time | ✅ With 30 min delay |
| **AI auto-replies** | ✅ Yes | ⚠️ Limited (sync delay) |
| **Statistics** | ✅ Full | ✅ Basic |
| **SLA** | ✅ From Avito | ❌ No |
| **Scale** | Unlimited | Up to 20 accounts |

---

## Use Cases

### Premium Mode
- Large service centers (>50 Avito dialogs/day)
- Want full automation
- Need real-time responses
- Ready to pay for convenience
- Example: Chain service center with 5 branches

### Basic Mode
- Small service centers (<20 Avito dialogs/day)
- Don't need automation
- Context is important, not real-time
- Want to save costs
- Example: Single service center, 2-3 operators

---

## Pricing (for ELO customers)

| Plan | ELO Subscription | Avito Subscription (paid to Avito) | Total | You Save vs Avito Business |
|------|------------------|-------------------------------------|-------|----------------------------|
| **Starter** ⭐ | 3000₽/month | 0₽ (Basic mode) | **3000₽/month** | **6000₽/month** |
| **Professional** | 5000₽/month | 6000₽/month (Premium, optional) | 11000₽/month | 0₽ (if Premium chosen) |
| **Enterprise** | 10000₽/month | 6000₽/month (Premium, optional) | 16000₽/month | 0₽ (if Premium chosen) |

### 💰 Cost Comparison

**Without ELO (using only Avito Business):**
- Avito Business: 6000₽/month
- Total features: Basic messenger access only
- AI analysis: ❌ No
- Context preservation: ❌ No
- Multi-channel: ❌ No

**With ELO Basic Mode:**
- ELO Starter: 3000₽/month
- Avito subscription: 0₽ (not needed!)
- **Total: 3000₽/month** (save 3000₽/month vs Avito Business alone!)
- AI analysis: ✅ Yes
- Context preservation: ✅ Yes
- Multi-channel: ✅ Yes (WhatsApp, Telegram, VK, MAX, etc.)

**Note:**
- Avito Business subscription (6000₽/month) is paid **directly to Avito**, not to ELO
- Basic mode is 100% FREE (no Avito subscription required)
- You can start with Basic mode and upgrade to Premium anytime
- **Most customers don't need Premium** — Basic mode is sufficient for 90% of use cases

---

## Configuration

### Database: elo_t_channel_accounts

Add `mode` field:

```sql
ALTER TABLE elo_t_channel_accounts
ADD COLUMN mode TEXT DEFAULT 'basic';  -- 'premium' | 'basic'

-- Constraint
ALTER TABLE elo_t_channel_accounts
ADD CONSTRAINT check_avito_mode
CHECK (
  channel_id != (SELECT id FROM elo_channels WHERE code = 'avito')
  OR mode IN ('premium', 'basic')
);
```

### Switching Modes

```bash
# Upgrade to Premium
UPDATE elo_t_channel_accounts
SET mode = 'premium'
WHERE id = 'account-id';

# Downgrade to Basic
UPDATE elo_t_channel_accounts
SET mode = 'basic'
WHERE id = 'account-id';
```
