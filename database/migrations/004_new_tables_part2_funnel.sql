-- ============================================================================
-- Migration 004 Part 2: Appeal Funnel (2 tables)
-- Date: 2025-11-23
-- Description: Detailed 9-stage funnel and transition history
-- ============================================================================

-- ============================================================================
-- 1. appeal_stages — Этапы воронки (9 этапов)
-- ============================================================================

CREATE TABLE IF NOT EXISTS appeal_stages (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    name varchar(255) NOT NULL,
    description text,
    stage_group varchar(50) NOT NULL,
    "order" int NOT NULL,
    color varchar(20),
    icon varchar(255),
    is_final boolean DEFAULT false,
    is_success boolean DEFAULT false,
    is_active boolean DEFAULT true,
    auto_transition_rules jsonb,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_stage_name UNIQUE (tenant_id, name),
    CONSTRAINT unique_tenant_stage_order UNIQUE (tenant_id, "order"),
    CONSTRAINT chk_stage_group CHECK (stage_group IN ('initial', 'processing', 'confirmed', 'completed', 'cancelled'))
);

CREATE INDEX IF NOT EXISTS idx_appeal_stages_tenant ON appeal_stages(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_stages_tenant_active ON appeal_stages(tenant_id, is_active);
CREATE INDEX IF NOT EXISTS idx_appeal_stages_order ON appeal_stages("order");

COMMENT ON TABLE appeal_stages IS 'Детальная 9-этапная воронка обработки заявок';

-- ============================================================================
-- 2. appeal_stage_transitions — История переходов по воронке
-- ============================================================================

CREATE TABLE IF NOT EXISTS appeal_stage_transitions (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    appeal_id uuid NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    from_stage_id uuid REFERENCES appeal_stages(id),
    to_stage_id uuid NOT NULL REFERENCES appeal_stages(id),
    operator_id uuid REFERENCES operators(id),
    transition_type varchar(50) DEFAULT 'manual',
    reason text,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_transition_type CHECK (transition_type IN ('manual', 'automatic', 'system'))
);

CREATE INDEX IF NOT EXISTS idx_stage_transitions_appeal ON appeal_stage_transitions(appeal_id);
CREATE INDEX IF NOT EXISTS idx_stage_transitions_tenant ON appeal_stage_transitions(tenant_id);
CREATE INDEX IF NOT EXISTS idx_stage_transitions_created ON appeal_stage_transitions(created_at);

COMMENT ON TABLE appeal_stage_transitions IS 'История перемещения заявки по этапам воронки';

-- ============================================================================
-- Add appeal_stage_id to appeals table
-- ============================================================================

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'appeals' AND column_name = 'appeal_stage_id'
    ) THEN
        ALTER TABLE appeals ADD COLUMN appeal_stage_id uuid REFERENCES appeal_stages(id);
        CREATE INDEX IF NOT EXISTS idx_appeals_stage ON appeals(appeal_stage_id);
    END IF;
END $$;

-- ============================================================================
-- Insert default 9 stages for all tenants
-- ============================================================================

INSERT INTO appeal_stages (tenant_id, name, description, stage_group, "order", color, icon, is_final, is_success)
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
FROM tenants t
CROSS JOIN (
    VALUES
        ('New Empty', 'Минимальные данные, клиент проявил интерес', 'initial', 1, '#9CA3AF', '📝', false, false),
        ('Сбор информации', 'Уточнение модели, поломки, деталей', 'initial', 2, '#3B82F6', '🔍', false, false),
        ('Информация собрана', 'Все обязательные поля заполнены', 'processing', 3, '#8B5CF6', '✅', false, false),
        ('Цена отправлена', 'Предложение отправлено клиенту', 'processing', 4, '#F59E0B', '💰', false, false),
        ('Клиент подтвердил', 'Клиент согласился на ремонт', 'confirmed', 5, '#10B981', '👍', false, false),
        ('QR-код отправлен', 'Клиент получил код для визита', 'confirmed', 6, '#06B6D4', '📱', false, false),
        ('Клиент пришёл', 'Мастер отсканировал QR', 'confirmed', 7, '#14B8A6', '🚶', false, false),
        ('Ремонт выполнен', 'Ремонт завершён успешно', 'completed', 8, '#22C55E', '🎉', true, true),
        ('Отказ/невыкуп/спам', 'Заявка закрыта без результата', 'cancelled', 9, '#EF4444', '❌', true, false)
) AS s(name, description, stage_group, order_num, color, icon, is_final, is_success)
WHERE NOT EXISTS (
    SELECT 1 FROM appeal_stages ast
    WHERE ast.tenant_id = t.id AND ast.name = s.name
);

-- ============================================================================
-- Create mapping from old stages to new stages
-- ============================================================================

DO $$
DECLARE
    tenant_rec RECORD;
    old_stage varchar;
    new_stage_id uuid;
BEGIN
    -- Для каждого тенанта обновляем старые заявки
    FOR tenant_rec IN SELECT id FROM tenants LOOP
        -- Маппинг старых этапов на новые
        -- 'Первичный контакт' → 'New Empty' (order 1)
        SELECT id INTO new_stage_id
        FROM appeal_stages
        WHERE tenant_id = tenant_rec.id AND "order" = 1
        LIMIT 1;

        IF new_stage_id IS NOT NULL THEN
            UPDATE appeals
            SET appeal_stage_id = new_stage_id
            WHERE tenant_id = tenant_rec.id
              AND stage = 'Первичный контакт'
              AND appeal_stage_id IS NULL;
        END IF;

        -- 'Сбор информации' → 'Сбор информации' (order 2)
        SELECT id INTO new_stage_id
        FROM appeal_stages
        WHERE tenant_id = tenant_rec.id AND "order" = 2
        LIMIT 1;

        IF new_stage_id IS NOT NULL THEN
            UPDATE appeals
            SET appeal_stage_id = new_stage_id
            WHERE tenant_id = tenant_rec.id
              AND stage IN ('Сбор информации', 'Требуется уточнение')
              AND appeal_stage_id IS NULL;
        END IF;

        -- 'Цена отправлена' → 'Цена отправлена' (order 4)
        SELECT id INTO new_stage_id
        FROM appeal_stages
        WHERE tenant_id = tenant_rec.id AND "order" = 4
        LIMIT 1;

        IF new_stage_id IS NOT NULL THEN
            UPDATE appeals
            SET appeal_stage_id = new_stage_id
            WHERE tenant_id = tenant_rec.id
              AND stage = 'Цена отправлена'
              AND appeal_stage_id IS NULL;
        END IF;

        -- 'В работе' → 'Информация собрана' (order 3)
        SELECT id INTO new_stage_id
        FROM appeal_stages
        WHERE tenant_id = tenant_rec.id AND "order" = 3
        LIMIT 1;

        IF new_stage_id IS NOT NULL THEN
            UPDATE appeals
            SET appeal_stage_id = new_stage_id
            WHERE tenant_id = tenant_rec.id
              AND stage = 'В работе'
              AND appeal_stage_id IS NULL;
        END IF;

        -- 'Завершено' → 'Ремонт выполнен' (order 8)
        SELECT id INTO new_stage_id
        FROM appeal_stages
        WHERE tenant_id = tenant_rec.id AND "order" = 8
        LIMIT 1;

        IF new_stage_id IS NOT NULL THEN
            UPDATE appeals
            SET appeal_stage_id = new_stage_id
            WHERE tenant_id = tenant_rec.id
              AND stage = 'Завершено'
              AND appeal_stage_id IS NULL;
        END IF;

        -- 'Отменено' → 'Отказ/невыкуп/спам' (order 9)
        SELECT id INTO new_stage_id
        FROM appeal_stages
        WHERE tenant_id = tenant_rec.id AND "order" = 9
        LIMIT 1;

        IF new_stage_id IS NOT NULL THEN
            UPDATE appeals
            SET appeal_stage_id = new_stage_id
            WHERE tenant_id = tenant_rec.id
              AND stage = 'Отменено'
              AND appeal_stage_id IS NULL;
        END IF;
    END LOOP;

    RAISE NOTICE 'Old stages migrated to new appeal_stages';
END $$;

-- ============================================================================
-- Migration complete: Part 2
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 004 Part 2 completed successfully: Appeal Funnel (2 tables)';
END $$;
