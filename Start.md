# START - Context for Continuing Work

## FIRST — Sync

**If reading this file SECOND time after git pull — SKIP this block and go to next section!**

```bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
```

After git pull — REREAD this file from the beginning (Start.md), starting from the next section (skipping this sync block to avoid loops).

---

## Last update date and time
**17 December 2025, 01:35 (UTC+4)**

---

## Проект: Android Messager — Омниканальный мессенджер

### Что это
Мобильное приложение для операторов сервисных центров. Общение с клиентами через разные мессенджеры (Avito, VK, MAX, Telegram, WhatsApp) из одного интерфейса + клиентский прокси для парсинга цен.

### Текущий статус
- ✅ Документация и архитектура готовы
- ✅ ROADMAP.md создан с деплоем и API
- ✅ **tunnel-server ЗАДЕПЛОЕН** на 155.212.221.189:8800
- ✅ WebSocket протокол реализован (hello, proxy_fetch, proxy_status)
- ✅ Android TunnelService готов к сборке
- ⏳ **NEXT: Подключить телефон (Termux или APK)**

---

## Архитектура: tunnel-server + mobile-server

### Схема
```
┌─────────────────────────────────────────────────────────────────┐
│                    VPS SERVER (155.212.221.189)                   │
│  ┌─────────────────────────────────────────────────────────────┐│
│  │                    tunnel-server:8800 ✅ RUNNING             ││
│  │  - WebSocket hub for all phones                             ││
│  │  - ProxyManager (load balancing, multi-tenant)              ││
│  │  - API: /api/health, /api/servers, /api/proxy/fetch         ││
│  └─────────────────────────────────────────────────────────────┘│
│                              ▲                                   │
│                              │ WebSocket                         │
└──────────────────────────────┼──────────────────────────────────┘
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
        ▼                      ▼                      ▼
┌───────────────┐    ┌───────────────┐    ┌───────────────┐
│   Phone 1     │    │   Phone 2     │    │   Phone N     │
│ mobile-server │    │ Android App   │    │ mobile-server │
│  - Telegram   │    │ TunnelService │    │  - Proxy only │
│  - Avito      │    │  - Proxy      │    │               │
│  - Proxy      │    │               │    │               │
└───────────────┘    └───────────────┘    └───────────────┘
```

### Три режима работы телефона
1. **Messenger Only** — только каналы мессенджеров
2. **Proxy Only** — только HTTP прокси для парсинга цен
3. **Both** — всё вместе

---

## Ключевые папки и файлы

| Путь | Описание |
|------|----------|
| `NEW/MVP/Android Messager/` | **Основная папка проекта** |
| `NEW/MVP/Android Messager/ROADMAP.md` | **Роадмап, деплой, API, env** |
| `NEW/MVP/Android Messager/tunnel-server/` | Бэкенд на VPS ✅ DEPLOYED |
| `NEW/MVP/Android Messager/mobile-server/` | Клиент для Termux на телефоне |
| `NEW/MVP/Android Messager/app_original/` | Android приложение (Kotlin) |

---

## Серверы

| Server | IP | Что там | Статус |
|--------|-----|---------|--------|
| **RU** | 45.144.177.128 | neo4j, redis, marzban (VPN) | ✅ Ready |
| **n8n** | 185.221.214.83 | n8n, postgresql | ✅ Ready |
| **TUNNEL** | 155.212.221.189 | **tunnel-server:8800** | ✅ **RUNNING** |

### Проверка tunnel-server
```bash
curl http://155.212.221.189:8800/api/health
# {"status":"ok","tunnels_connected":0,"version":"1.0.0"}

ssh root@155.212.221.189 "docker logs tunnel-server --tail 20"
```

---

## Секреты

| Параметр | Значение | Где |
|----------|----------|-----|
| SSH Password | Mi31415926pSss! | Все серверы |
| Neo4j Password | Mi31415926pS | 45.144.177.128 |
| PostgreSQL | Postgres159951 | 185.221.214.83:6544 |
| TUNNEL_SECRET | <generate 32 chars> | tunnel ↔ phone |

---

## TODO: План разработки

### Phase 1: Backend (tunnel-server) ✅ DONE

- ✅ WebSocket hub с ProxyManager
- ✅ Мультитенантность (tenant_id)
- ✅ Docker deployment на 155.212.221.189
- ✅ Health check работает

### Phase 2: Mobile Client ⏳ CURRENT

**Вариант A: Termux (mobile-server)**
```bash
# На телефоне в Termux
pkg install python
cd mobile-server
cp .env.example .env
nano .env  # TUNNEL_URL=ws://155.212.221.189:8800/ws
pip install -r requirements.txt
python -m tunnel_proxy.proxy
```

**Вариант B: Android App**
1. Open `app_original` in Android Studio
2. Configure tunnel URL in SessionManager
3. Build APK
4. Install and test

### Phase 3: End-to-End Testing ⏳

1. Подключить телефон к tunnel-server
2. Проверить proxy_fetch через API
3. Тестирование мессенджеров

---

## Quick Commands

```bash
# Проверить tunnel-server
curl http://155.212.221.189:8800/api/health

# Логи tunnel-server
ssh root@155.212.221.189 "docker logs tunnel-server --tail 50"

# Рестарт tunnel-server
ssh root@155.212.221.189 "cd /opt/eldoleado/tunnel-server && docker-compose restart"

# Re-deploy tunnel-server
cd "/c/Users/User/Eldoleado/NEW/MVP/Android Messager/tunnel-server"
scp -r app main.py requirements.txt Dockerfile docker-compose.yml root@155.212.221.189:/opt/eldoleado/tunnel-server/
ssh root@155.212.221.189 "cd /opt/eldoleado/tunnel-server && docker-compose down && docker-compose build --no-cache && docker-compose up -d"
```

---

## WebSocket Protocol

| Action | Direction | Description |
|--------|-----------|-------------|
| `hello` | Client→Server | Registration with tenant_id, services |
| `proxy_status` | Client→Server | WiFi/battery status updates |
| `http_request` | Server→Client | Proxy to local services |
| `proxy_fetch` | Server→Client | Direct URL fetch via mobile IP |
| `proxy_response` | Client→Server | Response from proxy_fetch |
| `ping`/`pong` | Both | Heartbeat |

---

## Полная документация

Смотри: `NEW/MVP/Android Messager/ROADMAP.md`

---

**Before ending session:** update Start.md, Stop.md, git push
