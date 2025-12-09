# CORE_NEW: Схема Neo4j

## Принципы

1. **Граф хранит СВЯЗИ**, PostgreSQL хранит данные
2. **ID синхронизированы** — UUID из PostgreSQL = id в Neo4j
3. **Граф для поиска** — быстрый поиск по связям без JOIN
4. **Минимум данных в графе** — только id и ключевые поля для фильтрации

---

## Узлы (Nodes)

### Client

```cypher
(:Client {
    id: "uuid",           -- Синхронизирован с PostgreSQL
    tenant_id: "uuid",
    name: "string",
    phone: "string",
    telegram_id: "string",
    vk_id: "string",
    whatsapp_id: "string",
    avito_id: "string",
    created_at: datetime
})

-- Индексы
CREATE INDEX client_id FOR (c:Client) ON (c.id);
CREATE INDEX client_tenant FOR (c:Client) ON (c.tenant_id);
CREATE INDEX client_phone FOR (c:Client) ON (c.phone);
CREATE INDEX client_telegram FOR (c:Client) ON (c.telegram_id);
```

### Device

```cypher
(:Device {
    id: "uuid",
    tenant_id: "uuid",
    brand: "string",        -- "Apple"
    model: "string",        -- "iPhone 14 Pro"
    owner_label: "string",  -- "мой", "жены"
    created_at: datetime
})

CREATE INDEX device_id FOR (d:Device) ON (d.id);
CREATE INDEX device_tenant FOR (d:Device) ON (d.tenant_id);
```

### Problem

```cypher
(:Problem {
    id: "uuid",
    tenant_id: "uuid",
    intent: "string",       -- REPAIR, PURCHASE, etc.
    stage: "string",        -- NEW, QUOTED, etc.
    description: "string",
    created_at: datetime
})

CREATE INDEX problem_id FOR (p:Problem) ON (p.id);
CREATE INDEX problem_stage FOR (p:Problem) ON (p.stage);
```

---

## Связи (Relationships)

### Клиент — Устройство

```cypher
-- Владеет устройством
(client:Client)-[:OWNS]->(device:Device)

-- Принёс на ремонт (не владелец)
(client:Client)-[:BROUGHT_FOR_REPAIR {
    date: datetime
}]->(device:Device)

-- Пользуется (не владелец)
(client:Client)-[:USES]->(device:Device)

-- Управляет (IT-отдел для корпоративных)
(client:Client)-[:MANAGES]->(device:Device)
```

### Клиент — Клиент

```cypher
-- Семейные связи
(client_a:Client)-[:SPOUSE]->(client_b:Client)
(client_a:Client)-[:PARENT_OF]->(client_b:Client)
(client_a:Client)-[:CHILD_OF]->(client_b:Client)

-- Социальные связи
(client_a:Client)-[:FRIEND]->(client_b:Client)
(client_a:Client)-[:COLLEAGUE]->(client_b:Client)

-- Реферальная связь
(client_a:Client)-[:REFERRED_BY {
    date: datetime,
    campaign_id: "uuid"
}]->(client_b:Client)
```

### Устройство — Проблема

```cypher
-- У устройства есть проблема
(device:Device)-[:HAS_PROBLEM]->(problem:Problem)
```

### Клиент — Касание (Touchpoint)

```cypher
-- Касание (только в Neo4j для быстрого обхода)
-- Данные касания в PostgreSQL
(client:Client)-[:TOUCHED {
    touchpoint_id: "uuid",
    type: "message",          -- message, call, visit, promo
    direction: "inbound",     -- inbound, outbound, promo
    channel: "telegram",
    timestamp: datetime
}]->(device:Device)

-- Касание без устройства (вопрос, спам)
(client:Client)-[:TOUCHED {
    touchpoint_id: "uuid",
    type: "message",
    direction: "inbound",
    channel: "telegram",
    timestamp: datetime
}]->(problem:Problem)
```

---

## Диаграмма графа

```
                            ┌─────────────┐
                            │   Client    │
                            │   (Иван)    │
                            └──────┬──────┘
                                   │
              ┌────────────────────┼────────────────────┐
              │                    │                    │
        [:SPOUSE]            [:OWNS]             [:BROUGHT_FOR_REPAIR]
              │                    │                    │
              ▼                    ▼                    ▼
      ┌─────────────┐      ┌─────────────┐      ┌─────────────┐
      │   Client    │      │   Device    │      │   Device    │
      │   (Мария)   │      │ iPhone 14   │      │ iPhone 13   │
      └──────┬──────┘      │   Pro       │      │   (жены)    │
             │             └──────┬──────┘      └──────┬──────┘
             │                    │                    │
        [:OWNS]            [:HAS_PROBLEM]       [:HAS_PROBLEM]
             │                    │                    │
             │                    ▼                    ▼
             │             ┌─────────────┐      ┌─────────────┐
             │             │   Problem   │      │   Problem   │
             │             │   Экран     │      │   Экран     │
             │             │ [IN_WORK]   │      │ [AWAITING]  │
             │             └─────────────┘      └─────────────┘
             │                                        ▲
             └─────────────────[:OWNS]────────────────┘
```

---

## Ключевые запросы

### 1. Найти все устройства клиента и его сети

```cypher
// Все устройства, к которым клиент имеет отношение
MATCH (c:Client {id: $clientId})
OPTIONAL MATCH (c)-[:OWNS|BROUGHT_FOR_REPAIR|USES|MANAGES]->(d:Device)
OPTIONAL MATCH (c)-[:SPOUSE|PARENT_OF|CHILD_OF|FRIEND|COLLEAGUE]-(related:Client)
OPTIONAL MATCH (related)-[:OWNS]->(rd:Device)
RETURN c, collect(DISTINCT d) as my_devices, collect(DISTINCT rd) as related_devices
```

### 2. Найти клиента по каналу

```cypher
// Найти клиента по telegram_id
MATCH (c:Client {telegram_id: $telegramId, tenant_id: $tenantId})
RETURN c
```

### 3. Получить контекст для диалога (ЛИНИЯ)

```cypher
// Полная линия: клиент → устройства → проблемы
MATCH (c:Client {id: $clientId})
OPTIONAL MATCH (c)-[:OWNS|BROUGHT_FOR_REPAIR]->(d:Device)
OPTIONAL MATCH (d)-[:HAS_PROBLEM]->(p:Problem)
WHERE p.stage NOT IN ['DELIVERED', 'CANCELLED']
RETURN c, collect({
    device: d,
    problems: collect(p)
}) as devices
```

### 4. Найти связанных клиентов

```cypher
MATCH (c:Client {id: $clientId})-[r]-(related:Client)
WHERE type(r) IN ['SPOUSE', 'PARENT_OF', 'CHILD_OF', 'FRIEND', 'COLLEAGUE', 'REFERRED_BY']
RETURN related, type(r) as relation
```

### 5. История касаний по устройству (от всех связанных клиентов)

```cypher
MATCH (d:Device {id: $deviceId})<-[:TOUCHED]-(c:Client)
RETURN c.name, r.type, r.direction, r.channel, r.timestamp
ORDER BY r.timestamp DESC
LIMIT 50
```

### 6. Граф для обогащения — кто кого привёл

```cypher
// Цепочка рефералов
MATCH path = (c:Client {id: $clientId})-[:REFERRED_BY*1..5]->(referrer:Client)
RETURN path
```

---

## Синхронизация PostgreSQL ↔ Neo4j

### Принцип: PostgreSQL = source of truth

1. **Создание сущности** → Создать в PostgreSQL → Webhook → Создать в Neo4j
2. **Обновление** → Обновить в PostgreSQL → Webhook → Обновить в Neo4j
3. **Удаление** → Удалить в PostgreSQL → Webhook → Удалить в Neo4j

### Webhook endpoints

```
POST /webhook/neo4j/entity/sync
{
    "entity_type": "client" | "device" | "problem",
    "action": "create" | "update" | "delete",
    "entity_id": "uuid",
    "tenant_id": "uuid",
    "data": { ... }
}

POST /webhook/neo4j/relation/sync
{
    "relation_type": "OWNS" | "SPOUSE" | "REFERRED_BY" | etc,
    "action": "create" | "delete",
    "from_id": "uuid",
    "to_id": "uuid",
    "properties": { ... }
}

POST /webhook/neo4j/touchpoint/register
{
    "client_id": "uuid",
    "device_id": "uuid",        // optional
    "problem_id": "uuid",       // optional
    "type": "message",
    "direction": "inbound",
    "channel": "telegram",
    "touchpoint_id": "uuid"
}
```

---

## Миграция

### Шаг 1: Создать constraints и indexes

```cypher
CREATE CONSTRAINT client_id_unique FOR (c:Client) REQUIRE c.id IS UNIQUE;
CREATE CONSTRAINT device_id_unique FOR (d:Device) REQUIRE d.id IS UNIQUE;
CREATE CONSTRAINT problem_id_unique FOR (p:Problem) REQUIRE p.id IS UNIQUE;
```

### Шаг 2: Мигрировать существующих клиентов

```cypher
// Batch import из PostgreSQL
UNWIND $clients as client
MERGE (c:Client {id: client.id})
SET c += client
```

### Шаг 3: Создать связи OWNS из appeal_devices

```cypher
// Из существующих данных
MATCH (c:Client {id: $clientId})
MATCH (d:Device {id: $deviceId})
MERGE (c)-[:OWNS]->(d)
```

---

## Следующий шаг

→ `04_API_CONTRACTS.md` — API контракты для Android приложения
