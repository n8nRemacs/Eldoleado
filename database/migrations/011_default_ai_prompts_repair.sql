-- ============================================================================
-- Migration 011: Default AI Prompts for Repair Vertical
-- Date: 2025-11-25
-- Description: Insert default entity configs and prompts for phone repair vertical
-- ============================================================================

-- ============================================================================
-- 1. Context Analyzer — определяет intent и контекст (выполняется ПЕРВОЙ)
-- ============================================================================

INSERT INTO ai_entity_configs (
    tenant_id, vertical_id, entity_type, entity_name, entity_key,
    system_prompt, user_prompt_template, response_schema, response_example,
    priority, is_required, model_name, temperature
)
SELECT
    t.id,
    'repair',
    'context_analysis',
    'Анализ контекста диалога',
    'context',
    'Ты — эксперт по анализу диалогов в сервисном центре по ремонту электроники.
Твоя задача — определить намерение клиента и контекст текущего сообщения.

ВАЖНО:
- Анализируй ВСЮ историю диалога
- Определи, говорит ли клиент о новом устройстве или о существующем
- Определи, добавляет ли клиент новую поломку или уточняет существующую
- Если непонятно какое устройство имеется в виду — отметь needs_clarification',

    'История диалога:
{message_history}

Текущие устройства в заявке:
{current_devices}

Новое сообщение клиента:
{message}

Определи:
1. intent — намерение клиента
2. device_action — действие с устройством
3. target_device_index — индекс устройства (0, 1, 2...) или null если новое
4. needs_clarification — нужно ли уточнение
5. clarification_question — вопрос для уточнения (если нужно)

Верни ТОЛЬКО JSON.',

    '{
        "type": "object",
        "required": ["intent", "device_action", "target_device_index", "needs_clarification"],
        "properties": {
            "intent": {
                "type": "string",
                "enum": ["new_device", "add_repair", "price_question", "status_question", "general_question", "confirmation", "greeting", "thanks", "unclear"]
            },
            "device_action": {
                "type": "string",
                "enum": ["create_new", "update_existing", "none"]
            },
            "target_device_index": {
                "type": ["integer", "null"],
                "description": "0-based index of target device, null if new device"
            },
            "needs_clarification": {
                "type": "boolean"
            },
            "clarification_question": {
                "type": ["string", "null"]
            },
            "detected_entities": {
                "type": "object",
                "properties": {
                    "brand_mentioned": {"type": ["string", "null"]},
                    "model_mentioned": {"type": ["string", "null"]},
                    "repair_mentioned": {"type": ["string", "null"]}
                }
            }
        }
    }'::jsonb,

    '{
        "intent": "add_repair",
        "device_action": "update_existing",
        "target_device_index": 0,
        "needs_clarification": false,
        "clarification_question": null,
        "detected_entities": {
            "brand_mentioned": null,
            "model_mentioned": null,
            "repair_mentioned": "замена батареи"
        }
    }'::jsonb,

    -100,  -- Самый высокий приоритет (выполняется первым)
    true,
    'gpt-4o-mini',
    0.2
FROM tenants t
WHERE NOT EXISTS (
    SELECT 1 FROM ai_entity_configs c
    WHERE c.tenant_id = t.id AND c.entity_type = 'context_analysis'
);

-- ============================================================================
-- 2. Device Count — определяет количество устройств
-- ============================================================================

INSERT INTO ai_entity_configs (
    tenant_id, vertical_id, entity_type, entity_name, entity_key,
    system_prompt, user_prompt_template, response_schema, response_example,
    priority, is_required, allow_multiple, model_name, temperature
)
SELECT
    t.id,
    'repair',
    'device_count',
    'Количество устройств',
    'device_count',
    'Ты — эксперт по анализу сообщений клиентов сервисного центра.
Определи, сколько РАЗНЫХ устройств упоминает клиент.',

    'Сообщение клиента:
{message}

История диалога:
{message_history}

Определи сколько РАЗНЫХ устройств упоминается.
Примеры:
- "iPhone 14 разбит экран" → 1 устройство
- "У меня два телефона: iPhone и Samsung" → 2 устройства
- "Ещё есть планшет" → +1 к существующим

Верни ТОЛЬКО JSON.',

    '{
        "type": "object",
        "required": ["device_count", "devices_summary"],
        "properties": {
            "device_count": {"type": "integer", "minimum": 0, "maximum": 10},
            "devices_summary": {
                "type": "array",
                "items": {
                    "type": "object",
                    "properties": {
                        "index": {"type": "integer"},
                        "brief": {"type": "string"}
                    }
                }
            }
        }
    }'::jsonb,

    '{"device_count": 2, "devices_summary": [{"index": 0, "brief": "iPhone 14"}, {"index": 1, "brief": "Samsung"}]}'::jsonb,

    -50,  -- Высокий приоритет
    true,
    false,
    'gpt-4o-mini',
    0.1
FROM tenants t
WHERE NOT EXISTS (
    SELECT 1 FROM ai_entity_configs c
    WHERE c.tenant_id = t.id AND c.entity_type = 'device_count'
);

-- ============================================================================
-- 3. Brand — извлечение бренда устройства
-- ============================================================================

INSERT INTO ai_entity_configs (
    tenant_id, vertical_id, entity_type, entity_name, entity_key,
    system_prompt, user_prompt_template, response_schema, response_example,
    reference_table, reference_query, reference_key_field, reference_display_field,
    priority, is_required, model_name, temperature
)
SELECT
    t.id,
    'repair',
    'brand',
    'Бренд устройства',
    'brand',
    'Ты — эксперт по определению брендов электронных устройств.
Извлеки бренд устройства из сообщения клиента.

ВАЖНО:
- Нормализуй название (эппл → Apple, самсунг → Samsung)
- Если бренд не упомянут явно, попробуй определить по модели
- Если невозможно определить, верни null',

    'Доступные бренды в системе:
{reference_data}

Сообщение клиента:
{message}

Контекст (устройство #{device_index}):
{context}

Извлеки бренд устройства. Верни ТОЛЬКО JSON.',

    '{
        "type": "object",
        "required": ["brand"],
        "properties": {
            "brand": {"type": ["string", "null"]},
            "brand_id": {"type": ["string", "null"]},
            "confidence": {"type": "number", "minimum": 0, "maximum": 1},
            "source": {"type": "string", "enum": ["explicit", "inferred_from_model", "context", "unknown"]}
        }
    }'::jsonb,

    '{"brand": "Apple", "brand_id": null, "confidence": 0.95, "source": "explicit"}'::jsonb,

    'brands',
    'SELECT id, name, normalized_name, aliases FROM brands WHERE tenant_id = $1 AND is_active = true ORDER BY name',
    'id',
    'name',

    10,
    true,
    'gpt-4o-mini',
    0.2
FROM tenants t
WHERE NOT EXISTS (
    SELECT 1 FROM ai_entity_configs c
    WHERE c.tenant_id = t.id AND c.entity_type = 'brand'
);

-- ============================================================================
-- 4. Model — извлечение модели устройства
-- ============================================================================

INSERT INTO ai_entity_configs (
    tenant_id, vertical_id, entity_type, entity_name, entity_key,
    system_prompt, user_prompt_template, response_schema, response_example,
    reference_table, reference_query, reference_key_field, reference_display_field,
    depends_on, priority, is_required, model_name, temperature
)
SELECT
    t.id,
    'repair',
    'model',
    'Модель устройства',
    'model',
    'Ты — эксперт по моделям электронных устройств.
Извлеки модель устройства из сообщения клиента.

ВАЖНО:
- Нормализуй название (14 про макс → iPhone 14 Pro Max, s23 → Galaxy S23)
- Учитывай уже определённый бренд
- Если модель не указана, верни null',

    'Бренд устройства: {brand}

Доступные модели для этого бренда:
{reference_data}

Сообщение клиента:
{message}

Извлеки модель устройства. Верни ТОЛЬКО JSON.',

    '{
        "type": "object",
        "required": ["model"],
        "properties": {
            "model": {"type": ["string", "null"]},
            "model_id": {"type": ["string", "null"]},
            "model_normalized": {"type": ["string", "null"]},
            "confidence": {"type": "number", "minimum": 0, "maximum": 1}
        }
    }'::jsonb,

    '{"model": "iPhone 14 Pro Max", "model_id": null, "model_normalized": "iphone-14-pro-max", "confidence": 0.9}'::jsonb,

    'models',
    'SELECT id, name, brand_id FROM models WHERE tenant_id = $1 AND brand_id = $2 AND is_active = true ORDER BY name',
    'id',
    'name',

    ARRAY['brand'],
    20,
    false,
    'gpt-4o-mini',
    0.2
FROM tenants t
WHERE NOT EXISTS (
    SELECT 1 FROM ai_entity_configs c
    WHERE c.tenant_id = t.id AND c.entity_type = 'model'
);

-- ============================================================================
-- 5. Device Type — тип устройства
-- ============================================================================

INSERT INTO ai_entity_configs (
    tenant_id, vertical_id, entity_type, entity_name, entity_key,
    system_prompt, user_prompt_template, response_schema, response_example,
    reference_table, reference_query, reference_key_field, reference_display_field,
    priority, is_required, model_name, temperature
)
SELECT
    t.id,
    'repair',
    'device_type',
    'Тип устройства',
    'device_type',
    'Определи тип устройства из сообщения клиента.',

    'Доступные типы устройств:
{reference_data}

Сообщение: {message}
Бренд: {brand}
Модель: {model}

Определи тип устройства. Верни ТОЛЬКО JSON.',

    '{
        "type": "object",
        "required": ["device_type"],
        "properties": {
            "device_type": {"type": ["string", "null"]},
            "device_type_id": {"type": ["string", "null"]}
        }
    }'::jsonb,

    '{"device_type": "smartphone", "device_type_id": null}'::jsonb,

    'device_types',
    'SELECT id, name FROM device_types WHERE tenant_id = $1 AND is_active = true ORDER BY name',
    'id',
    'name',

    25,
    false,
    'gpt-4o-mini',
    0.1
FROM tenants t
WHERE NOT EXISTS (
    SELECT 1 FROM ai_entity_configs c
    WHERE c.tenant_id = t.id AND c.entity_type = 'device_type'
);

-- ============================================================================
-- 6. Repair Category — категория ремонта
-- ============================================================================

INSERT INTO ai_entity_configs (
    tenant_id, vertical_id, entity_type, entity_name, entity_key,
    system_prompt, user_prompt_template, response_schema, response_example,
    reference_table, reference_query, reference_key_field, reference_display_field,
    allow_multiple, priority, is_required, model_name, temperature
)
SELECT
    t.id,
    'repair',
    'repair_category',
    'Категория ремонта',
    'repair_category',
    'Ты — эксперт по классификации неисправностей электроники.
Определи категорию ремонта из описания проблемы клиента.

Примеры маппинга:
- "разбит экран", "не работает тачскрин" → Дисплей
- "не заряжается", "зарядка отходит" → Порты
- "быстро разряжается", "батарея вздулась" → Аккумулятор
- "не работает динамик", "не слышно собеседника" → Звук',

    'Доступные категории ремонта:
{reference_data}

Сообщение клиента:
{message}

Устройство: {brand} {model}

Определи категорию(и) ремонта. Может быть несколько! Верни ТОЛЬКО JSON.',

    '{
        "type": "object",
        "required": ["repairs"],
        "properties": {
            "repairs": {
                "type": "array",
                "items": {
                    "type": "object",
                    "required": ["category"],
                    "properties": {
                        "category": {"type": "string"},
                        "category_id": {"type": ["string", "null"]},
                        "symptoms": {"type": "string"},
                        "confidence": {"type": "number"}
                    }
                }
            }
        }
    }'::jsonb,

    '{"repairs": [{"category": "Дисплей", "category_id": null, "symptoms": "разбит экран", "confidence": 0.95}]}'::jsonb,

    'repair_categories',
    'SELECT id, name, description FROM repair_categories WHERE tenant_id = $1 AND is_active = true ORDER BY "order", name',
    'id',
    'name',

    true,  -- allow_multiple
    30,
    true,
    'gpt-4o-mini',
    0.3
FROM tenants t
WHERE NOT EXISTS (
    SELECT 1 FROM ai_entity_configs c
    WHERE c.tenant_id = t.id AND c.entity_type = 'repair_category'
);

-- ============================================================================
-- 7. Device Details — дополнительные детали устройства
-- ============================================================================

INSERT INTO ai_entity_configs (
    tenant_id, vertical_id, entity_type, entity_name, entity_key,
    system_prompt, user_prompt_template, response_schema, response_example,
    priority, is_required, model_name, temperature
)
SELECT
    t.id,
    'repair',
    'device_details',
    'Детали устройства',
    'device_details',
    'Извлеки дополнительные детали об устройстве из сообщения.',

    'Сообщение: {message}
Устройство: {brand} {model}

Извлеки (если указаны):
- Цвет
- Состояние (новое, б/у, разбитое)
- Серийный номер / IMEI
- Комплектация

Верни ТОЛЬКО JSON. Неуказанные поля = null.',

    '{
        "type": "object",
        "properties": {
            "color": {"type": ["string", "null"]},
            "condition": {"type": ["string", "null"], "enum": ["new", "used", "damaged", null]},
            "serial_number": {"type": ["string", "null"]},
            "imei": {"type": ["string", "null"]},
            "accessories": {"type": ["array", "null"], "items": {"type": "string"}}
        }
    }'::jsonb,

    '{"color": "черный", "condition": "damaged", "serial_number": null, "imei": null, "accessories": null}'::jsonb,

    40,
    false,
    'gpt-4o-mini',
    0.2
FROM tenants t
WHERE NOT EXISTS (
    SELECT 1 FROM ai_entity_configs c
    WHERE c.tenant_id = t.id AND c.entity_type = 'device_details'
);

-- ============================================================================
-- 8. Client Info — информация о клиенте
-- ============================================================================

INSERT INTO ai_entity_configs (
    tenant_id, vertical_id, entity_type, entity_name, entity_key,
    system_prompt, user_prompt_template, response_schema, response_example,
    priority, is_required, model_name, temperature
)
SELECT
    t.id,
    'repair',
    'client_info',
    'Информация о клиенте',
    'client_info',
    'Извлеки контактную информацию клиента из сообщения.',

    'Сообщение: {message}
История диалога: {message_history}

Извлеки (если указаны):
- Имя клиента
- Телефон
- Адрес / район

Верни ТОЛЬКО JSON. Неуказанные поля = null.',

    '{
        "type": "object",
        "properties": {
            "client_name": {"type": ["string", "null"]},
            "phone": {"type": ["string", "null"]},
            "address": {"type": ["string", "null"]},
            "preferred_contact_time": {"type": ["string", "null"]}
        }
    }'::jsonb,

    '{"client_name": "Иван", "phone": null, "address": "м. Тверская", "preferred_contact_time": null}'::jsonb,

    50,
    false,
    'gpt-4o-mini',
    0.2
FROM tenants t
WHERE NOT EXISTS (
    SELECT 1 FROM ai_entity_configs c
    WHERE c.tenant_id = t.id AND c.entity_type = 'client_info'
);

-- ============================================================================
-- Migration complete
-- ============================================================================

DO $$
DECLARE
    v_count int;
BEGIN
    SELECT COUNT(*) INTO v_count FROM ai_entity_configs;
    RAISE NOTICE 'Migration 011 completed: Default AI Prompts for Repair Vertical';
    RAISE NOTICE 'Total ai_entity_configs records: %', v_count;
END $$;
