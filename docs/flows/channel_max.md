---
doc_key: max
doc_type: channel
entry_workflow: BAT IN MAX
participating_workflows:
  - BAT IN MAX
  - BAT_Tenant_Resolver
generated: 2025-12-07 16:19:58
---

# Поток: Входящее сообщение из MAX.ru

**Входящее сообщение из MAX.ru**

*Сгенерировано: 2025-12-07 16:19:58*

---

## Сводка

- **Точка входа:** `BAT IN MAX`
- **Workflows в цепочке:** 2
- **Операций с БД:** 1
- **Операций Redis:** 1
- **Вызовов AI:** 1
- **Затронуто таблиц:** 2

## Цепочка Workflows

```
BAT IN MAX (/max)
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

## Операции Redis

- **BAT IN MAX**: Push to Queue

## AI Операции

- **BAT IN MAX**: Transcribe Voice (openai)

## Внешние API вызовы

- **BAT IN MAX**: GET 
