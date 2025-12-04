# START - Контекст для продолжения работы

## Дата и время последнего обновления
**4 декабря 2025, 17:25 (UTC+4)**

---

## Текущее состояние проекта

### Что готово:

1. **Knowledge Base система (НОВОЕ)**
   - 294 компонента в project_components
   - 206 relations в component_relations
   - 1080 workflow nodes в 92 workflows
   - 22 flow docs в docs/flows/
   - CLAUDE.md с инструкциями для AI
   - Команды: `python scripts/full_sync.py`

2. **Android приложение (Eldoleado)**
   - Package: `com.eldoleado.app`
   - API возвращает devices[] с repairs[]
   - Собирается без ошибок

3. **API для devices/repairs**
   - API_Android_Device_Create/Update/Delete
   - API_Android_Repair_Create/Update/Delete
   - API_Android_Appeal_Detail возвращает devices[]

4. **Disambiguation workflow**
   - BAT_AI_Appeal_Router_v2_disambiguation.json
   - Различает устройства по модели и ремонту (не по владельцам)

5. **GitHub синхронизация**
   - Репозиторий: https://github.com/n8nRemacs/Eldoleado
   - Коммит: c4eaebe (58 files, +12966 lines)

6. **Neo4j граф**
   - Neo4j на `45.144.177.128:7474`
   - CRUD workflow активен

7. **n8n workflows (95 штук)**
   - Синхронизированы в KB
   - Теги: BattCRM + раздел (API, Core, In, Out, Tool, TaskWork)

---

## Knowledge Base Quick Commands

\`\`\`bash
# Полная синхронизация (n8n + KB + docs)
python scripts/full_sync.py

# Быстрая синхронизация
python scripts/full_sync.py --quick

# Обновить документацию потоков
python scripts/update_flow_docs.py --all

# Трассировка потока
python scripts/trace_flow.py "keyword"
\`\`\`

---

## Структура проекта

\`\`\`
Eldoleado/
├── app/                    # Android приложение
├── n8n_workflows/          # Все n8n workflows (синхронизированы)
├── workflows_to_import/    # Новые workflows для ручного импорта
│   └── modified/           # Модифицированные workflows
├── database/
│   ├── migrations/         # SQL миграции (017 последняя)
│   └── neo4j/              # Cypher скрипты
├── docs/flows/             # Автогенерируемая документация (22 docs)
├── scripts/                # KB и автоматизация
├── CLAUDE.md               # Инструкции для AI
├── KNOWLEDGE_BASE.md       # Автогенерируемая карта проекта
└── Start.md                # Этот файл
\`\`\`

---

## Статус данных

| Таблица | Количество |
|---------|-----------|
| project_components | 294 |
| component_relations | 206 |
| workflow_nodes | 1080 |
| appeals | 1 (тест) |
| appeal_devices | 7 (тест) |

---

## Серверы

| Сервер | IP/URL | Назначение |
|--------|--------|------------|
| n8n | n8n.n8nsrv.ru | Workflow automation |
| Neo4j | 45.144.177.128:7474 | Graph database |
| PostgreSQL | 185.221.214.83:6544 | Main database |

---

## Database Connection

\`\`\`
PostgreSQL: postgresql://supabase_admin:Mi31415926pS@185.221.214.83:6544/postgres
Neo4j: bolt://neo4j:Mi31415926pS@45.144.177.128:7687
\`\`\`

---

## Следующие шаги

1. **Android CRUD UI** - интерфейс для devices/repairs
2. **Context switching** - AI переключается между устройствами
3. **Тестирование disambiguation** - в реальных диалогах

---

**Перед завершением сессии**: обновить этот файл и git push
