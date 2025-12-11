# ELO_Out_WhatsApp

> Outgoing workflow for WhatsApp (Wappi.pro)

---

## General Information

| Parameter | Value |
|----------|----------|
| **Файл** | `NEW/workflows/ELO_Out/ELO_Out_WhatsApp.json` |
| **Триггер** | Execute Workflow Trigger |
| **Вызывается из** | Dialog Engine, AI Router, API |
| **Вызывает** | Wappi.pro API, Neo4j Touchpoint Register |
| **Выход** | Save to messages_history + touchpoint |

---

## Purpose

Sends a message to the client in WhatsApp via Wappi.pro API.

---

## Input Data

```json
{
  "out_message": {
    "external_chat_id": "79991234567@c.us",
    "message_text": "Здравствуйте! Ваша заявка принята.",
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
| **ID** | `51e468de-1b4f-4803-8b11-35ce03dfc607` |

---

### 2. Send WhatsApp Message

| Parameter | Value |
|----------|----------|
| **ID** | `d1f26b25-bd03-4887-9b29-c0381527e403` |
| **Тип** | n8n-nodes-base.httpRequest |
| **Method** | POST |
| **URL** | `https://wappi.pro/api/sync/message/send` |

**Body:**
```json
{
  "chatId": "{{ $json.out_message.external_chat_id }}",
  "text": "{{ $json.out_message.message_text }}"
}
```

---

### 3. Process WhatsApp Response

| Параметр | Значение |
|----------|----------|
| **ID** | `c7623091-c36b-4843-b74c-868f80d77a11` |
| **Тип** | n8n-nodes-base.code |

**Code:**
```javascript
const response = $input.first().json;
const inMessage = $('Execute Workflow Trigger').first().json;

const actionSource = inMessage.out_message?.action_source || '';
const touchpointDirection = (actionSource === 'promo' || actionSource === 'greeting' || actionSource === 'broadcast')
  ? 'promo'
  : 'outbound';

return {
  ...inMessage,
  whatsapp_response: response,
  message_id: response.messageId || Date.now().toString(),
  sent_at: new Date().toISOString(),
  touchpoint_direction: touchpointDirection
};
```

---

### 4. Save Message History

| Параметр | Значение |
|----------|----------|
| **ID** | `e25d32ee-5c20-4669-8cf1-e7cec3556b74` |
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
  'whatsapp',
  (SELECT id FROM channels WHERE code = 'whatsapp' LIMIT 1),
  '{{ $json.message_id }}',
  '{{ $json.out_message.external_chat_id }}'
RETURNING *;
```

---

### 5. Register Touchpoint

| Параметр | Значение |
|----------|----------|
| **ID** | `1eab701a-c893-47f3-bedc-656e21c17a4a` |
| **URL** | `https://n8n.n8nsrv.ru/webhook/neo4j/touchpoint/register` |

**Body:**
```json
{
  "client_id": "uuid",
  "appeal_id": "uuid",
  "channel": "whatsapp",
  "direction": "outbound|promo",
  "type": "message",
  "tenant_id": "uuid",
  "creates_entity": false
}
```

---

## Схема потока

```
Execute Trigger → Send WhatsApp Message → Process Response → Save History → Register Touchpoint
```

---

## Особенности

| Особенность | Описание |
|-------------|----------|
| **API** | Wappi.pro sync API |
| **chatId** | Полный формат: 79991234567@c.us |
| **message_type** | `agent` |
