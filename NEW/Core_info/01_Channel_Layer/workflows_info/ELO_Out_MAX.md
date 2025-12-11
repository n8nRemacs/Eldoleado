# ELO_Out_MAX

> Outgoing workflow for MAX (VK Teams/Mail.ru Messenger)

---

## General Information

| Parameter | Value |
|----------|----------|
| **Файл** | `NEW/workflows/ELO_Out/ELO_Out_MAX.json` |
| **Триггер** | Execute Workflow Trigger |
| **Вызывается из** | Dialog Engine, AI Router, API |
| **Вызывает** | MAX API, Neo4j Touchpoint Register |
| **Выход** | Save to messages_history + touchpoint |

---

## Purpose

Sends a message to the client in MAX (VK Teams).

---

## Input Data

```json
{
  "out_message": {
    "external_chat_id": "chat-456",
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
| **ID** | `a71503cc-25c1-400a-81f8-94e7331e68c7` |

---

### 2. Send MAX Message

| Parameter | Value |
|----------|----------|
| **ID** | `332b966b-a89f-4d90-a203-ccc4d62ec9b2` |
| **Тип** | n8n-nodes-base.httpRequest |
| **Method** | POST |
| **URL** | `{{ $env.MAX_API_URL }}/messages/send` |

**Body:**
```json
{
  "chat_id": "{{ $json.out_message.external_chat_id }}",
  "text": "{{ $json.out_message.message_text }}"
}
```

---

### 3. Process MAX Response

| Параметр | Значение |
|----------|----------|
| **ID** | `e0ef6423-d19d-4706-8ca8-b35fd7180f59` |
| **Тип** | n8n-nodes-base.code |

**Code:**
```javascript
const response = $input.first().json;

if (response.error) {
  console.error('MAX API Error:', response.error);
}

const inMessage = $('Execute Workflow Trigger').first().json;

const actionSource = inMessage.out_message?.action_source || '';
const touchpointDirection = (actionSource === 'promo' || actionSource === 'greeting' || actionSource === 'broadcast')
  ? 'promo'
  : 'outbound';

return {
  ...inMessage,
  max_response: response,
  message_id: response.message_id || response.id || Date.now().toString(),
  sent_at: new Date().toISOString(),
  touchpoint_direction: touchpointDirection
};
```

---

### 4. Save Message History

| Parameter | Value |
|----------|----------|
| **ID** | `c7f02af3-8f38-455b-84f7-ac8124c722be` |
| **Тип** | n8n-nodes-base.postgres |

**SQL query:**
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
  'max',
  (SELECT id FROM channels WHERE code = 'max' LIMIT 1),
  '{{ $json.message_id }}',
  '{{ $json.out_message.external_chat_id }}'
RETURNING *;
```

---

### 5. Register Touchpoint

| Parameter | Value |
|----------|----------|
| **ID** | `30f3276e-0e45-4c04-9738-f4f3dab53f85` |
| **URL** | `https://n8n.n8nsrv.ru/webhook/neo4j/touchpoint/register` |

**Body:**
```json
{
  "client_id": "uuid",
  "appeal_id": "uuid",
  "channel": "max",
  "direction": "outbound",
  "type": "message",
  "tenant_id": "uuid",
  "creates_entity": false
}
```

---

## Flow Diagram

```
Execute Trigger → Send MAX Message → Process Response → Save History → Register Touchpoint
```

---

## Env variables

| Variable | Description |
|------------|----------|
| `MAX_API_URL` | Base URL для MAX API |

---

## Features

| Feature | Description |
|-------------|----------|
| **Простой API** | Минимальные параметры |
| **chat_id** | ID чата в MAX |
| **Soft fail** | Логирует ошибку, не бросает exception |
