-- ============================================================================
-- ELO Migration 004: Seed Test Tenant
-- Test data for development and testing
-- ============================================================================

-- ----------------------------------------------------------------------------
-- 1. Create test tenant
-- ----------------------------------------------------------------------------
INSERT INTO elo_t_tenants (id, name, slug, settings, plan) VALUES
    ('11111111-1111-1111-1111-111111111111',
     'Test Repair Shop',
     'test-repair',
     '{
       "timezone": "Europe/Moscow",
       "currency": "RUB",
       "language": "ru",
       "ai_mode": "auto",
       "working_hours": {"start": "09:00", "end": "20:00"},
       "debounce_seconds": 10
     }',
     'pro')
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    settings = EXCLUDED.settings,
    updated_at = NOW();


-- ----------------------------------------------------------------------------
-- 2. Link tenant to phone_repair vertical
-- ----------------------------------------------------------------------------
INSERT INTO elo_t_tenant_verticals (tenant_id, vertical_id, is_primary)
SELECT
    '11111111-1111-1111-1111-111111111111',
    id,
    true
FROM elo_verticals WHERE code = 'phone_repair'
ON CONFLICT (tenant_id, vertical_id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 3. Create test operator (admin)
-- ----------------------------------------------------------------------------
INSERT INTO elo_t_operators (id, tenant_id, name, email, role, telegram_id) VALUES
    ('22222222-2222-2222-2222-222222222222',
     '11111111-1111-1111-1111-111111111111',
     'Test Admin',
     'admin@test.local',
     'admin',
     'test_admin_tg')
ON CONFLICT (tenant_id, email) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 4. Connect test Telegram bot
-- ----------------------------------------------------------------------------
INSERT INTO elo_t_channel_accounts (id, tenant_id, channel_id, account_id, account_name, is_active)
SELECT
    '33333333-3333-3333-3333-333333333333',
    '11111111-1111-1111-1111-111111111111',
    id,
    'TEST_BOT_TOKEN_12345',
    'Test Repair Bot',
    true
FROM elo_channels WHERE code = 'telegram'
ON CONFLICT (tenant_id, channel_id, account_id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 5. Create test client
-- ----------------------------------------------------------------------------
INSERT INTO elo_t_clients (id, tenant_id, name, phone) VALUES
    ('44444444-4444-4444-4444-444444444444',
     '11111111-1111-1111-1111-111111111111',
     'Test Client Ivan',
     '+79991234567')
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    phone = EXCLUDED.phone,
    updated_at = NOW();


-- ----------------------------------------------------------------------------
-- 6. Link test client to Telegram
-- ----------------------------------------------------------------------------
INSERT INTO elo_t_client_channels (client_id, channel_id, external_id, external_username)
SELECT
    '44444444-4444-4444-4444-444444444444',
    id,
    'tg_123456789',
    '@test_ivan'
FROM elo_channels WHERE code = 'telegram'
ON CONFLICT (client_id, channel_id) DO NOTHING;


-- ----------------------------------------------------------------------------
-- 7. Create test dialog
-- ----------------------------------------------------------------------------
INSERT INTO elo_t_dialogs (id, tenant_id, client_id, channel_id, external_chat_id, vertical_id, status_id)
SELECT
    '55555555-5555-5555-5555-555555555555',
    '11111111-1111-1111-1111-111111111111',
    '44444444-4444-4444-4444-444444444444',
    c.id,
    'tg_123456789',
    v.id,
    1  -- active
FROM elo_channels c, elo_verticals v
WHERE c.code = 'telegram' AND v.code = 'phone_repair'
ON CONFLICT (id) DO UPDATE SET
    status_id = 1,
    updated_at = NOW();


-- ----------------------------------------------------------------------------
-- 8. Add sample price list
-- ----------------------------------------------------------------------------
INSERT INTO elo_t_price_list (tenant_id, vertical_id, service_type, brand, model, issue_category, name, price_min, price_max, price_display, duration_minutes, duration_display)
SELECT
    '11111111-1111-1111-1111-111111111111',
    v.id,
    'repair',
    brand,
    model,
    category,
    service_name,
    min_price,
    max_price,
    price_text,
    duration,
    duration_text
FROM elo_verticals v,
(VALUES
    ('Apple', 'iPhone 14 Pro', 'Screen', 'Screen Replacement', 15000, 20000, '15000-20000₽', 60, '1 hour'),
    ('Apple', 'iPhone 14 Pro', 'Battery', 'Battery Replacement', 5000, 7000, '5000-7000₽', 30, '30 min'),
    ('Apple', 'iPhone 14', 'Screen', 'Screen Replacement', 12000, 16000, '12000-16000₽', 60, '1 hour'),
    ('Apple', 'iPhone 14', 'Battery', 'Battery Replacement', 4500, 6000, '4500-6000₽', 30, '30 min'),
    ('Apple', 'iPhone 13', 'Screen', 'Screen Replacement', 10000, 14000, '10000-14000₽', 60, '1 hour'),
    ('Apple', 'iPhone 13', 'Battery', 'Battery Replacement', 4000, 5500, '4000-5500₽', 30, '30 min'),
    ('Samsung', 'Galaxy S23', 'Screen', 'Screen Replacement', 18000, 25000, '18000-25000₽', 90, '1.5 hours'),
    ('Samsung', 'Galaxy S23', 'Battery', 'Battery Replacement', 5000, 7000, '5000-7000₽', 45, '45 min'),
    ('Xiaomi', NULL, 'Screen', 'Screen Replacement', 5000, 12000, '5000-12000₽', 60, '1 hour'),
    ('Xiaomi', NULL, 'Battery', 'Battery Replacement', 2500, 5000, '2500-5000₽', 30, '30 min')
) AS prices(brand, model, category, service_name, min_price, max_price, price_text, duration, duration_text)
WHERE v.code = 'phone_repair'
ON CONFLICT DO NOTHING;


-- ============================================================================
-- Summary: Test Data Created
-- ============================================================================
-- Tenant:          Test Repair Shop (11111111-1111-1111-1111-111111111111)
-- Operator:        Test Admin (admin@test.local)
-- Channel:         Telegram bot (TEST_BOT_TOKEN_12345)
-- Client:          Test Client Ivan (+79991234567, @test_ivan)
-- Dialog:          Active dialog (55555555-5555-5555-5555-555555555555)
-- Prices:          10 sample prices for iPhone/Samsung/Xiaomi
-- ============================================================================

-- Test query to verify
-- SELECT
--     t.name as tenant,
--     o.name as operator,
--     cl.name as client,
--     d.id as dialog_id,
--     d.status_id
-- FROM elo_t_tenants t
-- JOIN elo_t_operators o ON o.tenant_id = t.id
-- JOIN elo_t_clients cl ON cl.tenant_id = t.id
-- JOIN elo_t_dialogs d ON d.tenant_id = t.id
-- WHERE t.slug = 'test-repair';
