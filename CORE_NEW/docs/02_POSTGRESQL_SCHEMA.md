# CORE_NEW: Схема PostgreSQL

## Принципы

1. **PostgreSQL хранит данные**, Neo4j хранит связи
2. **Минимум дублирования** — связи только в графе
3. **UUID везде** — для синхронизации между системами
4. **tenant_id обязателен** — multi-tenant архитектура

---

## Основные сущности

### 1. clients — Клиенты

```sql
CREATE TABLE clients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),

    -- Идентификаторы каналов
    phone VARCHAR(20),
    telegram_id VARCHAR(50),
    vk_id VARCHAR(50),
    whatsapp_id VARCHAR(50),
    avito_id VARCHAR(50),

    -- Данные клиента
    name VARCHAR(255),

    -- Метаданные
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    -- Индексы для поиска по каналам
    UNIQUE(tenant_id, phone),
    UNIQUE(tenant_id, telegram_id),
    UNIQUE(tenant_id, vk_id),
    UNIQUE(tenant_id, whatsapp_id),
    UNIQUE(tenant_id, avito_id)
);

-- Индекс для поиска по имени
CREATE INDEX idx_clients_name ON clients(tenant_id, name);
```

### 2. devices — Устройства

```sql
CREATE TABLE devices (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),

    -- Владелец (primary)
    owner_id UUID NOT NULL REFERENCES clients(id),

    -- Данные устройства
    brand VARCHAR(100),           -- "Apple"
    model VARCHAR(100),           -- "iPhone 14 Pro"
    owner_label VARCHAR(100),     -- "мой", "жены", "сына"

    -- Идентификаторы
    imei VARCHAR(20),
    serial_number VARCHAR(50),

    -- Метаданные
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_devices_owner ON devices(owner_id);
CREATE INDEX idx_devices_tenant ON devices(tenant_id);
```

### 3. problems — Проблемы (заменяет appeals)

```sql
CREATE TABLE problems (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),

    -- Связь с устройством
    device_id UUID NOT NULL REFERENCES devices(id),

    -- Тип и описание
    intent VARCHAR(20) NOT NULL,  -- REPAIR, PURCHASE, SALE, QUESTION
    repair_category_id UUID REFERENCES repair_categories(id),
    issue_type_id UUID REFERENCES issue_types(id),
    description TEXT,

    -- Воронка
    stage VARCHAR(20) NOT NULL DEFAULT 'NEW',
    -- NEW, QUOTED, SCHEDULED, RECEIVED, IN_PROGRESS, READY, DELIVERED, CANCELLED

    -- Финансы
    quoted_price DECIMAL(10,2),
    final_price DECIMAL(10,2),

    -- Метаданные
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    closed_at TIMESTAMPTZ
);

CREATE INDEX idx_problems_device ON problems(device_id);
CREATE INDEX idx_problems_stage ON problems(tenant_id, stage);
CREATE INDEX idx_problems_active ON problems(tenant_id) WHERE stage NOT IN ('DELIVERED', 'CANCELLED');
```

### 4. touchpoints — Касания

```sql
CREATE TABLE touchpoints (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),

    -- Кто касается
    client_id UUID NOT NULL REFERENCES clients(id),

    -- К чему касание (опционально)
    device_id UUID REFERENCES devices(id),
    problem_id UUID REFERENCES problems(id),

    -- Тип касания
    type VARCHAR(20) NOT NULL,
    -- message, call, visit, pickup, promo, system

    direction VARCHAR(10) NOT NULL,
    -- inbound, outbound, promo

    -- Канал
    channel VARCHAR(20) NOT NULL,
    -- telegram, whatsapp, vk, avito, phone, visit

    -- Содержимое
    content TEXT,

    -- Внешние ID
    external_message_id VARCHAR(100),
    external_chat_id VARCHAR(100),

    -- Метаданные
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_touchpoints_client ON touchpoints(client_id, created_at DESC);
CREATE INDEX idx_touchpoints_device ON touchpoints(device_id, created_at DESC);
CREATE INDEX idx_touchpoints_problem ON touchpoints(problem_id, created_at DESC);
```

### 5. dialog_focus — Фокус диалога

```sql
CREATE TABLE dialog_focus (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),

    -- Чей фокус
    client_id UUID NOT NULL REFERENCES clients(id),
    channel VARCHAR(20) NOT NULL,

    -- Текущий фокус
    intent VARCHAR(20),           -- REPAIR, PURCHASE, etc.
    device_id UUID REFERENCES devices(id),
    problem_id UUID REFERENCES problems(id),

    -- Что ждём от клиента
    awaiting VARCHAR(50),
    -- device_brand, device_model, issue_description, price_approval, etc.

    -- Метаданные
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(client_id, channel)
);
```

### 6. client_relations — Связи между клиентами (кэш из графа)

```sql
CREATE TABLE client_relations (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),

    client_a_id UUID NOT NULL REFERENCES clients(id),
    client_b_id UUID NOT NULL REFERENCES clients(id),

    relation_type VARCHAR(20) NOT NULL,
    -- SPOUSE, PARENT, CHILD, FRIEND, COLLEAGUE, REFERRED_BY

    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(client_a_id, client_b_id, relation_type)
);
```

---

## Справочники

### repair_categories — Категории ремонта

```sql
CREATE TABLE repair_categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),

    name VARCHAR(100) NOT NULL,        -- "Замена экрана"
    device_type VARCHAR(50),           -- "smartphone", "laptop", "tablet"

    created_at TIMESTAMPTZ DEFAULT NOW()
);
```

### issue_types — Типы поломок

```sql
CREATE TABLE issue_types (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),

    category_id UUID NOT NULL REFERENCES repair_categories(id),
    name VARCHAR(100) NOT NULL,        -- "Не работает Face ID"

    -- Прайс
    base_price DECIMAL(10,2),

    created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

## Миграция с текущей схемы

### Что меняется

| Было | Стало | Комментарий |
|------|-------|-------------|
| appeals | problems | Проблема привязана к device, не к client |
| messages_history | touchpoints | Все касания в одной таблице |
| appeal_devices | devices | Устройства отдельно, владелец = client |
| — | dialog_focus | Новая таблица для фокуса |
| — | client_relations | Кэш связей из графа |

### План миграции

1. Создать новые таблицы параллельно
2. Написать скрипт миграции данных
3. Переключить workflows на новые таблицы
4. Удалить старые таблицы

---

## Ключевые запросы

### Получить клиента со всеми устройствами и проблемами

```sql
SELECT
    c.id, c.name,
    d.id as device_id, d.brand, d.model, d.owner_label,
    p.id as problem_id, p.intent, p.stage, p.description
FROM clients c
LEFT JOIN devices d ON d.owner_id = c.id
LEFT JOIN problems p ON p.device_id = d.id AND p.stage NOT IN ('DELIVERED', 'CANCELLED')
WHERE c.id = $1
ORDER BY d.created_at DESC, p.created_at DESC;
```

### Получить все устройства клиента (включая связанных)

```sql
WITH client_network AS (
    -- Сам клиент
    SELECT $1::uuid as client_id
    UNION
    -- Связанные клиенты
    SELECT client_b_id FROM client_relations WHERE client_a_id = $1
    UNION
    SELECT client_a_id FROM client_relations WHERE client_b_id = $1
)
SELECT d.*, c.name as owner_name
FROM devices d
JOIN clients c ON c.id = d.owner_id
WHERE d.owner_id IN (SELECT client_id FROM client_network)
ORDER BY d.created_at DESC;
```

### Список клиентов с активными проблемами

```sql
SELECT
    c.id, c.name,
    COUNT(DISTINCT d.id) as device_count,
    COUNT(DISTINCT p.id) FILTER (WHERE p.stage NOT IN ('DELIVERED', 'CANCELLED')) as active_problems,
    array_agg(DISTINCT
        CASE
            WHEN c.telegram_id IS NOT NULL THEN 'telegram'
            WHEN c.whatsapp_id IS NOT NULL THEN 'whatsapp'
            WHEN c.vk_id IS NOT NULL THEN 'vk'
            WHEN c.avito_id IS NOT NULL THEN 'avito'
        END
    ) FILTER (WHERE c.telegram_id IS NOT NULL OR c.whatsapp_id IS NOT NULL OR c.vk_id IS NOT NULL OR c.avito_id IS NOT NULL) as channels
FROM clients c
LEFT JOIN devices d ON d.owner_id = c.id
LEFT JOIN problems p ON p.device_id = d.id
WHERE c.tenant_id = $1
GROUP BY c.id
HAVING COUNT(DISTINCT p.id) FILTER (WHERE p.stage NOT IN ('DELIVERED', 'CANCELLED')) > 0
ORDER BY MAX(p.updated_at) DESC;
```

---

## Следующий шаг

→ `03_NEO4J_SCHEMA.md` — Схема графа Neo4j
