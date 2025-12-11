-- ============================================================================
-- ELO Migration 001: Domain Level Tables (elo_)
-- Global dictionaries - no tenant_id, no vertical_id
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. elo_channels - Communication channels
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_channels (
    id SERIAL PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,      -- telegram, whatsapp, avito, vk, max, form, phone
    name VARCHAR(100) NOT NULL,

    -- Channel configuration
    config JSONB DEFAULT '{}',
    -- config.supports_buttons: true/false
    -- config.supports_media: true/false
    -- config.max_message_length: 4096
    -- config.webhook_required: true/false

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_channels IS 'Domain level: Global channel dictionary';

-- Seed channels
INSERT INTO elo_channels (code, name, config) VALUES
    ('telegram', 'Telegram', '{"supports_buttons": true, "supports_media": true, "max_message_length": 4096}'),
    ('whatsapp', 'WhatsApp', '{"supports_buttons": true, "supports_media": true, "max_message_length": 4096}'),
    ('avito', 'Avito', '{"supports_buttons": false, "supports_media": true, "max_message_length": 4000}'),
    ('vk', 'VKontakte', '{"supports_buttons": true, "supports_media": true, "max_message_length": 4096}'),
    ('max', 'MAX (VK Teams)', '{"supports_buttons": true, "supports_media": true, "max_message_length": 4096}'),
    ('form', 'Web Form', '{"supports_buttons": false, "supports_media": false, "max_message_length": 10000}'),
    ('phone', 'Phone Call', '{"supports_buttons": false, "supports_media": false, "max_message_length": null}')
ON CONFLICT (code) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 2. elo_verticals - Business verticals
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_verticals (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,      -- phone_repair, auto_service, beauty_salon
    name VARCHAR(100) NOT NULL,
    description TEXT,

    -- Default configuration for this vertical
    default_config JSONB DEFAULT '{}',
    -- default_config.intents: ['repair', 'purchase', 'question']
    -- default_config.device_brands: ['Apple', 'Samsung', ...]
    -- default_config.issue_categories: ['Screen', 'Battery', ...]

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_verticals IS 'Domain level: Business vertical types';

-- Seed phone_repair vertical (MVP)
INSERT INTO elo_verticals (code, name, description, default_config) VALUES
    ('phone_repair', 'Phone Repair', 'Mobile phone repair services', '{
        "intents": ["repair", "purchase", "question", "sale"],
        "device_brands": ["Apple", "Samsung", "Xiaomi", "Huawei", "Honor", "Realme", "OPPO", "Vivo", "Google", "OnePlus"],
        "issue_categories": ["Screen", "Battery", "Charging", "Camera", "Speaker", "Microphone", "Software", "Water Damage", "Body"]
    }')
ON CONFLICT (code) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 3. elo_symptom_types - Global symptom catalog
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_symptom_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),                  -- Russian name for AI extraction
    description TEXT,

    -- Categorization
    category VARCHAR(50),                  -- screen, battery, charging, camera, etc.

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_symptom_types IS 'Domain level: Global symptom type catalog';

-- Seed symptoms for phone_repair
INSERT INTO elo_symptom_types (code, name, name_ru, category) VALUES
    -- Screen
    ('screen_cracked', 'Screen Cracked', 'Разбит экран', 'screen'),
    ('screen_not_working', 'Screen Not Working', 'Экран не работает', 'screen'),
    ('screen_flickering', 'Screen Flickering', 'Экран мерцает', 'screen'),
    ('screen_touch_issues', 'Touch Not Responding', 'Сенсор не работает', 'screen'),
    ('screen_lines', 'Lines on Screen', 'Полосы на экране', 'screen'),

    -- Battery
    ('battery_drain', 'Battery Drains Fast', 'Быстро разряжается', 'battery'),
    ('battery_swollen', 'Battery Swollen', 'Вздулась батарея', 'battery'),
    ('battery_not_charging', 'Not Charging', 'Не заряжается', 'battery'),
    ('battery_shutdowns', 'Random Shutdowns', 'Выключается сам', 'battery'),

    -- Charging
    ('charging_port_broken', 'Charging Port Broken', 'Сломан разъём зарядки', 'charging'),
    ('charging_slow', 'Slow Charging', 'Медленно заряжается', 'charging'),
    ('charging_cable_loose', 'Cable Not Holding', 'Кабель не держится', 'charging'),

    -- Camera
    ('camera_not_working', 'Camera Not Working', 'Камера не работает', 'camera'),
    ('camera_blurry', 'Camera Blurry', 'Камера мутная', 'camera'),
    ('camera_cracked', 'Camera Glass Cracked', 'Разбито стекло камеры', 'camera'),

    -- Audio
    ('speaker_not_working', 'Speaker Not Working', 'Динамик не работает', 'audio'),
    ('microphone_not_working', 'Microphone Not Working', 'Микрофон не работает', 'audio'),
    ('no_sound', 'No Sound', 'Нет звука', 'audio'),

    -- Software
    ('software_crash', 'Software Crashing', 'Глючит/зависает', 'software'),
    ('software_boot_loop', 'Boot Loop', 'Перезагружается', 'software'),
    ('software_update_failed', 'Update Failed', 'Не обновляется', 'software'),

    -- Other
    ('water_damage', 'Water Damage', 'Залит водой', 'damage'),
    ('buttons_not_working', 'Buttons Not Working', 'Кнопки не работают', 'hardware'),
    ('overheating', 'Overheating', 'Перегревается', 'hardware'),
    ('other', 'Other Issue', 'Другая проблема', 'other')
ON CONFLICT (code) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 4. elo_diagnosis_types - Global diagnosis catalog
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_diagnosis_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    category VARCHAR(50),

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_diagnosis_types IS 'Domain level: Global diagnosis type catalog';

-- Seed diagnoses
INSERT INTO elo_diagnosis_types (code, name, name_ru, category) VALUES
    ('display_replacement', 'Display Replacement Needed', 'Требуется замена дисплея', 'screen'),
    ('battery_replacement', 'Battery Replacement Needed', 'Требуется замена батареи', 'battery'),
    ('charging_port_replacement', 'Charging Port Replacement', 'Требуется замена разъёма', 'charging'),
    ('camera_replacement', 'Camera Replacement', 'Требуется замена камеры', 'camera'),
    ('speaker_replacement', 'Speaker Replacement', 'Требуется замена динамика', 'audio'),
    ('microphone_replacement', 'Microphone Replacement', 'Требуется замена микрофона', 'audio'),
    ('board_repair', 'Board Repair Needed', 'Требуется ремонт платы', 'hardware'),
    ('software_reinstall', 'Software Reinstall', 'Требуется перепрошивка', 'software'),
    ('cleaning_needed', 'Cleaning Needed', 'Требуется чистка', 'maintenance'),
    ('not_repairable', 'Not Repairable', 'Не подлежит ремонту', 'other')
ON CONFLICT (code) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 5. elo_repair_actions - Global repair action catalog
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_repair_actions (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    category VARCHAR(50),

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_repair_actions IS 'Domain level: Global repair action catalog';

-- Seed repair actions
INSERT INTO elo_repair_actions (code, name, name_ru, category) VALUES
    ('display_replaced', 'Display Replaced', 'Заменён дисплей', 'screen'),
    ('battery_replaced', 'Battery Replaced', 'Заменена батарея', 'battery'),
    ('charging_port_replaced', 'Charging Port Replaced', 'Заменён разъём зарядки', 'charging'),
    ('camera_replaced', 'Camera Replaced', 'Заменена камера', 'camera'),
    ('speaker_replaced', 'Speaker Replaced', 'Заменён динамик', 'audio'),
    ('microphone_replaced', 'Microphone Replaced', 'Заменён микрофон', 'audio'),
    ('board_repaired', 'Board Repaired', 'Отремонтирована плата', 'hardware'),
    ('software_reinstalled', 'Software Reinstalled', 'Перепрошит', 'software'),
    ('cleaned', 'Cleaned', 'Почищен', 'maintenance'),
    ('refused', 'Repair Refused', 'Отказ от ремонта', 'other')
ON CONFLICT (code) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 6. elo_problem_categories - Problem category catalog
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_problem_categories (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),

    -- Display order
    sort_order INT DEFAULT 0,

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_problem_categories IS 'Domain level: Problem category catalog';

-- Seed categories
INSERT INTO elo_problem_categories (code, name, name_ru, sort_order) VALUES
    ('screen', 'Screen', 'Экран', 1),
    ('battery', 'Battery', 'Батарея', 2),
    ('charging', 'Charging', 'Зарядка', 3),
    ('camera', 'Camera', 'Камера', 4),
    ('audio', 'Audio', 'Звук', 5),
    ('software', 'Software', 'Программное обеспечение', 6),
    ('hardware', 'Hardware', 'Аппаратные проблемы', 7),
    ('damage', 'Physical Damage', 'Физические повреждения', 8),
    ('other', 'Other', 'Другое', 99)
ON CONFLICT (code) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 7. elo_cypher_queries - Graph Query Tool queries
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_cypher_queries (
    id SERIAL PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL,     -- get_client_context, create_device, etc.
    name VARCHAR(255) NOT NULL,
    description TEXT,

    -- The Cypher query
    query TEXT NOT NULL,

    -- Parameter schema (for validation)
    param_schema JSONB DEFAULT '{}',
    -- param_schema: {"client_id": "uuid", "tenant_id": "uuid"}

    -- Metadata
    category VARCHAR(50),                  -- read, write, delete
    is_active BOOLEAN DEFAULT true,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_cypher_queries IS 'Domain level: Cypher queries for Graph Tool';

-- Seed queries for MVP
INSERT INTO elo_cypher_queries (code, name, description, query, param_schema, category) VALUES

-- Read queries
('get_client_context', 'Get Client Context', 'Full client context with devices, issues, symptoms',
$$MATCH (c:Client {pg_id: $client_id})
OPTIONAL MATCH (c)-[:OWNS]->(d:Device)
OPTIONAL MATCH (d)-[:HAS_ISSUE]->(i:Issue)
OPTIONAL MATCH (i)-[:HAS_INTAKE]->(int:Intake)
OPTIONAL MATCH (int)-[:HAS_SYMPTOM]->(s:Symptom)
OPTIONAL MATCH (i)-[:HAS_REPAIR]->(r:Repair)
OPTIONAL MATCH (c)-[:HAS_TRAIT]->(t:Trait)
RETURN c as client,
       collect(DISTINCT d) as devices,
       collect(DISTINCT i) as issues,
       collect(DISTINCT s) as symptoms,
       collect(DISTINCT r) as repairs,
       collect(DISTINCT t) as traits$$,
'{"client_id": "string"}', 'read'),

('get_device_history', 'Get Device History', 'Device repair history',
$$MATCH (d:Device {pg_id: $device_id})
OPTIONAL MATCH (d)-[:HAS_ISSUE]->(i:Issue)
OPTIONAL MATCH (i)-[:HAS_INTAKE]->(int:Intake)
OPTIONAL MATCH (int)-[:HAS_SYMPTOM]->(s:Symptom)
OPTIONAL MATCH (i)-[:HAS_DIAGNOSIS]->(diag:Diagnosis)
OPTIONAL MATCH (i)-[:HAS_REPAIR]->(r:Repair)
RETURN d as device,
       collect(DISTINCT {issue: i, symptoms: collect(s), diagnosis: diag, repair: r}) as history
ORDER BY i.created_at DESC$$,
'{"device_id": "string"}', 'read'),

-- Write queries
('create_client', 'Create Client', 'Create new client node',
$$CREATE (c:Client {
  pg_id: $client_id,
  tenant_id: $tenant_id,
  name: $name,
  phone: $phone,
  created_at: datetime()
})
RETURN c.pg_id as client_id$$,
'{"client_id": "string", "tenant_id": "string", "name": "string", "phone": "string"}', 'write'),

('create_or_get_device', 'Create or Get Device', 'Create device or get existing by brand+model',
$$MATCH (c:Client {pg_id: $client_id})
MERGE (d:Device {
  tenant_id: $tenant_id,
  brand: $brand,
  model: $model
})
ON CREATE SET
  d.pg_id = randomUUID(),
  d.color = $color,
  d.created_at = datetime()
ON MATCH SET
  d.updated_at = datetime()
MERGE (c)-[:OWNS]->(d)
RETURN d.pg_id as device_id, d as device$$,
'{"client_id": "string", "tenant_id": "string", "brand": "string", "model": "string", "color": "string"}', 'write'),

('create_issue', 'Create Issue', 'Create new issue for device',
$$MATCH (d:Device {pg_id: $device_id})
CREATE (i:Issue {
  pg_id: randomUUID(),
  tenant_id: $tenant_id,
  dialog_id: $dialog_id,
  vertical_code: $vertical_code,
  status: 'active',
  created_at: datetime()
})
CREATE (d)-[:HAS_ISSUE]->(i)
RETURN i.pg_id as issue_id, i as issue$$,
'{"device_id": "string", "tenant_id": "string", "dialog_id": "string", "vertical_code": "string"}', 'write'),

('create_intake_with_symptoms', 'Create Intake with Symptoms', 'Create intake and link symptoms',
$$MATCH (i:Issue {pg_id: $issue_id})
CREATE (int:Intake {
  pg_id: randomUUID(),
  tenant_id: $tenant_id,
  raw_text: $raw_text,
  created_at: datetime()
})
CREATE (i)-[:HAS_INTAKE]->(int)
WITH int
UNWIND $symptoms as symptom
CREATE (s:Symptom {
  pg_id: randomUUID(),
  code: symptom.code,
  text: symptom.text,
  confidence: symptom.confidence,
  created_at: datetime()
})
CREATE (int)-[:HAS_SYMPTOM]->(s)
RETURN int.pg_id as intake_id, count(s) as symptom_count$$,
'{"issue_id": "string", "tenant_id": "string", "raw_text": "string", "symptoms": "array"}', 'write'),

('add_client_trait', 'Add Client Trait', 'Add trait to client',
$$MATCH (c:Client {pg_id: $client_id})
MERGE (t:Trait {type: $type, value: $value})
ON CREATE SET t.created_at = datetime()
MERGE (c)-[r:HAS_TRAIT]->(t)
ON CREATE SET r.confidence = $confidence, r.source = $source, r.created_at = datetime()
ON MATCH SET r.confidence = $confidence, r.updated_at = datetime()
RETURN t$$,
'{"client_id": "string", "type": "string", "value": "string", "confidence": "number", "source": "string"}', 'write')

ON CONFLICT (code) DO UPDATE SET
    query = EXCLUDED.query,
    param_schema = EXCLUDED.param_schema,
    updated_at = NOW();


-- ----------------------------------------------------------------------------
-- Indexes for Domain level
-- ----------------------------------------------------------------------------
CREATE INDEX IF NOT EXISTS idx_elo_symptom_types_category ON elo_symptom_types(category);
CREATE INDEX IF NOT EXISTS idx_elo_diagnosis_types_category ON elo_diagnosis_types(category);
CREATE INDEX IF NOT EXISTS idx_elo_repair_actions_category ON elo_repair_actions(category);
CREATE INDEX IF NOT EXISTS idx_elo_cypher_queries_category ON elo_cypher_queries(category);


-- ============================================================================
-- Summary: Domain Level Tables Created
-- ============================================================================
-- elo_channels          - 7 channels
-- elo_verticals         - 1 vertical (phone_repair)
-- elo_symptom_types     - 25 symptom types
-- elo_diagnosis_types   - 10 diagnosis types
-- elo_repair_actions    - 10 repair actions
-- elo_problem_categories - 9 categories
-- elo_cypher_queries    - 7 queries for Graph Tool
-- ============================================================================
