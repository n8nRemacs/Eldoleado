# 🚀 Быстрый старт: Обновление BAT AI Appeal Router в n8n UI

## ✅ Что уже сделано

- ✅ Миграция БД выполнена успешно
  - Таблица `context_fields_config` создана (4 записи)
  - Поле `context_completion_status` добавлено в `appeals`
  - Обновлено 111 существующих appeals
  - Триггер автообновления настроен

- ✅ Исправлен connection string в `.env`:
  ```
  postgresql://supabase_admin:Mi31415926pS@185.221.214.83:6544/postgres
  ```

- ✅ Создана подробная инструкция: `HOW_TO_UPDATE_BAT_AI_APPEAL_ROUTER_REAL.md`

- ✅ Актуальный воркер сохранен: `workflows/BAT AI Appeal Router_yeSpnx9mPFRHQllf.json`

---

## 📋 Что нужно сделать (В n8n UI)

### Шаг 1: Откройте workflow

1. Перейдите: https://n8n.n8nsrv.ru
2. Найдите workflow: **BAT AI Appeal Router**
3. Откройте для редактирования
4. **Сделайте backup**: Settings → Duplicate workflow → Save as "BAT AI Appeal Router BACKUP 2025-11-22"

---

### Шаг 2: Добавьте 4 новых узла

#### Узел 1: "Check Appeal Stage"

**Позиция:** После узла "Prepare Session1"

**Тип:** Code (JavaScript)

**Code:**
```javascript
const input = $input.first().json;
const appeal = input.appeal || {};

console.log('=== CHECK APPEAL STAGE ===');
console.log('Current stage:', appeal.stage);

const currentStage = appeal.stage || 'Первичный контакт';
const conversationHistory = input.conversation_history || '';
const messageCount = (conversationHistory.match(/\[.*?\]/g) || []).length;

const isFirstContact = currentStage === 'Первичный контакт' && messageCount <= 1;
const shouldCollectContext = currentStage === 'Сбор контекста' ||
                              currentStage === 'Первичный контакт' ||
                              currentStage === null;

console.log('Is first contact:', isFirstContact);
console.log('Should collect context:', shouldCollectContext);

return {
  ...input,
  current_stage: currentStage,
  is_first_contact: isFirstContact,
  should_collect_context: shouldCollectContext
};
```

**Connection:**
- Вход: Prepare Session1
- Выход: Load Context Fields Config (новый узел)

---

#### Узел 2: "Load Context Fields Config"

**Тип:** Postgres

**Operation:** Execute Query

**Query:**
```sql
SELECT
  field_name,
  field_label,
  is_required,
  collection_order,
  tool_name,
  prompt_template
FROM context_fields_config
WHERE tenant_id = '{{ $json.tenant_id }}'::uuid
  AND is_active = true
  AND (deal_type_id IS NULL OR deal_type_id = '{{ $json.appeal.deal_type_id }}'::uuid)
ORDER BY collection_order ASC;
```

**Credentials:** Postgres account (существующие)

**Connection:**
- Вход: Check Appeal Stage
- Выход: Check What's Missing (новый узел)

---

#### Узел 3: "Check What's Missing"

**Тип:** Code (JavaScript)

**Code:**
```javascript
const input = $('Check Appeal Stage').first().json;
const configArray = $input.first().json;
const config = Array.isArray(configArray) ? configArray : [configArray];

const appeal = input.appeal || {};

console.log('=== CHECK WHAT\'S MISSING ===');

const missing = [];
const toolsToCall = [];
const fieldsStatus = {};

config.forEach(field => {
  const fieldValue = appeal[field.field_name];
  const isCollected = fieldValue !== null &&
                      fieldValue !== undefined &&
                      fieldValue !== '' &&
                      fieldValue !== 'null';

  fieldsStatus[field.field_name] = {
    label: field.field_label,
    is_required: field.is_required,
    is_collected: isCollected,
    tool_name: field.tool_name,
    current_value: fieldValue
  };

  if (!isCollected && field.tool_name) {
    missing.push(field.field_label);
    toolsToCall.push(field.tool_name);
  }
});

const requiredFields = config.filter(f => f.is_required);
const collectedRequired = requiredFields.filter(f =>
  appeal[f.field_name] !== null &&
  appeal[f.field_name] !== undefined &&
  appeal[f.field_name] !== ''
);

const isComplete = collectedRequired.length >= requiredFields.length && requiredFields.length > 0;

console.log('Missing fields:', missing);
console.log('Tools to call:', toolsToCall);
console.log('Is complete:', isComplete);

return {
  ...input,
  required_fields: config,
  missing_fields: missing,
  tools_to_call: toolsToCall,
  is_context_complete: isComplete,
  fields_status: fieldsStatus
};
```

**Connection:**
- Вход: Load Context Fields Config
- Выход: Attach Tools Base (существующий узел)

---

#### Узел 4: "Update Context Completion Status"

**Позиция:** После "Collect Tool Results", ПЕРЕД "AI Agent1"

**Тип:** Code (JavaScript)

**Code:**
```javascript
const input = $input.first().json;
const extracted = input.extracted_data || {};
const requiredFields = input.required_fields || [];

console.log('=== UPDATE CONTEXT COMPLETION STATUS ===');

const fieldsCollected = {};
let collectedCount = 0;
let requiredCount = 0;

requiredFields.forEach(field => {
  const value = extracted[field.field_name];
  const isCollected = value !== null && value !== undefined && value !== '' && value !== 'null';

  if (field.is_required) {
    requiredCount++;
  }

  if (isCollected) {
    fieldsCollected[field.field_name] = {
      value: String(value),
      collected_at: new Date().toISOString(),
      is_valid: true
    };

    if (field.is_required) {
      collectedCount++;
    }
  } else {
    fieldsCollected[field.field_name] = null;
  }
});

const isComplete = collectedCount >= requiredCount && requiredCount > 0;
const completionPercentage = requiredCount > 0
  ? Math.round((collectedCount / requiredCount) * 100)
  : 0;

const completionStatus = {
  fields_collected: fieldsCollected,
  required_fields_count: requiredCount,
  collected_required_count: collectedCount,
  is_complete: isComplete,
  completion_percentage: completionPercentage,
  updated_at: new Date().toISOString()
};

console.log('Completion status:', JSON.stringify(completionStatus, null, 2));

return {
  ...input,
  context_completion_status: completionStatus,
  is_context_complete: isComplete
};
```

**Connection:**
- Вход: Collect Tool Results
- Выход: AI Agent1

---

### Шаг 3: Обновите "Route by Completeness"

Найдите узел **"Route by Completeness"** и добавьте в конец функции (перед `return`):

```javascript
// Добавляем context_completion_status если он есть
if (input.context_completion_status) {
  const statusJson = JSON.stringify(input.context_completion_status).replace(/'/g, "''");
  setFields.push(`context_completion_status = '${statusJson}'::jsonb`);
}

// Обновляем stage если контекст полностью собран
if (input.is_context_complete === true) {
  setFields.push(`stage = 'Обработка заявки'`);
} else if (input.current_stage === 'Первичный контакт') {
  setFields.push(`stage = 'Сбор контекста'`);
}
```

---

### Шаг 4: Сохраните и активируйте

1. Нажмите **Save** (Ctrl+S)
2. Проверьте что нет ошибок в connections
3. Активируйте workflow (переключатель Active в правом верхнем углу)

---

## 🧪 Тестирование

После активации протестируйте 3 сценария:

### Тест 1: Приветствие
```
Отправьте в Telegram bot: "Здравствуйте"

Ожидаемый ответ:
"Здравствуйте! Я ассистент сервисного центра. Чем могу помочь?"

Stage должен измениться: "Первичный контакт" → "Сбор контекста"
```

### Тест 2: Постепенный сбор
```
1. "Хочу починить телефон"
   → "Понял! Какая модель вашего телефона?"

2. "iPhone 14 Pro"
   → "Отлично! Что именно нужно починить?"

3. "Заменить батарею"
   → Stage меняется на "Обработка заявки"
```

### Тест 3: Всё сразу
```
"Нужно заменить батарею на iPhone 14 Pro"

Ожидаемый ответ:
"Здравствуйте! Отлично, замена батареи iPhone 14 Pro. Запчасть будет наша или ваша?"

Stage сразу: "Обработка заявки"
```

---

## 📊 Проверка в БД

После тестов проверьте что данные сохраняются:

```sql
-- Последние обращения с context_completion_status
SELECT
  id,
  stage,
  context_completion_status->>'completion_percentage' as completion,
  context_completion_status->>'is_complete' as is_complete,
  created_at
FROM appeals
ORDER BY created_at DESC
LIMIT 5;
```

---

## ❗ Если что-то пошло не так

1. **Деактивируйте workflow** (переключатель Active)
2. **Восстановите из backup**: Откройте "BAT AI Appeal Router BACKUP 2025-11-22"
3. **Напишите мне** - опишу где ошибка

---

## 📚 Дополнительно

- **Полная инструкция**: `HOW_TO_UPDATE_BAT_AI_APPEAL_ROUTER_REAL.md`
- **Миграция БД**: `database/migrations/002_context_fields_management.sql` (уже выполнена)
- **Оригинальный воркер**: `workflows/BAT AI Appeal Router_yeSpnx9mPFRHQllf.json`

---

**Автор:** Claude Code
**Дата:** 2025-11-22
**Время выполнения:** ~30-40 минут

**Удачи! 🚀**
