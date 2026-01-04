# Pipeline Blocks Overview

**Version:** 1.0
**Date:** 2026-01-04
**Status:** Design

---

## Pipeline Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              ELO PIPELINE                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐   ┌─────────┐       │
│  │ BLOCK 1 │──►│ BLOCK 2 │──►│ BLOCK 3 │──►│ BLOCK 4 │──►│ BLOCK 5 │       │
│  │  Input  │   │ Context │   │Planning │   │Execution│   │ Output  │       │
│  └─────────┘   └─────────┘   └─────────┘   └─────────┘   └─────────┘       │
│                                                                              │
│  Channels      Extract       Decide        Generate      Send to            │
│  → Redis       Context       What to       Response      Channel            │
│  → Resolve     → Stage       Do Next       Execute                          │
│                                            Actions                           │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Block 1: Input (Skip - Already Working)

**Status:** Production
**Webhook:** Multiple channel endpoints
**Orchestrator:** ELO_Input_Processor

Handles incoming messages from all channels, batches them, resolves tenant/client/dialog.

---

## Block 2: Context Collection

**Status:** Development
**Webhook:** `/elo-core-ingest`
**Orchestrator:** ELO_Context_Collector

### Purpose

Extracts context from user message, evaluates funnel stage, updates dialog state.

### Black Box View

```
┌─────────────────────────────────────────────────────────────────┐
│                    BLOCK 2: Context Collection                   │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  INPUT                              OUTPUT                       │
│  ─────                              ──────                       │
│  {                                  {                            │
│    tenant_id                          tenant_id                  │
│    client_id                          client_id                  │
│    dialog_id                          dialog_id                  │
│    channel                            channel                    │
│    external_chat_id                   external_chat_id           │
│    text                               text                       │
│    media                              context: {...}             │
│    is_new_client                      extracted: {...}           │
│    is_new_dialog                      funnel: {                  │
│    trace_id                             current_stage            │
│  }                                      previous_stage           │
│                                         changed                  │
│                                         behavior_type            │
│                                         response                 │
│                                         actions[]                │
│                                       }                          │
│                                       trace_id                   │
│                                     }                            │
│                                                                  │
├─────────────────────────────────────────────────────────────────┤
│  WORKERS:                                                        │
│  • ELO_AI_Extract_v2 — context extraction (LLM)                 │
│  • ELO_Funnel_Controller_v2 — stage evaluation                  │
└─────────────────────────────────────────────────────────────────┘
```

### Input Schema

```json
{
  "tenant_id": "uuid",
  "client_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": "integer",
  "channel_account_id": "uuid",
  "channel": "string (telegram|whatsapp|avito|vk|max)",
  "external_chat_id": "string",
  "text": "string",
  "media": "object|null",
  "is_new_client": "boolean",
  "is_new_dialog": "boolean",
  "trace_id": "string"
}
```

### Output Schema

```json
{
  "tenant_id": "uuid",
  "client_id": "uuid",
  "dialog_id": "uuid",
  "channel": "string",
  "external_chat_id": "string",
  "text": "string",
  "media": "object|null",
  "context": {
    "...merged context fields..."
  },
  "extracted": {
    "entities": [],
    "grouped": {},
    "model": "string",
    "tokens_used": "integer"
  },
  "funnel": {
    "previous_stage": "string",
    "current_stage": "string",
    "changed": "boolean",
    "behavior_type": "string",
    "masks": {},
    "response": "object|null",
    "actions": []
  },
  "is_new_client": "boolean",
  "is_new_dialog": "boolean",
  "trace_id": "string",
  "block2_duration_ms": "integer"
}
```

---

## Block 3: Planning

**Status:** Design
**Webhook:** `/elo-block3-planner`
**Orchestrator:** ELO_Planner

### Purpose

Decides what to do next: generate response, execute actions, notify operator, or wait.

### Black Box View

```
┌─────────────────────────────────────────────────────────────────┐
│                       BLOCK 3: Planning                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  INPUT (from Block 2)               OUTPUT                       │
│  ───────────────────               ──────                        │
│  {                                  {                            │
│    ...block2_output                   ...block2_data             │
│    funnel.response                    plan: {                    │
│    funnel.actions                       should_respond           │
│    funnel.behavior_type                 response_goal            │
│  }                                      actions_to_execute       │
│                                         operator_mode            │
│                                         wait_for_event           │
│                                       }                          │
│                                       trace_id                   │
│                                     }                            │
│                                                                  │
├─────────────────────────────────────────────────────────────────┤
│  WORKERS:                                                        │
│  • ELO_Response_Planner — decides response goal                 │
│  • ELO_Action_Planner — plans action execution                  │
│  • ELO_Mode_Router — checks operator mode (manual/semi/auto)    │
└─────────────────────────────────────────────────────────────────┘
```

### Input Schema

```json
{
  "...all fields from Block 2 output...",
  "funnel": {
    "current_stage": "string",
    "behavior_type": "string",
    "response": {
      "type": "string (ask_field|send_promo|waiting|continue)",
      "field": "string|null",
      "prompt": "string|null"
    },
    "actions": []
  }
}
```

### Output Schema

```json
{
  "...all fields from input...",
  "plan": {
    "should_respond": "boolean",
    "response_goal": {
      "type": "string (ask_field|inform|confirm|escalate)",
      "template_id": "string|null",
      "field_to_ask": "string|null",
      "prompt_override": "string|null"
    },
    "actions_to_execute": [
      {
        "action_type": "string",
        "action_config": {},
        "priority": "integer"
      }
    ],
    "operator_mode": "string (manual|semi_auto|auto)",
    "wait_for_event": "string|null"
  },
  "trace_id": "string",
  "block3_duration_ms": "integer"
}
```

---

## Block 4: Execution

**Status:** Design
**Webhook:** `/elo-block4-executor`
**Orchestrator:** ELO_Executor

### Purpose

Executes the plan: generates AI response, notifies operator, executes actions.

### Black Box View

```
┌─────────────────────────────────────────────────────────────────┐
│                      BLOCK 4: Execution                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  INPUT (from Block 3)               OUTPUT                       │
│  ───────────────────               ──────                        │
│  {                                  {                            │
│    ...block3_output                   ...block3_data             │
│    plan.should_respond                response: {                │
│    plan.response_goal                   text                     │
│    plan.actions_to_execute              buttons[]                │
│    plan.operator_mode                   attachments[]            │
│  }                                    }                          │
│                                       actions_executed[]         │
│                                       operator_notified          │
│                                       ready_to_send              │
│                                       trace_id                   │
│                                     }                            │
│                                                                  │
├─────────────────────────────────────────────────────────────────┤
│  WORKERS:                                                        │
│  • ELO_Response_Generator — AI text generation (LLM)            │
│  • ELO_Operator_Notifier — sends to operator (FCM/WebSocket)    │
│  • ELO_Action_Executor — executes planned actions               │
└─────────────────────────────────────────────────────────────────┘
```

### Input Schema

```json
{
  "...all fields from Block 3 output...",
  "plan": {
    "should_respond": "boolean",
    "response_goal": {},
    "actions_to_execute": [],
    "operator_mode": "string"
  }
}
```

### Output Schema

```json
{
  "...all fields from input...",
  "response": {
    "text": "string|null",
    "buttons": [],
    "attachments": []
  },
  "actions_executed": [
    {
      "action_type": "string",
      "status": "string (success|failed|skipped)",
      "result": {}
    }
  ],
  "operator_notified": "boolean",
  "operator_response": "object|null",
  "ready_to_send": "boolean",
  "trace_id": "string",
  "block4_duration_ms": "integer"
}
```

---

## Block 5: Output

**Status:** Development
**Webhook:** `/elo-out-router`
**Orchestrator:** ELO_Out_Router

### Purpose

Routes response to appropriate channel and sends message to client.

### Black Box View

```
┌─────────────────────────────────────────────────────────────────┐
│                        BLOCK 5: Output                           │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  INPUT (from Block 4)               OUTPUT                       │
│  ───────────────────               ──────                        │
│  {                                  {                            │
│    channel                            status: "sent|failed"      │
│    external_chat_id                   message_id                 │
│    tenant_id                          channel                    │
│    response.text                      error: null|string         │
│    response.buttons                   trace_id                   │
│    response.attachments             }                            │
│    ready_to_send                                                 │
│  }                                                               │
│                                                                  │
├─────────────────────────────────────────────────────────────────┤
│  WORKERS:                                                        │
│  • ELO_Out_Telegram — send via Telegram Bot API                 │
│  • ELO_Out_WhatsApp — send via WhatsApp (Baileys)               │
│  • ELO_Out_Avito — send via Avito Messenger API                 │
│  • ELO_Out_VK — send via VK Community API                       │
│  • ELO_Out_MAX — send via MAX (VK Teams) API                    │
└─────────────────────────────────────────────────────────────────┘
```

### Input Schema

```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "client_id": "uuid",
  "channel": "string (telegram|whatsapp|avito|vk|max)",
  "channel_id": "integer",
  "channel_account_id": "uuid",
  "external_chat_id": "string",
  "response": {
    "text": "string",
    "buttons": [],
    "attachments": []
  },
  "ready_to_send": "boolean",
  "trace_id": "string"
}
```

### Output Schema

```json
{
  "status": "string (sent|failed|skipped)",
  "message_id": "string|null",
  "channel": "string",
  "external_chat_id": "string",
  "error": "string|null",
  "trace_id": "string",
  "block5_duration_ms": "integer"
}
```

---

## Inter-Block Communication

All blocks communicate via HTTP webhooks with JSON payloads.

| From | To | Webhook | Method |
|------|-----|---------|--------|
| Block 1 | Block 2 | `/elo-core-ingest` | POST |
| Block 2 | Block 3 | `/elo-block3-planner` | POST |
| Block 3 | Block 4 | `/elo-block4-executor` | POST |
| Block 4 | Block 5 | `/elo-out-router` | POST |

---

## Operator Modes

| Mode | Block 4 Behavior |
|------|------------------|
| `manual` | Don't generate response, only notify operator |
| `semi_auto` | Generate response, send to operator for approval |
| `auto` | Generate response, send directly to client |

---

## Error Handling

Each block should:
1. Log errors to `elo_t_events` table
2. Set `continueOnFail: true` for HTTP calls
3. Return error status in output if failed
4. Include `trace_id` in all logs

---

## Files

| Block | Specification File |
|-------|-------------------|
| Block 2 | `BLOCK_2_CONTEXT_COLLECTION.md` |
| Block 3 | `BLOCK_3_PLANNING.md` |
| Block 4 | `BLOCK_4_EXECUTION.md` |
| Block 5 | `BLOCK_5_OUTPUT.md` |

---

*Generated by Claude Code — 2026-01-04*
