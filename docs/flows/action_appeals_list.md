---
doc_key: appeals_list
doc_type: action
entry_workflow: API_Android_Appeals_List
participating_workflows:
  - API_Android_Appeals_List
generated: 2025-12-09 15:39:40
---

# Действие: Список обращений

**Список обращений**

*Сгенерировано: 2025-12-09 15:39:40*

---

## Сводка

- **Точка входа:** `API_Android_Appeals_List`
- **Workflows в цепочке:** 1
- **Операций с БД:** 3
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 7

## Цепочка Workflows

```
API_Android_Appeals_List (/api/operator/appeals/list)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `appeals` | чтение, **изменение** |
| `brands` | **изменение** |
| `clients` | **изменение** |
| `messages_history` | **изменение** |
| `models` | **изменение** |
| `operator_devices` | чтение |
| `operators` | чтение |

## Операции с базой данных

### Fetch Appeals

**Workflow:** API_Android_Appeals_List

**Операция:** Изменение из `appeals`, `messages_history`, `clients`

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
      WHEN 'new'         THEN 'ÐŸÐµÑ€Ð²Ð¸Ñ‡Ð½Ñ‹Ð¹ ÐºÐ¾Ð½Ñ‚Ð°ÐºÑ‚'
      WHEN 'in_progre
```

### Fetch Counts

**Workflow:** API_Android_Appeals_List

**Операция:** Чтение из `appeals`

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
  COALESCE(SUM((a.stage = 'ÐŸÐµÑ€Ð²Ð¸Ñ‡Ð½Ñ‹Ð¹ ÐºÐ¾Ð½Ñ‚Ð°ÐºÑ‚')::int), 0) AS new,
  COALESCE(SUM((a.stage = 'Ð¢Ñ€ÐµÐ±ÑƒÐµÑ‚ÑÑ ÑƒÑ‚Ð¾Ñ‡Ð½ÐµÐ½Ð¸Ðµ')::int), 0) AS in_progress,
  COALESCE(SUM((
```

### Get Tenant Ð¿Ð¾ operator_id

**Workflow:** API_Android_Appeals_List

**Операция:** Чтение из `operator_devices`, `operators`

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
