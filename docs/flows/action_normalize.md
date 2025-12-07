---
doc_key: normalize
doc_type: action
entry_workflow: API_Android_Normalize
participating_workflows:
  - API_Android_Normalize
  - BAT Operator Response Handler 3 - Text Voice Normalize
generated: 2025-12-07 16:19:59
---

# Действие: Нормализация текста ответа

**Нормализация текста ответа**

*Сгенерировано: 2025-12-07 16:19:59*

---

## Сводка

- **Точка входа:** `API_Android_Normalize`
- **Workflows в цепочке:** 2
- **Операций с БД:** 4
- **Операций Redis:** 0
- **Вызовов AI:** 3
- **Затронуто таблиц:** 8

## Цепочка Workflows

```
API_Android_Normalize (/api/android/appeals/:id/normalize)
  -> BAT Operator Response Handler 3 - Text Voice Normalize
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `appeals` | чтение |
| `brands` | чтение |
| `clients` | чтение |
| `messages_history` | чтение |
| `models` | чтение |
| `operator_devices` | чтение |
| `operators` | чтение |
| `repair_types` | чтение |

## Операции с базой данных

### Validate Token

**Workflow:** API_Android_Normalize

**Операция:** Чтение из `operators`, `operator_devices`

```sql
SELECT 
  od.operator_id,
  od.tenant_id,
  od.device_type
FROM operator_devices od
JOIN operators o ON o.id = od.operator_id
WHERE od.session_token = '{{ $json.session_token }}'
  AND od.device_type = 'mobile'
  AND o.is_active = true
LIMIT 1;
```

### Execute a SQL query

**Workflow:** BAT Operator Response Handler 3 - Text Voice Normalize

**Операция:** Чтение из `clients`, `appeals`

```sql
SELECT 
  a.id as appeal_id,
  a.tenant_id,
  a.client_id,
  c.telegram_id as client_telegram_id,
  a.ad_channel as channel
FROM appeals a
JOIN clients c ON c.id = a.client_id AND c.tenant_id = a.tenant_id
WHERE a.id = '{{ $json.appeal_id }}'::uuid
LIMIT 1;
```

### Execute a SQL query1

**Workflow:** BAT Operator Response Handler 3 - Text Voice Normalize

**Операция:** Чтение из `models`, `brands`, `appeals`

```sql
SELECT 
  a.*,
  c.name as client_name,
  c.phone as client_phone,
  c.telegram_id as client_telegram_id,
  b.name as brand_name,
  m.name as model_name,
  rt.name as repair_type_name
FROM appeals a
LEFT JOIN clients c ON c.id = a.client_id AND c.tenant_id = a.tenant_id
LEFT JOIN brands b ON b.id = a.brand_id
LEFT JOIN models m ON m.id = a.model_id
LEFT JOIN repair_types rt ON rt.id = a.repair_type_id
WHERE a.id = '{{ $json.appeal.id }}'::uuid
  AND a.tenant_id = '{{ $json.tenant_id }}'::uuid
LI
```

### Execute a SQL query2

**Workflow:** BAT Operator Response Handler 3 - Text Voice Normalize

**Операция:** Чтение из `messages_history`

```sql
SELECT message_text as client_first_message
FROM messages_history
WHERE appeal_id = '{{ $json.id }}'::uuid
  AND message_type = 'client'
  AND tenant_id = '{{ $json.tenant_id }}'::uuid
ORDER BY created_at ASC
LIMIT 1;
```

## AI Операции

- **BAT Operator Response Handler 3 - Text Voice Normalize**: Transcribe Voice (openai)
- **BAT Operator Response Handler 3 - Text Voice Normalize**: AI Text Normalizer (agent)
- **BAT Operator Response Handler 3 - Text Voice Normalize**: OpenAI Chat Model (lmchatopenai)
