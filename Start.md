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
**16 December 2025, 18:50 (UTC+4)**

---

## Текущая задача: MVP Messenger + Mobile Tunnel

### Контекст

Омниканальный мессенджер для сервисных центров.
- Android приложение готово (APK собран)
- Серверы мигрированы на NEW
- Tunnel Proxy для мобильного IP создан

### Архитектура

```
┌─────────────────────────────────────────────────────────────┐
│                     SERVER (NEW 155.212.221.189)            │
├─────────────────────────────────────────────────────────────┤
│  MCP Servers (логика)                                       │
│  ├── avito-messenger-api:8765 ─┐                            │
│  ├── vk-community-api:8767 ────┼── HTTP_PROXY=tunnel:8080   │
│  ├── max-bot-api:8768 ─────────┘                            │
│  ├── android-api:8780 (без прокси)                          │
│  └── redis:6379                                             │
│                                                             │
│  tunnel-proxy:8080 ◄─── WebSocket ──────────────────────────┼──┐
└─────────────────────────────────────────────────────────────┘  │
                                                                  │
┌─────────────────────────────────────────────────────────────┐  │
│               MOBILE (телефон/Termux)                        │◄─┘
├─────────────────────────────────────────────────────────────┤
│  tunnel.py:8765 → Mobile IP → Internet                      │
└─────────────────────────────────────────────────────────────┘
```

---

## Серверы (актуальный статус)

| Server | IP | Контейнеры | Статус |
|--------|-----|------------|--------|
| **RU** | 45.144.177.128 | neo4j, redis, marzban | ✅ Готов |
| **NEW** | 155.212.221.189 | tunnel-proxy, android-api, avito, vk, max, redis | ✅ Готов |
| **n8n** | 185.221.214.83 | n8n, postgresql | ⬜ Активировать workflows |
| **FI** | 217.145.79.27 | telegram, whatsapp (unhealthy) | ⚠️ Проверить |

---

## Задачи (TODO)

### ✅ Выполнено
- [x] RU сервер: оставить только neo4j + redis + marzban
- [x] NEW сервер: развернуть android-api, MCP серверы
- [x] Создать tunnel-proxy (server + mobile)
- [x] Настроить MCP серверы с HTTP_PROXY

### ⬜ Осталось
1. **Запустить tunnel на телефоне** — см. инструкцию ниже
2. **Обновить IP телефона** в tunnel-proxy
3. **Активировать n8n workflows** (API_Android_*)
4. **Обновить DNS** — android-api.eldoleado.ru → 155.212.221.189
5. **Проверить FI сервер** — telegram/whatsapp unhealthy

---

## Запуск Tunnel на телефоне

```bash
# Termux
pkg install python
pip install aiohttp

export TUNNEL_SECRET="Mi31415926pSss!"
export WS_PORT=8765

# Скопировать tunnel.py из NEW/MVP/tunnel-proxy/mobile/
python tunnel.py
```

После запуска — обновить на сервере:
```bash
ssh root@155.212.221.189
docker rm -f tunnel-proxy
docker run -d --name tunnel-proxy --network eldoleado -p 8080:8080 \
  -e MOBILE_WS_URL=ws://PHONE_IP:8765/ws \
  -e TUNNEL_SECRET=Mi31415926pSss! \
  --restart unless-stopped \
  tunnel-proxy
```

---

## Ключевые файлы

| Файл | Описание |
|------|----------|
| `NEW/MVP/tunnel-proxy/` | Tunnel Proxy (server + mobile) |
| `NEW/MVP/tunnel-proxy/mobile/tunnel.py` | Скрипт для телефона |
| `NEW/MVP/tunnel-proxy/server/` | Docker для сервера |
| `app/build/outputs/apk/debug/app-debug.apk` | Android APK |
| `MCP/mcp-ssh/servers.json` | SSH конфиг серверов |

---

## Параметры

См. `NEW/MVP/tunnel-proxy/params.md` для всех параметров и секретов.

---

## QUICK START

```bash
# 1. Запустить tunnel на телефоне
# 2. Обновить PHONE_IP в tunnel-proxy на сервере
# 3. Активировать n8n workflows через UI
# 4. Тестировать Android приложение
```

---

**Before ending session:** update Start.md, git push
