# ELO_In_MAX

> Incoming workflow for MAX (VK Teams/Mail.ru Messenger)

---

## General Information

| Parameter | Value |
|----------|----------|
| **File** | `NEW/workflows/ELO_In/ELO_In_MAX.json` |
| **Trigger** | Webhook POST `/max` |
| **Called from** | MAX.ru Bot API |
| **Calls** | ELO_Core_Tenant_Resolver |
| **Output** | Redis PUSH to `queue:incoming` |

---

## Purpose

Receives incoming messages from MAX (VK Teams, formerly Agent Mail.ru), normalizes and places them in queue.

---

## Input Data

```json
{
  "message": {
    "id": "msg-123",
    "chat_id": "chat-456",
    "from": {
      "id": "user-789",
      "name": "Иван Петров",
      "phone": "+79991234567"
    },
    "text": "Здравствуйте",
    "type": "text",
    "timestamp": 1702200000,
    "voice": {
      "url": "https://max.ru/voice.ogg"
    },
    "image": {
      "url": "https://max.ru/image.jpg"
    }
  }
}
```

---

## Output Data

```json
{
  "channel": "max",
  "external_user_id": "user-789",
  "external_chat_id": "chat-456",
  "text": "Здравствуйте",
  "timestamp": "2024-12-10T10:00:00Z",
  "client_phone": "+79991234567",
  "client_name": "Иван Петров",
  "meta": {
    "ad_channel": "max"
  }
}
```

---

## Nodes

### 1. MAX Trigger

| Parameter | Value |
|----------|----------|
| **ID** | `fdf2ef76-3a9a-4209-aa49-716f9cca1bfe` |
| **Path** | `/max` |

---

### 2. Extract MAX Data

| Parameter | Value |
|----------|----------|
| **ID** | `3ff642aa-05b1-4b07-94f9-0efe7782cb72` |
| **Type** | n8n-nodes-base.code |

**Code:**
```javascript
const event = $input.first().json;
const msg = event.message || event;
const sender = msg.from || msg.sender;

const messageText = msg.text || msg.body || '';

// Определяем типы медиа
const hasPhoto = msg.type === 'image' || !!msg.image;
const hasVoice = msg.type === 'voice' || msg.type === 'audio' || !!msg.voice || !!msg.audio;
const hasVideo = msg.type === 'video' || !!msg.video;
const hasDocument = msg.type === 'document' || !!msg.document;

// Извлекаем URL медиа
let photoUrl = msg.image?.url || null;
let voiceUrl = msg.voice?.url || msg.audio?.url || null;
let videoUrl = msg.video?.url || null;

// Данные отправителя
const senderId = sender?.id || sender?.phone || msg.chat_id;
const senderName = sender?.name || sender?.display_name || null;
const senderPhone = sender?.phone || null;

// Нормализуем телефон
let cleanPhone = null;
if (senderPhone) {
  cleanPhone = senderPhone.replace(/\D/g, '');
  if (cleanPhone.length === 11 && cleanPhone.startsWith('8')) {
    cleanPhone = '7' + cleanPhone.substring(1);
  }
  cleanPhone = '+' + cleanPhone;
}

return {
  has_voice: hasVoice,
  voice_url: voiceUrl,
  sender_id: senderId,
  sender_name: senderName,
  sender_phone: cleanPhone,
  chat_id: msg.chat_id || senderId?.toString(),
  message_text: messageText,
  has_photo: hasPhoto,
  photo_url: photoUrl,
  // ...
};
```

---

### 3-6. Voice Processing

Standard flow: Has Voice? → Download → Transcribe → Normalize

---

### 7-10. Tenant Resolver → Queue → Respond

**Response:** 200 OK
```json
{"success": true, "queued": true, "batch_key": "max:chat-456"}
```

---

## Flow Schema

```
MAX Trigger → Extract Data → Has Voice?
                                ├── YES → Download → Transcribe → Normalize
                                └── NO → Normalize
                                              ↓
                                Tenant Resolver → Queue → Respond
```

---

## MAX Features

| Feature | Description |
|-------------|----------|
| **Phone** | Can come in `sender.phone` |
| **Phone normalization** | 8 → 7 at beginning |
| **Media URL** | Direct links in `msg.voice.url`, `msg.image.url` |

---

## Dependencies

| Type | ID | Purpose |
|-----|-----|------------|
| Workflow | rRO6sxLqiCdgvLZz | Tenant Resolver |
| Redis | 7FQcEivUY94atW24 | Queue |
| OpenAI | ptoy1RvCOn39G0Af | Transcription |
