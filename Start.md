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
**19 December 2025, 16:15 (MSK, UTC+3)**

---

## Проект: Android Messager — Омниканальный мессенджер

### Что это
Мобильное приложение для операторов сервисных центров. Общение с клиентами через разные мессенджеры (Telegram, WhatsApp, Avito, MAX) из одного интерфейса.

### Текущий статус
- ✅ **Login + Roles** — работает (client/server/both)
- ✅ **Auth API** — `ELO_API_Android_Auth` в n8n
- ✅ **Dialogs API** — `ELO_API_Android_Dialogs` в n8n
- ✅ **Messages API** — `ELO_API_Android_Messages` в n8n
- ✅ **ChatActivity** — полноценный экран чата
- ✅ **tunnel-server** — работает на 155.212.221.189:8800
- 🔄 **WhatsApp** — nodejs-mobile встроен, WebSocket зависает
- ❌ **Telegram** — токен слетает при переустановке
- ❌ **Avito** — неправильная страница авторизации
- ❌ **MAX** — требует QR, но API не поддерживает

---

## CRITICAL: Проблемы с каналами (19.12.2025)

### WhatsApp — WebSocket зависает

**Статус:** Node.js + Baileys встроен в APK, но соединение не устанавливается.

**Лог показывает:**
```
[CONN] connection.update: {"connection":"connecting","receivedPendingNotifications":false}
```
После этого — тишина. Ни QR, ни ошибок.

**Что сделано:**
- ✅ libnode.so v18.20.4 встроен
- ✅ JNI bridge работает
- ✅ Baileys загружается (ESM, crypto polyfill)
- ✅ HTTP сервер на порту 3000
- ✅ DNS работает
- ✅ Endpoint `/pair` для pairing code добавлен

**Как проверить:**
```bash
# Посмотреть логи Node.js
adb shell "run-as com.eldoleado.app cat files/nodejs/node.log"

# Очистить и переустановить
adb shell "run-as com.eldoleado.app rm -rf files/nodejs"
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Другие каналы

| Канал | Проблема |
|-------|----------|
| **Telegram** | Токен слетает при переустановке (SharedPreferences) |
| **Avito** | При переходе открывается не та страница, токен не подхватывается |
| **MAX** | UI требует QR-код, но API MAX не поддерживает |

---

## Архитектура WhatsApp (nodejs-mobile)

```
Android App (Kotlin)
    │
    ├── WhatsAppSetupActivity
    │       │
    │       ├── NodeJSBridge.kt
    │       │     ├── loadLibrary("native-lib")
    │       │     ├── loadLibrary("node")
    │       │     └── startNodeWithArguments() → JNI
    │       │
    │       └── HTTP → http://127.0.0.1:3000
    │             ├── /status
    │             ├── /qr
    │             ├── /pair (NEW!)
    │             └── /connect
    │
    └── native-lib.cpp
          └── node::Start()
                │
                └── main.js
                      ├── HTTP server
                      ├── Baileys (@whiskeysockets/baileys)
                      └── node.log (file logging)
```

---

## NEXT STEPS

### 1. WhatsApp — попробовать Pairing Code
- Endpoint `/pair` уже добавлен
- Нужен UI для ввода номера телефона
- Показать код пользователю для ввода в WhatsApp

### 2. WhatsApp — альтернативы
- VPN/proxy
- Другие библиотеки (wa-js)
- WhatsApp Business API

### 3. Исправить другие каналы
- Telegram: сохранять токен на сервере
- Avito: исправить WebView
- MAX: изменить на bot token

---

## Ключевые файлы

| Файл | Описание |
|------|----------|
| `NEW/MVP/Android Messager/ROADMAP.md` | Полная документация проблем |
| `app/src/main/assets/nodejs/main.js` | WhatsApp bridge |
| `app/src/main/cpp/native-lib.cpp` | JNI bridge |
| `app/src/main/java/.../nodejs/NodeJSBridge.kt` | Kotlin wrapper |
| `app/src/main/java/.../setup/WhatsAppSetupActivity.kt` | Setup UI |

---

## Quick Commands

```bash
# Build
export JAVA_HOME="/c/Program Files/Android/Android Studio/jbr"
cd /c/Users/User/Documents/Eldoleado && ./gradlew.bat assembleDebug

# Install
adb install -r app/build/outputs/apk/debug/app-debug.apk

# WhatsApp logs
adb shell "run-as com.eldoleado.app cat files/nodejs/node.log"

# Clear WhatsApp data
adb shell "run-as com.eldoleado.app rm -rf files/nodejs"

# Check port
adb shell "netstat -tlnp | grep 3000"
```

---

**Before ending session:** update Start.md, Stop.md, git push
