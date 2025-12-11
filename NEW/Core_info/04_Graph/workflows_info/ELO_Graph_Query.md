# ELO_Graph_Query

> n8n Polygon — Blind Cypher executor via Graph Tool MCP

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `NEW` (to be created) |
| **File** | `NEW/workflows/ELO_Graph/ELO_Graph_Query.json` |
| **Trigger** | Webhook POST `/webhook/elo-graph-query` |
| **Called from** | Core workflows, Client Contour |
| **Calls** | Graph Tool MCP (8773) → Neo4j |

---

## Purpose

n8n polygon for debugging Graph Tool calls before production:

1. Receive query request with `query_code` and `params`
2. Forward to Graph Tool MCP (blind executor)
3. Return results

**Production:** Use Graph Tool MCP directly (HTTP call to 8773)
**Polygon:** This n8n workflow for debugging, logging, transformations

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CALLER                                          │
│           (Core workflows, Client Contour, manual debug)                     │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                   n8n Polygon: ELO_Graph_Query                              │
│                    POST /webhook/elo-graph-query                             │
├─────────────────────────────────────────────────────────────────────────────┤
│  1. Validate query_code and params                                          │
│  2. Log request (debug mode)                                                │
│  3. Forward to Graph Tool MCP                                               │
│  4. Log response (debug mode)                                               │
│  5. Return result                                                           │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                      Graph Tool MCP (8773)                                   │
│                        (blind executor)                                      │
├─────────────────────────────────────────────────────────────────────────────┤
│  1. SELECT query FROM elo_cypher_queries WHERE code = $query_code           │
│  2. Substitute params into query                                            │
│  3. Execute in Neo4j                                                        │
│  4. Return result                                                           │
└───────────────────────────────┬─────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                            Neo4j                                             │
│                   bolt+ssc://45.144.177.128:7687                            │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Input Data

**Request:**
```json
{
  "query_code": "get_client_context",
  "params": {
    "client_id": "550e8400-e29b-41d4-a716-446655440000"
  },
  "trace_id": "trace_xyz789"
}
```

---

## Output Data

**Success:**
```json
{
  "success": true,
  "data": [
    {
      "client": {
        "pg_id": "550e8400-...",
        "name": "Ivan Petrov",
        "phone": "+79991234567"
      },
      "devices": [...],
      "issues": [...],
      "traits": [...]
    }
  ],
  "execution_time_ms": 45,
  "trace_id": "trace_xyz789"
}
```

**Error:**
```json
{
  "success": false,
  "error": "Query not found: invalid_code",
  "execution_time_ms": 5,
  "trace_id": "trace_xyz789"
}
```

---

## Nodes

### 1. Webhook Trigger

| Parameter | Value |
|----------|----------|
| **Type** | Webhook |
| **Path** | `/webhook/elo-graph-query` |
| **Method** | POST |
| **Response** | Respond immediately with result |

---

### 2. Validate Input

```javascript
const input = $input.first().json;

// Required field
if (!input.query_code) {
  throw new Error('Missing required field: query_code');
}

// Default params to empty object
const params = input.params || {};

return {
  query_code: input.query_code,
  params: params,
  trace_id: input.trace_id || `trace_${Date.now()}`,
  received_at: new Date().toISOString()
};
```

---

### 3. Log Request (Debug)

```javascript
// Only in debug mode - log to console
const input = $input.first().json;

console.log(`[GRAPH] Request: ${input.query_code}`, {
  params: input.params,
  trace_id: input.trace_id
});

return input;
```

---

### 4. Call Graph Tool MCP

| Parameter | Value |
|----------|----------|
| **Type** | HTTP Request |
| **URL** | `http://45.144.177.128:8773/query` |
| **Method** | POST |
| **Headers** | `Content-Type: application/json` |
| **Timeout** | 30s |

**Body:**
```json
{
  "query_code": "{{ $json.query_code }}",
  "params": {{ JSON.stringify($json.params) }}
}
```

---

### 5. Handle Response

```javascript
const input = $('Validate Input').first().json;
const response = $input.first().json;

// Add trace_id to response
return {
  ...response,
  trace_id: input.trace_id
};
```

---

### 6. Log Response (Debug)

```javascript
const response = $input.first().json;

console.log(`[GRAPH] Response: ${response.success ? 'OK' : 'ERROR'}`, {
  execution_time_ms: response.execution_time_ms,
  trace_id: response.trace_id,
  error: response.error
});

return response;
```

---

### 7. Respond to Webhook

Return the response directly to caller.

---

## Flow Diagram

```
Webhook POST /webhook/elo-graph-query
         │
         ▼
┌─────────────────────┐
│  1. Validate Input  │  ← query_code required
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ 2. Log Request      │  ← Debug logging
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ 3. Call Graph Tool  │  ← HTTP POST to 8773
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ 4. Handle Response  │  ← Add trace_id
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ 5. Log Response     │  ← Debug logging
└──────────┬──────────┘
           │
           ▼
    Return to Caller
```

---

## Available Query Codes

Queries stored in `elo_cypher_queries` table:

| Code | Purpose | Required Params |
|------|---------|-----------------|
| `get_client_context` | Full client context | `client_id` |
| `create_client` | Create client node | `client_id`, `tenant_id`, `name`, `phone` |
| `create_device` | Create device with owner | `device_id`, `client_id`, `brand`, `model` |
| `create_issue` | Create issue for device | `issue_id`, `device_id`, `tenant_id`, `vertical_id` |
| `add_symptom` | Add symptom to issue | `symptom_id`, `issue_id`, `text`, `symptom_type_id` |
| `add_diagnosis` | Add diagnosis to issue | `diagnosis_id`, `issue_id`, `text`, `diagnosis_type_id` |
| `add_repair` | Add repair to issue | `repair_id`, `issue_id`, `text`, `repair_action_id`, `cost` |
| `add_client_trait` | Add trait to client | `client_id`, `type`, `value`, `confidence` |
| `get_device_history` | Device repair history | `device_id` |
| `find_similar_cases` | Similar diagnoses | `vertical_id`, `symptoms` |

---

## Example Calls

### Get Client Context

```json
{
  "query_code": "get_client_context",
  "params": {
    "client_id": "550e8400-e29b-41d4-a716-446655440000"
  }
}
```

### Create Client

```json
{
  "query_code": "create_client",
  "params": {
    "client_id": "550e8400-e29b-41d4-a716-446655440000",
    "tenant_id": "a0000000-0000-0000-0000-000000000001",
    "name": "Ivan Petrov",
    "phone": "+79991234567"
  }
}
```

### Create Device

```json
{
  "query_code": "create_device",
  "params": {
    "device_id": "dev-001",
    "client_id": "550e8400-...",
    "brand": "Apple",
    "model": "iPhone 14 Pro",
    "color": "Space Black"
  }
}
```

### Add Symptom

```json
{
  "query_code": "add_symptom",
  "params": {
    "symptom_id": "sym-001",
    "issue_id": "issue-001",
    "text": "Screen doesn't respond to touch",
    "symptom_type_id": "screen_touch"
  }
}
```

### Find Similar Cases

```json
{
  "query_code": "find_similar_cases",
  "params": {
    "vertical_id": 1,
    "symptoms": ["screen_cracked", "no_touch"]
  }
}
```

---

## Error Handling

| Error | Action |
|-------|--------|
| Missing query_code | Return 400 |
| Query not found in DB | Return `{success: false, error: "Query not found"}` |
| Neo4j connection error | Return `{success: false, error: "Neo4j unavailable"}` |
| Invalid params | Return `{success: false, error: "..."}` |
| Timeout | Return 504 |

---

## Direct MCP Call (Production)

For production, skip n8n polygon and call Graph Tool directly:

```bash
curl -X POST http://45.144.177.128:8773/query \
  -H "Content-Type: application/json" \
  -d '{
    "query_code": "get_client_context",
    "params": {"client_id": "..."}
  }'
```

---

## Health Check

```bash
curl http://45.144.177.128:8773/health
# {"status":"ok","postgres":true,"neo4j":true}
```

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Service | Graph Tool MCP (8773) | Cypher execution |
| Database | PostgreSQL | Query storage (elo_cypher_queries) |
| Database | Neo4j | Graph storage |

---

## Database Tables

| Table | Purpose |
|-------|---------|
| `elo_cypher_queries` | Stored Cypher queries by code |

**elo_cypher_queries schema:**
```sql
CREATE TABLE elo_cypher_queries (
  id SERIAL PRIMARY KEY,
  code VARCHAR(100) UNIQUE NOT NULL,
  query TEXT NOT NULL,
  param_schema JSONB,
  description TEXT,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

## Configuration

```env
# Graph Tool MCP
GRAPH_TOOL_URL=http://45.144.177.128:8773

# Or local (if running in same Docker network)
GRAPH_TOOL_URL=http://graph-tool:8773
```

---

**Document:** ELO_Graph_Query.md
**Date:** 2025-12-11
**Author:** Claude
**Status:** n8n polygon (for debugging before direct MCP calls)
