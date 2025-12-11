# ELO_Out_Telegram

> Outgoing workflow for Telegram

---

## General Information

| Parameter | Value |
|----------|----------|
| **Файл** | `NEW/workflows/ELO_Out/ELO_Out_Telegram.json` |
| **Триггер** | Execute Workflow Trigger |
| **Вызывается из** | Dialog Engine, AI Router, API |
| **Вызывает** | MCP Telegram API, Neo4j Touchpoint Register |
| **Выход** | Save to messages_history + touchpoint |

---

## Purpose

Sends a message to the client in Telegram via MCP, saves it to history, and registers a touchpoint in Neo4j.

---

## Input Data

```json
{
  "external_chat_id": "tg_123456789",
  "message_text": "Здравствуйте! Ваша заявка принята.",
  "tenant_id": "uuid",
  "appeal_id": "uuid",
  "client_id": "uuid",
  "action_source": "response"
}
```

**action_source options:**
- `response` — reply to client message
- `promo` — promo mailing
- `greeting` — auto greeting
- `broadcast` — mass mailing

---

## Output Data

**PostgreSQL:** INSERT into `messages_history`
**Neo4j:** Touchpoint via webhook

---

## Nodes

### 1. Execute Workflow Trigger

| Parameter | Value |
|----------|----------|
| **ID** | `4211abe2-d2b2-43b8-9806-4a6babb831cd` |
| **Тип** | n8n-nodes-base.executeWorkflowTrigger |

Receives data from the calling workflow.

---

### 2. Prepare Message

| Parameter | Value |
|----------|----------|
| **ID** | `14e83200-cea1-4390-b6d9-da0b0d245c05` |
| **Тип** | n8n-nodes-base.code |

**Код:**
```javascript
const input = $input.first().json;
const outMessage = input.out_message || {};

// Берём chat_id, убираем префикс tg_
let chatId = input.external_chat_id || outMessage.external_chat_id || '';
if (chatId && chatId.startsWith('tg_')) {
  chatId = chatId.substring(3);
}

const text = input.message_text || outMessage.message_text || input.text || '';
const tenantId = input.tenant_id || outMessage.tenant_id || '';

return {
  chat_id: chatId,
  text: text,
  parse_mode: 'Markdown',
  tenant_id: tenantId,
  appeal_id: input.appeal?.id || outMessage.appeal_id || input.appeal_id,
  client_id: input.client?.id || outMessage.client_id || input.client_id,
  action_source: input.action_source || outMessage.action_source || 'response'
};
```

---

### 3. Get Bot Token

| Parameter | Value |
|----------|----------|
| **ID** | `5f1960df-ae7c-4f8f-b7d0-80ab9fdfa287` |
| **Тип** | n8n-nodes-base.postgres |

**SQL query:**
```sql
SELECT value->>'token' as bot_token
FROM tenant_configs
WHERE tenant_id = '{{ $json.tenant_id }}'::uuid
  AND key = 'telegram_bot_token'
LIMIT 1;
```

**What we look for:** Bot token for tenant from `tenant_configs`

---

### 4. Merge Bot Token

```javascript
const prepared = $('Prepare Message').first().json;
const tokenResult = $input.first().json;
const botToken = tokenResult?.bot_token || '';

return {
  ...prepared,
  bot_token: botToken
};
```

---

### 5. Send via MCP Telegram

| Parameter | Value |
|----------|----------|
| **ID** | `8f4177f5-b3d2-4bb5-8e2d-af0eda0977c4` |
| **Тип** | n8n-nodes-base.httpRequest |
| **Method** | POST |
| **URL** | `https://tg.eldoleado.ru/api/send` |

**Headers:**
```
X-API-Key: mcp-telegram-secret-2024
Content-Type: application/json
```

**Body:**
```json
{
  "token": "{{ $json.bot_token }}",
  "chat_id": "{{ $json.chat_id }}",
  "text": "{{ $json.text }}",
  "parse_mode": "Markdown"
}
```

---

### 6. Process Telegram Response

| Параметр | Значение |
|----------|----------|
| **ID** | `df2e9d74-4bd6-44ab-be37-06d8962a84c2` |
| **Тип** | n8n-nodes-base.code |

**Код:**
```javascript
const response = $input.first().json;
const preparedData = $('Merge Bot Token').first().json;

// Определяем тип touchpoint
const actionSource = preparedData.action_source || '';
const touchpointDirection = (actionSource === 'promo' || actionSource === 'greeting' || actionSource === 'broadcast')
  ? 'promo'
  : 'outbound';

return {
  ...preparedData,
  telegram_response: response,
  message_id: response.message_id?.toString() || Date.now().toString(),
  sent_at: new Date().toISOString(),
  touchpoint_direction: touchpointDirection
};
```

**touchpoint_direction:**
- `outbound` — operator reply
- `promo` — promo, greeting, broadcast

---

### 7. Save Message History

| Parameter | Value |
|----------|----------|
| **ID** | `9c9201e1-d985-43df-9eb5-716d75cf84fa` |
| **Тип** | n8n-nodes-base.postgres |

**SQL query:**
```sql
INSERT INTO messages_history (
  tenant_id,
  appeal_id,
  message_type,
  message_text,
  channel,
  channel_id,
  external_message_id,
  external_chat_id
)
SELECT
  '{{ $json.tenant_id }}'::uuid,
  '{{ $json.appeal_id }}'::uuid,
  'agent',
  '{{ $json.text }}',
  'telegram',
  (SELECT id FROM channels WHERE code = 'telegram' LIMIT 1),
  '{{ $json.message_id }}',
  'tg_{{ $json.chat_id }}'
RETURNING *;
```

**Table:** `messages_history`
**message_type:** `agent` (outgoing from operator/bot)

---

### 8. Register Touchpoint

| Parameter | Value |
|----------|----------|
| **ID** | `e7d3ab39-affc-4d05-a9f3-52d0717d7247` |
| **Тип** | n8n-nodes-base.httpRequest |
| **URL** | `https://n8n.n8nsrv.ru/webhook/neo4j/touchpoint/register` |

**Body:**
```json
{
  "client_id": "uuid",
  "appeal_id": "uuid",
  "channel": "telegram",
  "direction": "outbound",
  "type": "message",
  "tenant_id": "uuid",
  "creates_entity": false
}
```

**Neo4j:** Registers a touchpoint for the interaction graph.

---

## Flow Diagram

```
Execute Trigger → Prepare Message → Get Bot Token → Merge Token
                                                        ↓
                                              Send via MCP Telegram
                                                        ↓
                                            Process Response
                                                        ↓
                                            Save Message History
                                                        ↓
                                            Register Touchpoint (Neo4j)
```

---

## Dependencies

| Type | ID | Purpose |
|-----|-----|------------|
| Postgres | n2SyhP9QhMnp1ryk | БД |
| External | tg.eldoleado.ru | MCP Telegram |
| External | neo4j/touchpoint | Touchpoint API |

---

## Features

| Feature | Description |
|-------------|----------|
| **Bot token из БД** | Мультитенант — токен по tenant_id |
| **parse_mode** | Markdown |
| **touchpoint_direction** | outbound или promo |
| **message_type** | `agent` в messages_history |
