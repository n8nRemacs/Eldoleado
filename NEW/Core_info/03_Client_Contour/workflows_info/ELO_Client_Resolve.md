# ELO_Client_Resolve

> n8n Polygon — Resolve tenant, client, dialog from incoming message

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `NEW` (to be created) |
| **File** | `NEW/workflows/ELO_Client/ELO_Client_Resolve.json` |
| **Trigger** | Webhook POST `/webhook/elo-client-resolve` |
| **Called from** | Input Contour (MCP:8771) |
| **Calls** | → Core Contour (ELO_Core_Ingest) |

---

## Purpose

n8n polygon for debugging Client Contour logic before porting to MCP:

1. **Tenant Resolver** — find tenant by bot_token/profile_id
2. **Client Resolver** — find/create client
3. **Dialog Resolver** — find/create dialog
4. **Forward to Core** — async fire-and-forget

**Principle:** No business logic. Just identity resolution.

---

## Input Data

**From Input Contour (MCP:8771):**
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
  "message_ids": ["msg_1", "msg_2"],
  "trace_id": "trace_xyz789",
  "media": {},
  "meta": {
    "batched": true,
    "batch_size": 2
  }
}
```

---

## Output Data

**To Core Contour:**
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
  "message_ids": ["msg_1", "msg_2"],
  "trace_id": "trace_xyz789",
  "media": {},
  "meta": {
    "batched": true,
    "batch_size": 2,
    "is_new_client": false,
    "is_new_dialog": false
  },
  "client": {
    "id": "uuid",
    "name": "Ivan Petrov",
    "phone": "+79991234567"
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

## Nodes

### 1. Webhook Trigger

| Parameter | Value |
|----------|----------|
| **Type** | Webhook |
| **Path** | `/webhook/elo-client-resolve` |
| **Method** | POST |
| **Response** | Immediately (async processing) |

---

### 2. Validate Input

```javascript
const input = $input.first().json;

// Required fields
const required = ['channel', 'external_chat_id', 'text'];
for (const field of required) {
  if (!input[field]) {
    throw new Error(`Missing required field: ${field}`);
  }
}

// Extract credential based on channel
let credential = null;
if (input.channel === 'telegram') credential = input.bot_token;
else if (input.channel === 'whatsapp') credential = input.profile_id;
else if (input.channel === 'vk') credential = input.group_id;
else if (input.channel === 'avito') credential = input.user_id;
else if (input.channel === 'max') credential = input.bot_id;

return {
  ...input,
  credential: credential,
  received_at: new Date().toISOString()
};
```

---

### 3. Tenant Resolver (SQL)

| Parameter | Value |
|----------|----------|
| **Type** | Postgres |
| **Operation** | Execute Query |

**SQL:**
```sql
SELECT
  t.id as tenant_id,
  t.domain_id,
  ca.id as channel_account_id,
  ca.channel_id
FROM elo_tenants t
JOIN elo_channel_accounts ca ON ca.tenant_id = t.id
WHERE ca.credential = '{{ $json.credential }}'
  AND ca.channel_code = '{{ $json.channel }}'
  AND ca.is_active = true
  AND t.is_active = true
LIMIT 1;
```

---

### 4. Tenant Found?

| Condition | Result |
|---------|-----------|
| `tenant_id` exists | → Client Resolver |
| `tenant_id` empty | → DLQ (Unknown Tenant) |

---

### 5. DLQ (Unknown Tenant)

```javascript
// Log to Redis DLQ
const input = $('Validate Input').first().json;

return {
  error: 'unknown_tenant',
  channel: input.channel,
  credential: input.credential,
  external_chat_id: input.external_chat_id,
  timestamp: new Date().toISOString()
};
```

Then: Redis RPUSH `dlq:unknown_tenant`

---

### 6. Client Resolver (SQL)

| Parameter | Value |
|----------|----------|
| **Type** | Postgres |
| **Operation** | Execute Query |

**SQL: Find by channel external_id**
```sql
SELECT c.id as client_id, c.name, c.phone
FROM elo_clients c
JOIN elo_client_channels cc ON cc.client_id = c.id
WHERE cc.channel_id = {{ $json.channel_id }}
  AND cc.external_id = '{{ $json.external_chat_id }}'
  AND c.tenant_id = '{{ $json.tenant_id }}'
LIMIT 1;
```

---

### 7. Client Found?

| Condition | Result |
|---------|-----------|
| `client_id` exists | → Dialog Resolver |
| `client_id` empty | → Find by Phone |

---

### 8. Find by Phone (SQL)

```sql
SELECT c.id as client_id, c.name, c.phone
FROM elo_clients c
WHERE c.tenant_id = '{{ $json.tenant_id }}'
  AND c.phone = '{{ $json.client_phone }}'
LIMIT 1;
```

If found → Link channel to client, then Dialog Resolver
If not found → Create Client

---

### 9. Create Client (SQL)

```sql
-- Insert client
INSERT INTO elo_clients (tenant_id, name, phone)
VALUES (
  '{{ $json.tenant_id }}',
  '{{ $json.client_name }}',
  '{{ $json.client_phone }}'
)
RETURNING id as client_id;

-- Link to channel
INSERT INTO elo_client_channels (client_id, channel_id, external_id)
VALUES (
  '{{ $json.client_id }}',
  {{ $json.channel_id }},
  '{{ $json.external_chat_id }}'
);
```

**Then:** Sync to Neo4j via Graph Tool (HTTP Request)

---

### 10. Neo4j Sync (HTTP Request)

| Parameter | Value |
|----------|----------|
| **Type** | HTTP Request |
| **URL** | `http://45.144.177.128:8773/query` |
| **Method** | POST |

**Body:**
```json
{
  "query_code": "create_client",
  "params": {
    "client_id": "{{ $json.client_id }}",
    "tenant_id": "{{ $json.tenant_id }}",
    "name": "{{ $json.client_name }}",
    "phone": "{{ $json.client_phone }}"
  }
}
```

---

### 11. Dialog Resolver (SQL)

| Parameter | Value |
|----------|----------|
| **Type** | Postgres |
| **Operation** | Execute Query |

**SQL: Find active dialog**
```sql
SELECT id as dialog_id, vertical_id, status
FROM elo_dialogs
WHERE client_id = '{{ $json.client_id }}'
  AND channel_id = {{ $json.channel_id }}
  AND status IN ('active', 'waiting')
ORDER BY updated_at DESC
LIMIT 1;
```

---

### 12. Dialog Found?

| Condition | Result |
|---------|-----------|
| `dialog_id` exists | → Build Output |
| `dialog_id` empty | → Create Dialog |

---

### 13. Create Dialog (SQL)

```sql
INSERT INTO elo_dialogs (tenant_id, client_id, channel_id, status)
VALUES (
  '{{ $json.tenant_id }}',
  '{{ $json.client_id }}',
  {{ $json.channel_id }},
  'active'
)
RETURNING id as dialog_id;

-- Note: vertical_id = NULL, Core determines later
```

---

### 14. Build Output

```javascript
const input = $('Validate Input').first().json;
const tenant = $('Tenant Resolver').first().json;
const client = $('Client Resolver').first()?.json || $('Create Client').first()?.json;
const dialog = $('Dialog Resolver').first()?.json || $('Create Dialog').first()?.json;

return {
  tenant_id: tenant.tenant_id,
  domain_id: tenant.domain_id,
  client_id: client.client_id,
  dialog_id: dialog.dialog_id,
  channel_id: tenant.channel_id,
  external_chat_id: input.external_chat_id,
  text: input.text,
  timestamp: input.timestamp,
  message_ids: input.message_ids,
  trace_id: input.trace_id,
  media: input.media || {},
  meta: {
    ...input.meta,
    is_new_client: !$('Client Resolver').first()?.json?.client_id,
    is_new_dialog: !$('Dialog Resolver').first()?.json?.dialog_id
  },
  client: {
    id: client.client_id,
    name: client.name || input.client_name,
    phone: client.phone || input.client_phone
  }
};
```

---

### 15. Forward to Core (HTTP Request)

| Parameter | Value |
|----------|----------|
| **Type** | HTTP Request |
| **URL** | `https://n8n.n8nsrv.ru/webhook/elo-core-ingest` |
| **Method** | POST |
| **Async** | Fire-and-forget (no wait) |

---

### 16. Respond to Input Contour

```javascript
const output = $('Build Output').first().json;

return {
  accepted: true,
  tenant_id: output.tenant_id,
  client_id: output.client_id,
  dialog_id: output.dialog_id,
  trace_id: output.trace_id
};
```

---

## Flow Diagram

```
Webhook POST /webhook/elo-client-resolve
         │
         ▼
┌─────────────────────┐
│  1. Validate Input  │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ 2. Tenant Resolver  │  ← elo_tenants + elo_channel_accounts
└──────────┬──────────┘
           │
     Found? ───NO──→ DLQ (Unknown Tenant)
           │
          YES
           ▼
┌─────────────────────┐
│ 3. Client Resolver  │  ← elo_clients + elo_client_channels
└──────────┬──────────┘
           │
     Found? ───NO──→ Find by Phone ──NO──→ Create Client → Neo4j Sync
           │                │
          YES             YES (link channel)
           │               │
           ▼◄──────────────┘
┌─────────────────────┐
│ 4. Dialog Resolver  │  ← elo_dialogs
└──────────┬──────────┘
           │
     Found? ───NO──→ Create Dialog
           │            │
          YES           │
           │            │
           ▼◄───────────┘
┌─────────────────────┐
│ 5. Build Output     │
└──────────┬──────────┘
           │
           ├──→ Forward to Core (async)
           │
           ▼
    Respond to Input Contour
```

---

## Error Handling

| Error | Action |
|-------|--------|
| Missing required field | Return 400, log error |
| Unknown tenant | Add to DLQ, return 404 |
| DB connection error | Return 500, log error |
| Neo4j sync fails | Log warning, continue |
| Core forward fails | Log warning, continue |

**Polygon mode:** All errors logged, no retries.

---

## Database Tables (elo_ prefix)

| Table | Purpose |
|-------|---------|
| `elo_tenants` | Tenant registry |
| `elo_channel_accounts` | Channel credentials |
| `elo_clients` | Client registry |
| `elo_client_channels` | Client-channel links |
| `elo_dialogs` | Active dialogs |

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Service | Graph Tool (8773) | Neo4j sync |
| Workflow | ELO_Core_Ingest | Core processing |
| Database | PostgreSQL | Identity storage |
| Database | Redis | DLQ storage |

---

## Configuration

```env
# PostgreSQL
DATABASE_URL=postgresql://app_user:***@185.221.214.83:6544/postgres

# Graph Tool
GRAPH_TOOL_URL=http://45.144.177.128:8773

# Core
CORE_URL=https://n8n.n8nsrv.ru/webhook/elo-core-ingest

# Redis (for DLQ)
REDIS_URL=redis://:password@redis:6379/0
```

---

**Document:** ELO_Client_Resolve.md
**Date:** 2025-12-11
**Author:** Claude
**Status:** n8n polygon (for debugging before MCP port)
