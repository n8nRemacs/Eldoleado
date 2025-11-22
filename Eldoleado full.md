# 📊 Полный анализ проекта BatteryCRM

## 🏗️ Общая архитектура системы

Это **Multi-Tenant CRM система** для сервисных центров по ремонту техники (в основном iPhone) с интеграцией:
- **Входящие каналы**: Telegram, VK, WhatsApp, Avito, MAX, Формы, Телефон
- **AI-обработка**: Автоматическое извлечение параметров обращений через Claude AI
- **Android-приложение**: Мобильное приложение для операторов
- **Push-уведомления**: FCM для Android

---

## 🔄 ПОЛНАЯ БИЗНЕС-ЛОГИКА: Путь сообщения клиента

### **ЭТАП 1: Входящее сообщение клиента**

```
Клиент пишет в Telegram → BAT IN Telegram
```

**Workflow: BAT IN Telegram**
1. **Telegram Trigger** - получает сообщение
2. **Switch** - определяет тип (текст/голос)
3. **Обработка голоса** (если есть):
   - Get Voice File → Transcribe Voice (OpenAI Whisper) → Normalize with Voice
4. **Normalize Input** - формирует унифицированную структуру:
```json
{
  "channel": "telegram",
  "external_user_id": "6416413182",
  "external_chat_id": "6416413182",
  "text": "Здравствуйте, хочу заменить батарею",
  "timestamp": "2025-10-20T07:02:00.000Z",
  "client_name": "РемАкс",
  "bot_token": "8411509872:..."
}
```
5. **Execute Tenant Resolver** → **Execute Universal Batcher**

---

### **ЭТАП 2: Определение Tenant**

```
BAT_Tenant_Resolver
```

**Логика:**
1. **Prepare Lookup** - определяет lookup_key по каналу:
   - Telegram → `telegram_bot_token`
   - VK → `vk_app_id`
   - WhatsApp → `whatsapp_phone_id`

2. **Find Tenant** - SQL запрос:
```sql
SELECT t.id as tenant_id, t.name as tenant_name
FROM tenants t
LEFT JOIN tenant_configs tc ON tc.tenant_id = t.id
WHERE tc.key = 'telegram_bot_token'
  AND tc.value->>'token' = '8411509872:...'
  AND t.is_active = true
```

3. **Результат:**
```json
{
  "tenant_id": "a0000000-0000-0000-0000-000000000001",
  "tenant_name": "Default Tenant"
}
```

---

### **ЭТАП 3: Поиск или создание клиента**

```
BAT Client Resolver
```

**Логика:**

1. **Find Client** - SQL запрос:
```sql
SELECT c.*, cm.master_client_id
FROM clients c
LEFT JOIN client_merges cm ON c.id = cm.merged_client_id
WHERE c.tenant_id = 'a0...'
  AND (c.telegram_id = '6416413182' OR c.phone = '+7...')
```

2. **Client Exists?** - IF условие:
   - **Да** → **Merge Found Client** → Execute Appeal Manager
   - **Нет** → **Execute Client Creator**

---

### **ЭТАП 4: Создание клиента (если не найден)**

```
BAT Client Creator
```

**SQL:**
```sql
INSERT INTO clients (
  tenant_id, phone, name, telegram_id, vk_id, whatsapp_id, avito_id
)
VALUES (
  'a0...', NULL, 'РемАкс', '6416413182', NULL, NULL, NULL
)
RETURNING *;
```

**Результат:**
```json
{
  "client": {
    "id": "f08a1543-c066-4aa0-847b-bd8f1321ee75",
    "name": "РемАкс",
    "telegram_id": "6416413182"
  },
  "client_found": true
}
```

---

### **ЭТАП 5: Управление обращениями (Appeals)**

```
BAT Appeal Manager
```

**Логика:**

1. **Find Active Appeal** - ищет активное обращение клиента:
```sql
SELECT a.id, a.stage, a.phone_model, a.model_id, a.operation_mode
FROM appeals a
WHERE a.client_id = 'f08a1543-...'
  AND a.tenant_id = 'a0...'
  AND a.stage NOT IN ('Завершено', 'Отменено')
  AND a.created_at > NOW() - INTERVAL '7 days'
ORDER BY a.created_at DESC
LIMIT 1
```

2. **Appeal Exists?**:
   - **Да** → **Merge Existing Appeal** (продолжаем диалог)
   - **Нет** → **Create New Appeal**

3. **Create New Appeal**:
```sql
INSERT INTO appeals (
  tenant_id, client_id, ad_channel_id, stage, operation_mode
)
VALUES (
  'a0...', 'f08...', (SELECT id FROM channels WHERE code = 'telegram'),
  'Первичный контакт', 'assist'
)
RETURNING *;
```

4. **Save Message History** - сохраняем сообщение клиента:
```sql
INSERT INTO messages_history (
  tenant_id, appeal_id, message_type, message_text, channel, created_at
)
VALUES (
  'a0...', '07af5e27-...', 'client', 'Здравствуйте', 'telegram', NOW()
)
```

5. **Prepare Contract** - формирует полный контекст
6. **Execute AI Extractor** → переходим к AI обработке

---

### **ЭТАП 6: AI обработка обращения**

```
BAT AI Appeal Router
```

**Мощнейший workflow! Вот его полная логика:**

#### **6.1. Load Appeal & History**
```sql
SELECT a.*, b.name as brand_name, m.name as model_name,
       rt.name as repair_type_name,
       COALESCE(jsonb_agg(mh.*), '[]') as messages
FROM appeals a
LEFT JOIN brands b ON b.id = a.brand_id
LEFT JOIN models m ON m.id = a.model_id
LEFT JOIN repair_types rt ON rt.id = a.repair_type_id
LEFT JOIN messages_history mh ON mh.appeal_id = a.id
WHERE a.id = '07af5e27-...'
GROUP BY a.id, b.name, m.name, rt.name
```

**Результат:** Полная история диалога + текущие параметры appeal

#### **6.2. Load Reference Data**
```sql
SELECT
  (SELECT jsonb_agg(jsonb_build_object('id', id, 'name', name)) FROM brands) as brands,
  (SELECT jsonb_agg(jsonb_build_object('id', id, 'name', name)) FROM repair_types) as repair_types
```

**Получаем справочники** для AI (список брендов, типов ремонта)

#### **6.3. Format Context for AI**
Формирует контекст для AI:
```javascript
conversationHistory = [
  "[09:21] Клиент: Здравствуйте, хочу заменить батарею",
  "[09:22] Бот: На каком устройстве?",
  "[09:23] Клиент: iPhone 14",
  "[НОВОЕ] Клиент: <текущее сообщение>"
]

currentState = {
  type: null,
  brand: null,
  model: null,
  repair_type: null,
  parts_owner: null
}

missing_fields = "тип обращения, бренд, модель, тип ремонта"
```

#### **6.4. Принудительный вызов 4 AI Tools**

**КРИТИЧНО!** Система **последовательно** вызывает 4 AI агента:

**Tool 1: Определить тип обращения**
```javascript
Call Tool - Type →
результат: { type: "ремонт", confidence: 0.9 }
```

**Tool 2: Извлечь бренд и модель**
```javascript
Call Tool - Model →
результат: { brand_id: "b808...", model: "iPhone 14", model_id: "..." }
```

**Tool 3: Определить тип ремонта**
```javascript
Call Tool - Repair →
результат: { repair_type: "Батарея", repair_type_id: "34962870-..." }
```

**Tool 4: Определить чья запчасть**
```javascript
Call Tool - Parts →
результат: { parts_owner: "наша", confidence: 1.0 }
```

#### **6.5. Collect Tool Results**
Собирает все данные:
```json
{
  "extracted_data": {
    "type": "ремонт",
    "model": "iPhone 14",
    "model_id": "xxx",
    "brand_id": "yyy",
    "repair_type": "Батарея",
    "repair_type_id": "zzz",
    "parts_owner": "наша"
  },
  "missing_fields": "",
  "next_action": "calculate_cost"
}
```

#### **6.6. AI Agent (формирование ответа)**
```
OpenAI GPT → Postgres Chat Memory (история диалога)
```

**Промпт:**
```
Ты — дружелюбный ассистент сервисного центра.

Данные об обращении (уже извлечены):
- Тип: ремонт
- Модель: iPhone 14
- Проблема: Батарея
- Запчасть: наша

Что не хватает: всё заполнено

Твоя задача: Сформировать естественный ответ клиенту.
```

**Ответ AI:**
```
"Отлично! Замена батареи на iPhone 14, запчасть будет наша.
Стоимость 3500₽, время работы 30 минут. Когда вам удобно подъехать?"
```

#### **6.7. Route by Completeness & Update Appeal**

**Динамический SQL формируется в зависимости от полноты данных:**

```sql
UPDATE appeals SET
  type = 'ремонт',
  model_id = 'xxx'::uuid,
  brand_id = 'yyy'::uuid,
  repair_type_id = 'zzz'::uuid,
  parts_owner = 'наша',
  phone_model = 'iPhone 14',
  routing_history = COALESCE(routing_history, '[]'::jsonb) ||
    '{"timestamp": "...", "action": "calculate_cost", "route": "full"}'::jsonb,
  updated_at = NOW()
WHERE id = '07af5e27-...'::uuid
RETURNING *;
```

#### **6.8. Route Decision**
Определяет следующее действие:
```javascript
if (extracted_data полный) {
  next_action = "notify_operator" // Уведомить оператора
} else if (extracted_data частичный) {
  next_action = "ask_model" // Спросить модель
} else {
  next_action = "ask_type" // Спросить тип
}
```

#### **6.9. Call BAT Operator Notifier**

---

### **ЭТАП 7: Уведомление операторов**

```
BAT Operator Notifier
```

**Логика:**

1. **Prepare Notification** - форматирует сообщение для операторов:
```javascript
text = `
🔔 Новое обращение

👤 Клиент: РемАкс
💬 Канал: telegram

✅ Устройство: iPhone 14
✅ Проблема: Батарея
📦 Запчасть: наша
✅ Тип: ремонт

💬 Сообщение клиента:
"Хочу заменить батарею"

🤖 Предложенный ответ:
"Отлично! Замена батареи на iPhone 14..."

<appeal_id>
`
```

2. **Get Active Operators** - получает всех активных операторов:
```sql
SELECT id, telegram_id, fcm_token, name
FROM operators
WHERE is_active = true
  AND tenant_id = 'a0...'
  AND (telegram_id IS NOT NULL OR fcm_token IS NOT NULL)
```

3. **Merge Notification with Operators** - для каждого оператора формирует пакет данных

4. **Параллельная отправка**:
   - **Send to Operators** (Telegram) → отправка в Telegram оператора
   - **Call BAT_FCM_Sender** → отправка push-уведомления на Android

5. **Execute a SQL query** - маппинг message_id → appeal_id:
```sql
INSERT INTO operator_message_appeal_map (
  tenant_id, operator_telegram_id, message_id, appeal_id
)
VALUES ('a0...', '123456', 994, '07af5e27-...')
```

6. **Log Operator Action** - логирование действия:
```sql
INSERT INTO operator_actions (
  tenant_id, operator_telegram_id, action_type, details
)
VALUES (
  'a0...', '123456', 'notify_appeal_ready',
  '{"appeal_id": "07af...", "message_id": 994}'
)
```

7. **Update Appeal Stage**:
```sql
UPDATE appeals SET
  stage = 'Требуется уточнение',
  operation_mode = 'manual',
  updated_at = NOW()
WHERE id = '07af5e27-...'
```

---

### **ЭТАП 8: FCM Push-уведомления**

```
BAT_FCM_Sender
```

**Логика:**

1. **Parse Input** - обрабатывает входные данные:
```json
{
  "tenant_id": "a0...",
  "notification_type": "new_appeal",
  "appeal_id": "07af...",
  "title": "🔔 Новое обращение",
  "body": "РемАкс MD - telegram"
}
```

2. **Get Operators with FCM** - находит операторов с FCM токенами:
```sql
SELECT id, fcm_token, name
FROM operators
WHERE is_active = true
  AND tenant_id = 'a0...'
  AND fcm_token IS NOT NULL
```

3. **Generate JWT Token** - создает JWT для авторизации в FCM:
```javascript
const jwt = require('jsonwebtoken');
const payload = {
  iss: 'firebase-adminsdk-fbsvc@batterycrm.iam.gserviceaccount.com',
  sub: 'firebase-adminsdk-fbsvc@batterycrm.iam.gserviceaccount.com',
  aud: 'https://oauth2.googleapis.com/token',
  iat: now,
  exp: now + 3600,
  scope: 'https://www.googleapis.com/auth/firebase.messaging'
};
const token = jwt.sign(payload, privateKey, { algorithm: 'RS256' });
```

4. **Get Access Token** - обменивает JWT на access_token:
```
POST https://oauth2.googleapis.com/token
grant_type=urn:ietf:params:oauth:grant-type:jwt-bearer&assertion=<jwt_token>
```

5. **Send FCM** - отправляет push-уведомление:
```json
POST https://fcm.googleapis.com/v1/projects/batterycrm/messages:send
{
  "message": {
    "token": "<fcm_token>",
    "notification": {
      "title": "🔔 Новое обращение",
      "body": "РемАкс MD - telegram"
    },
    "data": {
      "type": "new_appeal",
      "appeal_id": "07af5e27-...",
      "client_name": "РемАкс MD",
      "channel": "telegram",
      "priority": "high"
    },
    "android": {
      "priority": "high"
    }
  }
}
```

6. **Summary** - формирует отчет:
```json
{
  "success": true,
  "total_operators": 3,
  "sent_count": 3,
  "failed_count": 0
}
```

---

### **ЭТАП 9: Отправка ответа клиенту**

```
BAT Message Router → BAT OUT Telegram
```

**BAT Message Router:**
1. **Prepare OUT Message** - формирует сообщение для отправки:
```json
{
  "channel": "telegram",
  "external_chat_id": "6416413182",
  "message_text": "Отлично! Замена батареи на iPhone 14...",
  "appeal_id": "07af5e27-...",
  "client_id": "f08a1543-..."
}
```

2. **Switch** - маршрутизация по каналу (telegram/vk/whatsapp/avito/max)
3. **Execute OUT Telegram**

**BAT OUT Telegram:**
1. **Prepare Context** - подготовка контекста
2. **Fetch Chat Data** - получение данных чата
3. **Merge Context** - объединение данных
4. **Send Telegram Message** - отправка через Telegram API:
```
POST https://api.telegram.org/bot8411509872:AAF.../sendMessage
{
  "chat_id": "6416413182",
  "text": "Отлично! Замена батареи на iPhone 14...",
  "parse_mode": "Markdown"
}
```

5. **Save Message History** - сохранение в БД:
```sql
INSERT INTO messages_history (
  tenant_id, appeal_id, message_type, message_text, channel
)
VALUES (
  'a0...', '07af...', 'operator', 'Отлично! Замена...', 'telegram'
)
```

---

## 📱 ANDROID APP: Работа оператора

### **API 1: Авторизация**

```
POST /webhook/android/auth/login
```

**Логика (API_Android_Auth):**
1. Проверка credentials
2. **Удаление старых сессий**:
```sql
DELETE FROM operator_devices
WHERE operator_id = '7b3aac9e-...' AND device_type = 'mobile'
```

3. **Создание новой сессии**:
```sql
INSERT INTO operator_devices (
  operator_id, tenant_id, device_type, session_token, fcm_token, device_info
)
VALUES (
  '7b3aac9e-...', 'a0...', 'mobile',
  '4e9afe35-6901-4655-8226-5976e3dc4dd7',
  '<fcm_token>',
  '{"manufacturer": "Samsung", "model": "Galaxy S23"}'
)
```

**Response:**
```json
{
  "success": true,
  "session_token": "4e9afe35-6901-4655-8226-5976e3dc4dd7",
  "operator": {
    "id": "7b3aac9e-...",
    "name": "Иван Иванов"
  }
}
```

---

### **API 2: Список обращений**

```
GET /webhook/api/operator/appeals/list?status=new&limit=20
Header: x-session-token: 4e9afe35-...
```

**Логика (API_Operator_Appeals_List):**

1. **Get Tenant по operator_id**:
```sql
SELECT od.operator_id, od.tenant_id
FROM operator_devices od
JOIN operators o ON o.id = od.operator_id
WHERE od.session_token = '4e9afe35-...'
  AND od.device_type = 'mobile'
  AND o.is_active = true
```

2. **Fetch Appeals** - получение списка обращений:
```sql
SELECT a.id, a.stage, a.updated_at,
       c.name AS client_name, c.phone AS client_phone,
       b.name AS brand_name, m.name AS model_name,
       a.phone_model, a.parts_owner, a.estimated_cost,
       (SELECT mh.message_text FROM messages_history mh
        WHERE mh.appeal_id = a.id ORDER BY mh.created_at DESC LIMIT 1) AS last_message
FROM appeals a
JOIN clients c ON c.id = a.client_id
LEFT JOIN brands b ON b.id = a.brand_id
LEFT JOIN models m ON m.id = a.model_id
WHERE a.tenant_id = 'a0...'
  AND a.stage = 'Первичный контакт'
ORDER BY a.updated_at DESC
LIMIT 20
```

3. **Fetch Counts** - получение счетчиков:
```sql
SELECT
  SUM((a.stage = 'Первичный контакт')::int) AS new,
  SUM((a.stage = 'Требуется уточнение')::int) AS in_progress,
  SUM((a.stage = 'Завершено')::int) AS completed
FROM appeals a
WHERE a.tenant_id = 'a0...'
```

4. **Format Response**:
```json
{
  "success": true,
  "appeals": [
    {
      "id": "07af5e27-...",
      "client": { "name": "РемАкс", "phone": null },
      "device": { "model": "iPhone 14", "brand": "Apple" },
      "problem": "Запчасть: наша",
      "status": "Первичный контакт",
      "last_message": "Отлично! Замена батареи...",
      "updated_at": "2025-11-15T09:21:55.139Z"
    }
  ],
  "counts": { "new": 15, "in_progress": 8, "completed": 142 }
}
```

---

### **API 3: Детали обращения**

```
GET /webhook/.../api/operator/appeals/07af5e27-.../detail
Header: x-session-token: 4e9afe35-...
```

**Логика (API_Operator_Appeal_Detail):**

1. **Get Tenant** - получение tenant_id

2. **Fetch Appeal** - получение обращения:
```sql
SELECT a.*,
       c.name as client_name, c.phone as client_phone,
       b.name as brand_name, m.name as model_name,
       rt.name as repair_type_name
FROM appeals a
JOIN clients c ON c.id = a.client_id
LEFT JOIN brands b ON b.id = a.brand_id
LEFT JOIN models m ON m.id = a.model_id
LEFT JOIN repair_types rt ON rt.id = a.repair_type_id
WHERE a.id = '07af5e27-...' AND a.tenant_id = 'a0...'
```

3. **Tool - Build Appeal Meta** - формирование метаданных:

**Fetch Appeal Data:**
```sql
SELECT a.stage, a.deal_type, a.sales_channel, a.repair_type,
       a.parts_owner, a.estimated_cost, a.estimated_time,
       c.name as client_name, c.phone as client_phone,
       b.name as brand_model, it.name as issue_type, i.name as issue
FROM appeals a
JOIN clients c ON c.id = a.client_id
LEFT JOIN brands b ON b.id = a.brand_id
...
WHERE a.id = '07af5e27-...'
```

**Fetch Meta Config:**
```sql
SELECT mc.id, mc.label, mc.group, mc.order, mc.source_field, mc.format_type
FROM appeal_meta_config mc
LEFT JOIN appeal_meta_visibility mv
  ON mv.config_id = mc.id
  AND mv.deal_type_id = '<deal_type_id>'
WHERE mc.is_active = true
  AND (mv.is_visible = true OR mv.id IS NULL)
ORDER BY mc.order
```

**Build Meta Array:**
```javascript
meta = [
  {
    "id": "stage",
    "label": "Стадия",
    "value": "Первичный контакт",
    "group": "appeal",
    "order": 100
  },
  {
    "id": "client_name",
    "label": "Клиент",
    "value": "РемАкс",
    "group": "client",
    "order": 300
  },
  {
    "id": "brand_model",
    "label": "Устройство",
    "value": "iPhone 14",
    "group": "device",
    "order": 400
  }
]
```

4. **Fetch Messages** - получение истории сообщений:
```sql
SELECT mh.id, mh.message_type, mh.message_text,
       mh.created_at, o.name as operator_name
FROM messages_history mh
LEFT JOIN operators o ON mh.message_type = 'operator'
WHERE mh.appeal_id = '07af5e27-...'
ORDER BY mh.created_at ASC
```

**Response:**
```json
{
  "success": true,
  "appeal": {
    "id": "07af5e27-...",
    "stage": "Первичный контакт",
    "client": { "name": "РемАкс", "phone": null },
    "device": { "brand": "Apple", "model": "iPhone 14" },
    "meta": [
      { "id": "stage", "label": "Стадия", "value": "Первичный контакт" },
      { "id": "estimated_cost", "label": "Стоимость", "value": "3500 ₽" }
    ]
  },
  "messages": [
    {
      "id": "msg-001",
      "type": "client",
      "text": "Здравствуйте, хочу заменить батарею",
      "created_at": "2025-11-15T09:21:17.000Z"
    },
    {
      "id": "msg-002",
      "type": "operator",
      "text": "Отлично! Замена батареи на iPhone 14...",
      "created_at": "2025-11-15T09:21:55.000Z",
      "operator_name": "AI Assistant"
    }
  ]
}
```

---

### **API 4: Взять обращение в работу**

```
POST /webhook/.../api/operator/appeals/07af5e27-.../take
Header: x-session-token: 4e9afe35-...
```

**Логика (API_Operator_Take_Appeal):**

1. **Update Appeal**:
```sql
UPDATE appeals SET
  stage = 'В работе',
  operation_mode = 'manual',
  updated_at = NOW()
WHERE id = '07af5e27-...' AND tenant_id = 'a0...'
```

2. **Create/Update Status**:
```sql
INSERT INTO operator_appeal_status (
  tenant_id, operator_id, appeal_id, status, viewed_at
)
VALUES ('a0...', '7b3aac9e-...', '07af5e27-...', 'in_progress', NOW())
ON CONFLICT (tenant_id, operator_id, appeal_id)
DO UPDATE SET status = 'in_progress', viewed_at = NOW()
```

**Response:**
```json
{
  "success": true,
  "message": "Appeal taken successfully"
}
```

---

### **API 5: Отправить ответ клиенту**

```
POST /webhook/.../api/operator/appeals/07af5e27-.../send
Header: x-session-token: 4e9afe35-...
Body: {
  "response_text": "Привет! Подъезжайте сегодня в 14:00"
}
```

**Логика (API_Operator_Send_Response):**

1. **Parse Params** - парсинг параметров

2. **Get Context** - получение контекста:
```sql
SELECT o.id as operator_id, o.tenant_id,
       a.client_id, a.ad_channel as channel,
       c.telegram_id as client_telegram_id
FROM operators o
JOIN appeals a ON a.id = '07af5e27-...'
JOIN clients c ON c.id = a.client_id
WHERE o.session_token = '4e9afe35-...'
  AND o.is_active = true
```

3. **Prepare Message** - подготовка сообщения:
```json
{
  "channel": "telegram",
  "external_chat_id": "6416413182",
  "message_text": "Привет! Подъезжайте сегодня в 14:00",
  "appeal_id": "07af5e27-...",
  "client_id": "f08a1543-..."
}
```

4. **Call Message Router** → **BAT OUT Telegram** (см. ЭТАП 9)

5. **Update Status**:
```sql
UPDATE operator_appeal_status SET
  status = 'completed',
  completed_at = NOW()
WHERE appeal_id = '07af5e27-...' AND tenant_id = 'a0...'
```

**Response:**
```json
{
  "success": true,
  "message": "Response sent"
}
```

---

## 📊 СТРУКТУРА БАЗЫ ДАННЫХ (37 таблиц)

### **1. Multi-Tenant**
- `tenants` - компании (SaaS изоляция)
- `tenant_configs` - конфигурация (токены каналов, настройки)

### **2. Клиенты**
- `clients` - клиенты (с флагами has_telegram, has_whatsapp...)
- `client_merges` - объединение дубликатов клиентов

### **3. Обращения**
- `appeals` - обращения (37 полей!)
  - `stage`: Первичный контакт / Требуется уточнение / В работе / Завершено
  - `operation_mode`: assist / manual / auto
  - `type`, `type_identified`, `type_confidence` - AI классификация
  - `pending_operator_approval`, `draft_response` - режим assist
  - `routing_history` (jsonb) - история обработки AI

- `messages_history` - история сообщений
  - `message_type`: client / agent / operator
  - `channel`, `external_message_id`, `external_chat_id`

### **4. Операторы**
- `operators` - операторы
  - `session_token` (deprecated)
  - `telegram_id`, `fcm_token`

- `operator_devices` - устройства операторов
  - `device_type`: mobile / web / desktop
  - `session_token` - токен сессии
  - `fcm_token` - токен FCM
  - `device_info` (jsonb)

- `operator_actions` - лог действий операторов
- `operator_appeal_status` - статус обращения для оператора
- `operator_message_appeal_map` - маппинг message_id → appeal_id

### **5. Справочники**
- `channels` - каналы коммуникации
- `sales_channels` - каналы продаж
- `lead_sources` - источники лидов (с UTM)
- `device_types` - типы устройств
- `brands` - бренды (с normalized_name, aliases)
- `models` - модели
- `repair_types` - типы ремонта
- `issue_types` - типы проблем
- `issues` - конкретные проблемы
- `deal_types` - типы сделок (Ремонт, Покупка, Продажа, Спам)
- `services` - прайс-лист

### **6. Meta API**
- `appeal_meta_config` - конфигурация отображения параметров
  - `id`: stage, client_name, brand_model...
  - `label`: "Стадия", "Клиент", "Устройство"
  - `format_type`: text, date, duration, currency, boolean

- `appeal_meta_visibility` - правила видимости по типу сделки
  - Для "Спам" скрыты: issue_type, issue, repair_type...
  - Для "Покупка/Продажа" скрыты: issue_type, repair_type...

### **7. Дополнительные**
- `masters` - мастера
- `appointments` - записи на прием
- `knowledge_base` - база знаний (с vector embedding)

---

## 🔗 ВЗАИМОСВЯЗИ ВОРКЕРОВ

### **Входящий поток (IN):**
```
BAT IN Telegram/VK/WhatsApp/Avito/MAX/Phone/Form
  ↓
BAT_Tenant_Resolver (определение tenant)
  ↓
Universal Batcher (батчинг сообщений, ожидание 20 сек)
  ↓
BAT Client Resolver (поиск клиента)
  ↓ (если не найден)
BAT Client Creator (создание клиента)
  ↓
BAT Appeal Manager (управление обращениями)
  ↓
BAT AI Appeal Router (AI обработка)
  ↓
  ├── Tool - Определить тип обращения
  ├── Tool - Извлечь бренд и модель
  ├── Tool - Определить тип ремонта
  └── Tool - Определить чья запчасть
  ↓
Update Appeals (динамический SQL)
  ↓
BAT Operator Notifier (уведомление операторов)
  ↓
  ├── Telegram Message (оператору)
  └── BAT_FCM_Sender (push на Android)
```

### **Исходящий поток (OUT):**
```
BAT Message Router
  ↓ (switch по channel)
  ├── BAT OUT Telegram
  ├── BAT OUT VK
  ├── BAT OUT WhatsApp
  ├── BAT OUT Avito
  └── BAT OUT MAX
```

### **Android API:**
```
Android App
  ↓
API_Android_Auth (авторизация)
  ↓
API_Operator_Appeals_List (список обращений)
  ↓
API_Operator_Appeal_Detail (детали обращения)
  ├── Tool - Build Appeal Meta (формирование meta)
  └── Fetch Messages (история сообщений)
  ↓
API_Operator_Take_Appeal (взять в работу)
  ↓
API_Operator_Send_Response (отправить ответ)
  ↓
BAT Message Router → BAT OUT [Channel]
```

---

## ⚡ КЛЮЧЕВЫЕ ОСОБЕННОСТИ

### **1. Multi-Device Session Management**
- **Одна активная mobile сессия** на оператора
- При новом логине: старые сессии удаляются
- Авторизация через `operator_devices.session_token`

### **2. AI-обработка с принудительными Tools**
- **Последовательный вызов 4 AI агентов** для извлечения параметров
- **Postgres Chat Memory** для хранения истории диалогов (session_id = appeal_id)
- **Динамическое обновление appeals** по мере сбора информации

### **3. FCM Push-уведомления**
- **JWT-авторизация** в Google FCM
- **Высокий приоритет** для новых обращений
- **Data payload** с appeal_id для Deep Linking

### **4. Meta API**
- **Динамическая видимость** параметров по типу сделки
- **Форматирование** (date, duration, currency, boolean)
- **Группировка** (appeal, client, device, other)

### **5. Universal Batcher**
- **Батчинг сообщений** от клиента (ожидание 20 сек)
- **Объединение** нескольких сообщений в один запрос к AI

---

## 🎯 БИЗНЕС-СЦЕНАРИИ

### **Сценарий 1: Новый клиент пишет впервые**
1. Telegram → BAT IN Telegram
2. Tenant Resolver → находит tenant по bot_token
3. Client Resolver → НЕ находит клиента
4. Client Creator → создает нового клиента
5. Appeal Manager → создает новое обращение
6. AI Appeal Router → последовательно вызывает 4 Tools
7. AI Agent → формирует ответ с учетом извлеченных данных
8. Update Appeals → сохраняет извлеченные параметры
9. Operator Notifier → уведомляет операторов (Telegram + FCM)
10. Message Router → отправляет ответ клиенту

### **Сценарий 2: Оператор отвечает из Android**
1. Android App → API_Operator_Send_Response
2. Get Context → получает данные обращения и клиента
3. Prepare Message → формирует сообщение для канала
4. Message Router → определяет канал (telegram)
5. BAT OUT Telegram → отправляет в Telegram клиенту
6. Save Message History → сохраняет в БД

### **Сценарий 3: Клиент продолжает диалог**
1. Telegram → BAT IN Telegram
2. Client Resolver → находит существующего клиента
3. Appeal Manager → находит активное обращение
4. Save Message History → сохраняет сообщение
5. AI Appeal Router → обрабатывает с учетом ПОЛНОЙ истории диалога
6. Update Appeals → дополняет недостающие параметры
7. AI Agent → формирует ответ с учетом контекста

---

## 📐 АРХИТЕКТУРНЫЕ РЕШЕНИЯ

### **1. Multi-Tenant Architecture**
- **Полная изоляция данных** через tenant_id
- **Динамическая конфигурация** через tenant_configs (jsonb)
- **Масштабируемость**: один экземпляр для многих компаний

### **2. Event-Driven Workflow**
- **n8n workflows** как микросервисы
- **Execute Workflow** для связи между воркерами
- **Асинхронная обработка** через очереди

### **3. AI-First Approach**
- **4 специализированных AI агента** для извлечения данных
- **Контекстная память** через Postgres Chat Memory
- **Routing History** (jsonb) для отслеживания AI решений

### **4. Mobile-First API**
- **RESTful API** с session-based авторизацией
- **Meta API** для динамического UI
- **Push-уведомления** через FCM с JWT

### **5. Data Consistency**
- **ACID транзакции** в PostgreSQL
- **Foreign Keys** с CASCADE
- **Triggers** для автоматического обновления флагов

---

## 🚀 ТЕХНОЛОГИЧЕСКИЙ СТЕК

### **Backend:**
- **n8n** - workflow automation platform
- **PostgreSQL** - основная БД (с JSONB полями)
- **OpenAI API** - AI агенты (GPT-4, Whisper)
- **Node.js** - runtime для n8n code nodes

### **Integrations:**
- **Telegram Bot API**
- **VK API**
- **WhatsApp Business API**
- **Avito API**
- **Google FCM** (Firebase Cloud Messaging)

### **Authentication:**
- **JWT** (для FCM авторизации)
- **Session Tokens** (для Android API)

### **AI/ML:**
- **OpenAI GPT-4** - генерация ответов
- **OpenAI Whisper** - распознавание голоса
- **LangChain** - AI агенты и memory
- **Postgres Chat Memory** - хранение истории диалогов

---

## 📊 МЕТРИКИ И МОНИТОРИНГ

### **Отслеживаемые события:**
1. **operator_actions** - все действия операторов
   - `notify_appeal_ready` - уведомление отправлено
   - `take_appeal` - обращение взято в работу
   - `send_response` - ответ отправлен

2. **appeals.routing_history** - история AI обработки
   - `route`: full / partial / minimal
   - `action`: ask_type / ask_model / calculate_cost
   - `reasoning` - объяснение AI решения

3. **messages_history** - полная история коммуникаций
   - Клиент → Система
   - Система → Клиент
   - Оператор → Клиент

---

## 🔐 БЕЗОПАСНОСТЬ

### **1. Multi-Tenant Isolation**
- **Все запросы** проверяют tenant_id
- **Row-Level Security** через WHERE условия
- **Нет кросс-tenant запросов**

### **2. Session Management**
- **Уникальные session_token** (UUID v4)
- **Автоматическое удаление** старых mobile сессий
- **Timeout** для неактивных сессий

### **3. API Security**
- **x-session-token header** для авторизации
- **401 Unauthorized** при неверном токене
- **CORS headers** для web доступа

### **4. Data Privacy**
- **Encrypted FCM tokens**
- **No PII** в логах
- **GDPR-compliant** структура БД

---

## 📈 МАСШТАБИРУЕМОСТЬ

### **Горизонтальное масштабирование:**
- **n8n workflows** могут выполняться параллельно
- **PostgreSQL** поддерживает репликацию
- **FCM** поддерживает миллионы устройств

### **Вертикальное масштабирование:**
- **Database indexes** на tenant_id, appeal_id, client_id
- **JSONB indexes** на routing_history, tenant_configs
- **Connection pooling** для PostgreSQL

### **Оптимизации:**
- **Universal Batcher** - объединение сообщений (20 сек)
- **Caching** tenant_configs в памяти
- **Lazy loading** messages в Android app

---

Это **полная карта** работы проекта BatteryCRM! Каждое сообщение проходит через ~15-20 воркеров, с AI-обработкой, multi-tenant изоляцией и push-уведомлениями. Система разработана для масштабируемого SaaS решения с поддержкой множества каналов коммуникации и интеллектуальной автоматизацией через AI.
