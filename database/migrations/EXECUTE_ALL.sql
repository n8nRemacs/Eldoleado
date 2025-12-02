-- ============================================================================
-- EXECUTE ALL MIGRATIONS
-- ============================================================================
-- Этот файл содержит все 7 миграций последовательно
-- Можно выполнить одной командой:
-- psql -h localhost -U postgres -d batterycrm -f EXECUTE_ALL.sql
-- ============================================================================

\echo ''
\echo '========================================='
\echo 'Starting BatteryCRM Migrations'
\echo 'Total: 7 migration files, 13 new tables'
\echo '========================================='
\echo ''

-- ============================================================================
-- Part 1: Multi-Appeals Support (3 tables)
-- ============================================================================

\echo 'Executing Part 1: Multi-Appeals Support (3 tables)...'
\i 003_new_tables_part1_multi_appeals.sql
\echo ''

-- ============================================================================
-- Part 2: 9-Stage Funnel (2 tables)
-- ============================================================================

\echo 'Executing Part 2: 9-Stage Funnel (2 tables)...'
\i 004_new_tables_part2_funnel.sql
\echo ''

-- ============================================================================
-- Part 3: Promo Materials (3 tables)
-- ============================================================================

\echo 'Executing Part 3: Promo Materials (3 tables)...'
\i 005_new_tables_part3_promo.sql
\echo ''

-- ============================================================================
-- Part 4: Pricing Engine (3 tables)
-- ============================================================================

\echo 'Executing Part 4: Pricing Engine (3 tables)...'
\i 006_new_tables_part4_pricing.sql
\echo ''

-- ============================================================================
-- Part 5: AI & Voice Processing (2 tables)
-- ============================================================================

\echo 'Executing Part 5: AI & Voice Processing (2 tables)...'
\i 007_new_tables_part5_ai_voice.sql
\echo ''

-- ============================================================================
-- Part 6: CRM Integrations (3 tables)
-- ============================================================================

\echo 'Executing Part 6: CRM Integrations (3 tables)...'
\i 008_new_tables_part6_crm_integrations.sql
\echo ''

-- ============================================================================
-- Part 7: TTL Policies & Archival (2 tables)
-- ============================================================================

\echo 'Executing Part 7: TTL Policies & Archival (2 tables)...'
\i 009_new_tables_part7_ttl_policies.sql
\echo ''

-- ============================================================================
-- Verification
-- ============================================================================

\echo ''
\echo '========================================='
\echo 'Migration Complete! Running verification...'
\echo '========================================='
\echo ''

-- Count new tables
\echo 'Checking created tables...'
SELECT COUNT(*) as new_tables_count
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name IN (
    'repair_categories',
    'appeal_devices',
    'appeal_repairs',
    'appeal_stages',
    'appeal_stage_transitions',
    'promo_materials',
    'promo_triggers',
    'promo_history',
    'price_lists',
    'price_list_items',
    'price_mappings',
    'ai_chat_memory',
    'voice_transcriptions',
    'crm_integrations',
    'crm_sync_history',
    'crm_field_mappings',
    'data_retention_policies',
    'archived_data'
  );

\echo ''
\echo 'Expected: 18 tables'
\echo ''

-- Check default data
\echo 'Checking default repair categories per tenant...'
SELECT tenant_id, COUNT(*) as categories_count
FROM repair_categories
GROUP BY tenant_id
ORDER BY tenant_id;

\echo ''
\echo 'Expected: 10 categories per tenant'
\echo ''

\echo 'Checking default appeal stages per tenant...'
SELECT tenant_id, COUNT(*) as stages_count
FROM appeal_stages
GROUP BY tenant_id
ORDER BY tenant_id;

\echo ''
\echo 'Expected: 9 stages per tenant'
\echo ''

\echo 'Checking default retention policies per tenant...'
SELECT tenant_id, COUNT(*) as policies_count
FROM data_retention_policies
GROUP BY tenant_id
ORDER BY tenant_id;

\echo ''
\echo 'Expected: 5 policies per tenant'
\echo ''

\echo '========================================='
\echo 'All migrations executed successfully!'
\echo '========================================='
