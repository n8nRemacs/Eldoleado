# ELO Database Analysis

> Last sync: 2025-12-23 12:30

---

## Summary

| Category | Count |
|----------|-------|
| Transactional tables (elo_t_*) | 14 |
| Reference tables | 9 |
| Views (elo_v_*) | 5 |
| **Total** | 28 |

---

## Data Flow Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              INFRASTRUCTURE                                  │
│  ┌─────────────┐    ┌──────────────┐    ┌─────────────────────────────────┐ │
│  │elo_ip_nodes │───►│elo_t_tenants │◄───│elo_t_tenant_verticals          │ │
│  │(IP pool)    │    │(companies)   │    │(tenant↔vertical link)          │ │
│  └─────────────┘    └──────┬───────┘    └─────────────────────────────────┘ │
│                            │                                                 │
│         ┌──────────────────┼──────────────────┐                             │
│         ▼                  ▼                  ▼                             │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐                      │
│  │elo_t_       │    │elo_t_       │    │elo_t_       │                      │
│  │operators    │    │channel_     │    │clients      │                      │
│  │(users)      │    │accounts     │    │(customers)  │                      │
│  └──────┬──────┘    │(WA/TG/etc)  │    └──────┬──────┘                      │
│         │           └──────┬──────┘           │                             │
│         │                  │                  │                             │
└─────────┼──────────────────┼──────────────────┼─────────────────────────────┘
          │                  │                  │
          ▼                  ▼                  ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                              MESSAGING                                       │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                        elo_t_dialogs                                  │   │
│  │  (conversations: client + channel + operator + stage)                 │   │
│  └────────────────────────────────┬─────────────────────────────────────┘   │
│                                   │                                          │
│                                   ▼                                          │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                        elo_t_messages                                 │   │
│  │  (all messages in dialogs)                                            │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└──────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                              AI & TASKS                                      │
│  ┌─────────────────┐    ┌─────────────────┐                                 │
│  │elo_t_ai_        │    │elo_t_tasks      │                                 │
│  │extractions      │    │(work items)     │                                 │
│  │(AI findings)    │    │                 │                                 │
│  └─────────────────┘    └─────────────────┘                                 │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│                           REFERENCE DATA                                     │
│  ┌────────────┐ ┌──────────────┐ ┌───────────────┐ ┌──────────────────────┐ │
│  │elo_channels│ │elo_verticals │ │elo_symptom_   │ │elo_diagnosis_types  │ │
│  │(WA,TG,VK..)│ │(industries)  │ │types          │ │                      │ │
│  └────────────┘ └──────────────┘ └───────────────┘ └──────────────────────┘ │
│  ┌────────────────────┐ ┌───────────────────────┐ ┌──────────────────────┐  │
│  │elo_repair_actions  │ │elo_problem_categories │ │elo_cypher_queries   │  │
│  └────────────────────┘ └───────────────────────┘ └──────────────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Transactional Tables (elo_t_*)

### elo_t_tenants
**Purpose:** Companies/organizations using the system (multi-tenant)

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| name | varchar | NO | Company name |
| slug | varchar | YES | URL-friendly identifier |
| domain_id | integer | YES | Domain/region (default: 1) |
| settings | jsonb | YES | Custom settings |
| plan | varchar | YES | Subscription plan (free/pro/enterprise) |
| max_operators | integer | YES | Limit on operators (default: 3) |
| max_dialogs_per_month | integer | YES | Dialog limit (default: 500) |
| ip_node_id | integer | YES | **FK → elo_ip_nodes** - Preferred IP for channels |
| is_active | boolean | YES | Active flag |
| created_at | timestamptz | YES | Created timestamp |
| updated_at | timestamptz | YES | Updated timestamp |

**Relations:**
- → elo_ip_nodes (ip_node_id)

---

### elo_t_operators
**Purpose:** Users who handle dialogs (employees of tenants)

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| email | varchar | YES | Email for login |
| phone | varchar | YES | Phone number |
| telegram_id | varchar | YES | Telegram user ID |
| password_hash | varchar | YES | Hashed password |
| name | varchar | NO | Display name |
| avatar_url | text | YES | Profile picture URL |
| role | varchar | YES | Role (operator/admin/owner) |
| fcm_tokens | jsonb | YES | Firebase push tokens |
| settings | jsonb | YES | User preferences |
| is_active | boolean | YES | Active flag |
| last_seen_at | timestamptz | YES | Last activity |
| created_at | timestamptz | YES | Created timestamp |
| updated_at | timestamptz | YES | Updated timestamp |

**Relations:**
- → elo_t_tenants (tenant_id)

---

### elo_t_operator_devices
**Purpose:** Mobile/desktop devices registered by operators (for Android app)

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| operator_id | uuid | NO | **FK → elo_t_operators** |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| device_id | varchar | YES | Unique device identifier |
| device_type | varchar | NO | mobile/desktop/web |
| device_name | varchar | YES | Device model/name |
| device_info | jsonb | YES | OS version, app version |
| session_token | varchar | YES | **Auth token for API calls** |
| fcm_token | text | YES | Push notification token |
| app_mode | varchar | NO | client/operator mode |
| tunnel_url | text | YES | For debugging |
| tunnel_secret | varchar | YES | Tunnel auth |
| is_active | boolean | YES | Active session |
| last_active_at | timestamptz | YES | Last API call |
| created_at | timestamptz | YES | Created timestamp |
| updated_at | timestamptz | YES | Updated timestamp |

**Relations:**
- → elo_t_operators (operator_id)
- → elo_t_tenants (tenant_id)

---

### elo_t_channel_accounts
**Purpose:** Messenger accounts connected to tenants (WhatsApp sessions, Telegram bots, etc.)

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| channel_id | integer | NO | **FK → elo_channels** |
| account_id | varchar | NO | Account identifier (bot token, phone) |
| account_name | varchar | YES | Display name |
| webhook_hash | varchar | YES | For webhook validation |
| webhook_url | text | YES | Callback URL |
| credentials | jsonb | YES | API keys, tokens |
| ip_node_id | integer | YES | **FK → elo_ip_nodes** - Which IP/container |
| session_id | varchar | YES | **Baileys/MCP session ID** |
| session_status | varchar | YES | pending/qr/connected/disconnected |
| is_official | boolean | YES | Official API (no IP needed) |
| is_active | boolean | YES | Active flag |
| last_webhook_at | timestamptz | YES | Last incoming webhook |
| error_count | integer | YES | Consecutive errors |
| last_error | text | YES | Last error message |
| created_at | timestamptz | YES | Created timestamp |
| updated_at | timestamptz | YES | Updated timestamp |

**Relations:**
- → elo_t_tenants (tenant_id)
- → elo_channels (channel_id)
- → elo_ip_nodes (ip_node_id)

**Key for routing:** `session_id` links incoming messages to tenant

---

### elo_t_operator_channels
**Purpose:** Which operators handle which channel accounts

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| operator_id | uuid | NO | **FK → elo_t_operators** |
| channel_account_id | uuid | NO | **FK → elo_t_channel_accounts** |
| is_primary | boolean | YES | Primary responder |
| max_concurrent_dialogs | integer | YES | Workload limit (default: 50) |
| is_active | boolean | YES | Active flag |
| created_at | timestamptz | YES | Created timestamp |

**Relations:**
- → elo_t_operators (operator_id)
- → elo_t_channel_accounts (channel_account_id)

---

### elo_t_clients
**Purpose:** Customers who contact via messengers

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| name | varchar | YES | Client name |
| phone | varchar | YES | Phone number |
| email | varchar | YES | Email address |
| profile | jsonb | YES | Additional profile data |
| stats | jsonb | YES | Interaction statistics |
| neo4j_synced_at | timestamptz | YES | Last sync to graph DB |
| created_at | timestamptz | YES | Created timestamp |
| updated_at | timestamptz | YES | Updated timestamp |

**Relations:**
- → elo_t_tenants (tenant_id)

---

### elo_t_client_channels
**Purpose:** Client's messenger identities (one client can have WhatsApp + Telegram)

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | serial | NO | Primary key |
| client_id | uuid | NO | **FK → elo_t_clients** |
| channel_id | integer | NO | **FK → elo_channels** |
| external_id | varchar | NO | Messenger user ID (phone, chat_id) |
| external_username | varchar | YES | Username in messenger |
| metadata | jsonb | YES | Channel-specific data |
| created_at | timestamptz | YES | Created timestamp |
| updated_at | timestamptz | YES | Updated timestamp |

**Relations:**
- → elo_t_clients (client_id)
- → elo_channels (channel_id)

**Unique:** (client_id, channel_id, external_id)

---

### elo_t_dialogs
**Purpose:** Conversations between client and operator

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| client_id | uuid | NO | **FK → elo_t_clients** |
| channel_id | integer | NO | **FK → elo_channels** |
| channel_account_id | uuid | YES | **FK → elo_t_channel_accounts** |
| external_chat_id | varchar | YES | Messenger chat ID |
| vertical_id | integer | YES | **FK → elo_verticals** |
| status_id | integer | YES | Dialog status (1=active) |
| current_stage_id | integer | YES | **FK → elo_v_funnel_stages** |
| stage_entered_at | timestamptz | YES | When entered current stage |
| assigned_operator_id | uuid | YES | **FK → elo_t_operators** |
| context | jsonb | YES | AI context, extracted data |
| metadata | jsonb | YES | Additional metadata |
| last_message_at | timestamptz | YES | Last message timestamp |
| last_client_message_at | timestamptz | YES | Last client message |
| last_operator_message_at | timestamptz | YES | Last operator message |
| created_at | timestamptz | YES | Created timestamp |
| updated_at | timestamptz | YES | Updated timestamp |

**Relations:**
- → elo_t_tenants (tenant_id)
- → elo_t_clients (client_id)
- → elo_channels (channel_id)
- → elo_t_channel_accounts (channel_account_id)
- → elo_verticals (vertical_id)
- → elo_v_funnel_stages (current_stage_id)
- → elo_t_operators (assigned_operator_id)

---

### elo_t_messages
**Purpose:** All messages in dialogs

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| dialog_id | uuid | NO | **FK → elo_t_dialogs** |
| client_id | uuid | YES | **FK → elo_t_clients** |
| direction_id | integer | NO | 1=incoming, 2=outgoing |
| message_type_id | integer | YES | 1=text, 2=image, etc. |
| actor_type | varchar | NO | client/operator/ai/system |
| actor_id | uuid | YES | Who sent (operator_id or null) |
| content | text | YES | Message text |
| media | jsonb | YES | Images, voice, documents |
| changed_graph | boolean | YES | Triggered graph update |
| external_message_id | varchar | YES | Messenger message ID |
| trace_id | varchar | YES | For debugging |
| timestamp | timestamptz | NO | Message time |
| created_at | timestamptz | YES | Created timestamp |

**Relations:**
- → elo_t_tenants (tenant_id)
- → elo_t_dialogs (dialog_id)
- → elo_t_clients (client_id)

---

### elo_t_ai_extractions
**Purpose:** Data extracted by AI from messages (device model, issue, etc.)

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| dialog_id | uuid | NO | **FK → elo_t_dialogs** |
| message_id | uuid | YES | **FK → elo_t_messages** |
| extraction_type | varchar | NO | device_model/issue/intent/etc |
| value | jsonb | NO | Extracted value |
| confidence | numeric | YES | AI confidence 0-1 |
| status | varchar | YES | extracted/confirmed/rejected |
| confirmed_by | uuid | YES | **FK → elo_t_operators** |
| confirmed_at | timestamptz | YES | Confirmation time |
| created_at | timestamptz | YES | Created timestamp |

**Relations:**
- → elo_t_tenants (tenant_id)
- → elo_t_dialogs (dialog_id)
- → elo_t_messages (message_id)
- → elo_t_operators (confirmed_by)

---

### elo_t_tasks
**Purpose:** Work items (repairs, callbacks, follow-ups)

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| dialog_id | uuid | YES | **FK → elo_t_dialogs** |
| client_id | uuid | YES | **FK → elo_t_clients** |
| assignee_id | uuid | YES | **FK → elo_t_operators** |
| created_by_id | uuid | YES | **FK → elo_t_operators** |
| parent_task_id | uuid | YES | **FK → elo_t_tasks** (subtasks) |
| title | varchar | NO | Task title |
| description | text | YES | Details |
| task_type | varchar | YES | general/repair/callback/etc |
| deadline | timestamptz | YES | Due date |
| estimated_duration_min | integer | YES | Estimated time |
| status | varchar | YES | pending/in_progress/completed |
| priority | varchar | YES | low/normal/high/urgent |
| metadata | jsonb | YES | Additional data |
| started_at | timestamptz | YES | Work started |
| completed_at | timestamptz | YES | Work completed |
| created_at | timestamptz | YES | Created timestamp |
| updated_at | timestamptz | YES | Updated timestamp |

**Relations:**
- → elo_t_tenants (tenant_id)
- → elo_t_dialogs (dialog_id)
- → elo_t_clients (client_id)
- → elo_t_operators (assignee_id, created_by_id)
- → elo_t_tasks (parent_task_id) - self-reference

---

### elo_t_tenant_verticals
**Purpose:** Link tenants to verticals (industries they work in)

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | serial | NO | Primary key |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| vertical_id | integer | NO | **FK → elo_verticals** |
| custom_config | jsonb | YES | Tenant-specific overrides |
| is_primary | boolean | YES | Primary vertical |
| created_at | timestamptz | YES | Created timestamp |

**Relations:**
- → elo_t_tenants (tenant_id)
- → elo_verticals (vertical_id)

---

### elo_t_price_list
**Purpose:** Services and pricing for tenants

| Column | Type | Nullable | Description |
|--------|------|----------|-------------|
| id | uuid | NO | Primary key |
| tenant_id | uuid | NO | **FK → elo_t_tenants** |
| vertical_id | integer | YES | **FK → elo_verticals** |
| repair_action_id | integer | YES | **FK → elo_repair_actions** |
| service_type | varchar | NO | Service category |
| brand | varchar | YES | Device brand filter |
| model | varchar | YES | Device model filter |
| issue_category | varchar | YES | Issue type filter |
| name | varchar | NO | Service name |
| description | text | YES | Service description |
| price_min | numeric | YES | Minimum price |
| price_max | numeric | YES | Maximum price |
| price_display | varchar | YES | Display format ("от 1000₽") |
| currency | varchar | YES | Currency (default: RUB) |
| duration_minutes | integer | YES | Service duration |
| duration_display | varchar | YES | Display format ("30 мин") |
| availability | varchar | YES | available/unavailable/call |
| is_active | boolean | YES | Active flag |
| created_at | timestamptz | YES | Created timestamp |
| updated_at | timestamptz | YES | Updated timestamp |

**Relations:**
- → elo_t_tenants (tenant_id)
- → elo_verticals (vertical_id)
- → elo_repair_actions (repair_action_id)

---

## Reference Tables

### elo_channels
**Purpose:** Supported messenger channels

| id | code | name |
|----|------|------|
| 1 | telegram | Telegram |
| 2 | whatsapp | WhatsApp |
| 3 | avito | Avito |
| 4 | vk | VKontakte |
| 5 | max | MAX (VK Teams) |
| 6 | form | Web Form |
| 7 | phone | Phone Call |

---

### elo_verticals
**Purpose:** Industries/business types

| id | code | name |
|----|------|------|
| 1 | phone_repair | Phone Repair |

---

### elo_ip_nodes
**Purpose:** IP pool for reverse-engineered channels (WhatsApp Baileys, etc.)

| Column | Type | Description |
|--------|------|-------------|
| id | serial | Primary key |
| server_name | varchar | Server identifier (Messager_1) |
| server_host | varchar | SSH host for management |
| ip_address | inet | Actual IP for binding |
| port_whatsapp | integer | WhatsApp container port |
| port_telegram_user | integer | Telegram user port |
| port_vk_user | integer | VK user port |
| port_max | integer | MAX port |
| port_avito | integer | Avito port |
| max_sessions_per_type | integer | Limit per channel type (10) |
| status | varchar | active/maintenance/offline |

**Current data:**
| id | server_name | ip_address | status |
|----|-------------|------------|--------|
| 1 | MessagerOne | 155.212.221.189 | active |
| 2 | MessagerOne | 217.114.14.17 | active |

---

### elo_symptom_types / elo_diagnosis_types / elo_repair_actions
**Purpose:** Knowledge base for AI (phone repair domain)

Links:
- elo_symptom_diagnosis_links (symptom → diagnosis)
- elo_diagnosis_repair_links (diagnosis → repair action)

---

### elo_problem_categories
**Purpose:** Categories for issues (Screen, Battery, etc.)

---

### elo_cypher_queries
**Purpose:** Stored Neo4j queries for graph operations

---

## Views (elo_v_*)

| View | Purpose |
|------|---------|
| elo_v_ip_usage | IP node usage statistics |
| elo_v_funnel_stages | Dialog stages (from config) |
| elo_v_ai_settings | AI settings per tenant |
| elo_v_prompts | AI prompts |
| elo_v_triggers | Event triggers |
| elo_v_symptom_mappings | Symptom → diagnosis mappings |

---

## Current Issues Found

### 1. session_id is NULL in elo_t_channel_accounts

```sql
SELECT session_id, account_id FROM elo_t_channel_accounts WHERE channel_id = 2;
-- session_id = NULL, account_id = 'eldoleado_main'
```

**Problem:** Incoming WhatsApp has `sessionId: 'eldoleado_arceos'` but:
- `session_id` column is empty
- `account_id = 'eldoleado_main'` (different value)

**Fix needed:**
```sql
UPDATE elo_t_channel_accounts
SET session_id = 'eldoleado_arceos'
WHERE account_id = 'eldoleado_main' AND channel_id = 2;
```

### 2. ELO_Client_Resolve looks for wrong field

Currently looks for `profile_id` but should look for `session_id` from `meta.raw.sessionId`.

### 3. Naming convention: Messager_1 vs MessagerOne

Database has `MessagerOne`, proposed naming is `Messager_1`.

---

## Key Relationships Summary

```
elo_t_tenants (1)
    │
    ├──► elo_t_operators (N) ──► elo_t_operator_devices (N)
    │                      └──► elo_t_operator_channels (N)
    │
    ├──► elo_t_channel_accounts (N) ◄── elo_ip_nodes
    │
    ├──► elo_t_clients (N) ──► elo_t_client_channels (N)
    │
    └──► elo_t_dialogs (N) ──► elo_t_messages (N)
                          └──► elo_t_ai_extractions (N)
                          └──► elo_t_tasks (N)
```

---

*Generated: 2025-12-23*
