# API_Android_Auth

> Operator authentication (Android app)

---

## General Information

| Parameter | Value |
|----------|----------|
| **Файл** | `NEW/workflows/n8n_old/API/API_Android_Auth.json` |
| **ID в n8n** | U7Zak2jQ0rVG0fmp |
| **Триггер** | Webhook POST `/android/auth/login` |
| **Вызывается из** | Android App |
| **Вызывает** | — |
| **Выход** | HTTP Response (session_token) |

---

## Purpose

Authenticate operator by email/username and password. Creates a session in `operator_devices`, returns `session_token` for subsequent requests.

---

## Input Data

**Source:** HTTP POST from Android App

```json
{
  "login": "ivan@example.com",
  "password": "secret123",
  "device_info": {
    "device_id": "abc123",
    "model": "Samsung Galaxy S21",
    "os_version": "Android 13"
  }
}
```

---

## Output Data

**Success (200):**
```json
{
  "success": true,
  "operator_id": "uuid",
  "tenant_id": "uuid",
  "name": "Иван Петров",
  "email": "ivan@example.com",
  "location_id": "uuid",
  "session_token": "generated-uuid-token"
}
```

**Error (401) — operator not found:**
```json
{
  "success": false,
  "error": "operator_not_found_or_inactive"
}
```

**Error (401) — invalid password:**
```json
{
  "success": false,
  "error": "invalid_password"
}
```

---

## Input node

**Webhook - Auth Login**
- Type: `n8n-nodes-base.webhook`
- Method: POST
- Path: `/android/auth/login`
- Response Mode: responseNode
- CORS: `*`

---

## Output nodes

1. **Response - Success** (200 OK)
2. **Response - Not Found** (401)
3. **Response - Invalid Password** (401)

---

## Nodes

### 1. Webhook - Auth Login

| Parameter | Value |
|----------|----------|
| **ID** | `06ca83d7-ee4f-43cc-a834-b2fa2fcf8f35` |
| **Тип** | n8n-nodes-base.webhook |
| **Назначение** | Handle auth POST request |

---

### 2. Postgres - Find Operator

| Parameter | Value |
|----------|----------|
| **ID** | `b0bd0db4-ddd1-4b8c-9e8d-056d068dbeef` |
| **Тип** | n8n-nodes-base.postgres |
| **Назначение** | Find operator by email or username |

**SQL query:**
```sql
SELECT id, tenant_id, name, email, username, password_hash, is_active, location_id
FROM operators
WHERE (email = '{{ $json.body.login }}' OR username = '{{ $json.body.login }}')
AND is_active = true
LIMIT 1;
```

**Table:** `operators`

**What we look for:** Operator by email or username, active only

---

### 3. IF - Operator Exists & Active

| Parameter | Value |
|----------|----------|
| **ID** | `a892a92f-e6f1-4565-9434-d3b21ae7f379` |
| **Тип** | n8n-nodes-base.if |
| **Назначение** | Check operator found |

**Условия:**
- `$json.id` exists (не пустой)
- `$json.is_active` === true

**Ветки:**
- TRUE → Postgres - Verify Password
- FALSE → Response - Not Found (401)

---

### 4. Postgres - Verify Password

| Параметр | Значение |
|----------|----------|
| **ID** | `4949de6e-ec75-45ba-bb06-e0071b938659` |
| **Тип** | n8n-nodes-base.postgres |
| **Назначение** | Проверка пароля через PostgreSQL crypt |

**SQL запрос:**
```sql
SELECT
  '{{ $('Postgres - Find Operator').item.json.id }}' as operator_id,
  '{{ $('Postgres - Find Operator').item.json.tenant_id }}' as tenant_id,
  '{{ $('Postgres - Find Operator').item.json.name }}' as name,
  '{{ $('Postgres - Find Operator').item.json.email }}' as email,
  '{{ $('Postgres - Find Operator').item.json.location_id }}' as location_id,
  (password_hash = crypt('{{ $('Webhook - Auth Login').item.json.body.password }}', password_hash)) as password_valid
FROM operators
WHERE id = '{{ $('Postgres - Find Operator').item.json.id }}'
LIMIT 1;
```

**Таблица:** `operators`

**Что делаем:** Сравниваем пароль используя PostgreSQL функцию `crypt()`

---

### 5. IF - Password Valid

| Параметр | Значение |
|----------|----------|
| **ID** | `a635e3c3-7538-42f0-a9c9-64d812cb07da` |
| **Тип** | n8n-nodes-base.if |
| **Назначение** | Проверка результата верификации пароля |

**Условие:** `$json.password_valid` === true

**Ветки:**
- TRUE → Parse Device Info
- FALSE → Response - Invalid Password (401)

---

### 6. Parse Device Info

| Параметр | Значение |
|----------|----------|
| **ID** | `539d6a22-3f9d-4858-bf26-fbb37903dd61` |
| **Тип** | n8n-nodes-base.code |
| **Назначение** | Извлечение информации об устройстве |

**Код:**
```javascript
const body = $('Webhook - Auth Login').first().json.body || {};
const operatorData = $input.first().json;
const deviceInfo = body.device_info || null;
const deviceId = deviceInfo?.device_id || null;
const deviceInfoJson = deviceInfo ? JSON.stringify(deviceInfo) : null;

return {
  ...operatorData,
  device_info: deviceInfo,
  device_id: deviceId,
  device_info_json: deviceInfoJson
};
```

---

### 7. Delete Old Mobile Session

| Параметр | Значение |
|----------|----------|
| **ID** | `84d5f55f-14a3-4de0-a9d6-af4b1289d584` |
| **Тип** | n8n-nodes-base.postgres |
| **Назначение** | Удаление старой мобильной сессии |

**SQL запрос:**
```sql
DELETE FROM operator_devices
WHERE operator_id = '{{ $json.operator_id }}'::uuid
  AND device_type = 'mobile'
  AND tenant_id = '{{ $json.tenant_id }}'::uuid;

SELECT
  '{{ $json.operator_id }}' as operator_id,
  '{{ $json.tenant_id }}' as tenant_id,
  '{{ $json.name }}' as name,
  '{{ $json.email }}' as email,
  '{{ $json.location_id }}' as location_id,
  '{{ $json.device_id }}' as device_id,
  '{{ $json.device_info_json }}' as device_info_json;
```

**Таблица:** `operator_devices`

**Что делаем:** Удаляем все старые мобильные сессии этого оператора (single session policy)

---

### 8. Generate Session Token

| Параметр | Значение |
|----------|----------|
| **ID** | `bc956648-cb69-4b52-9624-22023d09c9f6` |
| **Тип** | n8n-nodes-base.code |
| **Назначение** | Генерация уникального токена сессии |

**Код:**
```javascript
const inputData = $input.first().json;
const crypto = require('crypto');
const sessionToken = crypto.randomUUID();

return {
  operator_id: inputData.operator_id,
  tenant_id: inputData.tenant_id,
  name: inputData.name,
  email: inputData.email,
  location_id: inputData.location_id,
  device_id: inputData.device_id,
  device_info_json: inputData.device_info_json,
  session_token: sessionToken
};
```

---

### 9. Insert into operator_devices

| Параметр | Значение |
|----------|----------|
| **ID** | `00c88884-4421-4302-8027-7ea07b28340e` |
| **Тип** | n8n-nodes-base.postgres |
| **Назначение** | Создание новой сессии |

**SQL запрос:**
```sql
INSERT INTO operator_devices (
  operator_id,
  tenant_id,
  device_type,
  session_token,
  fcm_token,
  device_id,
  device_info,
  created_at,
  last_active_at
) VALUES (
  '{{ $json.operator_id }}'::uuid,
  '{{ $json.tenant_id }}'::uuid,
  'mobile',
  '{{ $json.session_token }}',
  NULL,
  {{ $json.device_id && $json.device_id !== 'null' ? "'" + $json.device_id + "'" : "NULL" }},
  {{ $json.device_info_json ? "'" + $json.device_info_json + "'::jsonb" : "NULL" }},
  NOW(),
  NOW()
) RETURNING id;
```

**Таблица:** `operator_devices`

**Что записываем:**
- `operator_id` — UUID оператора
- `tenant_id` — UUID тенанта
- `device_type` — 'mobile'
- `session_token` — сгенерированный UUID
- `device_info` — JSON с информацией об устройстве

---

### 10. Prepare Response Data

| Параметр | Значение |
|----------|----------|
| **ID** | `ee33b662-aed4-4e6e-b0a6-afc9dfbd7a05` |
| **Тип** | n8n-nodes-base.code |
| **Назначение** | Подготовка данных для ответа |

**Код:**
```javascript
const operatorData = $('Parse Device Info').first().json;
const sessionToken = $('Generate Session Token').first().json.session_token;

return {
  operator_id: operatorData.operator_id,
  tenant_id: operatorData.tenant_id,
  name: operatorData.name,
  email: operatorData.email,
  location_id: operatorData.location_id,
  session_token: sessionToken
};
```

---

### 11. Response - Success

| Параметр | Значение |
|----------|----------|
| **ID** | `faa17d5c-871a-48fb-9041-de8a14b48a71` |
| **Тип** | n8n-nodes-base.respondToWebhook |
| **Назначение** | Успешный ответ |

**Response:** 200 OK

```json
{
  "success": true,
  "operator_id": "{{ $json.operator_id }}",
  "tenant_id": "{{ $json.tenant_id }}",
  "name": "{{ $json.name }}",
  "email": "{{ $json.email }}",
  "location_id": "{{ $json.location_id }}",
  "session_token": "{{ $json.session_token }}"
}
```

**Headers:**
- `Content-Type: application/json`
- `Access-Control-Allow-Origin: *`

---

### 12. Response - Not Found

| Параметр | Значение |
|----------|----------|
| **ID** | `5f231342-8ba6-4d7e-a309-f778567781a4` |
| **Тип** | n8n-nodes-base.respondToWebhook |
| **Назначение** | Ошибка: оператор не найден |

**Response:** 401

```json
{
  "success": false,
  "error": "operator_not_found_or_inactive"
}
```

---

### 13. Response - Invalid Password

| Параметр | Значение |
|----------|----------|
| **ID** | `532199be-cee9-41b1-86ac-c20399dbee45` |
| **Тип** | n8n-nodes-base.respondToWebhook |
| **Назначение** | Ошибка: неверный пароль |

**Response:** 401

```json
{
  "success": false,
  "error": "invalid_password"
}
```

---

## Схема потока

```
┌─────────────────────┐
│ Webhook - Auth      │
│ POST /android/auth  │
│ /login              │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Postgres - Find     │
│ Operator            │
│ (by email/username) │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ IF - Operator       │
│ Exists & Active?    │
└──────────┬──────────┘
      YES  │  NO
      ┌────┴────┐
      ▼         ▼
┌───────────┐ ┌───────────┐
│ Verify    │ │ Response  │
│ Password  │ │ Not Found │
│ (crypt)   │ │ 401       │
└─────┬─────┘ └───────────┘
      │
      ▼
┌─────────────────────┐
│ IF - Password       │
│ Valid?              │
└──────────┬──────────┘
      YES  │  NO
      ┌────┴────┐
      ▼         ▼
┌───────────┐ ┌───────────┐
│ Parse     │ │ Response  │
│ Device    │ │ Invalid   │
│ Info      │ │ Password  │
└─────┬─────┘ │ 401       │
      │       └───────────┘
      ▼
┌─────────────────────┐
│ Delete Old Mobile   │
│ Session             │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Generate Session    │
│ Token (UUID)        │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Insert into         │
│ operator_devices    │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Prepare Response    │
│ Data                │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Response - Success  │
│ 200 OK              │
└─────────────────────┘
```

---

## Таблицы БД

| Таблица | Операция | Назначение |
|---------|----------|------------|
| `operators` | SELECT | Поиск оператора, проверка пароля |
| `operator_devices` | DELETE | Удаление старых сессий |
| `operator_devices` | INSERT | Создание новой сессии |

---

## Зависимости

| Тип | Название | ID | Назначение |
|-----|----------|-----|------------|
| Credentials | Postgres account | n2SyhP9QhMnp1ryk | Доступ к БД |

---

## Безопасность

- Пароль проверяется через PostgreSQL `crypt()` — не передаётся в открытом виде в логи
- Single session policy: старая мобильная сессия удаляется при новом входе
- Session token — UUID v4, криптографически стойкий

---

## Примечание для миграции на ELO

**Старые таблицы → Новые:**
- `operators` → `elo_operators`
- `operator_devices` → `elo_operator_sessions`

**Изменения схемы:**
- Добавить `type_id` (FK на `elo_operator_types`) для различия human/ai
