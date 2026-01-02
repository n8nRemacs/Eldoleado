# Reverse Engineering Runbook

**Версия:** 1.0
**Дата:** 2025-01-01
**Цель:** Обновление MCP серверов при изменении API мессенджеров

---

## Оглавление

1. [Обзор процесса](#1-обзор-процесса)
2. [Необходимое оборудование и софт](#2-необходимое-оборудование-и-софт)
3. [Настройка окружения](#3-настройка-окружения)
4. [Мониторинг API](#4-мониторинг-api)
5. [WhatsApp: процесс реверса](#5-whatsapp-процесс-реверса)
6. [Telegram: процесс реверса](#6-telegram-процесс-реверса)
7. [Avito: процесс реверса](#7-avito-процесс-реверса)
8. [MAX (VK Teams): процесс реверса](#8-max-vk-teams-процесс-реверса)
9. [VK User: процесс реверса](#9-vk-user-процесс-реверса)
10. [Шаблоны промптов для Claude](#10-шаблоны-промптов-для-claude)
11. [Тестирование и деплой](#11-тестирование-и-деплой)
12. [Troubleshooting](#12-troubleshooting)

---

## 1. Обзор процесса

### Общая схема

```
┌─────────────────────────────────────────────────────────────────┐
│                    ТРИГГЕР: API сломался                        │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│  ШАГ 1: Захват трафика (mitmproxy)                    [15 мин] │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│  ШАГ 2: Декомпиляция APK (jadx)                       [10 мин] │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│  ШАГ 3: Анализ с Claude                               [30 мин] │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│  ШАГ 4: Патч и тестирование                           [30 мин] │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│  ШАГ 5: Деплой                                        [15 мин] │
└─────────────────────────────────────────────────────────────────┘

                    ОБЩЕЕ ВРЕМЯ: 1.5-2 часа
```

### Требуемые навыки

- Базовое понимание HTTP/HTTPS
- Умение следовать инструкциям
- Умение копировать данные в Claude
- **НЕ требуется:** знание криптографии, ассемблера, глубокий реверс

---

## 2. Необходимое оборудование и софт

### Оборудование

| Компонент | Требования | Примечание |
|-----------|------------|------------|
| Android телефон | Android 10+, разблокированный bootloader | Для root |
| Компьютер | Windows/Mac/Linux | Для инструментов |
| USB кабель | Для ADB | — |

### Рекомендуемые телефоны для root

| Телефон | Цена | Простота root |
|---------|------|---------------|
| Google Pixel 4a/5a | $150-200 | Очень легко |
| OnePlus 7/8 | $150-200 | Легко |
| Xiaomi (любой) | $100-150 | Легко (но ждать разблокировку) |
| Samsung | — | Сложно, не рекомендуется |

### Софт на телефоне

| Программа | Назначение | Ссылка |
|-----------|------------|--------|
| Magisk | Root | https://github.com/topjohnwu/Magisk |
| Magisk Trust User Certs | Доверие к mitmproxy сертификату | Magisk модуль |
| WhatsApp | Тестовый аккаунт | Play Store |
| Telegram | Тестовый аккаунт | Play Store |
| Avito | Тестовый аккаунт | Play Store |

### Софт на компьютере

| Программа | Назначение | Установка |
|-----------|------------|-----------|
| mitmproxy | Перехват HTTPS трафика | `pip install mitmproxy` |
| jadx | Декомпиляция APK | https://github.com/skylot/jadx |
| adb | Android Debug Bridge | Android SDK / `scoop install adb` |
| apktool | Распаковка APK (опционально) | https://ibotpeaches.github.io/Apktool/ |
| Frida | Runtime хуки (продвинутое) | `pip install frida-tools` |
| git | Контроль версий | https://git-scm.com |

---

## 3. Настройка окружения

### 3.1 Root телефона (один раз)

```bash
# 1. Разблокировать bootloader (зависит от производителя)
# Pixel:
adb reboot bootloader
fastboot flashing unlock

# 2. Установить Magisk
# - Скачать Magisk APK: https://github.com/topjohnwu/Magisk/releases
# - Установить через adb install
adb install Magisk-v27.0.apk

# 3. Пропатчить boot.img через Magisk (следовать инструкции в приложении)

# 4. Проверить root
adb shell su -c "whoami"
# Должно вывести: root
```

### 3.2 Настройка mitmproxy (один раз)

```bash
# 1. Установить mitmproxy
pip install mitmproxy

# 2. Запустить первый раз (создаст сертификаты)
mitmproxy
# Выйти: q

# 3. Сертификат будет в:
# Windows: %USERPROFILE%\.mitmproxy\mitmproxy-ca-cert.cer
# Linux/Mac: ~/.mitmproxy/mitmproxy-ca-cert.cer

# 4. Установить сертификат на телефон
adb push ~/.mitmproxy/mitmproxy-ca-cert.cer /sdcard/

# 5. На телефоне: Настройки → Безопасность → Установить сертификат
# Выбрать файл mitmproxy-ca-cert.cer

# 6. Для Android 10+: установить Magisk модуль "Move Certificates"
# Это переместит user-сертификат в system (нужно для перехвата)
```

### 3.3 Настройка Magisk Trust User Certs

```bash
# 1. Открыть Magisk
# 2. Настройки → Модули → Установить из хранилища
# 3. Найти "MagiskTrustUserCerts" или "Move Certificates"
# 4. Установить
# 5. Перезагрузить телефон
```

### 3.4 Настройка прокси на телефоне

```bash
# 1. Узнать IP компьютера в локальной сети
# Windows: ipconfig
# Linux/Mac: ifconfig или ip addr

# 2. На телефоне:
# Настройки → Wi-Fi → (ваша сеть) → Изменить
# Прокси: Вручную
# Хост: IP_КОМПЬЮТЕРА
# Порт: 8080

# 3. Запустить mitmproxy на компьютере
mitmproxy --listen-port 8080

# 4. Открыть браузер на телефоне → http://mitm.it
# Должна открыться страница mitmproxy
```

### 3.5 Создание baseline (один раз для каждого приложения)

```bash
# Создать директорию для baseline
mkdir -p ~/reverse-engineering/baseline/{whatsapp,telegram,avito,max}

# Сохранить текущие APK
adb shell pm path com.whatsapp
# Скопировать путь, например: /data/app/com.whatsapp-xxx/base.apk
adb pull /data/app/com.whatsapp-xxx/base.apk ~/reverse-engineering/baseline/whatsapp/

# Декомпилировать
jadx -d ~/reverse-engineering/baseline/whatsapp/src ~/reverse-engineering/baseline/whatsapp/base.apk

# Повторить для остальных приложений
```

---

## 4. Мониторинг API

### 4.1 Скрипт мониторинга

Создать файл `api_monitor.py`:

```python
#!/usr/bin/env python3
"""
API Health Monitor
Проверяет доступность API каждую минуту
При ошибке — отправляет алерт в Telegram
"""

import requests
import time
import json
from datetime import datetime

# Конфигурация
TELEGRAM_BOT_TOKEN = "YOUR_BOT_TOKEN"
TELEGRAM_CHAT_ID = "YOUR_CHAT_ID"
CHECK_INTERVAL = 60  # секунд

# Эндпоинты для проверки
ENDPOINTS = {
    "whatsapp": {
        "url": "http://155.212.221.189:8769/health",
        "expected_status": 200
    },
    "telegram_bot": {
        "url": "https://api.telegram.org/botYOUR_TOKEN/getMe",
        "expected_status": 200
    },
    "avito": {
        "url": "http://155.212.221.189:8765/health",
        "expected_status": 200
    },
    "max": {
        "url": "http://155.212.221.189:8768/health",
        "expected_status": 200
    }
}

def send_alert(message):
    """Отправить алерт в Telegram"""
    url = f"https://api.telegram.org/bot{TELEGRAM_BOT_TOKEN}/sendMessage"
    requests.post(url, json={
        "chat_id": TELEGRAM_CHAT_ID,
        "text": f"🚨 API ALERT\n\n{message}",
        "parse_mode": "HTML"
    })

def check_endpoint(name, config):
    """Проверить один эндпоинт"""
    try:
        response = requests.get(config["url"], timeout=10)
        if response.status_code != config["expected_status"]:
            return False, f"Status {response.status_code}"
        return True, "OK"
    except requests.exceptions.Timeout:
        return False, "Timeout"
    except requests.exceptions.ConnectionError:
        return False, "Connection Error"
    except Exception as e:
        return False, str(e)

def main():
    print(f"API Monitor started at {datetime.now()}")
    last_status = {name: True for name in ENDPOINTS}

    while True:
        for name, config in ENDPOINTS.items():
            ok, message = check_endpoint(name, config)

            # Если статус изменился с OK на ERROR
            if last_status[name] and not ok:
                alert_msg = f"<b>{name.upper()}</b> is DOWN!\n\n"
                alert_msg += f"URL: {config['url']}\n"
                alert_msg += f"Error: {message}\n"
                alert_msg += f"Time: {datetime.now()}"
                send_alert(alert_msg)
                print(f"[ALERT] {name}: {message}")

            # Если статус изменился с ERROR на OK
            elif not last_status[name] and ok:
                alert_msg = f"<b>{name.upper()}</b> is UP again!\n"
                alert_msg += f"Time: {datetime.now()}"
                send_alert(alert_msg)
                print(f"[RECOVERED] {name}")

            last_status[name] = ok

        time.sleep(CHECK_INTERVAL)

if __name__ == "__main__":
    main()
```

### 4.2 Запуск мониторинга

```bash
# Установить зависимости
pip install requests

# Запустить
python api_monitor.py

# Или как systemd сервис (Linux)
# /etc/systemd/system/api-monitor.service
```

### 4.3 Systemd сервис (для Linux сервера)

```ini
[Unit]
Description=API Health Monitor
After=network.target

[Service]
Type=simple
User=root
WorkingDirectory=/root/reverse-engineering
ExecStart=/usr/bin/python3 /root/reverse-engineering/api_monitor.py
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

---

## 5. WhatsApp: процесс реверса

### 5.1 Когда нужен реверс

- Мониторинг показал ошибку
- Baileys перестал работать
- Вышла новая версия WhatsApp с изменениями

### 5.2 Захват трафика

```bash
# 1. Запустить mitmproxy с записью
mitmweb --listen-port 8080 -w whatsapp_traffic.flow

# 2. На телефоне убедиться что прокси настроен

# 3. Открыть WhatsApp

# 4. Выполнить действия:
#    - Открыть чат
#    - Отправить текстовое сообщение
#    - Отправить фото
#    - Получить сообщение (попросить кого-то отправить)
#    - Отправить голосовое

# 5. Остановить mitmproxy (Ctrl+C)

# 6. Экспортировать в HAR
# В mitmweb: File → Export → HAR
# Сохранить как whatsapp_new.har
```

### 5.3 Декомпиляция APK

```bash
# 1. Получить путь к APK
adb shell pm path com.whatsapp
# Пример: /data/app/~~abc123==/com.whatsapp-xyz==/base.apk

# 2. Скачать APK
adb pull /data/app/~~abc123==/com.whatsapp-xyz==/base.apk whatsapp_new.apk

# 3. Декомпилировать
jadx -d whatsapp_new_src whatsapp_new.apk

# 4. Сравнить с baseline
diff -r ~/reverse-engineering/baseline/whatsapp/src whatsapp_new_src > whatsapp_diff.txt

# Или использовать meld для визуального сравнения
meld ~/reverse-engineering/baseline/whatsapp/src whatsapp_new_src
```

### 5.4 Ключевые классы WhatsApp

```
Где искать изменения:

com.whatsapp.protocol/           # Протокол общения
com.whatsapp.messaging/          # Отправка/получение сообщений
com.whatsapp.registration/       # Регистрация/авторизация
com.whatsapp.media/              # Медиа файлы
com.whatsapp.crypto/             # Шифрование (Signal Protocol)

Ключевые файлы:
- WAWebSocket.java               # WebSocket соединение
- MessageHandler.java            # Обработка сообщений
- MediaUpload.java               # Загрузка медиа
```

### 5.5 Анализ с Claude

См. [Раздел 9: Шаблоны промптов](#9-шаблоны-промптов-для-claude)

---

## 6. Telegram: процесс реверса

### 6.1 Особенности Telegram

- **MTProto** — открытый протокол (https://core.telegram.org/mtproto)
- Изменения редкие и документированные
- GramJS обычно обновляется быстро

### 6.2 Захват трафика

```bash
# Telegram использует свои сервера, не HTTPS
# Для MTProto нужен Frida

# Установить Frida на телефон
adb push frida-server-android-arm64 /data/local/tmp/
adb shell chmod +x /data/local/tmp/frida-server-android-arm64
adb shell su -c "/data/local/tmp/frida-server-android-arm64 &"

# Запустить скрипт перехвата
frida -U -f org.telegram.messenger -l telegram_hook.js
```

### 6.3 Frida скрипт для Telegram

Создать файл `telegram_hook.js`:

```javascript
// telegram_hook.js
// Перехват MTProto запросов

Java.perform(function() {
    var ConnectionsManager = Java.use("org.telegram.tgnet.ConnectionsManager");

    // Перехват отправки
    ConnectionsManager.sendRequest.overload(
        'org.telegram.tgnet.TLObject',
        'org.telegram.tgnet.RequestDelegate',
        'org.telegram.tgnet.QuickAckDelegate',
        'int', 'int', 'int', 'boolean'
    ).implementation = function(request, delegate, ackDelegate, flags, datacenterId, connetionType, immediate) {
        console.log("=== OUTGOING REQUEST ===");
        console.log("Type: " + request.getClass().getName());
        console.log("Data: " + JSON.stringify(request));
        return this.sendRequest(request, delegate, ackDelegate, flags, datacenterId, connetionType, immediate);
    };
});
```

### 6.4 Декомпиляция APK

```bash
# 1. Скачать APK
adb shell pm path org.telegram.messenger
adb pull /data/app/.../base.apk telegram_new.apk

# 2. Декомпилировать
jadx -d telegram_new_src telegram_new.apk

# 3. Ключевые классы:
#    org.telegram.tgnet/           # MTProto клиент
#    org.telegram.messenger/       # Логика приложения
#    org.telegram.ui/              # UI

# 4. Сравнить
diff -r ~/reverse-engineering/baseline/telegram/src telegram_new_src > telegram_diff.txt
```

### 6.5 Проверка Layer версии

```bash
# MTProto Layer — версия протокола
# Найти в коде:
grep -r "LAYER" telegram_new_src/org/telegram/tgnet/

# Пример: public static final int LAYER = 179;
# Если Layer изменился — нужно обновить GramJS
```

---

## 7. Avito: процесс реверса

### 7.1 Особенности Avito

- REST API поверх HTTPS
- Авторизация через Bearer token
- API относительно простой

### 7.2 Захват трафика

```bash
# 1. Запустить mitmproxy
mitmweb --listen-port 8080 -w avito_traffic.flow

# 2. На телефоне:
#    - Открыть Avito
#    - Войти в аккаунт
#    - Открыть чаты
#    - Отправить сообщение
#    - Получить сообщение

# 3. Экспортировать HAR
```

### 7.3 Ключевые эндпоинты Avito

```
Авторизация:
POST /api/1/auth/login

Сообщения:
GET  /api/1/messenger/chats
GET  /api/1/messenger/chats/{id}/messages
POST /api/1/messenger/chats/{id}/messages

Профиль:
GET  /api/1/profile
```

### 7.4 Декомпиляция

```bash
adb shell pm path ru.avito.app
adb pull /data/app/.../base.apk avito_new.apk
jadx -d avito_new_src avito_new.apk

# Ключевые классы:
#    ru.avito.messenger/
#    ru.avito.network/
#    ru.avito.api/
```

---

## 8. MAX (VK Teams) User: процесс реверса

### 8.1 Особенности MAX User

- **Реверс пользовательского аккаунта** (не Bot API)
- WebSocket для real-time событий
- REST API для операций
- Авторизация через OAuth2 / токен пользователя
- Приложение: "VK Teams" (бывший myteam)

### 8.2 Захват трафика

```bash
# MAX использует WebSocket — mitmproxy справится
mitmweb --listen-port 8080 --set websocket=true -w max_traffic.flow

# Действия в приложении VK Teams:
# - Авторизоваться (через VK ID или email)
# - Открыть чат
# - Отправить сообщение
# - Получить сообщение
# - Отправить файл/фото
```

### 8.3 Декомпиляция APK

```bash
# Получить APK
adb shell pm path ru.mail.myteam
# или
adb shell pm path com.vk.teams
adb pull /data/app/.../base.apk max_new.apk

# Декомпилировать
jadx -d max_new_src max_new.apk

# Ключевые классы:
#    ru.mail.myteam.network/       # API клиент
#    ru.mail.myteam.messenger/     # Сообщения
#    ru.mail.myteam.auth/          # Авторизация
#    ru.mail.myteam.websocket/     # WebSocket
```

### 8.4 Ключевые эндпоинты (User API)

```
Авторизация:
POST https://api.max.ru/auth/token
Headers:
  - Content-Type: application/x-www-form-urlencoded
Body:
  - grant_type=password
  - username={email}
  - password={password}
  - client_id={client_id}

Или OAuth2:
GET https://api.max.ru/oauth/authorize?client_id=...&redirect_uri=...

Получение чатов:
GET https://api.max.ru/chats/getChats
Headers:
  - Authorization: Bearer {access_token}

Сообщения:
GET https://api.max.ru/chats/getHistory?chatId={id}
POST https://api.max.ru/chats/sendMessage

WebSocket:
wss://api.max.ru/ws?token={access_token}

События WebSocket:
- msg:new         # Новое сообщение
- msg:read        # Прочитано
- msg:typing      # Печатает
- user:online     # Пользователь онлайн
```

### 8.5 Frida скрипт для MAX

```javascript
// max_hook.js
Java.perform(function() {
    // Хук на отправку HTTP запросов
    var OkHttpClient = Java.use('okhttp3.OkHttpClient');
    var Request = Java.use('okhttp3.Request');

    var RealCall = Java.use('okhttp3.internal.connection.RealCall');
    RealCall.execute.implementation = function() {
        var request = this.request();
        console.log("=== MAX REQUEST ===");
        console.log("URL: " + request.url().toString());
        console.log("Method: " + request.method());
        console.log("Headers: " + request.headers().toString());

        var response = this.execute();
        console.log("Response code: " + response.code());
        return response;
    };

    // Хук на WebSocket
    var WebSocketListener = Java.use('okhttp3.WebSocketListener');
    WebSocketListener.onMessage.overload('okhttp3.WebSocket', 'java.lang.String')
        .implementation = function(ws, text) {
            console.log("=== MAX WS MESSAGE ===");
            console.log(text);
            return this.onMessage(ws, text);
        };
});
```

---

## 9. VK User: процесс реверса

### 9.1 Особенности VK User

- **Реверс пользовательского аккаунта** (не Community API)
- VK API v5.199+ (версия меняется)
- Long Poll для получения сообщений
- Авторизация через OAuth2 (implicit flow) или direct auth
- Нужен access_token с правами messages

### 9.2 Авторизация VK

```
VK использует несколько методов:

1. Direct Auth (для своих приложений):
POST https://oauth.vk.com/token
  - grant_type=password
  - client_id=2274003  (Android app ID)
  - client_secret=hHbZxrka2uZ6jB1inYsH
  - username={phone}
  - password={password}
  - scope=messages,offline
  - v=5.199

2. Implicit OAuth (через WebView):
https://oauth.vk.com/authorize?client_id=...&scope=messages&redirect_uri=...

3. Code Flow (для серверов):
Более безопасный, но требует redirect_uri
```

### 9.3 Захват трафика

```bash
# mitmproxy для HTTPS
mitmweb --listen-port 8080 -w vk_traffic.flow

# Действия в VK:
# - Авторизоваться
# - Открыть сообщения
# - Отправить сообщение
# - Получить сообщение
# - Отправить фото
# - Посмотреть историю

# Обратить внимание на:
# - Версию API (v=5.xxx)
# - access_token в параметрах
# - Long Poll сервер
```

### 9.4 Декомпиляция APK

```bash
# Получить APK
adb shell pm path com.vkontakte.android
adb pull /data/app/.../base.apk vk_new.apk

# Декомпилировать
jadx -d vk_new_src vk_new.apk

# Ключевые классы:
#    com.vk.api/                   # VK API клиент
#    com.vk.messages/              # Сообщения
#    com.vk.auth/                  # Авторизация
#    com.vk.longpoll/              # Long Poll
#    com.vk.dto/                   # DTO модели
```

### 9.5 Ключевые эндпоинты VK

```
Базовый URL: https://api.vk.com/method/

Авторизация:
POST /token (oauth.vk.com)

Сообщения:
GET  messages.getConversations?v=5.199&access_token=...
GET  messages.getHistory?peer_id={id}&v=5.199&...
POST messages.send?peer_id={id}&message={text}&random_id={rand}&...

Long Poll (получение событий):
1. GET messages.getLongPollServer → {server, key, ts}
2. GET https://{server}?act=a_check&key={key}&ts={ts}&wait=25&mode=2

Медиа:
POST photos.getMessagesUploadServer
POST upload (upload_url из ответа)
POST photos.saveMessagesPhoto

Пользователи:
GET users.get?user_ids={id}
```

### 9.6 Long Poll формат

```javascript
// Ответ Long Poll
{
  "ts": "1234567890",
  "updates": [
    [4, message_id, flags, peer_id, timestamp, text, {...}],  // Новое сообщение
    [6, peer_id, local_id],                                    // Прочитано входящее
    [7, peer_id, local_id],                                    // Прочитано исходящее
    [8, -user_id, platform],                                   // Друг онлайн
    [9, -user_id],                                              // Друг оффлайн
    [61, user_id, flags],                                      // Печатает
    // ...
  ]
}

// Флаги сообщения (битовая маска)
1    = UNREAD
2    = OUTBOX
4    = REPLIED
8    = IMPORTANT
16   = CHAT (групповой чат)
32   = FRIENDS
64   = SPAM
128  = DELETED
256  = FIXED
512  = MEDIA
```

### 9.7 Frida скрипт для VK

```javascript
// vk_hook.js
Java.perform(function() {
    // Хук на VK API вызовы
    var VKApiCall = Java.use('com.vk.api.VKApiCall');

    VKApiCall.execute.implementation = function() {
        console.log("=== VK API CALL ===");
        console.log("Method: " + this.method);
        console.log("Params: " + JSON.stringify(this.params));

        var result = this.execute();
        console.log("Result: " + result);
        return result;
    };

    // Хук на Long Poll
    var LongPollService = Java.use('com.vk.longpoll.LongPollService');

    LongPollService.onUpdates.implementation = function(updates) {
        console.log("=== VK LONG POLL ===");
        console.log(JSON.stringify(updates));
        return this.onUpdates(updates);
    };
});
```

### 9.8 Особенности VK User API

```markdown
## Важные моменты:

1. Версия API (v=5.xxx)
   - Меняется часто
   - Нужно следить за deprecated методами
   - Новые версии могут требовать новые параметры

2. access_token
   - Срок жизни: вечный (с scope=offline)
   - Может быть отозван при смене пароля
   - Нужно хранить безопасно

3. Капча
   - VK может запросить капчу
   - Приходит captcha_sid и captcha_img
   - Нужно реализовать обработку

4. Rate Limits
   - 3 запроса в секунду для пользователя
   - 20 запросов в секунду для execute
   - При превышении: error 6 (Too many requests)

5. messages.send
   - Обязателен random_id (уникальный для идемпотентности)
   - peer_id: user_id, 2000000000+chat_id, -group_id

6. Long Poll
   - mode=2 для получения расширенных событий
   - wait=25 (25 секунд ожидания)
   - При разрыве — переподключаться с новым ts
```

---

## 10. Шаблоны промптов для Claude

### 10.1 Базовый анализ изменений

```markdown
# Контекст

Я разрабатываю MCP сервер для {MESSENGER} (WhatsApp/Telegram/Avito).
API перестал работать. Мне нужно понять что изменилось и как исправить.

# Старый трафик (работал)

```
{ВСТАВИТЬ HAR/ЗАПРОСЫ ИЗ BASELINE}
```

# Новый трафик (не работает)

```
{ВСТАВИТЬ HAR/ЗАПРОСЫ ПОСЛЕ ИЗМЕНЕНИЯ}
```

# Ошибка которую получаем

```
{ТЕКСТ ОШИБКИ}
```

# Diff кода приложения (jadx)

```diff
{ВСТАВИТЬ DIFF}
```

# Наш текущий код

```typescript
{ВСТАВИТЬ РЕЛЕВАНТНЫЙ КОД MCP}
```

# Задачи

1. Проанализируй различия между старым и новым трафиком
2. Определи что именно изменилось в протоколе
3. Объясни причину ошибки
4. Предложи патч для нашего кода
5. Укажи на потенциальные проблемы
```

### 10.2 Анализ нового эндпоинта

```markdown
# Контекст

В приложении {MESSENGER} появился новый функционал.
Мне нужно понять как работает API для этого функционала.

# Перехваченный трафик

## Запрос:
```
{METHOD} {URL}
Headers:
{HEADERS}

Body:
{BODY}
```

## Ответ:
```
Status: {STATUS}
Headers:
{HEADERS}

Body:
{BODY}
```

# Декомпилированный код (jadx)

```java
{КОД СВЯЗАННЫЙ С ЭТИМ ФУНКЦИОНАЛОМ}
```

# Задачи

1. Объясни что делает этот эндпоинт
2. Опиши структуру запроса и ответа
3. Напиши TypeScript интерфейсы для типизации
4. Напиши функцию для вызова этого API
```

### 10.3 Исправление ошибки авторизации

```markdown
# Контекст

MCP сервер для {MESSENGER} получает ошибку авторизации.
Раньше работало, теперь — нет.

# Как делаем авторизацию сейчас

```typescript
{НАШ КОД АВТОРИЗАЦИИ}
```

# Перехваченный трафик авторизации из приложения

## Запрос:
```
{ЗАПРОС}
```

## Ответ:
```
{ОТВЕТ}
```

# Ошибка которую получаем

```
{ОШИБКА}
```

# Diff кода авторизации (jadx)

```diff
{DIFF}
```

# Задачи

1. Найди различия в процессе авторизации
2. Определи что изменилось (headers, body, flow)
3. Предложи исправление
4. Проверь нет ли новых обязательных полей
```

### 10.4 Анализ шифрования/подписи

```markdown
# Контекст

Запросы к {MESSENGER} теперь требуют подпись или шифрование.
Мне нужно понять алгоритм.

# Перехваченные запросы

## Запрос 1:
```
{ЗАПРОС С ПОДПИСЬЮ}
```

## Запрос 2:
```
{ДРУГОЙ ЗАПРОС С ПОДПИСЬЮ}
```

## Запрос 3:
```
{ЕЩЁ ЗАПРОС}
```

# Декомпилированный код подписи (jadx)

```java
{КОД КОТОРЫЙ ГЕНЕРИРУЕТ ПОДПИСЬ}
```

# Задачи

1. Определи алгоритм подписи (HMAC, RSA, etc.)
2. Найди ключ или способ его получения
3. Определи какие поля входят в подпись
4. Напиши TypeScript функцию генерации подписи
```

### 10.5 Генерация патча

```markdown
# Контекст

Я понял что изменилось в API {MESSENGER}.
Теперь мне нужен патч для MCP сервера.

# Что изменилось

{ОПИСАНИЕ ИЗМЕНЕНИЙ}

# Текущий код MCP (который нужно изменить)

```typescript
{ПОЛНЫЙ ФАЙЛ ИЛИ РЕЛЕВАНТНАЯ ЧАСТЬ}
```

# Требования к патчу

1. Минимальные изменения
2. Обратная совместимость (если возможно)
3. Логирование для отладки
4. Обработка ошибок

# Задачи

1. Сгенерируй патч в формате diff
2. Объясни каждое изменение
3. Укажи как тестировать
4. Укажи риски
```

---

## 11. Тестирование и деплой

### 11.1 Локальное тестирование

```bash
# 1. Применить патч
cd ~/Eldoleado/NEW/MVP/MCP/mcp-whatsapp-arceos
git apply patch.diff

# 2. Собрать
npm run build

# 3. Запустить локально
npm run dev

# 4. Тестировать:
#    - Отправка сообщения
#    - Получение сообщения
#    - Медиа
#    - Авторизация
```

### 11.2 Чек-лист тестирования

```markdown
## WhatsApp
- [ ] Авторизация (QR код)
- [ ] Отправка текста
- [ ] Получение текста
- [ ] Отправка фото
- [ ] Получение фото
- [ ] Отправка голосового
- [ ] Получение голосового
- [ ] Отправка документа
- [ ] Получение документа
- [ ] Отправка в группу
- [ ] Получение из группы

## Telegram
- [ ] Авторизация (код)
- [ ] Отправка текста
- [ ] Получение текста
- [ ] Медиа
- [ ] Группы

## Avito
- [ ] Авторизация
- [ ] Получение чатов
- [ ] Отправка сообщения
- [ ] Получение сообщения

## MAX
- [ ] Авторизация
- [ ] WebSocket подключение
- [ ] Сообщения
```

### 11.3 Деплой

```bash
# 1. Коммит
git add -A
git commit -m "fix: Update protocol for {MESSENGER} API changes"

# 2. Push
git push origin main

# 3. Деплой на сервер
ssh root@155.212.221.189

# 4. Обновить и перезапустить
cd /app/mcp-whatsapp
git pull
docker-compose down
docker-compose up -d --build

# 5. Проверить логи
docker logs mcp-whatsapp-ip1 --tail 100 -f

# 6. Проверить health
curl http://localhost:8769/health
```

### 11.4 Rollback

```bash
# Если что-то пошло не так
git revert HEAD
git push origin main

# На сервере
cd /app/mcp-whatsapp
git pull
docker-compose down
docker-compose up -d --build
```

---

## 12. Troubleshooting

### Проблема: mitmproxy не перехватывает трафик

```bash
# Проверить что сертификат в system store
adb shell ls /system/etc/security/cacerts/ | grep mitmproxy

# Если нет — переустановить MagiskTrustUserCerts модуль
# и перезагрузить телефон
```

### Проблема: Certificate pinning

```bash
# Некоторые приложения проверяют сертификат
# Нужно отключить pinning через Frida

frida -U -f com.whatsapp -l ssl_pinning_bypass.js
```

Скрипт `ssl_pinning_bypass.js`:

```javascript
Java.perform(function() {
    var TrustManager = Java.use('javax.net.ssl.X509TrustManager');
    var SSLContext = Java.use('javax.net.ssl.SSLContext');

    var TrustManagerImpl = Java.registerClass({
        name: 'com.bypass.TrustManager',
        implements: [TrustManager],
        methods: {
            checkClientTrusted: function(chain, authType) {},
            checkServerTrusted: function(chain, authType) {},
            getAcceptedIssuers: function() { return []; }
        }
    });

    var TrustManagers = [TrustManagerImpl.$new()];
    var sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, TrustManagers, null);

    console.log("[*] SSL Pinning Bypassed");
});
```

### Проблема: Приложение крашится с Frida

```bash
# Использовать spawn вместо attach
frida -U -f com.whatsapp --no-pause -l script.js

# Или подождать полной загрузки
frida -U -n "WhatsApp" -l script.js
```

### Проблема: jadx падает на большом APK

```bash
# Увеличить память
jadx -d output -j 4 -Xmx8G large_app.apk

# Или декомпилировать только нужные классы
jadx -d output --show-bad-code app.apk
```

---

## Приложения

### A. Полезные ресурсы

| Ресурс | Ссылка |
|--------|--------|
| Baileys GitHub | https://github.com/WhiskeySockets/Baileys |
| GramJS GitHub | https://github.com/nicedoc/gramjs |
| Frida Docs | https://frida.re/docs/ |
| mitmproxy Docs | https://docs.mitmproxy.org/ |
| jadx GitHub | https://github.com/skylot/jadx |
| Magisk GitHub | https://github.com/topjohnwu/Magisk |

### B. Структура директорий

```
~/reverse-engineering/
├── baseline/
│   ├── whatsapp/
│   │   ├── base.apk
│   │   ├── src/
│   │   └── traffic.har
│   ├── telegram/
│   ├── avito/
│   └── max/
├── current/
│   ├── whatsapp/
│   ├── telegram/
│   ├── avito/
│   └── max/
├── scripts/
│   ├── api_monitor.py
│   ├── ssl_pinning_bypass.js
│   └── telegram_hook.js
└── patches/
    ├── 2025-01-15_whatsapp_auth.patch
    └── ...
```

### C. Контакты для эскалации

| Ситуация | Действие |
|----------|----------|
| Не могу понять изменения | Claude с полным контекстом |
| Claude не помогает | Baileys Discord/Issues |
| Критический даунтайм | Откат на предыдущую версию |

---

*Документ создан: 2025-01-01*
*Последнее обновление: 2025-01-01*
