# Workflows Analysis

**Last sync:** 2025-12-27

---

## Summary

| Category | Active | Inactive | Total |
|----------|--------|----------|-------|
| Channel Contour (In) | 4 | 2 | 6 |
| Channel Contour (Out) | 1 | 3 | 4 |
| API | 4 | 0 | 4 |
| AI Contour | 1 | 5 | 6 |
| Input Contour | 0 | 1 | 1 |
| Core | 1 | 2 | 3 |
| Graph Contour | 0 | 1 | 1 |
| Other | 2 | 1 | 3 |
| **Total n8n ELO** | **8** | **16** | **24** |
| **Local JSON files** | - | - | **71** |

---

## Data Flow Diagram (Actual)

```
                         MCP Channels (Webhooks)
                                 |
    +----------+----------+------+------+----------+
    |          |          |             |          |
    v          v          v             v          v
ELO_In_   ELO_In_    ELO_In_       ELO_In_    ELO_In_
WhatsApp  Telegram   Avito         VK         Phone
  [ON]      [off]    [ON]         [off]      [off]
    |                    |
    |                    +---> ELO_Core_Tenant_Resolver [NOT IN N8N!]
    |                                   |
    +-----------------------------------+
                    |
                    v
              Redis Queue (push)
              "queue:incoming:*"
                    |
                    v
         +-------------------+
         |   REDIS QUEUE     |
         +-------------------+
                    |
                    v (pop by schedule)
    +---------------+---------------+
    |               |               |
    v               v               v
ELO_Out_       ELO_Out_        ELO_Out_
Telegram_Bot   WhatsApp         MAX
   [ON]          [ON]          [off]
    |               |
    v               v
Telegram API   WhatsApp MCP
```

---

## Workflow Execute Calls

```
ELO_In_Avito ---------> ELO_Core_Tenant_Resolver
ELO_In_Telegram ------> ELO_Core_Tenant_Resolver
ELO_In_VK ------------> ELO_Core_Tenant_Resolver
ELO_In_MAX -----------> ELO_Core_Tenant_Resolver
ELO_In_Phone ---------> ELO_Core_Tenant_Resolver
ELO_In_Form ----------> ELO_Core_Tenant_Resolver

ELO_Out_Router -------> ELO_Out_Telegram
                    --> ELO_Out_WhatsApp
                    --> ELO_Out_Avito
                    --> ELO_Out_VK
                    --> ELO_Out_MAX

ELO_Input_Processor --> ELO_Client_Resolve
ELO_Resolver ---------> ELO_Unifier
```

**NOTE:** ELO_In_WhatsApp does NOT call Tenant Resolver!

---

## Redis Queue Flow

### Producers (push)
| Workflow | Queue |
|----------|-------|
| ELO_In_WhatsApp | queue:incoming:* |
| ELO_In_Avito | queue:incoming:* |
| ELO_In_Avito_User | queue:incoming:* |
| ELO_API_Android_Send_Message | queue:outgoing:* |

### Consumers (pop)
| Workflow | Queue |
|----------|-------|
| ELO_Out_Telegram_Bot | queue:outgoing:telegram |
| ELO_Out_WhatsApp | queue:outgoing:whatsapp |
| ELO_Out_Avito | queue:outgoing:avito |

### Cache Keys
| Workflow | Keys |
|----------|------|
| ELO_Client_Resolve | cache:tenant:*, cache:client:*, cache:dialog:* |
| ELO_Resolver | cache:tenant:*, cache:client:*, cache:dialog:* |
| ELO_Unifier | cache:client:phone:* |

---

## Channel Contour

### Inbound (ELO_In_*)

| n8n ID | Workflow | Active | Trigger | Description |
|--------|----------|--------|---------|-------------|
| PrsqCxCgoZuxnKus | ELO_In_WhatsApp | **ON** | Webhook | WhatsApp messages via MCP |
| - | ELO_In_Telegram | off | Webhook | Telegram messages via MCP |
| 3dZPOQ1WgYCx76Om | ELO_In_Avito | **ON** | Webhook | Avito messages via MCP |
| 1Fcexk3PCvHwE2nE | ELO_In_Avito_User | **ON** | Webhook | Avito user account |
| - | ELO_In_VK | off | Webhook | VK messages via MCP |
| 1TA6vErCOXG0eChF | ELO_In_Phone | off | Webhook | Phone call transcriptions |

**Flow:** Webhook -> Normalize -> Tenant Resolver -> Client Resolve -> Input Ingest

### Outbound (ELO_Out_*)

| n8n ID | Workflow | Active | Trigger | Description |
|--------|----------|--------|---------|-------------|
| A924a25CTS3CiJ0N | ELO_Out_Router | off | Execute | Routes to channel-specific Out |
| - | ELO_Out_WhatsApp | off | Execute | Send via WhatsApp MCP |
| GrChisyOV9ajgBDX | ELO_Out_Telegram_Bot | **ON** | Execute | Send via Telegram Bot API |
| - | ELO_Out_Avito | off | Execute | Send via Avito MCP |
| 8SxXTnGJe4qnN4Kx | ELO_Out_MAX | off | Execute | Send via MAX MCP |

**Flow:** Out Router -> Channel Out -> MCP -> Messenger

### Other Channel

| n8n ID | Workflow | Active | Description |
|--------|----------|--------|-------------|
| SUEamG8HK42dcj2U | ELO_Avito_Session_Refresh | **ON** | Refresh Avito sessions |

---

## API Contour

| n8n ID | Workflow | Active | Endpoint | Description |
|--------|----------|--------|----------|-------------|
| FKTNL7yNqFGfRrv3 | ELO_API_Android_Auth | **ON** | POST /android/auth | Login with PIN |
| 2EH6NEVKrvAuGSo6 | ELO_API_Android_Dialogs | **ON** | GET /android/dialogs | Get operator dialogs |
| 2uj0zqoqbSRd0iue | ELO_API_Android_Messages | **ON** | GET /android/messages | Get dialog messages |
| 6twQI6tVin73BcN1 | ELO_API_Android_Send_Message | **ON** | POST /android/send | Send message |

**Note:** All 4 API workflows are active and handle Android app communication.

---

## AI Contour

| n8n ID | Workflow | Active | Trigger | Description |
|--------|----------|--------|---------|-------------|
| DgnJMtf2Qu6LCha5 | ELO_Core_AI_Test_Stub | **ON** | Execute | Test stub (returns mock) |
| 3ohsxSod82QjGgXB | ELO_AI_Extract | off | Execute | Extract entities from message |
| KkCXa38EDLKJwy4i | ELO_Context_Collector | off | Execute | Collect context for AI |
| 8oyOJpnE7Cc0D27C | ELO_Core_Context_Builder | off | Execute | Build full context |
| IKYT3qf3XbmKmse3 | ELO_Core_Stage_Manager | off | Execute | Manage funnel stages |
| 2xOOPR7TeNxw9O2D | ELO_Core_Graph_Writer | off | Execute | Write to Neo4j |

---

## Input Contour

| n8n ID | Workflow | Active | Trigger | Description |
|--------|----------|--------|---------|-------------|
| 3AfObE32mua4kLmr | ELO_Input_Batcher | off | Schedule | Batch messages |

---

## Core

| n8n ID | Workflow | Active | Trigger | Description |
|--------|----------|--------|---------|-------------|
| LcB53NcwVT0K3K6U | ELO_Core_Dialog_Engine | off | Execute | Main dialog processing |
| EIBalgIkj2XP9iGm | ELO_Message_Router | **ON** | Execute | Route to operator/channel |

---

## Graph Contour

| n8n ID | Workflow | Active | Trigger | Description |
|--------|----------|--------|---------|-------------|
| 2ZzFlQ5LOZzJ5bGo | ELO_Graph_Query | off | Webhook | Execute Cypher queries |

---

## Untagged ELO Workflows

| n8n ID | Workflow | Active | Description |
|--------|----------|--------|-------------|
| K3Sm81ZI3aC12xqN | ELO_Unifier | off | Client unification by phone |
| OHjjTQDguN2G6xin | ELO_Client_Resolve | off | Client resolution |

---

## Critical Gaps Found

### 1. ELO_Core_Tenant_Resolver NOT IMPORTED to n8n!
All ELO_In_* workflows (except WhatsApp) call `ELO_Core_Tenant_Resolver`, but it's NOT in n8n!

**Files exist locally:**
- `Core/ELO_Core_Tenant_Resolver.json`
- `Core/ELO_Core_Tenant_Resolver_v2.json`
- `Core Contour/ELO_Core_Tenant_Resolver.json`

**Impact:** Incoming messages cannot resolve tenant → flow breaks immediately.

**Fix:** Import `ELO_Core_Tenant_Resolver.json` to n8n and activate it.

### 2. ELO_In_WhatsApp doesn't call Tenant Resolver
Unlike other In workflows, ELO_In_WhatsApp goes directly to Redis queue without calling ELO_Core_Tenant_Resolver.

**Impact:** Inconsistent flow between channels.

### 3. ELO_Out_Router is OFF but needed
All ELO_Out_* workflows expect to be called from ELO_Out_Router, but it's inactive.

**Impact:** Outgoing messages rely only on direct schedule polling from Redis.

### 4. No Consumer for incoming queue
Messages pushed to `queue:incoming:*` have no active consumer workflow.

**Impact:** Incoming messages are queued but never processed.

---

## Current Issues

### 1. IF nodes bug in n8n
n8n IF node v2 incorrectly handles `undefined` - data without `tenant_id` goes to TRUE branch instead of FALSE. See `123.md` for details.

**Solution:** Use `={{ !!$json.tenant_id }}` with boolean equals true.

### 2. Many workflows are inactive
Only 8 of 24 ELO workflows are active. Critical path is broken.

### 3. ELO_Client_Resolve has 38 nodes
This is a complex workflow handling tenant, client, and dialog resolution with Redis caching. It should be split or simplified.

### 4. Local vs n8n mismatch
71 local JSON files vs 24 n8n ELO workflows - many local files are drafts or v2 versions not deployed.

---

## Active Workflows Summary

| Workflow | Purpose |
|----------|---------|
| ELO_In_WhatsApp | Receive WhatsApp messages |
| ELO_In_Avito | Receive Avito messages |
| ELO_In_Avito_User | Avito user account |
| ELO_Message_Router | Route to operators |
| ELO_Out_Telegram_Bot | Send via Telegram Bot |
| ELO_Avito_Session_Refresh | Keep Avito sessions alive |
| ELO_API_Android_* (4) | Android app API |
| ELO_Core_AI_Test_Stub | AI test stub |

**Total Active:** 8 workflows

---

## Recommended Next Steps

### Priority 1: Fix Critical Path
1. **Find/Create ELO_Core_Tenant_Resolver** - it's called by all ELO_In_* but not in n8n
2. **Activate ELO_Client_Resolve** - needed to resolve clients
3. **Fix IF nodes** in ELO_Resolver workflows (see 123.md)

### Priority 2: Complete Flow
4. **Add consumer for incoming queue** - something needs to process `queue:incoming:*`
5. **Activate ELO_Out_Router** or ensure direct Redis polling works
6. **Standardize ELO_In_WhatsApp** to match other In workflows

### Priority 3: Cleanup
7. **Add ELO tag** to ELO_Client_Resolve and ELO_Unifier
8. **Remove duplicate local files** - 71 local vs 24 in n8n
9. **Document Redis queue names** - currently inconsistent

---

## External Dependencies

| Service | URL | Used By |
|---------|-----|---------|
| OpenRouter AI | openrouter.ai/api/v1/chat | ELO_AI_Extract, ELO_Message_Router |
| Neo4j | 45.144.177.128:7474 | ELO_Core_Graph_Writer, ELO_Core_Context_Builder |
| WhatsApp MCP | 155.212.221.189:8769 | ELO_Out_WhatsApp |
| Telegram API | api.telegram.org | ELO_Out_Telegram_Bot |
| Avito API | m.avito.ru | ELO_Avito_Session_Refresh |

---

*Generated by Claude Code - 2025-12-27*
