# Neo4j Workflows для импорта в n8n

## Содержимое папки

| Файл | Назначение | Эндпоинт |
|------|------------|----------|
| `BAT Neo4j CRUD.json` | Базовые CRUD операции с защитой от инъекций | POST /neo4j/crud |
| `BAT Neo4j Sync.json` | Синхронизация PostgreSQL → Neo4j | POST /neo4j/sync |
| `BAT Neo4j Touchpoint Tracker.json` | Трекинг точек касания для disambiguation | POST /neo4j/touchpoint |

---

## Порядок установки

### 1. Создать индексы в Neo4j

Выполнить скрипт `database/neo4j/001_create_indexes.cypher` в Neo4j Browser:
```
https://45.144.177.128:7474/browser/
```

### 2. Импортировать workflows в n8n

1. Открыть n8n
2. Перейти в Workflows → Import from file
3. Импортировать файлы из этой папки
4. Активировать workflows

---

## API Reference

### BAT Neo4j CRUD

**Endpoint:** `POST /neo4j/crud`

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

**Endpoint:** `POST /neo4j/sync`

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

**Endpoint:** `POST /neo4j/touchpoint`

Создаёт Touchpoint узел и связи для отслеживания упоминаний устройств.

**Пример (входящее сообщение об устройстве):**
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

## Интеграция в основной поток

### Вызов из BAT Client Creator

После `INSERT INTO clients`:
```javascript
// HTTP Request к /neo4j/sync
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
// HTTP Request к /neo4j/touchpoint
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
// HTTP Request к /neo4j/context
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
