---
doc_key: vk
doc_type: channel
entry_workflow: BAT IN VK
participating_workflows:
  - BAT IN VK
  - BAT_Tenant_Resolver
generated: 2025-12-05 15:01:41
---

# Поток: Входящее сообщение из VK

**Входящее сообщение из VK**

*Сгенерировано: 2025-12-05 15:01:41*

---

## Сводка

- **Точка входа:** `BAT IN VK`
- **Workflows в цепочке:** 2
- **Операций с БД:** 1
- **Операций Redis:** 1
- **Вызовов AI:** 1
- **Затронуто таблиц:** 2

## Цепочка Workflows

```
BAT IN VK (/vk)
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

- **BAT IN VK**: Push to Queue

## AI Операции

- **BAT IN VK**: Transcribe Voice (openai)

## Внешние API вызовы

- **BAT IN VK**: GET 
