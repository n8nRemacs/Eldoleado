-- ============================================================================
-- H/V SPLIT Migration 001: Create Schemas
-- Date: 2025-11-25
-- Description: Create horizontal and vertical_repair schemas
-- ============================================================================

-- ============================================================================
-- 1. Create schemas
-- ============================================================================

CREATE SCHEMA IF NOT EXISTS horizontal;
CREATE SCHEMA IF NOT EXISTS vertical_repair;

COMMENT ON SCHEMA horizontal IS 'Universal platform layer - tenants, leads, appeals, messages, operators, channels, funnel, promo, UTM, booking, AI, CRM engine';
COMMENT ON SCHEMA vertical_repair IS 'Phone/device repair vertical - brands, models, categories, devices, repairs, prices, QR codes';

-- ============================================================================
-- 2. Set search_path for convenience (optional, can be removed in production)
-- ============================================================================

-- Default search path: public first, then horizontal, then vertical_repair
ALTER DATABASE CURRENT SET search_path TO public, horizontal, vertical_repair;

-- ============================================================================
-- 3. Create enum types in horizontal schema
-- ============================================================================

-- Appeal status enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'appeal_status' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'horizontal')) THEN
        CREATE TYPE horizontal.appeal_status AS ENUM ('new', 'in_progress', 'completed', 'cancelled');
    END IF;
END $$;

-- Message direction enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'message_direction' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'horizontal')) THEN
        CREATE TYPE horizontal.message_direction AS ENUM ('incoming', 'outgoing');
    END IF;
END $$;

-- Channel type enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'channel_type' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'horizontal')) THEN
        CREATE TYPE horizontal.channel_type AS ENUM ('whatsapp', 'telegram', 'vk', 'avito', 'instagram', 'phone', 'web_form', 'email');
    END IF;
END $$;

-- Operator role enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'operator_role' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'horizontal')) THEN
        CREATE TYPE horizontal.operator_role AS ENUM ('admin', 'operator', 'master', 'manager');
    END IF;
END $$;

-- Promo material type enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'promo_type' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'horizontal')) THEN
        CREATE TYPE horizontal.promo_type AS ENUM ('video', 'pdf', 'image', 'text', 'link');
    END IF;
END $$;

-- Booking status enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'booking_status' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'horizontal')) THEN
        CREATE TYPE horizontal.booking_status AS ENUM ('pending', 'confirmed', 'completed', 'cancelled', 'no_show');
    END IF;
END $$;

-- CRM sync status enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'sync_status' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'horizontal')) THEN
        CREATE TYPE horizontal.sync_status AS ENUM ('pending', 'success', 'failed', 'retrying');
    END IF;
END $$;

-- ============================================================================
-- 4. Create enum types in vertical_repair schema
-- ============================================================================

-- Repair status enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'repair_status' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'vertical_repair')) THEN
        CREATE TYPE vertical_repair.repair_status AS ENUM ('pending', 'in_progress', 'completed', 'cancelled');
    END IF;
END $$;

-- Parts owner enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'parts_owner' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'vertical_repair')) THEN
        CREATE TYPE vertical_repair.parts_owner AS ENUM ('ours', 'client', 'mixed');
    END IF;
END $$;

-- QR code status enum
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'qr_status' AND typnamespace = (SELECT oid FROM pg_namespace WHERE nspname = 'vertical_repair')) THEN
        CREATE TYPE vertical_repair.qr_status AS ENUM ('active', 'scanned', 'expired', 'cancelled');
    END IF;
END $$;

-- ============================================================================
-- Migration complete: 001
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 001 completed: Created schemas horizontal and vertical_repair with enum types';
END $$;
