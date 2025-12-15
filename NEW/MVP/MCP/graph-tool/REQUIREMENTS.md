# Graph Tool MCP — Requirements

## Overview
Blind Cypher executor. Resolves query by code from PostgreSQL, substitutes params, executes in Neo4j, returns result.

## Endpoints
### POST /query
Input:
```json
{
  "query_code": "get_client_context",
  "params": { "client_id": "uuid" }
}
```
Logic:
1) SELECT query,param_schema FROM cypher_queries WHERE code=$query_code AND is_active=true
2) Execute Cypher with params (placeholders `$param`)
3) Return data

Success:
```json
{ "success": true, "data": {...}, "execution_time_ms": 12 }
```
Error:
```json
{ "success": false, "error": "Query not found: unknown_code", "execution_time_ms": 1 }
```

### GET /health
```json
{ "status": "ok", "postgres": true, "neo4j": true }
```

## Env
```
DATABASE_URL=postgresql://user:pass@185.221.214.83:6544/postgres
NEO4J_URI=bolt://45.144.177.128:7687
NEO4J_USER=neo4j
NEO4J_PASSWORD=Mi31415926pS
HOST=0.0.0.0
PORT=8773
```

## Files
```
MCP/graph-tool/
├── main.py
├── config.py
├── db.py
├── neo4j_client.py
├── requirements.txt
├── Dockerfile
└── REQUIREMENTS.md
```

## Notes
- Blind executor: no business logic, only lookup + execute + return.
- Uses asyncpg and neo4j async driver.
- Mock not needed if DB up; will return "Query not found" if missing.***

