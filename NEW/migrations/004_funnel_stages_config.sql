-- Migration: Update funnel stages with exit_conditions and config
-- Date: 2025-12-15

-- =====================================================
-- UPDATE FUNNEL STAGES CONFIG
-- =====================================================

-- data_collection (id=2)
UPDATE elo_v_funnel_stages SET
  code = 'data_collection',
  name = 'Data Collection',
  name_ru = 'Сбор данных',
  sort_order = 2,
  exit_conditions = '{"type": "all_lines_complete", "required_slots": ["device", "symptom"]}'::jsonb,
  config = '{"required_slots": ["device", "symptom"], "next_stage": "presentation"}'::jsonb
WHERE id = 2;

-- presentation (id=3)
UPDATE elo_v_funnel_stages SET
  code = 'presentation',
  name = 'Presentation',
  name_ru = 'Презентация',
  sort_order = 3,
  exit_conditions = '{"type": "slot_filled", "slot": "offer_acknowledged"}'::jsonb,
  config = '{"required_slots": ["offer_shown"], "next_stage": "agreement"}'::jsonb
WHERE id = 3;

-- agreement (id=4)
UPDATE elo_v_funnel_stages SET
  code = 'agreement',
  name = 'Agreement',
  name_ru = 'Согласование',
  sort_order = 4,
  exit_conditions = '{"type": "intent_detected", "intent": "agree_to_book"}'::jsonb,
  config = '{"required_slots": ["ready_to_book"], "next_stage": "booking"}'::jsonb
WHERE id = 4;

-- booking (id=5)
UPDATE elo_v_funnel_stages SET
  code = 'booking',
  name = 'Booking',
  name_ru = 'Запись',
  sort_order = 5,
  exit_conditions = '{"type": "all_slots_filled", "slots": ["date", "time", "name", "phone"]}'::jsonb,
  config = '{"required_slots": ["date", "time", "name", "phone"], "next_stage": "confirmation"}'::jsonb
WHERE id = 5;

-- confirmation (id=6)
UPDATE elo_v_funnel_stages SET
  sort_order = 6,
  exit_conditions = '{"type": "intent_detected", "intent": "confirm"}'::jsonb,
  config = '{"required_slots": ["confirmed"], "next_stage": null, "on_complete": "create_intake"}'::jsonb
WHERE id = 6;

-- =====================================================
-- VERIFICATION
-- =====================================================
-- SELECT code, name_ru, exit_conditions, config FROM elo_v_funnel_stages ORDER BY sort_order;
