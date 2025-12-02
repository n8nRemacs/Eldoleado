-- Migration 002: Context Fields Management
-- Добавляет управление сбором контекста через конфигурируемые поля
-- Author: Claude Code
-- Date: 2025-11-22

-- =====================================================
-- STEP 1: Create context_fields_config table
-- =====================================================

DO $$
BEGIN
    RAISE NOTICE 'Creating context_fields_config table...';
END $$;

CREATE TABLE IF NOT EXISTS context_fields_config (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id UUID NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    deal_type_id UUID REFERENCES deal_types(id) ON DELETE SET NULL,

    -- Field configuration
    field_name VARCHAR(100) NOT NULL,
    field_label VARCHAR(255) NOT NULL,
    is_required BOOLEAN DEFAULT false,
    collection_order INT NOT NULL,

    -- AI Tool configuration
    tool_name VARCHAR(100),
    prompt_template TEXT,

    -- Validation
    validation_rules JSONB DEFAULT '{}',

    -- Status
    is_active BOOLEAN DEFAULT true,

    -- Timestamps
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    -- Constraints
    CONSTRAINT unique_field_per_tenant_deal_type UNIQUE (tenant_id, deal_type_id, field_name)
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_context_fields_tenant
    ON context_fields_config(tenant_id)
    WHERE is_active = true;

CREATE INDEX IF NOT EXISTS idx_context_fields_deal_type
    ON context_fields_config(deal_type_id)
    WHERE is_active = true;

CREATE INDEX IF NOT EXISTS idx_context_fields_order
    ON context_fields_config(tenant_id, collection_order)
    WHERE is_active = true;

DO $$
BEGIN
    RAISE NOTICE '✅ context_fields_config table created';
END $$;

-- =====================================================
-- STEP 2: Add context_completion_status to appeals
-- =====================================================

DO $$
BEGIN
    RAISE NOTICE 'Adding context_completion_status to appeals...';
END $$;

ALTER TABLE appeals
ADD COLUMN IF NOT EXISTS context_completion_status JSONB DEFAULT '{
    "fields_collected": {},
    "required_fields_count": 0,
    "collected_required_count": 0,
    "is_complete": false,
    "completion_percentage": 0
}'::jsonb;

-- Create index for querying completion status
CREATE INDEX IF NOT EXISTS idx_appeals_context_complete
    ON appeals((context_completion_status->>'is_complete'));

DO $$
BEGIN
    RAISE NOTICE '✅ context_completion_status field added';
END $$;

-- =====================================================
-- STEP 3: Insert default context fields configuration
-- =====================================================

DO $$
DECLARE
    v_tenant_id UUID;
BEGIN
    RAISE NOTICE 'Inserting default context fields configuration...';

    -- Get first tenant (for testing)
    SELECT id INTO v_tenant_id FROM tenants LIMIT 1;

    IF v_tenant_id IS NULL THEN
        RAISE NOTICE '⚠️  No tenants found, skipping default data';
        RETURN;
    END IF;

    -- Insert default fields (applicable to all deal types)
    INSERT INTO context_fields_config (
        tenant_id,
        deal_type_id,
        field_name,
        field_label,
        is_required,
        collection_order,
        tool_name,
        prompt_template
    ) VALUES
    -- Required fields
    (
        v_tenant_id,
        NULL,
        'type',
        'Тип обращения',
        true,
        1,
        'Tool-Type',
        'Узнай у клиента тип обращения: ремонт, консультация, покупка, продажа.'
    ),
    (
        v_tenant_id,
        NULL,
        'model_id',
        'Модель устройства',
        true,
        2,
        'Tool-Model',
        'Узнай точную модель устройства клиента (например: iPhone 14 Pro, Samsung Galaxy S23).'
    ),
    (
        v_tenant_id,
        NULL,
        'repair_type_id',
        'Тип ремонта',
        true,
        3,
        'Tool-Repair',
        'Узнай что именно нужно починить или какая проблема с устройством.'
    ),
    -- Optional fields
    (
        v_tenant_id,
        NULL,
        'parts_owner',
        'Чья запчасть',
        false,
        4,
        'Tool-Parts',
        'Уточни будет ли запчасть наша или клиент принесет свою.'
    )
    ON CONFLICT (tenant_id, deal_type_id, field_name) DO NOTHING;

    RAISE NOTICE '✅ Default context fields inserted for tenant: %', v_tenant_id;
END $$;

-- =====================================================
-- STEP 4: Create helper function for checking context completion
-- =====================================================

DO $$
BEGIN
    RAISE NOTICE 'Creating helper function check_context_completion...';
END $$;

CREATE OR REPLACE FUNCTION check_context_completion(
    p_appeal_id UUID,
    p_tenant_id UUID
) RETURNS JSONB AS $$
DECLARE
    v_appeal RECORD;
    v_required_fields JSONB;
    v_fields_collected JSONB := '{}';
    v_required_count INT := 0;
    v_collected_count INT := 0;
    v_field RECORD;
    v_field_value TEXT;
    v_is_complete BOOLEAN;
    v_result JSONB;
BEGIN
    -- Get appeal
    SELECT * INTO v_appeal FROM appeals WHERE id = p_appeal_id;

    IF NOT FOUND THEN
        RETURN '{"error": "Appeal not found"}'::jsonb;
    END IF;

    -- Get required fields configuration
    FOR v_field IN
        SELECT field_name, field_label, is_required, tool_name
        FROM context_fields_config
        WHERE tenant_id = p_tenant_id
          AND is_active = true
          AND (deal_type_id IS NULL OR deal_type_id = v_appeal.deal_type_id)
        ORDER BY collection_order
    LOOP
        -- Get field value from appeal
        EXECUTE format('SELECT ($1).%I::text', v_field.field_name)
        INTO v_field_value
        USING v_appeal;

        -- Count required fields
        IF v_field.is_required THEN
            v_required_count := v_required_count + 1;
        END IF;

        -- Build fields_collected
        IF v_field_value IS NOT NULL AND v_field_value != '' THEN
            v_fields_collected := v_fields_collected || jsonb_build_object(
                v_field.field_name,
                jsonb_build_object(
                    'value', v_field_value,
                    'collected_at', NOW(),
                    'is_valid', true
                )
            );

            -- Count collected required fields
            IF v_field.is_required THEN
                v_collected_count := v_collected_count + 1;
            END IF;
        ELSE
            v_fields_collected := v_fields_collected || jsonb_build_object(
                v_field.field_name,
                NULL
            );
        END IF;
    END LOOP;

    -- Calculate completion
    v_is_complete := v_collected_count >= v_required_count AND v_required_count > 0;

    -- Build result
    v_result := jsonb_build_object(
        'fields_collected', v_fields_collected,
        'required_fields_count', v_required_count,
        'collected_required_count', v_collected_count,
        'is_complete', v_is_complete,
        'completion_percentage',
            CASE
                WHEN v_required_count > 0 THEN
                    ROUND((v_collected_count::numeric / v_required_count::numeric * 100)::numeric, 2)
                ELSE 0
            END
    );

    -- Update appeal
    UPDATE appeals
    SET context_completion_status = v_result,
        updated_at = NOW()
    WHERE id = p_appeal_id;

    RETURN v_result;
END;
$$ LANGUAGE plpgsql;

DO $$
BEGIN
    RAISE NOTICE '✅ Helper function created';
END $$;

-- =====================================================
-- STEP 5: Create trigger to auto-update context_completion_status
-- =====================================================

DO $$
BEGIN
    RAISE NOTICE 'Creating trigger for auto-update context completion...';
END $$;

CREATE OR REPLACE FUNCTION trigger_update_context_completion()
RETURNS TRIGGER AS $$
BEGIN
    -- Update context_completion_status when appeal fields change
    IF (NEW.type IS DISTINCT FROM OLD.type) OR
       (NEW.model_id IS DISTINCT FROM OLD.model_id) OR
       (NEW.repair_type_id IS DISTINCT FROM OLD.repair_type_id) OR
       (NEW.parts_owner IS DISTINCT FROM OLD.parts_owner) THEN

        NEW.context_completion_status := check_context_completion(NEW.id, NEW.tenant_id);

        -- Auto-update stage if context is complete
        IF (NEW.context_completion_status->>'is_complete')::boolean = true AND
           NEW.stage IN ('Первичный контакт', 'Сбор контекста') THEN
            NEW.stage := 'Обработка заявки';
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Drop trigger if exists
DROP TRIGGER IF EXISTS trg_appeals_context_completion ON appeals;

-- Create trigger
CREATE TRIGGER trg_appeals_context_completion
    BEFORE UPDATE ON appeals
    FOR EACH ROW
    EXECUTE FUNCTION trigger_update_context_completion();

DO $$
BEGIN
    RAISE NOTICE '✅ Trigger created';
END $$;

-- =====================================================
-- STEP 6: Update existing appeals with initial status
-- =====================================================

DO $$
DECLARE
    v_updated_count INT;
BEGIN
    RAISE NOTICE 'Updating existing appeals with initial context_completion_status...';

    -- Update appeals that don't have proper context_completion_status
    WITH updated AS (
        UPDATE appeals
        SET context_completion_status = check_context_completion(id, tenant_id)
        WHERE context_completion_status->>'fields_collected' IS NULL
           OR context_completion_status = '{}'::jsonb
        RETURNING id
    )
    SELECT COUNT(*) INTO v_updated_count FROM updated;

    RAISE NOTICE '✅ Updated % appeals', v_updated_count;
END $$;

-- =====================================================
-- VERIFICATION
-- =====================================================

DO $$
DECLARE
    v_config_count INT;
    v_appeals_with_status INT;
BEGIN
    RAISE NOTICE '';
    RAISE NOTICE '=== MIGRATION 002 VERIFICATION ===';

    -- Check context_fields_config
    SELECT COUNT(*) INTO v_config_count FROM context_fields_config;
    RAISE NOTICE '✅ context_fields_config: % rows', v_config_count;

    -- Check appeals with context_completion_status
    SELECT COUNT(*) INTO v_appeals_with_status
    FROM appeals
    WHERE context_completion_status IS NOT NULL;
    RAISE NOTICE '✅ appeals with context_completion_status: % rows', v_appeals_with_status;

    RAISE NOTICE '';
    RAISE NOTICE '🎉 Migration 002 completed successfully!';
    RAISE NOTICE '';
END $$;
