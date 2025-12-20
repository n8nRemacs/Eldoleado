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
**20 December 2025, 09:00 (MSK, UTC+3)**

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
- ✅ **WhatsApp** — Baileys + резидентный proxy работает!
- ❌ **Telegram** — токен слетает при переустановке
- ❌ **Avito** — неправильная страница авторизации
- ❌ **MAX** — требует QR, но API не поддерживает

---

## WhatsApp — РЕШЕНО (20.12.2025)

### Проблема была
- nodejs-mobile в APK — WebSocket зависал (datacenter IP блокируются WhatsApp)
- Серверный Baileys без proxy — тоже блокировка (405, 408 ошибки)

### Решение
**Baileys + резидентный proxy (geonix.com)**

### Как работает
```
mcp-whatsapp-baileys (localhost:3003)
    │
    ├── Baileys @whiskeysockets/baileys (latest)
    ├── socks-proxy-agent → резидентный proxy
    └── HTTP API:
          POST /sessions — создать сессию
          GET  /sessions/:id/qr — получить QR
          GET  /sessions/:id/status — статус
          POST /messages/text — отправить сообщение
```

### Proxy данные (geonix.com, 1GB до 20.01.2026)
```
socks5://4bac75b003ba6c8f:1Cl0A5wm@res.geonix.com:10000
```

### Запуск локально
```bash
cd /c/Users/User/Eldoleado/NEW/MVP/MCP/mcp-whatsapp-baileys
npm install
npm run build
PORT=3003 npm start
```

### Создать сессию и отправить сообщение
```bash
# Создать сессию с proxy
curl -X POST http://localhost:3003/sessions \
  -H "Content-Type: application/json" \
  -d '{"sessionId": "wa-proxy", "proxyUrl": "socks5://4bac75b003ba6c8f:1Cl0A5wm@res.geonix.com:10000"}'

# Получить QR (сохранить как PNG)
curl http://localhost:3003/sessions/wa-proxy/qr

# Отправить сообщение (через node для UTF-8)
node -e "const http=require('http');const data=JSON.stringify({sessionId:'wa-proxy',to:'79991234567',text:'Привет!'});const req=http.request({hostname:'localhost',port:3003,path:'/messages/text',method:'POST',headers:{'Content-Type':'application/json','Content-Length':Buffer.byteLength(data)}},res=>{let d='';res.on('data',c=>d+=c);res.on('end',()=>console.log(d))});req.write(data);req.end()"
```

---

## Другие каналы

| Канал | Проблема | Решение |
|-------|----------|---------|
| **Telegram** | Токен слетает при переустановке | Сохранять на сервере |
| **Avito** | Неправильная страница авторизации | Исправить WebView |
| **MAX** | Требует QR, но API не поддерживает | Использовать bot token |

---

## Ключевые файлы

| Файл | Описание |
|------|----------|
| `NEW/MVP/MCP/mcp-whatsapp-baileys/` | WhatsApp сервер (Baileys + proxy) |
| `NEW/MVP/Android Messager/ROADMAP.md` | Документация Android Messenger |
| `app/` | Android приложение |

---

## Quick Commands

```bash
# WhatsApp server
cd /c/Users/User/Eldoleado/NEW/MVP/MCP/mcp-whatsapp-baileys
PORT=3003 npm start

# Build Android
export JAVA_HOME="/c/Program Files/Android/Android Studio/jbr"
cd /c/Users/User/Eldoleado && ./gradlew.bat assembleDebug

# Install Android
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

**Before ending session:** update Start.md, Stop.md, git push
