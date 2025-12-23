# ELO Workflows Analysis

> Last sync: 2025-12-23 12:10

---

## Summary

| Metric | Value |
|--------|-------|
| Total workflows (n8n) | 40 |
| Active | 12 |
| Inactive | 28 |

---

## Data Flow Diagram

```
                              INCOMING MESSAGES
                                     |
         +---------------+-----------+-----------+---------------+
         |               |           |           |               |
         v               v           v           v               v
    [WhatsApp]      [Telegram]   [Avito]     [VK/MAX]        [App]
  ELO_In_WhatsApp  ELO_In_Tg   ELO_In_*    ELO_In_*      ELO_In_App
     [ACTIVE]      [inactive]  [inactive]  [inactive]     [ACTIVE]
         |               |           |           |               |
         +---------------+-----------+-----------+---------------+
                                     |
                                     v
                        +------------------------+
                        |   ELO_Message_Router   |
                        |        [ACTIVE]        |
                        +------------------------+
                                     |
                    +----------------+----------------+
                    |                                 |
                    v                                 v
         +--------------------+            +--------------------+
         | ELO_Client_Resolve |            |  ELO_Core_AI_*     |
         |      [ACTIVE]      |            |    [inactive]      |
         +--------------------+            +--------------------+
                    |
                    v
              [PostgreSQL]
            elo_t_messages
            elo_t_dialogs
            elo_t_clients



                              OUTGOING MESSAGES
                                     |
                                     v
                   +--------------------------------+
                   | ELO_API_Android_Send_Message   |
                   |           [ACTIVE]             |
                   +--------------------------------+
                                     |
                                     v
                              [Redis Queue]
                        queue:outgoing:{channel}
                                     |
         +---------------+-----------+-----------+---------------+
         |               |           |           |               |
         v               v           v           v               v
   ELO_Out_WA     ELO_Out_Tg    ELO_Out_VK  ELO_Out_MAX  ELO_Out_Avito
   [inactive]     [inactive]   [inactive]  [inactive]    [inactive]
         |               |           |           |               |
         v               v           v           v               v
    [Baileys]      [Telegram]    [VK API]   [MAX API]    [Avito API]
```

---

## Active Workflows (12)

| Workflow | Folder | Webhook Path |
|----------|--------|--------------|
| ELO_API_Android_Auth | API | /android/auth/login |
| ELO_API_Android_Dialogs | API | /android/dialogs |
| ELO_API_Android_Logout | API | /android/logout |
| ELO_API_Android_Messages | API | /android/messages |
| ELO_API_Android_Normalize | API | /android/normalize |
| ELO_API_Android_Register_FCM | API | /android-register-fcm |
| ELO_API_Android_Send_Message | API | /android/messages/send |
| ELO_Client_Resolve | Client Contour | /elo-client-resolve |
| ELO_Core_AI_Test_Stub | AI Contour | /elo-core-ingest |
| ELO_In_App | Channel Contour | /in-app |
| ELO_In_WhatsApp | Channel Contour | /whatsapp-incoming |
| ELO_Message_Router | Core Contour | /router |

---

## Android API Endpoints

| Endpoint | Workflow | Method | Status |
|----------|----------|--------|--------|
| /android/auth/login | ELO_API_Android_Auth | POST | ACTIVE |
| /android/dialogs | ELO_API_Android_Dialogs | GET | ACTIVE |
| /android/messages | ELO_API_Android_Messages | GET | ACTIVE |
| /android/messages/send | ELO_API_Android_Send_Message | POST | ACTIVE |
| /android/normalize | ELO_API_Android_Normalize | POST | ACTIVE |
| /android/logout | ELO_API_Android_Logout | POST | ACTIVE |
| /android-register-fcm | ELO_API_Android_Register_FCM | POST | ACTIVE |

---

## Workflows by Contour

### Channel Contour - ELO_In (8 workflows)

| Workflow | Status | Trigger |
|----------|--------|---------|
| ELO_In_WhatsApp | ACTIVE | webhook: /whatsapp-incoming |
| ELO_In_App | ACTIVE | webhook: /in-app |
| ELO_In_Telegram | inactive | webhook: /telegram-in |
| ELO_In_Avito | inactive | webhook: /avito |
| ELO_In_VK | inactive | webhook: /vk |
| ELO_In_MAX | inactive | webhook: /max |
| ELO_In_Phone | inactive | webhook: /phone |
| ELO_In_Form | inactive | webhook: /form |

### Channel Contour - ELO_Out (6 workflows)

| Workflow | Status | Trigger |
|----------|--------|---------|
| ELO_Out_WhatsApp | inactive | schedule (3 sec) |
| ELO_Out_Telegram | inactive | schedule (3 sec) |
| ELO_Out_VK | inactive | schedule (3 sec) |
| ELO_Out_MAX | inactive | schedule (3 sec) |
| ELO_Out_Avito | inactive | schedule (3 sec) |
| ELO_Out_Router | inactive | webhook |

### Input Contour (3 workflows)

| Workflow | Status | Trigger |
|----------|--------|---------|
| ELO_Input_Batcher | inactive | schedule |
| ELO_Input_Processor | inactive | schedule |
| ELO_Input_Ingest | inactive | webhook |

### Client Contour (1 workflow)

| Workflow | Status | Trigger |
|----------|--------|---------|
| ELO_Client_Resolve | ACTIVE | webhook: /elo-client-resolve |

### Core Contour (4 workflows)

| Workflow | Status | Trigger |
|----------|--------|---------|
| ELO_Message_Router | ACTIVE | webhook: /router |
| ELO_Core_Dialog_Engine | inactive | - |
| ELO_Core_Tenant_Resolver | inactive | - |
| ELO_Core_Batcher | inactive | webhook |

### AI Contour (10 workflows)

| Workflow | Status | Trigger |
|----------|--------|---------|
| ELO_Core_AI_Test_Stub | ACTIVE | webhook: /elo-core-ingest |
| ELO_AI_Extract | inactive | webhook |
| ELO_Context_Collector | inactive | webhook |
| ELO_Core_AI_Derive | inactive | webhook |
| ELO_Core_Context_Builder | inactive | webhook |
| ELO_Core_Graph_Writer | inactive | webhook |
| ELO_Core_Response_Generator | inactive | webhook |
| ELO_Core_Stage_Manager | inactive | webhook |
| ELO_Core_Triggers_Checker | inactive | webhook |
| ELO_Decision | inactive | webhook |
| ELO_Executor | inactive | webhook |

### Graph Contour (1 workflow)

| Workflow | Status | Trigger |
|----------|--------|---------|
| ELO_Graph_Query | inactive | webhook: /elo-graph-query |

---

## Current Issues

1. **ELO_Out_* inactive** - Outgoing message processors are not running
   - Messages queued in Redis but not sent to channels

2. **ELO_Input_Batcher inactive** - Redis batching not working
   - Set Deadline node hangs (Redis credentials issue)

3. **AI Contour inactive** - Only test stub is active
   - Full AI pipeline not operational

4. **DB Get Tenant mismatch** - Looking for wrong session_id
   - Query uses `eldoleado_arceos` but DB has `eldoleado_main`

---

## Servers

| Server | IP | Services |
|--------|-----|----------|
| n8n | 185.221.214.83 | n8n, PostgreSQL, Redis |
| MessagerOne | 155.212.221.189 | WhatsApp Baileys (8769) |
| Finnish | 217.145.79.27 | Telegram (8767) |
| RU Server | 45.144.177.128 | Avito, VK, MAX, Neo4j |

---

## File Structure

```
NEW/workflows/
├── API/                          # Android API endpoints
│   ├── ELO_API_Android_Auth.json
│   ├── ELO_API_Android_Dialogs.json
│   ├── ELO_API_Android_Messages.json
│   ├── ELO_API_Android_Normalize.json
│   ├── ELO_API_Android_Send_Message.json
│   ├── ELO_API_Android_Logout.json
│   └── ELO_API_Android_Register_FCM.json
├── Channel Contour/
│   ├── ELO_In/                   # Incoming message handlers
│   │   ├── ELO_In_WhatsApp.json  [ACTIVE]
│   │   ├── ELO_In_App.json       [ACTIVE]
│   │   └── ...
│   └── ELO_Out/                  # Outgoing message senders
│       ├── ELO_Out_WhatsApp.json [inactive]
│       └── ...
├── Input Contour/                # Message batching
├── Client Contour/               # Client resolution
├── Core Contour/                 # Core routing
├── AI Contour/                   # AI processing
└── Graph Contour/                # Neo4j queries
```

---

*Generated by sync script*
