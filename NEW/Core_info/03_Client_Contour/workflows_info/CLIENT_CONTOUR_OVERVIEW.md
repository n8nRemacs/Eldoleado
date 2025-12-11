# Client Contour — Overview

> Client identity resolution and dialog management (MCP Service)

**Server:** 45.144.177.128:8772
**Service:** `client-contour` (Docker)

---

## General Information

| Parameter | Value |
|-----------|-------|
| **Type** | MCP Service (Python/FastAPI) |
| **Port** | 8772 |
| **Endpoint** | POST `/resolve` |
| **Called from** | Input Contour (8771) |
| **Calls** | Core Contour (n8n webhook) |
| **Repository** | `MCP/client-contour/` |

---

## Purpose

Client Contour is responsible for **unified client** — one client across all channels.

1. **Tenant Resolver** — resolve tenant by bot_token/profile_id
2. **Client Resolver** — find/create client
3. **Dialog Resolver** — find/create dialog
4. **Forward to Core** — async fire-and-forget

**Principle:** No business logic here. Just identity resolution.

---

## Architecture

```
Input Contour (MCP:8771)
       │
       │ POST /resolve
       ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  CLIENT CONTOUR (MCP:8772)                                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌──────────────────┐     ┌──────────────────┐     ┌──────────────────┐    │
│  │ Tenant Resolver  │ ──▶ │  Client Resolver │ ──▶ │  Dialog Resolver │    │
│  └──────────────────┘     └──────────────────┘     └──────────────────┘    │
│         │                        │                        │                 │
│         ▼                        ▼                        ▼                 │
│    +tenant_id               +client_id                +dialog_id            │
│    +domain_id                                                               │
│    +channel_id                                                              │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐  │
│  │  Forward to Core (async, fire-and-forget)                             │  │
│  │  POST https://n8n.n8nsrv.ru/webhook/elo-core-ingest                   │  │
│  └──────────────────────────────────────────────────────────────────────┘  │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
       │
       │ Response to Input Contour
       ▼
  {accepted: true, tenant_id, client_id, dialog_id}
```

---

## Endpoints

### POST /resolve

Main endpoint. Receives batched message from Input Contour.

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
      {"channel": "telegram", "external_id": "123456789"}
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

### GET /health

```json
{
  "status": "ok",
  "postgres": true,
  "neo4j": true,
  "redis": true
}
```

### GET /dlq

Get unknown tenant errors.

### DELETE /dlq

Clear DLQ.

---

## Components

| # | Component | Purpose | File |
|---|-----------|---------|------|
| 1 | Tenant Resolver | Find tenant by credential | `resolvers/tenant.py` |
| 2 | Client Resolver | Find/create client | `resolvers/client.py` |
| 3 | Dialog Resolver | Find/create dialog | `resolvers/dialog.py` |

---

## 1. Tenant Resolver

### Logic

```
1. Get credential from request (bot_token, profile_id, etc.)
2. Query channel_accounts table
3. Return tenant_id, domain_id, channel_id
4. If not found → DLQ
```

### SQL: Resolve tenant

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

### Credential mapping

| Channel | Credential field | Source |
|---------|------------------|--------|
| telegram | `bot_token` | Full token |
| whatsapp | `profile_id` | Wappi profile ID |
| vk | `group_id` | VK group ID |
| avito | `user_id` | Avito user ID |
| max | `bot_id` | MAX bot ID |

---

## 2. Client Resolver (MVP — exact match only)

### Logic

```
1. Search by channel + external_id
   └── Found? → return client_id

2. Search by exact phone (if provided)
   └── Found? → link channel to existing client → return client_id

3. Create new client
   └── Create client record
   └── Create client_channel record
   └── Sync to Neo4j
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

**MVP: No fuzzy matching, no candidate flow, no auto-merge.**

---

## 3. Dialog Resolver

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

## Configuration

```env
# Database
DATABASE_URL=postgresql://user:pass@185.221.214.83:6544/postgres

# Neo4j (for client sync)
NEO4J_URI=bolt://45.144.177.128:7687
NEO4J_USER=neo4j
NEO4J_PASSWORD=***

# Redis (for DLQ)
REDIS_URL=redis://:password@redis:6379/0

# Core endpoint (async fire-and-forget)
CORE_URL=https://n8n.n8nsrv.ru/webhook/elo-core-ingest

# Service
HOST=0.0.0.0
PORT=8772
```

---

## Flow Diagram

```
Input Contour (8771)
     │
     │  POST /resolve
     │  {channel, bot_token, external_chat_id, text, ...}
     ▼
┌─────────────────┐
│ Tenant Resolver │  ← credential → tenant_id, domain_id, channel_id
└────────┬────────┘
         │  not found? → DLQ
         ▼
┌─────────────────┐
│ Client Resolver │  ← external_id/phone → client_id
└────────┬────────┘
         │  new? → create + Neo4j sync
         ▼
┌─────────────────┐
│ Dialog Resolver │  ← client_id + channel_id → dialog_id
└────────┬────────┘
         │  new? → create (vertical_id = NULL)
         ▼
┌─────────────────┐
│ Forward to Core │  ← async POST (fire-and-forget)
└────────┬────────┘
         │
         ▼
   Response to Input Contour
   {accepted: true, tenant_id, client_id, dialog_id}
```

---

## Files

```
MCP/client-contour/
├── main.py               # FastAPI app, /resolve endpoint
├── config.py             # Settings from env
├── resolvers/
│   ├── __init__.py
│   ├── tenant.py         # Tenant Resolver (mock)
│   ├── client.py         # Client Resolver (mock)
│   └── dialog.py         # Dialog Resolver (mock)
├── db.py                 # PostgreSQL connection
├── neo4j_client.py       # Neo4j connection
├── requirements.txt
├── Dockerfile
└── REQUIREMENTS.md       # Full specification
```

---

## Current Status

| Component | Status |
|-----------|--------|
| Tenant Resolver | Mock (returns test UUIDs) |
| Client Resolver | Mock (returns test UUIDs) |
| Dialog Resolver | Mock (returns test UUIDs) |
| Forward to Core | Implemented (logs if Core not ready) |
| DLQ | Implemented |

**Waiting for:** PostgreSQL tables (migration)

---

## Finalized Decisions

| Question | Decision |
|----------|----------|
| Sync vs Async to Core? | **Async (fire-and-forget)**. Return immediately after resolve. |
| DLQ for unknown tenant? | **Yes**, Redis `dlq:unknown_tenant` |
| Retry policy | **Input Contour handles retries**. Client Contour is stateless. |
| Client merge in MVP? | **No fuzzy matching**. Exact match only. |
| Protocol to Core | **HTTP JSON** (MVP). gRPC later. |
| vertical_id | **Core determines**. Client Contour passes NULL. |

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Table | `tenants` | Tenant lookup |
| Table | `channel_accounts` | Credential lookup |
| Table | `clients` | Client storage |
| Table | `client_channels` | Channel links |
| Table | `dialogs` | Dialog storage |
| Tool | Graph Query | Neo4j sync |
| Service | Redis | DLQ storage |

---

**Document:** CLIENT_CONTOUR_OVERVIEW.md
**Date:** 2025-12-11
**Author:** Dmitry + Claude
**Status:** MCP deployed, mock mode (waiting for DB)
