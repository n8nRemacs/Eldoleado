# Operator Notification Architecture

**Last updated:** 2026-01-03

---

## Overview

This document describes the architecture for notifying operators based on their work mode (manual, semi_auto, auto).

---

## Operator Modes

| Mode | AI generates response | Operator approves |
|------|----------------------|-------------------|
| `manual` | NO | NO |
| `semi_auto` | YES | YES |
| `auto` | YES | NO |

**Key insight:** The pipeline (extraction, funnel, context) runs identically for ALL modes.
The only differences are:
1. Whether AI generates a response text
2. Whether operator must approve before sending

- **manual**: Operator sees full context (device, symptom, stage), writes response from scratch
- **semi_auto**: AI generates draft, operator approves/edits before sending
- **auto**: AI generates and sends automatically (operator sees only escalations)

---

## Mode Storage

**Priority hierarchy:**
```
effective_mode = operator.settings.ai_mode   <- PRIORITY (if set)
                 ?? tenant.settings.ai_mode  <- fallback (tenant default)
                 ?? 'manual'                 <- system default
```

**Database locations:**

| Level | Table | Field | Example |
|-------|-------|-------|---------|
| Tenant | `elo_t_tenants` | `settings.ai_mode` | `{"ai_mode": "auto"}` |
| Operator | `elo_t_operators` | `settings.ai_mode` | `{"ai_mode": "manual"}` |

---

## Pipeline Flow

**IMPORTANT:** AI pipeline runs for ALL modes. The difference is only WHO RESPONDS.

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                    PIPELINE (RUNS ALWAYS, REGARDLESS OF MODE)                    │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                  │
│  ELO_In_* -> Batch -> Processor -> ELO_Resolver -> ELO_Pipeline_Orchestrator    │
│                                                              │                   │
│                                                              ▼                   │
│  ┌──────────────────────────────────────────────────────────────────────────┐   │
│  │  AI CONTEXT BUILDING (MANDATORY FOR ALL MODES)                           │   │
│  │                                                                           │   │
│  │  1. ELO_Task_Dispatcher -> Extract entities                              │   │
│  │     - device.brand, device.model                                          │   │
│  │     - symptom, complaint                                                  │   │
│  │     - intent                                                              │   │
│  │                                                                           │   │
│  │  2. ELO_Results_Aggregator -> Merge extractions                          │   │
│  │                                                                           │   │
│  │  3. ELO_Funnel_Controller -> Update funnel stage                         │   │
│  │     - Record stage transitions                                            │   │
│  │     - Update dialog context                                               │   │
│  │                                                                           │   │
│  │  4. Save to DB                                                            │   │
│  │     - elo_t_messages                                                      │   │
│  │     - elo_t_events                                                        │   │
│  │     - elo_t_ai_extractions                                                │   │
│  │     - elo_dialog_stage_history                                            │   │
│  │                                                                           │   │
│  │  OUTPUT: full_context = {                                                 │   │
│  │    client, device, symptoms, funnel_stage,                                │   │
│  │    extracted_data, dialog_history, ...                                    │   │
│  │  }                                                                        │   │
│  └──────────────────────────────────────────────────────────────────────────┘   │
│                                      │                                           │
│                                      ▼                                           │
│  ┌──────────────────────────────────────────────────────────────────────────┐   │
│  │  MODE ROUTER (after context is built)                                     │   │
│  │                                                                           │   │
│  │  mode = operator.settings.ai_mode ?? tenant.settings.ai_mode              │   │
│  └──────────────────────────────────────────────────────────────────────────┘   │
│                                      │                                           │
│              ┌───────────────────────┼───────────────────────┐                  │
│              ▼                       ▼                       ▼                  │
│          [MANUAL]              [SEMI_AUTO]               [AUTO]                 │
│              │                       │                       │                  │
│              │                  ┌────┴────┐             ┌────┴────┐             │
│              │                  │ Generate│             │ Generate│             │
│              │                  │ Response│             │ Response│             │
│              │                  └────┬────┘             └────┬────┘             │
│              │                       │                       │                  │
│              ▼                       ▼                       ▼                  │
│  ┌───────────────────┐   ┌───────────────────┐   ┌───────────────────────┐     │
│  │ NOTIFY OPERATOR   │   │ NOTIFY OPERATOR   │   │   can_auto_reply?     │     │
│  │                   │   │                   │   │      /        \       │     │
│  │ context: YES      │   │ context: YES      │   │    YES         NO     │     │
│  │ draft: NO         │   │ draft: YES        │   │     │      (escalate) │     │
│  │                   │   │                   │   │     ▼          │      │     │
│  │ Operator writes   │   │ Operator approves │   │  AUTO SEND  NOTIFY    │     │
│  │ response manually │   │ or edits draft    │   │             OPERATOR  │     │
│  └───────────────────┘   └───────────────────┘   └───────────────────────┘     │
│                                                                                  │
└─────────────────────────────────────────────────────────────────────────────────┘

NOTIFICATION POINTS (all AFTER Pipeline processing):
────────────────────────────────────────────────────
- MANUAL:     After context built, WITH full context, WITHOUT draft
- SEMI_AUTO:  After response generated, WITH full context + draft
- AUTO:       Only on escalation, WITH full context + attempted response
```

---

## Unified Pipeline (Same for All Modes)

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                         UNIFIED PIPELINE                                         │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                  │
│  ┌─────────────────────────────────────────────────────────────────────────┐    │
│  │  STAGE 1: CONTEXT BUILDING (IDENTICAL FOR ALL MODES)                    │    │
│  │                                                                          │    │
│  │  1. Extract entities (device, symptom, intent)                           │    │
│  │  2. Update funnel stage                                                  │    │
│  │  3. Save to DB (messages, events, extractions, stage_history)            │    │
│  │  4. Notify operator (always, except auto without escalation)             │    │
│  └─────────────────────────────────────────────────────────────────────────┘    │
│                                      │                                           │
│                                      ▼                                           │
│  ┌─────────────────────────────────────────────────────────────────────────┐    │
│  │  STAGE 2: MODE-SPECIFIC RESPONSE HANDLING                               │    │
│  │                                                                          │    │
│  │  ┌─────────────┬──────────────────────┬─────────────────────────────┐   │    │
│  │  │   MANUAL    │     SEMI_AUTO        │          AUTO               │   │    │
│  │  ├─────────────┼──────────────────────┼─────────────────────────────┤   │    │
│  │  │             │                      │                             │   │    │
│  │  │ Generate:   │ Generate: YES        │ Generate: YES               │   │    │
│  │  │ NO          │                      │                             │   │    │
│  │  │             │ Approve: YES         │ Approve: NO                 │   │    │
│  │  │ Approve:    │                      │                             │   │    │
│  │  │ NO          │ Operator approves    │ Send immediately            │   │    │
│  │  │             │ or edits draft       │ (or escalate if needed)     │   │    │
│  │  │ Operator    │                      │                             │   │    │
│  │  │ writes own  │                      │                             │   │    │
│  │  └─────────────┴──────────────────────┴─────────────────────────────┘   │    │
│  └─────────────────────────────────────────────────────────────────────────┘    │
│                                                                                  │
└─────────────────────────────────────────────────────────────────────────────────┘
```

## What Operator Sees in Each Mode

| Mode | Full Context | AI Draft | What Operator Does |
|------|-------------|----------|-------------------|
| **manual** | YES | NO | Writes response from scratch |
| **semi_auto** | YES | YES | Approves, edits, or rewrites draft |
| **auto** | Only on escalation | YES (what AI tried) | Handles escalated case |

---

## Notification Payload Structure

```javascript
// WebSocket + FCM payload
{
  // Identification
  tenant_id: "...",
  operator_id: "...",
  dialog_id: "...",
  message_id: "...",

  // Notification type
  notification_type: "new_message" | "approval_needed" | "escalation",

  // Client context
  client: {
    name: "Ivan",
    phone: "+7...",
    channel: "telegram",
    avatar_url: "..."
  },

  // Message
  message: {
    text: "iPhone 14 won't charge",
    preview: "iPhone 14 won't charge",
    timestamp: "2025-01-03T10:30:00Z",
    has_media: false
  },

  // AI data (only for semi_auto/auto escalation)
  ai_response: {
    draft_text: "Hello! Charging issue...",  // for semi_auto
    confidence: 0.85,
    extracted_data: {
      device: { brand: "Apple", model: "iPhone 14" },
      symptom: "won't charge"
    }
  },

  // For escalation
  escalation: {
    reason: "complex_issue" | "low_confidence" | "explicit_request",
    description: "Client requested operator"
  },

  // Actions
  requires_action: true,
  available_actions: ["approve", "edit", "reject", "reply"]
}
```

---

## Workflow Implementation

### Where notification happens per mode:

| Mode | Pipeline Point | Workflow | What we send |
|------|----------------|----------|--------------|
| **manual** | After ELO_Resolver | ELO_Resolver or ELO_Notify | `{type: "new_message", draft: null}` |
| **semi_auto** | After ELO_Pipeline_Orchestrator | ELO_Pipeline_Orchestrator | `{type: "approval_needed", draft: "..."}` |
| **auto** | After AI decision | ELO_Pipeline_Orchestrator | `{type: "escalation", reason: "..."}` |

### Recommended approach (Variant B):

```
ELO_Resolver:
  ... existing nodes ...
  -> [Get Assigned Operator + Mode]
  -> [Always Execute ELO_Pipeline_Orchestrator with mode param]

ELO_Pipeline_Orchestrator:
  [Trigger] -> [Mode Switch]
       ├─ manual    -> [Notify Operator] -> [Return]
       ├─ semi_auto -> [AI Process] -> [Notify with Draft] -> [Return]
       └─ auto      -> [AI Process] -> [Decision] -> [Auto Send / Escalate]
```

### Workflows to modify:

| Workflow | Changes |
|----------|---------|
| **ELO_Resolver** | + Get Assigned Operator, + Get Mode, + Pass to Pipeline |
| **ELO_Pipeline_Orchestrator** | + Mode Router, + Manual Handler, + Notify nodes, + Trigger fix |
| **NEW: ELO_Operator_Notify** | Separate workflow for notifications (reusable) |

---

## Notification Channels

### WebSocket (Real-time)
- Endpoint: `http://155.212.221.189:8780/api/push/send`
- For active app sessions
- Immediate delivery

### FCM (Push Notifications)
- For background/closed app
- Token stored in `elo_t_operators.fcm_tokens`
- Wakes up the app

### Both are sent together
```javascript
// Send both WebSocket and FCM
await Promise.all([
  sendWebSocket(operator_id, payload),
  sendFCM(operator.fcm_tokens, payload)
]);
```

---

## Escalation Triggers

Auto mode escalates to operator when:

| Trigger | Description | Example |
|---------|-------------|---------|
| `low_confidence` | AI confidence below threshold | confidence < 0.7 |
| `complex_issue` | Multiple symptoms or unclear | "screen broken and not charging" |
| `explicit_request` | Client asked for human | "talk to operator", "real person" |
| `price_negotiation` | Client negotiating price | "can you do cheaper?" |
| `complaint` | Client complaint detected | "terrible service", "refund" |
| `funnel_stuck` | Max retries on stage | 3 failed extractions |

---

## Silent Notifications (Auto Mode)

Even in auto mode, operators receive silent notifications for:
- History tracking
- Dashboard updates
- Statistics

```javascript
{
  notification_type: "auto_handled",
  silent: true,  // no sound/vibration
  dialog_id,
  ai_response_sent: "...",
  extracted_data: {...}
}
```

---

*Generated by Claude Code - 2026-01-03*
