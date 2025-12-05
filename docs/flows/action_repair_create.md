---
doc_key: repair_create
doc_type: action
entry_workflow: API_Android_Repair_Create
participating_workflows:
  - API_Android_Repair_Create
generated: 2025-12-05 15:01:47
---

# Действие: Добавить ремонт

**Добавить ремонт**

*Сгенерировано: 2025-12-05 15:01:47*

---

## Сводка

- **Точка входа:** `API_Android_Repair_Create`
- **Workflows в цепочке:** 1
- **Операций с БД:** 2
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 4

## Цепочка Workflows

```
API_Android_Repair_Create (/android/appeal-repairs)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `appeal_devices` | **создание** |
| `appeal_repairs` | **создание** |
| `operator_devices` | чтение |
| `operators` | чтение |

## Операции с базой данных

### Get Tenant

**Workflow:** API_Android_Repair_Create

**Операция:** Чтение из `operator_devices`, `operators`

```sql
SELECT od.operator_id, od.tenant_id
FROM operator_devices od
JOIN operators o ON o.id = od.operator_id
WHERE od.session_token = '{{ $json.session_token }}'
  AND od.device_type = 'mobile'
  AND o.is_active = true
LIMIT 1;
```

### SQL Create

**Workflow:** API_Android_Repair_Create

**Операция:** Создание из `appeal_repairs`, `appeal_devices`

```sql
INSERT INTO appeal_repairs (
  appeal_id, appeal_device_id, tenant_id,
  repair_category_id, repair_category_name,
  issue_type_id, issue_type_name,
  parts_owner, priority
)
SELECT
  ad.appeal_id,
  ad.id,
  '{{ $json.tenant_id }}'::uuid,
  NULLIF('{{ $json.repair_category_id }}', 'null')::uuid,
  NULLIF('{{ $json.repair_category_name }}', 'null'),
  NULLIF('{{ $json.issue_type_id }}', 'null')::uuid,
  NULLIF('{{ $json.issue_type_name }}', 'null'),
  COALESCE(NULLIF('{{ $json.parts_owner }}', '
```
