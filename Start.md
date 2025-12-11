# START - Context for Continuing Work

## FIRST — Sync

**If reading this file SECOND time after git pull — SKIP this block and go to next section!**

```bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
```

After git pull — REREAD this file from the beginning (Start.md), starting from the next section (skipping this sync block to avoid loops).

---

## Last update date and time
**December 11, 2025, 16:00 (UTC+4)**

---

## CURRENT PROJECT STATUS

### Strategy defined

**Product:** Dialog-centric CRM for service centers

**Philosophy:** "People talk. Machine keeps records."

**MVP Vertical:** Phone Repair + Buy/Sell (trade-in, used)

**WOW-effect (chosen):** "No lost customers" — AI responds at 11 PM, schedules for tomorrow

**Strategy:** NOT MVP, but full vertical product with maximum competitor gap

---

## OPEN QUESTIONS (to resolve in next session!)

### Graph — 4 technical questions

1. **Register vs Tracker — duplication or different scenarios?**
   - **Touchpoint Register** (`/neo4j/touchpoint/register`): Neo4j + PostgreSQL, supports mutual
   - **Touchpoint Tracker** (`/neo4j/touchpoint`): Neo4j only, adds ABOUT_DEVICE/ABOUT_PROBLEM with confidence

2. **Direction — who determines inbound/outbound/mutual?**
   - Calling workflow passes ready value?
   - Or Graph determines by is_new_client logic?

3. **enrichment_paths — what table is this?**
   - Channel conversion paths like "telegram → collect phone"?

4. **When to call which touchpoint?**
   - Register → for all incoming/outgoing?
   - Tracker → only when AI detected device mention?

---

## COMPETITIVE ADVANTAGE

```
Competitors:                          ELO:
─────────────────────────────────────────────────────────
Channel = 600₽/month                  Channel = 0₽ (smartphone-server)
Manual price list                     Parser + auto-prices
Lost call = lost customer             Call → Graph → Messenger
"Fill out the form"                   AI understands "14 pro max"
Responded in 2 hours                  AI responded at 11 PM
Kanban for 3 days                     15 minutes to deal
```

**Key insight:** B2C service = 15 minutes to decision, response in 1-2 minutes. amoCRM/Bitrix with multi-day kanban boards DON'T FIT.

---

## KILLER FEATURES (from ROADMAP.md)

| # | Feature | Description | ROI |
|---|---------|-------------|-----|
| 1 | **Smartphone-server** | Android app as server for free WhatsApp/Avito/MAX | 0₽ instead of 600-3000₽/month |
| 2 | **Price parser** | Store parsing → normalization → auto-prices | Current prices without manual entry |
| 3 | **Voice→Graph→Messenger** | Call → transcription → Neo4j → continue in chat | Calls not lost |
| 4 | **QR identification** | 4 types: tenant, device, repair, promo | Quick client identification |
| 5 | **Remonline/LiveSklad** | Two-way sync | No double entry |
| 6 | **Self-learning** | Operator feedback + real repairs | AI gets smarter |

---

## ANDROID APP — TWO MODES

```
┌─────────────────────────────────────────────────────────┐
│              ELO Android App                            │
│              (Google Play / RuStore)                    │
├─────────────────────────────────────────────────────────┤
│  MODE 1: OPERATOR (always active)                       │
│  ├── Push notifications                                 │
│  ├── Client responses (text, voice)                     │
│  ├── Dialog history                                     │
│  └── AI suggestions                                     │
├─────────────────────────────────────────────────────────┤
│  MODE 2: SERVER (hidden, by backend flag)               │
│  ├── Foreground Service                                 │
│  ├── WhatsApp/Avito/MAX modules (reverse-engineered)    │
│  └── WebSocket → ELO Backend                            │
└─────────────────────────────────────────────────────────┘
```

---

## AI TOOLS (from ROADMAP.md)

| Category | Tools |
|----------|-------|
| **Extraction** | device_extract, issue_extract, intent_classify, appointment_extract |
| **Actions** | appointment_create, appointment_reschedule, parts_search, order_create |
| **Lookup** | client_lookup, device_history, parts_catalog_search, knowledge_lookup, qr_resolve |
| **Generation** | response_generate, summary_generate, greeting_generate |
| **External** | remonline_sync, livesklad_sync, voice_transcribe |

---

## DEVELOPMENT APPROACH (agreed)

```
1. Work through all blocks (understand what exists)
   ├── Channel Layer ✅ (12/12)
   ├── Input Contour ✅ (5/5)
   ├── Core (analyzed, not documented)
   ├── Graph (4 questions open ↑)
   └── API (TODO)

2. Create detailed structure (how it should be)

3. DB structure (elo_* tables + new from ROADMAP)

4. Create workers

5. Debug
```

---

## WHAT'S DONE — FULL HISTORY

### Session 12.11.2025 (day) — MCP Contours Architecture + AI Tool

**Участники:** Senior (Claude Opus) + Junior (Claude Cursor)

**АРХИТЕКТУРНОЕ РЕШЕНИЕ:**
Создана новая 4-контурная архитектура с MCP "слепыми исполнителями":

```
MCP Channels → Input (8771) → Client (8772) → Core (n8n) → Graph (8773)
                                                    ↓
                                              AI Tool (8774)
```

**Работа Senior (Claude Opus):**

| # | Задача | Файлы | Статус |
|---|--------|-------|--------|
| 1 | AI Tool MCP (8774) | `MCP/ai-tool/main.py, config.py, Dockerfile, requirements.txt` | ✅ |
| 2 | AI Tool документация | `NEW/Core_info/06_AI_Tool/AI_TOOL_OVERVIEW.md` | ✅ |
| 3 | ELO_AI_Extract.md | `NEW/Core_info/06_AI_Tool/workflows_info/ELO_AI_Extract.md` | ✅ |
| 4 | ELO_AI_Chat.md | `NEW/Core_info/06_AI_Tool/workflows_info/ELO_AI_Chat.md` | ✅ |
| 5 | n8n JSON workflows | `NEW/workflows/ELO_AI/ELO_AI_Extract.json, ELO_AI_Chat.json` | ✅ |
| 6 | API_CONTRACTS.md | Добавлен AI Tool (8774) | ✅ |
| 7 | Junior task | `.claude/inbox.md` — задание на n8n workflows | ✅ |
| 8 | Junior review | `.claude/outbox.md` — ответы на вопросы | ✅ |

**Работа Junior (Claude Cursor):**

| # | Задача | Файлы | Статус |
|---|--------|-------|--------|
| 1 | ELO_Input_Ingest.json | `workflows_to_import/` | ✅ |
| 2 | ELO_Input_Worker.json | `workflows_to_import/` | ✅ |
| 3 | ELO_Client_Resolve.json | `workflows_to_import/` | ✅ |
| 4 | ELO_Graph_Query.json | `workflows_to_import/` | ✅ |
| 5 | ELO_Core_Ingest.json | `workflows_to_import/` | ✅ |
| 6 | Channel IN (6 шт) | Telegram, WhatsApp, Avito, VK, MAX, Form | ✅ |
| 7 | Channel OUT (5 шт) | Telegram, WhatsApp, Avito, VK, MAX | ✅ |

**Новые MCP сервисы:**

| Service | Port | Purpose | Status |
|---------|------|---------|--------|
| AI Tool | 8774 | /extract + /chat (OpenRouter) | ✅ Created |
| Graph Tool | 8773 | /query (Neo4j proxy) | ✅ Documented |
| Input Contour | 8771 | /ingest (Redis queue) | 📝 Documented |
| Client Contour | 8772 | /resolve (PostgreSQL) | 📝 Documented |

**n8n v2.0 Compliance:**
- Webhook typeVersion: 2
- Code typeVersion: 2
- HTTP Request typeVersion: 4.2
- respondToWebhook typeVersion: 1.1
- No Python Code Node
- No process.env in Code

**Git commits:**
- `0b32d20` — Add AI Tool MCP (8774) + n8n polygon documentation
- `3c1b8e7` — Add ELO_AI n8n polygon workflows (JSON)
- `cafd516` — Update Junior task: add AI Tool workflows + answer questions
- `cb0c105` — Answer Junior's questions: mocks sufficient

---

### Session 12.11.2025 (night) — Commercial Strategy + ROADMAP

**Commercial analysis:**
- Analyzed competitors: amoCRM, Bitrix24, Yclients, specialized CRMs
- Defined USP: AI understands client, omnichannel (7 channels), simplicity
- Chose monetization: Freemium (minimal at cost level 300-500₽)
- Chose WOW-effect: **"No lost customers"**

**Created ROADMAP.md (~1200 lines):**
- Killer Features with detailed diagrams
- Smartphone-server (one app — two modes)
- Price parser with normalization workflow (4 steps)
- Voice → Graph → Messenger (6 stages)
- QR identification (4 types)
- Remonline/LiveSklad integrations (API + webhooks)
- AI Tools (full catalog)
- SQL schemas for new tables
- Pricing (draft): Free/Minimal/Basic/Business
- WOW-demo scenario for first client

**Created ARCHITECTURE_SYNC.md (~550 lines):**
- Killer features mapping to 7 AI architecture levels
- New blocks: Device Gateway, Price Engine, Learning Engine, External Integrations
- Integration with existing MCP channels
- Implementation order (14 steps)

**Git commit:** `890c6ef` — "Docs: Product Roadmap + Architecture Sync + AI Tools" (2037 insertions)

---

### Session 12.10.2025 (night) — OLD Architecture Documentation

**Created documentation structure:**
- `NEW/Core_info/` — folder with block documentation
- `INDEX.md` — navigation for all blocks
- `HOW_TO_DOCUMENT.md` — documentation instruction (templates for Code/SQL/Redis/HTTP nodes)

**Channel Layer documented ✅ (12/12):**
- `01_Channel_Layer/workflows_info/` — 7 ELO_In + 5 ELO_Out
- Patterns: Standard (Redis queue), Direct (no Redis)

**Input Contour documented ✅ (5/5):**
- `02_Input_Contour/workflows_info/`:
  - INPUT_CONTOUR_OVERVIEW.md
  - ELO_Core_Tenant_Resolver.md
  - ELO_Core_Queue_Processor.md (every 5 sec)
  - ELO_Core_Batch_Debouncer.md (10 sec silence, 300 sec max)
  - ELO_Core_Client_Resolver.md

**Input Contour Redis keys:**
```
queue:incoming              — global incoming queue
queue:processor:lock        — processor mutex
queue:batch:{channel}:{id}  — per-chat message queue
lock:batch:{channel}:{id}   — per-chat processing lock (TTL 300s)
last_seen:{channel}:{id}    — timestamp of last message
```

**Core analyzed (not documented):**
- Read all workflows: Appeal_Manager, AI_Router, Task_Dispatcher, AI_Universal_Worker, Client_Creator
- Postponed until other blocks are worked through
- **Core Redis keys:** `ai_extraction_queue`, `batch:{batch_id}:status`

**Graph started (questions open):**
- Read all 5 Neo4j workflows
- Created `04_Graph/workflows_info/GRAPH_OVERVIEW.md`
- **5 webhooks:** /neo4j/context, /neo4j/crud, /neo4j/sync, /neo4j/touchpoint/register, /neo4j/touchpoint

**Git commits:**
- `2ec383b` — "Docs: Core_info documentation structure + Channel Layer + Input Contour + Graph overview" (138 files, 6352 insertions)
- `1eb6945` — "Docs: Detailed NEXT_STEPS.md with full session report" (499 insertions)

---

### Session 12.09.2025 (late evening) — n8n Workflows for CORE_NEW

**SQL migrations applied ✅:**
- File: `CORE_NEW/migrations/001_elo_tables.sql`
- All 13 elo_* tables created in PostgreSQL

**ELO Workflows created:**
- `ELO_In_*` — input workers (7 pcs) — renamed from BAT
- `ELO_Out_*` — output workers (5 pcs) — renamed from BAT
- `ELO_Core_Tenant_Resolver` — tenant identification by elo_channel_accounts

**Data Contract specification ✅:**
- File: `CORE_NEW/docs/06_DATA_CONTRACT.md`
- Minimal data package between workflows
- Passing rules: tenant_id → client_id → dialog_id

**Batching:**
- Timeout in `elo_tenants.settings.batch_timeout_sec` (default: 10 sec)
- Redis queues: `queue:elo:{channel}:{chat_id}`

---

### Session 12.09.2025 (night) — Tasks in PostgreSQL

**Task tables added ✅:**
- `elo_tasks` — tasks for employees
- `elo_task_updates` — update history
- **Decision: Tasks ONLY in PostgreSQL, not Neo4j** (CRUD, not graph)

**Total: 13 tables** with `elo_` prefix

---

### Session 12.09.2025 (evening) — AI Architecture

**AI Architecture — CREATED ✅:**
- File: `CORE_NEW/docs/05_AI_ARCHITECTURE.md`
- 7 levels: from data to messengers

**Key concepts:**
- **Prompt-in-Request** — prompts in request, not hardcoded
- **Stick-Carrot-Stick** — rules → AI freedom → validation
- **ai_freedom_level** — strictness regulator (0-100)
- **Graph + Extractor** — bidirectional connection

---

### Session 12.09.2025 (day) — CORE_NEW Architecture

**Created:**
- `CORE_NEW/docs/00_VISION.md` — Vision Document
- `CORE_NEW/docs/02_DATABASE_SCHEMA.md` — PostgreSQL Schema (11→13 tables)
- `CORE_NEW/docs/03_GRAPH_SCHEMA.md` — Neo4j Schema
- `CORE_NEW/docs/04_API_CONTRACTS.md` — API v2 Contracts

**Reason for CORE_NEW transition:** Found 10 duplicate devices "Apple iPhone 14 Pro" in one appeal. Decided to rebuild system properly.

---

## CURRENT PROJECT STATE

### CORE_NEW Documentation:

| File | Description | Status |
|------|-------------|--------|
| `CORE_NEW/docs/00_VISION.md` | Product vision | ✅ |
| `CORE_NEW/docs/01_CORE_DESIGN.md` | Core architecture, glossary | ✅ |
| `CORE_NEW/docs/02_DATABASE_SCHEMA.md` | PostgreSQL: 13 elo_* tables | ✅ |
| `CORE_NEW/docs/03_NEO4J_SCHEMA.md` | Neo4j: Client, Device, Problem | ✅ |
| `CORE_NEW/docs/04_API_CONTRACTS.md` | API v2 contracts | ✅ |
| `CORE_NEW/docs/05_AI_ARCHITECTURE.md` | AI: 7 levels | ✅ |
| `CORE_NEW/docs/06_DATA_CONTRACT.md` | Data package between workflows | ✅ |

### NEW Documentation (workflows):

| Folder | Content | Status |
|--------|---------|--------|
| `NEW/Core_info/01_Channel_Layer/` | 7 ELO_In + 5 ELO_Out | ✅ 12/12 |
| `NEW/Core_info/02_Input_Contour/` | Overview + workflows | ✅ |
| `NEW/Core_info/03_Client_Contour/` | Overview + ELO_Client_Resolve | ✅ |
| `NEW/Core_info/04_Graph/` | Overview + ELO_Graph_Query | ✅ |
| `NEW/Core_info/05_Core_Contour/` | Overview + workflows | ✅ |
| `NEW/Core_info/06_AI_Tool/` | Overview + ELO_AI_Extract + ELO_AI_Chat | ✅ NEW |
| `NEW/Core_info/API_CONTRACTS.md` | All webhooks and APIs | ✅ |

### n8n Workflows to Import:

| Folder | Files | Status |
|--------|-------|--------|
| `NEW/workflows/ELO_AI/` | ELO_AI_Extract.json, ELO_AI_Chat.json | ✅ Ready |
| `workflows_to_import/` | 15+ mock workflows (by Junior) | ✅ Ready |

### Product documentation:

| File | Description | Lines |
|------|-------------|-------|
| `NEW/ROADMAP.md` | Killer features, AI tools, SQL schemas, pricing | ~1200 |
| `NEW/ARCHITECTURE_SYNC.md` | Mapping to 7 architecture levels | ~550 |
| `NEW/NEXT_STEPS.md` | Detailed previous session report | ~550 |

---

## FOLDER STRUCTURE

```
Eldoleado/
├── CORE_NEW/               # Architecture (documentation)
│   ├── docs/               # 7 documents
│   ├── migrations/         # SQL migrations
│   └── CONTEXT.md          # Quick overview
│
├── NEW/                    # Workflows and roadmap
│   ├── Core_info/          # Block documentation
│   │   ├── 01_Channel_Layer/       # MCP channels (IN/OUT)
│   │   ├── 02_Input_Contour/       # Input processing (8771)
│   │   ├── 03_Client_Contour/      # Client resolution (8772)
│   │   ├── 04_Graph/               # Neo4j Graph Tool (8773)
│   │   ├── 05_Core_Contour/        # Business logic (n8n)
│   │   ├── 06_AI_Tool/             # AI operations (8774) ← NEW
│   │   └── API_CONTRACTS.md        # All APIs
│   ├── workflows/          # JSON workflow files
│   │   ├── ELO_InOut/      # Channel workflows
│   │   └── ELO_AI/         # AI Tool workflows ← NEW
│   ├── ROADMAP.md          # Product roadmap
│   ├── ARCHITECTURE_SYNC.md
│   └── NEXT_STEPS.md
│
├── MCP/                    # MCP servers (Python FastAPI)
│   ├── ai-tool/            # AI Tool (8774) ← NEW
│   ├── graph-tool/         # Graph Tool (8773)
│   ├── input-contour/      # Input Contour (8771)
│   ├── client-contour/     # Client Contour (8772)
│   ├── mcp-telegram/       # Telegram adapter
│   ├── mcp-whatsapp/       # WhatsApp adapter
│   └── ...                 # Other MCP adapters
│
├── workflows_to_import/    # n8n workflows for import ← NEW (by Junior)
│
├── .claude/                # AI collaboration ← NEW
│   ├── inbox.md            # Tasks for Junior
│   └── outbox.md           # Feedback to Junior
│
├── app/                    # Android app (Kotlin)
├── Old/                    # Old architecture (archive)
├── scripts/                # Utilities
├── Plans/                  # Business plans
├── CLAUDE.md               # AI instructions
├── Start.md                # This file
└── Stop.md                 # Completion checklist
```

---

## SERVERS

### Infrastructure:

| Server | IP/URL | Port | Purpose |
|--------|--------|------|---------|
| n8n | n8n.n8nsrv.ru | 443 | Workflow automation |
| Neo4j | 45.144.177.128 | 7474/7687 | Graph database |
| PostgreSQL | 185.221.214.83 | 6544 | Main database |
| Redis (RU) | 45.144.177.128 | 6379 | ai_extraction_queue |
| Redis (n8n) | 185.221.214.83 | 6379 | n8n cache |

### MCP Contours (NEW Architecture):

| Service | IP | Port | Purpose | Status |
|---------|----|----- |---------|--------|
| Input Contour | 45.144.177.128 | 8771 | Ingest + Redis queue | 📝 Documented |
| Client Contour | 45.144.177.128 | 8772 | Tenant/Client/Dialog | 📝 Documented |
| Graph Tool | 45.144.177.128 | 8773 | Neo4j proxy | 📝 Documented |
| AI Tool | 45.144.177.128 | 8774 | Extract + Chat (OpenRouter) | ✅ Created |

### MCP Channel Adapters:

| Service | IP | Port | Purpose |
|---------|----|----- |---------|
| MCP Telegram | 217.145.79.27 | 8767 | Telegram Bot API |
| MCP WhatsApp | 217.145.79.27 | 8766 | WhatsApp (Wappi.pro) |
| MCP Avito | 45.144.177.128 | 8765 | Avito Messenger |
| MCP VK | 45.144.177.128 | 8767 | VK Community |
| MCP MAX | 45.144.177.128 | 8768 | MAX (VK Teams) |
| MCP Form | 45.144.177.128 | 8770 | Web forms |
| Android API | 45.144.177.128 | 8780 | API Gateway |

---

## DATABASE CONNECTIONS

```
PostgreSQL: postgresql://supabase_admin:Mi31415926pS@185.221.214.83:6544/postgres
Neo4j: bolt://neo4j:Mi31415926pS@45.144.177.128:7687
Redis (RU): redis://:Mi31415926pSss!@45.144.177.128:6379
```

---

## NEXT STEPS (priority)

### ПРИОРИТЕТ 1: Импорт n8n workflows и тестирование

**1.1 Импорт в n8n UI** (https://n8n.n8nsrv.ru)
```
Файлы для импорта:
├── NEW/workflows/ELO_AI/ELO_AI_Extract.json
├── NEW/workflows/ELO_AI/ELO_AI_Chat.json
├── workflows_to_import/ELO_Input_Ingest.json
├── workflows_to_import/ELO_Input_Worker.json
├── workflows_to_import/ELO_Client_Resolve.json
├── workflows_to_import/ELO_Graph_Query.json
└── workflows_to_import/new/*.json (12 файлов)
```

**1.2 Активировать webhooks** — нажать "Active" toggle в каждом workflow

**1.3 Тестировать curl:**
```bash
# Test ELO_AI_Extract
curl -X POST https://n8n.n8nsrv.ru/webhook/elo-ai-extract \
  -H "Content-Type: application/json" \
  -d '{"message": "Разбил экран iPhone 14", "extraction_schema": {"type": "object"}}'

# Test ELO_Input_Ingest
curl -X POST https://n8n.n8nsrv.ru/webhook/elo-input-ingest \
  -H "Content-Type: application/json" \
  -d '{"channel": "telegram", "external_chat_id": "123", "text": "test"}'
```

### ПРИОРИТЕТ 2: Deploy AI Tool MCP (8774)

```bash
ssh root@45.144.177.128
cd /root/mcp
# Добавить ai-tool в docker-compose.yml
docker-compose up -d ai-tool
curl http://localhost:8774/health
```

### ПРИОРИТЕТ 3: E2E тест

После импорта — отправить тестовое сообщение через Telegram:
```
Telegram → MCP → n8n ELO_In_Telegram → ELO_Input_Ingest → ...
```

### Отложено (после MVP):

- Graph questions (Register vs Tracker)
- Operator Web App
- Price parser prototype
- Voice → Graph

---

## QUICK COMMANDS

```bash
# Redis queue check (RU server)
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! LLEN "ai_extraction_queue"'

# Neo4j status
curl -u neo4j:Mi31415926pS http://45.144.177.128:7474/db/neo4j/tx/commit -d '{"statements":[]}'

# API Gateway health
curl http://45.144.177.128:8780/health

# Update context
python scripts/update_core_context.py
```

---

## KEY DOCUMENTS TO READ

**On session start:**
1. This file (Start.md)
2. `NEW/ROADMAP.md` — killer features and AI tools
3. `NEW/ARCHITECTURE_SYNC.md` — architecture mapping
4. `CORE_NEW/docs/05_AI_ARCHITECTURE.md` — 7 levels

**When working with a block:**
- Channel Layer: `NEW/Core_info/01_Channel_Layer/`
- Input Contour: `NEW/Core_info/02_Input_Contour/`
- Graph: `NEW/Core_info/04_Graph/GRAPH_OVERVIEW.md`

---

**Before ending session:** update Start.md and Stop.md, git push
