# Avito Messenger REST API Server

FastAPI сервер для интеграции с Avito Messenger API.
Высокопроизводительный HTTP API с connection pooling, rate limiting и retry логикой.

## Архитектура

```
┌─────────────────┐     ┌───────────────────┐     ┌─────────────┐
│  Avito Webhook  │────>│   FastAPI Server  │────>│    n8n      │
│  (incoming)     │     │   /webhook/avito  │     │  BAT IN     │
└─────────────────┘     └───────────────────┘     └─────────────┘
                                 │
                                 v
┌─────────────────┐     ┌───────────────────┐
│   Avito API     │<────│  Connection Pool  │
│   (outgoing)    │     │  + Rate Limiter   │
└─────────────────┘     └───────────────────┘
                                 ^
                                 │
┌─────────────────┐     ┌───────────────────┐
│      n8n        │────>│   REST API        │
│   HTTP Request  │     │   /api/*          │
└─────────────────┘     └───────────────────┘
```

## Производительность

| Параметр | Значение |
|----------|----------|
| Параллельные запросы | до 20 (настраивается) |
| Rate limit | 100 req/min (под лимиты Avito) |
| Retry | 3 попытки с exponential backoff |
| Timeout | 30 сек на запрос |

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
| GET | `/api/chats/search?item_id=X` | Поиск по item_id |

### Messages
| Method | Endpoint | Описание |
|--------|----------|----------|
| GET | `/api/chats/{chat_id}/messages` | Список сообщений |
| POST | `/api/messages/send` | Отправить текст |
| POST | `/api/messages/send/image` | Отправить изображение |
| POST | `/api/messages/send/link` | Отправить ссылку |
| POST | `/api/messages/send/bulk` | Массовая отправка |
| DELETE | `/api/chats/{chat_id}/messages/{msg_id}` | Удалить сообщение |

### Batch
| Method | Endpoint | Описание |
|--------|----------|----------|
| GET | `/api/messages/unread/all` | Все непрочитанные |

### Webhooks
| Method | Endpoint | Описание |
|--------|----------|----------|
| POST | `/api/webhook/subscribe` | Подписаться |
| POST | `/api/webhook/unsubscribe` | Отписаться |

### Blacklist
| Method | Endpoint | Описание |
|--------|----------|----------|
| POST | `/api/blacklist` | Заблокировать пользователя |

### Incoming Webhook
| Method | Endpoint | Описание |
|--------|----------|----------|
| POST | `/webhook/avito` | Входящие сообщения от Avito |

## Установка

### Локально

```bash
cd mcp-avito
pip install -r requirements.txt
cp .env.example .env
# Отредактируйте .env

# Запуск
python app.py
# или
uvicorn app:app --host 0.0.0.0 --port 8765 --reload
```

### Docker

```bash
cd mcp-avito
cp .env.example .env
# Отредактируйте .env

docker-compose up -d
```

### Production (с gunicorn)

```bash
gunicorn app:app -w 4 -k uvicorn.workers.UvicornWorker -b 0.0.0.0:8765
```

## Конфигурация

| Переменная | По умолчанию | Описание |
|------------|--------------|----------|
| `AVITO_CLIENT_ID` | - | Client ID из Avito API |
| `AVITO_CLIENT_SECRET` | - | Client Secret из Avito API |
| `AVITO_USER_ID` | - | Ваш Avito User ID |
| `REDIS_URL` | redis://localhost:6379 | Redis для токенов и rate limit |
| `N8N_WEBHOOK_URL` | - | URL n8n для входящих сообщений |
| `SERVER_HOST` | 0.0.0.0 | Host сервера |
| `SERVER_PORT` | 8765 | Port сервера |
| `RATE_LIMIT_REQUESTS` | 100 | Лимит запросов в окне |
| `RATE_LIMIT_WINDOW` | 60 | Окно rate limit (сек) |
| `MAX_CONNECTIONS` | 20 | Макс. параллельных соединений |
| `MAX_RETRIES` | 3 | Попыток повтора при ошибках |
| `API_KEY` | - | Ключ для защиты API (опционально) |

## Интеграция с n8n

### Отправка сообщений из n8n

```
HTTP Request Node:
  Method: POST
  URL: http://your-server:8765/api/messages/send
  Headers:
    X-API-Key: your_api_key
  Body (JSON):
    {
      "chat_id": "{{ $json.external_chat_id }}",
      "text": "{{ $json.message_text }}"
    }
```

### Получение чатов

```
HTTP Request Node:
  Method: GET
  URL: http://your-server:8765/api/chats?unread_only=true
  Headers:
    X-API-Key: your_api_key
```

### Массовая отправка

```
HTTP Request Node:
  Method: POST
  URL: http://your-server:8765/api/messages/send/bulk
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

## Формат входящих сообщений

Webhook `/webhook/avito` нормализует сообщения Avito в формат:

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

## Swagger UI

После запуска доступна документация:
- Swagger UI: http://localhost:8765/docs
- ReDoc: http://localhost:8765/redoc
- OpenAPI JSON: http://localhost:8765/openapi.json

## Мониторинг

### Health Check
```bash
curl http://localhost:8765/health
```

### Server Info
```bash
curl http://localhost:8765/info
```

## Типы сообщений

| Тип | Описание | Отправка |
|-----|----------|----------|
| text | Текст | `/api/messages/send` |
| image | Изображение | `/api/messages/send/image` |
| link | Ссылка | `/api/messages/send/link` |
| voice | Голос | Только приём |
| video | Видео | Только приём |
| file | Файл | Только приём |

## Ограничения Avito API

- Требуется бизнес-аккаунт с подпиской MAXIMUM
- Лимит ~100-300 запросов в минуту
- Токен живёт 24 часа
- Нельзя отправлять voice/video/file (только получать)

## Troubleshooting

### Rate Limit Exceeded
Увеличьте `RATE_LIMIT_WINDOW` или уменьшите `RATE_LIMIT_REQUESTS`.

### Connection Timeout
Увеличьте `CONNECTION_TIMEOUT` или уменьшите `MAX_CONNECTIONS`.

### Redis Connection Failed
Сервер работает без Redis (локальный fallback), но рекомендуется Redis для:
- Шаринга токена между инстансами
- Точного rate limiting
- Персистентности токена
