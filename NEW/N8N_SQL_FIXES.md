# N8N SQL Fixes for Operator Assignment

> Date: 2025-12-20
> Issue: Dialogs не назначаются оператору, last_message_at не обновляется

---

## 1. ELO_Client_Resolve: DB Get Tenant

**Добавить получение channel_account_id:**

```sql
SELECT
    t.id as tenant_id,
    t.domain_id,
    ca.id as channel_account_id,
    ca.channel_id
FROM elo_t_tenants t
JOIN elo_t_channel_accounts ca ON ca.tenant_id = t.id
JOIN elo_channels c ON c.id = ca.channel_id
WHERE ca.account_id = '{{ $('Validate Input').item.json.meta.raw.sessionId }}'
  AND c.code = '{{ $('Validate Input').first().json.channel }}'
  AND ca.is_active = true
  AND t.is_active = true
LIMIT 1;
```

---

## 2. ELO_Client_Resolve: DB Create Dialog

**Заменить на (с назначением оператора):**

```sql
INSERT INTO elo_t_dialogs (
    tenant_id,
    client_id,
    channel_id,
    channel_account_id,
    status_id,
    assigned_operator_id,
    last_message_at
)
SELECT
    '{{ $('Prepare Dialog Cache Key').first().json.tenant_id }}'::uuid,
    '{{ $('Prepare Dialog Cache Key').first().json.client_id }}'::uuid,
    {{ $('Prepare Dialog Cache Key').first().json.channel_id }},
    '{{ $('Prepare Dialog Cache Key').first().json.channel_account_id }}'::uuid,
    1,
    -- Назначаем оператора из elo_t_operator_channels
    (SELECT oc.operator_id
     FROM elo_t_operator_channels oc
     WHERE oc.channel_account_id = '{{ $('Prepare Dialog Cache Key').first().json.channel_account_id }}'::uuid
       AND oc.is_active = true
     ORDER BY oc.is_primary DESC
     LIMIT 1),
    NOW()
RETURNING id as dialog_id;
```

---

## 3. ELO_Client_Resolve: Save Incoming Message

**Заменить на (с обновлением last_message_at):**

```sql
WITH msg AS (
    INSERT INTO elo_t_messages (
        tenant_id,
        dialog_id,
        client_id,
        direction_id,
        actor_type,
        actor_id,
        content,
        external_message_id,
        created_at
    ) VALUES (
        '{{ $('Build Output').item.json.tenant_id }}'::uuid,
        '{{ $('Build Output').item.json.dialog_id }}'::uuid,
        '{{ $('Build Output').item.json.client_id }}'::uuid,
        1,
        'client',
        '{{ $('Build Output').item.json.client_id }}'::uuid,
        '{{ $('Build Output').item.json.text.replace(/'/g, "''") }}',
        '{{ $('Build Output').item.json.trace_id }}',
        NOW()
    )
    RETURNING id, dialog_id
)
UPDATE elo_t_dialogs
SET last_message_at = NOW(),
    last_client_message_at = NOW(),
    updated_at = NOW()
WHERE id = (SELECT dialog_id FROM msg)
RETURNING (SELECT id FROM msg) as id;
```

---

## 4. ELO_Client_Resolve: Prepare Dialog Cache Key

**Добавить channel_account_id в код:**

```javascript
const input = $input.first().json;
const tenant = $('Merge Tenant').first().json;
const client = $('Cache Set Client').first()?.json || $('Merge Existing Client').first()?.json || $('Merge New Client').first()?.json;

return {
  tenant_id: tenant.tenant_id,
  channel_id: tenant.channel_id,
  channel_account_id: tenant.channel_account_id,  // <-- ДОБАВИТЬ
  client_id: client.client_id,
  external_chat_id: input.external_chat_id
};
```

---

## 5. ELO_API_Android_Messages: Webhook Path

**Проблема:** n8n не поддерживает динамические параметры (`:dialog_id`) в production webhooks.

**Решение:** Изменить path на `android/messages` и передавать `dialog_id` через query.

**Webhook node:**
- Path: `android/messages`
- Method: GET

**SQL запрос (изменить):**
```sql
SELECT
    m.id,
    m.content as text,
    m.direction_id,
    CASE WHEN m.direction_id = 1 THEN 'client' ELSE 'operator' END as sender,
    m.actor_type,
    EXTRACT(EPOCH FROM m.timestamp)::bigint * 1000 as timestamp,
    m.media,
    m.message_type_id
FROM elo_t_messages m
WHERE m.dialog_id = '{{ $json.query.dialog_id }}'::uuid
ORDER BY m.timestamp ASC;
```

**Android ApiService.kt:**
```kotlin
@GET("android/messages")
fun getChatMessages(
    @Query("dialog_id") dialogId: String,
    @Query("session_token") sessionToken: String
): Call<ChatMessagesResponse>
```

---

## 6. Обновить существующие диалоги

**Назначить оператора для существующих диалогов:**

```sql
UPDATE elo_t_dialogs d
SET assigned_operator_id = (
    SELECT oc.operator_id
    FROM elo_t_operator_channels oc
    JOIN elo_t_channel_accounts ca ON oc.channel_account_id = ca.id
    WHERE ca.tenant_id = d.tenant_id
      AND ca.channel_id = d.channel_id
      AND oc.is_active = true
    ORDER BY oc.is_primary DESC
    LIMIT 1
),
channel_account_id = (
    SELECT ca.id
    FROM elo_t_channel_accounts ca
    WHERE ca.tenant_id = d.tenant_id
      AND ca.channel_id = d.channel_id
      AND ca.is_active = true
    LIMIT 1
)
WHERE assigned_operator_id IS NULL;
```

---

## Checklist

- [ ] Обновить DB Get Tenant (добавить channel_account_id)
- [ ] Обновить Prepare Dialog Cache Key (передавать channel_account_id)
- [ ] Обновить DB Create Dialog (назначать оператора)
- [ ] Обновить Save Incoming Message (обновлять last_message_at)
- [ ] Изменить webhook path в ELO_API_Android_Messages
- [ ] Обновить Android ApiService.kt
- [ ] Выполнить SQL для существующих диалогов
