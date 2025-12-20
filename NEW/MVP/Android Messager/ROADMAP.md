# Android Messenger — Roadmap & Technical Documentation

**Last Updated:** 2025-12-20 09:00 (MSK, UTC+3)

---

## Current Status Overview

| Component | Status | Notes |
|-----------|--------|-------|
| **Login + Roles** | ✅ Ready | client/server/both modes |
| **Database (elo_)** | ✅ Created | elo_t_operator_devices |
| **Auth Workflow** | ✅ Ready | API_Android_Auth_ELO.json |
| **Android UI** | ✅ Built | Login с выбором режима |
| **tunnel-server** | ✅ Running | 155.212.221.189:8800 |
| **Dialogs API** | ✅ Ready | ELO_API_Android_Dialogs |
| **Messages API** | ✅ Ready | ELO_API_Android_Messages |
| **ChatActivity** | ✅ Built | Полный UI чата |
| **Telegram Setup** | ❌ Problem | Токен слетает при переустановке |
| **WhatsApp Setup** | ✅ SOLVED | Baileys + резидентный proxy (geonix.com) |
| **Avito Setup** | ❌ Problem | Токен не подхватывается, неправильная страница |
| **MAX Setup** | ❌ Problem | Требует QR, но API MAX не поддерживает |

---

## WhatsApp — SOLVED (20.12.2025)

### Solution: Baileys + Residential Proxy

**Status:** ✅ WORKING

**Problem was:**
- nodejs-mobile в APK — WebSocket зависал (datacenter IP блокируются WhatsApp)
- Серверный Baileys без proxy — тоже блокировка (405, 408 ошибки)
- VPN на рабочей станции направляет трафик через datacenter

**Solution:**
- Добавили поддержку SOCKS5 proxy в mcp-whatsapp-baileys
- Используем резидентный proxy от geonix.com
- Успешно подключились и отправили сообщения!

**Proxy details (geonix.com):**
```
Host: res.geonix.com
Port: 10000
Login: 4bac75b003ba6c8f
Password: 1Cl0A5wm
Plan: 1GB until 20.01.2026
URL: socks5://4bac75b003ba6c8f:1Cl0A5wm@res.geonix.com:10000
```

**How to run:**
```bash
cd /c/Users/User/Eldoleado/NEW/MVP/MCP/mcp-whatsapp-baileys
npm install
npm run build
PORT=3003 npm start
```

**API usage:**
```bash
# Create session with proxy
curl -X POST http://localhost:3003/sessions \
  -H "Content-Type: application/json" \
  -d '{"sessionId": "wa-proxy", "proxyUrl": "socks5://4bac75b003ba6c8f:1Cl0A5wm@res.geonix.com:10000"}'

# Get QR code
curl http://localhost:3003/sessions/wa-proxy/qr

# Send message (use node for UTF-8)
node -e "const http=require('http');const data=JSON.stringify({sessionId:'wa-proxy',to:'79991234567',text:'Hello!'});const req=http.request({hostname:'localhost',port:3003,path:'/messages/text',method:'POST',headers:{'Content-Type':'application/json','Content-Length':Buffer.byteLength(data)}},res=>{let d='';res.on('data',c=>d+=c);res.on('end',()=>console.log(d))});req.write(data);req.end()"
```

---

## Other Issues (19.12.2025)

### Previous WhatsApp attempts (archived)

**What we tried:**
1. ❌ **Termux + Node.js** — репозиторий недоступен, dependency hell
2. ❌ **nodejs-mobile embedded in APK** — WebSocket зависает (datacenter IP)
3. ❌ **Server Baileys without proxy** — blocked (405, 408)
4. ✅ **Baileys + residential proxy** — WORKS!

**What was done with nodejs-mobile (archived):**
- ✅ libnode.so v18.20.4 встроен в APK (arm64-v8a, armeabi-v7a, x86_64)
- ✅ JNI bridge (native-lib.cpp) для запуска Node.js
- ✅ CMake конфигурация для нативной сборки
- ✅ Baileys @whiskeysockets/baileys установлен в assets/nodejs/node_modules
- ✅ ESM module fix (dynamic import вместо require)
- ✅ crypto.subtle polyfill для nodejs-mobile
- ✅ pino-compatible logger для Baileys
- ✅ Файловое логирование (node.log) для отладки
- ✅ HTTP API на порту 3000 (status, qr, connect, pair, logout, send)
- ✅ DNS работает (web.whatsapp.com резолвится)
- ✅ HTTPS тест добавлен

**Current issue:**
```
[CONN] connection.update: {"connection":"connecting","receivedPendingNotifications":false}
```
WebSocket соединение с серверами WhatsApp не устанавливается (зависает).

**Possible causes:**
1. WhatsApp блокирует неофициальные клиенты
2. TLS fingerprinting
3. nodejs-mobile WebSocket имеет ограничения

**How to test/debug:**
```bash
# 1. Build and install
export JAVA_HOME="/c/Program Files/Android/Android Studio/jbr"
cd /c/Users/User/Documents/Eldoleado && ./gradlew.bat assembleDebug
adb shell "run-as com.eldoleado.app rm -rf files/nodejs"
adb install -r app/build/outputs/apk/debug/app-debug.apk

# 2. Open WhatsApp Setup in app

# 3. Check logs
adb shell "run-as com.eldoleado.app cat files/nodejs/node.log"

# 4. Check port
adb shell "netstat -tlnp | grep 3000"
```

**Next steps:**
1. Попробовать Pairing Code вместо QR (`/pair` endpoint добавлен)
2. Проверить proxy/VPN
3. Исследовать альтернативные библиотеки (wa-js, etc.)

---

### 2. Telegram — Token Lost on Reinstall

**Status:** ❌ Not fixed

**Problem:** При переустановке приложения токен бота слетает

**Cause:** Токен хранится в SharedPreferences, которые удаляются при переустановке

**Solution needed:**
1. Сохранять токен на сервере (в elo_t_channel_accounts)
2. При логине загружать токены с сервера

---

### 3. Avito — Wrong Page on Auth

**Status:** ❌ Not fixed

**Problem:**
1. Токен не подхватывается после авторизации
2. При переходе на страницу авторизации открывается неправильная страница

**Investigation needed:**
1. Проверить WebView перехват cookies
2. Проверить URL авторизации m.avito.ru
3. Проверить sessid extraction

---

### 4. MAX — No QR Support

**Status:** ❌ Blocked

**Problem:** UI требует QR-код, но API MAX (VK Teams) не поддерживает вход по QR

**Solution needed:**
1. Изменить UI на ввод bot token или OAuth
2. Использовать MAX Bot API вместо User API

---

## Part 1: WhatsApp Integration Details

### 1.1 nodejs-mobile Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                     Android App (Kotlin)                         │
├─────────────────────────────────────────────────────────────────┤
│  WhatsAppSetupActivity                                           │
│    │                                                             │
│    ├── NodeJSBridge.kt                                           │
│    │     ├── System.loadLibrary("native-lib")                   │
│    │     ├── System.loadLibrary("node")                         │
│    │     ├── copyNodeScripts() → assets/nodejs → filesDir       │
│    │     └── startNodeWithArguments() → JNI                     │
│    │                                                             │
│    └── HTTP requests to http://127.0.0.1:3000                   │
│          ├── /status → get connection status                    │
│          ├── /qr → get QR code                                  │
│          ├── /pair → request pairing code (phone number)        │
│          ├── /connect → start connection                        │
│          └── /send → send message                               │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ JNI
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Native Layer (C++)                            │
├─────────────────────────────────────────────────────────────────┤
│  native-lib.cpp                                                  │
│    └── startNodeWithArguments()                                 │
│          └── node::Start(argc, argv)                            │
│                                                                  │
│  CMakeLists.txt                                                  │
│    ├── native-lib.so (our bridge)                               │
│    └── libnode.so (nodejs-mobile v18.20.4)                      │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │ Node.js Runtime
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                    Node.js (main.js)                             │
├─────────────────────────────────────────────────────────────────┤
│  - HTTP server on port 3000                                      │
│  - @whiskeysockets/baileys for WhatsApp                         │
│  - File-based logging (node.log)                                │
│  - crypto.subtle polyfill                                        │
│  - Pairing code support                                          │
└─────────────────────────────────────────────────────────────────┘
```

### 1.2 Key Files

| File | Description |
|------|-------------|
| `app/src/main/cpp/native-lib.cpp` | JNI bridge to Node.js |
| `app/src/main/cpp/CMakeLists.txt` | CMake config for native build |
| `app/libnode/bin/*/libnode.so` | nodejs-mobile binaries |
| `app/libnode/include/node/` | Node.js headers |
| `app/src/main/assets/nodejs/main.js` | WhatsApp bridge script |
| `app/src/main/assets/nodejs/node_modules/` | npm dependencies |
| `app/src/main/java/.../nodejs/NodeJSBridge.kt` | Kotlin wrapper |
| `app/src/main/java/.../setup/WhatsAppSetupActivity.kt` | Setup UI |

### 1.3 Log File Location

```bash
# On device (requires debuggable app)
adb shell "run-as com.eldoleado.app cat files/nodejs/node.log"
```

### 1.4 HTTP API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/status` | GET | Connection status, phone, hasQr |
| `/qr` | GET | QR code string |
| `/pairing-code` | GET | Current pairing code |
| `/connect` | POST | Start WhatsApp connection |
| `/pair` | POST | Request pairing code `{phone: "+79..."}` |
| `/logout` | POST | Disconnect and clear session |
| `/send` | POST | Send message `{to: "...", text: "..."}` |

---

## Part 2: Authentication & Roles System

(Содержимое не изменилось - см. предыдущую версию)

---

## Appendix: Quick Commands

```bash
# Build Android app
export JAVA_HOME="/c/Program Files/Android/Android Studio/jbr"
cd /c/Users/User/Documents/Eldoleado
./gradlew.bat assembleDebug

# Install on device
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Clear nodejs data and reinstall (for testing)
adb shell "run-as com.eldoleado.app rm -rf files/nodejs"
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Check Node.js logs
adb shell "run-as com.eldoleado.app cat files/nodejs/node.log"

# Check if HTTP server is running
adb shell "netstat -tlnp | grep 3000"

# Test login API
curl -X POST https://n8n.n8nsrv.ru/webhook/android/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"admin@test.local","password":"test123","app_mode":"both"}'

# SSH to database server
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT * FROM elo_t_operators;'"
```

---

*Document version: 3.0 — 2025-12-19 16:15 MSK*
