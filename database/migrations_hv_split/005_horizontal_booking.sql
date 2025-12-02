-- ============================================================================
-- H/V SPLIT Migration 005: Horizontal Online Booking
-- Date: 2025-11-25
-- Description: Booking slots, bookings, booking settings
-- ============================================================================

SET search_path TO horizontal, public;

-- ============================================================================
-- 1. booking_settings - Tenant booking configuration
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.booking_settings (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Work hours
    work_start_time time DEFAULT '09:00',
    work_end_time time DEFAULT '20:00',

    -- Slots
    slot_duration_minutes int DEFAULT 30,
    buffer_between_slots int DEFAULT 0,
    max_bookings_per_slot int DEFAULT 1,

    -- Rules
    min_advance_hours int DEFAULT 2,
    max_advance_days int DEFAULT 14,

    -- Working days (ISO: 1=Monday, 7=Sunday)
    working_days jsonb DEFAULT '[1,2,3,4,5,6]',

    -- Breaks (lunch, etc)
    breaks jsonb DEFAULT '[]',

    -- Reminders
    reminder_enabled boolean DEFAULT true,
    reminder_hours_before int DEFAULT 24,
    reminder_message_template text,

    -- Auto-generate slots
    auto_generate_slots boolean DEFAULT true,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_booking_settings UNIQUE (tenant_id)
);

COMMENT ON TABLE horizontal.booking_settings IS 'Booking engine configuration per tenant';
COMMENT ON COLUMN horizontal.booking_settings.breaks IS 'JSON array: [{start_time, end_time, name}]';

-- ============================================================================
-- 2. booking_slots - Available time slots
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.booking_slots (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    slot_date date NOT NULL,
    start_time time NOT NULL,
    end_time time NOT NULL,

    -- Capacity
    max_bookings int DEFAULT 1,
    current_bookings int DEFAULT 0,

    -- Status
    is_available boolean DEFAULT true,
    is_blocked boolean DEFAULT false,
    block_reason text,

    -- Vertical filter (null = all)
    vertical_id varchar(50),

    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_slot UNIQUE (tenant_id, slot_date, start_time),
    CONSTRAINT chk_slot_times CHECK (start_time < end_time)
);

CREATE INDEX IF NOT EXISTS idx_booking_slots_tenant ON horizontal.booking_slots(tenant_id);
CREATE INDEX IF NOT EXISTS idx_booking_slots_date ON horizontal.booking_slots(slot_date);
CREATE INDEX IF NOT EXISTS idx_booking_slots_tenant_date ON horizontal.booking_slots(tenant_id, slot_date);
CREATE INDEX IF NOT EXISTS idx_booking_slots_available ON horizontal.booking_slots(tenant_id, slot_date, is_available) WHERE is_available = true;

COMMENT ON TABLE horizontal.booking_slots IS 'Available booking time slots';

-- ============================================================================
-- 3. bookings - Customer bookings
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.bookings (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES horizontal.appeals(id) ON DELETE CASCADE,
    lead_id uuid NOT NULL REFERENCES horizontal.leads(id),
    slot_id uuid REFERENCES horizontal.booking_slots(id),

    -- Time
    booking_date date NOT NULL,
    booking_time time NOT NULL,
    duration_minutes int,

    -- Status
    status varchar(50) DEFAULT 'confirmed',

    -- Reminders
    reminder_sent boolean DEFAULT false,
    reminder_sent_at timestamptz,

    -- Cancellation
    cancelled_at timestamptz,
    cancellation_reason text,
    cancelled_by varchar(50),

    -- Completion
    arrived_at timestamptz,
    completed_at timestamptz,

    notes text,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_booking_status CHECK (status IN ('pending', 'confirmed', 'completed', 'cancelled', 'no_show')),
    CONSTRAINT chk_cancelled_by CHECK (cancelled_by IS NULL OR cancelled_by IN ('customer', 'operator', 'system'))
);

CREATE INDEX IF NOT EXISTS idx_bookings_tenant ON horizontal.bookings(tenant_id);
CREATE INDEX IF NOT EXISTS idx_bookings_appeal ON horizontal.bookings(appeal_id);
CREATE INDEX IF NOT EXISTS idx_bookings_lead ON horizontal.bookings(lead_id);
CREATE INDEX IF NOT EXISTS idx_bookings_slot ON horizontal.bookings(slot_id);
CREATE INDEX IF NOT EXISTS idx_bookings_date ON horizontal.bookings(booking_date);
CREATE INDEX IF NOT EXISTS idx_bookings_status ON horizontal.bookings(status);
CREATE INDEX IF NOT EXISTS idx_bookings_tenant_date ON horizontal.bookings(tenant_id, booking_date);

COMMENT ON TABLE horizontal.bookings IS 'Customer appointment bookings';

-- ============================================================================
-- 4. booking_reminders - Scheduled reminders
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.booking_reminders (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    booking_id uuid NOT NULL REFERENCES horizontal.bookings(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    scheduled_at timestamptz NOT NULL,
    sent_at timestamptz,
    channel_id uuid REFERENCES horizontal.channels(id),
    message_id uuid REFERENCES horizontal.messages_history(id),

    status varchar(50) DEFAULT 'pending',

    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_reminder_status CHECK (status IN ('pending', 'sent', 'failed', 'cancelled'))
);

CREATE INDEX IF NOT EXISTS idx_booking_reminders_booking ON horizontal.booking_reminders(booking_id);
CREATE INDEX IF NOT EXISTS idx_booking_reminders_scheduled ON horizontal.booking_reminders(scheduled_at) WHERE status = 'pending';

COMMENT ON TABLE horizontal.booking_reminders IS 'Scheduled booking reminder notifications';

-- ============================================================================
-- Migration complete: 005
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 005 completed: Horizontal online booking system';
END $$;
