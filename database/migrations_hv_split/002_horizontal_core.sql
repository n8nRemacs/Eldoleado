-- ============================================================================
-- H/V SPLIT Migration 002: Horizontal Core Tables
-- Date: 2025-11-25
-- Description: Core horizontal tables - tenants, leads, appeals, messages, operators
-- ============================================================================

SET search_path TO horizontal, public;

-- ============================================================================
-- 1. tenants - Multi-tenant core
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.tenants (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(255) NOT NULL,
    slug varchar(100) UNIQUE,
    logo_url text,
    timezone varchar(50) DEFAULT 'Europe/Moscow',
    locale varchar(10) DEFAULT 'ru',
    is_active boolean DEFAULT true,
    subscription_plan varchar(50) DEFAULT 'basic',
    subscription_expires_at timestamptz,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_tenants_slug ON horizontal.tenants(slug);
CREATE INDEX IF NOT EXISTS idx_tenants_active ON horizontal.tenants(is_active);

COMMENT ON TABLE horizontal.tenants IS 'Service centers / businesses using the platform';

-- ============================================================================
-- 2. tenant_settings - Tenant configuration
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.tenant_settings (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Business hours
    work_start_time time DEFAULT '09:00',
    work_end_time time DEFAULT '20:00',
    working_days jsonb DEFAULT '[1,2,3,4,5,6]',

    -- Notifications
    notify_new_appeal boolean DEFAULT true,
    notify_new_message boolean DEFAULT true,

    -- Auto-responses
    auto_greeting_enabled boolean DEFAULT false,
    auto_greeting_text text,

    -- AI settings
    ai_enabled boolean DEFAULT true,
    ai_auto_extract boolean DEFAULT true,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_settings UNIQUE (tenant_id)
);

COMMENT ON TABLE horizontal.tenant_settings IS 'Tenant-specific configuration';

-- ============================================================================
-- 3. tenant_verticals - M2M tenant <-> vertical
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.tenant_verticals (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    vertical_id varchar(50) NOT NULL,
    is_active boolean DEFAULT true,
    config jsonb DEFAULT '{}',
    activated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_vertical UNIQUE (tenant_id, vertical_id),
    CONSTRAINT chk_vertical_id CHECK (vertical_id IN ('repair', 'auto', 'beauty', 'real_estate', 'vet', 'dental'))
);

CREATE INDEX IF NOT EXISTS idx_tenant_verticals_tenant ON horizontal.tenant_verticals(tenant_id);
CREATE INDEX IF NOT EXISTS idx_tenant_verticals_vertical ON horizontal.tenant_verticals(vertical_id);

COMMENT ON TABLE horizontal.tenant_verticals IS 'Which verticals each tenant uses';

-- ============================================================================
-- 4. leads - Customers/clients
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.leads (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Contact info
    phone varchar(50),
    phone_normalized varchar(20),
    email varchar(255),

    -- Name
    first_name varchar(100),
    last_name varchar(100),
    full_name varchar(255),

    -- Source
    source varchar(100),
    source_details jsonb,

    -- UTM
    utm_source varchar(255),
    utm_medium varchar(255),
    utm_campaign varchar(255),
    utm_term varchar(255),
    utm_content varchar(255),

    -- Meta
    tags jsonb DEFAULT '[]',
    notes text,

    -- Stats
    total_appeals int DEFAULT 0,
    last_appeal_at timestamptz,

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_leads_tenant ON horizontal.leads(tenant_id);
CREATE INDEX IF NOT EXISTS idx_leads_phone ON horizontal.leads(phone_normalized);
CREATE INDEX IF NOT EXISTS idx_leads_email ON horizontal.leads(email);
CREATE INDEX IF NOT EXISTS idx_leads_tenant_phone ON horizontal.leads(tenant_id, phone_normalized);
CREATE INDEX IF NOT EXISTS idx_leads_created ON horizontal.leads(created_at);

COMMENT ON TABLE horizontal.leads IS 'Customer contacts across all verticals';

-- ============================================================================
-- 5. channels - Communication channels
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.channels (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    channel_type varchar(50) NOT NULL,
    name varchar(255) NOT NULL,

    -- Credentials
    api_key text,
    api_secret text,
    webhook_url text,
    credentials jsonb,

    -- Settings
    is_active boolean DEFAULT true,
    is_default boolean DEFAULT false,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_channel_type CHECK (channel_type IN ('whatsapp', 'telegram', 'vk', 'avito', 'instagram', 'phone', 'web_form', 'email'))
);

CREATE INDEX IF NOT EXISTS idx_channels_tenant ON horizontal.channels(tenant_id);
CREATE INDEX IF NOT EXISTS idx_channels_tenant_type ON horizontal.channels(tenant_id, channel_type);

COMMENT ON TABLE horizontal.channels IS 'Communication channels configuration';

-- ============================================================================
-- 6. operators - Staff members
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.operators (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Auth
    email varchar(255) NOT NULL,
    password_hash varchar(255),

    -- Profile
    first_name varchar(100),
    last_name varchar(100),
    full_name varchar(255),
    phone varchar(50),
    avatar_url text,

    -- Role & permissions
    role varchar(50) DEFAULT 'operator',
    permissions jsonb DEFAULT '[]',

    -- FCM
    fcm_token text,
    fcm_token_updated_at timestamptz,

    -- Status
    is_active boolean DEFAULT true,
    is_online boolean DEFAULT false,
    last_seen_at timestamptz,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_operator_email UNIQUE (tenant_id, email),
    CONSTRAINT chk_operator_role CHECK (role IN ('admin', 'operator', 'master', 'manager'))
);

CREATE INDEX IF NOT EXISTS idx_operators_tenant ON horizontal.operators(tenant_id);
CREATE INDEX IF NOT EXISTS idx_operators_email ON horizontal.operators(email);
CREATE INDEX IF NOT EXISTS idx_operators_tenant_active ON horizontal.operators(tenant_id, is_active);

COMMENT ON TABLE horizontal.operators IS 'Staff members / users of the system';

-- ============================================================================
-- 7. operator_devices - Multi-device sessions
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.operator_devices (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    operator_id uuid NOT NULL REFERENCES horizontal.operators(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    device_id varchar(255) NOT NULL,
    device_type varchar(50) NOT NULL,
    device_name varchar(255),
    device_info jsonb,

    -- Session
    access_token text,
    refresh_token text,
    token_expires_at timestamptz,

    -- FCM
    fcm_token text,

    is_active boolean DEFAULT true,
    last_active_at timestamptz DEFAULT now(),
    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_operator_device UNIQUE (operator_id, device_id),
    CONSTRAINT chk_device_type CHECK (device_type IN ('mobile', 'desktop', 'web'))
);

CREATE INDEX IF NOT EXISTS idx_operator_devices_operator ON horizontal.operator_devices(operator_id);
CREATE INDEX IF NOT EXISTS idx_operator_devices_device ON horizontal.operator_devices(device_id);

COMMENT ON TABLE horizontal.operator_devices IS 'Operator device sessions for multi-device support';

-- ============================================================================
-- 8. sessions - JWT sessions
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.sessions (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    operator_id uuid NOT NULL REFERENCES horizontal.operators(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    device_id uuid REFERENCES horizontal.operator_devices(id) ON DELETE CASCADE,

    access_token_hash varchar(255),
    refresh_token_hash varchar(255),

    ip_address inet,
    user_agent text,

    expires_at timestamptz NOT NULL,
    revoked_at timestamptz,

    created_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_sessions_operator ON horizontal.sessions(operator_id);
CREATE INDEX IF NOT EXISTS idx_sessions_expires ON horizontal.sessions(expires_at) WHERE revoked_at IS NULL;

COMMENT ON TABLE horizontal.sessions IS 'Active JWT sessions';

-- ============================================================================
-- Migration complete: 002
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 002 completed: Horizontal core tables (tenants, leads, channels, operators)';
END $$;
