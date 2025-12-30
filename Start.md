# Start Session - 2025-12-31

## Текущий статус: Исправлен формат push-уведомлений

WhatsApp webhook работает, push-уведомления доходят. Исправлен формат message объекта для real-time обновления в Android приложении.

---

## ЧТО СДЕЛАНО 2025-12-31

### 1. ELO_Core_AI_Test_Stub_WS - Исправлен формат push

**Проблема:** Сообщения не обновлялись в открытом диалоге Android приложения

**Причина:** Workflow отправлял:
- `type` вместо `action`
- данные вложенные в `data` объект
- отсутствовал `message` объект с snake_case полями

**Решение:** Обновлён `Build WS Push Requests` node:
```javascript
// ВАЖНО: message fields ДОЛЖНЫ быть snake_case для Android app
{
  operator_id: op.json.operator_id,
  action: 'new_message',
  dialog_id: String(testResponse.dialog_id || ''),
  title: 'Новое сообщение',
  body: testResponse.message?.text || 'У вас новое сообщение',
  message: {
    id: 'msg_' + Date.now(),
    chat_id: String(testResponse.external_chat_id || ''),
    channel: String(testResponse.channel_id || ''),
    text: testResponse.message?.text || '',
    sender_name: testResponse.client?.name || 'Клиент',
    sender_phone: testResponse.client?.phone || '',
    server_id: ''
  }
}
```

**Файл:** `NEW/workflows/Core/ELO_Core_AI_Test_Stub_WS.json`

---

## ДЕЙСТВИЯ В n8n (вручную)

### 1. Импортировать обновлённый ELO_Core_AI_Test_Stub_WS

1. Открыть n8n UI: https://n8n.n8nsrv.ru
2. Импортировать: `NEW/workflows/Core/ELO_Core_AI_Test_Stub_WS.json`
3. Активировать workflow

### 2. Send WebSocket Push - включить Continue On Fail

В ноде `Send WebSocket Push` включить **Settings → Continue On Fail** (иногда timeout)

---

## Формат message для Android App

Android `ChatMessage.fromJson()` ожидает snake_case:
```json
{
  "id": "msg_123",
  "chat_id": "79997253777@s.whatsapp.net",
  "channel": "whatsapp",
  "text": "Текст сообщения",
  "sender_name": "Клиент",
  "sender_phone": "+79997253777",
  "server_id": ""
}
```

---

## ИЗВЕСТНЫЕ ПРОБЛЕМЫ

### 1. Дублирование уведомлений

**Симптом:** Иногда приходит 2 одинаковых push-уведомления

**Где искать:** BAT Debouncer в n8n workflows

### 2. ELO_In_WhatsApp - два формата webhook

Текущий workflow использует старый Baileys формат:
```javascript
event.message.key.remoteJid
event.message.message.conversation
```

mcp-whatsapp-arceos может отправлять новый формат:
```javascript
body.data.from
body.data.text
```

Нужно проверить какой формат реально приходит и обновить при необходимости.

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
curl -s http://155.212.221.189:8780/api/push/connections -H "X-Internal-Key: elo-internal-2024"

# Health checks
curl http://155.212.221.189:8769/health
curl http://155.212.221.189:8780/health
```

---

## Документация

| Файл | Описание |
|------|----------|
| Stop.md | Детали изменений 30.12 |
| CLAUDE.md | Основная документация проекта |

---

*Последнее обновление: 2025-12-31*
