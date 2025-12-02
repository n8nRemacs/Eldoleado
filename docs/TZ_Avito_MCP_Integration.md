# ТЗ: Интеграция Avito через MCP REST API Server

## Обзор

Интеграция мессенджера Avito в BatteryCRM через развёрнутый MCP REST API Server.

### Текущая инфраструктура

| Компонент | URL/Значение |
|-----------|--------------|
| **Avito MCP Server** | `http://45.144.177.128:8765` |
| **API Key** | `BattCRM_Avito_Secret_2024` |
| **Avito User ID** | `157920214` |
| **n8n Server** | `https://n8n.n8nsrv.ru` |
| **Webhook URL** | `https://n8n.n8nsrv.ru/webhook/avito` |

---

## Архитектура каналов BatteryCRM

```
┌─────────────────────────────────────────────────────────────────────┐
│                         ВХОДЯЩИЕ СООБЩЕНИЯ                          │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌──────────┐   ┌──────────┐   ┌──────────┐   ┌──────────┐         │
│  │ Telegram │   │  Avito   │   │    VK    │   │ WhatsApp │         │
│  │  Trigger │   │  Webhook │   │  Webhook │   │  Webhook │         │
│  └────┬─────┘   └────┬─────┘   └────┬─────┘   └────┬─────┘         │
│       │              │              │              │                │
│       │         ┌────┴─────┐        │              │                │
│       │         │ MCP REST │        │              │                │
│       │         │  Server  │        │              │                │
│       │         │ :8765    │        │              │                │
│       │         └────┬─────┘        │              │                │
│       │              │              │              │                │
│       ▼              ▼              ▼              ▼                │
│  ┌─────────────────────────────────────────────────────────┐       │
│  │              BAT IN {Channel} Workflows                  │       │
│  │  - Нормализация формата сообщения                       │       │
│  │  - Обработка голосовых (транскрипция)                   │       │
│  │  - Извлечение медиа                                     │       │
│  └─────────────────────────────────────────────────────────┘       │
│                              │                                      │
│                              ▼                                      │
│  ┌─────────────────────────────────────────────────────────┐       │
│  │                   Tenant Resolver                        │       │
│  │  - Определение тенанта по channel + external_user_id    │       │
│  └─────────────────────────────────────────────────────────┘       │
│                              │                                      │
│                              ▼                                      │
│  ┌─────────────────────────────────────────────────────────┐       │
│  │                  Universal Batcher                       │       │
│  │  - Агрегация сообщений в течение 30 сек                 │       │
│  │  - Объединение текста + голоса + медиа                  │       │
│  └─────────────────────────────────────────────────────────┘       │
│                              │                                      │
│                              ▼                                      │
│  ┌─────────────────────────────────────────────────────────┐       │
│  │                  AI Appeal Router                        │       │
│  │  - Создание/обновление обращения                        │       │
│  │  - Обработка ИИ                                         │       │
│  │  - Формирование ответа                                  │       │
│  └─────────────────────────────────────────────────────────┘       │
└─────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────┐
│                        ИСХОДЯЩИЕ СООБЩЕНИЯ                          │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌─────────────────────────────────────────────────────────┐       │
│  │                   Message Router                         │       │
│  │  - Определение канала по appeal.channel                 │       │
│  │  - Маршрутизация к BAT OUT {Channel}                    │       │
│  └─────────────────────────────────────────────────────────┘       │
│                              │                                      │
│       ┌──────────┬──────────┼──────────┬──────────┐                │
│       ▼          ▼          ▼          ▼          ▼                │
│  ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐           │
│  │  OUT   │ │  OUT   │ │  OUT   │ │  OUT   │ │  OUT   │           │
│  │Telegram│ │ Avito  │ │   VK   │ │WhatsApp│ │  MAX   │           │
│  └───┬────┘ └───┬────┘ └───┬────┘ └───┬────┘ └───┬────┘           │
│      │          │          │          │          │                 │
│      │     ┌────┴─────┐    │          │          │                 │
│      │     │ MCP REST │    │          │          │                 │
│      │     │  Server  │    │          │          │                 │
│      │     │ :8765    │    │          │          │                 │
│      │     └────┬─────┘    │          │          │                 │
│      │          │          │          │          │                 │
│      ▼          ▼          ▼          ▼          ▼                 │
│  ┌──────────────────────────────────────────────────────┐          │
│  │              Мессенджеры / API                        │          │
│  └──────────────────────────────────────────────────────┘          │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Часть 1: Входящие сообщения (BAT IN Avito)

### 1.1 Поток данных

```
Avito Messenger
      │
      │ (Webhook v2)
      ▼
┌─────────────────────────────┐
│   MCP REST API Server       │
│   POST /webhook/avito       │
│   - Валидация webhook       │
│   - Нормализация формата    │
│   - Forward to n8n          │
└─────────────────────────────┘
      │
      │ POST https://n8n.n8nsrv.ru/webhook/avito
      ▼
┌─────────────────────────────┐
│   BAT IN Avito (n8n)        │
│   - Обработка голоса        │
│   - Извлечение медиа        │
│   - Tenant Resolver         │
│   - Universal Batcher       │
└─────────────────────────────┘
```

### 1.2 Формат нормализованного сообщения от MCP Server

MCP Server автоматически нормализует webhook от Avito в стандартный формат:

```json
{
  "channel": "avito",
  "external_user_id": "12345678",
  "external_chat_id": "u2i-xxxxxxxx",
  "text": "Текст сообщения от клиента",
  "timestamp": "2025-12-01T17:30:00",

  "client_phone": null,
  "client_name": "Имя клиента",
  "client_email": null,

  "media": {
    "has_voice": false,
    "voice_url": null,
    "voice_transcribed_text": null,
    "has_images": true,
    "images": [{"url": "https://..."}],
    "has_video": false,
    "videos": [],
    "has_document": false
  },

  "meta": {
    "external_message_id": "msg_abc123",
    "ad_channel": "avito",
    "ad_id": "item_456789",
    "original_media_type": "image",
    "chat_type": "u2i",
    "user_id": 157920214,
    "raw": { /* исходный webhook */ }
  },

  "prefilled_data": {
    "model": null,
    "parts_owner": null
  }
}
```

### 1.3 Workflow BAT IN Avito - обновление

Текущий workflow нужно обновить для работы с MCP Server:

```
Webhook Trigger (POST /webhook/avito)
      │
      ▼
┌─────────────────────────────────┐
│ Validate & Parse                │
│ - Проверка формата              │
│ - Уже нормализовано MCP Server  │
└─────────────────────────────────┘
      │
      ▼
┌─────────────────────────────────┐
│ Has Voice? (Switch)             │
│ - media.has_voice === true      │
└─────────────────────────────────┘
      │
      ├─── YES ───┐
      │           ▼
      │    ┌──────────────────────┐
      │    │ Download Voice       │
      │    │ HTTP GET media.      │
      │    │ voice_url            │
      │    └──────────────────────┘
      │           │
      │           ▼
      │    ┌──────────────────────┐
      │    │ Transcribe (Whisper) │
      │    │ OpenAI API           │
      │    └──────────────────────┘
      │           │
      │           ▼
      │    ┌──────────────────────┐
      │    │ Merge Voice Text     │
      │    │ text + transcription │
      │    └──────────────────────┘
      │           │
      ├───────────┘
      │
      ▼
┌─────────────────────────────────┐
│ Execute Tenant Resolver         │
│ - Определить tenant_id          │
│ - По channel + external_user_id │
└─────────────────────────────────┘
      │
      ▼
┌─────────────────────────────────┐
│ Execute Universal Batcher       │
│ - Агрегация сообщений           │
│ - Задержка 30 сек               │
└─────────────────────────────────┘
```

---

## Часть 2: Исходящие сообщения (BAT OUT Avito)

### 2.1 Поток данных

```
BAT Message Router
      │
      │ channel === "avito"
      ▼
┌─────────────────────────────┐
│   BAT OUT Avito (n8n)       │
│   - Prepare payload         │
│   - Call MCP Server         │
└─────────────────────────────┘
      │
      │ POST http://45.144.177.128:8765/api/messages/send
      │ Header: X-API-Key: BattCRM_Avito_Secret_2024
      ▼
┌─────────────────────────────┐
│   MCP REST API Server       │
│   - Rate limiting           │
│   - Token management        │
│   - Retry logic             │
└─────────────────────────────┘
      │
      │ Avito Messenger API v3
      ▼
┌─────────────────────────────┐
│   Avito Messenger           │
│   - Сообщение доставлено    │
└─────────────────────────────┘
```

### 2.2 Workflow BAT OUT Avito - обновление

```
Execute Workflow Trigger
      │
      │ Input: {tenant_id, appeal_id, channel, message_text, external_chat_id}
      ▼
┌─────────────────────────────────┐
│ Prepare Context                 │
│ - Извлечь данные из input       │
│ - Валидация external_chat_id    │
└─────────────────────────────────┘
      │
      ▼
┌─────────────────────────────────┐
│ Send via MCP Server             │
│ HTTP Request:                   │
│   Method: POST                  │
│   URL: http://45.144.177.128:   │
│        8765/api/messages/send   │
│   Headers:                      │
│     X-API-Key: BattCRM_...      │
│   Body:                         │
│     {                           │
│       "chat_id": external_      │
│                  chat_id,       │
│       "text": message_text      │
│     }                           │
└─────────────────────────────────┘
      │
      ▼
┌─────────────────────────────────┐
│ Process Response                │
│ - Проверить success             │
│ - Извлечь message_id            │
└─────────────────────────────────┘
      │
      ▼
┌─────────────────────────────────┐
│ Save Message History            │
│ INSERT INTO messages_history    │
│   (tenant_id, appeal_id,        │
│    message_type='operator',     │
│    channel='avito', ...)        │
└─────────────────────────────────┘
      │
      ▼
┌─────────────────────────────────┐
│ Respond Success                 │
│ {success: true, channel: ...}   │
└─────────────────────────────────┘
```

### 2.3 HTTP Request Node конфигурация

```yaml
Node: HTTP Request
Name: "Send via MCP Server"
Method: POST
URL: http://45.144.177.128:8765/api/messages/send

Headers:
  X-API-Key: BattCRM_Avito_Secret_2024
  Content-Type: application/json

Body (JSON):
  {
    "chat_id": "={{ $json.external_chat_id }}",
    "text": "={{ $json.message_text }}"
  }

Options:
  Timeout: 30000
  Retry on Fail: true
  Max Retries: 2
```

---

## Часть 3: Настройка Avito Webhook

### 3.1 Подписка на webhook через MCP Server

```bash
curl -X POST http://45.144.177.128:8765/api/webhook/subscribe \
  -H "Content-Type: application/json" \
  -H "X-API-Key: BattCRM_Avito_Secret_2024" \
  -d '{
    "url": "http://45.144.177.128:8765/webhook/avito",
    "version": 2
  }'
```

### 3.2 Проверка подписки

```bash
# Health check
curl http://45.144.177.128:8765/health

# Server info
curl http://45.144.177.128:8765/info

# Test token
curl -H "X-API-Key: BattCRM_Avito_Secret_2024" \
     http://45.144.177.128:8765/api/token
```

---

## Часть 4: MCP Server API Reference

### 4.1 Endpoints для n8n

| Method | Endpoint | Описание | Использование |
|--------|----------|----------|---------------|
| GET | `/health` | Health check | Мониторинг |
| GET | `/api/token` | Получить токен | Диагностика |
| GET | `/api/chats` | Список чатов | Синхронизация |
| GET | `/api/chats/{id}/messages` | История сообщений | Синхронизация |
| POST | `/api/messages/send` | Отправить текст | **BAT OUT Avito** |
| POST | `/api/messages/send/image` | Отправить картинку | Промо |
| POST | `/api/messages/send/bulk` | Массовая отправка | Рассылки |
| POST | `/api/chats/{id}/read` | Прочитать чат | После обработки |

### 4.2 Формат запроса отправки сообщения

```json
POST /api/messages/send
Header: X-API-Key: BattCRM_Avito_Secret_2024

{
  "chat_id": "u2i-xxxxxxxx",
  "text": "Здравствуйте! Спасибо за обращение..."
}
```

### 4.3 Формат ответа

```json
{
  "success": true,
  "chat_id": "u2i-xxxxxxxx",
  "message": "Message sent",
  "response": {
    "id": "msg_abc123",
    "created": 1701450000
  }
}
```

### 4.4 Обработка ошибок

```json
// Rate limit
{
  "success": false,
  "error": "Rate limit exceeded, try again later"
}

// API error
{
  "success": false,
  "error": "Avito API Error 404: Chat not found",
  "details": {...}
}
```

---

## Часть 5: План внедрения

### Этап 1: Настройка webhook (1 час)

- [ ] Подписаться на Avito webhook через MCP Server
- [ ] Проверить получение тестового сообщения
- [ ] Убедиться что MCP Server форвардит в n8n

### Этап 2: Обновление BAT IN Avito (2 часа)

- [ ] Обновить workflow для приёма нормализованного формата
- [ ] Добавить обработку голосовых через HTTP Download + Whisper
- [ ] Протестировать входящие текстовые сообщения
- [ ] Протестировать входящие с изображениями
- [ ] Протестировать входящие голосовые

### Этап 3: Обновление BAT OUT Avito (1 час)

- [ ] Заменить прямые вызовы Avito API на MCP Server
- [ ] Убрать логику управления токеном (MCP Server делает это)
- [ ] Протестировать исходящие сообщения

### Этап 4: Обновление Message Router (30 мин)

- [ ] Проверить маршрут `channel === "avito"`
- [ ] Убедиться что вызывается обновлённый BAT OUT Avito

### Этап 5: Интеграционное тестирование (1 час)

- [ ] Отправить сообщение с Avito
- [ ] Проверить создание обращения
- [ ] Проверить ответ от ИИ/оператора
- [ ] Проверить доставку ответа в Avito

---

## Часть 6: Сравнение с Telegram

| Аспект | Telegram | Avito |
|--------|----------|-------|
| **Trigger** | n8n Telegram Trigger | Webhook → MCP Server → n8n Webhook |
| **Auth** | Bot Token в n8n | Client ID/Secret в MCP Server |
| **Token** | Статичный | OAuth 2.0 с автообновлением |
| **Rate Limit** | Мягкий | 100 req/min (MCP Server) |
| **Голос** | file_id → Telegram API | URL → HTTP Download |
| **Отправка** | n8n Telegram Node | HTTP Request → MCP Server |
| **История** | Нет доступа | GET /api/chats/{id}/messages |

---

## Часть 7: Мониторинг и логи

### 7.1 Логи MCP Server

```bash
# Подключиться к серверу
ssh root@45.144.177.128

# Логи контейнера
docker logs avito-messenger-api -f --tail 100

# Статус
docker ps | grep avito
```

### 7.2 Health Check для мониторинга

```bash
# Добавить в систему мониторинга
curl -s http://45.144.177.128:8765/health | jq .status
# Ожидаемый ответ: "healthy"
```

### 7.3 Метрики

```bash
curl -s http://45.144.177.128:8765/info
# {
#   "rate_limit_requests": 100,
#   "rate_limit_window": 60,
#   "max_connections": 20,
#   "avito_user_id": "157920..."
# }
```

---

## Приложение A: Credentials

```yaml
# MCP Server
URL: http://45.144.177.128:8765
API_KEY: BattCRM_Avito_Secret_2024

# Avito API (управляется MCP Server)
CLIENT_ID: trTwLtOgDpAtNnq412ec
CLIENT_SECRET: VgFdlaIhwk5nLwbyf_i5K6kiji3skDtAmqqgF5lH
USER_ID: 157920214

# n8n Webhook
WEBHOOK_URL: https://n8n.n8nsrv.ru/webhook/avito
```

---

## Приложение B: Команды управления

```bash
# Перезапуск MCP Server
ssh root@45.144.177.128 "cd /opt/avito-api && docker compose restart"

# Просмотр логов
ssh root@45.144.177.128 "docker logs avito-messenger-api --tail 50"

# Обновление конфига
ssh root@45.144.177.128 "nano /opt/avito-api/.env"
ssh root@45.144.177.128 "cd /opt/avito-api && docker compose down && docker compose up -d"

# Тест API
curl -H "X-API-Key: BattCRM_Avito_Secret_2024" http://45.144.177.128:8765/api/chats
```
