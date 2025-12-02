-- ============================================================================
-- H/V SPLIT Migration 007: Horizontal CRM Integration & TTL
-- Date: 2025-11-25
-- Description: CRM integrations, sync history, data retention policies
-- ============================================================================

SET search_path TO horizontal, public;

-- ============================================================================
-- 1. crm_integrations - CRM connections configuration
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.crm_integrations (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    crm_type varchar(50) NOT NULL,
    name varchar(255) NOT NULL,

    -- Connection
    api_url text NOT NULL,
    api_key text,
    api_secret text,
    credentials jsonb,

    -- Sync settings
    sync_direction varchar(50) DEFAULT 'both',
    auto_sync boolean DEFAULT true,
    sync_interval_minutes int DEFAULT 5,

    -- Mapping config
    default_mappings jsonb,

    is_active boolean DEFAULT true,
    last_sync_at timestamptz,
    last_error text,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_crm_type UNIQUE (tenant_id, crm_type),
    CONSTRAINT chk_crm_type CHECK (crm_type IN ('livesklad', 'remonline', 'bitrix24', 'amocrm', 'custom')),
    CONSTRAINT chk_sync_direction CHECK (sync_direction IN ('to_crm', 'from_crm', 'both'))
);

CREATE INDEX IF NOT EXISTS idx_crm_integrations_tenant ON horizontal.crm_integrations(tenant_id);
CREATE INDEX IF NOT EXISTS idx_crm_integrations_tenant_active ON horizontal.crm_integrations(tenant_id, is_active);

COMMENT ON TABLE horizontal.crm_integrations IS 'External CRM system integrations';

-- ============================================================================
-- 2. crm_sync_history - Sync operations log
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.crm_sync_history (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    crm_integration_id uuid NOT NULL REFERENCES horizontal.crm_integrations(id) ON DELETE CASCADE,
    appeal_id uuid REFERENCES horizontal.appeals(id) ON DELETE CASCADE,

    -- Operation
    sync_type varchar(50) NOT NULL,
    direction varchar(50) NOT NULL,

    -- External reference
    external_order_id varchar(255),

    -- Status
    status varchar(50) DEFAULT 'pending',

    -- Data
    request_payload jsonb,
    response_payload jsonb,
    error_message text,

    -- Timing
    synced_at timestamptz,
    processing_time_ms int,

    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_sync_type CHECK (sync_type IN ('create_order', 'update_order', 'get_order_status', 'get_order_result', 'sync_catalog')),
    CONSTRAINT chk_direction CHECK (direction IN ('to_crm', 'from_crm')),
    CONSTRAINT chk_status CHECK (status IN ('pending', 'success', 'failed', 'retrying'))
);

CREATE INDEX IF NOT EXISTS idx_crm_sync_history_appeal ON horizontal.crm_sync_history(appeal_id);
CREATE INDEX IF NOT EXISTS idx_crm_sync_history_tenant ON horizontal.crm_sync_history(tenant_id);
CREATE INDEX IF NOT EXISTS idx_crm_sync_history_integration ON horizontal.crm_sync_history(crm_integration_id);
CREATE INDEX IF NOT EXISTS idx_crm_sync_history_status ON horizontal.crm_sync_history(status);
CREATE INDEX IF NOT EXISTS idx_crm_sync_history_external ON horizontal.crm_sync_history(external_order_id);
CREATE INDEX IF NOT EXISTS idx_crm_sync_history_created ON horizontal.crm_sync_history(created_at);

COMMENT ON TABLE horizontal.crm_sync_history IS 'Log of CRM synchronization operations';

-- ============================================================================
-- 3. crm_field_mappings - Field mappings (moved to vertical, but keep reference here)
-- Note: Actual mappings are in vertical_repair.crm_field_mappings
-- This table is for universal mappings
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.crm_field_mappings_universal (
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

    -- Direction
    direction varchar(50) DEFAULT 'both',

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_integration_local_field UNIQUE (crm_integration_id, local_entity, local_field),
    CONSTRAINT chk_mapping_direction CHECK (direction IN ('to_crm', 'from_crm', 'both'))
);

CREATE INDEX IF NOT EXISTS idx_crm_field_mappings_integration ON horizontal.crm_field_mappings_universal(crm_integration_id);
CREATE INDEX IF NOT EXISTS idx_crm_field_mappings_tenant ON horizontal.crm_field_mappings_universal(tenant_id);

COMMENT ON TABLE horizontal.crm_field_mappings_universal IS 'Universal field mappings for CRM sync (lead, appeal, operator)';

-- ============================================================================
-- 4. data_retention_policies - TTL policies
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.data_retention_policies (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    entity_type varchar(100) NOT NULL,
    retention_days int NOT NULL,
    action_type varchar(50) DEFAULT 'archive',

    is_active boolean DEFAULT true,
    last_executed_at timestamptz,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_entity_type UNIQUE (tenant_id, entity_type),
    CONSTRAINT chk_entity_type CHECK (entity_type IN ('messages_history', 'voice_transcriptions', 'ai_chat_memory', 'crm_sync_history', 'appeal_stage_transitions', 'promo_history')),
    CONSTRAINT chk_action_type CHECK (action_type IN ('archive', 'delete', 'anonymize')),
    CONSTRAINT chk_retention_days CHECK (retention_days > 0)
);

CREATE INDEX IF NOT EXISTS idx_data_retention_tenant ON horizontal.data_retention_policies(tenant_id);
CREATE INDEX IF NOT EXISTS idx_data_retention_tenant_active ON horizontal.data_retention_policies(tenant_id, is_active);

COMMENT ON TABLE horizontal.data_retention_policies IS 'Data retention and TTL policies per tenant';

-- ============================================================================
-- 5. archived_data - Archived records storage
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.archived_data (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    entity_type varchar(100) NOT NULL,
    entity_id uuid NOT NULL,
    entity_data jsonb NOT NULL,

    archived_at timestamptz DEFAULT now(),
    archived_by varchar(50) DEFAULT 'system',
    retention_policy_id uuid REFERENCES horizontal.data_retention_policies(id)
);

CREATE INDEX IF NOT EXISTS idx_archived_data_tenant ON horizontal.archived_data(tenant_id);
CREATE INDEX IF NOT EXISTS idx_archived_data_entity_type ON horizontal.archived_data(entity_type);
CREATE INDEX IF NOT EXISTS idx_archived_data_entity_id ON horizontal.archived_data(entity_id);
CREATE INDEX IF NOT EXISTS idx_archived_data_archived_at ON horizontal.archived_data(archived_at);

COMMENT ON TABLE horizontal.archived_data IS 'Storage for archived data based on retention policies';

-- ============================================================================
-- 6. Insert default retention policies for existing tenants
-- ============================================================================

INSERT INTO horizontal.data_retention_policies (tenant_id, entity_type, retention_days, action_type)
SELECT
    t.id,
    pol.entity_type,
    pol.retention_days,
    pol.action_type
FROM horizontal.tenants t
CROSS JOIN (
    VALUES
        ('messages_history', 365, 'archive'),
        ('voice_transcriptions', 180, 'archive'),
        ('ai_chat_memory', 90, 'delete'),
        ('crm_sync_history', 365, 'archive'),
        ('appeal_stage_transitions', 365, 'archive'),
        ('promo_history', 365, 'archive')
) AS pol(entity_type, retention_days, action_type)
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.data_retention_policies drp
    WHERE drp.tenant_id = t.id AND drp.entity_type = pol.entity_type
);

-- ============================================================================
-- Migration complete: 007
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 007 completed: Horizontal CRM integration & TTL policies';
END $$;
