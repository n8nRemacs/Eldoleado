# START - Контекст для продолжения работы

## ПЕРВЫМ ДЕЛОМ — Синхронизация

**Если читаешь этот файл ВТОРОЙ раз после git pull — ПРОПУСТИ этот блок и переходи к следующей секции!**

```bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
```

После git pull — ПЕРЕЧИТАЙ этот файл заново (Start.md), начиная со следующей секции (пропустив этот блок синхронизации, чтобы не зациклиться).

---

## Дата и время последнего обновления
**9 декабря 2025, 17:00 (UTC+4)**

---

## CORE_NEW — Новая архитектура CRM

### Философия: "Люди общаются. Машина ведёт учёт."

```
Старый подход:  Клиент → Заявки → Устройства → Проблемы (50 таблиц)
Новый подход:   Клиент → Диалоги (с контекстом внутри) (11 таблиц)
```

- Человеку — естественное общение в мессенджерах
- Машине — рутина, учёт, таблицы (AI под капотом)
- БЕЗ канбанов, БЕЗ заполнения форм
- Отчётность из воздуха (AI понимает каждое сообщение)

---

## Что сделано в последних сессиях

### Сессия 09.12.2025 (вечер) — AI Архитектура

1. **AI Architecture — СОЗДАНА ✅**
   - Файл: `CORE_NEW/docs/05_AI_ARCHITECTURE.md`
   - 7 уровней: от данных до мессенджеров
   - Context Builder (PG + Neo4j)
   - Request Builder (Правила + AI + Правила)
   - Universal Orchestrator (промпт в запросе)
   - Universal Tools (один worker — все вертикали)

2. **Ключевые концепции:**
   - **Prompt-in-Request** — промпты передаются в запросе, не хардкодятся
   - **Кнут-Пряник-Кнут** — правила → AI-свобода → валидация
   - **ai_freedom_level** — регулятор жёсткости (0-100)
   - **Граф + Extractor** — двусторонняя связь (контекст ↔ обогащение)

3. **Принцип вертикалей:**
   - Ядро универсальное, вертикали = конфигурация в БД
   - Один worker обрабатывает ВСЕ вертикали
   - Разница только в промптах

### Сессия 09.12.2025 (день) — CORE_NEW Архитектура

1. **Vision Document — СОЗДАН ✅**
   - Файл: `CORE_NEW/docs/00_VISION.md`
   - Диалогоцентричная CRM

2. **PostgreSQL Schema — СОЗДАНА ✅**
   - Файл: `CORE_NEW/docs/02_DATABASE_SCHEMA.md`
   - **11 таблиц с префиксом `elo_` вместо 50!**
   - `elo_dialogs` — центральная сущность
   - `elo_events` — EventStore
   - Context в JSONB (устройство, проблема, цена — внутри диалога)

3. **Neo4j Graph Schema — СОЗДАНА ✅**
   - Файл: `CORE_NEW/docs/03_GRAPH_SCHEMA.md`
   - Лейблы с префиксом `ELO_`: Client, Dialog, Fact
   - Связи: ELO_HAS_DIALOG, ELO_FAMILY, ELO_REFERRED

4. **API Contracts v2 — СОЗДАНЫ ✅**
   - Файл: `CORE_NEW/docs/04_API_CONTRACTS.md`
   - `/dialogs` вместо `/appeals`
   - AI Suggestions API
   - Events API для timeline

### Предыдущие сессии — Старая архитектура

- **Android API Gateway** — FastAPI на 45.144.177.128:8780 ✅
- **Neo4j Context Builder** — работает ✅
- **Task Dispatcher + Worker** — работают ✅
- **95+ n8n workflows** — работают ✅

---

## Текущее состояние проекта

### CORE_NEW (новая архитектура):

| Компонент | Статус | Файл |
|-----------|--------|------|
| Vision | ✅ | `CORE_NEW/docs/00_VISION.md` |
| PostgreSQL Schema | ✅ | `CORE_NEW/docs/02_DATABASE_SCHEMA.md` |
| Neo4j Schema | ✅ | `CORE_NEW/docs/03_GRAPH_SCHEMA.md` |
| API v2 Contracts | ✅ | `CORE_NEW/docs/04_API_CONTRACTS.md` |
| AI Architecture | ✅ | `CORE_NEW/docs/05_AI_ARCHITECTURE.md` |
| SQL миграции | ⏳ | TODO |
| Workflows | ⏳ | TODO |

### Старая архитектура (рабочая):

| Компонент | Статус |
|-----------|--------|
| Android приложение | ✅ |
| Android API Gateway | ✅ |
| n8n workflows | ✅ |
| Neo4j интеграция | ✅ |
| PostgreSQL | ✅ |

---

## Следующие шаги (приоритет)

### 1. SQL миграции для CORE_NEW
```sql
-- Создать elo_* таблицы
-- Скрипты в CORE_NEW/migrations/
```

### 2. Адаптировать workflows
- Под `elo_dialogs` вместо `appeals`
- Под новый API v2

### 3. DNS + SSL для API
```
android-api.eldoleado.ru → 45.144.177.128
certbot --nginx -d android-api.eldoleado.ru
```

---

## Ключевые файлы CORE_NEW

| Файл | Описание |
|------|----------|
| `CORE_NEW/docs/00_VISION.md` | Видение продукта |
| `CORE_NEW/docs/01_CORE_DESIGN.md` | Архитектура ядра, концепция ЛИНИИ |
| `CORE_NEW/docs/02_DATABASE_SCHEMA.md` | PostgreSQL: 11 elo_* таблиц |
| `CORE_NEW/docs/03_NEO4J_SCHEMA.md` | Neo4j: Client, Device, Problem |
| `CORE_NEW/docs/04_API_CONTRACTS.md` | API v2 контракты |
| `CORE_NEW/docs/05_AI_ARCHITECTURE.md` | AI: 7 уровней, Prompt-in-Request |

---

## Серверы

| Сервер | IP/URL | Порт | Назначение |
|--------|--------|------|------------|
| n8n | n8n.n8nsrv.ru | 443 | Workflow automation |
| Neo4j | 45.144.177.128 | 7474/7687 | Graph database |
| PostgreSQL | 185.221.214.83 | 6544 | Main database |
| **Android API** | 45.144.177.128 | 8780 | API Gateway ✅ |
| Redis (RU) | 45.144.177.128 | 6379 | ai_extraction_queue |
| Redis (n8n) | 185.221.214.83 | 6379 | n8n cache |
| MCP Telegram | 217.145.79.27 | 443 | tg.eldoleado.ru |

---

## Database Connections

```
PostgreSQL: postgresql://supabase_admin:Mi31415926pS@185.221.214.83:6544/postgres
Neo4j: bolt://neo4j:Mi31415926pS@45.144.177.128:7687
Redis (RU): redis://:Mi31415926pSss!@45.144.177.128:6379
```

---

## Quick Commands

```bash
# Redis queue check (RU server)
ssh root@45.144.177.128 'docker exec redis redis-cli --no-auth-warning -a Mi31415926pSss! LLEN "ai_extraction_queue"'

# Neo4j status
curl -u neo4j:Mi31415926pS http://45.144.177.128:7474/db/neo4j/tx/commit -d '{"statements":[]}'

# API Gateway health
curl http://45.144.177.128:8780/health
```

---

## Документация

- `CORE_NEW/docs/` — документация новой архитектуры
- `Plans/` — бизнес-требования
- `CLAUDE.md` — инструкции для AI
- `Stop.md` — чеклист завершения сессии

---

**Перед завершением сессии**: обновить этот файл и git push
