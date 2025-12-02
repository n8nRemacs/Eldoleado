-- ============================================================================
-- H/V SPLIT Migration 009: Vertical Repair - Appeal Data
-- Date: 2025-11-25
-- Description: Appeal devices, appeal repairs, QR codes
-- ============================================================================

SET search_path TO vertical_repair, horizontal, public;

-- ============================================================================
-- 1. appeal_devices - Devices in appeal (multi-device support)
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.appeal_devices (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    appeal_id uuid NOT NULL REFERENCES horizontal.appeals(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Device info
    device_type_id uuid REFERENCES vertical_repair.device_types(id),
    brand_id uuid REFERENCES vertical_repair.brands(id),
    model_id uuid REFERENCES vertical_repair.models(id),

    -- Extracted text (if not matched)
    brand_text varchar(255),
    model_text varchar(255),

    -- Identifiers
    serial_number varchar(255),
    imei varchar(255),

    -- Appearance
    color varchar(100),
    condition varchar(100),
    condition_notes text,

    -- Accessories
    accessories jsonb DEFAULT '[]',

    -- Photos
    photos jsonb DEFAULT '[]',

    notes text,

    -- Order in appeal
    "order" int DEFAULT 0,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_appeal_devices_appeal ON vertical_repair.appeal_devices(appeal_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_tenant ON vertical_repair.appeal_devices(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_brand ON vertical_repair.appeal_devices(brand_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_model ON vertical_repair.appeal_devices(model_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_imei ON vertical_repair.appeal_devices(imei);

COMMENT ON TABLE vertical_repair.appeal_devices IS 'Devices in appeal (supports multiple devices per appeal)';
COMMENT ON COLUMN vertical_repair.appeal_devices.accessories IS 'JSON array: ["charger", "case", "box"]';
COMMENT ON COLUMN vertical_repair.appeal_devices.photos IS 'JSON array of photo URLs';

-- ============================================================================
-- 2. appeal_repairs - Repairs for each device
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.appeal_repairs (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    appeal_device_id uuid NOT NULL REFERENCES vertical_repair.appeal_devices(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Repair info
    repair_category_id uuid REFERENCES vertical_repair.repair_categories(id),
    issue_type_id uuid REFERENCES vertical_repair.issue_types(id),

    -- Extracted text
    category_text varchar(255),
    issue_text varchar(255),
    symptoms text,

    -- Diagnosis
    diagnosis text,
    diagnosis_photos jsonb DEFAULT '[]',

    -- Parts
    parts_owner varchar(50),
    parts_used jsonb DEFAULT '[]',

    -- Pricing
    estimated_cost numeric(10, 2),
    final_cost numeric(10, 2),
    parts_cost numeric(10, 2),
    labor_cost numeric(10, 2),

    -- Time
    estimated_time_minutes int,
    actual_time_minutes int,

    -- Status
    repair_status varchar(50) DEFAULT 'pending',

    -- Warranty
    warranty_days int,
    warranty_until date,

    notes text,

    -- Timestamps
    started_at timestamptz,
    completed_at timestamptz,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_parts_owner CHECK (parts_owner IS NULL OR parts_owner IN ('ours', 'client', 'mixed')),
    CONSTRAINT chk_repair_status CHECK (repair_status IN ('pending', 'in_progress', 'completed', 'cancelled', 'warranty'))
);

CREATE INDEX IF NOT EXISTS idx_appeal_repairs_device ON vertical_repair.appeal_repairs(appeal_device_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_tenant ON vertical_repair.appeal_repairs(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_category ON vertical_repair.appeal_repairs(repair_category_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_issue_type ON vertical_repair.appeal_repairs(issue_type_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_status ON vertical_repair.appeal_repairs(repair_status);

COMMENT ON TABLE vertical_repair.appeal_repairs IS 'Repairs for each device in appeal';
COMMENT ON COLUMN vertical_repair.appeal_repairs.parts_used IS 'JSON array: [{part_name, part_number, quantity, cost}]';

-- ============================================================================
-- 3. appeal_qr_codes - QR codes for visit
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.appeal_qr_codes (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES horizontal.appeals(id) ON DELETE CASCADE,

    -- QR code
    qr_code varchar(255) NOT NULL UNIQUE,
    qr_data jsonb,

    -- Booking link (if exists)
    booking_id uuid REFERENCES horizontal.bookings(id),

    -- Timing
    generated_at timestamptz DEFAULT now(),
    expires_at timestamptz NOT NULL,

    -- Scan info
    scanned_at timestamptz,
    scanned_by_operator_id uuid REFERENCES horizontal.operators(id),
    scan_location jsonb,

    -- Status
    status varchar(50) DEFAULT 'active',

    CONSTRAINT chk_qr_status CHECK (status IN ('active', 'scanned', 'expired', 'cancelled'))
);

CREATE INDEX IF NOT EXISTS idx_qr_codes_code ON vertical_repair.appeal_qr_codes(qr_code);
CREATE INDEX IF NOT EXISTS idx_qr_codes_appeal ON vertical_repair.appeal_qr_codes(appeal_id);
CREATE INDEX IF NOT EXISTS idx_qr_codes_tenant ON vertical_repair.appeal_qr_codes(tenant_id);
CREATE INDEX IF NOT EXISTS idx_qr_codes_status ON vertical_repair.appeal_qr_codes(status);
CREATE INDEX IF NOT EXISTS idx_qr_codes_expires ON vertical_repair.appeal_qr_codes(expires_at) WHERE status = 'active';

COMMENT ON TABLE vertical_repair.appeal_qr_codes IS 'QR codes for customer visit identification';

-- ============================================================================
-- Migration complete: 009
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 009 completed: Vertical Repair appeal devices, repairs, QR codes';
END $$;
