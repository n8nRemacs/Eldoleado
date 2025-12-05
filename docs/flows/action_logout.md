---
doc_key: logout
doc_type: action
entry_workflow: API_Android_Logout
participating_workflows:
  - API_Android_Logout
generated: 2025-12-05 15:01:45
---

# Действие: Выход из системы

**Выход из системы**

*Сгенерировано: 2025-12-05 15:01:45*

---

## Сводка

- **Точка входа:** `API_Android_Logout`
- **Workflows в цепочке:** 1
- **Операций с БД:** 1
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 1

## Цепочка Workflows

```
API_Android_Logout (/android/logout)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `operator_devices` | **удаление** |

## Операции с базой данных

### Delete Device Session

**Workflow:** API_Android_Logout

**Операция:** Удаление из `operator_devices`

```sql
DELETE FROM operator_devices 
WHERE session_token = '{{ $json.session_token }}'
RETURNING operator_id;
```
