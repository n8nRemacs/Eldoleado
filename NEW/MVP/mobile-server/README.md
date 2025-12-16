# Mobile Server

Мобильный сервер для туннелирования мессенджеров. Работает на Android (Termux) или Linux.

## Архитектура

```
┌─────────────────────────────────────────────────────────┐
│  Mobile Server                                          │
│                                                         │
│  ┌─────────────────┐     ┌─────────────────────────┐   │
│  │  Tunnel Proxy   │────►│  Webhook Server (:8000) │   │
│  │  (WebSocket)    │     └─────────────────────────┘   │
│  └────────┬────────┘                ▲                   │
│           │                         │ incoming          │
│  ┌────────┴─────────────────────────┴──────────────┐   │
│  │  Channels                                        │   │
│  │  ├── WhatsApp (Baileys)      :3001              │   │
│  │  ├── Telegram (Pyrogram)     :3002              │   │
│  │  ├── Avito (HTTP reverse)    :3003              │   │
│  │  ├── MAX (HTTP reverse)      :3004              │   │
│  │  ├── VK User                 :3005              │   │
│  │  └── HTTP Proxy              :3010              │   │
│  └─────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
```

## Установка

### Termux (Android)

```bash
# Установка зависимостей
pkg update && pkg upgrade
pkg install python nodejs git

# Клонирование
git clone <repo> && cd mobile-server

# Настройка
cp .env.example .env
nano .env  # заполнить TUNNEL_URL, TUNNEL_SECRET, API ключи

# Запуск
chmod +x *.sh
./start.sh
```

### Linux

```bash
# Установка
sudo apt install python3 python3-venv nodejs npm

# Запуск
./start.sh
```

## Конфигурация

### .env

```bash
# Tunnel
TUNNEL_URL=wss://tunnel.eldoleado.ru/ws
TUNNEL_SECRET=your_secret

# Telegram
TELEGRAM_API_ID=12345
TELEGRAM_API_HASH=abc123
TELEGRAM_PHONE=+79001234567

# VK
VK_ACCESS_TOKEN=vk_token

# Avito/MAX - cookies после логина
AVITO_SESSION_COOKIE=...
MAX_SESSION_COOKIE=...
```

## Каналы

| Канал | Порт | Тип | Авторизация |
|-------|------|-----|-------------|
| WhatsApp | 3001 | Baileys | QR код |
| Telegram | 3002 | Pyrogram | Телефон + код |
| Avito | 3003 | HTTP | Cookies |
| MAX | 3004 | HTTP | Cookies |
| VK | 3005 | VK API | Access Token |
| HTTP Proxy | 3010 | HTTP | Нет |

## API Endpoints

Все каналы предоставляют единый API:

```
GET  /health           - статус
GET  /dialogs          - список диалогов
GET  /history/{id}     - история сообщений
POST /send             - отправить сообщение
POST /send/photo       - отправить фото
POST /send/document    - отправить документ
```

### Пример отправки

```bash
curl -X POST http://localhost:3002/send \
  -H "Content-Type: application/json" \
  -d '{"chat_id": 123456, "text": "Привет!"}'
```

## Протокол туннеля

### Сервер → Телефон (команда)

```json
{
  "id": "uuid",
  "action": "http_request",
  "service": "telegram",
  "method": "POST",
  "path": "/send",
  "body": {"chat_id": 123, "text": "Hello"}
}
```

### Телефон → Сервер (ответ)

```json
{
  "id": "uuid",
  "status": 200,
  "body": {"message_id": 456}
}
```

### Телефон → Сервер (входящее)

```json
{
  "action": "incoming",
  "service": "telegram",
  "data": {
    "event": "message",
    "chat_id": 123,
    "text": "Ответ клиента"
  }
}
```

## Скрипты

```bash
./start.sh              # Запуск всего
./stop.sh               # Остановка всего
./start_individual.sh telegram  # Только Telegram
./start_individual.sh all       # Все по отдельности
```

## Логи

```bash
# Все логи
tail -f logs/mobile-server.log

# Конкретный канал
tail -f channels/telegram/logs/*.log
```
