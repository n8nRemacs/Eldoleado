-- ============================================================================
-- ELO Migration 003: Tenant Level Tables (elo_t_)
-- Tenant data - has tenant_id (required)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. elo_t_tenants - Tenants (businesses)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_tenants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Identity
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(100) UNIQUE,              -- for URL: batterycrm.eldoleado.ru

    -- Domain (for future multi-domain)
    domain_id INT DEFAULT 1,

    -- Settings
    settings JSONB DEFAULT '{}',
    -- settings.timezone: 'Europe/Moscow'
    -- settings.currency: 'RUB'
    -- settings.language: 'ru'
    -- settings.ai_mode: 'assist' | 'auto' | 'manual'
    -- settings.working_hours: {start: '09:00', end: '20:00'}
    -- settings.debounce_seconds: 10

    -- Subscription
    plan VARCHAR(50) DEFAULT 'free',       -- free, starter, pro, enterprise
    max_operators INT DEFAULT 3,
    max_dialogs_per_month INT DEFAULT 500,

    -- Status
    is_active BOOLEAN DEFAULT true,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_t_tenants IS 'Tenant level: Business accounts';

CREATE INDEX IF NOT EXISTS idx_elo_t_tenants_slug ON elo_t_tenants(slug);
CREATE INDEX IF NOT EXISTS idx_elo_t_tenants_active ON elo_t_tenants(is_active);


-- ----------------------------------------------------------------------------
-- 2. elo_t_tenant_verticals - Which verticals tenant uses
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_tenant_verticals (
    id SERIAL PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,
    vertical_id INT NOT NULL REFERENCES elo_verticals(id),

    -- Customization
    custom_config JSONB DEFAULT '{}',      -- Override vertical defaults

    is_primary BOOLEAN DEFAULT false,      -- Main vertical for this tenant

    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, vertical_id)
);

COMMENT ON TABLE elo_t_tenant_verticals IS 'Tenant level: Vertical assignments';

CREATE INDEX IF NOT EXISTS idx_elo_t_tenant_verticals_tenant ON elo_t_tenant_verticals(tenant_id);


-- ----------------------------------------------------------------------------
-- 3. elo_t_operators - Operators/staff
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_operators (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,

    -- Auth
    email VARCHAR(255),
    phone VARCHAR(20),
    telegram_id VARCHAR(50),
    password_hash VARCHAR(255),

    -- Profile
    name VARCHAR(255) NOT NULL,
    avatar_url TEXT,
    role VARCHAR(20) DEFAULT 'operator',   -- admin, operator, viewer

    -- Push notifications
    fcm_tokens JSONB DEFAULT '[]',

    -- Settings
    settings JSONB DEFAULT '{}',
    -- settings.notifications: {new_dialog: true, new_message: true}
    -- settings.working_hours: {start: '09:00', end: '18:00'}

    -- Status
    is_active BOOLEAN DEFAULT true,
    last_seen_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, email),
    UNIQUE(tenant_id, telegram_id)
);

COMMENT ON TABLE elo_t_operators IS 'Tenant level: Staff/operators';

CREATE INDEX IF NOT EXISTS idx_elo_t_operators_tenant ON elo_t_operators(tenant_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_operators_active ON elo_t_operators(tenant_id, is_active);


-- ----------------------------------------------------------------------------
-- 4. elo_t_channel_accounts - Connected channel accounts
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_channel_accounts (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,
    channel_id INT NOT NULL REFERENCES elo_channels(id),

    -- Account identification
    account_id VARCHAR(255) NOT NULL,      -- bot_token, profile_id, group_id
    account_name VARCHAR(255),             -- Bot name, display name

    -- Webhook (for incoming messages)
    webhook_hash VARCHAR(64),              -- For URL generation
    webhook_url TEXT,                      -- Full webhook URL

    -- Credentials (sensitive)
    credentials JSONB DEFAULT '{}',
    -- telegram: {bot_token: '...'}
    -- whatsapp: {profile_id: '...', api_key: '...'}
    -- avito: {client_id: '...', client_secret: '...'}

    -- Status
    is_active BOOLEAN DEFAULT true,
    last_webhook_at TIMESTAMPTZ,
    error_count INT DEFAULT 0,
    last_error TEXT,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(tenant_id, channel_id, account_id)
);

COMMENT ON TABLE elo_t_channel_accounts IS 'Tenant level: Connected messaging channels';

CREATE INDEX IF NOT EXISTS idx_elo_t_channel_accounts_tenant ON elo_t_channel_accounts(tenant_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_channel_accounts_account ON elo_t_channel_accounts(account_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_channel_accounts_webhook ON elo_t_channel_accounts(webhook_hash);


-- ----------------------------------------------------------------------------
-- 5. elo_t_clients - Clients (customers)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_clients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,

    -- Contact info
    name VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(255),

    -- Profile (AI-enriched)
    profile JSONB DEFAULT '{}',
    -- profile.preferences: ['original parts', 'fast service']
    -- profile.sentiment: 'positive' | 'neutral' | 'negative'
    -- profile.language: 'ru'

    -- Stats (calculated)
    stats JSONB DEFAULT '{}',
    -- stats.total_dialogs: 5
    -- stats.total_orders: 3
    -- stats.total_spent: 45000
    -- stats.last_contact_at: '2024-12-09T10:30:00Z'

    -- Neo4j sync
    neo4j_synced_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_t_clients IS 'Tenant level: Customer records';

CREATE INDEX IF NOT EXISTS idx_elo_t_clients_tenant ON elo_t_clients(tenant_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_clients_phone ON elo_t_clients(tenant_id, phone);
CREATE INDEX IF NOT EXISTS idx_elo_t_clients_email ON elo_t_clients(tenant_id, email);


-- ----------------------------------------------------------------------------
-- 6. elo_t_client_channels - Client channel identifiers
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_client_channels (
    id SERIAL PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES elo_t_clients(id) ON DELETE CASCADE,
    channel_id INT NOT NULL REFERENCES elo_channels(id),

    -- External identifier
    external_id VARCHAR(100) NOT NULL,     -- telegram_id, whatsapp_id, etc.
    external_username VARCHAR(100),        -- @username if available

    -- Metadata
    metadata JSONB DEFAULT '{}',
    -- metadata.first_seen_at: timestamp
    -- metadata.chat_id: for group chats

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(client_id, channel_id),
    UNIQUE(channel_id, external_id)  -- One external_id per channel globally
);

COMMENT ON TABLE elo_t_client_channels IS 'Tenant level: Client channel identifiers';

CREATE INDEX IF NOT EXISTS idx_elo_t_client_channels_client ON elo_t_client_channels(client_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_client_channels_external ON elo_t_client_channels(channel_id, external_id);


-- ----------------------------------------------------------------------------
-- 7. elo_t_dialogs - Dialogs (conversations)
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_dialogs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,
    client_id UUID NOT NULL REFERENCES elo_t_clients(id),

    -- Channel info
    channel_id INT NOT NULL REFERENCES elo_channels(id),
    external_chat_id VARCHAR(100),         -- Chat ID in external system

    -- Vertical (can be NULL initially, set by Core)
    vertical_id INT REFERENCES elo_verticals(id),

    -- Status
    status_id INT DEFAULT 1,               -- 1=active, 2=waiting, 3=completed, 4=archived

    -- Funnel stage
    current_stage_id INT REFERENCES elo_v_funnel_stages(id),
    stage_entered_at TIMESTAMPTZ,

    -- Assignment
    assigned_operator_id UUID REFERENCES elo_t_operators(id),

    -- AI context (updated after each message)
    context JSONB DEFAULT '{}',
    -- context.intent: 'repair' | 'purchase' | 'question'
    -- context.device: {brand: 'Apple', model: 'iPhone 14'}
    -- context.issue: 'screen cracked'
    -- context.price_quoted: 15000
    -- context.awaiting: 'price_confirmation' | null

    -- Metadata
    metadata JSONB DEFAULT '{}',
    -- metadata.source: 'organic' | 'ad' | 'referral'
    -- metadata.utm: {...}

    -- Timestamps
    last_message_at TIMESTAMPTZ,
    last_client_message_at TIMESTAMPTZ,
    last_operator_message_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_t_dialogs IS 'Tenant level: Conversation records';

CREATE INDEX IF NOT EXISTS idx_elo_t_dialogs_tenant ON elo_t_dialogs(tenant_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_dialogs_client ON elo_t_dialogs(client_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_dialogs_status ON elo_t_dialogs(tenant_id, status_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_dialogs_channel ON elo_t_dialogs(tenant_id, channel_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_dialogs_operator ON elo_t_dialogs(assigned_operator_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_dialogs_last_message ON elo_t_dialogs(tenant_id, last_message_at DESC);
CREATE INDEX IF NOT EXISTS idx_elo_t_dialogs_active ON elo_t_dialogs(tenant_id, status_id) WHERE status_id IN (1, 2);


-- ----------------------------------------------------------------------------
-- 8. elo_t_messages - Messages
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,
    dialog_id UUID NOT NULL REFERENCES elo_t_dialogs(id) ON DELETE CASCADE,
    client_id UUID REFERENCES elo_t_clients(id),

    -- Direction
    direction_id INT NOT NULL,             -- 1=inbound (from client), 2=outbound (to client)

    -- Message type
    message_type_id INT DEFAULT 1,         -- 1=text, 2=media, 3=voice, 4=system

    -- Actor
    actor_type VARCHAR(20) NOT NULL,       -- client, operator, ai, system
    actor_id UUID,                         -- operator_id or NULL

    -- Content
    content TEXT,
    media JSONB DEFAULT '{}',
    -- media.type: 'image' | 'voice' | 'document'
    -- media.url: '...'
    -- media.voice_transcribed: '...'

    -- Graph sync
    changed_graph BOOLEAN DEFAULT false,   -- Did this message change the graph?

    -- External reference
    external_message_id VARCHAR(100),
    trace_id VARCHAR(100),

    -- Timestamps
    timestamp TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_t_messages IS 'Tenant level: Message history';

CREATE INDEX IF NOT EXISTS idx_elo_t_messages_dialog ON elo_t_messages(dialog_id, timestamp DESC);
CREATE INDEX IF NOT EXISTS idx_elo_t_messages_tenant ON elo_t_messages(tenant_id, timestamp DESC);
CREATE INDEX IF NOT EXISTS idx_elo_t_messages_trace ON elo_t_messages(trace_id);


-- ----------------------------------------------------------------------------
-- 9. elo_t_price_list - Tenant price list
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_price_list (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,
    vertical_id INT REFERENCES elo_verticals(id),

    -- Service type
    service_type VARCHAR(50) NOT NULL,     -- repair, purchase, service

    -- Device filter (optional)
    brand VARCHAR(100),                    -- NULL = all brands
    model VARCHAR(100),                    -- NULL = all models
    issue_category VARCHAR(100),           -- "Screen", "Battery", etc.

    -- Service info
    name VARCHAR(255) NOT NULL,
    description TEXT,

    -- Pricing
    price_min DECIMAL(10,2),
    price_max DECIMAL(10,2),
    price_display VARCHAR(100),            -- "from 5000₽" or "5000-8000₽"
    currency VARCHAR(3) DEFAULT 'RUB',

    -- Duration
    duration_minutes INT,
    duration_display VARCHAR(50),          -- "1-2 hours"

    -- Availability
    availability VARCHAR(20) DEFAULT 'available',
    -- available, on_order, out_of_stock

    is_active BOOLEAN DEFAULT true,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_t_price_list IS 'Tenant level: Service price list';

CREATE INDEX IF NOT EXISTS idx_elo_t_price_list_tenant ON elo_t_price_list(tenant_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_price_list_search ON elo_t_price_list(tenant_id, brand, model, issue_category);
CREATE INDEX IF NOT EXISTS idx_elo_t_price_list_vertical ON elo_t_price_list(tenant_id, vertical_id);


-- ----------------------------------------------------------------------------
-- 10. elo_t_tasks - Tasks for staff
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_tasks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,

    -- Links
    dialog_id UUID REFERENCES elo_t_dialogs(id),
    client_id UUID REFERENCES elo_t_clients(id),

    -- Assignment
    assignee_id UUID REFERENCES elo_t_operators(id),
    created_by_id UUID REFERENCES elo_t_operators(id),

    -- Content
    title VARCHAR(255) NOT NULL,
    description TEXT,

    -- Type
    task_type VARCHAR(50) DEFAULT 'general',
    -- general, repair, call, delivery, purchase

    -- Timeline
    deadline TIMESTAMPTZ,
    estimated_duration_min INT,

    -- Status
    status VARCHAR(20) DEFAULT 'pending',
    -- pending, in_progress, blocked, completed, cancelled

    -- Priority
    priority VARCHAR(10) DEFAULT 'normal',
    -- low, normal, high, urgent

    -- Hierarchy
    parent_task_id UUID REFERENCES elo_t_tasks(id),

    -- Metadata
    metadata JSONB DEFAULT '{}',

    -- Timestamps
    started_at TIMESTAMPTZ,
    completed_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_t_tasks IS 'Tenant level: Staff tasks';

CREATE INDEX IF NOT EXISTS idx_elo_t_tasks_tenant ON elo_t_tasks(tenant_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_tasks_assignee ON elo_t_tasks(assignee_id, status);
CREATE INDEX IF NOT EXISTS idx_elo_t_tasks_dialog ON elo_t_tasks(dialog_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_tasks_status ON elo_t_tasks(tenant_id, status);
CREATE INDEX IF NOT EXISTS idx_elo_t_tasks_deadline ON elo_t_tasks(tenant_id, deadline) WHERE status IN ('pending', 'in_progress');


-- ----------------------------------------------------------------------------
-- 11. elo_t_ai_extractions - AI extraction results
-- ----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS elo_t_ai_extractions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES elo_t_tenants(id) ON DELETE CASCADE,
    dialog_id UUID NOT NULL REFERENCES elo_t_dialogs(id) ON DELETE CASCADE,
    message_id UUID REFERENCES elo_t_messages(id),

    -- What was extracted
    extraction_type VARCHAR(50) NOT NULL,
    -- device, symptoms, intent, sentiment, entity

    -- Result
    value JSONB NOT NULL,
    confidence DECIMAL(3,2),               -- 0.00 - 1.00

    -- Status
    status VARCHAR(20) DEFAULT 'extracted',
    -- extracted, confirmed, rejected, applied

    confirmed_by UUID REFERENCES elo_t_operators(id),
    confirmed_at TIMESTAMPTZ,

    created_at TIMESTAMPTZ DEFAULT NOW()
);

COMMENT ON TABLE elo_t_ai_extractions IS 'Tenant level: AI extraction results';

CREATE INDEX IF NOT EXISTS idx_elo_t_ai_extractions_dialog ON elo_t_ai_extractions(dialog_id);
CREATE INDEX IF NOT EXISTS idx_elo_t_ai_extractions_type ON elo_t_ai_extractions(tenant_id, extraction_type);


-- ============================================================================
-- Summary: Tenant Level Tables Created
-- ============================================================================
-- elo_t_tenants            - Business accounts
-- elo_t_tenant_verticals   - Vertical assignments
-- elo_t_operators          - Staff
-- elo_t_channel_accounts   - Connected channels
-- elo_t_clients            - Customers
-- elo_t_client_channels    - Customer channel IDs
-- elo_t_dialogs            - Conversations
-- elo_t_messages           - Message history
-- elo_t_price_list         - Service prices
-- elo_t_tasks              - Staff tasks
-- elo_t_ai_extractions     - AI results
-- ============================================================================
