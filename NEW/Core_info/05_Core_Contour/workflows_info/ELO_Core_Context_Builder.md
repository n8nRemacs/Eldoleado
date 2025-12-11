# ELO_Core_Context_Builder

> Loads full client context from PostgreSQL and Neo4j

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `NEW` (to be created) |
| **File** | `NEW/workflows/ELO_Core/ELO_Core_Context_Builder.json` |
| **Trigger** | Execute Workflow Trigger |
| **Called from** | ELO_Core_Ingest |
| **Calls** | Graph Tool (MCP:8773) |

---

## Purpose

Gather all context needed for AI extraction:
1. Load dialog state from PostgreSQL
2. Load client history from Neo4j
3. Combine into single context object

---

## Input Data

```json
{
  "tenant_id": "uuid",
  "client_id": "uuid",
  "dialog_id": "uuid",
  "text": "Привет, сколько стоит поменять дисплей на iPhone 14 Pro?",
  "meta": {
    "is_new_client": false,
    "is_new_dialog": false
  }
}
```

---

## Output Data

```json
{
  "context": {
    "tenant_id": "uuid",
    "client_id": "uuid",
    "dialog_id": "uuid",

    "dialog_state": {
      "vertical_id": null,
      "current_stage": null,
      "status": "active",
      "is_new": true
    },

    "client": {
      "name": "Ivan Petrov",
      "phone": "+79991234567",
      "traits": [],
      "total_issues": 0,
      "last_visit": null
    },

    "history": {
      "devices": [],
      "issues": [],
      "symptoms": [],
      "repairs": []
    },

    "message": {
      "text": "Привет, сколько стоит поменять дисплей на iPhone 14 Pro?",
      "timestamp": "2024-12-10T10:00:15Z"
    }
  }
}
```

---

## Nodes

### 1. Execute Workflow Trigger

Entry point — called from ELO_Core_Ingest.

---

### 2. Load Dialog State (PostgreSQL)

```sql
SELECT
  d.id as dialog_id,
  d.vertical_id,
  d.status_id,
  d.created_at,
  d.updated_at,
  c.name as client_name,
  c.phone as client_phone
FROM elo_dialogs d
JOIN elo_clients c ON c.id = d.client_id
WHERE d.id = $dialog_id
  AND d.tenant_id = $tenant_id;
```

| Parameter | Value |
|----------|----------|
| **Type** | PostgreSQL |
| **Operation** | Execute Query |
| **Output** | dialog_state |

---

### 3. Load Client Context (Graph Tool)

| Parameter | Value |
|----------|----------|
| **Type** | HTTP Request |
| **URL** | `http://graph-tool:8773/query` |
| **Method** | POST |

**Body:**
```json
{
  "query_code": "get_client_context",
  "params": {
    "client_id": "{{ $json.client_id }}"
  }
}
```

**Expected Cypher (stored in `cypher_queries`):**
```cypher
MATCH (c:Client {pg_id: $client_id})
OPTIONAL MATCH (c)-[:OWNS]->(d:Device)
OPTIONAL MATCH (d)-[:HAS_ISSUE]->(i:Issue)
OPTIONAL MATCH (i)-[:HAS_INTAKE]->(int:Intake)
OPTIONAL MATCH (int)-[:HAS_SYMPTOM]->(s:Symptom)
OPTIONAL MATCH (i)-[:HAS_REPAIR]->(r:Repair)
OPTIONAL MATCH (c)-[:HAS_TRAIT]->(t:Trait)
RETURN c,
       collect(DISTINCT d) as devices,
       collect(DISTINCT i) as issues,
       collect(DISTINCT s) as symptoms,
       collect(DISTINCT r) as repairs,
       collect(DISTINCT t) as traits
```

---

### 4. Combine Context

```javascript
const input = $('Execute Workflow Trigger').first().json;
const dialogState = $('Load Dialog State').first().json;
const graphContext = $('Load Client Context').first().json;

// Parse graph response
const graphData = graphContext.success ? graphContext.data : {};

return {
  context: {
    tenant_id: input.tenant_id,
    client_id: input.client_id,
    dialog_id: input.dialog_id,

    dialog_state: {
      vertical_id: dialogState?.vertical_id || null,
      current_stage: null,  // TODO: from funnel_stages
      status: dialogState?.status_id === 1 ? 'active' : 'closed',
      is_new: input.meta?.is_new_dialog || false
    },

    client: {
      name: dialogState?.client_name || input.client?.name,
      phone: dialogState?.client_phone || input.client?.phone,
      traits: graphData.traits || [],
      total_issues: (graphData.issues || []).length,
      last_visit: graphData.issues?.[0]?.created_at || null
    },

    history: {
      devices: graphData.devices || [],
      issues: graphData.issues || [],
      symptoms: graphData.symptoms || [],
      repairs: graphData.repairs || []
    },

    message: {
      text: input.text,
      timestamp: input.timestamp
    }
  }
};
```

---

## Flow Diagram

```
Execute Trigger
      │
      ├────────────────┬─────────────────┐
      │                │                 │
      ▼                ▼                 │
┌──────────┐    ┌──────────────┐         │
│PostgreSQL│    │  Graph Tool  │         │
│  Dialog  │    │   Context    │         │
└────┬─────┘    └──────┬───────┘         │
     │                 │                 │
     └────────┬────────┘                 │
              │                          │
              ▼                          │
       ┌────────────┐                    │
       │  Combine   │◄───────────────────┘
       │  Context   │     (original input)
       └─────┬──────┘
             │
             ▼
        Return context
```

---

## MVP v0 Simplifications

| Feature | MVP v0 | Full |
|---------|--------|------|
| Funnel stage | Skip | Load from funnel_stages |
| Focus score | Skip | Calculate based on known fields |
| AI settings | Skip | Load pre_rules, post_rules |
| Vertical detection | Skip | AI will suggest |

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Table | `elo_dialogs` | Dialog state |
| Table | `elo_clients` | Client info |
| Service | Graph Tool (8773) | Neo4j context |
| Query | `get_client_context` | Cypher query code |
