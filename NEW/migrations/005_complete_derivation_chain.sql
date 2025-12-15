-- Migration: Complete derivation chain (symptom → diagnosis → repair)
-- Date: 2025-12-15
-- Issue: 80% of symptoms have no links, derivation chain broken

-- =====================================================
-- 1. SYMPTOM → DIAGNOSIS LINKS (20 missing)
-- =====================================================

INSERT INTO elo_symptom_diagnosis_links (symptom_type_id, diagnosis_type_id, is_primary, confidence, is_active)
VALUES
-- Battery symptoms → battery_replacement (id=2)
(6, 2, true, 0.90, true),   -- battery_drain → battery_replacement
(7, 2, true, 0.95, true),   -- battery_swollen → battery_replacement
(9, 2, true, 0.85, true),   -- battery_shutdowns → battery_replacement

-- Battery not charging → может быть батарея или разъём
(8, 2, true, 0.60, true),   -- battery_not_charging → battery_replacement (primary)
(8, 3, false, 0.40, true),  -- battery_not_charging → charging_port_replacement (secondary)

-- Charging symptoms → charging_port_replacement (id=3)
(10, 3, true, 0.95, true),  -- charging_port_broken → charging_port_replacement
(11, 3, true, 0.80, true),  -- charging_slow → charging_port_replacement
(12, 3, true, 0.90, true),  -- charging_cable_loose → charging_port_replacement

-- Camera symptoms → camera_replacement (id=4)
(13, 4, true, 0.90, true),  -- camera_not_working → camera_replacement
(14, 4, true, 0.85, true),  -- camera_blurry → camera_replacement
(15, 4, true, 0.95, true),  -- camera_cracked → camera_replacement

-- Speaker/Sound symptoms → speaker_replacement (id=5)
(16, 5, true, 0.90, true),  -- speaker_not_working → speaker_replacement
(18, 5, true, 0.85, true),  -- no_sound → speaker_replacement

-- Microphone symptoms → microphone_replacement (id=6)
(17, 6, true, 0.90, true),  -- microphone_not_working → microphone_replacement

-- Software symptoms → software_reinstall (id=8)
(19, 8, true, 0.80, true),  -- software_crash → software_reinstall
(20, 8, true, 0.85, true),  -- software_boot_loop → software_reinstall
(21, 8, true, 0.90, true),  -- software_update_failed → software_reinstall

-- Water damage → cleaning first, then board_repair (id=7, 9)
(22, 9, true, 0.70, true),  -- water_damage → cleaning_needed (primary - first step)
(22, 7, false, 0.60, true), -- water_damage → board_repair (secondary - if cleaning fails)

-- Buttons not working → board_repair (id=7)
(23, 7, true, 0.75, true),  -- buttons_not_working → board_repair

-- Overheating → может быть батарея или плата
(24, 2, true, 0.50, true),  -- overheating → battery_replacement (primary)
(24, 7, false, 0.50, true), -- overheating → board_repair (secondary)

-- Other → not_repairable placeholder (id=10)
(25, 10, true, 0.50, true)  -- other → not_repairable (needs manual diagnosis)

ON CONFLICT DO NOTHING;

-- =====================================================
-- 2. DIAGNOSIS → REPAIR LINKS (7 missing)
-- =====================================================

INSERT INTO elo_diagnosis_repair_links (diagnosis_type_id, repair_action_id, is_primary, is_active)
VALUES
-- camera_replacement (4) → camera_replaced (4)
(4, 4, true, true),

-- speaker_replacement (5) → speaker_replaced (5)
(5, 5, true, true),

-- microphone_replacement (6) → microphone_replaced (6)
(6, 6, true, true),

-- board_repair (7) → board_repaired (7)
(7, 7, true, true),

-- software_reinstall (8) → software_reinstalled (8)
(8, 8, true, true),

-- cleaning_needed (9) → cleaned (9)
(9, 9, true, true),

-- not_repairable (10) → refused (10)
(10, 10, true, true)

ON CONFLICT DO NOTHING;

-- =====================================================
-- 3. ADD MISSING PRICES FOR NEW REPAIR ACTIONS
-- =====================================================

INSERT INTO elo_t_price_list (tenant_id, brand, model, repair_action_id, price_min, price_max, currency, is_active)
VALUES
-- Camera replacement prices
('11111111-1111-1111-1111-111111111111', 'Apple', 'iPhone 14', 4, 8000, 12000, 'RUB', true),
('11111111-1111-1111-1111-111111111111', 'Apple', 'iPhone 14 Pro', 4, 12000, 18000, 'RUB', true),
('11111111-1111-1111-1111-111111111111', 'Samsung', 'Galaxy S23', 4, 10000, 15000, 'RUB', true),

-- Speaker replacement prices
('11111111-1111-1111-1111-111111111111', 'Apple', NULL, 5, 3000, 5000, 'RUB', true),
('11111111-1111-1111-1111-111111111111', 'Samsung', NULL, 5, 2500, 4500, 'RUB', true),

-- Microphone replacement prices
('11111111-1111-1111-1111-111111111111', 'Apple', NULL, 6, 3500, 6000, 'RUB', true),
('11111111-1111-1111-1111-111111111111', 'Samsung', NULL, 6, 3000, 5000, 'RUB', true),

-- Board repair prices
('11111111-1111-1111-1111-111111111111', 'Apple', NULL, 7, 5000, 15000, 'RUB', true),
('11111111-1111-1111-1111-111111111111', 'Samsung', NULL, 7, 4000, 12000, 'RUB', true),

-- Software reinstall prices
('11111111-1111-1111-1111-111111111111', NULL, NULL, 8, 1500, 3000, 'RUB', true),

-- Cleaning prices
('11111111-1111-1111-1111-111111111111', NULL, NULL, 9, 2000, 5000, 'RUB', true),

-- Refused (no charge)
('11111111-1111-1111-1111-111111111111', NULL, NULL, 10, 0, 500, 'RUB', true)

ON CONFLICT DO NOTHING;

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================

-- Check symptom coverage:
-- SELECT st.code, dt.code as diagnosis, sdl.confidence
-- FROM elo_symptom_types st
-- LEFT JOIN elo_symptom_diagnosis_links sdl ON sdl.symptom_type_id = st.id
-- LEFT JOIN elo_diagnosis_types dt ON dt.id = sdl.diagnosis_type_id
-- ORDER BY st.code;

-- Check diagnosis coverage:
-- SELECT dt.code, ra.code as repair
-- FROM elo_diagnosis_types dt
-- LEFT JOIN elo_diagnosis_repair_links drl ON drl.diagnosis_type_id = dt.id
-- LEFT JOIN elo_repair_actions ra ON ra.id = drl.repair_action_id
-- ORDER BY dt.code;

-- Full chain test:
-- SELECT st.code as symptom, dt.code as diagnosis, ra.code as repair, p.price_min, p.price_max
-- FROM elo_symptom_types st
-- JOIN elo_symptom_diagnosis_links sdl ON sdl.symptom_type_id = st.id AND sdl.is_primary = true
-- JOIN elo_diagnosis_types dt ON dt.id = sdl.diagnosis_type_id
-- JOIN elo_diagnosis_repair_links drl ON drl.diagnosis_type_id = dt.id AND drl.is_primary = true
-- JOIN elo_repair_actions ra ON ra.id = drl.repair_action_id
-- LEFT JOIN elo_t_price_list p ON p.repair_action_id = ra.id AND p.brand = 'Apple'
-- ORDER BY st.code;
