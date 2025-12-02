# BatteryCRM - База данных: Расширенная схема

**Версия:** 2.0
**Дата:** 2025-11-23
**Статус:** Актуальная спецификация (37 существующих + 13 новых таблиц)

---

## Содержание

1. [Обзор](#обзор)
2. [Multi-Tenant Support](#multi-tenant-support)
3. [Клиенты и объединения](#клиенты-и-объединения)
4. [Заявки и сообщения](#заявки-и-сообщения)
5. [Мультизаявки (NEW)](#мультизаявки-new)
6. [Воронка обработки (NEW)](#воронка-обработки-new)
7. [Операторы и сессии](#операторы-и-сессии)
8. [Каналы и маркетинг](#каналы-и-маркетинг)
9. [Справочники устройств и услуг](#справочники-устройств-и-услуг)
10. [Промо-материалы (NEW)](#промо-материалы-new)
11. [Прайсы и расчёт стоимости (NEW)](#прайсы-и-расчёт-стоимости-new)
12. [AI обработка и транскрибация (NEW)](#ai-обработка-и-транскрибация-new)
13. [Интеграции с CRM (NEW)](#интеграции-с-crm-new)
14. [TTL политики и архивация (NEW)](#ttl-политики-и-архивация-new)
15. [Индексы и оптимизация](#индексы-и-оптимизация)
16. [Миграции](#миграции)

---

## Обзор

### Общая информация

**Текущее состояние:**
- **37 таблиц** — существующая схема (v5.1)
- **13 новых таблиц** — расширение для полного функционала
- **Всего: 50 таблиц**

**Принципы проектирования:**
1. **Multi-Tenant изоляция** — все основные таблицы содержат `tenant_id`
2. **Нормализация (3NF)** — минимизация дублирования данных
3. **Гибридный подход** — совместимость старой и новой структуры
4. **Расширяемость** — `jsonb` для динамических параметров
5. **Производительность** — индексы на все FK и частые фильтры

### Легенда обозначений

- **[EXISTING]** — таблица уже реализована
- **[NEW]** — новая таблица
- **[MODIFIED]** — существующая таблица с новыми полями
- **[DEPRECATED]** — устаревшие поля (сохранены для совместимости)

---

## Multi-Tenant Support

### tenants — Тенанты (сервисные центры) [EXISTING]

**Назначение:** Изоляция данных между разными сервисными центрами (SaaS)

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID тенанта |
| name | varchar | NOT NULL | Название компании |
| domain | varchar | NULL | Домен компании |
| external_id | text | NULL | ID во внешней системе |
| jwt_secret | text | NULL | JWT секрет |
| jwt_jwks | jsonb | NULL | JWT JWKS конфигурация |
| is_active | boolean | DEFAULT true | Активен ли тенант |
| suspend | boolean | DEFAULT false | Приостановлен ли |
| max_concurrent_users | int | DEFAULT 200 | Макс. одновременных пользователей |
| max_events_per_second | int | DEFAULT 100 | Rate limit событий |
| max_bytes_per_second | int | DEFAULT 100000 | Bandwidth limit |
| max_channels_per_client | int | DEFAULT 100 | Макс. каналов на клиента |
| max_joins_per_second | int | DEFAULT 500 | Rate limit подключений |
| postgres_cdc_default | text | DEFAULT 'postgres_cdc_rls' | CDC конфигурация |
| notify_private_alpha | boolean | DEFAULT false | Приватные уведомления |
| private_only | boolean | DEFAULT false | Только приватный режим |
| created_at | timestamp | DEFAULT now() | Дата создания |
| inserted_at | timestamp | NOT NULL | Timestamp вставки |
| updated_at | timestamp | NOT NULL | Timestamp обновления |

**Индексы:**
- PRIMARY KEY (id)

---

### tenant_configs — Конфигурация тенантов [EXISTING]

**Назначение:** Хранение настроек тенантов (ключ-значение)

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID конфигурации |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| key | varchar | NOT NULL | Ключ настройки |
| value | jsonb | NOT NULL | Значение настройки |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Ограничения:**
- UNIQUE(tenant_id, key) — уникальность ключа в рамках тенанта

**Примеры настроек:**
```json
{
  "key": "voice_response_style",
  "value": {"tone": "formal", "language": "ru", "max_length": 500}
}

{
  "key": "qr_code_ttl_hours",
  "value": 72
}

{
  "key": "promo_auto_send",
  "value": true
}
```

---

## Клиенты и объединения

### clients — Клиенты [EXISTING]

**Назначение:** Хранение данных клиентов с привязкой к различным платформам

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID клиента |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| phone | varchar | NULL | Телефон клиента |
| name | varchar | NULL | Имя клиента |
| email | varchar | NULL | Email клиента |
| telegram_id | varchar | NULL, indexed | Telegram ID |
| vk_id | varchar | NULL, indexed | VK ID |
| whatsapp_id | varchar | NULL, indexed | WhatsApp ID |
| avito_id | varchar | NULL, indexed | Avito ID |
| max_id | varchar | NULL | MAX интеграция |
| livesklad_client_id | varchar | NULL | LiveSklad маппинг |
| has_telegram | boolean | DEFAULT false | Верифицирован в Telegram |
| has_whatsapp | boolean | DEFAULT false | Верифицирован в WhatsApp |
| has_phone | boolean | DEFAULT false | Верифицирован телефон |
| has_yandex | boolean | DEFAULT false | Верифицирован в Yandex |
| has_google | boolean | DEFAULT false | Верифицирован в Google |
| has_instagram | boolean | DEFAULT false | Верифицирован в Instagram |
| has_avito | boolean | DEFAULT false | Верифицирован в Avito |
| unsubscribed | boolean | DEFAULT false | Отписался от рассылок |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Индексы:**
- tenant_id
- telegram_id, vk_id, whatsapp_id, avito_id
- has_telegram, has_whatsapp

---

### client_merges — Объединение клиентов [EXISTING]

**Назначение:** Хранение истории слияния дублирующихся клиентов

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID слияния |
| master_client_id | uuid | FK → clients(id), NOT NULL | Главный клиент |
| merged_client_id | uuid | FK → clients(id), NOT NULL, UNIQUE | Объединённый клиент |
| merged_by | varchar | NULL | ID оператора |
| merge_reason | text | NULL | Причина объединения |
| merged_at | timestamp | DEFAULT now() | Дата объединения |

**Логика:**
- Все заявки `merged_client_id` переносятся на `master_client_id`
- `merged_client_id` помечается неактивным

---

## Заявки и сообщения

### appeals — Заявки [MODIFIED]

**Назначение:** Центральная таблица заявок с гибридной структурой

**ВАЖНО:** Поддерживает ДВА режима:
1. **Простые заявки** — 1 устройство, 1 неисправность (старая структура)
2. **Мультизаявки** — N устройств, M неисправностей (новая структура)

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID заявки |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| client_id | uuid | FK → clients(id), NOT NULL | Клиент |
| **is_multi_appeal** | boolean | DEFAULT false | **[NEW]** Мультизаявка? |
| sales_channel_id | uuid | FK → sales_channels(id) | Канал продаж |
| lead_source_id | uuid | FK → lead_sources(id) | Источник лида |
| **appeal_stage_id** | uuid | FK → appeal_stages(id) | **[NEW]** Этап воронки |
| stage | varchar | DEFAULT 'Первичный контакт' | **[DEPRECATED]** Старый этап |
| deal_type_id | uuid | FK → deal_types(id) | Тип сделки |
| **Простая заявка (для совместимости):** | | | |
| device_type_id | uuid | FK → device_types(id) | **[DEPRECATED]** Тип устройства |
| brand_id | uuid | FK → brands(id) | **[DEPRECATED]** Бренд |
| model_id | uuid | FK → models(id) | **[DEPRECATED]** Модель |
| repair_type_id | uuid | FK → repair_types(id) | **[DEPRECATED]** Тип ремонта |
| issue_type_id | uuid | FK → issue_types(id) | **[DEPRECATED]** Тип неисправности |
| issue_id | uuid | FK → issues(id) | **[DEPRECATED]** Конкретная неисправность |
| parts_owner | varchar | NULL | **[DEPRECATED]** 'ours' / 'client' |
| estimated_cost | numeric | NULL | **[DEPRECATED]** Предв. стоимость |
| estimated_time | int | NULL | **[DEPRECATED]** Предв. время (мин) |
| **Общие поля:** | | | |
| problem_description | text | NULL | Описание проблемы |
| type | varchar | NULL | Тип обращения (repair/consultation/sale/purchase/spam) |
| type_confidence | numeric | DEFAULT 0 | Уверенность в типе |
| type_identified | boolean | DEFAULT false | Тип определён |
| type_reasoning | text | NULL | Объяснение типа |
| operation_mode | varchar | DEFAULT 'assist' | Режим: manual/assist/auto |
| pending_operator_approval | boolean | DEFAULT false | Ожидает подтверждения |
| draft_response | text | NULL | Черновик AI-ответа |
| routing_history | jsonb | DEFAULT '[]' | История роутинга |
| **Запись на визит:** | | | |
| appointment_datetime | timestamp | NULL | Дата/время визита |
| master_id | int | NULL | ID мастера |
| location_id | uuid | NULL | ID локации |
| **QR-код:** | | | |
| qr_code | text | NULL | QR-код заявки |
| **Интеграции:** | | | |
| livesklad_order_id | varchar | NULL | ID заказа в LiveSklad |
| synced_with_livesklad | boolean | DEFAULT false | Синхронизировано с LiveSklad |
| synced_at | timestamp | NULL | Timestamp синхронизации |
| **Timestamps визита:** | | | |
| visited_at | timestamp | NULL | Клиент пришёл |
| completed_at | timestamp | NULL | Ремонт завершён |
| **Уведомления (флаги):** | | | |
| notified_2h_after_contact | boolean | DEFAULT false | Уведомление через 2ч |
| notified_1day_before | boolean | DEFAULT false | Уведомление за 1 день |
| notified_2h_before | boolean | DEFAULT false | Уведомление за 2 часа |
| notified_after_completion | boolean | DEFAULT false | Уведомление после завершения |
| notified_review_request | boolean | DEFAULT false | Запрос отзыва |
| notified_warranty_reminder | boolean | DEFAULT false | Напоминание о гарантии |
| **Метаданные:** | | | |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Индексы:**
- tenant_id
- client_id
- appeal_stage_id
- sales_channel_id, lead_source_id
- brand_id, model_id (для совместимости)
- is_multi_appeal

**Логика гибридного подхода:**

```sql
-- Если is_multi_appeal = false:
-- используем старые поля (brand_id, model_id, issue_type_id, etc.)

-- Если is_multi_appeal = true:
-- используем связи: appeal → appeal_devices[] → appeal_repairs[]
```

---

### messages_history — История сообщений [EXISTING]

**Назначение:** Хранение всех входящих/исходящих сообщений

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID сообщения |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| appeal_id | uuid | FK → appeals(id), NOT NULL | Заявка |
| message_type | varchar | NULL | client / agent / operator |
| message_text | text | NOT NULL | Текст сообщения |
| channel_id | uuid | FK → channels(id) | Канал |
| external_message_id | varchar | NULL | ID сообщения во внешней системе |
| external_chat_id | varchar | NULL | ID чата во внешней системе |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Индексы:**
- tenant_id
- appeal_id
- created_at

---

## Мультизаявки (NEW)

### appeal_devices — Устройства в заявке [NEW]

**Назначение:** Поддержка нескольких устройств в одной заявке

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID устройства в заявке |
| appeal_id | uuid | FK → appeals(id) ON DELETE CASCADE | Заявка |
| tenant_id | uuid | FK → tenants(id) | Тенант (для изоляции) |
| device_type_id | uuid | FK → device_types(id) | Тип устройства |
| brand_id | uuid | FK → brands(id), NOT NULL | Бренд |
| model_id | uuid | FK → models(id) | Модель |
| serial_number | varchar | NULL | Серийный номер |
| imei | varchar | NULL | IMEI (для телефонов) |
| color | varchar | NULL | Цвет устройства |
| condition | varchar | NULL | Состояние (новое/б/у/разбитое) |
| accessories | jsonb | DEFAULT '[]' | Комплектация |
| notes | text | NULL | Дополнительные заметки |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Индексы:**
- appeal_id
- tenant_id
- brand_id, model_id

**Пример:**
```sql
-- Клиент принёс 2 устройства:
INSERT INTO appeal_devices (appeal_id, brand_id, model_id, color)
VALUES
  ('appeal-uuid', 'apple-uuid', 'iphone-13-uuid', 'Белый'),
  ('appeal-uuid', 'samsung-uuid', 'galaxy-s21-uuid', 'Чёрный');
```

---

### appeal_repairs — Неисправности устройств [NEW]

**Назначение:** Хранение нескольких неисправностей для каждого устройства

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID ремонта |
| appeal_device_id | uuid | FK → appeal_devices(id) ON DELETE CASCADE | Устройство |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| repair_category_id | uuid | FK → repair_categories(id) | **[NEW]** Категория ремонта |
| issue_type_id | uuid | FK → issue_types(id) | Тип неисправности |
| issue_id | uuid | FK → issues(id) | Конкретная неисправность |
| parts_owner | varchar | NULL | 'ours' / 'client' / 'mixed' |
| estimated_cost | numeric | NULL | Предварительная стоимость |
| final_cost | numeric | NULL | Итоговая стоимость |
| estimated_time_minutes | int | NULL | Предв. время (мин) |
| symptoms | text | NULL | Симптомы |
| diagnosis | text | NULL | Диагноз мастера |
| repair_status | varchar | DEFAULT 'pending' | pending / in_progress / completed / cancelled |
| completed_at | timestamp | NULL | Timestamp завершения |
| notes | text | NULL | Заметки |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Индексы:**
- appeal_device_id
- tenant_id
- repair_category_id
- issue_type_id

**Пример:**
```sql
-- iPhone 13 имеет 2 неисправности:
INSERT INTO appeal_repairs (appeal_device_id, repair_category_id, issue_type_id, estimated_cost)
VALUES
  ('device-1-uuid', 'display-uuid', 'screen-crack-uuid', 8500),
  ('device-1-uuid', 'battery-uuid', 'quick-drain-uuid', 3500);
```

---

### repair_categories — Категории ремонта [NEW]

**Назначение:** Справочник категорий ремонта (высший уровень классификации)

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID категории |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| name | varchar | NOT NULL | Название категории |
| description | text | NULL | Описание |
| icon | varchar | NULL | Иконка (emoji или URL) |
| order | int | DEFAULT 0 | Порядок сортировки |
| is_active | boolean | DEFAULT true | Активна ли |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Ограничения:**
- UNIQUE(tenant_id, name)

**Индексы:**
- tenant_id
- (tenant_id, is_active)

**Примеры категорий:**
```
- Дисплей
- Корпус
- Звук
- Связь
- Аккумулятор
- Камера
- Порты
- ПО (софт)
- Материнская плата
```

---

## Воронка обработки (NEW)

### appeal_stages — Этапы воронки [NEW]

**Назначение:** Детальная 9-этапная воронка обработки заявок

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID этапа |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| name | varchar | NOT NULL | Название этапа |
| description | text | NULL | Описание этапа |
| stage_group | varchar | NOT NULL | Группа этапа |
| order | int | NOT NULL | Порядок в воронке |
| color | varchar | NULL | Цвет для UI (#hex) |
| icon | varchar | NULL | Иконка (emoji или URL) |
| is_final | boolean | DEFAULT false | Финальный этап? |
| is_success | boolean | DEFAULT false | Успешное завершение? |
| is_active | boolean | DEFAULT true | Активен ли |
| auto_transition_rules | jsonb | NULL | Правила автоперехода |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Ограничения:**
- UNIQUE(tenant_id, name)
- UNIQUE(tenant_id, order)
- CHECK (stage_group IN ('initial', 'processing', 'confirmed', 'completed', 'cancelled'))

**Индексы:**
- tenant_id
- (tenant_id, is_active)
- order

**9 этапов (дефолтные):**

| # | Название | stage_group | is_final | is_success | Описание |
|---|----------|-------------|----------|------------|----------|
| 1 | New Empty | initial | false | false | Минимальные данные, клиент проявил интерес |
| 2 | Сбор информации | initial | false | false | Уточнение модели, поломки, деталей |
| 3 | Информация собрана | processing | false | false | Все обязательные поля заполнены |
| 4 | Цена отправлена | processing | false | false | Предложение отправлено клиенту |
| 5 | Клиент подтвердил | confirmed | false | false | Клиент согласился на ремонт |
| 6 | QR-код отправлен | confirmed | false | false | Клиент получил код для визита |
| 7 | Клиент пришёл | confirmed | false | false | Мастер отсканировал QR |
| 8 | Ремонт выполнен | completed | true | true | Ремонт завершён успешно |
| 9 | Отказ/невыкуп/спам | cancelled | true | false | Заявка закрыта без результата |

**Миграция со старой структуры:**
```sql
-- Маппинг старых этапов на новые:
'Первичный контакт' → 'New Empty'
'Сбор информации' → 'Сбор информации'
'Цена отправлена' → 'Цена отправлена'
'Подтверждено' → 'Клиент подтвердил'
'Завершено' → 'Ремонт выполнен'
```

---

### appeal_stage_transitions — История переходов по воронке [NEW]

**Назначение:** Хранение истории перемещения заявки по этапам

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID перехода |
| appeal_id | uuid | FK → appeals(id) ON DELETE CASCADE | Заявка |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| from_stage_id | uuid | FK → appeal_stages(id), NULL | Откуда (NULL для первого) |
| to_stage_id | uuid | FK → appeal_stages(id), NOT NULL | Куда |
| operator_id | uuid | FK → operators(id), NULL | Оператор (NULL для авто) |
| transition_type | varchar | DEFAULT 'manual' | manual / automatic / system |
| reason | text | NULL | Причина перехода |
| created_at | timestamp | DEFAULT now() | Timestamp перехода |

**Индексы:**
- appeal_id
- tenant_id
- created_at

**Цель:**
- Аналитика конверсии по этапам
- Время нахождения на каждом этапе
- Отслеживание действий операторов

---

## Операторы и сессии

### operators — Операторы [EXISTING]

**Назначение:** Хранение данных операторов

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID оператора |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| telegram_id | varchar | NULL | Telegram ID |
| username | varchar | NULL | Логин |
| email | varchar | NULL | Email |
| password_hash | varchar | NULL | Хеш пароля |
| name | varchar | NULL | Имя оператора |
| location_id | uuid | NULL | ID локации |
| session_token | varchar | NULL | **[DEPRECATED]** JWT токен |
| session_token_created_at | timestamp | NULL | **[DEPRECATED]** Создание токена |
| fcm_token | varchar | NULL | FCM токен (мобильное устройство) |
| fcm_token_updated_at | timestamp | DEFAULT now() | Обновление FCM |
| last_login_at | timestamp | NULL | Последний вход |
| last_login_platform | varchar | NULL | Платформа последнего входа |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Индексы:**
- tenant_id
- telegram_id
- session_token

---

### operator_devices — Устройства операторов (Multi-Device Sessions) [NEW]

**Назначение:** Управление множественными сессиями (1 mobile + 1 desktop)

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID устройства |
| operator_id | uuid | FK → operators(id) ON DELETE CASCADE | Оператор |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| device_id | varchar | NOT NULL | Уникальный ID устройства |
| device_type | varchar | NOT NULL | 'mobile' / 'desktop' |
| device_name | varchar | NULL | Название устройства |
| os | varchar | NULL | Операционная система |
| app_version | varchar | NULL | Версия приложения |
| fcm_token | varchar | NULL | FCM токен (для mobile) |
| jwt_token | text | NULL | JWT токен сессии |
| jwt_expires_at | timestamp | NULL | Срок действия JWT |
| last_active_at | timestamp | DEFAULT now() | Последняя активность |
| is_active | boolean | DEFAULT true | Активна ли сессия |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Ограничения:**
- UNIQUE(operator_id, device_type) — 1 mobile + 1 desktop на оператора
- CHECK (device_type IN ('mobile', 'desktop'))

**Индексы:**
- operator_id
- tenant_id
- (operator_id, device_type)
- jwt_token

**Логика:**
```sql
-- При авторизации на новом mobile:
-- 1. Деактивировать старую mobile сессию
UPDATE operator_devices
SET is_active = false
WHERE operator_id = :operator_id AND device_type = 'mobile';

-- 2. Создать новую сессию
INSERT INTO operator_devices (operator_id, device_type, device_id, jwt_token, ...)
VALUES (:operator_id, 'mobile', :device_id, :jwt, ...);
```

---

### operator_actions — Лог действий операторов [EXISTING]

**Назначение:** Аудит всех действий операторов

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID действия |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| operator_id | uuid | FK → operators(id) | Оператор |
| context_appeal_id | uuid | FK → appeals(id) | Контекст заявки |
| action_type | varchar | NOT NULL | Тип действия |
| details | jsonb | NULL | Детали действия |
| created_at | timestamp | DEFAULT now() | Timestamp действия |

**Индексы:**
- tenant_id
- operator_id
- context_appeal_id
- created_at

**Примеры action_type:**
```
- appeal_created
- appeal_updated
- message_sent
- stage_changed
- price_set
- qr_scanned
- promo_sent
```

---

### operator_appeal_status — Статус обработки заявки оператором [EXISTING]

**Назначение:** Отслеживание статуса заявки для каждого оператора

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID статуса |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| operator_id | uuid | FK → operators(id) | Оператор |
| appeal_id | uuid | FK → appeals(id) | Заявка |
| status | varchar | DEFAULT 'pending' | pending / viewed / completed |
| notified_at | timestamp | NULL | Время уведомления |
| viewed_at | timestamp | NULL | Время просмотра |
| completed_at | timestamp | NULL | Время завершения |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Ограничения:**
- UNIQUE(tenant_id, operator_id, appeal_id)

**Индексы:**
- tenant_id
- operator_id
- appeal_id
- status

---

## Каналы и маркетинг

### channels — Каналы коммуникации [EXISTING]

**Назначение:** Хранение настроек каналов (WhatsApp, Telegram, VK, Avito, Instagram)

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID канала |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| name | varchar | NOT NULL | Название канала |
| platform | varchar | NOT NULL | Тип платформы |
| external_id | varchar | NULL | ID во внешней системе |
| credentials | jsonb | NULL | API credentials |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Индексы:**
- tenant_id

**Примеры platform:**
```
- whatsapp
- telegram
- vk
- avito
- instagram
- web_form
- phone
```

---

### sales_channels — Каналы продаж [EXISTING]

**Назначение:** Источники обращений на уровне бизнеса

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID канала |
| tenant_id | uuid | FK → tenants(id), NOT NULL | Тенант |
| name | varchar | NOT NULL | Название канала |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamptz | DEFAULT now() | Дата создания |
| updated_at | timestamptz | DEFAULT now() | Дата обновления |

**Ограничения:**
- UNIQUE(tenant_id, name)

**Индексы:**
- tenant_id
- (tenant_id, is_active)

**Примеры:**
```
- Яндекс.Директ
- VK Реклама
- Органический поиск
- Повторное обращение
- Рекомендация
```

---

### lead_sources — Источники лидов с UTM [EXISTING]

**Назначение:** Детализация источников с UTM-метками

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID источника |
| tenant_id | uuid | FK → tenants(id), NOT NULL | Тенант |
| sales_channel_id | uuid | FK → sales_channels(id), NOT NULL | Канал продаж |
| name | varchar | NOT NULL | Название источника |
| utm_source | varchar | NULL | UTM source |
| utm_medium | varchar | NULL | UTM medium |
| utm_campaign | varchar | NULL | UTM campaign |
| utm_term | varchar | NULL | UTM term |
| utm_content | varchar | NULL | UTM content |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamptz | DEFAULT now() | Дата создания |
| updated_at | timestamptz | DEFAULT now() | Дата обновления |

**Ограничения:**
- UNIQUE(tenant_id, sales_channel_id, name)

**Индексы:**
- tenant_id
- sales_channel_id
- (tenant_id, is_active)

---

## Справочники устройств и услуг

### device_types — Типы устройств [EXISTING]

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK | ID типа |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| name | varchar | NOT NULL | Название типа |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Примеры:** Телефон, Планшет, Ноутбук, Умные часы

---

### brands — Бренды [EXISTING]

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK | ID бренда |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| name | varchar | NOT NULL | Название бренда |
| normalized_name | varchar | NOT NULL | Нормализованное название |
| aliases | jsonb | DEFAULT '[]' | Алиасы (варианты написания) |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Примеры:** Apple, Samsung, Xiaomi, Huawei

---

### models — Модели [EXISTING]

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK | ID модели |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| brand_id | uuid | FK → brands(id), NOT NULL | Бренд |
| device_type_id | uuid | FK → device_types(id) | Тип устройства |
| name | varchar | NOT NULL | Название модели |
| aliases | jsonb | DEFAULT '[]' | Алиасы |
| is_active | boolean | DEFAULT true | Активна ли |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Примеры:** iPhone 13, Galaxy S21, Redmi Note 10

---

### repair_types — Типы ремонта [EXISTING]

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK | ID типа |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| name | varchar | NOT NULL | Название типа |
| description | text | NULL | Описание |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Примеры:** Гарантийный, Негарантийный, Экспресс

---

### issue_types — Типы неисправностей [EXISTING]

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK | ID типа |
| tenant_id | uuid | FK → tenants(id), NOT NULL | Тенант |
| name | varchar | NOT NULL | Название типа |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamptz | DEFAULT now() | Дата создания |
| updated_at | timestamptz | DEFAULT now() | Дата обновления |

**Ограничения:**
- UNIQUE(tenant_id, name)

**Примеры:** Разбит экран, Не включается, Быстро разряжается

---

### issues — Конкретные неисправности [EXISTING]

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK | ID неисправности |
| tenant_id | uuid | FK → tenants(id), NOT NULL | Тенант |
| issue_type_id | uuid | FK → issue_types(id), NOT NULL | Тип неисправности |
| name | varchar | NOT NULL | Название |
| is_active | boolean | DEFAULT true | Активна ли |
| created_at | timestamptz | DEFAULT now() | Дата создания |
| updated_at | timestamptz | DEFAULT now() | Дата обновления |

**Ограничения:**
- UNIQUE(tenant_id, issue_type_id, name)

---

### deal_types — Типы сделок [EXISTING]

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK | ID типа |
| name | varchar | NOT NULL | Название типа |
| is_repair | boolean | DEFAULT false | Это ремонт? |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Примеры:** Ремонт, Консультация, Продажа, Скупка

---

## Промо-материалы (NEW)

### promo_materials — Библиотека промо-материалов [NEW]

**Назначение:** Хранение промо-материалов для автоматической отправки

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID материала |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| name | varchar | NOT NULL | Название материала |
| description | text | NULL | Описание |
| type | varchar | NOT NULL | Тип материала |
| content_url | text | NULL | URL контента |
| content_text | text | NULL | Текстовый контент |
| thumbnail_url | text | NULL | URL превью |
| category | varchar | NULL | Категория |
| tags | jsonb | DEFAULT '[]' | Теги для поиска |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Ограничения:**
- CHECK (type IN ('video', 'pdf', 'image', 'text', 'link'))

**Индексы:**
- tenant_id
- (tenant_id, is_active)
- category

**Примеры:**
```json
{
  "name": "Как происходит замена дисплея iPhone",
  "type": "video",
  "content_url": "https://youtube.com/watch?v=...",
  "category": "repair_process",
  "tags": ["iphone", "display", "replacement"]
}

{
  "name": "Схема прохода к сервису",
  "type": "image",
  "content_url": "https://cdn.example.com/map.jpg",
  "category": "location"
}
```

---

### promo_triggers — Правила отправки промо [NEW]

**Назначение:** Настройка автоматической отправки промо-материалов

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID правила |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| promo_material_id | uuid | FK → promo_materials(id) ON DELETE CASCADE | Промо-материал |
| trigger_type | varchar | NOT NULL | Тип триггера |
| trigger_conditions | jsonb | NOT NULL | Условия срабатывания |
| priority | int | DEFAULT 0 | Приоритет (для сортировки) |
| is_active | boolean | DEFAULT true | Активно ли правило |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Ограничения:**
- CHECK (trigger_type IN ('stage_change', 'brand_model', 'issue_type', 'time_based'))

**Индексы:**
- tenant_id
- promo_material_id
- (tenant_id, is_active)

**Примеры условий:**
```json
{
  "trigger_type": "stage_change",
  "trigger_conditions": {
    "to_stage_id": "qr-code-sent-uuid"
  }
}

{
  "trigger_type": "brand_model",
  "trigger_conditions": {
    "brand_id": "apple-uuid",
    "repair_category_id": "display-uuid"
  }
}
```

---

### promo_history — История отправки промо [NEW]

**Назначение:** Отслеживание отправленных промо-материалов

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID отправки |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| appeal_id | uuid | FK → appeals(id) ON DELETE CASCADE | Заявка |
| promo_material_id | uuid | FK → promo_materials(id) | Промо-материал |
| promo_trigger_id | uuid | FK → promo_triggers(id), NULL | Правило (NULL для ручной) |
| sent_at | timestamp | DEFAULT now() | Время отправки |
| channel_id | uuid | FK → channels(id) | Канал отправки |
| operator_id | uuid | FK → operators(id), NULL | Оператор (NULL для авто) |
| sent_type | varchar | DEFAULT 'automatic' | automatic / manual |

**Индексы:**
- appeal_id
- tenant_id
- promo_material_id
- sent_at

---

## Прайсы и расчёт стоимости (NEW)

### price_lists — Прайс-листы поставщиков [NEW]

**Назначение:** Хранение прайсов от разных поставщиков

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID прайс-листа |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| supplier_name | varchar | NOT NULL | Название поставщика |
| file_name | varchar | NULL | Имя исходного файла |
| file_url | text | NULL | URL файла |
| uploaded_at | timestamp | DEFAULT now() | Дата загрузки |
| valid_from | timestamp | NULL | Действителен с |
| valid_until | timestamp | NULL | Действителен до |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Индексы:**
- tenant_id
- (tenant_id, is_active)

---

### price_list_items — Позиции прайс-листов [NEW]

**Назначение:** Детальные позиции из прайсов

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID позиции |
| price_list_id | uuid | FK → price_lists(id) ON DELETE CASCADE | Прайс-лист |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| part_name | varchar | NOT NULL | Название детали |
| part_number | varchar | NULL | Артикул |
| description | text | NULL | Описание |
| price | numeric | NOT NULL | Цена закупки |
| currency | varchar | DEFAULT 'RUB' | Валюта |
| availability | varchar | DEFAULT 'in_stock' | Наличие |
| quantity | int | NULL | Количество |
| raw_data | jsonb | NULL | Сырые данные из прайса |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Ограничения:**
- CHECK (availability IN ('in_stock', 'out_of_stock', 'on_order'))

**Индексы:**
- price_list_id
- tenant_id
- part_name
- part_number

---

### price_mappings — Привязка деталей к устройствам [NEW]

**Назначение:** Маппинг деталей из прайсов на бренды/модели/категории

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID маппинга |
| price_list_item_id | uuid | FK → price_list_items(id) ON DELETE CASCADE | Позиция прайса |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| brand_id | uuid | FK → brands(id), NULL | Бренд (NULL = все) |
| model_id | uuid | FK → models(id), NULL | Модель (NULL = все для бренда) |
| repair_category_id | uuid | FK → repair_categories(id) | Категория ремонта |
| issue_type_id | uuid | FK → issue_types(id), NULL | Тип неисправности |
| retail_markup_percent | numeric | DEFAULT 50 | Наценка (%) |
| retail_price | numeric | NULL | Розничная цена (расчётная) |
| is_verified | boolean | DEFAULT false | Проверено оператором |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Индексы:**
- tenant_id
- brand_id, model_id
- repair_category_id
- issue_type_id

**Логика подсказки оператору:**
```sql
-- Найти подходящие детали для заявки:
SELECT pm.*, pli.part_name, pli.price, pm.retail_price
FROM price_mappings pm
JOIN price_list_items pli ON pli.id = pm.price_list_item_id
WHERE pm.tenant_id = :tenant_id
  AND (pm.brand_id = :brand_id OR pm.brand_id IS NULL)
  AND (pm.model_id = :model_id OR pm.model_id IS NULL)
  AND pm.repair_category_id = :repair_category_id
ORDER BY pm.is_verified DESC, pli.price ASC;
```

---

## AI обработка и транскрибация (NEW)

### ai_chat_memory — Память AI (контекст диалогов) [NEW]

**Назначение:** Хранение истории AI-обработки для контекстных запросов

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID записи |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| appeal_id | uuid | FK → appeals(id) ON DELETE CASCADE | Заявка |
| role | varchar | NOT NULL | user / assistant / system |
| content | text | NOT NULL | Содержимое сообщения |
| ai_model | varchar | NULL | Использованная модель AI |
| tokens_used | int | NULL | Использовано токенов |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Ограничения:**
- CHECK (role IN ('user', 'assistant', 'system'))

**Индексы:**
- appeal_id
- tenant_id
- created_at

**Назначение:**
- Сохранение контекста для последующих AI-запросов
- Уточняющие вопросы от AI
- Анализ качества AI-обработки

---

### voice_transcriptions — Транскрибация голосовых и звонков [NEW]

**Назначение:** Хранение транскриптов голосовых сообщений и звонков

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID транскрипции |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| appeal_id | uuid | FK → appeals(id) ON DELETE CASCADE | Заявка |
| message_id | uuid | FK → messages_history(id), NULL | Сообщение (для голосовых) |
| source_type | varchar | NOT NULL | Тип источника |
| audio_url | text | NULL | URL аудиофайла |
| audio_duration_sec | int | NULL | Длительность (сек) |
| transcription_text | text | NOT NULL | Текст транскрипции |
| transcription_engine | varchar | DEFAULT 'whisper' | Движок транскрибации |
| language | varchar | DEFAULT 'ru' | Язык |
| confidence | numeric | NULL | Уверенность (0-1) |
| processing_time_ms | int | NULL | Время обработки (мс) |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Ограничения:**
- CHECK (source_type IN ('voice_message', 'incoming_call', 'outgoing_call', 'operator_voice_response'))

**Индексы:**
- appeal_id
- tenant_id
- message_id
- source_type
- created_at

**Использование:**
- Автоматическая транскрибация всех голосовых сообщений
- Запись и транскрибация звонков (InCallService API)
- Голосовые ответы оператора (SpeechRecognizer → AI оформление)

---

## Интеграции с CRM (NEW)

### crm_integrations — Настройки интеграций [NEW]

**Назначение:** Хранение конфигураций интеграций с внешними CRM

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID интеграции |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| crm_type | varchar | NOT NULL | Тип CRM |
| name | varchar | NOT NULL | Название интеграции |
| api_url | text | NOT NULL | API endpoint |
| api_key | text | NULL | API ключ |
| api_secret | text | NULL | API secret |
| credentials | jsonb | NULL | Дополнительные credentials |
| sync_direction | varchar | DEFAULT 'both' | Направление синхронизации |
| auto_sync | boolean | DEFAULT true | Автоматическая синхронизация |
| is_active | boolean | DEFAULT true | Активна ли |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Ограничения:**
- UNIQUE(tenant_id, crm_type)
- CHECK (crm_type IN ('livesklad', 'remonline'))
- CHECK (sync_direction IN ('to_crm', 'from_crm', 'both'))

**Индексы:**
- tenant_id
- (tenant_id, is_active)

---

### crm_sync_history — История синхронизаций [NEW]

**Назначение:** Отслеживание синхронизации с CRM

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID синхронизации |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| crm_integration_id | uuid | FK → crm_integrations(id) | Интеграция |
| appeal_id | uuid | FK → appeals(id) ON DELETE CASCADE | Заявка |
| sync_type | varchar | NOT NULL | Тип синхронизации |
| direction | varchar | NOT NULL | Направление |
| external_order_id | varchar | NULL | ID заказа в CRM |
| status | varchar | DEFAULT 'pending' | Статус синхронизации |
| request_payload | jsonb | NULL | Отправленные данные |
| response_payload | jsonb | NULL | Ответ от CRM |
| error_message | text | NULL | Сообщение об ошибке |
| synced_at | timestamp | NULL | Timestamp синхронизации |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Ограничения:**
- CHECK (sync_type IN ('create_order', 'update_order', 'get_order_status', 'get_order_result'))
- CHECK (direction IN ('to_crm', 'from_crm'))
- CHECK (status IN ('pending', 'success', 'failed', 'retrying'))

**Индексы:**
- appeal_id
- tenant_id
- crm_integration_id
- status
- created_at

---

### crm_field_mappings — Маппинг полей CRM [NEW]

**Назначение:** Настройка соответствия полей между BatteryCRM и внешними CRM

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID маппинга |
| crm_integration_id | uuid | FK → crm_integrations(id) ON DELETE CASCADE | Интеграция |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| battery_field | varchar | NOT NULL | Поле в BatteryCRM |
| crm_field | varchar | NOT NULL | Поле в CRM |
| field_type | varchar | NOT NULL | Тип данных |
| transform_rule | jsonb | NULL | Правило трансформации |
| is_active | boolean | DEFAULT true | Активен ли |
| created_at | timestamp | DEFAULT now() | Дата создания |

**Ограничения:**
- UNIQUE(crm_integration_id, battery_field)

**Индексы:**
- crm_integration_id
- tenant_id

**Примеры:**
```json
{
  "battery_field": "appeals.brand_id",
  "crm_field": "order.device_brand",
  "field_type": "uuid_to_string",
  "transform_rule": {"lookup_table": "brands"}
}
```

---

## TTL политики и архивация (NEW)

### data_retention_policies — Политики хранения данных [NEW]

**Назначение:** Настройка сроков хранения данных и автоматической архивации

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID политики |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| entity_type | varchar | NOT NULL | Тип сущности |
| retention_days | int | NOT NULL | Срок хранения (дни) |
| action_type | varchar | DEFAULT 'archive' | Действие |
| is_active | boolean | DEFAULT true | Активна ли |
| created_at | timestamp | DEFAULT now() | Дата создания |
| updated_at | timestamp | DEFAULT now() | Дата обновления |

**Ограничения:**
- UNIQUE(tenant_id, entity_type)
- CHECK (entity_type IN ('messages_history', 'voice_transcriptions', 'ai_chat_memory', 'operator_actions', 'appeal_stage_transitions'))
- CHECK (action_type IN ('archive', 'delete', 'anonymize'))

**Индексы:**
- tenant_id
- (tenant_id, is_active)

**Примеры:**
```sql
-- Архивировать историю сообщений старше 30 дней
INSERT INTO data_retention_policies (tenant_id, entity_type, retention_days, action_type)
VALUES (:tenant_id, 'messages_history', 30, 'archive');

-- Удалять транскрипты старше 90 дней
INSERT INTO data_retention_policies (tenant_id, entity_type, retention_days, action_type)
VALUES (:tenant_id, 'voice_transcriptions', 90, 'delete');
```

---

### archived_data — Архивированные данные [NEW]

**Назначение:** Хранение архивированных данных

| Поле | Тип | Ограничения | Назначение |
|:-----|:----|:------------|:-----------|
| id | uuid | PK, gen_random_uuid() | ID архивной записи |
| tenant_id | uuid | FK → tenants(id) | Тенант |
| entity_type | varchar | NOT NULL | Тип сущности |
| entity_id | uuid | NOT NULL | ID оригинальной записи |
| entity_data | jsonb | NOT NULL | Архивированные данные |
| archived_at | timestamp | DEFAULT now() | Дата архивации |

**Индексы:**
- tenant_id
- entity_type
- entity_id
- archived_at

---

## Индексы и оптимизация

### Рекомендуемые индексы (полный список)

**Основные FK индексы:**
```sql
-- Multi-Tenant изоляция
CREATE INDEX idx_tenants_is_active ON tenants(is_active);

-- Клиенты
CREATE INDEX idx_clients_tenant ON clients(tenant_id);
CREATE INDEX idx_clients_telegram ON clients(telegram_id);
CREATE INDEX idx_clients_whatsapp ON clients(whatsapp_id);
CREATE INDEX idx_clients_phone ON clients(phone);

-- Заявки
CREATE INDEX idx_appeals_tenant ON appeals(tenant_id);
CREATE INDEX idx_appeals_client ON appeals(client_id);
CREATE INDEX idx_appeals_stage ON appeals(appeal_stage_id);
CREATE INDEX idx_appeals_multi ON appeals(is_multi_appeal);
CREATE INDEX idx_appeals_created ON appeals(created_at);

-- Мультизаявки
CREATE INDEX idx_appeal_devices_appeal ON appeal_devices(appeal_id);
CREATE INDEX idx_appeal_devices_tenant ON appeal_devices(tenant_id);
CREATE INDEX idx_appeal_repairs_device ON appeal_repairs(appeal_device_id);
CREATE INDEX idx_appeal_repairs_tenant ON appeal_repairs(tenant_id);

-- Сообщения
CREATE INDEX idx_messages_appeal ON messages_history(appeal_id);
CREATE INDEX idx_messages_tenant ON messages_history(tenant_id);
CREATE INDEX idx_messages_created ON messages_history(created_at);

-- Операторы
CREATE INDEX idx_operators_tenant ON operators(tenant_id);
CREATE INDEX idx_operator_devices_operator ON operator_devices(operator_id);
CREATE INDEX idx_operator_devices_type ON operator_devices(operator_id, device_type);

-- Промо
CREATE INDEX idx_promo_materials_tenant ON promo_materials(tenant_id);
CREATE INDEX idx_promo_triggers_tenant ON promo_triggers(tenant_id);
CREATE INDEX idx_promo_history_appeal ON promo_history(appeal_id);

-- Прайсы
CREATE INDEX idx_price_lists_tenant ON price_lists(tenant_id);
CREATE INDEX idx_price_mappings_tenant ON price_mappings(tenant_id);
CREATE INDEX idx_price_mappings_brand_model ON price_mappings(brand_id, model_id);

-- AI и транскрибация
CREATE INDEX idx_ai_chat_memory_appeal ON ai_chat_memory(appeal_id);
CREATE INDEX idx_voice_transcriptions_appeal ON voice_transcriptions(appeal_id);

-- CRM интеграции
CREATE INDEX idx_crm_sync_history_appeal ON crm_sync_history(appeal_id);
CREATE INDEX idx_crm_sync_history_status ON crm_sync_history(status);
```

### Composite индексы для производительности

```sql
-- Поиск активных заявок по тенанту и этапу
CREATE INDEX idx_appeals_tenant_stage_active
ON appeals(tenant_id, appeal_stage_id)
WHERE is_multi_appeal = false;

-- Поиск активных промо-материалов
CREATE INDEX idx_promo_materials_tenant_active
ON promo_materials(tenant_id, is_active);

-- Поиск активных устройств оператора
CREATE INDEX idx_operator_devices_active
ON operator_devices(operator_id, device_type)
WHERE is_active = true;

-- Статистика по этапам
CREATE INDEX idx_appeal_transitions_stage_created
ON appeal_stage_transitions(to_stage_id, created_at);
```

---

## Миграции

### Миграция со старой структуры на новую

**Этап 1: Создание новых таблиц**
```sql
-- Создать все NEW таблицы
-- См. секции выше
```

**Этап 2: Миграция существующих заявок**
```sql
-- Пометить все существующие заявки как простые (не мульти)
UPDATE appeals SET is_multi_appeal = false WHERE is_multi_appeal IS NULL;

-- Создать дефолтные этапы воронки
INSERT INTO appeal_stages (tenant_id, name, stage_group, order, ...)
VALUES
  (:tenant_id, 'New Empty', 'initial', 1, ...),
  (:tenant_id, 'Сбор информации', 'initial', 2, ...),
  ...;

-- Создать дефолтные категории ремонта
INSERT INTO repair_categories (tenant_id, name)
VALUES
  (:tenant_id, 'Дисплей'),
  (:tenant_id, 'Корпус'),
  (:tenant_id, 'Аккумулятор'),
  ...;
```

**Этап 3: Обратная совместимость**
```sql
-- Создать VIEW для совместимости со старыми запросами
CREATE VIEW appeals_legacy AS
SELECT
  a.*,
  COALESCE(ast.name, a.stage) AS stage_name
FROM appeals a
LEFT JOIN appeal_stages ast ON ast.id = a.appeal_stage_id;
```

---

## Связанные документы

- [01_SYSTEM_OVERVIEW.md](01_SYSTEM_OVERVIEW.md) — Общий обзор системы
- [03_WORKFLOWS_COMPLETE.md](03_WORKFLOWS_COMPLETE.md) — Детальное описание workflows
- [04_API_SPECIFICATION.md](04_API_SPECIFICATION.md) — REST API спецификация
- [05_ANDROID_APP_SPEC.md](05_ANDROID_APP_SPEC.md) — Спецификация Android-приложения

---

**Версия:** 2.0
**Дата:** 2025-11-23
**Таблиц:** 50 (37 existing + 13 new)
**Статус:** ✅ Готово к реализации
