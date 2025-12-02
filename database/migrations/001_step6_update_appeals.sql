-- ============================================================================
-- ШАГ 6: Обновление таблицы appeals (добавление новых полей)
-- ============================================================================

ALTER TABLE appeals ADD COLUMN IF NOT EXISTS conversation_context JSONB DEFAULT '{}';
ALTER TABLE appeals ADD COLUMN IF NOT EXISTS context_completion_status JSONB DEFAULT '{}';
ALTER TABLE appeals ADD COLUMN IF NOT EXISTS conversation_focus JSONB DEFAULT '{}';
ALTER TABLE appeals ADD COLUMN IF NOT EXISTS last_greeting_at TIMESTAMPTZ;

COMMENT ON COLUMN appeals.conversation_context IS 'Контекст диалога: упомянутые устройства, история переключений, clarifications';
COMMENT ON COLUMN appeals.context_completion_status IS 'Статус заполнения: fields_collected, required_fields_count, completion_percentage';
COMMENT ON COLUMN appeals.conversation_focus IS 'Текущий фокус: current_device_id, current_repair_id, focus_history, last_updated_by, auto_switch';
COMMENT ON COLUMN appeals.last_greeting_at IS 'Время последнего приветствия (для проверки нужно ли здороваться снова)';

SELECT 'Таблица appeals обновлена (4 новых поля)' as result;
