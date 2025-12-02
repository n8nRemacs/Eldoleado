-- ============================================================================
-- H/V SPLIT Migration 003: Horizontal Appeals & Messages
-- Date: 2025-11-25
-- Description: Appeals, messages, funnel stages
-- ============================================================================

SET search_path TO horizontal, public;

-- ============================================================================
-- 1. appeal_stages - 9-stage funnel definition
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.appeal_stages (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    description text,
    stage_group varchar(50) NOT NULL,
    "order" int NOT NULL,

    -- Display
    color varchar(20),
    icon varchar(255),

    -- Flags
    is_final boolean DEFAULT false,
    is_success boolean DEFAULT false,
    is_active boolean DEFAULT true,

    -- Auto-transition rules (JSON)
    auto_transition_rules jsonb,

    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_stage_name UNIQUE (tenant_id, name),
    CONSTRAINT unique_tenant_stage_order UNIQUE (tenant_id, "order"),
    CONSTRAINT chk_stage_group CHECK (stage_group IN ('initial', 'processing', 'confirmed', 'completed', 'cancelled'))
);

CREATE INDEX IF NOT EXISTS idx_appeal_stages_tenant ON horizontal.appeal_stages(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_stages_order ON horizontal.appeal_stages("order");

COMMENT ON TABLE horizontal.appeal_stages IS '9-stage funnel definition per tenant';

-- ============================================================================
-- 2. appeals - Main appeals/requests table
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.appeals (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    lead_id uuid NOT NULL REFERENCES horizontal.leads(id),

    -- Vertical
    vertical_id varchar(50) NOT NULL DEFAULT 'repair',

    -- Stage (new 9-stage system)
    appeal_stage_id uuid REFERENCES horizontal.appeal_stages(id),

    -- Legacy stage (for migration compatibility)
    stage varchar(100),

    -- Type
    appeal_type varchar(50) DEFAULT 'repair',

    -- Channel
    channel_id uuid REFERENCES horizontal.channels(id),
    channel_type varchar(50),

    -- Assignment
    assigned_operator_id uuid REFERENCES horizontal.operators(id),

    -- Multi-device flag
    is_multi_appeal boolean DEFAULT false,

    -- QR
    qr_generated_at timestamptz,
    qr_expires_at timestamptz,

    -- Pricing
    estimated_total numeric(10, 2),
    final_total numeric(10, 2),

    -- Priority & tags
    priority int DEFAULT 0,
    tags jsonb DEFAULT '[]',

    -- Notes
    notes text,
    internal_notes text,

    -- CRM sync
    external_crm_id varchar(255),
    external_crm_type varchar(50),
    last_synced_at timestamptz,

    -- Timestamps
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),
    completed_at timestamptz,

    CONSTRAINT chk_vertical_id CHECK (vertical_id IN ('repair', 'auto', 'beauty', 'real_estate', 'vet', 'dental')),
    CONSTRAINT chk_appeal_type CHECK (appeal_type IN ('repair', 'consultation', 'sale', 'purchase', 'warranty', 'other'))
);

CREATE INDEX IF NOT EXISTS idx_appeals_tenant ON horizontal.appeals(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeals_lead ON horizontal.appeals(lead_id);
CREATE INDEX IF NOT EXISTS idx_appeals_stage ON horizontal.appeals(appeal_stage_id);
CREATE INDEX IF NOT EXISTS idx_appeals_vertical ON horizontal.appeals(vertical_id);
CREATE INDEX IF NOT EXISTS idx_appeals_operator ON horizontal.appeals(assigned_operator_id);
CREATE INDEX IF NOT EXISTS idx_appeals_created ON horizontal.appeals(created_at);
CREATE INDEX IF NOT EXISTS idx_appeals_tenant_stage ON horizontal.appeals(tenant_id, appeal_stage_id);
CREATE INDEX IF NOT EXISTS idx_appeals_external_crm ON horizontal.appeals(external_crm_id);

COMMENT ON TABLE horizontal.appeals IS 'Main appeals/requests from customers';

-- ============================================================================
-- 3. appeal_stage_transitions - Funnel history
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.appeal_stage_transitions (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    appeal_id uuid NOT NULL REFERENCES horizontal.appeals(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    from_stage_id uuid REFERENCES horizontal.appeal_stages(id),
    to_stage_id uuid NOT NULL REFERENCES horizontal.appeal_stages(id),

    operator_id uuid REFERENCES horizontal.operators(id),
    transition_type varchar(50) DEFAULT 'manual',
    reason text,

    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_transition_type CHECK (transition_type IN ('manual', 'automatic', 'system'))
);

CREATE INDEX IF NOT EXISTS idx_stage_transitions_appeal ON horizontal.appeal_stage_transitions(appeal_id);
CREATE INDEX IF NOT EXISTS idx_stage_transitions_tenant ON horizontal.appeal_stage_transitions(tenant_id);
CREATE INDEX IF NOT EXISTS idx_stage_transitions_created ON horizontal.appeal_stage_transitions(created_at);

COMMENT ON TABLE horizontal.appeal_stage_transitions IS 'History of appeal stage changes';

-- ============================================================================
-- 4. messages_history - All messages
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.messages_history (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    appeal_id uuid REFERENCES horizontal.appeals(id) ON DELETE SET NULL,
    lead_id uuid REFERENCES horizontal.leads(id),

    -- Channel
    channel_id uuid REFERENCES horizontal.channels(id),
    channel_type varchar(50),

    -- Direction
    direction varchar(20) NOT NULL,

    -- Content
    message_type varchar(50) DEFAULT 'text',
    content text,
    media_url text,
    media_type varchar(50),

    -- Metadata
    external_message_id varchar(255),
    metadata jsonb,

    -- Sender
    sender_type varchar(20),
    sender_id uuid,
    sender_name varchar(255),

    -- Status
    status varchar(50) DEFAULT 'sent',
    delivered_at timestamptz,
    read_at timestamptz,

    -- AI processing
    ai_processed boolean DEFAULT false,
    ai_extracted_data jsonb,

    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_direction CHECK (direction IN ('incoming', 'outgoing')),
    CONSTRAINT chk_message_type CHECK (message_type IN ('text', 'image', 'video', 'audio', 'voice', 'document', 'location', 'contact', 'sticker')),
    CONSTRAINT chk_sender_type CHECK (sender_type IN ('lead', 'operator', 'system', 'bot'))
);

CREATE INDEX IF NOT EXISTS idx_messages_tenant ON horizontal.messages_history(tenant_id);
CREATE INDEX IF NOT EXISTS idx_messages_appeal ON horizontal.messages_history(appeal_id);
CREATE INDEX IF NOT EXISTS idx_messages_lead ON horizontal.messages_history(lead_id);
CREATE INDEX IF NOT EXISTS idx_messages_channel ON horizontal.messages_history(channel_id);
CREATE INDEX IF NOT EXISTS idx_messages_created ON horizontal.messages_history(created_at);
CREATE INDEX IF NOT EXISTS idx_messages_external ON horizontal.messages_history(external_message_id);

COMMENT ON TABLE horizontal.messages_history IS 'All incoming and outgoing messages';

-- ============================================================================
-- 5. Insert default 9 stages for existing tenants
-- ============================================================================

INSERT INTO horizontal.appeal_stages (tenant_id, name, description, stage_group, "order", color, icon, is_final, is_success)
SELECT
    t.id,
    s.name,
    s.description,
    s.stage_group,
    s.order_num,
    s.color,
    s.icon,
    s.is_final,
    s.is_success
FROM horizontal.tenants t
CROSS JOIN (
    VALUES
        ('New Empty', 'Minimal data, customer showed interest', 'initial', 1, '#9CA3AF', 'inbox', false, false),
        ('Collecting Info', 'Gathering model, issue, details', 'initial', 2, '#3B82F6', 'search', false, false),
        ('Info Collected', 'All required fields filled', 'processing', 3, '#8B5CF6', 'check-circle', false, false),
        ('Price Sent', 'Quote sent to customer', 'processing', 4, '#F59E0B', 'currency-dollar', false, false),
        ('Customer Confirmed', 'Customer agreed to repair', 'confirmed', 5, '#10B981', 'thumb-up', false, false),
        ('QR Sent', 'Customer received visit code', 'confirmed', 6, '#06B6D4', 'qrcode', false, false),
        ('Customer Arrived', 'Master scanned QR', 'confirmed', 7, '#14B8A6', 'user-check', false, false),
        ('Repair Done', 'Repair completed successfully', 'completed', 8, '#22C55E', 'badge-check', true, true),
        ('Cancelled/Spam', 'Appeal closed without result', 'cancelled', 9, '#EF4444', 'x-circle', true, false)
) AS s(name, description, stage_group, order_num, color, icon, is_final, is_success)
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.appeal_stages ast
    WHERE ast.tenant_id = t.id AND ast.name = s.name
);

-- ============================================================================
-- Migration complete: 003
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 003 completed: Horizontal appeals, messages, funnel stages';
END $$;
