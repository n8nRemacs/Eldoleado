# Client Contour MCP — Requirements

> Task for developer: create Client Contour MCP service

**Date:** 2025-12-11
**Status:** In development
**Updated:** 2025-12-11 (decisions finalized)

---

## Overview

Client Contour is a Python/FastAPI MCP service that:
1. Receives batched messages from Input Contour
2. Resolves tenant by credentials (bot_token, profile_id, etc.)
3. Resolves/creates client (exact match only in MVP)
4. Resolves/creates dialog
5. Sends enriched payload to Core (async, fire-and-forget)

---

## Architecture Position

```
Input Contour (MCP:8771)     Client Contour (MCP:8772)     Core Contour (n8n)
├── Tenant Resolver          ├── Client Resolver           ├── Context Builder
├── Queue/Processor          └── Dialog Resolver           ├── Orchestrator
└── Debouncer                    (один сервис, 2 метода)   └── Dialog Engine
```

```
MCP Adapters (telegram, whatsapp, ...)
       │
       │ POST /ingest (normalized message)
       ▼
┌─────────────────────────────────────────────────────────────┐
│  INPUT CONTOUR (MCP:8771)                                   │
│  • Queue to Redis                                           │
│  • Debounce (10s silence, 300s max_wait)                    │
│  • Aggregate messages                                       │
│  • POST to Client Contour                                   │
│  • Retry x3 + DLQ                                           │
└─────────────────────────────────────────────────────────────┘
       │
       │ POST /resolve (batched message)
       ▼
┌─────────────────────────────────────────────────────────────┐
│  CLIENT CONTOUR (MCP:8772) ← THIS SERVICE                   │
│  • Tenant Resolver (by bot_token/profile_id)                │
│  • Client Resolver (find/create, exact match only)          │
│  • Dialog Resolver (find/create active)                     │
│  • POST to Core (async, fire-and-forget)                    │
└─────────────────────────────────────────────────────────────┘
       │
       │ POST /webhook/elo-core-ingest (fully enriched)
       ▼
┌─────────────────────────────────────────────────────────────┐
│  CORE CONTOUR (n8n for now, MCP later)                      │
│  • Context Builder                                          │
│  • Orchestrator + AI                                        │
│  • Dialog Engine                                            │
│  • → Channel OUT (response to client)                       │
└─────────────────────────────────────────────────────────────┘
```

---

## Finalized Decisions

| Question | Decision |
|----------|----------|
| Sync vs Async to Core? | **Async (fire-and-forget)**. Return immediately after resolve. Core sends response via Channel OUT. |
| DLQ for unknown tenant? | **Yes**, Redis `dlq:unknown_tenant` |
| Retry policy | **Input Contour handles retries**. Client Contour is stateless. |
| Client merge in MVP? | **No fuzzy matching**. Exact match only (channel+external_id, then phone). |
| Protocol to Core | **HTTP JSON** (MVP). gRPC as future target. |
| vertical_id | **Core determines**. Client Contour passes NULL. |
| Redis keys format | **With tenant**: `queue:batch:{tenant_id}:{channel}:{chat_id}` |

---

## Endpoints

### POST /resolve

Main endpoint. Receives batched message from Input Contour, resolves all IDs, forwards to Core.

**Input (from Input Contour):**
```json
{
  "channel": "telegram",
  "bot_token": "123456:ABC...",
  "external_user_id": "987654321",
  "external_chat_id": "123456789",
  "client_name": "Ivan Petrov",
  "client_phone": "+79991234567",
  "text": "Merged message text",
  "timestamp": "2024-12-10T10:00:15Z",
  "message_ids": ["msg_1", "msg_2", "msg_3"],
  "trace_id": "trace_xyz789",
  "media": {...},
  "meta": {
    "batched": true,
    "batch_size": 3,
    "batch_reason": "debounce"
  }
}
```

**Process:**
1. Tenant Resolver → get `tenant_id`, `domain_id`, `channel_id`
2. Client Resolver → get/create `client_id`
3. Dialog Resolver → get/create `dialog_id`
4. Forward to Core (async, don't wait)
5. Return immediately to Input Contour

**Output to Core:**
```json
{
  "tenant_id": "uuid",
  "domain_id": 1,
  "client_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "external_chat_id": "123456789",
  "text": "Merged message text",
  "timestamp": "2024-12-10T10:00:15Z",
  "message_ids": ["msg_1", "msg_2", "msg_3"],
  "trace_id": "trace_xyz789",
  "media": {...},
  "meta": {
    "batched": true,
    "batch_size": 3,
    "batch_reason": "debounce",
    "is_new_client": false,
    "is_new_dialog": false
  },
  "client": {
    "id": "uuid",
    "name": "Ivan Petrov",
    "phone": "+79991234567",
    "channels": [
      {"channel": "telegram", "external_id": "123456789"},
      {"channel": "whatsapp", "external_id": "79991234567"}
    ]
  }
}
```

**Response to Input Contour:**
```json
{
  "accepted": true,
  "tenant_id": "uuid",
  "client_id": "uuid",
  "dialog_id": "uuid",
  "trace_id": "trace_xyz789"
}
```

---

### GET /health

Health check.

```json
{
  "status": "ok",
  "postgres": true,
  "neo4j": true,
  "redis": true
}
```

---

### GET /dlq

Get unknown tenant errors.

```json
{
  "count": 5,
  "items": [...]
}
```

### DELETE /dlq

Clear DLQ.

```json
{
  "cleared": true
}
```

---

## Tenant Resolver Logic

**SQL:**
```sql
SELECT
  t.id as tenant_id,
  t.domain_id,
  ca.id as channel_account_id,
  c.id as channel_id
FROM tenants t
JOIN channel_accounts ca ON ca.tenant_id = t.id
JOIN channels c ON c.id = ca.channel_id
WHERE ca.account_id = $credential    -- bot_token, profile_id, etc.
  AND c.code = $channel              -- 'telegram', 'whatsapp', etc.
  AND ca.is_active = true
  AND t.is_active = true
LIMIT 1;
```

**Credential mapping:**
| Channel | Credential field | Source |
|---------|------------------|--------|
| telegram | `bot_token` | Full token |
| whatsapp | `profile_id` | Wappi profile ID |
| vk | `group_id` | VK group ID |
| avito | `user_id` | Avito user ID |
| max | `bot_id` | MAX bot ID |

**Error handling:**
- Credential not found → 404 + DLQ `dlq:unknown_tenant`
- Tenant inactive → 403 "Tenant disabled"

---

## Client Resolver Logic (MVP — exact match only)

**Step 1: Find by channel + external_id**
```sql
SELECT c.id as client_id, c.name, c.phone
FROM clients c
JOIN client_channels cc ON cc.client_id = c.id
WHERE cc.channel_id = $channel_id
  AND cc.external_id = $external_chat_id
  AND c.tenant_id = $tenant_id
LIMIT 1;
```

**Step 2: Find by exact phone (if provided and Step 1 failed)**
```sql
SELECT id as client_id, name, phone
FROM clients
WHERE tenant_id = $tenant_id
  AND phone = $phone
LIMIT 1;
```
If found → link new channel to existing client.

**Step 3: Create new client (if Steps 1-2 failed)**
```sql
-- Create client
INSERT INTO clients (tenant_id, name, phone)
VALUES ($tenant_id, $name, $phone)
RETURNING id as client_id;

-- Link to channel
INSERT INTO client_channels (client_id, channel_id, external_id, external_username)
VALUES ($client_id, $channel_id, $external_chat_id, $username);
```

**Neo4j sync (after create):**
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

**MVP: No fuzzy matching, no candidate flow, no auto-merge.**

---

## Dialog Resolver Logic

**Step 1: Find active dialog**
```sql
SELECT id as dialog_id, vertical_id, status_id
FROM dialogs
WHERE client_id = $client_id
  AND channel_id = $channel_id
  AND status_id IN (1, 2)  -- active, waiting
ORDER BY updated_at DESC
LIMIT 1;
```

**Step 2: Create new dialog (if not found)**
```sql
INSERT INTO dialogs (tenant_id, client_id, channel_id, status_id)
VALUES ($tenant_id, $client_id, $channel_id, 1)  -- status: active
RETURNING id as dialog_id;

-- NOTE: vertical_id is NULL, Core will determine it
```

---

## Configuration (env)

```env
# Database
DATABASE_URL=postgresql://user:pass@185.221.214.83:6544/postgres

# Neo4j (for client sync)
NEO4J_URI=bolt://45.144.177.128:7687
NEO4J_USER=neo4j
NEO4J_PASSWORD=Mi31415926pS

# Redis (for DLQ)
REDIS_URL=redis://45.144.177.128:6379/0

# Core endpoint (async fire-and-forget)
CORE_URL=https://n8n.n8nsrv.ru/webhook/elo-core-ingest

# Service
HOST=0.0.0.0
PORT=8772
```

---

## Deployment

**Server:** RU (45.144.177.128)
**Port:** 8772

**Dependencies:**
- PostgreSQL (remote: 185.221.214.83:6544)
- Neo4j (local: 45.144.177.128:7687)
- Redis (local: 45.144.177.128:6379) — for DLQ

---

## Files to Create

```
MCP/client-contour/
├── main.py           # FastAPI app
├── config.py         # Settings from env
├── resolvers/
│   ├── __init__.py
│   ├── tenant.py     # Tenant Resolver
│   ├── client.py     # Client Resolver
│   └── dialog.py     # Dialog Resolver
├── db.py             # PostgreSQL connection
├── neo4j_client.py   # Neo4j connection (for sync)
├── requirements.txt
├── Dockerfile
└── REQUIREMENTS.md   # This file
```

---

## MVP Scope

**In scope:**
- Tenant Resolver (by credential)
- Client Resolver (exact match: channel+external_id, phone)
- Dialog Resolver (find/create active)
- Forward to Core (async)
- DLQ for unknown tenants

**Out of scope (future):**
- Client merge (fuzzy matching, candidate flow)
- Touchpoint registration
- Caching layer
- Metrics/tracing (structlog only for MVP)
- gRPC protocol
- client_merges table logic

---

## Mock Mode (until DB ready)

For development before database is ready:

```python
# Mock responses
MOCK_TENANT = {
    "tenant_id": "test-tenant-uuid",
    "domain_id": 1,
    "channel_id": 1
}

MOCK_CLIENT = {
    "id": "test-client-uuid",
    "name": "Test Client",
    "phone": None,
    "is_new": False,
    "channels": [
        {"channel": "telegram", "external_id": "123456789"}
    ]
}

MOCK_DIALOG = {
    "id": "test-dialog-uuid",
    "is_new": False
}
```

Core forward: log payload to console (n8n webhook not ready yet).

---

## Contract Fields

| Field | Required | Purpose |
|-------|----------|---------|
| `message_id` | Yes | Idempotency/dedup |
| `trace_id` | Yes | E2E tracing |
| `tenant_id` | Yes | Multi-tenant isolation |
| `client_id` | Yes | Client identity |
| `dialog_id` | Yes | Dialog context |

---

**Document:** REQUIREMENTS.md
**Author:** Claude + Dmitry
**Status:** Ready for implementation
