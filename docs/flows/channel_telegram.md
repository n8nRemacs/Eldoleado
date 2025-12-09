---
doc_key: telegram
doc_type: channel
entry_workflow: BAT IN Telegram
participating_workflows:
  - BAT IN Telegram
  - BAT_Tenant_Resolver
generated: 2025-12-09 15:39:37
---

# Поток: Входящее сообщение из Telegram

**Входящее сообщение из Telegram**

*Сгенерировано: 2025-12-09 15:39:37*

---

## Сводка

- **Точка входа:** `BAT IN Telegram`
- **Workflows в цепочке:** 2
- **Операций с БД:** 1
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 2

## Цепочка Workflows

```
BAT IN Telegram (/telegram-in)
  -> BAT_Tenant_Resolver
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `tenant_configs` | чтение |
| `tenants` | чтение |

## Операции с базой данных

### Find Tenant

**Workflow:** BAT_Tenant_Resolver

**Операция:** Чтение из `tenant_configs`, `tenants`

```sql
SELECT 
  t.id as tenant_id,
  t.name as tenant_name,
  t.is_active,
  tc.value as config
FROM tenants t
LEFT JOIN tenant_configs tc ON tc.tenant_id = t.id
WHERE tc.key = '{{ $json.lookup_key }}'
  AND tc.value->>'token' = '{{ $json.lookup_value }}'
  AND t.is_active = true
LIMIT 1;
```
