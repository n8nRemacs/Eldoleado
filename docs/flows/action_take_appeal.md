---
doc_key: take_appeal
doc_type: action
entry_workflow: API_Android_Take_Appeal
participating_workflows:
  - API_Android_Take_Appeal
generated: 2025-12-07 16:19:59
---

# Действие: Взять обращение в работу

**Взять обращение в работу**

*Сгенерировано: 2025-12-07 16:19:59*

---

## Сводка

- **Точка входа:** `API_Android_Take_Appeal`
- **Workflows в цепочке:** 1
- **Операций с БД:** 3
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 5

## Цепочка Workflows

```
API_Android_Take_Appeal (/api/android/appeals/:id/take)
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

### Get Operator

**Workflow:** API_Android_Take_Appeal

**Операция:** Чтение из `operators`, `operator_devices`

```sql
SELECT od.tenant_id, od.operator_id 
FROM operator_devices od
JOIN operators o ON o.id = od.operator_id
WHERE od.session_token = '{{ $json.session_token }}'
  AND od.device_type = 'mobile'
  AND o.is_active = true
LIMIT 1;
```

### Update Status

**Workflow:** API_Android_Take_Appeal

**Операция:** Создание из `set`, `operator_appeal_status`

```sql
INSERT INTO operator_appeal_status (tenant_id, appeal_id, operator_id, status, taken_at)
VALUES ('{{ $json.tenant_id }}'::uuid, '{{ $json.appeal_id }}'::uuid, '{{ $json.operator_id }}'::uuid, 'in_progress', NOW())
ON CONFLICT (tenant_id, appeal_id, operator_id) DO UPDATE SET status = 'in_progress', taken_at = NOW()
RETURNING id;
```

### Log Action

**Workflow:** API_Android_Take_Appeal

**Операция:** Создание из `operator_actions`

```sql
INSERT INTO operator_actions (tenant_id, operator_id, action_type, action_source, details)
VALUES (
  '{{ $json.tenant_id }}'::uuid, 
  '{{ $json.operator_id }}'::uuid, 
  'take_appeal', 
  'android_app', 
  jsonb_build_object('appeal_id', '{{ $json.appeal_id }}')
);
```
