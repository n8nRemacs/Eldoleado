# ELO_Core_Client_Resolver

> Finds or creates a client, passes to Core (Appeal Manager)

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `OUjTFzd7k4tdoAjh` |
| **Файл** | `NEW/workflows/n8n_old/Core/ELO_Core_Client_Resolver.json` |
| **Триггер** | Execute Workflow Trigger |
| **Вызывается из** | ELO_Core_Batch_Debouncer, ELO_In_Form, ELO_In_Phone |
| **Выход** | → Appeal Manager (Core) |

---

## Purpose

1. Looks up client by channel identifiers
2. If found — reuse existing
3. If not found — create new
4. Pass to Appeal Manager (Core)

---

## Client lookup

Client is searched by **any** of the identifiers:

| Channel | Field in clients | Field in data |
|-------|---------------|---------------|
| Phone | `phone` | `client_phone` |
| Telegram | `telegram_id` | `external_user_id` (если channel=telegram) |
| VK | `vk_id` | `external_user_id` (если channel=vk) |
| WhatsApp | `whatsapp_id` | `external_user_id` (если channel=whatsapp) |
| Avito | `avito_id` | `external_user_id` (если channel=avito) |

---

## Input Data

```json
{
  "channel": "telegram",
  "external_user_id": "tg_123456",
  "external_chat_id": "tg_123456",
  "text": "Привет\n\nРазбил экран",
  "timestamp": "2024-12-10T10:00:01Z",
  "tenant_id": "a1b2c3d4-...",
  "client_phone": "+79991234567",
  "client_name": "Иван",
  "media": {...},
  "meta": {...}
}
```

---

## Output Data (to Appeal Manager)

```json
{
  "channel": "telegram",
  "external_user_id": "tg_123456",
  "external_chat_id": "tg_123456",
  "text": "Привет\n\nРазбил экран",
  "timestamp": "2024-12-10T10:00:01Z",
  "tenant_id": "a1b2c3d4-...",
  "client": {
    "id": "uuid",
    "phone": "+79991234567",
    "name": "Иван",
    "telegram_id": "tg_123456",
    "vk_id": null,
    "whatsapp_id": null,
    "avito_id": null,
    "was_merged": false
  },
  "client_found": true,
  "media": {...},
  "meta": {...}
}
```

---

## Nodes

### 1. Execute Workflow Trigger

Entry point — called from Debouncer or directly from Form/Phone.

---

### 2. Find Client

| Parameter | Value |
|----------|----------|
| **Тип** | Postgres |

**SQL query:**
```sql
WITH potential_clients AS (
  SELECT * FROM clients
  WHERE tenant_id = '{{ $json.tenant_id }}'
    AND (
      (phone IS NOT NULL AND phone = '{{ $json.client_phone }}')
      OR (telegram_id IS NOT NULL AND telegram_id = '{{ $json.external_user_id }}'
          AND '{{ $json.channel }}' = 'telegram')
      OR (vk_id IS NOT NULL AND vk_id = '{{ $json.external_user_id }}'
          AND '{{ $json.channel }}' = 'vk')
      OR (whatsapp_id IS NOT NULL AND whatsapp_id = '{{ $json.external_user_id }}'
          AND '{{ $json.channel }}' = 'whatsapp')
      OR (avito_id IS NOT NULL AND avito_id = '{{ $json.external_user_id }}'
          AND '{{ $json.channel }}' = 'avito')
    )
  LIMIT 1
),
resolved_client AS (
  SELECT
    COALESCE(master.id, pc.id) as id,
    COALESCE(master.phone, pc.phone) as phone,
    COALESCE(master.name, pc.name) as name,
    COALESCE(master.telegram_id, pc.telegram_id) as telegram_id,
    COALESCE(master.vk_id, pc.vk_id) as vk_id,
    COALESCE(master.whatsapp_id, pc.whatsapp_id) as whatsapp_id,
    COALESCE(master.avito_id, pc.avito_id) as avito_id,
    CASE WHEN cm.id IS NOT NULL THEN true ELSE false END as was_merged
  FROM potential_clients pc
  LEFT JOIN client_merges cm ON pc.id = cm.merged_client_id
  LEFT JOIN clients master ON cm.master_client_id = master.id
)
SELECT * FROM resolved_client;
```

**Note:** Accounts for client merges via `client_merges`.

---

### 3. Prepare Contract

Подготавливает данные для дальнейшей обработки.

```javascript
// Обработка результата SQL
let clientRow = null;

if (sqlResult) {
  if (Array.isArray(sqlResult) && sqlResult.length > 0) {
    clientRow = sqlResult[0];
  } else if (sqlResult.id) {
    clientRow = sqlResult;
  }
}

// Клиент из SQL
const client = clientRow && clientRow.id ? {
  id: clientRow.id,
  phone: clientRow.phone,
  name: clientRow.name,
  telegram_id: clientRow.telegram_id,
  vk_id: clientRow.vk_id,
  whatsapp_id: clientRow.whatsapp_id,
  avito_id: clientRow.avito_id,
  was_merged: clientRow.was_merged || false
} : {};

return {
  ...src,
  client: client,
  client_found: !!(clientRow && clientRow.id)
};
```

---

### 4. Client Exists?

| Условие | Результат |
|---------|-----------|
| `client_found === true` | → Merge Found Client |
| `client_found === false` | → Execute Client Creator |

---

### 5. Merge Found Client

Формирует выходные данные с найденным клиентом:

```javascript
const inputData = $input.first().json;
const client = inputData.client;

return {
  ...inputData,
  client: {
    id: client.id,
    phone: client.phone,
    name: client.name,
    telegram_id: client.telegram_id,
    vk_id: client.vk_id,
    whatsapp_id: client.whatsapp_id,
    avito_id: client.avito_id,
    was_merged: client.was_merged || false
  },
  client_found: true
};
```

---

### 6. Execute Client Creator

| Параметр | Значение |
|----------|----------|
| **Тип** | Execute Workflow |
| **ID** | `vkQwat1iZhJJj7C9` |

Создаёт нового клиента если не найден.

---

### 7. Execute Appeal Manager

| Параметр | Значение |
|----------|----------|
| **Тип** | Execute Workflow |
| **ID** | `L2pYPcv7r8j5XFU3` |

**Это уже Core!** Appeal Manager — логика работы с заявками.

---

### 8. Respond Success

```json
{
  "success": true,
  "client_id": "uuid"
}
```

---

## Схема потока

```
Execute Trigger → Find Client (SQL) → Prepare Contract → Client Exists?
                                                             │
                                        ├── YES → Merge Found Client ─────┐
                                        └── NO → Execute Client Creator ──┤
                                                                          ↓
                                                             Execute Appeal Manager
                                                                          ↓
                                                                  Respond Success
```

---

## Merge клиентов

Система учитывает что клиенты могут быть объединены:

```
client_merges:
  merged_client_id → master_client_id
```

Если клиент был merged → используется master клиент.

---

## Вызовы

**Откуда вызывается:**
- `ELO_Core_Batch_Debouncer` — после склейки сообщений (мессенджеры)
- `ELO_In_Form` — напрямую (без Redis/Debounce)
- `ELO_In_Phone` — напрямую (без Redis/Debounce)

**Куда передаёт:**
- `Client Creator (vkQwat1iZhJJj7C9)` — если клиент не найден
- `Appeal Manager (L2pYPcv7r8j5XFU3)` — основная логика (Core)

---

## Граница Input Contour / Core

```
Input Contour                          Core
─────────────────────────────────────────────────────
Tenant Resolver                        Appeal Manager
Queue Processor                        AI Workers
Batch Debouncer                        Dialog Engine
Client Resolver ──────────────────────→ ...
```

Client Resolver — последний компонент Input Contour.
Appeal Manager — первый компонент Core.

---

## Миграция на ELO

| Старое | Новое |
|--------|-------|
| `clients` | `elo_clients` |
| `client_merges` | (?) `elo_client_links` или флаг |
| `appeals` | `elo_dialogs` (диалог-центричная модель) |

---

## Зависимости

| Тип | ID | Назначение |
|-----|-----|------------|
| Postgres | n2SyhP9QhMnp1ryk | БД |
| Workflow | vkQwat1iZhJJj7C9 | Client Creator |
| Workflow | L2pYPcv7r8j5XFU3 | Appeal Manager (Core) |
