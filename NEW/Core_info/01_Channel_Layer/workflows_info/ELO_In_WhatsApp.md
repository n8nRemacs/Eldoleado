# ELO_In_WhatsApp

> Incoming workflow for WhatsApp (Wappi.pro)

---

## General Information

| Parameter | Value |
|----------|----------|
| **File** | `NEW/workflows/ELO_In/ELO_In_WhatsApp.json` |
| **Trigger** | Webhook POST `/whatsapp` |
| **Called from** | mcp-whatsapp (Wappi.pro webhook) |
| **Calls** | ELO_Core_Tenant_Resolver |
| **Output** | Redis PUSH to `queue:incoming` |

---

## Purpose

Receives incoming messages from WhatsApp via Wappi.pro API, normalizes them into ELO Core Contract, resolves tenant and places them in Redis queue.

---

## Input Data

**Source:** HTTP POST from Wappi.pro webhook

```json
{
  "chatId": "79991234567@c.us",
  "sender": "79991234567",
  "text": "Здравствуйте",
  "messageId": "ABC123",
  "timestamp": 1702200000,
  "senderName": "Иван",
  "pushname": "Иван Петров",
  "media": {
    "type": "voice",
    "url": "https://wappi.pro/media/voice.ogg"
  }
}
```

---

## Output Data

**Destination:** Redis list `queue:incoming`

```json
{
  "channel": "whatsapp",
  "external_user_id": "79991234567",
  "external_chat_id": "79991234567@c.us",
  "text": "Здравствуйте [транскрипция]",
  "timestamp": "2024-12-10T10:00:00Z",
  "client_phone": "+79991234567",
  "client_name": "Иван Петров",
  "client_email": null,
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
    "external_message_id": "ABC123",
    "ad_channel": "whatsapp",
    "provider": "wappi"
  }
}
```

---

## Nodes

### 1. WhatsApp Trigger

| Parameter | Value |
|----------|----------|
| **ID** | `6c0afab1-a6e2-4d31-b80e-4e4bcc364fa2` |
| **Type** | n8n-nodes-base.webhook |
| **Path** | `/whatsapp` |

---

### 2. Extract WhatsApp Data

| Parameter | Value |
|----------|----------|
| **ID** | `cf70854f-bd22-4020-9a24-e7c0c95e3b6c` |
| **Type** | n8n-nodes-base.code |

**Code:**
```javascript
const event = $input.first().json;

// WAPPI API webhook format
const chatId = event.chatId || event.chat_id;
const sender = event.sender || event.from;
const messageText = event.text || event.body || '';
const messageId = event.messageId || event.id || Date.now().toString();
const timestamp = event.timestamp ? new Date(event.timestamp * 1000).toISOString() : new Date().toISOString();

// Извлекаем номер телефона из chatId (формат: 79991234567@c.us)
let phone = chatId.split('@')[0];
if (phone && !phone.startsWith('+')) {
  phone = '+' + phone;
}

// Проверяем медиа
const media = event.media || {};
const hasPhoto = media.type === 'image';
const hasVoice = media.type === 'audio' || media.type === 'voice';
const hasVideo = media.type === 'video';
const hasDocument = media.type === 'document';

let photoUrl = null, voiceUrl = null, videoUrl = null;
if (media.url) {
  if (hasPhoto) photoUrl = media.url;
  if (hasVoice) voiceUrl = media.url;
  if (hasVideo) videoUrl = media.url;
}

return {
  has_voice: hasVoice,
  voice_url: voiceUrl,
  raw_event: event,
  chat_id: chatId,
  phone: phone,
  message_text: messageText,
  message_id: messageId,
  timestamp: timestamp,
  sender_name: event.senderName || event.pushname || null,
  has_photo: hasPhoto,
  photo_url: photoUrl,
  has_video: hasVideo,
  video_url: videoUrl,
  has_document: hasDocument
};
```

---

### 3. Has Voice?

| Parameter | Value |
|----------|----------|
| **ID** | `ed0d8d4b-f1c5-4c79-ab66-c809d95eaa37` |
| **Type** | n8n-nodes-base.if |

**Condition:** `$json.has_voice === true`

---

### 4. Download Voice

| Parameter | Value |
|----------|----------|
| **ID** | `ca48afee-dd4f-45bb-b629-ffd423179943` |
| **Type** | n8n-nodes-base.httpRequest |
| **URL** | `{{ $json.voice_url }}` |

---

### 5. Transcribe Voice

| Parameter | Value |
|----------|----------|
| **ID** | `40afe54b-5196-4f9a-b5bf-6c75e839c4d7` |
| **Type** | @n8n/n8n-nodes-langchain.openAi |
| **Credentials** | OpenAi account (ptoy1RvCOn39G0Af) |

---

### 6. Normalize with Voice / without Voice

Forms ELO Core Contract with or without voice.

**Key fields:**
- `external_user_id`: phone number without `+`
- `external_chat_id`: full chatId (79991234567@c.us)
- `client_phone`: phone number with `+`
- `provider`: 'wappi'

---

### 7. Execute Tenant Resolver

| Parameter | Value |
|----------|----------|
| **ID** | `1eef9d59-66b3-4674-bd86-489df8db51c0` |
| **Calls** | ELO_Core_Tenant_Resolver (rRO6sxLqiCdgvLZz) |

---

### 8. Prepare for Queue

```javascript
const data = $input.first().json;
return {
  message_json: JSON.stringify(data),
  batch_key: `${data.channel}:${data.external_chat_id}`
};
```

---

### 9. Push to Queue

| Parameter | Value |
|----------|----------|
| **ID** | `31da9709-1005-4aeb-8bf6-c9c37463cb60` |
| **Redis** | RPUSH `queue:incoming` |
| **Credentials** | Redis account (7FQcEivUY94atW24) |

---

### 10. Respond Success

**Response:** 200 OK
```json
{"success": true, "queued": true, "batch_key": "whatsapp:79991234567@c.us"}
```

---

## Flow Schema

```
WhatsApp Trigger → Extract Data → Has Voice?
                                      ├── YES → Download → Transcribe → Normalize
                                      └── NO → Normalize
                                                    ↓
                                      Execute Tenant Resolver
                                                    ↓
                                      Prepare → Redis PUSH → Respond
```

---

## Features

- **Phone from chatId:** Extracted from format `79991234567@c.us`
- **Provider:** wappi (Wappi.pro API)
- **Media URL:** Direct links from Wappi.pro
