# Database Structure (BatteryCRM) - COMPLETE

Document describes ALL tables, fields, data types, purpose and relationships.
Current for project version v5.1 (Multi-Tenant + AI Training + Mini App + Android + Marketing Attribution).

**Updated:** 2025-11-20 23:35 (UTC+4)
**Status:** ✅ Complete and Actual

---

## 🏢 MULTI-TENANT SUPPORT

**All main tables contain `tenant_id UUID REFERENCES tenants(id)`**

### Table: tenants — Tenants (SaaS isolation)

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Tenant ID |
| name | varchar | NOT NULL | Company name |
| domain | varchar | NULL | Company domain |
| external_id | text | NULL | External system ID |
| jwt_secret | text | NULL | JWT secret |
| jwt_jwks | jsonb | NULL | JWT JWKS config |
| is_active | boolean | DEFAULT true | Active status |
| suspend | boolean | DEFAULT false | Suspension flag |
| max_concurrent_users | int | DEFAULT 200 | Max concurrent users |
| max_events_per_second | int | DEFAULT 100 | Rate limit |
| max_bytes_per_second | int | DEFAULT 100000 | Bandwidth limit |
| max_channels_per_client | int | DEFAULT 100 | Channel limit |
| max_joins_per_second | int | DEFAULT 500 | Joins rate limit |
| postgres_cdc_default | text | DEFAULT 'postgres_cdc_rls' | CDC config |
| notify_private_alpha | boolean | DEFAULT false | Private notifications |
| private_only | boolean | DEFAULT false | Private mode only |
| created_at | timestamp | DEFAULT now() | Creation date |
| inserted_at | timestamp | NOT NULL | Insertion timestamp |
| updated_at | timestamp | NOT NULL | Update timestamp |

---

### Table: tenant_configs — Tenant configurations

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Config ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| key | varchar | NOT NULL | Setting key |
| value | jsonb | NOT NULL | Setting value |
| created_at | timestamp | DEFAULT now() | Creation date |

Constraints: UNIQUE(tenant_id, key)

---

## 👥 CLIENTS AND MERGES

### Table: clients — Clients

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Client ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| phone | varchar | NULL | Client phone |
| name | varchar | NULL | Client name |
| email | varchar | NULL | Client email |
| telegram_id | varchar | NULL, index | Telegram ID |
| vk_id | varchar | NULL, index | VK ID |
| whatsapp_id | varchar | NULL, index | WhatsApp ID |
| avito_id | varchar | NULL, index | Avito ID |
| max_id | varchar | NULL | MAX integration ID |
| livesklad_client_id | varchar | NULL | LiveSklad mapping |
| has_telegram | boolean | DEFAULT false | Verified in Telegram |
| has_whatsapp | boolean | DEFAULT false | Verified in WhatsApp |
| has_phone | boolean | DEFAULT false | Verified phone |
| has_yandex | boolean | DEFAULT false | Verified in Yandex |
| has_google | boolean | DEFAULT false | Verified in Google |
| has_instagram | boolean | DEFAULT false | Verified in Instagram |
| has_avito | boolean | DEFAULT false | Verified in Avito |
| unsubscribed | boolean | DEFAULT false | Unsubscribed |
| created_at | timestamp | DEFAULT now() | Creation date |
| updated_at | timestamp | DEFAULT now() | Update date |

Indexes: tenant_id, telegram_id, vk_id, whatsapp_id, avito_id, has_telegram, has_whatsapp

---

### Table: client_merges — Client merges

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Merge ID |
| master_client_id | uuid | FK → clients(id), NOT NULL | Master client |
| merged_client_id | uuid | FK → clients(id), NOT NULL, UNIQUE | Merged client |
| merged_by | varchar | NULL | Operator ID |
| merge_reason | text | NULL | Merge reason |
| merged_at | timestamp | DEFAULT now() | Merge date |

---

## 📋 APPEALS AND MESSAGES

### Table: appeals — Appeals

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Appeal ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| client_id | uuid | FK → clients(id), NOT NULL | Client |
| channel_id | uuid | FK → channels(id) | Channel (deprecated) |
| ad_channel | varchar | NULL | Ad channel (deprecated) |
| ad_channel_id | uuid | FK → channels(id) | Ad channel ref |
| ad_id | varchar | NULL | Ad campaign ID |
| sales_channel_id | uuid | FK → sales_channels(id) | Sales channel |
| lead_source_id | uuid | FK → lead_sources(id) | Lead source |
| device_type_id | uuid | FK → device_types(id) | Device type |
| brand_id | uuid | FK → brands(id) | Brand |
| model_id | uuid | FK → models(id) | Model |
| phone_brand | varchar | NULL | Brand (text, legacy) |
| phone_model | varchar | NULL | Model (text, legacy) |
| deal_type_id | uuid | FK → deal_types(id) | Deal type |
| repair_type_id | uuid | FK → repair_types(id) | Repair type |
| issue_type_id | uuid | FK → issue_types(id) | Issue type |
| issue_id | uuid | FK → issues(id) | Specific issue |
| malfunction_category | varchar | NULL | Malfunction (legacy) |
| malfunction_type | varchar | NULL | Malfunction (legacy) |
| parts_owner | varchar | NULL | 'ours' / 'client' |
| client_parts | boolean | DEFAULT false | Client parts flag |
| problem_description | text | NULL | Problem description |
| estimated_cost | numeric | NULL | Estimated cost |
| estimated_time | int | NULL | Estimated time (min) |
| stage | varchar | DEFAULT 'Первичный контакт' | Stage |
| appeal_status | varchar | NULL | Status (legacy) |
| type | varchar | NULL | Appeal type |
| type_confidence | numeric | DEFAULT 0 | Type confidence |
| type_identified | boolean | DEFAULT false | Type identified |
| type_reasoning | text | NULL | Type reasoning |
| operation_mode | varchar | DEFAULT 'assist' | Mode: manual/assist/auto |
| pending_operator_approval | boolean | DEFAULT false | Awaiting approval |
| draft_response | text | NULL | AI draft |
| routing_history | jsonb | DEFAULT '[]' | Routing history |
| appointment_datetime | timestamp | NULL | Appointment time |
| master_id | int | NULL | Master ID |
| location_id | uuid | NULL | Location ID |
| qr_code | text | NULL | QR code |
| livesklad_order_id | varchar | NULL | LiveSklad order |
| synced_with_livesklad | boolean | DEFAULT false | Synced flag |
| synced_at | timestamp | NULL | Sync timestamp |
| visited_at | timestamp | NULL | Visit timestamp |
| completed_at | timestamp | NULL | Completion time |
| notified_2h_after_contact | boolean | DEFAULT false | Notification flag |
| notified_1day_before | boolean | DEFAULT false | Notification flag |
| notified_2h_before | boolean | DEFAULT false | Notification flag |
| notified_after_completion | boolean | DEFAULT false | Notification flag |
| notified_review_request | boolean | DEFAULT false | Notification flag |
| notified_warranty_reminder | boolean | DEFAULT false | Notification flag |
| notified_ | boolean | DEFAULT false | Generic flag |
| created_at | timestamp | DEFAULT now() | Creation date |
| updated_at | timestamp | DEFAULT now() | Update date |

Indexes: tenant_id, client_id, stage, brand_id, model_id, sales_channel_id, lead_source_id, issue_type_id, issue_id

---

### Table: messages_history — Message history

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Message ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| appeal_id | uuid | FK → appeals(id), NOT NULL | Appeal |
| message_type | varchar | NULL | client/agent/operator |
| message_text | text | NOT NULL | Message text |
| channel | varchar | NULL | Channel (deprecated) |
| channel_id | uuid | FK → channels(id) | Channel |
| external_message_id | varchar | NULL | External msg ID |
| external_chat_id | varchar | NULL | External chat ID |
| created_at | timestamp | DEFAULT now() | Creation date |

Indexes: tenant_id, appeal_id, created_at

---

## 👨‍💼 OPERATORS

### Table: operators — Operators

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Operator ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| telegram_id | varchar | NULL | Telegram ID |
| username | varchar | NULL | Username |
| email | varchar | NULL | Email |
| password_hash | varchar | NULL | Password hash |
| name | varchar | NULL | Operator name |
| location_id | uuid | NULL | Location ID |
| session_token | varchar | NULL | Session token |
| session_token_created_at | timestamp | NULL | Token creation |
| fcm_token | varchar | NULL | FCM token |
| fcm_token_updated_at | timestamp | DEFAULT now() | FCM update |
| last_login_at | timestamp | NULL | Last login |
| last_login_platform | varchar | NULL | Platform |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamp | DEFAULT now() | Creation date |
| updated_at | timestamp | DEFAULT now() | Update date |

Indexes: tenant_id, telegram_id, session_token

---

### Table: operator_actions — Operator action log

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Action ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| operator_id | uuid | FK → operators(id) | Operator |
| context_appeal_id | uuid | FK → appeals(id) | Appeal context |
| action_type | varchar | NOT NULL | Action type |
| details | jsonb | NULL | Action details |
| created_at | timestamp | DEFAULT now() | Action timestamp |

Indexes: tenant_id, operator_id, context_appeal_id, created_at

---

### Table: operator_appeal_status — Operator-appeal tracking

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Status ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| operator_id | uuid | FK → operators(id) | Operator |
| appeal_id | uuid | FK → appeals(id) | Appeal |
| status | varchar | DEFAULT 'pending' | Status |
| notified_at | timestamp | NULL | Notification time |
| viewed_at | timestamp | NULL | View time |
| completed_at | timestamp | NULL | Completion time |
| created_at | timestamp | DEFAULT now() | Creation date |
| updated_at | timestamp | DEFAULT now() | Update date |

Constraints: UNIQUE(tenant_id, operator_id, appeal_id)
Indexes: tenant_id, operator_id, appeal_id, status

---

## 📱 CHANNELS AND MARKETING

### Table: channels — Communication channels

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Channel ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| name | varchar | NOT NULL | Channel name |
| platform | varchar | NOT NULL | Platform type |
| external_id | varchar | NULL | External ID |
| credentials | jsonb | NULL | API credentials |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamp | DEFAULT now() | Creation date |

Indexes: tenant_id

---

### Table: sales_channels — Sales channels

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Channel ID |
| tenant_id | uuid | FK → tenants(id), NOT NULL | Tenant |
| name | varchar | NOT NULL | Channel name |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamptz | DEFAULT now() | Creation date |
| updated_at | timestamptz | DEFAULT now() | Update date |

Constraints: UNIQUE(tenant_id, name)
Indexes: tenant_id, (tenant_id, is_active)

---

### Table: lead_sources — Lead sources with UTM

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Source ID |
| tenant_id | uuid | FK → tenants(id), NOT NULL | Tenant |
| sales_channel_id | uuid | FK → sales_channels(id), NOT NULL | Sales channel |
| name | varchar | NOT NULL | Source name |
| utm_source | varchar | NULL | UTM source |
| utm_medium | varchar | NULL | UTM medium |
| utm_campaign | varchar | NULL | UTM campaign |
| utm_term | varchar | NULL | UTM term |
| utm_content | varchar | NULL | UTM content |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamptz | DEFAULT now() | Creation date |
| updated_at | timestamptz | DEFAULT now() | Update date |

Constraints: UNIQUE(tenant_id, sales_channel_id, name)
Indexes: tenant_id, sales_channel_id, (tenant_id, is_active)

---

## 📊 DEVICE AND SERVICE REFERENCES

### Table: device_types — Device types

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Type ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| name | varchar | NOT NULL | Type name |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamp | DEFAULT now() | Creation date |

Indexes: tenant_id

---

### Table: brands — Device brands

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Brand ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| name | varchar | NOT NULL | Brand name |
| normalized_name | varchar | NOT NULL | Normalized name |
| aliases | jsonb | DEFAULT '[]' | Aliases |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamp | DEFAULT now() | Creation date |

Indexes: tenant_id

---

### Table: models — Device models

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Model ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| brand_id | uuid | FK → brands(id), NOT NULL | Brand |
| device_type_id | uuid | FK → device_types(id) | Device type |
| name | varchar | NOT NULL | Model name |
| aliases | jsonb | DEFAULT '[]' | Aliases |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamp | DEFAULT now() | Creation date |

Indexes: tenant_id, brand_id, device_type_id

---

### Table: repair_types — Repair types

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Type ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| name | varchar | NOT NULL | Type name |
| description | text | NULL | Description |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamp | DEFAULT now() | Creation date |

Indexes: tenant_id

---

### Table: issue_types — Issue types

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Type ID |
| tenant_id | uuid | FK → tenants(id), NOT NULL | Tenant |
| name | varchar | NOT NULL | Type name |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamptz | DEFAULT now() | Creation date |
| updated_at | timestamptz | DEFAULT now() | Update date |

Constraints: UNIQUE(tenant_id, name)
Indexes: tenant_id, (tenant_id, is_active)

---

### Table: issues — Concrete issues

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Issue ID |
| tenant_id | uuid | FK → tenants(id), NOT NULL | Tenant |
| issue_type_id | uuid | FK → issue_types(id), NOT NULL | Issue type |
| name | varchar | NOT NULL | Issue name |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamptz | DEFAULT now() | Creation date |
| updated_at | timestamptz | DEFAULT now() | Update date |

Constraints: UNIQUE(tenant_id, issue_type_id, name)
Indexes: tenant_id, issue_type_id, (tenant_id, is_active)

---

### Table: deal_types — Deal types

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Type ID |
| name | varchar | NOT NULL | Type name |
| is_repair | boolean | DEFAULT false | Repair flag |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamp | DEFAULT now() | Creation date |

---

### Table: services — Services and prices

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Service ID |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| brand_id | uuid | FK → brands(id) | Brand |
| model_id | uuid | FK → models(id) | Model |
| repair_type_id | uuid | FK → repair_types(id) | Repair type |
| malfunction_category | varchar | NOT NULL | Category |
| malfunction_type | varchar | NOT NULL | Type |
| price_rub | numeric | NOT NULL | Price (RUB) |
| duration_minutes | int | NOT NULL | Duration (min) |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamp | DEFAULT now() | Creation date |
| updated_at | timestamp | DEFAULT now() | Update date |

Indexes: tenant_id

---

### Table: masters — Service masters

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | int | PK, serial | Master ID |
| name | varchar | NOT NULL | Master name |
| working_hours_start | time | DEFAULT '09:00' | Start time |
| working_hours_end | time | DEFAULT '20:00' | End time |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamp | DEFAULT now() | Creation date |

---

### Table: appointments — Appointments

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Appointment ID |
| appeal_id | uuid | FK → appeals(id), NOT NULL | Appeal |
| master_id | int | FK → masters(id), NOT NULL | Master |
| start_time | timestamp | NOT NULL | Start time |
| end_time | timestamp | NOT NULL | End time |
| status | varchar | DEFAULT 'Запланировано' | Status |
| created_at | timestamp | DEFAULT now() | Creation date |
| updated_at | timestamp | DEFAULT now() | Update date |

---

### Table: knowledge_base — Knowledge base

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | uuid | PK, gen_random_uuid() | Entry ID |
| content | text | NOT NULL | Content |
| content_type | varchar | NULL | Content type |
| embedding | vector | NULL | Vector embedding |
| metadata | jsonb | NULL | Metadata |
| source | varchar | NULL | Source |
| added_by | varchar | NULL | Added by |
| created_at | timestamp | DEFAULT now() | Creation date |
| updated_at | timestamp | DEFAULT now() | Update date |

---

### Table: appeal_meta_config — Appeal metadata config

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| id | text | PK | Parameter ID |
| label | text | NOT NULL | Display label |
| group | text | NOT NULL | Group |
| order | numeric | NOT NULL | Display order |
| source_field | text | NOT NULL | Data source |
| format_type | text | NOT NULL | Format type |
| is_active | boolean | DEFAULT true | Active status |
| created_at | timestamptz | DEFAULT now() | Creation date |
| updated_at | timestamptz | DEFAULT now() | Update date |

Constraints:
- CHECK (group IN ('appeal', 'client', 'device', 'other'))
- CHECK (format_type IN ('text', 'date', 'duration', 'currency', 'boolean'))

Indexes: is_active, order, group

---

### Table: appeal_meta_visibility — Appeal metadata visibility rules

| Field | Type | Constraints | Purpose |
|:------|:-----|:------------|:--------|
| config_id | text | FK → appeal_meta_config(id) | Config parameter |
| deal_type_id | uuid | FK → deal_types(id) | Deal type |
| tenant_id | uuid | FK → tenants(id) | Tenant |
| is_visible | boolean | DEFAULT true | Visibility flag |
| created_at | timestamptz | DEFAULT now() | Creation date |
| updated_at | timestamptz | DEFAULT now() | Update date |

Constraints: PRIMARY KEY (config_id, deal_type_id, tenant_id)

Indexes: tenant_id, (deal_type_id, tenant_id)

**Purpose:** Controls which metadata parameters are visible for specific deal types per tenant.

---

## END OF DOCUMENT

**Version:** 5.1
**Date:** 2025-11-20 23:35 (UTC+4)
**Tables:** 37
**Status:** ✅ Complete
