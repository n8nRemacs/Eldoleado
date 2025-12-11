# ELO_Core_Tenant_Resolver

> Determines tenant by channel credentials

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `rRO6sxLqiCdgvLZz` |
| **Файл** | `NEW/workflows/n8n_old/Core/ELO_Core_Tenant_Resolver.json` |
| **Триггер** | Execute Workflow Trigger |
| **Вызывается из** | Все BAT_IN_* workflows |
| **Выход** | Data + tenant_id |

---

## Purpose

Determines which tenant an incoming message belongs to via channel credentials:
- Telegram → by `bot_token`
- VK → by `app_id`
- WhatsApp → by `phone_id`
- Avito → by `client_id`

---

## Input Data

```json
{
  "channel": "telegram",
  "bot_token": "123456:ABC-DEF...",
  "text": "Привет",
  "external_chat_id": "tg_123456",
  ...
}
```

---

## Output Data

```json
{
  "channel": "telegram",
  "bot_token": "123456:ABC-DEF...",
  "text": "Привет",
  "external_chat_id": "tg_123456",
  "tenant_id": "a1b2c3d4-...",
  "tenant_name": "Ремонт телефонов",
  "tenant_config": {...}
}
```

---

## Nodes

### 1. When Executed by Another Workflow

| Parameter | Value |
|----------|----------|
| **ID** | `13b5f187-9f26-4bdb-9ad7-09424b7dca1e` |
| **Тип** | executeWorkflowTrigger |
| **Mode** | passthrough |

---

### 2. Prepare Lookup

| Parameter | Value |
|----------|----------|
| **ID** | `6755978f-3f9a-46b3-a5f6-158d82cbf74e` |
| **Тип** | n8n-nodes-base.code |

**Code:**
```javascript
const data = $input.first().json;
const channel = data.channel;

let lookupKey = '';
let lookupValue = '';

if (channel === 'telegram' && data.bot_token) {
  lookupKey = 'telegram_bot_token';
  lookupValue = data.bot_token;
} else if (channel === 'vk' && data.app_id) {
  lookupKey = 'vk_app_id';
  lookupValue = data.app_id;
} else if (channel === 'whatsapp' && data.phone_id) {
  lookupKey = 'whatsapp_phone_id';
  lookupValue = data.phone_id;
} else if (channel === 'avito' && data.client_id) {
  lookupKey = 'avito_client_id';
  lookupValue = data.client_id;
}

const needsDefaultTenant = !lookupKey || !lookupValue;

return {
  ...data,
  lookup_key: lookupKey,
  lookup_value: lookupValue,
  needs_default_tenant: needsDefaultTenant
};
```

**Mapping channel → lookup_key:**

| Channel | Field in data | lookup_key in tenant_configs |
|---------|---------------|----------------------------|
| telegram | bot_token | telegram_bot_token |
| vk | app_id | vk_app_id |
| whatsapp | phone_id | whatsapp_phone_id |
| avito | client_id | avito_client_id |

---

### 3. Skip DB Lookup?

| Parameter | Value |
|----------|----------|
| **ID** | `796b3841-439d-42c0-a0d9-fd34732004a9` |
| **Условие** | `needs_default_tenant === true` |

- TRUE → Use Default Tenant
- FALSE → Find Tenant (SQL)

---

### 4. Find Tenant

| Параметр | Значение |
|----------|----------|
| **ID** | `138033dd-7be2-4660-a6fa-668d6ef111f8` |
| **Тип** | n8n-nodes-base.postgres |

**SQL запрос:**
```sql
SELECT
  t.id as tenant_id,
  t.name as tenant_name,
  t.is_active,
  tc.value as config
FROM tenants t
LEFT JOIN tenant_configs tc ON tc.tenant_id = t.id
WHERE tc.key = '{{ $json.lookup_key }}'
  AND tc.value->>'token' = '{{ $json.lookup_value }}'
  AND t.is_active = true
LIMIT 1;
```

**Таблицы:**
- `tenants` — список тенантов
- `tenant_configs` — конфигурации (key-value, JSONB)

---

### 5. Tenant Found?

| Параметр | Значение |
|----------|----------|
| **ID** | `3b6f8c98-14b3-4425-953d-dbf8f77f11b4` |
| **Условие** | `tenant_id` is not empty |

- TRUE → Attach Tenant From DB
- FALSE → Use Default Tenant

---

### 6. Use Default Tenant

```javascript
const originalData = $('When Executed by Another Workflow').first().json;

return {
  ...originalData,
  tenant_id: 'a0000000-0000-0000-0000-000000000001',
  tenant_name: 'Default Tenant',
  tenant_config: {}
};
```

**Default Tenant ID:** `a0000000-0000-0000-0000-000000000001`

---

### 7. Attach Tenant From DB

```javascript
const tenantData = $input.first().json;
const originalData = $('When Executed by Another Workflow').first().json;

return {
  ...originalData,
  tenant_id: tenantData.tenant_id,
  tenant_name: tenantData.tenant_name,
  tenant_config: tenantData.config || {}
};
```

---

## Схема потока

```
Execute Trigger → Prepare Lookup → Skip DB Lookup?
                                        ├── YES → Use Default Tenant ─────┐
                                        └── NO → Find Tenant (SQL)        │
                                                      ↓                    │
                                                Tenant Found?              │
                                                  ├── YES → Attach Tenant ─┼─→ Return
                                                  └── NO → Use Default ────┘
```

---

## Миграция на ELO

| Старое | Новое |
|--------|-------|
| `tenants` | `elo_tenants` |
| `tenant_configs` | `elo_tenant_configs` или JSON в elo_tenants |

---

## Зависимости

| Тип | ID | Назначение |
|-----|-----|------------|
| Postgres | n2SyhP9QhMnp1ryk | БД |
