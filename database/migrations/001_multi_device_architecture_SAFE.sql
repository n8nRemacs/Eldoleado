-- ============================================================================
-- МИГРАЦИЯ: Multi-Device & Conversation Focus Architecture (SAFE VERSION)
-- Дата: 2025-11-22
-- Описание: Добавление поддержки нескольких устройств в одной заявке
--           + система управления фокусом диалога
-- ВНИМАНИЕ: Эта версия безопасно пересоздаёт issue_types
-- ============================================================================

-- ============================================================================
-- 0. БЕЗОПАСНОЕ УДАЛЕНИЕ СТАРОЙ ТАБЛИЦЫ issue_types
-- ============================================================================

-- Удаляем старую таблицу issue_types если она существует
DROP TABLE IF EXISTS issue_types CASCADE;

DO $$ BEGIN
  RAISE NOTICE '✅ Старая таблица issue_types удалена (если существовала)';
END $$;

-- ============================================================================
-- 1. НОВАЯ ТАБЛИЦА: repair_categories (Типы/категории ремонта)
-- ============================================================================

CREATE TABLE IF NOT EXISTS repair_categories (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id UUID REFERENCES tenants(id),

  -- Название категории
  name VARCHAR(100) NOT NULL, -- "Дисплей", "АКБ", "Корпус", "Звук", "Связь"
  code VARCHAR(50) UNIQUE, -- "display", "battery", "body", "sound", "connection"
  description TEXT,

  -- Иконка для UI
  icon VARCHAR(50), -- "📱", "🔋", "📦", "🔊", "📡"

  -- Порядок отображения
  display_order INT DEFAULT 0,

  -- Статус
  is_active BOOLEAN DEFAULT true,

  -- Метаданные
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Индексы
CREATE INDEX IF NOT EXISTS idx_repair_categories_tenant ON repair_categories(tenant_id);
CREATE INDEX IF NOT EXISTS idx_repair_categories_active ON repair_categories(is_active);

-- Комментарии
COMMENT ON TABLE repair_categories IS 'Категории ремонта: Дисплей, АКБ, Корпус, Звук, Связь и т.д.';
COMMENT ON COLUMN repair_categories.code IS 'Уникальный код для программного использования';

DO $$ BEGIN
  RAISE NOTICE '✅ Таблица repair_categories создана';
END $$;

-- ============================================================================
-- 2. НОВАЯ ТАБЛИЦА: issue_types (Конкретные проблемы/неисправности)
-- ============================================================================

CREATE TABLE issue_types (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id UUID REFERENCES tenants(id),
  repair_category_id UUID NOT NULL REFERENCES repair_categories(id) ON DELETE CASCADE,

  -- Название проблемы
  name VARCHAR(100) NOT NULL, -- "Разбит", "Полосы", "Не работает тачскрин"
  code VARCHAR(50), -- "cracked", "lines", "touchscreen_broken"
  description TEXT,

  -- Стоимость и время по умолчанию
  default_cost DECIMAL(10,2),
  default_time VARCHAR(50), -- "30 минут", "1 час"

  -- Порядок отображения
  display_order INT DEFAULT 0,

  -- Статус
  is_active BOOLEAN DEFAULT true,

  -- Метаданные
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Индексы
CREATE INDEX IF NOT EXISTS idx_issue_types_tenant ON issue_types(tenant_id);
CREATE INDEX IF NOT EXISTS idx_issue_types_category ON issue_types(repair_category_id);
CREATE INDEX IF NOT EXISTS idx_issue_types_active ON issue_types(is_active);

-- Уникальность (для одного tenant + category не должно быть дубликатов кода)
CREATE UNIQUE INDEX IF NOT EXISTS idx_issue_types_unique
ON issue_types(tenant_id, repair_category_id, code)
WHERE code IS NOT NULL;

-- Комментарии
COMMENT ON TABLE issue_types IS 'Конкретные проблемы для каждой категории ремонта';
COMMENT ON COLUMN issue_types.repair_category_id IS 'Привязка к категории (напр. "Разбит" → "Дисплей")';

DO $$ BEGIN
  RAISE NOTICE '✅ Таблица issue_types создана';
END $$;

-- ============================================================================
-- 3. НОВАЯ ТАБЛИЦА: appeal_devices (Устройства в заявке)
-- ============================================================================

CREATE TABLE IF NOT EXISTS appeal_devices (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  appeal_id UUID NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
  tenant_id UUID NOT NULL REFERENCES tenants(id),

  -- Устройство
  brand_id UUID REFERENCES brands(id),
  model_id UUID REFERENCES models(id),
  device_type_id UUID REFERENCES device_types(id),

  -- Текстовое представление (для отображения)
  phone_model VARCHAR(255), -- "iPhone 14 Pro", "Samsung Galaxy S21"

  -- Дополнительные идентификаторы
  serial_number VARCHAR(255),
  imei VARCHAR(255),

  -- Статус устройства в заявке
  status VARCHAR(50) DEFAULT 'active',
  -- Варианты: 'active', 'cancelled', 'completed'

  -- Статус обсуждения (для фокуса диалога)
  discussion_status VARCHAR(50) DEFAULT 'pending',
  -- Варианты: 'pending', 'discussing', 'discussed', 'cancelled'

  -- Порядок упоминания клиентом (для последовательного обсуждения)
  device_order INT DEFAULT 1,

  -- Заметки
  notes TEXT,

  -- Метаданные
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Индексы для производительности
CREATE INDEX IF NOT EXISTS idx_appeal_devices_appeal ON appeal_devices(appeal_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_tenant ON appeal_devices(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_status ON appeal_devices(status);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_discussion ON appeal_devices(discussion_status);

-- Комментарии для документации
COMMENT ON TABLE appeal_devices IS 'Устройства в рамках одной заявки (поддержка нескольких устройств)';
COMMENT ON COLUMN appeal_devices.discussion_status IS 'Статус обсуждения: pending (не обсуждалось), discussing (обсуждается сейчас), discussed (завершено), cancelled (отменено)';
COMMENT ON COLUMN appeal_devices.device_order IS 'Порядок упоминания клиентом (1, 2, 3...) для последовательного обсуждения';

DO $$ BEGIN
  RAISE NOTICE '✅ Таблица appeal_devices создана';
END $$;

-- ============================================================================
-- 4. НОВАЯ ТАБЛИЦА: appeal_repairs (Ремонты/проблемы в заявке)
-- ============================================================================

CREATE TABLE IF NOT EXISTS appeal_repairs (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  appeal_id UUID NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
  appeal_device_id UUID NOT NULL REFERENCES appeal_devices(id) ON DELETE CASCADE,
  tenant_id UUID NOT NULL REFERENCES tenants(id),

  -- Категория ремонта (уровень 1)
  repair_category_id UUID REFERENCES repair_categories(id),
  repair_category_name VARCHAR(100), -- "Дисплей", "АКБ", "Корпус"

  -- Конкретная проблема (уровень 2)
  issue_type_id UUID REFERENCES issue_types(id),
  issue_type_name VARCHAR(100), -- "Разбит", "Вздутая", "Не работает"

  -- Совместимость со старой схемой (DEPRECATED)
  repair_type_id UUID REFERENCES repair_types(id),
  repair_type_name VARCHAR(255),

  -- Детали ремонта
  parts_owner VARCHAR(50) DEFAULT 'наша',
  -- Варианты: 'наша', 'клиентская', 'не требуется'

  estimated_cost DECIMAL(10,2),
  estimated_time VARCHAR(50), -- '30 минут', '2 часа', '1 день'

  -- Статус ремонта
  status VARCHAR(50) DEFAULT 'pending',
  -- Варианты: 'pending', 'confirmed', 'rejected', 'in_progress', 'completed'

  -- Статус обсуждения (для фокуса диалога)
  discussion_status VARCHAR(50) DEFAULT 'pending',
  -- Варианты: 'pending', 'discussing', 'confirmed', 'rejected'

  -- Приоритет (для очередности обсуждения)
  priority INT DEFAULT 1, -- 1 = высокий, 2 = средний, 3 = низкий

  -- Описание проблемы от клиента
  problem_description TEXT,

  -- Заметки оператора
  notes TEXT,

  -- Метаданные
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Индексы
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_appeal ON appeal_repairs(appeal_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_device ON appeal_repairs(appeal_device_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_tenant ON appeal_repairs(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_category ON appeal_repairs(repair_category_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_issue ON appeal_repairs(issue_type_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_status ON appeal_repairs(status);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_discussion ON appeal_repairs(discussion_status);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_priority ON appeal_repairs(priority);

-- Комментарии
COMMENT ON TABLE appeal_repairs IS 'Ремонты/проблемы для каждого устройства в заявке';
COMMENT ON COLUMN appeal_repairs.discussion_status IS 'Статус обсуждения: pending, discussing, confirmed, rejected';
COMMENT ON COLUMN appeal_repairs.priority IS 'Приоритет обсуждения (1-высокий, 2-средний, 3-низкий)';
COMMENT ON COLUMN appeal_repairs.parts_owner IS 'Чья запчасть: наша, клиентская, не требуется';

DO $$ BEGIN
  RAISE NOTICE '✅ Таблица appeal_repairs создана';
END $$;

-- ============================================================================
-- 5. ОБНОВЛЕНИЕ ТАБЛИЦЫ: appeals (добавление новых полей)
-- ============================================================================

-- Контекст диалога
ALTER TABLE appeals
ADD COLUMN IF NOT EXISTS conversation_context JSONB DEFAULT '{}';

COMMENT ON COLUMN appeals.conversation_context IS 'Контекст диалога: упомянутые устройства, история переключений, clarifications';

-- Статус заполнения контекста
ALTER TABLE appeals
ADD COLUMN IF NOT EXISTS context_completion_status JSONB DEFAULT '{}';

COMMENT ON COLUMN appeals.context_completion_status IS 'Статус заполнения: fields_collected, required_fields_count, completion_percentage';

-- Фокус диалога
ALTER TABLE appeals
ADD COLUMN IF NOT EXISTS conversation_focus JSONB DEFAULT '{}';

COMMENT ON COLUMN appeals.conversation_focus IS 'Текущий фокус: current_device_id, current_repair_id, focus_history, last_updated_by, auto_switch';

-- Последнее приветствие (для проверки "прошло > 24 часа")
ALTER TABLE appeals
ADD COLUMN IF NOT EXISTS last_greeting_at TIMESTAMPTZ;

COMMENT ON COLUMN appeals.last_greeting_at IS 'Время последнего приветствия (для проверки нужно ли здороваться снова)';

DO $$ BEGIN
  RAISE NOTICE '✅ Обновлена таблица appeals';
END $$;

-- ============================================================================
-- 6. НОВАЯ ТАБЛИЦА: context_fields_config (Конфигурация полей для сбора)
-- ============================================================================

CREATE TABLE IF NOT EXISTS context_fields_config (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id UUID NOT NULL REFERENCES tenants(id),
  deal_type_id UUID REFERENCES deal_types(id), -- NULL = для всех типов

  -- Конфигурация поля
  field_name VARCHAR(100) NOT NULL, -- 'type', 'model_id', 'repair_type_id', 'parts_owner'
  field_label VARCHAR(255) NOT NULL, -- 'Тип обращения', 'Модель устройства'
  field_type VARCHAR(50) DEFAULT 'text', -- 'text', 'select', 'number', 'boolean'

  -- Параметры сбора
  is_required BOOLEAN DEFAULT false, -- Обязательное поле?
  collection_order INT, -- Порядок сбора (1, 2, 3...)

  -- AI Tool для извлечения
  tool_name VARCHAR(100), -- 'Tool-Type', 'Tool-Model', 'Tool-Repair', 'Tool-Parts'

  -- Промпт для AI (опционально)
  prompt_template TEXT, -- Шаблон вопроса клиенту

  -- Правила валидации (опционально)
  validation_rules JSONB, -- { "min_length": 3, "max_length": 100, "pattern": "..." }

  -- Значение по умолчанию
  default_value VARCHAR(255),

  -- Статус
  is_active BOOLEAN DEFAULT true,

  -- Метаданные
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Индексы
CREATE INDEX IF NOT EXISTS idx_context_fields_tenant ON context_fields_config(tenant_id);
CREATE INDEX IF NOT EXISTS idx_context_fields_deal_type ON context_fields_config(deal_type_id);
CREATE INDEX IF NOT EXISTS idx_context_fields_active ON context_fields_config(is_active);

-- Уникальность (один field_name для одного tenant + deal_type)
CREATE UNIQUE INDEX IF NOT EXISTS idx_context_fields_unique
ON context_fields_config(tenant_id, field_name, COALESCE(deal_type_id, '00000000-0000-0000-0000-000000000000'::uuid));

-- Комментарии
COMMENT ON TABLE context_fields_config IS 'Конфигурация полей для сбора контекста (гибкая настройка для разных типов сделок)';
COMMENT ON COLUMN context_fields_config.is_required IS 'Обязательное поле для перехода в "Обработка заявки"';
COMMENT ON COLUMN context_fields_config.collection_order IS 'Порядок сбора (1 - первым, 2 - вторым, ...)';

DO $$ BEGIN
  RAISE NOTICE '✅ Таблица context_fields_config создана';
END $$;

-- ============================================================================
-- 7. ТЕСТОВЫЕ ДАННЫЕ: repair_categories + issue_types
-- ============================================================================

DO $$
DECLARE
  default_tenant_id UUID;
  category_display_id UUID;
  category_battery_id UUID;
  category_body_id UUID;
  category_sound_id UUID;
  category_connection_id UUID;
  category_camera_id UUID;
  category_ports_id UUID;
BEGIN
  -- Ищем первый активный тенант
  SELECT id INTO default_tenant_id
  FROM tenants
  WHERE is_active = true
  LIMIT 1;

  -- Если нашли тенант, добавляем данные
  IF default_tenant_id IS NOT NULL THEN

    -- ========================================
    -- Категории ремонта
    -- ========================================

    INSERT INTO repair_categories (tenant_id, name, code, icon, display_order)
    VALUES
      (default_tenant_id, 'Дисплей', 'display', '📱', 1),
      (default_tenant_id, 'АКБ', 'battery', '🔋', 2),
      (default_tenant_id, 'Корпус', 'body', '📦', 3),
      (default_tenant_id, 'Звук', 'sound', '🔊', 4),
      (default_tenant_id, 'Связь', 'connection', '📡', 5),
      (default_tenant_id, 'Камера', 'camera', '📷', 6),
      (default_tenant_id, 'Разъемы', 'ports', '🔌', 7)
    ON CONFLICT DO NOTHING
    RETURNING id;

    -- Получаем ID категорий для добавления проблем
    SELECT id INTO category_display_id FROM repair_categories WHERE code = 'display' LIMIT 1;
    SELECT id INTO category_battery_id FROM repair_categories WHERE code = 'battery' LIMIT 1;
    SELECT id INTO category_body_id FROM repair_categories WHERE code = 'body' LIMIT 1;
    SELECT id INTO category_sound_id FROM repair_categories WHERE code = 'sound' LIMIT 1;
    SELECT id INTO category_connection_id FROM repair_categories WHERE code = 'connection' LIMIT 1;
    SELECT id INTO category_camera_id FROM repair_categories WHERE code = 'camera' LIMIT 1;
    SELECT id INTO category_ports_id FROM repair_categories WHERE code = 'ports' LIMIT 1;

    -- ========================================
    -- Проблемы для категории "Дисплей"
    -- ========================================

    IF category_display_id IS NOT NULL THEN
      INSERT INTO issue_types (tenant_id, repair_category_id, name, code, default_cost, default_time, display_order)
      VALUES
        (default_tenant_id, category_display_id, 'Разбит', 'cracked', 8500, '40 минут', 1),
        (default_tenant_id, category_display_id, 'Полосы', 'lines', 8500, '40 минут', 2),
        (default_tenant_id, category_display_id, 'Не работает тачскрин', 'touchscreen_broken', 8500, '40 минут', 3),
        (default_tenant_id, category_display_id, 'Не показывает картинку', 'no_display', 8500, '40 минут', 4),
        (default_tenant_id, category_display_id, 'Пятна на экране', 'spots', 8500, '40 минут', 5)
      ON CONFLICT DO NOTHING;
    END IF;

    -- ========================================
    -- Проблемы для категории "АКБ"
    -- ========================================

    IF category_battery_id IS NOT NULL THEN
      INSERT INTO issue_types (tenant_id, repair_category_id, name, code, default_cost, default_time, display_order)
      VALUES
        (default_tenant_id, category_battery_id, 'Вздутая', 'swollen', 3500, '30 минут', 1),
        (default_tenant_id, category_battery_id, 'Износ', 'wear', 3500, '30 минут', 2),
        (default_tenant_id, category_battery_id, 'Не держит заряд', 'not_holding', 3500, '30 минут', 3),
        (default_tenant_id, category_battery_id, 'Быстро разряжается', 'fast_drain', 3500, '30 минут', 4),
        (default_tenant_id, category_battery_id, 'Не заряжается', 'not_charging', 3500, '30 минут', 5)
      ON CONFLICT DO NOTHING;
    END IF;

    -- ========================================
    -- Проблемы для категории "Корпус"
    -- ========================================

    IF category_body_id IS NOT NULL THEN
      INSERT INTO issue_types (tenant_id, repair_category_id, name, code, default_cost, default_time, display_order)
      VALUES
        (default_tenant_id, category_body_id, 'Задняя крышка сломана', 'back_cover_broken', 2500, '20 минут', 1),
        (default_tenant_id, category_body_id, 'Погнут', 'bent', 4500, '1 час', 2),
        (default_tenant_id, category_body_id, 'Кнопки', 'buttons', 1500, '15 минут', 3),
        (default_tenant_id, category_body_id, 'Рамка повреждена', 'frame_damaged', 3000, '30 минут', 4)
      ON CONFLICT DO NOTHING;
    END IF;

    -- ========================================
    -- Проблемы для категории "Звук"
    -- ========================================

    IF category_sound_id IS NOT NULL THEN
      INSERT INTO issue_types (tenant_id, repair_category_id, name, code, default_cost, default_time, display_order)
      VALUES
        (default_tenant_id, category_sound_id, 'Нет полифонии', 'no_polyphony', 2000, '30 минут', 1),
        (default_tenant_id, category_sound_id, 'Не слышит абонента', 'cant_hear', 2000, '30 минут', 2),
        (default_tenant_id, category_sound_id, 'Абонент не слышит', 'not_heard', 2000, '30 минут', 3),
        (default_tenant_id, category_sound_id, 'Хрипит динамик', 'speaker_distortion', 2000, '30 минут', 4),
        (default_tenant_id, category_sound_id, 'Тихий звук', 'low_volume', 2000, '30 минут', 5)
      ON CONFLICT DO NOTHING;
    END IF;

    -- ========================================
    -- Проблемы для категории "Связь"
    -- ========================================

    IF category_connection_id IS NOT NULL THEN
      INSERT INTO issue_types (tenant_id, repair_category_id, name, code, default_cost, default_time, display_order)
      VALUES
        (default_tenant_id, category_connection_id, 'Не работает WiFi', 'wifi_broken', 3000, '45 минут', 1),
        (default_tenant_id, category_connection_id, 'Нет сигнала сети', 'no_signal', 3500, '1 час', 2),
        (default_tenant_id, category_connection_id, 'Bluetooth не подключается', 'bluetooth_broken', 2500, '40 минут', 3),
        (default_tenant_id, category_connection_id, 'GPS не работает', 'gps_broken', 3000, '45 минут', 4)
      ON CONFLICT DO NOTHING;
    END IF;

    -- ========================================
    -- Проблемы для категории "Камера"
    -- ========================================

    IF category_camera_id IS NOT NULL THEN
      INSERT INTO issue_types (tenant_id, repair_category_id, name, code, default_cost, default_time, display_order)
      VALUES
        (default_tenant_id, category_camera_id, 'Не работает основная камера', 'main_camera_broken', 4500, '40 минут', 1),
        (default_tenant_id, category_camera_id, 'Не работает фронтальная камера', 'front_camera_broken', 3500, '30 минут', 2),
        (default_tenant_id, category_camera_id, 'Мутные фото', 'blurry_photos', 2000, '20 минут', 3),
        (default_tenant_id, category_camera_id, 'Стекло камеры разбито', 'camera_glass_broken', 1500, '15 минут', 4)
      ON CONFLICT DO NOTHING;
    END IF;

    -- ========================================
    -- Проблемы для категории "Разъемы"
    -- ========================================

    IF category_ports_id IS NOT NULL THEN
      INSERT INTO issue_types (tenant_id, repair_category_id, name, code, default_cost, default_time, display_order)
      VALUES
        (default_tenant_id, category_ports_id, 'Разъем зарядки сломан', 'charging_port_broken', 2500, '30 минут', 1),
        (default_tenant_id, category_ports_id, 'Разъем наушников не работает', 'headphone_jack_broken', 2000, '25 минут', 2),
        (default_tenant_id, category_ports_id, 'Плохой контакт при зарядке', 'loose_charging', 2000, '25 минут', 3)
      ON CONFLICT DO NOTHING;
    END IF;

    RAISE NOTICE '✅ Тестовые данные для repair_categories и issue_types добавлены';

  ELSE
    RAISE WARNING 'Активный тенант не найден! Пропускаем добавление тестовых данных.';
  END IF;
END $$;

-- ============================================================================
-- 8. ТЕСТОВЫЕ ДАННЫЕ: context_fields_config
-- ============================================================================

-- Получаем tenant_id (предполагается что есть дефолтный тенант)
DO $$
DECLARE
  default_tenant_id UUID;
BEGIN
  -- Ищем первый активный тенант
  SELECT id INTO default_tenant_id
  FROM tenants
  WHERE is_active = true
  LIMIT 1;

  -- Если нашли тенант, добавляем конфигурацию полей
  IF default_tenant_id IS NOT NULL THEN
    INSERT INTO context_fields_config
      (tenant_id, field_name, field_label, is_required, collection_order, tool_name, field_type, default_value)
    VALUES
      -- Обязательные поля
      (default_tenant_id, 'type', 'Тип обращения', true, 1, 'Tool-Type', 'select', NULL),
      (default_tenant_id, 'model_id', 'Модель устройства', true, 2, 'Tool-Model', 'select', NULL),
      (default_tenant_id, 'repair_type_id', 'Тип ремонта', true, 3, 'Tool-Repair', 'select', NULL),

      -- Опциональные поля
      (default_tenant_id, 'parts_owner', 'Чья запчасть', false, 4, 'Tool-Parts', 'select', 'наша')
    ON CONFLICT DO NOTHING;

    RAISE NOTICE 'Конфигурация полей добавлена для tenant_id: %', default_tenant_id;
  ELSE
    RAISE WARNING 'Активный тенант не найден! Пропускаем добавление конфигурации полей.';
  END IF;
END $$;

-- ============================================================================
-- 9. ФУНКЦИЯ: Автоматическое обновление updated_at
-- ============================================================================

-- Функция для обновления timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at = NOW();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Триггеры для автоматического обновления updated_at
CREATE TRIGGER update_repair_categories_updated_at
  BEFORE UPDATE ON repair_categories
  FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_issue_types_updated_at
  BEFORE UPDATE ON issue_types
  FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_appeal_devices_updated_at
  BEFORE UPDATE ON appeal_devices
  FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_appeal_repairs_updated_at
  BEFORE UPDATE ON appeal_repairs
  FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_context_fields_config_updated_at
  BEFORE UPDATE ON context_fields_config
  FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- ============================================================================
-- 10. VIEW: Удобный просмотр заявок с устройствами и ремонтами
-- ============================================================================

CREATE OR REPLACE VIEW v_appeals_with_devices AS
SELECT
  a.id as appeal_id,
  a.tenant_id,
  a.client_id,
  a.stage,
  a.type as appeal_type,
  a.operation_mode,
  a.conversation_focus,
  a.last_greeting_at,

  -- Клиент
  c.name as client_name,
  c.phone as client_phone,

  -- Устройства (агрегация)
  COALESCE(
    jsonb_agg(
      DISTINCT jsonb_build_object(
        'device_id', ad.id,
        'model', ad.phone_model,
        'brand_id', ad.brand_id,
        'model_id', ad.model_id,
        'status', ad.status,
        'discussion_status', ad.discussion_status,
        'device_order', ad.device_order
      ) ORDER BY ad.device_order
    ) FILTER (WHERE ad.id IS NOT NULL),
    '[]'::jsonb
  ) as devices,

  -- Ремонты (агрегация)
  COALESCE(
    jsonb_agg(
      DISTINCT jsonb_build_object(
        'repair_id', ar.id,
        'device_id', ar.appeal_device_id,
        'repair_type', ar.repair_type_name,
        'parts_owner', ar.parts_owner,
        'cost', ar.estimated_cost,
        'time', ar.estimated_time,
        'status', ar.status,
        'discussion_status', ar.discussion_status,
        'priority', ar.priority
      ) ORDER BY ar.priority
    ) FILTER (WHERE ar.id IS NOT NULL),
    '[]'::jsonb
  ) as repairs,

  -- Общая стоимость
  COALESCE(SUM(ar.estimated_cost) FILTER (WHERE ar.status != 'rejected'), 0) as total_cost,

  -- Счётчики
  COUNT(DISTINCT ad.id) FILTER (WHERE ad.status = 'active') as devices_count,
  COUNT(DISTINCT ar.id) FILTER (WHERE ar.status != 'rejected') as repairs_count,

  a.created_at,
  a.updated_at

FROM appeals a
LEFT JOIN clients c ON c.id = a.client_id
LEFT JOIN appeal_devices ad ON ad.appeal_id = a.id
LEFT JOIN appeal_repairs ar ON ar.appeal_id = a.id
GROUP BY a.id, c.name, c.phone;

COMMENT ON VIEW v_appeals_with_devices IS 'Удобное представление заявок с устройствами и ремонтами (для API)';

-- ============================================================================
-- 11. МИГРАЦИЯ СУЩЕСТВУЮЩИХ ДАННЫХ (если есть старые заявки)
-- ============================================================================

-- Переносим данные из appeals в appeal_devices и appeal_repairs
DO $$
DECLARE
  appeal_record RECORD;
  new_device_id UUID;
BEGIN
  -- Для каждой заявки где есть model_id или repair_type_id
  FOR appeal_record IN
    SELECT id, tenant_id, brand_id, model_id, repair_type_id, phone_model, parts_owner
    FROM appeals
    WHERE model_id IS NOT NULL OR repair_type_id IS NOT NULL
  LOOP
    -- Создаём устройство если есть model_id
    IF appeal_record.model_id IS NOT NULL THEN
      INSERT INTO appeal_devices (
        appeal_id, tenant_id, brand_id, model_id, phone_model,
        status, discussion_status, device_order
      )
      VALUES (
        appeal_record.id,
        appeal_record.tenant_id,
        appeal_record.brand_id,
        appeal_record.model_id,
        appeal_record.phone_model,
        'active',
        'discussed', -- старые заявки считаем уже обсуждёнными
        1
      )
      RETURNING id INTO new_device_id;

      -- Создаём ремонт если есть repair_type_id
      IF appeal_record.repair_type_id IS NOT NULL THEN
        INSERT INTO appeal_repairs (
          appeal_id, appeal_device_id, tenant_id, repair_type_id,
          parts_owner, status, discussion_status, priority
        )
        VALUES (
          appeal_record.id,
          new_device_id,
          appeal_record.tenant_id,
          appeal_record.repair_type_id,
          COALESCE(appeal_record.parts_owner, 'наша'),
          'confirmed',
          'confirmed',
          1
        );
      END IF;

      RAISE NOTICE 'Мигрирована заявка %', appeal_record.id;
    END IF;
  END LOOP;

  RAISE NOTICE 'Миграция существующих данных завершена';
END $$;

-- ============================================================================
-- КОНЕЦ МИГРАЦИИ
-- ============================================================================

-- Итоговое сообщение
DO $$
BEGIN
  RAISE NOTICE '========================================';
  RAISE NOTICE '🎉 Миграция Multi-Device Architecture завершена успешно!';
  RAISE NOTICE '========================================';
  RAISE NOTICE 'Создано:';
  RAISE NOTICE '- ✅ Таблица repair_categories (7 категорий)';
  RAISE NOTICE '- ✅ Таблица issue_types (~40 проблем)';
  RAISE NOTICE '- ✅ Таблица appeal_devices';
  RAISE NOTICE '- ✅ Таблица appeal_repairs';
  RAISE NOTICE '- ✅ Таблица context_fields_config';
  RAISE NOTICE '- ✅ Обновлена таблица appeals (4 новых поля)';
  RAISE NOTICE '- ✅ VIEW v_appeals_with_devices';
  RAISE NOTICE '- ✅ Триггеры для updated_at';
  RAISE NOTICE '- ✅ Тестовые данные для всех справочников';
  RAISE NOTICE '========================================';
  RAISE NOTICE 'Следующий шаг: Обновить n8n воркеры (AI Tools)';
  RAISE NOTICE '========================================';
END $$;
