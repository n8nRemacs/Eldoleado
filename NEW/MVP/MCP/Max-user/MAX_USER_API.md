# MAX User API — Полное описание

## Обзор

MAX (ранее TamTam, ICQ) использует **WebSocket API** для мобильных и веб-клиентов. В отличие от Bot API (REST), User API работает через постоянное WebSocket-соединение с бинарным JSON-протоколом.

```
┌─────────────┐     WebSocket      ┌──────────────────────┐
│   Client    │◄──────────────────►│  ws-api.oneme.ru     │
│  (vkmax)    │   JSON packets     │  (MAX backend)       │
└─────────────┘                    └──────────────────────┘
```

---

## Подключение

**WebSocket URL:** `wss://ws-api.oneme.ru/websocket`

**Headers:**
```
Origin: https://web.max.ru
User-Agent: Mozilla/5.0 ...
```

---

## Структура пакета

Каждый пакет — JSON с 5 полями:

```json
{
  "ver": 11,        // Версия протокола (всегда 11)
  "cmd": 0,         // 0 = запрос, 1 = ответ
  "seq": 1,         // Порядковый номер (для матчинга request-response)
  "opcode": 64,     // ID метода (см. таблицу ниже)
  "payload": {}     // Данные метода
}
```

**Принцип работы:**
1. Клиент отправляет пакет с `cmd=0` и уникальным `seq`
2. Сервер отвечает пакетом с `cmd=1` и тем же `seq`
3. События (новые сообщения и т.д.) приходят с `cmd=1` без запроса

---

## Авторизация

### Схема авторизации

```
┌────────┐  1. Hello (opcode 6)     ┌────────┐
│        │─────────────────────────►│        │
│        │  2. Start Auth (17)      │        │
│ Client │─────────────────────────►│ Server │
│        │  3. SMS Code (18)        │        │
│        │─────────────────────────►│        │
│        │  4. Login Token ◄────────│        │
└────────┘                          └────────┘
```

### Шаг 1: Hello (opcode 6)

```json
{
  "ver": 11, "cmd": 0, "seq": 1, "opcode": 6,
  "payload": {
    "userAgent": {
      "deviceType": "WEB",
      "locale": "ru_RU",
      "osVersion": "Windows",
      "deviceName": "My Client",
      "appVersion": "25.11.2",
      "screen": "1920x1080",
      "timezone": "Europe/Moscow"
    },
    "deviceId": "uuid-v4-string"
  }
}
```

### Шаг 2: Запрос SMS (opcode 17)

```json
{
  "ver": 11, "cmd": 0, "seq": 2, "opcode": 17,
  "payload": {
    "phone": "+79001234567",
    "type": "START_AUTH",
    "language": "ru"
  }
}
```

**Ответ:** `payload.token` — токен для подтверждения SMS

### Шаг 3: Подтверждение SMS (opcode 18)

```json
{
  "ver": 11, "cmd": 0, "seq": 3, "opcode": 18,
  "payload": {
    "token": "sms_token_from_step_2",
    "verifyCode": "123456",
    "authTokenType": "CHECK_CODE"
  }
}
```

**Ответ:**
- `payload.profile` — данные профиля
- `payload.tokenAttrs.LOGIN.token` — токен для повторного входа

### Шаг 4 (опционально): 2FA (opcode 18)

Если включена двухфакторка:
```json
{
  "payload": {
    "token": "sms_token",
    "password": "2fa_password",
    "authTokenType": "CHECK_PASSWORD"
  }
}
```

### Повторный вход по токену (opcode 19)

```json
{
  "ver": 11, "cmd": 0, "seq": 1, "opcode": 19,
  "payload": {
    "interactive": true,
    "token": "saved_login_token",
    "chatsSync": 0,
    "contactsSync": 0,
    "chatsCount": 40
  }
}
```

---

## Keepalive

Каждые **30 секунд** отправлять:

```json
{"ver": 11, "cmd": 0, "seq": N, "opcode": 1, "payload": {"interactive": false}}
```

Иначе соединение закроется.

---

## Все Opcodes (из reverse-engineered Android APK)

> **Источник:** `defpackage/xhb.java` из декомпилированного MAX.apk

### Системные (1-5)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 1 | PING | Keepalive пакет |
| 2 | DEBUG | Отладочные команды |
| 3 | RECONNECT | Переподключение |
| 5 | LOG | Логирование |
| 6 | SESSION_INIT | Инициализация сессии (HELLO) |

### Авторизация (16-127)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 16 | PROFILE | Изменение профиля |
| 17 | AUTH_REQUEST | Запрос SMS кода |
| 18 | AUTH | Подтверждение SMS кода |
| 19 | LOGIN | Вход по сохранённому токену |
| 20 | LOGOUT | Выход из аккаунта |
| 21 | SYNC | Синхронизация данных |
| 22 | CONFIG | Получение конфигурации |
| 23 | AUTH_CONFIRM | Подтверждение авторизации |
| 25 | PRESET_AVATARS | Предустановленные аватары |
| 96 | SESSIONS_INFO | Информация о сессиях |
| 97 | SESSIONS_CLOSE | Закрытие сессий |
| 98 | PHONE_BIND_REQUEST | Привязка телефона |
| 99 | PHONE_BIND_CONFIRM | Подтверждение привязки |
| 101 | AUTH_LOGIN_RESTORE_PASSWORD | Восстановление пароля |
| 104 | AUTH_2FA_DETAILS | Детали 2FA |
| 107 | AUTH_VALIDATE_PASSWORD | Валидация пароля |
| 108 | AUTH_VALIDATE_HINT | Валидация подсказки |
| 109 | AUTH_VERIFY_EMAIL | Верификация email |
| 110 | AUTH_CHECK_EMAIL | Проверка email |
| 111 | AUTH_SET_2FA | Установка 2FA |
| 112 | AUTH_CREATE_TRACK | Создание трека авторизации |
| 113 | AUTH_CHECK_PASSWORD | Проверка 2FA пароля |
| 115 | AUTH_LOGIN_CHECK_PASSWORD | Проверка пароля при логине |
| 116 | AUTH_LOGIN_PROFILE_DELETE | Удаление профиля |

### Контакты (32-46)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 32 | CONTACT_INFO | Информация о контакте |
| 33 | CONTACT_ADD | Добавление контакта |
| 34 | CONTACT_UPDATE | Обновление контакта |
| 35 | CONTACT_PRESENCE | Статус присутствия |
| 36 | CONTACT_LIST | Список контактов |
| 37 | CONTACT_SEARCH | Поиск контактов |
| 38 | CONTACT_MUTUAL | Общие контакты |
| 39 | CONTACT_PHOTOS | Фото контактов |
| 40 | CONTACT_SORT | Сортировка контактов |
| 42 | CONTACT_VERIFY | Верификация контакта |
| 43 | REMOVE_CONTACT_PHOTO | Удаление фото контакта |
| 46 | CONTACT_INFO_BY_PHONE | Контакт по номеру телефона |

### Ассеты и стикеры (26-29, 259-261)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 26 | ASSETS_GET | Получение ассетов |
| 27 | ASSETS_UPDATE | Обновление ассетов |
| 28 | ASSETS_GET_BY_IDS | Получение по ID |
| 29 | ASSETS_ADD | Добавление ассета |
| 259 | ASSETS_REMOVE | Удаление ассета |
| 260 | ASSETS_MOVE | Перемещение ассета |
| 261 | ASSETS_LIST_MODIFY | Изменение списка |

### Чаты (48-63)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 48 | CHAT_INFO | Информация о чате |
| 49 | CHAT_HISTORY | История сообщений |
| 50 | CHAT_MARK | Пометка прочитанным |
| 51 | CHAT_MEDIA | Медиафайлы чата |
| 52 | CHAT_DELETE | Удаление чата |
| 53 | CHATS_LIST | Список чатов |
| 54 | CHAT_CLEAR | Очистка чата |
| 55 | CHAT_UPDATE | Обновление настроек чата |
| 56 | CHAT_CHECK_LINK | Проверка ссылки |
| 57 | CHAT_JOIN | Вступление в чат |
| 58 | CHAT_LEAVE | Выход из чата |
| 59 | CHAT_MEMBERS | Участники чата |
| 60 | PUBLIC_SEARCH | Поиск публичных чатов |
| 61 | CHAT_CLOSE | Закрытие чата |
| 63 | CHAT_CREATE | Создание чата |

### Сообщения (64-74, 178-181)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 64 | MSG_SEND | Отправка сообщения |
| 65 | MSG_TYPING | Индикатор печати |
| 66 | MSG_DELETE | Удаление сообщения |
| 67 | MSG_EDIT | Редактирование сообщения |
| 68 | CHAT_SEARCH | Поиск в чате |
| 70 | MSG_SHARE_PREVIEW | Превью пересылки |
| 71 | MSG_GET | Получение сообщения |
| 72 | MSG_SEARCH_TOUCH | Поиск сообщений (touch) |
| 73 | MSG_SEARCH | Поиск сообщений |
| 74 | MSG_GET_STAT | Статистика сообщения |
| 92 | MSG_DELETE_RANGE | Удаление диапазона |
| 178 | MSG_REACTION | Добавить реакцию |
| 179 | MSG_CANCEL_REACTION | Отменить реакцию |
| 180 | MSG_GET_REACTIONS | Получить реакции |
| 181 | MSG_GET_DETAILED_REACTIONS | Детальные реакции |

### Видеозвонки (76-79, 195)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 75 | CHAT_SUBSCRIBE | Подписка на чат |
| 76 | VIDEO_CHAT_START | Начать видеозвонок |
| 77 | CHAT_MEMBERS_UPDATE | Обновление участников |
| 78 | VIDEO_CHAT_START_ACTIVE | Активный видеозвонок |
| 79 | VIDEO_CHAT_HISTORY | История звонков |
| 84 | VIDEO_CHAT_CREATE_JOIN_LINK | Ссылка для входа в звонок |
| 195 | VIDEO_CHAT_MEMBERS | Участники звонка |

### Загрузка файлов (80-90)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 80 | PHOTO_UPLOAD | Загрузка фото |
| 81 | STICKER_UPLOAD | Загрузка стикера |
| 82 | VIDEO_UPLOAD | Загрузка видео |
| 83 | VIDEO_PLAY | Воспроизведение видео |
| 86 | CHAT_PIN_SET_VISIBILITY | Видимость закреп. сообщения |
| 87 | FILE_UPLOAD | Загрузка файла |
| 88 | FILE_DOWNLOAD | Скачивание файла |
| 89 | LINK_INFO | Информация о ссылке |

### Уведомления / События (128-159)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 128 | NOTIF_MESSAGE | Новое сообщение |
| 129 | NOTIF_TYPING | Кто-то печатает |
| 130 | NOTIF_MARK | Пометка прочитанным |
| 131 | NOTIF_CONTACT | Обновление контакта |
| 132 | NOTIF_PRESENCE | Изменение статуса |
| 134 | NOTIF_CONFIG | Изменение конфигурации |
| 135 | NOTIF_CHAT | Обновление чата |
| 136 | NOTIF_ATTACH | Новое вложение |
| 137 | NOTIF_CALL_START | Входящий звонок |
| 139 | NOTIF_CONTACT_SORT | Сортировка контактов |
| 140 | NOTIF_MSG_DELETE_RANGE | Удаление диапазона |
| 142 | NOTIF_MSG_DELETE | Удаление сообщения |
| 143 | NOTIF_CALLBACK_ANSWER | Ответ на callback |
| 147 | NOTIF_LOCATION | Геолокация |
| 148 | NOTIF_LOCATION_REQUEST | Запрос геолокации |
| 150 | NOTIF_ASSETS_UPDATE | Обновление ассетов |
| 152 | NOTIF_DRAFT | Черновик |
| 153 | NOTIF_DRAFT_DISCARD | Удаление черновика |
| 154 | NOTIF_MSG_DELAYED | Отложенное сообщение |
| 155 | NOTIF_MSG_REACTIONS_CHANGED | Изменение реакций |
| 156 | NOTIF_MSG_YOU_REACTED | Ваша реакция |
| 158 | OK_TOKEN | OK токен |
| 159 | NOTIF_PROFILE | Обновление профиля |

### Прочее (117-199, 272-292)

| Opcode | Константа | Описание |
|--------|-----------|----------|
| 117 | CHAT_COMPLAIN | Жалоба на чат |
| 118 | MSG_SEND_CALLBACK | Callback отправки |
| 119 | SUSPEND_BOT | Приостановка бота |
| 124 | LOCATION_STOP | Остановка геолокации |
| 125 | LOCATION_SEND | Отправка геолокации |
| 126 | LOCATION_REQUEST | Запрос геолокации |
| 127 | GET_LAST_MENTIONS | Последние упоминания |
| 144 | CHAT_BOT_COMMANDS | Команды бота |
| 145 | BOT_INFO | Информация о боте |
| 160 | WEB_APP_INIT_DATA | Данные веб-приложения |
| 161 | COMPLAIN | Жалоба |
| 162 | COMPLAIN_REASONS_GET | Причины жалоб |
| 176 | DRAFT_SAVE | Сохранение черновика |
| 177 | DRAFT_DISCARD | Удаление черновика |
| 193 | STICKER_CREATE | Создание стикера |
| 194 | STICKER_SUGGEST | Предложение стикера |
| 196 | CHAT_HIDE | Скрытие чата |
| 198 | CHAT_SEARCH_COMMON_PARTICIPANTS | Общие участники |
| 199 | PROFILE_DELETE | Удаление профиля |
| 200 | PROFILE_DELETE_TIME | Время удаления профиля |
| 272 | FOLDERS_GET | Получение папок |
| 273 | FOLDERS_GET_BY_ID | Папка по ID |
| 274 | FOLDERS_UPDATE | Обновление папки |
| 275 | FOLDERS_REORDER | Перестановка папок |
| 276 | FOLDERS_DELETE | Удаление папки |
| 277 | NOTIF_FOLDERS | Уведомление о папках |
| 292 | NOTIF_BANNERS | Баннеры |

---

## Protobuf структуры (Android API)

> **Источник:** `ru/ok/tamtam/nano/Tasks.java` — Android использует Protobuf, а веб — JSON

### MsgSend (Отправка сообщения)
```protobuf
message MsgSend {
  int64 requestId = 1;
  int64 messageId = 2;
  int64 chatId = 3;
  int64 chatServerId = 4;
  int64 userId = 5;
  bool notify = 6;
  int64 lastKnownDraftTime = 7;
}
```

### MsgDelete (Удаление сообщения)
```protobuf
message MsgDelete {
  int64 requestId = 1;
  int64 chatId = 2;
  int64 chatServerId = 3;
  repeated int64 messagesId = 4;
  repeated int64 messagesServerId = 5;
  string complaint = 6;
  bool forMe = 7;
  int32 itemTypeId = 8;
  bool notDeleteMessageFromDb = 9;
}
```

### MsgEdit (Редактирование)
```protobuf
message MsgEdit {
  int64 requestId = 1;
  int64 chatId = 2;
  int64 messageId = 3;
  int64 chatServerId = 4;
  int64 messageServerId = 5;
  string text = 6;
  string oldText = 7;
  int32 oldStatus = 8;
  Attaches oldAttaches = 9;
  bool editAttaches = 10;
  MessageElements oldElements = 11;
}
```

### MsgReact (Реакция)
```protobuf
message MsgReact {
  int64 requestId = 1;
  int64 chatId = 2;
  int64 messageId = 3;
  int64 chatServerId = 4;
  int64 messageServerId = 5;
  string reaction = 6;  // emoji
  int32 reactionType = 7;  // 0=EMOJI, 1=STICKER
}
```

### ChatCreate (Создание чата)
```protobuf
message ChatCreate {
  int64 requestId = 1;
  int64 chatId = 2;
  string chatType = 3;  // "CHAT", "CHANNEL"
  int64 groupId = 4;
  string subjectType = 5;
  int64 subjectId = 6;
  string startPayload = 7;
  int64 cid = 8;
}
```

### ChatDelete (Удаление чата)
```protobuf
message ChatDelete {
  int64 requestId = 1;
  int64 chatId = 2;
  int64 chatServerId = 3;
  int64 lastEventTime = 4;
  bool forAll = 5;
}
```

### FileUpload (Загрузка файла)
```protobuf
message FileUpload {
  int64 requestId = 1;
  string attachLocalId = 2;
  int32 attachType = 3;  // 2=PHOTO, 3=VIDEO, 4=AUDIO, 10=FILE
  int64 audioId = 4;
  int64 chatId = 5;
  Rect crop = 6;
  string file = 7;
  int64 fileId = 8;
  string fileName = 9;
  int64 lastUpdatedFile = 10;
  int64 lastUpdatedOriginalFile = 11;
  int64 messageId = 12;
  string originalFile = 13;
  bool profile = 14;
  string url = 15;
  int64 videoId = 16;
}
```

### FileDownload (Скачивание файла)
```protobuf
message FileDownload {
  int64 requestId = 1;
  string attachId = 2;
  string attachType = 3;
  int64 audioId = 4;
  bool checkAutoloadConnection = 5;
  int64 fileId = 6;
  string fileName = 7;
  int32 invalidateCount = 8;
  int64 messageId = 9;
  int64 mp4GifId = 10;
  bool notCopyVideoToGallery = 11;
  bool notifyProgress = 12;
  int64 stickerId = 13;
  string url = 14;
  bool useOriginalExtension = 15;
  int64 videoId = 16;
}
```

### Profile (Профиль)
```protobuf
message Profile {
  int64 requestId = 1;
  string firstName = 2;
  string lastName = 3;
  string description = 4;
  string link = 5;
  string avatarType = 6;
  long photoId = 7;
  string photoToken = 8;
  Rect crop = 9;
}
```

### Attaches (Вложения)
```protobuf
message Attaches {
  repeated Attach attach = 1;
  InlineKeyboard keyboard = 2;
  ReplyKeyboard replyKeyboard = 3;
  SendAction sendAction = 4;
}

message Attach {
  enum Type {
    UNKNOWN = 0;
    PHOTO = 2;
    VIDEO = 3;
    AUDIO = 4;
    STICKER = 5;
    SHARE = 6;
    APP = 7;
    CALL = 8;
    MUSIC = 9;
    FILE = 10;
    CONTACT = 11;
    PRESENT = 12;
    INLINE_KEYBOARD = 13;
    LOCATION = 14;
    DAILY_MEDIA = 15;
    WIDGET = 16;
  }

  int32 type = 1;
  Photo photo = 2;
  Video video = 3;
  Audio audio = 4;
  Sticker sticker = 5;
  File file = 6;
  Contact contact = 7;
  Location location = 8;
  // ...
}
```

---

## Форматы данных (WebSocket JSON)

### Отправка сообщения (opcode 64)

```json
{
  "chatId": 123456789,
  "message": {
    "text": "Привет!",
    "cid": 1750000000000,  // Client ID - случайное число
    "elements": [],
    "attaches": []
  },
  "notify": true
}
```

### Типы вложений (attaches)

**Фото:**
```json
{"_type": "PHOTO", "photoToken": "token_from_upload"}
```

**Видео:**
```json
{"_type": "VIDEO", "videoId": 12345, "token": "token"}
```

**Файл:**
```json
{"_type": "FILE", "fileId": 12345}
```

**Голосовое:**
```json
{"_type": "VOICE", "voiceId": 12345, "duration": 5000}
```

**Стикер:**
```json
{"_type": "STICKER", "stickerId": 598965}
```

**Геолокация:**
```json
{"_type": "LOCATION", "lat": 55.7558, "lon": 37.6173}
```

**Контакт:**
```json
{"_type": "CONTACT", "contactId": 123456}
```

**Опрос:**
```json
{
  "_type": "POLL",
  "question": "Как дела?",
  "options": [{"text": "Хорошо"}, {"text": "Отлично"}],
  "anonymous": false,
  "multipleChoice": false
}
```

### Ответ на сообщение

```json
{
  "message": {
    "text": "Ответ",
    "link": {
      "type": "REPLY",
      "messageId": "original_message_id"
    }
  }
}
```

### Пересылка

```json
{
  "message": {
    "text": "",
    "link": {
      "type": "FORWARD",
      "chatId": 123,
      "messageIds": ["msg1", "msg2"]
    }
  }
}
```

### Создание группы

```json
{
  "message": {
    "cid": 175xxx,
    "attaches": [{
      "_type": "CONTROL",
      "event": "new",
      "chatType": "CHAT",  // или "CHANNEL"
      "title": "Название группы",
      "userIds": [123, 456]
    }]
  }
}
```

---

## Загрузка файлов

### Схема загрузки

```
1. Запрос URL (opcode 80/82/84/87)
   └── Ответ: upload_url

2. POST файла на upload_url
   └── Ответ: token/id

3. Отправка сообщения с attach
   └── opcode 64 + attaches
```

### Пример загрузки фото

```python
# 1. Получить URL
resp = await client.invoke_method(opcode=80, payload={"count": 1})
upload_url = resp["payload"]["url"]

# 2. Загрузить файл
async with aiohttp.ClientSession() as session:
    data = aiohttp.FormData()
    data.add_field('file', file_bytes, filename='photo.jpg')
    await session.post(upload_url, data=data)

# 3. Отправить сообщение
token = resp_json["photos"]["0"]["token"]
await send_message(client, chat_id, "", attaches=[{
    "_type": "PHOTO",
    "photoToken": token
}])
```

---

## События (Incoming)

События приходят с `cmd=1` без запроса. Обработка через callback:

```python
async def on_event(client, packet):
    opcode = packet.get("opcode")
    payload = packet.get("payload", {})

    if opcode == 128:  # Новое сообщение
        chat_id = payload["chatId"]
        text = payload["message"]["text"]
        print(f"Новое сообщение в {chat_id}: {text}")

    elif opcode == 129:  # Онлайн-статус
        user_id = payload["userId"]
        status = payload["presence"]
        print(f"Пользователь {user_id} теперь {status}")

client.set_callback(on_event)
```

---

## Сравнение с Bot API

| Аспект | Bot API | User API |
|--------|---------|----------|
| Протокол | REST HTTP | WebSocket |
| URL | platform-api.max.ru | ws-api.oneme.ru |
| Авторизация | access_token (бот) | Телефон + SMS |
| Real-time | Long polling / Webhook | WebSocket push |
| Возможности | Ограничены | Полные |
| Создание групп | Нет | Да |
| Звонки | Нет | Да (отдельный протокол) |
| 2FA | Нет | Да |

---

## Ограничения и лимиты

- **Keepalive:** каждые 30 сек
- **Макс. размер сообщения:** ~4000 символов
- **Макс. файл:** 4 GB
- **Макс. участников группы:** 10000
- **Rate limits:** не документированы, но есть

---

## Безопасность

- Все соединения через WSS (TLS)
- Токены имеют срок жизни
- 2FA опционально
- Сессии можно завершать удалённо

---

## Библиотека vkmax

Расширенная версия: `vkmax-reference/`

```python
from vkmax import MaxClient
from vkmax.functions import (
    send_message, get_chat_history, send_voice,
    get_user_presence, verify_2fa_password
)

client = MaxClient()
await client.connect()
await client.login_by_token(token, device_id)

# Отправить сообщение
await send_message(client, chat_id=123, text="Привет!")

# История
history = await get_chat_history(client, chat_id=123, count=50)

# Голосовое
await send_voice(client, chat_id=123, voice_path="audio.ogg")
```

---

## Ссылки

- [vkmax GitHub](https://github.com/nsdkinx/vkmax)
- [MAX Bot API](https://dev.max.ru/docs-api)
- [Web-клиент](https://web.max.ru)
