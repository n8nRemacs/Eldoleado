# BatteryCRM - n8n Workflows: Полная спецификация

**Версия:** 2.0
**Дата:** 2025-11-23
**Статус:** Актуальная спецификация

---

## Содержание

1. [Обзор workflows](#обзор-workflows)
2. [Архитектура микросервисов](#архитектура-микросервисов)
3. [BAT Message Router](#bat-message-router)
4. [BAT Appeal Router](#bat-appeal-router)
5. [BAT Universal Batcher](#bat-universal-batcher)
6. [BAT IN Workflows (Входящие)](#bat-in-workflows-входящие)
7. [BAT OUT Workflows (Исходящие)](#bat-out-workflows-исходящие)
8. [BAT AI Tools](#bat-ai-tools)
9. [BAT Promo Sender (NEW)](#bat-promo-sender-new)
10. [BAT Price Calculator (NEW)](#bat-price-calculator-new)
11. [BAT CRM Sync (NEW)](#bat-crm-sync-new)
12. [BAT QR Code Manager (NEW)](#bat-qr-code-manager-new)
13. [BAT Voice Processor (NEW)](#bat-voice-processor-new)
14. [BAT Remarketing Exporter (NEW)](#bat-remarketing-exporter-new)
15. [BAT Data Archiver (NEW)](#bat-data-archiver-new)
16. [Вспомогательные workflows](#вспомогательные-workflows)
17. [Мониторинг и отладка](#мониторинг-и-отладка)

---

## Обзор workflows

### Общая информация

**Текущее состояние:**
- **3 существующих workflows** — Message Router, Appeal Router, Universal Batcher
- **~10 новых workflows** — Промо, прайсы, CRM sync, QR, голос, ремаркетинг

**Принципы архитектуры:**
1. **Event-Driven** — все workflows реагируют на события
2. **Stateless** — состояние хранится только в БД
3. **Идемпотентность** — повторное выполнение не вызывает дублирования
4. **Retry Logic** — автоматические повторы при ошибках
5. **Multi-Tenant изоляция** — все запросы фильтруются по `tenant_id`

### Карта workflows

```
┌─────────────────────────────────────────────────────────────┐
│                    ВХОДЯЩИЕ СОБЫТИЯ                         │
│  WhatsApp │ Telegram │ VK │ Avito │ Instagram │ Звонки     │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                   BAT IN Workflows                          │
│  BAT IN WhatsApp │ BAT IN Telegram │ BAT IN VK │ etc.      │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                  BAT Message Router                         │
│  - Приём сообщений                                          │
│  - Определение клиента                                      │
│  - Сохранение в messages_history                            │
│  - Роутинг на Appeal Router                                 │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                  BAT Appeal Router                          │
│  - AI обработка сообщений                                   │
│  - Создание/обновление заявок                               │
│  - Управление воронкой                                      │
│  - Триггеры для промо и CRM                                 │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│              ВСПОМОГАТЕЛЬНЫЕ WORKFLOWS                      │
│  Promo Sender │ Price Calculator │ CRM Sync │ QR Manager   │
│  Voice Processor │ Remarketing Exporter │ Data Archiver    │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                   BAT OUT Workflows                         │
│  BAT OUT WhatsApp │ BAT OUT Telegram │ BAT OUT VK │ etc.   │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                    FCM PUSH SERVICE                         │
│  - Отправка уведомлений операторам                          │
└─────────────────────────────────────────────────────────────┘
```

---

## Архитектура микросервисов

### Принципы проектирования

**1. Один workflow = одна ответственность**
- Message Router — только приём и роутинг сообщений
- Appeal Router — только обработка заявок
- Promo Sender — только отправка промо

**2. Асинхронная коммуникация**
- Workflows общаются через HTTP webhooks
- Очереди задач через Universal Batcher
- События через webhook triggers

**3. Shared Database**
- Все workflows работают с одной PostgreSQL БД
- Multi-Tenant изоляция через `tenant_id`
- Транзакционная целостность

**4. Error Handling**
```javascript
// Стандартный блок обработки ошибок в каждом workflow:
{
  "continueOnFail": false,
  "retry": 3,
  "retryDelay": 1000,
  "onError": "webhook_to_error_handler"
}
```

---

## BAT Message Router

### Назначение

Единая точка входа для всех входящих сообщений из различных каналов.

### Функции

1. **Приём сообщений**
   - WhatsApp, Telegram, VK, Avito, Instagram
   - Голосовые сообщения
   - Медиафайлы (фото, видео, документы)

2. **Нормализация данных**
   - Приведение к единому формату
   - Извлечение ID клиента (telegram_id, whatsapp_id, etc.)
   - Определение типа сообщения

3. **Определение/создание клиента**
   - Поиск клиента по platform_id
   - Создание нового клиента при первом обращении
   - Обновление флагов верификации (has_telegram, has_whatsapp, etc.)

4. **Определение/создание заявки**
   - Поиск активной заявки клиента
   - Создание новой заявки (если нет активных)

5. **Сохранение в БД**
   - `messages_history` — полный текст сообщения
   - Привязка к `appeal_id`
   - Сохранение медиафайлов

6. **Роутинг**
   - Отправка на **BAT Appeal Router** для AI-обработки
   - Отправка FCM-уведомления оператору

### Входные данные

```json
{
  "platform": "whatsapp",
  "external_chat_id": "79991234567@c.us",
  "external_message_id": "wamid.HBgNNzk5OTEyMzQ1NjcVAgA...",
  "message_type": "text",
  "message_text": "Привет! У меня разбился экран на iPhone 13",
  "timestamp": "2025-11-23T10:30:00Z",
  "media_url": null
}
```

### Логика работы

```javascript
// 1. Определить клиента
let client = await findClient({ whatsapp_id: external_chat_id, tenant_id });

if (!client) {
  client = await createClient({
    tenant_id,
    whatsapp_id: external_chat_id,
    has_whatsapp: true
  });
}

// 2. Определить активную заявку
let appeal = await findActiveAppeal({ client_id: client.id, tenant_id });

if (!appeal) {
  appeal = await createAppeal({
    tenant_id,
    client_id: client.id,
    sales_channel_id: getSalesChannelByPlatform('whatsapp'),
    appeal_stage_id: getStageByName('New Empty')
  });
}

// 3. Сохранить сообщение
await saveMessage({
  tenant_id,
  appeal_id: appeal.id,
  message_type: 'client',
  message_text,
  channel_id: getChannelByPlatform('whatsapp'),
  external_message_id,
  external_chat_id
});

// 4. Отправить на Appeal Router
await callWorkflow('BAT Appeal Router', {
  appeal_id: appeal.id,
  tenant_id,
  trigger: 'new_message'
});

// 5. Отправить FCM-уведомление
await sendFCM({
  appeal_id: appeal.id,
  title: 'Новое сообщение',
  body: message_text.substring(0, 100)
});
```

### Выходные данные

```json
{
  "success": true,
  "client_id": "uuid",
  "appeal_id": "uuid",
  "message_id": "uuid"
}
```

---

## BAT Appeal Router

### Назначение

Центральный workflow для обработки заявок, AI-извлечения сущностей и управления воронкой.

### Функции

1. **AI-обработка сообщений**
   - Вызов AI Tools для извлечения сущностей
   - Поддержка простых и мультизаявок
   - Сохранение контекста в `ai_chat_memory`

2. **Управление заявкой**
   - Обновление полей заявки (brand, model, issue_type, etc.)
   - Создание записей в `appeal_devices` и `appeal_repairs` (для мультизаявок)
   - Расчёт уверенности (confidence)

3. **Управление воронкой**
   - Автоматические переходы по этапам
   - Сохранение истории в `appeal_stage_transitions`
   - Триггеры событий при смене этапа

4. **Триггеры вспомогательных workflows**
   - Promo Sender (при смене этапа)
   - CRM Sync (при переходе на "Клиент пришёл")
   - QR Code Manager (при подтверждении клиента)

5. **Формирование черновиков ответов**
   - AI генерация ответа (если `operation_mode = 'assist'`)
   - Сохранение в `draft_response`
   - Ожидание утверждения оператором

### AI Tools (4 инструмента)

#### AI Tool 1: Appeal Type Classifier

**Назначение:** Определение типа обращения

**Input:**
```json
{
  "message_history": [
    {"role": "user", "content": "Привет! У меня разбился экран на iPhone 13"}
  ]
}
```

**Output:**
```json
{
  "appeal_type": "repair",
  "confidence": 0.95,
  "reasoning": "Клиент сообщает о поломке устройства, что указывает на запрос ремонта"
}
```

**Возможные типы:**
- `repair` — ремонт
- `consultation` — консультация
- `sale` — продажа
- `purchase` — покупка/скупка
- `spam` — спам

---

#### AI Tool 2: Device Extractor

**Назначение:** Извлечение данных устройства

**Input:**
```json
{
  "message_text": "У меня разбился экран на iPhone 13",
  "brands_list": ["Apple", "Samsung", "Xiaomi", ...],
  "models_list": ["iPhone 13", "iPhone 14", "Galaxy S21", ...]
}
```

**Output:**
```json
{
  "brand": "Apple",
  "model": "iPhone 13",
  "device_type": "Телефон",
  "confidence": 0.98,
  "color": null,
  "condition": null
}
```

---

#### AI Tool 3: Issue Extractor

**Назначение:** Извлечение данных о неисправности

**Input:**
```json
{
  "message_text": "У меня разбился экран на iPhone 13",
  "repair_categories_list": ["Дисплей", "Корпус", "Аккумулятор", ...],
  "issue_types_list": ["Разбит экран", "Не включается", "Быстро разряжается", ...]
}
```

**Output:**
```json
{
  "repair_category": "Дисплей",
  "issue_type": "Разбит экран",
  "symptoms": "Экран разбит",
  "possible_causes": "Падение устройства",
  "confidence": 0.97
}
```

---

#### AI Tool 4: Multi-Entity Extractor (NEW)

**Назначение:** Извлечение **множественных** устройств и неисправностей

**Input:**
```json
{
  "message_text": "Хочу отремонтировать iPhone 13 (разбит экран и батарея плохо держит) и Galaxy S21 (не работает камера)",
  "brands_list": [...],
  "models_list": [...],
  "repair_categories_list": [...],
  "issue_types_list": [...]
}
```

**Output:**
```json
{
  "is_multi_appeal": true,
  "devices": [
    {
      "brand": "Apple",
      "model": "iPhone 13",
      "device_type": "Телефон",
      "repairs": [
        {
          "repair_category": "Дисплей",
          "issue_type": "Разбит экран",
          "symptoms": "Экран разбит"
        },
        {
          "repair_category": "Аккумулятор",
          "issue_type": "Быстро разряжается",
          "symptoms": "Батарея плохо держит"
        }
      ]
    },
    {
      "brand": "Samsung",
      "model": "Galaxy S21",
      "device_type": "Телефон",
      "repairs": [
        {
          "repair_category": "Камера",
          "issue_type": "Не работает камера",
          "symptoms": "Не работает камера"
        }
      ]
    }
  ],
  "confidence": 0.92
}
```

---

### Логика работы Appeal Router

```javascript
// 1. Получить историю сообщений заявки
const messageHistory = await getMessagesHistory(appeal_id);

// 2. Вызвать AI Tool 1: Appeal Type Classifier
const typeResult = await callAI({
  tool: 'appeal_type_classifier',
  input: { message_history: messageHistory }
});

await updateAppeal({
  appeal_id,
  type: typeResult.appeal_type,
  type_confidence: typeResult.confidence,
  type_reasoning: typeResult.reasoning
});

// 3. Вызвать AI Tool 4: Multi-Entity Extractor
const entitiesResult = await callAI({
  tool: 'multi_entity_extractor',
  input: {
    message_text: messageHistory[messageHistory.length - 1].content,
    brands_list: await getBrands(tenant_id),
    models_list: await getModels(tenant_id),
    repair_categories_list: await getRepairCategories(tenant_id),
    issue_types_list: await getIssueTypes(tenant_id)
  }
});

// 4. Если мультизаявка:
if (entitiesResult.is_multi_appeal) {
  await updateAppeal({ appeal_id, is_multi_appeal: true });

  for (const device of entitiesResult.devices) {
    const deviceRecord = await createAppealDevice({
      appeal_id,
      tenant_id,
      brand_id: findBrandId(device.brand),
      model_id: findModelId(device.model),
      device_type_id: findDeviceTypeId(device.device_type)
    });

    for (const repair of device.repairs) {
      await createAppealRepair({
        appeal_device_id: deviceRecord.id,
        tenant_id,
        repair_category_id: findRepairCategoryId(repair.repair_category),
        issue_type_id: findIssueTypeId(repair.issue_type),
        symptoms: repair.symptoms
      });
    }
  }
} else {
  // Простая заявка (старая структура)
  await updateAppeal({
    appeal_id,
    brand_id: findBrandId(entitiesResult.brand),
    model_id: findModelId(entitiesResult.model),
    issue_type_id: findIssueTypeId(entitiesResult.issue_type)
  });
}

// 5. Проверить переход на следующий этап
const currentStage = await getAppealStage(appeal_id);
const nextStage = await calculateNextStage(appeal_id, currentStage);

if (nextStage && nextStage.id !== currentStage.id) {
  await transitionStage({
    appeal_id,
    from_stage_id: currentStage.id,
    to_stage_id: nextStage.id,
    transition_type: 'automatic'
  });

  // Триггеры при смене этапа
  await callWorkflow('BAT Promo Sender', { appeal_id, event: 'stage_change' });
}

// 6. Если operation_mode = 'assist', сгенерировать черновик ответа
if (appeal.operation_mode === 'assist') {
  const draftResponse = await callAI({
    tool: 'response_generator',
    input: {
      message_history: messageHistory,
      appeal_context: await getAppealContext(appeal_id)
    }
  });

  await updateAppeal({ appeal_id, draft_response: draftResponse, pending_operator_approval: true });

  // FCM уведомление оператору
  await sendFCM({
    appeal_id,
    title: 'Требуется подтверждение ответа',
    body: draftResponse.substring(0, 100)
  });
}
```

---

## BAT Universal Batcher

### Назначение

Оптимизация нагрузки через группировку однотипных операций и отложенные задачи.

### Функции

1. **Батчинг AI-запросов**
   - Группировка запросов к OpenAI API
   - Снижение количества вызовов
   - Экономия токенов

2. **Отложенные задачи**
   - Scheduled jobs (cron-like)
   - Delayed webhooks
   - Retry failed operations

3. **Массовые операции**
   - Bulk updates в БД
   - Массовая отправка уведомлений
   - Batch export данных

### Примеры использования

**1. Батчинг AI-запросов:**
```javascript
// Вместо 10 отдельных вызовов AI:
for (let appeal of appeals) {
  await callAI({ appeal_id: appeal.id });
}

// Один батч-запрос:
await batcher.addToBatch('ai_processing', appeals.map(a => a.id));
// Batch выполняется раз в 5 секунд или при достижении 10 элементов
```

**2. Отложенные уведомления:**
```javascript
// Отправить уведомление через 2 часа после первого контакта
await batcher.scheduleJob({
  workflow: 'BAT Notification Sender',
  delay: 7200, // seconds
  data: { appeal_id, notification_type: '2h_after_contact' }
});
```

**3. Массовые обновления:**
```javascript
// Обновить статус всех заявок старше 30 дней
await batcher.bulkUpdate('appeals', {
  filter: { created_at: { $lt: thirtyDaysAgo } },
  update: { stage: 'Отказ' }
});
```

---

## BAT IN Workflows (Входящие)

### BAT IN WhatsApp

**Назначение:** Приём сообщений из WhatsApp Business API

**Webhook URL:** `https://n8n.example.com/webhook/whatsapp-in`

**Источник:** Meta Business API / Twilio

**Обработка:**
1. Получить webhook от WhatsApp
2. Извлечь `from`, `message_id`, `message_type`, `text`, `media_url`
3. Нормализовать данные
4. Вызвать **BAT Message Router**

---

### BAT IN Telegram

**Назначение:** Приём сообщений из Telegram Bot API

**Webhook URL:** `https://n8n.example.com/webhook/telegram-in`

**Источник:** Telegram Bot API

**Обработка:**
1. Получить update от Telegram
2. Извлечь `chat_id`, `message_id`, `text`, `photo`, `document`
3. Нормализовать данные
4. Вызвать **BAT Message Router**

---

### BAT IN VK

**Назначение:** Приём сообщений из VK API

**Webhook URL:** `https://n8n.example.com/webhook/vk-in`

**Источник:** VK Callback API

**Обработка:**
1. Получить callback от VK
2. Извлечь `user_id`, `peer_id`, `text`, `attachments`
3. Нормализовать данные
4. Вызвать **BAT Message Router**

---

### BAT IN Avito

**Назначение:** Приём сообщений из Avito Messenger API

**Webhook URL:** `https://n8n.example.com/webhook/avito-in`

**Источник:** Avito Messenger API

**Обработка:**
1. Получить webhook от Avito
2. Извлечь `author_id`, `message_id`, `text`
3. Нормализовать данные
4. Вызвать **BAT Message Router**

---

### BAT IN Instagram (NEW)

**Назначение:** Приём сообщений из Instagram Direct

**Webhook URL:** `https://n8n.example.com/webhook/instagram-in`

**Источник:** Instagram Graph API

**Обработка:**
1. Получить webhook от Instagram
2. Извлечь `sender_id`, `message_id`, `text`, `media_url`
3. Нормализовать данные
4. Вызвать **BAT Message Router**

---

## BAT OUT Workflows (Исходящие)

### BAT OUT WhatsApp

**Назначение:** Отправка сообщений в WhatsApp

**Вход:**
```json
{
  "to": "79991234567",
  "message_type": "text",
  "text": "Здравствуйте! Стоимость ремонта экрана iPhone 13 - 8500 руб.",
  "media_url": null
}
```

**Обработка:**
1. Получить credentials канала из `channels`
2. Вызвать WhatsApp Business API
3. Сохранить в `messages_history` (message_type = 'operator')
4. Вернуть `external_message_id`

---

### BAT OUT Telegram

**Назначение:** Отправка сообщений в Telegram

**Вход:**
```json
{
  "chat_id": "123456789",
  "text": "Здравствуйте! Стоимость ремонта - 8500 руб.",
  "parse_mode": "Markdown"
}
```

**Обработка:**
1. Получить bot token из `channels`
2. Вызвать Telegram Bot API: `sendMessage`
3. Сохранить в `messages_history`
4. Вернуть `message_id`

---

### BAT OUT VK

**Назначение:** Отправка сообщений в VK

**Вход:**
```json
{
  "user_id": "123456",
  "message": "Здравствуйте! Стоимость ремонта - 8500 руб."
}
```

**Обработка:**
1. Получить access_token из `channels`
2. Вызвать VK API: `messages.send`
3. Сохранить в `messages_history`

---

### BAT OUT Avito

**Назначение:** Отправка сообщений в Avito

**Вход:**
```json
{
  "author_id": "user123",
  "text": "Здравствуйте! Стоимость ремонта - 8500 руб."
}
```

**Обработка:**
1. Получить credentials из `channels`
2. Вызвать Avito Messenger API
3. Сохранить в `messages_history`

---

### BAT OUT Instagram (NEW)

**Назначение:** Отправка сообщений в Instagram Direct

**Вход:**
```json
{
  "recipient_id": "instagram_user_id",
  "message": "Здравствуйте! Стоимость ремонта - 8500 руб."
}
```

**Обработка:**
1. Получить credentials из `channels`
2. Вызвать Instagram Graph API
3. Сохранить в `messages_history`

---

## BAT AI Tools

### BAT AI Appeal Type Classifier

**Endpoint:** `/webhook/ai-appeal-type-classifier`

**Input:**
```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "message_history": [...]
}
```

**Процесс:**
1. Загрузить промпт из конфигурации
2. Вызвать OpenAI API (Claude AI)
3. Парсинг JSON-ответа
4. Валидация результата
5. Сохранение в `appeals.type`

---

### BAT AI Device Extractor

**Endpoint:** `/webhook/ai-device-extractor`

**Input:**
```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "message_text": "...",
  "brands_list": [...],
  "models_list": [...]
}
```

**Процесс:**
1. Формирование промпта с контекстом
2. Вызов OpenAI API
3. Маппинг результата на ID в БД
4. Обновление `appeals.brand_id`, `appeals.model_id`

---

### BAT AI Issue Extractor

**Endpoint:** `/webhook/ai-issue-extractor`

**Input:**
```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "message_text": "...",
  "repair_categories_list": [...],
  "issue_types_list": [...]
}
```

**Процесс:**
1. Формирование промпта
2. Вызов OpenAI API
3. Маппинг на ID категорий и типов
4. Обновление БД

---

### BAT AI Multi-Entity Extractor (NEW)

**Endpoint:** `/webhook/ai-multi-entity-extractor`

**Input:**
```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "message_text": "...",
  "brands_list": [...],
  "models_list": [...],
  "repair_categories_list": [...],
  "issue_types_list": [...]
}
```

**Процесс:**
1. Определить количество устройств в сообщении
2. Извлечь для каждого устройства:
   - brand, model, device_type
   - repairs[] (массив неисправностей)
3. Вернуть структурированный JSON
4. Appeal Router создаст записи в `appeal_devices` и `appeal_repairs`

---

### BAT AI Response Generator (NEW)

**Endpoint:** `/webhook/ai-response-generator`

**Назначение:** Генерация ответов оператора в режиме `assist`

**Input:**
```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "message_history": [...],
  "appeal_context": {
    "brand": "Apple",
    "model": "iPhone 13",
    "issue_type": "Разбит экран",
    "estimated_cost": 8500
  }
}
```

**Output:**
```json
{
  "draft_response": "Здравствуйте! Благодарим за обращение. Стоимость замены дисплея на iPhone 13 составит 8500 руб. Ремонт займёт около 40 минут. Подтверждаете запись на ремонт?"
}
```

**Процесс:**
1. Загрузить стиль ответов тенанта из `tenant_configs`
2. Сформировать промпт с контекстом
3. Вызвать OpenAI API
4. Вернуть оформленный текст

---

### BAT AI Voice Response Formatter (NEW)

**Endpoint:** `/webhook/ai-voice-response-formatter`

**Назначение:** Оформление голосовых ответов оператора (Android SpeechRecognizer → AI → текст)

**Input:**
```json
{
  "raw_text": "привет да стоимость замены дисплея айфон тринадцать восемь с половиной тысяч рублей ремонт займет сорок минут",
  "tenant_id": "uuid",
  "appeal_context": { ... }
}
```

**Output:**
```json
{
  "formatted_text": "Здравствуйте! Стоимость замены дисплея на iPhone 13 составляет 8500 рублей. Ремонт займёт 40 минут."
}
```

**Процесс:**
1. Получить `raw_text` от Android SpeechRecognizer
2. Вызвать AI для оформления:
   - Исправить грамматику
   - Расставить знаки препинания
   - Привести к стилю сервиса
3. Вернуть `formatted_text`
4. Отправить клиенту через BAT OUT Workflow

---

## BAT Promo Sender (NEW)

### Назначение

Автоматическая отправка промо-материалов клиентам по триггерам.

### Триггеры

1. **stage_change** — смена этапа заявки
2. **brand_model_issue** — комбинация бренда, модели и неисправности
3. **time_based** — временные триггеры (после отправки цены, через 1 час после контакта)

### Вход

```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "event": "stage_change",
  "event_data": {
    "to_stage_id": "qr-code-sent-uuid"
  }
}
```

### Процесс

```javascript
// 1. Найти подходящие промо-материалы
const promoTriggers = await db.query(`
  SELECT pm.*, pt.*
  FROM promo_triggers pt
  JOIN promo_materials pm ON pm.id = pt.promo_material_id
  WHERE pt.tenant_id = $1
    AND pt.is_active = true
    AND pt.trigger_type = $2
    AND pt.trigger_conditions @> $3
  ORDER BY pt.priority DESC
`, [tenant_id, event, JSON.stringify(event_data)]);

// 2. Отправить каждый материал
for (const promo of promoTriggers) {
  // Получить канал клиента
  const appeal = await getAppeal(appeal_id);
  const client = await getClient(appeal.client_id);
  const preferredChannel = getPreferredChannel(client);

  // Отправить
  await sendPromo({
    appeal_id,
    promo_material_id: promo.id,
    channel_id: preferredChannel,
    content_type: promo.type,
    content: promo.content_url || promo.content_text
  });

  // Сохранить историю
  await savePromoHistory({
    appeal_id,
    promo_material_id: promo.id,
    promo_trigger_id: promo.trigger_id,
    channel_id: preferredChannel,
    sent_type: 'automatic'
  });
}
```

### Примеры триггеров

**1. Отправка схемы прохода при отправке QR:**
```json
{
  "trigger_type": "stage_change",
  "trigger_conditions": {
    "to_stage_id": "qr-code-sent-uuid"
  },
  "promo_material_id": "scheme-map-uuid"
}
```

**2. Видео замены дисплея для iPhone:**
```json
{
  "trigger_type": "brand_model",
  "trigger_conditions": {
    "brand_id": "apple-uuid",
    "repair_category_id": "display-uuid"
  },
  "promo_material_id": "iphone-display-replacement-video-uuid"
}
```

---

## BAT Price Calculator (NEW)

### Назначение

Полуавтоматический расчёт стоимости ремонта на основе прайс-листов поставщиков.

### Вход

```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "brand_id": "uuid",
  "model_id": "uuid",
  "repair_category_id": "uuid",
  "issue_type_id": "uuid"
}
```

### Процесс

```javascript
// 1. Найти подходящие детали из прайсов
const partsMatches = await db.query(`
  SELECT pli.*, pm.retail_price, pm.retail_markup_percent, pl.supplier_name
  FROM price_mappings pm
  JOIN price_list_items pli ON pli.id = pm.price_list_item_id
  JOIN price_lists pl ON pl.id = pli.price_list_id
  WHERE pm.tenant_id = $1
    AND (pm.brand_id = $2 OR pm.brand_id IS NULL)
    AND (pm.model_id = $3 OR pm.model_id IS NULL)
    AND pm.repair_category_id = $4
    AND (pm.issue_type_id = $5 OR pm.issue_type_id IS NULL)
    AND pl.is_active = true
  ORDER BY pm.is_verified DESC, pli.price ASC
`, [tenant_id, brand_id, model_id, repair_category_id, issue_type_id]);

// 2. Вернуть список подходящих деталей оператору
return {
  suggestions: partsMatches.map(p => ({
    part_name: p.part_name,
    part_number: p.part_number,
    supplier: p.supplier_name,
    purchase_price: p.price,
    retail_price: p.retail_price,
    markup_percent: p.retail_markup_percent
  }))
};
```

### Вызов из Android-приложения

```kotlin
// Оператор нажимает "Рассчитать стоимость"
val suggestions = apiClient.post("/webhook/price-calculator", {
  appeal_id = appealId,
  tenant_id = tenantId,
  brand_id = appeal.brand_id,
  model_id = appeal.model_id,
  repair_category_id = repair.repair_category_id,
  issue_type_id = repair.issue_type_id
})

// Показать список деталей оператору
showPriceSuggestions(suggestions)

// Оператор выбирает деталь и подтверждает цену
confirmPrice(selectedPart, finalPrice)
```

---

## BAT CRM Sync (NEW)

### Назначение

Синхронизация заявок с внешними CRM (LiveSklad, RemOnline).

### Вход

```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "sync_type": "create_order",
  "crm_type": "livesklad"
}
```

### Процесс

```javascript
// 1. Получить настройки интеграции
const crmIntegration = await getCRMIntegration(tenant_id, crm_type);

// 2. Получить данные заявки
const appeal = await getAppealFull(appeal_id); // с устройствами и ремонтами

// 3. Сформировать payload для CRM
let payload;

if (appeal.is_multi_appeal) {
  // Мультизаявка
  payload = {
    client: formatClient(appeal.client),
    devices: appeal.devices.map(d => ({
      brand: d.brand.name,
      model: d.model.name,
      repairs: d.repairs.map(r => ({
        issue: r.issue_type.name,
        cost: r.estimated_cost
      }))
    }))
  };
} else {
  // Простая заявка
  payload = {
    client: formatClient(appeal.client),
    device: {
      brand: appeal.brand.name,
      model: appeal.model.name
    },
    issue: appeal.issue_type.name,
    cost: appeal.estimated_cost
  };
}

// 4. Вызвать API CRM
const response = await callCRMAPI(crmIntegration, 'create_order', payload);

// 5. Сохранить результат
await saveCRMSyncHistory({
  appeal_id,
  tenant_id,
  crm_integration_id: crmIntegration.id,
  sync_type: 'create_order',
  direction: 'to_crm',
  external_order_id: response.order_id,
  status: 'success',
  request_payload: payload,
  response_payload: response
});

// 6. Обновить заявку
await updateAppeal({
  appeal_id,
  livesklad_order_id: response.order_id,
  synced_with_livesklad: true,
  synced_at: new Date()
});
```

### Триггеры синхронизации

1. **Клиент пришёл** (appeal_stage = "Клиент пришёл")
   - Создать заказ в CRM
   - Передать данные устройства и неисправности

2. **Ремонт завершён** (appeal_stage = "Ремонт выполнен")
   - Получить результат из CRM
   - Обновить итоговую стоимость

---

## BAT QR Code Manager (NEW)

### Назначение

Генерация и управление QR-кодами заявок.

### Функции

1. **Генерация QR-кода**
2. **Отправка QR-кода клиенту**
3. **Валидация сканирования**

### Генерация QR-кода

**Вход:**
```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid"
}
```

**Процесс:**
```javascript
// 1. Сформировать данные для QR
const qrData = {
  appeal_id,
  tenant_id,
  timestamp: Date.now()
};

// 2. Сгенерировать QR-код (библиотека qrcode)
const qrCodeBase64 = await QRCode.toDataURL(JSON.stringify(qrData));

// 3. Сохранить в БД
await updateAppeal({
  appeal_id,
  qr_code: qrCodeBase64
});

// 4. Вернуть QR
return { qr_code: qrCodeBase64 };
```

### Отправка QR-кода

**Вход:**
```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid"
}
```

**Процесс:**
```javascript
// 1. Получить QR-код
const appeal = await getAppeal(appeal_id);
const qrCodeBase64 = appeal.qr_code;

// 2. Конвертировать в PNG
const qrBuffer = Buffer.from(qrCodeBase64.split(',')[1], 'base64');

// 3. Отправить через канал клиента
const client = await getClient(appeal.client_id);
const channel = getPreferredChannel(client);

await sendMedia({
  channel,
  client,
  media_type: 'image',
  media_buffer: qrBuffer,
  caption: 'Ваш QR-код для визита в сервис. Предъявите его мастеру при визите.'
});

// 4. Обновить этап
await transitionStage({
  appeal_id,
  to_stage_id: getStageByName('QR-код отправлен').id,
  transition_type: 'automatic'
});
```

### Сканирование QR-кода (Android)

**Вход (от Android-приложения):**
```json
{
  "qr_data": "{\"appeal_id\":\"uuid\",\"tenant_id\":\"uuid\",\"timestamp\":1700000000}",
  "scanned_by_operator_id": "uuid"
}
```

**Процесс:**
```javascript
// 1. Парсинг QR
const { appeal_id, tenant_id, timestamp } = JSON.parse(qr_data);

// 2. Валидация
const appeal = await getAppeal(appeal_id);
if (!appeal) {
  return { error: 'Appeal not found' };
}

if (appeal.tenant_id !== tenant_id) {
  return { error: 'Invalid tenant' };
}

// 3. Обновить этап на "Клиент пришёл"
await transitionStage({
  appeal_id,
  to_stage_id: getStageByName('Клиент пришёл').id,
  operator_id: scanned_by_operator_id,
  transition_type: 'manual'
});

// 4. Обновить timestamp
await updateAppeal({
  appeal_id,
  visited_at: new Date()
});

// 5. Триггер синхронизации с CRM
await callWorkflow('BAT CRM Sync', {
  appeal_id,
  tenant_id,
  sync_type: 'create_order',
  crm_type: 'livesklad'
});

return { success: true, appeal };
```

---

## BAT Voice Processor (NEW)

### Назначение

Обработка голосовых сообщений и звонков.

### Функции

1. **Транскрибация голосовых сообщений** (Whisper API)
2. **Транскрибация звонков** (InCallService → Whisper)
3. **Форматирование голосовых ответов оператора** (SpeechRecognizer → AI)

### Транскрибация голосовых сообщений

**Вход:**
```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "audio_url": "https://cdn.example.com/audio/voice123.ogg",
  "source_type": "voice_message"
}
```

**Процесс:**
```javascript
// 1. Скачать аудиофайл
const audioBuffer = await downloadFile(audio_url);

// 2. Вызвать Whisper API
const transcription = await whisperAPI.transcribe({
  audio: audioBuffer,
  language: 'ru'
});

// 3. Сохранить транскрипт
await saveVoiceTranscription({
  tenant_id,
  appeal_id,
  source_type: 'voice_message',
  audio_url,
  audio_duration_sec: transcription.duration,
  transcription_text: transcription.text,
  transcription_engine: 'whisper',
  language: 'ru',
  confidence: transcription.confidence
});

// 4. Сохранить как текстовое сообщение
await saveMessage({
  tenant_id,
  appeal_id,
  message_type: 'client',
  message_text: `[Голосовое сообщение]: ${transcription.text}`,
  channel_id: getChannelByPlatform('whatsapp')
});

// 5. Отправить на Appeal Router для AI-обработки
await callWorkflow('BAT Appeal Router', {
  appeal_id,
  tenant_id,
  trigger: 'voice_transcribed'
});
```

### Транскрибация звонков

**Вход (от Android InCallService):**
```json
{
  "appeal_id": "uuid",
  "tenant_id": "uuid",
  "audio_url": "https://cdn.example.com/calls/call456.mp3",
  "source_type": "incoming_call",
  "duration_sec": 180
}
```

**Процесс:** Аналогично голосовым сообщениям

---

### Форматирование голосовых ответов

**Endpoint:** `/webhook/voice-response-formatter` (используется BAT AI Voice Response Formatter)

**Вход (от Android SpeechRecognizer):**
```json
{
  "raw_text": "привет да стоимость замены дисплея айфон тринадцать восемь с половиной тысяч рублей",
  "appeal_id": "uuid",
  "tenant_id": "uuid"
}
```

**Процесс:**
```javascript
// 1. Получить контекст заявки
const appealContext = await getAppealContext(appeal_id);

// 2. Вызвать AI для оформления
const formattedText = await callAI({
  tool: 'voice_response_formatter',
  input: {
    raw_text,
    tenant_id,
    appeal_context: appealContext
  }
});

// 3. Вернуть оформленный текст
return { formatted_text: formattedText };
```

**Использование в Android:**
```kotlin
// 1. Оператор нажимает кнопку микрофона
speechRecognizer.startListening(recognizerIntent)

// 2. Получен результат распознавания
override fun onResults(results: Bundle?) {
  val rawText = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0)

  // 3. Отправить на форматирование
  val response = apiClient.post("/webhook/voice-response-formatter", {
    raw_text = rawText,
    appeal_id = appealId,
    tenant_id = tenantId
  })

  // 4. Отправить оформленный текст клиенту
  sendMessage(response.formatted_text)
}
```

---

## BAT Remarketing Exporter (NEW)

### Назначение

Формирование и экспорт аудиторий ремаркетинга в рекламные платформы.

### Функции

1. **Формирование аудиторий** по критериям
2. **Экспорт в Яндекс.Директ**
3. **Экспорт в VK Реклама**
4. **Экспорт в Google Ads**

### Формирование аудиторий

**Критерии:**
```javascript
// Аудитория "Цена отправлена, но не подтвердил"
const audience1 = await db.query(`
  SELECT DISTINCT c.phone, c.email
  FROM clients c
  JOIN appeals a ON a.client_id = c.id
  JOIN appeal_stages ast ON ast.id = a.appeal_stage_id
  WHERE a.tenant_id = $1
    AND ast.name = 'Цена отправлена'
    AND a.created_at > NOW() - INTERVAL '7 days'
`, [tenant_id]);

// Аудитория "Успешные ремонты"
const audience2 = await db.query(`
  SELECT DISTINCT c.phone, c.email
  FROM clients c
  JOIN appeals a ON a.client_id = c.id
  JOIN appeal_stages ast ON ast.id = a.appeal_stage_id
  WHERE a.tenant_id = $1
    AND ast.name = 'Ремонт выполнен'
    AND a.completed_at > NOW() - INTERVAL '30 days'
`, [tenant_id]);
```

### Экспорт в Яндекс.Директ

**Вход:**
```json
{
  "tenant_id": "uuid",
  "audience_name": "Цена отправлена - не подтвердил",
  "audience_type": "price_sent_no_confirm"
}
```

**Процесс:**
```javascript
// 1. Сформировать аудиторию
const audience = await formAudience(tenant_id, audience_type);

// 2. Получить credentials Яндекс.Директ
const yandexCreds = await getMarketingIntegration(tenant_id, 'yandex_direct');

// 3. Вызвать API Яндекс.Директ
const response = await yandexAPI.createAudience({
  oauth_token: yandexCreds.oauth_token,
  name: audience_name,
  type: 'CRM',
  content: audience.map(c => ({ phone: c.phone }))
});

// 4. Сохранить историю экспорта
await saveRemarketingExportHistory({
  tenant_id,
  platform: 'yandex_direct',
  audience_type,
  audience_size: audience.length,
  external_audience_id: response.audience_id,
  status: 'success'
});
```

---

## BAT Data Archiver (NEW)

### Назначение

Автоматическая архивация старых данных согласно TTL политикам.

### Функции

1. **Проверка политик хранения**
2. **Архивация данных**
3. **Удаление архивированных данных**

### Процесс (Cron: каждую ночь в 03:00)

```javascript
// 1. Получить все активные политики
const policies = await db.query(`
  SELECT * FROM data_retention_policies WHERE is_active = true
`);

for (const policy of policies) {
  // 2. Найти данные для архивации
  const expiredRecords = await db.query(`
    SELECT * FROM ${policy.entity_type}
    WHERE tenant_id = $1
      AND created_at < NOW() - INTERVAL '${policy.retention_days} days'
  `, [policy.tenant_id]);

  if (policy.action_type === 'archive') {
    // 3. Архивировать
    for (const record of expiredRecords) {
      await db.query(`
        INSERT INTO archived_data (tenant_id, entity_type, entity_id, entity_data, archived_at)
        VALUES ($1, $2, $3, $4, NOW())
      `, [
        policy.tenant_id,
        policy.entity_type,
        record.id,
        JSON.stringify(record)
      ]);
    }

    // 4. Удалить из основной таблицы
    await db.query(`
      DELETE FROM ${policy.entity_type}
      WHERE id = ANY($1)
    `, [expiredRecords.map(r => r.id)]);

  } else if (policy.action_type === 'delete') {
    // Просто удалить
    await db.query(`
      DELETE FROM ${policy.entity_type}
      WHERE id = ANY($1)
    `, [expiredRecords.map(r => r.id)]);
  }

  console.log(`Archived ${expiredRecords.length} records from ${policy.entity_type}`);
}
```

---

## Вспомогательные workflows

### BAT FCM Sender

**Назначение:** Отправка Push-уведомлений операторам

**Вход:**
```json
{
  "operator_id": "uuid",
  "tenant_id": "uuid",
  "title": "Новое сообщение",
  "body": "Клиент написал: ...",
  "data": {
    "appeal_id": "uuid",
    "action": "open_appeal"
  }
}
```

**Процесс:**
```javascript
// 1. Получить FCM токены всех активных устройств оператора
const devices = await db.query(`
  SELECT fcm_token FROM operator_devices
  WHERE operator_id = $1 AND is_active = true AND fcm_token IS NOT NULL
`, [operator_id]);

// 2. Отправить FCM для каждого устройства
for (const device of devices) {
  await fcmAPI.send({
    token: device.fcm_token,
    notification: { title, body },
    data: data
  });
}
```

---

### BAT Error Handler

**Назначение:** Централизованная обработка ошибок из всех workflows

**Вход:**
```json
{
  "workflow_name": "BAT Appeal Router",
  "error_message": "Failed to call AI API",
  "error_stack": "...",
  "context": {
    "appeal_id": "uuid",
    "tenant_id": "uuid"
  }
}
```

**Процесс:**
1. Логировать ошибку в БД (`error_logs` таблица)
2. Отправить уведомление администратору
3. Если критическая ошибка — создать incident в мониторинге

---

### BAT Health Check

**Назначение:** Проверка здоровья системы

**Endpoint:** `/webhook/health-check`

**Процесс:**
```javascript
const healthStatus = {
  database: await checkDatabaseConnection(),
  ai_api: await checkAIAPI(),
  fcm: await checkFCM(),
  whatsapp: await checkWhatsAppAPI(),
  telegram: await checkTelegramAPI()
};

return {
  status: Object.values(healthStatus).every(s => s === 'ok') ? 'healthy' : 'degraded',
  checks: healthStatus
};
```

---

## Мониторинг и отладка

### Логирование

**Все workflows логируют:**
- Входные параметры
- Выходные данные
- Ошибки
- Время выполнения

**Таблица `workflow_execution_logs`:**
```sql
CREATE TABLE workflow_execution_logs (
  id uuid PRIMARY KEY,
  workflow_name varchar NOT NULL,
  execution_id varchar NOT NULL,
  input_data jsonb,
  output_data jsonb,
  error jsonb,
  duration_ms int,
  created_at timestamp DEFAULT now()
);
```

### Мониторинг производительности

**Метрики:**
- Среднее время выполнения workflow
- Частота ошибок
- Throughput (запросов в секунду)

**Инструменты:**
- n8n встроенная статистика
- Custom Prometheus exporter
- Grafana дашборды

---

## Связанные документы

- [01_SYSTEM_OVERVIEW.md](01_SYSTEM_OVERVIEW.md) — Общий обзор системы
- [02_DATABASE_SCHEMA_EXTENDED.md](02_DATABASE_SCHEMA_EXTENDED.md) — Схема БД
- [04_API_SPECIFICATION.md](04_API_SPECIFICATION.md) — REST API спецификация
- [05_ANDROID_APP_SPEC.md](05_ANDROID_APP_SPEC.md) — Спецификация Android

---

**Версия:** 2.0
**Дата:** 2025-11-23
**Workflows:** 3 существующих + ~12 новых
**Статус:** ✅ Готово к реализации
