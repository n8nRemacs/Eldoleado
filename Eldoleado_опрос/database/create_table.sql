-- =====================================================
-- Eldoleado Survey - Database Table
-- Таблица для хранения ответов на опрос владельцев мастерских
-- =====================================================

-- Создание таблицы
CREATE TABLE IF NOT EXISTS eldoleado_survey_responses (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMPTZ DEFAULT NOW(),

    -- ===== БЛОК 1: О мастерской =====
    q1_workshop_age TEXT,                    -- Как давно работает мастерская
    q2_employees TEXT,                       -- Сколько человек работает
    q3_clients_per_month TEXT,               -- Сколько клиентов в месяц
    q4_average_check TEXT,                   -- Средний чек
    q5_city TEXT,                            -- Город

    -- ===== БЛОК 2: Каналы связи =====
    q6_channels TEXT[],                      -- Через какие каналы пишут клиенты (массив)
    q6_channels_other TEXT,                  -- Другой канал (если указано)
    q7_main_channel TEXT,                    -- Самый частый канал
    q7_main_channel_other TEXT,              -- Другой основной канал
    q8_channel_confusion TEXT,               -- Бывает путаница
    q9_confusion_problem_score INT,          -- Насколько проблема (1-10)

    -- ===== БЛОК 3: Нерабочее время =====
    q10_after_hours_frequency TEXT,          -- Как часто пишут вечером/ночью
    q11_after_hours_handling TEXT,           -- Что делают с сообщениями
    q12_lost_clients_slow_response TEXT,     -- Теряли клиентов
    q13_after_hours_problem_score INT,       -- Насколько проблема (1-10)

    -- ===== БЛОК 4: Звонки =====
    q14_calls_per_day TEXT,                  -- Сколько звонков в день
    q15_call_logging TEXT,                   -- Где фиксируют результат звонка
    q16_forget_agreements TEXT,              -- Забывают договорённости
    q17_record_calls TEXT,                   -- Записывают звонки
    q18_calls_problem_score INT,             -- Насколько проблема (1-10)

    -- ===== БЛОК 5: Прайсы и поставщики =====
    q19_suppliers_count TEXT,                -- Сколько поставщиков
    q20_search_parts TEXT,                   -- Как ищут запчасти
    q20_search_parts_other TEXT,             -- Другой способ поиска
    q21_time_to_compare TEXT,                -- Время на сравнение цен
    q22_price_mismatch TEXT,                 -- Называли цену дороже
    q23_suppliers_problem_score INT,         -- Насколько проблема (1-10)

    -- ===== БЛОК 6: Маркетинг =====
    q24_client_sources TEXT[],               -- Откуда приходят клиенты (массив)
    q24_client_sources_other TEXT,           -- Другой источник
    q25_ad_budget TEXT,                      -- Бюджет на рекламу
    q26_has_marketer TEXT,                   -- Есть ли маркетолог
    q27_marketer_cost TEXT,                  -- Сколько платят маркетологу
    q28_marketer_satisfaction TEXT,          -- Довольны ли работой
    q29_track_conversion TEXT,               -- Считают конверсию
    q30_knows_cac BOOLEAN,                   -- Знают CAC
    q30_cac_amount INT,                      -- Стоимость привлечения клиента
    q31_marketing_problem_score INT,         -- Насколько проблема (1-10)

    -- ===== БЛОК 7: Удержание =====
    q32_repeat_clients TEXT,                 -- Процент повторных клиентов
    q33_client_database TEXT,                -- Как ведут базу клиентов
    q33_crm_name TEXT,                       -- Название CRM
    q34_remind_clients TEXT,                 -- Напоминают о себе
    q35_reminder_method TEXT,                -- Как напоминают
    q36_retention_problem_score INT,         -- Насколько проблема (1-10)

    -- ===== БЛОК 8: Главные боли (ранжирование) =====
    q37_rank_messengers INT,                 -- Ранг: разные мессенджеры
    q37_rank_night_messages INT,             -- Ранг: ночные сообщения
    q37_rank_suppliers INT,                  -- Ранг: прайсы поставщиков
    q37_rank_phone_agreements INT,           -- Ранг: забываю договорённости
    q37_rank_advertising INT,                -- Ранг: реклама
    q37_rank_retention INT,                  -- Ранг: удержание клиентов
    q38_magic_wand TEXT,                     -- Волшебная палочка - какую проблему решить
    q39_time_waster TEXT,                    -- Что отнимает больше всего времени

    -- ===== БЛОК 9: Готовность платить =====
    q40_current_software_spend TEXT,         -- Сколько тратят на софт
    q41_willingness_to_pay TEXT,             -- Готовность платить за сервис
    q42_important_function TEXT,             -- Самая важная функция
    q42_function_price INT,                  -- Цена за функцию
    q43_too_expensive INT,                   -- Слишком дорого (₽)
    q44_suspiciously_cheap INT,              -- Подозрительно дёшево (₽)

    -- ===== БЛОК 10: Завершение =====
    q45_tried_crm TEXT,                      -- Пробовали CRM/ботов
    q45_quit_reason TEXT,                    -- Почему бросили
    q46_must_have_features TEXT,             -- Что должен делать сервис
    q47_want_beta TEXT,                      -- Хотят участвовать в тестировании
    q48_contact TEXT,                        -- Контакт для связи
    q49_referrals TEXT,                      -- Рекомендации знакомых

    -- ===== Мета-данные =====
    ip_address TEXT,                         -- IP адрес
    user_agent TEXT,                         -- User Agent браузера
    completion_time_seconds INT              -- Время заполнения в секундах
);

-- Индексы для быстрого поиска
CREATE INDEX IF NOT EXISTS idx_survey_created_at ON eldoleado_survey_responses(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_survey_city ON eldoleado_survey_responses(q5_city);
CREATE INDEX IF NOT EXISTS idx_survey_want_beta ON eldoleado_survey_responses(q47_want_beta);

-- Комментарий к таблице
COMMENT ON TABLE eldoleado_survey_responses IS 'Ответы на опрос Eldoleado для владельцев сервисных мастерских';

-- =====================================================
-- Полезные запросы для анализа
-- =====================================================

-- Общая статистика
-- SELECT
--     COUNT(*) as total_responses,
--     COUNT(DISTINCT q5_city) as unique_cities,
--     COUNT(*) FILTER (WHERE q47_want_beta = 'yes') as want_beta,
--     AVG(completion_time_seconds) as avg_completion_time
-- FROM eldoleado_survey_responses;

-- Распределение по городам
-- SELECT q5_city, COUNT(*) as count
-- FROM eldoleado_survey_responses
-- GROUP BY q5_city
-- ORDER BY count DESC;

-- Средние оценки проблем
-- SELECT
--     AVG(q9_confusion_problem_score) as channels_problem,
--     AVG(q13_after_hours_problem_score) as after_hours_problem,
--     AVG(q18_calls_problem_score) as calls_problem,
--     AVG(q23_suppliers_problem_score) as suppliers_problem,
--     AVG(q31_marketing_problem_score) as marketing_problem,
--     AVG(q36_retention_problem_score) as retention_problem
-- FROM eldoleado_survey_responses;

-- Топ проблемы (по рангу 1)
-- SELECT
--     COUNT(*) FILTER (WHERE q37_rank_messengers = 1) as messengers_top,
--     COUNT(*) FILTER (WHERE q37_rank_night_messages = 1) as night_top,
--     COUNT(*) FILTER (WHERE q37_rank_suppliers = 1) as suppliers_top,
--     COUNT(*) FILTER (WHERE q37_rank_phone_agreements = 1) as phone_top,
--     COUNT(*) FILTER (WHERE q37_rank_advertising = 1) as advertising_top,
--     COUNT(*) FILTER (WHERE q37_rank_retention = 1) as retention_top
-- FROM eldoleado_survey_responses;

-- Готовность платить
-- SELECT q41_willingness_to_pay, COUNT(*) as count
-- FROM eldoleado_survey_responses
-- GROUP BY q41_willingness_to_pay
-- ORDER BY count DESC;

-- Экспорт для AI анализа (полный JSON)
-- SELECT jsonb_agg(to_jsonb(r))
-- FROM eldoleado_survey_responses r;
