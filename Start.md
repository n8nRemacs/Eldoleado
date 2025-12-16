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
**16 December 2025, 17:45 (UTC+4)**

---

## Текущая задача: Миграция серверов + MVP Messenger

### Контекст

Омниканальный мессенджер для сервисных центров.
- Android приложение готово (APK собран)
- Нужно активировать API и перенести серверы

### Серверы

| Server | IP | Что там | Что делать |
|--------|-----|---------|------------|
| **RU** | 45.144.177.128 | neo4j, redis, MCP | Оставить только neo4j + redis |
| **NEW** | 155.212.221.189 | пусто | Развернуть MCP + android-api |
| **n8n** | 185.221.214.83 | n8n | Активировать API воркфлоу |
| **FI** | 217.145.79.27 | telegram, whatsapp | Оставить как есть |

### MCP SSH Server

**ВАЖНО:** При старте Claude Code автоматически запускается MCP SSH сервер.

Доступные команды через MCP:
- `ssh_exec(server, command)` — выполнить команду
- `ssh_list_servers()` — список серверов
- `ssh_add_server(alias, host, ...)` — добавить сервер

Алиасы серверов:
- `ru` — 45.144.177.128
- `fi` — 217.145.79.27
- `n8n` — 185.221.214.83
- `new` — 155.212.221.189

---

## Задачи (TODO)

### 1. Миграция серверов

```bash
# На 45.144.177.128 остановить всё кроме neo4j и redis:
docker stop avito-messenger-api vk-community-api max-bot-api android-api whatsapp-api-wappi instagram-graph-api form-submission-api bull-board

# На 155.212.221.189 установить Docker и развернуть:
# - android-api
# - MCP серверы (avito, vk, max, etc.)
```

### 2. Активировать n8n воркфлоу

В n8n.n8nsrv.ru включить:
- API_Android_Auth
- API_Android_Appeals_List
- API_Android_Appeal_Detail
- И остальные API_Android_*

### 3. Обновить DNS

`android-api.eldoleado.ru` → 155.212.221.189

---

## Ключевые файлы

| Файл | Описание |
|------|----------|
| `app/build/outputs/apk/debug/app-debug.apk` | Android APK |
| `MCP/mcp-ssh/server.py` | MCP SSH сервер |
| `MCP/mcp-ssh/servers.json` | Конфиг серверов |
| `NEW/MVP/n8n_api/` | n8n воркфлоу для импорта |
| `~/.claude/claude_desktop_config.json` | MCP конфиг |

---

## Android App

**Готово:**
- ✅ Главный экран — список диалогов
- ✅ Жирный шрифт для непрочитанных
- ✅ Бейджи каналов (TG, WA, AV, VK, MX)
- ✅ AI режим отключен (серый)
- ✅ TunnelService для server mode

**APK:** `app/build/outputs/apk/debug/app-debug.apk`

**Сборка:**
```bash
cd C:/Users/User/Documents/Eldoleado
export JAVA_HOME="/c/Program Files/Android/Android Studio/jbr"
./gradlew.bat :app:assembleDebug
```

---

## QUICK START

```bash
# 1. Проверить MCP SSH работает
# (после перезапуска Claude Code)

# 2. Миграция серверов через MCP SSH:
# ssh_exec("ru", "docker stop ...")
# ssh_exec("new", "docker run ...")

# 3. Активировать n8n воркфлоу через UI
```

---

**Before ending session:** update Start.md, Stop.md, git push
