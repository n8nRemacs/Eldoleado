-- ============================================================================
-- H/V SPLIT MIGRATION - MASTER EXECUTION SCRIPT
-- Date: 2025-11-25
-- Description: Execute all H/V split migrations in order
-- ============================================================================

-- IMPORTANT: Run this script in a transaction for safety
-- If any step fails, the entire migration will be rolled back

BEGIN;

\echo '============================================'
\echo 'Starting H/V Split Migration'
\echo '============================================'

-- Phase 1: Create schemas and enums
\echo 'Executing 001_create_schemas.sql...'
\i 001_create_schemas.sql

-- Phase 2: Horizontal core tables
\echo 'Executing 002_horizontal_core.sql...'
\i 002_horizontal_core.sql

-- Phase 3: Horizontal appeals and messaging
\echo 'Executing 003_horizontal_appeals.sql...'
\i 003_horizontal_appeals.sql

-- Phase 4: Horizontal promo and UTM
\echo 'Executing 004_horizontal_promo_utm.sql...'
\i 004_horizontal_promo_utm.sql

-- Phase 5: Horizontal booking
\echo 'Executing 005_horizontal_booking.sql...'
\i 005_horizontal_booking.sql

-- Phase 6: Horizontal AI and voice
\echo 'Executing 006_horizontal_ai_voice.sql...'
\i 006_horizontal_ai_voice.sql

-- Phase 7: Horizontal CRM and TTL
\echo 'Executing 007_horizontal_crm_ttl.sql...'
\i 007_horizontal_crm_ttl.sql

-- Phase 8: Vertical Repair dictionaries
\echo 'Executing 008_vertical_repair_dictionaries.sql...'
\i 008_vertical_repair_dictionaries.sql

-- Phase 9: Vertical Repair appeal data
\echo 'Executing 009_vertical_repair_appeals.sql...'
\i 009_vertical_repair_appeals.sql

-- Phase 10: Vertical Repair pricing
\echo 'Executing 010_vertical_repair_pricing.sql...'
\i 010_vertical_repair_pricing.sql

-- Phase 11: Vertical Repair config
\echo 'Executing 011_vertical_repair_config.sql...'
\i 011_vertical_repair_config.sql

-- Phase 12: Data migration (OPTIONAL - only if migrating from existing public schema)
-- \echo 'Executing 012_data_migration.sql...'
-- \i 012_data_migration.sql

-- Phase 13: Views and functions
\echo 'Executing 013_views_functions.sql...'
\i 013_views_functions.sql

\echo '============================================'
\echo 'H/V Split Migration completed successfully!'
\echo '============================================'

-- Verify migration
SELECT 'horizontal' AS schema, count(*) AS table_count
FROM information_schema.tables
WHERE table_schema = 'horizontal'
UNION ALL
SELECT 'vertical_repair' AS schema, count(*) AS table_count
FROM information_schema.tables
WHERE table_schema = 'vertical_repair';

COMMIT;
