# AI Contour Architecture

## Overview

AI Contour - набор из 10 n8n workflows для обработки диалогов с клиентами.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           ВНЕШНИЕ ВЫЗОВЫ                                     │
│  Input Contour → ELO_Core_AI_Pipeline → Output Contour                      │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                    ┌───────────────┼───────────────┐
                    ▼               ▼               ▼
              ┌──────────┐   ┌──────────┐   ┌──────────┐
              │ AI Tools │   │ Workers  │   │ Storage  │
              └──────────┘   └──────────┘   └──────────┘
```

---

## Workflows Summary

| # | Workflow | Webhook Path | Назначение |
|---|----------|--------------|------------|
| 1 | **ELO_Core_AI_Pipeline** | `elo-core-ai-pipeline` | Главный pipeline, вызывает все остальные последовательно |
| 2 | **ELO_AI_Extract** | `elo-ai-extract` | Извлечение сущностей из текста (OpenRouter AI) |
| 3 | **ELO_Core_Lines_Analyzer** | `elo-core-lines-analyzer` | Управление линиями (device+symptom) |
| 4 | **ELO_Core_AI_Derive** | `elo-core-ai-derive` | Цепочка: symptom→diagnosis→repair→price |
| 5 | **ELO_Core_Triggers_Checker** | `elo-core-triggers-checker` | Проверка триггеров (скидки, инфо) |
| 6 | **ELO_Core_Stage_Manager** | `elo-core-stage-manager` | Управление стадиями воронки |
| 7 | **ELO_Core_Response_Generator** | `elo-core-response-generator` | Генерация ответа клиенту |
| 8 | **ELO_Core_Graph_Writer** | `elo-core-graph-writer` | Запись в Neo4j |
| 9 | **ELO_Core_Context_Builder** | `elo-core-context-builder` | Загрузка контекста из Neo4j/Redis |
| 10 | **ELO_Core_AI_Test_Stub** | - | Тестовая заглушка |

---

## Call Graph (Кто кого вызывает)

```
ELO_Core_AI_Pipeline
    │
    ├──[HTTP]──► ELO_AI_Extract
    │                │
    │                └──[HTTP]──► OpenRouter API (qwen/qwen3-30b-a3b:free)
    │
    ├──[HTTP]──► ELO_Core_Lines_Analyzer
    │
    ├──[HTTP]──► ELO_Core_AI_Derive
    │                │
    │                └──[PostgreSQL]──► elo_symptom_types, elo_diagnosis_types, etc.
    │
    ├──[HTTP]──► ELO_Core_Triggers_Checker
    │                │
    │                └──[PostgreSQL]──► elo_v_triggers
    │
    ├──[HTTP]──► ELO_Core_Stage_Manager
    │                │
    │                └──[PostgreSQL]──► elo_v_funnel_stages
    │
    ├──[HTTP]──► ELO_Core_Response_Generator
    │                │
    │                ├──[PostgreSQL]──► elo_v_prompts
    │                └──[HTTP]──► OpenRouter API (qwen/qwen3-30b-a3b:free)
    │
    └──[HTTP]──► ELO_Core_Graph_Writer
                     │
                     └──[HTTP]──► Neo4j (45.144.177.128:7474)
```

---

## Detailed Workflow Descriptions

### 1. ELO_Core_AI_Pipeline

**Webhook:** `POST /webhook/elo-core-ai-pipeline`

**Роль:** Главный pipeline. Получает сообщение от Input Contour, вызывает все воркеры последовательно по цепочке, возвращает ответ.

**Input:**
```json
{
  "tenant_id": "uuid",
  "client_id": "uuid",
  "dialog_id": "uuid",
  "text": "Разбил экран на айфоне",
  "channel_id": "telegram",
  "external_chat_id": "123456"
}
```

**Вызывает (последовательно):**

| Шаг | Workflow | Метод | URL | Условие |
|-----|----------|-------|-----|---------|
| 1 | Load Context | inline | - | Всегда |
| 2 | ELO_AI_Extract | HTTP POST | `elo-ai-extract` | Всегда |
| 3 | ELO_Core_Lines_Analyzer | HTTP POST | `elo-core-lines-analyzer` | Всегда |
| 4 | ELO_Core_AI_Derive | HTTP POST | `elo-core-ai-derive` | Если есть symptom без diagnosis |
| 5 | Apply Derivations | inline | - | Всегда |
| 6 | ELO_Core_Triggers_Checker | HTTP POST | `elo-core-triggers-checker` | Всегда |
| 7 | ELO_Core_Stage_Manager | HTTP POST | `elo-core-stage-manager` | Всегда |
| 8 | ELO_Core_Response_Generator | HTTP POST | `elo-core-response-generator` | Всегда |
| 9 | ELO_Core_Graph_Writer | HTTP POST | `elo-core-graph-writer` | Параллельно с Response Generator |

**Output:**
```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": "telegram",
  "external_chat_id": "123456",
  "message": {
    "text": "Понял! iPhone с разбитым экраном...",
    "buttons": [...]
  }
}
```

---

### 2. ELO_AI_Extract

**Webhook:** `POST /webhook/elo-ai-extract`

**Роль:** Извлечение сущностей из текста клиента через AI.

**Вызывает:**
| Сервис | Метод | URL |
|--------|-------|-----|
| OpenRouter | HTTP POST | `https://openrouter.ai/api/v1/chat/completions` |

**Input:**
```json
{
  "message": "Разбил экран на айфоне 14",
  "extraction_schema": {
    "entities": ["device", "symptom", "owner", "intent"],
    "multi_entity": true
  }
}
```

**Output:**
```json
{
  "entities": [
    {"type": "device", "brand": "Apple", "model": "iPhone 14"},
    {"type": "symptom", "text": "разбитый экран"}
  ]
}
```

---

### 3. ELO_Core_Lines_Analyzer

**Webhook:** `POST /webhook/elo-core-lines-analyzer`

**Роль:** Управление "линиями" - каждая линия = одно устройство + проблема. Поддержка multi-intake (несколько устройств в одном диалоге).

**Вызывает:** Ничего (чистая логика)

**Input:**
```json
{
  "context": {...},
  "extractions": {
    "entities": [...]
  }
}
```

**Логика:**
1. Создаёт новую линию для каждого нового device
2. Привязывает symptom к соответствующей линии
3. Управляет focus_line_id (какая линия активна)
4. Отслеживает cursor (какой слот заполняем)

**Output:**
```json
{
  "context": {
    "lines": [
      {"id": "line_0", "status": "active", "slots": {...}},
      {"id": "line_1", "status": "waiting", "slots": {...}}
    ],
    "focus_line_id": "line_0"
  },
  "changes": {
    "lines_created": 1,
    "lines_updated": 1,
    "focus_changed": false
  }
}
```

---

### 4. ELO_Core_AI_Derive

**Webhook:** `POST /webhook/elo-core-ai-derive`

**Роль:** Derivation chain - по symptom находит diagnosis, repair_type, price.

**Вызывает:**
| Сервис | Метод | Таблицы |
|--------|-------|---------|
| PostgreSQL | Query | `elo_symptom_types`, `elo_v_symptom_mappings`, `elo_symptom_diagnosis_links`, `elo_diagnosis_types`, `elo_diagnosis_repair_links`, `elo_repair_actions`, `elo_t_price_list` |

**Input:**
```json
{
  "context": {...},
  "lines_to_derive": ["line_0"]
}
```

**Условие вызова:** `line.slots.symptom && !line.slots.diagnosis`

**SQL Chain:**
```
symptom_text → symptom_type (через aliases)
            → diagnosis_type (через symptom_diagnosis_links)
            → repair_action (через diagnosis_repair_links)
            → price (из price_list по brand/model)
```

**Output:**
```json
{
  "derivations": [
    {
      "line_id": "line_0",
      "derived": {
        "symptom_type": {"uuid": "...", "code": "broken_screen"},
        "diagnosis": {"uuid": "...", "code": "display_damaged"},
        "repair_type": {"uuid": "...", "code": "display_replaced"},
        "price": {"amount": 5000, "currency": "RUB"}
      }
    }
  ]
}
```

---

### 5. ELO_Core_Triggers_Checker

**Webhook:** `POST /webhook/elo-core-triggers-checker`

**Роль:** Проверка триггеров (скидки, доп. информация) на основе условий.

**Вызывает:**
| Сервис | Метод | Таблицы |
|--------|-------|---------|
| PostgreSQL | Query | `elo_v_triggers` |

**Input:**
```json
{
  "context": {...},
  "stage": "presentation"
}
```

**Условия триггеров (примеры):**
```json
{"device.brand": "Apple"}                    // Точное совпадение
{"price.amount": {"$gt": 7000}}              // Больше 7000
{"repair_type.code": "display_replaced"}     // Код ремонта
```

**Output:**
```json
{
  "triggers_fired": [
    {
      "trigger_id": "apple_display_warranty",
      "action": {
        "type": "send_message",
        "message": "На замену дисплея Apple гарантия 6 месяцев."
      }
    }
  ],
  "context": {...}  // с updated executed_triggers
}
```

---

### 6. ELO_Core_Stage_Manager

**Webhook:** `POST /webhook/elo-core-stage-manager`

**Роль:** Управление стадиями воронки, проверка exit_conditions.

**Вызывает:**
| Сервис | Метод | Таблицы |
|--------|-------|---------|
| PostgreSQL | Query | `elo_v_funnel_stages` |

**Стадии воронки:**
```
data_collection → presentation → agreement → booking → confirmation
```

**Exit Conditions (из БД):**
| Stage | Exit Condition |
|-------|----------------|
| data_collection | `all_lines_complete` (device + symptom заполнены) |
| presentation | `slot_filled: offer_acknowledged` |
| agreement | `intent_detected: agree_to_book` |
| booking | `all_slots_filled: date, time, name, phone` |
| confirmation | `intent_detected: confirm` |

**Output:**
```json
{
  "context": {
    "current_stage": "presentation",
    "previous_stage": "data_collection"
  },
  "stage_changed": true,
  "transition": {
    "from": "data_collection",
    "to": "presentation",
    "reason": "all_lines_complete"
  }
}
```

---

### 7. ELO_Core_Response_Generator

**Webhook:** `POST /webhook/elo-core-response-generator`

**Роль:** Генерация текста ответа клиенту через AI.

**Вызывает:**
| Сервис | Метод | URL/Таблицы |
|--------|-------|-------------|
| PostgreSQL | Query | `elo_v_prompts` |
| OpenRouter | HTTP POST | `https://openrouter.ai/api/v1/chat/completions` |

**Input:**
```json
{
  "context": {...},
  "triggers_fired": [...]
}
```

**Логика:**
1. Определяет goal (ask_slot, present_offer, ask_confirmation, etc.)
2. Загружает prompt из `elo_v_prompts` по (vertical_id, goal_type, slot_name)
3. Заполняет template переменными (device, price, etc.)
4. Вызывает OpenRouter (qwen/qwen3-30b-a3b:free)
5. Добавляет кнопки в зависимости от stage

**Output:**
```json
{
  "response": {
    "text": "iPhone 14 с разбитым экраном. Замена дисплея - 5000₽. Записать?",
    "buttons": [
      {"text": "Записать", "callback_data": "book"},
      {"text": "Вопросы", "callback_data": "clarify"}
    ],
    "metadata": {
      "stage": "presentation",
      "asking_for": "present_offer"
    }
  }
}
```

---

### 8. ELO_Core_Graph_Writer

**Webhook:** `POST /webhook/elo-core-graph-writer`

**Роль:** Запись данных в Neo4j (Client, Device, Problem, Touchpoint).

**Вызывает:**
| Сервис | Метод | URL |
|--------|-------|-----|
| Neo4j | HTTP POST | `http://45.144.177.128:7474/db/neo4j/tx/commit` |

**Input:**
```json
{
  "context": {...},
  "write_mode": "incremental"
}
```

**Записывает:**
- `MERGE (c:Client {id: ...})`
- `MERGE (d:Device {brand: ..., model: ...})`
- `MERGE (p:Problem {symptom_text: ...})`
- `CREATE (t:Touchpoint {...})`
- Relationships: `OWNS`, `HAS_PROBLEM`, `OF_TYPE`, `HAD_TOUCHPOINT`, `ABOUT_DEVICE`

**Output:**
```json
{
  "success": true,
  "writes": {
    "client": 1,
    "devices": 1,
    "problems": 1,
    "touchpoint": 1
  }
}
```

---

### 9. ELO_Core_Context_Builder

**Webhook:** `POST /webhook/elo-core-context-builder`

**Роль:** Загрузка контекста клиента из Neo4j и Redis.

**Вызывает:**
| Сервис | Метод | URL |
|--------|-------|-----|
| Neo4j | HTTP POST | `http://45.144.177.128:7474/db/neo4j/tx/commit` |
| Redis | GET | `dialog:{dialog_id}` |

**Actions:**
| Action | Описание |
|--------|----------|
| `get_context` | Полный контекст клиента (devices, problems, channels) |
| `disambiguation` | Выбор устройства при неоднозначности |
| `match_entities` | Поиск device/problem по extracted данным |
| `load_dialog_context` | Загрузка из Redis |

---

### 10. ELO_Core_AI_Test_Stub

**Роль:** Тестовая заглушка для отладки без реального AI.

---

## Data Flow Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│   Client Message                                                            │
│        │                                                                    │
│        ▼                                                                    │
│   ┌──────────────────┐                                                      │
│   │     Pipeline     │                                                      │
│   └────────┬─────────┘                                                      │
│            │                                                                │
│   ┌────────┼────────────────────────────────────────────────────┐          │
│   │        ▼                                                     │          │
│   │   ┌──────────┐     ┌────────────────┐     ┌──────────┐      │          │
│   │   │ Extract  │────►│ Lines Analyzer │────►│  Derive  │      │          │
│   │   └──────────┘     └────────────────┘     └────┬─────┘      │          │
│   │        │                                        │            │          │
│   │        │ OpenRouter                            │ PostgreSQL │          │
│   │        │                                        │            │          │
│   │        ▼                                        ▼            │          │
│   │   ┌──────────────────────────────────────────────┐          │          │
│   │   │                  Context                      │          │          │
│   │   │  lines[], focus_line_id, current_stage       │          │          │
│   │   └──────────────────────────────────────────────┘          │          │
│   │                          │                                   │          │
│   │        ┌─────────────────┼─────────────────┐                │          │
│   │        ▼                 ▼                 ▼                │          │
│   │   ┌─────────┐     ┌───────────┐     ┌──────────┐           │          │
│   │   │Triggers │     │Stage Mgr  │     │ Response │           │          │
│   │   │ Checker │     │           │     │Generator │           │          │
│   │   └────┬────┘     └─────┬─────┘     └────┬─────┘           │          │
│   │        │                │                 │                 │          │
│   │        │ PostgreSQL     │ PostgreSQL      │ PostgreSQL      │          │
│   │        │ (triggers)     │ (stages)        │ (prompts)       │          │
│   │        │                │                 │ + OpenRouter    │          │
│   │        └────────────────┼─────────────────┘                │          │
│   │                         │                                   │          │
│   │                         ▼                                   │          │
│   │              ┌────────────────────┐                        │          │
│   │              │   Graph Writer     │                        │          │
│   │              │     (Neo4j)        │                        │          │
│   │              └────────────────────┘                        │          │
│   │                                                             │          │
│   └─────────────────────────────────────────────────────────────┘          │
│                                                                             │
│        ▼                                                                    │
│   Response to Client                                                        │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## External Services

| Service | Host | Port | Protocol | Used By |
|---------|------|------|----------|---------|
| OpenRouter | openrouter.ai | 443 | HTTPS | Extract, Response Generator |
| Neo4j | 45.144.177.128 | 7474 | HTTP | Graph Writer, Context Builder |
| PostgreSQL | 185.221.214.83 | 6544 | TCP | Derive, Triggers, Stage Manager, Response Generator |
| Redis | 45.144.177.128 | 6379 | TCP | Context Builder |

---

## Database Tables Used

| Workflow | Tables |
|----------|--------|
| AI_Derive | `elo_symptom_types`, `elo_v_symptom_mappings`, `elo_symptom_diagnosis_links`, `elo_diagnosis_types`, `elo_diagnosis_repair_links`, `elo_repair_actions`, `elo_t_price_list` |
| Triggers_Checker | `elo_v_triggers` |
| Stage_Manager | `elo_v_funnel_stages` |
| Response_Generator | `elo_v_prompts` |

---

## Call Methods Summary

| Method | Count | Used For |
|--------|-------|----------|
| **HTTP POST (webhook)** | 7 | Workflow-to-workflow calls |
| **HTTP POST (external)** | 3 | OpenRouter (x2), Neo4j |
| **PostgreSQL Query** | 4 | Business logic data |
| **Redis GET** | 1 | Context loading |
