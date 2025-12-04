# START API - Eldoleado Channels Integration

## ПЕРВЫМ ДЕЛОМ — Синхронизация

```bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
```

**После git pull — ПЕРЕЧИТАЙ этот файл заново (StartApi.md), пропустив этот блок синхронизации!**
Иначе будешь работать по устаревшей версии документа.

---

## Дата и время последнего обновления
**3 декабря 2025, 18:35 (UTC+4)**

---

## Быстрый старт

```
ВСЕ API КАНАЛЫ ЗАДЕПЛОЕНЫ И РАБОТАЮТ!

Развёрнуто на сервере 45.144.177.128:
- Avito MCP Server      → порт 8765
- WhatsApp MCP Server   → порт 8766 (Wappi.pro)
- VK MCP Server         → порт 8767 (прямой API)
- MAX MCP Server        → порт 8768 (прямой API)
- Instagram MCP Server  → порт 8769 (Graph API)
- Form API Server       → порт 8770

НОВАЯ АРХИТЕКТУРА (03.12.2025):
- Асинхронная обработка через Redis очереди
- 10 параллельных Debouncer воркеров для батчинга
- Файлы для импорта в workflows_to_import/

Следующие шаги:
1. Импортировать workflows из workflows_to_import/
2. Заменить placeholder'ы на реальные ID
3. Активировать workflows
4. Протестировать полный цикл IN → Batching → OUT
```

---

## Режим работы

**АВТОНОМНЫЙ РЕЖИМ** - выполняй все операции сразу без вопросов да/нет:
- SSH команды на серверах
- Docker операции
- Создание/редактирование файлов
- Деплой сервисов
- Тестирование API

---

## Новая архитектура обработки сообщений (Redis очереди)

```
ВХОД:
Каналы (Avito/VK/WhatsApp/MAX/Telegram)
    ↓ webhook/trigger
BAT_IN_{Channel} → нормализация → RPUSH queue:incoming
    ↓
BAT_Queue_Processor (5 сек) → группировка по chat_id
    ↓
queue:batch:{channel}:{chat_id} + queue:debounce:pending
    ↓
BAT_Batch_Debouncer_1..10 (10 сек) → ждёт тишины 20 сек
    ↓
Client Resolver → Appeal Manager

ВЫХОД:
Appeal Manager / AI Worker
    ↓ RPUSH queue:outgoing
BAT_Out_Processor (3 сек) → роутинг по channel
    ↓
BAT_OUT_{Channel} → отправка в канал
```

### Входные воркфлоу (BAT_IN_*)

| Воркфлоу | Триггер | Описание |
|----------|---------|----------|
| BAT_IN_Avito | Webhook | Получает от MCP, нормализует, → queue:incoming |
| BAT_IN_WhatsApp | Webhook | Получает от MCP, нормализует, → queue:incoming |
| BAT_IN_VK | Webhook | Получает от MCP, нормализует, → queue:incoming |
| BAT_IN_MAX | Webhook | Получает от MCP, нормализует, → queue:incoming |
| BAT_IN_Telegram | Telegram Trigger | Встроенный n8n триггер, → queue:incoming |

### Батчинг воркфлоу

| Воркфлоу | Интервал | Описание |
|----------|----------|----------|
| BAT_Queue_Processor | 5 сек | Забирает из queue:incoming, группирует по chat_id |
| BAT_Batch_Debouncer_1..10 | 10 сек | 10 параллельных воркеров, ждут тишины 20 сек |

### Выходные воркфлоу (BAT_OUT_*)

| Воркфлоу | Вызывается из | Описание |
|----------|---------------|----------|
| BAT_Out_Processor | Schedule (3 сек) | Забирает из queue:outgoing, роутит по каналам |
| BAT_OUT_Avito | BAT_Out_Processor | Отправка через MCP |
| BAT_OUT_WhatsApp | BAT_Out_Processor | Отправка через MCP |
| BAT_OUT_VK | BAT_Out_Processor | Отправка через MCP |
| BAT_OUT_MAX | BAT_Out_Processor | Отправка через MCP |
| BAT_OUT_Telegram | BAT_Out_Processor | Отправка через n8n Telegram |

---

## Redis ключи

| Ключ | Тип | Описание |
|------|-----|----------|
| `queue:incoming` | LIST | Входящие сообщения от всех каналов |
| `queue:outgoing` | LIST | Сообщения на отправку |
| `queue:batch:{channel}:{chat_id}` | LIST | Сообщения одного чата (для батчинга) |
| `queue:debounce:pending` | LIST | Задачи для дебаунсеров |
| `last_seen:{channel}:{chat_id}` | STRING | Время последнего сообщения (для debounce) |
| `lock:batch:{channel}:{chat_id}` | STRING | Lock на батч (TTL 300 сек) |

---

## Параметры батчинга

| Параметр | Значение | Описание |
|----------|----------|----------|
| Silence timeout | 20 сек | Сколько ждать тишины перед отправкой |
| Max wait | 300 сек | Максимальное время ожидания |
| Queue Processor interval | 5 сек | Интервал проверки входящей очереди |
| Debouncer interval | 10 сек | Интервал проверки задач дебаунсера |
| Out Processor interval | 3 сек | Интервал проверки исходящей очереди |
| Debouncer workers | 10 шт | Количество параллельных воркеров |

---

## Файлы для импорта

**Папка:** `workflows_to_import/`

```
IN воркфлоу:
- BAT_IN_Avito.json
- BAT_IN_WhatsApp.json
- BAT_IN_VK.json
- BAT_IN_MAX.json
- BAT_IN_Telegram.json

Батчинг:
- BAT_Queue_Processor.json
- BAT_Batch_Debouncer_1.json ... BAT_Batch_Debouncer_10.json

OUT воркфлоу:
- BAT_Out_Processor.json
- BAT_OUT_Avito.json
- BAT_OUT_WhatsApp.json
- BAT_OUT_VK.json
- BAT_OUT_MAX.json
- BAT_OUT_Telegram.json
```

### При импорте заменить

| Placeholder | Что нужно |
|-------------|-----------|
| `TELEGRAM_BOT_CRED_ID` | ID credential для Telegram Bot |
| `OUT_TELEGRAM_WORKFLOW_ID` | ID BAT_OUT_Telegram после импорта |

---

## Архитектура каналов

```
┌─────────────────────────────────────────────────────────────┐
│                    КАНАЛЫ ELDOLEADO                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐        │
│  │Telegram │  │  Avito  │  │   VK    │  │WhatsApp │        │
│  │  Bot    │  │   MCP   │  │   MCP   │  │   MCP   │        │
│  └────┬────┘  └────┬────┘  └────┬────┘  └────┬────┘        │
│       │            │            │            │              │
│       ▼            ▼            ▼            ▼              │
│  ┌────────────────────────────────────────────────────┐    │
│  │           Redis: queue:incoming                     │    │
│  └────────────────────────────────────────────────────┘    │
│                           │                                 │
│                           ▼                                 │
│  ┌────────────────────────────────────────────────────┐    │
│  │     BAT_Queue_Processor → BAT_Batch_Debouncer_*    │    │
│  │              (батчинг и дебаунсинг)                 │    │
│  └────────────────────────────────────────────────────┘    │
│                           │                                 │
│                           ▼                                 │
│  ┌────────────────────────────────────────────────────┐    │
│  │      Client Resolver → Appeal Manager → AI          │    │
│  └────────────────────────────────────────────────────┘    │
│                           │                                 │
│                           ▼                                 │
│  ┌────────────────────────────────────────────────────┐    │
│  │           Redis: queue:outgoing                     │    │
│  └────────────────────────────────────────────────────┘    │
│                           │                                 │
│                           ▼                                 │
│  ┌────────────────────────────────────────────────────┐    │
│  │       BAT_Out_Processor → BAT_OUT_{Channel}         │    │
│  └────────────────────────────────────────────────────┘    │
│                           │                                 │
│                           ▼                                 │
│  ┌────────────────────────────────────────────────────┐    │
│  │              PostgreSQL Database                    │    │
│  │        clients, appeals, messages_history           │    │
│  └────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

---

## Статус каналов

| Канал | Статус | URL/Порт | API Key |
|-------|--------|----------|---------|
| **Telegram** | Работает | n8n встроенный | Bot Token |
| **Avito** | Работает | 45.144.177.128:8765 | BattCRM_Avito_Secret_2024 |
| **WhatsApp** | Сервер работает | 45.144.177.128:8766 | BattCRM_WhatsApp_Secret_2024 |
| **VK** | Сервер работает | 45.144.177.128:8767 | BattCRM_VK_Secret_2024 |
| **MAX** | Сервер работает | 45.144.177.128:8768 | BattCRM_MAX_Secret_2024 |
| **Instagram** | Сервер работает | 45.144.177.128:8769 | BattCRM_Instagram_Secret_2024 |
| **Form** | Работает | 45.144.177.128:8770 | BattCRM_Form_Secret_2024 |
| **SMS** | Планируется | — | — |
| **Email** | Планируется | — | — |

---

## Серверы

| Сервер | IP | Порт | Назначение |
|--------|-----|------|-----------|
| **API Server** | 45.144.177.128 | 8765+ | MCP серверы каналов |
| **n8n** | n8n.n8nsrv.ru | 443 | Workflows |
| **Database** | 185.221.214.83 | 6544 | PostgreSQL |
| **Redis** | 185.221.214.83 | 6379 | Очереди, кэш |

### SSH доступы

```bash
# API Server (для MCP серверов)
ssh root@45.144.177.128
# Password: Mi31415926pSss!

# Database Server
ssh root@185.221.214.83
# Password: Mi31415926pS
```

---

## Avito MCP Server (развёрнут)

### Информация

| Параметр | Значение |
|----------|----------|
| URL | http://45.144.177.128:8765 |
| API Key | BattCRM_Avito_Secret_2024 |
| User ID | 157920214 |
| Client ID | trTwLtOgDpAtNnq412ec |
| Путь на сервере | /opt/avito-api |

### Команды

```bash
# Health check
curl http://45.144.177.128:8765/health

# Получить токен
curl -H "X-API-Key: BattCRM_Avito_Secret_2024" http://45.144.177.128:8765/api/token

# Отправить сообщение
curl -X POST http://45.144.177.128:8765/api/messages/send \
  -H "X-API-Key: BattCRM_Avito_Secret_2024" \
  -H "Content-Type: application/json" \
  -d '{"chat_id": "xxx", "text": "Привет!"}'

# Логи
ssh root@45.144.177.128 "docker logs avito-messenger-api --tail 50"

# Перезапуск
ssh root@45.144.177.128 "cd /opt/avito-api && docker compose restart"
```

### API Endpoints

| Method | Endpoint | Описание |
|--------|----------|----------|
| GET | `/health` | Health check |
| GET | `/info` | Server info |
| GET | `/api/token` | Получить/проверить токен |
| POST | `/api/token/refresh` | Обновить токен |
| GET | `/api/chats` | Список чатов |
| GET | `/api/chats/{id}` | Информация о чате |
| GET | `/api/chats/{id}/messages` | Сообщения чата |
| POST | `/api/messages/send` | Отправить текст |
| POST | `/api/messages/send/image` | Отправить картинку |
| POST | `/api/messages/send/bulk` | Массовая отправка |
| POST | `/api/webhook/subscribe` | Подписка на webhook |
| POST | `/webhook/avito` | Входящие от Avito |

---

## Form API Server (развёрнут)

### Информация

| Параметр | Значение |
|----------|----------|
| URL | http://45.144.177.128:8770 |
| API Key | BattCRM_Form_Secret_2024 |
| Путь на сервере | /opt/form-api |

### Команды

```bash
# Health check
curl http://45.144.177.128:8770/health

# Отправить форму (публичный endpoint)
curl -X POST http://45.144.177.128:8770/api/submit \
  -H "Content-Type: application/json" \
  -d '{"phone": "+79991234567", "name": "Иван Иванов", "form_id": "quiz_battery"}'

# Отправить квиз
curl -X POST http://45.144.177.128:8770/api/quiz \
  -H "Content-Type: application/json" \
  -d '{
    "phone": "+79991234567",
    "name": "Иван",
    "form_id": "quiz_battery",
    "quiz_answers": [
      {"question": "Марка телефона", "answer": "iPhone"},
      {"question": "Модель", "answer": "iPhone 12"}
    ]
  }'

# Логи
ssh root@45.144.177.128 "docker logs form-submission-api --tail 50"

# Перезапуск
ssh root@45.144.177.128 "cd /opt/form-api && docker compose restart"
```

---

## Унифицированный формат сообщений

Все каналы приводятся к единому формату:

```javascript
{
  channel: "telegram|avito|vk|whatsapp|max",
  external_user_id: "string",
  external_chat_id: "string",
  external_message_id: "string",

  text: "string",
  timestamp: "ISO 8601",

  client_name: "string|null",
  client_phone: "string|null",

  media: {
    has_voice: boolean,
    voice_url: "string|null",
    voice_transcribed_text: "string|null",
    has_images: boolean,
    images: [{url, file_id, caption}],
    has_video: boolean,
    has_document: boolean
  },

  meta: {
    ad_channel: "string",
    ad_id: "string|null",
    raw: {}
  }
}
```

---

## Документация

| Документ | Путь | Описание |
|----------|------|----------|
| ТЗ Avito | `docs/TZ_Avito_MCP_Integration.md` | Полная интеграция Avito |
| План каналов | `docs/Plan_Multi_Channel_Telegram_Avito.md` | Мультиканальная архитектура |
| Каналы Eldoleado | `Plans/eldoleado_channels_data.md` | Все каналы и данные |
| MCP Avito код | `mcp-avito/` | Исходники сервера |

---

## Порты на API сервере

| Порт | Сервис | Статус |
|------|--------|--------|
| 8765 | Avito MCP | Работает |
| 8766 | WhatsApp MCP (Wappi.pro) | Код готов |
| 8767 | VK MCP (прямой API v5.199) | Код готов |
| 8768 | MAX MCP (прямой API platform-api.max.ru) | Код готов |
| 8770 | Form API | Работает |
| 6379 | Redis | Работает |

---

## База данных

### Подключение

```
Host: 185.221.214.83
Port: 6544
Database: postgres
User: supabase_admin
Password: Mi31415926pS
```

### Ключевые таблицы

```sql
-- Клиенты с идентификаторами каналов
SELECT id, telegram_id, avito_id, vk_id, whatsapp_id, phone, name
FROM clients;

-- Обращения с каналом
SELECT id, channel, external_chat_id, status
FROM appeals;

-- История сообщений
SELECT id, channel, message_type, message_text, external_chat_id
FROM messages_history;
```

---

## Troubleshooting

### MCP Server не отвечает

```bash
# Проверить статус
ssh root@45.144.177.128 "docker ps"

# Посмотреть логи
ssh root@45.144.177.128 "docker logs avito-messenger-api --tail 100"

# Перезапустить
ssh root@45.144.177.128 "cd /opt/avito-api && docker compose restart"
```

### Ошибка авторизации API

```bash
# Проверить .env
ssh root@45.144.177.128 "cat /opt/avito-api/.env | grep -E 'CLIENT|SECRET|USER'"

# Принудительно обновить токен
curl -X POST -H "X-API-Key: BattCRM_Avito_Secret_2024" \
  http://45.144.177.128:8765/api/token/refresh
```

### Webhook не приходит

```bash
# Проверить подписку
curl -H "X-API-Key: BattCRM_Avito_Secret_2024" \
  http://45.144.177.128:8765/api/webhook/status

# Переподписаться
curl -X POST http://45.144.177.128:8765/api/webhook/subscribe \
  -H "X-API-Key: BattCRM_Avito_Secret_2024" \
  -H "Content-Type: application/json" \
  -d '{"url": "http://45.144.177.128:8765/webhook/avito", "version": 2}'
```

### Redis очереди

```bash
# Проверить длину входящей очереди
redis-cli -h 185.221.214.83 LLEN queue:incoming

# Проверить длину исходящей очереди
redis-cli -h 185.221.214.83 LLEN queue:outgoing

# Посмотреть pending задачи дебаунсеров
redis-cli -h 185.221.214.83 LLEN queue:debounce:pending

# Посмотреть все ключи батчей
redis-cli -h 185.221.214.83 KEYS "queue:batch:*"
```
