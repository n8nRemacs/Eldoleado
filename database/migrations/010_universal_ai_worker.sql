-- ============================================================================
-- Migration 010: Universal AI Worker System
-- Date: 2025-11-25
-- Description: Tables for universal AI extraction with Redis queue
-- ============================================================================

-- ============================================================================
-- 1. ai_entity_configs — Конфигурация сущностей для извлечения (по вертикали)
-- ============================================================================

CREATE TABLE IF NOT EXISTS ai_entity_configs (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    vertical_id varchar(50) NOT NULL DEFAULT 'repair',  -- 'repair', 'sales', etc.

    -- Идентификация сущности
    entity_type varchar(100) NOT NULL,      -- 'brand', 'model', 'repair_category', 'symptoms', 'device_count'
    entity_name varchar(255) NOT NULL,      -- Человеческое название: "Бренд устройства"
    entity_key varchar(100) NOT NULL,       -- Ключ для сохранения: 'brand', 'model', etc.

    -- Промпт (system + user template)
    system_prompt text,                     -- System prompt для AI
    user_prompt_template text NOT NULL,     -- User prompt с плейсхолдерами: {message}, {context}, {reference_data}

    -- JSON Schema ожидаемого ответа
    response_schema jsonb NOT NULL,         -- {"type": "object", "properties": {...}}
    response_example jsonb,                 -- Пример ответа для few-shot

    -- Справочник для маппинга (опционально)
    reference_table varchar(100),           -- 'brands', 'models', 'repair_categories'
    reference_query text,                   -- Custom SQL для загрузки справочника
    reference_key_field varchar(100) DEFAULT 'id',
    reference_display_field varchar(100) DEFAULT 'name',

    -- Зависимости от других сущностей
    depends_on varchar(100)[] DEFAULT '{}', -- ['brand'] для model

    -- Настройки
    priority int DEFAULT 0,                 -- Порядок выполнения (меньше = раньше)
    is_required boolean DEFAULT true,       -- Обязательно ли извлекать
    allow_multiple boolean DEFAULT false,   -- Может быть несколько значений
    is_active boolean DEFAULT true,

    -- AI настройки
    model_name varchar(100) DEFAULT 'gpt-4o-mini',
    temperature numeric(3,2) DEFAULT 0.3,
    max_tokens int DEFAULT 1024,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_vertical_entity UNIQUE (tenant_id, vertical_id, entity_type)
);

CREATE INDEX idx_ai_entity_configs_tenant ON ai_entity_configs(tenant_id);
CREATE INDEX idx_ai_entity_configs_vertical ON ai_entity_configs(tenant_id, vertical_id, is_active);
CREATE INDEX idx_ai_entity_configs_priority ON ai_entity_configs(tenant_id, vertical_id, priority);

COMMENT ON TABLE ai_entity_configs IS 'Конфигурация сущностей для AI извлечения по вертикалям';
COMMENT ON COLUMN ai_entity_configs.depends_on IS 'Массив entity_type от которых зависит (brand для model)';
COMMENT ON COLUMN ai_entity_configs.reference_query IS 'Custom SQL: SELECT id, name FROM brands WHERE tenant_id = $1';

-- ============================================================================
-- 2. ai_extraction_tasks — Очередь задач на извлечение (Redis-backed)
-- ============================================================================

CREATE TABLE IF NOT EXISTS ai_extraction_tasks (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,

    -- Конфигурация задачи
    entity_config_id uuid REFERENCES ai_entity_configs(id),
    entity_type varchar(100) NOT NULL,
    device_index int DEFAULT 0,             -- Для мульти-устройств (0, 1, 2...)

    -- Входные данные
    input_message text NOT NULL,            -- Текущее сообщение клиента
    input_context jsonb DEFAULT '{}',       -- Контекст: история, предыдущие извлечения
    input_reference_data jsonb,             -- Загруженный справочник

    -- Промпт (сгенерированный)
    generated_prompt text,

    -- Статус выполнения
    status varchar(50) DEFAULT 'pending',   -- pending, queued, processing, completed, failed, skipped

    -- Результат
    raw_response text,                      -- Сырой ответ от AI
    parsed_result jsonb,                    -- Распарсенный JSON
    mapped_result jsonb,                    -- После маппинга на ID из справочника
    confidence numeric(3,2),                -- Уверенность AI (0.0-1.0)

    -- Ошибки
    error_message text,
    error_code varchar(50),

    -- Retry логика
    attempts int DEFAULT 0,
    max_attempts int DEFAULT 3,
    next_retry_at timestamptz,

    -- Worker info
    worker_id varchar(100),                 -- ID воркера который взял задачу

    -- Timestamps
    created_at timestamptz DEFAULT now(),
    queued_at timestamptz,                  -- Когда добавлено в Redis
    started_at timestamptz,                 -- Когда воркер начал
    completed_at timestamptz,

    -- TTL
    expires_at timestamptz DEFAULT (now() + interval '24 hours')
);

-- Индексы для эффективного polling
CREATE INDEX idx_ai_tasks_pending ON ai_extraction_tasks(tenant_id, status, priority, created_at)
    WHERE status = 'pending';
CREATE INDEX idx_ai_tasks_queued ON ai_extraction_tasks(status, queued_at)
    WHERE status = 'queued';
CREATE INDEX idx_ai_tasks_appeal ON ai_extraction_tasks(appeal_id, device_index);
CREATE INDEX idx_ai_tasks_status ON ai_extraction_tasks(status, created_at);
CREATE INDEX idx_ai_tasks_retry ON ai_extraction_tasks(status, next_retry_at)
    WHERE status = 'failed' AND attempts < max_attempts;

-- Добавляем priority из config
ALTER TABLE ai_extraction_tasks ADD COLUMN IF NOT EXISTS priority int DEFAULT 0;

COMMENT ON TABLE ai_extraction_tasks IS 'Очередь задач AI извлечения с Redis интеграцией';

-- ============================================================================
-- 3. appeal_dialog_state — Состояние диалога по заявке
-- ============================================================================

CREATE TABLE IF NOT EXISTS appeal_dialog_state (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    appeal_id uuid NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,

    -- Текущий контекст диалога
    active_device_index int DEFAULT 0,      -- Какое устройство сейчас обсуждаем (0-based)
    total_devices int DEFAULT 1,            -- Сколько всего устройств в заявке

    current_topic varchar(50) DEFAULT 'device',  -- 'device', 'repair', 'price', 'delivery', 'general'
    last_intent varchar(100),               -- Последний определённый intent

    -- Флаги неопределённости
    needs_clarification boolean DEFAULT false,
    clarification_type varchar(50),         -- 'which_device', 'which_repair', 'confirm_device'
    clarification_options jsonb,            -- Варианты для уточнения

    -- Накопленный контекст для AI
    context_summary text,                   -- Краткое текстовое резюме диалога
    extracted_entities jsonb DEFAULT '{}',  -- {"devices": [{...}], "client_name": "...", ...}

    -- Метаданные
    messages_processed int DEFAULT 0,       -- Сколько сообщений обработано
    last_message_id uuid,                   -- ID последнего обработанного сообщения

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_appeal_dialog_state UNIQUE (appeal_id)
);

CREATE INDEX idx_dialog_state_appeal ON appeal_dialog_state(appeal_id);
CREATE INDEX idx_dialog_state_needs_clarification ON appeal_dialog_state(tenant_id, needs_clarification)
    WHERE needs_clarification = true;

COMMENT ON TABLE appeal_dialog_state IS 'Состояние диалога для отслеживания контекста мульти-устройств';
COMMENT ON COLUMN appeal_dialog_state.active_device_index IS '0-based индекс текущего обсуждаемого устройства';
COMMENT ON COLUMN appeal_dialog_state.extracted_entities IS 'Все извлечённые сущности в формате JSON';

-- ============================================================================
-- 4. ai_extraction_results — История результатов извлечения (для аналитики)
-- ============================================================================

CREATE TABLE IF NOT EXISTS ai_extraction_results (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    task_id uuid NOT NULL REFERENCES ai_extraction_tasks(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,

    entity_type varchar(100) NOT NULL,
    device_index int DEFAULT 0,

    -- Результат
    extracted_value text,                   -- Извлечённое значение (текст)
    mapped_id uuid,                         -- ID из справочника (если применимо)
    mapped_name varchar(255),               -- Название из справочника
    confidence numeric(3,2),

    -- Для аналитики
    input_message_hash varchar(64),         -- SHA256 сообщения
    model_used varchar(100),
    tokens_used int,
    processing_time_ms int,

    created_at timestamptz DEFAULT now()
);

CREATE INDEX idx_ai_results_appeal ON ai_extraction_results(appeal_id);
CREATE INDEX idx_ai_results_entity ON ai_extraction_results(tenant_id, entity_type, created_at);

COMMENT ON TABLE ai_extraction_results IS 'История результатов извлечения для аналитики и улучшения промптов';

-- ============================================================================
-- 5. Вспомогательные функции
-- ============================================================================

-- Функция для создания задач извлечения по appeal
CREATE OR REPLACE FUNCTION fn_create_extraction_tasks(
    p_tenant_id uuid,
    p_appeal_id uuid,
    p_message text,
    p_device_index int DEFAULT 0,
    p_context jsonb DEFAULT '{}'
) RETURNS SETOF uuid AS $$
DECLARE
    v_config RECORD;
    v_task_id uuid;
BEGIN
    -- Получить активные конфиги для вертикали repair
    FOR v_config IN
        SELECT * FROM ai_entity_configs
        WHERE tenant_id = p_tenant_id
          AND vertical_id = 'repair'
          AND is_active = true
        ORDER BY priority ASC
    LOOP
        -- Создать задачу
        INSERT INTO ai_extraction_tasks (
            tenant_id, appeal_id, entity_config_id, entity_type,
            device_index, input_message, input_context, priority, status
        ) VALUES (
            p_tenant_id, p_appeal_id, v_config.id, v_config.entity_type,
            p_device_index, p_message, p_context, v_config.priority, 'pending'
        ) RETURNING id INTO v_task_id;

        RETURN NEXT v_task_id;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Функция для получения следующей задачи из очереди
CREATE OR REPLACE FUNCTION fn_claim_extraction_task(
    p_worker_id varchar(100)
) RETURNS TABLE (
    task_id uuid,
    tenant_id uuid,
    appeal_id uuid,
    entity_type varchar(100),
    input_message text,
    input_context jsonb,
    entity_config_id uuid
) AS $$
DECLARE
    v_task_id uuid;
BEGIN
    -- Атомарно забрать задачу
    UPDATE ai_extraction_tasks t
    SET status = 'processing',
        worker_id = p_worker_id,
        started_at = now(),
        attempts = attempts + 1
    WHERE t.id = (
        SELECT id FROM ai_extraction_tasks
        WHERE status = 'pending'
        ORDER BY priority ASC, created_at ASC
        LIMIT 1
        FOR UPDATE SKIP LOCKED
    )
    RETURNING t.id INTO v_task_id;

    IF v_task_id IS NULL THEN
        RETURN;
    END IF;

    RETURN QUERY
    SELECT t.id, t.tenant_id, t.appeal_id, t.entity_type,
           t.input_message, t.input_context, t.entity_config_id
    FROM ai_extraction_tasks t
    WHERE t.id = v_task_id;
END;
$$ LANGUAGE plpgsql;

-- Функция для завершения задачи
CREATE OR REPLACE FUNCTION fn_complete_extraction_task(
    p_task_id uuid,
    p_raw_response text,
    p_parsed_result jsonb,
    p_mapped_result jsonb,
    p_confidence numeric DEFAULT NULL
) RETURNS void AS $$
BEGIN
    UPDATE ai_extraction_tasks
    SET status = 'completed',
        raw_response = p_raw_response,
        parsed_result = p_parsed_result,
        mapped_result = p_mapped_result,
        confidence = p_confidence,
        completed_at = now()
    WHERE id = p_task_id;
END;
$$ LANGUAGE plpgsql;

-- Функция для отметки ошибки
CREATE OR REPLACE FUNCTION fn_fail_extraction_task(
    p_task_id uuid,
    p_error_message text,
    p_error_code varchar DEFAULT NULL
) RETURNS void AS $$
DECLARE
    v_attempts int;
    v_max_attempts int;
BEGIN
    SELECT attempts, max_attempts INTO v_attempts, v_max_attempts
    FROM ai_extraction_tasks WHERE id = p_task_id;

    IF v_attempts >= v_max_attempts THEN
        UPDATE ai_extraction_tasks
        SET status = 'failed',
            error_message = p_error_message,
            error_code = p_error_code,
            completed_at = now()
        WHERE id = p_task_id;
    ELSE
        -- Retry через экспоненциальную задержку
        UPDATE ai_extraction_tasks
        SET status = 'pending',
            error_message = p_error_message,
            error_code = p_error_code,
            next_retry_at = now() + (interval '1 minute' * power(2, v_attempts))
        WHERE id = p_task_id;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- ============================================================================
-- 6. Триггер для автообновления updated_at
-- ============================================================================

CREATE OR REPLACE FUNCTION fn_update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_ai_entity_configs_updated ON ai_entity_configs;
CREATE TRIGGER trg_ai_entity_configs_updated
    BEFORE UPDATE ON ai_entity_configs
    FOR EACH ROW EXECUTE FUNCTION fn_update_timestamp();

DROP TRIGGER IF EXISTS trg_dialog_state_updated ON appeal_dialog_state;
CREATE TRIGGER trg_dialog_state_updated
    BEFORE UPDATE ON appeal_dialog_state
    FOR EACH ROW EXECUTE FUNCTION fn_update_timestamp();

-- ============================================================================
-- Migration complete
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 010 completed: Universal AI Worker System';
    RAISE NOTICE 'Tables created: ai_entity_configs, ai_extraction_tasks, appeal_dialog_state, ai_extraction_results';
    RAISE NOTICE 'Functions created: fn_create_extraction_tasks, fn_claim_extraction_task, fn_complete_extraction_task, fn_fail_extraction_task';
END $$;
