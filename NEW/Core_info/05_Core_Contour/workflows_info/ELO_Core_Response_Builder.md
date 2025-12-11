# ELO_Core_Response_Builder

> Builds and sends response to client via Channel OUT

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `NEW` (to be created) |
| **File** | `NEW/workflows/ELO_Core/ELO_Core_Response_Builder.json` |
| **Trigger** | Execute Workflow Trigger |
| **Called from** | ELO_Core_Ingest |
| **Calls** | Channel OUT (ELO_Out_*) |

---

## Purpose

Build and send response to client:
1. Format response message
2. Add buttons (if needed)
3. Call appropriate Channel OUT workflow

**MVP v0:** Stub response only. Full AI response in next version.

---

## Input Data

```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "client_id": "uuid",
  "channel_id": 1,
  "external_chat_id": "123456789",
  "trace_id": "trace_xyz789",
  "extractions": {
    "device": {
      "brand": "Apple",
      "model": "iPhone 14 Pro"
    },
    "symptoms": [
      {
        "code": "screen_replacement",
        "text": "замена дисплея"
      }
    ],
    "intent": {
      "type": "price_inquiry"
    }
  },
  "graph_operations": [
    {"operation": "create_device", "success": true}
  ]
}
```

---

## Output Data

**To Channel OUT:**
```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "external_chat_id": "123456789",
  "message": {
    "text": "Спасибо за обращение! Я записал информацию о вашем устройстве Apple iPhone 14 Pro. Мы свяжемся с вами в ближайшее время.",
    "buttons": [],
    "attachments": []
  },
  "trace_id": "trace_xyz789"
}
```

---

## Nodes

### 1. Execute Workflow Trigger

Entry point — called from ELO_Core_Ingest.

---

### 2. Build Response (MVP v0 — Stub)

```javascript
const input = $input.first().json;
const extractions = input.extractions || {};
const device = extractions.device;

// MVP v0: Simple stub response
let responseText = 'Спасибо за обращение!';

if (device?.brand && device?.model) {
  responseText = `Спасибо за обращение! Я записал информацию о вашем устройстве ${device.brand} ${device.model}.`;
}

if (extractions.symptoms?.length > 0) {
  const symptomTexts = extractions.symptoms.map(s => s.text).join(', ');
  responseText += ` Проблема: ${symptomTexts}.`;
}

responseText += ' Мы свяжемся с вами в ближайшее время.';

// TODO: Full AI response generation
// - Load response prompt from prompts table
// - Call AI for response
// - Apply post_rules validation

return {
  tenant_id: input.tenant_id,
  dialog_id: input.dialog_id,
  channel_id: input.channel_id,
  external_chat_id: input.external_chat_id,
  message: {
    text: responseText,
    buttons: [],
    attachments: []
  },
  trace_id: input.trace_id
};
```

---

### 3. Get Channel Code

```javascript
const channelId = $json.channel_id;

// Channel ID to code mapping
const channels = {
  1: 'telegram',
  2: 'whatsapp',
  3: 'avito',
  4: 'vk',
  5: 'max',
  6: 'form',
  7: 'phone'
};

return {
  ...$json,
  channel_code: channels[channelId] || 'telegram'
};
```

---

### 4. Route to Channel OUT

| Condition | Workflow |
|-----------|----------|
| `channel_code === 'telegram'` | ELO_Out_Telegram |
| `channel_code === 'whatsapp'` | ELO_Out_WhatsApp |
| `channel_code === 'avito'` | ELO_Out_Avito |
| `channel_code === 'vk'` | ELO_Out_VK |
| `channel_code === 'max'` | ELO_Out_MAX |
| default | Log error, skip |

**Type:** Switch node + Execute Workflow

---

### 5. → Channel OUT (Execute Workflow)

| Parameter | Value |
|----------|----------|
| **Type** | Execute Workflow |
| **Workflow** | ELO_Out_{channel} |

**Passed data:**
```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "external_chat_id": "123456789",
  "message": {
    "text": "...",
    "buttons": [],
    "attachments": []
  },
  "trace_id": "trace_xyz789"
}
```

---

## Flow Diagram

```
Execute Trigger
       │
       ▼
┌──────────────────┐
│  Build Response  │  ← MVP: Stub response
│    (MVP v0)      │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│Get Channel Code  │  ← channel_id → channel_code
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│Route to Channel  │  ← Switch by channel_code
│      OUT         │
└────────┬─────────┘
         │
    ┌────┴────┬────────┬────────┬────────┐
    ▼         ▼        ▼        ▼        ▼
┌────────┐ ┌────────┐ ┌─────┐ ┌────┐ ┌─────┐
│Telegram│ │WhatsApp│ │Avito│ │ VK │ │ MAX │
└────────┘ └────────┘ └─────┘ └────┘ └─────┘
```

---

## Response Templates (Future)

**MVP v0:** Hardcoded stub response.

**Full version:** Load from `prompts` table:

```sql
SELECT prompt_text, response_template
FROM prompts
WHERE vertical_id = $vertical_id
  AND funnel_stage_id = $stage_id
  AND prompt_type = 'response'
  AND is_active = true;
```

---

## Button Types (Future)

| Type | Example |
|------|---------|
| Quick reply | `{"text": "Да", "callback": "confirm"}` |
| URL | `{"text": "Наш сайт", "url": "https://..."}` |
| Phone | `{"text": "Позвонить", "phone": "+7..."}` |

**MVP v0:** No buttons.

---

## Channel OUT Contract

Each Channel OUT workflow expects:

```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "external_chat_id": "string",
  "message": {
    "text": "string",
    "buttons": [],
    "attachments": []
  },
  "trace_id": "string"
}
```

---

## Error Handling

| Error | Action |
|-------|--------|
| Unknown channel_id | Log error, skip send |
| Channel OUT fails | Log error, don't retry |
| Empty message | Skip send |

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Workflow | ELO_Out_Telegram | Telegram sending |
| Workflow | ELO_Out_WhatsApp | WhatsApp sending |
| Workflow | ELO_Out_Avito | Avito sending |
| Workflow | ELO_Out_VK | VK sending |
| Workflow | ELO_Out_MAX | MAX sending |
| Table | `prompts` | Response templates (future) |
