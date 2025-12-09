---
doc_key: phone
doc_type: channel
entry_workflow: BAT IN Phone
participating_workflows:
  - BAT IN Phone
  - BAT_Tenant_Resolver
generated: 2025-12-09 15:39:38
---

# Поток: Входящий звонок

**Входящий звонок**

*Сгенерировано: 2025-12-09 15:39:38*

---

## Сводка

- **Точка входа:** `BAT IN Phone`
- **Workflows в цепочке:** 2
- **Операций с БД:** 1
- **Операций Redis:** 0
- **Вызовов AI:** 1
- **Затронуто таблиц:** 2

## Цепочка Workflows

```
BAT IN Phone (/phone)
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

## AI Операции

- **BAT IN Phone**: Transcribe Recording (openai)

## Внешние API вызовы

- **BAT IN Phone**: GET 
