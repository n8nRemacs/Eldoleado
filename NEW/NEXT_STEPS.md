# Next Steps for ELO Design

> **Last updated:** December 10, 2025, 01:45 (UTC+4)
> **Status:** Documenting OLD architecture

---

## Agreed Development Approach

```
1. Work through all blocks (understand what EXISTS in OLD architecture)
   ├── Channel Layer ✅ DONE (12/12)
   ├── Input Contour ✅ DONE (5/5)
   ├── Core 🔄 ANALYZED (not documented)
   ├── Graph 🔄 STARTED (4 open questions)
   └── API ⏳ TODO (25+ endpoints)

2. Create detailed structure for NEW architecture (how it SHOULD be)

3. Design database structure (elo_* tables)

4. Create new workers

5. Debug and testing
```

**Important:** Documenting OLD architecture (BAT_*) is NOT a plan for NEW architecture (ELO_*). This is a phase of understanding existing logic before designing new.

---

## Session 10.12.2025 (night) — Detailed Report

### What Was Done

#### 1. Created Documentation Structure

**Folder:** `NEW/Core_info/`

```
NEW/Core_info/
├── INDEX.md                          # Navigation through all blocks
├── HOW_TO_DOCUMENT.md                # Documentation instructions
├── 01_Channel_Layer/
│   └── workflows_info/
│       ├── ELO_In_Telegram.md        ✅
│       ├── ELO_In_WhatsApp.md        ✅
│       ├── ELO_In_Avito.md           ✅
│       ├── ELO_In_VK.md              ✅
│       ├── ELO_In_MAX.md             ✅
│       ├── ELO_In_Form.md            ✅
│       ├── ELO_In_Phone.md           ✅
│       ├── ELO_Out_Telegram.md       ✅
│       ├── ELO_Out_WhatsApp.md       ✅
│       ├── ELO_Out_Avito.md          ✅
│       ├── ELO_Out_VK.md             ✅
│       └── ELO_Out_MAX.md            ✅
├── 02_Input_Contour/
│   └── workflows_info/
│       ├── INPUT_CONTOUR_OVERVIEW.md ✅
│       ├── ELO_Core_Tenant_Resolver.md   ✅
│       ├── ELO_Core_Queue_Processor.md   ✅
│       ├── ELO_Core_Batch_Debouncer.md   ✅
│       └── ELO_Core_Client_Resolver.md   ✅
├── 03_Core/
│   └── workflows_info/               # TODO
├── 04_Graph/
│   └── workflows_info/
│       └── GRAPH_OVERVIEW.md         ✅
├── 05_Diagnostic_Engine/
│   └── workflows_info/               # No workflows
└── 06_API/
    └── workflows_info/
        ├── API_Android_Auth.md       ✅
        └── API_Android_Appeals_List.md ✅
```

#### 2. Channel Layer — Fully Documented (12/12)

**ELO_In workflows (7 pcs):**

| Workflow | Nodes | Pattern | Features |
|----------|-------|---------|----------|
| ELO_In_Telegram | 12 | Standard | MCP payload, tg_ prefix, Redis queue |
| ELO_In_WhatsApp | 10 | Standard | Wappi.pro, phone from chatId (79991234567@c.us) |
| ELO_In_Avito | 13 | Standard | System filter (author_id===user_id), item_id |
| ELO_In_VK | 15 | Standard | Confirmation flow, response="ok" text |
| ELO_In_MAX | 10 | Standard | Phone normalization (8→7) |
| ELO_In_Form | 5 | **Direct** | NO Redis, prefilled_data.model |
| ELO_In_Phone | 7 | **Direct** | NO Redis, ALWAYS voice |

**ELO_In Patterns:**
- **Standard (5):** Telegram, WhatsApp, VK, MAX, Avito → Redis queue (async)
- **Direct (2):** Form, Phone → NO Redis (rare, synchronous)

**ELO_Out workflows (5 pcs):**

| Workflow | Nodes | Credentials | Features |
|----------|-------|-------------|----------|
| ELO_Out_Telegram | 8 | SQL tenant_configs | MCP API tg.eldoleado.ru |
| ELO_Out_WhatsApp | 5 | Direct | wappi.pro/api/sync |
| ELO_Out_Avito | 11 | Redis cache (TTL 86400) | OAuth refresh, text escape |
| ELO_Out_VK | 5 | N/A | random_id required |
| ELO_Out_MAX | 5 | N/A | MAX_API_URL env |

**Common ELO_Out Pattern:**
```
Execute Trigger → [Get Credentials?] → Send → Process → Save History → Register Touchpoint
```

#### 3. Input Contour — Fully Documented (5/5)

**Flow Architecture:**

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  IN Workflows (fast, ~100ms)                                                │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐               │
│  │Telegram │ │WhatsApp │ │  Avito  │ │   VK    │ │   MAX   │               │
│  └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘               │
│       │           │           │           │           │                    │
│       └───────────┴─────┬─────┴───────────┴───────────┘                    │
│                         ↓                                                   │
│         ┌───────────────────────────────┐                                  │
│         │  1. ELO_Core_Tenant_Resolver  │  ← determines tenant             │
│         └───────────────┬───────────────┘                                  │
│                         ↓                                                   │
│         ┌───────────────────────────────┐                                  │
│         │  Redis RPUSH queue:incoming   │  ← quick and exit                │
│         └───────────────┬───────────────┘                                  │
└─────────────────────────│──────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────────────────────────┐
│  Batcher (slow, 10s debounce)                                               │
│         ┌───────────────────────────────┐                                   │
│         │  2. ELO_Core_Queue_Processor  │  ← every 5 sec                    │
│         │     POP from queue:incoming   │                                   │
│         │     Group by chat_id          │                                   │
│         └───────────────┬───────────────┘                                   │
│                         ↓                                                    │
│         ┌───────────────────────────────┐                                   │
│         │  3. ELO_Core_Batch_Debouncer  │  ← waits 10s silence              │
│         │     ×10 copies (parallel)     │                                   │
│         │     Combines messages         │                                   │
│         └───────────────┬───────────────┘                                   │
│                         ↓                                                    │
│         ┌───────────────────────────────┐                                   │
│         │  4. ELO_Core_Client_Resolver  │  ← finds/creates client           │
│         └───────────────┬───────────────┘                                   │
└─────────────────────────│───────────────────────────────────────────────────┘
                          ↓
                    ┌───────────┐
                    │   CORE    │
                    └───────────┘
```

**Redis Keys Input Contour:**

| Key | Type | TTL | Purpose |
|-----|------|-----|---------|
| `queue:incoming` | List | — | Global incoming queue |
| `queue:processor:lock` | String | short | Mutex for Queue Processor |
| `queue:batch:{channel}:{chat_id}` | List | — | Per-chat message queue |
| `lock:batch:{channel}:{chat_id}` | String | 300s | Per-chat processing lock |
| `last_seen:{channel}:{chat_id}` | String | — | Last message timestamp |

**Debounce Logic:**
- Wait **10 seconds silence** (user confirmed: 20s too long)
- Maximum **300 seconds** waiting (protection from chatty users)
- After debounce — combine all messages into one text
- Voice messages marked `[Voice]: {transcription}`

**Documented Workflows:**

1. **ELO_Core_Tenant_Resolver** (rRO6sxLqiCdgvLZz)
   - 7 nodes
   - Mapping channel → lookup_key (telegram→telegram_bot_token, vk→vk_app_id, etc.)
   - Default tenant UUID: `a0000000-0000-0000-0000-000000000001`

2. **ELO_Core_Queue_Processor** (no ID, Schedule Trigger)
   - Schedule: every 5 seconds
   - 10× parallel POP (workaround for n8n)
   - Grouping by batch_key = `{channel}:{external_chat_id}`
   - Two-level locking (processor + per-chat)

3. **ELO_Core_Batch_Debouncer** (hwYfaLAKCwaWpoQk) ×10 copies
   - Debounce loop: Wait → Check Silence → Ready?
   - Combine Messages: sort by timestamp, join with `\n\n`
   - TODO: per-tenant debounce setting in elo_tenants

4. **ELO_Core_Client_Resolver** (no ID)
   - Find Client SQL with JOIN client_merges
   - Search by phone/telegram_id/vk_id/whatsapp_id/avito_id
   - Client Exists? → Merge / Execute Client Creator
   - → Execute Appeal Manager (boundary with Core)

#### 4. Core — Analyzed, NOT Documented

**Read Workflows:**

| Workflow | ID | Purpose |
|----------|-----|---------|
| BAT_Appeal_Manager | L2pYPcv7r8j5XFU3 | Core entry point |
| BAT_AI_Appeal_Router | Flhmu33l0ZhZhr90 | AI brain, routing |
| BAT_AI_Task_Dispatcher | aEzuOXgpLBTNZ4ie | AI task dispatcher |
| BAT_AI_Universal_Worker | CDHwzDiXqh3t0Iam | AI worker (×7 copies) |
| BAT_Client_Creator | vkQwat1iZhJJj7C9 | Client creation |

**Core Structure (understood, not documented):**

```
Client Resolver
      ↓
┌─────────────────────────────────────────────────────────────────┐
│  ELO_Core_Appeal_Manager (L2pYPcv7r8j5XFU3)                     │
│    • Find Active Appeal (7 days, not finished)                  │
│    • Create New Appeal (if none)                                │
│    • Save Message History                                       │
│    • Register Touchpoint (Neo4j webhook)                        │
│    → Execute AI Router                                          │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│  ELO_Core_AI_Router (Flhmu33l0ZhZhr90)                          │
│    • Load Appeal & Devices (SQL + history + devices)            │
│    • Load Fields Config (context_fields_config)                 │
│    • Prepare Context (focus, disambiguation, completeness %)    │
│    • Needs Extraction? → Task Dispatcher                        │
│    • Call Graph Matcher (Neo4j webhook)                         │
│    • AI Response Generator (OpenAI)                             │
│    • Route by Completeness (full/partial/minimal)               │
│    • Update Appeal Data (SQL)                                   │
│    • Route By Mode:                                             │
│        - auto → Call Client Response Sender                     │
│        - assist → Call Operator Notifier                        │
└─────────────────────────────────────────────────────────────────┘
```

**operation_mode:**
- **auto** — AI responds to client directly
- **assist** — AI prepares response, operator confirms

**Decision:** Core postponed until other blocks are worked through. This is the central block, needs to be adapted to everything else.

#### 5. Graph — Started, Has Open Questions

**Read Workflows (5 pcs):**

| Workflow | ID | Webhook |
|----------|-----|---------|
| BAT_Neo4j_Context_Builder | gF8hYMVuCRqCkw83 | /neo4j/context |
| BAT_Neo4j_CRUD | gtm1CfLF557Ta40P | /neo4j/crud |
| BAT_Neo4j_Sync | Jqu7d7yWOjyxm80x | /neo4j/sync |
| BAT_Neo4j_Touchpoint_Register | TrCjdgREvPAB2yyL | /neo4j/touchpoint/register |
| BAT_Neo4j_Touchpoint_Tracker | tKHYEwn1AR18UrDS | /neo4j/touchpoint |

**Neo4j Schema (from analysis):**

```
Node Types:
- Client (id, phone, name)
- Device (id, brand, model, owner_label)
- Problem (id, type, status)
- ProblemType (code)
- Channel (type, identifier, verified)
- Vertical (type)
- Touchpoint (id, timestamp, type, channel, direction)

Edge Types:
- Client -[:OWNS]-> Device
- Device -[:HAS_PROBLEM]-> Problem
- Problem -[:OF_TYPE]-> ProblemType
- Client -[:HAS_CHANNEL]-> Channel
- Client -[:CUSTOMER_OF]-> Vertical
- Touchpoint -[:FROM]-> Client (inbound)
- Touchpoint -[:TO]-> Client (outbound)
- Touchpoint -[:ABOUT_DEVICE]-> Device
- Touchpoint -[:ABOUT_PROBLEM]-> Problem
- Touchpoint -[:IN_VERTICAL]-> Vertical
```

**Created:** `04_Graph/workflows_info/GRAPH_OVERVIEW.md`

---

## OPEN GRAPH QUESTIONS (for morning!)

### 1. Register vs Tracker — Duplication or Different Scenarios?

**Touchpoint Register** (`/webhook/neo4j/touchpoint/register`):
```
Input data:
- client_id (required)
- appeal_id
- channel
- direction: inbound | outbound | mutual
- type: message | call | visit | promo | form
- is_new_client
- vertical_id
- tenant_id

What it does:
1. Creates Touchpoint node in Neo4j
2. Link with Client: FROM (inbound), TO (outbound), or BOTH (mutual)
3. Link with Vertical if specified
4. Saves to PostgreSQL touchpoints table
```

**Touchpoint Tracker** (`/webhook/neo4j/touchpoint`):
```
Input data:
- client_id (required)
- message_id
- channel
- direction: inbound | outbound (NO mutual!)
- type
- mentioned_device_id    ← additional
- mentioned_problem_id   ← additional
- confidence (0-1)       ← additional
- explicit (bool)        ← additional

What it does:
1. Creates Touchpoint node in Neo4j
2. Link with Client: FROM or TO
3. Link ABOUT_DEVICE (if device mentioned)
4. Link ABOUT_PROBLEM (if problem mentioned)
5. Does NOT save to PostgreSQL!
```

**Comparison:**

| Aspect | Register | Tracker |
|--------|----------|---------|
| PostgreSQL | ✅ Yes | ❌ No |
| mutual direction | ✅ Yes | ❌ No |
| ABOUT_DEVICE | ❌ No | ✅ Yes |
| ABOUT_PROBLEM | ❌ No | ✅ Yes |
| confidence | ❌ No | ✅ Yes |

**Hypothesis:**
- Register = registering contact fact (for funnel, analytics)
- Tracker = tracking what was discussed (for AI context)

**Question:** Is this correct? Or should they work together? Or is this duplication that needs to be merged?

### 2. Direction — Who Determines inbound/outbound/mutual?

In Touchpoint Register code there's a comment:
```javascript
// Direction determination logic:
// - is_new_client && no phone in DB → inbound
// - is_new_client && phone in DB → mutual
// - promo/newsletter → outbound
// - dialog (has inbound + was reply + client wrote) → mutual
```

But this is just a comment, not code!

**Question:** Who actually determines direction?
- Does calling workflow pass ready value?
- Or should Graph determine it by logic?

### 3. enrichment_paths — What Is This Table?

In Context Builder there's action `enrichment_suggestion`:
```javascript
// PostgreSQL query
SELECT * FROM enrichment_paths WHERE enabled = true ORDER BY priority DESC, conversion_rate DESC

// Logic
const suggestions = enrichmentPaths
  .filter(path => {
    const hasFrom = existingTypes.has(path.from_channel_type);
    const needsTo = !existingTypes.has(path.to_channel_type);
    return hasFrom && needsTo;
  })
  .slice(0, 3);
```

**Question:** What is this table? Structure? Conversion paths like "telegram → collect phone"?

### 4. When to Call Which Touchpoint?

**Question:**
- Register → for all incoming/outgoing messages?
- Tracker → only when AI identified device mention in text?

---

## Previous Context (from past sessions)

### Global Schema (`GLOBAL_SCHEMA.md`)

- **Principles:**
  - All tables relational (no hardcode, only FK)
  - Hybrid IDs: INT for directories, UUID for entities
  - Minimal packet between blocks: `{tenant_id, dialog_id}`

- **Hierarchy:** Domain → Vertical (one domain per tenant for MVP)

- **Directories (7):**
  - elo_domains, elo_verticals, elo_channels
  - elo_dialog_statuses, elo_message_types, elo_directions
  - elo_operator_types

- **Main Entities (5):**
  - elo_tenants, elo_operators, elo_clients
  - elo_dialogs, elo_messages

- **Linking:**
  - elo_tenant_verticals, elo_dialog_verticals
  - elo_channel_accounts, elo_client_channels

- **Data Contracts:**
  - Internal: `{tenant_id, dialog_id}`
  - External (API → App): expanded object

### 6 System Blocks

| # | Block | Status | Documents |
|---|-------|--------|-----------|
| 1 | Channel Layer (IN/OUT) | ✅ DONE | 12/12 |
| 2 | Billing | ⏳ TODO | — |
| 3 | Input Contour | ✅ DONE | 5/5 |
| 4 | Core | 🔄 Analyzed | 0 |
| 5 | Graph (Neo4j) | 🔄 Started | 1 + questions |
| 6 | Diagnostic Engine | ❓ No workflows | 0 |
| — | API | 🔄 Started | 2/27 |

---

## Naming Convention

- **BAT** prefix = BattCRM (old project name)
- **ELO** prefix = Eldoleado (new name)
- Channel Layer (ELO_In_*, ELO_Out_*) already renamed
- Input Contour documentation uses ELO_Core_* (though JSON still BAT_*)
- Core workflows (BAT_Appeal_Manager, etc.) not renamed yet
- `n8n_old/` — folder with all OLD BAT_* workflows
- `ELO_Core/` — folder for NEW ELO_Core_* workflows (empty for now)

---

## Folder Structure (current)

```
NEW/
├── GLOBAL_SCHEMA.md              # General schema (tables, contracts)
├── NEXT_STEPS.md                 # This file
├── Core_info/                    # Block documentation
│   ├── INDEX.md                  # Navigation
│   ├── HOW_TO_DOCUMENT.md        # Instructions
│   ├── 01_Channel_Layer/         # ✅ 12/12
│   ├── 02_Input_Contour/         # ✅ 5/5
│   ├── 03_Core/                  # TODO
│   ├── 04_Graph/                 # 🔄 1 + questions
│   ├── 05_Diagnostic_Engine/     # No workflows
│   └── 06_API/                   # 🔄 2/27
└── workflows/
    ├── ELO_InOut/                # New ELO_In/Out
    │   ├── ELO_In/               # 7 workflows
    │   └── ELO_Out/              # 5 workflows
    └── n8n_old/                  # Old BAT_* workflows
        ├── API/                  # 27 workflows
        ├── Core/                 # ~20 workflows
        ├── In/                   # 7 workflows
        ├── Out/                  # 5 workflows
        ├── TaskWork/             # Debouncer×10, Worker×7, OutProcessor×6
        └── Tool/                 # AI tools
```

---

## Next Session Plan (morning 10.12.2025)

### 1. Resolve Graph Questions

In order:
1. Register vs Tracker
2. Direction logic
3. enrichment_paths table
4. When which touchpoint

### 2. Document Graph (5 workflows)

After answering questions:
- ELO_Graph_Context_Builder.md
- ELO_Graph_CRUD.md
- ELO_Graph_Sync.md
- ELO_Graph_Touchpoint_Register.md
- ELO_Graph_Touchpoint_Tracker.md

### 3. Document API (25+ workflows)

After Graph — document Android API and Operator API.

### 4. Return to Core

After understanding all blocks — document Core as central element.

---

## Quick Reference

### Redis Keys (all blocks)

**Input Contour:**
- `queue:incoming` — global incoming queue
- `queue:processor:lock` — mutex for Queue Processor
- `queue:batch:{key}` — per-chat queue
- `lock:batch:{key}` — per-chat lock (TTL 300s)
- `last_seen:{key}` — timestamp

**Core:**
- `ai_extraction_queue` — AI Worker task queue
- `batch:{id}:status` — extraction batch status (TTL 300s)

**Channel Layer (Avito):**
- `avito_access_token` — OAuth token (TTL 86400s)

### Webhooks (Neo4j)

| Webhook | Purpose |
|---------|---------|
| POST /webhook/neo4j/context | AI context (get_context, disambiguation, match_entities, enrichment) |
| POST /webhook/neo4j/crud | CRUD operations |
| POST /webhook/neo4j/sync | PostgreSQL → Neo4j synchronization |
| POST /webhook/neo4j/touchpoint/register | Touch registration |
| POST /webhook/neo4j/touchpoint | Mention tracking |

### Key Workflow IDs

| Workflow | ID |
|----------|-----|
| Tenant Resolver | rRO6sxLqiCdgvLZz |
| Batch Debouncer | hwYfaLAKCwaWpoQk |
| Client Creator | vkQwat1iZhJJj7C9 |
| Appeal Manager | L2pYPcv7r8j5XFU3 |
| AI Router | Flhmu33l0ZhZhr90 |
| Task Dispatcher | aEzuOXgpLBTNZ4ie |
| AI Worker | CDHwzDiXqh3t0Iam |
| Client Response Sender | Gxd1gIKgk8HxuOya |
| Operator Notifier | GUeLgLcNnawYfpf9 |
| Context Builder | gF8hYMVuCRqCkw83 |
| Neo4j CRUD | gtm1CfLF557Ta40P |
| Neo4j Sync | Jqu7d7yWOjyxm80x |
| Touchpoint Register | TrCjdgREvPAB2yyL |
| Touchpoint Tracker | tKHYEwn1AR18UrDS |
