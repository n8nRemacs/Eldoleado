# AI Contour Architecture v2

## Overview

AI Contour — 3 контура с AI Agent + Tools архитектурой.

```
┌─────────────────────────────────────────────────────────────────────┐
│                         MESSAGE IN                                   │
└─────────────────────────────┬───────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────────┐
│  Контур 1: COLLECTOR (AI Agent)                                     │
│  ELO_Context_Collector                                              │
│                                                                     │
│  Tools: extract_entities, load_client, derive_chain, check_triggers│
└─────────────────────────────┬───────────────────────────────────────┘
                              ↓
                        Context Object
                              ↓
┌─────────────────────────────────────────────────────────────────────┐
│  Контур 2: DECISION (TODO)                                          │
│                                                                     │
│  • Stage Manager logic                                              │
│  • Prompt Selection                                                 │
└─────────────────────────────┬───────────────────────────────────────┘
                              ↓
                     Prompt + Parameters
                              ↓
┌─────────────────────────────────────────────────────────────────────┐
│  Контур 3: EXECUTOR (AI Agent)                                      │
│  ELO_Executor                                                       │
│                                                                     │
│  Tools: generate_text, write_graph, get_price, format_response      │
└─────────────────────────────┬───────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────────┐
│                        RESPONSE OUT                                  │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Workflows (10 files)

### Main Agents

| Workflow | Webhook | Роль |
|----------|---------|------|
| **ELO_Context_Collector** | `elo-context-collector` | AI Agent: сбор контекста |
| **ELO_Executor** | `elo-executor` | AI Agent: исполнение |

### Tools for Collector

| Workflow | Webhook | Роль |
|----------|---------|------|
| **ELO_AI_Extract** | `elo-ai-extract` | Извлечение entities (OpenRouter) |
| **ELO_Core_Context_Builder** | `elo-core-context-builder` | Загрузка контекста из Neo4j |
| **ELO_Core_AI_Derive** | `elo-core-ai-derive` | Цепочка: symptom→diagnosis→repair→price |
| **ELO_Core_Triggers_Checker** | `elo-core-triggers-checker` | Проверка триггеров |

### Tools for Executor

| Workflow | Webhook | Роль |
|----------|---------|------|
| **ELO_Core_Response_Generator** | `elo-core-response-generator` | Генерация текста (OpenRouter) |
| **ELO_Core_Graph_Writer** | `elo-core-graph-writer` | Запись в Neo4j |

### Decision (TODO)

| Workflow | Webhook | Роль |
|----------|---------|------|
| **ELO_Core_Stage_Manager** | `elo-core-stage-manager` | Управление стадиями (будет частью Decision) |

### Other

| Workflow | Роль |
|----------|------|
| **ELO_Core_AI_Test_Stub** | Тестовая заглушка |

---

## Контур 1: ELO_Context_Collector

**Type:** AI Agent + HTTP Tools

**Input:**
```json
{
  "tenant_id": "uuid",
  "client_id": "uuid",
  "dialog_id": "uuid",
  "text": "Разбил экран на айфоне 14",
  "channel_id": "telegram",
  "vertical_id": 1
}
```

**Tools:**
| Tool | Endpoint | Что делает |
|------|----------|------------|
| `extract_entities` | elo-ai-extract | device, symptom, intent из текста |
| `load_client_context` | elo-core-context-builder | история клиента из Neo4j |
| `derive_chain` | elo-core-ai-derive | diagnosis, repair, price |
| `check_triggers` | elo-core-triggers-checker | сработавшие триггеры |

**Output:**
```json
{
  "context": {
    "tenant_id": "...",
    "client_id": "...",
    "dialog_id": "...",
    "message": { "text": "...", "timestamp": "..." },
    "entities": [...],
    "lines": [{ "id": "line_0", "slots": {...} }],
    "derived": { "diagnosis": "...", "price": {...} },
    "triggers_fired": [...],
    "current_stage": "data_collection"
  }
}
```

---

## Контур 2: Decision (TODO)

**Пока не реализован.** Планируется:
- Граф решений
- Stage Manager logic
- Prompt Selection из БД

**Временно:** прямой вызов Executor с hardcoded промптом

---

## Контур 3: ELO_Executor

**Type:** AI Agent + HTTP/Code Tools

**Input:**
```json
{
  "context": { ... },
  "prompt": {
    "instruction": "Спроси какое устройство у клиента",
    "goal": "ask_device",
    "params": {}
  }
}
```

**Tools:**
| Tool | Type | Что делает |
|------|------|------------|
| `generate_text` | HTTP | AI генерация текста |
| `write_graph` | HTTP | Запись в Neo4j |
| `get_price` | Code | Цена из PostgreSQL |
| `format_response` | Code | Форматирование для канала |

**Output:**
```json
{
  "tenant_id": "...",
  "dialog_id": "...",
  "channel_id": "telegram",
  "external_chat_id": "...",
  "message": {
    "text": "Какое у вас устройство?",
    "buttons": [...]
  }
}
```

---

## External Services

| Service | URL | Used By |
|---------|-----|---------|
| OpenRouter | openrouter.ai | Context Collector, Executor, AI Extract, Response Generator |
| Neo4j | 45.144.177.128:7474 | Context Builder, Graph Writer |
| PostgreSQL | 185.221.214.83:6544 | Derive, Triggers, Stage Manager, Response Generator |
| Redis | 45.144.177.128:6379 | Context Builder |

---

## Deleted Workflows

| Workflow | Причина |
|----------|---------|
| ELO_Core_AI_Pipeline | Заменён на Collector → Decision → Executor |
| ELO_Core_Lines_Analyzer | Логика в Collector |

---

## Next Steps

1. **Decision контур** — граф решений, выбор промпта
2. **Тестирование** — Collector → Decision → Executor
3. **Оптимизация** — кэширование, параллельные вызовы где возможно
