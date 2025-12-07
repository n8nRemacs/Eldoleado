---
doc_key: device_create
doc_type: action
entry_workflow: API_Android_Device_Create
participating_workflows:
  - API_Android_Device_Create
generated: 2025-12-07 16:20:01
---

# Действие: Добавить устройство

**Добавить устройство**

*Сгенерировано: 2025-12-07 16:20:01*

---

## Сводка

- **Точка входа:** `API_Android_Device_Create`
- **Workflows в цепочке:** 1
- **Операций с БД:** 2
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 4

## Цепочка Workflows

```
API_Android_Device_Create (/android/appeal-devices)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `appeal_devices` | **создание** |
| `appeals` | **создание** |
| `operator_devices` | чтение |
| `operators` | чтение |

## Операции с базой данных

### Get Tenant

**Workflow:** API_Android_Device_Create

**Операция:** Чтение из `operators`, `operator_devices`

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

**Workflow:** API_Android_Device_Create

**Операция:** Создание из `appeals`, `appeal_devices`

```sql
INSERT INTO appeal_devices (appeal_id, tenant_id, brand_id, brand_name, model_id, model_name, phone_model, device_order)
SELECT
  '{{ $json.appeal_id }}'::uuid,
  '{{ $json.tenant_id }}'::uuid,
  NULLIF('{{ $json.brand_id }}', 'null')::uuid,
  NULLIF('{{ $json.brand_name }}', 'null'),
  NULLIF('{{ $json.model_id }}', 'null')::uuid,
  NULLIF('{{ $json.model_name }}', 'null'),
  NULLIF('{{ $json.phone_model }}', 'null'),
  COALESCE((SELECT MAX(device_order) + 1 FROM appeal_devices WHERE appeal_id 
```
