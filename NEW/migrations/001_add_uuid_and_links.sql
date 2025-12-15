-- Migration: Add UUID to reference tables and create derivation links
-- Date: 2025-12-15

-- =====================================================
-- 1. ADD UUID TO REFERENCE TABLES
-- =====================================================

-- Add uuid column to elo_symptom_types
ALTER TABLE elo_symptom_types
ADD COLUMN IF NOT EXISTS uuid UUID DEFAULT gen_random_uuid();

-- Add uuid column to elo_diagnosis_types
ALTER TABLE elo_diagnosis_types
ADD COLUMN IF NOT EXISTS uuid UUID DEFAULT gen_random_uuid();

-- Add uuid column to elo_repair_actions
ALTER TABLE elo_repair_actions
ADD COLUMN IF NOT EXISTS uuid UUID DEFAULT gen_random_uuid();

-- Generate UUIDs for existing records
UPDATE elo_symptom_types SET uuid = gen_random_uuid() WHERE uuid IS NULL;
UPDATE elo_diagnosis_types SET uuid = gen_random_uuid() WHERE uuid IS NULL;
UPDATE elo_repair_actions SET uuid = gen_random_uuid() WHERE uuid IS NULL;

-- Make uuid NOT NULL and UNIQUE
ALTER TABLE elo_symptom_types ALTER COLUMN uuid SET NOT NULL;
ALTER TABLE elo_diagnosis_types ALTER COLUMN uuid SET NOT NULL;
ALTER TABLE elo_repair_actions ALTER COLUMN uuid SET NOT NULL;

CREATE UNIQUE INDEX IF NOT EXISTS idx_symptom_types_uuid ON elo_symptom_types(uuid);
CREATE UNIQUE INDEX IF NOT EXISTS idx_diagnosis_types_uuid ON elo_diagnosis_types(uuid);
CREATE UNIQUE INDEX IF NOT EXISTS idx_repair_actions_uuid ON elo_repair_actions(uuid);

-- =====================================================
-- 2. CREATE SYMPTOM -> DIAGNOSIS LINK TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS elo_symptom_diagnosis_links (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    symptom_type_id INTEGER NOT NULL REFERENCES elo_symptom_types(id),
    diagnosis_type_id INTEGER NOT NULL REFERENCES elo_diagnosis_types(id),
    confidence DECIMAL(3,2) DEFAULT 1.0,  -- 0.00 - 1.00
    is_primary BOOLEAN DEFAULT true,       -- Primary diagnosis for this symptom
    vertical_id INTEGER REFERENCES elo_verticals(id),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(symptom_type_id, diagnosis_type_id, vertical_id)
);

CREATE INDEX IF NOT EXISTS idx_symptom_diagnosis_symptom ON elo_symptom_diagnosis_links(symptom_type_id);
CREATE INDEX IF NOT EXISTS idx_symptom_diagnosis_diagnosis ON elo_symptom_diagnosis_links(diagnosis_type_id);

COMMENT ON TABLE elo_symptom_diagnosis_links IS 'Links symptoms to their probable diagnoses';

-- =====================================================
-- 3. CREATE DIAGNOSIS -> REPAIR LINK TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS elo_diagnosis_repair_links (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    diagnosis_type_id INTEGER NOT NULL REFERENCES elo_diagnosis_types(id),
    repair_action_id INTEGER NOT NULL REFERENCES elo_repair_actions(id),
    is_primary BOOLEAN DEFAULT true,       -- Primary repair for this diagnosis
    vertical_id INTEGER REFERENCES elo_verticals(id),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    UNIQUE(diagnosis_type_id, repair_action_id, vertical_id)
);

CREATE INDEX IF NOT EXISTS idx_diagnosis_repair_diagnosis ON elo_diagnosis_repair_links(diagnosis_type_id);
CREATE INDEX IF NOT EXISTS idx_diagnosis_repair_repair ON elo_diagnosis_repair_links(repair_action_id);

COMMENT ON TABLE elo_diagnosis_repair_links IS 'Links diagnoses to required repair actions';

-- =====================================================
-- 4. ADD repair_action_id TO PRICE LIST
-- =====================================================

ALTER TABLE elo_t_price_list
ADD COLUMN IF NOT EXISTS repair_action_id INTEGER REFERENCES elo_repair_actions(id);

CREATE INDEX IF NOT EXISTS idx_price_list_repair_action ON elo_t_price_list(repair_action_id);

-- =====================================================
-- 5. POPULATE SYMPTOM ALIASES
-- =====================================================

-- Update aliases for common symptoms (Russian text patterns)
UPDATE elo_v_symptom_mappings SET aliases = '["разбит экран", "разбитый экран", "треснул экран", "трещина на экране", "разбил экран"]'::jsonb
WHERE symptom_type_id = 1;  -- screen_cracked

UPDATE elo_v_symptom_mappings SET aliases = '["экран не работает", "не работает экран", "черный экран", "экран погас", "не включается экран"]'::jsonb
WHERE symptom_type_id = 2;  -- screen_not_working

UPDATE elo_v_symptom_mappings SET aliases = '["экран мерцает", "мерцание экрана", "моргает экран", "мигает экран"]'::jsonb
WHERE symptom_type_id = 3;  -- screen_flickering

UPDATE elo_v_symptom_mappings SET aliases = '["сенсор не работает", "не реагирует на касания", "тачскрин не работает", "экран не откликается"]'::jsonb
WHERE symptom_type_id = 4;  -- screen_touch_issues

UPDATE elo_v_symptom_mappings SET aliases = '["полосы на экране", "полоса на экране", "артефакты на экране", "пиксели на экране"]'::jsonb
WHERE symptom_type_id = 5;  -- screen_lines

-- =====================================================
-- 6. POPULATE SYMPTOM -> DIAGNOSIS LINKS
-- =====================================================

-- Screen symptoms -> Display replacement
INSERT INTO elo_symptom_diagnosis_links (symptom_type_id, diagnosis_type_id, confidence, is_primary, vertical_id)
VALUES
    (1, 1, 0.95, true, 1),  -- screen_cracked -> display_replacement
    (2, 1, 0.90, true, 1),  -- screen_not_working -> display_replacement
    (3, 1, 0.85, true, 1),  -- screen_flickering -> display_replacement
    (4, 1, 0.80, true, 1),  -- screen_touch_issues -> display_replacement
    (5, 1, 0.90, true, 1)   -- screen_lines -> display_replacement
ON CONFLICT (symptom_type_id, diagnosis_type_id, vertical_id) DO NOTHING;

-- =====================================================
-- 7. POPULATE DIAGNOSIS -> REPAIR LINKS
-- =====================================================

INSERT INTO elo_diagnosis_repair_links (diagnosis_type_id, repair_action_id, is_primary, vertical_id)
VALUES
    (1, 1, true, 1),  -- display_replacement -> display_replaced
    (2, 2, true, 1),  -- battery_replacement -> battery_replaced
    (3, 3, true, 1)   -- charging_port_replacement -> charging_port_replaced
ON CONFLICT (diagnosis_type_id, repair_action_id, vertical_id) DO NOTHING;

-- =====================================================
-- 8. LINK PRICES TO REPAIR ACTIONS
-- =====================================================

-- Update price_list entries with repair_action_id
UPDATE elo_t_price_list SET repair_action_id = 1 WHERE name ILIKE '%Screen%' OR name ILIKE '%Display%' OR name ILIKE '%дисплей%' OR name ILIKE '%экран%';
UPDATE elo_t_price_list SET repair_action_id = 2 WHERE name ILIKE '%Battery%' OR name ILIKE '%батарея%' OR name ILIKE '%аккумулятор%';
UPDATE elo_t_price_list SET repair_action_id = 3 WHERE name ILIKE '%Charging%' OR name ILIKE '%зарядк%' OR name ILIKE '%разъём%';

-- =====================================================
-- VERIFICATION QUERIES (run after migration)
-- =====================================================

-- Check UUID columns added:
-- SELECT id, uuid, code, name_ru FROM elo_symptom_types LIMIT 5;
-- SELECT id, uuid, code, name_ru FROM elo_diagnosis_types LIMIT 5;
-- SELECT id, uuid, code, name_ru FROM elo_repair_actions LIMIT 5;

-- Check links created:
-- SELECT * FROM elo_symptom_diagnosis_links;
-- SELECT * FROM elo_diagnosis_repair_links;

-- Check aliases populated:
-- SELECT id, symptom_type_id, aliases FROM elo_v_symptom_mappings WHERE aliases != '[]';

-- Check prices linked:
-- SELECT id, brand, model, name, repair_action_id FROM elo_t_price_list WHERE repair_action_id IS NOT NULL;
