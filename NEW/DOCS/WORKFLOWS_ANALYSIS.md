# Workflows Analysis

**Last sync:** 2026-01-03

---

## Summary

| Category | Active | Inactive | Total |
|----------|--------|----------|-------|
| Channel Contour (In) | 8 | 3 | 11 |
| Channel Contour (Out) | 4 | 3 | 7 |
| API | 11 | 0 | 11 |
| AI Contour | 4 | 11 | 15 |
| Input Contour | 2 | 1 | 3 |
| Resolve Contour | 0 | 6 | 6 |
| Core Contour | 0 | 4 | 4 |
| Operator Contour | 1 | 0 | 1 |
| Graph Contour | 0 | 1 | 1 |
| **Total n8n ELO** | **26** | **31** | **57** |

---

## CURRENT ARCHITECTURE

### Main Data Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           CHANNEL INPUT (ELO_In_*)                          │
├─────────────────────────────────────────────────────────────────────────────┤
│  Telegram  │  WhatsApp  │  Avito  │ Avito_User│  MAX  │  App  │ VK/Form/Ph │
│   [ON]     │    [ON]    │   [ON]  │    [ON]   │  [ON] │ [ON]  │   [off]    │
└──────────────────────────────┬──────────────────────────────────────────────┘
                               │ Push to queue:incoming
                               ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                    INPUT CORE (Batching Layer)                              │
├─────────────────────────────────────────────────────────────────────────────┤
│  ELO_Input_Batcher [ON]         │  ELO_Input_Processor [ON]                 │
│  - Cron: every 3 sec            │  - Cron: every 3 sec                      │
│  - Pop from queue:incoming      │  - Check deadline:* keys                  │
│  - Push to batch:{key}          │  - Merge batch messages                   │
│  - Set deadline:{key}           │  - Call ELO_Resolver                      │
└──────────────────────────────┬──────────────────────────────────────────────┘
                               │ Execute Workflow
                               ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                         ELO_Resolver [off]                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│  Nodes:                                                                     │
│  1. Validate Input                                                          │
│  2. Call Tenant Resolver → ELO_Core_Tenant_Resolver                         │
│  3. Prepare Client → Call Client Resolver → ELO_Client_Resolver             │
│  4. Prepare Dialog → Call Dialog Resolver → ELO_Dialog_Resolver             │
│  5. Save Incoming Message                                                   │
│  6. Build Response                                                          │
│  7. Forward to Core → ELO_Pipeline_Orchestrator                             │
└──────────────────────────────┬──────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                  ELO_Pipeline_Orchestrator [off]                            │
├─────────────────────────────────────────────────────────────────────────────┤
│  Nodes:                                                                     │
│  1. Resolve Client / Resolve Dialog (merge)                                 │
│  2. Save Message Event                                                      │
│  3. Call Task Dispatcher → ELO_Task_Dispatcher [ON]                         │
│  4. Poll Extraction Results → ELO_Results_Aggregator [ON]                   │
│  5. Call Funnel Controller → ELO_Funnel_Controller [ON]                     │
│  6. Needs Response? → Call Response Generator                               │
│  7. Update Dialog Context                                                   │
│  8. Channel Router → Send via ELO_Out_*                                     │
└──────────────────────────────┬──────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                        CHANNEL OUTPUT (ELO_Out_*)                           │
├─────────────────────────────────────────────────────────────────────────────┤
│  Telegram_Bot │  WhatsApp  │    MAX   │   Avito   │    VK    │              │
│    [ON]       │   [ON]     │   [ON]   │   [off]   │   [off]  │              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## REDIS KEYS

| Key Pattern | Purpose | TTL |
|-------------|---------|-----|
| `queue:incoming` | Raw incoming messages | - |
| `batch:{channel}:{chat_id}` | Batched messages per dialog | 120s |
| `deadline:{channel}:{chat_id}` | When to process batch | 120s |
| `first_seen:{channel}:{chat_id}` | Batch start time | 120s |

---

## Channel Contour

### Inbound (ELO_In_*) - 8 active / 3 inactive

| Workflow | ID | Active | Trigger |
|----------|----|----|---------|
| ELO_In_Telegram | 4LGno2k7V9EIZOPz | **ON** | Webhook |
| ELO_In_Telegram_Bot | sGJxAIwGzMrfCjeA | **ON** | Webhook |
| ELO_In_WhatsApp | PrsqCxCgoZuxnKus | **ON** | Webhook |
| ELO_In_Avito | 3dZPOQ1WgYCx76Om | **ON** | Webhook |
| ELO_In_Avito_User | 1Fcexk3PCvHwE2nE | **ON** | Webhook |
| ELO_In_MAX | X3hqn2RJfBPrN9ld | **ON** | Webhook |
| ELO_In_App | ZC8ivCruNEq6Vc7b | **ON** | Webhook |
| ELO_In_VK | ksqmLjxbsBgfkToV | off | Webhook |
| ELO_In_Form | s1GvbRvYjv88hCdW | off | Webhook |
| ELO_In_Phone | 1TA6vErCOXG0eChF | off | Webhook |

### Outbound (ELO_Out_*) - 4 active / 3 inactive

| Workflow | ID | Active | Trigger |
|----------|----|----|---------|
| ELO_Out_Telegram_Bot | GrChisyOV9ajgBDX | **ON** | Execute |
| ELO_Out_WhatsApp | j8SAGf1ZIC8uqDya | **ON** | Execute |
| ELO_Out_MAX | 8SxXTnGJe4qnN4Kx | **ON** | Execute |
| ELO_Out_Router | A924a25CTS3CiJ0N | off | Execute |
| ELO_Out_Avito | X5hxPPtz3OQGB8SS | off | Execute |
| ELO_Out_VK | ZqRsbSo0I0z71Bwb | off | Execute |

---

## API Contour (11 workflows, all ON)

| Workflow | ID | Endpoint |
|----------|----|----|
| ELO_API_Android_Auth | FKTNL7yNqFGfRrv3 | POST /auth |
| ELO_API_Android_Logout | j9VlTpdXIdlfBZhO | POST /logout |
| ELO_API_Android_Dialogs | 2EH6NEVKrvAuGSo6 | GET /dialogs |
| ELO_API_Android_Messages | 2uj0zqoqbSRd0iue | GET /messages |
| ELO_API_Android_Send_Message | 6twQI6tVin73BcN1 | POST /send |
| ELO_API_Android_Normalize | nsMrS7SmpsxutrfN | POST /normalize |
| ELO_API_Android_Register_FCM | yW11VXCx1UCfJqXd | POST /fcm |
| ELO_API_Android_Register_Channel | YZBEIIMfkFN3LBOE | POST /register-channel |
| ELO_API_Channel_Setup | a3I85vep9LrKZa3C | POST /channel-setup |
| ELO_API_Channels_Status | 5X0cptV1tGWIer2y | GET /channels-status |
| ELO_API_Channel_Avito_Auth | wuoTQYQ1vTGB66yn | POST /avito-auth |

---

## AI Contour (15 workflows, 4 active)

| Workflow | ID | Active |
|----------|----|----|
| ELO_Task_Dispatcher | UBcxoMFDKVYlf59R | **ON** |
| ELO_Results_Aggregator | RtElCJItu5B6QZsc | **ON** |
| ELO_Funnel_Controller | GpxaC5zoQMjUjVwe | **ON** |
| ELO_Pipeline_Orchestrator | EOhQJdfA7XAiRlPO | off |
| ELO_Blind_Worker | 2p8xW7tUKME4DdYo | off |
| ELO_AI_Extract | 3ohsxSod82QjGgXB | off |
| ELO_Core_Response_Generator | vwcZwvOGoLnIrS3e | off |
| ELO_Core_Context_Builder | 8oyOJpnE7Cc0D27C | off |
| ELO_Context_Collector | KkCXa38EDLKJwy4i | off |
| ELO_Context_Router | KSe3p3fQJhj6d0Y2 | off |
| ELO_Core_Stage_Manager | IKYT3qf3XbmKmse3 | off |
| ELO_Core_AI_Derive | i9aZ3bd5btVjzZs8 | off |
| ELO_Worker_Executor | YSAGt05jujR6aQ4z | off |
| ELO_Executor | i6LnKuEIUETjrNBl | off |
| ELO_Decision | qaSDHs718S7rl1vX | off |

---

## Resolve Contour (6 workflows, all OFF)

| Workflow | ID | Purpose |
|----------|----|----|
| ELO_Resolver | YAaV3N1PmVgEmbXZ | Main chain: Tenant→Client→Dialog→Pipeline |
| ELO_Core_Tenant_Resolver | fuRDk8dKF5ViY8ad | Resolve tenant by channel |
| ELO_Tenant_Resolver | h97a3boAV7LuSBGZ | Old tenant resolver |
| ELO_Client_Resolver | MOa50VVpseLR5xna | Find/create client |
| ELO_Dialog_Resolver | bPNUrwJNSyj52z8B | Find/create dialog |
| ELO_Queue_Processor | Zn5jhbFY1Fuadj14 | Alternative (not used) |

---

## Input Contour (3 workflows, 2 active)

| Workflow | ID | Active | Purpose |
|----------|----|----|---------|
| ELO_Input_Batcher | 3AfObE32mua4kLmr | **ON** | Batches messages |
| ELO_Input_Processor | afbJwet95gV9Evc5 | **ON** | Processes batches → calls Resolver |
| ELO_Input_Ingest | peUaEixC2W6l75s5 | off | Alternative entry |

---

## Core Contour (4 workflows, all OFF)

| Workflow | ID | Purpose |
|----------|----|----|
| ELO_Core_Dialog_Engine | LcB53NcwVT0K3K6U | Dialog processing |
| ELO_Core_Graph_Writer | 2xOOPR7TeNxw9O2D | Write to Neo4j |
| ELO_Core_Batcher | lmMxcDEJAcverxhC | Alternative batcher |
| ELO_Core_Triggers_Checker | b9tW1Lfnf4kncdbA | Check triggers |

---

## Other

| Workflow | ID | Active | Purpose |
|----------|----|----|---------|
| ELO_Message_Router | EIBalgIkj2XP9iGm | **ON** | Route to operators |
| ELO_Graph_Query | 2ZzFlQ5LOZzJ5bGo | off | Neo4j queries |
| ELO_Avito_Session_Refresh | SUEamG8HK42dcj2U | **ON** | Keepalive |
| ELO_Unifier | K3Sm81ZI3aC12xqN | off | Unify clients |
| ELO_Core_AI_Test_Stub_WS | kZJ6u5VB4alwVjig | off | Testing |

---

## Database (56 tables)

### Transactional (elo_t_*)
- Tenants, Clients, Dialogs, Messages
- Operators, Operator_Devices, Operator_Channels
- Channel_Accounts, Client_Channels
- Events, Tasks, AI_Extractions
- Price_List, Tenant_Domains, Tenant_Verticals
- Funnel overrides, Context overrides

### Reference Tables
- Channels, Verticals, Domains, IP_Nodes
- Symptom/Diagnosis/Repair types and links
- Problem_Categories, Action_Types
- Intent_Types, Context_Types, Trigger_Types
- Funnel_Stages, Stage_Fields, Stage_CTA_Actions
- Worker_Configs, Funnel_Stage_Workers
- Prompts, Meta_Prompts, Cypher_Queries

### Views (elo_v_*)
- v_ai_settings, v_context_types, v_intent_types
- v_funnel_stages, v_prompts, v_triggers
- v_ip_usage, v_symptom_mappings

---

## Key Observations

### Working Chain (Active)
```
ELO_In_* → queue:incoming → ELO_Input_Batcher → batch:*
        → ELO_Input_Processor → ELO_Resolver → ELO_Pipeline_Orchestrator
```

### Active Workers
- ELO_Task_Dispatcher - dispatches extraction tasks
- ELO_Results_Aggregator - collects extraction results
- ELO_Funnel_Controller - manages funnel stages

### Inactive (need to enable for full AI)
- ELO_Resolver + sub-resolvers
- ELO_Pipeline_Orchestrator
- ELO_Core_Response_Generator
- ELO_Blind_Worker

---

*Generated by Claude Code - 2026-01-03*
