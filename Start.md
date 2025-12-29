# Start Session - 2025-12-29

## Текущий статус: Pipeline РАБОТАЕТ

Messenger -> Batcher -> Resolver -> AI Stub -> Save Message

---

## Инфраструктура

| Сервер | IP | Сервисы |
|--------|-----|---------|
| **Messenger** | 155.212.221.189 | MCP: telegram :8767, whatsapp :8769, avito :8793, max-user :8771 |
| **n8n** | 185.221.214.83 | n8n, PostgreSQL, Redis |
| **HTTPS Gateway** | msg.eldoleado.ru | nginx + Let's Encrypt |

---

## Активные workflows (14/45)

### Channel In (5 ON)
- ELO_In_WhatsApp
- ELO_In_Telegram_Bot
- ELO_In_Avito
- ELO_In_App
- ELO_Message_Router

### Channel Out (2 ON)
- ELO_Out_Telegram_Bot
- ELO_Out_WhatsApp

### API (7 ON)
- ELO_API_Android_Auth
- ELO_API_Android_Dialogs
- ELO_API_Android_Messages
- ELO_API_Android_Send_Message
- ELO_API_Android_Logout
- ELO_API_Android_Register_FCM
- ELO_API_Android_Normalize

### AI (1 ON)
- ELO_Core_AI_Test_Stub

---

## Следующие задачи

### Приоритет 1: Активировать pipeline

1. Активировать ELO_Input_Batcher
2. Активировать ELO_Input_Processor  
3. Активировать ELO_Resolver
4. Активировать ELO_Tenant_Resolver
5. Активировать ELO_Client_Resolver
6. Активировать ELO_Dialog_Resolver

### Приоритет 2: Тестирование

1. Отправить сообщение в WhatsApp
2. Проверить что сообщение проходит весь pipeline
3. Проверить сохранение в elo_t_messages
4. Проверить вызов ELO_Core_AI_Test_Stub

### Приоритет 3: AI ответы

1. Заменить Test Stub на реальный AI
2. Подключить ELO_Out_Router
3. Отправлять ответы обратно клиенту

---

## Ключевые URLs

- n8n: https://n8n.n8nsrv.ru
- AI Webhook: https://n8n.n8nsrv.ru/webhook/elo-core-ingest

---

## SSH

```bash
ssh root@155.212.221.189  # Messenger
ssh root@185.221.214.83   # n8n
```

---

## Полезные команды

```bash
# Очистить Redis кеш
ssh root@185.221.214.83 "docker exec n8n-redis redis-cli FLUSHALL"

# Проверить кеш
ssh root@185.221.214.83 "docker exec n8n-redis redis-cli KEYS 'cache:*'"

# Посмотреть сообщения
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT id, content, created_at FROM elo_t_messages ORDER BY created_at DESC LIMIT 5;'"
```

---

## Документация

| Файл | Описание |
|------|----------|
| Stop.md | Что сделано в прошлой сессии |
| NEW/DOCS/WORKFLOWS_ANALYSIS.md | Анализ всех workflows |
| NEW/DOCS/DATABASE_ANALYSIS.md | Структура БД |
| NEW/DOCS/SYNC_AND_ANALYZE.md | Инструкция синхронизации |

---

*Последнее обновление: 2025-12-29*
