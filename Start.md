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
**December 11, 2025, 23:30 (UTC+4)**

---

## CORE AI — КОНЦЕПЦИЯ РАЗРАБОТАНА

### Ключевое понимание

```
ВСЯ ЛОГИКА CORE AI = ГРАФ + ПРОМПТЫ
├── Схема слотов (что собирать)
├── Зависимости (как выводить)
├── Этапы воронки (stages)
├── Триггеры (conditions → actions)
└── Промпты (тексты)

ОТЛИЧИЕ ВЕРТИКАЛЕЙ = ТОЛЬКО TOOLS
Оркестратор один, логика одна — Context Lines
```

---

### Модель "Context Lines"

```
┌─────────────────────────────────────────────────────────┐
│                      CONTEXT                            │
│                                                         │
│  Line 0: ●──●──●──○──○  (cursor=3, waiting)            │
│  Line 1: ●──●──●──●──✓  (done)                         │
│  Line 2: ●──○──○──○──○  (cursor=1, active) ← focus     │
│                                                         │
│  ● = filled, ○ = empty, ✓ = complete                   │
└─────────────────────────────────────────────────────────┘

Line = intake с слотами [device, symptom, owner, price]
Cursor = где остановились
Focus = активная линия
Waiting = линии с обрывами
```

### Алгоритм (ВСЯ ЛОГИКА)

```
1. ПОЛУЧИТЬ сообщение клиента

2. AI EXTRACT — вытащить ВСЕ параметры (не по одному!)
   {device: ?, symptom: ?, owner: ?, ...}

3. АНАЛИЗ:
   - Новый owner/device? → CREATE линию
   - Owner/device другой линии? → SWITCH focus
   - Заполнить ВСЕ найденные слоты
   - Двинуть cursor

4. DERIVE — вычислить зависимые (symptom → repair → price)

5. TRIGGERS — проверить условия, выполнить действия

6. ПРОВЕРИТЬ:
   - Линия done? → убрать из waiting
   - Все done? → переход на следующий этап
   - Есть waiting? → switch на первую

7. СПРОСИТЬ — слот на cursor активной линии
```

---

### Этапы воронки

```
┌─────────────────────────────────────────────────────────┐
│  ЭТАП 1: Сбор данных                                    │
│  slots: [device, symptom, owner, price]                 │
├─────────────────────────────────────────────────────────┤
│  ЭТАП 2: Презентация                                    │
│  slots: [offer_shown] + triggers                        │
├─────────────────────────────────────────────────────────┤
│  ЭТАП 3: Согласование                                   │
│  slots: [conditions_ok, ready_to_book]                  │
├─────────────────────────────────────────────────────────┤
│  ЭТАП 4: Запись                                         │
│  slots: [date, time, name, phone]                       │
├─────────────────────────────────────────────────────────┤
│  ЭТАП 5: Подтверждение                                  │
│  slots: [confirmed] → INTAKE CREATED                    │
└─────────────────────────────────────────────────────────┘
```

---

### Триггеры в графе

```cypher
(:Trigger {stage: "presentation", conditions: {device_brand: "Apple", repair: "battery_replace"}})
  -[:EXECUTES]->
(:Action {type: "send_file", file: "battery_care.pdf"})
```

---

### Масштабирование и стоимость

```
1000 tenants × 50 диалогов × 40 сообщений = 2M msg/день

Python FastAPI async: 2-3 пода достаточно
OpenRouter Qwen3-30B: paid tier, нет лимита

Стоимость AI:
- Extract (дешёвая модель): ~$5/день
- Response (умная модель): ~$74/день
- Итого: ~$2.40/tenant/месяц
```

---

## PLAN FILE

**Полная концепция:** `.claude/plans/snazzy-prancing-piglet.md`

---

## NEXT STEPS (реализация)

### Фаза 1: Context Lines для сбора данных
1. Структура Line, Context (Python)
2. AI Extract для всех параметров схемы
3. Логика раскидывания по линиям
4. Focus / waiting / cursor
5. Derive зависимых слотов

### Фаза 2: Этапы воронки
1. Stage schema в графе
2. Переходы между этапами
3. Слоты для каждого этапа

### Фаза 3: Триггеры и действия
1. Trigger schema в графе
2. Проверка conditions
3. Выполнение actions

### Фаза 4: Воркеры
1. Правильно расставить воркеры
2. Описать контекст от пункта к пункту
3. Связать этапы в единый flow

---

## CURRENT PROJECT STATUS

### Strategy defined

**Product:** Dialog-centric CRM for service centers

**Philosophy:** "People talk. Machine keeps records."

**MVP Vertical:** Phone Repair + Buy/Sell (trade-in, used)

**WOW-effect:** "No lost customers" — AI responds at 11 PM, schedules for tomorrow

---

## WHAT'S DONE — FULL HISTORY

### Session 11.12.2025 (night) — CORE AI Concept ✅

**Разработана полная концепция Core AI:**

| Компонент | Описание | Статус |
|-----------|----------|--------|
| Context Lines | Модель сбора данных (lines, cursor, focus, waiting) | ✅ |
| Stages | Этапы воронки (5 этапов) | ✅ |
| Triggers | Условия → действия (в графе) | ✅ |
| Scaling | 2-3 пода для 1000 tenants | ✅ |
| Cost | ~$2.40/tenant/месяц на AI | ✅ |

**Тестовые данные в Neo4j:**
- DeviceModel: iPhone 12 Pro, iPhone 14
- RepairType: display, battery, charging
- Symptom → RepairType (с алиасами)
- Price: iPhone 12 Pro (8500, 2800, 2500)

**Git commit:** `98349ec` — Core AI: Inference-based logic design + Neo4j test data

---

### Session 12.11.2025 (day) — MCP Contours Architecture + AI Tool

**4-контурная архитектура:**
```
MCP Channels → Input (8771) → Client (8772) → Core (n8n) → Graph (8773)
                                                    ↓
                                              AI Tool (8774)
```

**MCP сервисы:**

| Service | Port | Purpose | Status |
|---------|------|---------|--------|
| Input Contour | 8771 | Ingest + Redis queue | 📝 Documented |
| Client Contour | 8772 | Tenant/Client/Dialog | ✅ Code ready |
| Graph Tool | 8773 | Neo4j proxy | 📝 Documented |
| AI Tool | 8774 | Extract + Chat | ✅ Created |

---

### Session 12.11.2025 (night) — Commercial Strategy + ROADMAP

**Created:**
- `NEW/ROADMAP.md` (~1200 lines) — Killer features, AI tools
- `NEW/ARCHITECTURE_SYNC.md` (~550 lines) — Architecture mapping

---

## SERVERS

### MCP Contours:

| Service | IP | Port | Status |
|---------|----|------|--------|
| Input Contour | 45.144.177.128 | 8771 | 📝 Documented |
| Client Contour | 45.144.177.128 | 8772 | ✅ Code ready |
| Graph Tool | 45.144.177.128 | 8773 | 📝 Documented |
| AI Tool | 45.144.177.128 | 8774 | ✅ Created |

### Infrastructure:

| Server | IP/URL | Port | Purpose |
|--------|--------|------|---------|
| n8n | n8n.n8nsrv.ru | 443 | Workflow automation |
| Neo4j | 45.144.177.128 | 7474/7687 | Graph database |
| PostgreSQL | 185.221.214.83 | 6544 | Main database |
| Redis (RU) | 45.144.177.128 | 6379 | Queues |

---

## DATABASE CONNECTIONS

```
PostgreSQL: postgresql://supabase_admin:Mi31415926pS@185.221.214.83:6544/postgres
Neo4j: bolt://neo4j:Mi31415926pS@45.144.177.128:7687
Redis (RU): redis://:Mi31415926pSss!@45.144.177.128:6379
```

---

## KEY DOCUMENTS

**On session start:**
1. This file (Start.md)
2. `.claude/plans/snazzy-prancing-piglet.md` — Core AI concept
3. `NEW/ROADMAP.md` — killer features
4. `CORE_NEW/docs/05_AI_ARCHITECTURE.md` — 7 levels

---

## QUICK COMMANDS

```bash
# Neo4j test data check
ssh root@45.144.177.128 "docker exec neo4j cypher-shell -a 'bolt+ssc://localhost:7687' -u neo4j -p 'Mi31415926pS' 'MATCH (n) RETURN labels(n), count(n)'"

# Redis queue check
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! LLEN "ai_extraction_queue"'

# Update context
python scripts/update_core_context.py
```

---

**Before ending session:** update Start.md and Stop.md, git push
