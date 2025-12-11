# Client Contour — Overview

> Client identity resolution and dialog management

---

## Purpose

Client Contour is responsible for **unified client** — one client across all channels.

**MVP Scope:**
- Client Resolver — find/create client
- Dialog Resolver — find/create dialog

**Future Scope (not MVP):**
- Channel Merger — merge clients across channels
- Touchpoint Analytics — track client journey

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  CLIENT CONTOUR                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐    │
│  │  Client Resolver │ ──▶ │  Dialog Resolver │ ──▶ │  (Future:        │    │
│  │                  │     │                  │     │   Channel Merger)│    │
│  └──────────────────┘     └──────────────────┘     └──────────────────┘    │
│         │                        │                                          │
│         ▼                        ▼                                          │
│    +client_id               +dialog_id                                      │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Input Contract

**From Input Contour:**
```json
{
  "tenant_id": "uuid",
  "domain_id": 1,
  "channel_id": 1,
  "external_chat_id": "123456",
  "external_user_id": "123456",
  "client_name": "Ivan",
  "client_phone": "+79991234567",
  "text": "Merged message text",
  "timestamp": "...",
  "meta": {...}
}
```

---

## Output Contract

**To Core Contour:**
```json
{
  "tenant_id": "uuid",
  "domain_id": 1,
  "client_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "text": "Merged message text",
  "timestamp": "...",
  "meta": {
    "external_chat_id": "123456",
    "is_new_client": false,
    "is_new_dialog": false
  }
}
```

**Note:** No `vertical_id` — determined in Core.

---

## Components

| # | Component | Purpose | MVP |
|---|-----------|---------|-----|
| 1 | Client Resolver | Find/create client by external_id or phone | Yes |
| 2 | Dialog Resolver | Find/create active dialog | Yes |
| 3 | Channel Merger | Merge clients across channels | No |
| 4 | Touchpoint Register | Track client journey | No |

---

## 1. Client Resolver

### Logic

```
1. Search by channel + external_id
   └── Found? → return client_id

2. Search by phone (if provided)
   └── Found? → link channel to existing client → return client_id

3. Create new client
   └── Create client record
   └── Create client_channel record
   └── Sync to Neo4j (Graph Query Tool)
   └── return client_id
```

### SQL: Find by external_id

```sql
SELECT c.id as client_id, c.name, c.phone
FROM clients c
JOIN client_channels cc ON cc.client_id = c.id
WHERE cc.channel_id = $channel_id
  AND cc.external_id = $external_chat_id
  AND c.tenant_id = $tenant_id
LIMIT 1;
```

### SQL: Find by phone

```sql
SELECT c.id as client_id, c.name, c.phone
FROM clients c
WHERE c.tenant_id = $tenant_id
  AND c.phone = $phone
LIMIT 1;
```

### SQL: Create client

```sql
-- 1. Create client
INSERT INTO clients (tenant_id, name, phone)
VALUES ($tenant_id, $name, $phone)
RETURNING id as client_id;

-- 2. Link to channel
INSERT INTO client_channels (client_id, channel_id, external_id, external_username)
VALUES ($client_id, $channel_id, $external_chat_id, $username);
```

### Neo4j Sync

After creating client, call Graph Query Tool:

```json
{
  "query_code": "create_client",
  "params": {
    "client_id": "uuid",
    "tenant_id": "uuid",
    "name": "Ivan",
    "phone": "+79991234567"
  }
}
```

---

## 2. Dialog Resolver

### Logic

```
1. Search active dialog for client + channel
   └── Found? → return dialog_id

2. Create new dialog
   └── Create dialog record (vertical_id = NULL)
   └── return dialog_id
```

### SQL: Find active dialog

```sql
SELECT id as dialog_id, vertical_id, status_id
FROM dialogs
WHERE client_id = $client_id
  AND channel_id = $channel_id
  AND status_id IN (1, 2)  -- active, waiting
ORDER BY updated_at DESC
LIMIT 1;
```

### SQL: Create dialog

```sql
INSERT INTO dialogs (tenant_id, client_id, channel_id, status_id)
VALUES ($tenant_id, $client_id, $channel_id, 1)  -- status: active
RETURNING id as dialog_id;

-- Note: vertical_id is NULL, will be set by Core
```

---

## 3. Channel Merger (Future)

**Not in MVP. Design notes for future:**

### Merge Scenarios

| Scenario | Trigger | Action |
|----------|---------|--------|
| Phone match | WhatsApp phone = Telegram phone | Auto-merge |
| Client statement | "I wrote you in Telegram" | AI suggests merge |
| Operator action | Sees duplicates in UI | Manual merge |

### Merge Logic

```
1. Identify merge candidates
2. Choose primary client (older, more data)
3. Move all client_channels to primary
4. Move all dialogs to primary
5. Merge Neo4j nodes
6. Delete secondary client
```

### Tables affected

- `clients` — delete secondary
- `client_channels` — update client_id
- `dialogs` — update client_id
- Neo4j — MERGE nodes

---

## 4. Touchpoint Register (Future)

**Not in MVP. Design notes for future:**

Every client interaction = touchpoint.

```sql
CREATE TABLE touchpoints (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID NOT NULL REFERENCES clients(id),
    channel_id INT NOT NULL REFERENCES channels(id),
    dialog_id UUID REFERENCES dialogs(id),
    direction_id INT NOT NULL REFERENCES directions(id),  -- in/out
    touchpoint_type VARCHAR(20) NOT NULL,  -- message, call, visit
    created_at TIMESTAMPTZ DEFAULT NOW()
);
```

**Use cases:**
- Client journey analytics
- Channel effectiveness
- Response time metrics

---

## Why Separate Contour?

| Reason | Explanation |
|--------|-------------|
| **Core concept** | Single client across channels is fundamental |
| **Complex logic** | Merging is non-trivial, deserves isolation |
| **Independent evolution** | Can add merge logic without touching Core |
| **Clear boundary** | Input: tenant + external_id, Output: client_id + dialog_id |

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Table | `clients` | Client storage |
| Table | `client_channels` | Channel links |
| Table | `dialogs` | Dialog storage |
| Tool | Graph Query | Neo4j sync |

---

## Data Flow

```
Input Contour
     │
     │  tenant_id, domain_id, channel_id, external_chat_id
     ▼
┌─────────────────┐
│ Client Resolver │
└────────┬────────┘
         │  +client_id
         ▼
┌─────────────────┐
│ Dialog Resolver │
└────────┬────────┘
         │  +dialog_id
         ▼
   Core Contour
```

---

**Document:** CLIENT_CONTOUR_OVERVIEW.md
**Date:** 2025-12-11
**Author:** Dmitry + Claude
**Status:** MVP scope defined, future scope outlined
