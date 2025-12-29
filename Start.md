# Start Session - 2025-12-30

## Текущий статус: Web App готов к деплою

Pipeline работает, добавлен web-app для операторов.

---

## Инфраструктура

| Сервер | IP | Сервисы |
|--------|-----|---------|
| **Messenger** | 155.212.221.189 | MCP: telegram :8767, whatsapp :8769, avito :8793, max-user :8771 |
| **n8n** | 185.221.214.83 | n8n, PostgreSQL, Redis |
| **HTTPS Gateway** | msg.eldoleado.ru | nginx + Let's Encrypt |

---

## Что готово

### Web App (web-app/)
- Login страница
- Dialogs список
- Chat страница
- Settings с подключением каналов
- Channel Setup Modals (WhatsApp, Telegram, MAX)

### MCP Servers
- mcp-whatsapp :8769
- mcp-telegram :8767
- mcp-avito :8793
- mcp-max-user :8771

---

## ВАЖНО: Нужно сделать

### 1. Переимпортировать workflow в n8n

Файл: `NEW/workflows/API/ELO_API_Channel_Setup.json`

1. Открыть n8n: https://n8n.n8nsrv.ru
2. Удалить старый workflow ELO_API_Channel_Setup
3. Импортировать новый файл
4. Активировать workflow

### 2. Тестировать MAX User

После импорта workflow:
1. Открыть web-app Settings
2. Добавить MAX User канал
3. Ввести телефон, получить SMS, ввести код

### 3. Деплой web-app

```bash
# На сервере 155.212.221.189
cd /opt/eldoleado/web-app
npm install
npm run build
# Настроить nginx для статики
```

---

## Активные workflows (14+)

### Channel In (5 ON)
- ELO_In_WhatsApp
- ELO_In_Telegram_Bot
- ELO_In_Avito
- ELO_In_App
- ELO_Message_Router

### Channel Out (2 ON)
- ELO_Out_Telegram_Bot
- ELO_Out_WhatsApp

### API (7+ ON)
- ELO_API_Android_Auth
- ELO_API_Android_Dialogs
- ELO_API_Android_Messages
- ELO_API_Android_Send_Message
- ELO_API_Android_Logout
- ELO_API_Android_Register_FCM
- ELO_API_Android_Normalize
- ELO_API_Channel_Setup (нужно импортировать!)
- ELO_API_Channels_Status (нужно импортировать!)

---

## SSH

```bash
ssh root@155.212.221.189  # Messenger
ssh root@185.221.214.83   # n8n
```

---

## Полезные команды

```bash
# Логи MAX User MCP
ssh root@155.212.221.189 "docker logs mcp-max-user --tail 50"

# Проверить сессии MAX
curl -H "X-API-Key: eldoleado_mcp_2024" http://155.212.221.189:8771/sessions

# Проверить статус каналов
curl https://n8n.n8nsrv.ru/webhook/v1/channels/status
```

---

## Документация

| Файл | Описание |
|------|----------|
| Stop.md | Что сделано в прошлой сессии (29.12) |
| web-app/README.md | Документация web-app |
| NEW/DOCS/WORKFLOWS_ANALYSIS.md | Анализ всех workflows |

---

*Последнее обновление: 2025-12-29*
