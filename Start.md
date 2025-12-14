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
**December 14, 2025, 13:10 (UTC+4)**

---

## COMPLETED: Input Contour — Batcher + Processor

### What was done

1. **ELO_Input_Batcher** — created and working
   - Schedule 3 sec
   - Pop Message → Parse → Push to Batch → Set Deadline

2. **ELO_Input_Processor** — created and working
   - Schedule 3 sec
   - Get All Deadlines → Check If Due → Get Batch → Merge → Call Client Resolve

3. **ELO_Client_Resolve** — fixed
   - Fixed table names: elo_* → elo_t_*
   - Fixed column names: credential → account_id, status → status_id
   - Fixed cache parsing: $json.value → $json.propertyName || $json.value
   - Fixed IF conditions: use is not empty operator

4. **ELO_Core_AI_Test_Stub** — fixed
   - Changed webhook to POST
   - Fixed data reading: $json → $json.body || $json

### Test Results

```
3 messages → ELO_Input_Batcher → batch:telegram:tg_test_001
           → ELO_Input_Processor → merge → "Message 1\n\nMessage 2\n\nMessage 3"
           → ELO_Client_Resolve → client + dialog created
           → ELO_Core_AI_Test_Stub → received correctly
```

---

## n8n Redis Node — IMPORTANT QUIRKS

| Operation | Where data is | Example |
|-----------|---------------|---------|
| GET | $json.propertyName | Check for null |
| POP | $json.propertyName or $json.value | Check both |
| KEYS | Keys as object properties | Object.keys($json) |
| SET | Needs String() | ={{String($json.value)}} |

**IF nodes:** use is not empty operator, NOT exists

---

## Architecture (n8n only, MCP disabled)

```
ELO_In_* → queue:incoming → ELO_Input_Batcher → batch:* 
         → ELO_Input_Processor → ELO_Client_Resolve 
         → ELO_Core_AI_Test_Stub → ELO_Out_Router
```

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

1. **Replace Test Stub with real Core AI**
2. **Test Output Contour** — ELO_Out_Router → ELO_Out_Telegram
3. **End-to-end test** — real Telegram message → response

---

**Before ending session:** update Start.md, git push
