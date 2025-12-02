-- ============================================================================
-- H/V SPLIT Migration 004: Horizontal Promo & UTM
-- Date: 2025-11-25
-- Description: Promo materials, triggers, UTM tracking, remarketing
-- ============================================================================

SET search_path TO horizontal, public;

-- ============================================================================
-- 1. promo_materials - Library of promotional content
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.promo_materials (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    description text,

    -- Content type
    type varchar(50) NOT NULL,

    -- Content
    content_url text,
    content_text text,
    thumbnail_url text,

    -- Organization
    category varchar(100),
    tags jsonb DEFAULT '[]',

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_promo_type CHECK (type IN ('video', 'pdf', 'image', 'text', 'link'))
);

CREATE INDEX IF NOT EXISTS idx_promo_materials_tenant ON horizontal.promo_materials(tenant_id);
CREATE INDEX IF NOT EXISTS idx_promo_materials_category ON horizontal.promo_materials(category);
CREATE INDEX IF NOT EXISTS idx_promo_materials_tenant_active ON horizontal.promo_materials(tenant_id, is_active);

COMMENT ON TABLE horizontal.promo_materials IS 'Library of promotional materials for automatic sending';

-- ============================================================================
-- 2. promo_triggers - Rules for automatic promo sending
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.promo_triggers (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    promo_material_id uuid NOT NULL REFERENCES horizontal.promo_materials(id) ON DELETE CASCADE,

    -- Trigger config
    trigger_type varchar(50) NOT NULL,
    trigger_conditions jsonb NOT NULL,

    -- Priority (higher = first)
    priority int DEFAULT 0,

    -- Vertical filter (null = all)
    vertical_id varchar(50),

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_trigger_type CHECK (trigger_type IN ('stage_change', 'brand_model', 'issue_type', 'time_based', 'custom'))
);

CREATE INDEX IF NOT EXISTS idx_promo_triggers_tenant ON horizontal.promo_triggers(tenant_id);
CREATE INDEX IF NOT EXISTS idx_promo_triggers_material ON horizontal.promo_triggers(promo_material_id);
CREATE INDEX IF NOT EXISTS idx_promo_triggers_tenant_active ON horizontal.promo_triggers(tenant_id, is_active);

COMMENT ON TABLE horizontal.promo_triggers IS 'Rules for automatic promo material sending';
COMMENT ON COLUMN horizontal.promo_triggers.trigger_conditions IS 'JSON conditions: {stage_id, brand_id, model_id, issue_type_id, etc}';

-- ============================================================================
-- 3. promo_history - Sent promo log
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.promo_history (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES horizontal.appeals(id) ON DELETE CASCADE,
    promo_material_id uuid NOT NULL REFERENCES horizontal.promo_materials(id),
    promo_trigger_id uuid REFERENCES horizontal.promo_triggers(id),

    -- Delivery
    channel_id uuid REFERENCES horizontal.channels(id),
    message_id uuid REFERENCES horizontal.messages_history(id),

    -- Who sent
    sent_type varchar(50) DEFAULT 'automatic',
    operator_id uuid REFERENCES horizontal.operators(id),

    sent_at timestamptz DEFAULT now(),

    CONSTRAINT chk_sent_type CHECK (sent_type IN ('automatic', 'manual'))
);

CREATE INDEX IF NOT EXISTS idx_promo_history_appeal ON horizontal.promo_history(appeal_id);
CREATE INDEX IF NOT EXISTS idx_promo_history_tenant ON horizontal.promo_history(tenant_id);
CREATE INDEX IF NOT EXISTS idx_promo_history_material ON horizontal.promo_history(promo_material_id);
CREATE INDEX IF NOT EXISTS idx_promo_history_sent_at ON horizontal.promo_history(sent_at);

COMMENT ON TABLE horizontal.promo_history IS 'Log of sent promotional materials';

-- ============================================================================
-- 4. utm_sources - UTM sources configuration
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.utm_sources (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    source_type varchar(50) NOT NULL,

    -- UTM defaults
    default_utm_source varchar(255),
    default_utm_medium varchar(255),

    -- API integration
    api_key text,
    api_secret text,
    credentials jsonb,

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_utm_source UNIQUE (tenant_id, name),
    CONSTRAINT chk_source_type CHECK (source_type IN ('yandex_direct', 'google_ads', 'vk_ads', 'facebook_ads', 'instagram_ads', 'avito_ads', 'organic', 'referral', 'other'))
);

CREATE INDEX IF NOT EXISTS idx_utm_sources_tenant ON horizontal.utm_sources(tenant_id);

COMMENT ON TABLE horizontal.utm_sources IS 'UTM sources and ad platforms configuration';

-- ============================================================================
-- 5. utm_campaigns - Advertising campaigns
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.utm_campaigns (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    utm_source_id uuid NOT NULL REFERENCES horizontal.utm_sources(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    external_campaign_id varchar(255),

    utm_campaign varchar(255),

    -- Budget
    budget numeric(12, 2),
    spent numeric(12, 2) DEFAULT 0,

    -- Stats
    impressions int DEFAULT 0,
    clicks int DEFAULT 0,
    leads int DEFAULT 0,
    conversions int DEFAULT 0,

    -- Dates
    start_date date,
    end_date date,

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_utm_campaigns_tenant ON horizontal.utm_campaigns(tenant_id);
CREATE INDEX IF NOT EXISTS idx_utm_campaigns_source ON horizontal.utm_campaigns(utm_source_id);
CREATE INDEX IF NOT EXISTS idx_utm_campaigns_external ON horizontal.utm_campaigns(external_campaign_id);

COMMENT ON TABLE horizontal.utm_campaigns IS 'Advertising campaigns tracking';

-- ============================================================================
-- 6. utm_ads - Individual ads
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.utm_ads (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    utm_campaign_id uuid NOT NULL REFERENCES horizontal.utm_campaigns(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    external_ad_id varchar(255),

    utm_content varchar(255),
    utm_term varchar(255),

    -- Stats
    impressions int DEFAULT 0,
    clicks int DEFAULT 0,
    leads int DEFAULT 0,
    conversions int DEFAULT 0,
    cost numeric(10, 2) DEFAULT 0,

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_utm_ads_campaign ON horizontal.utm_ads(utm_campaign_id);
CREATE INDEX IF NOT EXISTS idx_utm_ads_external ON horizontal.utm_ads(external_ad_id);

COMMENT ON TABLE horizontal.utm_ads IS 'Individual ads within campaigns';

-- ============================================================================
-- 7. remarketing_audiences - Audience segments for remarketing
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.remarketing_audiences (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    description text,

    -- Segment rules
    segment_type varchar(50) NOT NULL,
    segment_rules jsonb NOT NULL,

    -- External IDs (for ad platforms)
    yandex_audience_id varchar(255),
    google_audience_id varchar(255),
    vk_audience_id varchar(255),
    facebook_audience_id varchar(255),

    -- Stats
    audience_size int DEFAULT 0,
    last_synced_at timestamptz,

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_segment_type CHECK (segment_type IN ('completed_repair', 'abandoned', 'price_sent', 'loyal_customer', 'new_lead', 'custom'))
);

CREATE INDEX IF NOT EXISTS idx_remarketing_tenant ON horizontal.remarketing_audiences(tenant_id);
CREATE INDEX IF NOT EXISTS idx_remarketing_segment ON horizontal.remarketing_audiences(segment_type);

COMMENT ON TABLE horizontal.remarketing_audiences IS 'Audience segments for remarketing campaigns';
COMMENT ON COLUMN horizontal.remarketing_audiences.segment_rules IS 'JSON rules: {stage_ids, date_range, min_appeals, etc}';

-- ============================================================================
-- 8. remarketing_audience_members - Leads in audiences
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.remarketing_audience_members (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    audience_id uuid NOT NULL REFERENCES horizontal.remarketing_audiences(id) ON DELETE CASCADE,
    lead_id uuid NOT NULL REFERENCES horizontal.leads(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    added_at timestamptz DEFAULT now(),
    removed_at timestamptz,

    CONSTRAINT unique_audience_lead UNIQUE (audience_id, lead_id)
);

CREATE INDEX IF NOT EXISTS idx_audience_members_audience ON horizontal.remarketing_audience_members(audience_id);
CREATE INDEX IF NOT EXISTS idx_audience_members_lead ON horizontal.remarketing_audience_members(lead_id);

COMMENT ON TABLE horizontal.remarketing_audience_members IS 'Leads belonging to remarketing audiences';

-- ============================================================================
-- Migration complete: 004
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 004 completed: Horizontal promo materials, UTM tracking, remarketing';
END $$;
