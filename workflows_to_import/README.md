# Neo4j Workflows — API Reference

Все Neo4j workflows уже активны на сервере n8n. Исходники хранятся в `n8n_workflows/Core/`.

## Workflows на сервере

| Workflow | Эндпоинт | Файл |
|----------|----------|------|
| BAT Neo4j CRUD | POST /webhook/neo4j/crud | `n8n_workflows/Core/BAT_Neo4j_CRUD.json` |
| BAT Neo4j Sync | POST /webhook/neo4j/sync | `n8n_workflows/Core/BAT_Neo4j_Sync.json` |
| BAT Neo4j Touchpoint Tracker | POST /webhook/neo4j/touchpoint | `n8n_workflows/Core/BAT_Neo4j_Touchpoint_Tracker.json` |
| BAT Neo4j Context Builder | POST /webhook/neo4j/context | `n8n_workflows/Core/BAT_Neo4j_Context_Builder.json` |

---

## API Reference

### BAT Neo4j CRUD

**Endpoint:** `POST https://n8n.n8nsrv.ru/webhook/neo4j/crud`

#### Операции с узлами

**Create:**
```json
{
  "operation": "create",
  "nodeType": "Client",
  "nodeId": "uuid-123",
  "properties": {
    "name": "Иван",
    "created_at": "2025-12-02"
  }
}
```

**Read:**
```json
{
  "operation": "read",
  "nodeType": "Client",
  "nodeId": "uuid-123"
}
```

**Update:**
```json
{
  "operation": "update",
  "nodeType": "Client",
  "nodeId": "uuid-123",
  "properties": {
    "name": "Иван Петров"
  }
}
```

**Delete:**
```json
{
  "operation": "delete",
  "nodeType": "Client",
  "nodeId": "uuid-123"
}
```

#### Операции с рёбрами

**Create Edge:**
```json
{
  "operation": "create_edge",
  "edgeType": "OWNS",
  "fromId": "client-uuid",
  "toId": "device-uuid",
  "edgeProperties": {
    "since": "2025-12-02",
    "primary": true
  }
}
```

**Delete Edge:**
```json
{
  "operation": "delete_edge",
  "edgeType": "OWNS",
  "fromId": "client-uuid",
  "toId": "device-uuid"
}
```

#### Допустимые типы

**nodeType:**
- Client, Device, Problem, ProblemType, Touchpoint, Channel, Vertical, Fingerprint

**edgeType:**
- OWNS, HAS_PROBLEM, OF_TYPE, SOCIAL, REFERRED, HAS_CHANNEL, IDENTIFIED_BY
- CUSTOMER_OF, FROM, TO, ABOUT_DEVICE, ABOUT_PROBLEM, REFERS_TO, VIA_CHANNEL, IN_VERTICAL

---

### BAT Neo4j Sync

**Endpoint:** `POST https://n8n.n8nsrv.ru/webhook/neo4j/sync`

Синхронизирует сущности из PostgreSQL в Neo4j.

**Create Client:**
```json
{
  "entity_type": "client",
  "operation": "create",
  "data": {
    "id": "uuid-123",
    "name": "Иван"
  }
}
```

**Create Device (с привязкой к клиенту):**
```json
{
  "entity_type": "device",
  "operation": "create",
  "data": {
    "id": "device-uuid",
    "brand": "Apple",
    "model": "iPhone 12",
    "owner_label": "свой"
  },
  "parent_id": "client-uuid"
}
```

**Create Problem (с привязкой к устройству):**
```json
{
  "entity_type": "problem",
  "operation": "create",
  "data": {
    "id": "problem-uuid",
    "status": "in_progress"
  },
  "parent_id": "device-uuid"
}
```

**Update:**
```json
{
  "entity_type": "device",
  "operation": "update",
  "data": {
    "id": "device-uuid",
    "status": "completed"
  }
}
```

**Delete:**
```json
{
  "entity_type": "device",
  "operation": "delete",
  "data": {
    "id": "device-uuid"
  }
}
```

---

### BAT Neo4j Touchpoint Tracker

**Endpoint:** `POST https://n8n.n8nsrv.ru/webhook/neo4j/touchpoint`

Создаёт Touchpoint узел и связи для отслеживания упоминаний устройств.

**Полный пример:**
```json
{
  "client_id": "client-uuid",
  "message_id": "msg-uuid",
  "channel": "whatsapp",
  "direction": "inbound",
  "type": "message",
  "mentioned_device_id": "device-uuid",
  "confidence": 0.95,
  "explicit": true
}
```

**Минимальный пример:**
```json
{
  "client_id": "client-uuid",
  "channel": "telegram",
  "direction": "inbound"
}
```

---

### BAT Neo4j Context Builder

**Endpoint:** `POST https://n8n.n8nsrv.ru/webhook/neo4j/context`

Получает контекст клиента из графа для AI-обработки.

#### Действия (action):

**get_context** — полный контекст клиента:
```json
{
  "client_id": "client-uuid",
  "action": "get_context"
}
```

Ответ:
```json
{
  "success": true,
  "client": {...},
  "devices": [{"id": "...", "brand": "Apple", "model": "iPhone 12", "owner_label": "свой"}],
  "problems": [{"id": "...", "status": "in_progress", "type": "display"}],
  "channels": [{"type": "whatsapp", "identifier": "+7..."}],
  "verticals": ["phone_repair"]
}
```

**disambiguation** — определение устройства для ответа:
```json
{
  "client_id": "client-uuid",
  "action": "disambiguation"
}
```

Ответ:
```json
{
  "success": true,
  "devices": [...],
  "needsClarification": false,
  "suggestedDevice": {"id": "...", "brand": "Apple", "model": "iPhone 12"},
  "clarificationReason": null
}
```

Логика disambiguation:
- 1 устройство → suggestedDevice, без уточнения
- Сегодня упоминали только одно → suggestedDevice
- Сегодня упоминали несколько → needsClarification = true
- Есть активный ремонт → приоритет этому устройству

**enrichment_suggestion** — предложения по обогащению профиля:
```json
{
  "client_id": "client-uuid",
  "action": "enrichment_suggestion"
}
```

Ответ:
```json
{
  "success": true,
  "clientId": "...",
  "existingChannels": [{"type": "whatsapp", "identifier": "..."}],
  "suggestions": [
    {"from_channel_type": "whatsapp", "to_channel_type": "telegram", "mechanism": "...", "conversion_rate": 0.15}
  ]
}
```

---

## Интеграция в основной поток

### Вызов из BAT Client Creator

После `INSERT INTO clients`:
```javascript
// HTTP Request к /webhook/neo4j/sync
{
  "entity_type": "client",
  "operation": "create",
  "data": {
    "id": "{{ $json.id }}",
    "created_at": "{{ $now.toISOString() }}"
  }
}
```

### Вызов из BAT Universal Batcher

После сохранения сообщения:
```javascript
// HTTP Request к /webhook/neo4j/touchpoint
{
  "client_id": "{{ $json.client_id }}",
  "message_id": "{{ $json.message_id }}",
  "channel": "{{ $json.channel }}",
  "direction": "inbound"
}
```

### Вызов из BAT AI Appeal Router

Перед генерацией ответа (для disambiguation):
```javascript
// HTTP Request к /webhook/neo4j/context
{
  "client_id": "{{ $json.client_id }}",
  "action": "disambiguation"
}
```

---

## Ответы API

### Успешный ответ
```json
{
  "success": true,
  "count": 1,
  "data": [...]
}
```

### Ошибка валидации
```json
{
  "success": false,
  "error": "Invalid nodeType: BadType. Allowed: Client, Device, ..."
}
```

### Ошибка Neo4j
```json
{
  "success": false,
  "error": "Neo4j error message"
}
```
