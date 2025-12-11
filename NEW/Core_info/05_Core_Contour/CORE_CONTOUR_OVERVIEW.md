# Core Contour — Overview

> Business logic, AI processing, vertical detection, dialog management

**Ingest Endpoint:** POST `https://n8n.n8nsrv.ru/webhook/elo-core-ingest`
**AI Model:** Qwen3-30B via OpenRouter

---

## MVP v0 Scope

| Feature | MVP v0 | Full |
|---------|--------|------|
| **Vertical** | phone_repair only | Multi-vertical |
| **AI Model** | Qwen3-30B (OpenRouter) | Claude |
| **Response** | Stub (manual check graph) | Full AI response |
| **Funnel** | Not active | Microfunnel stages |
| **Tools** | extraction only | All tools |

**Test scenario:** "Привет, сколько стоит поменять дисплей на iPhone 14 Pro?"

**MVP Goal:** Verify extraction writes to graph correctly, then tune responses.

---

## Purpose

Core Contour is the **brain** of the system:
- **Context Builder** — gathers full context from PostgreSQL + Neo4j
- **Request Builder** — prepares AI request with "Stick-Carrot-Stick"
- **Orchestrator** — blind executor, calls tools
- **Dialog Engine** — saves to DB, syncs to Graph, manages dialog state

**Principle:** All business logic is here. Other contours are just pipes.

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CORE CONTOUR                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  FROM CLIENT CONTOUR                                                         │
│  (tenant_id, client_id, dialog_id, text, ...)                               │
│                         │                                                    │
│                         ▼                                                    │
│  ┌──────────────────────────────────────────────────────────────────┐       │
│  │  1. CONTEXT BUILDER                                               │       │
│  │     • Load dialog state (PostgreSQL)                              │       │
│  │     • Load client context (Neo4j via Graph Query Tool)            │       │
│  │     • Determine/verify vertical_id                                │       │
│  │     • Get current funnel stage                                    │       │
│  │     • Calculate focus_score                                       │       │
│  │     Output: context object                                        │       │
│  └──────────────────────────────┬───────────────────────────────────┘       │
│                                 │                                            │
│                                 ▼                                            │
│  ┌──────────────────────────────────────────────────────────────────┐       │
│  │  2. REQUEST BUILDER (Stick-Carrot-Stick)                          │       │
│  │     • Load prompt for funnel_stage (PostgreSQL)                   │       │
│  │     • Load ai_settings (pre_rules, freedom_level, post_rules)     │       │
│  │     • Build AI request with tools                                 │       │
│  │     Output: ai_request                                            │       │
│  └──────────────────────────────┬───────────────────────────────────┘       │
│                                 │                                            │
│                                 ▼                                            │
│  ┌──────────────────────────────────────────────────────────────────┐       │
│  │  3. ORCHESTRATOR (Blind Executor)                                 │       │
│  │     • Call AI model (Claude)                                      │       │
│  │     • Execute tool calls (Graph Query, Price Lookup, etc.)        │       │
│  │     • Validate response (post_rules)                              │       │
│  │     Output: ai_response, tool_results                             │       │
│  └──────────────────────────────┬───────────────────────────────────┘       │
│                                 │                                            │
│                                 ▼                                            │
│  ┌──────────────────────────────────────────────────────────────────┐       │
│  │  4. DIALOG ENGINE                                                 │       │
│  │     • Save message to PostgreSQL                                  │       │
│  │     • Sync entities to Neo4j (if changed_graph = true)            │       │
│  │     • Update dialog state (stage, vertical_id)                    │       │
│  │     • Prepare response for Channel Contour                        │       │
│  │     Output: response object                                       │       │
│  └──────────────────────────────┬───────────────────────────────────┘       │
│                                 │                                            │
│                                 ▼                                            │
│  TO CHANNEL CONTOUR (OUT)                                                    │
│  (dialog_id, channel_id, message, buttons, ...)                             │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## System Flow (4 Contours)

```
┌─────────────────┐
│ CHANNEL CONTOUR │  ← IN: receive, normalize, transcribe
└────────┬────────┘
         ↓
┌─────────────────┐
│  INPUT CONTOUR  │  ← Tenant Resolver, Queue, Batcher
└────────┬────────┘
         ↓
┌─────────────────┐
│ CLIENT CONTOUR  │  ← Client Resolver, Dialog Resolver
└────────┬────────┘
         ↓
┌─────────────────┐
│  CORE CONTOUR   │  ← Context Builder, Request Builder, Orchestrator, Dialog Engine
└────────┬────────┘
         ↓
┌─────────────────┐
│ CHANNEL CONTOUR │  ← OUT: Response Builder, send via MCP
└─────────────────┘
```

---

## Components

| # | Component | Purpose | MVP |
|---|-----------|---------|-----|
| 1 | Context Builder | Gather context, detect vertical | Yes |
| 2 | Request Builder | Prepare AI request (Stick-Carrot-Stick) | Yes |
| 3 | Orchestrator | Execute AI + tools | Yes |
| 4 | Dialog Engine | Save state, sync graph | Yes |

---

## 1. Context Builder

### Purpose

Gather all information needed for AI to make decisions.

### Input

```json
{
  "tenant_id": "uuid",
  "domain_id": 1,
  "client_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "text": "Merged message text",
  "timestamp": "...",
  "meta": {
    "external_chat_id": "123456",
    "is_new_client": false,
    "is_new_dialog": false
  }
}
```

### Process

```
1. Load dialog from PostgreSQL
   └── current_stage_id, vertical_id, assigned_operator_id

2. Load client context from Neo4j (via Graph Query Tool)
   └── devices, issues, symptoms, diagnoses, repairs, traits

3. Determine vertical_id
   └── New dialog? → AI will suggest
   └── Existing dialog? → use current or detect change

4. Get funnel stage settings
   └── ai_goal, entry_conditions, exit_conditions

5. Calculate focus_score (0-100%)
   └── Based on what's already known
```

### Output

```json
{
  "tenant_id": "uuid",
  "domain_id": 1,
  "client_id": "uuid",
  "dialog_id": "uuid",
  "vertical_id": 1,
  "channel_id": 1,

  "dialog_state": {
    "current_stage": "device",
    "stage_entered_at": "...",
    "is_new_dialog": false
  },

  "client_context": {
    "name": "Ivan",
    "phone": "+79991234567",
    "traits": [{"type": "vip", "value": true}],
    "devices": [...],
    "history": {
      "total_issues": 5,
      "last_visit": "2024-11-01",
      "verticals": [1, 2]
    }
  },

  "current_issue": {
    "device": {"brand": "Apple", "model": "iPhone 14"},
    "intake": {...},
    "symptoms": [...]
  },

  "focus_score": 45,
  "missing_fields": ["problem_description"],

  "message": {
    "text": "Merged message text",
    "timestamp": "..."
  }
}
```

### SQL: Load dialog state

```sql
SELECT
  d.id,
  d.vertical_id,
  d.current_stage_id,
  d.stage_entered_at,
  d.status,
  d.assigned_operator_id,
  fs.code as stage_code,
  fs.ai_goal,
  fs.entry_conditions,
  fs.exit_conditions
FROM dialogs d
LEFT JOIN funnel_stages fs ON fs.id = d.current_stage_id
WHERE d.id = $dialog_id;
```

### Graph Query: Load client context

```json
{
  "query_code": "get_client_context",
  "params": {
    "client_id": "$client_id"
  }
}
```

---

## 2. Request Builder (Stick-Carrot-Stick)

### Purpose

Prepare AI request with rules before, freedom during, validation after.

### Process

```
                    ┌─────────────────────────────────────┐
                    │           REQUEST BUILDER            │
                    ├─────────────────────────────────────┤
                    │                                      │
 ┌──────────────────┼──────────────────────────────────┐  │
 │  STICK (Before)  │  pre_rules from ai_settings      │  │
 │                  │  • Required extractions          │  │
 │                  │  • Forbidden topics              │  │
 │                  │  • Mandatory questions           │  │
 └──────────────────┼──────────────────────────────────┘  │
                    │                                      │
 ┌──────────────────┼──────────────────────────────────┐  │
 │  CARROT (During) │  freedom_level 0-100             │  │
 │                  │  • Prompt for current stage      │  │
 │                  │  • Allowed tools                 │  │
 │                  │  • Context                       │  │
 └──────────────────┼──────────────────────────────────┘  │
                    │                                      │
 ┌──────────────────┼──────────────────────────────────┐  │
 │  STICK (After)   │  post_rules from ai_settings     │  │
 │                  │  • Response length limits        │  │
 │                  │  • Forbidden words               │  │
 │                  │  • Required phrases              │  │
 └──────────────────┼──────────────────────────────────┘  │
                    │                                      │
                    └─────────────────────────────────────┘
```

### SQL: Get prompt

```sql
SELECT prompt_text, system_context, extraction_schema
FROM prompts
WHERE vertical_id = $vertical_id
  AND funnel_stage_id = $funnel_stage_id
  AND (tenant_id = $tenant_id OR tenant_id IS NULL)
  AND is_active = true
ORDER BY tenant_id NULLS LAST
LIMIT 1;
```

### SQL: Get AI settings

```sql
SELECT
  pre_rules,
  required_extractions,
  freedom_level,
  allowed_tools,
  post_rules,
  must_include
FROM ai_settings
WHERE vertical_id = $vertical_id
  AND (tenant_id = $tenant_id OR tenant_id IS NULL)
  AND is_active = true
ORDER BY tenant_id NULLS LAST
LIMIT 1;
```

### Output: AI Request

```json
{
  "model": "claude-3-5-sonnet",

  "system": "You are a phone repair assistant...",

  "messages": [
    {"role": "user", "content": "Context: ..."},
    {"role": "user", "content": "Message: Разбил экран iPhone 14"}
  ],

  "tools": [
    {
      "name": "extract_device",
      "description": "Extract device info from message",
      "parameters": {...}
    },
    {
      "name": "extract_symptoms",
      "description": "Extract symptoms from message",
      "parameters": {...}
    },
    {
      "name": "get_price",
      "description": "Get price range for repair",
      "parameters": {...}
    },
    {
      "name": "send_response",
      "description": "Send response to client",
      "parameters": {...}
    }
  ],

  "pre_rules": {
    "must_extract": ["device_brand", "device_model"],
    "forbidden_topics": ["politics", "competitors"]
  },

  "post_rules": {
    "max_length": 500,
    "forbidden_words": ["дорого", "долго"],
    "must_include": ["готовы помочь"]
  },

  "freedom_level": 50
}
```

### Freedom Level Examples

| Level | Behavior |
|-------|----------|
| 0 | Strict script, AI only substitutes data |
| 30 | AI can rephrase, but follows structure |
| 50 | Balanced: follows goals, free in wording |
| 70 | AI free in wording, but follows rules |
| 100 | Full freedom (dangerous, testing only) |

---

## 3. Orchestrator

### Purpose

Blind executor. Doesn't know business logic, only executes.

### Process

```
┌─────────────────────────────────────────────────────────────────┐
│                        ORCHESTRATOR                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  1. Receive ai_request from Request Builder                     │
│                                                                  │
│  2. Call AI model                                               │
│     └── Claude/GPT with tools                                   │
│                                                                  │
│  3. Process tool calls (loop until done)                        │
│     ┌─────────────────────────────────────────────────────┐     │
│     │  AI returns: "call tool extract_device"              │     │
│     │       │                                              │     │
│     │       ▼                                              │     │
│     │  Execute tool (via Tool Router)                      │     │
│     │       │                                              │     │
│     │       ▼                                              │     │
│     │  Return result to AI                                 │     │
│     │       │                                              │     │
│     │       ▼                                              │     │
│     │  AI continues or finishes                            │     │
│     └─────────────────────────────────────────────────────┘     │
│                                                                  │
│  4. Validate response (post_rules)                              │
│     └── Length, forbidden words, required phrases               │
│                                                                  │
│  5. Return result to Dialog Engine                              │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

### Tool Router

```
Tool call from AI
       │
       ▼
  ┌─────────────────────────────────────────────────────────────┐
  │                      TOOL ROUTER                             │
  │  (routes to appropriate tool based on tool_name)             │
  ├─────────────────────────────────────────────────────────────┤
  │                                                              │
  │  extract_device    → AI Extraction Tool                     │
  │  extract_symptoms  → AI Extraction Tool                     │
  │  get_price         → Price Lookup Tool                      │
  │  graph_query       → Graph Query Tool                       │
  │  send_response     → Response Builder (→ Channel OUT)       │
  │                                                              │
  └─────────────────────────────────────────────────────────────┘
```

### Output

```json
{
  "ai_response": {
    "text": "Понял, экран разбит на iPhone 14. Стоимость замены...",
    "tool_calls": [
      {
        "tool": "extract_device",
        "result": {"brand": "Apple", "model": "iPhone 14"}
      },
      {
        "tool": "get_price",
        "result": {"min": 5000, "max": 8000, "currency": "RUB"}
      }
    ]
  },
  "extractions": {
    "device": {"brand": "Apple", "model": "iPhone 14"},
    "symptoms": [{"code": "screen_cracked", "text": "разбит экран"}]
  },
  "response_to_client": {
    "text": "Понял, экран разбит на iPhone 14...",
    "buttons": [...]
  },
  "validation": {
    "passed": true,
    "warnings": []
  }
}
```

---

## 4. Dialog Engine

### Purpose

Save state, sync to graph, manage dialog lifecycle.

### Process

```
┌─────────────────────────────────────────────────────────────────┐
│                       DIALOG ENGINE                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  1. Save incoming message to PostgreSQL                         │
│     └── messages table (direction: in)                          │
│                                                                  │
│  2. Process extractions                                         │
│     └── Create/update entities in Neo4j                         │
│         • Device (if new)                                       │
│         • Issue (if new)                                        │
│         • Symptoms (from extraction)                            │
│         • Traits (if detected)                                  │
│                                                                  │
│  3. Update dialog state                                         │
│     └── dialogs table                                           │
│         • vertical_id (if determined)                           │
│         • current_stage_id (if stage changed)                   │
│         • stage_entered_at                                      │
│                                                                  │
│  4. Save outgoing message to PostgreSQL                         │
│     └── messages table (direction: out)                         │
│                                                                  │
│  5. Prepare response for Channel Contour                        │
│     └── Format for Response Builder                             │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

### SQL: Save message

```sql
INSERT INTO messages (
  tenant_id,
  dialog_id,
  client_id,
  direction_id,
  message_type_id,
  actor_type,
  actor_id,
  content,
  changed_graph,
  external_message_id,
  trace_id,
  timestamp
) VALUES (
  $tenant_id,
  $dialog_id,
  $client_id,
  $direction_id,
  $message_type_id,
  $actor_type,
  $actor_id,
  $content,
  $changed_graph,
  $external_message_id,
  $trace_id,
  $timestamp
)
RETURNING id;
```

### SQL: Update dialog state

```sql
UPDATE dialogs
SET
  vertical_id = COALESCE($vertical_id, vertical_id),
  current_stage_id = $current_stage_id,
  stage_entered_at = CASE
    WHEN current_stage_id != $current_stage_id THEN NOW()
    ELSE stage_entered_at
  END,
  updated_at = NOW()
WHERE id = $dialog_id;
```

### Graph Sync (when changed_graph = true)

```json
// Create device
{
  "query_code": "create_device",
  "params": {
    "client_id": "uuid",
    "device_id": "uuid",
    "brand": "Apple",
    "model": "iPhone 14",
    "color": null
  }
}

// Create issue
{
  "query_code": "create_issue",
  "params": {
    "device_id": "uuid",
    "issue_id": "uuid",
    "vertical_id": 1,
    "dialog_id": "uuid"
  }
}

// Add symptom (via intake)
// ... similar pattern
```

### Output (to Channel Contour)

```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "external_chat_id": "tg_123456789",
  "message": {
    "text": "Понял, экран разбит на iPhone 14...",
    "buttons": [
      {"text": "Записаться", "callback_data": "appointment"},
      {"text": "Узнать цену", "callback_data": "price"}
    ],
    "attachments": []
  },
  "trace_id": "trace_abc123"
}
```

---

## Vertical Detection

### Scenarios

| Scenario | Logic |
|----------|-------|
| New dialog, no context | AI analyzes first message → suggests vertical |
| New dialog, known client | Check client history → suggest likely vertical |
| Existing dialog | Use current vertical_id |
| Vertical change detected | Create new Issue with different vertical_id |

### Multi-Vertical Example

```
Dialog starts: "Разбил экран iPhone"
  → Core sets vertical_id = 1 (phone_repair)
  → Creates Issue for phone_repair

Later in same dialog: "А еще хочу продать старый Samsung"
  → AI detects new vertical intent
  → Core creates NEW Issue with vertical_id = 2 (buy_sell)
  → Same dialog, same client, multiple verticals
```

### SQL: Check client verticals

```sql
SELECT DISTINCT d.vertical_id, v.code, v.name, COUNT(*) as issue_count
FROM dialogs d
JOIN verticals v ON v.id = d.vertical_id
WHERE d.client_id = $client_id
  AND d.vertical_id IS NOT NULL
GROUP BY d.vertical_id, v.code, v.name
ORDER BY issue_count DESC;
```

---

## Tools Available in Core

| Tool | Purpose | Called When |
|------|---------|-------------|
| **Graph Query** | Neo4j operations | Context load, entity sync |
| **AI Call** | Claude/GPT requests | Extraction, response generation |
| **Price Lookup** | Price from price_templates | Client asks about price |
| **Send Message** | Via Channel OUT | Response ready |

---

## Input Contract

**From Client Contour:**
```json
{
  "tenant_id": "uuid",
  "domain_id": 1,
  "client_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "text": "Merged message text",
  "timestamp": "2024-12-10T10:00:15Z",
  "message_ids": ["msg_1", "msg_2"],
  "trace_id": "trace_xyz789",
  "meta": {
    "batched": true,
    "batch_size": 2,
    "external_chat_id": "123456",
    "is_new_client": false,
    "is_new_dialog": false
  }
}
```

---

## Output Contract

**To Channel Contour (OUT):**
```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "external_chat_id": "tg_123456789",
  "message": {
    "text": "Response text",
    "buttons": [
      {"text": "Yes", "callback_data": "yes"},
      {"text": "No", "callback_data": "no"}
    ],
    "attachments": []
  },
  "trace_id": "trace_abc123"
}
```

---

## Dependencies

| Type | Resource | Purpose | MVP v0 |
|------|----------|---------|--------|
| Table | `elo_dialogs` | Dialog state | ✅ |
| Table | `elo_messages` | Message history | ✅ |
| Table | `elo_clients` | Client records | ✅ |
| Table | `prompts` | AI prompts | Later |
| Table | `ai_settings` | Stick-Carrot-Stick rules | Later |
| Table | `funnel_stages` | Microfunnel config | Later |
| Table | `cypher_queries` | Graph queries | ✅ |
| Database | Neo4j | Graph storage | ✅ |
| External | OpenRouter API | AI extraction | ✅ |
| External | Claude API | AI responses | Later |

---

## Current Implementation

**Status:** n8n workflow (MVP v0)

| Component | MVP v0 | Target |
|-----------|--------|--------|
| Context Builder | Simplified (Neo4j full history) | MCP Core service |
| Request Builder | Static prompt + extraction schema | MCP Core service |
| Orchestrator | OpenRouter Qwen3-30B | MCP Core service |
| Dialog Engine | Write to graph, stub response | MCP Core service |

### MVP v0 Workflow

```
POST /webhook/elo-core-ingest
         │
         ▼
┌─────────────────────────────────────────────────────────────────┐
│  1. LOAD CONTEXT (Neo4j)                                         │
│     • Get full client history                                   │
│     • Get dialog state                                          │
└─────────────────────────────────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────────────────────────────┐
│  2. AI EXTRACTION (Qwen3-30B via OpenRouter)                    │
│     • Extract device info                                       │
│     • Extract symptoms                                          │
│     • Suggest vertical (phone_repair for MVP)                   │
└─────────────────────────────────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────────────────────────────┐
│  3. WRITE TO GRAPH (Neo4j)                                      │
│     • Create/update Device                                      │
│     • Create/update Issue                                       │
│     • Add Symptoms to Intake                                    │
└─────────────────────────────────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────────────────────────────┐
│  4. STUB RESPONSE                                               │
│     • "Спасибо, данные записаны"                                │
│     • Manual verification in Neo4j Browser                      │
└─────────────────────────────────────────────────────────────────┘
```

### MVP v0 Validation Checklist

1. Send test message: "Привет, сколько стоит поменять дисплей на iPhone 14 Pro?"
2. Check Neo4j Browser:
   - Device node created? (brand: Apple, model: iPhone 14 Pro)
   - Issue node created? (vertical: phone_repair)
   - Symptom extracted? (screen replacement needed)
3. If extraction correct → move to response tuning
4. If extraction wrong → adjust prompts

### OpenRouter Configuration

```env
OPENROUTER_API_KEY=sk-or-...
OPENROUTER_MODEL=qwen/qwen3-30b-a3b
OPENROUTER_BASE_URL=https://openrouter.ai/api/v1
```

### Future Migration to MCP

**Why migrate:**
- Better tool handling
- Faster execution
- Easier debugging
- Horizontal scaling
- Python ecosystem for AI tools

**When:** After MVP v0 validates extraction + response quality

---

**Document:** CORE_CONTOUR_OVERVIEW.md
**Date:** 2025-12-11
**Author:** Dmitry + Claude
**Status:** MVP v0 (extraction only, stub response)
