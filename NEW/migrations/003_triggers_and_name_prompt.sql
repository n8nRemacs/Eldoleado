-- Migration: Add triggers seed data and name prompt
-- Date: 2025-12-15

-- =====================================================
-- 1. ADD MISSING PROMPT FOR NAME SLOT
-- =====================================================

INSERT INTO elo_v_prompts (vertical_id, funnel_stage_id, prompt_type, goal_type, slot_name, name, system_prompt, user_prompt_template)
VALUES
(1, 5, 'response', 'ask_slot', 'name', 'Ask Name',
'Ты - вежливый оператор сервисного центра. Отвечай кратко, по делу.',
'Спроси как зовут клиента для записи.')
ON CONFLICT DO NOTHING;

-- =====================================================
-- 2. SEED TRIGGERS DATA
-- =====================================================

INSERT INTO elo_v_triggers (id, code, name, vertical_id, funnel_stage, conditions, action_type, action_data, once_per_dialog, priority, is_active)
VALUES
-- Apple display warranty
('e97fd184-1673-4112-8dc8-3692f0bc727c', 'apple_display_warranty', 'Apple display warranty info',
1, 'presentation',
'{"device.brand": "Apple", "repair_type.code": "display_replaced"}'::jsonb,
'send_message',
'{"message": "На замену дисплея Apple предоставляется гарантия 6 месяцев. Используем оригинальные комплектующие."}'::jsonb,
true, 100, true),

-- High price discount
('b4179064-a9a7-4967-9846-53ffbcac96bf', 'high_price_discount', 'High price discount offer',
1, 'presentation',
'{"price.amount": {"$gt": 7000}}'::jsonb,
'send_message',
'{"message": "При записи сегодня - скидка 5%!"}'::jsonb,
true, 90, true),

-- Battery care info
('279a6d63-39a7-4f10-8bbc-00ec7cc45f01', 'battery_care', 'Battery care info',
1, 'presentation',
'{"repair_type.code": "battery_replaced"}'::jsonb,
'send_message',
'{"message": "После замены батареи рекомендуем выполнить 2-3 полных цикла зарядки для калибровки."}'::jsonb,
true, 80, true)
ON CONFLICT (code) DO UPDATE SET
    conditions = EXCLUDED.conditions,
    action_data = EXCLUDED.action_data,
    priority = EXCLUDED.priority;

-- =====================================================
-- 3. ADD MORE USEFUL TRIGGERS
-- =====================================================

INSERT INTO elo_v_triggers (code, name, vertical_id, funnel_stage, conditions, action_type, action_data, once_per_dialog, priority, is_active)
VALUES
-- Samsung original parts
('samsung_original_parts', 'Samsung original parts info',
1, 'presentation',
'{"device.brand": "Samsung"}'::jsonb,
'send_message',
'{"message": "Для Samsung используем оригинальные запчасти с гарантией производителя."}'::jsonb,
true, 85, true),

-- Water damage warning
('water_damage_warning', 'Water damage complexity warning',
1, 'presentation',
'{"diagnosis.code": "water_damage_repair"}'::jsonb,
'send_message',
'{"message": "Ремонт после воды требует диагностики. Окончательная цена после осмотра."}'::jsonb,
true, 95, true),

-- Express repair offer
('express_repair', 'Express repair available',
1, 'booking',
'{"repair_type.code": "display_replaced"}'::jsonb,
'send_message',
'{"message": "Замена дисплея занимает 30-60 минут. Можете подождать в зоне ожидания."}'::jsonb,
true, 70, true)
ON CONFLICT (code) DO NOTHING;

-- =====================================================
-- VERIFICATION
-- =====================================================
-- SELECT code, name, conditions, action_data FROM elo_v_triggers ORDER BY priority DESC;
