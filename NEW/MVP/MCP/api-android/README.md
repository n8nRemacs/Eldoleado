# Android API Gateway

FastAPI сервер для Android приложения Eldoleado. Проксирует запросы к n8n webhooks с JWT аутентификацией.

## Архитектура

```
Android App  ──▶  API Gateway  ──▶  n8n webhooks
               (FastAPI :8780)

• JWT Auth
• Rate Limiting (TODO)
• Request Validation
• Unified Error Format
```

## Endpoints

### Auth
- `POST /api/auth/login` - Логин оператора
- `POST /api/auth/logout` - Выход

### Appeals
- `GET /api/appeals` - Список обращений
- `GET /api/appeals/{id}` - Детали обращения
- `POST /api/appeals/{id}/take` - Взять в работу
- `POST /api/appeals/{id}/reject` - Отклонить
- `POST /api/appeals/{id}/send` - Отправить сообщение
- `POST /api/appeals/{id}/promo` - Отправить промо
- `POST /api/appeals/{id}/normalize` - Нормализовать
- `PATCH /api/appeals/{id}/mode` - Сменить режим AI/manual

### Devices
- `POST /api/appeals/{id}/devices` - Добавить устройство
- `PATCH /api/devices/{id}` - Обновить устройство
- `DELETE /api/devices/{id}` - Удалить устройство

### Repairs
- `POST /api/devices/{id}/repairs` - Добавить ремонт
- `PATCH /api/repairs/{id}` - Обновить ремонт
- `DELETE /api/repairs/{id}` - Удалить ремонт

### Settings
- `POST /api/fcm/register` - Регистрация FCM токена
- `PATCH /api/settings` - Обновить настройки

## Запуск

### Development
```bash
pip install -r requirements.txt
cp .env.example .env
# Отредактировать .env
python app.py
```

### Docker
```bash
cp .env.example .env
# Отредактировать .env
docker-compose up -d
```

## Деплой на Beget

1. Создать VPS на Beget
2. Установить Docker:
```bash
curl -fsSL https://get.docker.com | sh
```

3. Склонировать репозиторий:
```bash
git clone https://github.com/n8nRemacs/Eldoleado.git
cd Eldoleado/MCP/api-android
```

4. Настроить .env:
```bash
cp .env.example .env
nano .env
```

5. Запустить:
```bash
docker network create eldoleado
docker-compose up -d
```

6. Настроить nginx (опционально):
```nginx
server {
    listen 443 ssl;
    server_name api.eldoleado.ru;

    location / {
        proxy_pass http://localhost:8780;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## API Documentation

После запуска доступна по адресу:
- Swagger UI: http://localhost:8780/docs
- ReDoc: http://localhost:8780/redoc
