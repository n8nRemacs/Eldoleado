-- ============================================================================
-- H/V SPLIT Migration 006: Horizontal AI & Voice
-- Date: 2025-11-25
-- Description: AI chat memory, voice transcriptions, AI worker queue
-- ============================================================================

SET search_path TO horizontal, public;

-- ============================================================================
-- 1. ai_chat_memory - AI conversation context
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.ai_chat_memory (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES horizontal.appeals(id) ON DELETE CASCADE,

    -- Message
    role varchar(50) NOT NULL,
    content text NOT NULL,

    -- AI model info
    ai_model varchar(100),
    tokens_used int,

    -- Vertical context
    vertical_id varchar(50),

    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_ai_role CHECK (role IN ('user', 'assistant', 'system', 'function'))
);

CREATE INDEX IF NOT EXISTS idx_ai_chat_memory_appeal ON horizontal.ai_chat_memory(appeal_id);
CREATE INDEX IF NOT EXISTS idx_ai_chat_memory_tenant ON horizontal.ai_chat_memory(tenant_id);
CREATE INDEX IF NOT EXISTS idx_ai_chat_memory_created ON horizontal.ai_chat_memory(created_at);

COMMENT ON TABLE horizontal.ai_chat_memory IS 'AI conversation context for contextual processing';

-- ============================================================================
-- 2. voice_transcriptions - Voice message and call transcriptions
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.voice_transcriptions (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES horizontal.appeals(id) ON DELETE CASCADE,
    message_id uuid REFERENCES horizontal.messages_history(id),

    -- Source
    source_type varchar(50) NOT NULL,

    -- Audio
    audio_url text,
    audio_duration_sec int,

    -- Transcription
    transcription_text text NOT NULL,
    transcription_engine varchar(50) DEFAULT 'whisper',
    language varchar(10) DEFAULT 'ru',
    confidence numeric(3, 2),

    -- Processing
    processing_time_ms int,
    processing_cost numeric(10, 6),

    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_source_type CHECK (source_type IN ('voice_message', 'incoming_call', 'outgoing_call', 'operator_voice_response'))
);

CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_appeal ON horizontal.voice_transcriptions(appeal_id);
CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_tenant ON horizontal.voice_transcriptions(tenant_id);
CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_message ON horizontal.voice_transcriptions(message_id);
CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_source ON horizontal.voice_transcriptions(source_type);
CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_created ON horizontal.voice_transcriptions(created_at);

COMMENT ON TABLE horizontal.voice_transcriptions IS 'Voice message and call transcriptions';

-- ============================================================================
-- 3. ai_extraction_tasks - AI worker queue
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.ai_extraction_tasks (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES horizontal.appeals(id) ON DELETE CASCADE,
    message_id uuid REFERENCES horizontal.messages_history(id),

    -- Task type
    task_type varchar(50) NOT NULL,
    vertical_id varchar(50) NOT NULL,

    -- Input
    input_text text NOT NULL,
    input_context jsonb,

    -- Prompt (from vertical config)
    prompt_template_id varchar(100),

    -- Status
    status varchar(50) DEFAULT 'pending',
    priority int DEFAULT 0,

    -- Result
    result jsonb,
    error_message text,

    -- Worker
    worker_id varchar(100),
    started_at timestamptz,
    completed_at timestamptz,

    -- Timing
    processing_time_ms int,
    tokens_used int,

    -- Retry
    retry_count int DEFAULT 0,
    max_retries int DEFAULT 3,

    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_task_type CHECK (task_type IN ('entity_extraction', 'classification', 'response_generation', 'summary', 'custom')),
    CONSTRAINT chk_task_status CHECK (status IN ('pending', 'processing', 'completed', 'failed', 'cancelled'))
);

CREATE INDEX IF NOT EXISTS idx_ai_tasks_tenant ON horizontal.ai_extraction_tasks(tenant_id);
CREATE INDEX IF NOT EXISTS idx_ai_tasks_appeal ON horizontal.ai_extraction_tasks(appeal_id);
CREATE INDEX IF NOT EXISTS idx_ai_tasks_status ON horizontal.ai_extraction_tasks(status);
CREATE INDEX IF NOT EXISTS idx_ai_tasks_pending ON horizontal.ai_extraction_tasks(priority DESC, created_at) WHERE status = 'pending';
CREATE INDEX IF NOT EXISTS idx_ai_tasks_created ON horizontal.ai_extraction_tasks(created_at);

COMMENT ON TABLE horizontal.ai_extraction_tasks IS 'AI worker queue for entity extraction and processing';

-- ============================================================================
-- 4. ai_prompts_config - AI prompts configuration (universal)
-- ============================================================================

CREATE TABLE IF NOT EXISTS horizontal.ai_prompts_config (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Prompt identity
    prompt_id varchar(100) NOT NULL,
    vertical_id varchar(50),

    -- Prompt content
    name varchar(255) NOT NULL,
    description text,
    system_prompt text,
    user_prompt_template text,

    -- Model settings
    model varchar(100) DEFAULT 'gpt-4o-mini',
    temperature numeric(2, 1) DEFAULT 0.3,
    max_tokens int DEFAULT 4096,

    -- Output schema (JSON Schema)
    output_schema jsonb,

    -- Version
    version int DEFAULT 1,
    is_active boolean DEFAULT true,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_prompt_id UNIQUE (tenant_id, prompt_id, vertical_id)
);

CREATE INDEX IF NOT EXISTS idx_ai_prompts_tenant ON horizontal.ai_prompts_config(tenant_id);
CREATE INDEX IF NOT EXISTS idx_ai_prompts_vertical ON horizontal.ai_prompts_config(vertical_id);
CREATE INDEX IF NOT EXISTS idx_ai_prompts_prompt_id ON horizontal.ai_prompts_config(prompt_id);

COMMENT ON TABLE horizontal.ai_prompts_config IS 'AI prompts configuration, can be overridden per tenant/vertical';
COMMENT ON COLUMN horizontal.ai_prompts_config.tenant_id IS 'NULL = global default prompt';

-- ============================================================================
-- 5. Insert default AI prompts
-- ============================================================================

INSERT INTO horizontal.ai_prompts_config (tenant_id, prompt_id, vertical_id, name, description, system_prompt, model)
VALUES
(NULL, 'appeal_type_classifier', NULL, 'Appeal Type Classifier', 'Classify appeal type: repair, consultation, sale, purchase, spam',
'You are a classifier for customer service requests. Classify the message into one of: repair, consultation, sale, purchase, spam. Return JSON: {"type": "...", "confidence": 0.0-1.0}', 'gpt-4o-mini'),

(NULL, 'multi_entity_extractor', 'repair', 'Multi-Entity Extractor (Repair)', 'Extract devices and repairs from customer message',
'Extract all devices and their issues from the customer message. Return JSON with devices array, each containing brand, model, repairs array.', 'gpt-4o-mini'),

(NULL, 'response_formatter', NULL, 'Response Formatter', 'Format operator voice response',
'Format the raw text into a polite, professional customer service response. Keep the meaning, improve grammar and style.', 'gpt-4o-mini')

ON CONFLICT DO NOTHING;

-- ============================================================================
-- Migration complete: 006
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 006 completed: Horizontal AI & Voice processing';
END $$;
