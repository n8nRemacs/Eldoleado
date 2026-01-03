-- Migration: Universal Funnel Behavior Types
-- Date: 2026-01-03
-- Description: Add behavior types, field collection, binary masks, and CTA actions

-- =====================================================
-- 1. ALTER elo_funnel_stages - add behavior columns
-- =====================================================

ALTER TABLE elo_funnel_stages
ADD COLUMN IF NOT EXISTS behavior_type VARCHAR(30) NOT NULL DEFAULT 'COLLECT_REQUIRED',
ADD COLUMN IF NOT EXISTS behavior_config JSONB DEFAULT '{}',
ADD COLUMN IF NOT EXISTS on_complete_transitions JSONB DEFAULT '[]',
ADD COLUMN IF NOT EXISTS qa_handler_enabled BOOLEAN DEFAULT true,
ADD COLUMN IF NOT EXISTS max_attempts_global INTEGER DEFAULT 3;

COMMENT ON COLUMN elo_funnel_stages.behavior_type IS 'COLLECT_REQUIRED | COLLECT_OPTIONAL | SEND_PROMO | CTA_WAIT';
COMMENT ON COLUMN elo_funnel_stages.behavior_config IS 'Type-specific config: ask_count, timeout_minutes, reminder_intervals, etc.';
COMMENT ON COLUMN elo_funnel_stages.on_complete_transitions IS 'Conditional transitions: [{condition, action, next_stage}]';
COMMENT ON COLUMN elo_funnel_stages.qa_handler_enabled IS 'Enable parallel QA handling on this stage';
COMMENT ON COLUMN elo_funnel_stages.max_attempts_global IS 'Max attempts before escalation';

-- =====================================================
-- 2. CREATE elo_stage_fields - fields to collect per stage
-- =====================================================

CREATE TABLE IF NOT EXISTS elo_stage_fields (
    id SERIAL PRIMARY KEY,
    stage_id INTEGER NOT NULL REFERENCES elo_funnel_stages(id) ON DELETE CASCADE,

    -- Field identification
    field_path VARCHAR(100) NOT NULL,           -- 'device.brand', 'client.phone', 'symptom.description'
    field_name VARCHAR(100) NOT NULL,           -- Human readable name

    -- Collection settings
    is_required BOOLEAN DEFAULT false,
    sort_order INTEGER DEFAULT 0,
    depends_on_field VARCHAR(100),              -- Dependency: only ask if this field is filled

    -- Prompts
    ask_prompt TEXT NOT NULL,                   -- "What is your phone brand?"
    clarify_prompt TEXT,                        -- "I didn't understand, please specify the brand"
    success_prompt TEXT,                        -- "Got it, {value}!"

    -- Extraction
    extraction_type VARCHAR(30) DEFAULT 'ai',   -- ai, regex, buttons, select
    extraction_config JSONB DEFAULT '{}',       -- Type-specific config
    validation_rules JSONB DEFAULT '[]',        -- [{type, params, error_message}]

    -- Attempts handling
    max_attempts INTEGER DEFAULT 3,
    on_max_attempts VARCHAR(30) DEFAULT 'escalate',  -- escalate, use_default, skip
    default_value JSONB,                        -- Default if on_max_attempts = 'use_default'

    -- Graph mapping (optional)
    graph_entity VARCHAR(50),                   -- Neo4j entity: Device, Appeal, Symptom
    graph_property VARCHAR(50),                 -- Neo4j property: brand, model, description

    -- Metadata
    created_at TIMESTAMPTZ DEFAULT NOW(),

    UNIQUE(stage_id, field_path)
);

CREATE INDEX IF NOT EXISTS idx_stage_fields_stage ON elo_stage_fields(stage_id);
CREATE INDEX IF NOT EXISTS idx_stage_fields_required ON elo_stage_fields(stage_id, is_required) WHERE is_required = true;

COMMENT ON TABLE elo_stage_fields IS 'Fields to collect on each funnel stage';

-- =====================================================
-- 3. CREATE elo_dialog_field_tracking - attempt tracking
-- =====================================================

CREATE TABLE IF NOT EXISTS elo_dialog_field_tracking (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    dialog_id UUID NOT NULL REFERENCES elo_t_dialogs(id) ON DELETE CASCADE,
    field_path VARCHAR(100) NOT NULL,

    -- Status
    status VARCHAR(20) DEFAULT 'pending',       -- pending, asking, filled, skipped, default, escalated
    attempt_count INTEGER DEFAULT 0,

    -- History
    responses JSONB DEFAULT '[]',               -- [{message_id, value, extracted_at, confidence}]
    final_value JSONB,
    value_source VARCHAR(20),                   -- extracted, confirmed, default, operator

    -- Timestamps
    first_asked_at TIMESTAMPTZ,
    filled_at TIMESTAMPTZ,

    UNIQUE(dialog_id, field_path)
);

CREATE INDEX IF NOT EXISTS idx_field_tracking_dialog ON elo_dialog_field_tracking(dialog_id);
CREATE INDEX IF NOT EXISTS idx_field_tracking_status ON elo_dialog_field_tracking(dialog_id, status);

COMMENT ON TABLE elo_dialog_field_tracking IS 'Track field collection attempts per dialog (for analytics)';

-- =====================================================
-- 4. CREATE elo_stage_cta_actions - CTA_WAIT actions
-- =====================================================

CREATE TABLE IF NOT EXISTS elo_stage_cta_actions (
    id SERIAL PRIMARY KEY,
    stage_id INTEGER NOT NULL REFERENCES elo_funnel_stages(id) ON DELETE CASCADE,

    -- Action definition
    action_code VARCHAR(50) NOT NULL,           -- agree_to_book, request_callback, decline, fill_form
    action_label VARCHAR(100),                  -- "Yes, book me", "Call me back"

    -- Detection
    detection_type VARCHAR(30) NOT NULL,        -- intent, pattern, event, webhook, button
    detection_config JSONB NOT NULL,            -- Type-specific config

    -- Result
    on_success_transition VARCHAR(50),          -- Next stage code
    on_success_actions JSONB DEFAULT '[]',      -- Actions to execute

    -- Metadata
    sort_order INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,

    UNIQUE(stage_id, action_code)
);

CREATE INDEX IF NOT EXISTS idx_cta_actions_stage ON elo_stage_cta_actions(stage_id);

COMMENT ON TABLE elo_stage_cta_actions IS 'CTA actions for CTA_WAIT behavior type';

-- =====================================================
-- 5. UPDATE existing stages with behavior_type
-- =====================================================

-- System stages: lead
UPDATE elo_funnel_stages SET
    behavior_type = 'COLLECT_REQUIRED',
    behavior_config = '{"auto_advance": true}'::jsonb
WHERE code = 'lead' AND behavior_type = 'COLLECT_REQUIRED';

-- data_collection
UPDATE elo_funnel_stages SET
    behavior_type = 'COLLECT_REQUIRED',
    behavior_config = '{}'::jsonb
WHERE code = 'data_collection';

-- presentation
UPDATE elo_funnel_stages SET
    behavior_type = 'SEND_PROMO',
    behavior_config = '{"auto_advance": true, "template_id": "price_offer"}'::jsonb
WHERE code = 'presentation';

-- agreement
UPDATE elo_funnel_stages SET
    behavior_type = 'CTA_WAIT',
    behavior_config = '{"timeout_minutes": 1440, "reminder_intervals": [60, 240, 720]}'::jsonb
WHERE code = 'agreement';

-- booking
UPDATE elo_funnel_stages SET
    behavior_type = 'COLLECT_REQUIRED',
    behavior_config = '{}'::jsonb,
    on_complete_transitions = '[
        {"condition": {"field": "client.phone", "op": "exists"}, "action": {"type": "send_template", "template_id": "whatsapp_summary"}, "next_stage": "confirmation"},
        {"condition": "default", "next_stage": "confirmation"}
    ]'::jsonb
WHERE code = 'booking';

-- confirmation
UPDATE elo_funnel_stages SET
    behavior_type = 'CTA_WAIT',
    behavior_config = '{"timeout_minutes": 60}'::jsonb
WHERE code = 'confirmation';

-- =====================================================
-- 6. INSERT stage fields for data_collection
-- =====================================================

-- Get data_collection stage id
DO $$
DECLARE
    v_stage_id INTEGER;
BEGIN
    SELECT id INTO v_stage_id FROM elo_funnel_stages WHERE code = 'data_collection' LIMIT 1;

    IF v_stage_id IS NOT NULL THEN
        -- device.brand
        INSERT INTO elo_stage_fields (stage_id, field_path, field_name, is_required, sort_order, ask_prompt, clarify_prompt, graph_entity, graph_property)
        VALUES (v_stage_id, 'device.brand', 'Device Brand', true, 1,
                'What brand is your device? (Apple, Samsung, Xiaomi, etc.)',
                'Please specify the manufacturer of your device',
                'Device', 'brand')
        ON CONFLICT (stage_id, field_path) DO NOTHING;

        -- device.model
        INSERT INTO elo_stage_fields (stage_id, field_path, field_name, is_required, sort_order, depends_on_field, ask_prompt, clarify_prompt, graph_entity, graph_property)
        VALUES (v_stage_id, 'device.model', 'Device Model', false, 2, 'device.brand',
                'What model is it?',
                'Please specify the model (e.g., iPhone 14, Galaxy S23)',
                'Device', 'model')
        ON CONFLICT (stage_id, field_path) DO NOTHING;

        -- symptom.description
        INSERT INTO elo_stage_fields (stage_id, field_path, field_name, is_required, sort_order, ask_prompt, clarify_prompt, graph_entity, graph_property)
        VALUES (v_stage_id, 'symptom.description', 'Problem Description', true, 3,
                'What problem are you experiencing with your device?',
                'Could you describe the issue in more detail?',
                'Symptom', 'description')
        ON CONFLICT (stage_id, field_path) DO NOTHING;
    END IF;
END $$;

-- =====================================================
-- 7. INSERT stage fields for booking
-- =====================================================

DO $$
DECLARE
    v_stage_id INTEGER;
BEGIN
    SELECT id INTO v_stage_id FROM elo_funnel_stages WHERE code = 'booking' LIMIT 1;

    IF v_stage_id IS NOT NULL THEN
        -- client.name
        INSERT INTO elo_stage_fields (stage_id, field_path, field_name, is_required, sort_order, ask_prompt, clarify_prompt)
        VALUES (v_stage_id, 'client.name', 'Client Name', true, 1,
                'What is your name?',
                'Please tell me your name for the booking')
        ON CONFLICT (stage_id, field_path) DO NOTHING;

        -- client.phone
        INSERT INTO elo_stage_fields (stage_id, field_path, field_name, is_required, sort_order, ask_prompt, clarify_prompt, extraction_type, validation_rules)
        VALUES (v_stage_id, 'client.phone', 'Phone Number', true, 2,
                'What is your phone number?',
                'Please provide a valid phone number',
                'regex', '[{"type": "phone", "error_message": "Invalid phone number format"}]'::jsonb)
        ON CONFLICT (stage_id, field_path) DO NOTHING;

        -- booking.date
        INSERT INTO elo_stage_fields (stage_id, field_path, field_name, is_required, sort_order, ask_prompt, clarify_prompt, extraction_type)
        VALUES (v_stage_id, 'booking.date', 'Appointment Date', true, 3,
                'What date would be convenient for you?',
                'Please specify a date (e.g., tomorrow, Monday, January 15)',
                'ai')
        ON CONFLICT (stage_id, field_path) DO NOTHING;

        -- booking.time
        INSERT INTO elo_stage_fields (stage_id, field_path, field_name, is_required, sort_order, depends_on_field, ask_prompt, clarify_prompt, extraction_type)
        VALUES (v_stage_id, 'booking.time', 'Appointment Time', true, 4, 'booking.date',
                'What time works best for you?',
                'Please specify a time (e.g., 10:00, morning, after lunch)',
                'ai')
        ON CONFLICT (stage_id, field_path) DO NOTHING;
    END IF;
END $$;

-- =====================================================
-- 8. INSERT CTA actions for agreement stage
-- =====================================================

DO $$
DECLARE
    v_stage_id INTEGER;
BEGIN
    SELECT id INTO v_stage_id FROM elo_funnel_stages WHERE code = 'agreement' LIMIT 1;

    IF v_stage_id IS NOT NULL THEN
        -- Agree to book
        INSERT INTO elo_stage_cta_actions (stage_id, action_code, action_label, detection_type, detection_config, on_success_transition, sort_order)
        VALUES (v_stage_id, 'agree_to_book', 'Yes, book me', 'intent',
                '{"intents": ["agree", "book", "yes", "ok", "lets_do_it"]}'::jsonb,
                'booking', 1)
        ON CONFLICT (stage_id, action_code) DO NOTHING;

        -- Request callback
        INSERT INTO elo_stage_cta_actions (stage_id, action_code, action_label, detection_type, detection_config, on_success_transition, on_success_actions, sort_order)
        VALUES (v_stage_id, 'request_callback', 'Call me back', 'intent',
                '{"intents": ["callback", "call_me", "phone_call"]}'::jsonb,
                'callback_scheduled',
                '[{"type": "create_task", "task_type": "callback"}]'::jsonb, 2)
        ON CONFLICT (stage_id, action_code) DO NOTHING;

        -- Decline
        INSERT INTO elo_stage_cta_actions (stage_id, action_code, action_label, detection_type, detection_config, on_success_transition, sort_order)
        VALUES (v_stage_id, 'decline', 'No, thanks', 'intent',
                '{"intents": ["no", "decline", "not_interested", "too_expensive"]}'::jsonb,
                'objection_handling', 3)
        ON CONFLICT (stage_id, action_code) DO NOTHING;
    END IF;
END $$;

-- =====================================================
-- 9. INSERT CTA actions for confirmation stage
-- =====================================================

DO $$
DECLARE
    v_stage_id INTEGER;
BEGIN
    SELECT id INTO v_stage_id FROM elo_funnel_stages WHERE code = 'confirmation' LIMIT 1;

    IF v_stage_id IS NOT NULL THEN
        INSERT INTO elo_stage_cta_actions (stage_id, action_code, action_label, detection_type, detection_config, on_success_transition, on_success_actions, sort_order)
        VALUES (v_stage_id, 'confirm', 'Confirm booking', 'intent',
                '{"intents": ["confirm", "yes", "correct", "all_good"]}'::jsonb,
                'done',
                '[{"type": "create_intake"}, {"type": "send_template", "template_id": "booking_confirmed"}]'::jsonb, 1)
        ON CONFLICT (stage_id, action_code) DO NOTHING;

        INSERT INTO elo_stage_cta_actions (stage_id, action_code, action_label, detection_type, detection_config, on_success_transition, sort_order)
        VALUES (v_stage_id, 'change', 'Change something', 'intent',
                '{"intents": ["change", "modify", "wrong", "different"]}'::jsonb,
                'booking', 2)
        ON CONFLICT (stage_id, action_code) DO NOTHING;
    END IF;
END $$;

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================

-- Check behavior types
-- SELECT code, behavior_type, behavior_config FROM elo_funnel_stages ORDER BY sort_order;

-- Check stage fields
-- SELECT sf.field_path, sf.is_required, sf.sort_order, fs.code as stage_code
-- FROM elo_stage_fields sf
-- JOIN elo_funnel_stages fs ON fs.id = sf.stage_id
-- ORDER BY fs.sort_order, sf.sort_order;

-- Check CTA actions
-- SELECT ca.action_code, ca.detection_type, fs.code as stage_code
-- FROM elo_stage_cta_actions ca
-- JOIN elo_funnel_stages fs ON fs.id = ca.stage_id
-- ORDER BY fs.sort_order, ca.sort_order;
