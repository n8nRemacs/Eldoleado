# Start Session - 2025-12-31

## Текущий статус: WhatsApp работает, нужно исправить Android App

WhatsApp webhook работает, push-уведомления доходят, но в приложении есть проблемы.

---

## ИЗВЕСТНЫЕ ПРОБЛЕМЫ (приоритет)

### 1. Android App - Сообщения не обновляются в открытом диалоге

**Симптом:** Когда открыт диалог, новые входящие сообщения не отображаются. Нужно выйти и зайти обратно.

**Файлы для исследования:**
- `app/.../operator/ChatFragment.kt` - слушает `BROADCAST_NEW_MESSAGE`
- `app/.../operator/ChatsRepository.kt` - `addIncomingMessage()`, `getMessagesLiveData()`
- `app/.../operator/OperatorWebSocketService.kt` - `broadcastNewMessage()`
- `app/.../operator/models/ChatModels.kt` - `ChatMessage.fromJson()` ожидает snake_case поля

**Формат message от сервера должен быть:**
```json
{
  "id": "msg_123",
  "chat_id": "79997253777",
  "channel": "whatsapp",
  "text": "...",
  "sender_name": "Клиент",
  "sender_phone": "79997253777",
  "server_id": ""
}
```

### 2. Дублирование уведомлений

**Симптом:** Иногда приходит 2 одинаковых push-уведомления

**Где искать:** BAT Debouncer в n8n workflows

---

## Инфраструктура

| Сервер | IP | Сервисы |
|--------|-----|---------|
| **Messenger** | 155.212.221.189 | mcp-whatsapp :8769, api-android :8780 |
| **Messenger IP2** | 217.114.14.17 | mcp-whatsapp :8769 |
| **n8n** | 185.221.214.83 | n8n, PostgreSQL, Redis |

---

## Сервисы (работают)

| Сервис | Порт | Версия | Статус |
|--------|------|--------|--------|
| mcp-whatsapp-ip1 | 155.212.221.189:8769 | v1.0.1 | OK |
| mcp-whatsapp-ip2 | 217.114.14.17:8769 | v1.0.1 | OK |
| api-android | 155.212.221.189:8780 | latest | OK |

---

## Что было сделано 2025-12-30

1. **mcp-whatsapp-arceos** - исправлен webhook (читал неправильную env переменную)
2. **api-android** - добавлен internal API key для n8n (`X-Internal-Key: elo-internal-2024`)
3. **api-android** - добавлена передача `message` объекта в WebSocket push

---

## n8n Workflows - нужно обновить

### ELO_In_WhatsApp - Extract WhatsApp Data

Код в Stop.md, нужно обновить в n8n UI под новый формат webhook от mcp-whatsapp-arceos.

### ELO_Core_AI_Test_Stub_WS

- `Send WebSocket Push` - включить **Continue On Fail**
- `Build WS Push Requests` - использовать snake_case для message полей

---

## SSH

```bash
ssh root@155.212.221.189  # Messenger
ssh root@185.221.214.83   # n8n
```

---

## Полезные команды

```bash
# Логи WhatsApp
ssh root@155.212.221.189 "docker logs mcp-whatsapp-ip1 --tail 50"

# Логи api-android
ssh root@155.212.221.189 "docker logs android-api --tail 50"

# Проверить WebSocket соединения
curl -s http://155.212.221.189:8780/api/push/connections -H "Authorization: <token>"

# Health checks
curl http://155.212.221.189:8769/health
curl http://155.212.221.189:8780/health
```

---

## Документация

| Файл | Описание |
|------|----------|
| Stop.md | Детали изменений 30.12 + код для n8n |
| CLAUDE.md | Основная документация проекта |

---

*Последнее обновление: 2025-12-30*
