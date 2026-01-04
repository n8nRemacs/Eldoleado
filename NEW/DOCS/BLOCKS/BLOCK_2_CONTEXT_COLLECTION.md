# Block 2: Context Collection

**Version:** 2.0
**Date:** 2026-01-04
**Status:** Development (Redis Queue Architecture)
**Orchestrator:** ELO_Context_Collector

---

## Purpose

Block 2 collects context from user messages:
1. Extracts entities using AI via Redis Queue (ELO_Blind_Worker)
2. Evaluates funnel stage with dynamic behavior handling
3. Updates dialog state in database
4. Prepares data for Block 3 (Planning)

---

## Architecture v2.0 (Redis Queue)

```
┌──────────────────────────────────────────────────────────────────────────────────┐
│                        BLOCK 2: Context Collection                                │
│                        Orchestrator: ELO_Context_Collector                        │
├──────────────────────────────────────────────────────────────────────────────────┤
│                                                                                   │
│  ┌─────────────┐                                                                 │
│  │   INPUT     │  Webhook: /elo-core-ingest                                      │
│  │  (Block 1)  │                                                                 │
│  └──────┬──────┘                                                                 │
│         │                                                                         │
│         ▼                                                                         │
│  ┌────────────────────────────────────────────────────────────────────────────┐  │
│  │                         ELO_Context_Collector                               │  │
│  │                                                                              │  │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐                     │  │
│  │  │  Prepare    │───►│  Get Dialog │───►│   Merge     │                     │  │
│  │  │   Input     │    │    State    │    │   State     │                     │  │
│  │  └─────────────┘    └─────────────┘    └──────┬──────┘                     │  │
│  │                                               │                             │  │
│  │         ┌─────────────────────────────────────┘                             │  │
│  │         ▼                                                                    │  │
│  │  ┌──────────────────────────────────────────────────────────────────────┐  │  │
│  │  │        WORKER 1: ELO_AI_Extract_v2 (Redis Queue Publisher)           │  │  │
│  │  │        Webhook: /elo-ai-extract-v2                                    │  │  │
│  │  │                                                                        │  │  │
│  │  │  1. Load context types (global/domain/vertical)                       │  │  │
│  │  │  2. Create N tasks (1 per context_type)                               │  │  │
│  │  │  3. Push to Redis: elo:tasks:pending                                  │  │  │
│  │  │  4. Poll Redis: elo:status:{trace_id} until complete                  │  │  │
│  │  │  5. Get results from elo:results:{trace_id}                           │  │  │
│  │  │  6. Aggregate & normalize                                              │  │  │
│  │  └───────────────────────────┬──────────────────────────────────────────┘  │  │
│  │                              │                                              │  │
│  │         ┌────────────────────┘                                              │  │
│  │         ▼                                                                    │  │
│  │  ┌──────────────────────────────────────────────────────────────────────┐  │  │
│  │  │        WORKER 2: ELO_Funnel_Controller_v2 (Dynamic Behaviors)        │  │  │
│  │  │        Webhook: /elo-funnel-controller-v2                             │  │  │
│  │  │                                                                        │  │  │
│  │  │  • Dynamic behavior handling via pattern matching                     │  │  │
│  │  │  • No hardcoded Switch - supports any behavior_type                   │  │  │
│  │  │  • Binary masks for field tracking                                    │  │  │
│  │  └───────────────────────────┬──────────────────────────────────────────┘  │  │
│  │                              │                                              │  │
│  │         ┌────────────────────┘                                              │  │
│  │         ▼                                                                    │  │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐                     │  │
│  │  │   Update    │───►│   Build     │───►│  Forward to │                     │  │
│  │  │   Dialog    │    │   Output    │    │   Block 3   │                     │  │
│  │  └─────────────┘    └─────────────┘    └─────────────┘                     │  │
│  │                                                                              │  │
│  └──────────────────────────────────────────────────────────────────────────────┘  │
│                                                                                   │
│  ┌─────────────┐                                                                 │
│  │   OUTPUT    │  Webhook: /elo-block3-planner                                   │
│  │  (Block 3)  │                                                                 │
│  └─────────────┘                                                                 │
│                                                                                   │
└──────────────────────────────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────────────────────────────┐
│                     ELO_Blind_Worker (N instances, scalable)                      │
├──────────────────────────────────────────────────────────────────────────────────┤
│                                                                                   │
│  Schedule (2s) → Pop Redis (elo:tasks:pending) → Parse Task                      │
│                                                      │                            │
│                              ┌───────────────────────┼───────────────────┐       │
│                              ▼                       ▼                   ▼       │
│                        llm_extraction          http_request       webhook_call   │
│                              │                       │                   │       │
│                              ▼                       └───────┬───────────┘       │
│                  Build Prompt + Call OpenRouter              │                    │
│                  (config.model = dynamic!)                   │                    │
│                              │                               │                    │
│                              ▼                               ▼                    │
│                       Parse Response              Format HTTP Result              │
│                              │                               │                    │
│                              └───────────┬───────────────────┘                    │
│                                          ▼                                        │
│                          Push → Redis (elo:results:{trace_id})                   │
│                          INCR → Redis (elo:counter:{trace_id})                   │
│                                          │                                        │
│                    if counter >= total → SET status = "complete"                 │
│                                                                                   │
└──────────────────────────────────────────────────────────────────────────────────┘
```

---

## Redis Keys

| Key Pattern | Type | Purpose | TTL |
|-------------|------|---------|-----|
| `elo:tasks:pending` | LIST | Task queue (LPUSH/RPOP) | - |
| `elo:results:{trace_id}` | LIST | Results per request | 300s |
| `elo:counter:{trace_id}` | INT | Completed tasks counter | 300s |
| `elo:status:{trace_id}` | STRING | Status (pending/complete) | 300s |

---

## Key Changes v2.0

| Aspect | v1.0 | v2.0 |
|--------|------|------|
| AI Calls | 1-3 big prompts | N small prompts (1 per context_type) |
| Hallucinations | High risk | Minimal (focused prompts) |
| Scalability | 1 workflow | N Blind Workers in parallel |
| Model | Hardcoded | Dynamic from `config.model` |
| Behavior Types | Hardcoded Switch | Dynamic pattern matching |
| Flexibility | Only extraction | Any task_type via Redis |

---

## Input Schema

**Webhook:** `POST /elo-core-ingest`
**Source:** Block 1 (ELO_Resolver)

```json
{
  "tenant_id": "string (uuid)",
  "client_id": "string (uuid)",
  "dialog_id": "string (uuid)",
  "channel_id": "integer",
  "channel_account_id": "string (uuid)",
  "channel": "telegram|whatsapp|avito|vk|max",
  "external_chat_id": "string",
  "text": "string",
  "media": "object|null",
  "is_new_client": "boolean",
  "is_new_dialog": "boolean",
  "trace_id": "string"
}
```

---

## Output Schema

**Webhook:** `POST /elo-block3-planner`
**Target:** Block 3 (ELO_Planner)

```json
{
  "tenant_id": "string",
  "client_id": "string",
  "dialog_id": "string",
  "channel": "string",
  "text": "string",
  "context": {
    "device": {"brand": "string", "model": "string"},
    "...extracted fields": "any"
  },
  "extracted": {
    "entities": [{"type": "string", "value": "any", "level": "string"}],
    "grouped": {"global": [], "domain": {}, "vertical": {}}
  },
  "funnel": {
    "previous_stage": "string|null",
    "current_stage": "string",
    "changed": "boolean",
    "behavior_type": "string",
    "masks": {"current": "string", "filled_count": "int", "required_count": "int"},
    "response": {"type": "string", "field": "string|null", "prompt": "string|null"},
    "actions": []
  },
  "trace_id": "string",
  "block2_duration_ms": "integer"
}
```

---

## Worker 1: ELO_AI_Extract_v2

### Purpose

Publishes extraction tasks to Redis queue, waits for ELO_Blind_Worker to process, aggregates results.

### Webhook

`POST /elo-ai-extract-v2`

### Flow

```
Webhook → Validate Input
              ↓ (parallel)
Load Global / Load Domain / Load Vertical / Load Normalization
              ↓
       Create Tasks (1 per context_type)
              ↓
       Init Counter (0) + Init Status (pending)
              ↓
       Push All Tasks → Redis (elo:tasks:pending)
              ↓
       ┌─── Wait 1s Loop ───┐
       │  Check Status Key   │ ←─────────────────┐
       │       ↓             │                   │
       │   Complete? ───No──→ Continue? ──Yes───┘
       │       ↓Yes                  ↓No
       │  Get All Results      Timeout (408)
       └────────┬────────────────────┘
                ↓
        Aggregate Results + Normalize
                ↓
            Respond
```

### Task Format (pushed to Redis)

```json
{
  "task_id": "extract_greeting_abc123",
  "trace_id": "trace_001",
  "task_type": "llm_extraction",
  "config": {
    "model": "qwen/qwen3-4b:free",
    "message": "Привет, у меня iPhone",
    "system_prompt": "Extract greeting from message...",
    "output_schema": {"value": "boolean"},
    "temperature": 0.1,
    "max_tokens": 200
  },
  "meta": {
    "code": "greeting",
    "level": "global",
    "total_tasks": 16
  },
  "callback": {
    "result_key": "elo:results:trace_001",
    "counter_key": "elo:counter:trace_001",
    "status_key": "elo:status:trace_001"
  }
}
```

### Context Type Levels

| Level | Table | Examples |
|-------|-------|----------|
| Global | `elo_context_types` | greeting, goodbye, sentiment, urgency |
| Domain | `elo_d_context_types` | device (electronics), vehicle (auto) |
| Vertical | `elo_v_context_types` | symptom (repair), warranty (repair) |

### Output Schema (unified)

```json
{
  "entities": [
    {"type": "greeting", "level": "global", "value": {"value": true}},
    {"type": "device", "level": "domain", "value": {"brand": "Apple", "model": "iPhone 13"}}
  ],
  "grouped": {
    "global": [...],
    "domain": {"electronics": [...]},
    "vertical": {"repair": [...]}
  },
  "trace_id": "string",
  "total_tasks": 16,
  "duration_ms": 3500
}
```

---

## Worker 2: ELO_Funnel_Controller_v2

### Purpose

Evaluates funnel stage with dynamic behavior handling. No hardcoded Switch - uses pattern matching on behavior_type.

### Webhook

`POST /elo-funnel-controller-v2`

### Dynamic Behavior Handling

```javascript
// Pattern matching - supports ANY behavior_type from database
if (behaviorType.includes('COLLECT') || behaviorType.includes('ITERATIVE') || behaviorType.includes('BATCH')) {
  // COLLECTION behaviors - ask for missing fields
} else if (behaviorType.includes('PROMO') || behaviorType.includes('SEND') || behaviorType.includes('PRESENT')) {
  // SEND/PRESENT behaviors - send content, auto-advance
} else if (behaviorType.includes('WAIT') || behaviorType.includes('CTA') || behaviorType.includes('EXTERNAL')) {
  // WAIT behaviors - detect CTA/intent in message
} else if (behaviorType.includes('TERMINAL') || behaviorType.includes('SUCCESS') || behaviorType.includes('CANCEL')) {
  // TERMINAL behaviors - end funnel
} else if (behaviorType.includes('ESCALATE') || behaviorType.includes('OPERATOR')) {
  // ESCALATE behaviors - notify operator
} else {
  // UNKNOWN - default handling
}
```

### Binary Masks

```
Stage with 5 fields:
Fields:        [device.brand, device.model, problem.type, owner.label, owner.phone]
Is Required:   [    1      ,      1      ,      1      ,      1     ,      0      ]

Current state: [   filled  ,   filled   ,   empty    ,   empty   ,    empty    ]
Current Mask:  "11000"

Complete: false (filled_count < required_count)
Next field to ask: problem.type (index 2)
```

---

## ELO_Blind_Worker

### Purpose

Universal worker that polls Redis queue, executes tasks (LLM/HTTP/Webhook), returns results. Scales horizontally.

### Key Features

- **Dynamic Model:** Uses `config.model` (not hardcoded)
- **Task Types:** llm_extraction, llm_generation, http_request, webhook_call
- **Scalable:** Run N instances for parallelism
- **Universal:** Any block can use via Redis

### Flow

```
Schedule (2s) → Pop Redis (elo:tasks:pending)
                     ↓
               Task Exists?
                     ↓ Yes
               Parse Task
                     ↓
          Task Router (by task_type)
                     ↓
     Build Prompt + Call OpenRouter (config.model)
                     ↓
            Parse Response
                     ↓
     Push Result → elo:results:{trace_id}
     INCR Counter → elo:counter:{trace_id}
                     ↓
     if counter >= total_tasks:
         SET elo:status:{trace_id} = "complete"
```

---

## Database Dependencies

### Read Operations

| Table | Purpose |
|-------|---------|
| `elo_t_dialogs` | Get current dialog state |
| `elo_funnel_stages` | Get stage configuration |
| `elo_context_types` | Global context types (6 rows) |
| `elo_d_context_types` | Domain context types |
| `elo_v_context_types` | Vertical context types |
| `elo_t_context_type_overrides` | Tenant overrides |
| `elo_normalization_rules` | Brand normalization |
| `elo_stage_fields` | Stage fields for masks |
| `elo_stage_cta_actions` | CTA detection config |

### Write Operations

| Table | Purpose |
|-------|---------|
| `elo_t_dialogs` | Update context, current_stage |

---

## Files

| File | Location |
|------|----------|
| Orchestrator | `NEW/workflows/Block 2 Context/ELO_Context_Collector.json` |
| Extraction Worker | `NEW/workflows/Block 2 Context/ELO_AI_Extract.json` |
| Funnel Controller | `NEW/workflows/Block 2 Context/ELO_Funnel_Controller.json` |
| Blind Worker | `NEW/workflows/AI Contour/Workers/ELO_Blind_Worker.json` |

---

## Performance Metrics

| Metric | Target | Notes |
|--------|--------|-------|
| Block 2 total | < 5000ms | With 16 context types |
| Per extraction task | < 500ms | Small focused prompt |
| Blind Worker poll | 2s | Schedule interval |
| Max poll timeout | 30s | 30 polls x 1s |

---

## Next Steps (Optimization)

1. **Redis Connection Pooling** - optimize Redis operations
2. **Batch LLM Calls** - group similar extractions
3. **Caching** - cache context types per tenant
4. **Metrics** - add Prometheus metrics
5. **Dead Letter Queue** - handle failed tasks

---

*Updated by Claude Code — 2026-01-04*
