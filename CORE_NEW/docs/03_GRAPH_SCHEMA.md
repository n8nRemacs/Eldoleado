# CORE_NEW: Схема графа Neo4j

## Зачем граф

PostgreSQL хранит данные. Neo4j хранит **связи** и обеспечивает:

1. **Быстрый поиск связей** — кто связан с кем
2. **Обогащение профиля** — что знаем о клиенте
3. **Контекст диалога** — вся история отношений
4. **Рефералы и сети** — кто кого привёл

### Префикс ELO_

Все лейблы имеют префикс `ELO_` для отделения от старых данных:
- `ELO_Client`, `ELO_Dialog`, `ELO_Fact`
- Можно запускать параллельно со старым графом

---

## Узлы (Nodes)

### ELO_Client — Клиент

```cypher
(:ELO_Client {
    id: "uuid",                 -- синхронизирован с PostgreSQL
    tenant_id: "uuid",

    -- Идентификаторы (для поиска)
    phone: "+79161234567",
    telegram_id: "123456789",
    whatsapp_id: "79161234567",

    -- Базовые данные
    name: "Иван Петров",

    -- Метрики (обновляются автоматически)
    total_dialogs: 5,
    total_completed: 3,
    last_contact_at: datetime,

    created_at: datetime
})
```

**Индексы:**
```cypher
CREATE INDEX elo_client_id FOR (c:ELO_Client) ON (c.id);
CREATE INDEX elo_client_tenant FOR (c:ELO_Client) ON (c.tenant_id);
CREATE INDEX elo_client_phone FOR (c:ELO_Client) ON (c.phone);
CREATE INDEX elo_client_telegram FOR (c:ELO_Client) ON (c.telegram_id);
```

### ELO_Dialog — Диалог

```cypher
(:ELO_Dialog {
    id: "uuid",
    tenant_id: "uuid",

    channel: "telegram",
    status: "active",           -- active, completed, archived

    -- Контекст (дублируется из PostgreSQL для быстрого доступа)
    intent: "repair",
    stage: "quoted",

    created_at: datetime,
    last_message_at: datetime
})
```

### ELO_Fact — Факт о клиенте

```cypher
(:ELO_Fact {
    id: "uuid",
    tenant_id: "uuid",

    type: "preference",         -- preference, personal, device, location
    value: "предпочитает оригинальные запчасти",

    confidence: 0.9,
    source_dialog_id: "uuid",   -- откуда узнали

    created_at: datetime
})
```

Примеры фактов:
- `{type: "preference", value: "хочет быстро"}`
- `{type: "personal", value: "есть жена Мария"}`
- `{type: "device", value: "iPhone 14 Pro, белый"}`
- `{type: "location", value: "работает рядом с ТЦ"}`

---

## Связи (Relationships)

### ELO_Client ↔ ELO_Dialog

```cypher
-- Клиент участвует в диалоге
(client:ELO_Client)-[:ELO_HAS_DIALOG]->(dialog:ELO_Dialog)
```

### ELO_Client ↔ ELO_Client

```cypher
-- Семейные связи
(client_a:ELO_Client)-[:ELO_FAMILY {
    type: "spouse"              -- spouse, parent, child, sibling
}]->(client_b:ELO_Client)

-- Социальные связи
(client_a:ELO_Client)-[:ELO_KNOWS {
    type: "friend"              -- friend, colleague, neighbor
}]->(client_b:ELO_Client)

-- Реферальная связь
(referrer:ELO_Client)-[:ELO_REFERRED {
    dialog_id: "uuid",          -- в каком диалоге привёл
    created_at: datetime
}]->(referred:ELO_Client)
```

### ELO_Client ↔ ELO_Fact

```cypher
-- Факт относится к клиенту
(client:ELO_Client)-[:ELO_HAS_FACT]->(fact:ELO_Fact)
```

---

## Диаграмма

```
                    ┌──────────────┐
                    │ ELO_Client   │
                    │   (Иван)     │
                    └──────┬───────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
 [:ELO_FAMILY]     [:ELO_HAS_DIALOG]   [:ELO_HAS_FACT]
   type:spouse             │                  │
        │                  │                  │
        ▼                  ▼                  ▼
┌──────────────┐   ┌──────────────┐   ┌──────────────┐
│ ELO_Client   │   │ ELO_Dialog   │   │  ELO_Fact    │
│   (Мария)    │   │ iPhone экран │   │ "оригинал"   │
└──────────────┘   │   repair     │   └──────────────┘
        │          │   completed  │
        │          └──────────────┘
 [:ELO_HAS_DIALOG]
        │
        ▼
┌──────────────┐
│ ELO_Dialog   │
│ iPhone 13    │
│   repair     │
└──────────────┘
```

---

## Ключевые запросы

### 1. Найти клиента со всеми связями

```cypher
MATCH (c:ELO_Client {id: $clientId})
OPTIONAL MATCH (c)-[:ELO_HAS_DIALOG]->(d:ELO_Dialog)
OPTIONAL MATCH (c)-[r:ELO_FAMILY|ELO_KNOWS|ELO_REFERRED]-(related:ELO_Client)
OPTIONAL MATCH (c)-[:ELO_HAS_FACT]->(f:ELO_Fact)
RETURN c,
       collect(DISTINCT d) as dialogs,
       collect(DISTINCT {client: related, relation: type(r), props: r}) as relations,
       collect(DISTINCT f) as facts
```

### 2. Контекст для AI перед ответом

```cypher
// Всё что знаем о клиенте для формирования ответа
MATCH (c:ELO_Client {id: $clientId})
OPTIONAL MATCH (c)-[:ELO_HAS_FACT]->(f:ELO_Fact)
OPTIONAL MATCH (c)-[:ELO_HAS_DIALOG]->(d:ELO_Dialog)
WHERE d.status = 'completed'
OPTIONAL MATCH (c)-[:ELO_FAMILY|ELO_KNOWS]-(related:ELO_Client)
RETURN c.name as name,
       collect(DISTINCT f.value) as facts,
       count(DISTINCT d) as completed_dialogs,
       collect(DISTINCT related.name) as related_people
```

### 3. Найти клиента по каналу

```cypher
MATCH (c:ELO_Client {tenant_id: $tenantId, telegram_id: $telegramId})
RETURN c
```

### 4. Цепочка рефералов

```cypher
// Кто кого привёл (до 5 уровней)
MATCH path = (c:ELO_Client {id: $clientId})<-[:ELO_REFERRED*1..5]-(referrer:ELO_Client)
RETURN path
```

### 5. Все клиенты семьи/круга

```cypher
// Клиент и все связанные люди
MATCH (c:ELO_Client {id: $clientId})-[:ELO_FAMILY|ELO_KNOWS*1..2]-(related:ELO_Client)
RETURN DISTINCT related
```

### 6. Поиск похожих клиентов (для рекомендаций)

```cypher
// Клиенты с похожими фактами
MATCH (c:ELO_Client {id: $clientId})-[:ELO_HAS_FACT]->(f:ELO_Fact)
MATCH (other:ELO_Client)-[:ELO_HAS_FACT]->(f2:ELO_Fact)
WHERE other.id <> c.id
  AND other.tenant_id = c.tenant_id
  AND f2.value = f.value
RETURN other, count(f2) as common_facts
ORDER BY common_facts DESC
LIMIT 10
```

---

## Синхронизация PostgreSQL → Neo4j

### Принцип

PostgreSQL = источник правды. Neo4j = read-optimized projection.

### Триггеры синхронизации

**При создании клиента:**
```cypher
MERGE (c:ELO_Client {id: $id})
SET c.tenant_id = $tenant_id,
    c.phone = $phone,
    c.telegram_id = $telegram_id,
    c.name = $name,
    c.created_at = datetime()
```

**При создании диалога:**
```cypher
// Создать ELO_Dialog
MERGE (d:ELO_Dialog {id: $id})
SET d.tenant_id = $tenant_id,
    d.channel = $channel,
    d.status = $status,
    d.created_at = datetime()

// Связать с клиентом
MATCH (c:ELO_Client {id: $client_id})
MERGE (c)-[:ELO_HAS_DIALOG]->(d)
```

**При извлечении факта AI:**
```cypher
// Создать ELO_Fact
CREATE (f:ELO_Fact {
    id: randomUUID(),
    tenant_id: $tenant_id,
    type: $type,
    value: $value,
    confidence: $confidence,
    source_dialog_id: $dialog_id,
    created_at: datetime()
})

// Связать с клиентом
MATCH (c:ELO_Client {id: $client_id})
CREATE (c)-[:ELO_HAS_FACT]->(f)
```

**При обнаружении связи между клиентами:**
```cypher
MATCH (c1:ELO_Client {id: $client1_id})
MATCH (c2:ELO_Client {id: $client2_id})
MERGE (c1)-[r:ELO_FAMILY {type: $relation_type}]->(c2)
```

---

## Webhook API

```
POST /webhook/neo4j/sync
{
    "event": "client.created" | "dialog.created" | "fact.extracted" | "relation.created",
    "tenant_id": "uuid",
    "data": { ... }
}
```

---

## Отличие от предыдущей схемы

| Было | Стало |
|------|-------|
| Device, Problem узлы | Нет отдельных узлов — данные в Dialog.context |
| OWNS, BROUGHT_FOR_REPAIR | Упрощено до связей между Client |
| Сложная иерархия | Плоская структура: Client → Dialog, Client → Fact |

### Почему упростили

В новой концепции устройства и проблемы — это **контекст диалога**, а не отдельные сущности. AI извлекает их и хранит в `dialogs.context` (PostgreSQL).

Граф нужен для:
- Связей между людьми
- Фактов о клиенте
- Быстрого поиска контекста

---

## MVP

Для MVP достаточно:

1. **Client** — узлы клиентов
2. **Dialog** — узлы диалогов
3. **HAS_DIALOG** — связь клиент → диалог
4. **REFERRED** — реферальная связь

Факты и семейные связи — на следующем этапе.
