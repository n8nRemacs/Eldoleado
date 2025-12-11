# ELO_Input_Ingest

> n8n Polygon — Message ingestion endpoint (emulates MCP Input Contour /ingest)

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `NEW` (to be created) |
| **File** | `NEW/workflows/ELO_Input/ELO_Input_Ingest.json` |
| **Trigger** | Webhook POST `/webhook/elo-input-ingest` |
| **Called from** | Channel IN workflows (Telegram, WhatsApp, Avito, VK, MAX) |
| **Calls** | Redis (queue storage) |

---

## Purpose

n8n polygon for debugging Input Contour ingestion before MCP deployment:

1. Receive message from channel
2. Generate message_id and trace_id
3. Idempotency check (Redis)
4. Push to Redis queue for async processing
5. Return immediately (~10ms response)

**MCP equivalent:** `POST http://45.144.177.128:8771/ingest`

---

## Architecture

```
Channel IN (Telegram, WhatsApp, etc.)
         │
         │ POST /webhook/elo-input-ingest
         ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  ELO_Input_Ingest (n8n Webhook)                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│  1. Generate message_id, trace_id                                           │
│  2. Idempotency check (Redis SETNX)                                         │
│  3. Push to Redis queue:incoming (RPUSH)                                    │
│  4. Return {accepted: true} immediately                                     │
└───────────────────────────────────────────────────────────────────────────┬─┘
                                                                            │
         Async processing by ELO_Input_Worker                               │
         (separate workflow, schedule trigger)                              │
                                                                            ▼
                                                               Redis: queue:incoming
```

**Note:** This workflow returns immediately. Actual processing is done by `ELO_Input_Worker`.

---

## Input Data

**From Channel IN:**
```json
{
  "channel": "telegram",
  "external_user_id": "987654321",
  "external_chat_id": "123456789",
  "text": "Привет, разбил экран iPhone",
  "timestamp": "2024-12-10T10:00:01Z",
  "client_phone": "+79991234567",
  "client_name": "Ivan Petrov",
  "bot_token": "123456:ABC...",
  "media": {},
  "meta": {}
}
```

---

## Output Data

**Response to Channel IN (immediate):**
```json
{
  "accepted": true,
  "message_id": "msg_550e8400-e29b-41d4-a716-446655440000",
  "trace_id": "trace_xyz789"
}
```

**To Redis queue:incoming:**
```json
{
  "channel": "telegram",
  "external_user_id": "987654321",
  "external_chat_id": "123456789",
  "text": "Привет, разбил экран iPhone",
  "timestamp": "2024-12-10T10:00:01Z",
  "client_phone": "+79991234567",
  "client_name": "Ivan Petrov",
  "bot_token": "123456:ABC...",
  "media": {},
  "meta": {},
  "message_id": "msg_550e8400-...",
  "trace_id": "trace_xyz789"
}
```

---

## Nodes

### 1. Webhook Trigger

| Parameter | Value |
|----------|----------|
| **Type** | Webhook |
| **typeVersion** | 2 |
| **Path** | `/webhook/elo-input-ingest` |
| **Method** | POST |
| **Response** | responseNode |

---

### 2. Prepare Message

```javascript
// v2 compatible - no process.env
const input = $input.first().json;

// Generate IDs if not provided
const message_id = input.message_id || `msg_${crypto.randomUUID()}`;
const trace_id = input.trace_id || `trace_${Date.now()}_${Math.random().toString(36).substr(2, 9)}`;

return {
  ...input,
  message_id: message_id,
  trace_id: trace_id,
  received_at: new Date().toISOString()
};
```

---

### 3. Idempotency Check (Redis SETNX)

| Parameter | Value |
|----------|----------|
| **Type** | Redis |
| **Operation** | Set |
| **Key** | `idempotency:message:{{ $json.message_id }}` |
| **Value** | `1` |
| **Options** | `{ "nx": true, "ex": 3600 }` |

**Logic:**
- If key doesn't exist → sets it, returns OK
- If key exists → returns null (duplicate)

---

### 4. Is Duplicate?

| Condition | Result |
|---------|-----------|
| Redis returned null | → Return Duplicate Response |
| Redis returned OK | → Push to Queue |

---

### 5. Return Duplicate Response

```javascript
const input = $('Prepare Message').first().json;

return {
  accepted: false,
  reason: "duplicate",
  message_id: input.message_id,
  trace_id: input.trace_id
};
```

Then: Respond to Webhook

---

### 6. Push to Queue (Redis RPUSH)

| Parameter | Value |
|----------|----------|
| **Type** | Redis |
| **Operation** | Push |
| **List** | `queue:incoming` |
| **Value** | `{{ JSON.stringify($json) }}` |

---

### 7. Respond Success

```javascript
const input = $('Prepare Message').first().json;

return {
  accepted: true,
  message_id: input.message_id,
  trace_id: input.trace_id
};
```

---

### 8. Respond to Webhook

| Parameter | Value |
|----------|----------|
| **Type** | Respond to Webhook |
| **typeVersion** | 1.1 |
| **Response** | `{{ $json }}` |

---

## Flow Diagram

```
Webhook POST /webhook/elo-input-ingest
         │
         ▼
┌─────────────────────┐
│ 1. Prepare Message  │  ← Generate message_id, trace_id
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ 2. Idempotency      │  ← Redis SETNX idempotency:message:{id}
│    Check            │
└──────────┬──────────┘
           │
     Is Duplicate?
           │
    ├── YES → Return {accepted: false, reason: "duplicate"}
    │
    └── NO
           │
           ▼
┌─────────────────────┐
│ 3. Push to Queue    │  ← Redis RPUSH queue:incoming
└──────────┬──────────┘
           │
           ▼
    Return {accepted: true, message_id, trace_id}
```

---

## Redis Keys

| Key | Type | TTL | Purpose |
|-----|------|-----|---------|
| `idempotency:message:{id}` | String | 1 hour | Duplicate detection |
| `queue:incoming` | List | — | Global incoming queue |

---

## Error Handling

| Error | Action |
|-------|--------|
| Redis unavailable | Return 503 |
| Invalid JSON | Return 400 |
| Missing required fields | Return 400 |

---

## Credentials

| Credential | Purpose |
|------------|---------|
| `ELO_Redis` | Redis connection for queue and idempotency |

**Redis URL:** `redis://:password@45.144.177.128:6379/0`

---

## n8n v2.0 Compliance

| Check | Status |
|-------|--------|
| No Python Code Node | ✅ |
| No `process.env` | ✅ |
| Webhook typeVersion: 2 | ✅ |
| Code typeVersion: 2 | ✅ |
| Redis uses Credentials | ✅ |

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Service | Redis | Queue storage |
| Workflow | ELO_Input_Worker | Async processing |

---

## Comparison with MCP

| Feature | MCP (8771) | n8n Polygon |
|---------|------------|-------------|
| Endpoint | `/ingest` | `/webhook/elo-input-ingest` |
| Idempotency | Redis SETNX | Redis SETNX |
| Queue | RPUSH queue:incoming | RPUSH queue:incoming |
| Response | Immediate | Immediate |
| Processing | Python asyncio worker | n8n Schedule workflow |

**Contract is identical. Implementation differs.**

---

**Document:** ELO_Input_Ingest.md
**Date:** 2025-12-11
**Author:** Claude
**Status:** n8n polygon (for debugging before MCP deployment)
