# CORE_NEW: API Контракты для Android v2

## Философия API

**Диалогоцентричный подход.** Главная сущность — диалог (elo_dialogs).
Устройства, проблемы, цены — это контекст диалога, хранящийся в JSONB.

```
Старый подход:  Клиент → Заявки → Устройства → Проблемы
Новый подход:   Клиент → Диалоги (с контекстом внутри)
```

---

## Базовый URL

```
https://45.144.177.128:8780/api/v2
```

## Аутентификация

```
Authorization: Bearer {jwt_token}
X-Tenant-ID: {tenant_uuid}
```

---

## 1. Диалоги (elo_dialogs)

### GET /dialogs — Список диалогов

**Главный экран приложения.** Показывает все активные разговоры.

**Query params:**
- `status` (string): "active" | "completed" | "all" (default: "active")
- `channel` (string): "telegram" | "whatsapp" | "vk" | "avito" | null
- `search` (string): поиск по имени клиента или контенту
- `page` (int, default: 1)
- `limit` (int, default: 20, max: 100)

**Response:**
```json
{
    "data": [
        {
            "id": "uuid",
            "client": {
                "id": "uuid",
                "name": "Иван Петров",
                "avatar_url": null
            },
            "channel": "telegram",
            "status": "active",

            "context": {
                "intent": "repair",
                "device": "iPhone 14 Pro",
                "issue": "разбит экран",
                "stage": "quoted",
                "price_quoted": 15000
            },

            "last_message": {
                "direction": "inbound",
                "content": "сколько по времени?",
                "created_at": "2024-12-09T10:30:00Z"
            },

            "unread_count": 2,
            "ai_suggestion": "Ремонт займёт 1 час. Запись на сегодня: 14:00, 16:00",

            "created_at": "2024-12-09T10:00:00Z",
            "last_message_at": "2024-12-09T10:30:00Z"
        }
    ],
    "pagination": {
        "page": 1,
        "limit": 20,
        "total": 45
    }
}
```

### GET /dialogs/{id} — Детали диалога

**Response:**
```json
{
    "id": "uuid",
    "tenant_id": "uuid",

    "client": {
        "id": "uuid",
        "name": "Иван Петров",
        "phone": "+79161234567",
        "channels": {
            "telegram": "123456789",
            "whatsapp": "79161234567"
        },
        "total_dialogs": 5,
        "completed_dialogs": 3
    },

    "channel": "telegram",
    "external_chat_id": "123456789",
    "status": "active",

    "context": {
        "intent": "repair",
        "device": {
            "brand": "Apple",
            "model": "iPhone 14 Pro",
            "color": "белый"
        },
        "issue": {
            "category": "замена экрана",
            "description": "разбит дисплей, тачскрин работает"
        },
        "stage": "quoted",
        "price_quoted": 15000,
        "price_final": null,
        "awaiting": "price_approval"
    },

    "assigned_operator": {
        "id": "uuid",
        "name": "Алексей"
    },

    "metadata": {
        "source": "organic",
        "utm_source": null
    },

    "created_at": "2024-12-09T10:00:00Z",
    "last_message_at": "2024-12-09T10:30:00Z"
}
```

### POST /dialogs — Создать диалог вручную

**Request:**
```json
{
    "client_id": "uuid",
    "channel": "telegram",
    "context": {
        "intent": "repair",
        "device": "iPhone 14 Pro",
        "issue": "не работает Face ID"
    }
}
```

### PATCH /dialogs/{id} — Обновить диалог

**Request:**
```json
{
    "status": "completed",
    "context": {
        "stage": "delivered",
        "price_final": 15000
    }
}
```

### PATCH /dialogs/{id}/context — Обновить только контекст

**Request:**
```json
{
    "stage": "in_progress",
    "price_quoted": 12000
}
```

---

## 2. Сообщения диалога (elo_events)

### GET /dialogs/{id}/messages — Сообщения диалога

**Query params:**
- `limit` (int, default: 50)
- `before` (datetime): пагинация — до этого времени
- `types` (string): "message" | "all" (включая системные события)

**Response:**
```json
{
    "data": [
        {
            "id": "uuid",
            "type": "message",
            "direction": "inbound",
            "content": "Здравствуйте, разбился экран на айфоне",
            "channel": "telegram",
            "created_at": "2024-12-09T10:00:00Z",
            "metadata": {
                "external_id": "12345"
            }
        },
        {
            "id": "uuid",
            "type": "message",
            "direction": "outbound",
            "content": "Добрый день! Какая модель iPhone?",
            "channel": "telegram",
            "created_at": "2024-12-09T10:01:00Z",
            "operator": {
                "id": "uuid",
                "name": "Алексей"
            }
        },
        {
            "id": "uuid",
            "type": "context_updated",
            "direction": null,
            "content": null,
            "created_at": "2024-12-09T10:02:00Z",
            "payload": {
                "field": "device",
                "old_value": null,
                "new_value": "iPhone 14 Pro",
                "source": "ai_extraction"
            }
        }
    ],
    "has_more": true
}
```

### POST /dialogs/{id}/messages — Отправить сообщение

**Request:**
```json
{
    "content": "Оригинальный дисплей 15000₽, копия 8000₽. Какой вариант?"
}
```

**Response:**
```json
{
    "id": "uuid",
    "sent": true,
    "external_message_id": "67890",
    "created_at": "2024-12-09T10:35:00Z"
}
```

---

## 3. Клиенты (elo_clients)

### GET /clients — Список клиентов

**Query params:**
- `search` (string): поиск по имени/телефону
- `has_active_dialogs` (bool): только с активными диалогами
- `page`, `limit`

**Response:**
```json
{
    "data": [
        {
            "id": "uuid",
            "name": "Иван Петров",
            "phone": "+79161234567",
            "channels": ["telegram", "whatsapp"],
            "active_dialogs_count": 1,
            "total_dialogs_count": 5,
            "last_contact_at": "2024-12-09T10:30:00Z"
        }
    ],
    "pagination": {...}
}
```

### GET /clients/{id} — Карточка клиента

**Response:**
```json
{
    "id": "uuid",
    "name": "Иван Петров",
    "phone": "+79161234567",

    "channels": {
        "telegram": "123456789",
        "whatsapp": "79161234567",
        "vk": null,
        "avito": null
    },

    "dialogs": [
        {
            "id": "uuid",
            "channel": "telegram",
            "status": "active",
            "context": {
                "intent": "repair",
                "device": "iPhone 14 Pro",
                "stage": "quoted"
            },
            "last_message_at": "2024-12-09T10:30:00Z"
        },
        {
            "id": "uuid",
            "channel": "telegram",
            "status": "completed",
            "context": {
                "intent": "repair",
                "device": "iPhone 13",
                "stage": "delivered",
                "price_final": 12000
            },
            "created_at": "2024-11-01T10:00:00Z"
        }
    ],

    "facts": [
        "предпочитает оригинальные запчасти",
        "работает рядом с ТЦ Европейский"
    ],

    "relations": [
        {
            "client_id": "uuid",
            "name": "Мария",
            "relation_type": "spouse"
        }
    ],

    "metrics": {
        "total_dialogs": 5,
        "completed_dialogs": 4,
        "total_spent": 45000,
        "first_contact_at": "2024-06-15T10:00:00Z",
        "last_contact_at": "2024-12-09T10:30:00Z"
    }
}
```

### POST /clients — Создать клиента

**Request:**
```json
{
    "name": "Новый клиент",
    "phone": "+79161234567",
    "telegram_id": "123456789"
}
```

### PATCH /clients/{id} — Обновить клиента

**Request:**
```json
{
    "name": "Иван Петрович Петров",
    "phone": "+79161234568"
}
```

---

## 4. AI Подсказки (elo_ai_suggestions)

### GET /dialogs/{id}/suggestions — Подсказки для диалога

**Response:**
```json
{
    "data": [
        {
            "id": "uuid",
            "type": "quick_reply",
            "content": "Оригинал: 15000₽ (1 час). Копия AAA: 8000₽ (1 час).",
            "confidence": 0.95,
            "action": "send_message"
        },
        {
            "id": "uuid",
            "type": "price_suggestion",
            "content": "Рекомендуемая цена: 14000₽ (скидка постоянному клиенту)",
            "confidence": 0.8,
            "action": "update_context"
        },
        {
            "id": "uuid",
            "type": "schedule_slot",
            "content": "Свободные слоты: сегодня 14:00, 16:00; завтра 10:00",
            "confidence": 0.9,
            "action": "send_message"
        }
    ]
}
```

### POST /dialogs/{id}/suggestions/{suggestion_id}/apply — Применить подсказку

**Response:**
```json
{
    "applied": true,
    "result": {
        "message_sent": true,
        "message_id": "uuid"
    }
}
```

### POST /dialogs/{id}/suggestions/{suggestion_id}/dismiss — Отклонить подсказку

---

## 5. События (elo_events) — Timeline

### GET /events — Лента событий

Для дашборда / аналитики.

**Query params:**
- `dialog_id` (uuid): фильтр по диалогу
- `client_id` (uuid): фильтр по клиенту
- `types` (string[]): типы событий
- `since` (datetime): с какого времени
- `limit` (int)

**Response:**
```json
{
    "data": [
        {
            "id": "uuid",
            "type": "message_received",
            "dialog_id": "uuid",
            "client_id": "uuid",
            "payload": {
                "content": "Здравствуйте",
                "channel": "telegram"
            },
            "created_at": "2024-12-09T10:00:00Z"
        },
        {
            "id": "uuid",
            "type": "context_extracted",
            "dialog_id": "uuid",
            "payload": {
                "intent": "repair",
                "device": "iPhone 14 Pro",
                "confidence": 0.95
            },
            "created_at": "2024-12-09T10:00:01Z"
        },
        {
            "id": "uuid",
            "type": "stage_changed",
            "dialog_id": "uuid",
            "payload": {
                "old_stage": "new",
                "new_stage": "quoted",
                "price": 15000
            },
            "created_at": "2024-12-09T10:05:00Z"
        }
    ]
}
```

---

## 6. Справочники

### GET /verticals — Вертикали бизнеса

```json
{
    "data": [
        {
            "code": "phone_repair",
            "name": "Ремонт телефонов",
            "intents": ["repair", "buy", "sell", "consult"],
            "stages": ["new", "quoted", "approved", "in_progress", "ready", "delivered"]
        }
    ]
}
```

### GET /price-list — Прайс-лист

**Query params:**
- `vertical` (string): код вертикали
- `search` (string): поиск по названию

```json
{
    "data": [
        {
            "id": "uuid",
            "category": "Замена экрана",
            "device_pattern": "iPhone 14 Pro*",
            "service_name": "Замена дисплея (оригинал)",
            "price_from": 15000,
            "price_to": 18000,
            "duration_minutes": 60
        }
    ]
}
```

---

## 7. Операторы (elo_operators)

### GET /operators — Список операторов

```json
{
    "data": [
        {
            "id": "uuid",
            "name": "Алексей",
            "role": "operator",
            "is_online": true,
            "active_dialogs_count": 5
        }
    ]
}
```

### GET /operators/me — Текущий оператор

```json
{
    "id": "uuid",
    "name": "Алексей",
    "role": "operator",
    "permissions": ["dialogs.read", "dialogs.write", "messages.send"],
    "settings": {
        "notifications_enabled": true,
        "auto_assign": true
    }
}
```

---

## WebSocket: Realtime обновления

### Подключение

```
wss://45.144.177.128:8780/ws?token={jwt_token}&tenant_id={tenant_uuid}
```

### События

**Новое сообщение в диалоге:**
```json
{
    "event": "dialog.message",
    "data": {
        "dialog_id": "uuid",
        "message": {
            "id": "uuid",
            "type": "message",
            "direction": "inbound",
            "content": "Текст сообщения",
            "created_at": "2024-12-09T10:30:00Z"
        }
    }
}
```

**Контекст диалога обновлён (AI извлёк данные):**
```json
{
    "event": "dialog.context_updated",
    "data": {
        "dialog_id": "uuid",
        "context": {
            "intent": "repair",
            "device": "iPhone 14 Pro",
            "issue": "разбит экран",
            "stage": "new"
        },
        "changes": ["device", "issue"]
    }
}
```

**Новая AI подсказка:**
```json
{
    "event": "dialog.suggestion",
    "data": {
        "dialog_id": "uuid",
        "suggestion": {
            "id": "uuid",
            "type": "quick_reply",
            "content": "Предложенный ответ..."
        }
    }
}
```

**Статус диалога изменён:**
```json
{
    "event": "dialog.status_changed",
    "data": {
        "dialog_id": "uuid",
        "old_status": "active",
        "new_status": "completed"
    }
}
```

**Новый диалог:**
```json
{
    "event": "dialog.new",
    "data": {
        "dialog": {
            "id": "uuid",
            "client": {"id": "uuid", "name": "Новый клиент"},
            "channel": "telegram",
            "context": {}
        }
    }
}
```

---

## Миграция с текущего API v1

| Старый endpoint | Новый endpoint | Изменения |
|-----------------|----------------|-----------|
| GET /appeals | GET /dialogs | Диалоги вместо заявок |
| GET /appeals/{id} | GET /dialogs/{id} | context вместо отдельных полей |
| GET /appeals/{id}/messages | GET /dialogs/{id}/messages | Без изменений |
| POST /messages | POST /dialogs/{id}/messages | Привязка к диалогу |
| GET /clients/{id}/touchpoints | GET /events?client_id={id} | События вместо touchpoints |

### Главные отличия

1. **Диалог = центральная сущность** (не заявка, не клиент)
2. **context JSONB** вместо отдельных таблиц устройств/проблем
3. **AI suggestions** как отдельный endpoint
4. **Events API** для аналитики и timeline

---

## Типы событий (event_type)

| Тип | Описание |
|-----|----------|
| `message_received` | Входящее сообщение |
| `message_sent` | Исходящее сообщение |
| `context_extracted` | AI извлёк контекст |
| `stage_changed` | Изменён этап воронки |
| `operator_assigned` | Назначен оператор |
| `dialog_created` | Создан диалог |
| `dialog_completed` | Диалог завершён |
| `client_created` | Создан клиент |
| `suggestion_generated` | AI сгенерировал подсказку |
| `suggestion_applied` | Подсказка применена |
| `suggestion_dismissed` | Подсказка отклонена |

---

## Этапы воронки (stage)

Зависят от вертикали и intent. Пример для `phone_repair` + `repair`:

| Stage | Название | Описание |
|-------|----------|----------|
| `new` | Новый | Диалог только начался |
| `qualifying` | Уточнение | Выясняем устройство/проблему |
| `quoted` | Озвучена цена | Клиент получил цену |
| `approved` | Согласовано | Клиент согласился |
| `received` | Принято | Устройство получено |
| `in_progress` | В работе | Идёт ремонт |
| `ready` | Готово | Ремонт завершён |
| `delivered` | Выдано | Клиент забрал |
| `cancelled` | Отменено | Клиент отказался |

---

## Следующий шаг

→ Переписать Core workflows для новой архитектуры
