# Eldoleado Project — CORE_NEW

> Unified project context file for Claude and developers

**Last updated:** 2025-12-11

---

## Quick Commands

When user says:
- **"update context"** → `python scripts/update_core_context.py`
- **"stop"** → Kill all background tasks

---

## CORE_NEW — New Architecture

### Philosophy: "People talk. Machine keeps records."

```
Old approach:  Client → Appeals → Devices → Problems (50 tables)
New approach:  Client → Dialogs (with context inside) (13 tables)
```

- For humans — natural messaging communication
- For machines — routine, accounting, tables (AI under the hood)
- NO kanban boards, NO form filling

---

## Documentation Index

| Category | Path | Description |
|----------|------|-------------|
| **CORE_NEW** | `CORE_NEW/docs/` | New architecture documentation |
| **Context** | `CORE_NEW/CONTEXT.md` | Quick project status overview |
| **Old** | `Old/docs/` | Old documentation (archive) |
| **Plans** | `Plans/` | Business plans and requirements |

### Key Documents (CORE_NEW)

| Document | Description |
|----------|-------------|
| `CORE_NEW/CONTEXT.md` | Quick Stats + project status |
| `CORE_NEW/docs/00_VISION.md` | Product vision |
| `CORE_NEW/docs/01_CORE_DESIGN.md` | Core architecture, glossary |
| `CORE_NEW/docs/02_DATABASE_SCHEMA.md` | PostgreSQL: 13 elo_* tables |
| `CORE_NEW/docs/03_NEO4J_SCHEMA.md` | Neo4j: Client, Device, Problem |
| `CORE_NEW/docs/04_API_CONTRACTS.md` | API v2 contracts |
| `CORE_NEW/docs/05_AI_ARCHITECTURE.md` | AI: 7 levels, Prompt-in-Request |

---

## Project Structure

```
CORE_NEW/               # New architecture (ACTIVE)
  ├── docs/             # Documentation
  ├── CONTEXT.md        # Project status
  └── migrations/       # SQL migrations (TODO)

app/                    # Android app (Kotlin)
MCP/                    # MCP servers (Python FastAPI)
  ├── mcp-telegram/
  ├── mcp-whatsapp/
  ├── mcp-avito/
  ├── mcp-vk/
  ├── mcp-max/
  ├── mcp-form/
  ├── api-android/
  └── shared/

scripts/                # Automation scripts
Plans/                  # Planning documents

Old/                    # Old architecture (ARCHIVE)
  ├── docs/             # Old documentation
  ├── database/         # Old migrations
  ├── n8n_workflows/    # Old workflows
  ├── scripts/          # Old sync scripts
  └── KNOWLEDGE_BASE.md # Old knowledge base
```

---

## Servers & Services

### RU Server (45.144.177.128)

| Service | Port | Description |
|---------|------|-------------|
| mcp-avito | 8765 | Avito Messenger API |
| mcp-vk | 8767 | VK Community API |
| mcp-max | 8768 | MAX (VK Teams) API |
| mcp-form | 8770 | Web forms API |
| api-android | 8780 | Android API Gateway |
| Neo4j | 7474/7687 | Graph database |
| Redis | 6379 | Cache |

### Finnish Server (217.145.79.27)

| Service | Port | Description |
|---------|------|-------------|
| mcp-telegram | 8767 | Telegram Bot API |
| mcp-whatsapp | 8766 | WhatsApp (Wappi.pro) |

### n8n Server (185.221.214.83)

| Service | Port | Description |
|---------|------|-------------|
| n8n | 5678 | https://n8n.n8nsrv.ru |
| PostgreSQL | 6544 | Main database |
| Redis | 6379 | n8n cache |

---

## Database Access (no passwords in code)

SSH keys configured. Passwords embedded in commands.

### PostgreSQL (185.221.214.83)
```bash
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'QUERY'"
```

### Neo4j (45.144.177.128)
```bash
ssh root@45.144.177.128 "docker exec neo4j cypher-shell -a 'bolt+ssc://localhost:7687' -u neo4j -p 'Mi31415926pS' 'QUERY'"
```

### n8n API (READ ONLY — changes via UI)
```bash
# List workflows
curl -s "https://n8n.n8nsrv.ru/api/v1/workflows" -H "X-N8N-API-KEY: n8n_api_key_below"

# Specific workflow
curl -s "https://n8n.n8nsrv.ru/api/v1/workflows/{id}" -H "X-N8N-API-KEY: n8n_api_key_below"

# API Key (expires 2026-01-01):
# eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzY0NzY4NzE0LCJleHAiOjE3NzI0ODE2MDB9.JzC21XpXh7188Qlx2xWpZPHQysksg_Jj0hWuTgy6PmQ
```

### Local scripts (Windows)
```cmd
scripts\db\pg.cmd "SELECT * FROM elo_tenants;"
scripts\db\neo4j.cmd "MATCH (n) RETURN count(n);"
```

---

## Database

**Connection:** `postgresql://supabase_admin:***@185.221.214.83:6544/postgres`

### CORE_NEW Tables (elo_*) — 26 tables

| Category | Tables |
|----------|--------|
| Core (5) | elo_tenants, elo_clients, elo_dialogs, elo_events, elo_operators |
| Directories (3) | elo_verticals, elo_tenant_verticals, elo_price_list |
| AI (2) | elo_ai_extractions, elo_ai_suggestions |
| Channels (1) | elo_channel_accounts |
| Tasks (2) | elo_tasks, elo_task_updates |
| Graph mirrors (7) | elo_messages, elo_issues, elo_symptoms, elo_diagnoses, elo_repairs, elo_facts, elo_settings |
| Type directories (4) | elo_symptom_types, elo_diagnosis_types, elo_repair_actions, elo_problem_categories |
| Micro-funnel (2) | elo_funnel_stages, elo_dialog_stage_history |

---

## AI Architecture (7 levels)

```
┌─────────────────────────────────────────────────────────┐
│  7. MCP Channels (Telegram, WhatsApp, Avito, VK, MAX)   │
├─────────────────────────────────────────────────────────┤
│  6. Response Builder (channel-specific formatting)      │
├─────────────────────────────────────────────────────────┤
│  5. Dialog Engine (message processing, graph)           │
├─────────────────────────────────────────────────────────┤
│  4. Universal Tools (workers with prompts)              │
├─────────────────────────────────────────────────────────┤
│  3. Universal Orchestrator (blind executor)             │
├─────────────────────────────────────────────────────────┤
│  2. Request Builder (rules → AI → rules)                │
├─────────────────────────────────────────────────────────┤
│  1. Context Builder (PostgreSQL + Neo4j)                │
└─────────────────────────────────────────────────────────┘
```

### Key Concepts

| Concept | Description |
|---------|-------------|
| **Prompt-in-Request** | Prompts passed in request, not hardcoded |
| **Stick-Carrot-Stick** | Rules → AI freedom → Validation |
| **ai_freedom_level** | 0-100, AI strictness regulator |
| **Dialog-centric** | Dialog is the central entity, not appeal |

---

## Scripts

| Script | Purpose |
|--------|---------|
| `update_core_context.py` | Update CORE_NEW/CONTEXT.md |
| `test_connections.py` | Test server connections |
| `n8n_manager.py` | Manage n8n workflows |

---

## Conventions

- **CORE_NEW tables:** prefix `elo_`
- **Neo4j labels:** no prefix (Client, Device, Problem)
- **CORE_NEW workflows:** prefix `ELO_` (TODO)
- Documentation in English

---

## n8n Rules

**IMPORTANT: n8n is READ ONLY. Modifying workflows via API is PROHIBITED.**

- Reading workflows via API — OK
- Creating/modifying workflows via API — PROHIBITED
- For changes: user imports manually via UI

---

## Session Workflow

**On start:**
1. `git pull`
2. Read `Start.md`
3. Read `CORE_NEW/CONTEXT.md`

**On finish:**
1. Update `Start.md`
2. `python scripts/update_core_context.py`
3. `git add -A && git commit && git push`

---

## History

### 2025-12-11
- Translated all docs to English (token optimization)

### 2025-12-09
- Created CORE_NEW system (13 tables instead of 50)
- Created AI architecture (7 levels)
- Moved old architecture to Old/
- Created CORE_NEW/CONTEXT.md
- Created scripts/update_core_context.py

### 2025-12-07
- Fixed BAT Batch Debouncer
- Created Redis debug guide

### 2025-12-06
- Deployed all MCP servers v2.0.0 multi-tenant
