-- ============================================================================
-- Migration 003 Part 1: Multi-Appeals Support (3 tables)
-- Date: 2025-11-23
-- Description: Tables for multi-device and multi-repair appeals
-- ============================================================================

-- ============================================================================
-- 1. repair_categories — Категории ремонта
-- ============================================================================

CREATE TABLE IF NOT EXISTS repair_categories (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    name varchar(255) NOT NULL,
    description text,
    icon varchar(255),
    "order" int DEFAULT 0,
    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_repair_category_name UNIQUE (tenant_id, name)
);

CREATE INDEX IF NOT EXISTS idx_repair_categories_tenant ON repair_categories(tenant_id);
CREATE INDEX IF NOT EXISTS idx_repair_categories_tenant_active ON repair_categories(tenant_id, is_active);

COMMENT ON TABLE repair_categories IS 'Справочник категорий ремонта (Дисплей, Корпус, Аккумулятор и т.д.)';

-- ============================================================================
-- 2. appeal_devices — Устройства в заявке
-- ============================================================================

CREATE TABLE IF NOT EXISTS appeal_devices (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    appeal_id uuid NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    device_type_id uuid REFERENCES device_types(id),
    brand_id uuid NOT NULL REFERENCES brands(id),
    model_id uuid REFERENCES models(id),
    serial_number varchar(255),
    imei varchar(255),
    color varchar(100),
    condition varchar(100),
    accessories jsonb DEFAULT '[]',
    notes text,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_appeal_devices_appeal ON appeal_devices(appeal_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_tenant ON appeal_devices(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_brand_model ON appeal_devices(brand_id, model_id);

COMMENT ON TABLE appeal_devices IS 'Устройства в заявке (для мультизаявок)';
COMMENT ON COLUMN appeal_devices.accessories IS 'Комплектация в формате JSON array';

-- ============================================================================
-- 3. appeal_repairs — Неисправности устройств
-- ============================================================================

CREATE TABLE IF NOT EXISTS appeal_repairs (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    appeal_device_id uuid NOT NULL REFERENCES appeal_devices(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    repair_category_id uuid REFERENCES repair_categories(id),
    issue_type_id uuid REFERENCES issue_types(id),
    issue_id uuid REFERENCES issues(id),
    parts_owner varchar(50),
    estimated_cost numeric(10, 2),
    final_cost numeric(10, 2),
    estimated_time_minutes int,
    symptoms text,
    diagnosis text,
    repair_status varchar(50) DEFAULT 'pending',
    completed_at timestamptz,
    notes text,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_parts_owner CHECK (parts_owner IN ('ours', 'client', 'mixed')),
    CONSTRAINT chk_repair_status CHECK (repair_status IN ('pending', 'in_progress', 'completed', 'cancelled'))
);

CREATE INDEX IF NOT EXISTS idx_appeal_repairs_device ON appeal_repairs(appeal_device_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_tenant ON appeal_repairs(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_category ON appeal_repairs(repair_category_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_issue_type ON appeal_repairs(issue_type_id);

COMMENT ON TABLE appeal_repairs IS 'Неисправности устройств в заявке (для мультизаявок)';

-- ============================================================================
-- Add is_multi_appeal flag to appeals table
-- ============================================================================

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'appeals' AND column_name = 'is_multi_appeal'
    ) THEN
        ALTER TABLE appeals ADD COLUMN is_multi_appeal boolean DEFAULT false;
        ALTER TABLE appeals ADD COLUMN qr_generated_at timestamptz;
        ALTER TABLE appeals ADD COLUMN qr_expires_at timestamptz;

        CREATE INDEX IF NOT EXISTS idx_appeals_is_multi ON appeals(is_multi_appeal);
    END IF;
END $$;

-- ============================================================================
-- Insert default repair categories
-- ============================================================================

INSERT INTO repair_categories (tenant_id, name, description, icon, "order")
SELECT
    t.id,
    cat.name,
    cat.description,
    cat.icon,
    cat.order_num
FROM tenants t
CROSS JOIN (
    VALUES
        ('Дисплей', 'Замена экрана, тачскрина, стекла', '📱', 1),
        ('Корпус', 'Ремонт корпуса, замена задней крышки', '🔧', 2),
        ('Аккумулятор', 'Замена батареи', '🔋', 3),
        ('Звук', 'Динамик, микрофон, разъём наушников', '🔊', 4),
        ('Связь', 'Wi-Fi, Bluetooth, сотовая связь, GPS', '📶', 5),
        ('Камера', 'Основная камера, фронтальная камера', '📷', 6),
        ('Порты', 'Разъём зарядки, USB, Lightning', '🔌', 7),
        ('Материнская плата', 'Ремонт платы, микросхем', '🖥️', 8),
        ('ПО (софт)', 'Прошивка, разблокировка, настройка', '💾', 9),
        ('Прочее', 'Другие неисправности', '🔨', 10)
) AS cat(name, description, icon, order_num)
WHERE NOT EXISTS (
    SELECT 1 FROM repair_categories rc
    WHERE rc.tenant_id = t.id AND rc.name = cat.name
);

-- ============================================================================
-- Migration complete: Part 1
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 003 Part 1 completed successfully: Multi-Appeals Support (3 tables)';
END $$;
