# API_Android_Appeals_List

> Appeals list for operator (Android app)

---

## General Information

| Parameter | Value |
|----------|----------|
| **Файл** | `NEW/workflows/n8n_old/API/API_Android_Appeals_List.json` |
| **ID в n8n** | 0t7CnDCcHA4MnmTp |
| **Триггер** | Webhook GET `/api/operator/appeals/list` |
| **Вызывается из** | Android App |
| **Вызывает** | — |
| **Выход** | HTTP Response (appeals list + counts) |

---

## Purpose

Returns appeals list for authorized operator with status filter and counters for UI.

---

## Input Data

**Source:** HTTP GET from Android App

**Headers:**
```
x-session-token: <session_token>
```

**Query params:**
```
?status=new&limit=20
```

| Parameter | Type | Description | Default |
|----------|-----|----------|---------|
| `status` | string | Фильтр: `new`, `in_progress`, `completed` | все |
| `limit` | int | Макс. количество | 20 (max 100) |

---

## Output Data

**Success (200):**
```json
{
  "success": true,
  "appeals": [
    {
      "id": "uuid",
      "client": {
        "name": "Иван Петров",
        "phone": "+79001234567"
      },
      "device": {
        "brand": "Apple",
        "model": "iPhone 14 Pro"
      },
      "problem": "Запчасть: клиент",
      "status": "Первичный контакт",
      "last_message": "Здравствуйте, нужен ремонт",
      "updated_at": "2024-12-10T10:30:00Z"
    }
  ],
  "counts": {
    "new": 5,
    "in_progress": 3,
    "completed": 12
  }
}
```

**Error (401):**
```json
{
  "success": false,
  "error": "unauthorized_or_operator_not_found"
}
```

---

## Input node

**Webhook**
- Type: `n8n-nodes-base.webhook`
- Method: GET
- Path: `/api/operator/appeals/list`
- Response Mode: responseNode
- CORS: `*`

---

## Output nodes

1. **Respond to Webhook** (200 OK)
2. **Respond 401** (unauthorized)

---

## Nodes

### 1. Webhook

| Parameter | Value |
|----------|----------|
| **ID** | `3a93ea19-8f72-472e-a3a2-2153e857fb1b` |
| **Тип** | n8n-nodes-base.webhook |
| **Назначение** | Handle GET request |

---

### 2. Parse Query

| Parameter | Value |
|----------|----------|
| **ID** | `0a30cacd-1dc6-47ed-b3e5-3ba33d88fd5b` |
| **Тип** | n8n-nodes-base.code |
| **Назначение** | Parse query parameters and header |

**Код:**
```javascript
const q = $json.query || {};
const status = (q.status || '').trim();
const limit = Math.max(1, Math.min(parseInt(q.limit || '20', 10) || 20, 100));
const session_token = $json.headers['x-session-token'] || $json.headers['X-Session-Token'] || '';

return {
  status,
  limit,
  session_token
};
```

**Output:**
```json
{
  "status": "new",
  "limit": 20,
  "session_token": "uuid-token"
}
```

---

### 3. Get Tenant по operator_id

| Параметр | Значение |
|----------|----------|
| **ID** | `61dda237-5ec6-4ec5-81aa-22e0f1a99d24` |
| **Тип** | n8n-nodes-base.postgres |
| **Назначение** | Получение tenant_id по session_token |

**SQL запрос:**
```sql
SELECT
  od.operator_id,
  od.tenant_id,
  od.device_type
FROM operator_devices od
JOIN operators o ON o.id = od.operator_id
WHERE od.session_token = '{{ $json.session_token }}'
  AND od.device_type = 'mobile'
  AND o.is_active = true
LIMIT 1;
```

**Таблицы:** `operator_devices`, `operators`

**Что ищем:** tenant_id по session_token, проверяем что оператор активен

---

### 4. Normalize Tenant Result

| Параметр | Значение |
|----------|----------|
| **ID** | `c18140d8-6e5c-450a-bfe6-ea968be63950` |
| **Тип** | n8n-nodes-base.code |
| **Назначение** | Нормализация результата SQL |

**Код:**
```javascript
const r = $input.first()?.json;
const row = Array.isArray(r) ? r[0] : r;
return { tenant_id: row?.tenant_id || null, ...($json || {}) };
```

---

### 5. Tenant Found?

| Параметр | Значение |
|----------|----------|
| **ID** | `60de4592-bb75-49b1-a541-0af78ce0f6a8` |
| **Тип** | n8n-nodes-base.if |
| **Назначение** | Проверка авторизации |

**Условия:**
- `$json.tenant_id` not empty
- `$json.tenant_id` !== 'null'

**Ветки:**
- TRUE → Fetch Appeals + Fetch Counts (параллельно)
- FALSE → Respond 401

---

### 6. Fetch Counts

| Параметр | Значение |
|----------|----------|
| **ID** | `55619512-29e2-42ba-820f-4376c96b6683` |
| **Тип** | n8n-nodes-base.postgres |
| **Назначение** | Подсчёт количества по статусам |

**SQL запрос:**
```sql
WITH vals AS (
  SELECT '{{ $json.tenant_id || "" }}' AS tenant_id_text
),
validated AS (
  SELECT CASE
           WHEN tenant_id_text ~* '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$'
             THEN tenant_id_text::uuid
           ELSE NULL
         END AS tenant_id
  FROM vals
)
SELECT
  COALESCE(SUM((a.stage = 'Первичный контакт')::int), 0) AS new,
  COALESCE(SUM((a.stage = 'Требуется уточнение')::int), 0) AS in_progress,
  COALESCE(SUM((a.stage = 'Завершено')::int), 0) AS completed
FROM validated v
LEFT JOIN appeals a
  ON v.tenant_id IS NOT NULL
 AND a.tenant_id = v.tenant_id;
```

**Таблица:** `appeals`

**Что считаем:** Количество appeals по каждому статусу для badges в UI

---

### 7. Fetch Appeals

| Параметр | Значение |
|----------|----------|
| **ID** | `9975fa1d-373b-40e1-851f-595ff7a97d87` |
| **Тип** | n8n-nodes-base.postgres |
| **Назначение** | Получение списка appeals |

**SQL запрос:**
```sql
WITH vals AS (
  SELECT
    '{{ $json.tenant_id || "" }}'      AS tenant_id_text,
    '{{ $json.status || "" }}'         AS status_text,
    '{{ $json.limit  || "20" }}'       AS limit_text
),
validated AS (
  SELECT
    CASE
      WHEN tenant_id_text ~* '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$'
        THEN tenant_id_text::uuid
      ELSE NULL
    END AS tenant_id,
    CASE status_text
      WHEN 'new'         THEN 'Первичный контакт'
      WHEN 'in_progress' THEN 'Требуется уточнение'
      WHEN 'completed'   THEN 'Завершено'
      ELSE NULL
    END AS stage_filter,
    CASE
      WHEN limit_text ~ '^[0-9]+$' THEN LEAST(GREATEST(limit_text::int, 1), 100)
      ELSE 20
    END AS lim
  FROM vals
)
SELECT
  a.id,
  a.stage,
  a.updated_at,
  c.name  AS client_name,
  c.phone AS client_phone,
  b.name  AS brand_name,
  m.name  AS model_name,
  a.phone_model,
  a.parts_owner,
  a.estimated_cost,
  a.estimated_time,
  (
    SELECT mh.message_text
    FROM messages_history mh
    WHERE mh.appeal_id = a.id
    ORDER BY mh.created_at DESC
    LIMIT 1
  ) AS last_message
FROM validated v
JOIN appeals a
  ON v.tenant_id IS NOT NULL
 AND a.tenant_id = v.tenant_id
JOIN clients c
  ON c.id = a.client_id AND c.tenant_id = a.tenant_id
LEFT JOIN brands b
  ON b.id = a.brand_id AND b.tenant_id = a.tenant_id
LEFT JOIN models m
  ON m.id = a.model_id AND m.tenant_id = a.tenant_id
WHERE (v.stage_filter IS NULL OR a.stage = v.stage_filter)
ORDER BY a.updated_at DESC
LIMIT (SELECT lim FROM validated);
```

**Таблицы:** `appeals`, `clients`, `brands`, `models`, `messages_history`

**Что получаем:**
- Список appeals с фильтром по статусу
- Информация о клиенте (name, phone)
- Информация об устройстве (brand, model)
- Последнее сообщение из messages_history

---

### 8. Edit Fields (каунты)

| Параметр | Значение |
|----------|----------|
| **ID** | `4b96ffef-5c92-4838-bd58-514b09aa3fb1` |
| **Тип** | n8n-nodes-base.set |
| **Назначение** | Добавление поля join для Merge |

Добавляет `join: 1` для объединения с appeals.

---

### 9. Pack Appeals

| Параметр | Значение |
|----------|----------|
| **ID** | `29f83fea-53be-482a-b0f1-563b502e6d15` |
| **Тип** | n8n-nodes-base.code |
| **Назначение** | Упаковка списка appeals |

**Код:**
```javascript
const items = $input.all().map(i => i.json);
return { __join: 1, appeals_raw: items };
```

---

### 10. Merge

| Параметр | Значение |
|----------|----------|
| **ID** | `042a5a6a-f1dd-48c9-b6f4-9261ab1e36ff` |
| **Тип** | n8n-nodes-base.merge |
| **Назначение** | Объединение counts и appeals |

**Mode:** Combine (по полю join)

**Входы:**
- Input 1: Counts (с `join: 1`)
- Input 2: Appeals (с `__join: 1`)

---

### 11. Format Response

| Параметр | Значение |
|----------|----------|
| **ID** | `d14a5088-af26-4e74-bd52-815dbe7f55c5` |
| **Тип** | n8n-nodes-base.code |
| **Назначение** | Форматирование финального ответа |

**Код:**
```javascript
const listRows = Array.isArray($json.appeals_raw) ? $json.appeals_raw : [];
const counts = {
  new: Number($json.new || 0),
  in_progress: Number($json.in_progress || 0),
  completed: Number($json.completed || 0),
};
const appeals = listRows.map(r => ({
  id: r.id,
  client: { name: r.client_name ?? null, phone: r.client_phone ?? null },
  device: { model: r.model_name ?? r.phone_model ?? null, brand: r.brand_name ?? null },
  problem: r.parts_owner ? `Запчасть: ${r.parts_owner}` : null,
  status: r.stage,
  last_message: r.last_message || '',
  updated_at: r.updated_at,
}));
return [{ success: true, appeals, counts }];
```

---

### 12. Respond to Webhook

| Параметр | Значение |
|----------|----------|
| **ID** | `08f9f054-18f9-48d0-a20a-7bf48a259c3a` |
| **Тип** | n8n-nodes-base.respondToWebhook |
| **Назначение** | Успешный ответ |

**Response:** 200 OK

**Headers:**
- `Access-Control-Allow-Origin: *`

---

### 13. Respond 401

| Параметр | Значение |
|----------|----------|
| **ID** | `cbfd9b65-0098-4c96-bddf-e8a13171f92c` |
| **Тип** | n8n-nodes-base.respondToWebhook |
| **Назначение** | Ошибка авторизации |

**Response:** 401

```json
{
  "success": false,
  "error": "unauthorized_or_operator_not_found"
}
```

---

## Схема потока

```
┌─────────────────────┐
│ Webhook             │
│ GET /api/operator/  │
│ appeals/list        │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Parse Query         │
│ (status, limit,     │
│  session_token)     │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Get Tenant by       │
│ session_token       │
│ (operator_devices)  │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Normalize Tenant    │
│ Result              │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ Tenant Found?       │
└──────────┬──────────┘
      YES  │  NO
      ┌────┴────┐
      │         ▼
      │    ┌───────────┐
      │    │ Respond   │
      │    │ 401       │
      │    └───────────┘
      │
      ├──────────────────┐
      │ (parallel)       │
      ▼                  ▼
┌───────────┐     ┌───────────┐
│ Fetch     │     │ Fetch     │
│ Counts    │     │ Appeals   │
└─────┬─────┘     └─────┬─────┘
      │                 │
      ▼                 ▼
┌───────────┐     ┌───────────┐
│ Edit      │     │ Pack      │
│ Fields    │     │ Appeals   │
│ (join:1)  │     │ (__join:1)│
└─────┬─────┘     └─────┬─────┘
      │                 │
      └────────┬────────┘
               │
               ▼
      ┌─────────────────┐
      │ Merge           │
      │ (combine)       │
      └────────┬────────┘
               │
               ▼
      ┌─────────────────┐
      │ Format Response │
      └────────┬────────┘
               │
               ▼
      ┌─────────────────┐
      │ Respond 200 OK  │
      └─────────────────┘
```

---

## Таблицы БД

| Таблица | Операция | Назначение |
|---------|----------|------------|
| `operator_devices` | SELECT | Получение tenant_id по session_token |
| `operators` | JOIN | Проверка is_active |
| `appeals` | SELECT | Список обращений + COUNT |
| `clients` | JOIN | Имя и телефон клиента |
| `brands` | LEFT JOIN | Название бренда |
| `models` | LEFT JOIN | Название модели |
| `messages_history` | Subquery | Последнее сообщение |

---

## Паттерны

### 1. Авторизация через session_token

```
Header: x-session-token → operator_devices.session_token → tenant_id
```

### 2. Параллельные запросы + Merge

```
IF True
  ├── Fetch Counts → Edit Fields (join:1)
  └── Fetch Appeals → Pack Appeals (__join:1)
        │
        ▼
      Merge (combine by join field)
```

### 3. Маппинг статусов

| API статус | БД статус (stage) |
|------------|-------------------|
| `new` | Первичный контакт |
| `in_progress` | Требуется уточнение |
| `completed` | Завершено |

---

## Зависимости

| Тип | Название | ID | Назначение |
|-----|----------|-----|------------|
| Credentials | Postgres account | n2SyhP9QhMnp1ryk | Доступ к БД |

---

## Примечание для миграции на ELO

**Старые таблицы → Новые:**
- `appeals` → `elo_dialogs`
- `clients` → `elo_clients`
- `brands`, `models` → убрать (устройство в Neo4j)
- `messages_history` → `elo_messages`
- `operator_devices` → `elo_operator_sessions`

**Изменения:**
- Статусы: вместо русских строк → `elo_dialog_statuses.code`
- Устройство: из Neo4j (ELO_Device)
- last_message: из `elo_messages`
