---
doc_key: reject
doc_type: action
entry_workflow: API_Android_Reject
participating_workflows:
  - API_Android_Reject
generated: 2025-12-07 16:20:00
---

# Действие: Отклонить обращение

**Отклонить обращение**

*Сгенерировано: 2025-12-07 16:20:00*

---

## Сводка

- **Точка входа:** `API_Android_Reject`
- **Workflows в цепочке:** 1
- **Операций с БД:** 3
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 5

## Цепочка Workflows

```
API_Android_Reject (/api/android/appeals/:id/reject)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `operator_actions` | **создание** |
| `operator_appeal_status` | **создание** |
| `operator_devices` | чтение |
| `operators` | чтение |
| `set` | **создание** |

## Операции с базой данных

### Validate Token

**Workflow:** API_Android_Reject

**Операция:** Чтение из `operators`, `operator_devices`

```sql
SELECT 
  od.operator_id,
  od.tenant_id
FROM operator_devices od
JOIN operators o ON o.id = od.operator_id
WHERE od.session_token = '{{ $json.session_token }}'
  AND od.device_type = 'mobile'
  AND o.is_active = true
LIMIT 1;
```

### Log Action

**Workflow:** API_Android_Reject

**Операция:** Создание из `operator_actions`

```sql
WITH vals AS (
  SELECT
    '{{ $json.tenant_id }}'::uuid AS tenant_id,
    '{{ $json.operator_id }}'::uuid AS operator_id,
    '{{ $json.appeal_id }}'::uuid AS appeal_id,
    '{{ $json.reject_reason || "" }}' AS reject_reason
)
INSERT INTO operator_actions (
  tenant_id,
  operator_id,
  operator_telegram_id,
  action_type,
  action_source,
  context_appeal_id,
  details
)
SELECT
  tenant_id,
  operator_id,
  NULL,
  'reject_appeal',
  'mini_app',
  appeal_id,
  jsonb_build_object(
    'appeal_
```

### Update Status

**Workflow:** API_Android_Reject

**Операция:** Создание из `set`, `operator_appeal_status`

```sql
WITH vals AS (
  SELECT
    '{{ $('Normalize Tenant').item.json.tenant_id }}'::uuid AS tenant_id,
    '{{ $('Normalize Tenant').item.json.operator_id }}'::uuid AS operator_id,
    '{{ $('Normalize Tenant').item.json.appeal_id }}'::uuid AS appeal_id
)
INSERT INTO operator_appeal_status (
  tenant_id,
  operator_id,
  appeal_id,
  status
)
SELECT
  tenant_id,
  operator_id,
  appeal_id,
  'rejected'
FROM vals
ON CONFLICT (tenant_id, appeal_id, operator_id)
DO UPDATE SET
  status = 'rejected',
  up
```
