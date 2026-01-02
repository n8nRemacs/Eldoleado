-- ============================================================================
-- ELO Migration 007: Domains & Context Extraction System
-- Four-level hierarchy: Global → Domain → Vertical → Tenant
-- ============================================================================

-- ============================================================================
-- PART 1: DOMAINS
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1.1 elo_domains - Business domains (electronics, auto, software)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_domains (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    -- Neo4j database name for this domain
    neo4j_database VARCHAR(50),

    -- Graph entities for this domain
    graph_entities JSONB DEFAULT '[]',
    -- ["Client", "Appeal", "Device", "Symptom", "Issue"]

    is_active BOOLEAN DEFAULT true,
    sort_order INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_domains IS 'Global: Business domains (aggregations of verticals)';

-- Seed 3 domains
INSERT INTO elo_domains (code, name, name_ru, neo4j_database, graph_entities, sort_order) VALUES
    ('electronics', 'Electronics', 'Электроника', 'electronics',
     '["Client", "Appeal", "Device", "Symptom", "Issue"]', 1),
    ('auto', 'Automotive', 'Авто', 'auto',
     '["Client", "Appeal", "Vehicle", "Symptom", "Issue"]', 2),
    ('software', 'Software', 'Программное обеспечение', 'software',
     '["Client", "Company", "Product", "License", "Ticket"]', 3)
ON CONFLICT (code) DO UPDATE SET
    name = EXCLUDED.name,
    name_ru = EXCLUDED.name_ru,
    neo4j_database = EXCLUDED.neo4j_database,
    graph_entities = EXCLUDED.graph_entities;

-- ----------------------------------------------------------------------------
-- 1.2 Add domain_id to elo_verticals
-- ----------------------------------------------------------------------------
ALTER TABLE elo_verticals ADD COLUMN IF NOT EXISTS domain_id INTEGER REFERENCES elo_domains(id);

-- Update existing verticals
UPDATE elo_verticals SET domain_id = (SELECT id FROM elo_domains WHERE code = 'electronics')
WHERE domain_id IS NULL;

-- ----------------------------------------------------------------------------
-- 1.3 elo_t_tenant_domains - Tenant connected domains
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_tenant_domains (
    id SERIAL PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,
    domain_id INTEGER NOT NULL REFERENCES elo_domains(id),

    is_primary BOOLEAN DEFAULT false,

    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, domain_id)
);

COMMENT ON TABLE elo_t_tenant_domains IS 'Tenant: Connected domains';

CREATE INDEX IF NOT EXISTS idx_elo_t_tenant_domains_tenant ON elo_t_tenant_domains(tenant_id);


-- ============================================================================
-- PART 2: GLOBAL LEVEL - Context Types, Intent Types, Normalization
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 2.1 elo_context_types - Global context types (greeting, goodbye, sentiment)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_context_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    -- Category
    category VARCHAR(30) NOT NULL,  -- funnel, meta, appeal
    -- funnel = affects stage (greeting, goodbye)
    -- meta = analytics only (sentiment, urgency)
    -- appeal = goes to graph (device, symptom)

    -- Data type
    data_type VARCHAR(20) DEFAULT 'string',  -- string, boolean, enum, number, object

    -- For enum types
    enum_values JSONB,

    -- Extraction config
    default_prompt TEXT,
    output_schema JSONB,

    is_active BOOLEAN DEFAULT true,
    sort_order INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_context_types IS 'Global: Context types available to all';

-- Seed global context types
INSERT INTO elo_context_types (code, name, name_ru, category, data_type, default_prompt) VALUES
    ('greeting', 'Greeting', 'Приветствие', 'funnel', 'boolean',
     'Is this a greeting message? Return true/false'),
    ('goodbye', 'Goodbye', 'Прощание', 'funnel', 'boolean',
     'Is this a goodbye message? Return true/false'),
    ('sentiment', 'Sentiment', 'Настроение', 'meta', 'enum',
     'Determine sentiment: positive, neutral, negative'),
    ('urgency', 'Urgency', 'Срочность', 'meta', 'enum',
     'Determine urgency: low, normal, high, critical'),
    ('question', 'Question', 'Вопрос', 'funnel', 'boolean',
     'Is this a question? Return true/false'),
    ('complaint', 'Complaint', 'Жалоба', 'meta', 'boolean',
     'Is this a complaint? Return true/false')
ON CONFLICT (code) DO NOTHING;

-- Update enum values
UPDATE elo_context_types SET enum_values = '["positive", "neutral", "negative"]' WHERE code = 'sentiment';
UPDATE elo_context_types SET enum_values = '["low", "normal", "high", "critical"]' WHERE code = 'urgency';

-- ----------------------------------------------------------------------------
-- 2.2 elo_intent_types - Global intent types
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_intent_types (
    id SERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    is_active BOOLEAN DEFAULT true,
    sort_order INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_intent_types IS 'Global: Base intent types';

-- Seed global intents
INSERT INTO elo_intent_types (code, name, name_ru, sort_order) VALUES
    ('greeting', 'Greeting', 'Приветствие', 1),
    ('goodbye', 'Goodbye', 'Прощание', 2),
    ('question', 'Question', 'Вопрос', 3),
    ('complaint', 'Complaint', 'Жалоба', 4),
    ('thanks', 'Thanks', 'Благодарность', 5),
    ('unclear', 'Unclear', 'Непонятно', 99)
ON CONFLICT (code) DO NOTHING;

-- ----------------------------------------------------------------------------
-- 2.3 elo_normalization_rules - Global normalization rules
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_normalization_rules (
    id SERIAL PRIMARY KEY,

    -- Level: global, domain, vertical, tenant
    level VARCHAR(20) NOT NULL DEFAULT 'global',
    domain_id INTEGER REFERENCES elo_domains(id),
    vertical_id INTEGER REFERENCES elo_verticals(id),
    tenant_id UUID REFERENCES elo_t_tenants(id),

    -- Target field
    field_code VARCHAR(50) NOT NULL,  -- device.brand, symptom.code

    -- Rule
    pattern VARCHAR(255) NOT NULL,     -- "айфон", "iphone", "iphon"
    normalized_value VARCHAR(255) NOT NULL,  -- "Apple"

    -- Priority (higher = checked first)
    priority INTEGER DEFAULT 0,

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_normalization_rules IS 'Multi-level: Normalization rules for extracted values';

CREATE INDEX IF NOT EXISTS idx_elo_normalization_rules_field ON elo_normalization_rules(field_code);
CREATE INDEX IF NOT EXISTS idx_elo_normalization_rules_level ON elo_normalization_rules(level, domain_id, vertical_id, tenant_id);

-- Seed some brand normalization rules
INSERT INTO elo_normalization_rules (level, field_code, pattern, normalized_value, priority) VALUES
    ('global', 'device.brand', 'айфон', 'Apple', 100),
    ('global', 'device.brand', 'iphone', 'Apple', 100),
    ('global', 'device.brand', 'apple', 'Apple', 100),
    ('global', 'device.brand', 'самсунг', 'Samsung', 90),
    ('global', 'device.brand', 'samsung', 'Samsung', 90),
    ('global', 'device.brand', 'галакси', 'Samsung', 85),
    ('global', 'device.brand', 'xiaomi', 'Xiaomi', 80),
    ('global', 'device.brand', 'сяоми', 'Xiaomi', 80),
    ('global', 'device.brand', 'редми', 'Xiaomi', 75),
    ('global', 'device.brand', 'хуавей', 'Huawei', 70),
    ('global', 'device.brand', 'huawei', 'Huawei', 70),
    ('global', 'device.brand', 'honor', 'Honor', 65),
    ('global', 'device.brand', 'хонор', 'Honor', 65)
ON CONFLICT DO NOTHING;


-- ============================================================================
-- PART 3: DOMAIN LEVEL - Context & Intent Types per Domain
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 3.1 elo_d_context_types - Domain-specific context types
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_d_context_types (
    id SERIAL PRIMARY KEY,
    domain_id INTEGER NOT NULL REFERENCES elo_domains(id),

    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    category VARCHAR(30) NOT NULL,  -- appeal, funnel, meta
    data_type VARCHAR(20) DEFAULT 'object',

    -- Schema for this context type
    output_schema JSONB,
    -- {"brand": "string", "model": "string", "color": "string"}

    -- Default extraction prompt
    default_prompt TEXT,

    -- Graph mapping
    graph_entity VARCHAR(50),
    graph_properties JSONB,

    is_required BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    sort_order INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(domain_id, code)
);

COMMENT ON TABLE elo_d_context_types IS 'Domain: Context types specific to domain';

-- Seed electronics domain context types
INSERT INTO elo_d_context_types (domain_id, code, name, name_ru, category, data_type, output_schema, graph_entity, graph_properties, is_required) VALUES
    ((SELECT id FROM elo_domains WHERE code = 'electronics'),
     'device', 'Device', 'Устройство', 'appeal', 'object',
     '{"brand": "string", "model": "string", "color": "string", "imei": "string"}',
     'Device', '{"brand": "brand", "model": "model", "color": "color"}', true),
    ((SELECT id FROM elo_domains WHERE code = 'electronics'),
     'owner', 'Owner Type', 'Владелец', 'appeal', 'enum',
     '{"owner": "string"}',
     'Appeal', '{"owner_type": "owner"}', false)
ON CONFLICT (domain_id, code) DO NOTHING;

-- Seed auto domain context types
INSERT INTO elo_d_context_types (domain_id, code, name, name_ru, category, data_type, output_schema, graph_entity, is_required) VALUES
    ((SELECT id FROM elo_domains WHERE code = 'auto'),
     'vehicle', 'Vehicle', 'Автомобиль', 'appeal', 'object',
     '{"make": "string", "model": "string", "year": "number", "vin": "string", "mileage": "number"}',
     'Vehicle', true),
    ((SELECT id FROM elo_domains WHERE code = 'auto'),
     'owner', 'Owner Type', 'Владелец', 'appeal', 'enum',
     '{"owner": "string"}',
     'Appeal', false)
ON CONFLICT (domain_id, code) DO NOTHING;

-- Seed software domain context types
INSERT INTO elo_d_context_types (domain_id, code, name, name_ru, category, data_type, output_schema, graph_entity, is_required) VALUES
    ((SELECT id FROM elo_domains WHERE code = 'software'),
     'product', 'Product', 'Продукт', 'appeal', 'object',
     '{"name": "string", "version": "string", "type": "string"}',
     'Product', true),
    ((SELECT id FROM elo_domains WHERE code = 'software'),
     'company', 'Company', 'Компания', 'appeal', 'object',
     '{"name": "string", "size": "string", "industry": "string"}',
     'Company', false)
ON CONFLICT (domain_id, code) DO NOTHING;

-- ----------------------------------------------------------------------------
-- 3.2 elo_d_intent_types - Domain-specific intent types
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_d_intent_types (
    id SERIAL PRIMARY KEY,
    domain_id INTEGER NOT NULL REFERENCES elo_domains(id),

    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    -- Which global intent this extends
    parent_intent_id INTEGER REFERENCES elo_intent_types(id),

    is_active BOOLEAN DEFAULT true,
    sort_order INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(domain_id, code)
);

COMMENT ON TABLE elo_d_intent_types IS 'Domain: Intent types specific to domain';

-- Seed domain intents
INSERT INTO elo_d_intent_types (domain_id, code, name, name_ru) VALUES
    ((SELECT id FROM elo_domains WHERE code = 'electronics'), 'device_inquiry', 'Device Inquiry', 'Вопрос об устройстве'),
    ((SELECT id FROM elo_domains WHERE code = 'auto'), 'vehicle_inquiry', 'Vehicle Inquiry', 'Вопрос об авто'),
    ((SELECT id FROM elo_domains WHERE code = 'software'), 'product_inquiry', 'Product Inquiry', 'Вопрос о продукте')
ON CONFLICT (domain_id, code) DO NOTHING;


-- ============================================================================
-- PART 4: VERTICAL LEVEL - Context & Intent Types per Vertical
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 4.1 elo_v_context_types - Vertical-specific context types
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_v_context_types (
    id SERIAL PRIMARY KEY,
    vertical_id INTEGER NOT NULL REFERENCES elo_verticals(id),

    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    category VARCHAR(30) NOT NULL,
    data_type VARCHAR(20) DEFAULT 'string',

    output_schema JSONB,
    default_prompt TEXT,

    graph_entity VARCHAR(50),
    graph_properties JSONB,

    is_required BOOLEAN DEFAULT false,
    is_active BOOLEAN DEFAULT true,
    sort_order INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(vertical_id, code)
);

COMMENT ON TABLE elo_v_context_types IS 'Vertical: Context types specific to vertical';

-- We need to create repair vertical first if not exists
INSERT INTO elo_verticals (code, name, description, domain_id)
SELECT 'repair', 'Repair', 'Repair services', d.id
FROM elo_domains d WHERE d.code = 'electronics'
ON CONFLICT (code) DO UPDATE SET domain_id = EXCLUDED.domain_id;

-- Seed repair vertical context types
INSERT INTO elo_v_context_types (vertical_id, code, name, name_ru, category, data_type, output_schema, graph_entity, is_required) VALUES
    ((SELECT id FROM elo_verticals WHERE code = 'repair'),
     'symptom', 'Symptom', 'Симптом', 'appeal', 'object',
     '{"text": "string", "code": "string"}',
     'Symptom', true),
    ((SELECT id FROM elo_verticals WHERE code = 'repair'),
     'complaint', 'Complaint', 'Жалоба клиента', 'appeal', 'string',
     '{"complaint": "string"}',
     'Issue', true),
    ((SELECT id FROM elo_verticals WHERE code = 'repair'),
     'warranty', 'Warranty', 'Гарантия', 'appeal', 'boolean',
     '{"warranty": "boolean"}',
     'Issue', false),
    ((SELECT id FROM elo_verticals WHERE code = 'repair'),
     'purchase_year', 'Purchase Year', 'Год покупки', 'appeal', 'number',
     '{"year": "number"}',
     'Device', false)
ON CONFLICT (vertical_id, code) DO NOTHING;

-- ----------------------------------------------------------------------------
-- 4.2 elo_v_intent_types - Vertical-specific intent types
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_v_intent_types (
    id SERIAL PRIMARY KEY,
    vertical_id INTEGER NOT NULL REFERENCES elo_verticals(id),

    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    is_active BOOLEAN DEFAULT true,
    sort_order INTEGER DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(vertical_id, code)
);

COMMENT ON TABLE elo_v_intent_types IS 'Vertical: Intent types specific to vertical';

-- Seed vertical intents
INSERT INTO elo_v_intent_types (vertical_id, code, name, name_ru) VALUES
    ((SELECT id FROM elo_verticals WHERE code = 'repair'), 'repair_request', 'Repair Request', 'Запрос на ремонт'),
    ((SELECT id FROM elo_verticals WHERE code = 'repair'), 'price_inquiry', 'Price Inquiry', 'Запрос цены'),
    ((SELECT id FROM elo_verticals WHERE code = 'repair'), 'book_appointment', 'Book Appointment', 'Запись на приём')
ON CONFLICT (vertical_id, code) DO NOTHING;


-- ============================================================================
-- PART 5: TENANT LEVEL - Overrides and Custom Fields
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 5.1 elo_t_context_type_overrides - Tenant overrides for context types
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_context_type_overrides (
    id SERIAL PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,

    -- Reference to context type (domain or vertical level)
    context_type_source VARCHAR(20) NOT NULL,  -- 'domain', 'vertical'
    d_context_type_id INTEGER REFERENCES elo_d_context_types(id),
    v_context_type_id INTEGER REFERENCES elo_v_context_types(id),

    -- What can be overridden
    is_required BOOLEAN,
    custom_prompt TEXT,
    custom_output_schema JSONB,

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    CONSTRAINT chk_context_type_source CHECK (
        (context_type_source = 'domain' AND d_context_type_id IS NOT NULL) OR
        (context_type_source = 'vertical' AND v_context_type_id IS NOT NULL)
    )
);

COMMENT ON TABLE elo_t_context_type_overrides IS 'Tenant: Overrides for context types';

CREATE INDEX IF NOT EXISTS idx_elo_t_context_type_overrides_tenant ON elo_t_context_type_overrides(tenant_id);

-- ----------------------------------------------------------------------------
-- 5.2 elo_custom_fields - Custom fields (any level)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_custom_fields (
    id SERIAL PRIMARY KEY,

    -- Level
    level VARCHAR(20) NOT NULL,  -- domain, vertical, tenant
    domain_id INTEGER REFERENCES elo_domains(id),
    vertical_id INTEGER REFERENCES elo_verticals(id),
    tenant_id UUID REFERENCES elo_t_tenants(id),

    -- Field definition
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    data_type VARCHAR(20) NOT NULL,  -- string, number, boolean, enum, date
    enum_values JSONB,

    -- Collection settings
    is_required BOOLEAN DEFAULT false,
    prompt_template TEXT,
    sort_order INTEGER DEFAULT 0,

    -- Graph mapping
    graph_entity VARCHAR(50),
    graph_property VARCHAR(50),

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_custom_fields IS 'Multi-level: Custom fields for extraction';

CREATE INDEX IF NOT EXISTS idx_elo_custom_fields_level ON elo_custom_fields(level, domain_id, vertical_id, tenant_id);

-- ----------------------------------------------------------------------------
-- 5.3 elo_t_custom_field_overrides - Tenant field overrides
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_custom_field_overrides (
    id SERIAL PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,
    field_id INTEGER NOT NULL REFERENCES elo_custom_fields(id),

    is_required BOOLEAN,
    prompt_template TEXT,
    sort_order INTEGER,

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, field_id)
);

COMMENT ON TABLE elo_t_custom_field_overrides IS 'Tenant: Custom field overrides';


-- ============================================================================
-- PART 6: FUNNEL SYSTEM
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 6.1 elo_funnel_stages - Unified funnel stages (system/vertical/tenant)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_funnel_stages (
    id SERIAL PRIMARY KEY,

    -- Type and level
    stage_type VARCHAR(20) NOT NULL,  -- system, vertical, tenant

    -- Binding (NULL for system stages)
    vertical_id INTEGER REFERENCES elo_verticals(id),
    tenant_id UUID REFERENCES elo_t_tenants(id),

    -- Definition
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    -- Order
    sort_order INTEGER NOT NULL,

    -- Conditions (JSONB)
    entry_conditions JSONB,
    exit_conditions JSONB,

    -- Actions (JSONB)
    on_enter_actions JSONB,
    on_exit_actions JSONB,
    on_message_actions JSONB,

    -- Timeout
    timeout_minutes INTEGER,
    on_timeout_actions JSONB,

    -- Flags
    is_required BOOLEAN DEFAULT false,
    can_skip BOOLEAN DEFAULT false,

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_funnel_stages IS 'Multi-level: Funnel stages (system required, vertical default, tenant custom)';

CREATE INDEX IF NOT EXISTS idx_elo_funnel_stages_type ON elo_funnel_stages(stage_type);
CREATE INDEX IF NOT EXISTS idx_elo_funnel_stages_vertical ON elo_funnel_stages(vertical_id);
CREATE INDEX IF NOT EXISTS idx_elo_funnel_stages_tenant ON elo_funnel_stages(tenant_id);

-- Seed system stages (required for all)
INSERT INTO elo_funnel_stages (stage_type, code, name, name_ru, sort_order, is_required, exit_conditions) VALUES
    ('system', 'lead', 'Lead', 'Лид', 10, true, '{"type": "any_of", "conditions": [{"field": "intent", "op": "exists"}]}'),
    ('system', 'qualification', 'Qualification', 'Квалификация', 20, true, '{"type": "any_of", "conditions": [{"field": "qualification_status", "op": "exists"}]}'),
    ('system', 'data_collection', 'Data Collection', 'Сбор данных', 30, true, '{"type": "all_of", "conditions": [{"field": "device", "op": "exists"}, {"field": "complaint", "op": "exists"}]}')
ON CONFLICT DO NOTHING;

-- ----------------------------------------------------------------------------
-- 6.2 elo_t_funnel_stage_overrides - Tenant stage overrides
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_funnel_stage_overrides (
    id SERIAL PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,
    stage_id INTEGER NOT NULL REFERENCES elo_funnel_stages(id),

    -- What can be overridden
    sort_order INTEGER,
    exit_conditions JSONB,
    on_enter_actions JSONB,
    on_exit_actions JSONB,
    timeout_minutes INTEGER,

    -- Disable stage (only for non-required stages)
    is_disabled BOOLEAN DEFAULT false,

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, stage_id)
);

COMMENT ON TABLE elo_t_funnel_stage_overrides IS 'Tenant: Funnel stage overrides';

-- ----------------------------------------------------------------------------
-- 6.3 elo_t_funnel_custom_stages - Tenant custom stages
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_funnel_custom_stages (
    id SERIAL PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,

    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),

    -- Position: insert after which stage
    insert_after_stage_id INTEGER REFERENCES elo_funnel_stages(id),

    -- Conditions and actions
    entry_conditions JSONB,
    exit_conditions JSONB,
    on_enter_actions JSONB,
    on_exit_actions JSONB,

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, code)
);

COMMENT ON TABLE elo_t_funnel_custom_stages IS 'Tenant: Custom funnel stages';


-- ============================================================================
-- PART 7: UNIVERSAL WORKERS SYSTEM
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 7.1 elo_prompts - Unified prompts table (all levels)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_prompts (
    id SERIAL PRIMARY KEY,

    -- Level
    level VARCHAR(20) NOT NULL,  -- global, domain, vertical, tenant
    domain_id INTEGER REFERENCES elo_domains(id),
    vertical_id INTEGER REFERENCES elo_verticals(id),
    tenant_id UUID REFERENCES elo_t_tenants(id),

    -- Identification
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100),
    description TEXT,

    -- Prompt content
    system_prompt TEXT,
    user_prompt_template TEXT,

    -- Output schema
    output_schema JSONB,

    -- Versioning
    version INTEGER DEFAULT 1,
    is_active BOOLEAN DEFAULT true,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_prompts IS 'Multi-level: AI prompts for extraction and generation';

CREATE INDEX IF NOT EXISTS idx_elo_prompts_level ON elo_prompts(level, domain_id, vertical_id, tenant_id);
CREATE INDEX IF NOT EXISTS idx_elo_prompts_code ON elo_prompts(code);

-- ----------------------------------------------------------------------------
-- 7.2 elo_worker_configs - Worker configurations
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_worker_configs (
    id SERIAL PRIMARY KEY,

    -- Worker type
    worker_type VARCHAR(30) NOT NULL,  -- extractor, data_fetch, hook_inbound, hook_outbound, generator

    -- Level
    level VARCHAR(20) NOT NULL,
    domain_id INTEGER REFERENCES elo_domains(id),
    vertical_id INTEGER REFERENCES elo_verticals(id),
    tenant_id UUID REFERENCES elo_t_tenants(id),

    -- Identification
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100),

    -- Prompt reference (for extractor/generator)
    prompt_id INTEGER REFERENCES elo_prompts(id),

    -- Configuration (depends on worker_type)
    config JSONB NOT NULL,

    -- Caching
    cache_enabled BOOLEAN DEFAULT true,
    cache_key_template VARCHAR(200),
    cache_ttl_minutes INTEGER DEFAULT 60,

    -- Trigger
    trigger_stage VARCHAR(50),
    trigger_event VARCHAR(50),

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_worker_configs IS 'Multi-level: Universal worker configurations';

CREATE INDEX IF NOT EXISTS idx_elo_worker_configs_type ON elo_worker_configs(worker_type);
CREATE INDEX IF NOT EXISTS idx_elo_worker_configs_level ON elo_worker_configs(level, domain_id, vertical_id, tenant_id);

-- ----------------------------------------------------------------------------
-- 7.3 elo_funnel_stage_workers - Link stages to workers
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_funnel_stage_workers (
    id SERIAL PRIMARY KEY,
    stage_id INTEGER NOT NULL REFERENCES elo_funnel_stages(id),
    worker_config_id INTEGER NOT NULL REFERENCES elo_worker_configs(id),

    -- Execution order (groups run sequentially, within group - parallel)
    execution_group INTEGER DEFAULT 0,

    -- Run conditions
    run_conditions JSONB,

    -- Result handling
    on_success JSONB,
    on_failure JSONB,

    is_active BOOLEAN DEFAULT true,

    UNIQUE(stage_id, worker_config_id)
);

COMMENT ON TABLE elo_funnel_stage_workers IS 'Link: Funnel stages to worker configs';


-- ============================================================================
-- PART 8: PREDEFINED ACTIONS & TRIGGERS
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 8.1 elo_action_types - Catalog of action types
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_action_types (
    id SERIAL PRIMARY KEY,

    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    -- Category
    category VARCHAR(30) NOT NULL,  -- response, notification, integration, system

    -- Parameter schema
    params_schema JSONB NOT NULL,

    -- Implementation
    worker_type VARCHAR(30),
    worker_config_template JSONB,

    -- Availability
    available_for_levels JSONB DEFAULT '["domain", "vertical", "tenant"]',
    requires_integration BOOLEAN DEFAULT false,

    is_active BOOLEAN DEFAULT true,
    sort_order INTEGER DEFAULT 0,

    -- For future custom actions
    is_custom BOOLEAN DEFAULT false,
    custom_handler_code TEXT
);

COMMENT ON TABLE elo_action_types IS 'Global: Catalog of predefined action types';

-- Seed action types
INSERT INTO elo_action_types (code, name, name_ru, category, params_schema, sort_order) VALUES
    ('send_text', 'Send Text', 'Отправить текст', 'response',
     '{"template_id": {"type": "string"}, "variables": {"type": "object"}}', 1),
    ('send_media', 'Send Media', 'Отправить медиа', 'response',
     '{"media_type": {"type": "string", "enum": ["image", "video", "document"]}, "url": {"type": "string"}}', 2),
    ('send_link', 'Send Link', 'Отправить ссылку', 'response',
     '{"url": {"type": "string"}, "title": {"type": "string"}, "description": {"type": "string"}}', 3),
    ('send_buttons', 'Send Buttons', 'Отправить кнопки', 'response',
     '{"text": {"type": "string"}, "buttons": {"type": "array"}}', 4),
    ('send_promo', 'Send Promo', 'Отправить промо', 'response',
     '{"promo_id": {"type": "string"}, "template_id": {"type": "string"}}', 5),
    ('book_appointment', 'Book Appointment', 'Записать на приём', 'integration',
     '{"service_type": {"type": "string"}, "collect_fields": {"type": "array"}}', 10),
    ('create_task', 'Create Task', 'Создать задачу', 'system',
     '{"task_type": {"type": "string"}, "assignee_rule": {"type": "string"}}', 20),
    ('notify_operator', 'Notify Operator', 'Уведомить оператора', 'notification',
     '{"channel": {"type": "string"}, "priority": {"type": "string"}}', 21),
    ('notify_client', 'Notify Client', 'Уведомить клиента', 'notification',
     '{"channel": {"type": "string"}, "template_id": {"type": "string"}}', 22),
    ('close_appeal', 'Close Appeal', 'Закрыть обращение', 'system',
     '{"status": {"type": "string"}, "reason": {"type": "string"}}', 30),
    ('transfer_operator', 'Transfer to Operator', 'Передать оператору', 'system',
     '{"operator_rule": {"type": "string"}, "priority": {"type": "string"}}', 31),
    ('collect_field', 'Collect Field', 'Запросить поле', 'system',
     '{"field_code": {"type": "string"}, "prompt_override": {"type": "string"}}', 40),
    ('skip_stage', 'Skip Stage', 'Пропустить этап', 'system',
     '{"target_stage": {"type": "string"}}', 41)
ON CONFLICT (code) DO NOTHING;

-- ----------------------------------------------------------------------------
-- 8.2 elo_trigger_types - Catalog of trigger types
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_trigger_types (
    id SERIAL PRIMARY KEY,

    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    name_ru VARCHAR(100),
    description TEXT,

    -- Category
    category VARCHAR(30) NOT NULL,  -- context, funnel, time, event, logic

    -- Condition schema
    condition_schema JSONB NOT NULL,

    -- When to evaluate
    evaluation_point VARCHAR(30),  -- on_message, on_stage_enter, on_stage_exit, scheduled, on_event

    is_active BOOLEAN DEFAULT true,
    sort_order INTEGER DEFAULT 0,

    -- For future custom triggers
    is_custom BOOLEAN DEFAULT false,
    custom_evaluator_code TEXT
);

COMMENT ON TABLE elo_trigger_types IS 'Global: Catalog of predefined trigger types';

-- Seed trigger types
INSERT INTO elo_trigger_types (code, name, name_ru, category, condition_schema, evaluation_point, sort_order) VALUES
    ('field_filled', 'Field Filled', 'Поле заполнено', 'context',
     '{"field_path": {"type": "string", "required": true}}', 'on_message', 1),
    ('field_empty', 'Field Empty', 'Поле пустое', 'context',
     '{"field_path": {"type": "string", "required": true}}', 'on_message', 2),
    ('field_equals', 'Field Equals', 'Поле равно', 'context',
     '{"field_path": {"type": "string"}, "value": {"type": "any"}}', 'on_message', 3),
    ('field_contains', 'Field Contains', 'Поле содержит', 'context',
     '{"field_path": {"type": "string"}, "substring": {"type": "string"}}', 'on_message', 4),
    ('stage_entered', 'Stage Entered', 'Вошли в этап', 'funnel',
     '{"stage_code": {"type": "string", "required": true}}', 'on_stage_enter', 10),
    ('stage_exited', 'Stage Exited', 'Вышли из этапа', 'funnel',
     '{"stage_code": {"type": "string", "required": true}}', 'on_stage_exit', 11),
    ('stage_timeout', 'Stage Timeout', 'Таймаут этапа', 'time',
     '{"stage_code": {"type": "string"}, "minutes": {"type": "number"}}', 'scheduled', 20),
    ('dialog_timeout', 'Dialog Timeout', 'Таймаут диалога', 'time',
     '{"minutes": {"type": "number", "required": true}}', 'scheduled', 21),
    ('intent_detected', 'Intent Detected', 'Обнаружен интент', 'context',
     '{"intent_code": {"type": "string", "required": true}}', 'on_message', 30),
    ('domain_mismatch', 'Domain Mismatch', 'Не тот домен', 'context',
     '{}', 'on_message', 40),
    ('vertical_mismatch', 'Vertical Mismatch', 'Не та вертикаль', 'context',
     '{}', 'on_message', 41),
    ('webhook_received', 'Webhook Received', 'Получен webhook', 'event',
     '{"webhook_code": {"type": "string"}}', 'on_event', 50),
    ('all_of', 'All Of', 'Все условия', 'logic',
     '{"conditions": {"type": "array", "required": true}}', 'on_message', 90),
    ('any_of', 'Any Of', 'Любое условие', 'logic',
     '{"conditions": {"type": "array", "required": true}}', 'on_message', 91),
    ('none_of', 'None Of', 'Ни одно условие', 'logic',
     '{"conditions": {"type": "array", "required": true}}', 'on_message', 92)
ON CONFLICT (code) DO NOTHING;

-- ----------------------------------------------------------------------------
-- 8.3 elo_triggers - Configured triggers
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_triggers (
    id SERIAL PRIMARY KEY,

    -- Level
    level VARCHAR(20) NOT NULL,
    domain_id INTEGER REFERENCES elo_domains(id),
    vertical_id INTEGER REFERENCES elo_verticals(id),
    tenant_id UUID REFERENCES elo_t_tenants(id),

    -- Identification
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100),

    -- Trigger type
    trigger_type_id INTEGER NOT NULL REFERENCES elo_trigger_types(id),

    -- Condition params
    condition_params JSONB NOT NULL,

    -- Action
    action_type_id INTEGER NOT NULL REFERENCES elo_action_types(id),
    action_params JSONB NOT NULL,

    -- Bind to funnel stage (optional)
    funnel_stage_id INTEGER REFERENCES elo_funnel_stages(id),

    -- Limits
    once_per_dialog BOOLEAN DEFAULT false,
    once_per_appeal BOOLEAN DEFAULT false,
    max_executions INTEGER,

    -- Priority
    priority INTEGER DEFAULT 0,

    -- Versioning
    version INTEGER DEFAULT 1,

    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_triggers IS 'Multi-level: Configured triggers';

CREATE INDEX IF NOT EXISTS idx_elo_triggers_level ON elo_triggers(level, domain_id, vertical_id, tenant_id);
CREATE INDEX IF NOT EXISTS idx_elo_triggers_stage ON elo_triggers(funnel_stage_id);


-- ============================================================================
-- PART 9: AUTO-GENERATION SYSTEM
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 9.1 elo_meta_prompts - Prompts for generating prompts
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_meta_prompts (
    id SERIAL PRIMARY KEY,

    generation_type VARCHAR(50) NOT NULL,  -- field_extractor, normalization, funnel_stage

    system_prompt TEXT NOT NULL,
    user_prompt_template TEXT NOT NULL,

    input_schema JSONB,
    output_schema JSONB,

    -- Few-shot examples
    examples JSONB,

    version INTEGER DEFAULT 1,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_meta_prompts IS 'Global: Meta-prompts for auto-generation';

-- Seed meta-prompt for field extractor generation
INSERT INTO elo_meta_prompts (generation_type, system_prompt, user_prompt_template, output_schema) VALUES
    ('field_extractor',
     'You are a prompt engineer. Generate extraction prompts for chatbot systems. The prompts should be clear, concise, and return JSON.',
     E'Generate an extraction prompt for the following field:\n\nField code: {{field_code}}\nField name: {{field_name}}\nDescription: {{field_description}}\nData type: {{data_type}}\nEnum values: {{enum_values}}\nRequired: {{is_required}}\nDomain: {{domain_name}}\nVertical: {{vertical_name}}\n\nReturn JSON with:\n- system_prompt: The system prompt for extraction\n- user_prompt_template: Template with {{message}} placeholder\n- output_schema: JSON schema for the output',
     '{"system_prompt": "string", "user_prompt_template": "string", "output_schema": "object"}')
ON CONFLICT DO NOTHING;

-- ----------------------------------------------------------------------------
-- 9.2 elo_auto_generation_log - Log of auto-generated configs
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_auto_generation_log (
    id SERIAL PRIMARY KEY,

    generation_type VARCHAR(30) NOT NULL,  -- field_prompt, normalization

    -- Source
    source_type VARCHAR(30) NOT NULL,
    source_id INTEGER NOT NULL,

    -- Result
    target_type VARCHAR(30) NOT NULL,
    target_id INTEGER,

    -- Meta
    meta_prompt_id INTEGER REFERENCES elo_meta_prompts(id),
    input_data JSONB,
    generated_output JSONB,

    status VARCHAR(20) DEFAULT 'active',  -- active, replaced, deleted

    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_auto_generation_log IS 'Log: Auto-generated configurations';

CREATE INDEX IF NOT EXISTS idx_elo_auto_generation_log_source ON elo_auto_generation_log(source_type, source_id);


-- ============================================================================
-- SUMMARY
-- ============================================================================
-- Created tables:
--
-- GLOBAL LEVEL:
--   elo_domains                    - Business domains
--   elo_context_types              - Global context types
--   elo_intent_types               - Global intent types
--   elo_normalization_rules        - Normalization rules (multi-level)
--
-- DOMAIN LEVEL:
--   elo_d_context_types            - Domain context types
--   elo_d_intent_types             - Domain intent types
--
-- VERTICAL LEVEL:
--   elo_v_context_types            - Vertical context types
--   elo_v_intent_types             - Vertical intent types
--
-- TENANT LEVEL:
--   elo_t_tenant_domains           - Tenant connected domains
--   elo_t_context_type_overrides   - Tenant overrides for context types
--   elo_custom_fields              - Custom fields (multi-level)
--   elo_t_custom_field_overrides   - Tenant field overrides
--
-- FUNNEL SYSTEM:
--   elo_funnel_stages              - Funnel stages (multi-level)
--   elo_t_funnel_stage_overrides   - Tenant stage overrides
--   elo_t_funnel_custom_stages     - Tenant custom stages
--
-- WORKERS SYSTEM:
--   elo_prompts                    - AI prompts (multi-level)
--   elo_worker_configs             - Worker configurations
--   elo_funnel_stage_workers       - Stage-worker links
--
-- ACTIONS & TRIGGERS:
--   elo_action_types               - Action type catalog
--   elo_trigger_types              - Trigger type catalog
--   elo_triggers                   - Configured triggers
--
-- AUTO-GENERATION:
--   elo_meta_prompts               - Meta-prompts for generation
--   elo_auto_generation_log        - Generation log
-- ============================================================================
