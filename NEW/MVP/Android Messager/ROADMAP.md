# Android Messenger — Roadmap & Technical Documentation

**Last Updated:** 2025-12-17 22:45 (MSK, UTC+3)

---

## Current Status Overview

| Component | Status | Notes |
|-----------|--------|-------|
| **Login + Roles** | ✅ Ready | client/server/both modes |
| **Database (elo_)** | ✅ Created | elo_t_operator_devices |
| **Auth Workflow** | ✅ Ready | API_Android_Auth_ELO.json |
| **Android UI** | ✅ Built | Login с выбором режима |
| **tunnel-server** | ✅ Running | 155.212.221.189:8800 |
| **Dialogs API** | ⬜ Not started | Mock data in app |
| **Channel Setup** | 🔄 Partial | UI есть, backend нет |

---

## Part 1: Authentication & Roles System

### 1.1 Three Operation Modes

| Mode | Код | UI | Tunnel | Описание |
|------|-----|-----|--------|----------|
| **Оператор** | `client` | ✅ | ❌ | Только мессенджер, без сервера |
| **Оператор + Сервер** | `both` | ✅ | ✅ | Мессенджер + приём из каналов |
| **Сервер** | `server` | ❌ | ✅ | Только приём, без интерфейса |

### 1.2 Database Schema

**Table:** `elo_t_operator_devices` (создана 2025-12-17)

```sql
CREATE TABLE elo_t_operator_devices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    operator_id UUID NOT NULL REFERENCES elo_t_operators(id) ON DELETE CASCADE,
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,

    -- Device identification
    device_id VARCHAR(255),           -- Android device ID
    device_type VARCHAR(20) NOT NULL DEFAULT 'mobile',
    device_name VARCHAR(255),
    device_info JSONB DEFAULT '{}',

    -- Session
    session_token VARCHAR(255) UNIQUE,
    fcm_token TEXT,

    -- App mode
    app_mode VARCHAR(20) NOT NULL DEFAULT 'client',  -- client | server | both

    -- Tunnel settings (for server/both modes)
    tunnel_url TEXT,
    tunnel_secret VARCHAR(255),

    -- Status
    is_active BOOLEAN DEFAULT true,
    last_active_at TIMESTAMPTZ DEFAULT now(),
    created_at TIMESTAMPTZ DEFAULT now(),
    updated_at TIMESTAMPTZ DEFAULT now(),

    UNIQUE(operator_id, device_type, tenant_id)
);
```

### 1.3 Login Flow

```
┌──────────────────────────────────────────────────────────────────┐
│                         LOGIN SCREEN                              │
├──────────────────────────────────────────────────────────────────┤
│  Email/Phone: [_________________________]                         │
│  Password:    [_________________________]                         │
│                                                                   │
│  Режим работы:                                                    │
│  ○ Оператор           - Только мессенджер                        │
│  ○ Оператор + Сервер  - Мессенджер + каналы                      │
│  ○ Сервер             - Только приём сообщений                   │
│                                                                   │
│  [            ВОЙТИ            ]                                  │
└──────────────────────────────────────────────────────────────────┘
                              │
                              ▼
              POST /webhook/android/auth/login
              {
                "login": "email_or_phone",
                "password": "***",
                "app_mode": "client|server|both",
                "device_info": {...}
              }
                              │
                              ▼
              Response:
              {
                "success": true,
                "operator_id": "uuid",
                "tenant_id": "uuid",
                "session_token": "uuid",
                "app_mode": "both",
                "tunnel_url": "https://tunnel.eldoleado.ru/{session}",
                "tunnel_secret": "abc123..."
              }
```

### 1.4 Files Modified/Created

**Android App:**
- [LoginActivity.kt](../../app/src/main/java/com/eldoleado/app/LoginActivity.kt) — добавлен RadioGroup для выбора режима
- [activity_login.xml](../../app/src/main/res/layout/activity_login.xml) — UI с тремя radio buttons
- [ApiService.kt](../../app/src/main/java/com/eldoleado/app/api/ApiService.kt) — `LoginRequest.app_mode`
- [SessionManager.kt](../../app/src/main/java/com/eldoleado/app/SessionManager.kt) — константы MODE_*

**Workflows:**
- [API_Android_Auth_ELO.json](../workflows/API/API_Android_Auth_ELO.json) — новый workflow для elo_ таблиц

### 1.5 API Endpoints (Auth)

| Endpoint | Method | Body | Response |
|----------|--------|------|----------|
| `android/auth/login` | POST | `{login, password, app_mode, device_info}` | `{success, operator_id, tenant_id, session_token, app_mode, tunnel_url, tunnel_secret}` |
| `android/logout` | POST | `{session_token}` | `{success}` |

---

## Part 2: Main Screen (Dialogs List)

### 2.1 Current Implementation

**Файлы:**
- [MainActivity.kt](../../app/src/main/java/com/eldoleado/app/MainActivity.kt)
- [activity_main.xml](../../app/src/main/res/layout/activity_main.xml)
- [DialogsAdapter.kt](../../app/src/main/java/com/eldoleado/app/adapters/DialogsAdapter.kt)
- [DialogEntity.kt](../../app/src/main/java/com/eldoleado/app/data/database/entities/DialogEntity.kt)

**Сортировка диалогов:**
```kotlin
// Sort: unread first (oldest unread on top), then read (newest on top)
val sortedDialogs = newDialogs.sortedWith(
    compareBy<DialogEntity> { it.unreadCount == 0 }  // unread first
        .thenBy { if (it.unreadCount > 0) it.lastMessageTime else Long.MAX_VALUE - it.lastMessageTime }
)
```

### 2.2 Problem: No Real API

Сейчас `loadDialogs()` в MainActivity использует **mock данные**:
```kotlin
private fun loadDialogs() {
    // TODO: Load from API
    val mockDialogs = listOf(
        DialogEntity(id = "1", clientName = "Тест", channel = "telegram", ...)
    )
    dialogsAdapter.updateDialogs(mockDialogs)
}
```

### 2.3 Required: Dialogs API

**Endpoint нужен:** `GET /android/dialogs`

**Response:**
```json
{
  "success": true,
  "dialogs": [
    {
      "id": "uuid",
      "client_name": "Иван Петров",
      "client_phone": "+79001234567",
      "channel": "telegram",
      "chat_id": "123456789",
      "last_message_text": "Здравствуйте...",
      "last_message_time": 1702800000000,
      "last_message_is_voice": false,
      "unread_count": 3
    }
  ]
}
```

---

## Part 3: Settings Screen

### 3.1 Sections by Mode

| Section | client | both | server |
|---------|--------|------|--------|
| Каналы | ❌ | ✅ | ✅ |
| Уведомления | ❌ | ✅ | ✅ |
| Запись звонков | ✅ | ✅ | ❌ |
| Выход | ✅ | ✅ | ✅ |

### 3.2 Channels Section (для server/both)

**Files:**
- [section_channels.xml](../../app/src/main/res/layout/section_channels.xml)
- [ChannelCredentialsManager.kt](../../app/src/main/java/com/eldoleado/app/channels/ChannelCredentialsManager.kt)

**Каналы:**
| Канал | Способ настройки | Status |
|-------|------------------|--------|
| Telegram | Bot Token или User API | ✅ UI ready |
| WhatsApp | QR-код | ✅ UI ready |
| Avito | WebView login | ✅ UI ready |
| MAX | QR-код | 🔄 Partial |

**Статусы каналов:**
- `NOT_CONFIGURED` — серый кружок
- `CHECKING` — жёлтый кружок
- `CONNECTED` — зелёный кружок
- `ERROR` — красный кружок

### 3.3 Notifications Section

**Files:**
- [section_notifications.xml](../../app/src/main/res/layout/section_notifications.xml)
- [ChannelMonitorService.kt](../../app/src/main/java/com/eldoleado/app/channels/ChannelMonitorService.kt)
- [AlertSender.kt](../../app/src/main/java/com/eldoleado/app/channels/AlertSender.kt)

**Настройки:**
- Bot Token для алертов
- Chat ID администратора
- Уведомлять о: батарее, сети, каналах

---

## Part 4: Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              n8n SERVER (185.221.214.83)                     │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │  Webhooks:                                                           │    │
│  │  - android/auth/login     → ELO_API_Android_Auth                    │    │
│  │  - android/dialogs        → ELO_API_Android_Dialogs (TODO)          │    │
│  │  - android/messages       → ELO_API_Android_Messages (TODO)         │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
│                                      │                                       │
│                                      ▼                                       │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │  PostgreSQL: elo_t_operators, elo_t_operator_devices, elo_t_dialogs │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      │ HTTPS
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                              ANDROID APP                                     │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │  Mode: client/both/server                                            │    │
│  │  - LoginActivity → выбор режима                                     │    │
│  │  - MainActivity → список диалогов (client/both)                     │    │
│  │  - TunnelService → WebSocket к tunnel-server (server/both)          │    │
│  │  - ChannelMonitorService → мониторинг и алерты (server/both)        │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────────────────┘
                                      │
                                      │ WebSocket (server/both modes)
                                      ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         tunnel-server (155.212.221.189:8800)                 │
│  - Приём сообщений из каналов                                               │
│  - Proxy через мобильный IP                                                 │
│  - Forwarding в n8n                                                         │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Part 5: Problems & Solutions

### 5.1 Current Problems

| Problem | Impact | Solution |
|---------|--------|----------|
| **Нет API для диалогов** | Список пустой (mock data) | Создать workflow ELO_API_Android_Dialogs |
| **Workflow использует старые таблицы** | Login не работает | Импортировать API_Android_Auth_ELO.json |
| **Нет тестового оператора в elo_t_operators** | Нельзя залогиниться | Создать оператора в БД |
| **WhatsApp/MAX требуют node.js** | Сложность интеграции | Рассмотреть альтернативы |

### 5.2 Workflow Migration

**Старые таблицы (НЕ использовать):**
- `operators` → `elo_t_operators`
- `operator_devices` → `elo_t_operator_devices`
- `tenants` → `elo_t_tenants`

**Новые workflows нужно создать:**
| Workflow | Путь | Статус |
|----------|------|--------|
| ELO_API_Android_Auth | NEW/workflows/API/API_Android_Auth_ELO.json | ✅ Создан |
| ELO_API_Android_Dialogs | - | ⬜ TODO |
| ELO_API_Android_Messages | - | ⬜ TODO |
| ELO_API_Android_Send | - | ⬜ TODO |

---

## Part 6: Next Steps (Priority Order)

### Step 1: Setup Test Environment
```bash
# 1. Создать тестовый tenant в elo_t_tenants
INSERT INTO elo_t_tenants (id, name) VALUES (gen_random_uuid(), 'Test Tenant');

# 2. Создать тестового оператора в elo_t_operators
INSERT INTO elo_t_operators (tenant_id, email, password_hash, name)
VALUES ('tenant_uuid', 'test@test.com', crypt('password', gen_salt('bf')), 'Test Operator');
```

### Step 2: Import Auth Workflow
1. Открыть n8n: https://n8n.n8nsrv.ru
2. Import → Upload from file: `API_Android_Auth_ELO.json`
3. Активировать workflow
4. Деактивировать старый `API_Android_Auth`

### Step 3: Test Login
```bash
curl -X POST https://n8n.n8nsrv.ru/webhook/android/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "login": "test@test.com",
    "password": "password",
    "app_mode": "both"
  }'
```

### Step 4: Create Dialogs API
Создать workflow для получения списка диалогов:
- Endpoint: `GET /android/dialogs?operator_id={uuid}`
- Query: `SELECT * FROM elo_t_dialogs WHERE assigned_operator_id = ?`
- Response: JSON с массивом диалогов

### Step 5: Test Full Flow
1. Запустить приложение на Android
2. Залогиниться с режимом "Оператор + Сервер"
3. Проверить список диалогов
4. Проверить настройки каналов

---

## Part 7: Tunnel Server (Reference)

### 7.1 Deployment Status

**Server:** 155.212.221.189:8800 ✅ Running

**Health check:**
```bash
curl http://155.212.221.189:8800/api/health
# {"status":"ok","tunnels_connected":0,"version":"1.0.0"}
```

### 7.2 WebSocket Protocol

| Action | Direction | Description |
|--------|-----------|-------------|
| `hello` | Client→Server | Registration with tenant_id, app_mode |
| `proxy_status` | Client→Server | WiFi/battery status |
| `http_request` | Server→Client | Request to local service |
| `proxy_fetch` | Server→Client | Fetch URL via mobile IP |
| `push_message` | Server→Client | New message notification |

### 7.3 Android TunnelService

**Files:**
- [TunnelService.kt](../../app/src/main/java/com/eldoleado/app/tunnel/TunnelService.kt)

**Features:**
- ✅ WebSocket connection with auto-reconnect
- ✅ Foreground service
- ✅ `hello` message with device info
- ✅ `proxy_status` updates
- ✅ `proxy_fetch` handler

---

## Part 8: Channel Setup Wizards

### 8.1 Telegram Setup

**File:** [TelegramSetupActivity.kt](../../app/src/main/java/com/eldoleado/app/channels/setup/TelegramSetupActivity.kt)

**Options:**
1. **Bot Token** — получить от @BotFather
2. **User API** — API_ID + API_HASH от my.telegram.org

**Flow (Bot):**
```
1. Ввести Bot Token
2. Проверка: GET https://api.telegram.org/bot{token}/getMe
3. Если OK → сохранить в ChannelCredentialsManager
```

### 8.2 WhatsApp Setup

**File:** [WhatsAppSetupActivity.kt](../../app/src/main/java/com/eldoleado/app/channels/setup/WhatsAppSetupActivity.kt)

**Problem:** Требует Baileys (Node.js) на телефоне

**Workaround options:**
1. Termux + Node.js + Baileys
2. WhatsApp Business API (платный)
3. Wappi.pro (внешний сервис)

### 8.3 Avito Setup

**File:** [AvitoSetupActivity.kt](../../app/src/main/java/com/eldoleado/app/channels/setup/AvitoSetupActivity.kt)

**Flow:**
```
1. Открыть WebView с m.avito.ru
2. Пользователь логинится
3. Перехватить cookies → извлечь sessid
4. Проверка: POST /messenger/getChannels
```

### 8.4 MAX Setup

**File:** [MaxSetupActivity.kt](../../app/src/main/java/com/eldoleado/app/channels/setup/MaxSetupActivity.kt)

**Status:** Partial (нужна интеграция с vkmax)

---

## Appendix A: File Structure

```
app/src/main/java/com/eldoleado/app/
├── LoginActivity.kt              # Логин с выбором режима
├── MainActivity.kt               # Главный экран (диалоги + настройки)
├── SessionManager.kt             # Хранение сессии, app_mode
├── api/
│   ├── ApiService.kt             # Retrofit endpoints
│   └── RetrofitClient.kt         # Base URL: n8n.n8nsrv.ru/webhook
├── adapters/
│   └── DialogsAdapter.kt         # RecyclerView для диалогов
├── channels/
│   ├── ChannelCredentialsManager.kt  # Хранение credentials
│   ├── ChannelMonitorService.kt      # Мониторинг каналов
│   ├── AlertSender.kt                # Отправка алертов
│   └── setup/
│       ├── TelegramSetupActivity.kt
│       ├── WhatsAppSetupActivity.kt
│       ├── AvitoSetupActivity.kt
│       └── MaxSetupActivity.kt
├── data/database/entities/
│   └── DialogEntity.kt           # Room entity для диалогов
└── tunnel/
    └── TunnelService.kt          # WebSocket к tunnel-server

NEW/workflows/API/
├── API_Android_Auth.json         # Старый (operators)
├── API_Android_Auth_ELO.json     # Новый (elo_t_operators) ✅
├── API_Android_Logout.json
└── API_Android_Register_FCM.json
```

---

## Appendix B: Quick Commands

```bash
# Build Android app
export JAVA_HOME="/c/Program Files/Android/Android Studio/jbr"
cd /c/Users/User/Eldoleado
./gradlew.bat assembleDebug

# Check tunnel-server
curl http://155.212.221.189:8800/api/health

# Test login API (after workflow import)
curl -X POST https://n8n.n8nsrv.ru/webhook/android/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"test@test.com","password":"test","app_mode":"client"}'

# SSH to database server
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT * FROM elo_t_operators;'"
```

---

## Appendix C: Environment Variables

**Android App (BuildConfig):**
```
BASE_URL=https://n8n.n8nsrv.ru/webhook/
TUNNEL_URL=wss://tunnel.eldoleado.ru/ws
```

**tunnel-server (.env):**
```
HOST=0.0.0.0
PORT=8800
POSTGRES_HOST=185.221.214.83
POSTGRES_PORT=6544
POSTGRES_DB=postgres
POSTGRES_USER=supabase_admin
```

---

*Document version: 2.0 — 2025-12-17 22:45 MSK*
