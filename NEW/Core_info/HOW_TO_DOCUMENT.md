# How to Document Workflows

> Instructions for creating n8n workflow documentation

---

## Documentation Process

### 1. Read the JSON workflow

```bash
# Find the file
NEW/workflows/ELO_In/ELO_In_Telegram.json
NEW/workflows/ELO_Out/ELO_Out_Telegram.json
NEW/workflows/n8n_old/API/API_Android_Auth.json
```

### 2. Extract key information

From JSON you need to get:

| What | Where in JSON |
|------|---------------|
| Name | `name` |
| Nodes | `nodes[]` |
| Connections | `connections` |
| Trigger | First node (webhook/executeWorkflowTrigger) |

### 3. Create markdown file

**Path:** `Core_info/{BLOCK}/workflows_info/{WORKFLOW_NAME}.md`

**Blocks:**
- `01_Channel_Layer` — ELO_In_*, ELO_Out_*
- `02_Input_Contour` — Tenant/Client/Dialog Resolver, Batcher
- `03_Core` — Dialog Engine, AI Router
- `04_Graph` — Neo4j operations
- `05_Diagnostic_Engine` — Symptoms, diagnoses
- `06_API` — API_Android_*

---

## Documentation Template

```markdown
# {Workflow Name}

> Brief description (1 line)

---

## General Information

| Parameter | Value |
|-----------|-------|
| **File** | `path/to/file.json` |
| **Trigger** | Webhook POST `/path` or Execute Workflow Trigger |
| **Called from** | Where it's called from |
| **Calls** | What other workflows it calls |
| **Output** | Where it sends results |

---

## Purpose

2-3 sentences about what the workflow does.

---

## Input Data

**Source:** where data comes from

\```json
{
  "field": "value"
}
\```

---

## Output Data

**Destination:** where data is sent

\```json
{
  "field": "value"
}
\```

---

## Nodes

### 1. {Node Name}

| Parameter | Value |
|-----------|-------|
| **ID** | `uuid from json` |
| **Type** | n8n-nodes-base.xxx |

**Description:** what the node does

---

## Flow Diagram

\```
Node1 → Node2 → Node3
            ├── YES → Node4
            └── NO → Node5
\```

---

## Specifics

| Feature | Description |
|---------|-------------|
| **Key feature** | Explanation |

---

## Dependencies

| Type | ID | Purpose |
|------|-----|---------|
| Workflow | uuid | Name |
| Redis | uuid | What for |
| Postgres | uuid | Database |
```

---

## What to Document in Nodes

### Code Node

**Must include:**
- Full code (or key parts if very long)
- What the logic does
- What fields it extracts/transforms

```markdown
**Code:**
\```javascript
const data = $input.first().json;
// ... code
return { field: value };
\```
```

### PostgreSQL Node

**Must include:**
- Full SQL query
- What tables it uses
- What it returns

```markdown
**SQL query:**
\```sql
SELECT * FROM table WHERE id = '{{ $json.id }}'
\```

**Table:** `table_name`
```

### Redis Node

**Must include:**
- Operation (GET/SET/PUSH/POP)
- Key
- TTL (if SET)
- Who writes / who reads

```markdown
**Redis:**
| Operation | Key | TTL | Purpose |
|-----------|-----|-----|---------|
| SET | `avito_access_token` | 86400s | OAuth token cache |
| RPUSH | `queue:incoming` | — | Message queue |
```

### HTTP Request Node

**Must include:**
- URL
- Method
- Headers (important ones)
- Body format

```markdown
**HTTP Request:**
| Parameter | Value |
|-----------|-------|
| **URL** | `https://api.example.com/endpoint` |
| **Method** | POST |
| **Headers** | `Authorization: Bearer {{token}}` |
```

### IF Node

**Must include:**
- Condition
- What happens on TRUE/FALSE

```markdown
**Condition:** `$json.field === true`
- TRUE → Node A
- FALSE → Node B
```

### Execute Workflow Node

**Must include:**
- Called workflow ID
- Workflow name

```markdown
**Calls:** ELO_Core_Tenant_Resolver (rRO6sxLqiCdgvLZz)
```

---

## Patterns for Different Workflow Types

### ELO_In (incoming)

```
Webhook → Extract Data → Has Voice?
                            ├── YES → Download → Transcribe → Normalize
                            └── NO → Normalize
                                          ↓
                            Execute Tenant Resolver
                                          ↓
                            [Redis PUSH or Client Resolver]
                                          ↓
                            Respond
```

**Key sections:**
- How data is extracted from webhook
- Phone normalization
- ELO Core Contract format
- Where it sends (Redis or directly)

### ELO_Out (outgoing)

```
Execute Trigger → [Get Credentials] → Send Message → Process Response
                                                          ↓
                                            Save Message History (PostgreSQL)
                                                          ↓
                                            Register Touchpoint (Neo4j webhook)
```

**Key sections:**
- Where credentials come from
- Channel send format
- Determining touchpoint_direction

### API (Android)

```
Webhook → Parse/Validate → [Auth Check] → Business Logic → Format Response → Respond
```

**Key sections:**
- Endpoint (path, method)
- Authorization (x-session-token)
- SQL queries
- Response format

---

## Updating INDEX.md

After creating documentation, update `Core_info/INDEX.md`:

1. Add file to structure (with ✅)
2. Add to workflows table
3. Update block counter

---

## Examples of Good Documentation

- `01_Channel_Layer/workflows_info/ELO_In_Avito.md` — filtering, specifics
- `01_Channel_Layer/workflows_info/ELO_Out_Avito.md` — OAuth refresh, Redis cache
- `06_API/workflows_info/API_Android_Auth.md` — full auth flow
