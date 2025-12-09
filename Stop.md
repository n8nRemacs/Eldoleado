# STOP - Чеклист завершения сессии

> **ВАЖНО:** При обновлении этого файла ВСЕГДА указывай дату И время в формате: `DD месяц YYYY, HH:MM (UTC+4)`

---

## ОБЯЗАТЕЛЬНО перед закрытием сессии:

### 1. Обновить Start.md

**ВАЖНО:** В начало Start.md ВСЕГДА добавляй блок синхронизации:

```markdown
## ПЕРВЫМ ДЕЛОМ — Синхронизация

**Если читаешь этот файл ВТОРОЙ раз после git pull — ПРОПУСТИ этот блок и переходи к следующей секции!**

\`\`\`bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
\`\`\`

После git pull — ПЕРЕЧИТАЙ этот файл заново (Start.md), начиная со следующей секции (пропустив этот блок синхронизации, чтобы не зациклиться).

---
```

Затем обнови секцию "Что готово" — добавь всё что сделано в этой сессии.

### 2. Git sync
```bash
git add -A && git commit -m "Session update: краткое описание" && git push
```

---

## Последняя сессия: 9 декабря 2025, 15:30 (UTC+4)

## Что сделано в этой сессии

### CORE_NEW — Концептуально новая архитектура CRM

**Причина:** Обнаружены 10 дублей устройств "Apple iPhone 14 Pro" в одной заявке. Решено переделать систему правильно, а не патчить.

**Философия:** "Люди общаются. Машина ведёт учёт."
- Человеку — естественное общение в мессенджерах
- Машине — рутина, учёт, таблицы, поля (AI под капотом)
- БЕЗ канбанов, БЕЗ заполнения форм
- Отчётность генерируется автоматически из диалогов

### 1. Vision Document — СОЗДАН ✅
- Файл: `CORE_NEW/docs/00_VISION.md`
- Ключевой принцип: диалогоцентричная CRM
- Люди общаются как привыкли, AI делает всё остальное

### 2. PostgreSQL Schema — СОЗДАНА ✅
- Файл: `CORE_NEW/docs/02_DATABASE_SCHEMA.md`
- **11 таблиц вместо 50!**
- Все таблицы с префиксом `elo_`
- Центральная сущность: `elo_dialogs` (вместо appeals)
- EventStore: `elo_events`
- Context в JSONB (устройство, проблема, цена — внутри диалога)

### 3. Neo4j Graph Schema — СОЗДАНА ✅
- Файл: `CORE_NEW/docs/03_GRAPH_SCHEMA.md`
- Все лейблы с префиксом `ELO_`
- Узлы: `ELO_Client`, `ELO_Dialog`, `ELO_Fact`
- Связи: `ELO_HAS_DIALOG`, `ELO_FAMILY`, `ELO_REFERRED`, etc.
- PostgreSQL = источник правды, Neo4j = read-optimized projection

### 4. API Contracts v2 — СОЗДАНЫ ✅
- Файл: `CORE_NEW/docs/04_API_CONTRACTS.md`
- Диалогоцентричный подход: `/dialogs` вместо `/appeals`
- Context JSONB вместо отдельных таблиц
- AI Suggestions API: `/dialogs/{id}/suggestions`
- Events API для timeline/аналитики
- WebSocket события для realtime

---

## Что НЕ сделано (на следующую сессию)

1. **Переписать Core workflows для CORE_NEW**
   - Адаптировать под elo_dialogs вместо appeals
   - Использовать новый API v2

2. **Создать SQL миграции**
   - Скрипты создания elo_* таблиц
   - Миграция данных из старой схемы

3. **DNS + SSL для API**
   - `android-api.eldoleado.ru → 45.144.177.128`

---

## Ключевые файлы CORE_NEW

| Файл | Описание |
|------|----------|
| `CORE_NEW/docs/00_VISION.md` | Видение продукта |
| `CORE_NEW/docs/02_DATABASE_SCHEMA.md` | PostgreSQL схема (11 elo_* таблиц) |
| `CORE_NEW/docs/03_GRAPH_SCHEMA.md` | Neo4j схема (ELO_* лейблы) |
| `CORE_NEW/docs/04_API_CONTRACTS.md` | API v2 контракты |
| `CORE_NEW/Concept/ВИДЕНИЕ СИСТЕМЫ ELDOLIDO (2025 → 2030)` | Исходное видение |

---

## Серверы

| Сервер | IP | Порт | Назначение |
|--------|-----|------|------------|
| n8n | n8n.n8nsrv.ru | 443 | Workflow automation |
| Neo4j | 45.144.177.128 | 7474/7687 | Graph database |
| PostgreSQL | 185.221.214.83 | 6544 | Main database |
| Android API | 45.144.177.128 | 8780 | API Gateway (FastAPI) |
| Redis (RU) | 45.144.177.128 | 6379 | ai_extraction_queue |
| Redis (n8n) | 185.221.214.83 | 6379 | n8n cache |
| MCP Telegram | 217.145.79.27 | 443 | tg.eldoleado.ru |

---

## GitHub

- Репозиторий: https://github.com/n8nRemacs/Eldoleado

---

## Для продолжения

1. Прочитать `Start.md`
2. Прочитать `CORE_NEW/docs/00_VISION.md` — понять философию
3. Прочитать `CORE_NEW/docs/02_DATABASE_SCHEMA.md` — схема БД
4. Создать SQL миграции для elo_* таблиц
5. Адаптировать workflows под новую архитектуру
