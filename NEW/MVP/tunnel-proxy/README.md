# Tunnel Proxy

Туннель для проксирования трафика через мобильный IP.

## Архитектура

```
┌─────────────────────────────────────────────────────────────┐
│                     SERVER (VPS)                             │
├─────────────────────────────────────────────────────────────┤
│  MCP Servers                                                │
│  ├── avito-messenger-api ──┐                                │
│  ├── vk-community-api ─────┼── HTTP_PROXY=http://tunnel:8080│
│  ├── whatsapp-api ─────────┤                                │
│  └── parsers ──────────────┘                                │
│                                                             │
│  tunnel-proxy (localhost:8080) ◄─── WebSocket ──────────────┼──┐
└─────────────────────────────────────────────────────────────┘  │
                                                                  │
┌─────────────────────────────────────────────────────────────┐  │
│               MOBILE (телефон/Termux)                        │◄─┘
├─────────────────────────────────────────────────────────────┤
│  mobile-tunnel (0.0.0.0:8765)                               │
│       ↓                                                     │
│  HTTP requests → Mobile Internet → Target                   │
└─────────────────────────────────────────────────────────────┘
```

## Компоненты

### Mobile (телефон)

Простой WebSocket сервер + HTTP клиент. Получает запросы через туннель, выполняет их через мобильный интернет.

```bash
cd mobile
cp .env.example .env
nano .env  # задать TUNNEL_SECRET

chmod +x start.sh
./start.sh
```

### Server (VPS)

HTTP прокси который пробрасывает запросы через туннель на телефон.

```bash
cd server
cp .env.example .env
nano .env  # задать MOBILE_WS_URL и TUNNEL_SECRET

docker-compose up -d
```

## Использование

### 1. Запустить на телефоне

```bash
# Termux
pkg install python
pip install aiohttp

export TUNNEL_SECRET="my_secret"
python tunnel.py
```

### 2. Запустить на сервере

```bash
docker run -d \
  --name tunnel-proxy \
  --network eldoleado \
  -p 8080:8080 \
  -e MOBILE_WS_URL=ws://PHONE_IP:8765/ws \
  -e TUNNEL_SECRET=my_secret \
  tunnel-proxy
```

### 3. Использовать в MCP серверах

```bash
docker run -d \
  --name avito-messenger-api \
  --network eldoleado \
  -e HTTP_PROXY=http://tunnel-proxy:8080 \
  -e HTTPS_PROXY=http://tunnel-proxy:8080 \
  avito-messenger-api:v2.0.0
```

## API

### Mobile Tunnel

```
GET  /health     - статус
GET  /ws         - WebSocket endpoint (для сервера)
```

### Server Proxy

```
GET  /health     - статус
*    /*          - проксирование запросов
```

## Протокол

### Server → Mobile (запрос)

```json
{
  "id": "uuid",
  "action": "http",
  "method": "GET",
  "url": "https://api.avito.ru/...",
  "headers": {"Authorization": "Bearer ..."},
  "body": null,
  "timeout": 30
}
```

### Mobile → Server (ответ)

```json
{
  "id": "uuid",
  "status": 200,
  "headers": {"content-type": "application/json"},
  "body": "{...}",
  "body_encoded": false
}
```

## Безопасность

- `TUNNEL_SECRET` - общий секрет для авторизации
- WebSocket через WS (внутри VPN) или WSS (публично)
- Телефон должен быть за NAT или VPN

## Termux Quick Start

```bash
# Установка
pkg update && pkg install python

# Запуск
cd mobile-tunnel
pip install aiohttp
export TUNNEL_SECRET="secret123"
export WS_PORT=8765
python tunnel.py

# Фоновый режим
nohup python tunnel.py > tunnel.log 2>&1 &
```
