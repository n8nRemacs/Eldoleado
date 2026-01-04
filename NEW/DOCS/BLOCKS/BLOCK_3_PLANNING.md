# Block 3: Planning

**Version:** 1.0
**Date:** 2026-01-04
**Status:** Design
**Orchestrator:** ELO_Planner (TO CREATE)

---

## Purpose

Block 3 analyzes context and funnel state to decide what to do next:
1. Should AI respond to the client?
2. What should the response goal be?
3. Which actions need to be executed?
4. What is the operator mode?
5. Should we wait for an event?

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           BLOCK 3: Planning                                  │
│                          Orchestrator: ELO_Planner                           │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────┐                                                            │
│  │   INPUT     │  Webhook: /elo-block3-planner                              │
│  │  (Block 2)  │                                                            │
│  └──────┬──────┘                                                            │
│         │                                                                    │
│         ▼                                                                    │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                           ELO_Planner                                │   │
│  │                                                                       │   │
│  │  ┌─────────────┐    ┌─────────────┐                                 │   │
│  │  │  Validate   │───►│  Get Mode   │                                 │   │
│  │  │   Input     │    │  Settings   │                                 │   │
│  │  └─────────────┘    └──────┬──────┘                                 │   │
│  │                            │                                         │   │
│  │         ┌──────────────────┘                                         │   │
│  │         │                                                            │   │
│  │         ▼                                                            │   │
│  │  ┌──────────────────────────────────────┐                           │   │
│  │  │     WORKER 1: ELO_Response_Planner   │                           │   │
│  │  │     Decides response strategy        │                           │   │
│  │  └───────────────────┬──────────────────┘                           │   │
│  │                      │                                               │   │
│  │         ┌────────────┘                                               │   │
│  │         ▼                                                            │   │
│  │  ┌──────────────────────────────────────┐                           │   │
│  │  │     WORKER 2: ELO_Action_Planner     │                           │   │
│  │  │     Plans action execution           │                           │   │
│  │  └───────────────────┬──────────────────┘                           │   │
│  │                      │                                               │   │
│  │         ┌────────────┘                                               │   │
│  │         ▼                                                            │   │
│  │  ┌──────────────────────────────────────┐                           │   │
│  │  │     WORKER 3: ELO_Mode_Router        │                           │   │
│  │  │     Applies operator mode logic      │                           │   │
│  │  └───────────────────┬──────────────────┘                           │   │
│  │                      │                                               │   │
│  │         ┌────────────┘                                               │   │
│  │         ▼                                                            │   │
│  │  ┌─────────────┐    ┌─────────────┐                                 │   │
│  │  │   Build     │───►│  Forward to │                                 │   │
│  │  │   Plan      │    │   Block 4   │                                 │   │
│  │  └─────────────┘    └─────────────┘                                 │   │
│  │                                                                       │   │
│  └───────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
│  ┌─────────────┐                                                            │
│  │   OUTPUT    │  Webhook: /elo-block4-executor                             │
│  │  (Block 4)  │                                                            │
│  └─────────────┘                                                            │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Input Schema

**Webhook:** `POST /elo-block3-planner`
**Source:** Block 2 (ELO_Context_Collector)

```json
{
  "tenant_id": "string (uuid)",
  "client_id": "string (uuid)",
  "dialog_id": "string (uuid)",
  "channel": "string",
  "channel_id": "integer",
  "channel_account_id": "string (uuid)",
  "external_chat_id": "string",
  "text": "string",
  "media": "object|null",
  "context": "object (merged context)",
  "extracted": {
    "entities": "array",
    "grouped": "object",
    "model": "string",
    "tokens_used": "integer"
  },
  "funnel": {
    "previous_stage": "string|null",
    "current_stage": "string",
    "changed": "boolean",
    "behavior_type": "string",
    "masks": {
      "current": "string",
      "required": "string",
      "complete": "boolean"
    },
    "response": {
      "type": "string",
      "field": "string|null",
      "prompt": "string|null"
    },
    "actions": "array"
  },
  "is_new_client": "boolean",
  "is_new_dialog": "boolean",
  "trace_id": "string",
  "block2_duration_ms": "integer"
}
```

---

## Output Schema

**Webhook:** `POST /elo-block4-executor`
**Target:** Block 4 (ELO_Executor)

```json
{
  "...all input fields...": "passthrough",

  "plan": {
    "should_respond": {
      "type": "boolean",
      "description": "True if AI should generate response"
    },
    "response_goal": {
      "type": "object|null",
      "description": "Response generation goal",
      "schema": {
        "type": "string (ask_field|inform|confirm|escalate|greeting|closing)",
        "template_id": "string|null (for template-based responses)",
        "field_to_ask": "string|null (field path to ask for)",
        "prompt_override": "string|null (custom prompt for generation)",
        "tone": "string (friendly|formal|urgent)",
        "include_price": "boolean",
        "include_cta": "boolean"
      }
    },
    "actions_to_execute": {
      "type": "array",
      "description": "Actions for Block 4 to execute",
      "items": {
        "action_type": "string",
        "action_config": "object",
        "priority": "integer (1=highest)"
      }
    },
    "operator_mode": {
      "type": "string",
      "enum": ["manual", "semi_auto", "auto"],
      "description": "Current operator mode"
    },
    "wait_for_event": {
      "type": "string|null",
      "description": "Event to wait for before continuing",
      "enum": [null, "operator_approval", "payment_confirmation", "external_callback"]
    },
    "skip_response": {
      "type": "boolean",
      "description": "True if response should be skipped (e.g., after escalation)"
    },
    "reason": {
      "type": "string",
      "description": "Explanation of planning decision"
    }
  },

  "trace_id": "string",
  "block3_duration_ms": "integer"
}
```

### Output Example

```json
{
  "tenant_id": "550e8400-e29b-41d4-a716-446655440000",
  "dialog_id": "987fcdeb-51a2-3bc4-d567-890123456789",
  "channel": "telegram",
  "funnel": {
    "current_stage": "data_collection",
    "behavior_type": "COLLECT_REQUIRED",
    "response": {
      "type": "ask_field",
      "field": "owner.label",
      "prompt": "Как к вам обращаться?"
    }
  },
  "plan": {
    "should_respond": true,
    "response_goal": {
      "type": "ask_field",
      "field_to_ask": "owner.label",
      "prompt_override": null,
      "tone": "friendly"
    },
    "actions_to_execute": [],
    "operator_mode": "auto",
    "wait_for_event": null,
    "skip_response": false,
    "reason": "Stage requires owner.label field"
  },
  "trace_id": "msg_1704326400_abc123",
  "block3_duration_ms": 45
}
```

---

## Worker 1: ELO_Response_Planner

### Purpose

Decides whether to respond and what the response goal should be.

### Webhook

`POST /elo-response-planner` (TO CREATE)

### Input Schema

```json
{
  "funnel": {
    "current_stage": "string",
    "behavior_type": "string",
    "response": "object|null",
    "masks": "object"
  },
  "context": "object",
  "is_new_client": "boolean",
  "is_new_dialog": "boolean",
  "text": "string",
  "trace_id": "string"
}
```

### Output Schema

```json
{
  "should_respond": "boolean",
  "response_goal": {
    "type": "string",
    "field_to_ask": "string|null",
    "template_id": "string|null",
    "prompt_override": "string|null",
    "tone": "string",
    "include_price": "boolean",
    "include_cta": "boolean"
  },
  "reason": "string"
}
```

### Decision Logic

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      RESPONSE PLANNING LOGIC                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  1. NEW DIALOG HANDLING                                                      │
│  ──────────────────────                                                      │
│  IF is_new_dialog AND is_new_client:                                        │
│    → response_goal.type = "greeting"                                        │
│    → should_respond = true                                                  │
│                                                                              │
│  2. BEHAVIOR TYPE ROUTING                                                    │
│  ──────────────────────────                                                  │
│  SWITCH funnel.behavior_type:                                               │
│                                                                              │
│    CASE "COLLECT_REQUIRED":                                                 │
│      IF funnel.response.type == "ask_field":                               │
│        → response_goal.type = "ask_field"                                  │
│        → response_goal.field_to_ask = funnel.response.field               │
│        → should_respond = true                                             │
│      ELSE IF masks.complete:                                                │
│        → response_goal.type = "confirm"                                    │
│        → should_respond = true                                             │
│                                                                              │
│    CASE "COLLECT_OPTIONAL":                                                 │
│      IF funnel.response.type == "ask_optional":                            │
│        → response_goal.type = "ask_field"                                  │
│        → response_goal.tone = "casual"                                     │
│        → should_respond = true                                             │
│      ELSE IF funnel.response.type == "skip":                               │
│        → should_respond = false                                            │
│                                                                              │
│    CASE "SEND_PROMO":                                                       │
│      → response_goal.type = "inform"                                       │
│      → response_goal.template_id = behavior_config.template_id            │
│      → response_goal.include_price = true                                  │
│      → should_respond = true                                               │
│                                                                              │
│    CASE "CTA_WAIT":                                                         │
│      IF funnel.response.type == "cta_detected":                            │
│        → Handle detected action                                            │
│        → should_respond = depends on action                               │
│      ELSE:                                                                  │
│        → response_goal.type = "waiting"                                    │
│        → response_goal.include_cta = true                                  │
│        → should_respond = true                                             │
│                                                                              │
│  3. ESCALATION DETECTION                                                     │
│  ─────────────────────────                                                   │
│  IF context.requires_escalation OR funnel.behavior_type == "escalate":     │
│    → response_goal.type = "escalate"                                       │
│    → should_respond = false (operator will respond)                        │
│                                                                              │
│  4. CLOSING DETECTION                                                        │
│  ────────────────────                                                        │
│  IF funnel.current_stage == "confirmation" AND masks.complete:             │
│    → response_goal.type = "closing"                                        │
│    → should_respond = true                                                 │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Response Goal Types

| Type | Description | Use Case |
|------|-------------|----------|
| `greeting` | Welcome message | New client/dialog |
| `ask_field` | Ask for specific field | Field collection |
| `inform` | Provide information | Send promo, prices |
| `confirm` | Confirm collected data | Before action |
| `escalate` | Hand over to operator | Complex issue |
| `waiting` | Waiting for action | CTA stage |
| `closing` | Closing message | Dialog complete |

---

## Worker 2: ELO_Action_Planner

### Purpose

Plans which actions should be executed based on funnel response and stage transitions.

### Webhook

`POST /elo-action-planner` (TO CREATE)

### Input Schema

```json
{
  "funnel": {
    "actions": "array",
    "current_stage": "string",
    "changed": "boolean"
  },
  "context": "object",
  "response_goal": "object (from ELO_Response_Planner)",
  "trace_id": "string"
}
```

### Output Schema

```json
{
  "actions_to_execute": [
    {
      "action_type": "string",
      "action_config": "object",
      "priority": "integer"
    }
  ],
  "reason": "string"
}
```

### Action Types

| Action Type | Priority | Description |
|-------------|----------|-------------|
| `notify_operator` | 1 | Send notification to operator |
| `send_template` | 2 | Send predefined template |
| `update_context` | 3 | Update context in database |
| `write_to_graph` | 4 | Write to Neo4j graph |
| `call_http` | 5 | Call external HTTP API |
| `schedule_reminder` | 6 | Schedule follow-up message |
| `close_dialog` | 7 | Close the dialog |

### Decision Logic

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        ACTION PLANNING LOGIC                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  1. PROCESS FUNNEL ACTIONS                                                   │
│  ──────────────────────────                                                  │
│  FOR each action IN funnel.actions:                                         │
│    → Add to actions_to_execute with appropriate priority                   │
│                                                                              │
│  2. STAGE TRANSITION ACTIONS                                                 │
│  ────────────────────────────                                                │
│  IF funnel.changed:                                                         │
│    → Check stage.on_exit_actions for previous stage                        │
│    → Check stage.on_enter_actions for new stage                            │
│    → Add to actions_to_execute                                             │
│                                                                              │
│  3. RESPONSE-BASED ACTIONS                                                   │
│  ──────────────────────────                                                  │
│  IF response_goal.type == "escalate":                                       │
│    → Add notify_operator action (priority 1)                               │
│                                                                              │
│  IF response_goal.include_price:                                            │
│    → May need to fetch pricing first                                       │
│                                                                              │
│  4. CONTEXT UPDATE ACTIONS                                                   │
│  ──────────────────────────                                                  │
│  IF extracted entities exist:                                               │
│    → Add update_context action                                             │
│    → Add write_to_graph action (if Neo4j enabled)                          │
│                                                                              │
│  5. PRIORITY SORTING                                                         │
│  ────────────────────                                                        │
│  Sort actions by priority (1 = highest)                                     │
│  Remove duplicates                                                          │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Worker 3: ELO_Mode_Router

### Purpose

Applies operator mode logic and determines if approval is needed.

### Webhook

`POST /elo-mode-router` (TO CREATE)

### Input Schema

```json
{
  "tenant_id": "string (uuid)",
  "dialog_id": "string (uuid)",
  "channel_account_id": "string (uuid)",
  "response_goal": "object",
  "actions_to_execute": "array",
  "trace_id": "string"
}
```

### Output Schema

```json
{
  "operator_mode": "string (manual|semi_auto|auto)",
  "wait_for_event": "string|null",
  "modifications": {
    "should_respond": "boolean|null (override if needed)",
    "skip_response": "boolean",
    "add_actions": "array (additional actions)"
  },
  "reason": "string"
}
```

### Mode Logic

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          OPERATOR MODE LOGIC                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  1. GET OPERATOR MODE                                                        │
│  ─────────────────────                                                       │
│  Query elo_t_channel_accounts.operator_mode                                 │
│  Query elo_t_dialogs.operator_mode_override                                 │
│  effective_mode = override ?? channel_mode ?? 'auto'                        │
│                                                                              │
│  2. APPLY MODE RULES                                                         │
│  ────────────────────                                                        │
│                                                                              │
│  MODE = "manual":                                                            │
│  ─────────────────                                                           │
│  • should_respond = false                                                   │
│  • skip_response = true                                                     │
│  • add_actions = [notify_operator]                                          │
│  • reason = "Manual mode: operator handles everything"                      │
│                                                                              │
│  MODE = "semi_auto":                                                         │
│  ───────────────────                                                         │
│  • should_respond = true                                                    │
│  • wait_for_event = "operator_approval"                                     │
│  • add_actions = [notify_operator_with_draft]                               │
│  • reason = "Semi-auto: generate draft, wait for approval"                 │
│                                                                              │
│  MODE = "auto":                                                              │
│  ──────────────                                                              │
│  • should_respond = true                                                    │
│  • wait_for_event = null                                                    │
│  • reason = "Auto mode: send response directly"                            │
│                                                                              │
│  3. ESCALATION OVERRIDE                                                      │
│  ───────────────────────                                                     │
│  IF response_goal.type == "escalate":                                       │
│    → Always notify operator                                                 │
│    → wait_for_event = "operator_response"                                  │
│                                                                              │
│  4. SPECIAL CASES                                                            │
│  ─────────────────                                                           │
│  IF dialog.is_vip OR context.priority == "high":                           │
│    → Force semi_auto mode                                                  │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Operator Modes

| Mode | Generate Response | Wait for Approval | Send to Client |
|------|-------------------|-------------------|----------------|
| `manual` | No | N/A | Operator sends |
| `semi_auto` | Yes | Yes | After approval |
| `auto` | Yes | No | Immediately |

---

## Orchestrator Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        ELO_Planner (Orchestrator)                            │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  Step 1: RECEIVE & VALIDATE                                                  │
│  ──────────────────────────                                                  │
│  • Receive from Block 2 via /elo-block3-planner                             │
│  • Validate required fields                                                 │
│  • Record block3_start timestamp                                            │
│                                                                              │
│  Step 2: CALL ELO_Response_Planner                                          │
│  ──────────────────────────────────                                          │
│  • Input: funnel, context, is_new_client, is_new_dialog, text              │
│  • Output: should_respond, response_goal, reason                           │
│                                                                              │
│  Step 3: CALL ELO_Action_Planner                                            │
│  ────────────────────────────────                                            │
│  • Input: funnel, context, response_goal                                   │
│  • Output: actions_to_execute                                              │
│                                                                              │
│  Step 4: CALL ELO_Mode_Router                                               │
│  ─────────────────────────────                                               │
│  • Input: tenant_id, dialog_id, channel_account_id, response_goal, actions │
│  • Output: operator_mode, wait_for_event, modifications                    │
│                                                                              │
│  Step 5: MERGE RESULTS                                                       │
│  ─────────────────────                                                       │
│  • Apply mode modifications to plan                                         │
│  • Finalize actions list                                                    │
│                                                                              │
│  Step 6: BUILD PLAN OUTPUT                                                   │
│  ─────────────────────────                                                   │
│  • Compile final plan object                                                │
│  • Calculate block3_duration_ms                                             │
│                                                                              │
│  Step 7: FORWARD TO BLOCK 4                                                  │
│  ──────────────────────────                                                  │
│  • HTTP POST to /elo-block4-executor                                        │
│                                                                              │
│  Step 8: RESPOND                                                             │
│  ────────────────                                                            │
│  • Return status to caller                                                  │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Database Dependencies

### Read Operations

| Table | Purpose |
|-------|---------|
| `elo_t_channel_accounts` | Get operator_mode setting |
| `elo_t_dialogs` | Get operator_mode_override |
| `elo_funnel_stages` | Get on_enter_actions, on_exit_actions |
| `elo_prompts` | Get response templates |

### Write Operations

| Table | Purpose |
|-------|---------|
| `elo_t_events` | Log planning decisions |

---

## Test Scenarios

### Scenario 1: Auto Mode - Ask Field

**Input:**
```json
{
  "funnel": {
    "behavior_type": "COLLECT_REQUIRED",
    "response": { "type": "ask_field", "field": "device.brand" }
  },
  "operator_mode": "auto"
}
```

**Expected Output:**
```json
{
  "plan": {
    "should_respond": true,
    "response_goal": { "type": "ask_field", "field_to_ask": "device.brand" },
    "operator_mode": "auto",
    "wait_for_event": null
  }
}
```

### Scenario 2: Semi-Auto Mode

**Input:**
```json
{
  "funnel": {
    "behavior_type": "SEND_PROMO"
  },
  "operator_mode": "semi_auto"
}
```

**Expected Output:**
```json
{
  "plan": {
    "should_respond": true,
    "response_goal": { "type": "inform", "include_price": true },
    "operator_mode": "semi_auto",
    "wait_for_event": "operator_approval",
    "actions_to_execute": [
      { "action_type": "notify_operator_with_draft", "priority": 1 }
    ]
  }
}
```

### Scenario 3: Manual Mode - Escalation

**Input:**
```json
{
  "funnel": {
    "behavior_type": "escalate"
  },
  "operator_mode": "manual"
}
```

**Expected Output:**
```json
{
  "plan": {
    "should_respond": false,
    "skip_response": true,
    "operator_mode": "manual",
    "actions_to_execute": [
      { "action_type": "notify_operator", "priority": 1 }
    ],
    "reason": "Manual mode: operator handles escalation"
  }
}
```

---

## Performance Metrics

| Metric | Target |
|--------|--------|
| Block 3 total time | < 200ms |
| Response planning | < 50ms |
| Action planning | < 50ms |
| Mode routing | < 50ms |

---

## Files (TO CREATE)

| File | Location |
|------|----------|
| Orchestrator | `NEW/workflows/Core/ELO_Planner.json` |
| Worker 1 | `NEW/workflows/Core/ELO_Response_Planner.json` |
| Worker 2 | `NEW/workflows/Core/ELO_Action_Planner.json` |
| Worker 3 | `NEW/workflows/Core/ELO_Mode_Router.json` |

---

## Implementation Notes

### Simplified Option

Block 3 can be implemented as a single Code node if workers are not needed initially:

```javascript
// ELO_Planner - Simple Version
const input = $input.first().json;
const funnel = input.funnel;

// Response planning
let should_respond = true;
let response_goal = null;

switch (funnel.behavior_type) {
  case 'COLLECT_REQUIRED':
  case 'COLLECT_OPTIONAL':
    response_goal = {
      type: funnel.response.type,
      field_to_ask: funnel.response.field,
      prompt_override: funnel.response.prompt
    };
    break;
  case 'SEND_PROMO':
    response_goal = { type: 'inform', include_price: true };
    break;
  case 'CTA_WAIT':
    response_goal = { type: 'waiting', include_cta: true };
    break;
  case 'escalate':
    should_respond = false;
    response_goal = { type: 'escalate' };
    break;
}

// Default to auto mode
const operator_mode = 'auto';
const wait_for_event = null;

return {
  ...input,
  plan: {
    should_respond,
    response_goal,
    actions_to_execute: funnel.actions || [],
    operator_mode,
    wait_for_event,
    skip_response: false,
    reason: `${funnel.behavior_type} → ${response_goal?.type}`
  },
  block3_duration_ms: Date.now() - input.block3_start
};
```

---

*Generated by Claude Code — 2026-01-04*
