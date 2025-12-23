# Start Session - План на следующую сессию

## Приоритет 1: Авторизация каналов через Android

### 1.1 Avito - Puppeteer авторизация
- [ ] Установить Puppeteer/Playwright на сервер (155.212.221.189)
- [ ] Создать endpoint `/auth/avito/start` - принимает login/password
- [ ] Puppeteer логинится на avito.ru, извлекает sessid
- [ ] Сохраняем только sessid (не credentials)
- [ ] Android: экран ввода логин/пароль → вызов endpoint → статус

### 1.2 MAX - QR авторизация (User API)
- [ ] Добавить WebSocket endpoint для QR авторизации
- [ ] Использовать opcode 20 (QR_LOGIN) из MAX User API
- [ ] Android: показать QR код → пользователь сканирует в MAX → connected
- [ ] Документация: `NEW/MVP/MCP/Max-user/MAX_USER_API.md`

### 1.3 Telegram Bot
- [ ] Endpoint для регистрации бота по токену
- [ ] Токен получается от @BotFather
- [ ] Android: ввод токена → проверка через getMe → сохранение

### 1.4 Telegram User (позже)
- [ ] Endpoint для SMS авторизации
- [ ] api_id/api_hash - глобальные (в .env сервера)
- [ ] Пользователь вводит телефон → получает SMS код → вводит код

---

## Приоритет 2: Humanized отправка сообщений

**Уже сделано на 155.212.221.189:**

| Сервис | Порт | Humanizer | Typing |
|--------|------|-----------|--------|
| `avito-messenger-api` | 8766 | ✅ | ❌ (нет в API) |
| `max-bot-api` | 8768 | ✅ | ✅ typing_on |
| `telegram-bot-api` | 8761 | ✅ | ✅ typing |
| `telegram-user-api` | 8762 | ✅ | ✅ (ждёт credentials) |

**Задержки:**
- Рабочее (09:00-18:00): 3-7 сек
- Нерабочее (18:00-00:00, 07:00-09:00): 15-45 сек
- Ночное (00:00-07:00): 1-3 мин

---

## Серверы

| Сервер | IP | Сервисы |
|--------|-----|---------|
| MessagerOne | 155.212.221.189 | WhatsApp (8769), Avito (8766), MAX (8768), Telegram Bot (8761), Telegram User (8762) |
| n8n | 185.221.214.83 | n8n, PostgreSQL, Redis |
| Finnish | 217.145.79.27 | Telegram (legacy) |

---

## Тестовые данные

- **Оператор:** Test Admin (22222222-2222-2222-2222-222222222222)
- **Session:** 85bc5364-7765-4562-be9e-02d899bb575e
- **WhatsApp Session:** eldoleado_arceos
- **Телефон оператора:** +79997253777

---

## Полезные команды

```bash
# === Проверка сервисов ===
ssh root@155.212.221.189 "docker ps --format 'table {{.Names}}\t{{.Status}}\t{{.Ports}}'"

# === Логи ===
ssh root@155.212.221.189 "docker logs avito-messenger-api --tail 20"
ssh root@155.212.221.189 "docker logs max-bot-api --tail 20"
ssh root@155.212.221.189 "docker logs telegram-bot-api --tail 20"

# === WhatsApp ===
curl http://155.212.221.189:8769/sessions

# === Telegram User (.env) ===
ssh root@155.212.221.189 "cat /opt/mcp-telegram-user/.env"
```

---

## Файлы проекта

### Humanized клиенты
- `NEW/MVP/MCP/mcp-avito-user/humanized_client.py`
- `NEW/MVP/MCP/mcp-max/humanized_client.py`
- `NEW/MVP/MCP/Max-user/humanized_client.py`
- `NEW/MVP/MCP/Max-user/max_user_client.py`

### Android
- `app/src/main/java/com/eldoleado/app/channels/setup/WhatsAppSetupActivity.kt`
- `app/src/main/java/com/eldoleado/app/channels/ChannelCredentialsManager.kt`
