# Input Contour → MCP Transition Plan

> Goal: move Input Contour out of n8n into an independent MCP service

**Status:** Synced with architecture (2025-12-11)

---

## Contour Separation (agreed)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  INPUT CONTOUR (MCP Service)                                                 │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐                       │
│  │   Tenant     │→ │    Queue     │→ │   Batch      │                       │
│  │   Resolver   │  │   Processor  │  │   Debouncer  │                       │
│  └──────────────┘  └──────────────┘  └──────────────┘                       │
│    +tenant_id        grouping          +merged text                          │
│    +domain_id                                                                │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  CLIENT CONTOUR (same service, separate endpoints)                           │
│  ┌──────────────┐  ┌──────────────┐                                         │
│  │   Client     │→ │   Dialog     │                                         │
│  │   Resolver   │  │   Resolver   │                                         │
│  └──────────────┘  └──────────────┘                                         │
│    +client_id        +dialog_id                                              │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  CORE CONTOUR                                                                │
│  Context Builder → Request Builder → Orchestrator → Dialog Engine           │
│    +vertical_id                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Decision:** Input + Client = separate contours (easier to evolve resolve rules independently).

---

## Contracts (data)

### Ingress (from MCP adapters / IN workflows)

```json
{
  "channel": "telegram",
  "external_user_id": "123456",
  "external_chat_id": "123456",
  "text": "Message text",
  "timestamp": "2024-12-10T10:00:00Z",
  "client_phone": "+79991234567",
  "client_name": "Ivan",
  "bot_token": "123:ABC...",
  "media": {...},
  "meta": {...},
  "message_id": "msg_abc123",
  "trace_id": "trace_xyz789"
}
```

**Required fields:**
- `message_id` — for idempotency/dedup
- `trace_id` — for end-to-end tracing

### Internal (after tenant resolve, before debounce)

```json
{
  // ...ingress fields +
  "tenant_id": "uuid",
  "domain_id": 1,
  "channel_id": 1,
  "batch_key": "{tenant}:{channel}:{chat_id}",
  "received_at": "..."
}
```

### Egress to Core

```json
{
  "tenant_id": "uuid",
  "domain_id": 1,
  "client_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "text": "Merged message text",
  "timestamp": "2024-12-10T10:00:15Z",
  "message_ids": ["msg_1", "msg_2", "msg_3"],
  "trace_id": "trace_xyz789",
  "meta": {
    "batched": true,
    "batch_size": 3,
    "batch_reason": "silence_reached",
    "external_chat_id": "123456",
    "is_new_client": false,
    "is_new_dialog": false
  }
}
```

**Note:** No `vertical_id` — Core determines it.

---

## Input Contour (MCP) — components

1. **Ingest API**: HTTP `/ingest`, validate, enqueue, respond 202 with `message_id`, `trace_id`, `batch_key`.

2. **Queue Layer (Redis)**:
   - `queue:incoming` — global incoming queue
   - `queue:processor:lock` — processor lock (30s)
   - `queue:batch:{tenant}:{channel}:{chat_id}` — per-tenant per-chat batch
   - `lock:batch:{tenant}:{channel}:{chat_id}` — chat processing lock (TTL 300s)
   - `last_seen:{tenant}:{channel}:{chat_id}` — last message time
   - Idempotency by `message_id`

3. **Queue Processor**: every 1–2s, LPOP/stream read N, group by `batch_key`.

4. **Debouncer/Aggregator**: wait `debounce_seconds` (10s default), `max_wait_seconds` 300s; merge text/media; build egress.

5. **Output Dispatcher**: send egress to Client Contour (HTTP) with retries.

6. **Config**: per-tenant `debounce_seconds`, `max_wait_seconds`, rate limits.

---

## Client Contour — endpoints

**Same service, two endpoints:**

### POST /resolve-client

**Input:**
```json
{
  "tenant_id": "uuid",
  "channel_id": 1,
  "external_chat_id": "123456",
  "external_user_id": "123456",
  "client_phone": "+79991234567",
  "client_name": "Ivan",
  "message_id": "msg_abc123",
  "trace_id": "trace_xyz789"
}
```

**Output:**
```json
{
  "client": {
    "id": "uuid",
    "name": "Ivan",
    "phone": "+79991234567",
    "channels": [
      {"channel_id": 1, "channel": "telegram", "external_id": "123456"},
      {"channel_id": 2, "channel": "whatsapp", "external_id": "79991234567"}
    ]
  },
  "match_status": "found|created",
  "trace_id": "trace_xyz789"
}
```

**MVP Logic:**
1. Search by `channel_id` + `external_chat_id` → found
2. Search by exact `phone` match → found (link new channel)
3. Otherwise → create new client

**No fuzzy/ambiguous in MVP.**

### POST /resolve-dialog

**Input:**
```json
{
  "tenant_id": "uuid",
  "client_id": "uuid",
  "channel_id": 1,
  "trace_id": "trace_xyz789"
}
```

**Output:**
```json
{
  "dialog": {
    "id": "uuid",
    "status_id": 1,
    "vertical_id": null
  },
  "is_new": false,
  "trace_id": "trace_xyz789"
}
```

**Logic:**
1. Search active dialog for `client_id` + `channel_id`
2. If found → return
3. If not → create (vertical_id = NULL)

---

## Storage/indexes

### clients
```sql
CREATE INDEX idx_clients_tenant_phone ON clients(tenant_id, phone);
```

### client_channels
```sql
CREATE UNIQUE INDEX idx_client_channels_lookup
ON client_channels(channel_id, external_id);
```

### client_merges (for future, not used in MVP)
```sql
CREATE TABLE client_merges (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    master_client_id UUID NOT NULL REFERENCES clients(id),
    merged_client_id UUID NOT NULL REFERENCES clients(id),
    reason VARCHAR(50),  -- 'phone_match', 'manual', 'ai_suggested'
    merged_at TIMESTAMPTZ DEFAULT NOW(),
    merged_by UUID  -- operator_id if manual
);
```

**MVP:** Table exists in schema, not used in logic.

---

## Protocol Decision

| Option | Pros | Cons | Decision |
|--------|------|------|----------|
| HTTP JSON | Fast start, n8n compatible | Less performant | **MVP** |
| gRPC | Performance, strict contracts | More setup | Target for prod |

**Decision:** Start with HTTP JSON, migrate to gRPC later if needed.

---

## Multi-tenant / domain / vertical guardrails

- `tenant_id` in all Redis keys: `queue:batch:{tenant}:{channel}:{chat_id}`
- `domain_id` retrieved from tenant record
- `vertical_id` NOT set in Input/Client Contour — Core determines per dialog/issue
- Grouping by `tenant + channel + chat_id` to avoid cross-tenant mixing

---

## Channel adapter requirements

| Channel | message_id source | trace_id | Notes |
|---------|-------------------|----------|-------|
| Telegram | `message_id` from webhook | generate/propagate | Keep `bot_token`, `user_id`, `chat_id` |
| WhatsApp | `messageId`/`id` | generate/propagate | Normalize phone to E.164 |
| VK | VK `message.id` | generate/propagate | Include `group_id` in `meta` |
| Avito | `payload.value.id` | generate/propagate | `author_id`, `chat_id`, `item_id` → meta |
| MAX | `message.id` | generate/propagate | Normalize phone if present |
| Form | generate UUID | generate/propagate | One-shot (no Redis) |
| Phone | `call_id` | generate/propagate | One-shot (no Redis) |

All adapters must send: `message_id` + `trace_id`.

---

## Rollout sequence

1. Add `message_id`/`trace_id` in MCP adapters
2. Start ingest → Redis queue (parallel with old path)
3. Launch Queue Processor + Debouncer, enable for test tenant
4. Launch Client Resolver + Dialog Resolver endpoints
5. Switch Core to consume new egress
6. Future: merge/fuzzy, blocklists, per-tenant policies

---

## Decisions Summary

| # | Question | Decision |
|---|----------|----------|
| 1 | Contour separation | Input + Client = separate contours |
| 2 | Dialog Resolver location | Client Contour, separate endpoint |
| 3 | Client response format | Array of channels |
| 4 | Merge in MVP | Exact match only (phone, channel_id) |
| 5 | message_id, trace_id | Required in contracts |
| 6 | vertical_id | Core determines, not Input/Client |
| 7 | Redis keys | Include tenant: `{tenant}:{channel}:{chat_id}` |
| 8 | Protocol | HTTP JSON for MVP, gRPC target |
| 9 | client_merges table | In schema, not used in MVP |

---

**Document:** MCP_TRANSITION.md
**Last sync:** 2025-12-11
**Authors:** Developer + Claude
**Status:** Synced with ARCHITECTURE.md
