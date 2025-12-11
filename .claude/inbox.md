# Task for Junior (Cursor)
**From:** Claude (Senior)
**Date:** 2025-12-11
**trace_id:** task_004
**Priority:** HIGH

---

## Task: Create ALL n8n Workflow JSON Files for Import

Создай **готовые JSON файлы** для импорта в n8n UI для ВСЕХ контуров системы ELO.

**n8n версия:** 1.125.3
**n8n URL:** https://n8n.n8nsrv.ru
**Формат:** n8n export JSON (File → Export → JSON)

---

# ЧАСТЬ 1: ДОКУМЕНТАЦИЯ

## Внутренняя документация проекта

### Главные документы:

| Документ | Путь | Описание |
|----------|------|----------|
| **API_CONTRACTS.md** | `NEW/Core_info/API_CONTRACTS.md` | ВСЕ webhooks и API между контурами |
| **INDEX.md** | `NEW/Core_info/INDEX.md` | Общий индекс документации |

### Channel Layer (01_Channel_Layer):

| Документ | Путь |
|----------|------|
| **CHANNEL_CONTOUR_OVERVIEW.md** | `NEW/Core_info/01_Channel_Layer/CHANNEL_CONTOUR_OVERVIEW.md` |
| ELO_In_Telegram.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_Telegram.md` |
| ELO_In_WhatsApp.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_WhatsApp.md` |
| ELO_In_Avito.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_Avito.md` |
| ELO_In_VK.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_VK.md` |
| ELO_In_MAX.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_MAX.md` |
| ELO_In_Form.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_Form.md` |
| ELO_In_Phone.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_Phone.md` |
| ELO_Out_Telegram.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_Out_Telegram.md` |
| ELO_Out_WhatsApp.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_Out_WhatsApp.md` |
| ELO_Out_Avito.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_Out_Avito.md` |
| ELO_Out_VK.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_Out_VK.md` |
| ELO_Out_MAX.md | `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_Out_MAX.md` |

### Input Contour (02_Input_Contour):

| Документ | Путь |
|----------|------|
| **INPUT_CONTOUR_OVERVIEW.md** | `NEW/Core_info/02_Input_Contour/workflows_info/INPUT_CONTOUR_OVERVIEW.md` |
| **ELO_Input_Ingest.md** | `NEW/Core_info/02_Input_Contour/workflows_info/ELO_Input_Ingest.md` |
| **ELO_Input_Worker.md** | `NEW/Core_info/02_Input_Contour/workflows_info/ELO_Input_Worker.md` |
| MCP_TRANSITION.md | `NEW/Core_info/02_Input_Contour/MCP_TRANSITION.md` |

### Client Contour (03_Client_Contour):

| Документ | Путь |
|----------|------|
| **CLIENT_CONTOUR_OVERVIEW.md** | `NEW/Core_info/03_Client_Contour/workflows_info/CLIENT_CONTOUR_OVERVIEW.md` |
| **ELO_Client_Resolve.md** | `NEW/Core_info/03_Client_Contour/workflows_info/ELO_Client_Resolve.md` |

### Graph Contour (04_Graph):

| Документ | Путь |
|----------|------|
| **GRAPH_OVERVIEW.md** | `NEW/Core_info/04_Graph/workflows_info/GRAPH_OVERVIEW.md` |
| **ELO_Graph_Query.md** | `NEW/Core_info/04_Graph/workflows_info/ELO_Graph_Query.md` |

### Core Contour (05_Core_Contour):

| Документ | Путь |
|----------|------|
| **CORE_CONTOUR_OVERVIEW.md** | `NEW/Core_info/05_Core_Contour/CORE_CONTOUR_OVERVIEW.md` |
| ELO_Core_Ingest.md | `NEW/Core_info/05_Core_Contour/workflows_info/ELO_Core_Ingest.md` |
| ELO_Core_Context_Builder.md | `NEW/Core_info/05_Core_Contour/workflows_info/ELO_Core_Context_Builder.md` |
| ELO_Core_AI_Extractor.md | `NEW/Core_info/05_Core_Contour/workflows_info/ELO_Core_AI_Extractor.md` |
| ELO_Core_Graph_Writer.md | `NEW/Core_info/05_Core_Contour/workflows_info/ELO_Core_Graph_Writer.md` |
| ELO_Core_Response_Builder.md | `NEW/Core_info/05_Core_Contour/workflows_info/ELO_Core_Response_Builder.md` |

---

## Внешняя документация n8n

### КРИТИЧЕСКИ ВАЖНЫЕ:

| Документ | URL | Зачем |
|----------|-----|-------|
| **v2.0 Breaking Changes** | https://docs.n8n.io/2-0-breaking-changes/ | ОБЯЗАТЕЛЬНО изучить! |
| **n8n Docs Home** | https://docs.n8n.io/ | Главная справка |

### Документация по нодам:

| Node | URL |
|------|-----|
| **Webhook** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.webhook/ |
| **Code** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.code/ |
| **HTTP Request** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.httprequest/ |
| **Postgres** | https://docs.n8n.io/integrations/builtin/app-nodes/n8n-nodes-base.postgres/ |
| **Redis** | https://docs.n8n.io/integrations/builtin/app-nodes/n8n-nodes-base.redis/ |
| **IF** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.if/ |
| **Switch** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.switch/ |
| **Respond to Webhook** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.respondtowebhook/ |
| **Schedule Trigger** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.scheduletrigger/ |
| **Execute Workflow** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.executeworkflow/ |
| **Set** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.set/ |
| **Merge** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.merge/ |
| **Loop Over Items** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.splitinbatches/ |
| **Item Lists** | https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.itemlists/ |

### Общие руководства:

| Тема | URL |
|------|-----|
| **Expressions** | https://docs.n8n.io/code/expressions/ |
| **Built-in methods** | https://docs.n8n.io/code/builtin/ |
| **Data transformation** | https://docs.n8n.io/data/ |
| **Error handling** | https://docs.n8n.io/flow-logic/error-handling/ |
| **Credentials** | https://docs.n8n.io/credentials/ |
| **Workflow JSON format** | https://docs.n8n.io/api/workflow-definition/ |

---

# ЧАСТЬ 2: BREAKING CHANGES v2.0

## КРИТИЧНО: n8n v2.0 Breaking Changes

**Текущая версия:** 1.125.3
**Готовимся к:** v2.0 (выходит через 5 дней!)

Изучи ОБЯЗАТЕЛЬНО: https://docs.n8n.io/2-0-breaking-changes/

---

### КРАСНЫЕ ФЛАГИ — ЗАПРЕЩЕНО ИСПОЛЬЗОВАТЬ:

| # | Что НЕЛЬЗЯ | Почему | Что использовать ВМЕСТО |
|---|------------|--------|-------------------------|
| 1 | **Python Code Node** | УДАЛЁН в v2 (Pyodide removed) | JavaScript Code Node |
| 2 | **`process.env.XXX`** в Code Node | Заблокирован доступ к env | n8n Credentials |
| 3 | **ExecuteCommand Node** | Отключён по умолчанию | HTTP Request к внешним сервисам |
| 4 | **LocalFileTrigger Node** | Отключён по умолчанию | Webhook Trigger |
| 5 | **MySQL/MariaDB** credentials | Поддержка УДАЛЕНА | PostgreSQL |
| 6 | **In-memory binary data** | Режим УДАЛЁН | File storage / S3 |
| 7 | **Frontend workflow hooks** | DEPRECATED | Backend hooks |
| 8 | **`n8n --tunnel`** | УДАЛЁН | Reverse proxy (nginx) |
| 9 | **CLI `update:workflow`** | Команда УДАЛЕНА | API или UI |
| 10 | **SQLite legacy driver** | УДАЛЁН | Новый SQLite или PostgreSQL |

---

### ОБЯЗАТЕЛЬНЫЕ ПРАКТИКИ для v2-совместимости:

```
✅ DO:
- Credentials для всех подключений (DB, API keys)
- JavaScript Code Node (typeVersion: 2)
- Webhook Node (typeVersion: 2)
- HTTP Request Node для внешних вызовов
- PostgreSQL Node с credentials
- Respond to Webhook Node для ответов
- Schedule Trigger для периодических задач

❌ DON'T:
- process.env.DATABASE_URL в коде
- Python код
- ExecuteCommand для shell команд
- Hardcoded passwords в SQL
- LocalFileTrigger
- MySQL/MariaDB
```

---

### Node Type Versions (используй ТОЛЬКО эти):

| Node | typeVersion | Примечание |
|------|-------------|------------|
| Webhook | 2 | Обязательно `responseMode: "responseNode"` |
| Code | 2 | JavaScript only |
| HTTP Request | 4.2 | Новейший |
| Postgres | 2.5 | С credentials |
| Redis | 1 | С credentials |
| IF | 2 | — |
| Respond to Webhook | 1.1 | — |
| Schedule Trigger | 1.2 | — |
| Set | 3.4 | — |
| Switch | 3 | — |

---

### Sub-workflow Warning:

В v2 изменилось поведение return data из sub-workflows с Wait/Form/HITL.
Если используешь Execute Workflow → проверь что возвращается после resume.

---

# ЧАСТЬ 3: WORKFLOWS TO CREATE

## Архитектура системы:

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           CHANNEL LAYER                                      │
│  Telegram → WhatsApp → Avito → VK → MAX → Form → Phone                      │
│  (ELO_In_*)                                    (ELO_Out_*)                   │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼ POST /ingest
┌─────────────────────────────────────────────────────────────────────────────┐
│  INPUT CONTOUR (8771)                                                        │
│  ELO_Input_Ingest → Redis Queue → ELO_Input_Worker                          │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼ POST /resolve
┌─────────────────────────────────────────────────────────────────────────────┐
│  CLIENT CONTOUR (8772)                                                       │
│  ELO_Client_Resolve → Tenant → Client → Dialog                              │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼ POST /webhook/elo-core-ingest
┌─────────────────────────────────────────────────────────────────────────────┐
│  CORE CONTOUR (n8n)                                                          │
│  ELO_Core_Ingest → Context Builder → AI → Graph Writer → Response Builder   │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼ POST /query
┌─────────────────────────────────────────────────────────────────────────────┐
│  GRAPH TOOL (8773)                                                           │
│  ELO_Graph_Query (прокси к Neo4j)                                           │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Deliverable 1: Input Contour (PRIORITY: HIGH)

### 1.1 ELO_Input_Ingest.json

**Путь:** `NEW/workflows/ELO_Input/ELO_Input_Ingest.json`
**Документация:** `NEW/Core_info/02_Input_Contour/workflows_info/ELO_Input_Ingest.md`

**Назначение:** Приём сообщений от Channel Layer, проверка идемпотентности, добавление в Redis очередь.

```
Webhook (POST /webhook/elo-input-ingest)
    │
    ▼
Code: Generate message_id + trace_id
    │
    ▼
Redis: SISMEMBER processed:{message_id} ──EXISTS──▶ Respond: {accepted: true, duplicate: true}
    │
    NOT EXISTS
    ▼
Redis: SADD processed:{message_id} (TTL 24h)
    │
    ▼
Redis: RPUSH queue:incoming (JSON message)
    │
    ▼
Respond to Webhook: {accepted: true, message_id, trace_id}
```

### 1.2 ELO_Input_Worker.json

**Путь:** `NEW/workflows/ELO_Input/ELO_Input_Worker.json`
**Документация:** `NEW/Core_info/02_Input_Contour/workflows_info/ELO_Input_Worker.md`

**Назначение:** Обработка очереди с debounce (10s silence / 300s max wait).

```
Schedule Trigger (every 2 seconds)
    │
    ▼
Redis: 10x LPOP queue:incoming
    │
    ▼
Code: Group by batch_key (tenant:channel:chat_id)
    │
    ├── Empty? ──YES──▶ Check Deadlines
    │
    └── NO
        │
        ▼
    For each group:
        Redis: RPUSH queue:batch:{key}
        Redis: ZADD batch:deadlines (score=deadline)
        Redis: ZADD batch:first_seen NX (score=first_seen)
    │
    ▼
Redis: ZRANGEBYSCORE batch:deadlines -inf {now}
    │
    ├── No due batches ──▶ End
    │
    └── Has due batches
        │
        ▼
    For each due batch:
        Redis: LRANGE queue:batch:{key} 0 -1
        Code: Merge texts
        HTTP: POST to Client Contour /resolve
        Redis: DELETE + ZREM (cleanup)
```

**ВАЖНО: Debounce параметры:**
- `debounce_seconds: 10` — тишина перед отправкой
- `max_wait_seconds: 300` — максимум 5 минут ожидания

---

## Deliverable 2: Client Contour (PRIORITY: HIGH)

### 2.1 ELO_Client_Resolve.json

**Путь:** `NEW/workflows/ELO_Client/ELO_Client_Resolve.json`
**Документация:** `NEW/Core_info/03_Client_Contour/workflows_info/ELO_Client_Resolve.md`

**Назначение:** Резолвинг tenant → client → dialog по входящему сообщению.

```
Webhook (POST /webhook/elo-client-resolve)
    │
    ▼
Code: Validate Input
    │
    ▼
Postgres: Tenant Resolver ──NOT FOUND──▶ Redis: DLQ Push ──▶ Respond Error
    │
    FOUND
    ▼
Postgres: Client by External ID
    │
    ├── FOUND ────────────────────────────┐
    │                                      │
    └── NOT FOUND                          │
        │                                  │
        ▼                                  │
    Postgres: Client by Phone              │
        │                                  │
        ├── FOUND ─▶ Link Channel ────────┤
        │                                  │
        └── NOT FOUND                      │
            │                              │
            ▼                              │
        Postgres: Create Client            │
            │                              │
            ▼                              │
        HTTP: Neo4j Sync (8773) ──────────┤
                                           │
    ◀──────────────────────────────────────┘
    │
    ▼
Postgres: Find Active Dialog
    │
    ├── FOUND ────────────────────────────┐
    │                                      │
    └── NOT FOUND                          │
        │                                  │
        ▼                                  │
    Postgres: Create Dialog ──────────────┤
                                           │
    ◀──────────────────────────────────────┘
    │
    ▼
Code: Build Output
    │
    ├──▶ HTTP: Forward to Core (async, continue on fail)
    │
    ▼
Respond to Webhook: {accepted: true, tenant_id, client_id, dialog_id}
```

**SQL Queries — см. документацию ELO_Client_Resolve.md**

---

## Deliverable 3: Graph Proxy (PRIORITY: MEDIUM)

### 3.1 ELO_Graph_Query.json

**Путь:** `NEW/workflows/ELO_Graph/ELO_Graph_Query.json`
**Документация:** `NEW/Core_info/04_Graph/workflows_info/ELO_Graph_Query.md`

**Назначение:** Прокси к Graph Tool MCP (Neo4j).

```
Webhook (POST /webhook/elo-graph-query)
    │
    ▼
Code: Validate Input (query_code required)
    │
    ▼
HTTP Request: POST http://45.144.177.128:8773/query
    │
    ▼
Code: Add trace_id to response
    │
    ▼
Respond to Webhook
```

---

## Deliverable 3.5: AI Tool Proxy (PRIORITY: MEDIUM) — УЖЕ СОЗДАНО!

**ГОТОВО!** JSON файлы уже созданы Senior'ом:

### 3.5.1 ELO_AI_Extract.json ✅

**Путь:** `NEW/workflows/ELO_AI/ELO_AI_Extract.json`
**Документация:** `NEW/Core_info/06_AI_Tool/workflows_info/ELO_AI_Extract.md`

**Назначение:** Прокси к AI Tool MCP для извлечения данных.

```
Webhook (POST /webhook/elo-ai-extract)
    │
    ▼
Code: Validate Input (message, extraction_schema required)
    │
    ▼
HTTP Request: POST http://45.144.177.128:8774/extract
    │
    ▼
Code: Add trace_id to response
    │
    ▼
Respond to Webhook
```

### 3.5.2 ELO_AI_Chat.json ✅

**Путь:** `NEW/workflows/ELO_AI/ELO_AI_Chat.json`
**Документация:** `NEW/Core_info/06_AI_Tool/workflows_info/ELO_AI_Chat.md`

**Назначение:** Прокси к AI Tool MCP для AI чата с tools.

```
Webhook (POST /webhook/elo-ai-chat)
    │
    ▼
Code: Validate Input (messages array required)
    │
    ▼
HTTP Request: POST http://45.144.177.128:8774/chat
    │
    ▼
Code: Add trace_id to response
    │
    ▼
Respond to Webhook
```

**Импорт:** Можно сразу импортировать в n8n UI!

---

## Deliverable 4: Channel Layer IN (PRIORITY: MEDIUM)

**Общая структура для всех каналов:**

```
MCP Trigger (webhook from MCP adapter)
    │
    ▼
Code: Normalize to ELO Core Contract
    │
    ▼
IF: Has Voice? ──YES──▶ HTTP: OpenAI Whisper ──▶ Code: Append transcription
    │
    NO
    ▼
Code: Add message_id, trace_id
    │
    ▼
HTTP: POST to Input Contour /ingest
    │
    ▼
Respond to Webhook: {accepted: true}
```

### 4.1 ELO_In_Telegram.json
**Путь:** `NEW/workflows/ELO_InOut/ELO_In_Telegram.json`
**Документация:** `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_Telegram.md`

### 4.2 ELO_In_WhatsApp.json
**Путь:** `NEW/workflows/ELO_InOut/ELO_In_WhatsApp.json`
**Документация:** `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_WhatsApp.md`

### 4.3 ELO_In_Avito.json
**Путь:** `NEW/workflows/ELO_InOut/ELO_In_Avito.json`
**Документация:** `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_Avito.md`

### 4.4 ELO_In_VK.json
**Путь:** `NEW/workflows/ELO_InOut/ELO_In_VK.json`
**Документация:** `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_VK.md`

### 4.5 ELO_In_MAX.json
**Путь:** `NEW/workflows/ELO_InOut/ELO_In_MAX.json`
**Документация:** `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_MAX.md`

### 4.6 ELO_In_Form.json
**Путь:** `NEW/workflows/ELO_InOut/ELO_In_Form.json`
**Документация:** `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_In_Form.md`

---

## Deliverable 5: Channel Layer OUT (PRIORITY: LOW)

**Общая структура:**

```
Webhook (from Core)
    │
    ▼
Code: Response Builder (format for channel)
    │
    ▼
HTTP: Call MCP adapter to send
    │
    ▼
Postgres: Save outgoing message
    │
    ▼
Respond to Webhook
```

### 5.1 ELO_Out_Telegram.json
**Путь:** `NEW/workflows/ELO_InOut/ELO_Out_Telegram.json`
**Документация:** `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_Out_Telegram.md`

### 5.2 ELO_Out_WhatsApp.json
**Путь:** `NEW/workflows/ELO_InOut/ELO_Out_WhatsApp.json`
**Документация:** `NEW/Core_info/01_Channel_Layer/workflows_info/ELO_Out_WhatsApp.md`

### 5.3-5.5 ELO_Out_Avito/VK/MAX.json
**По аналогии с документацией**

---

# ЧАСТЬ 4: CREDENTIALS

## Создай в n8n UI:

| Name | Type | Connection |
|------|------|------------|
| **ELO_PostgreSQL** | PostgreSQL | `postgresql://app_user:Mi31415926pS@185.221.214.83:6544/postgres` |
| **ELO_Redis** | Redis | `redis://185.221.214.83:6379` |
| **OpenAI** | OpenAI | API Key (для транскрипции голоса) |

---

# ЧАСТЬ 5: JSON FORMAT

## Пример структуры n8n workflow JSON:

```json
{
  "name": "ELO_Input_Ingest",
  "nodes": [
    {
      "parameters": {
        "httpMethod": "POST",
        "path": "elo-input-ingest",
        "responseMode": "responseNode"
      },
      "id": "uuid-1",
      "name": "Webhook",
      "type": "n8n-nodes-base.webhook",
      "typeVersion": 2,
      "position": [250, 300]
    },
    {
      "parameters": {
        "jsCode": "const now = Date.now();\nconst message_id = `msg_${now}_${Math.random().toString(36).substr(2, 9)}`;\nconst trace_id = `trace_${now}_${Math.random().toString(36).substr(2, 9)}`;\n\nreturn {\n  ...items[0].json,\n  message_id,\n  trace_id\n};"
      },
      "id": "uuid-2",
      "name": "Generate IDs",
      "type": "n8n-nodes-base.code",
      "typeVersion": 2,
      "position": [450, 300]
    }
  ],
  "connections": {
    "Webhook": {
      "main": [[{"node": "Generate IDs", "type": "main", "index": 0}]]
    }
  },
  "settings": {
    "executionOrder": "v1"
  }
}
```

---

# ЧАСТЬ 6: ACCEPTANCE CRITERIA

## Для КАЖДОГО workflow:

- [ ] JSON файл импортируется в n8n без ошибок
- [ ] Webhooks доступны после активации workflow
- [ ] Нет Python Code Nodes
- [ ] Нет `process.env` в Code Nodes
- [ ] Postgres использует Credentials (не hardcoded)
- [ ] Redis использует Credentials
- [ ] typeVersion соответствует таблице выше
- [ ] Есть error handling (DLQ для критичных ошибок)

## Общие критерии:

- [ ] ELO_Input_Ingest: принимает сообщения, добавляет в Redis
- [ ] ELO_Input_Worker: debounce работает (10s silence)
- [ ] ELO_Client_Resolve: полный flow tenant → client → dialog
- [ ] ELO_Graph_Query: проксирует к Graph Tool MCP
- [ ] Channel IN: нормализация + POST to Input Contour
- [ ] Channel OUT: Response Builder + send via MCP

---

# ЧАСТЬ 7: RESOURCES

## Серверы:

| Service | URL | Purpose |
|---------|-----|---------|
| n8n | https://n8n.n8nsrv.ru | Workflow engine |
| PostgreSQL | 185.221.214.83:6544 | Main database |
| Redis | 185.221.214.83:6379 | Queue + cache |
| Graph Tool | http://45.144.177.128:8773 | Neo4j proxy |
| MCP Telegram | http://217.145.79.27:8767 | Telegram adapter |
| MCP WhatsApp | http://217.145.79.27:8766 | WhatsApp adapter |

## Существующие примеры workflows:

```
NEW/workflows/ELO_InOut/ — примеры IN/OUT workflows (если есть)
```

---

# ЧАСТЬ 8: HOW TO

## Как создать JSON

**Вариант 1 (рекомендуется):**
1. Создай workflow в n8n UI
2. File → Export → Download as JSON
3. Сохрани в указанный путь

**Вариант 2:**
1. Напиши JSON вручную по структуре выше
2. Проверь импорт в n8n

## Как тестировать

1. Импортируй JSON в n8n
2. Активируй workflow
3. Отправь тестовый запрос через curl:
```bash
curl -X POST https://n8n.n8nsrv.ru/webhook/elo-input-ingest \
  -H "Content-Type: application/json" \
  -d '{"channel": "telegram", "external_chat_id": "123", "text": "test"}'
```

---

# ЧАСТЬ 9: QUESTIONS

**Срок:** Качественно > быстро. Лучше переспросить, чем переделывать.

**Вопросы:** Пиши в `.claude/outbox.md`

**Формат вопроса:**
```markdown
## Question from Junior
**Date:** 2025-12-XX
**Topic:** [название workflow]

Вопрос...

Варианты которые рассматриваю:
1. ...
2. ...
```

---

**Good luck!**
