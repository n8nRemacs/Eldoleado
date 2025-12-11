# ELO_Core_Graph_Writer

> Writes AI extractions to Neo4j graph via Graph Tool

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `NEW` (to be created) |
| **File** | `NEW/workflows/ELO_Core/ELO_Core_Graph_Writer.json` |
| **Trigger** | Execute Workflow Trigger |
| **Called from** | ELO_Core_Ingest |
| **Calls** | Graph Tool (MCP:8773) |

---

## Purpose

Write extracted entities to Neo4j:
1. Create/update Device
2. Create Issue (if new)
3. Create Intake with Symptoms
4. Save message (if changed_graph = true)

---

## Input Data

```json
{
  "tenant_id": "uuid",
  "client_id": "uuid",
  "dialog_id": "uuid",
  "extractions": {
    "device": {
      "brand": "Apple",
      "model": "iPhone 14 Pro",
      "color": null,
      "confidence": 0.95
    },
    "symptoms": [
      {
        "code": "screen_replacement",
        "text": "замена дисплея",
        "confidence": 0.9
      }
    ],
    "vertical": {
      "code": "phone_repair",
      "confidence": 0.95
    }
  },
  "original_text": "Привет, сколько стоит поменять дисплей на iPhone 14 Pro?"
}
```

---

## Output Data

```json
{
  "graph_operations": [
    {
      "operation": "create_device",
      "success": true,
      "device_id": "uuid"
    },
    {
      "operation": "create_issue",
      "success": true,
      "issue_id": "uuid"
    },
    {
      "operation": "create_intake_with_symptoms",
      "success": true,
      "intake_id": "uuid",
      "symptom_count": 1
    }
  ],
  "changed_graph": true
}
```

---

## Nodes

### 1. Execute Workflow Trigger

Entry point — called from ELO_Core_Ingest.

---

### 2. Check Extractions

```javascript
const input = $input.first().json;
const extractions = input.extractions || {};

const hasDevice = extractions.device?.brand && extractions.device?.model;
const hasSymptoms = (extractions.symptoms || []).length > 0;

return {
  ...input,
  has_device: hasDevice,
  has_symptoms: hasSymptoms,
  should_write: hasDevice || hasSymptoms
};
```

---

### 3. Should Write?

| Condition | Result |
|-----------|--------|
| `should_write === true` | → Create Device |
| `should_write === false` | → Skip (return empty) |

---

### 4. Create Device (Graph Tool)

| Parameter | Value |
|----------|----------|
| **Type** | HTTP Request |
| **URL** | `http://graph-tool:8773/query` |
| **Method** | POST |

**Body:**
```json
{
  "query_code": "create_or_get_device",
  "params": {
    "client_id": "{{ $json.client_id }}",
    "tenant_id": "{{ $json.tenant_id }}",
    "brand": "{{ $json.extractions.device.brand }}",
    "model": "{{ $json.extractions.device.model }}",
    "color": "{{ $json.extractions.device.color }}"
  }
}
```

**Expected Cypher (`create_or_get_device`):**
```cypher
MATCH (c:Client {pg_id: $client_id})
MERGE (d:Device {
  tenant_id: $tenant_id,
  brand: $brand,
  model: $model
})
ON CREATE SET
  d.pg_id = randomUUID(),
  d.color = $color,
  d.created_at = datetime()
ON MATCH SET
  d.updated_at = datetime()
MERGE (c)-[:OWNS]->(d)
RETURN d.pg_id as device_id, d
```

---

### 5. Create Issue (Graph Tool)

| Parameter | Value |
|----------|----------|
| **Type** | HTTP Request |
| **URL** | `http://graph-tool:8773/query` |
| **Method** | POST |

**Body:**
```json
{
  "query_code": "create_issue",
  "params": {
    "device_id": "{{ $json.device_id }}",
    "tenant_id": "{{ $json.tenant_id }}",
    "dialog_id": "{{ $json.dialog_id }}",
    "vertical_code": "{{ $json.extractions.vertical.code }}"
  }
}
```

**Expected Cypher (`create_issue`):**
```cypher
MATCH (d:Device {pg_id: $device_id})
CREATE (i:Issue {
  pg_id: randomUUID(),
  tenant_id: $tenant_id,
  dialog_id: $dialog_id,
  vertical_code: $vertical_code,
  status: 'active',
  created_at: datetime()
})
CREATE (d)-[:HAS_ISSUE]->(i)
RETURN i.pg_id as issue_id, i
```

---

### 6. Create Intake with Symptoms (Graph Tool)

| Parameter | Value |
|----------|----------|
| **Type** | HTTP Request |
| **URL** | `http://graph-tool:8773/query` |
| **Method** | POST |

**Body:**
```json
{
  "query_code": "create_intake_with_symptoms",
  "params": {
    "issue_id": "{{ $json.issue_id }}",
    "tenant_id": "{{ $json.tenant_id }}",
    "raw_text": "{{ $json.original_text }}",
    "symptoms": {{ JSON.stringify($json.extractions.symptoms) }}
  }
}
```

**Expected Cypher (`create_intake_with_symptoms`):**
```cypher
MATCH (i:Issue {pg_id: $issue_id})
CREATE (int:Intake {
  pg_id: randomUUID(),
  tenant_id: $tenant_id,
  raw_text: $raw_text,
  created_at: datetime()
})
CREATE (i)-[:HAS_INTAKE]->(int)
WITH int, $symptoms as symptoms
UNWIND symptoms as symptom
CREATE (s:Symptom {
  pg_id: randomUUID(),
  code: symptom.code,
  text: symptom.text,
  confidence: symptom.confidence,
  created_at: datetime()
})
CREATE (int)-[:HAS_SYMPTOM]->(s)
RETURN int.pg_id as intake_id, count(s) as symptom_count
```

---

### 7. Collect Results

```javascript
const createDevice = $('Create Device').first().json;
const createIssue = $('Create Issue').first().json;
const createIntake = $('Create Intake with Symptoms').first().json;

const operations = [];

if (createDevice?.success) {
  operations.push({
    operation: 'create_device',
    success: true,
    device_id: createDevice.data?.device_id
  });
}

if (createIssue?.success) {
  operations.push({
    operation: 'create_issue',
    success: true,
    issue_id: createIssue.data?.issue_id
  });
}

if (createIntake?.success) {
  operations.push({
    operation: 'create_intake_with_symptoms',
    success: true,
    intake_id: createIntake.data?.intake_id,
    symptom_count: createIntake.data?.symptom_count || 0
  });
}

return {
  graph_operations: operations,
  changed_graph: operations.length > 0
};
```

---

## Flow Diagram

```
Execute Trigger
       │
       ▼
┌──────────────────┐
│Check Extractions │
└────────┬─────────┘
         │
    Should Write?
    ├── NO → Return empty
    └── YES ↓
         │
         ▼
┌──────────────────┐
│  Create Device   │  ← Graph Tool: create_or_get_device
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│  Create Issue    │  ← Graph Tool: create_issue
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│ Create Intake +  │  ← Graph Tool: create_intake_with_symptoms
│    Symptoms      │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│ Collect Results  │
└────────┬─────────┘
         │
         ▼
   Return operations
```

---

## Graph Structure Created

```
(Client)
    │
  [:OWNS]
    │
    ▼
(Device) ←── brand: "Apple", model: "iPhone 14 Pro"
    │
  [:HAS_ISSUE]
    │
    ▼
(Issue) ←── vertical_code: "phone_repair", dialog_id: "uuid"
    │
  [:HAS_INTAKE]
    │
    ▼
(Intake) ←── raw_text: "Привет, сколько стоит..."
    │
  [:HAS_SYMPTOM]
    │
    ▼
(Symptom) ←── code: "screen_replacement", text: "замена дисплея"
```

---

## Required Cypher Queries

Add to `cypher_queries` table:

| code | purpose |
|------|---------|
| `create_or_get_device` | Create device or get existing |
| `create_issue` | Create new issue for device |
| `create_intake_with_symptoms` | Create intake with symptoms |

---

## Error Handling

| Error | Action |
|-------|--------|
| Device creation fails | Log, skip issue creation |
| Issue creation fails | Log, skip intake creation |
| Symptom creation fails | Log, return partial success |

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Service | Graph Tool (8773) | Neo4j operations |
| Query | `create_or_get_device` | Device creation |
| Query | `create_issue` | Issue creation |
| Query | `create_intake_with_symptoms` | Intake + symptoms |
