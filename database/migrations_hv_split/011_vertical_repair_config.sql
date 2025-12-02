-- ============================================================================
-- H/V SPLIT Migration 011: Vertical Repair - Configuration & CRM Mappings
-- Date: 2025-11-25
-- Description: Vertical-specific configuration, CRM field mappings
-- ============================================================================

SET search_path TO vertical_repair, horizontal, public;

-- ============================================================================
-- 1. vertical_repair_config - Repair vertical settings per tenant
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.config (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- General settings
    default_warranty_days int DEFAULT 30,
    require_imei boolean DEFAULT false,
    require_serial boolean DEFAULT false,
    require_device_photo boolean DEFAULT false,

    -- QR Code settings
    qr_enabled boolean DEFAULT true,
    qr_expiration_hours int DEFAULT 48,
    qr_format varchar(50) DEFAULT 'png',

    -- Pricing settings
    default_markup_percent numeric(5, 2) DEFAULT 30,
    min_repair_price numeric(10, 2),
    show_price_range boolean DEFAULT true,

    -- Diagnosis settings
    require_diagnosis boolean DEFAULT false,
    diagnosis_template text,

    -- Parts settings
    track_parts_inventory boolean DEFAULT false,
    default_parts_owner varchar(50) DEFAULT 'ours',

    -- AI extraction settings
    ai_extract_brand boolean DEFAULT true,
    ai_extract_model boolean DEFAULT true,
    ai_extract_issue boolean DEFAULT true,
    ai_extract_color boolean DEFAULT true,
    ai_confidence_threshold numeric(3, 2) DEFAULT 0.7,

    -- CRM sync settings
    default_crm_id uuid REFERENCES horizontal.crm_integrations(id),
    auto_create_crm_order boolean DEFAULT true,
    sync_status_updates boolean DEFAULT true,

    -- UI settings
    custom_fields jsonb DEFAULT '[]',
    required_fields jsonb DEFAULT '["brand", "issue"]',

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_config UNIQUE (tenant_id),
    CONSTRAINT chk_default_parts_owner CHECK (default_parts_owner IN ('ours', 'client', 'mixed'))
);

CREATE INDEX IF NOT EXISTS idx_vr_config_tenant ON vertical_repair.config(tenant_id);

COMMENT ON TABLE vertical_repair.config IS 'Configuration settings for repair vertical per tenant';
COMMENT ON COLUMN vertical_repair.config.custom_fields IS 'JSON array: [{name, type, required, options}]';
COMMENT ON COLUMN vertical_repair.config.required_fields IS 'JSON array of required field names';

-- ============================================================================
-- 2. crm_field_mappings - Vertical-specific CRM field mappings
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.crm_field_mappings (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    crm_integration_id uuid NOT NULL REFERENCES horizontal.crm_integrations(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Mapping
    local_entity varchar(100) NOT NULL,
    local_field varchar(255) NOT NULL,
    crm_field varchar(255) NOT NULL,

    -- Transform
    field_type varchar(100) NOT NULL,
    transform_rule jsonb,
    default_value text,

    -- Direction
    direction varchar(50) DEFAULT 'both',

    -- Custom mapping for repair-specific fields
    is_repair_specific boolean DEFAULT true,

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_vr_mapping UNIQUE (crm_integration_id, local_entity, local_field),
    CONSTRAINT chk_vr_mapping_direction CHECK (direction IN ('to_crm', 'from_crm', 'both')),
    CONSTRAINT chk_vr_local_entity CHECK (local_entity IN (
        'appeal_device', 'appeal_repair', 'brand', 'model',
        'repair_category', 'issue_type', 'price_list_item'
    ))
);

CREATE INDEX IF NOT EXISTS idx_vr_crm_mappings_integration ON vertical_repair.crm_field_mappings(crm_integration_id);
CREATE INDEX IF NOT EXISTS idx_vr_crm_mappings_tenant ON vertical_repair.crm_field_mappings(tenant_id);
CREATE INDEX IF NOT EXISTS idx_vr_crm_mappings_entity ON vertical_repair.crm_field_mappings(local_entity);

COMMENT ON TABLE vertical_repair.crm_field_mappings IS 'Repair-vertical specific CRM field mappings';
COMMENT ON COLUMN vertical_repair.crm_field_mappings.transform_rule IS 'JSON: {type: "map"|"format"|"lookup", config: {...}}';

-- ============================================================================
-- 3. Insert default config for existing tenants
-- ============================================================================

INSERT INTO vertical_repair.config (tenant_id)
SELECT t.id
FROM horizontal.tenants t
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.config c WHERE c.tenant_id = t.id
);

-- ============================================================================
-- 4. Insert default CRM field mappings for LiveSklad
-- ============================================================================

INSERT INTO vertical_repair.crm_field_mappings (
    crm_integration_id, tenant_id, local_entity, local_field, crm_field,
    field_type, transform_rule, direction
)
SELECT
    ci.id,
    ci.tenant_id,
    m.local_entity,
    m.local_field,
    m.crm_field,
    m.field_type,
    m.transform_rule::jsonb,
    m.direction
FROM horizontal.crm_integrations ci
CROSS JOIN (
    VALUES
        -- Device mappings
        ('appeal_device', 'brand_text', 'brand', 'string', '{}', 'to_crm'),
        ('appeal_device', 'model_text', 'model', 'string', '{}', 'to_crm'),
        ('appeal_device', 'serial_number', 'sn', 'string', '{}', 'both'),
        ('appeal_device', 'imei', 'imei', 'string', '{}', 'both'),
        ('appeal_device', 'color', 'color', 'string', '{}', 'to_crm'),
        ('appeal_device', 'condition', 'appearance', 'string', '{}', 'to_crm'),

        -- Repair mappings
        ('appeal_repair', 'category_text', 'malfunction_group', 'string', '{}', 'to_crm'),
        ('appeal_repair', 'issue_text', 'malfunction', 'string', '{}', 'to_crm'),
        ('appeal_repair', 'symptoms', 'notes', 'string', '{}', 'to_crm'),
        ('appeal_repair', 'estimated_cost', 'estimated_cost', 'number', '{}', 'to_crm'),
        ('appeal_repair', 'final_cost', 'final_cost', 'number', '{}', 'both'),
        ('appeal_repair', 'repair_status', 'status', 'string', '{"type":"map","config":{"pending":"new","in_progress":"in_work","completed":"done","cancelled":"canceled"}}', 'both')
) AS m(local_entity, local_field, crm_field, field_type, transform_rule, direction)
WHERE ci.crm_type = 'livesklad'
AND NOT EXISTS (
    SELECT 1 FROM vertical_repair.crm_field_mappings cfm
    WHERE cfm.crm_integration_id = ci.id
    AND cfm.local_entity = m.local_entity
    AND cfm.local_field = m.local_field
);

-- ============================================================================
-- 5. ai_extraction_prompts - Vertical-specific AI prompts
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.ai_extraction_prompts (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Prompt identification
    field_name varchar(100) NOT NULL,
    prompt_name varchar(255) NOT NULL,

    -- Prompt content
    system_prompt text NOT NULL,
    user_prompt_template text NOT NULL,
    examples jsonb DEFAULT '[]',

    -- Model settings
    model_name varchar(100) DEFAULT 'gpt-4o-mini',
    temperature numeric(2, 1) DEFAULT 0.3,
    max_tokens int DEFAULT 500,

    -- Validation
    output_type varchar(50) DEFAULT 'string',
    validation_regex varchar(500),
    allowed_values jsonb,

    -- Status
    is_active boolean DEFAULT true,
    priority int DEFAULT 0,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_field_prompt UNIQUE (tenant_id, field_name),
    CONSTRAINT chk_output_type CHECK (output_type IN ('string', 'number', 'boolean', 'json', 'array'))
);

CREATE INDEX IF NOT EXISTS idx_ai_prompts_tenant ON vertical_repair.ai_extraction_prompts(tenant_id);
CREATE INDEX IF NOT EXISTS idx_ai_prompts_field ON vertical_repair.ai_extraction_prompts(field_name);

COMMENT ON TABLE vertical_repair.ai_extraction_prompts IS 'AI extraction prompts specific to repair vertical';

-- ============================================================================
-- Insert default extraction prompts for repair vertical
-- ============================================================================

INSERT INTO vertical_repair.ai_extraction_prompts (
    tenant_id, field_name, prompt_name, system_prompt, user_prompt_template,
    examples, output_type
)
SELECT
    t.id,
    p.field_name,
    p.prompt_name,
    p.system_prompt,
    p.user_prompt_template,
    p.examples::jsonb,
    p.output_type
FROM horizontal.tenants t
CROSS JOIN (
    VALUES
        (
            'brand',
            'Extract Device Brand',
            'You are a device brand extractor. Extract the brand name from the user message about phone repair. Return ONLY the brand name in Russian or English, nothing else. If no brand mentioned, return "unknown".',
            'Extract brand from: {{message}}',
            '[{"input":"Разбил экран айфона","output":"Apple"},{"input":"Samsung S23 не включается","output":"Samsung"}]',
            'string'
        ),
        (
            'model',
            'Extract Device Model',
            'You are a device model extractor. Extract the model name/number from the user message about phone repair. Return ONLY the model name, nothing else. If no model mentioned, return "unknown".',
            'Extract model from: {{message}}',
            '[{"input":"iPhone 14 Pro Max экран треснул","output":"iPhone 14 Pro Max"},{"input":"Галакси S23 ультра батарея","output":"Galaxy S23 Ultra"}]',
            'string'
        ),
        (
            'issue',
            'Extract Repair Issue',
            'You are a repair issue extractor. Extract the main problem/issue from the user message. Return a brief description of the problem in Russian. If no issue clear, return "Требуется диагностика".',
            'Extract issue from: {{message}}',
            '[{"input":"экран разбился полностью","output":"Разбит экран"},{"input":"телефон не заряжается","output":"Не заряжается"}]',
            'string'
        ),
        (
            'color',
            'Extract Device Color',
            'You are a device color extractor. Extract the color of the device from the user message. Return ONLY the color in Russian. If no color mentioned, return "unknown".',
            'Extract color from: {{message}}',
            '[{"input":"белый айфон 14","output":"белый"},{"input":"черный самсунг","output":"черный"}]',
            'string'
        )
) AS p(field_name, prompt_name, system_prompt, user_prompt_template, examples, output_type)
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.ai_extraction_prompts aep
    WHERE aep.tenant_id = t.id AND aep.field_name = p.field_name
);

-- ============================================================================
-- Migration complete: 011
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 011 completed: Vertical Repair config & CRM mappings';
END $$;
