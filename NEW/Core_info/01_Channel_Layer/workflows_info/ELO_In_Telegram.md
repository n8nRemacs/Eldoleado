# ELO_In_Telegram

> Incoming workflow for Telegram

---

## General Information

| Parameter | Value |
|----------|----------|
| **File** | `NEW/workflows/ELO_In/ELO_In_Telegram.json` |
| **Trigger** | Webhook POST `/telegram-in` |
| **Called from** | mcp-telegram (HTTP POST) |
| **Calls** | **Input Contour (8771)** POST `/ingest` |
| **Output** | HTTP Response {accepted: true} |

---

## Architecture Change (MCP-aligned)

> **IMPORTANT:** This workflow now calls Input Contour MCP `/ingest` instead of Tenant Resolver directly.
>
> **Old flow (deprecated):**
> ```
> mcp-telegram → ELO_In_Telegram → Tenant Resolver → Redis queue
> ```
>
> **New flow (MCP-aligned):**
> ```
> mcp-telegram → ELO_In_Telegram → Input Contour (8771) /ingest → (async processing)
> ```
>
> Tenant resolution now happens in **Client Contour (8772)**, not Channel Layer.

---

## Purpose

Receives incoming messages from Telegram via mcp-telegram, normalizes them into ELO Core Contract, and sends to Input Contour for queueing and debounce.

---

## Input Data

**Source:** HTTP POST from mcp-telegram

```json
{
  "chat_id": "123456789",
  "user_id": "987654321",
  "text": "Здравствуйте, нужен ремонт",
  "bot_token": "123456:ABC-DEF...",
  "first_name": "Иван",
  "last_name": "Петров",
  "username": "ivanpetrov",
  "message_id": "54321",
  "timestamp": "2024-12-10T10:30:00Z",
  "attachments": [
    {"type": "voice", "file_id": "AwACAgIA...", "duration": 5},
    {"type": "photo", "file_id": "AgACAgIA..."}
  ],
  "is_callback": false,
  "callback_data": null,
  "raw_data": {}
}
```

---

## Output Data

**Destination:** Redis list `queue:incoming`

```json
{
  "channel": "telegram",
  "bot_token": "123456:ABC...",
  "tenant_id": "uuid-from-resolver",
  "external_user_id": "tg_987654321",
  "external_chat_id": "tg_123456789",
  "text": "Здравствуйте нужен ремонт айфона",
  "timestamp": "2024-12-10T10:30:00Z",
  "client_phone": null,
  "client_name": "Иван Петров",
  "client_email": null,
  "client_username": "ivanpetrov",
  "media": {
    "has_voice": true,
    "voice_transcribed_text": "транскрипция",
    "has_images": false,
    "images": [],
    "has_video": false,
    "videos": [],
    "has_document": false
  },
  "meta": {
    "external_message_id": "54321",
    "ad_channel": "telegram",
    "telegram_user_id": "987654321",
    "telegram_chat_id": "123456789",
    "is_callback": false,
    "callback_data": null,
    "provider": "mcp-telegram"
  },
  "prefilled_data": {
    "model": null,
    "parts_owner": null
  }
}
```

---

## Input Node

**Telegram Webhook**
- Type: `n8n-nodes-base.webhook`
- Method: POST
- Path: `/telegram-in`
- Response Mode: responseNode

---

## Output Node

**Respond Success**
- Type: `n8n-nodes-base.respondToWebhook`
- Response: 200 OK

```json
{
  "success": true,
  "queued": true,
  "batch_key": "telegram:tg_123456789"
}
```

---

## Nodes

### 1. Telegram Webhook

| Parameter | Value |
|----------|----------|
| **ID** | `8915c2af-b9a5-4d55-8d16-71ea317766a0` |
| **Type** | n8n-nodes-base.webhook |
| **Purpose** | Receive HTTP POST from mcp-telegram |

**Logic:** Accepts JSON body and passes it forward.

---

### 2. Extract Telegram Data

| Parameter | Value |
|----------|----------|
| **ID** | `d5bedbd4-8fd7-4267-bfaf-2b0416f5cf62` |
| **Type** | n8n-nodes-base.code |
| **Purpose** | Extract and normalize data from MCP format |

**Code:**
```javascript
// MCP Telegram присылает нормализованные данные
// Webhook передает данные в body
const rawInput = $input.first().json;
const event = rawInput.body || rawInput;

// Извлекаем данные из MCP формата
const chatId = event.chat_id?.toString() || '';
const userId = event.user_id?.toString() || '';
const messageText = event.text || '';
const messageId = event.message_id?.toString() || Date.now().toString();
const timestamp = event.timestamp || new Date().toISOString();
const botToken = event.bot_token || '';

// Имя пользователя
let clientName = event.first_name || '';
if (event.last_name) {
  clientName += (clientName ? ' ' : '') + event.last_name;
}
if (!clientName && event.username) {
  clientName = '@' + event.username;
}

// Проверяем медиа из attachments
const attachments = event.attachments || [];
const hasVoice = attachments.some(a => a.type === 'voice');
const hasPhoto = attachments.some(a => a.type === 'photo');
const hasVideo = attachments.some(a => a.type === 'video' || a.type === 'video_note');
const hasDocument = attachments.some(a => a.type === 'document');

// Получаем file_id для голосового
const voiceAttachment = attachments.find(a => a.type === 'voice');
const voiceFileId = voiceAttachment?.file_id || null;

// Получаем фото
const photoAttachments = attachments.filter(a => a.type === 'photo');
const videoAttachments = attachments.filter(a => a.type === 'video' || a.type === 'video_note');

return {
  has_voice: hasVoice,
  voice_file_id: voiceFileId,
  bot_token: botToken,
  raw_event: event.raw_data || event,
  chat_id: chatId,
  user_id: userId,
  phone: null,
  message_text: messageText,
  message_id: messageId,
  timestamp: timestamp,
  sender_name: clientName,
  username: event.username,
  has_photo: hasPhoto,
  photo_attachments: photoAttachments,
  has_video: hasVideo,
  video_attachments: videoAttachments,
  has_document: hasDocument,
  is_callback: event.is_callback || false,
  callback_data: event.callback_data || null
};
```

**Output:**
```json
{
  "has_voice": true,
  "voice_file_id": "AwACAgIA...",
  "bot_token": "123456:ABC...",
  "chat_id": "123456789",
  "user_id": "987654321",
  "message_text": "Здравствуйте",
  "message_id": "54321",
  "timestamp": "2024-12-10T10:30:00Z",
  "sender_name": "Иван Петров",
  "username": "ivanpetrov",
  "has_photo": false,
  "photo_attachments": [],
  "has_video": false,
  "video_attachments": [],
  "has_document": false,
  "is_callback": false,
  "callback_data": null
}
```

---

### 3. Has Voice?

| Parameter | Value |
|----------|----------|
| **ID** | `bd4eb3fb-2539-4b2f-85f6-668caac0dc4f` |
| **Type** | n8n-nodes-base.if |
| **Purpose** | Branch: voice present or not |

**Condition:** `$json.has_voice === true`

**Branches:**
- TRUE → Get Voice File Path (process voice)
- FALSE → Normalize without Voice (skip transcription)

---

### 4. Get Voice File Path

| Parameter | Value |
|----------|----------|
| **ID** | `fcc16922-65f0-4611-a455-065ef84ba0a8` |
| **Type** | n8n-nodes-base.httpRequest |
| **Purpose** | Get file path via Telegram API |

**URL:**
```
https://api.telegram.org/bot{{ $json.bot_token }}/getFile?file_id={{ $json.voice_file_id }}
```

**Output:**
```json
{
  "ok": true,
  "result": {
    "file_id": "AwACAgIA...",
    "file_path": "voice/file_123.oga"
  }
}
```

---

### 5. Download Voice

| Parameter | Value |
|----------|----------|
| **ID** | `a20ef264-df54-4b01-b18b-dfa1ec339ec6` |
| **Type** | n8n-nodes-base.httpRequest |
| **Purpose** | Download audio file |

**URL:**
```
https://api.telegram.org/file/bot{{ $('Extract Telegram Data').item.json.bot_token }}/{{ $json.result.file_path }}
```

**Response Format:** File (binary)

---

### 6. Transcribe Voice

| Parameter | Value |
|----------|----------|
| **ID** | `65a9b6e2-6258-4b48-abf1-79ac28311149` |
| **Type** | @n8n/n8n-nodes-langchain.openAi |
| **Purpose** | Transcription via OpenAI Whisper |

**Credentials:** OpenAi account (id: ptoy1RvCOn39G0Af)

**Output:**
```json
{
  "text": "Здравствуйте нужен ремонт айфона"
}
```

---

### 7. Normalize with Voice

| Parameter | Value |
|----------|----------|
| **ID** | `8bbe38c2-ea26-49c2-bcd5-59a67f997b3c` |
| **Type** | n8n-nodes-base.code |
| **Purpose** | Form ELO Core Contract (with voice) |

**Code:**
```javascript
const data = $('Extract Telegram Data').first().json;
const transcription = $input.first().json.text;

let messageText = data.message_text;
if (transcription) {
  messageText = (messageText ? messageText + '\n\n' : '') + transcription;
}

return {
  channel: 'telegram',
  bot_token: data.bot_token,
  external_user_id: 'tg_' + data.user_id,
  external_chat_id: 'tg_' + data.chat_id,

  text: messageText,
  timestamp: data.timestamp,

  client_phone: null,
  client_name: data.sender_name,
  client_email: null,
  client_username: data.username,

  media: {
    has_voice: true,
    voice_transcribed_text: transcription,
    has_images: data.has_photo,
    images: data.photo_attachments.map(a => ({ file_id: a.file_id, type: 'telegram_photo' })),
    has_video: data.has_video,
    videos: data.video_attachments.map(a => ({ file_id: a.file_id, type: 'telegram_video' })),
    has_document: data.has_document
  },

  meta: {
    external_message_id: data.message_id,
    ad_channel: 'telegram',
    ad_id: null,
    original_media_type: 'voice',
    telegram_user_id: data.user_id,
    telegram_chat_id: data.chat_id,
    is_callback: data.is_callback,
    callback_data: data.callback_data,
    raw: data.raw_event,
    provider: 'mcp-telegram'
  },

  prefilled_data: {
    model: null,
    parts_owner: null
  }
};
```

---

### 8. Normalize without Voice

| Parameter | Value |
|----------|----------|
| **ID** | `02fa5c60-969d-432b-8fe4-793be2f156a7` |
| **Type** | n8n-nodes-base.code |
| **Purpose** | Form ELO Core Contract (without voice) |

**Code:**
```javascript
const data = $input.first().json;

return {
  channel: 'telegram',
  bot_token: data.bot_token,
  external_user_id: 'tg_' + data.user_id,
  external_chat_id: 'tg_' + data.chat_id,

  text: data.message_text,
  timestamp: data.timestamp,

  client_phone: null,
  client_name: data.sender_name,
  client_email: null,
  client_username: data.username,

  media: {
    has_voice: false,
    voice_transcribed_text: null,
    has_images: data.has_photo,
    images: data.photo_attachments.map(a => ({ file_id: a.file_id, type: 'telegram_photo' })),
    has_video: data.has_video,
    videos: data.video_attachments.map(a => ({ file_id: a.file_id, type: 'telegram_video' })),
    has_document: data.has_document,
    has_callback: data.is_callback
  },

  meta: {
    external_message_id: data.message_id,
    ad_channel: 'telegram',
    ad_id: null,
    original_media_type: data.has_photo ? 'photo' : data.has_video ? 'video' : data.is_callback ? 'callback' : 'text',
    telegram_user_id: data.user_id,
    telegram_chat_id: data.chat_id,
    is_callback: data.is_callback,
    callback_data: data.callback_data,
    raw: data.raw_event,
    provider: 'mcp-telegram'
  },

  prefilled_data: {
    model: null,
    parts_owner: null
  }
};
```

---

### 9. ~~Execute Tenant Resolver~~ → POST to Input Contour (MCP-aligned)

| Parameter | Value |
|----------|----------|
| **ID** | `169e4c2e-d29d-4cf3-9841-382cfc3ceec7` |
| **Type** | n8n-nodes-base.httpRequest |
| **typeVersion** | 4.2 |
| **Purpose** | Send normalized message to Input Contour |

> **DEPRECATED:** Old node called `ELO_Core_Tenant_Resolver` directly.
> **NEW:** Call Input Contour MCP `/ingest` endpoint.

**HTTP Request:**
- **URL:** `http://45.144.177.128:8771/ingest` (MCP) or `https://n8n.n8nsrv.ru/webhook/elo-input-ingest` (n8n polygon)
- **Method:** POST
- **Body:** ELO Core Contract (JSON)

**What it does:**
- Sends normalized message to Input Contour
- Input Contour handles idempotency, queue, debounce
- Returns immediately with `{accepted: true, message_id, trace_id}`

---

### ~~10. Prepare for Queue~~ (REMOVED)

> **DEPRECATED:** Redis queueing now handled by Input Contour, not Channel workflow.

---

### ~~11. Push to Queue~~ (REMOVED)

> **DEPRECATED:** Redis operations now in Input Contour MCP, not Channel workflow.

---

### 12. Respond Success

| Parameter | Value |
|----------|----------|
| **ID** | `533dd045-b34f-4178-b133-059723be4564` |
| **Type** | n8n-nodes-base.respondToWebhook |
| **Purpose** | HTTP Response for mcp-telegram |

**Response:** 200 OK
```json
{
  "success": true,
  "queued": true,
  "batch_key": "telegram:tg_123456789"
}
```

---

## Flow Schema

```
┌─────────────────┐
│ Telegram        │
│ Webhook         │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ Extract         │
│ Telegram Data   │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ Has Voice?      │
└────────┬────────┘
    YES  │  NO
    ┌────┴────┐
    ▼         ▼
┌───────┐  ┌───────────────┐
│ Get   │  │ Normalize     │
│ Voice │  │ without Voice │
│ Path  │  └───────┬───────┘
└───┬───┘          │
    ▼              │
┌───────┐          │
│Download│         │
│ Voice │          │
└───┬───┘          │
    ▼              │
┌───────┐          │
│Transcr│          │
│ibe    │          │
└───┬───┘          │
    ▼              │
┌───────────────┐  │
│ Normalize     │  │
│ with Voice    │  │
└───────┬───────┘  │
        │          │
        └────┬─────┘
             ▼
    ┌─────────────────┐
    │ Execute Tenant  │
    │ Resolver        │
    └────────┬────────┘
             │
             ▼
    ┌─────────────────┐
    │ Prepare for     │
    │ Queue           │
    └────────┬────────┘
             │
             ▼
    ┌─────────────────┐
    │ Push to Queue   │
    │ (Redis RPUSH)   │
    └────────┬────────┘
             │
             ▼
    ┌─────────────────┐
    │ Respond Success │
    │ (200 OK)        │
    └─────────────────┘
```

---

## Dependencies

| Type | Name | ID | Purpose |
|-----|----------|-----|------------|
| Credentials | OpenAi account | ptoy1RvCOn39G0Af | Voice transcription |
| Service | **Input Contour (8771)** | — | POST /ingest |
| External API | Telegram Bot API | — | Download voice files |

**Removed dependencies (now in Input/Client Contour):**
- ~~Redis account~~ — queue operations in Input Contour
- ~~ELO_Core_Tenant_Resolver~~ — tenant resolution in Client Contour

---

## Error Handling

| Error | Reason | Handling |
|--------|---------|-----------|
| Telegram API error | API unavailable | Retry or skip voice |
| OpenAI transcription error | Whisper error | Use empty text |
| Input Contour error | 8771 unavailable | Return 503, MCP gets error |
| Input Contour timeout | 8771 slow | Return 504, MCP gets error |
