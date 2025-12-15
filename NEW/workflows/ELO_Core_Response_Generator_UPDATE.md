# ELO_Core_Response_Generator — Обновление на SQL

## Что изменилось

| Было (хардкод) | Стало (SQL) |
|---------------|-------------|
| 8 промптов в коде | Таблица elo_v_prompts |
| Шаблоны в JS | Шаблоны в БД |

## Таблица: elo_v_prompts

```sql
id INTEGER
vertical_id INTEGER
funnel_stage_id INTEGER
prompt_type VARCHAR (extraction/response)
goal_type VARCHAR (ask_slot/confirm_data/present_offer/...)
slot_name VARCHAR (device/symptom/date/time/phone)
name VARCHAR
system_prompt TEXT
user_prompt_template TEXT
```

## Текущие промпты в БД

| goal_type | slot_name | name |
|-----------|-----------|------|
| ask_slot | device | Ask Device |
| ask_slot | symptom | Ask Symptom |
| confirm_data | - | Confirm Data |
| present_offer | - | Present Offer |
| ask_slot | date | Ask Date |
| ask_slot | time | Ask Time |
| ask_slot | phone | Ask Phone |
| ask_final_confirmation | - | Final Confirmation |

## Инструкция по обновлению в n8n

### 1. Добавить PostgreSQL ноду после "Determine Goal"

**Name:** Load Prompt

**SQL Query:**
```sql
SELECT
    system_prompt,
    user_prompt_template
FROM elo_v_prompts
WHERE vertical_id = $1
  AND prompt_type = 'response'
  AND goal_type = $2
  AND (slot_name = $3 OR (slot_name IS NULL AND $3 IS NULL))
  AND is_active = true
LIMIT 1;
```

**Query Replacement:**
```
={{ [$json.context.vertical_id, $json.response_goal.type, $json.response_goal.slot || null] }}
```

### 2. Обновить ноду "Build Prompt"

Заменить хардкод PROMPTS на данные из БД:

```javascript
const context = $json.context;
const goal = $json.response_goal;
const focusLine = $json.focus_line;
const promptData = $('Load Prompt').first().json;

// Build template variables
const vars = {
  device: focusLine?.slots?.device
    ? `${focusLine.slots.device.brand} ${focusLine.slots.device.model}`
    : 'устройство',
  symptom: focusLine?.slots?.symptom?.text || 'проблема',
  diagnosis: focusLine?.slots?.diagnosis?.text || '',
  repair: focusLine?.slots?.repair_type?.text || '',
  price: focusLine?.slots?.price?.amount || '???',
  date: context.booking?.date || '',
  time: context.booking?.time || ''
};

// Replace {{var}} placeholders
let userPrompt = promptData.user_prompt_template || '';
for (const [key, value] of Object.entries(vars)) {
  userPrompt = userPrompt.replace(new RegExp(`{{${key}}}`, 'g'), value);
}

// Add trigger messages
if (goal.trigger_messages?.length > 0) {
  userPrompt += '\n\nТакже добавь: ' + goal.trigger_messages.join('; ');
}

return {
  system_prompt: promptData.system_prompt,
  user_prompt: userPrompt,
  context: context,
  goal: goal,
  focus_line: focusLine
};
```

### 3. Обновить HTTP Request "Call OpenRouter"

В body использовать:
```json
{
  "model": "qwen/qwen3-30b-a3b:free",
  "messages": [
    {"role": "system", "content": "{{ $json.system_prompt }}"},
    {"role": "user", "content": "{{ $json.user_prompt }}"}
  ],
  "max_tokens": 200
}
```

## Структура workflow после обновления

```
Webhook
   │
   ▼
Determine Goal
   │
   ▼
PostgreSQL Load Prompt  ← NEW
   │
   ▼
Build Prompt  ← UPDATED (use DB data)
   │
   ▼
Call OpenRouter
   │
   ▼
Parse Response
   │
   ▼
Respond
```

## Добавление нового промпта

```sql
INSERT INTO elo_v_prompts
(vertical_id, funnel_stage_id, prompt_type, goal_type, slot_name, name, system_prompt, user_prompt_template)
VALUES (
    1,
    5,  -- appointment stage
    'response',
    'ask_slot',
    'name',
    'Ask Name',
    'Ты - вежливый оператор сервисного центра.',
    'Спроси имя клиента для записи.'
);
```

## Тест

```bash
curl -X POST https://n8n.n8nsrv.ru/webhook/response \
  -H "Content-Type: application/json" \
  -d '{
    "context": {
      "vertical_id": 1,
      "current_stage": "device",
      "focus_line_id": "line_0",
      "lines": [{"id": "line_0", "slots": {}}]
    },
    "triggers_fired": []
  }'
```

Ожидаемый результат: Ответ с вопросом "Какое устройство нужно отремонтировать?"
