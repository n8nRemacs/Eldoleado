# ELO Global Data Schema

> Version: 1.0
> Date: 2025-12-10

---

## Principles

1. **All tables are relational** — no hardcoding, only FK to directories
2. **Hybrid IDs** — INT for directories, UUID for entities
3. **Minimal package** — tenant_id + dialog_id for passing between blocks

---

## Data Contracts

### Internal (between n8n blocks)
```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid"
}
```
Minimum. Everything else — query from DB. `domain_id` is retrieved from `tenant_id`.

### External (API → Android App)
```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "client": {
    "id": "uuid",
    "name": "Ivan",
    "phone": "+7..."
  },
  "channel": "telegram",
  "status": "active",
  "last_message": "...",
  "updated_at": "..."
}
```
Expanded data for display.

---

## Hierarchy: Domain → Vertical

```
Domain (domain)
  └── Vertical (vertical)

Example:
Electronics (domain)
  ├── phone_repair (vertical)
  ├── buy_sell (vertical)
  └── accessories (vertical)
```

**Rule:** dialog can switch between verticals within one domain. Switching to another domain — escalation or new dialog.

**MVP:** one domain per tenant.

---

## Directories (INT PRIMARY KEY)

### elo_domains
Top-level business domains.
```sql
CREATE TABLE elo_domains (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,   -- electronics, food, auto
    name VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT true
);
```

### elo_verticals
Verticals within a domain.
```sql
CREATE TABLE elo_verticals (
    id SERIAL PRIMARY KEY,
    domain_id INT NOT NULL REFERENCES elo_domains(id),
    code VARCHAR(50) UNIQUE NOT NULL,   -- phone_repair, buy_sell
    name VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT true
);
```

### elo_channels
Communication channels.
```sql
CREATE TABLE elo_channels (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,   -- telegram, whatsapp, vk, avito, max, form, phone
    name VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT true
);
```

### elo_dialog_statuses
Dialog statuses.
```sql
CREATE TABLE elo_dialog_statuses (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,   -- active, waiting, closed, escalated
    name VARCHAR(50) NOT NULL
);
```

### elo_message_types
Message types.
```sql
CREATE TABLE elo_message_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,   -- text, voice, image, file, location
    name VARCHAR(50) NOT NULL
);
```

### elo_directions
Message direction.
```sql
CREATE TABLE elo_directions (
    id SERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,   -- in, out
    name VARCHAR(20) NOT NULL
);
```

### elo_operator_types
Operator types.
```sql
CREATE TABLE elo_operator_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,   -- human, ai
    name VARCHAR(50) NOT NULL
);
```

---

## Main Entities (UUID PRIMARY KEY)

### elo_tenants
System tenants.
```sql
CREATE TABLE elo_tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    domain_id INT NOT NULL REFERENCES elo_domains(id),
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);
```

### elo_operators
Operators (humans and AI).
```sql
CREATE TABLE elo_operators (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),
    type_id INT NOT NULL REFERENCES elo_operator_types(id),
    name VARCHAR(255) NOT NULL,           -- "Ivan Petrov" or "AI Assistant"
    ai_model VARCHAR(50),                 -- NULL for human, 'claude-3' for ai
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);
```

### elo_clients
Clients.
```sql
CREATE TABLE elo_clients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),
    name VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);
```

### elo_dialogs
Client dialogs.
```sql
CREATE TABLE elo_dialogs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),
    client_id UUID NOT NULL REFERENCES elo_clients(id),
    channel_id INT NOT NULL REFERENCES elo_channels(id),
    status_id INT NOT NULL REFERENCES elo_dialog_statuses(id),
    operator_id UUID REFERENCES elo_operators(id),   -- current responsible
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);
```

### elo_messages
Dialog messages.
```sql
CREATE TABLE elo_messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    dialog_id UUID NOT NULL REFERENCES elo_dialogs(id),
    direction_id INT NOT NULL REFERENCES elo_directions(id),
    operator_id UUID REFERENCES elo_operators(id),   -- NULL for incoming, filled for outgoing
    message_type_id INT NOT NULL REFERENCES elo_message_types(id),
    content TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

## Junction Tables

### elo_tenant_verticals
Which verticals are available to tenant.
```sql
CREATE TABLE elo_tenant_verticals (
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),
    vertical_id INT NOT NULL REFERENCES elo_verticals(id),
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (tenant_id, vertical_id)
);
```

### elo_dialog_verticals
Which verticals are active in dialog.
```sql
CREATE TABLE elo_dialog_verticals (
    dialog_id UUID NOT NULL REFERENCES elo_dialogs(id),
    vertical_id INT NOT NULL REFERENCES elo_verticals(id),
    status_id INT NOT NULL REFERENCES elo_dialog_statuses(id),   -- status for this vertical
    operator_id UUID REFERENCES elo_operators(id),               -- responsible for this vertical
    created_at TIMESTAMPTZ DEFAULT NOW(),
    PRIMARY KEY (dialog_id, vertical_id)
);
```

---

## System Blocks

| # | Block | Description |
|---|-------|-------------|
| 1 | Channel Layer | MCP + In + Out (per channel) |
| 2 | Billing | Subscriptions, balances, checks |
| 3 | Input Contour | Tenant Resolver → Batcher |
| 4 | Core | Dialog Engine + AI Pipeline |
| 5 | Graph | Neo4j (Client, Device, Symptom, Diagnosis, Repair) |
| 6 | Diagnostic Engine | Symptoms → Diagnoses → Repairs |

+ **Tools** — separately later.

---

## Block 1: Channel Layer (IN/OUT)

### What ELO_In receives from MCP (NormalizedMessage)

MCP already does normalization and voice transcription.

```json
{
  "channel": "telegram",
  "bot_token": "123:ABC...",
  "message_id": 111,
  "chat_id": 789,
  "user_id": 456,
  "user_name": "ivan",
  "first_name": "Ivan",
  "last_name": "Petrov",
  "username": "ivan",
  "text": "Hi, my phone is broken",
  "voice_text": "transcription if voice",
  "attachments": [
    {"type": "photo", "file_id": "..."},
    {"type": "voice", "file_id": "...", "duration": 5}
  ],
  "timestamp": "2025-12-10T12:00:00Z",
  "is_callback": false,
  "callback_data": null,
  "raw_data": {}
}
```

### What ELO_In does

1. By `bot_token` → find `tenant_id` (Tenant Resolver, table `elo_channel_accounts`)
2. By `chat_id` + `channel` → find or create `client_id`
3. By `client_id` + `channel` → find or create `dialog_id`
4. Save message in `elo_messages`
5. Pass further: `{tenant_id, dialog_id}`

### What ELO_Out receives

```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "message": {
    "text": "Response to client",
    "operator_id": "uuid",
    "reply_markup": {}
  }
}
```

### What ELO_Out does

1. From `dialog_id` get `channel_id`, external `chat_id`
2. From `elo_channel_accounts` get `bot_token` / credentials
3. Call MCP `/api/send` with needed parameters
4. Save message in `elo_messages` (direction = out)

### Table elo_channel_accounts

Tenant ↔ channel ↔ account link for Tenant Resolver and sending.

```sql
CREATE TABLE elo_channel_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),
    channel_id INT NOT NULL REFERENCES elo_channels(id),

    -- Identification
    account_id VARCHAR(100) NOT NULL,    -- bot_token, phone, group_id
    account_name VARCHAR(255),           -- display name
    webhook_hash VARCHAR(32) UNIQUE,     -- for Tenant Resolver: /webhook/{hash} → tenant_id

    -- Credentials (for sending)
    credentials JSONB,                   -- {token, api_key, secret...}

    -- MCP endpoint
    mcp_base_url VARCHAR(255),           -- https://mcp-telegram.example.com

    -- Status
    is_active BOOLEAN DEFAULT true,

    -- Audit
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, channel_id, account_id)
);

-- Index for fast lookup by bot_token/account_id
CREATE INDEX idx_channel_accounts_account ON elo_channel_accounts(account_id);
CREATE INDEX idx_channel_accounts_webhook ON elo_channel_accounts(webhook_hash);
```

### Table elo_client_channels

Client link with their identifiers in different channels.

```sql
CREATE TABLE elo_client_channels (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID NOT NULL REFERENCES elo_clients(id),
    channel_id INT NOT NULL REFERENCES elo_channels(id),

    external_id VARCHAR(100) NOT NULL,   -- chat_id in Telegram, phone in WhatsApp
    external_username VARCHAR(100),      -- @username if available

    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(client_id, channel_id, external_id)
);

-- Index for fast client lookup by external_id
CREATE INDEX idx_client_channels_external ON elo_client_channels(channel_id, external_id);
```

---

## Block 3: Input Contour

**Flow:**
```
MCP → ELO_In → [Input Contour] → Core
                    ↓
         Tenant Resolver → Batcher → Client/Dialog Resolver
```

### 3.1 Tenant Resolver

**Receives from ELO_In:**
```json
{
  "channel": "telegram",
  "bot_token": "123:ABC...",
  "chat_id": 789,
  "user_id": 456,
  "text": "...",
  ...
}
```

**Does:**
1. By `bot_token` → SELECT from `elo_channel_accounts` → `tenant_id`, `account_id`
2. Check `is_active` for tenant and channel_account
3. If not found → log and discard

**Returns:**
```json
{
  "tenant_id": "uuid",
  "channel_id": 1,
  "account_id": "uuid",
  "external_user_id": "456",
  "external_chat_id": "789",
  "text": "...",
  "voice_text": "...",
  "attachments": [...],
  "timestamp": "..."
}
```

### 3.2 Batcher (Debounce)

**Why:** client sends 5 messages in 3 seconds — process as one.

**Architecture:** high-load component, parallel processing via Redis.

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│ ELO_In_TG   │────▶│             │     │             │
├─────────────┤     │             │     │             │
│ ELO_In_WA   │────▶│    Redis    │────▶│  Batcher    │
├─────────────┤     │   Queue     │     │  Workers    │
│ ELO_In_VK   │────▶│             │     │  (N copies) │
└─────────────┘     └─────────────┘     └─────────────┘
```

**Redis structures:**
```
# Incoming queue
elo:in:queue                    -- LIST, incoming messages

# Batches by key (tenant + external_chat_id + channel)
elo:batch:{key}:messages        -- LIST, messages in batch
elo:batch:{key}:timer           -- STRING, timestamp of last message

# Debounce settings (from tenant settings)
elo:tenant:{id}:debounce_ms     -- STRING, wait time (default 3000)
```

**Logic:**
1. Message arrives in `elo:in:queue`
2. Worker takes it, calculates `batch_key = {tenant_id}:{channel_id}:{external_chat_id}`
3. RPUSH to `elo:batch:{key}:messages`
4. SET `elo:batch:{key}:timer` = NOW
5. Separate process checks timers, if N ms passed — sends batch further

**Returns:**
```json
{
  "tenant_id": "uuid",
  "channel_id": 1,
  "account_id": "uuid",
  "external_user_id": "456",
  "external_chat_id": "789",
  "batch": {
    "message_count": 3,
    "combined_text": "Hi\nMy phone is broken\nScreen is cracked",
    "attachments": [...],
    "first_timestamp": "...",
    "last_timestamp": "..."
  }
}
```

### 3.3 Client/Dialog Resolver

**Receives:** batch with `tenant_id`, `channel_id`, `external_chat_id`

**Does:**
1. By `channel_id` + `external_chat_id` → search in `elo_client_channels` → `client_id`
2. If not found → create `client` and `client_channel`
3. By `client_id` + `channel_id` → search active `dialog` or create new
4. Save messages in `elo_messages`

**Returns to Core:**
```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid"
}
```

Minimal Internal contract. Core fetches what it needs itself.

### Principle of data passing between nodes

**n8n problem:** after SQL node the incoming payload is lost.

**Solution:** Code node after each SQL for merging data.

```javascript
// Example: after SQL "Tenant Resolver"
const input = $('Webhook').first().json;  // original data
const sqlResult = $input.first().json;    // SQL result

return {
  ...input,                    // pass through everything
  tenant_id: sqlResult.tenant_id,
  channel_id: sqlResult.channel_id,
  account_id: sqlResult.id
};
```

**Principle:** one growing object, each worker adds its fields.

### Input Contour Workers (preliminary)

| # | Worker | Input | SQL | Adds |
|---|--------|-------|-----|------|
| 1 | Tenant Resolver | MCP payload | 1 (channel_accounts) | tenant_id, channel_id, account_id |
| 2 | Batcher | + tenant_id | 0 (Redis) | batch{} |
| 3 | Client Resolver | + batch | 1-2 (client, client_channels) | client_id |
| 4 | Dialog Resolver | + client_id | 1-2 (dialog) | dialog_id |
| 5 | Message Saver | + dialog_id | 1 INSERT (messages) | message_id |

**TODO:** decide — many small workers or combine 3+4+5.

---

## Seed Data

### elo_domains
| id | code | name |
|----|------|------|
| 1 | electronics | Electronics |

### elo_verticals
| id | domain_id | code | name |
|----|-----------|------|------|
| 1 | 1 | phone_repair | Phone Repair |
| 2 | 1 | buy_sell | Buy/Sell |

### elo_channels
| id | code | name |
|----|------|------|
| 1 | telegram | Telegram |
| 2 | whatsapp | WhatsApp |
| 3 | vk | VKontakte |
| 4 | avito | Avito |
| 5 | max | MAX |
| 6 | form | Form |
| 7 | phone | Phone |

### elo_dialog_statuses
| id | code | name |
|----|------|------|
| 1 | active | Active |
| 2 | waiting | Waiting |
| 3 | closed | Closed |
| 4 | escalated | Escalated |

### elo_message_types
| id | code | name |
|----|------|------|
| 1 | text | Text |
| 2 | voice | Voice |
| 3 | image | Image |
| 4 | file | File |
| 5 | location | Location |

### elo_directions
| id | code | name |
|----|------|------|
| 1 | in | Incoming |
| 2 | out | Outgoing |

### elo_operator_types
| id | code | name |
|----|------|------|
| 1 | human | Human |
| 2 | ai | AI |
