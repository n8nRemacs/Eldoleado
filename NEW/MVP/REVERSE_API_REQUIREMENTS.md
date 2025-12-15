# Reverse Engineering API Requirements

## Цель
Полнофункциональные мессенджеры от имени пользователя через туннель на Android.

---

## 1. Авито (Reverse Engineering)

### Базовые операции

| # | Операция | Endpoint (нужно найти) | Приоритет |
|---|----------|------------------------|-----------|
| 1 | Авторизация | Login/session | P0 |
| 2 | Получить список чатов | GET /chats | P0 |
| 3 | Получить сообщения чата | GET /chats/{id}/messages | P0 |
| 4 | Отправить текст | POST /chats/{id}/messages | P0 |
| 5 | Прочитать сообщения | POST /chats/{id}/read | P1 |

### Медиа

| # | Операция | Endpoint | Приоритет |
|---|----------|----------|-----------|
| 6 | Отправить фото | POST /messages/image | P0 |
| 7 | Отправить видео | POST /messages/video | P1 |
| 8 | Отправить файл | POST /messages/file | P1 |
| 9 | Скачать медиа | GET /media/{id} | P1 |
| 10 | Голосовое сообщение | POST/GET /voice | P1 |

### Редактирование

| # | Операция | Endpoint | Приоритет |
|---|----------|----------|-----------|
| 11 | Редактировать сообщение | PATCH /messages/{id} | P2 |
| 12 | Удалить сообщение | DELETE /messages/{id} | P2 |

### IP-телефония (Авито)

| # | Операция | Endpoint | Приоритет |
|---|----------|----------|-----------|
| 13 | Список звонков | GET /calls | P1 |
| 14 | Скачать запись звонка | GET /calls/{id}/record | P1 |
| 15 | Метаданные звонка | GET /calls/{id} | P2 |

### Webhook/Polling

| # | Операция | Endpoint | Приоритет |
|---|----------|----------|-----------|
| 16 | Long polling | GET /updates | P0 |
| 17 | WebSocket | WSS /realtime | P1 |

---

## 2. Max (Reverse Engineering)

### Базовые операции

| # | Операция | Endpoint (нужно найти) | Приоритет |
|---|----------|------------------------|-----------|
| 1 | Авторизация | Login/OAuth | P0 |
| 2 | Получить список диалогов | GET /dialogs | P0 |
| 3 | Получить сообщения | GET /dialogs/{id}/messages | P0 |
| 4 | Отправить текст | POST /messages | P0 |
| 5 | Typing indicator | POST /typing | P2 |

### Медиа

| # | Операция | Endpoint | Приоритет |
|---|----------|----------|-----------|
| 6 | Отправить фото | POST /messages/photo | P0 |
| 7 | Отправить видео | POST /messages/video | P1 |
| 8 | Отправить файл | POST /messages/file | P1 |
| 9 | Стикер | POST /messages/sticker | P2 |
| 10 | Голосовое | POST /messages/voice | P1 |

### Редактирование

| # | Операция | Endpoint | Приоритет |
|---|----------|----------|-----------|
| 11 | Редактировать | PATCH /messages/{id} | P2 |
| 12 | Удалить | DELETE /messages/{id} | P2 |
| 13 | Переслать | POST /messages/forward | P2 |

### Real-time

| # | Операция | Endpoint | Приоритет |
|---|----------|----------|-----------|
| 14 | Long polling | GET /updates | P0 |
| 15 | WebSocket | WSS /events | P1 |

---

## 3. VK User API

### Базовые операции (messages.*)

| # | Операция | VK API Method | Приоритет |
|---|----------|---------------|-----------|
| 1 | Авторизация | OAuth implicit flow | P0 |
| 2 | Список диалогов | messages.getConversations | P0 |
| 3 | История сообщений | messages.getHistory | P0 |
| 4 | Отправить | messages.send | P0 |
| 5 | Прочитать | messages.markAsRead | P1 |

### Медиа

| # | Операция | VK API Method | Приоритет |
|---|----------|---------------|-----------|
| 6 | Загрузить фото | photos.getMessagesUploadServer | P0 |
| 7 | Загрузить документ | docs.getMessagesUploadServer | P1 |
| 8 | Голосовое | docs.getMessagesUploadServer (audio_message) | P1 |

### Редактирование

| # | Операция | VK API Method | Приоритет |
|---|----------|---------------|-----------|
| 9 | Редактировать | messages.edit | P2 |
| 10 | Удалить | messages.delete | P2 |
| 11 | Переслать | messages.send (forward) | P2 |

### Real-time

| # | Операция | VK API Method | Приоритет |
|---|----------|---------------|-----------|
| 12 | Long Poll | messages.getLongPollServer | P0 |
| 13 | Callback API | — | P1 |

---

## 4. Telegram User (Pyrogram)

### Уже реализовано в Pyrogram

| # | Операция | Pyrogram Method | Статус |
|---|----------|-----------------|--------|
| 1 | Авторизация | Client.start() | ✅ |
| 2 | Список диалогов | get_dialogs() | ✅ |
| 3 | История | get_chat_history() | ✅ |
| 4 | Отправить | send_message() | ✅ |
| 5 | Фото | send_photo() | ✅ |
| 6 | Видео | send_video() | ✅ |
| 7 | Файл | send_document() | ✅ |
| 8 | Голосовое | send_voice() | ✅ |
| 9 | Редактировать | edit_message_text() | ✅ |
| 10 | Удалить | delete_messages() | ✅ |
| 11 | Real-time | on_message handler | ✅ |

---

## 5. WhatsApp (Baileys)

### Уже реализовано в Baileys

| # | Операция | Baileys Method | Статус |
|---|----------|----------------|--------|
| 1 | QR авторизация | sock.ev.on('connection.update') | ✅ |
| 2 | Список чатов | sock.groupFetchAllParticipating() | ✅ |
| 3 | История | sock.fetchMessageHistory() | ⚠️ limited |
| 4 | Отправить | sock.sendMessage() | ✅ |
| 5 | Фото | sock.sendMessage(image) | ✅ |
| 6 | Видео | sock.sendMessage(video) | ✅ |
| 7 | Файл | sock.sendMessage(document) | ✅ |
| 8 | Голосовое | sock.sendMessage(audio, ptt) | ✅ |
| 9 | Редактировать | sock.sendMessage(edit) | ✅ |
| 10 | Удалить | sock.sendMessage(delete) | ✅ |
| 11 | Real-time | sock.ev.on('messages.upsert') | ✅ |

---

## Где искать API

### Авито

```
1. Android APK → декомпиляция → smali/java
2. Charles Proxy / mitmproxy на телефоне
3. Browser DevTools → Network tab
4. Официальная документация (частично)
```

### Max

```
1. Web-версия max.ru → DevTools → Network
2. Android APK декомпиляция
3. mitmproxy для перехвата
```

### VK User

```
VK User API документирован, но требует:
- Standalone приложение
- access_token с scope=messages
- Может потребоваться подтверждение телефона
```

---

## Инструменты для реверса

| Инструмент | Для чего |
|------------|----------|
| mitmproxy | Перехват HTTPS трафика |
| Charles Proxy | То же, GUI |
| Frida | Runtime hooking Android |
| jadx | Декомпиляция APK |
| Apktool | Разборка APK |
| HTTPToolkit | HTTP отладка |

---

## План действий

### Шаг 1: Настроить среду
- [ ] mitmproxy + Android с сертификатом
- [ ] Установить Авито, Max, VK на тестовый телефон

### Шаг 2: Перехватить трафик
- [ ] Авито: все запросы при работе с чатами
- [ ] Max: все запросы мессенджера
- [ ] VK: проверить User API доступность

### Шаг 3: Документировать API
- [ ] Записать endpoints, headers, body
- [ ] Определить auth flow
- [ ] Найти механизм real-time updates

### Шаг 4: Реализовать клиенты
- [ ] avito_user_client.py
- [ ] max_user_client.py
- [ ] vk_user_client.py
- [ ] telegram_user_client.py (Pyrogram wrapper)
