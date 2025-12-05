---
doc_key: send_response
doc_type: action
entry_workflow: API_Android_Send_Response
participating_workflows:
  - API_Android_Send_Response
generated: 2025-12-05 15:01:43
---

# Действие: Отправка ответа клиенту

**Отправка ответа клиенту**

*Сгенерировано: 2025-12-05 15:01:43*

---

## Сводка

- **Точка входа:** `API_Android_Send_Response`
- **Workflows в цепочке:** 1
- **Операций с БД:** 2
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 5

## Цепочка Workflows

```
API_Android_Send_Response (//api/android/appeals/{id}/send)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `appeals` | чтение |
| `clients` | чтение |
| `operator_appeal_status` | **изменение** |
| `operator_devices` | чтение |
| `operators` | чтение |

## Операции с базой данных

### Get Context

**Workflow:** API_Android_Send_Response

**Операция:** Чтение из `operator_devices`, `clients`, `appeals`

```sql
SELECT 
  od.operator_id, 
  od.tenant_id, 
  a.client_id, 
  a.ad_channel as channel, 
  c.telegram_id as client_telegram_id
FROM operator_devices od
JOIN operators o ON o.id = od.operator_id
JOIN appeals a ON a.id = '{{ $json.appeal_id }}'::uuid AND a.tenant_id = od.tenant_id
JOIN clients c ON c.id = a.client_id AND c.tenant_id = od.tenant_id
WHERE od.session_token = '{{ $json.session_token }}'
  AND od.device_type = 'mobile'
  AND o.is_active = true
LIMIT 1;
```

### Update Status

**Workflow:** API_Android_Send_Response

**Операция:** Изменение из `operator_appeal_status`

```sql
UPDATE operator_appeal_status SET status = 'completed', completed_at = NOW() WHERE appeal_id = '{{ $('Normalize').first().json.appeal_id }}'::uuid AND tenant_id = '{{ $json.tenant_id }}'::uuid;
```
