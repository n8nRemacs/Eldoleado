# START - Контекст для продолжения работы

## Дата и время последнего обновления
**3 декабря 2025, 17:20 (UTC+4)**

---

## Текущее состояние проекта

### Всё работает:

1. **Android приложение (Eldoleado)**
   - Package: `com.eldoleado.app`
   - Собирается без ошибок
   - Запускается на эмуляторе Pixel_7
   - UI работает (заявки, сообщения, кнопки)

2. **GitHub синхронизация**
   - Репозиторий: https://github.com/n8nRemacs/Eldoleado
   - Проект переименован из BatteryCRM → Eldoleado

3. **Neo4j граф (ПОЛНОСТЬЮ РАБОТАЕТ)**
   - Neo4j на `45.144.177.128:7474`
   - Credentials: `neo4j / Mi31415926pS`
   - Индексы и constraints созданы
   - CRUD workflow активен и протестирован

4. **n8n workflows (синхронизированы)**
   - 50+ workflows в папке `n8n_workflows/`
   - Теги: BattCRM + раздел (API, Core, In, Out, Tool, TaskWork)
   - Neo4j CRUD работает через webhook

---

## Структура проекта

```
Eldoleado/
├── app/                    # Android приложение (основное)
│   └── src/main/java/com/eldoleado/app/
├── n8n_workflows/          # Все n8n workflows (синхронизированы)
│   ├── API/                # API endpoints
│   ├── Core/               # Основные workflows (включая Neo4j)
│   ├── In/                 # Входящие каналы
│   ├── Out/                # Исходящие каналы
│   ├── Tool/               # Инструменты
│   └── TaskWork/           # AI workers
├── workflows_to_import/    # Новые workflows для ручного импорта
├── database/
│   ├── migrations/         # SQL миграции PostgreSQL
│   └── neo4j/              # Cypher скрипты Neo4j
├── Plans/                  # Планы и документация
│   └── Eldoleado_Спецификация_Графа.md
└── scripts/                # Скрипты автоматизации
    └── sync_n8n_workflows.py
```

---

## Команды для работы

### Сборка Android:
```bash
cd "C:/Users/User/Documents/Eldoleado"
JAVA_HOME="/c/Program Files/Android/Android Studio/jbr" ./gradlew.bat assembleDebug
```

### Запуск на эмуляторе:
```bash
"$LOCALAPPDATA/Android/Sdk/platform-tools/adb.exe" install -r app/build/outputs/apk/debug/app-debug.apk
"$LOCALAPPDATA/Android/Sdk/platform-tools/adb.exe" shell monkey -p com.eldoleado.app -c android.intent.category.LAUNCHER 1
```

### Синхронизация workflows с n8n:
```bash
curl -s -X GET "https://n8n.n8nsrv.ru/api/v1/workflows" -H "X-N8N-API-KEY: <YOUR_KEY>" > temp_workflows.json
python scripts/sync_n8n_workflows.py
```

### Тест Neo4j напрямую:
```bash
curl -s -X POST "http://45.144.177.128:7474/db/neo4j/tx/commit" \
  -H "Content-Type: application/json" \
  -u "neo4j:Mi31415926pS" \
  -d '{"statements": [{"statement": "RETURN 1 as test"}]}'
```

---

## Серверы

| Сервер | IP/URL | Назначение |
|--------|--------|------------|
| n8n | 185.221.214.83 / n8n.n8nsrv.ru | Workflow automation |
| Neo4j | 45.144.177.128:7474 | Graph database |
| PostgreSQL | 185.221.214.83:6544 | Main database |
| API | 45.144.177.128 | Backend API |

---

## Neo4j Credentials

```
Host: 45.144.177.128
Port: 7474 (HTTP), 7687 (Bolt)
User: neo4j
Password: Mi31415926pS
```

---

## Neo4j CRUD API (РАБОТАЕТ)

**Endpoint:** `POST https://n8n.n8nsrv.ru/webhook/neo4j/crud`

```bash
# CREATE
curl -X POST "https://n8n.n8nsrv.ru/webhook/neo4j/crud" \
  -H "Content-Type: application/json" \
  -d '{"operation": "create", "nodeType": "Client", "nodeId": "id-123", "properties": {"name": "Test"}}'

# READ (один узел)
curl -X POST ... -d '{"operation": "read", "nodeType": "Client", "nodeId": "id-123"}'

# READ (все узлы типа)
curl -X POST ... -d '{"operation": "read", "nodeType": "Client"}'

# UPDATE
curl -X POST ... -d '{"operation": "update", "nodeType": "Client", "nodeId": "id-123", "properties": {"name": "New"}}'

# DELETE
curl -X POST ... -d '{"operation": "delete", "nodeType": "Client", "nodeId": "id-123"}'
```

---

## Документация

- `Plans/Eldoleado_Спецификация_Графа.md` — полная спецификация графа
- `workflows_to_import/README.md` — API reference для Neo4j workflows
- `database/neo4j/001_create_indexes.cypher` — индексы и constraints

---

## Следующие шаги

1. **Интегрировать Neo4j в основной поток:**
   - BAT Client Creator → вызывать Neo4j Sync
   - BAT Universal Batcher → вызывать Touchpoint Tracker
   - BAT AI Appeal Router → вызывать Context Builder

2. **Создать дополнительные Neo4j workflows:**
   - Neo4j Sync (PostgreSQL → Neo4j)
   - Neo4j Touchpoint Tracker
   - Neo4j Context Builder

---

**Перед завершением сессии**: обновить этот файл и git push
