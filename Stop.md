# Stop Session - 2025-12-23

## Что сделано сегодня

### 1. Humanized отправка сообщений на 155.212.221.189

Развернут humanizer модуль для задержек перед отправкой сообщений:

| Период | Время | Задержка |
|--------|-------|----------|
| Рабочее | 09:00-18:00 | 3-7 сек |
| Нерабочее | 18:00-00:00, 07:00-09:00 | 15-45 сек |
| Ночное | 00:00-07:00 | 1-3 мин |

**Статус сервисов:**

| Сервис | Порт | Humanizer | Typing |
|--------|------|-----------|--------|
| `avito-messenger-api` | 8766 | ✅ | ❌ (нет в API) |
| `max-bot-api` | 8768 | ✅ | ✅ typing_on |
| `telegram-bot-api` | 8761 | ✅ | ✅ typing |
| `telegram-user-api` | 8762 | ⏳ (ждёт credentials) | ✅ |

### 2. Telegram Bot API (порт 8761)

- Развернут `/opt/mcp-telegram/` из локального проекта
- Docker image: `telegram-bot-api:v1.0.3`
- Humanizer + typing action интегрированы
- Работает через registry ботов (multi-tenant)

### 3. Telegram User API (порт 8762)

- Развернут `/opt/mcp-telegram-user/`
- Docker image: `telegram-user-api:v1.0.0`
- **НЕ ЗАПУЩЕН** — требуются реальные credentials:
  - `TELEGRAM_API_ID` — из my.telegram.org
  - `TELEGRAM_API_HASH` — из my.telegram.org
  - `TELEGRAM_PHONE_NUMBER` — телефон для авторизации

### 4. Исправлен Redis в shared/storage.py

**Было:**
```python
REDIS_URL = os.environ.get("REDIS_URL", "redis://redis:6379")
```

**Стало:**
```python
REDIS_URL = os.environ.get("REDIS_URL", "redis://127.0.0.1:6379")
```

### 5. Апгрейд сервера

- Было: 1 core, 1 GB RAM
- Стало: 2 cores, 4 GB RAM
- Причина: Docker builds перегружали сервер

---

## Текущая архитектура 155.212.221.189

```
┌────────────────────────────────────────────────────────────┐
│                    MessagerOne Server                       │
│                    155.212.221.189                          │
├────────────────────────────────────────────────────────────┤
│  avito-messenger-api:8766   │  max-bot-api:8768            │
│  telegram-bot-api:8761      │  telegram-user-api:8762 (⏳)  │
│  mcp-whatsapp-ip1:8769      │  android-api:8780            │
│  redis:6379                 │  tunnel-server:80            │
└────────────────────────────────────────────────────────────┘
```

---

## Следующая сессия (Start.md)

**Приоритет 1:** Авторизация каналов через Android

1. **Avito** — Puppeteer на сервере (login/password → sessid)
2. **MAX** — QR авторизация (User API opcode 20)
3. **Telegram Bot** — ввод токена от @BotFather
4. **Telegram User** — SMS авторизация (позже)

---

## Тестовые команды

```bash
# Проверить все сервисы
ssh root@155.212.221.189 "docker ps --format 'table {{.Names}}\t{{.Status}}\t{{.Ports}}'"

# Логи humanized сервисов
ssh root@155.212.221.189 "docker logs avito-messenger-api --tail 20"
ssh root@155.212.221.189 "docker logs max-bot-api --tail 20"
ssh root@155.212.221.189 "docker logs telegram-bot-api --tail 20"

# Проверить humanizer в контейнере
ssh root@155.212.221.189 "docker exec avito-messenger-api grep -l 'humanizer' /app/*.py"

# WhatsApp
curl http://155.212.221.189:8769/sessions
```

---

## Humanized Client файлы

| Клиент | Путь |
|--------|------|
| Avito | `NEW/MVP/MCP/mcp-avito-user/humanized_client.py` |
| MAX Bot | `NEW/MVP/MCP/mcp-max/humanized_client.py` |
| MAX User | `NEW/MVP/MCP/Max-user/humanized_client.py` |
| MAX User Client | `NEW/MVP/MCP/Max-user/max_user_client.py` |
