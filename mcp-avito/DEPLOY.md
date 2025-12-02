# ТЗ: Развёртывание Avito Messenger API Server

## Что создано

Папка `mcp-avito/` с FastAPI сервером для интеграции с Avito Messenger:

```
mcp-avito/
├── app.py               # Главный FastAPI сервер
├── avito_client.py      # Async клиент Avito API (pooling + rate limit + retry)
├── config.py            # Настройки из ENV
├── requirements.txt     # Зависимости Python
├── .env.example         # Пример конфигурации
├── Dockerfile           # Docker образ
├── docker-compose.yml   # Docker compose с Redis
├── __init__.py          # Package
└── README.md            # Документация
```

### Возможности сервера

- **20 параллельных соединений** к Avito API
- **Rate limiting** (100 req/min) - не превышает лимиты Avito
- **Retry с exponential backoff** при ошибках
- **Redis** для хранения токенов и rate limit счётчиков
- **Webhook handler** для входящих сообщений → n8n

---

## Шаг 1: Подготовка сервера

```bash
# Обновить систему
apt update && apt upgrade -y

# Установить Python 3.11+
apt install -y python3.11 python3.11-venv python3-pip git

# Установить Redis (для токенов и rate limiting)
apt install -y redis-server
systemctl enable redis-server
systemctl start redis-server

# Открыть порт 8765 (для API)
ufw allow 8765/tcp
# Или если iptables:
iptables -A INPUT -p tcp --dport 8765 -j ACCEPT
```

---

## Шаг 2: Копирование файлов на сервер

```bash
# На сервере создать папку
mkdir -p /opt/avito-api
cd /opt/avito-api
```

### Вариант A: Через SCP (с локальной машины Windows)

```powershell
scp -r C:\Users\User\Dropbox\BattCRMApp\mcp-avito\* root@SERVER_IP:/opt/avito-api/
```

### Вариант B: Создать файлы вручную

Скопировать содержимое каждого файла через `nano` или `cat >`:

```bash
nano /opt/avito-api/app.py
# вставить содержимое app.py

nano /opt/avito-api/avito_client.py
# вставить содержимое avito_client.py

nano /opt/avito-api/config.py
# вставить содержимое config.py

nano /opt/avito-api/requirements.txt
# вставить содержимое requirements.txt
```

---

## Шаг 3: Настройка окружения

```bash
cd /opt/avito-api

# Создать виртуальное окружение
python3 -m venv venv
source venv/bin/activate

# Установить зависимости
pip install --upgrade pip
pip install -r requirements.txt
```

### Создать .env файл

```bash
cat > /opt/avito-api/.env << 'EOF'
# ============================================
# Avito API Credentials (ОБЯЗАТЕЛЬНО ЗАПОЛНИТЬ!)
# Получить на: https://www.avito.ru/professionals/api
# ============================================
AVITO_CLIENT_ID=your_client_id_here
AVITO_CLIENT_SECRET=your_client_secret_here
AVITO_USER_ID=your_avito_user_id_here

# ============================================
# Redis (для токенов и rate limiting)
# ============================================
REDIS_URL=redis://localhost:6379

# ============================================
# n8n Integration
# Webhook URL куда отправлять входящие сообщения
# ============================================
N8N_WEBHOOK_URL=https://n8n.n8nsrv.ru/webhook/avito

# ============================================
# Server Settings
# ============================================
SERVER_HOST=0.0.0.0
SERVER_PORT=8765

# ============================================
# Rate Limiting (под лимиты Avito API)
# ============================================
RATE_LIMIT_REQUESTS=100
RATE_LIMIT_WINDOW=60

# ============================================
# Connection Pool
# ============================================
MAX_CONNECTIONS=20
CONNECTION_TIMEOUT=30.0

# ============================================
# Retry Settings
# ============================================
MAX_RETRIES=3
RETRY_MIN_WAIT=1.0
RETRY_MAX_WAIT=10.0

# ============================================
# API Key для защиты endpoints (опционально)
# Если задан - нужен заголовок X-API-Key
# ============================================
API_KEY=your_secret_api_key_here
EOF
```

**ВАЖНО:** Отредактируй `.env` и заполни:
- `AVITO_CLIENT_ID`
- `AVITO_CLIENT_SECRET`
- `AVITO_USER_ID`

---

## Шаг 4: Тестовый запуск

```bash
cd /opt/avito-api
source venv/bin/activate
python app.py
```

Должен появиться вывод:
```
INFO:     Started server process
INFO:     Waiting for application startup.
INFO:     Application startup complete.
INFO:     Uvicorn running on http://0.0.0.0:8765
```

Проверить: `curl http://localhost:8765/health`

---

## Шаг 5: Production запуск через systemd

```bash
cat > /etc/systemd/system/avito-api.service << 'EOF'
[Unit]
Description=Avito Messenger API Server
After=network.target redis-server.service
Wants=redis-server.service

[Service]
Type=simple
User=root
WorkingDirectory=/opt/avito-api
Environment=PATH=/opt/avito-api/venv/bin
EnvironmentFile=/opt/avito-api/.env
ExecStart=/opt/avito-api/venv/bin/gunicorn app:app -w 4 -k uvicorn.workers.UvicornWorker -b 0.0.0.0:8765
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
EOF

# Применить и запустить
systemctl daemon-reload
systemctl enable avito-api
systemctl start avito-api

# Проверить статус
systemctl status avito-api

# Логи
journalctl -u avito-api -f
```

---

## Шаг 6: Проверка работы

### Health Check
```bash
curl http://localhost:8765/health
```
Ответ:
```json
{"status":"healthy","service":"avito-messenger-api","timestamp":"2025-12-01T..."}
```

### Server Info
```bash
curl http://localhost:8765/info
```

### Swagger UI
Открыть в браузере: `http://SERVER_IP:8765/docs`

---

## Шаг 7: Подписка на Avito Webhook

После запуска сервера нужно подписаться на уведомления от Avito:

```bash
curl -X POST http://localhost:8765/api/webhook/subscribe \
  -H "Content-Type: application/json" \
  -H "X-API-Key: your_secret_api_key_here" \
  -d '{
    "url": "http://SERVER_IP:8765/webhook/avito",
    "version": 2
  }'
```

**Замени `SERVER_IP` на внешний IP сервера!**

---

## REST API Endpoints

### Health & Info
| Method | Endpoint | Описание |
|--------|----------|----------|
| GET | `/health` | Health check |
| GET | `/info` | Server configuration |

### Auth
| Method | Endpoint | Описание |
|--------|----------|----------|
| GET | `/api/token` | Получить токен |
| POST | `/api/token/refresh` | Обновить токен |

### Chats
| Method | Endpoint | Описание |
|--------|----------|----------|
| GET | `/api/chats` | Список чатов |
| GET | `/api/chats/{chat_id}` | Информация о чате |
| POST | `/api/chats/{chat_id}/read` | Отметить прочитанным |
| GET | `/api/chats/unread/count` | Счётчик непрочитанных |
| GET | `/api/chats/search?item_id=X` | Поиск по объявлению |

### Messages
| Method | Endpoint | Описание |
|--------|----------|----------|
| GET | `/api/chats/{chat_id}/messages` | Список сообщений |
| POST | `/api/messages/send` | Отправить текст |
| POST | `/api/messages/send/image` | Отправить изображение |
| POST | `/api/messages/send/link` | Отправить ссылку |
| POST | `/api/messages/send/bulk` | Массовая отправка |
| DELETE | `/api/chats/{id}/messages/{msg_id}` | Удалить сообщение |

### Batch
| Method | Endpoint | Описание |
|--------|----------|----------|
| GET | `/api/messages/unread/all` | Все непрочитанные сообщения |

### Webhooks
| Method | Endpoint | Описание |
|--------|----------|----------|
| POST | `/api/webhook/subscribe` | Подписаться на события |
| POST | `/api/webhook/unsubscribe` | Отписаться |

### Incoming (от Avito)
| Method | Endpoint | Описание |
|--------|----------|----------|
| POST | `/webhook/avito` | Входящие сообщения → n8n |

---

## Интеграция с n8n

### Отправка сообщений из n8n

```
HTTP Request Node:
  Method: POST
  URL: http://SERVER_IP:8765/api/messages/send
  Headers:
    X-API-Key: your_api_key
  Body (JSON):
    {
      "chat_id": "{{ $json.external_chat_id }}",
      "text": "{{ $json.message_text }}"
    }
```

### Массовая отправка

```
HTTP Request Node:
  Method: POST
  URL: http://SERVER_IP:8765/api/messages/send/bulk
  Headers:
    X-API-Key: your_api_key
  Body (JSON):
    {
      "messages": [
        {"chat_id": "chat1", "text": "Hello 1"},
        {"chat_id": "chat2", "text": "Hello 2"}
      ]
    }
```

---

## Формат входящих сообщений (webhook → n8n)

Сервер нормализует сообщения от Avito в формат:

```json
{
  "channel": "avito",
  "external_user_id": "12345678",
  "external_chat_id": "chat_abc123",
  "text": "Текст сообщения",
  "timestamp": "2025-12-01T17:30:00",
  "client_name": "Имя клиента",
  "media": {
    "has_voice": false,
    "voice_url": null,
    "has_images": true,
    "images": [{"url": "https://..."}],
    "has_video": false,
    "has_document": false
  },
  "meta": {
    "external_message_id": "msg_123",
    "ad_id": "item_456",
    "original_media_type": "image"
  }
}
```

Этот формат совместим с существующим `BAT IN Avito` workflow.

---

## Troubleshooting

### Сервер не запускается
```bash
# Проверить логи
journalctl -u avito-api -n 50

# Проверить .env
cat /opt/avito-api/.env
```

### Redis не работает
```bash
systemctl status redis-server
redis-cli ping  # Должен вернуть PONG
```

### Rate Limit Exceeded
Уменьшить `RATE_LIMIT_REQUESTS` или увеличить `RATE_LIMIT_WINDOW` в `.env`

### Connection Timeout
Увеличить `CONNECTION_TIMEOUT` в `.env`

---

## Что нужно от тебя

1. **Avito API credentials** - получить на https://www.avito.ru/professionals/api
   - CLIENT_ID
   - CLIENT_SECRET
   - USER_ID

2. **Открыть SSH** на сервере (порт 22) - тогда смогу сам задеплоить

3. **Или выполнить команды выше вручную** через панель сервера
