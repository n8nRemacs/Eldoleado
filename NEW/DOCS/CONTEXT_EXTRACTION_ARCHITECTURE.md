# Context Extraction Architecture

> Four-level configurable context extraction system for AI Contour

**Version:** 1.0
**Created:** 2026-01-02

---

## Table of Contents

1. [Overview](#overview)
2. [Four-Level Hierarchy](#four-level-hierarchy)
3. [Domains](#domains)
4. [Graph Structure](#graph-structure)
5. [Funnel System](#funnel-system)
6. [Universal Workers](#universal-workers)
7. [Actions & Triggers](#actions--triggers)
8. [Context Routing](#context-routing)
9. [Database Tables](#database-tables)
10. [Workflows](#workflows)

---

## Overview

### Philosophy

The system extracts context from user messages using a **four-level hierarchy**:
- **Global** - greeting, goodbye, sentiment (available to all)
- **Domain** - device, vehicle, product (business domain specifics)
- **Vertical** - symptom, price_expectation (operation specifics)
- **Tenant** - overrides and custom types

**Routing:** If context belongs to an unconnected domain/vertical → stub response.

### Architecture Diagram

```
PostgreSQL (single database)                    Neo4j Enterprise (multi-database)
┌─────────────────────────────────┐            ┌─────────────────────────────────┐
│                                 │            │                                 │
│  GLOBAL LEVEL                   │            │  ┌───────────┐ ┌───────────┐   │
│  elo_context_types              │            │  │electronics│ │   auto    │   │
│  elo_intent_types               │            │  │           │ │           │   │
│  elo_normalization_rules        │            │  │ Client    │ │ Client    │   │
│           │                     │            │  │ Appeal    │ │ Appeal    │   │
│           ▼                     │            │  │ Device    │ │ Vehicle   │   │
│  ┌────────┴────────┐            │            │  │ Symptom   │ │ Issue     │   │
│  │ DOMAIN LEVEL    │            │            │  │ Issue     │ │           │   │
│  │ elo_domains     │            │            │  └───────────┘ └───────────┘   │
│  │ elo_d_*         │            │            │                                 │
│  └────────┬────────┘            │            │  ┌───────────┐                  │
│           │                     │            │  │ software  │                  │
│           ▼                     │            │  │           │                  │
│  ┌────────┴────────┐            │            │  │ Client    │                  │
│  │ VERTICAL LEVEL  │            │            │  │ Company   │                  │
│  │ elo_verticals   │            │            │  │ Product   │                  │
│  │ elo_v_*         │            │            │  │ License   │                  │
│  └────────┬────────┘            │            │  └───────────┘                  │
│           │                     │            │                                 │
│           ▼                     │            │  Link via pg_id (client_id)     │
│  ┌────────┴────────┐            │            └─────────────────────────────────┘
│  │ TENANT LEVEL    │            │
│  │ elo_t_*         │            │
│  └─────────────────┘            │
│                                 │
└─────────────────────────────────┘
```

---

## Four-Level Hierarchy

### Global Level (elo_)

Context types available to **all tenants** regardless of domain/vertical.

| Table | Description |
|-------|-------------|
| `elo_context_types` | greeting, goodbye, sentiment, urgency |
| `elo_intent_types` | question, complaint, thanks |
| `elo_normalization_rules` | Normalization rules (brand, owner, etc.) |

### Domain Level (elo_d_)

Context types specific to **business domain**.

| Domain | Context Types |
|--------|---------------|
| electronics | device (brand, model, color, imei), owner |
| auto | vehicle (make, model, year, vin, mileage), owner |
| software | product (name, version, type), company |

| Table | Description |
|-------|-------------|
| `elo_domains` | Domain definitions |
| `elo_d_context_types` | Domain-specific context types |
| `elo_d_intent_types` | Domain-specific intents |
| `elo_d_entity_types` | Graph entities for domain |

### Vertical Level (elo_v_)

Context types specific to **operation type** within a domain.

| Vertical | Domain | Context Types |
|----------|--------|---------------|
| repair | electronics | symptom, warranty, urgency_repair |
| sales | electronics | price_expectation, condition |
| buyback | electronics | condition, price_offer |
| repair | auto | issue, urgency, warranty |
| sales | software | budget, timeline, decision_maker |

| Table | Description |
|-------|-------------|
| `elo_verticals` | Vertical definitions (tied to domain) |
| `elo_v_context_types` | Vertical-specific context types |
| `elo_v_intent_types` | Vertical-specific intents |
| `elo_v_funnel_stages` | Base funnel stages |

### Tenant Level (elo_t_)

**Overrides and custom types** for specific tenant.

| Table | Description |
|-------|-------------|
| `elo_t_tenant_domains` | Connected domains |
| `elo_t_tenant_verticals` | Connected verticals |
| `elo_t_context_type_overrides` | Override context_types |
| `elo_t_context_extractions` | Extraction results |
| `elo_t_routing_rules` | Custom routing rules |

---

## Domains

### Electronics Domain

**Neo4j Entities:** Client, Appeal, Device, Symptom, Issue

**Context Types:**
| Code | Description |
|------|-------------|
| device | brand, model, color, imei |
| owner | my, wife, husband, kid, friend |

**Verticals:**
- repair - symptom, warranty, urgency_repair
- sales - price_expectation, condition
- buyback - condition, price_offer
- rental - rental_period, deposit

### Auto Domain

**Neo4j Entities:** Client, Appeal, Vehicle, Issue, Part, Service

**Context Types:**
| Code | Description |
|------|-------------|
| vehicle | make, model, year, vin, mileage |
| owner | my, company, fleet |

**Verticals:**
- repair - issue, urgency, warranty
- sales - price_expectation, condition, history
- buyback - condition, price_offer, documents

### Software Domain

**Neo4j Entities:** Client, Company, Product, License, Ticket

**Context Types:**
| Code | Description |
|------|-------------|
| product | name, version, type |
| company | name, size, industry |

**Verticals:**
- sales - budget, timeline, decision_maker
- support - issue, license_status, priority
- marketing - campaign, source, interest_level

---

## Graph Structure

### Electronics Domain Entities

```
CLIENT
   │
   └── APPEAL (1:N per Dialog)
          │
          └── DEVICE: iPhone 14 Pro
                    │
                    ├── SYMPTOM (shared per device)
                    │      ├── "dropped"
                    │      └── "water damage"
                    │
                    ├── ISSUE 1 (full lifecycle)
                    │      ├── complaint: "won't charge" ─── client
                    │      ├── diagnosis: "port corroded" ── operator
                    │      └── repair: "replace port" ───── master
                    │
                    └── ISSUE 2
                           ├── complaint: "no Wi-Fi" ─────── client
                           ├── diagnosis: "chip damaged" ── operator
                           └── repair: "replace chip" ───── master
```

### Issue Entity Structure

```javascript
Issue {
  id: uuid,

  // Stage 1: Client
  complaint: string,           // "won't charge"
  complaint_at: timestamp,

  // Stage 2: Operator
  diagnosis: string,           // "lightning port - corrosion"
  diagnosis_by: operator_id,
  diagnosis_at: timestamp,

  // Stage 3: Master
  repair: string,              // "replace lightning port"
  repair_by: master_id,
  repair_at: timestamp,

  // Status
  status: "pending" | "diagnosed" | "repaired" | "closed"
}
```

### Graph Relationships

```
Client ──HAS_APPEAL──→ Appeal ──FOR_DEVICE──→ Device
                                                │
                                    ┌───────────┴───────────┐
                                    │                       │
                              HAS_SYMPTOM              HAS_ISSUE
                                    │                       │
                                    ▼                       ▼
                                 Symptom                  Issue
```

### Context Collection Rules

| Rule | Description |
|------|-------------|
| **Full collection** | Don't switch until collected: device + complaint |
| **Device switch** | If different device mentioned → ask "same or different?" |
| **Same model** | iPhone 12 + iPhone 12 → ask if new appeal or not |
| **Return to incomplete** | After collecting new → return to incomplete |
| **Symptoms shared** | Symptoms shared per device, not per issue |

---

## Funnel System

### Stage Types

| Type | Level | Can Remove? | Example |
|------|-------|-------------|---------|
| **system** | Global | No | lead, qualification, data_collection |
| **vertical** | Vertical | Yes | presentation, agreement, booking |
| **tenant** | Tenant | Yes | promo_offer, loyalty_check |

### Required System Stages

```
1. LEAD
   ├── Goal: understand intent
   ├── Actions: greeting, clarify goal
   └── Exit: intent determined

2. QUALIFICATION
   ├── Goal: qualified or not
   ├── Checks: domain? vertical? spam?
   └── Exit: qualified / not_qualified / spam

3. DATA_COLLECTION
   ├── Goal: collect minimum for graph
   ├── Required: device + complaint (for repair)
   └── Exit: all required fields filled
```

### Funnel Inheritance

```
VERTICAL (electronics.repair)
   │
   │  Base funnel:
   │  lead → qual → collect → presentation → agreement → booking → done
   │
   ▼
TENANT (iPhone Master)
   │
   │  Overrides:
   │  ├── After collect add: promo_offer
   │  ├── booking: change exit_conditions
   │  └── Add: loyalty_check before done
   │
   ▼
   lead → qual → collect → promo_offer → presentation → agreement → booking → loyalty_check → done
```

### Exit Conditions (Examples)

```json
// DATA_COLLECTION exit
{
  "type": "all_of",
  "conditions": [
    { "field": "device.brand", "op": "exists" },
    { "field": "device.model", "op": "exists" },
    { "field": "issue.complaint", "op": "exists" }
  ]
}

// BOOKING exit
{
  "type": "all_of",
  "conditions": [
    { "field": "booking.date", "op": "exists" },
    { "field": "booking.time", "op": "exists" },
    { "field": "booking.confirmed", "op": "eq", "value": true }
  ]
}
```

---

## Universal Workers

### Philosophy

All workers are **"blind"** and unified - work from DB config, no hardcode.

### Worker Types

| Type | Purpose | Example |
|------|---------|---------|
| **extractor** | Extract from message | device, symptom, intent |
| **data_fetch** | Query databases | price, availability, specs |
| **hook_inbound** | Receive webhook | CRM diagnosis, master repair |
| **hook_outbound** | HTTP request out | Notify CRM, send to external |
| **generator** | Generate response | Response text, buttons |

### Parallel Execution with Groups

```
Stage: DATA_COLLECTION

Group 0 (parallel):
├── extract_device      ─┐
├── extract_symptom     ─┼─→ execute simultaneously
├── extract_complaint   ─┤
└── extract_intent      ─┘

Group 1 (after group 0, parallel):
├── fetch_price         ─┐   (depends on device)
└── fetch_availability  ─┘   (depends on device)

Group 2 (after group 1):
└── notify_operator          (depends on all data)
```

### Caching

```
Message 1: "I have an iPhone 14 that won't charge"
├── extract_device → { brand: "Apple", model: "iPhone 14" }
│   └── CACHE SET: device:iPhone14 = result
├── extract_complaint → { text: "won't charge" }
└── fetch_price → { min: 3000, max: 5000 }
    └── CACHE SET: price:iPhone14:charging = result

Message 2: "Also the screen is cracked"
├── extract_device → CACHE HIT (already have device)
├── extract_complaint → { text: "cracked screen" }  (new complaint)
└── fetch_price → NEW REQUEST (different repair)
```

### Worker Config Examples

**EXTRACTOR:**
```json
{
  "prompt_id": 15,
  "normalization_rules_ref": "device_brand",
  "graph_mapping": {
    "entity": "Device",
    "properties": { "brand": "brand", "model": "model" }
  },
  "context_path": "appeals[active].device"
}
```

**DATA_FETCH:**
```json
{
  "source_type": "postgresql",
  "query": "SELECT price_min, price_max FROM elo_price_list WHERE device_model ILIKE $1",
  "params": [{ "path": "context.device.model" }],
  "result_mapping": { "price.min": "price_min", "price.max": "price_max" }
}
```

---

## Actions & Triggers

### Predefined Actions (Catalog)

| Code | Category | Description |
|------|----------|-------------|
| send_text | response | Text response |
| send_media | response | Send media |
| send_link | response | Send link |
| send_buttons | response | Response with buttons |
| send_promo | response | Promo materials |
| book_appointment | integration | Book appointment |
| create_task | system | Create task |
| notify_operator | notification | Notify operator |
| notify_client | notification | Notify client |
| update_crm | integration | Update CRM |
| close_appeal | system | Close appeal |
| transfer_operator | system | Transfer to operator |
| collect_field | system | Request field |
| skip_stage | system | Skip stage |

### Predefined Triggers (Catalog)

| Code | Category | Description |
|------|----------|-------------|
| field_filled | context | Field is filled |
| field_empty | context | Field is empty |
| field_equals | context | Field equals value |
| field_contains | context | Field contains substring |
| stage_entered | funnel | Entered stage |
| stage_exited | funnel | Exited stage |
| stage_timeout | time | Stage timeout |
| dialog_timeout | time | No response N minutes |
| appeal_count | context | Number of appeals |
| intent_detected | context | Intent detected |
| domain_mismatch | context | Outside domain |
| vertical_mismatch | context | Outside vertical |
| webhook_received | event | Received webhook |
| all_of | logic | All conditions |
| any_of | logic | Any condition |

### Trigger Example

```json
{
  "code": "promo_on_device",
  "trigger_type": "field_filled",
  "condition_params": { "field_path": "device.model" },
  "action_type": "send_promo",
  "action_params": { "promo_id": "device_discount" },
  "once_per_appeal": true
}
```

---

## Context Routing

### Data Flow

```
Message: "Hi! I want to sell my iPhone 14 and ask about car repair"

                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────┐
│ 1. EXTRACTION (all levels, top to bottom)                           │
│                                                                      │
│    Global:    greeting=true, intent_base=greeting                    │
│    Domain:    device={brand:Apple, model:14}, vehicle=car            │
│    Vertical:  intent_action=sell (sales), intent_action=repair       │
└─────────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────┐
│ 2. ROUTING (check tenant connections)                               │
│                                                                      │
│    Tenant "iPhone Master":                                           │
│    ├── Domain: electronics ✓                                         │
│    ├── Domain: auto ✗                                                │
│    ├── Vertical: repair ✓                                            │
│    └── Vertical: sales ✗                                             │
│                                                                      │
│    Result:                                                           │
│    ├── device (Apple 14) → ✓ accept, write to graph                  │
│    ├── vehicle (car) → ✗ stub: "we don't handle auto"                │
│    ├── intent=sell → ✗ stub: "we don't handle sales"                 │
│    └── intent=repair → ✓ but it's about auto, ignore                 │
└─────────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────┐
│ 3. RESULT                                                            │
│                                                                      │
│    accepted_context: [greeting, device]                              │
│    rejected_context: [vehicle, sell_intent]                          │
│    response_hints: ["We only handle electronics repair"]             │
└─────────────────────────────────────────────────────────────────────┘
```

### Multi-Vertical Logic

**Same device + different vertical:**
```
Current: iPhone 14, repair, collecting
Message: "Actually I'll sell it"

→ Same device (iPhone 14)
→ Different vertical (repair → buyback)

⚠️ REQUIRES CLARIFICATION:
"Do you want to cancel repair and sell iPhone 14?
 Or continue repair and also evaluate for buyback?"

Option 1: "Cancel repair"
   → Appeal 1: status=cancelled
   → Appeal 2: iPhone 14, vertical=buyback

Option 2: "Continue both"
   → Appeal 1: stays active
   → Appeal 2: iPhone 14, vertical=buyback (new)
```

**Different device + any vertical:**
```
Current: iPhone 14, repair
Message: "And I want to sell my old Samsung"

→ Different device (Samsung ≠ iPhone)
→ Different vertical (buyback)

✓ NO CLARIFICATION NEEDED
→ Appeal 1: iPhone 14, repair (continue)
→ Appeal 2: Samsung, buyback (new)
```

### Rejection Handling

AI generates stub response itself based on context:
- Connected domains/verticals of tenant
- What client asked
- Tenant name and specialization

```
Client: "I want to repair my car"
Context: tenant="iPhone Master", domains=["electronics"], verticals=["repair"]

AI: "Unfortunately, we specialize in electronics repair -
     phones, tablets, laptops. How can I help in this area?"
```

---

## Database Tables

### Table Overview by Level

| Level | Prefix | Tables |
|-------|--------|--------|
| Global | elo_ | context_types, intent_types, normalization_rules |
| Domain | elo_d_ | context_types, intent_types, entity_types |
| Vertical | elo_v_ | context_types, intent_types, funnel_stages |
| Tenant | elo_t_ | tenant_domains, tenant_verticals, context_type_overrides |
| System | elo_ | domains, verticals, custom_fields, prompts, worker_configs |
| Actions | elo_ | action_types, trigger_types, triggers |

### Key Tables

**elo_domains:**
```sql
CREATE TABLE elo_domains (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    neo4j_database VARCHAR(50),
    config JSONB DEFAULT '{}',
    is_active BOOLEAN DEFAULT true
);
```

**elo_funnel_stages:**
```sql
CREATE TABLE elo_funnel_stages (
    id SERIAL PRIMARY KEY,
    stage_type VARCHAR(20) NOT NULL,  -- system, vertical, tenant
    vertical_id INTEGER REFERENCES elo_verticals(id),
    tenant_id INTEGER,
    code VARCHAR(50) NOT NULL,
    sort_order INTEGER NOT NULL,
    entry_conditions JSONB,
    exit_conditions JSONB,
    on_enter_actions JSONB,
    on_exit_actions JSONB,
    is_required BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true
);
```

**elo_worker_configs:**
```sql
CREATE TABLE elo_worker_configs (
    id SERIAL PRIMARY KEY,
    worker_type VARCHAR(30) NOT NULL,  -- extractor, data_fetch, hook_*, generator
    level VARCHAR(20) NOT NULL,
    domain_id INTEGER,
    vertical_id INTEGER,
    tenant_id INTEGER,
    code VARCHAR(50) NOT NULL,
    prompt_id INTEGER,
    config JSONB NOT NULL,
    cache_enabled BOOLEAN DEFAULT true,
    cache_ttl_minutes INTEGER DEFAULT 60,
    is_active BOOLEAN DEFAULT true
);
```

---

## Workflows

### ELO_AI_Extract

**Purpose:** Extract context from message using four-level hierarchy.

**Flow:**
1. Load global context types
2. Load domain context types (based on tenant domains)
3. Load vertical context types (based on tenant verticals)
4. Load tenant overrides
5. Merge all into extraction config
6. Call LLM with merged prompt
7. Apply normalization rules
8. Return extracted context

### ELO_Context_Router

**Purpose:** Route extracted context based on tenant connections.

**Flow:**
1. Receive extracted context
2. Check each context item against tenant packages
3. Mark as accepted/rejected
4. Generate rejection hints for AI
5. Return routing result

### ELO_Funnel_Controller

**Purpose:** Manage funnel stage transitions.

**Flow:**
1. Get current stage
2. Check exit conditions
3. If met → transition to next stage
4. Execute stage actions (on_exit, on_enter)
5. Check for context switch
6. Return stage update

### ELO_Worker_Executor

**Purpose:** Execute universal workers.

**Flow:**
1. Receive worker config
2. Check cache
3. Check run conditions
4. Execute by worker type
5. Apply normalization
6. Update context
7. Update graph
8. Set cache
9. Return result

---

## Migration

The system is implemented in migration: `007_domains_context_extraction.sql`

Includes:
- All table definitions
- Seed data for 3 domains
- Global context/intent types
- Action/trigger catalogs (14 actions, 15 triggers)
- System funnel stages
- Meta-prompts for auto-generation

---

## References

- Plan file: `polished-foraging-lobster.md`
- Migration: `NEW/migrations/007_domains_context_extraction.sql`
- Existing workflows: `NEW/workflows/AI Contour/`
