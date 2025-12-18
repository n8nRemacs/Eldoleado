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
**18 December 2025, 22:30 (MSK, UTC+3)**

---

## Проект: Android Messager — Омниканальный мессенджер

### Что это
Мобильное приложение для операторов сервисных центров. Общение с клиентами через разные мессенджеры (Telegram, WhatsApp, Avito, MAX) из одного интерфейса.

### Текущий статус
- ✅ **Login + Roles** — работает (client/server/both)
- ✅ **Auth API** — `ELO_API_Android_Auth` в n8n
- ✅ **Dialogs API** — `ELO_API_Android_Dialogs` в n8n
- ✅ **Messages API** — `ELO_API_Android_Messages` в n8n
- ✅ **Send Message API** — `ELO_API_Android_SendMessage` в n8n
- ✅ **ChatActivity** — полноценный экран чата с кнопками
- ✅ **Тестовые сообщения** — добавлены в elo_t_messages (14 шт)
- ✅ **tunnel-server** — работает на 155.212.221.189:8800
- 🔄 **Channel Setup** — UI готов, backend частично

---

## Что сделано в сессии (18.12.2025, вечер)

### 1. Test Messages ✅
- Добавлены тестовые сообщения в БД (14 шт для 3 диалогов)
- Иван Петров (Telegram) — 5 сообщений
- Мария Сидорова (WhatsApp) — 4 сообщения
- Алексей Козлов (Avito) — 5 сообщений

### 2. Send Message API ✅
- Создан workflow `ELO_API_Android_SendMessage`
- Endpoint: `POST /android-send/android/dialogs/:dialog_id/messages`
- Сохраняет сообщение в БД, обновляет диалог

### 3. Chat UI обновлён ✅
- Панель кнопок: Normalize (зелёная), Voice (оранжевая), Send (синяя)
- Поле ввода 80dp с кнопками Clear и Reject
- Нижняя навигация: Диалоги / Настройки
- Дизайн сообщений: CardView, клиент слева (голубой), оператор справа (оранжевый)

### 4. Файлы скопированы из app_old
- Drawables: bg_button_normalize, bg_button_voice, bg_button_send_wide, bg_button_clear, bg_button_reject, bg_input_field
- Icons: ic_edit, ic_mic, ic_clear, ic_close, ic_appeals, ic_settings
- Menu: bottom_navigation_menu.xml

---

## Архитектура (актуальная)

```
┌─────────────────────────────────────────────────────────────────┐
│                    n8n SERVER (185.221.214.83)                   │
│  Webhooks:                                                       │
│  - android/auth/login     → ELO_API_Android_Auth                │
│  - android/dialogs        → ELO_API_Android_Dialogs             │
│  - android-messages/...   → ELO_API_Android_Messages            │
└─────────────────────────────────────────────────────────────────┘
                              │ HTTPS
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    ANDROID APP (Eldoleado)                       │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  LoginActivity → MainActivity → ChatActivity              │   │
│  │  - Dialogs list from API                                  │   │
│  │  - Messages from API                                      │   │
│  │  - TunnelService for server/both modes                    │   │
│  └──────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

---

## NEXT STEPS (19.12.2025)

### Priority 1: API кнопок чата
- **Normalize API** — нормализация текста через AI
- **Voice API** — отправка голосовых сообщений
- **Reject API** — отклонение AI-ответа

### Priority 2: UI окна сообщений
- Доработать дизайн сообщений
- Добавить медиа-вложения (фото, документы)
- Индикатор "печатает..."

### Priority 3: Режим через права (НЕ выбор)
- Убрать выбор режима при логине (client/server/both)
- Режим определяется правами оператора в БД
- Поле `app_mode` в `elo_t_operators`
- Auth API возвращает режим на основе прав

### Priority 4: Channel Setup Backend
- Telegram Bot verification
- Avito sessid validation
- WhatsApp integration

---

## Тестовые данные

### Оператор
- Email: `admin@test.local`
- Password: `test123`
- Tenant: `Test Repair Shop`

### Диалоги (в БД)
| Клиент | Канал | Chat ID |
|--------|-------|---------|
| Иван Петров | Telegram | 123456789 |
| Алексей Козлов | Avito | avito_chat_555 |
| Мария Сидорова | WhatsApp | 79007654321 |

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
cd /c/Users/User/Documents/Eldoleado && ./gradlew.bat assembleDebug

# Install on emulator
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Test login
curl -X POST https://n8n.n8nsrv.ru/webhook/android/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"admin@test.local","password":"test123","app_mode":"both"}'

# Test dialogs
curl "https://n8n.n8nsrv.ru/webhook/android/dialogs?session_token=YOUR_TOKEN"

# Database access
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT * FROM elo_t_dialogs;'"
```

---

## Ключевые файлы

| Файл | Описание |
|------|----------|
| `NEW/MVP/Android Messager/ROADMAP.md` | Полная документация |
| `NEW/workflows/API/API_Android_Auth_ELO.json` | Auth workflow |
| `NEW/workflows/API/API_Android_Dialogs.json` | Dialogs workflow |
| `NEW/workflows/API/API_Android_Messages.json` | Messages workflow |
| `NEW/workflows/API/API_Android_SendMessage.json` | Send Message workflow |
| `app/src/main/java/.../ChatActivity.kt` | Экран чата с кнопками |
| `app/src/main/java/.../ChatMessagesAdapter.kt` | Адаптер сообщений (CardView) |
| `app/src/main/java/.../MainActivity.kt` | Главный экран |
| `app/src/main/java/.../api/ApiService.kt` | API endpoints |

---

**Before ending session:** update Start.md, Stop.md, git push
