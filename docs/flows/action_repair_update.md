---
doc_key: repair_update
doc_type: action
entry_workflow: API_Android_Repair_Update
participating_workflows:
  - API_Android_Repair_Update
generated: 2025-12-05 15:01:47
---

# Действие: Обновить ремонт

**Обновить ремонт**

*Сгенерировано: 2025-12-05 15:01:47*

---

## Сводка

- **Точка входа:** `API_Android_Repair_Update`
- **Workflows в цепочке:** 1
- **Операций с БД:** 2
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 3

## Цепочка Workflows

```
API_Android_Repair_Update (/android/appeal-repairs/:id)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `appeal_repairs` | **изменение** |
| `operator_devices` | чтение |
| `operators` | чтение |

## Операции с базой данных

### Get Tenant

**Workflow:** API_Android_Repair_Update

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

### SQL Update

**Workflow:** API_Android_Repair_Update

**Операция:** Изменение из `appeal_repairs`

```sql
UPDATE appeal_repairs
SET
  repair_category_id = CASE WHEN '{{ $json.repair_category_id }}' = 'undefined' THEN repair_category_id ELSE NULLIF('{{ $json.repair_category_id }}', 'null')::uuid END,
  repair_category_name = CASE WHEN '{{ $json.repair_category_name }}' = 'undefined' THEN repair_category_name ELSE NULLIF('{{ $json.repair_category_name }}', 'null') END,
  issue_type_id = CASE WHEN '{{ $json.issue_type_id }}' = 'undefined' THEN issue_type_id ELSE NULLIF('{{ $json.issue_type_id }}', 'nul
```
