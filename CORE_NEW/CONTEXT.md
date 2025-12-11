# CORE_NEW — Контекст разработки

*Последнее обновление: 2025-12-11 16:00 (UTC+4)*

> Этот файл — единый источник правды о состоянии CORE_NEW.
> Обновляется при каждом коммите/закрытии сессии.

---

## Quick Stats

| Категория | Количество | Статус |
|-----------|------------|--------|
| PostgreSQL таблицы (elo_*) | 26 | ✅ Созданы |
| Neo4j лейблы (ELO_*) | 5 | ✅ Созданы |
| API v2 эндпоинты | 25+ | ✅ Описаны |
| n8n workflows (ELO_*) | 17+ | ✅ JSON готовы к импорту |
| MCP серверы | 11 | ✅ v2.0.0 production |
| MCP Contours | 4 | ✅ NEW Architecture |

---

## Текущий статус

### ✅ Новая 4-контурная архитектура (2025-12-11)

**Архитектура спроектирована и задокументирована:**

```
MCP Channels → Input (8771) → Client (8772) → Core (n8n) → Graph (8773)
                                                    ↓
                                              AI Tool (8774)
```

### MCP Contours ("слепые исполнители"):

| Contour | Port | Purpose | Code | Status |
|---------|------|---------|------|--------|
| Input | 8771 | Ingest + Redis queue | `MCP/input-contour/` | 📝 Documented |
| Client | 8772 | Tenant/Client/Dialog | `MCP/client-contour/` | 📝 Documented |
| Graph Tool | 8773 | Neo4j proxy | `MCP/graph-tool/` | 📝 Documented |
| AI Tool | 8774 | Extract + Chat | `MCP/ai-tool/` | ✅ Created |

### n8n Workflows готовы к импорту:

| Category | Files | Location |
|----------|-------|----------|
| AI Tool (Senior) | ELO_AI_Extract.json, ELO_AI_Chat.json | `NEW/workflows/ELO_AI/` |
| Input (Junior) | ELO_Input_Ingest.json, ELO_Input_Worker.json | `workflows_to_import/` |
| Client (Junior) | ELO_Client_Resolve.json | `workflows_to_import/` |
| Graph (Junior) | ELO_Graph_Query.json | `workflows_to_import/` |
| Core (Junior) | ELO_Core_Ingest.json | `workflows_to_import/` |
| Channels (Junior) | 11 IN/OUT workflows | `workflows_to_import/` |

### Следующий шаг:
1. Импортировать workflows в n8n
2. Активировать webhooks
3. Протестировать цепочку curl

---

## Архитектура

### 7 уровней AI системы

```
┌─────────────────────────────────────────────────────────┐
│  7. MCP Channels (Telegram, WhatsApp, Avito, VK, MAX)   │
├─────────────────────────────────────────────────────────┤
│  6. Response Builder (форматирование под канал)         │
├─────────────────────────────────────────────────────────┤
│  5. Dialog Engine (обработка сообщений, граф)           │
├─────────────────────────────────────────────────────────┤
│  4. Universal Tools (воркеры с промптами)               │
├─────────────────────────────────────────────────────────┤
│  3. Universal Orchestrator (слепой исполнитель)         │
├─────────────────────────────────────────────────────────┤
│  2. Request Builder (правила → AI → правила)            │
├─────────────────────────────────────────────────────────┤
│  1. Context Builder (PostgreSQL + Neo4j)                │
└─────────────────────────────────────────────────────────┘
```

### Ключевые концепции

| Концепция | Описание |
|-----------|----------|
| **Prompt-in-Request** | Промпты передаются в запросе, не хардкодятся |
| **Кнут-Пряник-Кнут** | Правила → AI-свобода → Валидация |
| **ai_freedom_level** | 0-100, регулятор жёсткости AI |
| **Dialog-centric** | Диалог — центральная сущность, не заявка |
| **Tasks в PostgreSQL** | Задачи — CRUD, не граф |

---

## PostgreSQL: 14 таблиц (elo_*)

### Ядро (5)

| Таблица | Описание | Ключевые поля |
|---------|----------|---------------|
| `elo_tenants` | Тенанты | settings (JSONB), plan, max_operators |
| `elo_clients` | Клиенты | phone, telegram_id, profile (JSONB), stats (JSONB) |
| `elo_dialogs` | Диалоги | channel, status, context (JSONB), assigned_operator_id |
| `elo_events` | События | event_type, data (JSONB), actor_type |
| `elo_operators` | Операторы | role, fcm_tokens (JSONB), settings (JSONB) |

### Справочники (3)

| Таблица | Описание |
|---------|----------|
| `elo_verticals` | Вертикали бизнеса (phone_repair, auto_service) |
| `elo_tenant_verticals` | Привязка тенанта к вертикали |
| `elo_price_list` | Прайс-лист услуг |

### AI (2)

| Таблица | Описание |
|---------|----------|
| `elo_ai_extractions` | Извлечения AI (intent, device, issue) |
| `elo_ai_suggestions` | Подсказки AI (ответы, действия) |

### Каналы (1)

| Таблица | Описание |
|---------|----------|
| `elo_channel_accounts` | Аккаунты каналов (webhook_hash, credentials) |

### Задачи (2)

| Таблица | Описание |
|---------|----------|
| `elo_tasks` | Задачи сотрудников |
| `elo_task_updates` | История обновлений задач |

### Seed data (1)

| Таблица | Описание |
|---------|----------|
| `elo_problem_categories` | Категории проблем (display, battery, etc) |
| `elo_funnel_stages` | Этапы воронки для вертикали |
| `elo_symptom_types` | Типы симптомов |

---

## Neo4j: 5 лейблов (ELO_*)

| Лейбл | Описание | Связи |
|-------|----------|-------|
| `ELO_Client` | Клиент | HAS_DIALOG, FAMILY, REFERRED |
| `ELO_Dialog` | Диалог | ABOUT_DEVICE, HAS_ISSUE |
| `ELO_Device` | Устройство | — |
| `ELO_Problem` | Проблема | — |
| `ELO_Fact` | Факт о клиенте | — |

---

## n8n Workflows (ELO_*)

### Текущее состояние (НЕ РАБОТАЕТ)

| Workflow | Статус в n8n | Проблема |
|----------|--------------|----------|
| ELO_In_Telegram | ACTIVE | Вызывает старый BAT_Tenant_Resolver |
| ELO_In_WhatsApp | ACTIVE | Вызывает старый BAT_Tenant_Resolver |
| ELO_In_Avito | ACTIVE | Вызывает старый BAT_Tenant_Resolver |
| ELO_In_VK | ACTIVE | Вызывает старый BAT_Tenant_Resolver |
| ELO_In_MAX | ACTIVE | Вызывает старый BAT_Tenant_Resolver |
| ELO_In_Form | ACTIVE | Вызывает старый BAT_Tenant_Resolver |
| ELO_In_Phone | ACTIVE | Вызывает старый BAT_Tenant_Resolver |
| ELO_Core_Tenant_Resolver | OFF | Никто не вызывает |
| ELO_Core_Batcher | OFF | Никто не вызывает |
| ELO_Core_Dialog_Engine | OFF | Никто не вызывает |
| ELO_Out_Telegram | OFF | Никто не вызывает |
| ELO_Out_WhatsApp | OFF | Никто не вызывает |
| ELO_Out_Avito | OFF | Никто не вызывает |
| ELO_Out_VK | OFF | Никто не вызывает |
| ELO_Out_MAX | OFF | Никто не вызывает |

### Требуется

Перепроектировать архитектуру workflows:
1. Определить блоки и их ответственность
2. Определить связи (Redis, Execute Workflow, HTTP)
3. Определить триггеры каждого блока
4. Создать workflows по утверждённой схеме

---

## MCP Серверы

### MCP Contours (NEW):

| Сервер | Порт | Хост | Статус |
|--------|------|------|--------|
| input-contour | 8771 | 45.144.177.128 (RU) | 📝 Documented |
| client-contour | 8772 | 45.144.177.128 (RU) | 📝 Documented |
| graph-tool | 8773 | 45.144.177.128 (RU) | 📝 Documented |
| ai-tool | 8774 | 45.144.177.128 (RU) | ✅ Created |

### MCP Channel Adapters:

| Сервер | Порт | Хост | Статус |
|--------|------|------|--------|
| mcp-telegram | 8767 | 217.145.79.27 (FI) | ✅ v2.0.0 |
| mcp-whatsapp | 8766 | 217.145.79.27 (FI) | ✅ v2.0.0 |
| mcp-avito | 8765 | 45.144.177.128 (RU) | ✅ v2.0.0 |
| mcp-vk | 8767 | 45.144.177.128 (RU) | ✅ v2.0.0 |
| mcp-max | 8768 | 45.144.177.128 (RU) | ✅ v2.0.0 |
| mcp-form | 8770 | 45.144.177.128 (RU) | ✅ v1.0.0 |
| api-android | 8780 | 45.144.177.128 (RU) | ✅ Gateway |

---

## Файлы документации

| Файл | Описание | Статус |
|------|----------|--------|
| [00_VISION.md](docs/00_VISION.md) | Видение продукта | ✅ |
| [01_CORE_DESIGN.md](docs/01_CORE_DESIGN.md) | Архитектура ядра, глоссарий | ✅ |
| [02_DATABASE_SCHEMA.md](docs/02_DATABASE_SCHEMA.md) | PostgreSQL: 14 таблиц | ✅ |
| [03_NEO4J_SCHEMA.md](docs/03_NEO4J_SCHEMA.md) | Neo4j: 5 лейблов | ✅ |
| [04_API_CONTRACTS.md](docs/04_API_CONTRACTS.md) | API v2 контракты | ✅ |
| [05_AI_ARCHITECTURE.md](docs/05_AI_ARCHITECTURE.md) | AI: 7 уровней | ✅ |
| [06_DATA_CONTRACT.md](docs/06_DATA_CONTRACT.md) | Формат данных между workflows | ✅ |
| [NEXT_SESSION_BRIEF.md](docs/NEXT_SESSION_BRIEF.md) | Brief для проектирования | ✅ |
| [workflows/](docs/workflows/) | Черновики документации workflows | ⚠️ Требует переработки |

---

## Доступ к ресурсам

### PostgreSQL
```bash
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'QUERY'"
```

### Neo4j
```bash
ssh root@45.144.177.128 "docker exec neo4j cypher-shell -a 'bolt+ssc://localhost:7687' -u neo4j -p 'Mi31415926pS' 'QUERY'"
```

### n8n API (read-only)
```bash
curl -s "https://n8n.n8nsrv.ru/api/v1/workflows" -H "X-N8N-API-KEY: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzY0NzY4NzE0LCJleHAiOjE3NzI0ODE2MDB9.JzC21XpXh7188Qlx2xWpZPHQysksg_Jj0hWuTgy6PmQ"
```

### Redis Insight
http://185.221.214.83:5540

---

## Папки проекта

| Папка | Назначение |
|-------|------------|
| `CORE_NEW/docs/` | Документация новой архитектуры |
| `CORE_NEW/migrations/` | SQL миграции |
| `CORE_NEW/workflows/` | Синхронизированные JSON workflows (read-only) |
| `workflows_to_import/` | Новые/изменённые workflows для импорта в n8n |
| `Old/n8n_workflows/` | Старые BAT_* workflows (справочно) |

---

## История изменений

| Дата | Изменение |
|------|-----------|
| **2025-12-11** | **✅ Новая 4-контурная архитектура** |
| 2025-12-11 | Создан AI Tool MCP (8774) — extract + chat |
| 2025-12-11 | Создана документация 06_AI_Tool/ |
| 2025-12-11 | Создано ELO_AI_Extract.json, ELO_AI_Chat.json |
| 2025-12-11 | Junior создал 15+ mock workflows |
| 2025-12-11 | Обновлён API_CONTRACTS.md |
| 2025-12-10 | Импортированы 15 ELO_* workflows (требуют обновления) |
| 2025-12-10 | Анализ архитектуры: workflows не связаны правильно |
| 2025-12-10 | Решение: перепроектировать с нуля |
| 2025-12-10 | Создана документация workflows (черновик) |
| 2025-12-09 | Создан CONTEXT.md |
| 2025-12-09 | Добавлены таблицы elo_tasks, elo_task_updates |
| 2025-12-09 | Создана AI архитектура (7 уровней) |
| 2025-12-09 | Создана схема PostgreSQL (14 таблиц) |
| 2025-12-09 | Создана схема Neo4j (5 лейблов) |

---

## Следующие шаги

1. **Импортировать workflows в n8n**
   - `NEW/workflows/ELO_AI/` — AI Tool
   - `workflows_to_import/` — все остальные
2. **Активировать webhooks** — проверить endpoints
3. **Протестировать цепочку** — curl к Input → Client → Core
4. **Задеплоить MCP Contours** — Input, Client, Graph на сервер
5. **Протестировать E2E** — Telegram → AI ответ
