# Block 5: Output

**Version:** 1.0
**Date:** 2026-01-04
**Status:** Development
**Orchestrator:** ELO_Out_Router

---

## Purpose

Block 5 sends the generated response to the client via the appropriate channel:
1. Routes message to correct channel handler
2. Formats message for channel-specific API
3. Sends message via channel API
4. Logs message in database
5. Returns delivery status

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                            BLOCK 5: Output                                   │
│                         Orchestrator: ELO_Out_Router                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────┐                                                            │
│  │   INPUT     │  Webhook: /elo-out-router                                  │
│  │  (Block 4)  │                                                            │
│  └──────┬──────┘                                                            │
│         │                                                                    │
│         ▼                                                                    │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                         ELO_Out_Router                               │   │
│  │                                                                       │   │
│  │  ┌─────────────┐    ┌─────────────┐                                 │   │
│  │  │  Validate   │───►│   Check     │                                 │   │
│  │  │   Input     │    │  ready_to_  │                                 │   │
│  │  │             │    │   send      │                                 │   │
│  │  └─────────────┘    └──────┬──────┘                                 │   │
│  │                            │                                         │   │
│  │         ┌──────────────────┼──────────────────────────────┐          │   │
│  │         │                  │                              │          │   │
│  │         ▼                  ▼                              ▼          │   │
│  │  ┌──────────────┐  ┌──────────────┐               ┌──────────────┐  │   │
│  │  │ ELO_Out_     │  │ ELO_Out_     │      ...      │ ELO_Out_     │  │   │
│  │  │ Telegram     │  │ WhatsApp     │               │ MAX          │  │   │
│  │  └──────┬───────┘  └──────┬───────┘               └──────┬───────┘  │   │
│  │         │                  │                              │          │   │
│  │         └──────────────────┼──────────────────────────────┘          │   │
│  │                            │                                         │   │
│  │                            ▼                                         │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐              │   │
│  │  │   Log       │───►│   Build     │───►│   Respond   │              │   │
│  │  │  Message    │    │   Result    │    │             │              │   │
│  │  └─────────────┘    └─────────────┘    └─────────────┘              │   │
│  │                                                                       │   │
│  └───────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
│  ┌─────────────┐                                                            │
│  │   OUTPUT    │  Response to caller                                        │
│  │   (Final)   │                                                            │
│  └─────────────┘                                                            │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Input Schema

**Webhook:** `POST /elo-out-router`
**Source:** Block 4 (ELO_Executor)

```json
{
  "tenant_id": {
    "type": "string (uuid)",
    "required": true,
    "description": "Tenant identifier"
  },
  "dialog_id": {
    "type": "string (uuid)",
    "required": true,
    "description": "Dialog identifier"
  },
  "client_id": {
    "type": "string (uuid)",
    "required": true,
    "description": "Client identifier"
  },
  "channel": {
    "type": "string",
    "required": true,
    "enum": ["telegram", "whatsapp", "avito", "vk", "max"],
    "description": "Channel code for routing"
  },
  "channel_id": {
    "type": "integer",
    "required": true,
    "description": "Channel type ID"
  },
  "channel_account_id": {
    "type": "string (uuid)",
    "required": true,
    "description": "Channel account identifier"
  },
  "external_chat_id": {
    "type": "string",
    "required": true,
    "description": "External chat/conversation identifier"
  },
  "response": {
    "type": "object",
    "required": true,
    "description": "Response to send",
    "schema": {
      "text": "string|null",
      "buttons": [
        {
          "text": "string",
          "callback_data": "string|null",
          "url": "string|null"
        }
      ],
      "attachments": [
        {
          "type": "string (photo|document|video|voice)",
          "url": "string",
          "file_id": "string|null",
          "caption": "string|null"
        }
      ],
      "keyboard": {
        "buttons": "array of arrays",
        "one_time": "boolean",
        "resize": "boolean"
      }
    }
  },
  "ready_to_send": {
    "type": "boolean",
    "required": true,
    "description": "True if message should be sent"
  },
  "skip_sending": {
    "type": "boolean",
    "required": false,
    "description": "True to skip sending (log only)"
  },
  "trace_id": {
    "type": "string",
    "required": true,
    "description": "Trace ID for logging"
  }
}
```

### Input Example

```json
{
  "tenant_id": "550e8400-e29b-41d4-a716-446655440000",
  "dialog_id": "987fcdeb-51a2-3bc4-d567-890123456789",
  "client_id": "123e4567-e89b-12d3-a456-426614174000",
  "channel": "telegram",
  "channel_id": 1,
  "channel_account_id": "abc12345-6789-0def-ghij-klmnopqrstuv",
  "external_chat_id": "123456789",
  "response": {
    "text": "Подскажите, как к вам обращаться?",
    "buttons": [],
    "attachments": []
  },
  "ready_to_send": true,
  "trace_id": "msg_1704326400_abc123"
}
```

---

## Output Schema

**Response:** JSON to caller

```json
{
  "status": {
    "type": "string",
    "enum": ["sent", "failed", "skipped"],
    "description": "Delivery status"
  },
  "message_id": {
    "type": "string|null",
    "description": "Message ID from channel API (if sent)"
  },
  "channel": {
    "type": "string",
    "description": "Channel that handled the message"
  },
  "external_chat_id": {
    "type": "string",
    "description": "External chat ID"
  },
  "error": {
    "type": "string|null",
    "description": "Error message if failed"
  },
  "trace_id": {
    "type": "string",
    "description": "Trace ID"
  },
  "block5_duration_ms": {
    "type": "integer",
    "description": "Block 5 processing time"
  }
}
```

### Output Example

```json
{
  "status": "sent",
  "message_id": "12345678",
  "channel": "telegram",
  "external_chat_id": "123456789",
  "error": null,
  "trace_id": "msg_1704326400_abc123",
  "block5_duration_ms": 120
}
```

---

## Channel Workers

### Worker: ELO_Out_Telegram

**Purpose:** Send messages via Telegram Bot API

**Webhook:** `POST /elo-out-telegram`

**MCP Server:** `mcp-telegram:8767`

**Input Schema:**

```json
{
  "chat_id": "string",
  "bot_token": "string",
  "text": "string|null",
  "parse_mode": "string (HTML|Markdown)",
  "reply_markup": {
    "inline_keyboard": [[{ "text": "string", "callback_data": "string" }]],
    "keyboard": [[{ "text": "string" }]],
    "one_time_keyboard": "boolean",
    "resize_keyboard": "boolean"
  },
  "photo": "string|null (URL or file_id)",
  "document": "string|null",
  "caption": "string|null"
}
```

**Output Schema:**

```json
{
  "ok": "boolean",
  "message_id": "integer",
  "error": "string|null"
}
```

**API Endpoints:**

| Action | Telegram API |
|--------|--------------|
| Send text | `POST /bot{token}/sendMessage` |
| Send photo | `POST /bot{token}/sendPhoto` |
| Send document | `POST /bot{token}/sendDocument` |

**Button Support:**

| Type | Implementation |
|------|----------------|
| Inline buttons | `inline_keyboard` in `reply_markup` |
| Reply keyboard | `keyboard` in `reply_markup` |
| URL buttons | `inline_keyboard` with `url` field |

---

### Worker: ELO_Out_WhatsApp

**Purpose:** Send messages via WhatsApp (Baileys)

**Webhook:** `POST /elo-out-whatsapp`

**MCP Server:** `mcp-whatsapp:8766`

**Input Schema:**

```json
{
  "chat_id": "string (phone@s.whatsapp.net)",
  "session_id": "string",
  "text": "string|null",
  "buttons": [
    {
      "buttonId": "string",
      "buttonText": { "displayText": "string" }
    }
  ],
  "image": {
    "url": "string"
  },
  "document": {
    "url": "string",
    "mimetype": "string",
    "fileName": "string"
  },
  "caption": "string|null"
}
```

**Output Schema:**

```json
{
  "status": "string (sent|failed)",
  "message_id": "string",
  "error": "string|null"
}
```

**API Endpoints:**

| Action | Baileys Method |
|--------|----------------|
| Send text | `sendMessage(jid, { text })` |
| Send image | `sendMessage(jid, { image: { url } })` |
| Send document | `sendMessage(jid, { document: { url } })` |
| Send buttons | `sendMessage(jid, { buttons })` |

**Button Support:**

| Type | Support |
|------|---------|
| Quick reply buttons | Yes (max 3) |
| URL buttons | Via text links |

---

### Worker: ELO_Out_Avito

**Purpose:** Send messages via Avito Messenger API

**Webhook:** `POST /elo-out-avito`

**MCP Server:** `mcp-avito:8765` or `mcp-avito-official:8790`

**Input Schema:**

```json
{
  "chat_id": "string",
  "user_id": "string",
  "access_token": "string",
  "text": "string",
  "images": [
    {
      "url": "string"
    }
  ]
}
```

**Output Schema:**

```json
{
  "status": "string (sent|failed)",
  "message_id": "string",
  "error": "string|null"
}
```

**API Endpoints:**

| Action | Avito API |
|--------|-----------|
| Send text | `POST /messenger/v2/accounts/{user_id}/chats/{chat_id}/messages` |

**Limitations:**

- No inline buttons
- Limited formatting
- Image attachments via URL

---

### Worker: ELO_Out_VK

**Purpose:** Send messages via VK Community API

**Webhook:** `POST /elo-out-vk`

**MCP Server:** `mcp-vk:8767`

**Input Schema:**

```json
{
  "peer_id": "integer",
  "access_token": "string",
  "message": "string|null",
  "keyboard": {
    "one_time": "boolean",
    "buttons": [[
      {
        "action": {
          "type": "string (text|open_link|callback)",
          "label": "string",
          "link": "string|null",
          "payload": "string|null"
        },
        "color": "string (primary|secondary|positive|negative)"
      }
    ]]
  },
  "attachment": "string (photo{owner_id}_{media_id})"
}
```

**Output Schema:**

```json
{
  "response": "integer (message_id)",
  "error": "object|null"
}
```

**API Endpoints:**

| Action | VK API |
|--------|--------|
| Send message | `messages.send` |

**Button Support:**

| Type | Implementation |
|------|----------------|
| Text buttons | `type: "text"` with colors |
| URL buttons | `type: "open_link"` |
| Callback buttons | `type: "callback"` |

---

### Worker: ELO_Out_MAX

**Purpose:** Send messages via MAX (VK Teams) API

**Webhook:** `POST /elo-out-max`

**MCP Server:** `mcp-max:8768`

**Input Schema:**

```json
{
  "chat_id": "string",
  "bot_token": "string",
  "text": "string|null",
  "attachments": [
    {
      "type": "string (image|file)",
      "url": "string"
    }
  ],
  "inline_keyboard": {
    "buttons": [[
      {
        "type": "string (callback|link)",
        "text": "string",
        "url": "string|null",
        "payload": "string|null"
      }
    ]]
  }
}
```

**Output Schema:**

```json
{
  "success": "boolean",
  "message_id": "string",
  "error": "string|null"
}
```

**API Endpoints:**

| Action | MAX API |
|--------|---------|
| Send text | `POST /messages` |

**Button Support:**

| Type | Implementation |
|------|----------------|
| Inline buttons | `inline_keyboard` |
| URL buttons | `type: "link"` |

---

## Orchestrator Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       ELO_Out_Router (Orchestrator)                          │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  Step 1: RECEIVE & VALIDATE                                                  │
│  ──────────────────────────                                                  │
│  • Receive from Block 4 via /elo-out-router                                 │
│  • Validate required fields (channel, chat_id, response)                   │
│  • Record block5_start timestamp                                            │
│                                                                              │
│  Step 2: CHECK SEND CONDITIONS                                               │
│  ──────────────────────────────                                              │
│  IF NOT ready_to_send OR skip_sending:                                      │
│    → status = "skipped"                                                     │
│    → Skip to Step 6                                                         │
│                                                                              │
│  Step 3: GET CHANNEL CREDENTIALS                                             │
│  ─────────────────────────────────                                           │
│  Query elo_t_channel_accounts:                                              │
│  • WHERE id = channel_account_id                                            │
│  • Get: bot_token, access_token, session_id, etc.                          │
│                                                                              │
│  Step 4: ROUTE TO CHANNEL WORKER                                             │
│  ─────────────────────────────────                                           │
│  SWITCH channel:                                                            │
│                                                                              │
│    CASE "telegram":                                                         │
│      → Call ELO_Out_Telegram                                               │
│      → Format: inline_keyboard, parse_mode = HTML                          │
│                                                                              │
│    CASE "whatsapp":                                                         │
│      → Call ELO_Out_WhatsApp                                               │
│      → Format: buttons array, jid format                                   │
│                                                                              │
│    CASE "avito":                                                            │
│      → Call ELO_Out_Avito                                                  │
│      → Format: plain text only                                             │
│                                                                              │
│    CASE "vk":                                                               │
│      → Call ELO_Out_VK                                                     │
│      → Format: keyboard object, peer_id                                    │
│                                                                              │
│    CASE "max":                                                              │
│      → Call ELO_Out_MAX                                                    │
│      → Format: inline_keyboard                                             │
│                                                                              │
│  Step 5: LOG MESSAGE                                                         │
│  ────────────────────                                                        │
│  INSERT INTO elo_t_messages:                                                │
│  • dialog_id, direction = 'out'                                            │
│  • content = response.text                                                 │
│  • external_message_id = message_id from API                               │
│  • status = 'sent' | 'failed'                                              │
│                                                                              │
│  Step 6: BUILD RESULT                                                        │
│  ─────────────────────                                                       │
│  • status: sent | failed | skipped                                         │
│  • message_id: from channel API                                            │
│  • error: if failed                                                        │
│  • block5_duration_ms                                                      │
│                                                                              │
│  Step 7: RESPOND                                                             │
│  ────────────────                                                            │
│  Return result JSON                                                         │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Channel Formatting Matrix

| Feature | Telegram | WhatsApp | Avito | VK | MAX |
|---------|----------|----------|-------|-----|-----|
| **Text** | HTML/Markdown | Plain | Plain | Plain | Markdown |
| **Inline buttons** | Yes | Limited (3) | No | Yes | Yes |
| **URL buttons** | Yes | No | No | Yes | Yes |
| **Reply keyboard** | Yes | No | No | Yes | No |
| **Photos** | Yes | Yes | Yes | Yes | Yes |
| **Documents** | Yes | Yes | No | Yes | Yes |
| **Voice** | Yes | Yes | No | Yes | Yes |
| **Max text length** | 4096 | 4096 | 1000 | 4096 | 4096 |

---

## Button Transformation

```javascript
// Universal button format (input)
const buttons = [
  { text: "Да", callback_data: "confirm_yes" },
  { text: "Нет", callback_data: "confirm_no" },
  { text: "Сайт", url: "https://example.com" }
];

// Telegram format
const telegram_markup = {
  inline_keyboard: [[
    { text: "Да", callback_data: "confirm_yes" },
    { text: "Нет", callback_data: "confirm_no" }
  ], [
    { text: "Сайт", url: "https://example.com" }
  ]]
};

// WhatsApp format (buttons only, no URLs)
const whatsapp_buttons = [
  { buttonId: "confirm_yes", buttonText: { displayText: "Да" } },
  { buttonId: "confirm_no", buttonText: { displayText: "Нет" } }
];
// URL button → text link in message

// VK format
const vk_keyboard = {
  one_time: false,
  buttons: [[
    { action: { type: "callback", label: "Да", payload: "confirm_yes" }, color: "positive" },
    { action: { type: "callback", label: "Нет", payload: "confirm_no" }, color: "negative" }
  ], [
    { action: { type: "open_link", label: "Сайт", link: "https://example.com" } }
  ]]
};

// Avito: No buttons, append to text
const avito_text = `${original_text}\n\nВыберите: Да / Нет\nСайт: https://example.com`;
```

---

## Database Dependencies

### Read Operations

| Table | Purpose |
|-------|---------|
| `elo_t_channel_accounts` | Get channel credentials |

### Write Operations

| Table | Purpose |
|-------|---------|
| `elo_t_messages` | Log outgoing message |
| `elo_t_dialogs` | Update last_message_at |
| `elo_t_events` | Log send event |

---

## External Dependencies

| Service | Port | Purpose |
|---------|------|---------|
| mcp-telegram | 8767 | Telegram Bot API |
| mcp-whatsapp | 8766 | WhatsApp via Baileys |
| mcp-avito | 8765 | Avito Messenger |
| mcp-avito-official | 8790 | Avito Official API |
| mcp-vk | 8767 | VK Community API |
| mcp-max | 8768 | MAX (VK Teams) API |

---

## Error Handling

| Error | Handler | Action |
|-------|---------|--------|
| Channel not supported | Return | status: "failed", error: "unsupported channel" |
| Credentials missing | Return | status: "failed", error: "missing credentials" |
| API timeout | Retry | 1 retry with 2s delay |
| API error (4xx) | Return | status: "failed", error from API |
| API error (5xx) | Retry | 2 retries with exponential backoff |
| Rate limit | Queue | Add to retry queue with delay |

---

## Test Scenarios

### Scenario 1: Telegram Text Message

**Input:**
```json
{
  "channel": "telegram",
  "external_chat_id": "123456789",
  "response": {
    "text": "Привет! Чем могу помочь?",
    "buttons": [
      { "text": "Ремонт", "callback_data": "service_repair" },
      { "text": "Цены", "callback_data": "show_prices" }
    ]
  },
  "ready_to_send": true
}
```

**Expected Output:**
```json
{
  "status": "sent",
  "message_id": "12345678",
  "channel": "telegram"
}
```

### Scenario 2: WhatsApp with Photo

**Input:**
```json
{
  "channel": "whatsapp",
  "external_chat_id": "79001234567",
  "response": {
    "text": "Вот прайс-лист на ремонт",
    "attachments": [
      { "type": "photo", "url": "https://example.com/price.jpg" }
    ]
  },
  "ready_to_send": true
}
```

**Expected Output:**
```json
{
  "status": "sent",
  "message_id": "BAE5...",
  "channel": "whatsapp"
}
```

### Scenario 3: Skip Sending

**Input:**
```json
{
  "channel": "telegram",
  "response": { "text": null },
  "ready_to_send": false,
  "skip_sending": true
}
```

**Expected Output:**
```json
{
  "status": "skipped",
  "message_id": null,
  "channel": "telegram"
}
```

---

## Performance Metrics

| Metric | Target |
|--------|--------|
| Block 5 total time | < 2000ms |
| Channel API call | < 1500ms |
| Database logging | < 200ms |
| Message formatting | < 50ms |

---

## Files

### Existing (may need updates)

| File | Location |
|------|----------|
| Orchestrator | `NEW/workflows/Channel Out/ELO_Out_Router.json` |
| Telegram | `NEW/workflows/Channel Out/ELO_Out_Telegram.json` |
| WhatsApp | `NEW/workflows/Channel Out/ELO_Out_WhatsApp.json` |

### MCP Servers

| Server | Location |
|--------|----------|
| mcp-telegram | `MCP/mcp-telegram/` |
| mcp-whatsapp | `MCP/mcp-whatsapp/` |
| mcp-avito | `MCP/mcp-avito/` |
| mcp-vk | `MCP/mcp-vk/` |
| mcp-max | `MCP/mcp-max/` |

---

## Implementation Notes

### Simple Router Implementation

```javascript
// ELO_Out_Router - Core logic
const input = $input.first().json;

if (!input.ready_to_send || input.skip_sending) {
  return {
    status: 'skipped',
    message_id: null,
    channel: input.channel,
    trace_id: input.trace_id,
    block5_duration_ms: 0
  };
}

// Get channel credentials
const credentials = await getChannelCredentials(input.channel_account_id);

// Route to appropriate worker
let result;
switch (input.channel) {
  case 'telegram':
    result = await callTelegramAPI(input, credentials);
    break;
  case 'whatsapp':
    result = await callWhatsAppAPI(input, credentials);
    break;
  case 'avito':
    result = await callAvitoAPI(input, credentials);
    break;
  case 'vk':
    result = await callVKAPI(input, credentials);
    break;
  case 'max':
    result = await callMAXAPI(input, credentials);
    break;
  default:
    result = { status: 'failed', error: 'Unknown channel' };
}

// Log message
await logMessage(input.dialog_id, 'out', input.response.text, result.message_id);

return {
  status: result.ok ? 'sent' : 'failed',
  message_id: result.message_id,
  channel: input.channel,
  error: result.error,
  trace_id: input.trace_id,
  block5_duration_ms: Date.now() - input.block5_start
};
```

---

*Generated by Claude Code — 2026-01-04*
