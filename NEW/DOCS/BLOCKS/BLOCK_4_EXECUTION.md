# Block 4: Execution

**Version:** 1.0
**Date:** 2026-01-04
**Status:** Design
**Orchestrator:** ELO_Executor (TO CREATE)

---

## Purpose

Block 4 executes the plan from Block 3:
1. Generates AI response text (if needed)
2. Notifies operator (if needed)
3. Executes planned actions
4. Prepares response for Block 5

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          BLOCK 4: Execution                                  │
│                         Orchestrator: ELO_Executor                           │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────┐                                                            │
│  │   INPUT     │  Webhook: /elo-block4-executor                             │
│  │  (Block 3)  │                                                            │
│  └──────┬──────┘                                                            │
│         │                                                                    │
│         ▼                                                                    │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │                          ELO_Executor                                │   │
│  │                                                                       │   │
│  │  ┌─────────────┐    ┌─────────────┐                                 │   │
│  │  │  Validate   │───►│  Check Mode │                                 │   │
│  │  │   Input     │    │  & Flags    │                                 │   │
│  │  └─────────────┘    └──────┬──────┘                                 │   │
│  │                            │                                         │   │
│  │         ┌──────────────────┼──────────────────┐                      │   │
│  │         │                  │                  │                      │   │
│  │         ▼                  ▼                  ▼                      │   │
│  │  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐               │   │
│  │  │   WORKER 1   │  │   WORKER 2   │  │   WORKER 3   │               │   │
│  │  │  Response    │  │   Operator   │  │    Action    │               │   │
│  │  │  Generator   │  │   Notifier   │  │   Executor   │               │   │
│  │  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘               │   │
│  │         │                  │                  │                      │   │
│  │         └──────────────────┼──────────────────┘                      │   │
│  │                            │                                         │   │
│  │                            ▼                                         │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐              │   │
│  │  │   Merge     │───►│   Build     │───►│  Forward to │              │   │
│  │  │  Results    │    │  Response   │    │   Block 5   │              │   │
│  │  └─────────────┘    └─────────────┘    └─────────────┘              │   │
│  │                                                                       │   │
│  └───────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
│  ┌─────────────┐                                                            │
│  │   OUTPUT    │  Webhook: /elo-out-router                                  │
│  │  (Block 5)  │                                                            │
│  └─────────────┘                                                            │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Input Schema

**Webhook:** `POST /elo-block4-executor`
**Source:** Block 3 (ELO_Planner)

```json
{
  "tenant_id": "string (uuid)",
  "client_id": "string (uuid)",
  "dialog_id": "string (uuid)",
  "channel": "string",
  "channel_id": "integer",
  "channel_account_id": "string (uuid)",
  "external_chat_id": "string",
  "text": "string (original message)",
  "media": "object|null",
  "context": "object",
  "extracted": "object",
  "funnel": "object",
  "is_new_client": "boolean",
  "is_new_dialog": "boolean",
  "plan": {
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
    "actions_to_execute": [
      {
        "action_type": "string",
        "action_config": "object",
        "priority": "integer"
      }
    ],
    "operator_mode": "string",
    "wait_for_event": "string|null",
    "skip_response": "boolean"
  },
  "trace_id": "string",
  "block3_duration_ms": "integer"
}
```

---

## Output Schema

**Webhook:** `POST /elo-out-router`
**Target:** Block 5 (ELO_Out_Router)

```json
{
  "tenant_id": "string (uuid)",
  "client_id": "string (uuid)",
  "dialog_id": "string (uuid)",
  "channel": "string",
  "channel_id": "integer",
  "channel_account_id": "string (uuid)",
  "external_chat_id": "string",

  "response": {
    "text": {
      "type": "string|null",
      "description": "Generated response text"
    },
    "buttons": {
      "type": "array",
      "description": "Inline buttons for channels that support them",
      "items": {
        "text": "string",
        "callback_data": "string|null",
        "url": "string|null"
      }
    },
    "attachments": {
      "type": "array",
      "description": "Media attachments",
      "items": {
        "type": "string (photo|document|video)",
        "url": "string",
        "caption": "string|null"
      }
    },
    "keyboard": {
      "type": "object|null",
      "description": "Reply keyboard (for Telegram)",
      "schema": {
        "buttons": "array of string arrays",
        "one_time": "boolean",
        "resize": "boolean"
      }
    }
  },

  "actions_executed": {
    "type": "array",
    "description": "Results of executed actions",
    "items": {
      "action_type": "string",
      "status": "string (success|failed|skipped)",
      "result": "object|null",
      "error": "string|null"
    }
  },

  "operator_notified": {
    "type": "boolean",
    "description": "True if operator was notified"
  },

  "operator_response": {
    "type": "object|null",
    "description": "Operator response if in semi_auto mode and approved",
    "schema": {
      "approved": "boolean",
      "modified_text": "string|null",
      "operator_id": "string (uuid)"
    }
  },

  "ready_to_send": {
    "type": "boolean",
    "description": "True if response is ready to send to client"
  },

  "skip_sending": {
    "type": "boolean",
    "description": "True if sending should be skipped"
  },

  "trace_id": "string",
  "block4_duration_ms": "integer"
}
```

### Output Example

```json
{
  "tenant_id": "550e8400-e29b-41d4-a716-446655440000",
  "dialog_id": "987fcdeb-51a2-3bc4-d567-890123456789",
  "channel": "telegram",
  "channel_id": 1,
  "channel_account_id": "abc12345-6789-0def-ghij-klmnopqrstuv",
  "external_chat_id": "123456789",
  "response": {
    "text": "Отлично, iPhone 13 Pro! Подскажите, пожалуйста, как к вам обращаться?",
    "buttons": [],
    "attachments": []
  },
  "actions_executed": [
    {
      "action_type": "update_context",
      "status": "success",
      "result": { "fields_updated": ["device.brand", "device.model"] }
    }
  ],
  "operator_notified": false,
  "operator_response": null,
  "ready_to_send": true,
  "skip_sending": false,
  "trace_id": "msg_1704326400_abc123",
  "block4_duration_ms": 850
}
```

---

## Worker 1: ELO_Response_Generator

### Purpose

Generates AI response text based on response goal, context, and tone settings.

### Webhook

`POST /elo-response-generator` (TO CREATE)

### Input Schema

```json
{
  "response_goal": {
    "type": "string",
    "field_to_ask": "string|null",
    "template_id": "string|null",
    "prompt_override": "string|null",
    "tone": "string",
    "include_price": "boolean",
    "include_cta": "boolean"
  },
  "context": "object",
  "funnel": {
    "current_stage": "string",
    "behavior_type": "string"
  },
  "tenant_id": "string (uuid)",
  "dialog_id": "string (uuid)",
  "is_new_client": "boolean",
  "is_new_dialog": "boolean",
  "trace_id": "string"
}
```

### Output Schema

```json
{
  "text": {
    "type": "string",
    "description": "Generated response text"
  },
  "buttons": {
    "type": "array",
    "description": "Suggested buttons"
  },
  "attachments": {
    "type": "array",
    "description": "Attachments to include"
  },
  "keyboard": {
    "type": "object|null",
    "description": "Reply keyboard"
  },
  "generation_stats": {
    "model": "string",
    "tokens_used": "integer",
    "duration_ms": "integer"
  }
}
```

### Generation Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                      ELO_Response_Generator                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  Step 1: LOAD PROMPT TEMPLATE                                                │
│  ─────────────────────────────                                               │
│  Query elo_prompts for response generation prompt                           │
│  Apply tenant overrides if exist                                            │
│                                                                              │
│  Step 2: BUILD SYSTEM PROMPT                                                 │
│  ────────────────────────────                                                │
│  Include:                                                                    │
│  • Role description (repair shop assistant)                                 │
│  • Tone guidelines (friendly/formal/urgent)                                 │
│  • Context summary                                                          │
│  • Stage information                                                        │
│  • Response constraints                                                     │
│                                                                              │
│  Step 3: BUILD USER PROMPT                                                   │
│  ─────────────────────────                                                   │
│  Based on response_goal.type:                                               │
│                                                                              │
│  TYPE = "greeting":                                                          │
│    "Поприветствуй нового клиента. Предложи помощь."                        │
│                                                                              │
│  TYPE = "ask_field":                                                         │
│    "Спроси у клиента: {field_to_ask}.                                       │
│     Используй prompt: {prompt_override}"                                    │
│                                                                              │
│  TYPE = "inform":                                                            │
│    "Расскажи о услугах. Include price: {include_price}"                    │
│                                                                              │
│  TYPE = "confirm":                                                           │
│    "Подтверди данные клиента: {context summary}"                           │
│                                                                              │
│  TYPE = "closing":                                                           │
│    "Завершающее сообщение. Поблагодари клиента."                           │
│                                                                              │
│  Step 4: CALL LLM                                                            │
│  ─────────────────                                                           │
│  Model: qwen/qwen3-30b-a3b:free (or configured)                             │
│  Temperature: 0.7                                                            │
│  Max tokens: 500                                                             │
│                                                                              │
│  Step 5: POST-PROCESS                                                        │
│  ────────────────────                                                        │
│  • Remove thinking tags                                                     │
│  • Apply channel-specific formatting                                        │
│  • Generate buttons if include_cta                                          │
│  • Attach price list if include_price                                       │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Response Templates

| Goal Type | Template Pattern |
|-----------|------------------|
| `greeting` | Приветствие + предложение помощи |
| `ask_field` | Вежливый вопрос о поле |
| `inform` | Информация + call to action |
| `confirm` | Резюме данных + подтверждение |
| `waiting` | Ожидание действия + доступные опции |
| `closing` | Благодарность + контакты |

### Tone Guidelines

| Tone | Characteristics |
|------|-----------------|
| `friendly` | Неформальный, emoji допустимы, "ты" |
| `formal` | Уважительный, без emoji, "вы" |
| `urgent` | Краткий, деловой, без лишних слов |

---

## Worker 2: ELO_Operator_Notifier

### Purpose

Sends notifications to operators via FCM, WebSocket, or both.

### Webhook

`POST /elo-operator-notifier` (TO CREATE)

### Input Schema

```json
{
  "tenant_id": "string (uuid)",
  "dialog_id": "string (uuid)",
  "client_id": "string (uuid)",
  "channel": "string",
  "operator_mode": "string",
  "notification_type": {
    "type": "string",
    "enum": ["new_message", "escalation", "approval_required", "action_required"]
  },
  "message_preview": "string",
  "draft_response": "string|null (for semi_auto)",
  "context_summary": "object",
  "trace_id": "string"
}
```

### Output Schema

```json
{
  "notified": "boolean",
  "notification_channels": {
    "fcm": {
      "sent": "boolean",
      "device_count": "integer",
      "error": "string|null"
    },
    "websocket": {
      "sent": "boolean",
      "active_sessions": "integer",
      "error": "string|null"
    }
  },
  "operators_notified": [
    {
      "operator_id": "string (uuid)",
      "name": "string",
      "channel": "string (fcm|websocket)"
    }
  ]
}
```

### Notification Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       ELO_Operator_Notifier                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  Step 1: GET ASSIGNED OPERATORS                                              │
│  ──────────────────────────────                                              │
│  Query elo_t_operator_channels for:                                         │
│  • channel_account_id match                                                 │
│  • is_active = true                                                         │
│  • Get operator_id list                                                     │
│                                                                              │
│  Step 2: GET OPERATOR DEVICES                                                │
│  ─────────────────────────────                                               │
│  Query elo_t_operator_devices for:                                          │
│  • operator_id IN (assigned operators)                                      │
│  • is_active = true                                                         │
│  • Get fcm_tokens, websocket sessions                                       │
│                                                                              │
│  Step 3: BUILD NOTIFICATION PAYLOAD                                          │
│  ───────────────────────────────────                                         │
│  {                                                                           │
│    "type": notification_type,                                               │
│    "dialog_id": dialog_id,                                                  │
│    "client_name": context.owner.label,                                      │
│    "channel": channel,                                                      │
│    "preview": message_preview,                                              │
│    "draft": draft_response,                                                 │
│    "timestamp": now                                                         │
│  }                                                                           │
│                                                                              │
│  Step 4: SEND FCM NOTIFICATIONS                                              │
│  ──────────────────────────────                                              │
│  For each fcm_token:                                                        │
│  • POST to FCM API                                                          │
│  • Track success/failure                                                    │
│                                                                              │
│  Step 5: SEND WEBSOCKET NOTIFICATIONS                                        │
│  ─────────────────────────────────────                                       │
│  For each active websocket session:                                         │
│  • Send JSON message                                                        │
│  • Track delivery                                                           │
│                                                                              │
│  Step 6: LOG NOTIFICATION                                                    │
│  ─────────────────────────                                                   │
│  Insert into elo_t_events                                                   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

### Notification Types

| Type | When | Contains |
|------|------|----------|
| `new_message` | New message in manual mode | Message preview |
| `escalation` | Issue escalated | Full context |
| `approval_required` | Semi-auto mode | Draft + approve/reject |
| `action_required` | CTA detected | Action details |

---

## Worker 3: ELO_Action_Executor

### Purpose

Executes planned actions in priority order.

### Webhook

`POST /elo-action-executor` (TO CREATE)

### Input Schema

```json
{
  "actions_to_execute": [
    {
      "action_type": "string",
      "action_config": "object",
      "priority": "integer"
    }
  ],
  "context": "object",
  "tenant_id": "string (uuid)",
  "dialog_id": "string (uuid)",
  "trace_id": "string"
}
```

### Output Schema

```json
{
  "actions_executed": [
    {
      "action_type": "string",
      "status": "string (success|failed|skipped)",
      "result": "object|null",
      "error": "string|null",
      "duration_ms": "integer"
    }
  ],
  "all_succeeded": "boolean"
}
```

### Supported Actions

| Action Type | Description | Config |
|-------------|-------------|--------|
| `update_context` | Update dialog context | `{ fields: {} }` |
| `write_to_graph` | Write to Neo4j | `{ node_type, properties }` |
| `send_template` | Send template message | `{ template_id }` |
| `schedule_reminder` | Schedule future message | `{ delay_minutes, template_id }` |
| `close_dialog` | Close dialog | `{ reason, summary }` |
| `call_http` | External HTTP call | `{ url, method, body }` |
| `notify_operator` | Notify operator | `{ type, message }` |

### Execution Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                       ELO_Action_Executor                                    │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  Step 1: SORT BY PRIORITY                                                    │
│  ─────────────────────────                                                   │
│  Sort actions by priority (1 = highest)                                     │
│                                                                              │
│  Step 2: EXECUTE EACH ACTION                                                 │
│  ────────────────────────────                                                │
│  FOR each action IN sorted_actions:                                         │
│                                                                              │
│    SWITCH action.action_type:                                               │
│                                                                              │
│      CASE "update_context":                                                 │
│        UPDATE elo_t_dialogs SET context = merge(context, action.fields)   │
│        → result: { fields_updated }                                        │
│                                                                              │
│      CASE "write_to_graph":                                                 │
│        Call Neo4j Cypher API                                               │
│        → result: { nodes_created }                                         │
│                                                                              │
│      CASE "send_template":                                                  │
│        Query elo_prompts for template                                      │
│        Render with context                                                 │
│        Add to response attachments                                         │
│        → result: { template_rendered }                                     │
│                                                                              │
│      CASE "schedule_reminder":                                              │
│        Insert into elo_t_tasks                                             │
│        → result: { task_id, scheduled_at }                                 │
│                                                                              │
│      CASE "close_dialog":                                                   │
│        UPDATE elo_t_dialogs SET status = 'closed'                         │
│        → result: { closed_at }                                             │
│                                                                              │
│      CASE "call_http":                                                      │
│        HTTP request to external URL                                        │
│        → result: { response_code, body }                                   │
│                                                                              │
│    Track status: success | failed | skipped                                │
│                                                                              │
│  Step 3: AGGREGATE RESULTS                                                   │
│  ──────────────────────────                                                  │
│  Collect all action results                                                 │
│  Calculate all_succeeded flag                                               │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Orchestrator Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                        ELO_Executor (Orchestrator)                           │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  Step 1: RECEIVE & VALIDATE                                                  │
│  ──────────────────────────                                                  │
│  • Receive from Block 3 via /elo-block4-executor                            │
│  • Record block4_start timestamp                                            │
│                                                                              │
│  Step 2: CHECK SKIP CONDITIONS                                               │
│  ──────────────────────────────                                              │
│  IF plan.skip_response OR NOT plan.should_respond:                          │
│    → Skip response generation                                               │
│    → Proceed to actions only                                                │
│                                                                              │
│  Step 3: PARALLEL EXECUTION                                                  │
│  ───────────────────────────                                                 │
│  Execute in parallel (where possible):                                      │
│                                                                              │
│  ┌───────────────┐  ┌───────────────┐  ┌───────────────┐                   │
│  │ IF should_    │  │ IF notify_    │  │ IF actions    │                   │
│  │ respond:      │  │ operator:     │  │ to execute:   │                   │
│  │               │  │               │  │               │                   │
│  │ Call Response │  │ Call Operator │  │ Call Action   │                   │
│  │ Generator     │  │ Notifier      │  │ Executor      │                   │
│  └───────┬───────┘  └───────┬───────┘  └───────┬───────┘                   │
│          │                  │                  │                            │
│          └──────────────────┼──────────────────┘                            │
│                             │                                               │
│                             ▼                                               │
│                                                                              │
│  Step 4: WAIT FOR APPROVAL (if semi_auto)                                    │
│  ────────────────────────────────────────                                    │
│  IF operator_mode == "semi_auto" AND wait_for_event == "operator_approval": │
│    → Wait for operator response (timeout: 5 min)                            │
│    → Apply modifications if any                                             │
│    OR skip if rejected                                                      │
│                                                                              │
│  Step 5: MERGE RESULTS                                                       │
│  ─────────────────────                                                       │
│  Combine:                                                                    │
│  • Generated response (text, buttons, attachments)                          │
│  • Operator notification status                                             │
│  • Action execution results                                                 │
│                                                                              │
│  Step 6: BUILD OUTPUT                                                        │
│  ────────────────────                                                        │
│  • Compile response object                                                  │
│  • Set ready_to_send flag                                                   │
│  • Calculate block4_duration_ms                                             │
│                                                                              │
│  Step 7: FORWARD TO BLOCK 5                                                  │
│  ──────────────────────────                                                  │
│  IF ready_to_send AND NOT skip_sending:                                     │
│    → HTTP POST to /elo-out-router                                           │
│                                                                              │
│  Step 8: RESPOND                                                             │
│  ────────────────                                                            │
│  Return status to caller                                                    │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Operator Approval Flow (Semi-Auto Mode)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                     SEMI-AUTO APPROVAL FLOW                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  1. GENERATE DRAFT                                                           │
│     ELO_Response_Generator creates draft response                           │
│                                                                              │
│  2. NOTIFY OPERATOR                                                          │
│     ELO_Operator_Notifier sends:                                            │
│     {                                                                        │
│       type: "approval_required",                                            │
│       draft_response: "generated text",                                     │
│       client_message: "original message",                                   │
│       context_summary: {...}                                                │
│     }                                                                        │
│                                                                              │
│  3. WAIT FOR RESPONSE                                                        │
│     Options:                                                                 │
│     a) Operator approves → ready_to_send = true                             │
│     b) Operator modifies → use modified_text                                │
│     c) Operator rejects → skip_sending = true                               │
│     d) Timeout (5 min) → auto-approve OR escalate (configurable)           │
│                                                                              │
│  4. CONTINUE TO BLOCK 5                                                      │
│     With approved/modified response                                         │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Database Dependencies

### Read Operations

| Table | Purpose |
|-------|---------|
| `elo_prompts` | Get generation prompts |
| `elo_t_operator_channels` | Get assigned operators |
| `elo_t_operator_devices` | Get FCM tokens, websockets |
| `elo_t_price_list` | Get prices (if include_price) |

### Write Operations

| Table | Purpose |
|-------|---------|
| `elo_t_dialogs` | Update context, status |
| `elo_t_messages` | Store generated message |
| `elo_t_tasks` | Schedule reminders |
| `elo_t_events` | Log execution events |

---

## External Dependencies

| Service | Type | Purpose |
|---------|------|---------|
| OpenRouter API | HTTP | LLM inference for generation |
| FCM API | HTTP | Push notifications |
| Neo4j | HTTP | Graph writes |
| Block 5 | HTTP | Forward output to /elo-out-router |

---

## Test Scenarios

### Scenario 1: Auto Mode - Generate and Send

**Input:**
```json
{
  "plan": {
    "should_respond": true,
    "response_goal": {
      "type": "ask_field",
      "field_to_ask": "owner.label",
      "tone": "friendly"
    },
    "operator_mode": "auto",
    "actions_to_execute": []
  }
}
```

**Expected Output:**
```json
{
  "response": {
    "text": "Подскажите, как к вам обращаться? 😊"
  },
  "ready_to_send": true,
  "operator_notified": false
}
```

### Scenario 2: Semi-Auto Mode - Wait for Approval

**Input:**
```json
{
  "plan": {
    "should_respond": true,
    "operator_mode": "semi_auto",
    "wait_for_event": "operator_approval"
  }
}
```

**Expected Output:**
```json
{
  "response": {
    "text": "Draft response..."
  },
  "ready_to_send": false,
  "operator_notified": true,
  "operator_response": null  // Waiting
}
```

### Scenario 3: Manual Mode - Notify Only

**Input:**
```json
{
  "plan": {
    "should_respond": false,
    "skip_response": true,
    "operator_mode": "manual",
    "actions_to_execute": [
      { "action_type": "notify_operator" }
    ]
  }
}
```

**Expected Output:**
```json
{
  "response": { "text": null },
  "ready_to_send": false,
  "skip_sending": true,
  "operator_notified": true
}
```

---

## Performance Metrics

| Metric | Target |
|--------|--------|
| Block 4 total time | < 3000ms |
| Response generation | < 2000ms |
| Operator notification | < 500ms |
| Action execution | < 1000ms |

---

## Files (TO CREATE)

| File | Location |
|------|----------|
| Orchestrator | `NEW/workflows/Core/ELO_Executor.json` |
| Worker 1 | `NEW/workflows/Core/ELO_Response_Generator.json` |
| Worker 2 | `NEW/workflows/Core/ELO_Operator_Notifier.json` |
| Worker 3 | `NEW/workflows/Core/ELO_Action_Executor.json` |

---

## Implementation Notes

### Simplified Version

For initial implementation, Block 4 can be a single workflow:

```javascript
// ELO_Executor - Simple Version
const input = $input.first().json;
const plan = input.plan;

let response = { text: null, buttons: [], attachments: [] };
let ready_to_send = false;

// Generate response if needed
if (plan.should_respond && !plan.skip_response) {
  // Call LLM for response generation
  // ... (simplified: use template-based response)

  const goal = plan.response_goal;
  switch (goal.type) {
    case 'ask_field':
      response.text = goal.prompt_override || `Пожалуйста, укажите ${goal.field_to_ask}`;
      break;
    case 'greeting':
      response.text = 'Здравствуйте! Чем могу помочь?';
      break;
    case 'inform':
      response.text = 'Информация о наших услугах...';
      break;
  }

  ready_to_send = plan.operator_mode === 'auto';
}

return {
  ...input,
  response,
  actions_executed: [],
  operator_notified: false,
  ready_to_send,
  skip_sending: plan.skip_response,
  block4_duration_ms: Date.now() - input.block4_start
};
```

---

*Generated by Claude Code — 2026-01-04*
