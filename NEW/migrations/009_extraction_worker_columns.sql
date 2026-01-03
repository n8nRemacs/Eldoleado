-- Migration: Add extraction worker columns to context_types tables
-- Date: 2026-01-03
-- Description: Columns for blind worker extraction (prompt, examples, confidence)

-- =====================================================
-- 1. ALTER elo_context_types (Global)
-- =====================================================

ALTER TABLE elo_context_types
ADD COLUMN IF NOT EXISTS extraction_prompt TEXT,
ADD COLUMN IF NOT EXISTS examples JSONB DEFAULT '[]',
ADD COLUMN IF NOT EXISTS negative_examples JSONB DEFAULT '[]',
ADD COLUMN IF NOT EXISTS min_confidence DECIMAL(3,2) DEFAULT 0.7,
ADD COLUMN IF NOT EXISTS model_preference VARCHAR(20) DEFAULT 'fast';

COMMENT ON COLUMN elo_context_types.extraction_prompt IS 'Prompt template for LLM extraction. Use {{message}} placeholder.';
COMMENT ON COLUMN elo_context_types.examples IS 'Few-shot examples: [{input, output}]';
COMMENT ON COLUMN elo_context_types.negative_examples IS 'What NOT to extract: [{input, reason}]';
COMMENT ON COLUMN elo_context_types.min_confidence IS 'Minimum confidence threshold (0-1)';
COMMENT ON COLUMN elo_context_types.model_preference IS 'fast (gpt-4o-mini) or accurate (gpt-4o)';

-- =====================================================
-- 2. ALTER elo_d_context_types (Domain)
-- =====================================================

ALTER TABLE elo_d_context_types
ADD COLUMN IF NOT EXISTS extraction_prompt TEXT,
ADD COLUMN IF NOT EXISTS examples JSONB DEFAULT '[]',
ADD COLUMN IF NOT EXISTS negative_examples JSONB DEFAULT '[]',
ADD COLUMN IF NOT EXISTS min_confidence DECIMAL(3,2) DEFAULT 0.8,
ADD COLUMN IF NOT EXISTS model_preference VARCHAR(20) DEFAULT 'fast';

COMMENT ON COLUMN elo_d_context_types.extraction_prompt IS 'Prompt template for LLM extraction';
COMMENT ON COLUMN elo_d_context_types.examples IS 'Few-shot examples';
COMMENT ON COLUMN elo_d_context_types.negative_examples IS 'What NOT to extract';
COMMENT ON COLUMN elo_d_context_types.min_confidence IS 'Minimum confidence (domain usually higher)';
COMMENT ON COLUMN elo_d_context_types.model_preference IS 'fast or accurate';

-- =====================================================
-- 3. ALTER elo_v_context_types (Vertical)
-- =====================================================

ALTER TABLE elo_v_context_types
ADD COLUMN IF NOT EXISTS extraction_prompt TEXT,
ADD COLUMN IF NOT EXISTS examples JSONB DEFAULT '[]',
ADD COLUMN IF NOT EXISTS negative_examples JSONB DEFAULT '[]',
ADD COLUMN IF NOT EXISTS min_confidence DECIMAL(3,2) DEFAULT 0.8,
ADD COLUMN IF NOT EXISTS model_preference VARCHAR(20) DEFAULT 'fast';

COMMENT ON COLUMN elo_v_context_types.extraction_prompt IS 'Prompt template for LLM extraction';

-- =====================================================
-- 4. SEED: Global context types extraction prompts
-- =====================================================

-- Intent
UPDATE elo_context_types SET
  extraction_prompt = 'Classify the intent of this message. Return ONLY one of: question, complaint, greeting, thanks, booking, cancel, clarify, other.

Return JSON: {"intent": "...", "confidence": 0.0-1.0}',
  examples = '[
    {"input": "Привет", "output": {"intent": "greeting", "confidence": 0.99}},
    {"input": "Сколько стоит ремонт?", "output": {"intent": "question", "confidence": 0.95}},
    {"input": "Мой телефон не работает", "output": {"intent": "complaint", "confidence": 0.9}},
    {"input": "Спасибо за помощь", "output": {"intent": "thanks", "confidence": 0.95}},
    {"input": "Хочу записаться", "output": {"intent": "booking", "confidence": 0.9}}
  ]'::jsonb,
  min_confidence = 0.7,
  model_preference = 'fast'
WHERE code = 'intent';

-- Sentiment (if exists)
UPDATE elo_context_types SET
  extraction_prompt = 'Determine the sentiment of this message: positive, negative, or neutral.

Return JSON: {"sentiment": "...", "confidence": 0.0-1.0}',
  examples = '[
    {"input": "Отлично, спасибо!", "output": {"sentiment": "positive", "confidence": 0.95}},
    {"input": "Это ужасно, ничего не работает", "output": {"sentiment": "negative", "confidence": 0.9}},
    {"input": "Окей, понял", "output": {"sentiment": "neutral", "confidence": 0.85}}
  ]'::jsonb,
  min_confidence = 0.6,
  model_preference = 'fast'
WHERE code = 'sentiment';

-- Urgency (if exists)
UPDATE elo_context_types SET
  extraction_prompt = 'Determine urgency level: low, medium, high, critical. Return null if urgency is not mentioned.

Return JSON: {"urgency": "..." or null, "confidence": 0.0-1.0}',
  examples = '[
    {"input": "Когда удобно", "output": {"urgency": "low", "confidence": 0.8}},
    {"input": "Нужно сегодня", "output": {"urgency": "high", "confidence": 0.9}},
    {"input": "СРОЧНО!!!", "output": {"urgency": "critical", "confidence": 0.95}}
  ]'::jsonb,
  negative_examples = '[
    {"input": "Привет, как дела?", "reason": "No urgency mentioned"}
  ]'::jsonb,
  min_confidence = 0.7,
  model_preference = 'fast'
WHERE code = 'urgency';

-- =====================================================
-- 5. SEED: Domain context types (electronics)
-- =====================================================

-- Device extraction
UPDATE elo_d_context_types SET
  extraction_prompt = 'Extract device information from the message. If NO device is mentioned, return null.

Look for: brand (Apple, Samsung, Xiaomi, Huawei, etc.), model (iPhone 14, Galaxy S23, etc.), type (phone, tablet, laptop, watch).

Return JSON: {"brand": "...", "model": "...", "type": "...", "confidence": 0.0-1.0} or null',
  examples = '[
    {"input": "iPhone 14 Pro не включается", "output": {"brand": "Apple", "model": "iPhone 14 Pro", "type": "phone", "confidence": 0.98}},
    {"input": "Самсунг галакси с23", "output": {"brand": "Samsung", "model": "Galaxy S23", "type": "phone", "confidence": 0.95}},
    {"input": "Мой ноутбук", "output": {"brand": null, "model": null, "type": "laptop", "confidence": 0.7}}
  ]'::jsonb,
  negative_examples = '[
    {"input": "Сколько стоит ремонт?", "reason": "No device mentioned"},
    {"input": "Привет", "reason": "No device mentioned"}
  ]'::jsonb,
  min_confidence = 0.8,
  model_preference = 'fast'
WHERE code = 'device';

-- =====================================================
-- 6. SEED: Vertical context types (repair)
-- =====================================================

-- Symptom extraction
UPDATE elo_v_context_types SET
  extraction_prompt = 'Extract the problem/symptom from the message. Focus on WHAT is broken, not the device itself.

Return JSON: {"description": "...", "category": "...", "confidence": 0.0-1.0} or null

Categories: battery, screen, charging, software, water_damage, physical_damage, audio, camera, connectivity, other',
  examples = '[
    {"input": "не заряжается", "output": {"description": "не заряжается", "category": "charging", "confidence": 0.95}},
    {"input": "разбил экран", "output": {"description": "разбитый экран", "category": "screen", "confidence": 0.98}},
    {"input": "быстро садится батарея", "output": {"description": "быстро разряжается", "category": "battery", "confidence": 0.9}},
    {"input": "упал в воду", "output": {"description": "попала вода", "category": "water_damage", "confidence": 0.95}}
  ]'::jsonb,
  negative_examples = '[
    {"input": "iPhone 14", "reason": "No symptom, just device"},
    {"input": "Сколько стоит?", "reason": "No symptom described"}
  ]'::jsonb,
  min_confidence = 0.8,
  model_preference = 'fast'
WHERE code = 'symptom';

-- =====================================================
-- VERIFICATION QUERIES
-- =====================================================

-- Check global context types
-- SELECT code, extraction_prompt IS NOT NULL as has_prompt, min_confidence
-- FROM elo_context_types WHERE is_active = true;

-- Check domain context types
-- SELECT d.code as domain, ct.code, ct.extraction_prompt IS NOT NULL as has_prompt
-- FROM elo_d_context_types ct
-- JOIN elo_domains d ON d.id = ct.domain_id
-- WHERE ct.is_active = true;

-- Check vertical context types
-- SELECT v.code as vertical, ct.code, ct.extraction_prompt IS NOT NULL as has_prompt
-- FROM elo_v_context_types ct
-- JOIN elo_verticals v ON v.id = ct.vertical_id
-- WHERE ct.is_active = true;
