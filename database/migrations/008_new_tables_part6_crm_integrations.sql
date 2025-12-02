-- ============================================================================
-- Migration 008 Part 6: CRM Integrations (3 tables)
-- Date: 2025-11-23
-- Description: Integration with external CRM systems (LiveSklad, RemOnline)
-- ============================================================================

-- ============================================================================
-- 1. crm_integrations — Настройки интеграций с CRM
-- ============================================================================

CREATE TABLE IF NOT EXISTS crm_integrations (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    crm_type varchar(50) NOT NULL,
    name varchar(255) NOT NULL,
    api_url text NOT NULL,
    api_key text,
    api_secret text,
    credentials jsonb,
    sync_direction varchar(50) DEFAULT 'both',
    auto_sync boolean DEFAULT true,
    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_crm_type UNIQUE (tenant_id, crm_type),
    CONSTRAINT chk_crm_type CHECK (crm_type IN ('livesklad', 'remonline')),
    CONSTRAINT chk_sync_direction CHECK (sync_direction IN ('to_crm', 'from_crm', 'both'))
);

CREATE INDEX IF NOT EXISTS idx_crm_integrations_tenant ON crm_integrations(tenant_id);
CREATE INDEX IF NOT EXISTS idx_crm_integrations_tenant_active ON crm_integrations(tenant_id, is_active);

COMMENT ON TABLE crm_integrations IS 'Настройки интеграций с внешними CRM системами';
COMMENT ON COLUMN crm_integrations.credentials IS 'Дополнительные credentials в формате JSON';
COMMENT ON COLUMN crm_integrations.sync_direction IS 'Направление синхронизации: to_crm, from_crm, both';

-- ============================================================================
-- 2. crm_sync_history — История синхронизаций с CRM
-- ============================================================================

CREATE TABLE IF NOT EXISTS crm_sync_history (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    crm_integration_id uuid NOT NULL REFERENCES crm_integrations(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
    sync_type varchar(50) NOT NULL,
    direction varchar(50) NOT NULL,
    external_order_id varchar(255),
    status varchar(50) DEFAULT 'pending',
    request_payload jsonb,
    response_payload jsonb,
    error_message text,
    synced_at timestamptz,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_sync_type CHECK (sync_type IN ('create_order', 'update_order', 'get_order_status', 'get_order_result')),
    CONSTRAINT chk_direction CHECK (direction IN ('to_crm', 'from_crm')),
    CONSTRAINT chk_status CHECK (status IN ('pending', 'success', 'failed', 'retrying'))
);

CREATE INDEX IF NOT EXISTS idx_crm_sync_history_appeal ON crm_sync_history(appeal_id);
CREATE INDEX IF NOT EXISTS idx_crm_sync_history_tenant ON crm_sync_history(tenant_id);
CREATE INDEX IF NOT EXISTS idx_crm_sync_history_integration ON crm_sync_history(crm_integration_id);
CREATE INDEX IF NOT EXISTS idx_crm_sync_history_status ON crm_sync_history(status);
CREATE INDEX IF NOT EXISTS idx_crm_sync_history_created ON crm_sync_history(created_at);

COMMENT ON TABLE crm_sync_history IS 'История синхронизации заявок с внешними CRM';
COMMENT ON COLUMN crm_sync_history.external_order_id IS 'ID заказа в внешней CRM системе';
COMMENT ON COLUMN crm_sync_history.request_payload IS 'Отправленные данные в формате JSON';
COMMENT ON COLUMN crm_sync_history.response_payload IS 'Ответ от CRM в формате JSON';

-- ============================================================================
-- 3. crm_field_mappings — Маппинг полей между BatteryCRM и внешними CRM
-- ============================================================================

CREATE TABLE IF NOT EXISTS crm_field_mappings (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    crm_integration_id uuid NOT NULL REFERENCES crm_integrations(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    battery_field varchar(255) NOT NULL,
    crm_field varchar(255) NOT NULL,
    field_type varchar(100) NOT NULL,
    transform_rule jsonb,
    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_integration_battery_field UNIQUE (crm_integration_id, battery_field)
);

CREATE INDEX IF NOT EXISTS idx_crm_field_mappings_integration ON crm_field_mappings(crm_integration_id);
CREATE INDEX IF NOT EXISTS idx_crm_field_mappings_tenant ON crm_field_mappings(tenant_id);

COMMENT ON TABLE crm_field_mappings IS 'Настройка соответствия полей между BatteryCRM и внешними CRM';
COMMENT ON COLUMN crm_field_mappings.battery_field IS 'Поле в BatteryCRM (например, appeals.brand_id)';
COMMENT ON COLUMN crm_field_mappings.crm_field IS 'Поле в внешней CRM (например, order.device_brand)';
COMMENT ON COLUMN crm_field_mappings.field_type IS 'Тип преобразования (uuid_to_string, date_format, и т.д.)';
COMMENT ON COLUMN crm_field_mappings.transform_rule IS 'Правила трансформации данных в формате JSON';

-- ============================================================================
-- Migration complete: Part 6
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 008 Part 6 completed successfully: CRM Integrations (3 tables)';
END $$;
