# API Contracts — Inter-Contour Communication

> Complete reference for all APIs and Webhooks between ELO contours

**Date:** 2025-12-11
**Author:** Dmitry + Claude

---

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              MCP CHANNEL ADAPTERS                                │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐              │
│  │ Telegram │ │ WhatsApp │ │  Avito   │ │    VK    │ │   MAX    │              │
│  │  :8767   │ │  :8766   │ │  :8765   │ │  :8767   │ │  :8768   │              │
│  │ Finnish  │ │ Finnish  │ │   RU     │ │   RU     │ │   RU     │              │
│  └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘              │
│       └───────────────────┬─────────────────────────────────┘                   │
│                           │                                                      │
│                           ▼  HTTP POST (normalized message)                     │
└───────────────────────────│─────────────────────────────────────────────────────┘
                            │
┌───────────────────────────│─────────────────────────────────────────────────────┐
│  n8n Channel IN           │                                                      │
│  ┌────────────────────────▼────────────────────────────┐                        │
│  │  ELO_In_Telegram, ELO_In_WhatsApp, etc.             │                        │
│  │  • Voice transcription (OpenAI Whisper)             │                        │
│  │  • Normalize to ELO Core Contract                   │                        │
│  └────────────────────────┬────────────────────────────┘                        │
│                           │                                                      │
│                           ▼  POST /ingest                                       │
└───────────────────────────│─────────────────────────────────────────────────────┘
                            │
┌───────────────────────────│─────────────────────────────────────────────────────┐
│  INPUT CONTOUR (MCP:8771) │                                                      │
│  ┌────────────────────────▼────────────────────────────┐                        │
│  │  /ingest endpoint                                    │                        │
│  │  • Idempotency check                                 │                        │
│  │  • Queue to Redis                                    │                        │
│  │  • Debounce (10s silence / 300s max)                │                        │
│  │  • Merge messages                                    │                        │
│  └────────────────────────┬────────────────────────────┘                        │
│                           │                                                      │
│                           ▼  POST /resolve                                      │
└───────────────────────────│─────────────────────────────────────────────────────┘
                            │
┌───────────────────────────│─────────────────────────────────────────────────────┐
│  CLIENT CONTOUR (MCP:8772)│                                                      │
│  ┌────────────────────────▼────────────────────────────┐                        │
│  │  /resolve endpoint                                   │                        │
│  │  • Tenant Resolver (by credential)                  │                        │
│  │  • Client Resolver (find/create)                    │                        │
│  │  • Dialog Resolver (find/create)                    │                        │
│  └────────────────────────┬────────────────────────────┘                        │
│                           │                                                      │
│                           ▼  POST /webhook/elo-core-ingest                      │
└───────────────────────────│─────────────────────────────────────────────────────┘
                            │
┌───────────────────────────│─────────────────────────────────────────────────────┐
│  CORE CONTOUR (n8n)       │                                                      │
│  ┌────────────────────────▼────────────────────────────┐                        │
│  │  /webhook/elo-core-ingest                           │                        │
│  │  • Context Builder                                   │                        │
│  │  • AI Orchestrator                                   │                        │
│  │  • Dialog Engine                                     │                        │
│  └────────────────────────┬────────────────────────────┘                        │
│                           │                                                      │
│                           ├──▶ POST /query (Graph Tool :8773)                   │
│                           │                                                      │
│                           ▼  POST /webhook/elo-out-{channel}                    │
└───────────────────────────│─────────────────────────────────────────────────────┘
                            │
┌───────────────────────────│─────────────────────────────────────────────────────┐
│  n8n Channel OUT          │                                                      │
│  ┌────────────────────────▼────────────────────────────┐                        │
│  │  ELO_Out_Telegram, ELO_Out_WhatsApp, etc.           │                        │
│  │  • Response Builder (format for channel)            │                        │
│  │  • Call MCP adapter to send                          │                        │
│  └────────────────────────┬────────────────────────────┘                        │
│                           │                                                      │
│                           ▼  POST /send                                         │
└───────────────────────────│─────────────────────────────────────────────────────┘
                            │
                            ▼
                     MCP Channel Adapters (send to messengers)
```

---

## 1. MCP Adapters → n8n Channel IN

### Telegram (Finnish Server)

| Parameter | Value |
|-----------|-------|
| **From** | mcp-telegram (217.145.79.27:8767) |
| **To** | n8n `POST /telegram-in` |
| **Method** | POST |
| **Content-Type** | application/json |

**Request:**
```json
{
  "chat_id": "123456789",
  "user_id": "987654321",
  "text": "Message text",
  "message_id": "54321",
  "timestamp": "2024-12-10T10:30:00Z",
  "bot_token": "123456:ABC-DEF...",
  "first_name": "Ivan",
  "last_name": "Petrov",
  "username": "ivanpetrov",
  "attachments": [
    {"type": "voice", "file_id": "AwACAgIA...", "duration": 5},
    {"type": "photo", "file_id": "AgACAgIA..."}
  ],
  "is_callback": false,
  "callback_data": null,
  "raw_data": {}
}
```

**Response:**
```json
{
  "success": true,
  "queued": true
}
```

### WhatsApp (Finnish Server)

| Parameter | Value |
|-----------|-------|
| **From** | mcp-whatsapp (217.145.79.27:8766) |
| **To** | n8n `POST /whatsapp-in` |

**Request:**
```json
{
  "chat_id": "79991234567",
  "user_id": "79991234567",
  "text": "Message text",
  "message_id": "wamid.xxx",
  "timestamp": "2024-12-10T10:30:00Z",
  "profile_id": "wappi_profile_123",
  "sender_name": "Ivan Petrov",
  "attachments": [],
  "raw_data": {}
}
```

### Avito (RU Server)

| Parameter | Value |
|-----------|-------|
| **From** | mcp-avito (45.144.177.128:8765) |
| **To** | n8n `POST /avito-in` |

**Request:**
```json
{
  "chat_id": "avito_chat_123",
  "user_id": "avito_user_456",
  "text": "Message text",
  "message_id": "msg_789",
  "timestamp": "2024-12-10T10:30:00Z",
  "user_id_credential": "avito_client_id",
  "sender_name": "Ivan P.",
  "item_id": "12345678",
  "item_title": "iPhone 14 Pro",
  "raw_data": {}
}
```

### VK (RU Server)

| Parameter | Value |
|-----------|-------|
| **From** | mcp-vk (45.144.177.128:8767) |
| **To** | n8n `POST /vk-in` |

**Request:**
```json
{
  "chat_id": "vk_123456",
  "user_id": "vk_789012",
  "text": "Message text",
  "message_id": "12345",
  "timestamp": "2024-12-10T10:30:00Z",
  "group_id": "123456789",
  "sender_name": "Ivan Petrov",
  "attachments": [],
  "raw_data": {}
}
```

### MAX (RU Server)

| Parameter | Value |
|-----------|-------|
| **From** | mcp-max (45.144.177.128:8768) |
| **To** | n8n `POST /max-in` |

**Request:**
```json
{
  "chat_id": "max_chat_123",
  "user_id": "max_user_456",
  "text": "Message text",
  "message_id": "msg_789",
  "timestamp": "2024-12-10T10:30:00Z",
  "bot_id": "max_bot_id",
  "sender_name": "Ivan Petrov",
  "raw_data": {}
}
```

---

## 2. n8n Channel IN → Input Contour

### POST /ingest

| Parameter | Value |
|-----------|-------|
| **From** | n8n ELO_In_* workflows |
| **To** | Input Contour MCP (45.144.177.128:8771) |
| **n8n Polygon** | `https://n8n.n8nsrv.ru/webhook/elo-input-ingest` |
| **Method** | POST |
| **Content-Type** | application/json |
| **Timeout** | 10s (fast response expected) |

**Request (ELO Core Contract):**
```json
{
  "channel": "telegram",
  "bot_token": "123456:ABC...",
  "external_user_id": "tg_987654321",
  "external_chat_id": "tg_123456789",
  "text": "Message text (with voice transcription if any)",
  "timestamp": "2024-12-10T10:30:00Z",
  "client_phone": "+79991234567",
  "client_name": "Ivan Petrov",
  "message_id": "msg_550e8400-...",
  "trace_id": "trace_xyz789",
  "media": {
    "has_voice": true,
    "voice_transcribed_text": "transcription",
    "has_images": false,
    "images": []
  },
  "meta": {
    "external_message_id": "54321",
    "provider": "mcp-telegram"
  }
}
```

**Response (immediate, ~10ms):**
```json
{
  "accepted": true,
  "message_id": "msg_550e8400-...",
  "trace_id": "trace_xyz789"
}
```

**Error Response:**
```json
{
  "accepted": false,
  "reason": "duplicate",
  "message_id": "msg_550e8400-...",
  "trace_id": "trace_xyz789"
}
```

---

## 3. Input Contour → Client Contour

### POST /resolve

| Parameter | Value |
|-----------|-------|
| **From** | Input Contour MCP (8771) |
| **To** | Client Contour MCP (45.144.177.128:8772) |
| **n8n Polygon** | `https://n8n.n8nsrv.ru/webhook/elo-client-resolve` |
| **Method** | POST |
| **Content-Type** | application/json |
| **Timeout** | 30s |

**Request (batched message):**
```json
{
  "channel": "telegram",
  "bot_token": "123456:ABC...",
  "external_user_id": "tg_987654321",
  "external_chat_id": "tg_123456789",
  "client_name": "Ivan Petrov",
  "client_phone": "+79991234567",
  "text": "Merged message text\n\nSecond message\n\nThird message",
  "timestamp": "2024-12-10T10:00:15Z",
  "message_ids": ["msg_1", "msg_2", "msg_3"],
  "trace_id": "trace_xyz789",
  "media": {},
  "meta": {
    "batched": true,
    "batch_size": 3,
    "batch_reason": "debounce"
  }
}
```

**Response:**
```json
{
  "accepted": true,
  "tenant_id": "550e8400-e29b-41d4-a716-446655440000",
  "client_id": "660f9500-f39c-42e5-b827-557766550111",
  "dialog_id": "770a0600-a40d-43f6-c938-668877660222",
  "trace_id": "trace_xyz789"
}
```

**Error Response (unknown tenant):**
```json
{
  "accepted": false,
  "reason": "unknown_tenant",
  "trace_id": "trace_xyz789"
}
```

---

## 4. Client Contour → Core

### POST /webhook/elo-core-ingest

| Parameter | Value |
|-----------|-------|
| **From** | Client Contour MCP (8772) |
| **To** | n8n Core `https://n8n.n8nsrv.ru/webhook/elo-core-ingest` |
| **Method** | POST |
| **Content-Type** | application/json |
| **Mode** | Fire-and-forget (async) |

**Request:**
```json
{
  "tenant_id": "550e8400-e29b-41d4-a716-446655440000",
  "domain_id": 1,
  "client_id": "660f9500-f39c-42e5-b827-557766550111",
  "dialog_id": "770a0600-a40d-43f6-c938-668877660222",
  "channel_id": 1,
  "external_chat_id": "tg_123456789",
  "text": "Merged message text",
  "timestamp": "2024-12-10T10:00:15Z",
  "message_ids": ["msg_1", "msg_2", "msg_3"],
  "trace_id": "trace_xyz789",
  "media": {},
  "meta": {
    "batched": true,
    "batch_size": 3,
    "batch_reason": "debounce",
    "is_new_client": false,
    "is_new_dialog": false
  },
  "client": {
    "id": "660f9500-f39c-42e5-b827-557766550111",
    "name": "Ivan Petrov",
    "phone": "+79991234567"
  }
}
```

**Response:**
```json
{
  "received": true,
  "trace_id": "trace_xyz789"
}
```

---

## 5. Core → Graph Tool

### POST /query

| Parameter | Value |
|-----------|-------|
| **From** | n8n Core workflows |
| **To** | Graph Tool MCP (45.144.177.128:8773) |
| **n8n Polygon** | `https://n8n.n8nsrv.ru/webhook/elo-graph-query` |
| **Method** | POST |
| **Content-Type** | application/json |
| **Timeout** | 30s |

**Request:**
```json
{
  "query_code": "get_client_context",
  "params": {
    "client_id": "660f9500-f39c-42e5-b827-557766550111"
  }
}
```

**Response (success):**
```json
{
  "success": true,
  "data": [
    {
      "client": {
        "pg_id": "660f9500-...",
        "name": "Ivan Petrov",
        "phone": "+79991234567"
      },
      "devices": [...],
      "issues": [...],
      "traits": [...]
    }
  ],
  "execution_time_ms": 45,
  "trace_id": "trace_xyz789"
}
```

**Response (error):**
```json
{
  "success": false,
  "error": "Query not found: invalid_code",
  "execution_time_ms": 5
}
```

### Available Query Codes

| Code | Purpose | Required Params |
|------|---------|-----------------|
| `get_client_context` | Full client context | `client_id` |
| `create_client` | Create client node | `client_id`, `tenant_id`, `name`, `phone` |
| `create_device` | Create device | `device_id`, `client_id`, `brand`, `model` |
| `create_issue` | Create issue | `issue_id`, `device_id`, `tenant_id`, `vertical_id` |
| `add_symptom` | Add symptom | `symptom_id`, `issue_id`, `text`, `symptom_type_id` |
| `add_diagnosis` | Add diagnosis | `diagnosis_id`, `issue_id`, `text`, `diagnosis_type_id` |
| `add_repair` | Add repair | `repair_id`, `issue_id`, `text`, `repair_action_id`, `cost` |
| `add_client_trait` | Add trait | `client_id`, `type`, `value`, `confidence` |
| `get_device_history` | Device history | `device_id` |
| `find_similar_cases` | Similar diagnoses | `vertical_id`, `symptoms` |

---

## 6. Core → Channel OUT

### POST /webhook/elo-out-{channel}

| Parameter | Value |
|-----------|-------|
| **From** | n8n Core (Dialog Engine) |
| **To** | n8n Channel OUT workflows |
| **Method** | POST |
| **Content-Type** | application/json |

**Channels:**
- `POST /webhook/elo-out-telegram`
- `POST /webhook/elo-out-whatsapp`
- `POST /webhook/elo-out-avito`
- `POST /webhook/elo-out-vk`
- `POST /webhook/elo-out-max`

**Request:**
```json
{
  "tenant_id": "550e8400-...",
  "dialog_id": "770a0600-...",
  "channel_id": 1,
  "external_chat_id": "tg_123456789",
  "credential": "123456:ABC...",
  "message": {
    "text": "Response text with **markdown**",
    "buttons": [
      {"text": "Yes", "callback_data": "yes"},
      {"text": "No", "callback_data": "no"}
    ],
    "attachments": [
      {"type": "image", "url": "https://..."}
    ]
  },
  "trace_id": "trace_xyz789"
}
```

**Response:**
```json
{
  "sent": true,
  "message_id": "external_msg_12345",
  "trace_id": "trace_xyz789"
}
```

---

## 7. Channel OUT → MCP Adapters

### Telegram: POST /send

| Parameter | Value |
|-----------|-------|
| **From** | n8n ELO_Out_Telegram |
| **To** | mcp-telegram (217.145.79.27:8767) |
| **Method** | POST |

**Request:**
```json
{
  "bot_token": "123456:ABC...",
  "chat_id": "123456789",
  "text": "Response text",
  "parse_mode": "HTML",
  "reply_markup": {
    "inline_keyboard": [[
      {"text": "Yes", "callback_data": "yes"},
      {"text": "No", "callback_data": "no"}
    ]]
  }
}
```

### WhatsApp: POST /send

| Parameter | Value |
|-----------|-------|
| **From** | n8n ELO_Out_WhatsApp |
| **To** | mcp-whatsapp (217.145.79.27:8766) |

**Request:**
```json
{
  "profile_id": "wappi_profile_123",
  "chat_id": "79991234567",
  "text": "Response text",
  "quick_replies": [
    {"title": "Yes"},
    {"title": "No"}
  ]
}
```

### Avito: POST /send

| Parameter | Value |
|-----------|-------|
| **From** | n8n ELO_Out_Avito |
| **To** | mcp-avito (45.144.177.128:8765) |

**Request:**
```json
{
  "user_id": "avito_client_id",
  "chat_id": "avito_chat_123",
  "text": "Response text (plain, max 2000 chars)"
}
```

### VK: POST /send

| Parameter | Value |
|-----------|-------|
| **From** | n8n ELO_Out_VK |
| **To** | mcp-vk (45.144.177.128:8767) |

**Request:**
```json
{
  "group_id": "123456789",
  "user_id": "vk_789012",
  "text": "Response text",
  "keyboard": {
    "buttons": [[
      {"action": {"type": "text", "label": "Yes"}}
    ]]
  }
}
```

### MAX: POST /send

| Parameter | Value |
|-----------|-------|
| **From** | n8n ELO_Out_MAX |
| **To** | mcp-max (45.144.177.128:8768) |

**Request:**
```json
{
  "bot_id": "max_bot_id",
  "chat_id": "max_chat_123",
  "text": "Response text",
  "buttons": [
    {"text": "Yes", "callback_data": "yes"}
  ]
}
```

---

## 8. Health Check Endpoints

| Service | Endpoint | Expected Response |
|---------|----------|-------------------|
| Input Contour | `GET http://45.144.177.128:8771/health` | `{"status":"ok","redis":true}` |
| Client Contour | `GET http://45.144.177.128:8772/health` | `{"status":"ok","postgres":true}` |
| Graph Tool | `GET http://45.144.177.128:8773/health` | `{"status":"ok","postgres":true,"neo4j":true}` |
| mcp-telegram | `GET http://217.145.79.27:8767/health` | `{"status":"ok"}` |
| mcp-whatsapp | `GET http://217.145.79.27:8766/health` | `{"status":"ok"}` |
| mcp-avito | `GET http://45.144.177.128:8765/health` | `{"status":"ok"}` |
| mcp-vk | `GET http://45.144.177.128:8767/health` | `{"status":"ok"}` |
| mcp-max | `GET http://45.144.177.128:8768/health` | `{"status":"ok"}` |

---

## 9. DLQ (Dead Letter Queue) Endpoints

### Input Contour DLQ

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `GET /dlq` | GET | Get failed messages |
| `DELETE /dlq` | DELETE | Clear DLQ |

**Redis Key:** `dlq:input_contour`

### Client Contour DLQ

| Endpoint | Method | Purpose |
|----------|--------|---------|
| `GET /dlq` | GET | Get unknown tenant errors |
| `DELETE /dlq` | DELETE | Clear DLQ |

**Redis Key:** `dlq:unknown_tenant`

---

## 10. n8n Polygon Webhooks Summary

| Workflow | Path | Purpose |
|----------|------|---------|
| ELO_Input_Ingest | `/webhook/elo-input-ingest` | Emulates MCP 8771 /ingest |
| ELO_Input_Worker | (Schedule trigger) | Emulates MCP 8771 worker |
| ELO_Client_Resolve | `/webhook/elo-client-resolve` | Emulates MCP 8772 /resolve |
| ELO_Graph_Query | `/webhook/elo-graph-query` | Proxy to Graph Tool 8773 |
| ELO_Core_Ingest | `/webhook/elo-core-ingest` | Core entry point |
| ELO_Out_Telegram | `/webhook/elo-out-telegram` | Send to Telegram |
| ELO_Out_WhatsApp | `/webhook/elo-out-whatsapp` | Send to WhatsApp |
| ELO_Out_Avito | `/webhook/elo-out-avito` | Send to Avito |
| ELO_Out_VK | `/webhook/elo-out-vk` | Send to VK |
| ELO_Out_MAX | `/webhook/elo-out-max` | Send to MAX |

**Base URL:** `https://n8n.n8nsrv.ru`

---

## 11. Credential Mapping

| Channel | Credential Field | Source |
|---------|------------------|--------|
| telegram | `bot_token` | Full Telegram bot token |
| whatsapp | `profile_id` | Wappi.pro profile ID |
| vk | `group_id` | VK community ID |
| avito | `user_id` | Avito user/client ID |
| max | `bot_id` | MAX (VK Teams) bot ID |

---

## 12. Error Codes

| Code | Meaning | Action |
|------|---------|--------|
| 200 | Success | Continue |
| 400 | Bad Request | Check request format |
| 404 | Not Found (unknown tenant) | Check credentials |
| 409 | Conflict (duplicate) | Ignore, already processed |
| 500 | Internal Error | Retry with backoff |
| 502 | Bad Gateway | Service unavailable, retry |
| 503 | Service Unavailable | Service starting, retry |
| 504 | Gateway Timeout | Timeout, retry |

---

**Document:** API_CONTRACTS.md
**Date:** 2025-12-11
**Author:** Dmitry + Claude
**Status:** Complete
