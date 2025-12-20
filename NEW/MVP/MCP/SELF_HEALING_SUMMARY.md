# Self-Healing & Monitoring System

**Дата:** 2025-12-20

---

## Что сделано

### Фаза 1: WhatsApp Baileys (ГОТОВО)

Обновлен `mcp-whatsapp-baileys/` (TypeScript/Node.js):

**Новые файлы:**
- `src/alerts.ts` - AlertService для отправки уведомлений в Telegram + n8n
- `src/metrics.ts` - MetricsCollector для сбора метрик (сообщения, ошибки, реконнекты)

**Обновленные файлы:**
- `src/types.ts` - добавлен интерфейс HealthResponse
- `src/baileys.ts` - интегрированы AlertService и MetricsCollector
  - Exponential backoff: 3s -> 6s -> 12s -> 24s -> 48s -> 60s (max)
  - Максимум 10 попыток переподключения
  - Стратегии восстановления по кодам ошибок (401, 403, 408, 515 и др.)
- `src/index.ts` - расширенные /health эндпоинты

**Конфигурация (.env):**
```env
ALERT_TELEGRAM_BOT_TOKEN=123456:ABC...
ALERT_TELEGRAM_CHAT_ID=-1001234567890
ALERT_N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/alerts
```

---

### Фаза 2: Общая Python библиотека (ГОТОВО)

Создана общая Python библиотека в `shared/`:
- `self_healing.py` - класс SelfHealingMixin с exponential backoff
- `watchdog.py` - ConnectionWatchdog для мониторинга соединений
- `health.py` - класс HealthChecker с метриками
- `alerts.py` - AlertService для уведомлений в Telegram + n8n

### Фаза 3: Интеграция в Python MCP серверы (ГОТОВО)

#### mcp-vk
- `config.py` - добавлены настройки алертов (alert_telegram_bot_token, alert_telegram_chat_id, alert_n8n_webhook_url)
- `app.py` - интегрированы health_checker и alert_service
  - `/health` эндпоинт с health_score
  - `/health/extended` эндпоинт с полными метриками
  - Запись метрик при отправке сообщения (строка 448)
  - Запись метрик при получении вебхука (строка 914)

#### mcp-max
- `config.py` - добавлены ALERT_TELEGRAM_BOT_TOKEN, ALERT_TELEGRAM_CHAT_ID, ALERT_N8N_WEBHOOK_URL
- `app.py` - интегрированы health_checker и alert_service
  - `/health` эндпоинт с health_score
  - `/health/extended` эндпоинт с полными метриками
  - Запись метрик при отправке (строка 485)
  - Запись метрик при получении вебхука (строка 758)
  - Запись ошибок при API ошибках (строка 489)

#### mcp-telegram
- `config.py` - добавлены ALERT_TELEGRAM_BOT_TOKEN, ALERT_TELEGRAM_CHAT_ID, ALERT_N8N_WEBHOOK_URL
- `app.py` - интегрированы health_checker и alert_service
  - `/health` эндпоинт с health_score
  - `/health/extended` эндпоинт с полными метриками
  - Запись метрик при отправке (строка 549)
  - Запись метрик при получении вебхука (строка 820)
  - Запись ошибок при API ошибках (строка 560)

#### mcp-avito (сделано ранее)
- Уже интегрировано в прошлой сессии

---

## Новые эндпоинты

Все серверы теперь имеют:

### GET /health
```json
{
  "status": "healthy",
  "service": "vk-community-api",
  "version": "2.1.0",
  "accounts_loaded": 5,
  "health_score": 95,
  "timestamp": "2025-12-20T10:30:00"
}
```

### GET /health/extended
```json
{
  "channel": "vk",
  "version": "2.1.0",
  "status": "healthy",
  "health_score": 95,
  "uptime_seconds": 86400,
  "sessions": {
    "abc123": {
      "status": "connected",
      "messages_sent_24h": 150,
      "messages_received_24h": 230,
      "errors_24h": 3,
      "last_activity": "2025-12-20T10:30:00"
    }
  },
  "totals": {
    "messages_sent_24h": 150,
    "messages_received_24h": 230,
    "errors_24h": 3
  }
}
```

---

## Настройка

Для включения алертов добавить в `.env` файл каждого сервера:

```env
# Для mcp-vk (маленькие буквы)
alert_telegram_bot_token=123456:ABC...
alert_telegram_chat_id=-1001234567890
alert_n8n_webhook_url=https://n8n.n8nsrv.ru/webhook/alerts

# Для mcp-max, mcp-telegram (БОЛЬШИЕ буквы)
ALERT_TELEGRAM_BOT_TOKEN=123456:ABC...
ALERT_TELEGRAM_CHAT_ID=-1001234567890
ALERT_N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/alerts
```

---

## Все измененные/созданные файлы

```
NEW/MVP/MCP/
├── mcp-whatsapp-baileys/
│   └── src/
│       ├── alerts.ts (НОВЫЙ)
│       ├── metrics.ts (НОВЫЙ)
│       ├── types.ts (обновлен)
│       ├── baileys.ts (интегрирован self-healing)
│       └── index.ts (расширен /health)
│
├── shared/
│   ├── __init__.py (обновлены экспорты)
│   ├── self_healing.py (НОВЫЙ)
│   ├── watchdog.py (НОВЫЙ)
│   ├── health.py (НОВЫЙ)
│   └── alerts.py (НОВЫЙ)
│
├── mcp-vk/
│   ├── config.py (добавлены настройки алертов)
│   └── app.py (интегрирован health_checker, метрики)
│
├── mcp-max/
│   ├── config.py (добавлены настройки алертов)
│   └── app.py (интегрирован health_checker, метрики)
│
├── mcp-telegram/
│   ├── config.py (добавлены настройки алертов)
│   └── app.py (интегрирован health_checker, метрики)
│
├── mcp-avito/
│   ├── config.py (уже были настройки алертов)
│   └── app.py (уже интегрировано)
│
└── mcp-canary/ (НОВЫЙ СЕРВИС)
    ├── app.py
    ├── config.py
    ├── alerter.py
    ├── scheduler.py
    ├── requirements.txt
    ├── .env.example
    ├── monitors/
    │   ├── __init__.py
    │   ├── base.py
    │   ├── whatsapp.py
    │   ├── telegram.py
    │   ├── vk.py
    │   ├── max.py
    │   └── avito.py
    └── schemas/
        ├── __init__.py
        └── responses.py
```

---

## Фаза 4: API Canary Service (ГОТОВО)

Создан `mcp-canary/` — сервис мониторинга всех API эндпоинтов:

```
mcp-canary/
├── app.py              # FastAPI сервер (порт 8790)
├── config.py           # Настройки + прокси
├── alerter.py          # Алерты Telegram + n8n
├── scheduler.py        # APScheduler для периодических проверок
├── monitors/
│   ├── base.py         # BaseMonitor class
│   ├── whatsapp.py     # WhatsAppMonitor
│   ├── telegram.py     # TelegramMonitor
│   ├── vk.py           # VKMonitor
│   ├── max.py          # MaxMonitor
│   └── avito.py        # AvitoMonitor
├── schemas/
│   └── responses.py    # Валидация схем ответов
├── requirements.txt
└── .env.example
```

**Функции:**
- Периодические health checks (каждую 1 мин)
- Проверка API эндпоинтов (каждые 5 мин)
- Поддержка резидентских прокси для внешних API
- Алерты при сбоях (Telegram + n8n)
- Dashboard статусов (/status)

**Эндпоинты Canary:**
- `GET /status` — статус всех каналов
- `GET /status/{channel}` — детальный статус канала
- `POST /test/{channel}` — принудительный тест
- `POST /test/all` — тест всех каналов
- `GET /history` — история проверок

**Деплой:** FI сервер (217.145.79.27:8790)

---

## Что дальше (Опционально)

1. Экспорт метрик в Prometheus
2. Агрегированные алерты (не спамить если несколько каналов упало)
3. Web dashboard для визуализации

---

## Тестирование

Проверка синтаксиса прошла успешно:
```
python -m py_compile mcp-vk/app.py mcp-max/app.py mcp-telegram/app.py
# Syntax OK
```

Для проверки health эндпоинтов после деплоя:
```bash
# WhatsApp Baileys (FI сервер)
curl http://217.145.79.27:3000/health
curl http://217.145.79.27:3000/health/extended

# VK (RU сервер)
curl http://45.144.177.128:8767/health
curl http://45.144.177.128:8767/health/extended

# MAX (RU сервер)
curl http://45.144.177.128:8768/health
curl http://45.144.177.128:8768/health/extended

# Telegram (FI сервер)
curl http://217.145.79.27:8767/health
curl http://217.145.79.27:8767/health/extended

# Avito (RU сервер)
curl http://45.144.177.128:8765/health
curl http://45.144.177.128:8765/health/extended

# API Canary (FI сервер)
curl http://217.145.79.27:8790/health
curl http://217.145.79.27:8790/status
```
