# Multi-Appeal API Documentation

**Версия:** 1.0
**Дата:** 2025-11-23
**Статус:** Готово к использованию

---

## 📋 Обзор

Multi-Appeal API позволяет управлять заявками с несколькими устройствами и несколькими неисправностями для каждого устройства.

### Основные возможности

- ✅ Создание/обновление мультизаявок с несколькими устройствами
- ✅ Каждое устройство может иметь несколько неисправностей
- ✅ Получение полной информации о всех устройствах и ремонтах
- ✅ Удаление отдельных устройств из заявки
- ✅ Автоматический расчёт общей стоимости
- ✅ Поддержка гибридного подхода (простые + мультизаявки)

---

## 🔐 Аутентификация

Все endpoints требуют заголовок с session token:

```
X-Session-Token: <operator_session_token>
```

---

## 📡 Endpoints

### 1. PUT /api/operator/appeals/:appeal_id/devices

**Назначение:** Создать или обновить устройства в мультизаявке

**Headers:**
```
Content-Type: application/json
X-Session-Token: <token>
```

**Request Body:**
```json
{
  "devices": [
    {
      "id": "device-uuid-001",
      "brand_id": "brand-apple-uuid",
      "model_id": "model-iphone14-uuid",
      "device_type_id": "dtype-smartphone-uuid",
      "color": "Чёрный",
      "condition": "Хорошее, есть царапины",
      "serial_number": "F2LW1234ABC",
      "imei": "352099001234567",
      "accessories": ["Чехол", "Зарядка", "Наушники"],
      "notes": "Клиент просит сохранить данные",
      "repairs": [
        {
          "id": "repair-uuid-001",
          "repair_category_id": "cat-battery-uuid",
          "issue_type_id": "issue-battery-drain-uuid",
          "issue_id": "specific-issue-uuid",
          "parts_owner": "ours",
          "estimated_cost": 3500,
          "estimated_time_minutes": 30,
          "symptoms": "Батарея быстро разряжается, телефон выключается при 20%",
          "diagnosis": "Батарея изношена, требуется замена",
          "repair_status": "pending",
          "notes": "Использовать оригинальную батарею"
        },
        {
          "repair_category_id": "cat-display-uuid",
          "issue_type_id": "issue-screen-crack-uuid",
          "parts_owner": "ours",
          "estimated_cost": 7500,
          "estimated_time_minutes": 45,
          "symptoms": "Трещина на экране в правом верхнем углу",
          "repair_status": "pending"
        }
      ]
    },
    {
      "brand_id": "brand-samsung-uuid",
      "model_id": "model-s23-uuid",
      "color": "Белый",
      "condition": "Отличное",
      "repairs": [
        {
          "repair_category_id": "cat-charging-uuid",
          "issue_type_id": "issue-no-charge-uuid",
          "parts_owner": "client",
          "estimated_cost": 2500,
          "estimated_time_minutes": 60,
          "symptoms": "Не заряжается, разъём шатается",
          "diagnosis": "Повреждён разъём USB-C",
          "repair_status": "pending"
        }
      ]
    }
  ]
}
```

**Response 200 OK:**
```json
{
  "success": true,
  "data": {
    "appeal_id": "07af5e27-...",
    "devices_count": 2,
    "repairs_count": 3,
    "total_estimated_cost": 13500,
    "updated_at": "2025-11-23T10:45:00Z"
  }
}
```

**Response 400 Bad Request:**
```json
{
  "success": false,
  "error": "invalid_devices",
  "message": "Devices array is required and must not be empty"
}
```

**Response 401 Unauthorized:**
```json
{
  "success": false,
  "error": "unauthorized",
  "message": "Invalid session or appeal not found"
}
```

---

### 2. GET /api/operator/appeals/:appeal_id/devices

**Назначение:** Получить все устройства и ремонты заявки

**Headers:**
```
X-Session-Token: <token>
```

**Response 200 OK:**
```json
{
  "success": true,
  "data": {
    "appeal_id": "07af5e27-...",
    "is_multi_appeal": true,
    "client_name": "Иван Иванов",
    "client_phone": "+79001234567",
    "channel_name": "Telegram",
    "simple_brand_id": null,
    "simple_model_id": null,
    "devices": [
      {
        "id": "device-001",
        "brand_id": "brand-apple",
        "brand_name": "Apple",
        "model_id": "model-iphone14",
        "model_name": "iPhone 14",
        "device_type_id": "dtype-smartphone",
        "color": "Чёрный",
        "condition": "Хорошее, есть царапины",
        "serial_number": "F2LW1234ABC",
        "imei": "352099001234567",
        "accessories": ["Чехол", "Зарядка"],
        "notes": "Клиент просит сохранить данные",
        "repairs": [
          {
            "id": "repair-001",
            "repair_category_id": "cat-battery",
            "repair_category_name": "Аккумулятор",
            "issue_type_id": "issue-battery-drain",
            "issue_type_name": "Быстрый разряд",
            "issue_id": "specific-issue",
            "issue_name": "Батарея разряжается за 3 часа",
            "parts_owner": "ours",
            "estimated_cost": 3500,
            "final_cost": null,
            "estimated_time_minutes": 30,
            "symptoms": "Батарея быстро разряжается, телефон выключается при 20%",
            "diagnosis": "Батарея изношена, требуется замена",
            "repair_status": "pending",
            "completed_at": null,
            "notes": "Использовать оригинальную батарею"
          },
          {
            "id": "repair-002",
            "repair_category_id": "cat-display",
            "repair_category_name": "Дисплей",
            "issue_type_id": "issue-screen-crack",
            "issue_type_name": "Трещина на экране",
            "estimated_cost": 7500,
            "estimated_time_minutes": 45,
            "symptoms": "Трещина на экране в правом верхнем углу",
            "repair_status": "pending"
          }
        ]
      },
      {
        "id": "device-002",
        "brand_id": "brand-samsung",
        "brand_name": "Samsung",
        "model_id": "model-s23",
        "model_name": "Galaxy S23",
        "color": "Белый",
        "condition": "Отличное",
        "repairs": [
          {
            "id": "repair-003",
            "repair_category_id": "cat-charging",
            "repair_category_name": "Порты",
            "issue_type_id": "issue-no-charge",
            "issue_type_name": "Не заряжается",
            "parts_owner": "client",
            "estimated_cost": 2500,
            "estimated_time_minutes": 60,
            "symptoms": "Не заряжается, разъём шатается",
            "diagnosis": "Повреждён разъём USB-C",
            "repair_status": "pending"
          }
        ]
      }
    ]
  }
}
```

**Примечание:** Если `is_multi_appeal = false`, массив `devices` будет пустым, и информация будет в `simple_brand_id`, `simple_model_id` (из таблицы `appeals` напрямую).

---

### 3. DELETE /api/operator/appeals/:appeal_id/devices/:device_id

**Назначение:** Удалить устройство из мультизаявки

**Headers:**
```
X-Session-Token: <token>
```

**Response 200 OK:**
```json
{
  "success": true,
  "data": {
    "deleted_device_id": "device-002",
    "appeal_id": "07af5e27-...",
    "remaining_devices_count": 1,
    "is_multi_appeal": true,
    "updated_at": "2025-11-23T11:00:00Z"
  }
}
```

**Response 400 Bad Request (последнее устройство):**
```json
{
  "success": false,
  "error": "cannot_delete_last_device",
  "message": "Cannot delete the last device in a multi-appeal. Delete the appeal instead."
}
```

**Response 404 Not Found:**
```json
{
  "success": false,
  "error": "not_found",
  "message": "Device not found or unauthorized"
}
```

**Логика:**
- При удалении устройства все его ремонты удаляются автоматически (CASCADE)
- Если остаётся только 1 устройство, флаг `is_multi_appeal` автоматически устанавливается в `false`
- Нельзя удалить последнее устройство — нужно удалять всю заявку

---

## 🔄 Жизненный цикл мультизаявки

### 1. Создание мультизаявки

```bash
# Шаг 1: Создать обычную заявку (через AI или вручную)
POST /api/operator/appeals
{
  "client_id": "client-uuid",
  "channel": "telegram",
  "description": "Ремонт 2 телефонов"
}

# Шаг 2: Добавить устройства и неисправности
PUT /api/operator/appeals/07af5e27-.../devices
{
  "devices": [...]
}

# Результат: is_multi_appeal = true
```

### 2. Получение информации

```bash
GET /api/operator/appeals/07af5e27-.../devices
```

### 3. Обновление устройства

```bash
# Отправить тот же PUT с обновлёнными данными
PUT /api/operator/appeals/07af5e27-.../devices
{
  "devices": [
    {
      "id": "existing-device-uuid",  # Если есть ID — UPDATE
      "brand_id": "...",
      "repairs": [
        {
          "id": "existing-repair-uuid",  # Если есть ID — UPDATE
          "estimated_cost": 4000  # Обновлённая цена
        }
      ]
    },
    {
      # Нет ID — создаст новое устройство
      "brand_id": "...",
      "repairs": [...]
    }
  ]
}
```

### 4. Удаление устройства

```bash
DELETE /api/operator/appeals/07af5e27-.../devices/device-002
```

### 5. Изменение статусов ремонтов

```bash
PUT /api/operator/appeals/07af5e27-.../devices
{
  "devices": [
    {
      "id": "device-001",
      "repairs": [
        {
          "id": "repair-001",
          "repair_status": "completed",  # Обновляем статус
          "completed_at": "2025-11-23T14:00:00Z",
          "final_cost": 3500
        }
      ]
    }
  ]
}
```

---

## 🗄️ Структура БД

### Таблицы

#### `appeals`
```sql
- id (uuid, PK)
- tenant_id (uuid, FK → tenants)
- client_id (uuid, FK → clients)
- is_multi_appeal (boolean)  # NEW
- brand_id (uuid, NULL для мультизаявок)
- model_id (uuid, NULL для мультизаявок)
- ...
```

#### `appeal_devices`
```sql
- id (uuid, PK)
- appeal_id (uuid, FK → appeals ON DELETE CASCADE)
- tenant_id (uuid, FK → tenants ON DELETE CASCADE)
- brand_id (uuid, FK → brands)
- model_id (uuid, FK → models)
- serial_number (varchar, NULL)
- imei (varchar, NULL)
- color (varchar, NULL)
- condition (varchar, NULL)
- accessories (jsonb)
- notes (text)
- created_at, updated_at
```

#### `appeal_repairs`
```sql
- id (uuid, PK)
- appeal_device_id (uuid, FK → appeal_devices ON DELETE CASCADE)
- tenant_id (uuid, FK → tenants ON DELETE CASCADE)
- repair_category_id (uuid, FK → repair_categories)
- issue_type_id (uuid, FK → issue_types)
- issue_id (uuid, FK → issues)
- parts_owner (varchar: 'ours', 'client', 'mixed')
- estimated_cost (numeric)
- final_cost (numeric)
- estimated_time_minutes (int)
- symptoms (text)
- diagnosis (text)
- repair_status (varchar: 'pending', 'in_progress', 'completed', 'cancelled')
- completed_at (timestamptz)
- notes (text)
- created_at, updated_at
```

#### `repair_categories`
```sql
- id (uuid, PK)
- tenant_id (uuid, FK → tenants)
- name (varchar: 'Дисплей', 'Аккумулятор', 'Корпус', и т.д.)
- description (text)
- icon (varchar)
- order (int)
- is_active (boolean)
```

---

## 📊 Примеры использования

### Пример 1: Простая заявка → Мультизаявка

```javascript
// Начальное состояние: простая заявка
{
  "id": "appeal-001",
  "is_multi_appeal": false,
  "brand_id": "brand-apple",
  "model_id": "model-iphone14",
  "issue_id": "issue-battery"
}

// Клиент приносит второе устройство
PUT /api/operator/appeals/appeal-001/devices
{
  "devices": [
    {
      "brand_id": "brand-apple",
      "model_id": "model-iphone14",
      "repairs": [
        {
          "repair_category_id": "cat-battery",
          "estimated_cost": 3500
        }
      ]
    },
    {
      "brand_id": "brand-samsung",
      "model_id": "model-s23",
      "repairs": [
        {
          "repair_category_id": "cat-display",
          "estimated_cost": 8000
        }
      ]
    }
  ]
}

// Результат:
{
  "is_multi_appeal": true,  # Автоматически установлен
  "devices_count": 2,
  "repairs_count": 2,
  "total_estimated_cost": 11500
}
```

### Пример 2: Массовое обновление статусов

```javascript
// Обновить статусы всех ремонтов разом
PUT /api/operator/appeals/appeal-001/devices
{
  "devices": [
    {
      "id": "device-001",
      "repairs": [
        {
          "id": "repair-001",
          "repair_status": "completed",
          "completed_at": "2025-11-23T14:00:00Z",
          "final_cost": 3500
        },
        {
          "id": "repair-002",
          "repair_status": "completed",
          "completed_at": "2025-11-23T14:30:00Z",
          "final_cost": 7500
        }
      ]
    },
    {
      "id": "device-002",
      "repairs": [
        {
          "id": "repair-003",
          "repair_status": "in_progress"
        }
      ]
    }
  ]
}
```

---

## ⚠️ Важные замечания

### Валидация

1. **Обязательные поля для device:**
   - `brand_id` (UUID)
   - `repairs` (массив, минимум 1 элемент)

2. **Обязательные поля для repair:**
   - Минимум одно из: `repair_category_id` или `issue_type_id`

3. **UUID формат:**
   - Все ID должны быть в формате `xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx`

### Безопасность

- Все операции проверяют `tenant_id` — полная изоляция между тенантами
- Session token проверяется на каждом запросе
- Оператор может видеть только заявки своего тенанта

### Производительность

- Используются транзакции для атомарности
- CASCADE удаление для автоматической очистки связанных данных
- Индексы на всех FK для быстрых JOIN'ов

### Ограничения

- Нельзя удалить последнее устройство из мультизаявки
- При UPDATE с существующим `id` обновляются только указанные поля
- Без `id` всегда создаётся новая запись

---

## 🧪 Тестирование

### cURL примеры

```bash
# 1. Создать мультизаявку
curl -X PUT http://localhost:5678/webhook/api/operator/appeals/07af5e27-.../devices \
  -H "Content-Type: application/json" \
  -H "X-Session-Token: your-session-token" \
  -d '{
    "devices": [
      {
        "brand_id": "brand-apple-uuid",
        "model_id": "model-iphone14-uuid",
        "repairs": [
          {
            "repair_category_id": "cat-battery-uuid",
            "estimated_cost": 3500
          }
        ]
      }
    ]
  }'

# 2. Получить мультизаявку
curl -X GET http://localhost:5678/webhook/api/operator/appeals/07af5e27-.../devices \
  -H "X-Session-Token: your-session-token"

# 3. Удалить устройство
curl -X DELETE http://localhost:5678/webhook/api/operator/appeals/07af5e27-.../devices/device-002 \
  -H "X-Session-Token: your-session-token"
```

---

## 📝 Changelog

### Version 1.0 (2025-11-23)
- ✅ Реализован PUT /api/operator/appeals/:id/devices
- ✅ Реализован GET /api/operator/appeals/:id/devices
- ✅ Реализован DELETE /api/operator/appeals/:id/devices/:device_id
- ✅ Поддержка вложенных repairs для каждого device
- ✅ Автоматический расчёт total_estimated_cost
- ✅ Автоматическое управление флагом is_multi_appeal
- ✅ CASCADE удаление repairs при удалении device

---

## 🔗 Связанные документы

- [04_API_SPECIFICATION.md](docs/current/04_API_SPECIFICATION.md) — Полная спецификация API
- [02_DATABASE_SCHEMA_EXTENDED.md](docs/current/02_DATABASE_SCHEMA_EXTENDED.md) — Схема БД
- [05_ANDROID_APP_SPEC.md](docs/current/05_ANDROID_APP_SPEC.md) — Android интеграция

---

**Дата создания:** 2025-11-23
**Версия:** 1.0
**Статус:** Готово к production
