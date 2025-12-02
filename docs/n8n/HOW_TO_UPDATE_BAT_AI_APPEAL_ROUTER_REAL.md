# Инструкция по обновлению AI воркера для управления стадиями

## 📋 Контекст

Воркер **BAT AI Appeal Router** (или аналогичный) нужно обновить для правильного управления стадиями обращения.

**⚠️ ВАЖНО:** Воркер "BAT AI Appeal Router" не найден в n8n API. Возможно он переименован. Проверьте:
- BAT AI Extractor (14 nodes)
- BAT Appeal Manager (14 nodes)

Эти инструкции применимы к любому воркеру который:
- Вызывает AI Tools для извлечения данных
- Использует AI Agent для генерации ответов
- Обновляет таблицу `appeals`

---

## 🎯 Цель изменений

Реализовать правильное управление стадиями:

1. **"Первичный контакт"** - приветствие (БЕЗ вызова Tools)
2. **"Сбор контекста"** - условный вызов ТОЛЬКО нужных Tools
3. **"Обработка заявки"** - все обязательные поля собраны

---

## 🔍 Как найти нужный воркер

```bash
# Список всех воркеров с AI
python -c "
import requests
r = requests.get('https://n8n.n8nsrv.ru/api/v1/workflows',
    headers={'X-N8N-API-KEY': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M'})
workflows = r.json()['data']
ai_workflows = [w for w in workflows if 'AI' in w['name'] or 'Tool' in w['name']]
for w in ai_workflows:
    print(f\"{w['id']}: {w['name']} ({len(w.get('nodes', []))} nodes)\")
"
```

Ищите воркер который:
- Имеет узлы "Call Tool - Type", "Call Tool - Model", "Call Tool - Repair", "Call Tool - Parts"
- Имеет узел "@n8n/n8n-nodes-langchain.agent" (AI Agent)
- Обновляет таблицу `appeals`

---

## ⚠️ ПЕРЕД НАЧАЛОМ

1. ✅ Выполните миграцию БД: `database/migrations/002_context_fields_management.sql`
2. ✅ Сделайте backup воркера через n8n UI: Settings → Export Workflow
3. ✅ Проверьте что таблица `context_fields_config` создана и заполнена данными

---

## 🏗️ Архитектура изменений

### Текущий flow (ПЛОХО):

```
Load Appeal → Format Context →
→ Call Tool-Type → Call Tool-Model → Call Tool-Repair → Call Tool-Parts →
→ AI Agent → Update Appeals
```

**Проблемы:**
- Tools вызываются ВСЕГДА
- Нет проверки stage
- Нет проверки какие поля уже заполнены

### Новый flow (ХОРОШО):

```
Load Appeal → Format Context → CHECK STAGE →

IF stage = "Первичный контакт":
  → AI Agent (greeting) → Update stage to "Сбор контекста"

IF stage = "Сбор контекста":
  → Load Context Config →
  → Check What's Missing →
  → Call ONLY needed Tools →
  → Update Context Status →
  → AI Agent (context collection) →
  → Update Appeals (with context_completion_status and stage)
```

---

## 📝 Изменения по шагам

### ШАГ 1: Добавить узел "Check Appeal Stage"

**Тип:** Code (JavaScript)
**Позиция:** После "Format Context for AI" или аналогичного

```javascript
const input = $input.first().json;
const appeal = input.appeal || {};

console.log('=== CHECK APPEAL STAGE ===');
console.log('Current stage:', appeal.stage);

// Определяем текущую стадию
const currentStage = appeal.stage || 'Первичный контакт';

// Проверяем количество сообщений в истории
const conversationHistory = input.conversation_history || '';
const messageCount = (conversationHistory.match(/\[.*?\]/g) || []).length;

// Определяем что делать
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

---

### ШАГ 2: Добавить узел "Load Context Fields Config"

**Тип:** Postgres
**Operation:** executeQuery

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

**Connection:** Подключить ТОЛЬКО если `should_collect_context = true`

---

### ШАГ 3: Добавить узел "Check What's Missing"

**Тип:** Code (JavaScript)

```javascript
const input = $('Check Appeal Stage').first().json;
const configArray = $input.first().json;
const config = Array.isArray(configArray) ? configArray : [configArray];

const appeal = input.appeal || {};

console.log('=== CHECK WHAT\'S MISSING ===');

// Проверяем какие поля уже заполнены
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

// Проверяем полноту
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

---

### ШАГ 4: Добавить Switch "Route Tools"

**Тип:** Switch

Создайте маршруты:

1. **Route "skip_tools"** (если Tools не нужны):
   - Условие: `{{ $json.tools_to_call.length === 0 }}`
   - Куда: Прямо на AI Agent (минуя Tools)

2. **Route "call_tools"** (если нужны Tools):
   - Условие: `{{ $json.tools_to_call.length > 0 }}`
   - Куда: На блок вызова Tools

---

### ШАГ 5: Условный вызов Tools

Вместо последовательного вызова всех Tools, сделайте:

```
Check What's Missing
    ↓
[Switch по tools_to_call]
    ↓
IF includes 'Tool-Type' → Call Tool-Type
IF includes 'Tool-Model' → Call Tool-Model
IF includes 'Tool-Repair' → Call Tool-Repair
IF includes 'Tool-Parts' → Call Tool-Parts
    ↓
[Merge Results]
```

**Узел "Conditional Call Tool-Type":**

**Тип:** IF (Switch)
```javascript
{{ $json.tools_to_call.includes('Tool-Type') }}
```

Если TRUE → Call Tool - Type
Если FALSE → Пропустить

Повторите для каждого Tool.

---

### ШАГ 6: Обновить "Collect Tool Results"

Добавьте обработку случая когда Tools не вызывались:

```javascript
const input = $('Check What's Missing').first().json;
const toolsCalled = input.tools_to_call && input.tools_to_call.length > 0;

console.log('=== COLLECT TOOL RESULTS ===');
console.log('Tools were called:', toolsCalled);

let extractedData = {};

if (toolsCalled) {
  // Собираем результаты из Tools (существующая логика)
  const typeResult = $('Call Tool - Type').first()?.json || {};
  const modelResult = $('Call Tool - Model').first()?.json || {};
  const repairResult = $('Call Tool - Repair').first()?.json || {};
  const partsResult = $('Call Tool - Parts').first()?.json || {};

  extractedData = {
    type: typeResult.type || input.appeal.type || null,
    model: modelResult.model || input.appeal.model_name || null,
    model_id: modelResult.model_id || input.appeal.model_id || null,
    brand_id: modelResult.brand_id || input.appeal.brand_id || null,
    repair_type: repairResult.repair_type || input.appeal.repair_type_name || null,
    repair_type_id: repairResult.repair_type_id || input.appeal.repair_type_id || null,
    parts_owner: partsResult.parts_owner || input.appeal.parts_owner || null
  };
} else {
  // Tools не вызывались, используем данные из appeal
  const appeal = input.appeal || {};
  extractedData = {
    type: appeal.type || null,
    model: appeal.model_name || null,
    model_id: appeal.model_id || null,
    brand_id: appeal.brand_id || null,
    repair_type: appeal.repair_type_name || null,
    repair_type_id: appeal.repair_type_id || null,
    parts_owner: appeal.parts_owner || null
  };
}

return {
  ...input,
  extracted_data: extractedData
};
```

---

### ШАГ 7: Добавить "Update Context Completion Status"

**Тип:** Code (JavaScript)
**Позиция:** После "Collect Tool Results", ПЕРЕД AI Agent

```javascript
const input = $input.first().json;
const extracted = input.extracted_data || {};
const requiredFields = input.required_fields || [];

console.log('=== UPDATE CONTEXT COMPLETION STATUS ===');

// Формируем fields_collected
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

---

### ШАГ 8: Обновить SQL в "Update Appeals"

Найдите узел который делает `UPDATE appeals` и добавьте:

```sql
UPDATE appeals SET
  type = COALESCE(NULLIF('{{ $json.extracted_data.type }}', 'null'), type),
  model_id = COALESCE(NULLIF('{{ $json.extracted_data.model_id }}', 'null')::uuid, model_id),
  brand_id = COALESCE(NULLIF('{{ $json.extracted_data.brand_id }}', 'null')::uuid, brand_id),
  repair_type_id = COALESCE(NULLIF('{{ $json.extracted_data.repair_type_id }}', 'null')::uuid, repair_type_id),
  parts_owner = COALESCE(NULLIF('{{ $json.extracted_data.parts_owner }}', 'null'), parts_owner),

  -- НОВОЕ: Обновляем context_completion_status
  context_completion_status = '{{ $json.context_completion_status | stringify }}'::jsonb,

  -- НОВОЕ: Обновляем stage
  stage = CASE
    WHEN '{{ $json.is_context_complete }}' = 'true' THEN 'Обработка заявки'
    WHEN '{{ $json.current_stage }}' = 'Первичный контакт' THEN 'Сбор контекста'
    ELSE stage
  END,

  routing_history = COALESCE(routing_history, '[]'::jsonb) || '{{ $json.routing_entry | stringify }}'::jsonb,
  updated_at = NOW()
WHERE id = '{{ $json.appeal_id }}'::uuid
RETURNING *;
```

---

### ШАГ 9: Обновить промпт AI Agent

Сделайте динамический промпт в зависимости от стадии:

```javascript
// В узле перед AI Agent
const input = $input.first().json;

let systemPrompt = '';

if (input.is_first_contact) {
  systemPrompt = `Ты — дружелюбный ассистент сервисного центра по ремонту iPhone.

Это первый контакт с клиентом. Твоя задача:
1. Поприветствовать клиента
2. Кратко представиться
3. Спросить, чем можешь помочь

Будь кратким, естественным и дружелюбным.
НЕ используй markdown.

Сообщение клиента: "${input.text}"`;

} else if (input.should_collect_context) {
  const missingList = input.missing_fields.join(', ');
  const collectedSummary = Object.entries(input.extracted_data || {})
    .filter(([k, v]) => v !== null && v !== '')
    .map(([k, v]) => `- ${k}: ${v}`)
    .join('\n');

  systemPrompt = `Ты — ассистент сервисного центра. Сейчас ты собираешь информацию об обращении.

Уже собрано:
${collectedSummary || 'Ничего'}

Не хватает: ${missingList || 'всё собрано'}

Последнее сообщение клиента: "${input.text}"

Твоя задача:
1. Естественно узнать недостающую информацию
2. Задать вопрос о СЛЕДУЮЩЕМ недостающем поле (не обо всех сразу)
3. Быть кратким и по делу

НЕ используй markdown.`;

} else {
  systemPrompt = `Ты — ассистент сервисного центра. Все необходимые данные собраны.

Данные обращения:
${JSON.stringify(input.extracted_data, null, 2)}

Сообщение клиента: "${input.text}"

Твоя задача: Дай полезный ответ клиенту на основе собранных данных.

НЕ используй markdown.`;
}

return {
  ...input,
  systemPrompt: systemPrompt
};
```

Затем в настройках AI Agent используйте:
```
System Message: {{ $json.systemPrompt }}
```

---

## 🎯 Проверка результата

### Тест 1: Приветствие

```
Input:
- stage: "Первичный контакт"
- text: "Здравствуйте"
- conversation_history: "Первое сообщение в диалоге"

Expected:
1. Check Stage → is_first_contact = true
2. НЕ вызывать Load Context Config
3. НЕ вызывать Tools
4. AI Agent → "Здравствуйте! Я ассистент сервисного центра. Чем могу помочь?"
5. Update stage = "Сбор контекста"
```

### Тест 2: Постепенный сбор

```
Input:
- stage: "Сбор контекста"
- text: "Хочу починить телефон"
- appeal: { type: null, model_id: null }

Expected:
1. Load Context Config
2. Check What's Missing → tools_to_call: ['Tool-Type', 'Tool-Model', 'Tool-Repair']
3. Вызвать эти 3 Tools
4. Collect Results → type='ремонт', model_id=null
5. Update Context Status → is_complete = false, completion_percentage = 33%
6. AI Agent → "Понял! Какая модель вашего телефона?"
7. Update appeals с context_completion_status
```

### Тест 3: Всё сразу

```
Input:
- stage: "Первичный контакт"
- text: "Нужно заменить батарею на iPhone 14 Pro"

Expected:
1. is_first_contact = true
2. Load Context Config
3. Check Missing → tools_to_call: ['Tool-Type', 'Tool-Model', 'Tool-Repair']
4. Вызвать Tools → извлечь все данные
5. Update Context Status → is_complete = true (осталось только parts_owner - необязательное)
6. AI Agent → "Здравствуйте! Отлично, замена батареи iPhone 14 Pro. Запчасть будет наша или ваша?"
7. Update stage = "Обработка заявки"
```

---

## ⚠️ Важные замечания

1. **Триггер БД**: Миграция `002_context_fields_management.sql` создаёт триггер который автоматически обновляет `context_completion_status`. Убедитесь что она выполнена.

2. **Обратная совместимость**: Если `stage = NULL`, считаем что это "Первичный контакт".

3. **Параллельные вызовы**: Желательно изменить connections чтобы Tools вызывались параллельно (если n8n это поддерживает).

4. **Ошибки Tools**: Добавьте обработку ошибок - если Tool упал, не останавливайте весь flow.

5. **Тестирование**: Обязательно протестируйте ВСЕ 3 сценария перед деплоем.

---

## 📋 Чеклист

- [ ] Миграция 002 выполнена в БД
- [ ] Найден правильный воркер в n8n
- [ ] Сделан backup воркера
- [ ] Добавлен узел "Check Appeal Stage"
- [ ] Добавлен узел "Load Context Fields Config"
- [ ] Добавлен узел "Check What's Missing"
- [ ] Добавлен Switch "Route Tools"
- [ ] Изменена логика вызова Tools (условно)
- [ ] Обновлён узел "Collect Tool Results"
- [ ] Добавлен узел "Update Context Completion Status"
- [ ] Обновлён SQL в "Update Appeals"
- [ ] Обновлён промпт AI Agent (динамический)
- [ ] Протестирован сценарий 1 (приветствие)
- [ ] Протестирован сценарий 2 (постепенный сбор)
- [ ] Протестирован сценарий 3 (всё сразу)
- [ ] Обновлена документация

---

## 🚀 Как применить

1. Выполните миграцию БД через Supabase Dashboard
2. Найдите нужный воркер в n8n (см. раздел "Как найти нужный воркер")
3. Откройте воркер в n8n UI
4. Сделайте backup (Export)
5. Внесите изменения по этой инструкции
6. Activate воркер
7. Протестируйте все сценарии
8. Обновите документацию

---

**Автор:** Claude Code
**Дата:** 2025-11-22
**Версия:** 1.0

**Следующий шаг:** Найдите воркер и примените эти изменения вручную в n8n UI.
