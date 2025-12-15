# AI Contour Architecture v2

## Overview

AI Contour — 3 контура: Collector → Decision → Executor

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
│  Контур 2: DECISION (Rules Engine)                                  │
│  ELO_Decision                                                       │
│                                                                     │
│  • Load rules from elo_v_decision_rules                             │
│  • Evaluate conditions against context                              │
│  • Select prompt from elo_v_prompts                                 │
└─────────────────────────────┬───────────────────────────────────────┘
                              ↓
                     Instruction + Context
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

## Workflows (11 files)

### Main Contours

| Workflow | Webhook | Роль |
|----------|---------|------|
| **ELO_Context_Collector** | `elo-context-collector` | AI Agent: сбор контекста |
| **ELO_Decision** | `elo-decision` | Rules Engine: выбор промпта |
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

### Other

| Workflow | Роль |
|----------|------|
| **ELO_Core_Stage_Manager** | Управление стадиями (legacy, логика в Decision) |
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

## Контур 2: ELO_Decision

**Type:** Rules Engine (no AI, deterministic)

**Input:**
```json
{
  "context": { ... }
}
```

**Logic:**
1. Load rules from `elo_v_decision_rules` (sorted by priority DESC)
2. Evaluate each rule's conditions against context
3. First matching rule wins
4. Load prompt template from `elo_v_prompts`
5. Substitute variables in template
6. Return instruction for Executor

**Database Tables:**
| Table | Purpose |
|-------|---------|
| `elo_v_decision_rules` | Rules: conditions → actions (JSONB) |
| `elo_v_prompts` | Prompt templates with variables |

**Condition Operators:**
```json
{
  "field": "value",           // exact match
  "field": null,              // field is null/missing
  "field": {"$exists": true}, // field exists
  "field": {"$gt": 100},      // greater than
  "field": {"$in": ["a","b"]} // one of
}
```

**Output:**
```json
{
  "instruction": "Ask customer what device they have...",
  "goal": "ask_device",
  "prompt_id": "ask_device",
  "buttons": [...],
  "side_effects": [...],
  "context": { ... }
}
```

---

## Контур 3: ELO_Executor

**Type:** AI Agent + HTTP/Code Tools

**Input:**
```json
{
  "instruction": "Ask customer what device...",
  "goal": "ask_device",
  "context": { ... },
  "buttons": [...],
  "side_effects": [...]
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

## Decision Rules (примеры)

| Priority | Rule | Conditions | Action |
|----------|------|------------|--------|
| 200 | handle_cancel | intent=cancel | confirm_cancel |
| 190 | handle_greeting | intent=greeting, is_first_message | greeting |
| 100 | ask_device | stage=data_collection, device=null | ask_device |
| 90 | ask_symptom | stage=data_collection, device≠null, symptom=null | ask_symptom |
| 70 | present_offer | stage=presentation | present_offer |
| 60 | ask_agreement | stage=agreement | ask_agreement |
| 50 | booking_complete | stage=booking, all_slots_filled | confirmation |
| 10 | fallback | {} (always) | clarify |

---

## External Services

| Service | URL | Used By |
|---------|-----|---------|
| OpenRouter | openrouter.ai | Collector, Executor, AI Extract, Response Generator |
| Neo4j | 45.144.177.128:7474 | Context Builder, Graph Writer |
| PostgreSQL | 185.221.214.83:6544 | Decision, Derive, Triggers, Response Generator |
| Redis | 45.144.177.128:6379 | Context Builder |

---

## Database Tables

| Table | Purpose |
|-------|---------|
| `elo_v_decision_rules` | Decision rules (conditions → actions) |
| `elo_v_prompts` | Prompt templates |
| `elo_v_funnel_stages` | Stage definitions |
| `elo_v_triggers` | Trigger conditions |

---

## Full Flow Example

```
1. Message: "Разбил экран на айфоне"
                    ↓
2. Collector:
   - extract_entities → {device: iPhone, symptom: разбит экран}
   - derive_chain → {diagnosis: display_damaged, price: 5000}
                    ↓
3. Context:
   {stage: "data_collection", device: {...}, symptom: "...", price: 5000}
                    ↓
4. Decision:
   - Check rule [100] ask_device: device=null? NO
   - Check rule [90] ask_symptom: symptom=null? NO
   - Check rule [85] derive_chain: price=null? NO
   - Check rule [80] ready_for_presentation: all data? YES ✓
   → Action: transition to "presentation"
                    ↓
5. Decision (stage=presentation):
   - Check rule [70] present_offer: stage=presentation? YES ✓
   → prompt_id: "present_offer"
                    ↓
6. Executor:
   - generate_text(instruction, context)
   - format_response(channel=telegram)
                    ↓
7. Response:
   "iPhone, разбитый экран. Замена дисплея — 5000₽. Записать?"
   [Записаться] [Вопросы]
```

---

## Deleted Workflows

| Workflow | Причина |
|----------|---------|
| ELO_Core_AI_Pipeline | Заменён на Collector → Decision → Executor |
| ELO_Core_Lines_Analyzer | Логика в Collector |
