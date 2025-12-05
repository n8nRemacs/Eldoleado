---
doc_key: login
doc_type: action
entry_workflow: API_Android_Auth
participating_workflows:
  - API_Android_Auth
generated: 2025-12-05 15:01:45
---

# Действие: Авторизация оператора

**Авторизация оператора**

*Сгенерировано: 2025-12-05 15:01:45*

---

## Сводка

- **Точка входа:** `API_Android_Auth`
- **Workflows в цепочке:** 1
- **Операций с БД:** 4
- **Операций Redis:** 0
- **Вызовов AI:** 0
- **Затронуто таблиц:** 2

## Цепочка Workflows

```
API_Android_Auth (/android/auth/login)
```

## Затронутые таблицы

| Таблица | Операции |
|---------|----------|
| `operator_devices` | **удаление**, **создание** |
| `operators` | чтение |

## Операции с базой данных

### Postgres - Find Operator

**Workflow:** API_Android_Auth

**Операция:** Чтение из `operators`

```sql
SELECT id, tenant_id, name, email, username, password_hash, is_active, location_id
FROM operators
WHERE (email = '{{ $json.body.login }}' OR username = '{{ $json.body.login }}')
AND is_active = true
LIMIT 1;
```

### Postgres - Verify Password

**Workflow:** API_Android_Auth

**Операция:** Чтение из `operators`

```sql
SELECT 
  '{{ $('Postgres - Find Operator').item.json.id }}' as operator_id,
  '{{ $('Postgres - Find Operator').item.json.tenant_id }}' as tenant_id,
  '{{ $('Postgres - Find Operator').item.json.name }}' as name,
  '{{ $('Postgres - Find Operator').item.json.email }}' as email,
  '{{ $('Postgres - Find Operator').item.json.location_id }}' as location_id,
  (password_hash = crypt('{{ $('Webhook - Auth Login').item.json.body.password }}', password_hash)) as password_valid
FROM operators
WHERE id
```

### Delete Old Mobile Session

**Workflow:** API_Android_Auth

**Операция:** Удаление из `operator_devices`

```sql
DELETE FROM operator_devices 
WHERE operator_id = '{{ $json.operator_id }}'::uuid
  AND device_type = 'mobile'
  AND tenant_id = '{{ $json.tenant_id }}'::uuid;

SELECT 
  '{{ $json.operator_id }}' as operator_id,
  '{{ $json.tenant_id }}' as tenant_id,
  '{{ $json.name }}' as name,
  '{{ $json.email }}' as email,
  '{{ $json.location_id }}' as location_id,
  '{{ $json.device_id }}' as device_id,
  '{{ $json.device_info_json }}' as device_info_json;
```

### Insert into operator_devices

**Workflow:** API_Android_Auth

**Операция:** Создание из `operator_devices`

```sql
INSERT INTO operator_devices (
  operator_id,
  tenant_id,
  device_type,
  session_token,
  fcm_token,
  device_id,
  device_info,
  created_at,
  last_active_at
) VALUES (
  '{{ $json.operator_id }}'::uuid,
  '{{ $json.tenant_id }}'::uuid,
  'mobile',
  '{{ $json.session_token }}',
  NULL,
  {{ $json.device_id && $json.device_id !== 'null' ? "'" + $json.device_id + "'" : "NULL" }},
  {{ $json.device_info_json ? "'" + $json.device_info_json + "'::jsonb" : "NULL" }},
  NOW(),
  NOW()
) RETURNING
```
