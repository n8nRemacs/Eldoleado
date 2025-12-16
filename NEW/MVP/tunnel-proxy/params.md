# Параметры развёртывания

## NEW Server (155.212.221.189)

### Docker контейнеры

```bash
# Redis
docker run -d --name redis --network eldoleado -p 6379:6379 \
  -v redis_data:/data \
  redis:7-alpine redis-server --appendonly yes --requirepass 'Mi31415926pSss!'

# Android API
docker run -d --name android-api --network eldoleado -p 8780:8780 \
  -e SERVER_HOST=0.0.0.0 \
  -e SERVER_PORT=8780 \
  -e N8N_BASE_URL=https://n8n.n8nsrv.ru/webhook \
  -e N8N_TIMEOUT=30 \
  -e JWT_SECRET=Eldoleado-JWT-Secret-2024-Mi31415926 \
  -e JWT_EXPIRE_HOURS=72 \
  -e LOG_LEVEL=INFO \
  --restart unless-stopped \
  android-api-android-api:latest

# Tunnel Proxy
docker run -d --name tunnel-proxy --network eldoleado -p 8080:8080 \
  -e MOBILE_WS_URL=ws://PHONE_IP:8765/ws \
  -e TUNNEL_SECRET=Mi31415926pSss! \
  -e RECONNECT_DELAY=10 \
  --restart unless-stopped \
  tunnel-proxy

# Avito MCP
docker run -d --name avito-messenger-api --network eldoleado -p 8765:8765 \
  -e AVITO_CLIENT_ID=trTwLtOgDpAtNnq412ec \
  -e AVITO_CLIENT_SECRET=VgFdlaIhwk5nLwbyf_i5K6kiji3skDtAmqqgF5lH \
  -e AVITO_USER_ID=157920214 \
  -e REDIS_URL=redis://:Mi31415926pSss!@redis:6379 \
  -e N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/avito \
  -e SERVER_HOST=0.0.0.0 \
  -e SERVER_PORT=8765 \
  -e API_KEY=BattCRM_Avito_Secret_2024 \
  -e HTTP_PROXY=http://tunnel-proxy:8080 \
  -e HTTPS_PROXY=http://tunnel-proxy:8080 \
  --restart unless-stopped \
  avito-messenger-api:v2.0.0

# VK MCP
docker run -d --name vk-community-api --network eldoleado -p 8767:8767 \
  -e REDIS_URL=redis://:Mi31415926pSss!@redis:6379 \
  -e N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/vk-in \
  -e SERVER_HOST=0.0.0.0 \
  -e SERVER_PORT=8767 \
  -e API_KEY=BattCRM_VK_Secret_2024 \
  -e HTTP_PROXY=http://tunnel-proxy:8080 \
  -e HTTPS_PROXY=http://tunnel-proxy:8080 \
  --restart unless-stopped \
  vk-community-api:v2.0.0

# MAX MCP
docker run -d --name max-bot-api --network eldoleado -p 8768:8768 \
  -e REDIS_URL=redis://:Mi31415926pSss!@redis:6379 \
  -e N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/max-in \
  -e SERVER_HOST=0.0.0.0 \
  -e SERVER_PORT=8768 \
  -e API_KEY=BattCRM_MAX_Secret_2024 \
  -e HTTP_PROXY=http://tunnel-proxy:8080 \
  -e HTTPS_PROXY=http://tunnel-proxy:8080 \
  --restart unless-stopped \
  max-bot-api:v2.0.0
```

---

## Mobile (Termux)

```bash
export TUNNEL_SECRET="Mi31415926pSss!"
export WS_PORT=8765
export LOG_LEVEL=INFO
```

---

## Секреты

| Параметр | Значение | Где используется |
|----------|----------|------------------|
| TUNNEL_SECRET | Mi31415926pSss! | tunnel-proxy, mobile |
| JWT_SECRET | Eldoleado-JWT-Secret-2024-Mi31415926 | android-api |
| Redis Password | Mi31415926pSss! | redis, MCP серверы |
| API_KEY (Avito) | BattCRM_Avito_Secret_2024 | avito-messenger-api |
| API_KEY (VK) | BattCRM_VK_Secret_2024 | vk-community-api |
| API_KEY (MAX) | BattCRM_MAX_Secret_2024 | max-bot-api |
| AVITO_CLIENT_ID | trTwLtOgDpAtNnq412ec | avito-messenger-api |
| AVITO_CLIENT_SECRET | VgFdlaIhwk5nLwbyf_i5K6kiji3skDtAmqqgF5lH | avito-messenger-api |
| AVITO_USER_ID | 157920214 | avito-messenger-api |

---

## Порты

| Сервис | Порт | Описание |
|--------|------|----------|
| tunnel-proxy | 8080 | HTTP прокси через мобильный туннель |
| android-api | 8780 | API для Android приложения |
| avito-messenger-api | 8765 | Avito MCP |
| vk-community-api | 8767 | VK MCP |
| max-bot-api | 8768 | MAX MCP |
| redis | 6379 | Redis |
| mobile tunnel | 8765 | WebSocket на телефоне |

---

## URLs

| Сервис | URL |
|--------|-----|
| n8n | https://n8n.n8nsrv.ru |
| n8n webhook base | https://n8n.n8nsrv.ru/webhook |
| Neo4j (RU) | bolt+ssc://45.144.177.128:7687 |
| PostgreSQL (n8n) | postgresql://185.221.214.83:6544/postgres |
