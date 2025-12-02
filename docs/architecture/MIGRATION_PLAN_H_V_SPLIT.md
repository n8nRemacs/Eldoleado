# План миграции: Монолит → Horizontal/Vertical Architecture
## От 65 таблиц к модульной H/V системе

**Дата создания:** 24 ноября 2025
**Версия:** 1.0
**Статус:** Ready for execution

---

## 📋 Оглавление

1. [Текущее состояние](#текущее-состояние)
2. [Целевая архитектура](#целевая-архитектура)
3. [Стратегия миграции](#стратегия-миграции)
4. [Phase 0: Анализ и подготовка](#phase-0-анализ-и-подготовка)
5. [Phase 1: Создание schemas](#phase-1-создание-schemas)
6. [Phase 2: Миграция данных](#phase-2-миграция-данных)
7. [Phase 3: API split](#phase-3-api-split)
8. [Phase 4: Testing & Rollout](#phase-4-testing--rollout)
9. [Rollback план](#rollback-план)
10. [Риски и митигация](#риски-и-митигация)

---

## Текущее состояние

### Структура БД (монолит):

```
Database: batterycrm
├─ 65 таблиц (все в одной схеме public)
├─ Всё заточено под repair вертикаль
├─ Нет разделения horizontal/vertical
└─ Нет поддержки multi-vertical tenant'ов
```

### Ключевые таблицы:

**Универсальные (будут в horizontal):**
- tenants, clients, operators
- appeals, messages
- channels, push_tokens
- utm_tags, sessions

**Repair-specific (будут в vertical_repair):**
- brands, models, device_types
- categories, issue_types
- appeal_devices, appeal_repairs
- price_lists, price_list_items

**Смешанные (нужно решить):**
- appeal_stages (настраиваемые, но vertical-specific)
- promo_materials (настраиваемые)

---

## Целевая архитектура

### Структура БД (H/V split):

```
Database: batterycrm

Schema: horizontal (27 таблиц)
├─ Универсальные сущности
│  ├─ tenants
│  ├─ tenant_verticals (M2M) ⭐ НОВАЯ
│  ├─ leads (renamed from clients) ⭐ ПЕРЕИМЕНОВАНА
│  ├─ appeals (+ vertical_id) ⭐ ИЗМЕНЕНА
│  ├─ messages
│  ├─ operators
│  └─ ...
│
└─ Универсальные сервисы
   ├─ channels
   ├─ ai_prompts ⭐ НОВАЯ
   ├─ ai_tasks ⭐ НОВАЯ
   └─ ...

Schema: vertical_repair (12 таблиц)
├─ Справочники
│  ├─ brands
│  ├─ models
│  ├─ device_types
│  ├─ categories
│  └─ issue_types
│
├─ Данные
│  ├─ appeal_devices
│  ├─ appeal_repairs
│  ├─ price_lists
│  └─ price_list_items
│
└─ Конфигурация
   └─ tenant_config ⭐ НОВАЯ
```

---

## Стратегия миграции

### Принципы:

1. **Zero downtime** — система работает во время миграции
2. **Backward compatibility** — старые API работают через aliases
3. **Постепенность** — мигрируем по этапам, тестируем каждый
4. **Rollback готовность** — можем откатиться на любом этапе

### Подход:

**"Dual Write, Gradual Read Switch"**

```
Phase 1: Создаем новые schemas
Phase 2: Dual write (пишем в старые + новые таблицы)
Phase 3: Переключаем read на новые таблицы
Phase 4: Удаляем старые таблицы
```

---

## Phase 0: Анализ и подготовка

### Срок: 3-5 дней

### Задачи:

#### 1. Инвентаризация таблиц

```sql
-- Скрипт анализа текущей БД
SELECT
  table_name,
  pg_size_pretty(pg_total_relation_size(quote_ident(table_name))) as size,
  (SELECT COUNT(*) FROM information_schema.columns
   WHERE table_name = t.table_name) as column_count
FROM information_schema.tables t
WHERE table_schema = 'public'
ORDER BY pg_total_relation_size(quote_ident(table_name)) DESC;
```

#### 2. Классификация таблиц

Создать файл `table_classification.md`:

```markdown
# Классификация таблиц

## HORIZONTAL (27 таблиц):
- tenants ✅
- clients → leads (rename) ⚠️
- appeals (add vertical_id) ⚠️
- messages ✅
- operators ✅
- channels ✅
- ...

## VERTICAL_REPAIR (12 таблиц):
- brands ✅
- models ✅
- device_types ✅
- categories ✅
- issue_types ✅
- appeal_devices ✅
- appeal_repairs ✅
- price_lists ✅
- price_list_items ✅
- ...

## НОВЫЕ таблицы:
- horizontal.tenant_verticals
- horizontal.ai_prompts
- horizontal.ai_tasks
- vertical_repair.tenant_config
```

#### 3. Анализ зависимостей

```sql
-- Найти все FK constraints
SELECT
    tc.table_name,
    kcu.column_name,
    ccu.table_name AS foreign_table_name,
    ccu.column_name AS foreign_column_name
FROM information_schema.table_constraints AS tc
JOIN information_schema.key_column_usage AS kcu
  ON tc.constraint_name = kcu.constraint_name
JOIN information_schema.constraint_column_usage AS ccu
  ON ccu.constraint_name = tc.constraint_name
WHERE tc.constraint_type = 'FOREIGN KEY'
ORDER BY tc.table_name;
```

#### 4. Backup текущей БД

```bash
# Full backup
pg_dump -h 185.221.214.83 -p 6544 -U supabase_admin -d batterycrm \
  -F c -f backup_before_migration_$(date +%Y%m%d).dump

# Schema only
pg_dump -h 185.221.214.83 -p 6544 -U supabase_admin -d batterycrm \
  --schema-only -f schema_before_migration.sql
```

### Deliverables:

- ✅ Список всех таблиц с классификацией
- ✅ Граф зависимостей
- ✅ Backup БД
- ✅ План миграции утвержден

---

## Phase 1: Создание schemas

### Срок: 5-7 дней

### Задача 1.1: Создать schemas

```sql
-- Создаем новые schemas
CREATE SCHEMA IF NOT EXISTS horizontal;
CREATE SCHEMA IF NOT EXISTS vertical_repair;

-- Комментарии
COMMENT ON SCHEMA horizontal IS 'Universal platform core - leads, appeals, messages, AI';
COMMENT ON SCHEMA vertical_repair IS 'Repair vertical - devices, repairs, pricing';
```

### Задача 1.2: Создать новые таблицы horizontal

#### tenant_verticals (M2M связь):

```sql
CREATE TABLE horizontal.tenant_verticals (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id UUID NOT NULL,
  vertical_id VARCHAR(50) NOT NULL, -- 'repair', 'auto', 'real_estate'

  -- Статус
  enabled BOOLEAN DEFAULT true,

  -- Конфигурация
  config JSONB DEFAULT '{}',

  -- Даты
  installed_at TIMESTAMPTZ DEFAULT NOW(),
  disabled_at TIMESTAMPTZ,

  -- История включений/выключений
  toggle_history JSONB DEFAULT '[]',

  CONSTRAINT unique_tenant_vertical UNIQUE(tenant_id, vertical_id)
);

CREATE INDEX idx_tenant_verticals_tenant ON horizontal.tenant_verticals(tenant_id);
CREATE INDEX idx_tenant_verticals_vertical ON horizontal.tenant_verticals(vertical_id);
CREATE INDEX idx_tenant_verticals_enabled ON horizontal.tenant_verticals(tenant_id, enabled);
```

#### ai_prompts (универсальное хранилище промптов):

```sql
CREATE TABLE horizontal.ai_prompts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

  -- Идентификация
  name VARCHAR(100) NOT NULL, -- 'extract_device', 'generate_response'
  vertical_id VARCHAR(50), -- NULL = universal, 'repair' = vertical-specific

  -- Промпт
  prompt_template TEXT NOT NULL,
  system_message TEXT,

  -- Параметры
  model VARCHAR(50) DEFAULT 'claude-3-haiku-20240307',
  temperature DECIMAL(3, 2) DEFAULT 0.7,
  max_tokens INTEGER DEFAULT 1000,

  -- Версионирование
  version INTEGER DEFAULT 1,
  is_active BOOLEAN DEFAULT true,

  -- Метаданные
  description TEXT,
  examples JSONB,

  -- Даты
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW(),

  CONSTRAINT unique_prompt_version UNIQUE(name, vertical_id, version)
);

CREATE INDEX idx_ai_prompts_name ON horizontal.ai_prompts(name, vertical_id, is_active);
```

#### ai_tasks (очередь задач для AI):

```sql
CREATE TABLE horizontal.ai_tasks (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

  -- Связь
  tenant_id UUID NOT NULL,
  appeal_id UUID,

  -- Задача
  task_type VARCHAR(50) NOT NULL, -- 'extract_entities', 'generate_response'
  prompt_id UUID REFERENCES horizontal.ai_prompts(id),

  -- Входные данные
  input_data JSONB NOT NULL,

  -- Результат
  status VARCHAR(20) DEFAULT 'pending', -- pending/processing/completed/failed
  output_data JSONB,
  error_message TEXT,

  -- AI метрики
  tokens_used INTEGER,
  execution_time_ms INTEGER,
  model_used VARCHAR(50),

  -- Приоритет
  priority INTEGER DEFAULT 0, -- выше = приоритетнее

  -- Даты
  created_at TIMESTAMPTZ DEFAULT NOW(),
  started_at TIMESTAMPTZ,
  completed_at TIMESTAMPTZ
);

CREATE INDEX idx_ai_tasks_status ON horizontal.ai_tasks(status, priority, created_at);
CREATE INDEX idx_ai_tasks_tenant ON horizontal.ai_tasks(tenant_id);
CREATE INDEX idx_ai_tasks_appeal ON horizontal.ai_tasks(appeal_id);
```

### Задача 1.3: Мигрировать таблицы в horizontal

**Стратегия:** Копируем структуру, данные мигрируем позже

```sql
-- Создаем таблицы в horizontal со структурой из public
CREATE TABLE horizontal.tenants (LIKE public.tenants INCLUDING ALL);
CREATE TABLE horizontal.operators (LIKE public.operators INCLUDING ALL);
CREATE TABLE horizontal.messages (LIKE public.messages INCLUDING ALL);
CREATE TABLE horizontal.channels (LIKE public.channels INCLUDING ALL);
CREATE TABLE horizontal.push_tokens (LIKE public.push_tokens INCLUDING ALL);
-- ... остальные 22 таблицы

-- Важно: appeals с изменениями
CREATE TABLE horizontal.appeals (
  LIKE public.appeals INCLUDING ALL,

  -- Добавляем новые поля
  vertical_id VARCHAR(50), -- 'repair', 'auto', etc
  vertical_status VARCHAR(20) DEFAULT 'active', -- active/disabled/orphaned

  -- Для миграции между вертикалями
  previous_vertical_id VARCHAR(50),
  migration_data JSONB,
  migrated_at TIMESTAMPTZ
);

ALTER TABLE horizontal.appeals
ADD CONSTRAINT fk_appeals_tenant_vertical
FOREIGN KEY (tenant_id, vertical_id)
REFERENCES horizontal.tenant_verticals(tenant_id, vertical_id);

CREATE INDEX idx_appeals_vertical ON horizontal.appeals(tenant_id, vertical_id);

-- clients → leads (переименование)
CREATE TABLE horizontal.leads (
  LIKE public.clients INCLUDING ALL
);

-- Добавить комментарий
COMMENT ON TABLE horizontal.leads IS 'End customers of tenants (renamed from clients for clarity)';
```

### Задача 1.4: Мигрировать таблицы в vertical_repair

```sql
-- Справочники
CREATE TABLE vertical_repair.brands (LIKE public.brands INCLUDING ALL);
CREATE TABLE vertical_repair.models (LIKE public.models INCLUDING ALL);
CREATE TABLE vertical_repair.device_types (LIKE public.device_types INCLUDING ALL);
CREATE TABLE vertical_repair.categories (LIKE public.categories INCLUDING ALL);
CREATE TABLE vertical_repair.issue_types (LIKE public.issue_types INCLUDING ALL);

-- Данные
CREATE TABLE vertical_repair.appeal_devices (LIKE public.appeal_devices INCLUDING ALL);
CREATE TABLE vertical_repair.appeal_repairs (LIKE public.appeal_repairs INCLUDING ALL);
CREATE TABLE vertical_repair.price_lists (LIKE public.price_lists INCLUDING ALL);
CREATE TABLE vertical_repair.price_list_items (LIKE public.price_list_items INCLUDING ALL);
CREATE TABLE vertical_repair.price_mappings (LIKE public.price_mappings INCLUDING ALL);

-- Новая таблица: конфигурация вертикали для tenant'а
CREATE TABLE vertical_repair.tenant_config (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id UUID NOT NULL UNIQUE,

  -- Статус
  enabled BOOLEAN DEFAULT true,

  -- Настройки воронки
  funnel_stages JSONB DEFAULT '["Новая", "Диагностика", "Ремонт", "Готово"]',

  -- AI настройки
  rag_prompts JSONB DEFAULT '{}',
  ai_auto_responses JSONB DEFAULT '{}',

  -- Триггеры
  triggers JSONB DEFAULT '[]',

  -- Калькулятор цен
  price_calculator_config JSONB DEFAULT '{}',

  -- Метаданные
  config_version VARCHAR(20) DEFAULT '1.0',

  -- Даты
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW(),
  disabled_at TIMESTAMPTZ,
  disabled_until TIMESTAMPTZ
);

CREATE INDEX idx_tenant_config_tenant ON vertical_repair.tenant_config(tenant_id);
CREATE INDEX idx_tenant_config_enabled ON vertical_repair.tenant_config(enabled);
```

### Задача 1.5: Обновить Foreign Keys

```sql
-- FK в vertical_repair должны ссылаться на horizontal
ALTER TABLE vertical_repair.appeal_devices
DROP CONSTRAINT IF EXISTS appeal_devices_appeal_id_fkey,
ADD CONSTRAINT fk_appeal_devices_appeal
FOREIGN KEY (appeal_id) REFERENCES horizontal.appeals(id) ON DELETE CASCADE;

ALTER TABLE vertical_repair.appeal_repairs
DROP CONSTRAINT IF EXISTS appeal_repairs_device_id_fkey,
ADD CONSTRAINT fk_appeal_repairs_device
FOREIGN KEY (device_id) REFERENCES vertical_repair.appeal_devices(id) ON DELETE CASCADE;

-- И так далее для всех FK между schemas
```

### Deliverables Phase 1:

- ✅ Schemas созданы (horizontal, vertical_repair)
- ✅ Все таблицы созданы с правильной структурой
- ✅ FK настроены между schemas
- ✅ Индексы созданы
- ✅ Новые таблицы (tenant_verticals, ai_prompts, ai_tasks, tenant_config)

---

## Phase 2: Миграция данных

### Срок: 7-10 дней

### Стратегия: Dual Write

**Концепция:** Пишем данные одновременно в старые и новые таблицы

```
Write запрос → Application Layer
                ├─ Write to public.* (старая схема)
                └─ Write to horizontal.* / vertical_repair.* (новая схема)

Read запрос → Application Layer
              └─ Read from public.* (пока старая)
```

### Задача 2.1: Миграция существующих данных

```sql
-- Миграция tenants
INSERT INTO horizontal.tenants
SELECT * FROM public.tenants;

-- Миграция clients → leads
INSERT INTO horizontal.leads
SELECT * FROM public.clients;

-- Миграция appeals (с добавлением vertical_id)
INSERT INTO horizontal.appeals (
  -- все старые поля
  id, tenant_id, client_id, status, source, created_at, ...,
  -- новые поля
  vertical_id,
  vertical_status
)
SELECT
  id, tenant_id, client_id, status, source, created_at, ...,
  'repair' as vertical_id, -- все существующие заявки = repair
  'active' as vertical_status
FROM public.appeals;

-- Миграция messages
INSERT INTO horizontal.messages
SELECT * FROM public.messages;

-- Миграция operators
INSERT INTO horizontal.operators
SELECT * FROM public.operators;

-- ... остальные horizontal таблицы

-- Миграция vertical_repair таблиц
INSERT INTO vertical_repair.brands SELECT * FROM public.brands;
INSERT INTO vertical_repair.models SELECT * FROM public.models;
INSERT INTO vertical_repair.categories SELECT * FROM public.categories;
INSERT INTO vertical_repair.appeal_devices SELECT * FROM public.appeal_devices;
INSERT INTO vertical_repair.appeal_repairs SELECT * FROM public.appeal_repairs;
-- ... остальные vertical таблицы
```

### Задача 2.2: Создать tenant_verticals записи

```sql
-- Все существующие tenant'ы используют repair вертикаль
INSERT INTO horizontal.tenant_verticals (tenant_id, vertical_id, enabled, installed_at)
SELECT
  id as tenant_id,
  'repair' as vertical_id,
  true as enabled,
  created_at as installed_at
FROM horizontal.tenants;
```

### Задача 2.3: Создать tenant_config записи

```sql
-- Конфигурация по умолчанию для каждого tenant'а
INSERT INTO vertical_repair.tenant_config (tenant_id, enabled)
SELECT id, true
FROM horizontal.tenants;
```

### Задача 2.4: Настроить Dual Write в коде

**n8n workflows:**

```javascript
// Пример: создание нового appeal
// БЫЛО:
await db.query(`
  INSERT INTO public.appeals (tenant_id, client_id, status)
  VALUES ($1, $2, $3)
  RETURNING *
`, [tenantId, clientId, status]);

// СТАЛО (dual write):
const appeal = await db.query(`
  INSERT INTO public.appeals (tenant_id, client_id, status)
  VALUES ($1, $2, $3)
  RETURNING *
`, [tenantId, clientId, status]);

// Одновременно пишем в новую схему
await db.query(`
  INSERT INTO horizontal.appeals (id, tenant_id, lead_id, status, vertical_id)
  VALUES ($1, $2, $3, $4, $5)
`, [appeal.id, tenantId, clientId, status, 'repair']);
```

### Задача 2.5: Создать triggers для синхронизации

**Альтернатива dual write в коде — DB triggers:**

```sql
-- Trigger: при INSERT в public.appeals → INSERT в horizontal.appeals
CREATE OR REPLACE FUNCTION sync_appeals_to_horizontal()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO horizontal.appeals (
    id, tenant_id, lead_id, status, source, vertical_id, vertical_status, created_at
  ) VALUES (
    NEW.id, NEW.tenant_id, NEW.client_id, NEW.status, NEW.source,
    'repair', 'active', NEW.created_at
  )
  ON CONFLICT (id) DO UPDATE SET
    status = EXCLUDED.status,
    updated_at = NOW();

  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_sync_appeals
AFTER INSERT OR UPDATE ON public.appeals
FOR EACH ROW EXECUTE FUNCTION sync_appeals_to_horizontal();

-- Аналогично для других таблиц
```

### Задача 2.6: Верификация данных

```sql
-- Проверка что данные совпадают
SELECT
  'tenants' as table_name,
  (SELECT COUNT(*) FROM public.tenants) as old_count,
  (SELECT COUNT(*) FROM horizontal.tenants) as new_count,
  (SELECT COUNT(*) FROM public.tenants) = (SELECT COUNT(*) FROM horizontal.tenants) as match;

SELECT
  'appeals' as table_name,
  (SELECT COUNT(*) FROM public.appeals) as old_count,
  (SELECT COUNT(*) FROM horizontal.appeals) as new_count,
  (SELECT COUNT(*) FROM public.appeals) = (SELECT COUNT(*) FROM horizontal.appeals) as match;

-- ... для всех таблиц
```

### Deliverables Phase 2:

- ✅ Все данные мигрированы в новые schemas
- ✅ Dual write настроен (код или triggers)
- ✅ Данные синхронизированы
- ✅ Верификация пройдена (100% match)

---

## Phase 3: API split

### Срок: 10-14 дней

### Задача 3.1: Создать Internal Horizontal API

**Новые endpoints (только для вертикалей):**

```javascript
// n8n workflow: Internal Horizontal API

// GET /internal/horizontal/leads
// Требует: X-Vertical-Secret header
router.get('/internal/horizontal/leads', async (req, res) => {
  // Проверка что запрос от вертикали
  const verticalSecret = req.headers['x-vertical-secret'];
  if (!validateVerticalSecret(verticalSecret)) {
    return res.status(403).json({error: 'Forbidden'});
  }

  const { tenant_id } = req.query;

  // Запрос к БД
  const leads = await db.query(`
    SELECT * FROM horizontal.leads
    WHERE tenant_id = $1
  `, [tenant_id]);

  res.json(leads);
});

// GET /internal/horizontal/appeals
router.get('/internal/horizontal/appeals', async (req, res) => {
  const verticalSecret = req.headers['x-vertical-secret'];
  if (!validateVerticalSecret(verticalSecret)) {
    return res.status(403).json({error: 'Forbidden'});
  }

  const { tenant_id, vertical_id } = req.query;

  const appeals = await db.query(`
    SELECT * FROM horizontal.appeals
    WHERE tenant_id = $1 AND vertical_id = $2
  `, [tenant_id, vertical_id]);

  res.json(appeals);
});

// POST /internal/horizontal/messages
router.post('/internal/horizontal/messages', async (req, res) => {
  const verticalSecret = req.headers['x-vertical-secret'];
  if (!validateVerticalSecret(verticalSecret)) {
    return res.status(403).json({error: 'Forbidden'});
  }

  const { tenant_id, lead_id, text, channel } = req.body;

  const message = await db.query(`
    INSERT INTO horizontal.messages (tenant_id, lead_id, text, channel)
    VALUES ($1, $2, $3, $4)
    RETURNING *
  `, [tenant_id, lead_id, text, channel]);

  res.json(message);
});
```

### Задача 3.2: Создать Vertical Repair API

**Новые endpoints (для tenant'ов):**

```javascript
// n8n workflow: Vertical Repair API

// GET /api/vertical/repair/leads
router.get('/api/vertical/repair/leads', async (req, res) => {
  const { tenant_id, auth_token } = req.headers;

  // 1. Проверка прав tenant'а
  const hasAccess = await checkTenantAccess(tenant_id, 'repair');
  if (!hasAccess) {
    return res.status(403).json({error: 'Vertical not enabled'});
  }

  // 2. Проверка тарифа (rate limits, features)
  await checkRateLimits(tenant_id);

  // 3. Запрос к horizontal (через internal API)
  const leads = await internalAPI.get('/internal/horizontal/leads', {
    headers: {'X-Vertical-Secret': VERTICAL_REPAIR_SECRET},
    params: {tenant_id}
  });

  // 4. Обогащаем данными из vertical_repair (если нужно)
  // ...

  // 5. Возврат
  res.json(leads);
});

// GET /api/vertical/repair/appeals/:id/devices
router.get('/api/vertical/repair/appeals/:id/devices', async (req, res) => {
  const { tenant_id } = req.headers;
  const { id: appeal_id } = req.params;

  // Проверки
  await checkTenantAccess(tenant_id, 'repair');
  await checkRateLimits(tenant_id);

  // Запрос devices из vertical_repair
  const devices = await db.query(`
    SELECT d.*, b.name as brand_name, m.name as model_name
    FROM vertical_repair.appeal_devices d
    LEFT JOIN vertical_repair.brands b ON d.brand_id = b.id
    LEFT JOIN vertical_repair.models m ON d.model_id = m.id
    WHERE d.appeal_id = $1
  `, [appeal_id]);

  res.json(devices);
});

// PUT /api/vertical/repair/appeals/:id/devices
router.put('/api/vertical/repair/appeals/:id/devices', async (req, res) => {
  const { tenant_id } = req.headers;
  const { id: appeal_id } = req.params;
  const { devices } = req.body;

  // Проверки
  await checkTenantAccess(tenant_id, 'repair');
  await checkPlanFeature(tenant_id, 'multi_device');

  // Транзакция
  await db.transaction(async (trx) => {
    for (const device of devices) {
      if (device.id) {
        // Update existing
        await trx.query(`
          UPDATE vertical_repair.appeal_devices
          SET brand_id = $1, model_id = $2, updated_at = NOW()
          WHERE id = $3 AND appeal_id = $4
        `, [device.brand_id, device.model_id, device.id, appeal_id]);
      } else {
        // Insert new
        await trx.query(`
          INSERT INTO vertical_repair.appeal_devices
          (appeal_id, brand_id, model_id, tenant_id)
          VALUES ($1, $2, $3, $4)
        `, [appeal_id, device.brand_id, device.model_id, tenant_id]);
      }
    }
  });

  res.json({success: true});
});
```

### Задача 3.3: Backward Compatibility Layer

**Старые endpoints продолжают работать:**

```javascript
// GET /api/operator/appeals (старый endpoint)
router.get('/api/operator/appeals', async (req, res) => {
  // Redirect to new API
  const tenant = await getTenant(req.headers.tenant_id);
  const primaryVertical = await getPrimaryVertical(tenant.id); // 'repair'

  // Call vertical API
  const result = await axios.get(
    `/api/vertical/${primaryVertical}/appeals`,
    {headers: req.headers}
  );

  res.json(result.data);
});

// Или через views:
CREATE VIEW public.clients AS
SELECT * FROM horizontal.leads;

CREATE VIEW public.appeals AS
SELECT
  id, tenant_id,
  lead_id as client_id, -- обратный маппинг
  status, source, created_at
FROM horizontal.appeals;
```

### Задача 3.4: Переключить read на новые таблицы

**Постепенно переводим workflows на новые schemas:**

```javascript
// Этап 1: Читаем из новых, но fallback на старые
const appeals = await db.query(`
  SELECT * FROM horizontal.appeals WHERE tenant_id = $1
`, [tenantId]).catch(err => {
  // Fallback
  return db.query(`SELECT * FROM public.appeals WHERE tenant_id = $1`, [tenantId]);
});

// Этап 2: Только новые (после тестирования)
const appeals = await db.query(`
  SELECT * FROM horizontal.appeals WHERE tenant_id = $1
`, [tenantId]);
```

### Deliverables Phase 3:

- ✅ Internal Horizontal API работает
- ✅ Vertical Repair API работает
- ✅ Backward compatibility через views/redirects
- ✅ Все workflows переведены на новые endpoints
- ✅ Старые endpoints deprecated (но работают)

---

## Phase 4: Testing & Rollout

### Срок: 7-10 дней

### Задача 4.1: Unit тесты

```javascript
// Тесты для Internal Horizontal API
describe('Internal Horizontal API', () => {
  test('GET /internal/horizontal/leads requires secret', async () => {
    const res = await request(app)
      .get('/internal/horizontal/leads?tenant_id=t001');

    expect(res.status).toBe(403);
  });

  test('GET /internal/horizontal/leads returns data with valid secret', async () => {
    const res = await request(app)
      .get('/internal/horizontal/leads?tenant_id=t001')
      .set('X-Vertical-Secret', VALID_SECRET);

    expect(res.status).toBe(200);
    expect(res.body).toBeInstanceOf(Array);
  });
});

// Тесты для Vertical Repair API
describe('Vertical Repair API', () => {
  test('GET /api/vertical/repair/leads checks tenant access', async () => {
    // Tenant without repair vertical
    const res = await request(app)
      .get('/api/vertical/repair/leads')
      .set('tenant-id', 't999')
      .set('auth-token', 'valid_token');

    expect(res.status).toBe(403);
    expect(res.body.error).toContain('Vertical not enabled');
  });
});
```

### Задача 4.2: Integration тесты

```javascript
// E2E тест: создание заявки через vertical API
describe('E2E: Create Appeal via Vertical API', () => {
  test('Full flow', async () => {
    // 1. Create lead
    const lead = await request(app)
      .post('/api/vertical/repair/leads')
      .set('tenant-id', 't001')
      .send({name: 'Иван', phone: '+79001234567'});

    expect(lead.status).toBe(201);

    // 2. Create appeal
    const appeal = await request(app)
      .post('/api/vertical/repair/appeals')
      .set('tenant-id', 't001')
      .send({lead_id: lead.body.id, source: 'whatsapp'});

    expect(appeal.status).toBe(201);
    expect(appeal.body.vertical_id).toBe('repair');

    // 3. Add device
    const device = await request(app)
      .put(`/api/vertical/repair/appeals/${appeal.body.id}/devices`)
      .set('tenant-id', 't001')
      .send({devices: [{brand_id: 'b001', model_id: 'm001'}]});

    expect(device.status).toBe(200);

    // 4. Verify data in both schemas
    const horizontalData = await db.query(
      'SELECT * FROM horizontal.appeals WHERE id = $1',
      [appeal.body.id]
    );
    expect(horizontalData.rows.length).toBe(1);

    const verticalData = await db.query(
      'SELECT * FROM vertical_repair.appeal_devices WHERE appeal_id = $1',
      [appeal.body.id]
    );
    expect(verticalData.rows.length).toBe(1);
  });
});
```

### Задача 4.3: Load testing

```bash
# Используем k6 или artillery
k6 run --vus 100 --duration 30s load_test.js
```

```javascript
// load_test.js
import http from 'k6/http';
import { check } from 'k6';

export default function() {
  // Test vertical API under load
  const res = http.get('https://n8n.n8nsrv.ru/webhook/api/vertical/repair/appeals', {
    headers: {
      'tenant-id': 't001',
      'auth-token': 'test_token'
    }
  });

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response time < 500ms': (r) => r.timings.duration < 500,
  });
}
```

### Задача 4.4: Monitoring setup

```javascript
// Метрики для мониторинга
const metrics = {
  // API performance
  'api.vertical.repair.latency': histogram,
  'api.horizontal.internal.latency': histogram,

  // Dual write sync
  'dual_write.sync_lag': gauge,
  'dual_write.errors': counter,

  // Database
  'db.horizontal.queries_per_sec': counter,
  'db.vertical_repair.queries_per_sec': counter,

  // Migration
  'migration.rows_synced': counter,
  'migration.sync_errors': counter,
};

// Alerts
if (metrics['dual_write.sync_lag'] > 1000) {
  alert('Dual write sync lag > 1 second!');
}

if (metrics['migration.sync_errors'] > 10) {
  alert('Migration sync errors > 10!');
}
```

### Задача 4.5: Production Rollout

**Стратегия: Canary Deployment**

```
Week 1: 10% traffic → new API
  ├─ Monitor metrics
  ├─ No errors? → Week 2
  └─ Errors? → Rollback

Week 2: 50% traffic → new API
  ├─ Monitor metrics
  ├─ No errors? → Week 3
  └─ Errors? → Rollback

Week 3: 100% traffic → new API
  └─ Monitor for 1 week

Week 4: Deprecate old endpoints
```

### Задача 4.6: Отключить Dual Write

**После успешного rollout:**

```sql
-- Удалить triggers синхронизации
DROP TRIGGER IF EXISTS trigger_sync_appeals ON public.appeals;
DROP FUNCTION IF EXISTS sync_appeals_to_horizontal();

-- ... для всех таблиц
```

```javascript
// Удалить dual write код из workflows
// БЫЛО:
await db.query('INSERT INTO public.appeals ...');
await db.query('INSERT INTO horizontal.appeals ...');

// СТАЛО:
await db.query('INSERT INTO horizontal.appeals ...');
```

### Задача 4.7: Удалить старые таблицы

**Только после 2-4 недель успешной работы на новых schemas:**

```sql
-- Backup на всякий случай
CREATE SCHEMA archive;
CREATE TABLE archive.appeals AS SELECT * FROM public.appeals;
CREATE TABLE archive.clients AS SELECT * FROM public.clients;
-- ... все старые таблицы

-- Удалить старые таблицы
DROP TABLE IF EXISTS public.appeals CASCADE;
DROP TABLE IF EXISTS public.clients CASCADE;
DROP TABLE IF EXISTS public.brands CASCADE;
-- ... все 65 таблиц

-- Удалить views (если создавали)
DROP VIEW IF EXISTS public.clients;
DROP VIEW IF EXISTS public.appeals;
```

### Deliverables Phase 4:

- ✅ Все тесты проходят (unit, integration, load)
- ✅ Production rollout завершен (100% traffic)
- ✅ Dual write отключен
- ✅ Старые таблицы удалены (после backup)
- ✅ Monitoring работает
- ✅ Документация обновлена

---

## Rollback план

### Если что-то пошло не так на любом этапе:

#### Rollback Phase 1 (schemas созданы):

```sql
-- Просто удалить новые schemas
DROP SCHEMA IF EXISTS horizontal CASCADE;
DROP SCHEMA IF EXISTS vertical_repair CASCADE;

-- Восстановить из backup если нужно
pg_restore -h ... -d batterycrm backup_before_migration.dump
```

**Impact:** Нет (схемы не используются)

---

#### Rollback Phase 2 (данные мигрированы, dual write):

```sql
-- Удалить triggers
DROP TRIGGER IF EXISTS trigger_sync_appeals ON public.appeals;
-- ... все triggers

-- Очистить новые schemas (но не удалять)
TRUNCATE TABLE horizontal.appeals CASCADE;
TRUNCATE TABLE horizontal.leads CASCADE;
-- ... все таблицы

-- Данные остаются в public.*
```

**Impact:** Minimal (dual write может быть отключен, данные в public.*)

---

#### Rollback Phase 3 (API переключен):

```javascript
// Переключить флаг в конфиге
const USE_NEW_API = false; // было true

// Или через environment variable
process.env.USE_NEW_SCHEMAS = 'false';

// В коде:
if (process.env.USE_NEW_SCHEMAS === 'true') {
  return db.query('SELECT * FROM horizontal.appeals ...');
} else {
  return db.query('SELECT * FROM public.appeals ...');
}
```

**Impact:** Medium (может потребоваться restart workflows)

---

#### Rollback Phase 4 (production rollout):

**Если в процессе canary:**

```nginx
# В Nginx или load balancer
location /api/vertical/ {
  # Rollback to old endpoints
  proxy_pass http://old-api-server;
}
```

**Если уже 100% traffic:**

```bash
# Восстановить старые таблицы из archive
CREATE TABLE public.appeals AS SELECT * FROM archive.appeals;
CREATE TABLE public.clients AS SELECT * FROM archive.clients;
# ... все таблицы

# Переключить код обратно (USE_NEW_API = false)
# Restart workflows
```

**Impact:** High (требует downtime 5-15 минут)

---

## Риски и митигация

### Риск 1: Потеря данных при миграции

**Вероятность:** Low
**Impact:** Critical

**Митигация:**
- ✅ Full backup перед началом
- ✅ Dual write (данные дублируются)
- ✅ Верификация на каждом этапе
- ✅ Архивные таблицы перед удалением

---

### Риск 2: Performance деградация

**Вероятность:** Medium
**Impact:** High

**Причины:**
- Joins между schemas медленнее
- Dual write = 2x запросов

**Митигация:**
- ✅ Load testing перед production
- ✅ Индексы на всех FK
- ✅ Мониторинг query performance
- ✅ Rollback готов

---

### Риск 3: Bugs в новом API

**Вероятность:** Medium
**Impact:** Medium

**Митигация:**
- ✅ Unit & integration тесты
- ✅ Canary deployment (10% → 50% → 100%)
- ✅ Monitoring & alerts
- ✅ Rollback готов

---

### Риск 4: Длительность миграции

**Вероятность:** High
**Impact:** Low

**Оценка:** 4-6 недель (может растянуться до 8-10)

**Митигация:**
- ✅ Phased approach (можно остановиться на любом этапе)
- ✅ Dual write минимизирует urgency
- ✅ Team bandwidth учтен

---

## Timeline

### Оптимистичный (5 недель):

```
Week 1: Phase 0 (анализ) + Phase 1 (schemas)
Week 2: Phase 2 (миграция данных)
Week 3-4: Phase 3 (API split)
Week 5: Phase 4 (testing & rollout 10%)
Week 6+: Phase 4 (rollout 50% → 100%)
```

### Реалистичный (8 недель):

```
Week 1: Phase 0
Week 2-3: Phase 1
Week 4-5: Phase 2
Week 6-7: Phase 3
Week 8-10: Phase 4
```

### Пессимистичный (12 недель):

```
Week 1-2: Phase 0 + Phase 1
Week 3-5: Phase 2 (проблемы с dual write)
Week 6-9: Phase 3 (bugs, rework)
Week 10-12: Phase 4 (rollout медленный)
```

---

## Критерии успеха

### Phase 0:
- ✅ Все таблицы классифицированы
- ✅ Backup создан
- ✅ План утвержден

### Phase 1:
- ✅ Schemas созданы
- ✅ Все таблицы созданы с правильной структурой
- ✅ FK работают

### Phase 2:
- ✅ 100% данных мигрировано
- ✅ Dual write работает без ошибок
- ✅ Верификация пройдена

### Phase 3:
- ✅ Internal API работает
- ✅ Vertical API работает
- ✅ Backward compatibility работает
- ✅ Все workflows переведены

### Phase 4:
- ✅ Тесты проходят
- ✅ Production rollout 100%
- ✅ Старые таблицы удалены
- ✅ Zero customer impact

---

## Заключение

Этот план миграции обеспечивает:
- **Zero downtime** — система работает во время миграции
- **Safety** — rollback возможен на любом этапе
- **Flexibility** — можно остановиться/продолжить
- **Validation** — тестирование на каждом этапе

**Следующие шаги:**
1. Утвердить план с командой
2. Создать backup текущей БД
3. Начать Phase 0 (анализ и подготовка)

---

**Версия:** 1.0
**Дата:** 24 ноября 2025
**Статус:** Ready for execution
**Ответственный:** Development Team
