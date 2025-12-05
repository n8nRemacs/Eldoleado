---
doc_key: device_delete
doc_type: action
entry_workflow: API_Android_Device_Delete
participating_workflows:
  - API_Android_Device_Delete
generated: 2025-12-05 15:01:47
---

# Действие: Удалить устройство

**Удалить устройство**

*Сгенерировано: 2025-12-05 15:01:47*

---

## Сводка

- **Точка входа:** `API_Android_Device_Delete`
- **Workflows в цепочке:** 1
- **Операций с БД:** 2
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 3

## Цепочка Workflows

```
API_Android_Device_Delete (/android/appeal-devices/:id)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `appeal_devices` | **удаление** |
| `operator_devices` | чтение |
| `operators` | чтение |

## Операции с базой данных

### Get Tenant

**Workflow:** API_Android_Device_Delete

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

### SQL Delete

**Workflow:** API_Android_Device_Delete

**Операция:** Удаление из `appeal_devices`

```sql
DELETE FROM appeal_devices
WHERE id = '{{ $json.device_id }}'::uuid
  AND tenant_id = '{{ $json.tenant_id }}'::uuid
RETURNING id;
```
