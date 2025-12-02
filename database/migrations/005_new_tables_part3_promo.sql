-- ============================================================================
-- Migration 005 Part 3: Promo Materials (3 tables)
-- Date: 2025-11-23
-- Description: Automatic promo materials sending system
-- ============================================================================

-- ============================================================================
-- 1. promo_materials — Библиотека промо-материалов
-- ============================================================================

CREATE TABLE IF NOT EXISTS promo_materials (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    name varchar(255) NOT NULL,
    description text,
    type varchar(50) NOT NULL,
    content_url text,
    content_text text,
    thumbnail_url text,
    category varchar(100),
    tags jsonb DEFAULT '[]',
    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_promo_type CHECK (type IN ('video', 'pdf', 'image', 'text', 'link'))
);

CREATE INDEX IF NOT EXISTS idx_promo_materials_tenant ON promo_materials(tenant_id);
CREATE INDEX IF NOT EXISTS idx_promo_materials_tenant_active ON promo_materials(tenant_id, is_active);
CREATE INDEX IF NOT EXISTS idx_promo_materials_category ON promo_materials(category);

COMMENT ON TABLE promo_materials IS 'Библиотека промо-материалов для автоматической отправки';
COMMENT ON COLUMN promo_materials.tags IS 'Теги для поиска в формате JSON array';

-- ============================================================================
-- 2. promo_triggers — Правила отправки промо
-- ============================================================================

CREATE TABLE IF NOT EXISTS promo_triggers (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    promo_material_id uuid NOT NULL REFERENCES promo_materials(id) ON DELETE CASCADE,
    trigger_type varchar(50) NOT NULL,
    trigger_conditions jsonb NOT NULL,
    priority int DEFAULT 0,
    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_trigger_type CHECK (trigger_type IN ('stage_change', 'brand_model', 'issue_type', 'time_based'))
);

CREATE INDEX IF NOT EXISTS idx_promo_triggers_tenant ON promo_triggers(tenant_id);
CREATE INDEX IF NOT EXISTS idx_promo_triggers_material ON promo_triggers(promo_material_id);
CREATE INDEX IF NOT EXISTS idx_promo_triggers_tenant_active ON promo_triggers(tenant_id, is_active);

COMMENT ON TABLE promo_triggers IS 'Настройка автоматической отправки промо-материалов по триггерам';
COMMENT ON COLUMN promo_triggers.trigger_conditions IS 'Условия срабатывания в формате JSON';

-- ============================================================================
-- 3. promo_history — История отправки промо
-- ============================================================================

CREATE TABLE IF NOT EXISTS promo_history (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
    promo_material_id uuid NOT NULL REFERENCES promo_materials(id),
    promo_trigger_id uuid REFERENCES promo_triggers(id),
    sent_at timestamptz DEFAULT now(),
    channel_id uuid REFERENCES channels(id),
    operator_id uuid REFERENCES operators(id),
    sent_type varchar(50) DEFAULT 'automatic',

    CONSTRAINT chk_sent_type CHECK (sent_type IN ('automatic', 'manual'))
);

CREATE INDEX IF NOT EXISTS idx_promo_history_appeal ON promo_history(appeal_id);
CREATE INDEX IF NOT EXISTS idx_promo_history_tenant ON promo_history(tenant_id);
CREATE INDEX IF NOT EXISTS idx_promo_history_material ON promo_history(promo_material_id);
CREATE INDEX IF NOT EXISTS idx_promo_history_sent_at ON promo_history(sent_at);

COMMENT ON TABLE promo_history IS 'Отслеживание отправленных промо-материалов';

-- ============================================================================
-- Migration complete: Part 3
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 005 Part 3 completed successfully: Promo Materials (3 tables)';
END $$;
