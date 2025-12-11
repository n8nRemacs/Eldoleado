# Core_info — Workflow Documentation

> Detailed description of each workflow: inputs, outputs, nodes, SQL, Redis, Neo4j

**[HOW_TO_DOCUMENT.md](HOW_TO_DOCUMENT.md)** — Workflow documentation guide

---

## Structure

```
Core_info/
├── INDEX.md                              ← this file
├── HOW_TO_DOCUMENT.md                    ← guide
├── 01_Channel_Layer/
│   └── workflows_info/
│       ├── ELO_In_Telegram.md            ✅
│       ├── ELO_In_WhatsApp.md            ✅
│       ├── ELO_In_Avito.md               ✅
│       ├── ELO_In_VK.md                  ✅
│       ├── ELO_In_MAX.md                 ✅
│       ├── ELO_In_Form.md                ✅
│       ├── ELO_In_Phone.md               ✅
│       ├── ELO_Out_Telegram.md           ✅
│       ├── ELO_Out_WhatsApp.md           ✅
│       ├── ELO_Out_Avito.md              ✅
│       ├── ELO_Out_VK.md                 ✅
│       └── ELO_Out_MAX.md                ✅
├── 02_Input_Contour/
│   └── workflows_info/
│       ├── INPUT_CONTOUR_OVERVIEW.md        ✅
│       ├── ELO_Core_Tenant_Resolver.md      ✅
│       ├── ELO_Core_Queue_Processor.md      ✅
│       ├── ELO_Core_Batch_Debouncer.md      ✅
│       └── ELO_Core_Client_Resolver.md      ✅
├── 03_Client_Contour/
│   └── workflows_info/
│       └── CLIENT_CONTOUR_OVERVIEW.md   ✅
├── 04_Graph/
│   └── workflows_info/
│       └── GRAPH_OVERVIEW.md            ✅
├── 05_Core_Contour/
│   └── CORE_CONTOUR_OVERVIEW.md         ✅
└── 06_API/
    └── workflows_info/
        ├── API_Android_Auth.md           ✅
        └── API_Android_Appeals_List.md   ✅
```

---

## System Blocks

| # | Block | Description | Status |
|---|------|----------|--------|
| 1 | Channel Layer | ELO_In_*, ELO_Out_*, Response Builder | **13/13** |
| 2 | Input Contour | Tenant Resolver, Queue, Batcher | **5/5** |
| 3 | Client Contour | Client Resolver, Dialog Resolver | **1/1** |
| 4 | Graph | Neo4j operations, Graph Query Tool | **1/1** |
| 5 | Core Contour | Context Builder, Request Builder, Orchestrator, Dialog Engine | **1/1** |
| 6 | API | Android API endpoints | 2/27 |

---

## Documented Workflows

### 01_Channel_Layer (13)

| Document | File | Features |
|----------|------|----------|
| **Overview** | [CHANNEL_CONTOUR_OVERVIEW.md](01_Channel_Layer/CHANNEL_CONTOUR_OVERVIEW.md) | IN + OUT unified, Response Builder, channel limits |

#### ELO_In (7)

| Workflow | File | Features |
|----------|------|-------------|
| ELO_In_Telegram | [ELO_In_Telegram.md](01_Channel_Layer/workflows_info/ELO_In_Telegram.md) | MCP API, bot_token passthrough |
| ELO_In_WhatsApp | [ELO_In_WhatsApp.md](01_Channel_Layer/workflows_info/ELO_In_WhatsApp.md) | Wappi.pro, phone from chatId |
| ELO_In_Avito | [ELO_In_Avito.md](01_Channel_Layer/workflows_info/ELO_In_Avito.md) | Filter system messages, ad_id |
| ELO_In_VK | [ELO_In_VK.md](01_Channel_Layer/workflows_info/ELO_In_VK.md) | Confirmation flow, "ok" response |
| ELO_In_MAX | [ELO_In_MAX.md](01_Channel_Layer/workflows_info/ELO_In_MAX.md) | Phone 8→7, sender extraction |
| ELO_In_Form | [ELO_In_Form.md](01_Channel_Layer/workflows_info/ELO_In_Form.md) | **NO Redis**, prefilled model |
| ELO_In_Phone | [ELO_In_Phone.md](01_Channel_Layer/workflows_info/ELO_In_Phone.md) | **NO Redis**, always voice |

#### ELO_Out (5)

| Workflow | File | Features |
|----------|------|-------------|
| ELO_Out_Telegram | [ELO_Out_Telegram.md](01_Channel_Layer/workflows_info/ELO_Out_Telegram.md) | Bot token from DB, MCP API |
| ELO_Out_WhatsApp | [ELO_Out_WhatsApp.md](01_Channel_Layer/workflows_info/ELO_Out_WhatsApp.md) | Wappi.pro sync API |
| ELO_Out_Avito | [ELO_Out_Avito.md](01_Channel_Layer/workflows_info/ELO_Out_Avito.md) | OAuth token refresh, Redis cache |
| ELO_Out_VK | [ELO_Out_VK.md](01_Channel_Layer/workflows_info/ELO_Out_VK.md) | random_id required |
| ELO_Out_MAX | [ELO_Out_MAX.md](01_Channel_Layer/workflows_info/ELO_Out_MAX.md) | MAX_API_URL env |

### 02_Input_Contour (5)

| Workflow | File | Features |
|----------|------|-------------|
| Overview | [INPUT_CONTOUR_OVERVIEW.md](02_Input_Contour/workflows_info/INPUT_CONTOUR_OVERVIEW.md) | Architecture, Redis keys, debounce logic |
| ELO_Core_Tenant_Resolver | [ELO_Core_Tenant_Resolver.md](02_Input_Contour/workflows_info/ELO_Core_Tenant_Resolver.md) | Determine tenant by credentials |
| ELO_Core_Queue_Processor | [ELO_Core_Queue_Processor.md](02_Input_Contour/workflows_info/ELO_Core_Queue_Processor.md) | POP from queue, group by chat |
| ELO_Core_Batch_Debouncer | [ELO_Core_Batch_Debouncer.md](02_Input_Contour/workflows_info/ELO_Core_Batch_Debouncer.md) | 10s debounce, message concatenation |
| ELO_Core_Client_Resolver | [ELO_Core_Client_Resolver.md](02_Input_Contour/workflows_info/ELO_Core_Client_Resolver.md) | Find/create client |

### 03_Client_Contour (1)

| Document | File | Features |
|----------|------|----------|
| Overview | [CLIENT_CONTOUR_OVERVIEW.md](03_Client_Contour/workflows_info/CLIENT_CONTOUR_OVERVIEW.md) | Client Resolver, Dialog Resolver, future merge logic |

### 04_Graph (1)

| Document | File | Features |
|----------|------|----------|
| Overview | [GRAPH_OVERVIEW.md](04_Graph/workflows_info/GRAPH_OVERVIEW.md) | Neo4j schema, Graph Query Tool, cypher_queries |

### 05_Core_Contour (1)

| Document | File | Features |
|----------|------|----------|
| Overview | [CORE_CONTOUR_OVERVIEW.md](05_Core_Contour/CORE_CONTOUR_OVERVIEW.md) | Context Builder, Request Builder (Stick-Carrot-Stick), Orchestrator, Dialog Engine |

### 06_API (2)

| Workflow | File | Description |
|----------|------|----------|
| API_Android_Auth | [API_Android_Auth.md](06_API/workflows_info/API_Android_Auth.md) | Operator authorization |
| API_Android_Appeals_List | [API_Android_Appeals_List.md](06_API/workflows_info/API_Android_Appeals_List.md) | Appeals list |

---

## ELO_In Patterns

| Type | Workflows | Features |
|-----|-----------|-------------|
| **Standard** | Telegram, WhatsApp, VK, MAX, Avito | Redis queue, Tenant Resolver |
| **Direct** | Form, Phone | NO Redis, Client Resolver directly |

**Standard flow:**
```
Webhook → Extract → Voice? → Normalize → Tenant Resolver → Redis PUSH → Respond
```

**Direct flow:**
```
Webhook → Extract → (Voice?) → Normalize → Tenant Resolver → Client Resolver → Respond
```

---

## ELO_Out Patterns

**Common flow:**
```
Execute Trigger → [Get Credentials?] → Send Message → Process Response → Save History → Register Touchpoint
```

| Channel | Credentials | Features |
|---------|------------|-------------|
| Telegram | PostgreSQL (tenant_configs) | MCP API |
| WhatsApp | - | Wappi.pro direct |
| Avito | Redis cache + OAuth refresh | Escape text |
| VK | - | random_id required |
| MAX | - | MAX_API_URL env |

---

## Input Contour — Redis Flow

```
IN Workflows (fast, ~100ms)
     │
     ▼
┌─────────────────┐
│ Tenant Resolver │  ← determines tenant
└────────┬────────┘
         ▼
   Redis RPUSH
   queue:incoming
         │
═════════│═════════════ boundary IN / Batcher
         ▼
┌─────────────────┐
│ Queue Processor │  ← every 5 sec
│  POP × 10       │
└────────┬────────┘
         ▼
   Group by chat_id
         │
     ┌───┴───┐
     ▼       ▼
  Has Lock?  No Lock
     │         │
  Push to    Create lock +
  queue:batch Trigger Debouncer
         │
         ▼
┌─────────────────┐
│ Batch Debouncer │  ← waits 10s silence
│  × 10 copies    │
└────────┬────────┘
         ▼
   Combine messages
         │
         ▼
┌─────────────────┐
│ Client Resolver │  ← find/create client
└────────┬────────┘
═════════│═════════════ boundary Input Contour / Core
         ▼
    Appeal Manager
```

**Redis keys:**
| Key | TTL | Purpose |
|------|-----|------------|
| `queue:incoming` | — | Incoming queue |
| `queue:processor:lock` | 30s | Processor mutex |
| `lock:batch:{key}` | 300s | Chat processing lock |
| `queue:batch:{key}` | — | Per-chat queue |
| `last_seen:{key}` | — | Last message time |

---

## Documentation Format

Each file contains:

1. **General Information** — trigger, inputs/outputs
2. **Input Data** — JSON format
3. **Output Data** — JSON format
4. **Nodes** — detailed description of each:
   - Node type
   - Purpose
   - **Code** (if Code node)
   - **SQL query** (if Postgres)
   - **Redis operation** (what we store/retrieve)
   - **Neo4j query** (what we search)
5. **Flow Schema** — ASCII diagram
6. **Dependencies** — credentials, external APIs
7. **Features** — channel specifics

---

## Remaining Documentation

### API (from n8n_old)

- [ ] API_Android_Appeal_Detail
- [ ] API_Android_Device_Create
- [ ] API_Android_Device_Update
- [ ] API_Android_Send_Promo
- [ ] ... (and 20+ others)

---

## How to Add New Workflow

1. Read workflow JSON file
2. Create `.md` file in corresponding `workflows_info/` folder
3. Fill using template (see existing files)
4. Update this INDEX.md
