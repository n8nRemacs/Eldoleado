-- ============================================================================
-- Migration 009 Part 7: TTL Policies & Data Archival (2 tables)
-- Date: 2025-11-23
-- Description: Data retention policies and archival storage
-- ============================================================================

-- ============================================================================
-- 1. data_retention_policies — Политики хранения данных
-- ============================================================================

CREATE TABLE IF NOT EXISTS data_retention_policies (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    entity_type varchar(100) NOT NULL,
    retention_days int NOT NULL,
    action_type varchar(50) DEFAULT 'archive',
    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_entity_type UNIQUE (tenant_id, entity_type),
    CONSTRAINT chk_entity_type CHECK (entity_type IN ('messages_history', 'voice_transcriptions', 'ai_chat_memory', 'operator_actions', 'appeal_stage_transitions')),
    CONSTRAINT chk_action_type CHECK (action_type IN ('archive', 'delete', 'anonymize')),
    CONSTRAINT chk_retention_days CHECK (retention_days > 0)
);

CREATE INDEX IF NOT EXISTS idx_data_retention_policies_tenant ON data_retention_policies(tenant_id);
CREATE INDEX IF NOT EXISTS idx_data_retention_policies_tenant_active ON data_retention_policies(tenant_id, is_active);

COMMENT ON TABLE data_retention_policies IS 'Настройка сроков хранения данных и автоматической архивации';
COMMENT ON COLUMN data_retention_policies.entity_type IS 'Тип сущности для которой применяется политика';
COMMENT ON COLUMN data_retention_policies.retention_days IS 'Срок хранения в днях до выполнения action_type';
COMMENT ON COLUMN data_retention_policies.action_type IS 'Действие: archive (архивировать), delete (удалить), anonymize (обезличить)';

-- ============================================================================
-- 2. archived_data — Архивированные данные
-- ============================================================================

CREATE TABLE IF NOT EXISTS archived_data (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    entity_type varchar(100) NOT NULL,
    entity_id uuid NOT NULL,
    entity_data jsonb NOT NULL,
    archived_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_archived_data_tenant ON archived_data(tenant_id);
CREATE INDEX IF NOT EXISTS idx_archived_data_entity_type ON archived_data(entity_type);
CREATE INDEX IF NOT EXISTS idx_archived_data_entity_id ON archived_data(entity_id);
CREATE INDEX IF NOT EXISTS idx_archived_data_archived_at ON archived_data(archived_at);

COMMENT ON TABLE archived_data IS 'Хранение архивированных данных в формате JSON';
COMMENT ON COLUMN archived_data.entity_type IS 'Тип архивированной сущности (соответствует data_retention_policies.entity_type)';
COMMENT ON COLUMN archived_data.entity_id IS 'ID оригинальной записи в исходной таблице';
COMMENT ON COLUMN archived_data.entity_data IS 'Полные данные архивированной записи в формате JSON';

-- ============================================================================
-- Insert default retention policies for all tenants
-- ============================================================================

INSERT INTO data_retention_policies (tenant_id, entity_type, retention_days, action_type)
SELECT
    t.id,
    pol.entity_type,
    pol.retention_days,
    pol.action_type
FROM tenants t
CROSS JOIN (
    VALUES
        ('messages_history', 365, 'archive'),
        ('voice_transcriptions', 180, 'archive'),
        ('ai_chat_memory', 90, 'delete'),
        ('operator_actions', 730, 'archive'),
        ('appeal_stage_transitions', 365, 'archive')
) AS pol(entity_type, retention_days, action_type)
WHERE NOT EXISTS (
    SELECT 1 FROM data_retention_policies drp
    WHERE drp.tenant_id = t.id AND drp.entity_type = pol.entity_type
);

-- ============================================================================
-- Migration complete: Part 7
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 009 Part 7 completed successfully: TTL Policies & Data Archival (2 tables)';
END $$;
