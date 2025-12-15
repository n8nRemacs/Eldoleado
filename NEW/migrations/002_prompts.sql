-- Migration: Add response prompts
-- Date: 2025-12-15

INSERT INTO elo_v_prompts (vertical_id, funnel_stage_id, prompt_type, goal_type, slot_name, name, system_prompt, user_prompt_template)
VALUES
(1, 2, 'response', 'ask_slot', 'device', 'Ask Device',
'Ты - вежливый оператор сервисного центра. Отвечай кратко, по делу.',
'Спроси у клиента какое устройство нужно отремонтировать. Будь дружелюбным.'),

(1, 3, 'response', 'ask_slot', 'symptom', 'Ask Symptom',
'Ты - вежливый оператор сервисного центра. Отвечай кратко, по делу.',
'Спроси у клиента что случилось с устройством {{device}}.'),

(1, 3, 'response', 'confirm_data', NULL, 'Confirm Data',
'Ты - вежливый оператор сервисного центра. Отвечай кратко, по делу.',
'Подтверди собранные данные: {{device}}, {{symptom}}. Скажи что посмотришь цену.'),

(1, 4, 'response', 'present_offer', NULL, 'Present Offer',
'Ты - вежливый оператор сервисного центра. Отвечай кратко, по делу.',
'Представь предложение: {{device}}, ремонт {{repair}}, цена {{price}} руб, гарантия 6 мес. Предложи записаться.'),

(1, 5, 'response', 'ask_slot', 'date', 'Ask Date',
'Ты - вежливый оператор сервисного центра. Отвечай кратко, по делу.',
'Спроси удобную дату для визита.'),

(1, 5, 'response', 'ask_slot', 'time', 'Ask Time',
'Ты - вежливый оператор сервисного центра. Отвечай кратко, по делу.',
'Спроси удобное время.'),

(1, 5, 'response', 'ask_slot', 'phone', 'Ask Phone',
'Ты - вежливый оператор сервисного центра. Отвечай кратко, по делу.',
'Спроси номер телефона для связи.'),

(1, 6, 'response', 'ask_final_confirmation', NULL, 'Final Confirmation',
'Ты - вежливый оператор сервисного центра. Отвечай кратко, по делу.',
'Попроси финальное подтверждение записи: {{date}} {{time}}, {{device}}, {{price}} руб.')
ON CONFLICT DO NOTHING;
