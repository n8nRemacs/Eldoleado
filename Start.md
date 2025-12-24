# Start Session - План на следующую сессию

## Приоритет 1: Тестирование Avito

- [ ] Проверить стабильность WakeLock (нет disconnect 1006)
- [ ] Проверить создание клиента в elo_t_clients
- [ ] Проверить client_name в сообщениях

---

## Приоритет 2: Другие каналы

### MAX - QR авторизация
- [ ] WebSocket endpoint для QR
- [ ] opcode 20 (QR_LOGIN)
- [ ] Android: показать QR → сканирование → connected

### Telegram Bot
- [ ] Endpoint для регистрации по токену
- [ ] Android: ввод токена → getMe → сохранение

---

## Текущий статус каналов

| Канал | Incoming | Outgoing | Авторизация |
|-------|----------|----------|-------------|
| Avito | ✅ Android WebView | ⏳ | ✅ WebView cookies |
| WhatsApp | ✅ Baileys | ✅ | ✅ QR код |
| MAX | ⏳ | ⏳ | ⏳ QR код |
| Telegram Bot | ⏳ | ⏳ | ⏳ Token |

---

## n8n изменения (применить в UI)

### ELO_In_Avito_User → Normalize Message
Добавить:
```javascript
external_user_id: body.sender_id || msg.fromUid,
client_name: body.sender_name || rawMsg.userName || null,
```

### ELO_Client_Resolve → Validate Input
Полный код в 123.md

### ELO_Client_Resolve → Prepare Client Cache Key
```javascript
const clientExternalId = data.external_user_id || data.external_chat_id;
return { ...data, client_cache_key, client_external_id: clientExternalId };
```

---

## Серверы

| Сервер | IP | Сервисы |
|--------|-----|---------|
| MessagerOne | 155.212.221.189 | WhatsApp, MAX, Telegram |
| n8n | 185.221.214.83 | n8n, PostgreSQL, Redis |
| Android | Mobile IP | Avito WebSocket |

---

## Важно

- **Avito работает только с мобильного IP** — VPN отключать!
- `sender_id` — уникальный ID клиента в Avito
- `sender_name` — имя для отображения
- WakeLock держит WebSocket активным в sleep

---

## Файлы

| Файл | Описание |
|------|----------|
| `AvitoWebViewClient.kt` | WebSocket через WebView |
| `ChannelMonitorService.kt` | Foreground service + WakeLock |
| `123.md` | Подробный отчёт |
| `DATABASE_ANALYSIS.md` | Схема БД |

---

*Последнее обновление: 2025-12-24*
