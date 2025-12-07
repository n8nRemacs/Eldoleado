---
doc_key: appeal_detail
doc_type: action
entry_workflow: API_Android_Appeal_Detail
participating_workflows:
  - API_Android_Appeal_Detail
  - Tool - Build Appeal Meta
generated: 2025-12-07 16:20:00
---

# Действие: Детали обращения

**Детали обращения**

*Сгенерировано: 2025-12-07 16:20:00*

---

## Сводка

- **Точка входа:** `API_Android_Appeal_Detail`
- **Workflows в цепочке:** 2
- **Операций с БД:** 4
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 18

## Цепочка Workflows

```
API_Android_Appeal_Detail (/api/android/appeals/:id)
  -> Tool - Build Appeal Meta
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `appeal_data` | чтение |
| `appeal_devices` | **изменение** |
| `appeal_info` | чтение |
| `appeal_meta_config` | чтение |
| `appeal_meta_visibility` | чтение |
| `appeal_repairs` | **изменение** |
| `appeals` | чтение, **изменение** |
| `brands` | чтение, **изменение** |
| `clients` | чтение, **изменение** |
| `deal_types` | чтение, **изменение** |
| `issue_types` | **изменение** |
| `messages_history` | **изменение** |
| `models` | чтение, **изменение** |
| `operator_actions` | **изменение** |
| `operator_devices` | чтение |
| `operators` | чтение |
| `repair_categories` | **изменение** |
| `repair_types` | чтение, **изменение** |

## Операции с базой данных

### Get Tenant

**Workflow:** API_Android_Appeal_Detail

**Операция:** Чтение из `operators`, `operator_devices`

```sql
SELECT od.operator_id, od.tenant_id 
FROM operator_devices od
WHERE od.session_token = '{{ $('Webhook').item.json.headers["x-session-token"] }}'
AND od.device_type = 'mobile'
AND EXISTS (
  SELECT 1 FROM operators o 
  WHERE o.id = od.operator_id 
  AND o.is_active = true
)
LIMIT 1;
```

### Fetch Appeal & History

**Workflow:** API_Android_Appeal_Detail

**Операция:** Изменение из `models`, `operator_actions`, `repair_categories`

```sql
WITH vals AS (
SELECT
'{{ $json.tenant_id || "" }}' AS tenant_id_text,
'{{ $json.appeal_id || "" }}' AS appeal_id_text,
'{{ $json.limit || 50 }}' AS limit_text
),
validated AS (
SELECT
CASE WHEN tenant_id_text ~* '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$' THEN tenant_id_text::uuid ELSE NULL END AS tenant_id,
CASE WHEN appeal_id_text ~* '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$' THEN appeal_id_text::uuid ELSE NULL END AS appeal_id,
CASE WHEN limit_text 
```

### Fetch Appeal Data

**Workflow:** Tool - Build Appeal Meta

**Операция:** Чтение из `models`, `deal_types`, `brands`

```sql
WITH appeal_data AS (
  SELECT
    a.id,
    a.stage,
    a.estimated_cost,
    a.estimated_time,
    a.parts_owner,
    a.created_at,
    c.name AS client_name,
    c.phone AS client_phone,
    c.has_telegram,
    c.has_whatsapp,
    c.has_phone,
    c.has_yandex,
    c.has_google,
    c.has_instagram,
    c.has_avito,
    b.name AS brand_name,
    m.name AS model_name,
    dt.name AS deal_type_name,
    rt.name AS repair_type_name,
    NULL AS sales_channel_name,
    NULL AS lead_source_name,

```

### Fetch Meta Config

**Workflow:** Tool - Build Appeal Meta

**Операция:** Чтение из `appeal_info`, `appeal_meta_config`, `appeals`

```sql
WITH appeal_info AS (
  SELECT a.deal_type_id
  FROM appeals a
  WHERE a.id = '{{ $json.appeal_id }}'::uuid
    AND a.tenant_id = '{{ $json.tenant_id }}'::uuid
  LIMIT 1
)
SELECT 
  amc.id, 
  amc.label, 
  amc."group", 
  amc."order", 
  amc.source_field, 
  amc.format_type
FROM appeal_meta_config amc
LEFT JOIN appeal_meta_visibility amv 
  ON amv.config_id = amc.id 
  AND amv.deal_type_id = (SELECT deal_type_id FROM appeal_info)
  AND amv.tenant_id = '{{ $json.tenant_id }}'::uuid
WHERE amc.is_
```
