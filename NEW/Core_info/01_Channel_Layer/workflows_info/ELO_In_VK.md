# ELO_In_VK

> Incoming workflow for VK Community Messages

---

## General Information

| Parameter | Value |
|----------|----------|
| **File** | `NEW/workflows/ELO_In/ELO_In_VK.json` |
| **Trigger** | Webhook POST `/vk` |
| **Called from** | VK Callback API |
| **Calls** | ELO_Core_Tenant_Resolver |
| **Output** | Redis PUSH to `queue:incoming` |

---

## Purpose

Receives incoming messages from VK communities via Callback API, handles confirmation requests, normalizes and places them in queue.

---

## Input Data

**Confirmation request:**
```json
{
  "type": "confirmation",
  "group_id": 123456789
}
```

**New message:**
```json
{
  "type": "message_new",
  "object": {
    "message": {
      "id": 12345,
      "from_id": 123456789,
      "peer_id": 123456789,
  "text": "Здравствуйте",
      "date": 1702200000,
      "attachments": [
        {
          "type": "audio_message",
          "audio_message": {
            "link_mp3": "https://vk.com/audio.mp3"
          }
        }
      ]
    }
  }
}
```

---

## Output Data

```json
{
  "channel": "vk",
  "external_user_id": "123456789",
  "external_chat_id": "123456789",
  "text": "Здравствуйте [транскрипция]",
  "timestamp": "2024-12-10T10:00:00Z",
  "client_phone": null,
  "client_name": null,
  "meta": {
    "group_id": "123456789"
  }
}
```

---

## Nodes

### 1. VK Trigger

| Parameter | Value |
|----------|----------|
| **ID** | `640302a9-28d4-4e52-994a-a48259d07422` |
| **Path** | `/vk` |

---

### 2. Is Confirmation?

| Parameter | Value |
|----------|----------|
| **ID** | `5a1b6925-ebe6-42c8-a575-715616ff2bb3` |
| **Type** | n8n-nodes-base.if |

**Condition:** `$json.type === "confirmation"`

VK requires webhook confirmation during setup.

---

### 3. Send Confirmation

| Parameter | Value |
|----------|----------|
| **ID** | `b92668a6-18d9-4191-bb73-1a38354cdf4c` |
| **Response** | `$env.VK_CONFIRMATION_STRING` |

Returns confirmation code from env variable.

---

### 4. Is New Message?

| Parameter | Value |
|----------|----------|
| **ID** | `6067d84a-2098-4d69-8670-e274d8f7d767` |
| **Condition** | `$json.type === "message_new"` |

- TRUE → process message
- FALSE → Respond OK (Other) — ignore other events

---

### 5. Has Voice?

| Parameter | Value |
|----------|----------|
| **ID** | `943521d1-905b-42f3-ab47-578678f34ac3` |
| **Condition** | `attachments.some(a => a.type === 'audio_message')` |

---

### 6. Extract Voice URL

```javascript
const msg = $input.first().json.object.message;
const voiceAttachment = msg.attachments.find(a => a.type === 'audio_message');
const voiceUrl = voiceAttachment?.audio_message?.link_mp3;

return {
  voice_url: voiceUrl,
  original_message: msg
};
```

---

### 7-8. Download Voice + Transcribe

Standard voice processing via OpenAI Whisper.

---

### 9-10. Normalize with/without Voice

**VK specifics:**
```javascript
const msg = $input.first().json.object.message;

// Фото - берём максимальный размер
const photoAttachment = msg.attachments.find(a => a.type === 'photo');
if (photoAttachment?.photo?.sizes) {
  const maxSize = photoAttachment.photo.sizes.reduce((max, size) =>
    (size.width * size.height > max.width * max.height) ? size : max
  );
  photoUrl = maxSize.url;
}

return {
  channel: 'vk',
  external_user_id: msg.from_id.toString(),
  external_chat_id: msg.peer_id.toString(),
  timestamp: new Date(msg.date * 1000).toISOString(),
  meta: {
    group_id: msg.group_id?.toString()
  }
};
```

---

### 11-14. Tenant Resolver → Queue → Respond

**Response:** `ok` (plain text, VK requirement)

---

## Flow Schema

```
VK Trigger → Is Confirmation?
                ├── YES → Send Confirmation Code
                └── NO → Is New Message?
                            ├── NO → Respond OK (Other)
                            └── YES → Has Voice?
                                        ├── YES → Extract URL → Download → Transcribe → Normalize
                                        └── NO → Normalize
                                                      ↓
                                        Tenant Resolver → Queue → Respond "ok"
```

---

## VK Features

| Feature | Description |
|-------------|----------|
| **Confirmation** | First request — webhook confirmation |
| **Response** | Always `ok` (plain text), otherwise VK retries |
| **audio_message** | Voice messages in `.link_mp3` |
| **photo.sizes** | Array of sizes, select maximum |
| **from_id** | User ID |
| **peer_id** | Chat ID (= from_id for private messages) |

---

## Env Variables

| Variable | Description |
|------------|----------|
| `VK_CONFIRMATION_STRING` | Webhook confirmation code |
