# План параллельного подключения каналов: Telegram + Avito

## Текущее состояние

### Telegram (работает)
- **BAT IN Telegram** - входящие сообщения
- **BAT OUT Telegram** - исходящие сообщения
- **BAT Telegram Bot Handler** - ответы операторов через бота

### Avito (в разработке)
- **MCP Server** развёрнут: `http://45.144.177.128:8765`
- **BAT IN Avito** - требует обновления для работы с MCP
- **BAT OUT Avito** - требует обновления для работы с MCP

---

## Архитектура мультиканальной системы

```
                    ┌─────────────────────────────────────┐
                    │           КЛИЕНТЫ                   │
                    └─────────────────────────────────────┘
                              │           │
              ┌───────────────┘           └───────────────┐
              ▼                                           ▼
    ┌─────────────────┐                       ┌─────────────────┐
    │    Telegram     │                       │     Avito       │
    │   @BotUsername  │                       │   Мессенджер    │
    └────────┬────────┘                       └────────┬────────┘
             │                                         │
             │ Webhook                                 │ Webhook v2
             ▼                                         ▼
    ┌─────────────────┐                       ┌─────────────────┐
    │ n8n Telegram    │                       │   MCP Server    │
    │ Trigger Node    │                       │   :8765         │
    └────────┬────────┘                       └────────┬────────┘
             │                                         │
             │                                         │ Forward
             │                                         ▼
             │                                ┌─────────────────┐
             │                                │ n8n Webhook     │
             │                                │ /webhook/avito  │
             │                                └────────┬────────┘
             │                                         │
             ▼                                         ▼
    ┌─────────────────┐                       ┌─────────────────┐
    │ BAT IN Telegram │                       │  BAT IN Avito   │
    │ - Parse message │                       │ - Parse message │
    │ - Voice→Text    │                       │ - Voice→Text    │
    │ - Normalize     │                       │ - Normalize     │
    └────────┬────────┘                       └────────┬────────┘
             │                                         │
             │         ┌─────────────────┐             │
             └────────►│ Tenant Resolver │◄────────────┘
                       │ - Find tenant   │
                       │ - Find/Create   │
                       │   client        │
                       └────────┬────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │Universal Batcher│
                       │ - 30 sec window │
                       │ - Merge msgs    │
                       └────────┬────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │ AI Appeal Router│
                       │ - Create appeal │
                       │ - AI processing │
                       │ - Generate resp │
                       └────────┬────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │ Message Router  │
                       │ - Route by      │
                       │   channel       │
                       └────────┬────────┘
                                │
             ┌──────────────────┴──────────────────┐
             │                                     │
             ▼                                     ▼
    ┌─────────────────┐                   ┌─────────────────┐
    │BAT OUT Telegram │                   │ BAT OUT Avito   │
    │ - Telegram API  │                   │ - MCP Server    │
    └────────┬────────┘                   └────────┬────────┘
             │                                     │
             │                                     │ HTTP Request
             │                                     ▼
             │                            ┌─────────────────┐
             │                            │   MCP Server    │
             │                            │ - Token mgmt    │
             │                            │ - Rate limit    │
             │                            │ - Avito API     │
             │                            └────────┬────────┘
             │                                     │
             ▼                                     ▼
    ┌─────────────────┐                   ┌─────────────────┐
    │    Telegram     │                   │     Avito       │
    │   Messenger     │                   │   Messenger     │
    └─────────────────┘                   └─────────────────┘
```

---

## Унифицированный формат сообщений

Оба канала приводятся к единому формату:

```javascript
{
  // Идентификация канала
  channel: "telegram" | "avito",
  external_user_id: "string",      // ID пользователя в канале
  external_chat_id: "string",      // ID чата
  external_message_id: "string",   // ID сообщения

  // Контент
  text: "string",                  // Текст + транскрипция голоса
  timestamp: "ISO 8601",

  // Данные клиента
  client_name: "string | null",
  client_phone: "string | null",   // Только WhatsApp/MAX
  client_email: "string | null",

  // Медиа
  media: {
    has_voice: boolean,
    voice_url: "string | null",           // Avito: URL, Telegram: null
    voice_file_id: "string | null",       // Telegram: file_id
    voice_transcribed_text: "string | null",

    has_images: boolean,
    images: [{
      url: "string | null",               // Avito
      file_id: "string | null",           // Telegram
      caption: "string | null"
    }],

    has_video: boolean,
    has_document: boolean
  },

  // Метаданные
  meta: {
    ad_channel: "telegram" | "avito",
    ad_id: "string | null",               // Avito: item_id
    original_media_type: "text|voice|photo|video",
    raw: { /* оригинальный payload */ },

    // Telegram-specific
    username: "@username",
    bot_token: "string",

    // Avito-specific
    chat_type: "u2i | u2u",
    user_id: number
  }
}
```

---

## План реализации

### Фаза 1: Подготовка (выполнено)

- [x] Развернуть MCP Server на 45.144.177.128:8765
- [x] Настроить credentials (CLIENT_ID, SECRET, USER_ID)
- [x] Проверить получение токена
- [x] Создать ТЗ по интеграции

### Фаза 2: Настройка Webhook

```bash
# Подписка на webhook Avito
curl -X POST http://45.144.177.128:8765/api/webhook/subscribe \
  -H "X-API-Key: BattCRM_Avito_Secret_2024" \
  -H "Content-Type: application/json" \
  -d '{"url": "http://45.144.177.128:8765/webhook/avito", "version": 2}'
```

### Фаза 3: Обновление BAT IN Avito

**Изменения:**
1. Webhook Trigger принимает уже нормализованный формат от MCP Server
2. Обработка голоса: HTTP Download (URL) вместо Telegram file_id
3. Формат уже совместим с Tenant Resolver

**Новый workflow:**
```
n8n Webhook (/webhook/avito)
      │
      │ Нормализованный формат от MCP Server
      ▼
┌─────────────────────────┐
│ Has Voice? (Switch)     │
│ media.has_voice === true│
└─────────────────────────┘
      │
      ├── YES ──► Download Voice (HTTP) ──► Transcribe (Whisper) ──► Merge
      │
      └── NO ───────────────────────────────────────────────────────► Merge
                                                                        │
      ┌─────────────────────────────────────────────────────────────────┘
      ▼
┌─────────────────────────┐
│ Execute Tenant Resolver │
└─────────────────────────┘
      │
      ▼
┌─────────────────────────┐
│ Execute Universal       │
│ Batcher                 │
└─────────────────────────┘
```

### Фаза 4: Обновление BAT OUT Avito

**Изменения:**
1. Убрать логику получения/обновления токена (MCP Server делает это)
2. Заменить прямой вызов Avito API на HTTP Request к MCP Server

**HTTP Request конфигурация:**
```yaml
Method: POST
URL: http://45.144.177.128:8765/api/messages/send
Headers:
  X-API-Key: BattCRM_Avito_Secret_2024
Body:
  {
    "chat_id": "{{ external_chat_id }}",
    "text": "{{ message_text }}"
  }
```

### Фаза 5: Тестирование

| Тест | Telegram | Avito |
|------|----------|-------|
| Входящее текстовое | ✓ Работает | Проверить |
| Входящее с картинкой | ✓ Работает | Проверить |
| Входящее голосовое | ✓ Работает | Проверить |
| Создание обращения | ✓ Работает | Проверить |
| Ответ ИИ | ✓ Работает | Проверить |
| Ответ оператора | ✓ Работает | Проверить |
| Доставка ответа | ✓ Работает | Проверить |

---

## Таблица маршрутизации

### Message Router Switch

```javascript
// В BAT Message Router
switch (channel) {
  case "telegram":
    // Execute BAT OUT Telegram
    break;
  case "avito":
    // Execute BAT OUT Avito
    break;
  case "vk":
    // Execute BAT OUT VK
    break;
  case "whatsapp":
    // Execute BAT OUT WhatsApp
    break;
  case "max":
    // Execute BAT OUT MAX
    break;
  default:
    throw new Error(`Unknown channel: ${channel}`);
}
```

---

## База данных

### Таблица clients

```sql
-- Клиент может иметь ID в разных каналах
CREATE TABLE clients (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES tenants(id),

  -- Идентификаторы в каналах
  telegram_id VARCHAR(50),
  avito_id VARCHAR(50),
  vk_id VARCHAR(50),
  whatsapp_id VARCHAR(50),
  max_id VARCHAR(50),

  -- Контактные данные
  phone VARCHAR(20),
  name VARCHAR(255),
  email VARCHAR(255),

  created_at TIMESTAMP DEFAULT NOW()
);

-- Индексы для быстрого поиска
CREATE INDEX idx_clients_telegram ON clients(telegram_id);
CREATE INDEX idx_clients_avito ON clients(avito_id);
```

### Таблица appeals

```sql
-- Обращение привязано к каналу
CREATE TABLE appeals (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES tenants(id),
  client_id UUID REFERENCES clients(id),

  -- Канал обращения
  channel VARCHAR(20) NOT NULL,  -- telegram, avito, vk, ...
  external_chat_id VARCHAR(100), -- ID чата в канале

  status VARCHAR(50),
  created_at TIMESTAMP DEFAULT NOW()
);
```

### Таблица messages_history

```sql
-- История сообщений
CREATE TABLE messages_history (
  id UUID PRIMARY KEY,
  tenant_id UUID,
  appeal_id UUID REFERENCES appeals(id),

  message_type VARCHAR(20),      -- client, operator, agent
  message_text TEXT,

  channel VARCHAR(20),           -- telegram, avito, ...
  external_message_id VARCHAR(100),
  external_chat_id VARCHAR(100),

  created_at TIMESTAMP DEFAULT NOW()
);
```

---

## Логика Tenant Resolver

```javascript
// Поиск/создание клиента по каналу
async function resolveClient(channel, external_user_id, tenant_id) {
  // 1. Поиск существующего клиента
  let client = await db.query(`
    SELECT * FROM clients
    WHERE tenant_id = $1
    AND ${channel}_id = $2
  `, [tenant_id, external_user_id]);

  // 2. Если не найден - создать
  if (!client) {
    client = await db.query(`
      INSERT INTO clients (tenant_id, ${channel}_id, name)
      VALUES ($1, $2, $3)
      RETURNING *
    `, [tenant_id, external_user_id, client_name]);
  }

  return client;
}
```

---

## Мониторинг

### Health Checks

```bash
# Telegram (через n8n)
# Проверить что workflow BAT IN Telegram активен

# Avito MCP Server
curl -s http://45.144.177.128:8765/health
# {"status":"healthy","service":"avito-messenger-api"}

# n8n Webhook
curl -s https://n8n.n8nsrv.ru/webhook/avito -X POST -d '{"test":true}'
```

### Логи

```bash
# MCP Server логи
ssh root@45.144.177.128 "docker logs avito-messenger-api -f --tail 100"

# n8n логи
# Смотреть в интерфейсе n8n: Executions
```

---

## Чеклист запуска

### Telegram (уже работает)
- [x] Telegram Bot создан
- [x] Webhook настроен
- [x] BAT IN Telegram активен
- [x] BAT OUT Telegram активен
- [x] Telegram Bot Handler активен

### Avito (в процессе)
- [x] MCP Server развёрнут
- [x] Credentials настроены
- [ ] Webhook подписан
- [ ] BAT IN Avito обновлён
- [ ] BAT OUT Avito обновлён
- [ ] Message Router проверен
- [ ] Интеграционный тест пройден

---

## Следующие шаги

1. **Подписать webhook Avito** через MCP Server
2. **Обновить BAT IN Avito** в n8n
3. **Обновить BAT OUT Avito** в n8n
4. **Протестировать** полный цикл сообщений
5. **Мониторинг** - настроить алерты на ошибки
