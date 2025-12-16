# Параметры развёртывания

## Архитектура (правильная)

```
Android App (TunnelService)
    │
    │ WebSocket (мобильный IP, Android TLS)
    ▼
tunnel-server:8765 ◄──── MCP серверы (POST /proxy)
    │
    └── avito-messenger-api:8766
    └── vk-community-api:8767
    └── max-bot-api:8768
```

**Ключевой момент:** HTTP запросы выполняются на Android устройстве через OkHttp.
Это даёт:
- Мобильный IP
- Android TLS fingerprint
- Реальный Device ID

---

## NEW Server (155.212.221.189)

### Порты

| Сервис | Порт | Описание |
|--------|------|----------|
| tunnel-server | 8765 | WebSocket для Android + HTTP API для MCP |
| avito-messenger-api | 8766 | Avito MCP |
| vk-community-api | 8767 | VK MCP |
| max-bot-api | 8768 | MAX MCP |
| android-api | 8780 | API для Android приложения |
| redis | 6379 | Redis |

### Docker команды

```bash
# Tunnel Server (WebSocket + HTTP API)
docker run -d --name tunnel-server --network eldoleado -p 8765:8765 \
  -e WS_PORT=8765 \
  -e TUNNEL_SECRET=Mi31415926pSss! \
  -e API_KEY=BattCRM_Tunnel_Secret_2024 \
  --restart unless-stopped \
  tunnel-server

# Android API
docker run -d --name android-api --network eldoleado -p 8780:8780 \
  -e SERVER_HOST=0.0.0.0 \
  -e SERVER_PORT=8780 \
  -e N8N_BASE_URL=https://n8n.n8nsrv.ru/webhook \
  -e JWT_SECRET=Eldoleado-JWT-Secret-2024-Mi31415926 \
  -e JWT_EXPIRE_HOURS=72 \
  --restart unless-stopped \
  android-api-android-api:latest

# Avito MCP
docker run -d --name avito-messenger-api --network eldoleado -p 8766:8766 \
  -e AVITO_CLIENT_ID=trTwLtOgDpAtNnq412ec \
  -e AVITO_CLIENT_SECRET=VgFdlaIhwk5nLwbyf_i5K6kiji3skDtAmqqgF5lH \
  -e AVITO_USER_ID=157920214 \
  -e REDIS_URL=redis://:Mi31415926pSss!@redis:6379 \
  -e N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/avito \
  -e SERVER_HOST=0.0.0.0 -e SERVER_PORT=8766 \
  -e API_KEY=BattCRM_Avito_Secret_2024 \
  -e TUNNEL_PROXY_URL=http://tunnel-server:8765/proxy \
  -e TUNNEL_API_KEY=BattCRM_Tunnel_Secret_2024 \
  --restart unless-stopped \
  avito-messenger-api:v2.0.0

# VK MCP
docker run -d --name vk-community-api --network eldoleado -p 8767:8767 \
  -e REDIS_URL=redis://:Mi31415926pSss!@redis:6379 \
  -e N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/vk-in \
  -e SERVER_HOST=0.0.0.0 -e SERVER_PORT=8767 \
  -e API_KEY=BattCRM_VK_Secret_2024 \
  -e TUNNEL_PROXY_URL=http://tunnel-server:8765/proxy \
  -e TUNNEL_API_KEY=BattCRM_Tunnel_Secret_2024 \
  --restart unless-stopped \
  vk-community-api:v2.0.0

# MAX MCP
docker run -d --name max-bot-api --network eldoleado -p 8768:8768 \
  -e REDIS_URL=redis://:Mi31415926pSss!@redis:6379 \
  -e N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/max-in \
  -e SERVER_HOST=0.0.0.0 -e SERVER_PORT=8768 \
  -e API_KEY=BattCRM_MAX_Secret_2024 \
  -e TUNNEL_PROXY_URL=http://tunnel-server:8765/proxy \
  -e TUNNEL_API_KEY=BattCRM_Tunnel_Secret_2024 \
  --restart unless-stopped \
  max-bot-api:v2.0.0

# Redis
docker run -d --name redis --network eldoleado -p 6379:6379 \
  -v redis_data:/data \
  redis:7-alpine redis-server --appendonly yes --requirepass 'Mi31415926pSss!'
```

---

## Android App (TunnelService)

### Настройки

| Параметр | Значение |
|----------|----------|
| Tunnel URL | `ws://155.212.221.189:8765/ws` |
| Tunnel Secret | `Mi31415926pSss!` |

### SessionManager

В приложении сохранить:
```kotlin
sessionManager.setTunnelUrl("ws://155.212.221.189:8765/ws")
sessionManager.setTunnelSecret("Mi31415926pSss!")
```

---

## Секреты

| Параметр | Значение | Где используется |
|----------|----------|------------------|
| TUNNEL_SECRET | Mi31415926pSss! | tunnel-server, Android app |
| TUNNEL_API_KEY | BattCRM_Tunnel_Secret_2024 | tunnel-server, MCP серверы |
| JWT_SECRET | Eldoleado-JWT-Secret-2024-Mi31415926 | android-api |
| Redis Password | Mi31415926pSss! | redis, MCP серверы |
| API_KEY (Avito) | BattCRM_Avito_Secret_2024 | avito-messenger-api |
| API_KEY (VK) | BattCRM_VK_Secret_2024 | vk-community-api |
| API_KEY (MAX) | BattCRM_MAX_Secret_2024 | max-bot-api |

---

## URLs

| Сервис | URL |
|--------|-----|
| Tunnel WebSocket | ws://155.212.221.189:8765/ws |
| Tunnel HTTP API | http://155.212.221.189:8765/proxy |
| Android API | http://155.212.221.189:8780 |
| n8n | https://n8n.n8nsrv.ru |
| Neo4j (RU) | bolt+ssc://45.144.177.128:7687 |

---

## Использование tunnel в MCP серверах

```python
import aiohttp

TUNNEL_URL = "http://tunnel-server:8765/proxy"
TUNNEL_API_KEY = "BattCRM_Tunnel_Secret_2024"

async def request_via_mobile(url, method="GET", headers=None, body=None):
    """Выполнить HTTP запрос через мобильное устройство."""
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
            data = await resp.json()
            if "error" in data:
                raise Exception(data["error"])
            return data
```
