# ELO_Out_VK

> Outgoing workflow for VK Community Messages

---

## General Information

| Parameter | Value |
|----------|----------|
| **Файл** | `NEW/workflows/ELO_Out/ELO_Out_VK.json` |
| **Триггер** | Execute Workflow Trigger |
| **Вызывается из** | Dialog Engine, AI Router, API |
| **Вызывает** | VK API, Neo4j Touchpoint Register |
| **Выход** | Save to messages_history + touchpoint |

---

## Purpose

Sends a message to the client in VK via community API.

---

## Input Data

```json
{
  "out_message": {
    "external_chat_id": "123456789",
    "message_text": "Здравствуйте!",
    "tenant_id": "uuid",
    "appeal_id": "uuid",
    "client_id": "uuid",
    "action_source": "response"
  }
}
```

---

## Nodes

### 1. Execute Workflow Trigger

| Parameter | Value |
|----------|----------|
| **ID** | `c1793240-7a20-4d79-915e-e32355865147` |

---

### 2. Send VK Message

| Parameter | Value |
|----------|----------|
| **ID** | `2cc59825-5ac3-4c7d-8b44-0feec498cae6` |
| **Тип** | n8n-nodes-base.httpRequest |
| **Method** | POST |
| **URL** | `https://api.vk.com/method/messages.send` |

**Query Parameters:**
```
peer_id={{ $json.out_message.external_chat_id }}
message={{ $json.out_message.message_text }}
random_id={{ Math.floor(Math.random() * 1000000000) }}
v=5.131
```

**Important:** `random_id` is required by VK API — unique ID for deduplication.

---

### 3. Process VK Response

| Parameter | Value |
|----------|----------|
| **ID** | `637dba7f-fb2c-4687-9def-18f9fc1ecf19` |
| **Тип** | n8n-nodes-base.code |

**Code:**
```javascript
const response = $input.first().json;

if (response.error) {
  throw new Error(`VK API Error: ${response.error.error_msg}`);
}

const inMessage = $('Execute Workflow Trigger').first().json;

const actionSource = inMessage.out_message?.action_source || '';
const touchpointDirection = (actionSource === 'promo' || actionSource === 'greeting' || actionSource === 'broadcast')
  ? 'promo'
  : 'outbound';

return {
  ...inMessage,
  vk_response: response.response,
  message_id: response.response?.toString() || 'unknown',
  sent_at: new Date().toISOString(),
  touchpoint_direction: touchpointDirection
};
```

**VK Response:** `response` содержит ID отправленного сообщения.

---

### 4. Save Message History

| Параметр | Значение |
|----------|----------|
| **ID** | `50a12d4c-4dab-49a1-b1b3-430663fcbb70` |
| **Тип** | n8n-nodes-base.postgres |

**SQL запрос:**
```sql
INSERT INTO messages_history (
  tenant_id, appeal_id, message_type, message_text,
  channel, channel_id, external_message_id, external_chat_id
)
SELECT
  '{{ $json.out_message.tenant_id }}'::uuid,
  '{{ $json.out_message.appeal_id }}'::uuid,
  'agent',
  '{{ $json.out_message.message_text }}',
  'vk',
  (SELECT id FROM channels WHERE code = 'vk' LIMIT 1),
  '{{ $json.message_id }}',
  '{{ $json.out_message.external_chat_id }}'
RETURNING *;
```

---

### 5. Register Touchpoint

| Параметр | Значение |
|----------|----------|
| **ID** | `9ffa3c8c-d099-4818-82a7-cf1a8cc99788` |
| **URL** | `https://n8n.n8nsrv.ru/webhook/neo4j/touchpoint/register` |

**Body:**
```json
{
  "client_id": "uuid",
  "appeal_id": "uuid",
  "channel": "vk",
  "direction": "outbound",
  "type": "message",
  "tenant_id": "uuid",
  "creates_entity": false
}
```

---

## Схема потока

```
Execute Trigger → Send VK Message → Process Response → Save History → Register Touchpoint
```

---

## VK API особенности

| Параметр | Значение | Описание |
|----------|----------|----------|
| `peer_id` | user_id | ID получателя |
| `random_id` | random int | Дедупликация сообщений |
| `v` | 5.131 | Версия API |
| `access_token` | env/credential | Токен сообщества |

---

## Обработка ошибок

VK возвращает ошибки в формате:
```json
{
  "error": {
    "error_code": 100,
    "error_msg": "One of the parameters specified was missing or invalid"
  }
}
```

Workflow бросает exception при наличии `error` в ответе.
