# ELDOLEADO: Complete Architecture

> Unified architecture document
> Date: 2025-12-11
> Version: 3.0

---

## 1. CORE PRINCIPLES

### 1.1 Mission

**Eldoleado — CRM with unified client.**

All communication channels (telegram, whatsapp, vk, avito, calls, visits) are tied to one client. All touchpoints are tracked in a unified context.

### 1.2 Key Principles

| Principle | Description |
|-----------|-------------|
| **People communicate, machine keeps records** | Humans — natural communication, machine — routine and tables |
| **Everything is configurable** | Hardcode = field in relational table via ID. No enums in code. |
| **Dialog-centricity** | Dialog is the central entity, not a ticket |
| **Client-centricity** | One client = one profile, regardless of channel |
| **Graph = source of truth** | Neo4j stores relationships, PostgreSQL — cache for fast reads |
| **Prompt-in-Request** | Prompts passed in request, not hardcoded |
| **Stick-Carrot-Stick** | Rules → AI-freedom → Validation |

### 1.3 Architecture Simplification

```
❌ Old approach:
   Client → Tickets → Devices → Problems (50 tables)

✅ New approach:
   Client → Dialogs (with context inside) (minimum tables)
```

### 1.4 MVP Definition

**MVP ≠ minimal prototype. MVP = production-ready vertical.**

Must prove that architecture scales to any vertical/domain. Switching verticals = changing config records, NOT code.

---

## 2. INHERITANCE MODEL

```
Level 1: Global Directories (shared by all)
              ↓
Level 2: Domain Directories (per domain)
              ↓
Level 3: Vertical Directories (per vertical)
              ↓
Level 4: Tenant Settings (override for Pro tier)
```

**Rule:**
- Domain inherits global directories
- Vertical inherits domain + adds own
- Tenant inherits vertical + overrides what needed (Pro tier only)

---

## 3. DIRECTORY STRUCTURE

### Level 1: Global Directories

Shared across all domains, verticals, tenants.

| Table | Purpose | Examples |
|-------|---------|----------|
| `channels` | Communication channels | telegram, whatsapp, vk, avito, max, form, phone |
| `message_types` | Types of messages | text, voice, image, file, location |
| `directions` | Message direction | in, out |
| `operator_types` | Who responds | human, ai |

### Level 2: Domain Directories

Per domain (Electronics, Auto, Food, etc.)

| Table | Purpose | Examples |
|-------|---------|----------|
| `domains` | Business domains | electronics, auto, food, beauty |
| `verticals` | Verticals within domain | phone_repair, buy_sell, accessories |
| `funnel_stages` | Microfunnel stages | greeting, device, problem, price, appointment, closing |
| `entity_types` | What we track | device, vehicle, appliance |
| `extraction_fields` | What AI extracts | brand, model, year, color, mileage |

### Level 3: Vertical Directories

Per vertical (Phone Repair, Buy/Sell, etc.)

| Table | Purpose | Examples |
|-------|---------|----------|
| `symptom_types` | Client-reported symptoms | won't charge, cracked screen, no sound |
| `diagnosis_types` | Technician findings | dead battery, broken connector, water damage |
| `repair_actions` | What was done | replaced, repaired, cleaned |
| `problem_categories` | Problem groupings | display, battery, charging, sound |
| `price_templates` | Price ranges by category | display replacement: 3000-8000₽ |

### Level 4: Tenant Settings (Pro Tier)

Defaults from vertical, overridable by Pro tier tenants.

---

## 4. SETTINGS TABLES

### 4.1 Prompts Table

AI prompts per funnel stage.

```sql
prompts (
    id UUID PK,
    vertical_id INT FK,          -- which vertical
    funnel_stage_id INT FK,      -- which stage
    tenant_id UUID FK NULL,      -- NULL = vertical default, filled = tenant override (Pro)

    prompt_text TEXT,            -- the prompt itself
    system_context TEXT,         -- system context
    extraction_schema JSONB,     -- what to extract at this stage

    is_default BOOLEAN,          -- TRUE = system default for vertical
    is_active BOOLEAN,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
)
```

**Logic:**
- Free/Basic: uses `tenant_id IS NULL` (defaults only)
- Pro: can INSERT own record with `tenant_id` (overrides default)

**Query to get prompt:**
```sql
SELECT * FROM prompts
WHERE vertical_id = :vertical
  AND funnel_stage_id = :stage
  AND (tenant_id = :tenant OR tenant_id IS NULL)
ORDER BY tenant_id NULLS LAST
LIMIT 1
```

### 4.2 AI Settings Table (Stick-Carrot-Stick)

AI behavior control.

```sql
ai_settings (
    id UUID PK,
    vertical_id INT FK,
    tenant_id UUID FK NULL,      -- NULL = vertical default

    -- Stick (before AI)
    pre_rules JSONB,             -- hard rules BEFORE AI: what must ask, what forbidden
    required_extractions TEXT[], -- MUST extract: ['device_brand', 'device_model']

    -- Carrot (AI freedom)
    freedom_level INT,           -- 0-100: 0 = script only, 100 = full freedom
    allowed_tools TEXT[],        -- which tools AI can call

    -- Stick (after AI)
    post_rules JSONB,            -- response validation: length, tone, forbidden words
    must_include TEXT[],         -- MUST include in response

    is_active BOOLEAN,
    created_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ
)
```

**freedom_level examples:**

| Level | Behavior |
|-------|----------|
| 0 | Strict script, AI only substitutes data |
| 30 | AI can rephrase, but follows structure |
| 70 | AI free in wording, but follows rules |
| 100 | Full freedom (dangerous, testing only) |

---

## 5. VERTICALS

### 5.1 Concept

**Vertical ≠ Message type.** Vertical = business model.

| Vertical | Model | Focus | Status |
|----------|-------|-------|--------|
| **phone_repair** | Service | Return rate, LTV | ✅ MVP |
| **buy_sell** | Transactional | Conversion, fast cycle | ⏳ Later |

### 5.2 Proof of Concept: Two Verticals

**Vertical 1: Phone Repair**
```
Domain: Electronics
Entity: Device (brand, model, color)
Symptoms: won't charge, cracked screen, no sound
Diagnoses: dead battery, broken connector
Repairs: replaced display, replaced battery
```

**Vertical 2: Auto Repair**
```
Domain: Auto
Entity: Vehicle (make, model, year, mileage)
Symptoms: won't start, strange noise, overheating
Diagnoses: dead starter, worn brakes
Repairs: replaced starter, changed oil
```

**Switching = changing config records, NOT code.**

---

## 6. SYSTEM CONTOURS AND BLOCKS

### 6.1 Contour Architecture

System is divided into **contours** — logical groups of blocks with clear boundaries.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CLIENT                                          │
│                       (Telegram, WhatsApp, ...)                              │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  CHANNEL CONTOUR                                                             │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │                         MCP CHANNELS (IN)                            │    │
│  │                    Receive, normalize, transcribe                    │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  INPUT CONTOUR                                                               │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐                       │
│  │   Tenant     │→ │    Queue     │→ │    Batch     │                       │
│  │   Resolver   │  │   Processor  │  │   Debouncer  │                       │
│  └──────────────┘  └──────────────┘  └──────────────┘                       │
│         +tenant_id, +domain_id              +merged text                     │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  CLIENT CONTOUR                                                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐                       │
│  │   Client     │→ │   Dialog     │→ │  (Future:    │                       │
│  │   Resolver   │  │   Resolver   │  │   Merger)    │                       │
│  └──────────────┘  └──────────────┘  └──────────────┘                       │
│         +client_id        +dialog_id        channel merge (later)            │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  CORE CONTOUR                                                                │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐    │
│  │   Context    │→ │   Request    │→ │ Orchestrator │→ │   Dialog     │    │
│  │   Builder    │  │   Builder    │  │              │  │   Engine     │    │
│  └──────────────┘  └──────────────┘  └──────────────┘  └──────────────┘    │
│    +context          +ai_request        calls tools       saves to DB        │
│    +vertical_id                                           +sync to Graph     │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  OUTPUT CONTOUR                                                              │
│  ┌──────────────┐  ┌──────────────┐                                         │
│  │   Response   │→ │ MCP CHANNELS │                                         │
│  │   Builder    │  │    (OUT)     │                                         │
│  └──────────────┘  └──────────────┘                                         │
│    format for channel    send to messenger                                   │
└─────────────────────────────────────────────────────────────────────────────┘
```

### 6.2 Contour Summary

| # | Contour | Blocks | Responsibility |
|---|---------|--------|----------------|
| 1 | **Channel** | MCP In, MCP Out | Receive/send, normalize, transcribe voice |
| 2 | **Input** | Tenant Resolver, Queue, Batcher | Determine tenant, buffer, merge messages |
| 3 | **Client** | Client Resolver, Dialog Resolver | Find/create client & dialog |
| 4 | **Core** | Context, Request, Orchestrator, Dialog Engine | Business logic, AI, vertical detection |
| 5 | **Output** | Response Builder, MCP Out | Format and send response |

### 6.3 Client Contour Details

**MVP Scope:**
- Client Resolver — find/create client by external_id
- Dialog Resolver — find/create active dialog

**Future Scope (not MVP):**
- Channel Merger — merge clients across channels (Telegram + WhatsApp = one client)
- Touchpoint Analytics — track client journey

**Why separate contour:**
- Single client across all channels is core concept
- Merging logic is complex, deserves isolation
- Can evolve independently from Core

### 6.4 Core Contour Blocks

| # | Block | What it does | Characteristics |
|---|-------|--------------|-----------------|
| 1 | **Context Builder** | Gather context from PostgreSQL + Neo4j, determine focus | Stateless |
| 2 | **Request Builder** | Rules + AI + validation ("Stick-Carrot-Stick") | Stateless |
| 3 | **Orchestrator** | Blind executor, calls tools | Stateless, AI-driven |
| 4 | **Dialog Engine** | Save to DB, sync to Neo4j, set vertical_id | Source of truth |

**Vertical Detection (in Core):**
- New dialog → AI analyzes first message → suggests vertical
- Existing dialog → use current vertical or detect change
- Multi-vertical → same dialog can have Issues in different verticals

### 6.5 Key Principles

**Orchestrator — BLIND:**
- DOESN'T KNOW about other tools (sees only those passed in request)
- DOESN'T KNOW business logic (only executes)
- All knowledge — in request

**Universal Tools — EVEN MORE BLIND:**
- Knows only its prompt
- One worker processes ALL verticals (difference in prompt)

**Contour Boundaries:**
- Each contour has clear input/output contract
- Contours can be replaced/scaled independently
- No business logic leaks between contours

---

## 7. THREE LAYERS OF TRUTH

### 7.1 Chain

```
Intake (container of client's words)
    │
    ├── Symptom 1 ("doesn't charge")
    ├── Symptom 2 ("screen cracked")
    └── Symptom 3 ("battery swollen")
           │
           ▼
    Diagnosis (technician found)
           │
           ▼
    Repair (what was done)
```

### 7.2 Definitions

| Entity | Source | Description |
|--------|--------|-------------|
| **Intake** | Dialog | Container — everything client said at initial contact |
| **Symptom** | AI extraction | Extracted symptom from client's words |
| **Diagnosis** | Technician | What was actually found during diagnostics |
| **Repair** | Technician | What was done |

### 7.3 Why Three Layers

| Problem | Solution |
|---------|----------|
| Client says "screen doesn't work," but problem is backlight | Separate symptom and diagnosis |
| Reception expected one thing, technician found another | Record discrepancy → learn |
| Follow-up asks "how's the new display?", but display wasn't replaced | Rely on REPAIR, not on SYMPTOM |
| Price statistics by symptom are wrong | Calculate by actual repairs |

---

## 8. DIALOG MICROFUNNEL

### 8.1 Concept

Instead of "contact type" (Repair, Purchase, Consultation) — **microfunnel**:

```
👋 Greeting → 📱 Device → 🔧 Problem → 💰 Price → 📅 Appointment → 👋 Closing
```

**Not 7 sales stages over a month. But 7 micro-stages in 15 minutes.**

### 8.2 Standard Stages (phone_repair vertical)

| # | code | name | ai_goal |
|---|------|------|---------|
| 1 | greeting | Greeting | Say hello, learn name |
| 2 | device | Device | Brand, model, color, owner |
| 3 | problem | Problem | Symptoms, when it started |
| 4 | price | Price | State the cost |
| 5 | appointment | Appointment | Date/time of visit |
| 6 | closing | Closing | Confirm, say goodbye |

### 8.3 Stage Structure

```json
{
  "code": "problem",
  "name": "Problem Discussion",
  "position": 3,
  "entry_conditions": {
    "required_fields": ["device_model"]
  },
  "ai_goal": "Collect symptoms: what doesn't work, when it started",
  "ai_prompt": "Client told about device {device}. Clarify the problem...",
  "auto_actions": {},
  "exit_conditions": {
    "any_of": ["symptom_text", "problem_category"]
  }
}
```

---

## 9. FOCUS SCORE

### 9.1 Concept

**focus_score** — percentage of dialog context completeness (0-100%).

```
15% — only name, unclear what they want
45% — have device, no problem
80% — everything clear, clarifying details
100% — full context, ready to work
```

### 9.2 Why

- 👁 Quick orientation — see immediately where gaps are
- ⚡ Resource saving — at 100% don't make extra LLM requests
- 🎯 Don't lose incomplete — don't let client leave until we collect info

### 9.3 Computed, Not Stored

1. **Graph = source of truth** — always current data
2. **15-20ms** — fast enough for each request
3. **No desync** — no need to invalidate cache

---

## 10. PostgreSQL vs Neo4j

### 10.1 Formula

```
PostgreSQL (JSONB) = cache (fast to read)
Neo4j (graph) = source of truth (query on changes)
```

### 10.2 What to Store Where

**PostgreSQL (required):**
- Authorization, operator sessions
- Clients (for search, API)
- Dialogs (status, current stage)
- Messages (full history)
- Tenant settings
- Deviations (log for analysis)

**Neo4j (required):**
- Client (for relationships)
- Device, Issue, Intake, Symptom, Diagnosis, Repair
- All relationships between entities
- DefectSign, QuestionTree, SignPattern
- Type references

---

## 11. DIAGNOSTIC ENGINE

### 11.1 Concept

Self-learning expert diagnostic system:
- Asks clarifying questions based on signs/defects
- Predicts diagnosis by combination of signs
- Learns from discrepancies between expectation and fact
- Accumulates expertise in knowledge graph

### 11.2 Entities

| Entity | Purpose | MVP |
|--------|---------|-----|
| **DefectSign** | Sign with question ("Does wireless work?") | ✅ |
| **SignCategory** | Sign category (charging, display, sound) | ✅ |
| **QuestionTree** | JSON question tree for adaptive dialog | ✅ |
| **SignPattern** | Sign combination → diagnosis with probability | ⏳ Later |
| **Deviation** | Intake vs Repair discrepancy | ⏳ Infrastructure |

---

## 12. PRO TIER FEATURES

What Pro tenants can customize:

| Feature | Table | Description |
|---------|-------|-------------|
| **Custom prompts** | `prompts` | Override default prompts per funnel stage |
| **Stick-Carrot-Stick** | `ai_settings` | Control AI behavior: pre-rules, freedom, post-rules |

---

## 13. SERVERS AND SERVICES

### MCP Services Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  MCP ADAPTERS (channel-specific)                                             │
│  ┌───────────┐ ┌───────────┐ ┌───────────┐ ┌───────────┐ ┌───────────┐     │
│  │ mcp-tg    │ │ mcp-wa    │ │ mcp-avito │ │ mcp-vk    │ │ mcp-max   │     │
│  └─────┬─────┘ └─────┬─────┘ └─────┬─────┘ └─────┬─────┘ └─────┬─────┘     │
│        └─────────────┴───────┬─────┴─────────────┴─────────────┘           │
└──────────────────────────────│──────────────────────────────────────────────┘
                               │ POST /ingest
                               ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  MCP-INPUT (Input + Client Contours)                                         │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   Ingest    │→ │   Tenant    │→ │   Queue +   │→ │   Client    │        │
│  │   API       │  │   Resolver  │  │   Debounce  │  │   Resolver  │        │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘        │
│                                                            │                │
│  Stack: Python FastAPI + Redis + PostgreSQL                │                │
└────────────────────────────────────────────────────────────│────────────────┘
                                                             │ POST to Core
                                                             ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  CORE (n8n for now, MCP later)                                               │
│  Context Builder → Request Builder → Orchestrator → Dialog Engine           │
└─────────────────────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│  MCP ADAPTERS (OUT)                                                          │
│  Send responses back to channels                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

### RU Server (45.144.177.128)

| Service | Port | Status |
|---------|------|--------|
| mcp-avito | 8765 | ✅ Running |
| mcp-vk | 8767 | ✅ Running |
| mcp-max | 8768 | ✅ Running |
| mcp-form | 8770 | ✅ Running |
| **mcp-input** | 8771 | 🚧 In development |
| api-android | 8780 | ✅ Running |
| Neo4j | 7474/7687 | ✅ Running |
| Redis | 6379 | ✅ Running |

### Finnish Server (217.145.79.27)

| Service | Port | Status |
|---------|------|--------|
| mcp-telegram | 8767 | ✅ Running |
| mcp-whatsapp | 8766 | ✅ Running |

### n8n Server (185.221.214.83)

| Service | Port | Status |
|---------|------|--------|
| n8n | 5678 | ✅ Running |
| PostgreSQL | 6544 | ✅ Running |
| Redis | 6379 | ✅ Running |

### MCP-INPUT Service Details

**Location:** RU Server (45.144.177.128:8771)

**Endpoints:**
| Endpoint | Method | Purpose |
|----------|--------|---------|
| `/ingest` | POST | Receive messages from adapters |
| `/resolve-client` | POST | Find/create client |
| `/resolve-dialog` | POST | Find/create dialog |
| `/health` | GET | Health check |

**Dependencies:**
- Redis (queue, debounce timers, locks)
- PostgreSQL (tenant, client, dialog)
- Neo4j (client sync via Graph Query Tool)

**Why MCP instead of n8n:**
| n8n Problem | MCP Solution |
|-------------|--------------|
| No parallel processing | Async workers, horizontal scale |
| Wait node blocks | Async debounce, Redis timers |
| 10 Debouncer copies = hack | Single service, N workers |
| Hard to debug queues | Clean code, logs, metrics |

---

## 14. GLOSSARY

| Term | Definition |
|------|------------|
| **Vertical** | Business model (phone_repair, buy_sell) |
| **Domain** | Business area (electronics, auto, food) |
| **Client** | Person, unified profile regardless of channel |
| **Dialog** | Communication unit with client |
| **Device** | Client's device/object |
| **Issue** | Contact case (container) |
| **Intake** | Client's words (symptom container) |
| **Symptom** | Extracted symptom |
| **Diagnosis** | What technician found |
| **Repair** | What was done |
| **Touchpoint** | Any client interaction with business |
| **Microfunnel** | Sequence of dialog stages |
| **focus_score** | Context completeness percentage (0-100%) |
| **freedom_level** | AI freedom level (0-100) |
| **Stick-Carrot-Stick** | Pre-rules → AI freedom → Post-validation |
| **Prompt-in-Request** | Prompts in request, not hardcoded |

---

## 15. PostgreSQL SCHEMA

### 15.1 Level 1: Global Directories

```sql
-- Communication channels
CREATE TABLE channels (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,   -- telegram, whatsapp, vk, avito, max, form, phone
    name VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT true
);

-- Message types
CREATE TABLE message_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,   -- text, voice, image, file, location
    name VARCHAR(50) NOT NULL
);

-- Message direction
CREATE TABLE directions (
    id SERIAL PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,   -- in, out
    name VARCHAR(20) NOT NULL
);

-- Operator types
CREATE TABLE operator_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,   -- human, ai
    name VARCHAR(50) NOT NULL
);
```

### 15.2 Level 2: Domain Directories

```sql
-- Business domains
CREATE TABLE domains (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,   -- electronics, auto, food, beauty
    name VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT true
);

-- Verticals within domain
CREATE TABLE verticals (
    id SERIAL PRIMARY KEY,
    domain_id INT NOT NULL REFERENCES domains(id),
    code VARCHAR(50) UNIQUE NOT NULL,   -- phone_repair, buy_sell
    name VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT true
);

-- Microfunnel stages (per vertical)
CREATE TABLE funnel_stages (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES verticals(id),
    code VARCHAR(30) NOT NULL,          -- greeting, device, problem, price, appointment, closing
    name VARCHAR(100) NOT NULL,
    position INT NOT NULL,              -- order in funnel
    entry_conditions JSONB,             -- {"required_fields": ["device_model"]}
    exit_conditions JSONB,              -- {"any_of": ["symptom_text", "problem_category"]}
    ai_goal TEXT,                       -- what AI should achieve at this stage
    is_active BOOLEAN DEFAULT true,
    UNIQUE(vertical_id, code)
);

-- Entity types (what we track per vertical)
CREATE TABLE entity_types (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES verticals(id),
    code VARCHAR(30) NOT NULL,          -- device, vehicle, appliance
    name VARCHAR(100) NOT NULL,
    UNIQUE(vertical_id, code)
);

-- Extraction fields (what AI extracts per entity type)
CREATE TABLE extraction_fields (
    id SERIAL PRIMARY KEY,
    entity_type_id INT NOT NULL REFERENCES entity_types(id),
    code VARCHAR(30) NOT NULL,          -- brand, model, year, color, mileage
    name VARCHAR(100) NOT NULL,
    data_type VARCHAR(20) NOT NULL,     -- string, number, date, enum
    is_required BOOLEAN DEFAULT false,
    enum_values JSONB,                  -- for enum type: ["Apple", "Samsung", "Xiaomi"]
    UNIQUE(entity_type_id, code)
);
```

### 15.3 Level 3: Vertical Directories

```sql
-- Symptom types (per vertical)
CREATE TABLE symptom_types (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES verticals(id),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,         -- "won't charge", "cracked screen"
    category_id INT REFERENCES problem_categories(id),
    is_active BOOLEAN DEFAULT true,
    UNIQUE(vertical_id, code)
);

-- Diagnosis types (per vertical)
CREATE TABLE diagnosis_types (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES verticals(id),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,         -- "dead battery", "broken connector"
    category_id INT REFERENCES problem_categories(id),
    is_active BOOLEAN DEFAULT true,
    UNIQUE(vertical_id, code)
);

-- Repair actions (per vertical)
CREATE TABLE repair_actions (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES verticals(id),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(200) NOT NULL,         -- "replaced", "repaired", "cleaned"
    is_active BOOLEAN DEFAULT true,
    UNIQUE(vertical_id, code)
);

-- Problem categories (per vertical)
CREATE TABLE problem_categories (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES verticals(id),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,         -- "display", "battery", "charging"
    parent_id INT REFERENCES problem_categories(id),  -- for hierarchy
    is_active BOOLEAN DEFAULT true,
    UNIQUE(vertical_id, code)
);

-- Price templates (per vertical)
CREATE TABLE price_templates (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES verticals(id),
    category_id INT REFERENCES problem_categories(id),
    name VARCHAR(200) NOT NULL,
    price_min INT,
    price_max INT,
    currency VARCHAR(3) DEFAULT 'RUB',
    is_active BOOLEAN DEFAULT true
);
```

### 15.4 Level 4: Tenant Settings

```sql
-- AI prompts per funnel stage
CREATE TABLE prompts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vertical_id INT NOT NULL REFERENCES verticals(id),
    funnel_stage_id INT NOT NULL REFERENCES funnel_stages(id),
    tenant_id UUID REFERENCES tenants(id),  -- NULL = vertical default, filled = Pro override

    prompt_text TEXT NOT NULL,
    system_context TEXT,
    extraction_schema JSONB,            -- what to extract at this stage

    is_default BOOLEAN DEFAULT false,   -- TRUE = system default for vertical
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- AI behavior settings (Stick-Carrot-Stick)
CREATE TABLE ai_settings (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    vertical_id INT NOT NULL REFERENCES verticals(id),
    tenant_id UUID REFERENCES tenants(id),  -- NULL = vertical default

    -- Stick (before AI)
    pre_rules JSONB,                    -- hard rules BEFORE AI
    required_extractions TEXT[],        -- MUST extract: ['device_brand', 'device_model']

    -- Carrot (AI freedom)
    freedom_level INT DEFAULT 50,       -- 0-100
    allowed_tools TEXT[],               -- which tools AI can call

    -- Stick (after AI)
    post_rules JSONB,                   -- response validation
    must_include TEXT[],                -- MUST include in response

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);
```

### 15.5 Core Tables

```sql
-- Tenants
CREATE TABLE tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    domain_id INT NOT NULL REFERENCES domains(id),
    name VARCHAR(255) NOT NULL,
    settings JSONB,                     -- general settings
    subscription_tier VARCHAR(20) DEFAULT 'free',  -- free, basic, pro
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Tenant-vertical relationship
CREATE TABLE tenant_verticals (
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    vertical_id INT NOT NULL REFERENCES verticals(id),
    is_active BOOLEAN DEFAULT true,
    PRIMARY KEY (tenant_id, vertical_id)
);

-- Operators
CREATE TABLE operators (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    type_id INT NOT NULL REFERENCES operator_types(id),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    username VARCHAR(100),
    password_hash VARCHAR(255),
    ai_model VARCHAR(50),               -- NULL for human, 'claude-3' for ai
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Operator sessions
CREATE TABLE operator_sessions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    operator_id UUID NOT NULL REFERENCES operators(id),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    session_token VARCHAR(255) UNIQUE NOT NULL,
    device_type VARCHAR(20),            -- mobile, web
    device_id VARCHAR(255),
    device_info JSONB,
    fcm_token VARCHAR(255),             -- for push notifications
    created_at TIMESTAMPTZ DEFAULT NOW(),
    last_active_at TIMESTAMPTZ DEFAULT NOW()
);

-- Clients
CREATE TABLE clients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    name VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),
    first_seen_at TIMESTAMPTZ DEFAULT NOW(),
    last_seen_at TIMESTAMPTZ DEFAULT NOW(),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Client channel identifiers
CREATE TABLE client_channels (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    client_id UUID NOT NULL REFERENCES clients(id),
    channel_id INT NOT NULL REFERENCES channels(id),
    external_id VARCHAR(100) NOT NULL,   -- chat_id in Telegram, phone in WhatsApp
    external_username VARCHAR(100),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(client_id, channel_id, external_id)
);

CREATE UNIQUE INDEX idx_client_channels_lookup ON client_channels(channel_id, external_id);

-- Client merges (for future, not used in MVP)
CREATE TABLE client_merges (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    master_client_id UUID NOT NULL REFERENCES clients(id),
    merged_client_id UUID NOT NULL REFERENCES clients(id),
    reason VARCHAR(50),              -- 'phone_match', 'manual', 'ai_suggested'
    merged_at TIMESTAMPTZ DEFAULT NOW(),
    merged_by UUID REFERENCES operators(id)
);

-- Dialogs
CREATE TABLE dialogs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    client_id UUID NOT NULL REFERENCES clients(id),
    channel_id INT NOT NULL REFERENCES channels(id),
    vertical_id INT REFERENCES verticals(id),
    external_chat_id VARCHAR(100),
    current_stage_id INT REFERENCES funnel_stages(id),
    stage_entered_at TIMESTAMPTZ,
    status VARCHAR(20) DEFAULT 'active',  -- active, waiting, closed, escalated
    assigned_operator_id UUID REFERENCES operators(id),
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Messages
CREATE TABLE messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    dialog_id UUID NOT NULL REFERENCES dialogs(id),
    client_id UUID NOT NULL REFERENCES clients(id),
    direction_id INT NOT NULL REFERENCES directions(id),
    message_type_id INT NOT NULL REFERENCES message_types(id),
    actor_type VARCHAR(20),             -- client, operator, ai
    actor_id UUID,                      -- operator_id if not client
    content TEXT,
    has_media BOOLEAN DEFAULT false,
    media_type VARCHAR(50),
    media_url TEXT,
    changed_graph BOOLEAN DEFAULT false, -- TRUE if message created/updated graph entities
    external_message_id VARCHAR(100),   -- original message_id from channel (for idempotency)
    trace_id VARCHAR(100),              -- end-to-end tracing
    timestamp TIMESTAMPTZ DEFAULT NOW(),
    created_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_messages_external ON messages(tenant_id, external_message_id);
CREATE INDEX idx_messages_trace ON messages(trace_id);

-- Channel accounts (for tenant)
CREATE TABLE channel_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    channel_id INT NOT NULL REFERENCES channels(id),
    account_id VARCHAR(100) NOT NULL,    -- bot_token, phone, group_id
    account_name VARCHAR(255),
    webhook_hash VARCHAR(32) UNIQUE,     -- for Tenant Resolver: /webhook/{hash} → tenant_id
    credentials JSONB,                   -- {token, api_key, secret...}
    mcp_base_url VARCHAR(255),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(tenant_id, channel_id, account_id)
);
```

### 15.6 Indexes

```sql
-- Clients: search by identifiers
CREATE INDEX idx_clients_phone ON clients(tenant_id, phone);
CREATE INDEX idx_clients_tenant ON clients(tenant_id);

-- Client channels: find client by external_id
CREATE INDEX idx_client_channels_external ON client_channels(channel_id, external_id);

-- Dialogs: list for operator
CREATE INDEX idx_dialogs_tenant_status ON dialogs(tenant_id, status, updated_at DESC);
CREATE INDEX idx_dialogs_client ON dialogs(client_id);
CREATE INDEX idx_dialogs_operator ON dialogs(assigned_operator_id);

-- Messages: history
CREATE INDEX idx_messages_dialog ON messages(dialog_id, timestamp DESC);
CREATE INDEX idx_messages_client ON messages(client_id, timestamp DESC);

-- Sessions: authorization
CREATE UNIQUE INDEX idx_sessions_token ON operator_sessions(session_token);

-- Channel accounts: tenant resolver
CREATE INDEX idx_channel_accounts_account ON channel_accounts(account_id);
CREATE INDEX idx_channel_accounts_webhook ON channel_accounts(webhook_hash);

-- Prompts: get prompt for stage
CREATE INDEX idx_prompts_stage ON prompts(vertical_id, funnel_stage_id, tenant_id);

-- AI settings: get settings
CREATE INDEX idx_ai_settings_vertical ON ai_settings(vertical_id, tenant_id);
```

---

## 16. Neo4j SCHEMA

### 16.1 Main Nodes (9 nodes)

| Node | Purpose | Key Properties |
|------|---------|----------------|
| **Client** | Client in graph | pg_id, tenant_id, name, phone |
| **Device** | Device/object | pg_id, tenant_id, brand, model, color, entity_type |
| **Issue** | Contact case | pg_id, tenant_id, vertical_id, dialog_id, status |
| **Intake** | Client's words | pg_id, raw_text, created_at |
| **Symptom** | Extracted symptom | pg_id, symptom_type_id, text |
| **Diagnosis** | Technician finding | pg_id, diagnosis_type_id, text, confirmed_by |
| **Repair** | Completed repair | pg_id, repair_action_id, text, cost |
| **Message** | Graph-changing message | pg_id, direction, created_at |
| **Trait** | Client attribute | type, value, confidence, source |

**Notes:**
- `tenant_id` — on all main nodes for multi-tenant isolation
- `vertical_id` — on Issue for multi-vertical support
- `Message` — stored ONLY when it changes the graph (creates Device, Symptom, etc.)
- `Trait` — attributes about client (vip, knows_prices, prefers_whatsapp, etc.)

### 16.2 Reference Nodes (per vertical)

| Node | Purpose | Properties |
|------|---------|------------|
| **SymptomType** | Symptom type reference | pg_id, vertical_id, code, name |
| **DiagnosisType** | Diagnosis type reference | pg_id, vertical_id, code, name |
| **RepairAction** | Repair type reference | pg_id, vertical_id, code, name |
| **ProblemCategory** | Problem category reference | pg_id, vertical_id, code, name |

**Note:** Reference nodes have `vertical_id` because each vertical has its own set of symptoms/diagnoses/repairs.

### 16.3 Diagnostic Engine Nodes

| Node | Purpose |
|------|---------|
| **DefectSign** | Sign with question |
| **SignCategory** | Category of signs |
| **QuestionTree** | JSON question tree |
| **SignPattern** | Sign combination → diagnosis |

### 16.4 Relationships

```cypher
// Client relationships
(Client)-[:OWNS]->(Device)
(Client)-[:BROUGHT]->(Device)           // someone else's device
(Client)-[:FAMILY {type: "spouse"|"parent"|"child"|"sibling"}]->(Client)
(Client)-[:REFERRED]->(Client)          // referral chain
(Client)-[:HAS_TRAIT]->(Trait)

// Device relationships
(Device)-[:HAS_ISSUE]->(Issue)

// Issue relationships (per vertical)
(Issue)-[:HAS_INTAKE]->(Intake)
(Issue)-[:HAS_DIAGNOSIS]->(Diagnosis)
(Issue)-[:HAS_REPAIR]->(Repair)
(Issue)-[:PROBLEM_CATEGORY]->(ProblemCategory)

// Intake relationships
(Intake)-[:HAS_SYMPTOM]->(Symptom)

// Type references
(Symptom)-[:SYMPTOM_TYPE]->(SymptomType)
(Diagnosis)-[:DIAGNOSIS_TYPE]->(DiagnosisType)
(Repair)-[:REPAIR_ACTION]->(RepairAction)

// Message relationships (graph-changing only)
(Message)-[:FROM]->(Client)
(Message)-[:CREATED]->(Device | Issue | Symptom | Trait)

// Diagnostic engine
(DefectSign)-[:INDICATES]->(DiagnosisType)
(SignPattern)-[:PREDICTS]->(DiagnosisType)
(DefectSign)-[:IN_CATEGORY]->(SignCategory)
```

### 16.5 Multi-Vertical Support

One client can have issues in multiple verticals:

```
Client "Ivan"
  └── Device "iPhone 14"
        ├── Issue (vertical: phone_repair) - screen cracked
        └── Issue (vertical: buy_sell) - wants to sell

Same client, same device, different verticals.
Each Issue has its own vertical_id.
```

**Cypher for multi-vertical:**
```cypher
// All verticals client interacted with
MATCH (c:Client {pg_id: $client_id})-[:OWNS|BROUGHT]->(d:Device)-[:HAS_ISSUE]->(i:Issue)
RETURN DISTINCT i.vertical_id

// History for specific vertical
MATCH (c:Client {pg_id: $client_id})-[:OWNS|BROUGHT]->(d:Device)-[:HAS_ISSUE]->(i:Issue)
WHERE i.vertical_id = $vertical_id
RETURN d, i ORDER BY i.created_at DESC
```

### 16.6 Constraints and Indexes

```cypher
// Uniqueness constraints
CREATE CONSTRAINT client_pg_id IF NOT EXISTS FOR (c:Client) REQUIRE c.pg_id IS UNIQUE;
CREATE CONSTRAINT device_pg_id IF NOT EXISTS FOR (d:Device) REQUIRE d.pg_id IS UNIQUE;
CREATE CONSTRAINT issue_pg_id IF NOT EXISTS FOR (i:Issue) REQUIRE i.pg_id IS UNIQUE;
CREATE CONSTRAINT message_pg_id IF NOT EXISTS FOR (m:Message) REQUIRE m.pg_id IS UNIQUE;
CREATE CONSTRAINT symptomtype_pg_id IF NOT EXISTS FOR (st:SymptomType) REQUIRE st.pg_id IS UNIQUE;
CREATE CONSTRAINT diagnosistype_pg_id IF NOT EXISTS FOR (dt:DiagnosisType) REQUIRE dt.pg_id IS UNIQUE;

// Multi-tenant indexes
CREATE INDEX client_tenant IF NOT EXISTS FOR (c:Client) ON (c.tenant_id);
CREATE INDEX device_tenant IF NOT EXISTS FOR (d:Device) ON (d.tenant_id);
CREATE INDEX issue_tenant IF NOT EXISTS FOR (i:Issue) ON (i.tenant_id);

// Multi-vertical indexes
CREATE INDEX issue_vertical IF NOT EXISTS FOR (i:Issue) ON (i.vertical_id);
CREATE INDEX symptomtype_vertical IF NOT EXISTS FOR (st:SymptomType) ON (st.vertical_id);

// Search indexes
CREATE INDEX device_brand_model IF NOT EXISTS FOR (d:Device) ON (d.brand, d.model);
CREATE INDEX issue_status IF NOT EXISTS FOR (i:Issue) ON (i.status);
CREATE INDEX trait_type IF NOT EXISTS FOR (t:Trait) ON (t.type);
```

### 16.7 Main Cypher Queries

**1. Get full client context:**
```cypher
MATCH (c:Client {pg_id: $client_id})
OPTIONAL MATCH (c)-[:OWNS|BROUGHT]->(d:Device)
OPTIONAL MATCH (d)-[:HAS_ISSUE]->(i:Issue)
OPTIONAL MATCH (i)-[:HAS_INTAKE]->(intake)-[:HAS_SYMPTOM]->(s:Symptom)
OPTIONAL MATCH (i)-[:HAS_DIAGNOSIS]->(diag:Diagnosis)
OPTIONAL MATCH (i)-[:HAS_REPAIR]->(r:Repair)
OPTIONAL MATCH (c)-[:HAS_TRAIT]->(t:Trait)
RETURN c, collect(DISTINCT d) as devices,
       collect(DISTINCT i) as issues,
       collect(DISTINCT s) as symptoms,
       collect(DISTINCT diag) as diagnoses,
       collect(DISTINCT r) as repairs,
       collect(DISTINCT t) as traits
```

**2. Find similar cases (for AI suggestions):**
```cypher
MATCH (i:Issue)-[:PROBLEM_CATEGORY]->(cat:ProblemCategory {code: $category})
MATCH (i)-[:HAS_DIAGNOSIS]->(d:Diagnosis)
WHERE i.vertical_id = $vertical_id
RETURN cat.code, d.text, count(*) as frequency
ORDER BY frequency DESC
LIMIT 5
```

**3. Device repair history:**
```cypher
MATCH (d:Device {pg_id: $device_id})-[:HAS_ISSUE]->(i:Issue)
OPTIONAL MATCH (i)-[:HAS_DIAGNOSIS]->(diag)
OPTIONAL MATCH (i)-[:HAS_REPAIR]->(r)
RETURN i, collect(diag) as diagnoses, collect(r) as repairs
ORDER BY i.created_at DESC
```

**4. Client network (family, referrals):**
```cypher
MATCH (c:Client {pg_id: $client_id})
OPTIONAL MATCH (c)-[fam:FAMILY]->(family:Client)
OPTIONAL MATCH (c)-[:REFERRED]->(referred:Client)
OPTIONAL MATCH (referrer:Client)-[:REFERRED]->(c)
RETURN c,
       collect({client: family, relation: fam.type}) as family_members,
       collect(referred) as referred_clients,
       collect(referrer) as referred_by
```

**5. Client traits:**
```cypher
MATCH (c:Client {pg_id: $client_id})-[:HAS_TRAIT]->(t:Trait)
RETURN t.type, t.value, t.confidence, t.source
ORDER BY t.confidence DESC
```

**6. Symptom → Diagnosis statistics:**
```cypher
MATCH (s:Symptom)-[:SYMPTOM_TYPE]->(st:SymptomType {code: $symptom_code})
MATCH (s)<-[:HAS_SYMPTOM]-(intake)<-[:HAS_INTAKE]-(i:Issue)
MATCH (i)-[:HAS_DIAGNOSIS]->(d:Diagnosis)-[:DIAGNOSIS_TYPE]->(dt:DiagnosisType)
WHERE st.vertical_id = $vertical_id
RETURN st.name, dt.name, count(*) as occurrences
ORDER BY occurrences DESC
```

**7. Create device with owner:**
```cypher
MATCH (c:Client {pg_id: $client_id})
CREATE (d:Device {
  pg_id: $device_id,
  tenant_id: c.tenant_id,
  brand: $brand,
  model: $model,
  color: $color,
  created_at: datetime()
})
CREATE (c)-[:OWNS]->(d)
RETURN d
```

**8. Create issue for device:**
```cypher
MATCH (d:Device {pg_id: $device_id})
CREATE (i:Issue {
  pg_id: $issue_id,
  tenant_id: d.tenant_id,
  vertical_id: $vertical_id,
  dialog_id: $dialog_id,
  status: 'active',
  created_at: datetime()
})
CREATE (d)-[:HAS_ISSUE]->(i)
RETURN i
```

**9. Add trait to client:**
```cypher
MATCH (c:Client {pg_id: $client_id})
MERGE (t:Trait {type: $type, value: $value})
ON CREATE SET t.confidence = $confidence, t.source = $source, t.created_at = datetime()
MERGE (c)-[:HAS_TRAIT]->(t)
RETURN t
```

**10. Graph-changing message:**
```cypher
MATCH (c:Client {pg_id: $client_id})
CREATE (m:Message {
  pg_id: $message_id,
  direction: $direction,
  created_at: datetime()
})
CREATE (m)-[:FROM]->(c)
WITH m
MATCH (target {pg_id: $created_entity_id})
CREATE (m)-[:CREATED]->(target)
RETURN m
```

### 16.8 Graph Schema Storage

**Проблема:** Graph Query Tool слепой. Кто знает структуру графа?

**Решение:** Структура хранится в PostgreSQL, Worker читает и создаёт.

```
┌─────────────────────────────────────────────────────────────────┐
│              graph_* tables (PostgreSQL)                         │
│  - graph_node_types (какие узлы есть)                           │
│  - graph_node_properties (какие свойства)                       │
│  - graph_relationship_types (какие связи)                       │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│              Graph Schema Worker (Tool)                          │
│  1. Читает схему из PostgreSQL                                  │
│  2. Создаёт constraints/indexes в Neo4j                         │
│  3. Валидирует структуру                                        │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼
                           Neo4j
```

**Таблицы:**

```sql
-- Node types (какие узлы существуют)
CREATE TABLE graph_node_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,      -- 'Client', 'Device', 'Issue'
    name VARCHAR(100) NOT NULL,
    description TEXT,
    vertical_id INT REFERENCES verticals(id),  -- NULL = global (Client, Device)
    is_core BOOLEAN DEFAULT false,         -- true = обязательный для вертикали
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Node properties (какие свойства у узлов)
CREATE TABLE graph_node_properties (
    id SERIAL PRIMARY KEY,
    node_type_id INT NOT NULL REFERENCES graph_node_types(id),
    code VARCHAR(50) NOT NULL,             -- 'pg_id', 'brand', 'model'
    name VARCHAR(100) NOT NULL,
    data_type VARCHAR(20) NOT NULL,        -- 'uuid', 'string', 'int', 'float', 'datetime', 'boolean'
    is_required BOOLEAN DEFAULT false,
    is_unique BOOLEAN DEFAULT false,       -- CREATE CONSTRAINT
    is_indexed BOOLEAN DEFAULT false,      -- CREATE INDEX
    default_value TEXT,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(node_type_id, code)
);

-- Relationship types (какие связи между узлами)
CREATE TABLE graph_relationship_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL,             -- 'OWNS', 'HAS_ISSUE', 'FAMILY'
    name VARCHAR(100) NOT NULL,
    from_node_type_id INT REFERENCES graph_node_types(id),
    to_node_type_id INT REFERENCES graph_node_types(id),
    properties JSONB,                       -- {"type": "string"} for FAMILY
    vertical_id INT REFERENCES verticals(id),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(code, from_node_type_id, to_node_type_id)
);

CREATE INDEX idx_graph_node_types_vertical ON graph_node_types(vertical_id);
CREATE INDEX idx_graph_rel_types_vertical ON graph_relationship_types(vertical_id);
```

**Уровни:**

| Уровень | Что | Когда |
|---------|-----|-------|
| **MVP** | Минимальная структура из seed data | При создании вертикали |
| **Future** | Динамическое добавление через UI/AI | В процессе работы |

**MVP Flow:**
```
1. Admin создаёт вертикаль в UI
2. Worker читает graph_node_types WHERE vertical_id = X OR is_core = true
3. Worker создаёт constraints/indexes в Neo4j
4. Готово к работе
```

**Future Flow (не MVP):**
```
1. AI в диалоге понимает: "нужен новый тип узла"
2. AI вызывает Tool: add_node_type
3. Tool добавляет запись в graph_node_types
4. Worker обновляет Neo4j
```

**Seed для MVP (phone_repair):**

```sql
-- Global nodes (для всех вертикалей)
INSERT INTO graph_node_types (code, name, is_core) VALUES
('Client', 'Client', true),
('Trait', 'Client Trait', true),
('Message', 'Graph-changing Message', true);

-- Phone repair specific
INSERT INTO graph_node_types (code, name, vertical_id, is_core) VALUES
('Device', 'Mobile Device', 1, true),
('Issue', 'Repair Issue', 1, true),
('Intake', 'Client Intake', 1, true),
('Symptom', 'Extracted Symptom', 1, true),
('Diagnosis', 'Technician Diagnosis', 1, false),
('Repair', 'Completed Repair', 1, false);

-- Properties for Client
INSERT INTO graph_node_properties (node_type_id, code, name, data_type, is_required, is_unique, is_indexed) VALUES
(1, 'pg_id', 'PostgreSQL ID', 'uuid', true, true, false),
(1, 'tenant_id', 'Tenant ID', 'uuid', true, false, true),
(1, 'name', 'Name', 'string', false, false, false),
(1, 'phone', 'Phone', 'string', false, false, true);

-- Properties for Device
INSERT INTO graph_node_properties (node_type_id, code, name, data_type, is_required, is_unique, is_indexed) VALUES
(4, 'pg_id', 'PostgreSQL ID', 'uuid', true, true, false),
(4, 'tenant_id', 'Tenant ID', 'uuid', true, false, true),
(4, 'brand', 'Brand', 'string', true, false, true),
(4, 'model', 'Model', 'string', true, false, true),
(4, 'color', 'Color', 'string', false, false, false);

-- Relationships
INSERT INTO graph_relationship_types (code, name, from_node_type_id, to_node_type_id) VALUES
('OWNS', 'Owns', 1, 4),           -- Client → Device
('BROUGHT', 'Brought', 1, 4),     -- Client → Device (someone else's)
('FAMILY', 'Family', 1, 1),       -- Client → Client
('REFERRED', 'Referred', 1, 1),   -- Client → Client
('HAS_TRAIT', 'Has Trait', 1, 2), -- Client → Trait
('HAS_ISSUE', 'Has Issue', 4, 5), -- Device → Issue
('HAS_INTAKE', 'Has Intake', 5, 6),   -- Issue → Intake
('HAS_SYMPTOM', 'Has Symptom', 6, 7), -- Intake → Symptom
('HAS_DIAGNOSIS', 'Has Diagnosis', 5, 8), -- Issue → Diagnosis
('HAS_REPAIR', 'Has Repair', 5, 9);       -- Issue → Repair
```

---

## 17. SEED DATA

### 17.1 Global Directories

```sql
-- channels
INSERT INTO channels (code, name) VALUES
('telegram', 'Telegram'),
('whatsapp', 'WhatsApp'),
('vk', 'VKontakte'),
('avito', 'Avito'),
('max', 'MAX'),
('form', 'Web Form'),
('phone', 'Phone');

-- message_types
INSERT INTO message_types (code, name) VALUES
('text', 'Text'),
('voice', 'Voice'),
('image', 'Image'),
('file', 'File'),
('location', 'Location');

-- directions
INSERT INTO directions (code, name) VALUES
('in', 'Incoming'),
('out', 'Outgoing');

-- operator_types
INSERT INTO operator_types (code, name) VALUES
('human', 'Human'),
('ai', 'AI');
```

### 17.2 Domain: Electronics

```sql
-- domain
INSERT INTO domains (code, name) VALUES ('electronics', 'Electronics');

-- verticals
INSERT INTO verticals (domain_id, code, name) VALUES
(1, 'phone_repair', 'Phone Repair'),
(1, 'buy_sell', 'Buy/Sell');

-- funnel stages for phone_repair
INSERT INTO funnel_stages (vertical_id, code, name, position, ai_goal) VALUES
(1, 'greeting', 'Greeting', 1, 'Say hello, learn client name'),
(1, 'device', 'Device', 2, 'Get brand, model, color, ownership'),
(1, 'problem', 'Problem', 3, 'Collect symptoms, when it started'),
(1, 'price', 'Price', 4, 'State the cost range'),
(1, 'appointment', 'Appointment', 5, 'Schedule date/time of visit'),
(1, 'closing', 'Closing', 6, 'Confirm details, say goodbye');

-- entity types
INSERT INTO entity_types (vertical_id, code, name) VALUES
(1, 'device', 'Mobile Device');

-- extraction fields for device
INSERT INTO extraction_fields (entity_type_id, code, name, data_type, is_required) VALUES
(1, 'brand', 'Brand', 'enum', true),
(1, 'model', 'Model', 'string', true),
(1, 'color', 'Color', 'string', false),
(1, 'storage', 'Storage', 'string', false);

-- problem categories
INSERT INTO problem_categories (vertical_id, code, name) VALUES
(1, 'display', 'Display'),
(1, 'battery', 'Battery'),
(1, 'charging', 'Charging'),
(1, 'sound', 'Sound'),
(1, 'camera', 'Camera'),
(1, 'software', 'Software');

-- symptom types
INSERT INTO symptom_types (vertical_id, code, name, category_id) VALUES
(1, 'screen_cracked', 'Cracked screen', 1),
(1, 'screen_black', 'Black screen', 1),
(1, 'screen_touch_not_working', 'Touch not working', 1),
(1, 'battery_drains_fast', 'Battery drains fast', 2),
(1, 'battery_swollen', 'Battery swollen', 2),
(1, 'not_charging', 'Not charging', 3),
(1, 'charging_slow', 'Charging slowly', 3),
(1, 'no_sound', 'No sound', 4),
(1, 'speaker_quiet', 'Speaker quiet', 4);

-- diagnosis types
INSERT INTO diagnosis_types (vertical_id, code, name, category_id) VALUES
(1, 'display_broken', 'Display broken', 1),
(1, 'touch_ic_dead', 'Touch IC dead', 1),
(1, 'battery_dead', 'Battery dead', 2),
(1, 'charging_port_broken', 'Charging port broken', 3),
(1, 'charging_ic_dead', 'Charging IC dead', 3),
(1, 'speaker_broken', 'Speaker broken', 4);

-- repair actions
INSERT INTO repair_actions (vertical_id, code, name) VALUES
(1, 'replaced', 'Replaced'),
(1, 'repaired', 'Repaired'),
(1, 'cleaned', 'Cleaned'),
(1, 'reflashed', 'Reflashed');
```

---

## 18. TOOLS

Tools — слепые исполнители внутри Core. Не знают бизнес-логику, только выполняют команды.

### 18.1 Tool Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                           CORE                                   │
│  (логика, оркестрация, принятие решений)                        │
│                                                                  │
│    ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐      │
│    │  Graph   │  │   Send   │  │    AI    │  │  Price   │      │
│    │  Query   │  │  Message │  │   Call   │  │  Lookup  │      │
│    └────┬─────┘  └────┬─────┘  └────┬─────┘  └────┬─────┘      │
└─────────┼─────────────┼─────────────┼─────────────┼─────────────┘
          │             │             │             │
          ▼             ▼             ▼             ▼
       Neo4j          MCP         Claude       PostgreSQL
```

**Принцип:** Core решает ЧТО делать, Tool выполняет КАК.

### 18.2 Tool: Graph Query

Слепой исполнитель Cypher запросов.

**Input:**
```json
{
  "query_code": "get_client_context",
  "params": {
    "client_id": "550e8400-e29b-41d4-a716-446655440000"
  }
}
```

**Логика:**
1. SELECT query FROM cypher_queries WHERE code = $query_code
2. Подставить params в query
3. Выполнить в Neo4j
4. Вернуть результат

**Output:**
```json
{
  "success": true,
  "data": { ... },
  "execution_time_ms": 12
}
```

**Не знает:**
- Зачем нужен этот запрос
- Что будет с результатом
- Бизнес-логику

### 18.3 Tool: Send Message

Слепой отправщик сообщений через MCP.

**Input:**
```json
{
  "dialog_id": "uuid",
  "message": {
    "text": "Привет! Чем могу помочь?",
    "buttons": [...],
    "attachments": [...]
  }
}
```

**Логика:**
1. По dialog_id → получить channel_id, chat_id, credentials
2. Вызвать MCP endpoint нужного канала
3. Сохранить сообщение в elo_messages
4. Вернуть статус

### 18.4 Tool: AI Call

Слепой вызов AI модели.

**Input:**
```json
{
  "prompt_code": "extract_device",
  "context": "...",
  "message": "У меня айфон 14 про макс синий"
}
```

**Логика:**
1. SELECT prompt FROM prompts WHERE code = $prompt_code
2. Собрать полный prompt (system + context + message)
3. Вызвать Claude/GPT
4. Вернуть structured output

### 18.5 Tool: Price Lookup

Поиск цены из прайс-листа.

**Input:**
```json
{
  "vertical_id": 1,
  "device_brand": "Apple",
  "device_model": "iPhone 14",
  "repair_action": "screen_replacement"
}
```

**Логика:**
1. SELECT price_range FROM price_list WHERE ...
2. Вернуть min/max цену

### 18.6 Table: cypher_queries

```sql
CREATE TABLE cypher_queries (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,     -- 'get_client_context', 'create_device'
    name VARCHAR(100) NOT NULL,           -- 'Get full client context'
    description TEXT,
    query TEXT NOT NULL,                  -- Cypher query с $параметрами
    params_schema JSONB,                  -- {"client_id": "uuid", "vertical_id": "int"}
    vertical_id INT REFERENCES verticals(id),  -- NULL = global
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX idx_cypher_queries_code ON cypher_queries(code);
```

### 18.7 Seed: cypher_queries

```sql
INSERT INTO cypher_queries (code, name, query, params_schema) VALUES

-- Client context
('get_client_context', 'Get full client context', '
MATCH (c:Client {pg_id: $client_id})
OPTIONAL MATCH (c)-[:OWNS|BROUGHT]->(d:Device)
OPTIONAL MATCH (d)-[:HAS_ISSUE]->(i:Issue)
OPTIONAL MATCH (i)-[:HAS_INTAKE]->(intake)-[:HAS_SYMPTOM]->(s:Symptom)
OPTIONAL MATCH (i)-[:HAS_DIAGNOSIS]->(diag:Diagnosis)
OPTIONAL MATCH (i)-[:HAS_REPAIR]->(r:Repair)
OPTIONAL MATCH (c)-[:HAS_TRAIT]->(t:Trait)
RETURN c, collect(DISTINCT d) as devices,
       collect(DISTINCT i) as issues,
       collect(DISTINCT s) as symptoms,
       collect(DISTINCT diag) as diagnoses,
       collect(DISTINCT r) as repairs,
       collect(DISTINCT t) as traits
', '{"client_id": "uuid"}'),

-- Similar cases
('find_similar_cases', 'Find similar diagnosis cases', '
MATCH (i:Issue)-[:PROBLEM_CATEGORY]->(cat:ProblemCategory {code: $category})
MATCH (i)-[:HAS_DIAGNOSIS]->(d:Diagnosis)
WHERE i.vertical_id = $vertical_id
RETURN cat.code, d.text, count(*) as frequency
ORDER BY frequency DESC
LIMIT 5
', '{"category": "string", "vertical_id": "int"}'),

-- Device history
('get_device_history', 'Get device repair history', '
MATCH (d:Device {pg_id: $device_id})-[:HAS_ISSUE]->(i:Issue)
OPTIONAL MATCH (i)-[:HAS_DIAGNOSIS]->(diag)
OPTIONAL MATCH (i)-[:HAS_REPAIR]->(r)
RETURN i, collect(diag) as diagnoses, collect(r) as repairs
ORDER BY i.created_at DESC
', '{"device_id": "uuid"}'),

-- Client network
('get_client_network', 'Get family and referrals', '
MATCH (c:Client {pg_id: $client_id})
OPTIONAL MATCH (c)-[fam:FAMILY]->(family:Client)
OPTIONAL MATCH (c)-[:REFERRED]->(referred:Client)
OPTIONAL MATCH (referrer:Client)-[:REFERRED]->(c)
RETURN c,
       collect({client: family, relation: fam.type}) as family_members,
       collect(referred) as referred_clients,
       collect(referrer) as referred_by
', '{"client_id": "uuid"}'),

-- Client traits
('get_client_traits', 'Get client traits', '
MATCH (c:Client {pg_id: $client_id})-[:HAS_TRAIT]->(t:Trait)
RETURN t.type, t.value, t.confidence, t.source
ORDER BY t.confidence DESC
', '{"client_id": "uuid"}'),

-- Create device
('create_device', 'Create device with owner', '
MATCH (c:Client {pg_id: $client_id})
CREATE (d:Device {
  pg_id: $device_id,
  tenant_id: c.tenant_id,
  brand: $brand,
  model: $model,
  color: $color,
  created_at: datetime()
})
CREATE (c)-[:OWNS]->(d)
RETURN d
', '{"client_id": "uuid", "device_id": "uuid", "brand": "string", "model": "string", "color": "string"}'),

-- Create issue
('create_issue', 'Create issue for device', '
MATCH (d:Device {pg_id: $device_id})
CREATE (i:Issue {
  pg_id: $issue_id,
  tenant_id: d.tenant_id,
  vertical_id: $vertical_id,
  dialog_id: $dialog_id,
  status: ''active'',
  created_at: datetime()
})
CREATE (d)-[:HAS_ISSUE]->(i)
RETURN i
', '{"device_id": "uuid", "issue_id": "uuid", "vertical_id": "int", "dialog_id": "uuid"}'),

-- Add trait
('add_client_trait', 'Add trait to client', '
MATCH (c:Client {pg_id: $client_id})
MERGE (t:Trait {type: $type, value: $value})
ON CREATE SET t.confidence = $confidence, t.source = $source, t.created_at = datetime()
MERGE (c)-[:HAS_TRAIT]->(t)
RETURN t
', '{"client_id": "uuid", "type": "string", "value": "string", "confidence": "float", "source": "string"}'),

-- Symptom statistics
('get_symptom_diagnosis_stats', 'Symptom to diagnosis statistics', '
MATCH (s:Symptom)-[:SYMPTOM_TYPE]->(st:SymptomType {code: $symptom_code})
MATCH (s)<-[:HAS_SYMPTOM]-(intake)<-[:HAS_INTAKE]-(i:Issue)
MATCH (i)-[:HAS_DIAGNOSIS]->(d:Diagnosis)-[:DIAGNOSIS_TYPE]->(dt:DiagnosisType)
WHERE st.vertical_id = $vertical_id
RETURN st.name, dt.name, count(*) as occurrences
ORDER BY occurrences DESC
', '{"symptom_code": "string", "vertical_id": "int"}'),

-- Multi-vertical history
('get_client_verticals', 'All verticals client interacted with', '
MATCH (c:Client {pg_id: $client_id})-[:OWNS|BROUGHT]->(d:Device)-[:HAS_ISSUE]->(i:Issue)
RETURN DISTINCT i.vertical_id
', '{"client_id": "uuid"}');
```

---

## 19. NEXT STEPS

Blocks to design in detail (separate documents):

| # | Block | File | Description |
|---|-------|------|-------------|
| 1 | Core Tables | `BLOCK_01_CORE.md` | Tenants, operators, clients, dialogs, messages |
| 2 | Channel Layer | `BLOCK_02_CHANNELS.md` | MCP integration, In/Out workflows |
| 3 | Input Contour | `BLOCK_03_INPUT.md` | Batching, tenant/client resolution |
| 4 | AI Pipeline | `BLOCK_04_AI.md` | Context, Request, Orchestrator, Tools |
| 5 | Graph | `BLOCK_05_GRAPH.md` | Neo4j operations, sync logic |
| 6 | API | `BLOCK_06_API.md` | Android/Web endpoints |

Each block document will contain:
- Detailed flow diagrams
- All SQL/Cypher queries
- Redis keys and logic
- Input/output contracts
- Error handling

---

**Document:** ARCHITECTURE.md
**Date:** 2025-12-11
**Author:** Dmitry + Claude
**Status:** Designing blocks
