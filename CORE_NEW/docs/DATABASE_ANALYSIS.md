# Database Analysis

**Last sync:** 2025-12-24

---

## Summary

| Category | Count |
|----------|-------|
| Transactional tables (elo_t_*) | 13 |
| Reference tables (elo_*) | 12 |
| Views (elo_v_*) | 5 |
| **Total** | 30 |

---

## Reference Tables

### elo_channels
| id | name |
|----|------|
| 1 | Telegram |
| 2 | WhatsApp |
| 3 | Avito |
| 4 | VKontakte |
| 5 | MAX (VK Teams) |
| 6 | Web Form |
| 7 | Phone Call |

### elo_ip_nodes
| id | server_name | ip_address | status |
|----|-------------|------------|--------|
| 1 | MessagerOne | 155.212.221.189 | active |
| 2 | MessagerOne | 217.114.14.17 | active |

---

## Core Transactional Tables

### elo_t_tenants
Main tenant (business account) table.

| Column | Type | Description |
|--------|------|-------------|
| id | uuid | Primary key |
| name | varchar | Tenant name |
| settings | jsonb | Tenant settings |

### elo_t_clients
Client (contact) table.

| Column | Type | Description |
|--------|------|-------------|
| id | uuid | Primary key |
| tenant_id | uuid | FK to tenants |
| name | varchar | Client name (from sender_name) |
| phone | varchar | Phone number |
| email | varchar | Email |
| profile | jsonb | Additional profile data |

### elo_t_client_channels
Links clients to channels with external IDs.

| Column | Type | Description |
|--------|------|-------------|
| id | serial | Primary key |
| client_id | uuid | FK to clients |
| channel_id | int | FK to elo_channels |
| external_id | varchar | External ID (sender_id for Avito) |
| external_username | varchar | Username if available |

**Important:** This is how we link `sender_id` to a client!

### elo_t_dialogs
Conversation/dialog table.

| Column | Type | Description |
|--------|------|-------------|
| id | uuid | Primary key |
| tenant_id | uuid | FK to tenants |
| client_id | uuid | FK to clients |
| channel_id | int | FK to elo_channels |
| external_chat_id | varchar | External chat ID |
| status_id | int | Dialog status |
| context | jsonb | Dialog context |

### elo_t_messages
Message table.

| Column | Type | Description |
|--------|------|-------------|
| id | uuid | Primary key |
| tenant_id | uuid | FK to tenants |
| dialog_id | uuid | FK to dialogs |
| direction_id | int | 1=incoming, 2=outgoing |
| actor_type | varchar | 'client', 'operator', 'ai' |
| content | text | Message text |
| external_message_id | varchar | External message ID |

### elo_t_channel_accounts
Channel account credentials.

| Column | Type | Description |
|--------|------|-------------|
| id | uuid | Primary key |
| tenant_id | uuid | FK to tenants |
| channel_id | int | FK to elo_channels |
| account_id | varchar | Account identifier |
| credentials | jsonb | Credentials (sessid, tokens) |
| session_status | varchar | 'active', 'pending', 'expired' |
| ip_node_id | int | FK to elo_ip_nodes |

---

## Data Flow

```
Incoming Message (Avito WebSocket)
         │
         ▼
┌─────────────────────────────────────────────────────────────┐
│                    ELO_In_Avito_User                         │
│  sender_id → external_id                                     │
│  sender_name → client_name                                   │
└─────────────────────────────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────────────────────────┐
│                    ELO_Client_Resolve                        │
│  1. Cache lookup: cache:client:{channel}:{external_id}       │
│  2. DB lookup: elo_t_client_channels WHERE external_id = ?   │
│  3. If not found → CREATE elo_t_clients + elo_t_client_channels│
└─────────────────────────────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────────────────────────┐
│                    elo_t_clients                             │
│  id: uuid, name: sender_name, tenant_id: tenant_id           │
└─────────────────────────────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────────────────────────┐
│                    elo_t_client_channels                     │
│  client_id → elo_t_clients.id                                │
│  external_id = sender_id                                     │
│  channel_id = 3 (Avito)                                      │
└─────────────────────────────────────────────────────────────┘
```

---

## Current Issues

### 1. Normalize Message: client_name not passed
**Location:** ELO_In_Avito_User → Normalize Message
**Problem:** `client_name: rawMsg.userName` - but Android sends `body.sender_name`
**Fix:** `client_name: body.sender_name || rawMsg.userName || null`

### 2. DB Create Client uses external_chat_id instead of external_user_id
**Location:** ELO_Client_Resolve → DB Create Client
**Current:**
```sql
INSERT INTO elo_t_client_channels (client_id, channel_id, external_id)
SELECT client_id, channel_id, 'external_chat_id'
```
**Should be:** `external_user_id` (sender_id)

---

## Key Relationships

```
elo_t_tenants
    │
    ├──< elo_t_clients (tenant_id)
    │       │
    │       └──< elo_t_client_channels (client_id)
    │               │
    │               └──> elo_channels (channel_id)
    │
    ├──< elo_t_dialogs (tenant_id, client_id)
    │       │
    │       └──< elo_t_messages (dialog_id)
    │
    └──< elo_t_channel_accounts (tenant_id)
            │
            └──> elo_ip_nodes (ip_node_id)
```

---

*Generated by Claude Code*
