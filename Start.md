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
**16 December 2025, 19:50 (UTC+4)**

---

## Текущая задача: MVP Messenger + Mobile Tunnel

### Контекст

Омниканальный мессенджер для сервисных центров.
- Android приложение готово
- Серверы настроены на NEW
- **Tunnel Server готов** — ждёт подключения Android

### Архитектура (защита от банов)

```
Android App (TunnelService)
    │
    │ WebSocket (мобильный IP + Android TLS fingerprint)
    ▼
tunnel-server:8765 ◄──── MCP серверы (POST /proxy)
    │
    ├── avito-messenger-api:8766
    ├── vk-community-api:8767
    └── max-bot-api:8768
```

**Почему это защищает от банов:**
- ✅ Мобильный IP (не серверный)
- ✅ Android TLS fingerprint (OkHttp, не Python)
- ✅ Реальный Device ID

---

## Серверы (актуальный статус)

| Server | IP | Контейнеры | Статус |
|--------|-----|------------|--------|
| **NEW** | 155.212.221.189 | tunnel-server, android-api, avito, vk, max, redis | ✅ Готов |
| **RU** | 45.144.177.128 | neo4j, redis, marzban | ✅ Готов |
| **n8n** | 185.221.214.83 | n8n, postgresql | ⬜ Активировать workflows |
| **FI** | 217.145.79.27 | telegram, whatsapp | ⚠️ Проверить |

### NEW Server — Порты

| Сервис | Порт | Описание |
|--------|------|----------|
| tunnel-server | 8765 | WebSocket + HTTP API |
| avito-messenger-api | 8766 | Avito MCP |
| vk-community-api | 8767 | VK MCP |
| max-bot-api | 8768 | MAX MCP |
| android-api | 8780 | API для приложения |
| redis | 6379 | Redis |

---

## Задачи (TODO)

### ✅ Выполнено
- [x] RU сервер: оставить только neo4j + redis + marzban
- [x] NEW сервер: развернуть android-api, MCP серверы
- [x] Создать tunnel-server (правильная архитектура)
- [x] Обновить TunnelService в Android приложении

### ⬜ Осталось
1. **Настроить Android приложение** — указать Tunnel URL
2. **Запустить TunnelService** — включить в приложении
3. **Активировать n8n workflows** (API_Android_*)
4. **Тестировать** — проверить работу через мобильный IP

---

## Настройка Android приложения

В приложении указать:
- **Tunnel URL:** `ws://155.212.221.189:8765/ws`
- **Tunnel Secret:** `Mi31415926pSss!`

После настройки — включить TunnelService.

Проверить подключение:
```bash
curl http://155.212.221.189:8765/devices
```

---

## Ключевые файлы

| Файл | Описание |
|------|----------|
| `NEW/MVP/tunnel-proxy/` | Tunnel Proxy (server + Android) |
| `NEW/MVP/tunnel-proxy/params.md` | Все параметры и секреты |
| `NEW/MVP/tunnel-proxy/server/tunnel_server.py` | Серверная часть |
| `NEW/MVP/app_original/src/.../tunnel/TunnelService.kt` | Android сервис |
| `app/build/outputs/apk/debug/app-debug.apk` | Android APK |

---

## QUICK START

```bash
# 1. Проверить tunnel-server работает
curl http://155.212.221.189:8765/health

# 2. Настроить приложение и включить TunnelService

# 3. Проверить подключение устройства
curl http://155.212.221.189:8765/devices

# 4. Активировать n8n workflows через UI
```

---

**Before ending session:** update Start.md, git push
