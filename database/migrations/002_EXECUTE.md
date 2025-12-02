# Выполнение миграции 002: Context Fields Management

## 📋 Что делает эта миграция

1. **Создаёт таблицу `context_fields_config`** - конфигурация полей для сбора контекста
2. **Добавляет поле `context_completion_status`** в таблицу `appeals`
3. **Вставляет базовые данные** - 4 поля (type, model_id, repair_type_id, parts_owner)
4. **Создаёт helper функцию** `check_context_completion()` для автоматического расчёта
5. **Создаёт trigger** для автообновления при изменении полей appeal
6. **Обновляет существующие appeals** с начальным статусом

## 🚀 Выполнение

### Вариант 1: Через Supabase UI (рекомендуется)

1. Открыть https://supabase.n8nsrv.ru
2. Зайти в SQL Editor
3. Скопировать содержимое `002_context_fields_management.sql`
4. Выполнить
5. Проверить результат в разделе NOTICES

### Вариант 2: Через psql

```bash
psql "postgresql://postgres:Mi31415926pSss!@db.cfaelqdjknmopodvxmyw.supabase.co:5432/postgres" \
  -f database/migrations/002_context_fields_management.sql
```

### Вариант 3: Через Python скрипт

```bash
python run_migration.py database/migrations/002_context_fields_management.sql
```

## ✅ Проверка результата

### 1. Проверить создание таблицы

```sql
SELECT COUNT(*) FROM context_fields_config;
-- Должно быть 4 строки (для первого tenant)
```

### 2. Проверить поле в appeals

```sql
SELECT id, stage, context_completion_status
FROM appeals
LIMIT 5;
```

Ожидаемая структура `context_completion_status`:
```json
{
  "fields_collected": {
    "type": {"value": "ремонт", "collected_at": "...", "is_valid": true},
    "model_id": {"value": "uuid", "collected_at": "...", "is_valid": true},
    "repair_type_id": null,
    "parts_owner": null
  },
  "required_fields_count": 3,
  "collected_required_count": 2,
  "is_complete": false,
  "completion_percentage": 66.67
}
```

### 3. Проверить триггер

```sql
-- Создать тестовое обращение
INSERT INTO appeals (tenant_id, client_id, channel_id, type)
VALUES (
    (SELECT id FROM tenants LIMIT 1),
    (SELECT id FROM clients LIMIT 1),
    (SELECT id FROM channels LIMIT 1),
    'ремонт'
);

-- Обновить поле
UPDATE appeals
SET model_id = (SELECT id FROM models LIMIT 1)
WHERE id = (SELECT id FROM appeals ORDER BY created_at DESC LIMIT 1);

-- Проверить что context_completion_status обновился
SELECT context_completion_status
FROM appeals
ORDER BY created_at DESC
LIMIT 1;
```

### 4. Проверить helper функцию

```sql
SELECT check_context_completion(
    (SELECT id FROM appeals LIMIT 1),
    (SELECT tenant_id FROM appeals LIMIT 1)
);
```

## 📊 Новая структура

### Таблица context_fields_config

| Поле | Тип | Описание |
|------|-----|----------|
| id | UUID | Primary key |
| tenant_id | UUID | Tenant |
| deal_type_id | UUID | Тип сделки (nullable) |
| field_name | VARCHAR(100) | Имя поля (type, model_id...) |
| field_label | VARCHAR(255) | Название для UI |
| is_required | BOOLEAN | Обязательное поле? |
| collection_order | INT | Порядок сбора |
| tool_name | VARCHAR(100) | AI Tool для сбора |
| prompt_template | TEXT | Шаблон вопроса |
| validation_rules | JSONB | Правила валидации |
| is_active | BOOLEAN | Активно? |

### Поле appeals.context_completion_status

```json
{
  "fields_collected": {
    "field_name": {
      "value": "значение",
      "collected_at": "timestamp",
      "is_valid": true
    }
  },
  "required_fields_count": 3,
  "collected_required_count": 2,
  "is_complete": false,
  "completion_percentage": 66.67
}
```

## 🔄 Откат миграции

Если нужно откатить изменения:

```sql
-- Удалить триггер
DROP TRIGGER IF EXISTS trg_appeals_context_completion ON appeals;

-- Удалить функцию
DROP FUNCTION IF EXISTS trigger_update_context_completion();
DROP FUNCTION IF EXISTS check_context_completion(UUID, UUID);

-- Удалить поле
ALTER TABLE appeals DROP COLUMN IF EXISTS context_completion_status;

-- Удалить таблицу
DROP TABLE IF EXISTS context_fields_config;
```

## 📝 Примечания

- Миграция безопасна для production
- Не удаляет и не изменяет существующие данные
- Триггер обновляет статус автоматически при изменении полей
- Helper функция может быть вызвана вручную для пересчёта

## ⚠️ Важно

После выполнения миграции нужно обновить:
1. **BAT AI Appeal Router** - добавить логику проверки стадий
2. **BAT Appeal Manager** - устанавливать начальную стадию "Первичный контакт"
3. Документацию - Database_Structure_BatteryCRM_COMPLETE.md
