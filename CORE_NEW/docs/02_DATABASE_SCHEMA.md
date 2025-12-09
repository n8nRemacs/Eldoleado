# CORE_NEW: Схема базы данных

## Философия новой схемы

**Старая схема:** Appeal-centric (50 таблиц, много legacy полей)
```
appeals → appeal_devices → appeal_repairs → messages_history
```

**Новая схема:** Dialog-centric (минимум таблиц, AI заполняет)
```
elo_clients → elo_dialogs → elo_events → [entities извлекаются автоматически]
```

### Принципы

1. **Минимум ручного ввода** — AI извлекает данные из диалога
2. **Events как источник правды** — всё что происходит = событие
3. **Граф для связей** — PostgreSQL хранит данные, Neo4j — связи
4. **Мягкая схема** — JSONB для расширяемости без миграций

### Префикс ELO_

Все новые таблицы имеют префикс `elo_` (Eldoleado):
- Легко отличить от старых таблиц
- Нет конфликтов при миграции
- Можно запускать параллельно со старой системой

---

## Ядро системы

### 1. elo_tenants — Тенанты

```sql
CREATE TABLE elo_tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Основное
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(100) UNIQUE,           -- для URL: batterycrm.eldoleado.ru

    -- Настройки
    settings JSONB DEFAULT '{}',        -- все настройки тенанта
    -- settings.timezone, settings.currency, settings.language
    -- settings.ai_mode: 'assist' | 'auto' | 'manual'
    -- settings.working_hours: {start: '09:00', end: '20:00'}

    -- Лимиты
    plan VARCHAR(50) DEFAULT 'free',    -- free, starter, pro, enterprise
    max_operators INT DEFAULT 3,
    max_dialogs_per_month INT DEFAULT 500,

    -- Статус
    is_active BOOLEAN DEFAULT true,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);
```

### 2. elo_clients — Клиенты

```sql
CREATE TABLE elo_clients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),

    -- Идентификаторы каналов (как найти клиента)
    phone VARCHAR(20),
    telegram_id VARCHAR(50),
    whatsapp_id VARCHAR(50),
    vk_id VARCHAR(50),
    avito_id VARCHAR(50),
    email VARCHAR(255),

    -- Данные клиента (AI извлекает из диалогов)
    name VARCHAR(255),

    -- Обогащение (собирается автоматически)
    profile JSONB DEFAULT '{}',
    -- profile.preferences: ['оригинальные запчасти', 'быстро']
    -- profile.devices: [{brand: 'Apple', model: 'iPhone 14'}]
    -- profile.facts: ['есть жена Мария', 'работает рядом']
    -- profile.sentiment: 'positive' | 'neutral' | 'negative'

    -- Статистика (считается автоматически)
    stats JSONB DEFAULT '{}',
    -- stats.total_dialogs: 5
    -- stats.total_orders: 3
    -- stats.total_spent: 45000
    -- stats.last_contact_at: '2024-12-09T10:30:00Z'
    -- stats.avg_response_time_sec: 120

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    -- Уникальность по каналам в рамках тенанта
    UNIQUE(tenant_id, phone),
    UNIQUE(tenant_id, telegram_id),
    UNIQUE(tenant_id, whatsapp_id),
    UNIQUE(tenant_id, vk_id),
    UNIQUE(tenant_id, avito_id),
    UNIQUE(tenant_id, email)
);

CREATE INDEX idx_elo_clients_tenant ON elo_clients(tenant_id);
CREATE INDEX idx_elo_clients_phone ON elo_clients(tenant_id, phone);
CREATE INDEX idx_elo_clients_telegram ON elo_clients(tenant_id, telegram_id);
```

### 3. elo_dialogs — Диалоги (заменяет appeals)

```sql
CREATE TABLE elo_dialogs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),
    client_id UUID NOT NULL REFERENCES elo_clients(id),

    -- Текущий канал диалога
    channel VARCHAR(20) NOT NULL,       -- telegram, whatsapp, vk, avito, phone, web
    external_chat_id VARCHAR(100),      -- ID чата во внешней системе

    -- Статус
    status VARCHAR(20) DEFAULT 'active',
    -- active: диалог идёт
    -- waiting: ждём ответа клиента
    -- completed: завершён успешно
    -- archived: архивирован

    -- AI понимание (обновляется после каждого сообщения)
    context JSONB DEFAULT '{}',
    -- context.intent: 'repair' | 'purchase' | 'question' | 'sale' | null
    -- context.device: {brand: 'Apple', model: 'iPhone 14 Pro'}
    -- context.issue: 'разбит экран'
    -- context.stage: 'collecting_info' | 'quoted' | 'confirmed' | 'in_progress' | 'completed'
    -- context.price_quoted: 15000
    -- context.appointment: '2024-12-10T14:00:00Z'
    -- context.awaiting: 'price_confirmation' | 'appointment_time' | null

    -- Метаданные
    metadata JSONB DEFAULT '{}',
    -- metadata.source: 'organic' | 'ad' | 'referral'
    -- metadata.utm: {source: '...', medium: '...', campaign: '...'}
    -- metadata.referrer_client_id: 'uuid'

    -- Операторы
    assigned_operator_id UUID REFERENCES elo_operators(id),
    last_operator_id UUID REFERENCES elo_operators(id),

    -- Timestamps
    last_message_at TIMESTAMPTZ,
    last_client_message_at TIMESTAMPTZ,
    last_operator_message_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_elo_dialogs_tenant ON elo_dialogs(tenant_id);
CREATE INDEX idx_elo_dialogs_client ON elo_dialogs(client_id);
CREATE INDEX idx_elo_dialogs_status ON elo_dialogs(tenant_id, status);
CREATE INDEX idx_elo_dialogs_channel ON elo_dialogs(tenant_id, channel);
CREATE INDEX idx_elo_dialogs_last_message ON elo_dialogs(tenant_id, last_message_at DESC);
```

### 4. elo_events — Все события (EventStore)

```sql
CREATE TABLE elo_events (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),

    -- Привязка
    dialog_id UUID REFERENCES elo_dialogs(id),
    client_id UUID REFERENCES elo_clients(id),

    -- Тип события
    event_type VARCHAR(50) NOT NULL,
    -- message.inbound, message.outbound
    -- dialog.created, dialog.stage_changed, dialog.completed
    -- client.created, client.updated
    -- order.created, order.stage_changed
    -- appointment.scheduled, appointment.completed
    -- payment.received
    -- ai.extraction, ai.suggestion

    -- Данные события
    data JSONB NOT NULL,
    -- Для message: {text: '...', channel: 'telegram', sender: 'client'|'operator'|'ai'}
    -- Для stage_changed: {from: '...', to: '...', reason: '...'}
    -- Для ai.extraction: {entities: [...], confidence: 0.95}

    -- Кто инициировал
    actor_type VARCHAR(20),             -- client, operator, ai, system
    actor_id UUID,                      -- client_id или operator_id

    -- Внешние ID
    external_id VARCHAR(100),           -- message_id из мессенджера

    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Партиционирование по месяцам для производительности
-- CREATE TABLE elo_events_2024_12 PARTITION OF elo_events FOR VALUES FROM ('2024-12-01') TO ('2025-01-01');

CREATE INDEX idx_elo_events_tenant ON elo_events(tenant_id);
CREATE INDEX idx_elo_events_dialog ON elo_events(dialog_id);
CREATE INDEX idx_elo_events_client ON elo_events(client_id);
CREATE INDEX idx_elo_events_type ON elo_events(tenant_id, event_type);
CREATE INDEX idx_elo_events_created ON elo_events(tenant_id, created_at DESC);
```

### 5. elo_operators — Операторы

```sql
CREATE TABLE elo_operators (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),

    -- Аутентификация
    email VARCHAR(255),
    phone VARCHAR(20),
    telegram_id VARCHAR(50),
    password_hash VARCHAR(255),

    -- Профиль
    name VARCHAR(255) NOT NULL,
    avatar_url TEXT,
    role VARCHAR(20) DEFAULT 'operator', -- admin, operator, viewer

    -- Push уведомления
    fcm_tokens JSONB DEFAULT '[]',      -- массив токенов (несколько устройств)

    -- Настройки
    settings JSONB DEFAULT '{}',
    -- settings.notifications: {new_dialog: true, new_message: true}
    -- settings.working_hours: {start: '09:00', end: '18:00'}

    -- Статус
    is_active BOOLEAN DEFAULT true,
    last_seen_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, email),
    UNIQUE(tenant_id, telegram_id)
);

CREATE INDEX idx_elo_operators_tenant ON elo_operators(tenant_id);
```

---

## Справочники (минимум для MVP)

### 6. elo_verticals — Вертикали бизнеса

```sql
CREATE TABLE elo_verticals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    code VARCHAR(50) UNIQUE NOT NULL,   -- phone_repair, auto_service, beauty_salon
    name VARCHAR(100) NOT NULL,

    -- Конфигурация вертикали
    config JSONB NOT NULL,
    -- config.intents: ['repair', 'purchase', 'question']
    -- config.stages: ['new', 'quoted', 'confirmed', 'in_progress', 'completed']
    -- config.entity_types: ['device', 'issue', 'service']
    -- config.extraction_prompts: {...}

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Дефолтная вертикаль для MVP
INSERT INTO elo_verticals (code, name, config) VALUES (
    'phone_repair',
    'Ремонт телефонов',
    '{
        "intents": ["repair", "purchase", "question", "sale"],
        "stages": ["new", "quoted", "scheduled", "received", "in_progress", "ready", "delivered"],
        "entity_types": ["device", "issue"],
        "device_brands": ["Apple", "Samsung", "Xiaomi", "Huawei", "Honor", "Realme", "OPPO", "Vivo"],
        "issue_categories": ["Дисплей", "Аккумулятор", "Корпус", "Камера", "Связь", "ПО"]
    }'
);
```

### 7. elo_tenant_verticals — Привязка тенанта к вертикали

```sql
CREATE TABLE elo_tenant_verticals (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),
    vertical_id UUID NOT NULL REFERENCES elo_verticals(id),

    -- Кастомизация вертикали для тенанта
    custom_config JSONB DEFAULT '{}',
    -- Переопределяет config из verticals

    is_primary BOOLEAN DEFAULT false,   -- основная вертикаль тенанта

    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, vertical_id)
);
```

### 8. elo_price_list — Прайс-лист

```sql
CREATE TABLE elo_price_list (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),

    -- Что
    service_type VARCHAR(50) NOT NULL,  -- repair, purchase, service

    -- Для чего (опционально)
    brand VARCHAR(100),                 -- NULL = для всех
    model VARCHAR(100),                 -- NULL = для всех моделей бренда
    issue_category VARCHAR(100),        -- "Дисплей", "Аккумулятор"

    -- Название услуги
    name VARCHAR(255) NOT NULL,         -- "Замена экрана"
    description TEXT,

    -- Цена
    price_min DECIMAL(10,2),
    price_max DECIMAL(10,2),
    price_display VARCHAR(100),         -- "от 5000₽" или "5000-8000₽"

    -- Время
    duration_minutes INT,
    duration_display VARCHAR(50),       -- "1-2 часа"

    -- Наличие
    availability VARCHAR(20) DEFAULT 'available',
    -- available, on_order, out_of_stock

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_elo_price_list_tenant ON elo_price_list(tenant_id);
CREATE INDEX idx_elo_price_list_search ON elo_price_list(tenant_id, brand, model, issue_category);
```

---

## AI и контекст

### 9. elo_ai_extractions — Извлечения AI

```sql
CREATE TABLE elo_ai_extractions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),
    dialog_id UUID NOT NULL REFERENCES elo_dialogs(id),
    event_id UUID REFERENCES elo_events(id),  -- к какому сообщению относится

    -- Что извлекли
    extraction_type VARCHAR(50) NOT NULL,
    -- intent, device, issue, price_request, appointment_request, sentiment

    -- Результат
    value JSONB NOT NULL,
    -- Для intent: {type: 'repair', confidence: 0.95}
    -- Для device: {brand: 'Apple', model: 'iPhone 14 Pro', confidence: 0.9}
    -- Для issue: {category: 'Дисплей', description: 'разбит экран', confidence: 0.85}

    confidence DECIMAL(3,2),            -- 0.00 - 1.00

    -- Статус
    status VARCHAR(20) DEFAULT 'extracted',
    -- extracted: AI извлёк
    -- confirmed: оператор подтвердил
    -- rejected: оператор отклонил
    -- applied: применено к диалогу

    confirmed_by UUID REFERENCES elo_operators(id),
    confirmed_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_elo_ai_extractions_dialog ON elo_ai_extractions(dialog_id);
CREATE INDEX idx_elo_ai_extractions_type ON elo_ai_extractions(tenant_id, extraction_type);
```

### 10. elo_ai_suggestions — Подсказки AI

```sql
CREATE TABLE elo_ai_suggestions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),
    dialog_id UUID NOT NULL REFERENCES elo_dialogs(id),

    -- Тип подсказки
    suggestion_type VARCHAR(50) NOT NULL,
    -- response: предложенный ответ
    -- action: предложенное действие
    -- info: информация для оператора

    -- Содержимое
    content JSONB NOT NULL,
    -- Для response: {text: '...', quick_replies: ['Да', 'Нет']}
    -- Для action: {action: 'send_price', params: {price: 15000}}
    -- Для info: {type: 'price_found', data: {service: '...', price: 15000}}

    -- Использование
    status VARCHAR(20) DEFAULT 'pending',
    -- pending, used, ignored, modified

    used_by UUID REFERENCES elo_operators(id),
    used_at TIMESTAMPTZ,
    modified_text TEXT,                 -- если оператор изменил

    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_elo_ai_suggestions_dialog ON elo_ai_suggestions(dialog_id);
```

---

## Каналы

### 11. elo_channel_accounts — Аккаунты каналов

```sql
CREATE TABLE elo_channel_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_tenants(id),

    channel VARCHAR(20) NOT NULL,       -- telegram, whatsapp, vk, avito

    -- Идентификация
    account_id VARCHAR(100) NOT NULL,   -- bot token, phone, group_id
    account_name VARCHAR(255),          -- название бота/аккаунта

    -- Webhook
    webhook_hash VARCHAR(32),           -- для URL вебхука
    webhook_url TEXT,                   -- полный URL

    -- Credentials (зашифрованы)
    credentials JSONB,
    -- telegram: {bot_token: '...'}
    -- whatsapp: {profile_id: '...', api_key: '...'}
    -- vk: {group_id: '...', token: '...'}

    -- Статус
    is_active BOOLEAN DEFAULT true,
    last_webhook_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, channel, account_id)
);

CREATE INDEX idx_elo_channel_accounts_tenant ON elo_channel_accounts(tenant_id);
CREATE INDEX idx_elo_channel_accounts_webhook ON elo_channel_accounts(webhook_hash);
```

---

## Отчётность (views и materialized views)

### Аналитика диалогов

```sql
CREATE MATERIALIZED VIEW elo_analytics_dialogs_daily AS
SELECT
    tenant_id,
    DATE(created_at) as date,
    channel,
    context->>'intent' as intent,
    context->>'stage' as final_stage,
    COUNT(*) as dialog_count,
    COUNT(*) FILTER (WHERE status = 'completed') as completed_count,
    AVG(EXTRACT(EPOCH FROM (
        COALESCE(
            (SELECT MIN(created_at) FROM elo_events e
             WHERE e.dialog_id = d.id AND e.event_type = 'message.outbound'),
            NOW()
        ) - created_at
    ))) as avg_first_response_sec
FROM elo_dialogs d
GROUP BY tenant_id, DATE(created_at), channel, context->>'intent', context->>'stage';

-- Обновлять раз в час
-- REFRESH MATERIALIZED VIEW elo_analytics_dialogs_daily;
```

### Аналитика клиентов

```sql
CREATE MATERIALIZED VIEW elo_analytics_clients AS
SELECT
    c.tenant_id,
    c.id as client_id,
    c.name,
    COUNT(DISTINCT d.id) as total_dialogs,
    COUNT(DISTINCT d.id) FILTER (WHERE d.status = 'completed') as completed_dialogs,
    SUM((d.context->>'price_quoted')::numeric) as total_quoted,
    MAX(d.last_message_at) as last_contact_at,
    array_agg(DISTINCT d.channel) as channels
FROM elo_clients c
LEFT JOIN elo_dialogs d ON d.client_id = c.id
GROUP BY c.tenant_id, c.id, c.name;
```

---

## Миграция со старой схемы

### Маппинг таблиц

| Старая таблица | Новая таблица | Комментарий |
|----------------|---------------|-------------|
| appeals | elo_dialogs | Основная замена |
| messages_history | elo_events (type='message.*') | Сообщения = события |
| clients | elo_clients | Упрощённая структура |
| operators | elo_operators | Упрощённая структура |
| appeal_devices | elo_dialogs.context.device | JSONB внутри диалога |
| appeal_repairs | elo_dialogs.context.issue | JSONB внутри диалога |
| appeal_stages | elo_verticals.config.stages | Конфигурация вертикали |
| brands, models | elo_verticals.config + elo_price_list | Справочники в конфиге |

### Скрипт миграции (концепт)

```sql
-- 1. Создать новые таблицы (elo_*)
-- 2. Мигрировать клиентов
INSERT INTO elo_clients (id, tenant_id, phone, telegram_id, ...)
SELECT id, tenant_id, phone, telegram_id, ...
FROM clients;

-- 3. Мигрировать appeals → elo_dialogs
INSERT INTO elo_dialogs (id, tenant_id, client_id, channel, status, context)
SELECT
    a.id,
    a.tenant_id,
    a.client_id,
    COALESCE(c.platform, 'unknown'),
    CASE
        WHEN a.stage IN ('Ремонт выполнен', 'Завершено') THEN 'completed'
        WHEN a.stage IN ('Отказ', 'Спам') THEN 'archived'
        ELSE 'active'
    END,
    jsonb_build_object(
        'intent', a.type,
        'device', jsonb_build_object('brand', b.name, 'model', m.name),
        'issue', a.problem_description,
        'stage', a.stage,
        'price_quoted', a.estimated_cost
    )
FROM appeals a
LEFT JOIN channels c ON c.id = a.channel_id
LEFT JOIN brands b ON b.id = a.brand_id
LEFT JOIN models m ON m.id = a.model_id;

-- 4. Мигрировать messages_history → elo_events
INSERT INTO elo_events (tenant_id, dialog_id, client_id, event_type, data, created_at)
SELECT
    tenant_id,
    appeal_id,
    (SELECT client_id FROM appeals WHERE id = appeal_id),
    CASE
        WHEN message_type = 'client' THEN 'message.inbound'
        ELSE 'message.outbound'
    END,
    jsonb_build_object(
        'text', message_text,
        'channel', channel,
        'sender', message_type
    ),
    created_at
FROM messages_history;
```

---

## Итого

### Количество таблиц

**Ядро (5):** elo_tenants, elo_clients, elo_dialogs, elo_events, elo_operators
**Справочники (3):** elo_verticals, elo_tenant_verticals, elo_price_list
**AI (2):** elo_ai_extractions, elo_ai_suggestions
**Каналы (1):** elo_channel_accounts

**Всего: 11 таблиц с префиксом elo_** (вместо 50)

### Принципы

1. **Префикс elo_** — отделение от старой системы
2. **JSONB везде** — гибкость без миграций
3. **Events = источник правды** — всё что происходит
4. **AI заполняет** — минимум ручного ввода
5. **Materialized views** — для отчётности
