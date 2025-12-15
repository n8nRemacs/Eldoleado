# Client Contour MCP (mock)

FastAPI mock service that implements the /resolve endpoint per REQUIREMENTS.md, resolves IDs with stubs, and forwards to Core (fire-and-forget).

## What it does (MVP mock)
- `/resolve` (POST): accepts batched message from Input Contour, mock-resolves tenant/client/dialog, sends payload to `CORE_URL` (fire-and-forget), returns IDs.
- `/health`: returns static status (postgres/neo4j are false in mock).

## What it does NOT do yet
- No real DB/Neo4j lookups.
- No DLQ; relies on Input Contour retries if needed.
- No metrics/tracing.

## Env vars
- `CORE_URL` — target Core ingest (currently n8n webhook).
- `DATABASE_URL` — placeholder, not used in mock.
- `NEO4J_URI`, `NEO4J_USER`, `NEO4J_PASSWORD` — placeholders, not used in mock.
- `REDIS_URL` — placeholder if DLQ is added later.
- `HOST` (default `0.0.0.0`), `PORT` (default `8772`).

## Run
```bash
cd MCP/client-contour
python -m venv .venv
source .venv/bin/activate  # Windows: .venv\Scripts\activate
pip install -r requirements.txt

export CORE_URL=https://n8n.n8nsrv.ru/webhook/elo-core-ingest
export HOST=0.0.0.0
export PORT=8772

uvicorn main:app --reload --host $HOST --port $PORT
```

## Files
- `main.py` — FastAPI app with `/resolve`, `/health`
- `config.py` — env settings
- `resolvers/tenant.py` — mock tenant resolver
- `resolvers/client.py` — mock client resolver
- `resolvers/dialog.py` — mock dialog resolver
- `requirements.txt`
- `REQUIREMENTS.md` — functional spec

## Next steps
- Replace mock resolvers with real DB/Neo4j lookups.
- Add DLQ for unknown tenant/failed posts if required.
- Add metrics/tracing and stricter validation.

