# Input Contour — Overview

> Input contour: buffering and preparing messages before Core

---

## Purpose

Input Contour solves:
- **IN workflows are fast** (~100ms) — need quick reply to messenger
- **Batcher is slow** (10s+ debounce) — waits for client to finish typing
- **Redis as buffer** — decouples speeds

---

## Architecture

```
┌──────────────────────────────────────────────────────────────────┐
│  IN Workflows (fast, ~100ms)                                      │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐     │
│  │Telegram │ │WhatsApp │ │  Avito  │ │   VK    │ │   MAX   │     │
│  └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘     │
│       │           │           │           │           │          │
│       └───────────┴─────┬─────┴───────────┴───────────┘          │
│                         ↓                                         │
│         ┌───────────────────────────────┐                        │
│         │  1. Tenant Resolver           │  ← tenant_id, domain_id│
│         └───────────────┬───────────────┘                        │
│                         ↓                                         │
│         ┌───────────────────────────────┐                        │
│         │  Redis RPUSH queue:incoming   │  ← fast exit           │
│         └───────────────┬───────────────┘                        │
└─────────────────────────│────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────────────┐
│  Batcher (slow, 10s debounce)                                       │
│         ┌───────────────────────────────┐                           │
│         │  2. Queue Processor           │  ← every 5s              │
│         │     POP from queue:incoming   │                           │
│         │     Group by tenant + chat_id │                           │
│         └───────────────┬───────────────┘                           │
│                         ↓                                            │
│         ┌───────────────────────────────┐                           │
│         │  3. Batch Debouncer ×10       │  ← waits 10s silence      │
│         │     Merges messages           │                           │
│         └───────────────┬───────────────┘                           │
│                         ↓                                            │
│         ┌───────────────────────────────┐                           │
│         │  4. Client Resolver           │  ← find/create client     │
│         └───────────────┬───────────────┘                           │
│                         ↓                                            │
│         ┌───────────────────────────────┐                           │
│         │  5. Dialog Resolver           │  ← find/create dialog     │
│         └───────────────┬───────────────┘                           │
└─────────────────────────│───────────────────────────────────────────┘
                          ↓
                    ┌───────────┐
                    │   CORE    │  ← vertical_id определяется здесь
                    └───────────┘
```

---

## Multi-Vertical / Multi-Domain Support

### What Input Contour determines:

| Step | What | How | Table |
|------|------|-----|-------|
| **Tenant Resolver** | `tenant_id` | By channel credentials (bot_token, etc.) | `channel_accounts` |
| **Tenant Resolver** | `domain_id` | From tenant record | `tenants.domain_id` |
| **Client Resolver** | `client_id` | By tenant + external_id | `clients`, `client_channels` |
| **Dialog Resolver** | `dialog_id` | By client + channel, active dialog | `dialogs` |

### What Input Contour does NOT determine:

| Field | Why | Where determined |
|-------|-----|------------------|
| `vertical_id` | Unknown at input stage | **Core** (by dialog context, AI, or rules) |

**Why vertical_id is not in Input Contour:**
- New client → vertical unknown yet
- Existing client → may have multiple verticals
- Vertical determined by dialog context (what client is asking about)

---

## Components

| # | Workflow | Purpose | Output |
|---|----------|---------|--------|
| 1 | Tenant Resolver | Determines tenant by credentials | +tenant_id, +domain_id |
| 2 | Queue Processor | Takes from queue, groups by tenant+chat | batches |
| 3 | Batch Debouncer | Waits for silence, merges messages | merged text |
| 4 | Client Resolver | Finds/creates client | +client_id |
| 5 | Dialog Resolver | Finds/creates dialog | +dialog_id |

---

## Tenant Resolver (updated for new schema)

**SQL Query:**
```sql
SELECT
  t.id as tenant_id,
  t.domain_id,                    -- ← for multi-domain
  t.name as tenant_name,
  ca.id as channel_account_id
FROM tenants t
JOIN channel_accounts ca ON ca.tenant_id = t.id
JOIN channels c ON c.id = ca.channel_id
WHERE ca.account_id = $credential    -- bot_token, phone_id, etc.
  AND c.code = $channel              -- 'telegram', 'whatsapp', etc.
  AND ca.is_active = true
  AND t.is_active = true
LIMIT 1;
```

**Tables used:**
- `tenants` — tenant with domain_id
- `channel_accounts` — tenant ↔ channel ↔ credentials
- `channels` — channel directory

---

## Client Resolver

**Logic:**
1. By `channel_id` + `external_chat_id` → search in `client_channels`
2. If found → return `client_id`
3. If not found → create `client` + `client_channel` record

**SQL (find):**
```sql
SELECT c.id as client_id, c.name, c.phone
FROM clients c
JOIN client_channels cc ON cc.client_id = c.id
WHERE cc.channel_id = $channel_id
  AND cc.external_id = $external_chat_id
  AND c.tenant_id = $tenant_id
LIMIT 1;
```

**SQL (create):**
```sql
-- 1. Create client
INSERT INTO clients (tenant_id, name, phone)
VALUES ($tenant_id, $name, $phone)
RETURNING id as client_id;

-- 2. Link to channel
INSERT INTO client_channels (client_id, channel_id, external_id, external_username)
VALUES ($client_id, $channel_id, $external_chat_id, $username);
```

---

## Dialog Resolver

**Logic:**
1. By `client_id` + `channel_id` → search active dialog
2. If found → return `dialog_id`
3. If not found → create new dialog (without vertical_id!)

**SQL (find active):**
```sql
SELECT id as dialog_id, vertical_id, status_id
FROM dialogs
WHERE client_id = $client_id
  AND channel_id = $channel_id
  AND status_id IN (1, 2)  -- active, waiting
ORDER BY updated_at DESC
LIMIT 1;
```

**SQL (create):**
```sql
INSERT INTO dialogs (tenant_id, client_id, channel_id, status_id)
VALUES ($tenant_id, $client_id, $channel_id, 1)  -- status: active
RETURNING id as dialog_id;
-- Note: vertical_id is NULL, will be set by Core
```

---

## Redis Keys

| Key | Type | TTL | Purpose |
|-----|------|-----|---------|
| `queue:incoming` | List | — | Global incoming queue |
| `queue:processor:lock` | String | 30s | Processor lock |
| `queue:batch:{tenant}:{channel}:{chat_id}` | List | — | Per-tenant per-chat batch |
| `lock:batch:{tenant}:{channel}:{chat_id}` | String | 300s | Chat processing lock |
| `last_seen:{tenant}:{channel}:{chat_id}` | String | — | Last message time |

**Note:** Keys now include `tenant_id` for multi-tenant isolation.

---

## Debounce Logic

**Why:** Client writes multiple messages in a row — wait and merge.

```
10:00:01 — "Привет"           ┐
10:00:03 — "Разбил экран"     ├── batch
10:00:05 — "iPhone 14"        ┘

         ↓ silence 10 sec ↓

10:00:15 — Merged message → Core
           "Привет\n\nРазбил экран\n\niPhone 14"
```

**Parameters:**
- `debounce_seconds: 10` — wait for silence
- `max_wait_seconds: 300` — max 5 min (safety)

---

## Data Contract

**Input to Input Contour (from IN workflow):**
```json
{
  "channel": "telegram",
  "external_user_id": "123456",
  "external_chat_id": "123456",
  "text": "Привет",
  "timestamp": "2024-12-10T10:00:00Z",
  "client_name": "Иван",
  "bot_token": "123:ABC...",
  "media": {...}
}
```

**Output from Input Contour (to Core):**
```json
{
  "tenant_id": "uuid",
  "domain_id": 1,
  "dialog_id": "uuid",
  "client_id": "uuid",
  "channel_id": 1,
  "text": "Привет\n\nРазбил экран\n\niPhone 14",
  "timestamp": "2024-12-10T10:00:01Z",
  "meta": {
    "batched": true,
    "batch_size": 3,
    "batch_reason": "silence_reached",
    "external_chat_id": "123456"
  }
}
```

**Note:** No `vertical_id` in output — Core will determine it.

---

## Form and Phone — Without Redis

```
Form/Phone → Tenant Resolver → Client Resolver → Dialog Resolver → Core
```

**Why:**
- Rare requests (no queue needed)
- No debounce needed (one message = one request)
- Can process synchronously

---

## Vertical Determination (in Core, not Input Contour)

After Input Contour, Core determines `vertical_id`:

| Scenario | How |
|----------|-----|
| New dialog | AI analyzes first message, suggests vertical |
| Existing dialog | Use current `dialog.vertical_id` |
| Client says "want to sell phone" | AI detects vertical change → create new Issue with different vertical |

**Multi-vertical example:**
```
Dialog starts: "Разбил экран iPhone"
  → Core sets vertical_id = 1 (phone_repair)

Later in same dialog: "А еще хочу продать старый Samsung"
  → Core creates new Issue with vertical_id = 2 (buy_sell)
  → Same dialog, same client, multiple verticals
```

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Table | `tenants` | Tenant lookup |
| Table | `channel_accounts` | Credentials → tenant mapping |
| Table | `clients` | Client storage |
| Table | `client_channels` | External ID → client mapping |
| Table | `dialogs` | Dialog storage |
| Redis | queue:incoming | Message buffer |

---

**Document:** INPUT_CONTOUR_OVERVIEW.md
**Date:** 2025-12-11
**Author:** Dmitry + Claude
**Status:** Updated for multi-vertical/multi-domain
