# STOP - Session Completion Checklist

> **IMPORTANT:** When updating this file ALWAYS specify date AND time in format: `DD Month YYYY, HH:MM (UTC+4)`

---

## MANDATORY before closing session:

### 1. Update Start.md

**IMPORTANT:** ALWAYS add sync block at the beginning of Start.md:

```markdown
## FIRST — Sync

**If reading this file SECOND time after git pull — SKIP this block and go to next section!**

\`\`\`bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
\`\`\`

After git pull — REREAD this file from the beginning (Start.md), starting from the next section (skipping this sync block to avoid loops).

---
```

Then update "What's done" section — add everything done in this session.

### 2. Clean project
Delete temporary files from project root.

### 3. Update CORE_NEW context
```bash
python scripts/update_core_context.py
```

### 4. Git sync
```bash
git add -A && git commit -m "Session update: brief description" && git push
```

---

## Last session: 16 December 2025, 17:45 (UTC+4)

---

## What's done in this session

### 1. Android App — Dialogs UI
- Создан `DialogEntity.kt` — сущность диалога
- Создан `DialogsAdapter.kt` — адаптер с жирным шрифтом для непрочитанных
- Создан `item_dialog.xml` — layout элемента списка
- Обновлён `MainActivity.kt` — переключено на диалоги
- Обновлены ресурсы: colors.xml, bottom_navigation_menu.xml, activity_main.xml
- Кнопки AI режима отключены (серые)
- **APK собран успешно:** `app/build/outputs/apk/debug/app-debug.apk`

### 2. TunnelService
- Скопирован `TunnelService.kt` в root app
- Обновлён `SessionManager.kt` с поддержкой app modes (client/server/both)

### 3. MCP SSH Server
- Создан `MCP/mcp-ssh/server.py` — MCP сервер для SSH
- Настроены серверы: ru, fi, n8n, new (155.212.221.189)
- Добавлен в `~/.claude/claude_desktop_config.json`

### 4. Инфраструктура
- Проверен n8n (185.221.214.83) — воркфлоу импортированы
- Проверен android-api на 45.144.177.128

---

## Current system state

**Android App:**
- ✅ Диалоги UI готов
- ✅ APK собран
- ⏳ API эндпойнты в n8n нужно активировать

**Servers:**
- 45.144.177.128 — neo4j, redis, MCP серверы (будут перенесены)
- 155.212.221.189 — новый сервер для мессенджера (пустой)
- 185.221.214.83 — n8n (воркфлоу есть, нужно активировать)

**MCP SSH:**
- ✅ Создан
- ⏳ Нужен перезапуск Claude Code

---

## NEXT STEPS

1. **Перезапустить Claude Code** — активировать MCP SSH
2. **Миграция серверов:**
   - Оставить на 45.144.177.128: neo4j, redis
   - Перенести на 155.212.221.189: все MCP серверы, android-api
3. **Активировать n8n воркфлоу** — API_Android_Auth и другие
4. **Тест Android приложения**

---

## To continue

1. `git pull`
2. Read `Start.md`
3. Перезапустить Claude Code для MCP SSH
4. Продолжить миграцию серверов
