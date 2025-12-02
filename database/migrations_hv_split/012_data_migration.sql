-- ============================================================================
-- H/V SPLIT Migration 012: Data Migration from Public Schema
-- Date: 2025-11-25
-- Description: Migrate existing data from public schema to H/V schemas
-- IMPORTANT: Run this AFTER all structure migrations (001-011)
-- ============================================================================

-- ============================================================================
-- PHASE 1: Migrate Horizontal Core Data
-- ============================================================================

SET search_path TO horizontal, vertical_repair, public;

-- ----------------------------------------------------------------------------
-- 1.1 Migrate tenants
-- ----------------------------------------------------------------------------
INSERT INTO horizontal.tenants (
    id, name, domain, is_active, settings, created_at, updated_at
)
SELECT
    id,
    name,
    domain,
    COALESCE(is_active, true),
    COALESCE(settings, '{}'),
    COALESCE(created_at, now()),
    COALESCE(updated_at, now())
FROM public.tenants
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.tenants ht WHERE ht.id = public.tenants.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 1.1: Tenants migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 1.2 Migrate leads
-- ----------------------------------------------------------------------------
INSERT INTO horizontal.leads (
    id, tenant_id, phone, phone_normalized, first_name, last_name, full_name,
    email, source, metadata, is_active, created_at, updated_at
)
SELECT
    id,
    tenant_id,
    phone,
    phone_normalized,
    first_name,
    last_name,
    full_name,
    email,
    source,
    COALESCE(metadata, '{}'),
    COALESCE(is_active, true),
    COALESCE(created_at, now()),
    COALESCE(updated_at, now())
FROM public.leads
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.leads hl WHERE hl.id = public.leads.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 1.2: Leads migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 1.3 Migrate channels
-- ----------------------------------------------------------------------------
INSERT INTO horizontal.channels (
    id, tenant_id, channel_type, name, whatsapp_phone, whatsapp_id,
    telegram_bot_token, telegram_bot_username, api_key, webhook_url,
    is_active, created_at, updated_at
)
SELECT
    id,
    tenant_id,
    channel_type,
    name,
    whatsapp_phone,
    whatsapp_id,
    telegram_bot_token,
    telegram_bot_username,
    api_key,
    webhook_url,
    COALESCE(is_active, true),
    COALESCE(created_at, now()),
    COALESCE(updated_at, now())
FROM public.channels
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.channels hc WHERE hc.id = public.channels.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 1.3: Channels migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 1.4 Migrate operators
-- ----------------------------------------------------------------------------
INSERT INTO horizontal.operators (
    id, tenant_id, name, phone, phone_normalized, email, role,
    is_active, settings, created_at, updated_at
)
SELECT
    id,
    tenant_id,
    name,
    phone,
    phone_normalized,
    email,
    COALESCE(role, 'operator'),
    COALESCE(is_active, true),
    COALESCE(settings, '{}'),
    COALESCE(created_at, now()),
    COALESCE(updated_at, now())
FROM public.operators
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.operators ho WHERE ho.id = public.operators.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 1.4: Operators migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 1.5 Migrate sessions
-- ----------------------------------------------------------------------------
INSERT INTO horizontal.sessions (
    id, tenant_id, lead_id, channel_id, operator_id, status,
    started_at, ended_at, last_message_at, metadata, created_at
)
SELECT
    id,
    tenant_id,
    lead_id,
    channel_id,
    operator_id,
    COALESCE(status, 'active'),
    started_at,
    ended_at,
    last_message_at,
    COALESCE(metadata, '{}'),
    COALESCE(created_at, now())
FROM public.sessions
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.sessions hs WHERE hs.id = public.sessions.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 1.5: Sessions migrated'; END $$;

-- ============================================================================
-- PHASE 2: Migrate Vertical Repair Dictionaries
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 2.1 Migrate brands
-- ----------------------------------------------------------------------------
INSERT INTO vertical_repair.brands (
    id, tenant_id, name, name_normalized, aliases, logo_url, is_active, "order", created_at
)
SELECT
    id,
    tenant_id,
    name,
    COALESCE(name_normalized, lower(regexp_replace(name, '[^a-zA-Z0-9]', '', 'g'))),
    COALESCE(aliases, '[]'),
    logo_url,
    COALESCE(is_active, true),
    COALESCE("order", 0),
    COALESCE(created_at, now())
FROM public.brands
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.brands vb WHERE vb.id = public.brands.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 2.1: Brands migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 2.2 Migrate device_types
-- ----------------------------------------------------------------------------
INSERT INTO vertical_repair.device_types (
    id, tenant_id, name, icon, "order", is_active, created_at
)
SELECT
    id,
    tenant_id,
    name,
    icon,
    COALESCE("order", 0),
    COALESCE(is_active, true),
    COALESCE(created_at, now())
FROM public.device_types
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.device_types vdt WHERE vdt.id = public.device_types.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 2.2: Device types migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 2.3 Migrate models
-- ----------------------------------------------------------------------------
INSERT INTO vertical_repair.models (
    id, tenant_id, brand_id, device_type_id, name, name_normalized, aliases,
    release_year, specifications, image_url, is_active, "order", created_at
)
SELECT
    id,
    tenant_id,
    brand_id,
    device_type_id,
    name,
    COALESCE(name_normalized, lower(regexp_replace(name, '[^a-zA-Z0-9]', '', 'g'))),
    COALESCE(aliases, '[]'),
    release_year,
    COALESCE(specifications, '{}'),
    image_url,
    COALESCE(is_active, true),
    COALESCE("order", 0),
    COALESCE(created_at, now())
FROM public.models
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.models vm WHERE vm.id = public.models.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 2.3: Models migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 2.4 Migrate repair_categories
-- ----------------------------------------------------------------------------
INSERT INTO vertical_repair.repair_categories (
    id, tenant_id, name, description, icon, "order", is_active, created_at
)
SELECT
    id,
    tenant_id,
    name,
    description,
    icon,
    COALESCE("order", 0),
    COALESCE(is_active, true),
    COALESCE(created_at, now())
FROM public.repair_categories
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.repair_categories vrc WHERE vrc.id = public.repair_categories.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 2.4: Repair categories migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 2.5 Migrate issue_types
-- ----------------------------------------------------------------------------
INSERT INTO vertical_repair.issue_types (
    id, tenant_id, repair_category_id, name, description, symptoms_template,
    default_price_min, default_price_max, default_time_minutes, "order", is_active, created_at
)
SELECT
    id,
    tenant_id,
    repair_category_id,
    name,
    description,
    symptoms_template,
    default_price_min,
    default_price_max,
    default_time_minutes,
    COALESCE("order", 0),
    COALESCE(is_active, true),
    COALESCE(created_at, now())
FROM public.issue_types
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.issue_types vit WHERE vit.id = public.issue_types.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 2.5: Issue types migrated'; END $$;

-- ============================================================================
-- PHASE 3: Migrate Appeal Funnel
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 3.1 Migrate appeal_stages
-- ----------------------------------------------------------------------------
INSERT INTO horizontal.appeal_stages (
    id, tenant_id, name, description, stage_group, "order",
    color, icon, is_final, is_success, is_active, auto_transition_rules, created_at
)
SELECT
    id,
    tenant_id,
    name,
    description,
    stage_group,
    "order",
    color,
    icon,
    COALESCE(is_final, false),
    COALESCE(is_success, false),
    COALESCE(is_active, true),
    auto_transition_rules,
    COALESCE(created_at, now())
FROM public.appeal_stages
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.appeal_stages has WHERE has.id = public.appeal_stages.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 3.1: Appeal stages migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 3.2 Migrate appeals (core data to horizontal)
-- ----------------------------------------------------------------------------

-- First, get the vertical_id for repair vertical
DO $$
DECLARE
    repair_vertical_id uuid;
BEGIN
    -- Get or create repair vertical reference
    SELECT id INTO repair_vertical_id FROM horizontal.tenant_verticals
    WHERE vertical_code = 'repair' LIMIT 1;

    -- If no vertical exists, we'll use NULL (can be updated later)
    IF repair_vertical_id IS NULL THEN
        RAISE NOTICE 'No repair vertical found, appeals will have NULL vertical_id';
    END IF;
END $$;

INSERT INTO horizontal.appeals (
    id, tenant_id, vertical_id, lead_id, channel_id, session_id, operator_id,
    appeal_stage_id, status, priority, source, metadata,
    created_at, updated_at
)
SELECT
    a.id,
    a.tenant_id,
    (SELECT id FROM horizontal.tenant_verticals tv
     WHERE tv.tenant_id = a.tenant_id AND tv.vertical_code = 'repair' LIMIT 1),
    a.lead_id,
    a.channel_id,
    a.session_id,
    a.operator_id,
    a.appeal_stage_id,
    COALESCE(a.status, 'new'),
    COALESCE(a.priority, 'normal'),
    a.source,
    COALESCE(a.metadata, '{}'),
    COALESCE(a.created_at, now()),
    COALESCE(a.updated_at, now())
FROM public.appeals a
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.appeals ha WHERE ha.id = a.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 3.2: Appeals migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 3.3 Migrate appeal_stage_transitions
-- ----------------------------------------------------------------------------
INSERT INTO horizontal.appeal_stage_transitions (
    id, appeal_id, tenant_id, from_stage_id, to_stage_id,
    operator_id, transition_type, reason, created_at
)
SELECT
    id,
    appeal_id,
    tenant_id,
    from_stage_id,
    to_stage_id,
    operator_id,
    COALESCE(transition_type, 'manual'),
    reason,
    COALESCE(created_at, now())
FROM public.appeal_stage_transitions
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.appeal_stage_transitions hast
    WHERE hast.id = public.appeal_stage_transitions.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 3.3: Appeal stage transitions migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 3.4 Migrate messages_history
-- ----------------------------------------------------------------------------
INSERT INTO horizontal.messages_history (
    id, tenant_id, appeal_id, session_id, lead_id, channel_id, operator_id,
    direction, message_type, content, media_url, media_type,
    external_message_id, status, metadata, sent_at, delivered_at, read_at, created_at
)
SELECT
    id,
    tenant_id,
    appeal_id,
    session_id,
    lead_id,
    channel_id,
    operator_id,
    direction,
    COALESCE(message_type, 'text'),
    content,
    media_url,
    media_type,
    external_message_id,
    COALESCE(status, 'sent'),
    COALESCE(metadata, '{}'),
    sent_at,
    delivered_at,
    read_at,
    COALESCE(created_at, now())
FROM public.messages_history
WHERE NOT EXISTS (
    SELECT 1 FROM horizontal.messages_history hmh
    WHERE hmh.id = public.messages_history.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 3.4: Messages history migrated'; END $$;

-- ============================================================================
-- PHASE 4: Migrate Appeal Devices and Repairs
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 4.1 Migrate appeal_devices
-- ----------------------------------------------------------------------------
INSERT INTO vertical_repair.appeal_devices (
    id, appeal_id, tenant_id, device_type_id, brand_id, model_id,
    brand_text, model_text, serial_number, imei, color, condition,
    condition_notes, accessories, photos, notes, "order", created_at, updated_at
)
SELECT
    ad.id,
    ad.appeal_id,
    ad.tenant_id,
    ad.device_type_id,
    ad.brand_id,
    ad.model_id,
    -- Extract text from brand/model if available
    (SELECT name FROM public.brands b WHERE b.id = ad.brand_id),
    (SELECT name FROM public.models m WHERE m.id = ad.model_id),
    ad.serial_number,
    ad.imei,
    ad.color,
    ad.condition,
    NULL, -- condition_notes (new field)
    COALESCE(ad.accessories, '[]'),
    '[]'::jsonb, -- photos (new field)
    ad.notes,
    0, -- order (new field)
    COALESCE(ad.created_at, now()),
    COALESCE(ad.updated_at, now())
FROM public.appeal_devices ad
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.appeal_devices vad WHERE vad.id = ad.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 4.1: Appeal devices migrated'; END $$;

-- ----------------------------------------------------------------------------
-- 4.2 Migrate appeal_repairs
-- ----------------------------------------------------------------------------
INSERT INTO vertical_repair.appeal_repairs (
    id, appeal_device_id, tenant_id, repair_category_id, issue_type_id,
    category_text, issue_text, symptoms, diagnosis, diagnosis_photos,
    parts_owner, parts_used, estimated_cost, final_cost, parts_cost, labor_cost,
    estimated_time_minutes, actual_time_minutes, repair_status,
    warranty_days, warranty_until, notes, started_at, completed_at,
    created_at, updated_at
)
SELECT
    ar.id,
    ar.appeal_device_id,
    ar.tenant_id,
    ar.repair_category_id,
    ar.issue_type_id,
    -- Extract text from category/issue_type
    (SELECT name FROM public.repair_categories rc WHERE rc.id = ar.repair_category_id),
    (SELECT name FROM public.issue_types it WHERE it.id = ar.issue_type_id),
    ar.symptoms,
    ar.diagnosis,
    '[]'::jsonb, -- diagnosis_photos (new field)
    ar.parts_owner,
    '[]'::jsonb, -- parts_used (new field)
    ar.estimated_cost,
    ar.final_cost,
    NULL, -- parts_cost (new field)
    NULL, -- labor_cost (new field)
    ar.estimated_time_minutes,
    NULL, -- actual_time_minutes (new field)
    COALESCE(ar.repair_status, 'pending'),
    NULL, -- warranty_days (new field)
    NULL, -- warranty_until (new field)
    ar.notes,
    NULL, -- started_at (new field)
    ar.completed_at,
    COALESCE(ar.created_at, now()),
    COALESCE(ar.updated_at, now())
FROM public.appeal_repairs ar
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.appeal_repairs var WHERE var.id = ar.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 4.2: Appeal repairs migrated'; END $$;

-- ============================================================================
-- PHASE 5: Create legacy appeal devices for appeals without multi-device data
-- For appeals that don't have appeal_devices records, create them from appeal fields
-- ============================================================================

INSERT INTO vertical_repair.appeal_devices (
    appeal_id, tenant_id, brand_id, model_id, brand_text, model_text,
    "order", created_at, updated_at
)
SELECT
    a.id,
    a.tenant_id,
    a.brand_id,
    a.model_id,
    (SELECT name FROM public.brands b WHERE b.id = a.brand_id),
    (SELECT name FROM public.models m WHERE m.id = a.model_id),
    0,
    a.created_at,
    a.updated_at
FROM public.appeals a
WHERE a.brand_id IS NOT NULL
AND NOT EXISTS (
    SELECT 1 FROM vertical_repair.appeal_devices vad WHERE vad.appeal_id = a.id
);

DO $$ BEGIN RAISE NOTICE 'Phase 5: Legacy appeal devices created'; END $$;

-- ============================================================================
-- PHASE 6: Migrate AI and Voice Data (if exists)
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 6.1 Migrate ai_chat_memory (if table exists)
-- ----------------------------------------------------------------------------
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'ai_chat_memory' AND table_schema = 'public') THEN
        INSERT INTO horizontal.ai_chat_memory (
            id, tenant_id, appeal_id, lead_id, role, content, token_count,
            model_used, created_at
        )
        SELECT
            id, tenant_id, appeal_id, lead_id, role, content, token_count,
            model_used, created_at
        FROM public.ai_chat_memory
        WHERE NOT EXISTS (
            SELECT 1 FROM horizontal.ai_chat_memory ham WHERE ham.id = public.ai_chat_memory.id
        );
        RAISE NOTICE 'Phase 6.1: AI chat memory migrated';
    ELSE
        RAISE NOTICE 'Phase 6.1: ai_chat_memory table not found, skipping';
    END IF;
END $$;

-- ----------------------------------------------------------------------------
-- 6.2 Migrate voice_transcriptions (if table exists)
-- ----------------------------------------------------------------------------
DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'voice_transcriptions' AND table_schema = 'public') THEN
        INSERT INTO horizontal.voice_transcriptions (
            id, tenant_id, appeal_id, message_id, audio_url, audio_duration_seconds,
            transcription, confidence, language, model_used, processing_time_ms,
            status, error_message, created_at
        )
        SELECT
            id, tenant_id, appeal_id, message_id, audio_url, audio_duration_seconds,
            transcription, confidence, language, model_used, processing_time_ms,
            status, error_message, created_at
        FROM public.voice_transcriptions
        WHERE NOT EXISTS (
            SELECT 1 FROM horizontal.voice_transcriptions hvt WHERE hvt.id = public.voice_transcriptions.id
        );
        RAISE NOTICE 'Phase 6.2: Voice transcriptions migrated';
    ELSE
        RAISE NOTICE 'Phase 6.2: voice_transcriptions table not found, skipping';
    END IF;
END $$;

-- ============================================================================
-- PHASE 7: Update sequences and statistics
-- ============================================================================

-- Analyze all new tables for query optimization
ANALYZE horizontal.tenants;
ANALYZE horizontal.leads;
ANALYZE horizontal.channels;
ANALYZE horizontal.operators;
ANALYZE horizontal.sessions;
ANALYZE horizontal.appeals;
ANALYZE horizontal.appeal_stages;
ANALYZE horizontal.appeal_stage_transitions;
ANALYZE horizontal.messages_history;
ANALYZE vertical_repair.brands;
ANALYZE vertical_repair.device_types;
ANALYZE vertical_repair.models;
ANALYZE vertical_repair.repair_categories;
ANALYZE vertical_repair.issue_types;
ANALYZE vertical_repair.appeal_devices;
ANALYZE vertical_repair.appeal_repairs;

DO $$ BEGIN RAISE NOTICE 'Phase 7: Statistics updated'; END $$;

-- ============================================================================
-- Migration complete: 012
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE '============================================';
    RAISE NOTICE 'Migration 012 completed: Data migration from public schema';
    RAISE NOTICE 'All data has been copied to horizontal and vertical_repair schemas';
    RAISE NOTICE 'Original data in public schema is preserved';
    RAISE NOTICE '============================================';
END $$;
