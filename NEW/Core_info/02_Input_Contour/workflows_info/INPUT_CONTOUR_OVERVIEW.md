# Input Contour — Overview

> Input contour: buffering and preparing messages before Client Contour

**MCP Service:** 45.144.177.128:8771
**n8n Polygon:** Webhooks for debugging

---

## Purpose

Input Contour solves:
- **IN workflows are fast** (~100ms) — need quick reply to messenger
- **Batcher is slow** (10s+ debounce) — waits for client to finish typing
- **Redis as buffer** — decouples speeds

**Input Contour does NOT:**
- ❌ Resolve tenant (this is Client Contour)
- ❌ Resolve client (this is Client Contour)
- ❌ Resolve dialog (this is Client Contour)

---

## Architecture (aligned with MCP)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  CHANNEL IN (MCP servers per channel)                                        │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐                │
│  │Telegram │ │WhatsApp │ │  Avito  │ │   VK    │ │   MAX   │                │
│  └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘                │
│       │           │           │           │           │                     │
│       └───────────┴─────┬─────┴───────────┴───────────┘                     │
│                         │                                                    │
│                         │  POST /ingest                                      │
└─────────────────────────│───────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────────────────────┐
│  INPUT CONTOUR (MCP:8771)                                                    │
│                                                                              │
│         ┌───────────────────────────────┐                                   │
│         │  1. /ingest endpoint          │  ← idempotency check              │
│         │     + generate message_id     │                                   │
│         │     + generate trace_id       │                                   │
│         └───────────────┬───────────────┘                                   │
│                         ↓                                                    │
│         ┌───────────────────────────────┐                                   │
│         │  2. Redis RPUSH queue:incoming│  ← fast exit (~10ms)              │
│         └───────────────┬───────────────┘                                   │
│                         │                                                    │
│  - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  │
│                         │  Worker Loop (async)                               │
│                         ↓                                                    │
│         ┌───────────────────────────────┐                                   │
│         │  3. process_batch()           │  ← LPOP queue:incoming            │
│         │     Group by batch_key        │     batch_key = tenant:channel:id │
│         │     Set deadline per batch    │                                   │
│         └───────────────┬───────────────┘                                   │
│                         ↓                                                    │
│         ┌───────────────────────────────┐                                   │
│         │  4. process_deadlines()       │  ← check ZSET deadlines           │
│         │     Wait for silence (10s)    │     or max_wait (300s)            │
│         └───────────────┬───────────────┘                                   │
│                         ↓                                                    │
│         ┌───────────────────────────────┐                                   │
│         │  5. aggregate_and_dispatch()  │  ← merge texts, send              │
│         │     Retry 3x with backoff     │                                   │
│         │     DLQ on failure            │                                   │
│         └───────────────┬───────────────┘                                   │
│                         │                                                    │
│                         │  POST /resolve                                     │
└─────────────────────────│───────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────────────────────┐
│  CLIENT CONTOUR (MCP:8772)                                                   │
│                                                                              │
│         ┌───────────────────────────────┐                                   │
│         │  1. Tenant Resolver           │  ← tenant_id by credential        │
│         └───────────────┬───────────────┘                                   │
│                         ↓                                                    │
│         ┌───────────────────────────────┐                                   │
│         │  2. Client Resolver           │  ← find/create client             │
│         └───────────────┬───────────────┘                                   │
│                         ↓                                                    │
│         ┌───────────────────────────────┐                                   │
│         │  3. Dialog Resolver           │  ← find/create dialog             │
│         └───────────────┬───────────────┘                                   │
│                         │                                                    │
│                         │  POST /webhook/elo-core-ingest                     │
└─────────────────────────│───────────────────────────────────────────────────┘
                          ↓
                    ┌───────────┐
                    │   CORE    │  ← vertical_id определяется здесь
                    └───────────┘
```

**Note:** In MCP architecture, Client/Dialog Resolver is in **Client Contour (8772)**, not Input Contour.

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

## Components (n8n Polygon)

| # | Workflow | Purpose | Output |
|---|----------|---------|--------|
| 1 | **ELO_Input_Ingest** | Receives messages, pushes to queue | accepted: true |
| 2 | **ELO_Input_Worker** | Processes queue, debounces, dispatches | POST to Client Contour |

**Deprecated (moved to Client Contour):**
| # | Old Workflow | New Location | Note |
|---|--------------|--------------|------|
| ~~1~~ | ELO_Core_Tenant_Resolver | Client Contour | Now in 8772 |
| ~~2~~ | ELO_Core_Client_Resolver | Client Contour | Now in 8772 |
| ~~3~~ | ELO_Core_Queue_Processor | Merged into ELO_Input_Worker | n8n-native approach |
| ~~4~~ | ELO_Core_Batch_Debouncer | Merged into ELO_Input_Worker | n8n-native approach |

---

## ~~Tenant Resolver~~ → Moved to Client Contour

> **DEPRECATED:** Tenant resolution is now in Client Contour (8772)
> See: `03_Client_Contour/workflows_info/CLIENT_CONTOUR_OVERVIEW.md`

---

## ~~Client Resolver~~ → Moved to Client Contour

> **DEPRECATED:** Client resolution is now in Client Contour (8772)
> See: `03_Client_Contour/workflows_info/ELO_Client_Resolve.md`

---

## ~~Dialog Resolver~~ → Moved to Client Contour

> **DEPRECATED:** Dialog resolution is now in Client Contour (8772)
> See: `03_Client_Contour/workflows_info/ELO_Client_Resolve.md`

---

## Redis Keys (n8n Polygon)

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

## Dependencies (n8n Polygon)

| Type | Resource | Purpose |
|------|----------|---------|
| Redis | queue:incoming | Global message queue |
| Redis | batch:deadlines | Debounce deadline ZSET |
| Redis | batch:first_seen | First message time ZSET |
| Redis | dlq:input_contour | Dead letter queue |
| Service | Client Contour (8772) | Next step for resolved messages |

**Note:** Input Contour does NOT access PostgreSQL directly. All DB operations are in Client Contour.

---

## n8n Polygon Workflows

| Workflow | File | Description |
|----------|------|-------------|
| **ELO_Input_Ingest** | `ELO_Input_Ingest.json` | Webhook `/webhook/elo-input-ingest` |
| **ELO_Input_Worker** | `ELO_Input_Worker.json` | Schedule trigger, debounce, dispatch |

**Workflow descriptions:**
- `ELO_Input_Ingest.md` — Detailed node descriptions
- `ELO_Input_Worker.md` — Detailed node descriptions

---

## Deprecated Old Workflows

| Old File | Status | Replacement |
|----------|--------|-------------|
| `ELO_Core_Tenant_Resolver.md` | ⚠️ Deprecated | Moved to Client Contour |
| `ELO_Core_Client_Resolver.md` | ⚠️ Deprecated | Moved to Client Contour |
| `ELO_Core_Queue_Processor.md` | ⚠️ Deprecated | Merged into ELO_Input_Worker |
| `ELO_Core_Batch_Debouncer.md` | ⚠️ Deprecated | Merged into ELO_Input_Worker |

**Keep old files for reference, but use new n8n polygon workflows for development.**

---

**Document:** INPUT_CONTOUR_OVERVIEW.md
**Date:** 2025-12-11
**Author:** Dmitry + Claude
**Status:** Updated for n8n polygon architecture (aligned with MCP)
