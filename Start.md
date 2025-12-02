# START - Контекст для продолжения работы

## Дата и время последнего обновления
**2 декабря 2025, 23:45 (UTC+4)**

---

## Текущее состояние проекта

### Всё работает:

1. **Android приложение**
   - Собирается без ошибок
   - Запускается на эмуляторе Pixel_7
   - UI работает (заявки, сообщения, кнопки)

2. **GitHub синхронизация**
   - Репозиторий: https://github.com/n8nRemacs/batterycrm
   - Последний коммит: `040785d` (Neo4j workflows)

3. **Neo4j интеграция (Eldoleado)**
   - Neo4j работает на `45.144.177.128:7474`
   - Созданы workflows для импорта в `workflows_to_import/`

---

## Что нужно сделать (Neo4j интеграция)

### 1. Выполнить индексы в Neo4j
```cypher
-- Открыть https://45.144.177.128:7474/browser/
-- Выполнить скрипт: database/neo4j/001_create_indexes.cypher
```

### 2. Импортировать workflows в n8n
Из папки `workflows_to_import/`:
- `BAT Neo4j CRUD.json` — заменяет существующий (добавлена безопасность)
- `BAT Neo4j Sync.json` — НОВЫЙ (синхронизация PostgreSQL → Neo4j)
- `BAT Neo4j Touchpoint Tracker.json` — НОВЫЙ (трекинг для disambiguation)

### 3. Интегрировать в основной поток
- BAT Client Creator → вызывать Neo4j Sync
- BAT Universal Batcher → вызывать Touchpoint Tracker
- BAT AI Appeal Router → вызывать Context Builder

---

## Структура проекта

```
BattCRMApp/
├── app/                    # Android приложение (основное)
├── n8n_workflows/          # Все n8n workflows
│   ├── API/                # API endpoints
│   ├── Core/               # Основные workflows
│   ├── In/                 # Входящие каналы
│   ├── Out/                # Исходящие каналы
│   ├── Tool/               # Инструменты
│   └── TaskWork/           # AI workers
├── workflows_to_import/    # НОВЫЕ workflows для импорта в n8n
├── database/
│   ├── migrations/         # SQL миграции PostgreSQL
│   └── neo4j/              # Cypher скрипты Neo4j
├── Plans/                  # Планы и документация
│   └── Eldoleado_Спецификация_Графа.md  # Спецификация графа
└── scripts/                # Скрипты автоматизации
```

---

## Команды для работы

### Сборка Android:
```bash
cd "C:/Users/User/Dropbox/BattCRMApp"
JAVA_HOME="/c/Program Files/Android/Android Studio/jbr" ./gradlew.bat assembleDebug
```

### Запуск на эмуляторе:
```bash
"$LOCALAPPDATA/Android/Sdk/platform-tools/adb.exe" install -r app/build/outputs/apk/debug/app-debug.apk
"$LOCALAPPDATA/Android/Sdk/platform-tools/adb.exe" shell monkey -p com.batterycrm.app -c android.intent.category.LAUNCHER 1
```

### Синхронизация с GitHub:
```bash
scripts\git-sync.bat
```

---

## Важные ссылки

- **GitHub**: https://github.com/n8nRemacs/batterycrm
- **n8n**: https://n8n.n8nsrv.ru
- **Neo4j**: https://45.144.177.128:7474
- **API Server**: 45.144.177.128
- **Database**: 185.221.214.83:6544

---

## Документация Neo4j

- `Plans/Eldoleado_Спецификация_Графа.md` — полная спецификация графа
- `workflows_to_import/README.md` — API reference для workflows

---

**Перед завершением сессии**: `scripts\git-sync.bat`
