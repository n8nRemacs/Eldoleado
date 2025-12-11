# ELO_Out_Avito

> Outgoing workflow for Avito Messenger

---

## General Information

| Parameter | Value |
|----------|----------|
| **Файл** | `NEW/workflows/ELO_Out/ELO_Out_Avito.json` |
| **Триггер** | Execute Workflow Trigger |
| **Вызывается из** | Dialog Engine, AI Router, API |
| **Вызывает** | Avito Messenger API, Neo4j Touchpoint Register |
| **Выход** | Save to messages_history + touchpoint |

---

## Purpose

Sends a message to the client in Avito Messenger with automatic OAuth token refresh.

---

## Input Data

```json
{
  "out_message": {
    "external_chat_id": "chat-uuid",
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
| **ID** | `f65efffd-9145-4af6-bcfc-ebc4e5b0865e` |

---

### 2. Get Access Token

| Параметр | Значение |
|----------|----------|
| **ID** | `29e00a74-5a5b-455f-acaa-4e6bd442d22b` |
| **Тип** | n8n-nodes-base.redis |
| **Operation** | GET |
| **Key** | `avito_access_token` |

**Redis:** Get cached OAuth token.

---

### 3. Token Exists?

| Параметр | Значение |
|----------|----------|
| **ID** | `e1d4dd24-4275-4c4c-afbb-69f2e85fbb5f` |
| **Условие** | `$json.value` is not empty |

- TRUE → Merge Token (use existing)
- FALSE → Refresh Token (get new)

---

### 4. Refresh Token

| Параметр | Значение |
|----------|----------|
| **ID** | `f9602f64-ca1d-4015-961b-9dd6a2be3e07` |
| **URL** | `https://api.avito.ru/token` |
| **Method** | POST |
| **Content-Type** | form-urlencoded |

**Body:**
```
grant_type=client_credentials
```

Gets new access_token via OAuth.

---

### 5. Save New Token

| Параметр | Значение |
|----------|----------|
| **ID** | `1471d7fd-266d-4edc-b0cc-8c7cd3964466` |
| **Тип** | n8n-nodes-base.redis |
| **Operation** | SET |
| **Key** | `avito_access_token` |
| **TTL** | 86400 (24 часа) |

**Redis:** Cache new token for 24 hours.

---

### 6. Merge Token

| Параметр | Значение |
|----------|----------|
| **ID** | `1c8a9393-8625-4c18-a452-14c6d4c553cc` |

Merges message data with access_token.

---

### 7. Prepare Message

| Параметр | Значение |
|----------|----------|
| **ID** | `a373f611-022f-4a8d-bf6b-1427614f2534` |

**Code (escaping):**
```javascript
const data = $input.first().json;

const escapedText = data.out_message.message_text
  .replace(/\\/g, '\\\\')
  .replace(/"/g, '\\"')
  .replace(/\n/g, '\\n')
  .replace(/\r/g, '\\r')
  .replace(/\t/g, '\\t');

return {
  ...data,
  escaped_message_text: escapedText
};
```

---

### 8. Send Avito Message

| Параметр | Значение |
|----------|----------|
| **ID** | `e87773ea-8952-4838-90b2-192854a7b22f` |
| **URL** | `https://api.avito.ru/messenger/v3/accounts/{{ $env.AVITO_USER_ID }}/chats/{{ $json.out_message.external_chat_id }}/messages` |
| **Method** | POST |

**Headers:**
```
Authorization: Bearer {{ $json.access_token }}
Content-Type: application/json
```

**Body:**
```json
{
  "message": {
    "type": "text",
    "text": "{{ $json.escaped_message_text }}"
  }
}
```

---

### 9. Process Avito Response

| Параметр | Значение |
|----------|----------|
| **ID** | `ffa7e194-4b1c-4381-82f8-1f76cee5109c` |

Processes response, determines touchpoint_direction.

---

### 10. Save Message History

**SQL:** INSERT into `messages_history` with `channel = 'avito'`

---

### 11. Register Touchpoint

**URL:** `https://n8n.n8nsrv.ru/webhook/neo4j/touchpoint/register`

---

## Flow Diagram

```
Execute Trigger → Get Access Token (Redis) → Token Exists?
                                                  ├── YES → Merge Token
                                                  └── NO → Refresh Token → Save Token (Redis) → Merge Token
                                                                                                    ↓
                                                                  Prepare Message → Send Avito Message
                                                                                          ↓
                                                              Process Response → Save History → Register Touchpoint
```

---

## Redis operations

| Operation | Key | TTL | Purpose |
|----------|-----|-----|------------|
| GET | `avito_access_token` | — | Get cached token |
| SET | `avito_access_token` | 86400s | Save new token |

---

## Env variables

| Variable | Description |
|------------|----------|
| `AVITO_USER_ID` | ID аккаунта Avito |
| `AVITO_CLIENT_ID` | OAuth client_id |
| `AVITO_CLIENT_SECRET` | OAuth client_secret |

---

## Features

| Feature | Description |
|-------------|----------|
| **OAuth** | Automatic token refresh |
| **Redis cache** | Token cached for 24 hours |
| **Escape** | Special handling of escape characters |
| **API v3** | Avito Messenger API v3 |
