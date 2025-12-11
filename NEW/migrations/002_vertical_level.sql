-- ============================================================================
-- ELO Migration 002: Vertical Level Tables (elo_v_)
-- Settings per vertical - has vertical_id, no tenant_id
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. elo_v_funnel_stages - Microfunnel stages per vertical
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_v_funnel_stages (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES elo_verticals(id),

    code VARCHAR(50) NOT NULL,             -- greeting, device, problem, price, appointment, etc.
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),

    -- Stage position
    sort_order INT NOT NULL DEFAULT 0,

    -- AI goal for this stage
    ai_goal TEXT,
    -- "Extract device brand and model from client message"
    -- "Clarify the problem symptoms"
    -- "Provide price estimate"

    -- Conditions
    entry_conditions JSONB DEFAULT '{}',
    -- entry_conditions.required_fields: ['device_brand']
    -- entry_conditions.from_stages: ['greeting']

    exit_conditions JSONB DEFAULT '{}',
    -- exit_conditions.required_fields: ['device_brand', 'device_model']
    -- exit_conditions.or_timeout_minutes: 60

    -- Stage behavior
    config JSONB DEFAULT '{}',
    -- config.auto_advance: true/false
    -- config.require_operator: true/false
    -- config.max_messages: 10

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(vertical_id, code)
);

COMMENT ON TABLE elo_v_funnel_stages IS 'Vertical level: Microfunnel stages for each vertical';

-- Seed funnel stages for phone_repair
INSERT INTO elo_v_funnel_stages (vertical_id, code, name, name_ru, sort_order, ai_goal) VALUES
    ((SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
     'greeting', 'Greeting', 'Приветствие', 1,
     'Greet the client, understand initial intent'),

    ((SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
     'device', 'Device Identification', 'Определение устройства', 2,
     'Extract device brand and model from conversation'),

    ((SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
     'problem', 'Problem Description', 'Описание проблемы', 3,
     'Understand what is wrong with the device, extract symptoms'),

    ((SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
     'price', 'Price Quote', 'Цена', 4,
     'Provide price estimate based on device and problem'),

    ((SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
     'appointment', 'Appointment', 'Запись', 5,
     'Schedule appointment or arrange device drop-off'),

    ((SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
     'confirmation', 'Confirmation', 'Подтверждение', 6,
     'Confirm all details with client'),

    ((SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
     'completed', 'Completed', 'Завершён', 99,
     'Dialog completed successfully')
ON CONFLICT (vertical_id, code) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 2. elo_v_prompts - AI prompts per vertical and stage
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_v_prompts (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES elo_verticals(id),
    funnel_stage_id INT REFERENCES elo_v_funnel_stages(id),

    -- Prompt type
    prompt_type VARCHAR(30) NOT NULL,      -- extraction, response, system
    name VARCHAR(100) NOT NULL,

    -- The prompt
    system_prompt TEXT,                    -- System message for AI
    user_prompt_template TEXT,             -- Template with {{variables}}

    -- Extraction schema (for extraction prompts)
    extraction_schema JSONB DEFAULT '{}',
    -- extraction_schema.device: {brand: string, model: string}
    -- extraction_schema.symptoms: [{code: string, text: string}]

    -- Metadata
    version INT DEFAULT 1,
    is_active BOOLEAN DEFAULT true,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(vertical_id, funnel_stage_id, prompt_type)
);

COMMENT ON TABLE elo_v_prompts IS 'Vertical level: AI prompts for extraction and response';

-- Seed extraction prompt for phone_repair
INSERT INTO elo_v_prompts (vertical_id, funnel_stage_id, prompt_type, name, system_prompt, extraction_schema) VALUES
    ((SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
     NULL,  -- global for vertical
     'extraction',
     'Phone Repair Extraction',
     'You are an assistant for a phone repair service.
Your task is to extract structured information from client messages.

Extract:
1. Device (brand, model, color)
2. Symptoms (what is wrong)
3. Intent (repair, price inquiry, question)

Respond ONLY in JSON format:
{
  "device": {"brand": "...", "model": "...", "color": null},
  "symptoms": [{"code": "...", "text": "..."}],
  "intent": {"type": "...", "text": "..."}
}

Symptom codes: screen_cracked, screen_not_working, battery_drain, battery_swollen,
charging_port_broken, camera_not_working, water_damage, software_crash, other

Intent types: repair_request, price_inquiry, consultation, appointment',
     '{
       "device": {"brand": "string", "model": "string", "color": "string"},
       "symptoms": [{"code": "string", "text": "string", "confidence": "number"}],
       "intent": {"type": "string", "text": "string"}
     }')
ON CONFLICT (vertical_id, funnel_stage_id, prompt_type) DO UPDATE SET
    system_prompt = EXCLUDED.system_prompt,
    extraction_schema = EXCLUDED.extraction_schema,
    updated_at = NOW();


-- ----------------------------------------------------------------------------
-- 3. elo_v_ai_settings - AI behavior settings per vertical
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_v_ai_settings (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES elo_verticals(id),
    funnel_stage_id INT REFERENCES elo_v_funnel_stages(id),

    name VARCHAR(100) NOT NULL,

    -- Stick-Carrot-Stick settings
    pre_rules JSONB DEFAULT '{}',
    -- pre_rules.must_extract: ['device_brand', 'device_model']
    -- pre_rules.forbidden_topics: ['politics', 'competitors']
    -- pre_rules.required_questions: ['what device?']

    freedom_level INT DEFAULT 50 CHECK (freedom_level >= 0 AND freedom_level <= 100),
    -- 0 = strict script
    -- 50 = balanced
    -- 100 = full freedom

    post_rules JSONB DEFAULT '{}',
    -- post_rules.max_length: 500
    -- post_rules.forbidden_words: ['expensive', 'long time']
    -- post_rules.must_include: ['happy to help']

    -- Allowed tools
    allowed_tools JSONB DEFAULT '[]',
    -- ['extract_device', 'extract_symptoms', 'get_price', 'send_response']

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(vertical_id, funnel_stage_id)
);

COMMENT ON TABLE elo_v_ai_settings IS 'Vertical level: AI behavior settings (Stick-Carrot-Stick)';

-- Seed AI settings for phone_repair
INSERT INTO elo_v_ai_settings (vertical_id, funnel_stage_id, name, pre_rules, freedom_level, post_rules, allowed_tools) VALUES
    ((SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
     NULL,  -- global for vertical
     'Phone Repair Default',
     '{
       "must_extract": ["device"],
       "forbidden_topics": ["politics", "religion"]
     }',
     50,
     '{
       "max_length": 500,
       "forbidden_words": ["дорого", "долго"],
       "must_include_tone": "friendly"
     }',
     '["extract_device", "extract_symptoms", "get_price", "send_response", "graph_query"]')
ON CONFLICT (vertical_id, funnel_stage_id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 4. elo_v_symptom_mappings - Which symptoms are relevant for vertical
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_v_symptom_mappings (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES elo_verticals(id),
    symptom_type_id INT NOT NULL REFERENCES elo_symptom_types(id),

    -- Vertical-specific config for this symptom
    priority INT DEFAULT 0,                -- higher = more common
    aliases JSONB DEFAULT '[]',            -- ["doesn't charge", "charging problem"]

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(vertical_id, symptom_type_id)
);

COMMENT ON TABLE elo_v_symptom_mappings IS 'Vertical level: Symptom type mappings per vertical';

-- Link all symptoms to phone_repair vertical
INSERT INTO elo_v_symptom_mappings (vertical_id, symptom_type_id, priority)
SELECT
    (SELECT id FROM elo_verticals WHERE code = 'phone_repair'),
    id,
    CASE category
        WHEN 'screen' THEN 100
        WHEN 'battery' THEN 90
        WHEN 'charging' THEN 80
        WHEN 'camera' THEN 70
        WHEN 'audio' THEN 60
        ELSE 50
    END
FROM elo_symptom_types
ON CONFLICT (vertical_id, symptom_type_id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- Indexes for Vertical level
-- ----------------------------------------------------------------------------
CREATE INDEX IF NOT EXISTS idx_elo_v_funnel_stages_vertical ON elo_v_funnel_stages(vertical_id);
CREATE INDEX IF NOT EXISTS idx_elo_v_prompts_vertical ON elo_v_prompts(vertical_id);
CREATE INDEX IF NOT EXISTS idx_elo_v_ai_settings_vertical ON elo_v_ai_settings(vertical_id);
CREATE INDEX IF NOT EXISTS idx_elo_v_symptom_mappings_vertical ON elo_v_symptom_mappings(vertical_id);


-- ============================================================================
-- Summary: Vertical Level Tables Created
-- ============================================================================
-- elo_v_funnel_stages     - 7 stages for phone_repair
-- elo_v_prompts           - 1 extraction prompt
-- elo_v_ai_settings       - 1 default setting
-- elo_v_symptom_mappings  - 25 symptom mappings
-- ============================================================================
