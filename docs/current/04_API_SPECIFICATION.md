# BatteryCRM - REST API Specification

**Версия:** 2.0
**Дата:** 2025-11-23
**Статус:** Актуальная спецификация
**Base URL:** `https://n8n.n8nsrv.ru/webhook`

---

## Содержание

1. [Обзор API](#обзор-api)
2. [Аутентификация и авторизация](#аутентификация-и-авторизация)
3. [Общие правила](#общие-правила)
4. [Endpoints для операторов](#endpoints-для-операторов)
5. [Endpoints для Android-приложения](#endpoints-для-android-приложения)
6. [Endpoints для workflows](#endpoints-для-workflows)
7. [Endpoints для интеграций](#endpoints-для-интеграций)
8. [Коды ошибок](#коды-ошибок)

---

## Обзор API

### Назначение

**BatteryCRM API** предоставляет интерфейс для:
- Авторизации операторов (Android/Desktop)
- Управления заявками и сообщениями
- Работы с мультизаявками (несколько устройств и неисправностей)
- Голосовой обработки (транскрибация, AI оформление)
- Управления промо-материалами
- Расчёта стоимости (прайсы поставщиков)
- Интеграции с внешними CRM (LiveSklad, RemOnline)
- Работы с QR-кодами

### Архитектура

```
┌─────────────────────────────────────────┐
│   Android/Desktop Apps                  │
│   (JWT Bearer Token)                    │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│   n8n API Gateway                       │
│   https://n8n.n8nsrv.ru/webhook         │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│   n8n Workflows                         │
│   (API_*, BAT_*)                        │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│   PostgreSQL Database                   │
│   185.221.214.83:6544                   │
└─────────────────────────────────────────┘
```

### Принципы API

1. **RESTful** — стандартные HTTP методы (GET, POST, PUT, DELETE)
2. **JSON** — все запросы и ответы в формате JSON
3. **Stateless** — состояние хранится только в БД
4. **Multi-Tenant** — автоматическая изоляция по `tenant_id`
5. **Idempotency** — поддержка идемпотентных операций через `X-Idempotency-Key`

---

## Аутентификация и авторизация

### JWT Bearer Token

Все запросы к API (кроме `/auth/login`) требуют JWT токен в заголовке:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Структура JWT токена

```json
{
  "operator_id": "7b3aac9e-...",
  "tenant_id": "a0eebc99-...",
  "role": "operator",
  "device_type": "mobile",
  "session_token": "4e9afe35-...",
  "exp": 1700000000
}
```

### Получение токена

**Endpoint:** `POST /auth/login`

**Request:**
```json
{
  "email": "operator@example.com",
  "password": "password123",
  "device_type": "mobile",
  "fcm_token": "fcm_token_here",
  "device_info": {
    "manufacturer": "Samsung",
    "model": "Galaxy S23",
    "os_version": "Android 13"
  }
}
```

**Response:**
```json
{
  "success": true,
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "operator": {
    "id": "7b3aac9e-...",
    "tenant_id": "a0eebc99-...",
    "name": "Иван Иванов",
    "email": "operator@example.com",
    "role": "operator"
  },
  "session": {
    "session_token": "4e9afe35-...",
    "device_type": "mobile",
    "expires_at": "2025-12-23T12:00:00Z"
  }
}
```

**Логика:**
1. Проверка credentials в таблице `operators`
2. **Удаление старой сессии** (если есть) для данного `operator_id` + `device_type`
3. Создание новой записи в `operator_devices`
4. Генерация JWT токена
5. Возврат токена клиенту

**Важно:** Один оператор может иметь максимум **1 mobile + 1 desktop** сессию одновременно.

---

### Обновление токена

**Endpoint:** `POST /auth/refresh`

**Headers:**
```http
Authorization: Bearer <expired_token>
```

**Response:**
```json
{
  "success": true,
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "expires_at": "2025-12-24T12:00:00Z"
}
```

---

### Выход (Logout)

**Endpoint:** `POST /auth/logout`

**Headers:**
```http
Authorization: Bearer <token>
```

**Response:**
```json
{
  "success": true
}
```

**Логика:**
1. Извлечение `session_token` из JWT
2. Удаление записи из `operator_devices`
3. Токен становится недействительным

---

## Общие правила

### Заголовки запросов

Все запросы должны содержать:

```http
Content-Type: application/json
Authorization: Bearer <token>
X-Request-ID: <uuid>          # Опционально, для трейсинга
X-Idempotency-Key: <uuid>     # Опционально, для идемпотентности
```

### Формат ответов

**Успешный ответ:**
```json
{
  "success": true,
  "data": { ... }
}
```

**Ошибка:**
```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Invalid appeal_id",
    "details": {
      "field": "appeal_id",
      "issue": "must be a valid UUID"
    }
  }
}
```

### Пагинация

Endpoints с списками поддерживают пагинацию:

**Query Parameters:**
- `limit` — количество записей (по умолчанию 20, максимум 100)
- `offset` — смещение (по умолчанию 0)

**Response:**
```json
{
  "success": true,
  "data": [...],
  "pagination": {
    "limit": 20,
    "offset": 0,
    "total": 145,
    "has_more": true
  }
}
```

### Multi-Tenant изоляция

Все запросы автоматически фильтруются по `tenant_id` из JWT токена.

Например:
```sql
SELECT * FROM appeals WHERE tenant_id = '<tenant_id_from_jwt>' AND id = '<appeal_id>'
```

---

## Endpoints для операторов

### 1. Получение списка заявок

**Endpoint:** `GET /api/operator/appeals`

**Query Parameters:**
- `status` — фильтр по статусу (опционально, можно несколько через запятую)
- `stage` — фильтр по этапу воронки (опционально)
- `assigned_to_me` — только мои заявки (boolean)
- `search` — поиск по тексту (имя клиента, описание)
- `limit` — количество записей (по умолчанию 20)
- `offset` — смещение (по умолчанию 0)
- `sort_by` — сортировка (`created_at`, `updated_at`, `priority`)
- `sort_order` — направление сортировки (`asc`, `desc`)

**Example:**
```http
GET /api/operator/appeals?status=new,in_progress&limit=50&sort_by=created_at&sort_order=desc
```

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "07af5e27-...",
      "client": {
        "id": "f08a1543-...",
        "name": "Иван Петров",
        "phone": "+79001234567"
      },
      "channel": "telegram",
      "appeal_type": "repair",
      "stage": "information_gathering",
      "status": "in_progress",
      "device": {
        "brand": "Apple",
        "model": "iPhone 14"
      },
      "issue": "Батарея",
      "unread_count": 3,
      "last_message": {
        "text": "Здравствуйте! Можно заменить батарею?",
        "timestamp": "2025-11-23T10:30:00Z",
        "from": "client"
      },
      "assigned_operator_id": "7b3aac9e-...",
      "created_at": "2025-11-23T10:25:00Z",
      "updated_at": "2025-11-23T10:30:00Z"
    }
  ],
  "pagination": {
    "limit": 50,
    "offset": 0,
    "total": 145,
    "has_more": true
  }
}
```

---

### 2. Получение детальной информации о заявке

**Endpoint:** `GET /api/operator/appeals/:appeal_id`

**Path Parameters:**
- `appeal_id` — UUID заявки

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "07af5e27-...",
    "tenant_id": "a0eebc99-...",

    "client": {
      "id": "f08a1543-...",
      "name": "Иван Петров",
      "phone": "+79001234567",
      "email": "ivan@example.com",
      "telegram_id": "6416413182",
      "has_telegram": true,
      "has_whatsapp": false,
      "unsubscribed": false
    },

    "channel": "telegram",
    "external_chat_id": "6416413182",

    "appeal_type": "repair",
    "stage": "information_gathering",
    "status": "in_progress",

    "devices": [
      {
        "id": "dev-001",
        "brand_id": "brand-apple",
        "brand": "Apple",
        "model_id": "model-iphone14",
        "model": "iPhone 14",
        "color": "Чёрный",
        "condition": "Хорошее",
        "repairs": [
          {
            "id": "rep-001",
            "repair_type_id": "rt-battery",
            "repair_type": "Замена батареи",
            "category": "Аккумулятор",
            "description": "Батарея быстро разряжается",
            "symptoms": ["Быстрый разряд", "Перезагрузки"],
            "estimated_price": 3500
          }
        ]
      }
    ],

    "description": "Клиент жалуется на быстрый разряд батареи iPhone 14",
    "operator_notes": "Уточнить модификацию (128GB/256GB/512GB)",

    "assigned_operator_id": "7b3aac9e-...",
    "assigned_operator_name": "Иван Иванов",

    "utm": {
      "source": "yandex",
      "medium": "cpc",
      "campaign": "repair_battery",
      "content": "ad_group_1"
    },

    "qr_code": {
      "code": "BAT-2025-001234",
      "generated_at": "2025-11-23T11:00:00Z",
      "expires_at": "2025-11-26T11:00:00Z",
      "url": "https://crm.example.com/qr/BAT-2025-001234"
    },

    "visit": {
      "scheduled_at": "2025-11-24T14:00:00Z",
      "visited_at": null,
      "scanned_by_operator_id": null
    },

    "created_at": "2025-11-23T10:25:00Z",
    "updated_at": "2025-11-23T11:30:00Z"
  }
}
```

**Важно:**
- Если заявка простая (1 устройство, 1 неисправность), поля `brand_id`, `model_id`, `issue_id` заполнены напрямую в таблице `appeals`
- Если заявка сложная (мультизаявка), используется массив `devices` с вложенными `repairs`
- API автоматически определяет тип заявки и возвращает унифицированный формат

---

### 3. История сообщений заявки

**Endpoint:** `GET /api/operator/appeals/:appeal_id/messages`

**Query Parameters:**
- `limit` — количество сообщений (по умолчанию 50)
- `before` — ID сообщения для пагинации (загрузка истории)
- `after` — ID сообщения для пагинации (новые сообщения)

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": "msg-001",
      "appeal_id": "07af5e27-...",
      "message_type": "client",
      "message_text": "Здравствуйте! Можно заменить батарею на iPhone 14?",
      "channel": "telegram",
      "media": [],
      "voice_url": null,
      "transcript": null,
      "ai_entities": {
        "brand": "Apple",
        "model": "iPhone 14",
        "issue": "Батарея"
      },
      "sent_by_operator_id": null,
      "created_at": "2025-11-23T10:25:00Z"
    },
    {
      "id": "msg-002",
      "appeal_id": "07af5e27-...",
      "message_type": "operator",
      "message_text": "Здравствуйте! Да, конечно. Замена батареи на iPhone 14 стоит 3500 руб. Вам удобно приехать завтра?",
      "channel": "telegram",
      "media": [],
      "voice_url": null,
      "transcript": null,
      "sent_by_operator_id": "7b3aac9e-...",
      "sent_by_operator_name": "Иван Иванов",
      "created_at": "2025-11-23T10:30:00Z"
    }
  ],
  "pagination": {
    "has_more_before": false,
    "has_more_after": false
  }
}
```

---

### 4. Отправка сообщения клиенту

**Endpoint:** `POST /api/operator/appeals/:appeal_id/send-message`

**Request:**
```json
{
  "message_text": "Здравствуйте! Да, конечно. Замена батареи стоит 3500 руб.",
  "media": [
    {
      "type": "image",
      "url": "https://example.com/images/battery.jpg"
    }
  ]
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "message_id": "msg-003",
    "sent_at": "2025-11-23T10:35:00Z",
    "channel": "telegram",
    "status": "sent"
  }
}
```

**Логика:**
1. Получение `appeal_id`, `channel`, `external_chat_id` из БД
2. Сохранение сообщения в `messages_history`
3. Вызов соответствующего `BAT OUT` workflow (Telegram/WhatsApp/VK/Avito/Instagram)
4. Отправка через API канала
5. Возврат статуса

---

### 5. Обновление заявки (простая структура)

**Endpoint:** `PUT /api/operator/appeals/:appeal_id`

**Request:**
```json
{
  "stage": "price_sent",
  "status": "in_progress",
  "brand_id": "brand-apple",
  "model_id": "model-iphone14",
  "issue_id": "issue-battery",
  "repair_type_id": "rt-battery",
  "description": "Обновлённое описание",
  "operator_notes": "Клиент согласен на замену",
  "estimated_price": 3500
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": "07af5e27-...",
    "updated_at": "2025-11-23T10:40:00Z"
  }
}
```

---

### 6. Создание/обновление мультизаявки

**Endpoint:** `PUT /api/operator/appeals/:appeal_id/devices`

**Request:**
```json
{
  "devices": [
    {
      "id": "dev-001",
      "brand_id": "brand-apple",
      "model_id": "model-iphone14",
      "color": "Чёрный",
      "condition": "Хорошее",
      "repairs": [
        {
          "id": "rep-001",
          "repair_type_id": "rt-battery",
          "category": "Аккумулятор",
          "description": "Батарея быстро разряжается",
          "symptoms": ["Быстрый разряд", "Перезагрузки"],
          "estimated_price": 3500
        },
        {
          "repair_type_id": "rt-display",
          "category": "Дисплей",
          "description": "Трещина на экране",
          "symptoms": ["Трещина"],
          "estimated_price": 7500
        }
      ]
    },
    {
      "brand_id": "brand-samsung",
      "model_id": "model-s23",
      "repairs": [
        {
          "repair_type_id": "rt-charging",
          "category": "Зарядка",
          "description": "Не заряжается",
          "estimated_price": 2500
        }
      ]
    }
  ]
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "appeal_id": "07af5e27-...",
    "devices_count": 2,
    "repairs_count": 3,
    "total_estimated_price": 13500,
    "updated_at": "2025-11-23T10:45:00Z"
  }
}
```

**Логика:**
1. Если `device.id` присутствует — обновление существующего устройства
2. Если `device.id` отсутствует — создание нового устройства
3. Аналогично для `repair.id`
4. Пересчёт общей стоимости
5. Обновление `updated_at` в таблице `appeals`

---

### 7. Изменение этапа воронки

**Endpoint:** `POST /api/operator/appeals/:appeal_id/change-stage`

**Request:**
```json
{
  "new_stage": "price_sent",
  "comment": "Отправлена цена клиенту"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "appeal_id": "07af5e27-...",
    "old_stage": "information_gathered",
    "new_stage": "price_sent",
    "changed_at": "2025-11-23T10:50:00Z"
  }
}
```

**Логика:**
1. Обновление `stage` в таблице `appeals`
2. Создание записи в `appeal_stage_transitions` для истории
3. Проверка триггеров для автоматических действий:
   - Если `new_stage == "price_sent"` → триггер отправки промо-материалов
   - Если `new_stage == "client_confirmed"` → генерация QR-кода
4. Возврат результата

---

### 8. Расчёт стоимости (подсказки из прайсов)

**Endpoint:** `GET /api/operator/pricing/suggestions`

**Query Parameters:**
- `brand_id` — ID бренда (обязательно)
- `model_id` — ID модели (обязательно)
- `repair_type_id` — ID типа ремонта (обязательно)
- `category` — категория ремонта (опционально)

**Example:**
```http
GET /api/operator/pricing/suggestions?brand_id=brand-apple&model_id=model-iphone14&repair_type_id=rt-battery
```

**Response:**
```json
{
  "success": true,
  "data": {
    "suggestions": [
      {
        "price_list_id": "pl-001",
        "supplier_name": "Поставщик А",
        "part_name": "Аккумулятор iPhone 14 (оригинал)",
        "part_price": 2500,
        "suggested_price": 3500,
        "margin_percent": 40,
        "in_stock": true,
        "delivery_days": 1
      },
      {
        "price_list_id": "pl-002",
        "supplier_name": "Поставщик Б",
        "part_name": "Аккумулятор iPhone 14 (копия)",
        "part_price": 1500,
        "suggested_price": 2500,
        "margin_percent": 67,
        "in_stock": true,
        "delivery_days": 0
      }
    ],
    "default_suggestion": {
      "part_name": "Аккумулятор iPhone 14 (оригинал)",
      "suggested_price": 3500
    }
  }
}
```

**Логика:**
1. Поиск в таблице `price_mappings` по `brand_id`, `model_id`, `repair_type_id`
2. Получение списка запчастей из `price_list_items`
3. Расчёт рекомендуемой цены с учётом наценки из `tenant_configs`
4. Сортировка по приоритету (оригинал > копия, наличие > доставка)
5. Возврат топ-5 вариантов

---

### 9. Генерация QR-кода

**Endpoint:** `POST /api/operator/appeals/:appeal_id/generate-qr`

**Request:**
```json
{
  "ttl_hours": 72
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "qr_code": "BAT-2025-001234",
    "qr_image_url": "https://n8n.n8nsrv.ru/qr/BAT-2025-001234.png",
    "generated_at": "2025-11-23T11:00:00Z",
    "expires_at": "2025-11-26T11:00:00Z"
  }
}
```

**Логика:**
1. Генерация уникального кода формата `BAT-{year}-{number}`
2. Создание QR-изображения через библиотеку `qrcode`
3. Сохранение в `appeals.qr_code`, `appeals.qr_generated_at`, `appeals.qr_expires_at`
4. Возврат URL изображения

---

### 10. Сканирование QR-кода (Android-only)

**Endpoint:** `POST /api/operator/qr/scan`

**Request:**
```json
{
  "qr_code": "BAT-2025-001234"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "appeal_id": "07af5e27-...",
    "client_name": "Иван Петров",
    "devices": [
      {
        "brand": "Apple",
        "model": "iPhone 14",
        "repairs": ["Замена батареи"]
      }
    ],
    "total_price": 3500,
    "scanned_at": "2025-11-24T14:05:00Z"
  }
}
```

**Логика:**
1. Поиск заявки по `qr_code`
2. Проверка срока действия (`qr_expires_at > NOW()`)
3. Обновление `stage` → `"client_arrived"`
4. Сохранение `visit.visited_at`, `visit.scanned_by_operator_id`
5. Создание записи в `appeal_stage_transitions`
6. Возврат данных заявки

---

### 11. Отправка промо-материалов

**Endpoint:** `POST /api/operator/appeals/:appeal_id/send-promo`

**Request:**
```json
{
  "promo_material_id": "promo-001"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "promo_material_id": "promo-001",
    "sent_at": "2025-11-23T11:10:00Z",
    "channel": "telegram"
  }
}
```

**Логика:**
1. Получение промо-материала из `promo_materials`
2. Формирование сообщения с медиа
3. Вызов `BAT Promo Sender` workflow
4. Сохранение записи в `promo_history`
5. Отправка через соответствующий `BAT OUT` workflow

---

## Endpoints для Android-приложения

### 1. Голосовой ответ оператора (Android SpeechRecognizer)

**Endpoint:** `POST /api/operator/voice/format-response`

**Request:**
```json
{
  "appeal_id": "07af5e27-...",
  "raw_text": "да конечно замена батареи три пять завтра можете приехать",
  "context": {
    "client_name": "Иван",
    "brand": "Apple",
    "model": "iPhone 14",
    "issue": "Батарея"
  }
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "formatted_text": "Здравствуйте, Иван! Да, конечно. Замена батареи на iPhone 14 стоит 3500 рублей. Вы можете приехать к нам завтра. Удобное время?",
    "original_text": "да конечно замена батареи три пять завтра можете приехать"
  }
}
```

**Логика:**
1. Получение `raw_text` от Android SpeechRecognizer
2. Отправка запроса к AI (Claude) с промптом:
   ```
   Ты помощник оператора сервисного центра. Преобразуй неформальный голосовой ответ
   оператора в грамотный текст для отправки клиенту.

   Контекст: {context}
   Голосовой ответ: {raw_text}

   Требования:
   - Грамотный русский язык
   - Вежливый тон
   - Структурированность
   - Обращение к клиенту по имени
   ```
3. Получение отформатированного текста
4. Возврат клиенту для отправки

**Важно:** Текст НЕ отправляется автоматически! Оператор видит отформатированный вариант и может редактировать перед отправкой.

---

### 2. Транскрибация голосового сообщения

**Endpoint:** `POST /api/operator/voice/transcribe`

**Request:**
```json
{
  "audio_url": "https://api.telegram.org/file/bot.../voice.ogg",
  "format": "ogg",
  "appeal_id": "07af5e27-..."
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "transcript": "Здравствуйте, можно заменить батарею на айфоне четырнадцать?",
    "duration_seconds": 5.3,
    "language": "ru",
    "confidence": 0.95,
    "ai_entities": {
      "brand": "Apple",
      "model": "iPhone 14",
      "issue": "Батарея"
    }
  }
}
```

**Логика:**
1. Загрузка аудио файла
2. Транскрибация через Whisper API (существующий)
3. Извлечение сущностей через AI Tools
4. Сохранение транскрипта в `voice_transcriptions`
5. Обновление заявки с новыми сущностями (если найдены)

---

### 3. Запись телефонного звонка (Android InCallService)

**Endpoint:** `POST /api/operator/calls/upload-recording`

**Request:**
```json
{
  "appeal_id": "07af5e27-...",
  "call_direction": "incoming",
  "phone_number": "+79001234567",
  "duration_seconds": 180,
  "recording_url": "https://storage.example.com/recordings/call_001.m4a"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "call_id": "call-001",
    "transcript": "Здравствуйте! Я хотел узнать сколько стоит замена батареи на iPhone 14...",
    "ai_entities": {
      "brand": "Apple",
      "model": "iPhone 14",
      "issue": "Батарея"
    },
    "processed_at": "2025-11-23T11:20:00Z"
  }
}
```

**Логика:**
1. Сохранение URL записи в `voice_transcriptions`
2. Вызов `BAT Voice Processor` workflow
3. Транскрибация через Whisper
4. Извлечение сущностей через AI Tools
5. Автоматическое обновление заявки
6. Возврат результата

---

### 4. Загрузка медиа (фото, видео)

**Endpoint:** `POST /api/operator/media/upload`

**Request:** (multipart/form-data)
```
appeal_id: 07af5e27-...
file: <binary_file>
media_type: image
caption: Фото устройства клиента
```

**Response:**
```json
{
  "success": true,
  "data": {
    "media_id": "media-001",
    "url": "https://storage.example.com/media/07af5e27.../image_001.jpg",
    "thumbnail_url": "https://storage.example.com/media/07af5e27.../image_001_thumb.jpg",
    "media_type": "image",
    "uploaded_at": "2025-11-23T11:25:00Z"
  }
}
```

---

### 5. Push-уведомления (проверка статуса)

**Endpoint:** `GET /api/operator/notifications/status`

**Response:**
```json
{
  "success": true,
  "data": {
    "fcm_registered": true,
    "last_notification_at": "2025-11-23T11:30:00Z",
    "unread_count": 5
  }
}
```

---

## Endpoints для workflows

### 1. Webhook для входящих сообщений

**Endpoint:** `POST /webhook/message/incoming`

**Request:**
```json
{
  "channel": "telegram",
  "external_chat_id": "6416413182",
  "message_text": "Здравствуйте! Можно заменить батарею на iPhone 14?",
  "message_id": "msg_ext_001",
  "timestamp": "2025-11-23T10:25:00Z",
  "media": []
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "appeal_id": "07af5e27-...",
    "client_id": "f08a1543-...",
    "processed": true
  }
}
```

**Логика:**
Вызов `BAT Message Router` workflow:
1. Определение/создание клиента
2. Определение/создание заявки
3. Сохранение сообщения в `messages_history`
4. Вызов `BAT Appeal Router` для AI обработки
5. Отправка Push-уведомления оператору

---

### 2. Webhook для статуса доставки сообщения

**Endpoint:** `POST /webhook/message/delivery-status`

**Request:**
```json
{
  "message_id": "msg-003",
  "status": "delivered",
  "timestamp": "2025-11-23T10:35:05Z"
}
```

**Response:**
```json
{
  "success": true
}
```

---

### 3. AI обработка сообщения (внутренний endpoint)

**Endpoint:** `POST /webhook/ai/process-message`

**Request:**
```json
{
  "appeal_id": "07af5e27-...",
  "message_text": "Здравствуйте! Можно заменить батарею на iPhone 14?",
  "context": {
    "previous_messages": [],
    "existing_entities": {}
  }
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "appeal_type": "repair",
    "brand": "Apple",
    "model": "iPhone 14",
    "issues": ["Батарея"],
    "category": "Аккумулятор",
    "confidence": 0.95,
    "needs_clarification": false
  }
}
```

---

## Endpoints для интеграций

### 1. Синхронизация с LiveSklad

**Endpoint:** `POST /api/integrations/livesklad/sync-order`

**Request:**
```json
{
  "appeal_id": "07af5e27-...",
  "action": "create_order"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "livesklad_order_id": "LS-12345",
    "status": "created",
    "synced_at": "2025-11-23T11:40:00Z"
  }
}
```

---

### 2. Синхронизация с RemOnline

**Endpoint:** `POST /api/integrations/remonline/sync-order`

**Request:**
```json
{
  "appeal_id": "07af5e27-...",
  "action": "create_order"
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "remonline_order_id": "RO-67890",
    "status": "created",
    "synced_at": "2025-11-23T11:45:00Z"
  }
}
```

---

### 3. Экспорт для ремаркетинга

**Endpoint:** `POST /api/integrations/remarketing/export`

**Request:**
```json
{
  "platform": "yandex_direct",
  "filter": {
    "stage": "rejected",
    "date_from": "2025-11-01",
    "date_to": "2025-11-23"
  }
}
```

**Response:**
```json
{
  "success": true,
  "data": {
    "export_url": "https://storage.example.com/exports/yandex_direct_20251123.csv",
    "records_count": 145,
    "exported_at": "2025-11-23T11:50:00Z"
  }
}
```

**Логика:**
1. Выборка заявок по фильтру
2. Формирование CSV с полями: `email`, `phone`, `utm_source`, `utm_campaign`
3. Загрузка в storage
4. Возврат URL файла

---

## Коды ошибок

### HTTP Status Codes

- `200 OK` — успешный запрос
- `201 Created` — ресурс создан
- `400 Bad Request` — некорректные данные
- `401 Unauthorized` — токен отсутствует или невалиден
- `403 Forbidden` — нет доступа (multi-tenant изоляция)
- `404 Not Found` — ресурс не найден
- `422 Unprocessable Entity` — ошибка валидации
- `429 Too Many Requests` — превышен rate limit
- `500 Internal Server Error` — внутренняя ошибка сервера

### Коды ошибок приложения

| Код | Описание |
|:----|:---------|
| `AUTH_FAILED` | Неверные credentials |
| `TOKEN_EXPIRED` | Токен истёк |
| `TOKEN_INVALID` | Токен невалиден |
| `TENANT_NOT_FOUND` | Тенант не найден |
| `APPEAL_NOT_FOUND` | Заявка не найдена |
| `CLIENT_NOT_FOUND` | Клиент не найден |
| `VALIDATION_ERROR` | Ошибка валидации данных |
| `DEVICE_LIMIT_EXCEEDED` | Превышен лимит устройств (1 mobile + 1 desktop) |
| `QR_EXPIRED` | QR-код истёк |
| `QR_ALREADY_SCANNED` | QR-код уже отсканирован |
| `PROMO_NOT_FOUND` | Промо-материал не найден |
| `PRICE_LIST_NOT_FOUND` | Прайс-лист не найден |
| `INTEGRATION_ERROR` | Ошибка интеграции с внешним сервисом |
| `MEDIA_UPLOAD_FAILED` | Ошибка загрузки медиа |
| `VOICE_TRANSCRIPTION_FAILED` | Ошибка транскрибации голоса |
| `AI_PROCESSING_FAILED` | Ошибка AI обработки |
| `RATE_LIMIT_EXCEEDED` | Превышен лимит запросов |

### Пример ошибки

```json
{
  "success": false,
  "error": {
    "code": "APPEAL_NOT_FOUND",
    "message": "Appeal with ID 07af5e27-... not found",
    "details": {
      "appeal_id": "07af5e27-...",
      "tenant_id": "a0eebc99-..."
    },
    "timestamp": "2025-11-23T12:00:00Z"
  }
}
```

---

## Rate Limiting

### Лимиты по умолчанию

- **Авторизация:** 10 запросов/минуту на IP
- **API операторов:** 100 запросов/минуту на operator_id
- **Webhooks:** 1000 запросов/минуту на tenant_id
- **Медиа загрузка:** 20 файлов/минуту на operator_id

### Заголовки Rate Limit

```http
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1700000060
```

### Превышение лимита

```json
{
  "success": false,
  "error": {
    "code": "RATE_LIMIT_EXCEEDED",
    "message": "Rate limit exceeded. Try again in 30 seconds.",
    "retry_after": 30
  }
}
```

---

## Идемпотентность

### X-Idempotency-Key

Для критичных операций (отправка сообщений, создание заявок) поддерживается идемпотентность:

**Request:**
```http
POST /api/operator/appeals/07af5e27-.../send-message
X-Idempotency-Key: 550e8400-e29b-41d4-a716-446655440000
```

**Логика:**
1. Проверка существования `idempotency_key` в БД
2. Если ключ найден — возврат кешированного ответа
3. Если ключ новый — выполнение операции и сохранение результата
4. TTL ключа: 24 часа

---

## Webhooks (для внешних систем)

### Регистрация webhook

**Endpoint:** `POST /api/webhooks/register`

**Request:**
```json
{
  "event": "appeal.stage_changed",
  "url": "https://external-system.com/webhook/appeals",
  "secret": "webhook_secret_key"
}
```

### События

- `appeal.created` — создана новая заявка
- `appeal.stage_changed` — изменён этап воронки
- `appeal.completed` — заявка завершена
- `message.received` — получено новое сообщение
- `qr.scanned` — QR-код отсканирован

### Формат webhook

**POST к зарегистрированному URL:**
```json
{
  "event": "appeal.stage_changed",
  "timestamp": "2025-11-23T12:00:00Z",
  "data": {
    "appeal_id": "07af5e27-...",
    "old_stage": "information_gathered",
    "new_stage": "price_sent"
  },
  "signature": "sha256=..."
}
```

---

**Конец спецификации API** — все endpoints реализуются через n8n workflows.
