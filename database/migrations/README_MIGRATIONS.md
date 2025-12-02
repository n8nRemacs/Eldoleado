# Database Migrations Guide

**Версия:** 1.0
**Дата:** 2025-11-23
**Статус:** Готово к выполнению

---

## Обзор миграций

Создано **7 файлов миграций** для добавления **13 новых таблиц** в систему BatteryCRM.

### Список миграций

| Файл | Таблицы | Описание |
|:-----|:--------|:---------|
| `003_new_tables_part1_multi_appeals.sql` | 3 | Multi-Appeals Support |
| `004_new_tables_part2_funnel.sql` | 2 | 9-Stage Funnel System |
| `005_new_tables_part3_promo.sql` | 3 | Promo Materials Automation |
| `006_new_tables_part4_pricing.sql` | 3 | Price Calculation Engine |
| `007_new_tables_part5_ai_voice.sql` | 2 | AI & Voice Processing |
| `008_new_tables_part6_crm_integrations.sql` | 3 | CRM Integrations |
| `009_new_tables_part7_ttl_policies.sql` | 2 | TTL Policies & Archival |

**Всего:** 18 таблиц создаётся (13 новых + 5 существующих обновляются)

---

## Подробности по таблицам

### Part 1: Multi-Appeals Support (3 таблицы)

1. **repair_categories** — Категории ремонта
   - Справочник: Дисплей, Корпус, Аккумулятор и т.д.
   - 10 категорий вставляются автоматически для всех тенантов

2. **appeal_devices** — Устройства в заявке
   - Поддержка мультизаявок (несколько устройств)
   - Связь с appeal_id, brand_id, model_id

3. **appeal_repairs** — Неисправности устройств
   - Привязка к appeal_devices
   - Связь с repair_categories, issue_types
   - Статусы: pending, in_progress, completed, cancelled

**Дополнительно:**
- Добавляется флаг `is_multi_appeal` в таблицу `appeals`
- Добавляются поля `qr_generated_at`, `qr_expires_at` в `appeals`

---

### Part 2: 9-Stage Funnel (2 таблицы)

1. **appeal_stages** — Новая система этапов (9 стадий)
   - New Empty → Ремонт выполнен
   - Группировка: initial, processing, confirmed, completed, cancelled

2. **appeal_stage_transitions** — История переходов
   - Полный аудит изменений этапов
   - Связь с operator_id, prev_stage_id, new_stage_id

**Миграция данных:**
- Старая 5-этапная система мигрирует на 9-этапную
- Автоматический маппинг для всех существующих заявок

---

### Part 3: Promo Materials (3 таблицы)

1. **promo_materials** — Библиотека промо-материалов
   - PDF, изображения, видео
   - Привязка к брендам/категориям

2. **promo_triggers** — Правила автоотправки
   - Триггеры: stage_change, brand_model, issue_type, time_based
   - Условия в JSONB

3. **promo_history** — История отправки
   - Отслеживание автоматических и ручных отправок

---

### Part 4: Pricing Engine (3 таблицы)

1. **price_lists** — Прайс-листы поставщиков
   - Валидность (valid_from, valid_until)
   - Активация/деактивация

2. **price_list_items** — Позиции прайсов
   - Детали, артикулы, цены
   - Наличие: in_stock, out_of_stock, on_order

3. **price_mappings** — Маппинг деталей на устройства
   - Привязка к брендам/моделям/категориям
   - Расчёт розничной цены с наценкой

---

### Part 5: AI & Voice Processing (2 таблицы)

1. **ai_chat_memory** — Память AI (контекст)
   - Роли: user, assistant, system
   - Использованные токены

2. **voice_transcriptions** — Транскрибация
   - Голосовые сообщения
   - Записи звонков (incoming/outgoing)
   - Whisper engine

---

### Part 6: CRM Integrations (3 таблицы)

1. **crm_integrations** — Настройки CRM
   - Типы: livesklad, remonline
   - API credentials
   - Направление синхронизации

2. **crm_sync_history** — История синхронизаций
   - Статусы: pending, success, failed, retrying
   - Request/response payloads

3. **crm_field_mappings** — Маппинг полей
   - Соответствие полей BatteryCRM ↔ CRM
   - Правила трансформации

---

### Part 7: TTL Policies & Archival (2 таблицы)

1. **data_retention_policies** — Политики хранения
   - Сущности: messages_history, voice_transcriptions, ai_chat_memory
   - Действия: archive, delete, anonymize
   - Вставляются значения по умолчанию для всех тенантов

2. **archived_data** — Архивное хранилище
   - JSONB storage для архивированных данных

---

## Порядок выполнения миграций

### Вариант 1: Через psql (рекомендуется)

```bash
# Подключение к БД
psql -h localhost -U postgres -d batterycrm

# Выполнение по порядку
\i database/migrations/003_new_tables_part1_multi_appeals.sql
\i database/migrations/004_new_tables_part2_funnel.sql
\i database/migrations/005_new_tables_part3_promo.sql
\i database/migrations/006_new_tables_part4_pricing.sql
\i database/migrations/007_new_tables_part5_ai_voice.sql
\i database/migrations/008_new_tables_part6_crm_integrations.sql
\i database/migrations/009_new_tables_part7_ttl_policies.sql
```

### Вариант 2: Через PostgreSQL MCP API

```bash
# Использовать Claude Code с MCP инструментом
# Выполнить каждый файл через pg_execute_sql
```

### Вариант 3: Через Docker (если БД в контейнере)

```bash
docker exec -i postgres_container psql -U postgres -d batterycrm < database/migrations/003_new_tables_part1_multi_appeals.sql
docker exec -i postgres_container psql -U postgres -d batterycrm < database/migrations/004_new_tables_part2_funnel.sql
# ... и так далее
```

---

## Проверка после выполнения

### 1. Проверить созданные таблицы

```sql
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name IN (
    'repair_categories',
    'appeal_devices',
    'appeal_repairs',
    'appeal_stages',
    'appeal_stage_transitions',
    'promo_materials',
    'promo_triggers',
    'promo_history',
    'price_lists',
    'price_list_items',
    'price_mappings',
    'ai_chat_memory',
    'voice_transcriptions',
    'crm_integrations',
    'crm_sync_history',
    'crm_field_mappings',
    'data_retention_policies',
    'archived_data'
  )
ORDER BY table_name;
```

Должно вернуть **18 таблиц**.

### 2. Проверить вставленные данные

```sql
-- Проверить категории ремонта
SELECT tenant_id, COUNT(*) as categories_count
FROM repair_categories
GROUP BY tenant_id;
-- Должно быть 10 категорий на каждого тенанта

-- Проверить этапы воронки
SELECT tenant_id, COUNT(*) as stages_count
FROM appeal_stages
GROUP BY tenant_id;
-- Должно быть 9 этапов на каждого тенанта

-- Проверить политики хранения
SELECT tenant_id, COUNT(*) as policies_count
FROM data_retention_policies
GROUP BY tenant_id;
-- Должно быть 5 политик на каждого тенанта
```

### 3. Проверить индексы

```sql
SELECT tablename, indexname
FROM pg_indexes
WHERE schemaname = 'public'
  AND tablename IN (
    'repair_categories', 'appeal_devices', 'appeal_repairs',
    'appeal_stages', 'appeal_stage_transitions',
    'promo_materials', 'promo_triggers', 'promo_history',
    'price_lists', 'price_list_items', 'price_mappings',
    'ai_chat_memory', 'voice_transcriptions',
    'crm_integrations', 'crm_sync_history', 'crm_field_mappings',
    'data_retention_policies', 'archived_data'
  )
ORDER BY tablename, indexname;
```

### 4. Проверить ограничения (constraints)

```sql
SELECT
    tc.table_name,
    tc.constraint_name,
    tc.constraint_type
FROM information_schema.table_constraints tc
WHERE tc.table_schema = 'public'
  AND tc.table_name IN (
    'repair_categories', 'appeal_devices', 'appeal_repairs',
    'appeal_stages', 'appeal_stage_transitions',
    'promo_materials', 'promo_triggers', 'promo_history',
    'price_lists', 'price_list_items', 'price_mappings',
    'ai_chat_memory', 'voice_transcriptions',
    'crm_integrations', 'crm_sync_history', 'crm_field_mappings',
    'data_retention_policies', 'archived_data'
  )
ORDER BY tc.table_name, tc.constraint_type;
```

---

## Откат миграций (Rollback)

Если нужно откатить миграции:

```sql
-- ВНИМАНИЕ: Удалит все данные из новых таблиц!

DROP TABLE IF EXISTS archived_data CASCADE;
DROP TABLE IF EXISTS data_retention_policies CASCADE;

DROP TABLE IF EXISTS crm_field_mappings CASCADE;
DROP TABLE IF EXISTS crm_sync_history CASCADE;
DROP TABLE IF EXISTS crm_integrations CASCADE;

DROP TABLE IF EXISTS voice_transcriptions CASCADE;
DROP TABLE IF EXISTS ai_chat_memory CASCADE;

DROP TABLE IF EXISTS price_mappings CASCADE;
DROP TABLE IF EXISTS price_list_items CASCADE;
DROP TABLE IF EXISTS price_lists CASCADE;

DROP TABLE IF EXISTS promo_history CASCADE;
DROP TABLE IF EXISTS promo_triggers CASCADE;
DROP TABLE IF EXISTS promo_materials CASCADE;

DROP TABLE IF EXISTS appeal_stage_transitions CASCADE;
DROP TABLE IF EXISTS appeal_stages CASCADE;

DROP TABLE IF EXISTS appeal_repairs CASCADE;
DROP TABLE IF EXISTS appeal_devices CASCADE;
DROP TABLE IF EXISTS repair_categories CASCADE;

-- Удалить добавленные колонки из appeals
ALTER TABLE appeals DROP COLUMN IF EXISTS is_multi_appeal;
ALTER TABLE appeals DROP COLUMN IF EXISTS qr_generated_at;
ALTER TABLE appeals DROP COLUMN IF EXISTS qr_expires_at;
```

---

## Особенности миграций

### Идемпотентность

Все миграции используют `CREATE TABLE IF NOT EXISTS` — можно запускать многократно без ошибок.

### Cascade Deletes

Все FK используют `ON DELETE CASCADE` — при удалении тенанта все связанные данные удаляются автоматически.

### Tenant Isolation

Все таблицы содержат `tenant_id` — полная изоляция данных между тенантами.

### Default Values

Автоматически вставляются:
- 10 категорий ремонта
- 9 этапов воронки
- 5 политик хранения данных

### Миграция существующих данных

**Migration 004** автоматически мигрирует:
- Старые этапы (5) → Новые этапы (9)
- Все существующие заявки получают новый `appeal_stage_id`

---

## Тестирование

### 1. Dev окружение

```bash
# Применить на dev БД
psql -h dev-db-host -U postgres -d batterycrm_dev < migrations/*.sql
```

### 2. Staging окружение

```bash
# Применить на staging БД
psql -h staging-db-host -U postgres -d batterycrm_staging < migrations/*.sql
```

### 3. Production окружение

```bash
# ВАЖНО: Сделать backup перед применением!
pg_dump -h prod-db-host -U postgres batterycrm_prod > backup_before_migrations.sql

# Применить миграции
psql -h prod-db-host -U postgres -d batterycrm_prod < migrations/*.sql
```

---

## Troubleshooting

### Ошибка: relation already exists

**Решение:** Миграции идемпотентны, просто продолжайте выполнение.

### Ошибка: foreign key constraint violation

**Причина:** Нарушен порядок выполнения миграций.
**Решение:** Выполняйте строго по порядку 003 → 004 → ... → 009.

### Ошибка: duplicate key value violates unique constraint

**Причина:** Данные уже вставлены (например, repair_categories).
**Решение:** Нормально, INSERT использует `WHERE NOT EXISTS`.

---

## Следующие шаги

После успешного выполнения миграций:

1. ✅ Обновить API endpoints для работы с новыми таблицами
2. ✅ Обновить n8n workflows (Multi-Entity Extractor)
3. ✅ Реализовать Multi-Appeal API
4. ✅ Реализовать Promo Materials API
5. ✅ Реализовать Pricing Engine API
6. ✅ Обновить Android app (новые экраны)
7. ✅ Обновить Desktop app (аналитика, админка)

---

## Контакты

При возникновении вопросов или проблем:
- Документация: `docs/current/02_DATABASE_SCHEMA_EXTENDED.md`
- Start file: `Start.md`

---

**Дата создания:** 2025-11-23
**Версия:** 1.0
