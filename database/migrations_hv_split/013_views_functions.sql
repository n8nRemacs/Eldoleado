-- ============================================================================
-- H/V SPLIT Migration 013: Views and Functions
-- Date: 2025-11-25
-- Description: Helper views and functions for H/V architecture
-- ============================================================================

SET search_path TO horizontal, vertical_repair, public;

-- ============================================================================
-- PART 1: HORIZONTAL VIEWS
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1.1 v_appeals_full - Complete appeal view with all related data
-- ----------------------------------------------------------------------------
CREATE OR REPLACE VIEW horizontal.v_appeals_full AS
SELECT
    a.id,
    a.tenant_id,
    a.vertical_id,
    a.lead_id,
    a.channel_id,
    a.session_id,
    a.operator_id,
    a.appeal_stage_id,
    a.status,
    a.priority,
    a.source,
    a.metadata,
    a.created_at,
    a.updated_at,

    -- Lead info
    l.phone AS lead_phone,
    l.full_name AS lead_name,
    l.email AS lead_email,

    -- Channel info
    c.channel_type,
    c.name AS channel_name,

    -- Operator info
    o.name AS operator_name,

    -- Stage info
    s.name AS stage_name,
    s.stage_group,
    s.color AS stage_color,
    s.is_final,
    s.is_success,

    -- Vertical info
    tv.vertical_code,
    tv.vertical_name

FROM horizontal.appeals a
LEFT JOIN horizontal.leads l ON a.lead_id = l.id
LEFT JOIN horizontal.channels c ON a.channel_id = c.id
LEFT JOIN horizontal.operators o ON a.operator_id = o.id
LEFT JOIN horizontal.appeal_stages s ON a.appeal_stage_id = s.id
LEFT JOIN horizontal.tenant_verticals tv ON a.vertical_id = tv.id;

COMMENT ON VIEW horizontal.v_appeals_full IS 'Complete appeal view with lead, channel, operator, stage info';

-- ----------------------------------------------------------------------------
-- 1.2 v_funnel_stats - Funnel statistics per tenant
-- ----------------------------------------------------------------------------
CREATE OR REPLACE VIEW horizontal.v_funnel_stats AS
SELECT
    a.tenant_id,
    s.id AS stage_id,
    s.name AS stage_name,
    s."order" AS stage_order,
    s.stage_group,
    s.color,
    COUNT(a.id) AS appeal_count,
    COUNT(a.id) FILTER (WHERE a.created_at >= CURRENT_DATE - INTERVAL '7 days') AS last_7_days,
    COUNT(a.id) FILTER (WHERE a.created_at >= CURRENT_DATE - INTERVAL '30 days') AS last_30_days
FROM horizontal.appeal_stages s
LEFT JOIN horizontal.appeals a ON a.appeal_stage_id = s.id AND a.tenant_id = s.tenant_id
WHERE s.is_active = true
GROUP BY a.tenant_id, s.id, s.name, s."order", s.stage_group, s.color
ORDER BY s."order";

COMMENT ON VIEW horizontal.v_funnel_stats IS 'Funnel statistics grouped by stage';

-- ----------------------------------------------------------------------------
-- 1.3 v_operator_workload - Operator workload statistics
-- ----------------------------------------------------------------------------
CREATE OR REPLACE VIEW horizontal.v_operator_workload AS
SELECT
    o.id AS operator_id,
    o.tenant_id,
    o.name AS operator_name,
    o.role,
    COUNT(a.id) FILTER (WHERE a.status NOT IN ('completed', 'cancelled')) AS active_appeals,
    COUNT(a.id) FILTER (WHERE a.created_at >= CURRENT_DATE) AS today_appeals,
    COUNT(a.id) FILTER (WHERE a.status = 'completed' AND a.updated_at >= CURRENT_DATE - INTERVAL '7 days') AS completed_7_days,
    MAX(a.created_at) AS last_appeal_at
FROM horizontal.operators o
LEFT JOIN horizontal.appeals a ON a.operator_id = o.id
WHERE o.is_active = true
GROUP BY o.id, o.tenant_id, o.name, o.role;

COMMENT ON VIEW horizontal.v_operator_workload IS 'Operator workload and performance metrics';

-- ============================================================================
-- PART 2: VERTICAL REPAIR VIEWS
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 2.1 v_repair_appeals_full - Complete repair appeal view
-- ----------------------------------------------------------------------------
CREATE OR REPLACE VIEW vertical_repair.v_repair_appeals_full AS
SELECT
    a.id AS appeal_id,
    a.tenant_id,
    a.lead_id,
    a.status AS appeal_status,
    a.created_at AS appeal_created_at,

    -- Lead info
    l.phone AS lead_phone,
    l.full_name AS lead_name,

    -- Device info
    ad.id AS device_id,
    ad.brand_id,
    ad.model_id,
    COALESCE(ad.brand_text, b.name) AS brand_name,
    COALESCE(ad.model_text, m.name) AS model_name,
    ad.imei,
    ad.serial_number,
    ad.color,
    ad.condition,

    -- Repair info
    ar.id AS repair_id,
    ar.repair_category_id,
    ar.issue_type_id,
    COALESCE(ar.category_text, rc.name) AS category_name,
    COALESCE(ar.issue_text, it.name) AS issue_name,
    ar.symptoms,
    ar.diagnosis,
    ar.estimated_cost,
    ar.final_cost,
    ar.repair_status,
    ar.warranty_days

FROM horizontal.appeals a
JOIN horizontal.leads l ON a.lead_id = l.id
LEFT JOIN vertical_repair.appeal_devices ad ON ad.appeal_id = a.id
LEFT JOIN vertical_repair.brands b ON ad.brand_id = b.id
LEFT JOIN vertical_repair.models m ON ad.model_id = m.id
LEFT JOIN vertical_repair.appeal_repairs ar ON ar.appeal_device_id = ad.id
LEFT JOIN vertical_repair.repair_categories rc ON ar.repair_category_id = rc.id
LEFT JOIN vertical_repair.issue_types it ON ar.issue_type_id = it.id;

COMMENT ON VIEW vertical_repair.v_repair_appeals_full IS 'Complete repair appeal view with device and repair details';

-- ----------------------------------------------------------------------------
-- 2.2 v_brand_model_stats - Brand/Model popularity statistics
-- ----------------------------------------------------------------------------
CREATE OR REPLACE VIEW vertical_repair.v_brand_model_stats AS
SELECT
    ad.tenant_id,
    b.id AS brand_id,
    b.name AS brand_name,
    m.id AS model_id,
    m.name AS model_name,
    COUNT(DISTINCT ad.appeal_id) AS appeal_count,
    COUNT(ar.id) AS repair_count,
    AVG(ar.final_cost) AS avg_repair_cost
FROM vertical_repair.appeal_devices ad
JOIN vertical_repair.brands b ON ad.brand_id = b.id
LEFT JOIN vertical_repair.models m ON ad.model_id = m.id
LEFT JOIN vertical_repair.appeal_repairs ar ON ar.appeal_device_id = ad.id
GROUP BY ad.tenant_id, b.id, b.name, m.id, m.name;

COMMENT ON VIEW vertical_repair.v_brand_model_stats IS 'Brand and model popularity statistics';

-- ----------------------------------------------------------------------------
-- 2.3 v_repair_category_stats - Repair category statistics
-- ----------------------------------------------------------------------------
CREATE OR REPLACE VIEW vertical_repair.v_repair_category_stats AS
SELECT
    ar.tenant_id,
    rc.id AS category_id,
    rc.name AS category_name,
    rc.icon,
    COUNT(ar.id) AS repair_count,
    COUNT(ar.id) FILTER (WHERE ar.repair_status = 'completed') AS completed_count,
    AVG(ar.final_cost) AS avg_cost,
    AVG(ar.actual_time_minutes) AS avg_time_minutes
FROM vertical_repair.repair_categories rc
LEFT JOIN vertical_repair.appeal_repairs ar ON ar.repair_category_id = rc.id
WHERE rc.is_active = true
GROUP BY ar.tenant_id, rc.id, rc.name, rc.icon;

COMMENT ON VIEW vertical_repair.v_repair_category_stats IS 'Repair category statistics';

-- ============================================================================
-- PART 3: HORIZONTAL FUNCTIONS
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 3.1 fn_get_next_stage - Get next funnel stage
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION horizontal.fn_get_next_stage(
    p_tenant_id uuid,
    p_current_stage_id uuid
) RETURNS uuid AS $$
DECLARE
    v_current_order int;
    v_next_stage_id uuid;
BEGIN
    -- Get current stage order
    SELECT "order" INTO v_current_order
    FROM horizontal.appeal_stages
    WHERE id = p_current_stage_id AND tenant_id = p_tenant_id;

    -- Get next stage
    SELECT id INTO v_next_stage_id
    FROM horizontal.appeal_stages
    WHERE tenant_id = p_tenant_id
      AND "order" > v_current_order
      AND is_active = true
    ORDER BY "order"
    LIMIT 1;

    RETURN v_next_stage_id;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION horizontal.fn_get_next_stage IS 'Get next funnel stage for an appeal';

-- ----------------------------------------------------------------------------
-- 3.2 fn_move_appeal_stage - Move appeal to new stage with history
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION horizontal.fn_move_appeal_stage(
    p_appeal_id uuid,
    p_to_stage_id uuid,
    p_operator_id uuid DEFAULT NULL,
    p_transition_type varchar DEFAULT 'manual',
    p_reason text DEFAULT NULL
) RETURNS boolean AS $$
DECLARE
    v_from_stage_id uuid;
    v_tenant_id uuid;
BEGIN
    -- Get current stage and tenant
    SELECT appeal_stage_id, tenant_id
    INTO v_from_stage_id, v_tenant_id
    FROM horizontal.appeals
    WHERE id = p_appeal_id;

    -- Skip if same stage
    IF v_from_stage_id = p_to_stage_id THEN
        RETURN false;
    END IF;

    -- Update appeal
    UPDATE horizontal.appeals
    SET appeal_stage_id = p_to_stage_id,
        updated_at = now()
    WHERE id = p_appeal_id;

    -- Record transition
    INSERT INTO horizontal.appeal_stage_transitions (
        appeal_id, tenant_id, from_stage_id, to_stage_id,
        operator_id, transition_type, reason
    ) VALUES (
        p_appeal_id, v_tenant_id, v_from_stage_id, p_to_stage_id,
        p_operator_id, p_transition_type, p_reason
    );

    RETURN true;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION horizontal.fn_move_appeal_stage IS 'Move appeal to new stage and record transition';

-- ----------------------------------------------------------------------------
-- 3.3 fn_get_or_create_lead - Get existing lead or create new
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION horizontal.fn_get_or_create_lead(
    p_tenant_id uuid,
    p_phone varchar,
    p_name varchar DEFAULT NULL,
    p_source varchar DEFAULT NULL
) RETURNS uuid AS $$
DECLARE
    v_lead_id uuid;
    v_phone_normalized varchar;
BEGIN
    -- Normalize phone
    v_phone_normalized := regexp_replace(p_phone, '[^0-9+]', '', 'g');

    -- Try to find existing lead
    SELECT id INTO v_lead_id
    FROM horizontal.leads
    WHERE tenant_id = p_tenant_id
      AND (phone_normalized = v_phone_normalized OR phone = p_phone)
    LIMIT 1;

    -- Create if not found
    IF v_lead_id IS NULL THEN
        INSERT INTO horizontal.leads (tenant_id, phone, phone_normalized, full_name, source)
        VALUES (p_tenant_id, p_phone, v_phone_normalized, p_name, p_source)
        RETURNING id INTO v_lead_id;
    ELSE
        -- Update name if provided and current is empty
        UPDATE horizontal.leads
        SET full_name = COALESCE(full_name, p_name),
            updated_at = now()
        WHERE id = v_lead_id AND full_name IS NULL AND p_name IS NOT NULL;
    END IF;

    RETURN v_lead_id;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION horizontal.fn_get_or_create_lead IS 'Get existing lead by phone or create new one';

-- ============================================================================
-- PART 4: VERTICAL REPAIR FUNCTIONS
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 4.1 fn_find_brand - Find brand by name with fuzzy matching
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION vertical_repair.fn_find_brand(
    p_tenant_id uuid,
    p_brand_text varchar
) RETURNS uuid AS $$
DECLARE
    v_brand_id uuid;
    v_search_text varchar;
BEGIN
    -- Normalize search text
    v_search_text := lower(trim(p_brand_text));

    -- Try exact match first
    SELECT id INTO v_brand_id
    FROM vertical_repair.brands
    WHERE tenant_id = p_tenant_id
      AND is_active = true
      AND (lower(name) = v_search_text OR name_normalized = v_search_text)
    LIMIT 1;

    IF v_brand_id IS NOT NULL THEN
        RETURN v_brand_id;
    END IF;

    -- Try alias match
    SELECT id INTO v_brand_id
    FROM vertical_repair.brands
    WHERE tenant_id = p_tenant_id
      AND is_active = true
      AND aliases @> to_jsonb(v_search_text)
    LIMIT 1;

    IF v_brand_id IS NOT NULL THEN
        RETURN v_brand_id;
    END IF;

    -- Try partial match
    SELECT id INTO v_brand_id
    FROM vertical_repair.brands
    WHERE tenant_id = p_tenant_id
      AND is_active = true
      AND (lower(name) LIKE '%' || v_search_text || '%'
           OR v_search_text LIKE '%' || lower(name) || '%')
    ORDER BY length(name)
    LIMIT 1;

    RETURN v_brand_id;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION vertical_repair.fn_find_brand IS 'Find brand by name with fuzzy matching';

-- ----------------------------------------------------------------------------
-- 4.2 fn_find_model - Find model by name with fuzzy matching
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION vertical_repair.fn_find_model(
    p_tenant_id uuid,
    p_brand_id uuid,
    p_model_text varchar
) RETURNS uuid AS $$
DECLARE
    v_model_id uuid;
    v_search_text varchar;
BEGIN
    -- Normalize search text
    v_search_text := lower(trim(p_model_text));

    -- Try exact match first
    SELECT id INTO v_model_id
    FROM vertical_repair.models
    WHERE tenant_id = p_tenant_id
      AND (p_brand_id IS NULL OR brand_id = p_brand_id)
      AND is_active = true
      AND (lower(name) = v_search_text OR name_normalized = v_search_text)
    LIMIT 1;

    IF v_model_id IS NOT NULL THEN
        RETURN v_model_id;
    END IF;

    -- Try alias match
    SELECT id INTO v_model_id
    FROM vertical_repair.models
    WHERE tenant_id = p_tenant_id
      AND (p_brand_id IS NULL OR brand_id = p_brand_id)
      AND is_active = true
      AND aliases @> to_jsonb(v_search_text)
    LIMIT 1;

    IF v_model_id IS NOT NULL THEN
        RETURN v_model_id;
    END IF;

    -- Try partial match (model names often contain numbers)
    SELECT id INTO v_model_id
    FROM vertical_repair.models
    WHERE tenant_id = p_tenant_id
      AND (p_brand_id IS NULL OR brand_id = p_brand_id)
      AND is_active = true
      AND (lower(name) LIKE '%' || v_search_text || '%'
           OR v_search_text LIKE '%' || lower(name) || '%')
    ORDER BY
        CASE WHEN lower(name) = v_search_text THEN 0 ELSE 1 END,
        length(name)
    LIMIT 1;

    RETURN v_model_id;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION vertical_repair.fn_find_model IS 'Find model by name with fuzzy matching';

-- ----------------------------------------------------------------------------
-- 4.3 fn_get_repair_price - Get estimated repair price
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION vertical_repair.fn_get_repair_price(
    p_tenant_id uuid,
    p_brand_id uuid,
    p_model_id uuid,
    p_repair_category_id uuid,
    p_issue_type_id uuid
) RETURNS TABLE (
    min_price numeric,
    max_price numeric,
    default_price numeric,
    estimated_minutes int
) AS $$
BEGIN
    -- Try exact match first
    RETURN QUERY
    SELECT rp.min_price, rp.max_price, rp.default_price, rp.estimated_minutes
    FROM vertical_repair.repair_prices rp
    WHERE rp.tenant_id = p_tenant_id
      AND rp.brand_id = p_brand_id
      AND rp.model_id = p_model_id
      AND rp.repair_category_id = p_repair_category_id
      AND (p_issue_type_id IS NULL OR rp.issue_type_id = p_issue_type_id)
      AND rp.is_active = true
    LIMIT 1;

    IF FOUND THEN
        RETURN;
    END IF;

    -- Try brand-only match
    RETURN QUERY
    SELECT rp.min_price, rp.max_price, rp.default_price, rp.estimated_minutes
    FROM vertical_repair.repair_prices rp
    WHERE rp.tenant_id = p_tenant_id
      AND rp.brand_id = p_brand_id
      AND rp.model_id IS NULL
      AND rp.repair_category_id = p_repair_category_id
      AND rp.is_active = true
    LIMIT 1;

    IF FOUND THEN
        RETURN;
    END IF;

    -- Fallback to issue_type defaults
    RETURN QUERY
    SELECT it.default_price_min, it.default_price_max, it.default_price_min, it.default_time_minutes
    FROM vertical_repair.issue_types it
    WHERE it.id = p_issue_type_id
      AND it.tenant_id = p_tenant_id
    LIMIT 1;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION vertical_repair.fn_get_repair_price IS 'Get estimated repair price based on device and issue';

-- ----------------------------------------------------------------------------
-- 4.4 fn_calculate_appeal_total - Calculate total cost for appeal
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION vertical_repair.fn_calculate_appeal_total(
    p_appeal_id uuid
) RETURNS TABLE (
    device_count int,
    repair_count int,
    estimated_total numeric,
    final_total numeric
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        COUNT(DISTINCT ad.id)::int AS device_count,
        COUNT(ar.id)::int AS repair_count,
        COALESCE(SUM(ar.estimated_cost), 0) AS estimated_total,
        COALESCE(SUM(ar.final_cost), 0) AS final_total
    FROM vertical_repair.appeal_devices ad
    LEFT JOIN vertical_repair.appeal_repairs ar ON ar.appeal_device_id = ad.id
    WHERE ad.appeal_id = p_appeal_id;
END;
$$ LANGUAGE plpgsql;

COMMENT ON FUNCTION vertical_repair.fn_calculate_appeal_total IS 'Calculate total cost for all devices/repairs in appeal';

-- ============================================================================
-- PART 5: TRIGGERS
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 5.1 Trigger: Update updated_at timestamp
-- ----------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION horizontal.fn_update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Apply to horizontal tables
DO $$
DECLARE
    t_name text;
BEGIN
    FOR t_name IN
        SELECT table_name FROM information_schema.tables
        WHERE table_schema = 'horizontal'
        AND table_type = 'BASE TABLE'
        AND EXISTS (
            SELECT 1 FROM information_schema.columns
            WHERE table_schema = 'horizontal'
            AND table_name = tables.table_name
            AND column_name = 'updated_at'
        )
    LOOP
        EXECUTE format(
            'DROP TRIGGER IF EXISTS trg_update_timestamp ON horizontal.%I',
            t_name
        );
        EXECUTE format(
            'CREATE TRIGGER trg_update_timestamp BEFORE UPDATE ON horizontal.%I
             FOR EACH ROW EXECUTE FUNCTION horizontal.fn_update_timestamp()',
            t_name
        );
    END LOOP;
END $$;

-- Apply to vertical_repair tables
DO $$
DECLARE
    t_name text;
BEGIN
    FOR t_name IN
        SELECT table_name FROM information_schema.tables
        WHERE table_schema = 'vertical_repair'
        AND table_type = 'BASE TABLE'
        AND EXISTS (
            SELECT 1 FROM information_schema.columns
            WHERE table_schema = 'vertical_repair'
            AND table_name = tables.table_name
            AND column_name = 'updated_at'
        )
    LOOP
        EXECUTE format(
            'DROP TRIGGER IF EXISTS trg_update_timestamp ON vertical_repair.%I',
            t_name
        );
        EXECUTE format(
            'CREATE TRIGGER trg_update_timestamp BEFORE UPDATE ON vertical_repair.%I
             FOR EACH ROW EXECUTE FUNCTION horizontal.fn_update_timestamp()',
            t_name
        );
    END LOOP;
END $$;

-- ============================================================================
-- PART 6: COMPATIBILITY VIEWS (for existing code)
-- ============================================================================

-- Create views in public schema that map to new H/V tables
-- This allows existing code to work without modification

CREATE OR REPLACE VIEW public.tenants AS SELECT * FROM horizontal.tenants;
CREATE OR REPLACE VIEW public.leads AS SELECT * FROM horizontal.leads;
CREATE OR REPLACE VIEW public.channels AS SELECT * FROM horizontal.channels;
CREATE OR REPLACE VIEW public.operators AS SELECT * FROM horizontal.operators;
CREATE OR REPLACE VIEW public.sessions AS SELECT * FROM horizontal.sessions;
CREATE OR REPLACE VIEW public.appeals AS SELECT * FROM horizontal.appeals;
CREATE OR REPLACE VIEW public.appeal_stages AS SELECT * FROM horizontal.appeal_stages;
CREATE OR REPLACE VIEW public.messages_history AS SELECT * FROM horizontal.messages_history;

CREATE OR REPLACE VIEW public.brands AS SELECT * FROM vertical_repair.brands;
CREATE OR REPLACE VIEW public.device_types AS SELECT * FROM vertical_repair.device_types;
CREATE OR REPLACE VIEW public.models AS SELECT * FROM vertical_repair.models;
CREATE OR REPLACE VIEW public.repair_categories AS SELECT * FROM vertical_repair.repair_categories;
CREATE OR REPLACE VIEW public.issue_types AS SELECT * FROM vertical_repair.issue_types;
CREATE OR REPLACE VIEW public.appeal_devices AS SELECT * FROM vertical_repair.appeal_devices;
CREATE OR REPLACE VIEW public.appeal_repairs AS SELECT * FROM vertical_repair.appeal_repairs;

COMMENT ON VIEW public.tenants IS 'Compatibility view - maps to horizontal.tenants';
COMMENT ON VIEW public.brands IS 'Compatibility view - maps to vertical_repair.brands';

-- ============================================================================
-- Migration complete: 013
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE '============================================';
    RAISE NOTICE 'Migration 013 completed: Views and Functions';
    RAISE NOTICE 'Created:';
    RAISE NOTICE '  - 3 horizontal views (appeals_full, funnel_stats, operator_workload)';
    RAISE NOTICE '  - 3 vertical repair views (repair_appeals_full, brand_model_stats, category_stats)';
    RAISE NOTICE '  - 3 horizontal functions (get_next_stage, move_appeal_stage, get_or_create_lead)';
    RAISE NOTICE '  - 4 vertical repair functions (find_brand, find_model, get_repair_price, calculate_total)';
    RAISE NOTICE '  - Auto-update timestamp triggers';
    RAISE NOTICE '  - Compatibility views in public schema';
    RAISE NOTICE '============================================';
END $$;
