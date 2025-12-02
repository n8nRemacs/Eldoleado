-- ============================================================================
-- Migration 007 Part 5: AI & Voice Processing (2 tables)
-- Date: 2025-11-23
-- Description: AI chat memory and voice transcriptions
-- ============================================================================

-- ============================================================================
-- 1. ai_chat_memory — Память AI (контекст диалогов)
-- ============================================================================

CREATE TABLE IF NOT EXISTS ai_chat_memory (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
    role varchar(50) NOT NULL,
    content text NOT NULL,
    ai_model varchar(100),
    tokens_used int,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_ai_role CHECK (role IN ('user', 'assistant', 'system'))
);

CREATE INDEX IF NOT EXISTS idx_ai_chat_memory_appeal ON ai_chat_memory(appeal_id);
CREATE INDEX IF NOT EXISTS idx_ai_chat_memory_tenant ON ai_chat_memory(tenant_id);
CREATE INDEX IF NOT EXISTS idx_ai_chat_memory_created ON ai_chat_memory(created_at);

COMMENT ON TABLE ai_chat_memory IS 'Хранение истории AI-обработки для контекстных запросов';

-- ============================================================================
-- 2. voice_transcriptions — Транскрибация голосовых и звонков
-- ============================================================================

CREATE TABLE IF NOT EXISTS voice_transcriptions (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    appeal_id uuid NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
    message_id uuid REFERENCES messages_history(id),
    source_type varchar(50) NOT NULL,
    audio_url text,
    audio_duration_sec int,
    transcription_text text NOT NULL,
    transcription_engine varchar(50) DEFAULT 'whisper',
    language varchar(10) DEFAULT 'ru',
    confidence numeric(3, 2),
    processing_time_ms int,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_source_type CHECK (source_type IN ('voice_message', 'incoming_call', 'outgoing_call', 'operator_voice_response'))
);

CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_appeal ON voice_transcriptions(appeal_id);
CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_tenant ON voice_transcriptions(tenant_id);
CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_message ON voice_transcriptions(message_id);
CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_source ON voice_transcriptions(source_type);
CREATE INDEX IF NOT EXISTS idx_voice_transcriptions_created ON voice_transcriptions(created_at);

COMMENT ON TABLE voice_transcriptions IS 'Транскрибация голосовых сообщений и телефонных звонков';

-- ============================================================================
-- Migration complete: Part 5
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 007 Part 5 completed successfully: AI & Voice Processing (2 tables)';
END $$;
