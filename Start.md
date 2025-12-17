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
**17 December 2025, 22:50 (MSK, UTC+3)**

---

## Проект: Android Messager — Омниканальный мессенджер

### Что это
Мобильное приложение для операторов сервисных центров. Общение с клиентами через разные мессенджеры (Telegram, WhatsApp, Avito, MAX) из одного интерфейса.

### Текущий статус
- ✅ **Login + Roles** — выбор режима (client/server/both) на экране входа
- ✅ **Database** — создана таблица `elo_t_operator_devices` с app_mode
- ✅ **Auth Workflow** — `API_Android_Auth_ELO.json` для elo_ таблиц
- ✅ **Android UI** — Login с RadioGroup для выбора режима
- ✅ **tunnel-server** — работает на 155.212.221.189:8800
- ⬜ **Dialogs API** — mock data, нужен реальный endpoint
- 🔄 **Channel Setup** — UI готов, backend частично

---

## Что сделано в текущей сессии (17.12.2025)

### 1. Login + Roles System ✅
- Три режима: `client` (оператор), `server` (только сервер), `both` (оба)
- `LoginActivity.kt` — RadioGroup для выбора режима
- `activity_login.xml` — UI с описанием каждого режима
- `LoginRequest.app_mode` — передаётся на сервер
- `SessionManager` — сохраняет режим в SharedPreferences

### 2. Database Schema ✅
- Создана таблица `elo_t_operator_devices`:
  - `app_mode` (client/server/both)
  - `tunnel_url`, `tunnel_secret`
  - `session_token`, `fcm_token`
  - Связь с `elo_t_operators` и `elo_t_tenants`

### 3. Auth Workflow ✅
- `API_Android_Auth_ELO.json` — использует elo_ таблицы
- Возвращает: `app_mode`, `tunnel_url`, `tunnel_secret`
- Автогенерация `tunnel_secret` для server/both режимов
- **Требуется:** импорт в n8n

### 4. Documentation ✅
- Обновлён `ROADMAP.md` с полным описанием:
  - Архитектура системы
  - API endpoints
  - Файловая структура
  - Проблемы и решения
  - Next steps

---

## Архитектура (актуальная)

```
┌─────────────────────────────────────────────────────────────────┐
│                    n8n SERVER (185.221.214.83)                   │
│  Webhooks: android/auth/login → ELO_API_Android_Auth            │
│  Database: elo_t_operators, elo_t_operator_devices              │
└─────────────────────────────────────────────────────────────────┘
                              │ HTTPS
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    ANDROID APP (Eldoleado)                       │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  Mode: client         Mode: both         Mode: server    │   │
│  │  ┌─────────────┐     ┌─────────────┐    ┌─────────────┐ │   │
│  │  │ Messenger   │     │ Messenger   │    │ TunnelSvc   │ │   │
│  │  │ UI only     │     │ + Tunnel    │    │ only        │ │   │
│  │  └─────────────┘     └─────────────┘    └─────────────┘ │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
                              │ WebSocket (server/both)
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│              tunnel-server (155.212.221.189:8800)                │
│  - Приём сообщений из каналов (Telegram, Avito, MAX)            │
│  - Proxy через мобильный IP                                     │
│  - Forwarding в n8n                                             │
└─────────────────────────────────────────────────────────────────┘
```

---

## NEXT STEPS

### Priority 1: Test Auth Flow
1. [ ] Импортировать `API_Android_Auth_ELO.json` в n8n
2. [ ] Создать тестового оператора в `elo_t_operators`
3. [ ] Протестировать логин с curl
4. [ ] Протестировать логин из Android app

### Priority 2: Dialogs API
1. [ ] Создать workflow `ELO_API_Android_Dialogs`
2. [ ] Endpoint: `GET /android/dialogs?operator_id={uuid}`
3. [ ] Подключить в MainActivity вместо mock data

### Priority 3: Channel Setup Backend
1. [ ] Telegram Bot verification API
2. [ ] Avito sessid validation
3. [ ] WhatsApp (решить: Baileys/Wappi/WebView)

---

## Серверы

| Server | IP | Что там | Статус |
|--------|-----|---------|--------|
| **n8n** | 185.221.214.83 | n8n, postgresql | ✅ Ready |
| **Tunnel** | 155.212.221.189 | tunnel-server:8800 | ✅ Running |
| **Finnish** | 217.145.79.27 | mcp-telegram, mcp-whatsapp | ✅ Ready |
| **RU** | 45.144.177.128 | mcp-avito, mcp-max, neo4j | ✅ Ready |

---

## Quick Commands

```bash
# Build Android app
export JAVA_HOME="/c/Program Files/Android/Android Studio/jbr"
cd /c/Users/User/Eldoleado && ./gradlew.bat assembleDebug

# Check tunnel-server
curl http://155.212.221.189:8800/api/health

# Test login (after workflow import)
curl -X POST https://n8n.n8nsrv.ru/webhook/android/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"test@test.com","password":"test","app_mode":"client"}'

# Database access
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT * FROM elo_t_operators;'"
```

---

## Ключевые файлы

| Файл | Описание |
|------|----------|
| `NEW/MVP/Android Messager/ROADMAP.md` | **Полная документация (обновлено!)** |
| `NEW/workflows/API/API_Android_Auth_ELO.json` | Auth workflow для импорта |
| `app/src/main/java/.../LoginActivity.kt` | Логин с выбором режима |
| `app/src/main/java/.../SessionManager.kt` | Хранение app_mode |
| `app/src/main/res/layout/activity_login.xml` | UI логина |

---

**Before ending session:** update Start.md, Stop.md, git push
