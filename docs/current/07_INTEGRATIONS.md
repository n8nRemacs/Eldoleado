# BatteryCRM - Интеграции: Техническая спецификация

**Версия:** 2.0
**Дата:** 2025-11-23
**Статус:** Актуальная спецификация

---

## Содержание

1. [Обзор интеграций](#обзор-интеграций)
2. [Каналы коммуникации](#каналы-коммуникации)
3. [CRM системы](#crm-системы)
4. [Рекламные платформы](#рекламные-платформы)
5. [AI сервисы](#ai-сервисы)
6. [Push-уведомления](#push-уведомления)
7. [Хранилище файлов](#хранилище-файлов)

---

## Обзор интеграций

### Типы интеграций

BatteryCRM интегрируется с внешними сервисами для:
1. **Коммуникации** — приём и отправка сообщений клиентам
2. **CRM** — синхронизация заказов с внешними системами
3. **Реклама** — экспорт аудиторий для ремаркетинга
4. **AI** — обработка сообщений, транскрибация, извлечение сущностей
5. **Push** — уведомления операторам
6. **Storage** — хранение медиа файлов

### Архитектура интеграций

```
┌────────────────────────────────────────────────┐
│           BatteryCRM (n8n workflows)           │
└────────────────────────────────────────────────┘
                      ↓
    ┌─────────────────┼─────────────────┐
    ↓                 ↓                 ↓
┌─────────┐      ┌─────────┐      ┌─────────┐
│ Channels│      │   CRM   │      │   Ads   │
│ WhatsApp│      │LiveSklad│      │ Yandex  │
│Telegram │      │RemOnline│      │VK Ads   │
│   VK    │      │         │      │Google   │
│  Avito  │      │         │      │         │
│Instagram│      │         │      │         │
└─────────┘      └─────────┘      └─────────┘
```

---

## Каналы коммуникации

### 1. WhatsApp Business API

**Назначение:** Приём и отправка сообщений через WhatsApp

**Провайдер:** WhatsApp Business API (официальный) или сторонние провайдеры (WABA, Green API)

#### Конфигурация

**Credentials:**
```json
{
  "phone_number_id": "123456789",
  "business_account_id": "987654321",
  "access_token": "EAAxxxxx...",
  "webhook_verify_token": "my_secure_token_123",
  "api_version": "v18.0"
}
```

#### Входящие сообщения (Webhook)

**Endpoint:** `POST /webhook/whatsapp/incoming`

**Request от WhatsApp:**
```json
{
  "object": "whatsapp_business_account",
  "entry": [
    {
      "id": "123456789",
      "changes": [
        {
          "value": {
            "messaging_product": "whatsapp",
            "metadata": {
              "display_phone_number": "79001234567",
              "phone_number_id": "123456789"
            },
            "contacts": [
              {
                "profile": {
                  "name": "Иван Петров"
                },
                "wa_id": "79009876543"
              }
            ],
            "messages": [
              {
                "from": "79009876543",
                "id": "wamid.xxx",
                "timestamp": "1700000000",
                "type": "text",
                "text": {
                  "body": "Здравствуйте! Можно заменить батарею на iPhone 14?"
                }
              }
            ]
          }
        }
      ]
    }
  ]
}
```

**Обработка в n8n (BAT IN WhatsApp):**
1. Webhook Trigger — получение сообщения
2. Extract Data — извлечение полей
3. Call BAT Message Router — передача в основной роутер

#### Исходящие сообщения

**API:** `POST https://graph.facebook.com/v18.0/{phone_number_id}/messages`

**Request:**
```json
{
  "messaging_product": "whatsapp",
  "recipient_type": "individual",
  "to": "79009876543",
  "type": "text",
  "text": {
    "body": "Здравствуйте, Иван! Да, конечно. Замена батареи на iPhone 14 стоит 3500 рублей."
  }
}
```

**Headers:**
```http
Authorization: Bearer EAAxxxxx...
Content-Type: application/json
```

**Отправка медиа (изображение):**
```json
{
  "messaging_product": "whatsapp",
  "recipient_type": "individual",
  "to": "79009876543",
  "type": "image",
  "image": {
    "link": "https://example.com/images/battery.jpg",
    "caption": "Аккумулятор для iPhone 14"
  }
}
```

**Обработка в n8n (BAT OUT WhatsApp):**
1. Webhook Trigger — получение запроса от BAT Message Router
2. Prepare Message — формирование тела запроса
3. HTTP Request — отправка через WhatsApp API
4. Update Message Status — обновление статуса в БД

---

### 2. Telegram Bot API

**Назначение:** Приём и отправка сообщений через Telegram

**Провайдер:** Telegram Bot API (официальный, бесплатный)

#### Конфигурация

**Создание бота:**
1. Написать @BotFather в Telegram
2. Команда `/newbot`
3. Получить `bot_token`

**Credentials:**
```json
{
  "bot_token": "8411509872:AAFxxx...",
  "webhook_url": "https://n8n.n8nsrv.ru/webhook/telegram/incoming"
}
```

**Установка webhook:**
```bash
curl -X POST "https://api.telegram.org/bot8411509872:AAFxxx.../setWebhook" \
  -H "Content-Type: application/json" \
  -d '{"url": "https://n8n.n8nsrv.ru/webhook/telegram/incoming"}'
```

#### Входящие сообщения

**Endpoint:** `POST /webhook/telegram/incoming`

**Request от Telegram:**
```json
{
  "update_id": 123456789,
  "message": {
    "message_id": 1234,
    "from": {
      "id": 6416413182,
      "is_bot": false,
      "first_name": "Иван",
      "last_name": "Петров",
      "username": "ivan_petrov"
    },
    "chat": {
      "id": 6416413182,
      "first_name": "Иван",
      "last_name": "Петров",
      "username": "ivan_petrov",
      "type": "private"
    },
    "date": 1700000000,
    "text": "Здравствуйте! Можно заменить батарею на iPhone 14?"
  }
}
```

**Голосовое сообщение:**
```json
{
  "update_id": 123456790,
  "message": {
    "message_id": 1235,
    "from": { "id": 6416413182, "first_name": "Иван" },
    "chat": { "id": 6416413182, "type": "private" },
    "date": 1700000010,
    "voice": {
      "duration": 5,
      "mime_type": "audio/ogg",
      "file_id": "AwACAgIAAxkBAAIxxx...",
      "file_unique_id": "AgADxxx...",
      "file_size": 12345
    }
  }
}
```

#### Исходящие сообщения

**API:** `POST https://api.telegram.org/bot{bot_token}/sendMessage`

**Текстовое сообщение:**
```json
{
  "chat_id": 6416413182,
  "text": "Здравствуйте, Иван! Да, конечно. Замена батареи на iPhone 14 стоит 3500 рублей.",
  "parse_mode": "Markdown"
}
```

**Сообщение с фото:**
```bash
POST https://api.telegram.org/bot{bot_token}/sendPhoto
{
  "chat_id": 6416413182,
  "photo": "https://example.com/images/battery.jpg",
  "caption": "Аккумулятор для iPhone 14"
}
```

**Получение файла голосового сообщения:**
```bash
# 1. Получить file_path
GET https://api.telegram.org/bot{bot_token}/getFile?file_id={file_id}

# Response:
{
  "ok": true,
  "result": {
    "file_id": "AwACAgIAAxkBAAIxxx...",
    "file_unique_id": "AgADxxx...",
    "file_size": 12345,
    "file_path": "voice/file_123.oga"
  }
}

# 2. Скачать файл
GET https://api.telegram.org/file/bot{bot_token}/voice/file_123.oga
```

---

### 3. VK API

**Назначение:** Приём и отправка сообщений через VK (ВКонтакте)

**Провайдер:** VK API (официальный)

#### Конфигурация

**Создание приложения:**
1. Перейти на https://vk.com/apps?act=manage
2. Создать приложение типа "Standalone-приложение"
3. Получить `access_token`

**Credentials:**
```json
{
  "group_id": "123456789",
  "access_token": "vk1.a.xxx...",
  "api_version": "5.131",
  "secret_key": "my_secret_key"
}
```

**Настройка Long Poll сервера:**
```
Настройки → Работа с API → Long Poll API → Включить
Типы событий: Входящие сообщения
```

#### Входящие сообщения (Long Poll)

**n8n workflow для Long Poll:**
```
Cron Trigger (каждые 5 секунд)
  ↓
HTTP Request: getLongPollServer
  ↓
HTTP Request: Listen to Long Poll
  ↓
Parse Events
  ↓
Call BAT Message Router
```

**API: getLongPollServer**
```bash
GET https://api.vk.com/method/groups.getLongPollServer?group_id=123456789&access_token={token}&v=5.131

Response:
{
  "response": {
    "key": "xxx",
    "server": "https://lp.vk.com/wh123456789",
    "ts": "1234567890"
  }
}
```

**Событие входящего сообщения:**
```json
{
  "type": "message_new",
  "object": {
    "message": {
      "id": 123,
      "date": 1700000000,
      "from_id": 987654321,
      "text": "Здравствуйте! Можно заменить батарею?",
      "peer_id": 987654321
    }
  },
  "group_id": 123456789
}
```

#### Исходящие сообщения

**API:** `POST https://api.vk.com/method/messages.send`

**Request:**
```http
POST https://api.vk.com/method/messages.send
  ?user_id=987654321
  &message=Здравствуйте! Замена батареи стоит 3500 рублей.
  &random_id=12345678
  &access_token={token}
  &v=5.131
```

**С фото:**
```http
# 1. Загрузить фото
POST https://api.vk.com/method/photos.getMessagesUploadServer
  ?peer_id=987654321
  &access_token={token}
  &v=5.131

# 2. Отправить файл на upload_url
POST {upload_url}
  -F photo=@battery.jpg

# 3. Сохранить фото
POST https://api.vk.com/method/photos.saveMessagesPhoto
  ?photo={photo}
  &server={server}
  &hash={hash}
  &access_token={token}
  &v=5.131

# 4. Отправить сообщение с фото
POST https://api.vk.com/method/messages.send
  ?user_id=987654321
  &message=Аккумулятор для iPhone 14
  &attachment=photo{owner_id}_{photo_id}
  &random_id=12345679
  &access_token={token}
  &v=5.131
```

---

### 4. Avito Messenger API

**Назначение:** Приём и отправка сообщений через Avito

**Провайдер:** Avito API (официальный)

#### Конфигурация

**Регистрация приложения:**
1. Перейти на https://developers.avito.ru
2. Создать приложение
3. Получить `client_id` и `client_secret`

**Credentials:**
```json
{
  "client_id": "avito_client_id",
  "client_secret": "avito_client_secret",
  "access_token": "xxx...",
  "refresh_token": "yyy..."
}
```

#### Получение токена

```bash
POST https://api.avito.ru/token
  -H "Content-Type: application/x-www-form-urlencoded"
  -d "grant_type=client_credentials&client_id={client_id}&client_secret={client_secret}"

Response:
{
  "access_token": "xxx...",
  "expires_in": 86400,
  "token_type": "Bearer"
}
```

#### Входящие сообщения (Webhook)

**Endpoint:** `POST /webhook/avito/incoming`

**Request от Avito:**
```json
{
  "type": "message",
  "payload": {
    "value": {
      "chat_id": "chat_123456",
      "message_id": "msg_789012",
      "user_id": "user_345678",
      "author_id": "user_345678",
      "created": "2025-11-23T10:25:00Z",
      "direction": "in",
      "type": "text",
      "content": {
        "text": "Здравствуйте! Сколько стоит замена батареи на iPhone 14?"
      }
    }
  }
}
```

#### Исходящие сообщения

**API:** `POST https://api.avito.ru/messenger/v2/accounts/{user_id}/chats/{chat_id}/messages`

**Request:**
```json
{
  "message": {
    "type": "text",
    "text": "Здравствуйте! Замена батареи на iPhone 14 стоит 3500 рублей."
  }
}
```

**Headers:**
```http
Authorization: Bearer {access_token}
Content-Type: application/json
```

---

### 5. Instagram Direct Messages (NEW)

**Назначение:** Приём и отправка сообщений через Instagram Direct

**Провайдер:** Instagram Graph API (Facebook)

#### Конфигурация

**Требования:**
1. Instagram Business Account
2. Facebook Page, связанная с Instagram
3. Instagram Graph API доступ

**Credentials:**
```json
{
  "instagram_account_id": "123456789",
  "page_id": "987654321",
  "access_token": "EAAxxxxx..."
}
```

#### Входящие сообщения (Webhook)

**Endpoint:** `POST /webhook/instagram/incoming`

**Подписка на webhook:**
```bash
POST https://graph.facebook.com/v18.0/{page_id}/subscribed_apps
  ?subscribed_fields=messages,messaging_postbacks
  &access_token={access_token}
```

**Request от Instagram:**
```json
{
  "object": "instagram",
  "entry": [
    {
      "id": "123456789",
      "time": 1700000000,
      "messaging": [
        {
          "sender": {
            "id": "987654321"
          },
          "recipient": {
            "id": "123456789"
          },
          "timestamp": 1700000000,
          "message": {
            "mid": "mid.xxx",
            "text": "Здравствуйте! Можно заменить батарею на iPhone 14?"
          }
        }
      ]
    }
  ]
}
```

#### Исходящие сообщения

**API:** `POST https://graph.facebook.com/v18.0/me/messages`

**Request:**
```json
{
  "recipient": {
    "id": "987654321"
  },
  "message": {
    "text": "Здравствуйте! Замена батареи на iPhone 14 стоит 3500 рублей."
  }
}
```

**Headers:**
```http
Authorization: Bearer {access_token}
Content-Type: application/json
```

---

## CRM системы

### 1. LiveSklad

**Назначение:** Синхронизация заказов с LiveSklad CRM

**API документация:** https://api.livesklad.com/docs

#### Конфигурация

**Credentials:**
```json
{
  "api_key": "livesklad_api_key_xxx",
  "shop_id": "12345",
  "base_url": "https://api.livesklad.com/v1"
}
```

#### Создание заказа

**API:** `POST /v1/orders`

**Request:**
```json
{
  "shop_id": "12345",
  "client": {
    "name": "Иван Петров",
    "phone": "+79001234567",
    "email": "ivan@example.com"
  },
  "device": {
    "brand": "Apple",
    "model": "iPhone 14",
    "imei": "123456789012345",
    "color": "Чёрный",
    "condition": "Хорошее"
  },
  "repairs": [
    {
      "repair_type": "Замена батареи",
      "description": "Батарея быстро разряжается",
      "price": 3500,
      "parts": [
        {
          "name": "Аккумулятор iPhone 14",
          "quantity": 1,
          "price": 2500
        }
      ]
    }
  ],
  "status": "new",
  "notes": "Клиент согласился на ремонт"
}
```

**Response:**
```json
{
  "success": true,
  "order_id": "LS-12345",
  "status": "created",
  "created_at": "2025-11-23T10:30:00Z"
}
```

**Сохранение в БД (crm_sync_history):**
```sql
INSERT INTO crm_sync_history (
  tenant_id, appeal_id, crm_type, external_order_id, sync_status, synced_at
)
VALUES (
  'a0eebc99-...', '07af5e27-...', 'livesklad', 'LS-12345', 'success', NOW()
)
```

#### Получение статуса заказа

**API:** `GET /v1/orders/{order_id}`

**Response:**
```json
{
  "order_id": "LS-12345",
  "status": "in_progress",
  "repair_status": "parts_ordered",
  "updated_at": "2025-11-23T14:00:00Z"
}
```

**Обновление заявки в BatteryCRM:**
```sql
UPDATE appeals
SET
  crm_status = 'in_progress',
  crm_repair_status = 'parts_ordered',
  updated_at = NOW()
WHERE id = '07af5e27-...'
```

#### n8n workflow (BAT CRM Sync)

```
Schedule Trigger (каждые 15 минут)
  ↓
PostgreSQL: Get Appeals with CRM sync pending
  ↓
Loop over Appeals
  ↓
HTTP Request: Create Order in LiveSklad
  ↓
PostgreSQL: Save sync history
  ↓
PostgreSQL: Update appeal status
```

---

### 2. RemOnline

**Назначение:** Синхронизация заказов с RemOnline CRM

**API документация:** https://api.remonline.ru/docs

#### Конфигурация

**Credentials:**
```json
{
  "api_key": "remonline_api_key_xxx",
  "company_id": "67890",
  "base_url": "https://api.remonline.ru/v1"
}
```

#### Создание заказа

**API:** `POST /v1/orders`

**Request:**
```json
{
  "company_id": "67890",
  "client": {
    "name": "Иван Петров",
    "phone": "+79001234567"
  },
  "device": {
    "type": "smartphone",
    "brand": "Apple",
    "model": "iPhone 14"
  },
  "works": [
    {
      "name": "Замена батареи",
      "price": 3500,
      "quantity": 1
    }
  ],
  "status": "new"
}
```

**Response:**
```json
{
  "success": true,
  "order_id": "RO-67890",
  "order_number": "12345",
  "created_at": "2025-11-23T10:30:00Z"
}
```

---

## Рекламные платформы

### 1. Яндекс.Директ

**Назначение:** Экспорт аудиторий для ремаркетинга

**API:** Яндекс.Аудитории API

#### Создание сегмента

**API:** `POST https://api-audience.yandex.ru/v1/management/segments`

**Request:**
```json
{
  "segment": {
    "name": "BatteryCRM - Отказники (2025-11)",
    "segment_type": "uploaded",
    "hashed": false
  }
}
```

**Response:**
```json
{
  "segment": {
    "id": 12345678,
    "name": "BatteryCRM - Отказники (2025-11)",
    "status": "is_processed"
  }
}
```

#### Загрузка данных

**API:** `POST https://api-audience.yandex.ru/v1/management/segments/{segment_id}/data`

**Request (CSV):**
```csv
Email,Phone
ivan@example.com,79001234567
petr@example.com,79009876543
```

**Headers:**
```http
Authorization: Bearer {access_token}
Content-Type: text/csv
```

#### n8n workflow (BAT Remarketing Exporter)

```
Schedule Trigger (раз в день)
  ↓
PostgreSQL: Get Rejected Appeals (last 30 days)
  ↓
Format CSV (email, phone)
  ↓
HTTP Request: Create Yandex Audience Segment
  ↓
HTTP Request: Upload Data to Segment
  ↓
PostgreSQL: Log Export
```

---

### 2. VK Ads (myTarget)

**Назначение:** Экспорт аудиторий для ремаркетинга в VK

**API:** myTarget API

#### Создание аудитории

**API:** `POST https://target.my.com/api/v2/remarketing_users_lists.json`

**Request:**
```json
{
  "name": "BatteryCRM - Отказники",
  "type": "user_list"
}
```

#### Загрузка контактов

**API:** `POST https://target.my.com/api/v2/remarketing_users_lists/{list_id}/users.json`

**Request:**
```json
{
  "contacts": [
    {
      "type": "email",
      "value": "ivan@example.com"
    },
    {
      "type": "phone",
      "value": "79001234567"
    }
  ]
}
```

---

### 3. Google Ads (Customer Match)

**Назначение:** Экспорт аудиторий для Google Ads

**API:** Google Ads API

#### Создание User List

**API:** `POST https://googleads.googleapis.com/v14/customers/{customer_id}/userLists`

**Request:**
```json
{
  "userList": {
    "name": "BatteryCRM - Отказники",
    "membershipLifeSpan": 540,
    "crmBasedUserList": {
      "uploadKeyType": "CONTACT_INFO",
      "dataSourceType": "FIRST_PARTY"
    }
  }
}
```

#### Добавление контактов

**API:** `POST https://googleads.googleapis.com/v14/customers/{customer_id}/offlineUserDataJobs`

**Request:**
```json
{
  "job": {
    "type": "CUSTOMER_MATCH_USER_LIST",
    "customerMatchUserListMetadata": {
      "userList": "customers/{customer_id}/userLists/{user_list_id}"
    },
    "operations": [
      {
        "create": {
          "userIdentifiers": [
            {
              "hashedEmail": "sha256_hash_of_email"
            },
            {
              "hashedPhoneNumber": "sha256_hash_of_phone"
            }
          ]
        }
      }
    ]
  }
}
```

---

## AI сервисы

### 1. Claude AI (Anthropic)

**Назначение:** AI обработка сообщений, извлечение сущностей, оформление голосовых ответов

**API:** Anthropic API

#### Конфигурация

**Credentials:**
```json
{
  "api_key": "sk-ant-xxx...",
  "model": "claude-3-5-sonnet-20241022",
  "base_url": "https://api.anthropic.com"
}
```

#### Извлечение сущностей (AI Tool 4: Multi-Entity Extractor)

**API:** `POST https://api.anthropic.com/v1/messages`

**Request:**
```json
{
  "model": "claude-3-5-sonnet-20241022",
  "max_tokens": 1024,
  "tools": [
    {
      "name": "extract_entities",
      "description": "Извлечь устройства и неисправности из сообщения клиента",
      "input_schema": {
        "type": "object",
        "properties": {
          "devices": {
            "type": "array",
            "items": {
              "type": "object",
              "properties": {
                "brand": { "type": "string" },
                "model": { "type": "string" },
                "repairs": {
                  "type": "array",
                  "items": {
                    "type": "object",
                    "properties": {
                      "repair_type": { "type": "string" },
                      "category": { "type": "string" },
                      "description": { "type": "string" }
                    }
                  }
                }
              }
            }
          },
          "appeal_type": { "type": "string" }
        }
      }
    }
  ],
  "messages": [
    {
      "role": "user",
      "content": "У меня два телефона сломались. iPhone 14 не держит батарею и экран треснул. А ещё Samsung Galaxy S23 не заряжается."
    }
  ]
}
```

**Response:**
```json
{
  "id": "msg_xxx",
  "type": "message",
  "role": "assistant",
  "content": [
    {
      "type": "tool_use",
      "id": "tool_xxx",
      "name": "extract_entities",
      "input": {
        "appeal_type": "repair",
        "devices": [
          {
            "brand": "Apple",
            "model": "iPhone 14",
            "repairs": [
              {
                "repair_type": "Замена батареи",
                "category": "Аккумулятор",
                "description": "Не держит батарею"
              },
              {
                "repair_type": "Замена дисплея",
                "category": "Дисплей",
                "description": "Экран треснул"
              }
            ]
          },
          {
            "brand": "Samsung",
            "model": "Galaxy S23",
            "repairs": [
              {
                "repair_type": "Замена порта зарядки",
                "category": "Зарядка",
                "description": "Не заряжается"
              }
            ]
          }
        ]
      }
    }
  ]
}
```

#### Оформление голосового ответа

**Request:**
```json
{
  "model": "claude-3-5-sonnet-20241022",
  "max_tokens": 500,
  "messages": [
    {
      "role": "user",
      "content": "Ты помощник оператора сервисного центра. Преобразуй неформальный голосовой ответ оператора в грамотный текст для отправки клиенту.\n\nКонтекст:\n- Клиент: Иван\n- Устройство: iPhone 14\n- Проблема: Батарея\n\nГолосовой ответ: \"да конечно замена батареи три пять завтра можете приехать\"\n\nТребования:\n- Грамотный русский язык\n- Вежливый тон\n- Структурированность\n- Обращение к клиенту по имени"
    }
  ]
}
```

**Response:**
```json
{
  "id": "msg_yyy",
  "content": [
    {
      "type": "text",
      "text": "Здравствуйте, Иван! Да, конечно. Замена батареи на iPhone 14 стоит 3500 рублей. Вы можете приехать к нам завтра. Удобное время?"
    }
  ]
}
```

---

### 2. OpenAI Whisper (Транскрибация)

**Назначение:** Транскрибация голосовых сообщений и звонков

**API:** OpenAI API

#### Конфигурация

**Credentials:**
```json
{
  "api_key": "sk-xxx...",
  "model": "whisper-1"
}
```

#### Транскрибация

**API:** `POST https://api.openai.com/v1/audio/transcriptions`

**Request (multipart/form-data):**
```
file: <audio_file.ogg>
model: whisper-1
language: ru
```

**Response:**
```json
{
  "text": "Здравствуйте, можно заменить батарею на айфоне четырнадцать?"
}
```

---

## Push-уведомления

### Firebase Cloud Messaging (FCM)

**Назначение:** Push-уведомления операторам на Android/Desktop

#### Конфигурация

**Firebase Project:**
1. Создать проект на https://console.firebase.google.com
2. Получить `server_key` (Settings → Cloud Messaging)

**Credentials:**
```json
{
  "server_key": "AAAA...xxx",
  "sender_id": "123456789012"
}
```

#### Отправка уведомления

**API:** `POST https://fcm.googleapis.com/fcm/send`

**Request:**
```json
{
  "to": "{fcm_token_оператора}",
  "notification": {
    "title": "Новое сообщение от Ивана Петрова",
    "body": "Здравствуйте! Можно заменить батарею на iPhone 14?",
    "icon": "ic_notification",
    "sound": "default",
    "click_action": "OPEN_APPEAL"
  },
  "data": {
    "appeal_id": "07af5e27-...",
    "client_name": "Иван Петров",
    "type": "new_message"
  }
}
```

**Headers:**
```http
Authorization: key={server_key}
Content-Type: application/json
```

---

## Хранилище файлов

### AWS S3 / MinIO / Supabase Storage

**Назначение:** Хранение медиа файлов (фото, видео, голосовые, записи звонков)

#### Конфигурация (Supabase Storage)

**Credentials:**
```json
{
  "project_url": "https://supabase.n8nsrv.ru",
  "anon_key": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "service_role_key": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "bucket_name": "media"
}
```

#### Загрузка файла

**API:** `POST https://supabase.n8nsrv.ru/storage/v1/object/{bucket_name}/{file_path}`

**Request (multipart/form-data):**
```
file: <binary_file>
```

**Headers:**
```http
Authorization: Bearer {service_role_key}
Content-Type: multipart/form-data
```

**Response:**
```json
{
  "Key": "media/appeals/07af5e27-.../image_001.jpg",
  "Bucket": "media"
}
```

#### Получение публичного URL

**URL:** `https://supabase.n8nsrv.ru/storage/v1/object/public/media/appeals/07af5e27-.../image_001.jpg`

---

**Конец спецификации интеграций.**
