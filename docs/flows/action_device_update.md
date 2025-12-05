---
doc_key: device_update
doc_type: action
entry_workflow: API_Android_Device_Update
participating_workflows:
  - API_Android_Device_Update
generated: 2025-12-05 15:01:46
---

# Действие: Обновить устройство

**Обновить устройство**

*Сгенерировано: 2025-12-05 15:01:46*

---

## Сводка

- **Точка входа:** `API_Android_Device_Update`
- **Workflows в цепочке:** 1
- **Операций с БД:** 2
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 3

## Цепочка Workflows

```
API_Android_Device_Update (/android/appeal-devices/:id)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `appeal_devices` | **изменение** |
| `operator_devices` | чтение |
| `operators` | чтение |

## Операции с базой данных

### Get Tenant

**Workflow:** API_Android_Device_Update

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

**Workflow:** API_Android_Device_Update

**Операция:** Изменение из `appeal_devices`

```sql
UPDATE appeal_devices
SET
  brand_id = CASE WHEN '{{ $json.brand_id }}' = 'undefined' THEN brand_id ELSE NULLIF('{{ $json.brand_id }}', 'null')::uuid END,
  brand_name = CASE WHEN '{{ $json.brand_name }}' = 'undefined' THEN brand_name ELSE NULLIF('{{ $json.brand_name }}', 'null') END,
  model_id = CASE WHEN '{{ $json.model_id }}' = 'undefined' THEN model_id ELSE NULLIF('{{ $json.model_id }}', 'null')::uuid END,
  model_name = CASE WHEN '{{ $json.model_name }}' = 'undefined' THEN model_name ELSE 
```
