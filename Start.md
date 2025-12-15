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
**15 December 2025, 13:00 (UTC+4)**

---

## COMPLETED: AI Contour — Hardcode Removed

### What was done

1. **All workflows rewritten to use PostgreSQL** instead of hardcoded config:
   - ELO_Core_AI_Derive → SQL derivation chain
   - ELO_Core_Triggers_Checker → SQL triggers from elo_v_triggers
   - ELO_Core_Stage_Manager → SQL stages from elo_v_funnel_stages
   - ELO_Core_Response_Generator → SQL prompts from elo_v_prompts

2. **ELO_AI_Extract rewritten** — from AI Tool MCP (8774) to OpenRouter API

3. **ELO_AI_Chat deleted** — unused, redundant

4. **New workflows created:**
   - ELO_Core_Graph_Writer — write to Neo4j
   - ELO_Core_Context_Builder — load context from Neo4j/Redis

5. **Documentation created:**
   - AI_CONTOUR_ARCHITECTURE.md — full architecture, call graph, data flow

---

## COMPLETED: Database — Derivation Chain

1. **Migrations created (005):**
   - symptom → diagnosis links: 5 → 28
   - diagnosis → repair links: 3 → 10
   - price list entries: 10 → 22

2. **100% coverage** — all 25 symptoms now have derivation chain

---

## Current AI Contour (10 workflows)

```
ELO_Core_AI_Pipeline.json        # Main pipeline (sequential calls)
ELO_AI_Extract.json              # Entity extraction (OpenRouter)
ELO_Core_Lines_Analyzer.json     # Multi-intake lines management
ELO_Core_AI_Derive.json          # symptom→diagnosis→repair→price
ELO_Core_Triggers_Checker.json   # Conditional triggers
ELO_Core_Stage_Manager.json      # Funnel stage management
ELO_Core_Response_Generator.json # AI response generation
ELO_Core_Graph_Writer.json       # Neo4j persistence
ELO_Core_Context_Builder.json    # Context loading
ELO_Core_AI_Test_Stub.json       # Test stub
```

**Location:** `NEW/workflows/AI Contour/`

---

## Current System State

**Database:** Ready
- All tables created (global, vertical, tenant levels)
- Derivation chain 100% complete
- Test tenant configured

**AI Contour:** Ready
- 10 workflows, no hardcode
- All config from PostgreSQL
- All AI calls via OpenRouter

**Not yet connected:**
- Context_Builder not called by Pipeline (inline code instead)
- End-to-end test not done

---

## Architecture (n8n only, MCP disabled)

```
ELO_In_* → queue:incoming → ELO_Input_Batcher → batch:*
         → ELO_Input_Processor
              ├─ Merge Batch
              ├─ **Translate to EN** (OpenRouter Qwen3)
              └─ Call Client Resolve
         → ELO_Client_Resolve
         → ELO_Core_AI (English only)
         → ELO_Out_Router
              ├─ **Translate to client lang**
              └─ Channel OUT
```

## Language Architecture

**Core AI works entirely in English.**

| Step | Where | What |
|------|-------|------|
| Input | ELO_Input_Processor | Translate client message → EN |
| Core | ELO_Core_AI | All prompts/responses in EN |
| Output | ELO_Out_Router | Translate response → client's lang |

---

## SERVERS

| Server | IP/URL | Port | Purpose |
|--------|--------|------|---------|
| n8n | n8n.n8nsrv.ru | 443 | Workflow automation |
| Neo4j | 45.144.177.128 | 7474/7687 | Graph database |
| PostgreSQL | 185.221.214.83 | 6544 | Main database |
| Redis (RU) | 45.144.177.128 | 6379 | Queues |

---

## QUICK COMMANDS

```bash
# Redis - check all keys
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! KEYS "*"'

# Redis - clear all
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! FLUSHALL'

# Redis - add test messages (with bot_token!)
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! RPUSH "queue:incoming" "{\"channel\":\"telegram\",\"bot_token\":\"TEST_BOT_TOKEN_12345\",\"external_chat_id\":\"tg_test_001\",\"text\":\"Test 1\"}"'
```

---

## NEXT STEPS

1. **Connect Context_Builder to Pipeline** — load existing context instead of creating new
2. **Import workflows to n8n** — all JSON files ready in `NEW/workflows/AI Contour/`
3. **Test derivation chain** — real message → extraction → derivation → price
4. **End-to-end test** — Telegram → Core AI → response

---

**Before ending session:** update Start.md, git push
