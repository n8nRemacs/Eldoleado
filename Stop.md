# Stop Session - 2025-12-30

## WhatsApp Webhook + Push Notifications

Исправлена интеграция WhatsApp с n8n и push-уведомлениями для Android.

---

## Что сделано сегодня

### 1. mcp-whatsapp-arceos - Исправлен webhook

**Проблема:** Webhook не отправлялся на n8n

**Причина:** Код читал `process.env.DEFAULT_WEBHOOK_URL`, а в контейнере была переменная `WEBHOOK_URL`

**Решение:** `index.ts:40`
```typescript
defaultWebhookUrl: process.env.DEFAULT_WEBHOOK_URL || process.env.WEBHOOK_URL,
```

**Версия:** v1.0.1 задеплоена на оба IP

### 2. api-android - Добавлен internal API key

**Проблема:** n8n workflow не мог вызвать `/api/push/send` (требовал JWT)

**Решение:**
- Добавлен `get_internal_or_operator` в `auth.py`
- Добавлен `internal_api_key` в `config.py`
- Endpoint принимает `X-Internal-Key: elo-internal-2024`

### 3. api-android - Передача message объекта

**Проблема:** Push не содержал объект `message` для real-time обновления чата

**Решение:** `app.py` - добавлено `"message": body.get("message")`

### 4. n8n Workflow - ELO_In_WhatsApp

Нужно обновить ноду `Extract WhatsApp Data` под новый формат webhook:

```javascript
const input = $input.first().json;
const body = input.body || input;
const data = body.data || {};

if (body.event !== 'message') return [];
if (data.isGroup) return [];

const hasText = !!data.text;
const hasMedia = data.type === 'image' || data.type === 'video' || data.type === 'audio' || data.type === 'document';
if (!hasText && !hasMedia) return [];

const phone = '+' + data.from;
const messageText = data.text || data.caption || '';
const timestamp = data.timestamp ? new Date(data.timestamp).toISOString() : new Date().toISOString();

return {
  session_id: body.sessionId,
  session_hash: body.sessionHash,
  chat_id: data.from + '@s.whatsapp.net',
  phone: phone,
  message_text: messageText,
  message_id: data.messageId,
  timestamp: timestamp,
  sender_name: data.fromName || null,
  has_photo: data.type === 'image',
  has_voice: data.type === 'audio',
  has_video: data.type === 'video',
  has_document: data.type === 'document',
  raw_event: body
};
```

### 5. n8n Workflow - ELO_Core_AI_Test_Stub_WS

Нода `Send WebSocket Push` - включить **Continue On Fail** (timeout иногда)

Нода `Build WS Push Requests` - использовать snake_case для message:
```javascript
message: {
  id: 'msg_' + Date.now(),
  chat_id: ...,      // не chatId
  sender_name: ...,  // не senderName
  sender_phone: ..., // не senderPhone
  server_id: ...     // не serverId
}
```

---

## ИЗВЕСТНЫЕ ПРОБЛЕМЫ

### 1. Android App - Сообщения не обновляются в открытом диалоге

**Симптом:** Когда открыт диалог, новые входящие сообщения не отображаются. Нужно выйти и зайти обратно.

**Где искать:**
- `ChatFragment.kt` - слушает `BROADCAST_NEW_MESSAGE`
- `ChatsRepository.kt` - `addIncomingMessage()` и `getMessagesLiveData()`
- `OperatorWebSocketService.kt` - `broadcastNewMessage()`

**Вероятная причина:** Формат message объекта или проблема с LiveData

### 2. Дублирование уведомлений

**Симптом:** Иногда приходит 2 одинаковых push-уведомления

**Где искать:** BAT Debouncer или batching в n8n workflows

---

## Сервисы (актуально)

| Сервис | Порт | Версия | Статус |
|--------|------|--------|--------|
| mcp-whatsapp-ip1 | 155.212.221.189:8769 | v1.0.1 | OK |
| mcp-whatsapp-ip2 | 217.114.14.17:8769 | v1.0.1 | OK |
| api-android | 155.212.221.189:8780 | latest | OK |

---

## Следующие шаги

1. **Исправить real-time обновление в ChatFragment** - сообщения должны появляться без перезахода
2. **Найти причину дублирования уведомлений** - проверить BAT Debouncer
3. **Обновить Extract WhatsApp Data в n8n** - код выше

---

*Сессия завершена: 2025-12-30*
