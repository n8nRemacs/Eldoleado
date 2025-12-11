# ELO_Core_Ingest

> Main entry point — receives messages from Client Contour, orchestrates processing

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `NEW` (to be created) |
| **File** | `NEW/workflows/ELO_Core/ELO_Core_Ingest.json` |
| **Trigger** | Webhook POST `/webhook/elo-core-ingest` |
| **Called from** | Client Contour (MCP:8772) |
| **Calls** | → Context Builder → AI Extractor → Graph Writer → Response Builder |

---

## Purpose

Main orchestrator for Core Contour:
1. Receives enriched message from Client Contour
2. Loads context (PostgreSQL + Neo4j)
3. Calls AI for extraction
4. Writes to graph
5. Sends response (stub in MVP v0)

---

## Input Data

**From Client Contour:**
```json
{
  "tenant_id": "uuid",
  "domain_id": 1,
  "client_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "external_chat_id": "123456789",
  "text": "Привет, сколько стоит поменять дисплей на iPhone 14 Pro?",
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

---

## Output Data

**To Channel OUT (via Response Builder):**
```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "external_chat_id": "123456789",
  "message": {
    "text": "Спасибо за обращение! Данные записаны.",
    "buttons": []
  },
  "trace_id": "trace_xyz789"
}
```

**MVP v0:** Stub response only. Manual verification in Neo4j Browser.

---

## Nodes

### 1. Webhook Trigger

| Parameter | Value |
|----------|----------|
| **Type** | Webhook |
| **Path** | `/webhook/elo-core-ingest` |
| **Method** | POST |
| **Response** | Immediately (async processing) |

---

### 2. Validate Input

```javascript
const input = $input.first().json;

// Required fields
const required = ['tenant_id', 'client_id', 'dialog_id', 'text'];
for (const field of required) {
  if (!input[field]) {
    throw new Error(`Missing required field: ${field}`);
  }
}

return {
  ...input,
  received_at: new Date().toISOString()
};
```

---

### 3. → Context Builder

| Parameter | Value |
|----------|----------|
| **Type** | Execute Workflow |
| **Workflow** | ELO_Core_Context_Builder |

Passes: `tenant_id`, `client_id`, `dialog_id`

---

### 4. → AI Extractor

| Parameter | Value |
|----------|----------|
| **Type** | Execute Workflow |
| **Workflow** | ELO_Core_AI_Extractor |

Passes: `text`, `context` (from Context Builder)

---

### 5. → Graph Writer

| Parameter | Value |
|----------|----------|
| **Type** | Execute Workflow |
| **Workflow** | ELO_Core_Graph_Writer |

Passes: `extractions` (from AI Extractor)

---

### 6. → Response Builder

| Parameter | Value |
|----------|----------|
| **Type** | Execute Workflow |
| **Workflow** | ELO_Core_Response_Builder |

Passes: `dialog_id`, `channel_id`, `external_chat_id`

---

## Flow Diagram

```
Webhook POST /webhook/elo-core-ingest
         │
         ▼
┌─────────────────────┐
│  1. Validate Input  │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  2. Context Builder │  ← Load from PostgreSQL + Neo4j
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  3. AI Extractor    │  ← Qwen3-30B via OpenRouter
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  4. Graph Writer    │  ← Write to Neo4j via Graph Tool
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│  5. Response Builder│  ← Stub response (MVP v0)
└──────────┬──────────┘
           │
           ▼
      Channel OUT
```

---

## Error Handling

| Error | Action |
|-------|--------|
| Missing required field | Return 400, log error |
| Context Builder fails | Log, continue with empty context |
| AI Extractor fails | Log, skip graph write |
| Graph Writer fails | Log, continue to response |
| Response Builder fails | Log error |

**MVP v0:** Log errors, don't retry. Manual investigation.

---

## Dependencies

| Type | ID | Purpose |
|------|-----|---------|
| Workflow | NEW | ELO_Core_Context_Builder |
| Workflow | NEW | ELO_Core_AI_Extractor |
| Workflow | NEW | ELO_Core_Graph_Writer |
| Workflow | NEW | ELO_Core_Response_Builder |
