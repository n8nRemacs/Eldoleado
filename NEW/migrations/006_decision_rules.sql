-- Migration: Decision Rules Table
-- Date: 2025-12-15
-- Purpose: Store decision rules for prompt selection (no hardcode)

-- =====================================================
-- 1. CREATE TABLE
-- =====================================================

CREATE TABLE IF NOT EXISTS elo_v_decision_rules (
    id SERIAL PRIMARY KEY,
    vertical_id INT NOT NULL REFERENCES elo_verticals(id),

    -- Rule identification
    rule_code VARCHAR(50) NOT NULL,
    rule_name VARCHAR(100) NOT NULL,
    description TEXT,

    -- Priority (higher = checked first)
    priority INT NOT NULL DEFAULT 50,

    -- Conditions (all must match)
    conditions JSONB NOT NULL DEFAULT '{}',

    -- Actions (what to do when matched)
    actions JSONB NOT NULL DEFAULT '{}',

    -- Status
    is_active BOOLEAN DEFAULT true,

    -- Meta
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(vertical_id, rule_code)
);

-- Index for fast lookup
CREATE INDEX idx_decision_rules_lookup
ON elo_v_decision_rules(vertical_id, is_active, priority DESC);

-- =====================================================
-- 2. INSERT BASIC RULES (phone_repair vertical)
-- =====================================================

INSERT INTO elo_v_decision_rules (vertical_id, rule_code, rule_name, priority, conditions, actions, description) VALUES

-- === DATA COLLECTION STAGE ===

-- No device yet
(1, 'ask_device', 'Ask for device', 100,
 '{"stage": "data_collection", "has_device": false}',
 '{"goal": "ask_device", "prompt_id": "ask_device", "buttons": [{"text": "iPhone", "data": "device:iphone"}, {"text": "Samsung", "data": "device:samsung"}, {"text": "Другой", "data": "device:other"}]}',
 'Ask customer what device they have'),

-- Has device, no symptom
(1, 'ask_symptom', 'Ask for symptom', 90,
 '{"stage": "data_collection", "has_device": true, "has_symptom": false}',
 '{"goal": "ask_symptom", "prompt_id": "ask_symptom"}',
 'Ask customer what is wrong with device'),

-- Has device and symptom, need to derive
(1, 'derive_chain', 'Derive diagnosis and price', 85,
 '{"stage": "data_collection", "has_device": true, "has_symptom": true, "has_price": false}',
 '{"goal": "derive", "prompt_id": "deriving", "side_effects": [{"type": "derive_chain"}]}',
 'Run derivation chain to get diagnosis and price'),

-- Ready for presentation (all data collected)
(1, 'ready_for_presentation', 'Move to presentation', 80,
 '{"stage": "data_collection", "has_device": true, "has_symptom": true, "has_price": true}',
 '{"goal": "transition", "next_stage": "presentation"}',
 'All data collected, transition to presentation'),

-- === PRESENTATION STAGE ===

-- Present offer to customer
(1, 'present_offer', 'Present price and offer', 70,
 '{"stage": "presentation"}',
 '{"goal": "present_offer", "prompt_id": "present_offer", "buttons": [{"text": "Записаться", "data": "action:book"}, {"text": "Вопросы", "data": "action:questions"}]}',
 'Show price and ask if customer wants to book'),

-- === AGREEMENT STAGE ===

-- Customer agrees to book
(1, 'customer_agrees', 'Customer wants to book', 65,
 '{"stage": "agreement", "intent": "agree"}',
 '{"goal": "transition", "next_stage": "booking"}',
 'Customer agreed, move to booking'),

-- Customer has questions
(1, 'customer_questions', 'Customer has questions', 64,
 '{"stage": "agreement", "intent": "clarify"}',
 '{"goal": "answer_questions", "prompt_id": "answer_questions"}',
 'Answer customer questions'),

-- Customer declines
(1, 'customer_declines', 'Customer declines', 63,
 '{"stage": "agreement", "intent": "decline"}',
 '{"goal": "handle_decline", "prompt_id": "handle_decline"}',
 'Handle customer decline politely'),

-- Default for agreement stage
(1, 'ask_agreement', 'Ask for agreement', 60,
 '{"stage": "agreement"}',
 '{"goal": "ask_agreement", "prompt_id": "ask_agreement", "buttons": [{"text": "Да, записать", "data": "intent:agree"}, {"text": "Нет", "data": "intent:decline"}]}',
 'Ask customer if they want to proceed'),

-- === BOOKING STAGE ===

-- Need date
(1, 'ask_date', 'Ask for booking date', 55,
 '{"stage": "booking", "has_booking_date": false}',
 '{"goal": "ask_date", "prompt_id": "ask_booking_date"}',
 'Ask customer for preferred date'),

-- Need time
(1, 'ask_time', 'Ask for booking time', 54,
 '{"stage": "booking", "has_booking_date": true, "has_booking_time": false}',
 '{"goal": "ask_time", "prompt_id": "ask_booking_time"}',
 'Ask customer for preferred time'),

-- Need name
(1, 'ask_name', 'Ask for customer name', 53,
 '{"stage": "booking", "has_booking_date": true, "has_booking_time": true, "has_booking_name": false}',
 '{"goal": "ask_name", "prompt_id": "ask_booking_name"}',
 'Ask customer for their name'),

-- Need phone
(1, 'ask_phone', 'Ask for customer phone', 52,
 '{"stage": "booking", "has_booking_date": true, "has_booking_time": true, "has_booking_name": true, "has_booking_phone": false}',
 '{"goal": "ask_phone", "prompt_id": "ask_booking_phone"}',
 'Ask customer for their phone number'),

-- All booking info collected
(1, 'booking_complete', 'Booking info complete', 50,
 '{"stage": "booking", "has_booking_date": true, "has_booking_time": true, "has_booking_name": true, "has_booking_phone": true}',
 '{"goal": "transition", "next_stage": "confirmation"}',
 'All booking info collected, move to confirmation'),

-- === CONFIRMATION STAGE ===

-- Show confirmation and ask to confirm
(1, 'ask_confirmation', 'Ask to confirm booking', 45,
 '{"stage": "confirmation"}',
 '{"goal": "ask_confirmation", "prompt_id": "ask_confirmation", "buttons": [{"text": "Подтверждаю", "data": "intent:confirm"}, {"text": "Изменить", "data": "intent:edit"}]}',
 'Show booking summary and ask for confirmation'),

-- Customer confirms
(1, 'booking_confirmed', 'Booking confirmed', 44,
 '{"stage": "confirmation", "intent": "confirm"}',
 '{"goal": "complete", "prompt_id": "booking_confirmed", "side_effects": [{"type": "create_booking"}, {"type": "write_graph"}]}',
 'Booking confirmed, complete the process'),

-- === SPECIAL INTENTS (high priority) ===

-- Cancel intent at any stage
(1, 'handle_cancel', 'Handle cancel request', 200,
 '{"intent": "cancel"}',
 '{"goal": "confirm_cancel", "prompt_id": "confirm_cancel", "buttons": [{"text": "Да, отменить", "data": "intent:confirm_cancel"}, {"text": "Нет, продолжить", "data": "intent:continue"}]}',
 'Customer wants to cancel'),

-- Greeting at any stage
(1, 'handle_greeting', 'Handle greeting', 190,
 '{"intent": "greeting", "is_first_message": true}',
 '{"goal": "greeting", "prompt_id": "greeting"}',
 'Respond to greeting'),

-- FAQ/question at any stage
(1, 'handle_faq', 'Handle FAQ', 180,
 '{"intent": "faq"}',
 '{"goal": "answer_faq", "prompt_id": "answer_faq"}',
 'Answer frequently asked question'),

-- === FALLBACK (lowest priority) ===

(1, 'fallback', 'Fallback - unclear input', 10,
 '{}',
 '{"goal": "clarify", "prompt_id": "clarify"}',
 'When nothing else matches, ask for clarification')

ON CONFLICT (vertical_id, rule_code) DO UPDATE SET
    rule_name = EXCLUDED.rule_name,
    priority = EXCLUDED.priority,
    conditions = EXCLUDED.conditions,
    actions = EXCLUDED.actions,
    description = EXCLUDED.description,
    updated_at = NOW();

-- =====================================================
-- 3. ADD PROMPTS FOR DECISION RULES
-- =====================================================

-- Update elo_v_prompts table if needed (add prompt_id column)
ALTER TABLE elo_v_prompts
ADD COLUMN IF NOT EXISTS prompt_id VARCHAR(50);

-- Create unique index on prompt_id
CREATE UNIQUE INDEX IF NOT EXISTS idx_prompts_prompt_id
ON elo_v_prompts(vertical_id, prompt_id) WHERE prompt_id IS NOT NULL;

-- Insert prompts
INSERT INTO elo_v_prompts (vertical_id, prompt_id, goal_type, template, is_active) VALUES

(1, 'ask_device', 'ask_device',
 'Ask the customer what device they have. Be friendly and offer common options like iPhone, Samsung, Xiaomi. Keep it short.',
 true),

(1, 'ask_symptom', 'ask_symptom',
 'The customer has {device.brand} {device.model}. Ask what problem they are experiencing. Use simple language.',
 true),

(1, 'deriving', 'deriving',
 'Processing the request. Please wait.',
 true),

(1, 'present_offer', 'present_offer',
 'Present the repair offer to customer:
Device: {device.brand} {device.model}
Problem: {symptom}
Repair: {repair.name}
Price: {price.min}-{price.max} {price.currency}
Time: {repair.duration}

Ask if they want to book an appointment. Be professional and friendly.',
 true),

(1, 'ask_agreement', 'ask_agreement',
 'Ask the customer if they want to proceed with the repair booking.',
 true),

(1, 'answer_questions', 'answer_questions',
 'Answer the customer questions about the repair. Context: {device.brand} {device.model}, {repair.name}, price {price.min}-{price.max}.',
 true),

(1, 'handle_decline', 'handle_decline',
 'The customer declined. Thank them politely and offer to help if they change their mind.',
 true),

(1, 'ask_booking_date', 'ask_date',
 'Ask the customer what date they prefer for the appointment. Mention that we work Mon-Sat.',
 true),

(1, 'ask_booking_time', 'ask_time',
 'Ask the customer what time works for them on {booking.date}. Working hours: 10:00-19:00.',
 true),

(1, 'ask_booking_name', 'ask_name',
 'Ask for the customer name for the booking.',
 true),

(1, 'ask_booking_phone', 'ask_phone',
 'Ask for the customer phone number to confirm the booking.',
 true),

(1, 'ask_confirmation', 'ask_confirmation',
 'Show booking summary and ask for confirmation:
Name: {booking.name}
Phone: {booking.phone}
Date: {booking.date}
Time: {booking.time}
Device: {device.brand} {device.model}
Repair: {repair.name}
Price: {price.amount} {price.currency}',
 true),

(1, 'booking_confirmed', 'booking_confirmed',
 'Confirm the booking is complete. Thank the customer. Remind them of date, time, and address.',
 true),

(1, 'confirm_cancel', 'confirm_cancel',
 'The customer wants to cancel. Ask for confirmation.',
 true),

(1, 'greeting', 'greeting',
 'Greet the customer warmly. You are a phone repair service assistant. Ask how you can help.',
 true),

(1, 'answer_faq', 'answer_faq',
 'Answer the customer question based on context. Be helpful and concise.',
 true),

(1, 'clarify', 'clarify',
 'The input was unclear. Politely ask the customer to clarify what they need.',
 true)

ON CONFLICT (vertical_id, goal_type) DO UPDATE SET
    prompt_id = EXCLUDED.prompt_id,
    template = EXCLUDED.template,
    updated_at = NOW();

-- =====================================================
-- VERIFICATION
-- =====================================================

-- SELECT rule_code, priority, conditions, actions
-- FROM elo_v_decision_rules
-- WHERE vertical_id = 1
-- ORDER BY priority DESC;
