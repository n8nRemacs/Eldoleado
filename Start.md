# Start Session - План на 2025-12-21

## Приоритет 1: Исправить нормализацию текста

### Проблема
Webhook `android-normalize/android/dialogs/:dialog_id/normalize` не работает - n8n не поддерживает динамические path параметры.

### Решение
1. **n8n:** Изменить webhook path на `android/normalize`

2. **ApiService.kt:** Убрать `@Path`, использовать body:
   ```kotlin
   @POST("android/normalize")
   fun normalizeDialogText(
       @Body request: NormalizeDialogRequest
   ): Call<NormalizeDialogResponse>
   ```

3. **NormalizeDialogRequest:** Добавить dialog_id:
   ```kotlin
   data class NormalizeDialogRequest(
       val session_token: String,
       val dialog_id: String,
       val text: String
   )
   ```

4. **ChatActivity.kt:** Передавать dialog_id в request

---

## Приоритет 2: Исправить отправку сообщений

Проверить webhook для отправки сообщений - вероятно та же проблема с path параметрами.

Текущий endpoint в ApiService.kt:
```kotlin
@POST("android-messages/android/dialogs/{dialog_id}/messages")
fun sendChatMessage(...)
```

---

## Приоритет 3: Автоназначение оператора для новых диалогов

### ELO_Client_Resolve - изменения:

1. **DB Get Tenant** - добавить `channel_account_id`:
   ```sql
   SELECT t.id as tenant_id, t.domain_id, ca.id as channel_account_id, ca.channel_id
   FROM elo_t_tenants t
   JOIN elo_t_channel_accounts ca ON ca.tenant_id = t.id
   ...
   ```

2. **Prepare Dialog Cache Key** - передавать `channel_account_id`

3. **DB Create Dialog** - назначать оператора:
   ```sql
   INSERT INTO elo_t_dialogs (..., assigned_operator_id, channel_account_id)
   SELECT ...,
       (SELECT oc.operator_id FROM elo_t_operator_channels oc
        WHERE oc.channel_account_id = '...' AND oc.is_active = true
        ORDER BY oc.is_primary DESC LIMIT 1),
       '...'
   ```

4. **Save Incoming Message** - обновлять `last_message_at`:
   ```sql
   WITH msg AS (INSERT INTO elo_t_messages ... RETURNING id, dialog_id)
   UPDATE elo_t_dialogs SET last_message_at = NOW() WHERE id = (SELECT dialog_id FROM msg)
   ```

---

## Документация

Полные SQL запросы в: `NEW/N8N_SQL_FIXES.md`

---

## Baileys Session (ЛОКАЛЬНО!)

**ВАЖНО: Baileys работает ЛОКАЛЬНО на рабочем компе, НЕ на сервере!**

```bash
cd /c/Users/User/Documents/Eldoleado/NEW/MVP/MCP/mcp-whatsapp-baileys
npm run build && npm start
```

**Port:** localhost:8766
**Session ID:** test-local
**Session path:** C:\Users\User\Documents\Eldoleado\NEW\MVP\MCP\mcp-whatsapp-baileys\sessions\test-local\
**Phone:** 79171708077 (Ремакс)
**Webhook:** https://n8n.n8nsrv.ru/webhook/whatsapp-incoming

**ВАЖНО:** В БД `elo_t_channel_accounts.account_id` должен совпадать с Session ID!

---

## Тестовые данные

- **Оператор:** Test Admin (22222222-2222-2222-2222-222222222222)
- **Session:** 85bc5364-7765-4562-be9e-02d899bb575e
- **Диалог:** cff56064-1fc3-4152-8e64-6e0266a87bf6
- **Клиент:** Дмитрий (+79997253777, WhatsApp)

---

## Команды для тестирования

```bash
# === BAILEYS (ЛОКАЛЬНО!) ===
cd /c/Users/User/Documents/Eldoleado/NEW/MVP/MCP/mcp-whatsapp-baileys
npm run build && npm start   # Запустить Baileys

# Check Baileys status
curl http://localhost:8766/sessions

# === n8n webhooks ===
# Тест messages webhook
curl "https://n8n.n8nsrv.ru/webhook/android/messages?dialog_id=cff56064-1fc3-4152-8e64-6e0266a87bf6&session_token=85bc5364-7765-4562-be9e-02d899bb575e"

# Тест normalize webhook (после фикса)
curl -X POST "https://n8n.n8nsrv.ru/webhook/android/normalize" \
  -H "Content-Type: application/json" \
  -d '{"session_token":"85bc5364-7765-4562-be9e-02d899bb575e","dialog_id":"cff56064-1fc3-4152-8e64-6e0266a87bf6","text":"тест"}'

# === n8n / Redis (сервер 185.221.214.83) ===
ssh root@185.221.214.83 "docker exec n8n-redis redis-cli LRANGE queue:incoming 0 5"

# === Database ===
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT * FROM elo_t_messages LIMIT 5;'"
```
