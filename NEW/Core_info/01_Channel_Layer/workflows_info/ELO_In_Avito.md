# ELO_In_Avito

> Incoming workflow for Avito Messenger

---

## General Information

| Parameter | Value |
|----------|----------|
| **File** | `NEW/workflows/ELO_In/ELO_In_Avito.json` |
| **Trigger** | Webhook POST `/avito` |
| **Called from** | Avito Messenger webhook |
| **Calls** | ELO_Core_Tenant_Resolver |
| **Output** | Redis PUSH to `queue:incoming` |

---

## Purpose

Receives incoming messages from Avito Messenger, filters system messages, normalizes and places them in queue.

---

## Input Data

**Source:** Avito Messenger webhook

```json
{
  "id": "webhook-id",
  "timestamp": 1702200000,
  "payload": {
    "value": {
      "id": "message-id",
      "chat_id": "chat-uuid",
      "user_id": 123456,
      "author_id": 789012,
  "author": {"name": "Покупатель"},
  "content": {"text": "Здравствуйте"},
      "type": "text",
      "created": 1702200000,
      "item_id": 12345678
    }
  }
}
```

---

## Output Data

```json
{
  "channel": "avito",
  "external_user_id": "789012",
  "external_chat_id": "chat-uuid",
  "text": "Здравствуйте",
  "timestamp": "2024-12-10T10:00:00Z",
  "client_phone": null,
  "client_name": "Покупатель",
  "meta": {
    "ad_id": "12345678",
    "chat_type": "u2i"
  }
}
```

---

## Nodes

### 1. Avito Trigger

| Parameter | Value |
|----------|----------|
| **ID** | `fe64d5c7-7982-4d3d-aa9e-f93a078ff6f4` |
| **Path** | `/avito` |

---

### 2. Parse Auth Filter

| Parameter | Value |
|----------|----------|
| **ID** | `99c5e201-93fc-49d9-9150-3eded9edeb4f` |
| **Type** | n8n-nodes-base.code |

**System message filtering logic:**
```javascript
const webhookData = $input.first().json;
const msg = webhookData.payload?.value;

// AUTH CHECK
if (!webhookData.id || !webhookData.timestamp) {
  throw new Error('Invalid webhook format');
}

// SYSTEM FILTER - пропускаем системные сообщения
const isSystem = (
  msg.author_id === msg.user_id ||
  msg.author_id === 0 ||
  !msg.author_id
);

if (isSystem) {
  return { skip: true, reason: 'system_message' };
}

// ... извлечение данных
return {
  skip: false,
  has_voice: msg.type === 'voice',
  author_id: msg.author_id,
  author_name: msg.author?.name,
  chat_id: msg.chat_id,
  message_text: msg.content?.text || '',
  item_id: msg.item_id,
  // ...
};
```

**System message filter:**
- `author_id === user_id` — message from seller (ourselves)
- `author_id === 0` — system notification
- `!author_id` — no author

---

### 3. Should Skip?

| Parameter | Value |
|----------|----------|
| **ID** | `13395364-4167-4f5e-a011-77440689eb14` |
| **Type** | n8n-nodes-base.if |

**Condition:** `$json.skip === true`

- TRUE → Respond Skipped (200 OK, skip)
- FALSE → continue processing

---

### 4. Respond Skipped

```json
{"ok": true, "skipped": true}
```

---

### 5-9. Voice Processing + Normalize

Similar to other channels: Download → Transcribe → Normalize

**Avito specifics:**
- `external_user_id`: author_id (buyer ID)
- `ad_id`: item_id (ad ID)
- `chat_type`: from raw_message

---

### 10-12. Tenant Resolver → Queue → Respond

**Response:**
```json
{"ok": true, "queued": true, "batch_key": "avito:chat-uuid"}
```

---

## Flow Schema

```
Avito Trigger → Parse Auth Filter → Should Skip?
                                        ├── YES → Respond Skipped
                                        └── NO → Has Voice?
                                                    ├── YES → Download → Transcribe → Normalize
                                                    └── NO → Normalize
                                                                  ↓
                                                    Tenant Resolver → Queue → Respond
```

---

## Features

| Feature | Description |
|-------------|----------|
| **System filter** | Skips messages from seller and system |
| **ad_id** | Ad ID for linking to item |
| **author_id** | Buyer ID (not user_id!) |
| **chat_type** | Chat type (u2i - user to item) |

---

## Dependencies

| Type | ID | Purpose |
|-----|-----|------------|
| Workflow | rRO6sxLqiCdgvLZz | Tenant Resolver |
| Redis | 7FQcEivUY94atW24 | Queue |
| OpenAI | ptoy1RvCOn39G0Af | Transcription |
