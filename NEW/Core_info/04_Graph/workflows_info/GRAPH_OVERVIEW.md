# Graph Block — Overview

> Neo4j graph database for client context, device history, diagnostic chain

**Server:** 45.144.177.128:7474 (RU Server)
**Bolt:** bolt+ssc://45.144.177.128:7687

---

## Purpose

Graph stores **relationships** that PostgreSQL cannot efficiently query:
- Client → Device ownership history
- Device → Issue → Symptom → Diagnosis → Repair chain
- Client networks (family, referrals)
- Client traits (behavioral patterns)

**Principle:** PostgreSQL = fast reads, Neo4j = source of truth for relationships.

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CORE                                            │
│                   (logic, orchestration, decisions)                          │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                        Tool: Graph Query                                     │
│                    (blind Cypher executor)                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│  Input: { query_code, params }                                              │
│  1. SELECT query FROM cypher_queries WHERE code = $query_code               │
│  2. Substitute params                                                        │
│  3. Execute in Neo4j                                                         │
│  4. Return result                                                            │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                            Neo4j                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Tool: Graph Query** — blind executor. Doesn't know business logic, only executes queries by code.

---

## Node Types (9 main nodes)

| Node | Purpose | Key Properties | Scope |
|------|---------|----------------|-------|
| **Client** | Client in graph | pg_id, tenant_id, name, phone | Global |
| **Device** | Device/object | pg_id, tenant_id, brand, model, color | Per vertical |
| **Issue** | Contact case | pg_id, tenant_id, vertical_id, dialog_id, status | Per vertical |
| **Intake** | Client's words | pg_id, raw_text, created_at | Per vertical |
| **Symptom** | Extracted symptom | pg_id, symptom_type_id, text | Per vertical |
| **Diagnosis** | Technician finding | pg_id, diagnosis_type_id, text, confirmed_by | Per vertical |
| **Repair** | Completed repair | pg_id, repair_action_id, text, cost | Per vertical |
| **Message** | Graph-changing message | pg_id, direction, created_at | Global |
| **Trait** | Client attribute | type, value, confidence, source | Global |

**Notes:**
- `tenant_id` — on all main nodes for multi-tenant isolation
- `vertical_id` — on Issue for multi-vertical support
- `Message` — stored ONLY when it changes the graph
- `Trait` — client attributes (vip, knows_prices, prefers_whatsapp)

---

## Reference Nodes (per vertical)

| Node | Purpose | Properties |
|------|---------|------------|
| **SymptomType** | Symptom type reference | pg_id, vertical_id, code, name |
| **DiagnosisType** | Diagnosis type reference | pg_id, vertical_id, code, name |
| **RepairAction** | Repair type reference | pg_id, vertical_id, code, name |
| **ProblemCategory** | Problem category reference | pg_id, vertical_id, code, name |

---

## Relationships

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
```

---

## Visual Schema

```
                              ┌─────────────┐
                              │   Client    │
                              └──────┬──────┘
               ┌──────────────────┬──┴───┬──────────────────┐
               │                  │      │                  │
         [:OWNS]           [:BROUGHT] [:HAS_TRAIT]    [:FAMILY]
               │                  │      │            [:REFERRED]
               ▼                  ▼      ▼                  │
        ┌──────────┐       ┌──────────┐ ┌───────┐          │
        │  Device  │       │  Device  │ │ Trait │     ┌────▼────┐
        └────┬─────┘       │(brought) │ └───────┘     │ Client  │
             │             └──────────┘               └─────────┘
       [:HAS_ISSUE]
             │
             ▼
      ┌──────────┐
      │  Issue   │ ◄─── vertical_id (multi-vertical support)
      └────┬─────┘
           │
    ┌──────┼──────┬─────────────┐
    │      │      │             │
    ▼      ▼      ▼             ▼
┌────────┐ ┌──────────┐ ┌──────────┐
│ Intake │ │Diagnosis │ │  Repair  │
└───┬────┘ └──────────┘ └──────────┘
    │
[:HAS_SYMPTOM]
    │
    ▼
┌─────────┐
│ Symptom │
└─────────┘
```

---

## Three Layers of Truth

```
Intake (container of client's words)
    │
    ├── Symptom 1 ("doesn't charge")     ← AI extracted from client's words
    ├── Symptom 2 ("screen cracked")
    └── Symptom 3 ("battery swollen")
           │
           ▼
    Diagnosis                             ← Technician's finding
           │
           ▼
    Repair                                ← What was actually done
```

| Entity | Source | Description |
|--------|--------|-------------|
| **Intake** | Dialog | Container — everything client said |
| **Symptom** | AI | Extracted symptoms from client's words |
| **Diagnosis** | Technician | What was actually found |
| **Repair** | Technician | What was done |

---

## Multi-Vertical Support

One client can have issues in multiple verticals:

```
Client "Ivan"
  └── Device "iPhone 14"
        ├── Issue (vertical: phone_repair) - screen cracked
        └── Issue (vertical: buy_sell) - wants to sell

Same client, same device, different verticals.
Each Issue has its own vertical_id.
```

---

## Graph Schema Storage (PostgreSQL)

**Problem:** Graph Query Tool is blind. Who knows the graph structure?

**Solution:** Structure stored in PostgreSQL, Worker reads and creates.

| Table | Purpose |
|-------|---------|
| `graph_node_types` | What nodes exist (Client, Device, Issue...) |
| `graph_node_properties` | What properties nodes have (pg_id, brand...) |
| `graph_relationship_types` | What relationships exist (OWNS, HAS_ISSUE...) |

**MVP:** Minimal structure from seed data, Worker creates on vertical init.

**Future:** Dynamic addition via UI/AI during dialog.

---

## Cypher Queries Storage

Queries stored in `cypher_queries` table:

| code | Purpose |
|------|---------|
| `get_client_context` | Full client context with all relationships |
| `find_similar_cases` | Similar diagnosis cases for AI suggestions |
| `get_device_history` | Device repair history |
| `get_client_network` | Family and referrals |
| `get_client_traits` | Client behavioral traits |
| `create_device` | Create device with owner |
| `create_issue` | Create issue for device |
| `add_client_trait` | Add trait to client |
| `get_symptom_diagnosis_stats` | Symptom → Diagnosis statistics |
| `get_client_verticals` | All verticals client interacted with |

---

## Tool: Graph Query

**Input:**
```json
{
  "query_code": "get_client_context",
  "params": {
    "client_id": "550e8400-e29b-41d4-a716-446655440000"
  }
}
```

**Logic:**
1. `SELECT query FROM cypher_queries WHERE code = $query_code`
2. Substitute params into query
3. Execute in Neo4j
4. Return result

**Output:**
```json
{
  "success": true,
  "data": { ... },
  "execution_time_ms": 12
}
```

**Doesn't know:**
- Why this query is needed
- What happens with the result
- Business logic

---

## Constraints and Indexes

```cypher
// Uniqueness constraints
CREATE CONSTRAINT client_pg_id IF NOT EXISTS FOR (c:Client) REQUIRE c.pg_id IS UNIQUE;
CREATE CONSTRAINT device_pg_id IF NOT EXISTS FOR (d:Device) REQUIRE d.pg_id IS UNIQUE;
CREATE CONSTRAINT issue_pg_id IF NOT EXISTS FOR (i:Issue) REQUIRE i.pg_id IS UNIQUE;
CREATE CONSTRAINT message_pg_id IF NOT EXISTS FOR (m:Message) REQUIRE m.pg_id IS UNIQUE;

// Multi-tenant indexes
CREATE INDEX client_tenant IF NOT EXISTS FOR (c:Client) ON (c.tenant_id);
CREATE INDEX device_tenant IF NOT EXISTS FOR (d:Device) ON (d.tenant_id);
CREATE INDEX issue_tenant IF NOT EXISTS FOR (i:Issue) ON (i.tenant_id);

// Multi-vertical indexes
CREATE INDEX issue_vertical IF NOT EXISTS FOR (i:Issue) ON (i.vertical_id);

// Search indexes
CREATE INDEX device_brand_model IF NOT EXISTS FOR (d:Device) ON (d.brand, d.model);
CREATE INDEX trait_type IF NOT EXISTS FOR (t:Trait) ON (t.type);
```

---

## Example Queries

### Get Client Context
```json
{
  "query_code": "get_client_context",
  "params": { "client_id": "uuid" }
}
```

### Create Device
```json
{
  "query_code": "create_device",
  "params": {
    "client_id": "uuid",
    "device_id": "uuid",
    "brand": "Apple",
    "model": "iPhone 14",
    "color": "blue"
  }
}
```

### Add Trait
```json
{
  "query_code": "add_client_trait",
  "params": {
    "client_id": "uuid",
    "type": "behavior",
    "value": "vip",
    "confidence": 0.9,
    "source": "ai_extraction"
  }
}
```

### Find Similar Cases
```json
{
  "query_code": "find_similar_cases",
  "params": {
    "category": "battery",
    "vertical_id": 1
  }
}
```

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Database | Neo4j (45.144.177.128:7687) | Graph storage |
| Table | `cypher_queries` | Query storage |
| Table | `graph_node_types` | Schema storage |
| Table | `graph_node_properties` | Properties storage |
| Table | `graph_relationship_types` | Relationships storage |

---

## Who Calls Graph

| Caller | Query Code | Purpose |
|--------|------------|---------|
| Context Builder | `get_client_context` | AI context |
| Dialog Engine | `create_device`, `create_issue` | Graph updates |
| AI Pipeline | `find_similar_cases` | Suggestions |
| API | `get_device_history` | Client app |

---

**Document:** GRAPH_OVERVIEW.md
**Date:** 2025-12-11
**Author:** Dmitry + Claude
**Status:** Updated for new architecture
