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
**4 декабря 2025, 17:30 (UTC+4)**

---

## Текущее состояние проекта

### Что готово:

1. **Knowledge Base система**
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

6. **Neo4j граф**
   - Neo4j на `45.144.177.128:7474`
   - CRUD workflow активен

7. **n8n workflows (95 штук)**
   - Синхронизированы в KB
   - Теги: BattCRM + раздел (API, Core, In, Out, Tool, TaskWork)

8. **Система Touchpoints**
   - 4 типа касаний: `inbound`, `outbound`, `promo`, `mutual`
   - Автоматическое определение `mutual` по парам

9. **Client Merge (СПРОЕКТИРОВАНО)**
   - Таблица `client_merges` в PostgreSQL
   - Ребро `MERGED_INTO` в Neo4j
   - Логика объединения дубликатов клиентов

10. **Channel Enrichment (СПРОЕКТИРОВАНО)**
    - Классы идентификаторов (phone, telegram, vk, yandex, fingerprint, email, avito)
    - Маршруты обогащения профилей
    - Tracking система для отслеживания конверсий
    - Ребро `ENRICHED_VIA` в Neo4j

---

## Knowledge Base Quick Commands

```bash
# Полная синхронизация (n8n + KB + docs)
python scripts/full_sync.py

# Быстрая синхронизация
python scripts/full_sync.py --quick

# Обновить документацию потоков
python scripts/update_flow_docs.py --all

# Трассировка потока
python scripts/trace_flow.py "keyword"
```

---

## Структура проекта

```
Eldoleado/
├── app/                    # Android приложение
├── n8n_workflows/          # Все n8n workflows (синхронизированы)
├── workflows_to_import/    # Новые workflows для ручного импорта
│   └── modified/           # Модифицированные workflows
├── database/
│   ├── migrations/         # SQL миграции (017 последняя)
│   └── neo4j/              # Cypher скрипты
├── docs/flows/             # Автогенерируемая документация (22 docs)
├── Plans/                  # Планы и спецификации
│   ├── Eldoleado_Спецификация_Графа.md
│   └── Eldoleado full.md
├── scripts/                # KB и автоматизация
├── CLAUDE.md               # Инструкции для AI
├── KNOWLEDGE_BASE.md       # Автогенерируемая карта проекта
└── Start.md                # Этот файл
```

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

## Channel Enrichment (Обогащение профиля)

### Концепция
Получение новых идентификаторов клиента через целевые действия ("приманки").

### Классы идентификаторов (группы эквивалентности)

| Класс | Члены | Что получаем |
|-------|-------|--------------|
| phone | phone, whatsapp, max | phone_number |
| telegram | telegram | telegram_id |
| vk | vk | vk_id |
| yandex | yandex_maps | yandex_id |
| fingerprint | website | fp_hash |
| email | email | email |
| avito | avito | avito_user_id |

**Принцип:** Нет смысла получать WhatsApp, если есть телефон — это один класс. Но TG, VK, Yandex — новые классы.

### Ключевые маршруты

| Откуда → Куда | Приманка | Конверсия |
|---------------|----------|-----------|
| phone → telegram | "Подпишитесь на TG — акции" | 10% |
| phone → vk | "Вступите в VK — скидка 5%" | 8% |
| phone → yandex | "Оставьте отзыв — бонус" | 5% |
| telegram → phone | Кнопка "Поделиться контактом" | 25% |
| avito → telegram | "В TG отвечаем быстрее" | 15% |

### Таблицы БД (спроектированы)
- `identifier_classes` — классы идентификаторов
- `identifier_class_members` — члены классов
- `enrichment_routes` — маршруты обогащения
- `enrichment_action_templates` — шаблоны приманок
- `enrichment_tracking_codes` — tracking коды
- `client_enrichment_history` — история

---

## Система Touchpoints (касаний)

### Типы касаний:

| Тип | Направление | Ожидаем ответ? | Участвует в mutual |
|-----|-------------|----------------|-------------------|
| `inbound` | Клиент → Нам | Да (отвечаем) | Да |
| `outbound` | Мы → Клиент | Да | Да |
| `promo` | Мы → Клиент | Нет | Только с creates_entity |
| `mutual` | Двусторонний | - | Результат |

### Логика:
- `inbound` + есть `outbound` за период → `mutual`
- `inbound` + `promo` + `creates_entity=true` → `mutual`
- `promo` → всегда `promo` (mutual вычисляется при inbound)

---

## Client Merge (Объединение клиентов)

### Таблица client_merges

| Поле | Тип | Описание |
|------|-----|----------|
| master_client_id | UUID | Главный клиент |
| merged_client_id | UUID | Объединённый (деактивируется) |
| merge_reason | TEXT | same_phone, same_whatsapp, manual, ai_detected |
| merged_by | VARCHAR | ID оператора или "system" |

### Логика:
1. Все заявки merged_client переносятся на master_client
2. merged_client помечается неактивным
3. В Neo4j создаётся ребро `MERGED_INTO`

---

## Серверы

| Сервер | IP/URL | Назначение |
|--------|--------|------------|
| n8n | n8n.n8nsrv.ru | Workflow automation |
| Neo4j | 45.144.177.128:7474 | Graph database |
| PostgreSQL | 185.221.214.83:6544 | Main database |

---

## Database Connection

```
PostgreSQL: postgresql://supabase_admin:Mi31415926pS@185.221.214.83:6544/postgres
Neo4j: bolt://neo4j:Mi31415926pS@45.144.177.128:7687
```

---

## Neo4j CRUD API

**Endpoint:** `POST https://n8n.n8nsrv.ru/webhook/neo4j/crud`

```bash
# CREATE
curl -X POST "https://n8n.n8nsrv.ru/webhook/neo4j/crud" \
  -H "Content-Type: application/json" \
  -d '{"operation": "create", "nodeType": "Client", "nodeId": "id-123", "properties": {"name": "Test"}}'

# READ / UPDATE / DELETE — аналогично
```

---

## Neo4j ключевые рёбра

| Ребро | От → К | Описание |
|-------|--------|----------|
| OWNS | Client → Device | Владение устройством |
| HAS_PROBLEM | Device → Problem | Поломка |
| SOCIAL | Client → Client | Семья/друзья |
| REFERRED | Client → Client | Реферал |
| MERGED_INTO | Client → Client | Объединение дубликатов |
| HAS_CHANNEL | Client → Channel | Канал связи |
| ENRICHED_VIA | Client → Channel | Канал через enrichment |
| FROM/TO | Touchpoint → Client | Направление касания |

---

## Документация

- `Plans/Eldoleado_Спецификация_Графа.md` — полная спецификация графа (вершины, рёбра, enrichment)
- `Plans/Eldoleado full.md` — база знаний проекта (бизнес-логика, workflows, БД)
- `CLAUDE.md` — инструкции для AI
- `KNOWLEDGE_BASE.md` — автогенерируемая карта проекта

---

## Следующие шаги

1. **Android CRUD UI** - интерфейс для devices/repairs
2. **Context switching** - AI переключается между устройствами
3. **Создать SQL миграции для enrichment таблиц**
4. **Создать n8n workflow для enrichment**

---

**Перед завершением сессии**: обновить этот файл и git push
