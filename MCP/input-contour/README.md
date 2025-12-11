# Input Contour MCP (stub)

Minimal FastAPI service to validate the MCP contract and queue/dispatch flow.

## What it does (now)
- `/ingest` (POST): accepts ingress payload, ensures `message_id`/`trace_id`, enqueues to Redis list `queue:incoming`.
- Background worker: pops from Redis, groups by `tenant:channel:chat_id`, aggregates texts, and POSTs to a Core stub (`CORE_URL`).
- Health: `/health`.

## What it does NOT do yet
- No real debounce timing (immediate aggregate per batch pop).
- No Client/Dialog resolve (left to downstream).
- No idempotency store beyond `message_id` carried through.
- No metrics/tracing; minimal logging to stdout.

## Run
```bash
cd MCP/input-contour
python -m venv .venv
source .venv/bin/activate  # on Windows: .venv\Scripts\activate
pip install -r requirements.txt

export REDIS_URL=redis://localhost:6379/0
export CORE_URL=http://localhost:9000/core/ingest  # stub endpoint
export DEBOUNCE_SECONDS=10
export MAX_WAIT_SECONDS=300
export POLL_INTERVAL=1.0
export BATCH_SIZE=100

uvicorn main:app --reload --port 8080
```

## Env vars
- `REDIS_URL` — Redis DSN.
- `CORE_URL` — downstream Core (stub) endpoint for egress.
- `DEBOUNCE_SECONDS`, `MAX_WAIT_SECONDS` — reserved for future true debounce.
- `POLL_INTERVAL` — worker poll interval (seconds).
- `BATCH_SIZE` — number of LPOP attempts per poll.

## Next steps (per MCP_TRANSITION.md)
- Implement real debounce (per-tenant keys with TTL timers).
- Add idempotency store for `message_id`.
- Add Client/Dialog Resolver calls and propagate ids to egress.
- Add metrics/logging/tracing; backoff/retries with DLQ.
- Support media aggregation and validation.

