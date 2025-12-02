# Деплой AI Multi-Entity Extractor - Пошаговая инструкция

## Обзор

Этот документ содержит пошаговые инструкции по развёртыванию **AI Multi-Entity Extractor** workflow в production.

## Предварительные требования

### 1. n8n сервер
- ✅ n8n установлен и работает: https://n8n.n8nsrv.ru
- ✅ PostgreSQL настроен (185.221.214.83:6544)
- ✅ Multi-Appeal API развёрнут

### 2. Anthropic API
- ⚠️ **Нужно получить API ключ** от Anthropic
- Зарегистрироваться: https://console.anthropic.com/
- Создать API key
- Положить на баланс минимум $5

### 3. Файлы для деплоя
- `workflows/BAT AI Multi-Entity Extractor.json`
- `prompts/multi_entity_extractor_prompt.md`
- `AI_MULTI_ENTITY_EXTRACTOR_DOC.md` (документация)

---

## Шаг 1: Настройка Anthropic API Credentials

### 1.1 Войти в n8n UI
```
https://n8n.n8nsrv.ru
```

### 1.2 Создать новый Credential

1. Перейти: **Settings** → **Credentials** → **Add Credential**
2. Выбрать тип: **Header Auth**
3. Заполнить поля:

**Name:**
```
Anthropic API Key
```

**Credential Data:**

| Поле | Значение |
|:-----|:---------|
| Name | `x-api-key` |
| Value | `sk-ant-api03-...` (ваш ключ) |

4. Нажать **Create**

### 1.3 Добавить второй header (Anthropic-Version)

К сожалению, n8n Header Auth поддерживает только один header. Нужно использовать HTTP Request node с настройкой headers вручную.

**Альтернатива:** В workflow уже настроен HTTP Request node с двумя headers:
- `x-api-key`: через Header Auth credential
- `anthropic-version`: `2023-06-01` (hardcoded в node)

---

## Шаг 2: Импорт Workflow в n8n

### 2.1 Скопировать файл на сервер

```bash
# Локально
cd c:\Users\User\Dropbox\BattCRMApp

# Скопировать workflow
scp "workflows/BAT AI Multi-Entity Extractor.json" user@n8nsrv.ru:/tmp/

# Или использовать GUI (WinSCP, FileZilla)
```

### 2.2 Импорт через n8n UI

1. Открыть n8n: https://n8n.n8nsrv.ru
2. В левом меню: **Workflows** → **Import**
3. Выбрать файл: `/tmp/BAT AI Multi-Entity Extractor.json`
4. Нажать **Import**

### 2.3 Проверка импорта

Убедитесь, что workflow импортировался с такими nodes:
- Execute Workflow Trigger
- Load Brands
- Load Models
- Load Repair Categories
- Merge Reference Data
- Build AI Prompt
- Call Claude AI
- Parse AI Response
- Map To DB IDs

---

## Шаг 3: Настройка Credentials в Workflow

### 3.1 Открыть workflow
Перейти в только что импортированный workflow.

### 3.2 Настроить PostgreSQL credential

Для каждого node (Load Brands, Load Models, Load Repair Categories):

1. Кликнуть на node
2. В секции **Credentials** выбрать существующий **Postgres account**
3. Проверить параметры подключения:
   - Host: `185.221.214.83`
   - Port: `6544`
   - Database: `postgres`
   - User: `supabase_admin`
   - Password: `Mi31415926pS`

### 3.3 Настроить Anthropic API credential

Для node **Call Claude AI**:

1. Кликнуть на node
2. В секции **Credentials** выбрать созданный **Anthropic API Key**
3. Проверить параметры:
   - Method: `POST`
   - URL: `https://api.anthropic.com/v1/messages`
   - Headers:
     - `x-api-key`: через credential
     - `anthropic-version`: `2023-06-01`

### 3.4 Сохранить workflow

Нажать **Save** (Ctrl+S).

---

## Шаг 4: Тестирование Workflow

### 4.1 Подготовить тестовые данные

Скопировать этот JSON:

```json
{
  "tenant_id": "a0000000-0000-0000-0000-000000000001",
  "appeal_id": "00000000-0000-0000-0000-000000000001",
  "current_message": "У меня два телефона: iPhone 14 - батарея и разбит экран, и Samsung S23 - не заряжается",
  "message_history": [
    {
      "timestamp": "2025-11-23T10:00:00Z",
      "is_client": true,
      "text": "Здравствуйте, хочу отремонтировать телефоны"
    }
  ]
}
```

### 4.2 Запустить тест

1. В workflow кликнуть на **Execute Workflow Trigger** node
2. Вставить тестовый JSON в поле **Input Data**
3. Нажать **Test Workflow**

### 4.3 Проверить результат

Ожидаемый результат в последнем node (**Map To DB IDs**):

```json
{
  "tenant_id": "a0000000-0000-0000-0000-000000000001",
  "appeal_id": "00000000-0000-0000-0000-000000000001",
  "is_multi_device": true,
  "device_count": 2,
  "devices": [
    {
      "brand": "Apple",
      "brand_id": "...",
      "model": "iPhone 14",
      "model_id": "...",
      "repairs": [
        {"category_id": "...", "category_name": "Аккумулятор", "symptoms": "батарея"},
        {"category_id": "...", "category_name": "Дисплей", "symptoms": "разбит экран"}
      ]
    },
    {
      "brand": "Samsung",
      "brand_id": "...",
      "model": "Galaxy S23",
      "model_id": "...",
      "repairs": [
        {"category_id": "...", "category_name": "Порты", "symptoms": "не заряжается"}
      ]
    }
  ]
}
```

### 4.4 Возможные ошибки

**Ошибка: "Failed to connect to database"**
- Проверить PostgreSQL credentials
- Проверить доступ к серверу: `telnet 185.221.214.83 6544`

**Ошибка: "Anthropic API error: 401 Unauthorized"**
- Проверить API key в credential
- Проверить баланс на https://console.anthropic.com/

**Ошибка: "Failed to parse AI response as JSON"**
- Проверить промпт в node **Build AI Prompt**
- Посмотреть сырой ответ Claude AI в node **Call Claude AI**

---

## Шаг 5: Получить Workflow ID

После успешного теста:

1. Скопировать **Workflow ID** из URL:
   ```
   https://n8n.n8nsrv.ru/workflow/XXXXXXXXXXXXX
                                 ^^^^^^^^^^^^^
                                 Workflow ID
   ```

2. Сохранить ID для следующего шага.

Пример:
```
Workflow ID: abcd1234efgh5678
```

---

## Шаг 6: Интеграция в BAT AI Appeal Router

### 6.1 Открыть Appeal Router workflow

1. Перейти: **Workflows** → **BAT AI Appeal Router**
2. Найти место, где вызываются AI Tools (Device Extractor, Issue Extractor)

### 6.2 Добавить новый node

1. Кликнуть **+** (добавить node)
2. Выбрать: **Action in n8n** → **Execute Workflow**
3. Настроить:

**Parameters:**
- Workflow ID: `abcd1234efgh5678` (из Шага 5)
- Source: `Execute Workflow Trigger`

**Node Name:**
```
Call Multi-Entity Extractor
```

### 6.3 Подключить входные данные

Входные данные для Multi-Entity Extractor берутся из Appeal Router:

```javascript
// В node "Prepare Multi-Entity Input" (Code node)
return {
  tenant_id: $('Load Appeal & History').item.json.tenant_id,
  appeal_id: $('Load Appeal & History').item.json.id,
  current_message: $('Load Appeal & History').item.json.messages.slice(-1)[0].text,
  message_history: $('Load Appeal & History').item.json.messages
};
```

### 6.4 Обработать результат

После вызова Multi-Entity Extractor, Appeal Router должен:

1. **Проверить is_multi_device**:
```javascript
const result = $('Call Multi-Entity Extractor').item.json;

if (result.is_multi_device) {
  // Мультизаявка: вызвать Multi-Appeal API
  return {
    use_multi_appeal_api: true,
    devices: result.devices
  };
} else {
  // Простая заявка: использовать старую логику
  return {
    use_multi_appeal_api: false,
    brand: result.devices[0].brand,
    model: result.devices[0].model,
    ...
  };
}
```

2. **Вызвать Multi-Appeal API**:
```javascript
// Node: Call Multi-Appeal API (HTTP Request)
// Method: PUT
// URL: https://n8n.n8nsrv.ru/webhook/api/operator/appeals/{{ $json.appeal_id }}/devices
// Body:
{
  "devices": $json.devices
}
```

### 6.5 Сохранить Appeal Router

Нажать **Save** (Ctrl+S).

---

## Шаг 7: E2E Тестирование

### 7.1 Создать тестовую заявку

Отправить сообщение в WhatsApp/Telegram от клиента:

```
У меня два телефона: iPhone 14 - батарея и разбит экран, и Samsung S23 - не заряжается
```

### 7.2 Проверить логи n8n

1. Перейти: **Executions** (в левом меню)
2. Найти выполнение **BAT AI Appeal Router**
3. Проверить, что вызвался **BAT AI Multi-Entity Extractor**
4. Проверить результат

### 7.3 Проверить БД

```sql
-- Проверить заявку
SELECT id, is_multi_appeal FROM appeals WHERE id = 'appeal-uuid';

-- Проверить устройства
SELECT * FROM appeal_devices WHERE appeal_id = 'appeal-uuid';

-- Проверить неисправности
SELECT ar.*, rc.name as category_name
FROM appeal_repairs ar
JOIN repair_categories rc ON rc.id = ar.repair_category_id
WHERE ar.appeal_device_id IN (
  SELECT id FROM appeal_devices WHERE appeal_id = 'appeal-uuid'
);
```

### 7.4 Ожидаемый результат

**appeals:**
- `is_multi_appeal = true`

**appeal_devices:**
- 2 записи (iPhone 14, Samsung S23)

**appeal_repairs:**
- 3 записи:
  - iPhone 14 → Аккумулятор
  - iPhone 14 → Дисплей
  - Samsung S23 → Порты

---

## Шаг 8: Мониторинг и логирование

### 8.1 Настроить алерты в n8n

1. Перейти: **Settings** → **Community Nodes**
2. Установить: **n8n-nodes-discord** (для алертов)
3. Настроить webhook для ошибок

### 8.2 Создать Dashboard

Рекомендуемые метрики:
- Количество вызовов Multi-Entity Extractor (за час/день)
- Процент мультизаявок (is_multi_device = true)
- Среднее время выполнения workflow
- Частота ошибок
- Стоимость API вызовов (Anthropic)

### 8.3 Логирование в PostgreSQL

Создать таблицу для логов:

```sql
CREATE TABLE ai_extraction_logs (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id uuid NOT NULL,
  appeal_id uuid NOT NULL,
  workflow_execution_id varchar,
  input_message text,
  output_devices jsonb,
  device_count int,
  is_multi_device boolean,
  execution_time_ms int,
  anthropic_cost numeric(10, 6),
  error text,
  created_at timestamp DEFAULT now()
);

CREATE INDEX idx_ai_extraction_logs_appeal ON ai_extraction_logs(appeal_id);
CREATE INDEX idx_ai_extraction_logs_tenant ON ai_extraction_logs(tenant_id);
CREATE INDEX idx_ai_extraction_logs_created ON ai_extraction_logs(created_at);
```

---

## Шаг 9: Оптимизация

### 9.1 Кеширование справочников

Вместо загрузки brands/models/categories на каждый запрос, можно кешировать:

```javascript
// В node "Load Brands" добавить кеш
const CACHE_TTL = 3600000; // 1 час
const cacheKey = 'brands_cache';

// Проверить кеш
const cached = $getWorkflowStaticData('global')[cacheKey];
if (cached && (Date.now() - cached.timestamp < CACHE_TTL)) {
  return cached.data;
}

// Загрузить из БД
const brands = await db.query('SELECT ...');

// Сохранить в кеш
$getWorkflowStaticData('global')[cacheKey] = {
  data: brands,
  timestamp: Date.now()
};

return brands;
```

### 9.2 Batch обработка

Если нужно обработать несколько заявок одновременно:

```javascript
// В Appeal Router
const appeals = $input.all(); // Получить все заявки

// Вызвать Multi-Entity Extractor для каждой
const results = [];
for (const appeal of appeals) {
  const result = await $executeWorkflow('Multi-Entity Extractor', appeal);
  results.push(result);
}

return results;
```

### 9.3 Fallback на старую логику

Если Multi-Entity Extractor недоступен, использовать старую логику:

```javascript
try {
  const result = await $executeWorkflow('Multi-Entity Extractor', input);
  return result;
} catch (error) {
  console.error('Multi-Entity Extractor failed, fallback to old logic:', error);

  // Старая логика (одно устройство, одна неисправность)
  const device = await extractDevice(input);
  const issue = await extractIssue(input);

  return {
    is_multi_device: false,
    devices: [{
      ...device,
      repairs: [issue]
    }]
  };
}
```

---

## Шаг 10: Rollback план

Если что-то пойдёт не так:

### 10.1 Деактивировать workflow

1. Открыть **BAT AI Appeal Router**
2. Найти node **Call Multi-Entity Extractor**
3. Отключить node (кликнуть правой кнопкой → **Disable**)
4. Сохранить workflow

### 10.2 Откатить изменения в БД

```sql
-- Удалить записи, созданные через Multi-Entity Extractor
DELETE FROM appeal_repairs WHERE appeal_device_id IN (
  SELECT id FROM appeal_devices WHERE created_at > '2025-11-23T18:00:00'
);

DELETE FROM appeal_devices WHERE created_at > '2025-11-23T18:00:00';

-- Сбросить флаг is_multi_appeal
UPDATE appeals
SET is_multi_appeal = false
WHERE created_at > '2025-11-23T18:00:00';
```

### 10.3 Восстановить старую логику

Вернуться к старым AI Tools:
- BAT AI Device Extractor
- BAT AI Issue Extractor

---

## Checklist перед деплоем

- [ ] Anthropic API key получен и добавлен в n8n credentials
- [ ] Workflow импортирован в n8n
- [ ] PostgreSQL credentials настроены
- [ ] Workflow протестирован на тестовых данных
- [ ] Workflow ID скопирован
- [ ] Appeal Router обновлён с вызовом Multi-Entity Extractor
- [ ] E2E тест пройден (отправка сообщения от клиента)
- [ ] БД проверена (appeal_devices, appeal_repairs созданы)
- [ ] Мониторинг настроен
- [ ] Логирование настроено
- [ ] Rollback план подготовлен

---

## Контакты и ссылки

**Endpoints:**
- n8n UI: https://n8n.n8nsrv.ru
- PostgreSQL: 185.221.214.83:6544
- Anthropic Console: https://console.anthropic.com/

**Документация:**
- AI_MULTI_ENTITY_EXTRACTOR_DOC.md
- MULTI_APPEAL_API_DOCUMENTATION.md
- Start.md

**Автор:** Claude Code + User
**Дата:** 2025-11-23
**Версия:** 1.0
