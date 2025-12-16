# Tunnel Proxy

HTTP прокси через мобильный IP для защиты от банов мессенджеров.

## Почему это работает

| Фактор | Без прокси | С прокси |
|--------|-----------|----------|
| **IP адрес** | ❌ Серверный (бан) | ✅ Мобильный |
| **TLS Fingerprint** | ❌ Python/aiohttp | ✅ Android/OkHttp |
| **Device ID** | ❌ Нет | ✅ Реальное устройство |

## Архитектура

```
┌─────────────────────────────────────────────────────────────┐
│  SERVER (VPS)                                                │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  MCP Servers ────► tunnel-server:8765/proxy                  │
│  (avito, vk, max)       │                                    │
│                         │ HTTP API                           │
│                         ▼                                    │
│              ┌─────────────────────┐                         │
│              │   tunnel-server     │                         │
│              │   (WebSocket)       │◄── Android connects here│
│              └──────────┬──────────┘                         │
│                         │                                    │
└─────────────────────────┼────────────────────────────────────┘
                          │ WebSocket
                          ▼
┌─────────────────────────────────────────────────────────────┐
│  ANDROID APP (TunnelService)                                 │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  1. Подключается к tunnel-server по WebSocket                │
│  2. Получает HTTP запросы                                    │
│  3. Выполняет через OkHttp (Android TLS fingerprint!)        │
│  4. Возвращает ответы                                        │
│                                                              │
│  IP = мобильный ✅                                           │
│  TLS = Android ✅                                            │
└─────────────────────────────────────────────────────────────┘
```

## Компоненты

### 1. tunnel-server (на VPS)

WebSocket сервер + HTTP API.

```bash
# Сборка и запуск
cd server
docker build -t tunnel-server .
docker run -d --name tunnel-server \
  --network eldoleado \
  -p 8765:8765 \
  -e TUNNEL_SECRET=Mi31415926pSss! \
  -e API_KEY=BattCRM_Tunnel_Secret_2024 \
  tunnel-server
```

**Endpoints:**
- `GET /health` — статус
- `GET /devices` — список устройств
- `POST /proxy` — отправить HTTP запрос через мобильный прокси

### 2. TunnelService (в Android приложении)

Foreground Service, подключается к tunnel-server.

**Настройки в приложении:**
- Tunnel URL: `wss://your-server.com:8765/ws`
- Tunnel Secret: `Mi31415926pSss!`

## API

### POST /proxy

Отправить HTTP запрос через мобильное устройство.

```bash
curl -X POST http://tunnel-server:8765/proxy \
  -H "Content-Type: application/json" \
  -H "X-API-Key: BattCRM_Tunnel_Secret_2024" \
  -d '{
    "url": "https://api.avito.ru/messenger/v2/accounts",
    "method": "GET",
    "headers": {
      "Authorization": "Bearer token..."
    },
    "timeout": 30
  }'
```

**Response:**
```json
{
  "id": "uuid",
  "status": 200,
  "headers": {"content-type": "application/json"},
  "body": "{...}",
  "body_base64": false
}
```

### Использование в MCP серверах

```python
import aiohttp

TUNNEL_URL = "http://tunnel-server:8765/proxy"
TUNNEL_API_KEY = "BattCRM_Tunnel_Secret_2024"

async def request_via_tunnel(url, method="GET", headers=None, body=None):
    async with aiohttp.ClientSession() as session:
        async with session.post(
            TUNNEL_URL,
            headers={"X-API-Key": TUNNEL_API_KEY},
            json={
                "url": url,
                "method": method,
                "headers": headers or {},
                "body": body or ""
            }
        ) as resp:
            return await resp.json()
```

## Протокол

### Server → Android (запрос)

```json
{
  "id": "uuid",
  "action": "http",
  "method": "GET",
  "url": "https://api.avito.ru/...",
  "headers": {"Authorization": "Bearer ..."},
  "body": "",
  "body_base64": false,
  "timeout": 30
}
```

### Android → Server (ответ)

```json
{
  "id": "uuid",
  "status": 200,
  "headers": {"content-type": "application/json"},
  "body": "{\"data\": ...}",
  "body_base64": false
}
```

## Безопасность

| Параметр | Описание |
|----------|----------|
| `TUNNEL_SECRET` | Авторизация Android → Server (WebSocket) |
| `API_KEY` | Авторизация MCP → Server (HTTP API) |

## Деплой

### 1. Собрать и запустить tunnel-server

```bash
ssh root@155.212.221.189
cd /opt/tunnel-server
docker build -t tunnel-server .
docker run -d --name tunnel-server \
  --network eldoleado \
  -p 8765:8765 \
  -e TUNNEL_SECRET=Mi31415926pSss! \
  -e API_KEY=BattCRM_Tunnel_Secret_2024 \
  tunnel-server
```

### 2. Настроить Android приложение

В настройках указать:
- Tunnel URL: `ws://155.212.221.189:8765/ws`
- Tunnel Secret: `Mi31415926pSss!`

### 3. Включить TunnelService

Запустить сервис туннеля в приложении.

### 4. Проверить подключение

```bash
curl http://155.212.221.189:8765/devices
```

## Мониторинг

```bash
# Статус сервера
curl http://tunnel-server:8765/health

# Подключенные устройства
curl http://tunnel-server:8765/devices

# Детальный статус (с API ключом)
curl -H "X-API-Key: BattCRM_Tunnel_Secret_2024" \
  http://tunnel-server:8765/status
```
