# Client Contour MCP — Requirements

> Task for developer: create Client Contour MCP service

**Date:** 2025-12-11
**Status:** Not started

---

## Overview

Client Contour is a Python/FastAPI MCP service that:
1. Receives messages from Input Contour (after batching)
2. Resolves tenant by credentials (bot_token, profile_id, etc.)
3. Resolves/creates client
4. Resolves/creates dialog
5. Sends enriched payload to Core

---

## Architecture Position

```
MCP Adapters (telegram, whatsapp, ...)
       │
       │ POST /ingest (normalized message)
       ▼
┌─────────────────────────────────────────────────────────────┐
│  INPUT CONTOUR (MCP)                                         │
│  • Queue to Redis                                            │
│  • Debounce (10s silence)                                    │
│  • Aggregate messages                                        │
│  • POST to Client Contour                                    │
└─────────────────────────────────────────────────────────────┘
       │
       │ POST /resolve (batched message)
       ▼
┌─────────────────────────────────────────────────────────────┐
│  CLIENT CONTOUR (MCP) ← THIS SERVICE                         │
│  • Tenant Resolver (by bot_token/profile_id)                 │
│  • Client Resolver (find/create)                             │
│  • Dialog Resolver (find/create active)                      │
│  • POST to Core                                              │
└─────────────────────────────────────────────────────────────┘
       │
       │ POST /webhook/elo-core-ingest (fully enriched)
       ▼
┌─────────────────────────────────────────────────────────────┐
│  CORE (n8n for now, MCP later)                               │
│  • Context Builder                                           │
│  • AI Processing                                             │
│  • Response                                                  │
└─────────────────────────────────────────────────────────────┘
```

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
    "batch_reason": "silence_reached"
  }
}
```

**Process:**
1. Tenant Resolver → get `tenant_id`, `domain_id`, `channel_id`
2. Client Resolver → get/create `client_id`
3. Dialog Resolver → get/create `dialog_id`
4. Forward to Core

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
    "batch_reason": "silence_reached",
    "is_new_client": false,
    "is_new_dialog": false
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
  "neo4j": true
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
- Credential not found → 404 "Unknown tenant"
- Tenant inactive → 403 "Tenant disabled"

---

## Client Resolver Logic

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

**Step 2: Find by phone (if provided and Step 1 failed)**
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
NEO4J_PASSWORD=***

# Core endpoint
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
- Redis (local: 45.144.177.128:6379) — for caching, optional

---

## Integration with Input Contour

Input Contour must be updated to call Client Contour:

```python
# In input-contour/main.py

CLIENT_CONTOUR_URL = os.getenv("CLIENT_CONTOUR_URL", "http://localhost:8772/resolve")

async def aggregate_and_dispatch(key: str):
    # ... existing aggregation logic ...

    # Instead of posting directly to CORE_URL,
    # post to Client Contour which will forward to Core
    async with httpx.AsyncClient(timeout=30.0) as client:
        response = await client.post(CLIENT_CONTOUR_URL, json=payload)
        # Client Contour handles forwarding to Core
```

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
- Forward to Core

**Out of scope (future):**
- Client merge (fuzzy matching)
- Touchpoint registration
- Caching layer
- Metrics/tracing

---

## Questions for Discussion

1. Should Client Contour respond synchronously (wait for Core response) or async (fire-and-forget to Core)?
   - **Recommendation:** Async (fire-and-forget), return immediately after resolve

2. Should failed tenant resolution be logged to DLQ?
   - **Recommendation:** Yes, log to Redis `dlq:unknown_tenant`

3. Error retry policy?
   - **Recommendation:** Let Input Contour handle retries, Client Contour is stateless

---

**Document:** REQUIREMENTS.md
**Author:** Claude
**Status:** Ready for implementation
