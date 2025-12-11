# STOP - Session Completion Checklist

> **IMPORTANT:** When updating this file ALWAYS specify date AND time in format: `DD Month YYYY, HH:MM (UTC+4)`

---

## MANDATORY before closing session:

### 1. Update Start.md

**IMPORTANT:** ALWAYS add sync block at the beginning of Start.md:

```markdown
## FIRST — Sync

**If reading this file SECOND time after git pull — SKIP this block and go to next section!**

\`\`\`bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
\`\`\`

After git pull — REREAD this file from the beginning (Start.md), starting from the next section (skipping this sync block to avoid loops).

---
```

Then update "What's done" section — add everything done in this session.

### 2. Clean project
Delete temporary files from project root:
```bash
# Check what's in root
ls -la *.py *.tmp *.log *.bak 2>/dev/null

# Typical garbage to delete:
# - One-time scripts (check_*.py, test_*.py, deploy_*.py)
# - Archives (*.tar.gz, *.zip)
# - Logs (*.log)
# - Backups (*.bak, *~)
```

Move temporary scripts to `Old/scripts/` or delete.

### 3. Update CORE_NEW context
```bash
python scripts/update_core_context.py
```
Script automatically updates `CORE_NEW/CONTEXT.md` with current data:
- PostgreSQL table count
- Neo4j label count
- API endpoint count
- Workflow count
- Documentation status

### 4. Git sync
```bash
git add -A && git commit -m "Session update: brief description" && git push
```

---

## Last session: 11 December 2025, 23:30 (UTC+4)

---

## What's done in this session

### CORE AI: Полная концепция разработана ✅

**Ключевое понимание:**
```
ВСЯ ЛОГИКА = ГРАФ + ПРОМПТЫ
Никакого хардкода цепочек воркеров
```

---

### 1. Модель "Context Lines" ✅

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
```

**Структура:**
- Line = intake с слотами (device, symptom, owner, price)
- Cursor = где остановились на линии
- Focus = активная линия
- Waiting = линии с обрывами (вернуться позже)

**Алгоритм:**
1. AI Extract — вытащить ВСЕ параметры из сообщения
2. Раскидать по линиям (новая? смена фокуса?)
3. Derive зависимых (symptom → repair → price)
4. Спросить что пусто на cursor
5. Вернуться к waiting

---

### 2. Этапы воронки (Stages) ✅

```
ЭТАП 1: Сбор данных    → slots: [device, symptom, owner, price]
ЭТАП 2: Презентация    → slots: [offer_shown] + triggers
ЭТАП 3: Согласование   → slots: [conditions_ok, ready_to_book]
ЭТАП 4: Запись         → slots: [date, time, name, phone]
ЭТАП 5: Подтверждение  → slots: [confirmed]
```

Та же модель Context Lines применяется к каждому этапу!

---

### 3. Триггеры и действия ✅

```cypher
// В графе:
(:Trigger {stage: "presentation", conditions: {device_brand: "Apple", repair: "battery_replace"}})
  -[:EXECUTES]->
(:Action {type: "send_file", file: "battery_care.pdf"})
```

Примеры:
- iPhone + АКБ → отправить инструкцию
- Цена > 10000 → предложить скидку
- Этап согласования → спросить "Записать?"

---

### 4. Архитектура оркестратора ✅

```
ОРКЕСТРАТОР (один, универсальный)
│
├── Вертикаль A: + tools [гарантия, запчасти]
├── Вертикаль B: + tools [календарь, CRM]
│
└── Логика ОДНА = Context Lines
```

**Отличие вертикалей = только tools.**
**Всё остальное = граф + промпты.**

---

### 5. Масштабирование ✅

```
1000 tenants × 50 диалогов × 40 сообщений = 2M msg/день

Python FastAPI async:
- 1 под = 400 concurrent requests
- 2-3 пода достаточно для 1000 tenants

OpenRouter Qwen3-30B:
- Paid tier: нет лимита
- Latency: 1-3 сек
```

---

### 6. Стоимость AI ✅

```
Qwen3-30B (умная, для ответов):
  $0.06/1M input, $0.22/1M output

Qwen3-4B (дешёвая, для extract):
  ~$0.005/1M tokens

Расчёт (1000 tenants):
  Extract: 100% сообщений × дешёвая модель = ~$5/день
  Response: 50% сообщений × умная модель = ~$74/день

  Итого: ~$80/день = ~$2,400/месяц
  = $2.40/tenant/месяц на AI
```

---

### 7. Тестовые данные в Neo4j ✅

```
DeviceModel: iPhone 12 Pro, iPhone 14
RepairType: display_replace, battery_replace, charging_replace
Symptom → RepairType (с алиасами)
Price: iPhone 12 Pro (8500, 2800, 2500)
```

---

## Ключевые файлы сессии

| Файл | Описание |
|------|----------|
| `.claude/plans/snazzy-prancing-piglet.md` | Полная концепция Core AI |
| Neo4j | Тестовые данные (DeviceModel, RepairType, Symptom, Price) |

---

## НА ЧЁМ ОСТАНОВИЛИСЬ

### Концепция готова, реализация — завтра:

**Фаза 1:** Context Lines для сбора данных
- Структура Line, Context
- AI Extract для всех параметров
- Логика раскидывания по линиям
- Focus / waiting / cursor
- Derive зависимых слотов

**Фаза 2:** Этапы воронки
- Stage schema в графе
- Переходы между этапами

**Фаза 3:** Триггеры и действия
- Trigger schema в графе
- Проверка conditions → execute actions

**Фаза 4:** Воркеры и контекст
- Правильно расставить воркеры
- Описать контекст между этапами

---

## To continue

1. **git pull** — sync latest changes
2. **Read Start.md** — full context
3. **Read plan** — `.claude/plans/snazzy-prancing-piglet.md`
4. **Implement Phase 1** — Context Lines
