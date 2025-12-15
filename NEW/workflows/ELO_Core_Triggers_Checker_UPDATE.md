# ELO_Core_Triggers_Checker — Обновление на SQL

## Что изменилось

| Было (хардкод) | Стало (SQL) |
|---------------|-------------|
| 3 триггера в коде | Таблица elo_v_triggers |
| Изменения = код | Изменения = БД |

## Новая таблица: elo_v_triggers

```sql
id UUID
code VARCHAR(100) UNIQUE
name VARCHAR(255)
vertical_id INTEGER
funnel_stage VARCHAR(50)
conditions JSONB
action_type VARCHAR(50)
action_data JSONB
once_per_dialog BOOLEAN
priority INTEGER
is_active BOOLEAN
```

## Текущие триггеры в БД

| code | stage | action |
|------|-------|--------|
| apple_display_warranty | presentation | Гарантия 6 мес на дисплей Apple |
| high_price_discount | presentation | Скидка 5% при цене > 7000 |
| battery_care | presentation | Инфо о калибровке батареи |

## Инструкция по обновлению в n8n

### 1. Заменить ноду "Load Triggers" на PostgreSQL

**SQL Query:**
```sql
SELECT
    id,
    code as trigger_id,
    name as trigger_name,
    funnel_stage as stage,
    conditions,
    action_type,
    action_data as action,
    once_per_dialog,
    priority
FROM elo_v_triggers
WHERE funnel_stage = $1
  AND vertical_id = $2
  AND is_active = true
ORDER BY priority DESC;
```

**Query Replacement:** `={{ [$json.stage, $json.context.vertical_id] }}`

### 2. Обновить ноду "Evaluate Triggers"

Заменить начало кода:

```javascript
// БЫЛО:
const TRIGGERS = [...]; // хардкод
const stageTriggers = TRIGGERS.filter(t => t.stage === stage);

// СТАЛО:
const stageTriggers = $('PostgreSQL Load Triggers').all().map(item => {
  const t = item.json;
  return {
    trigger_id: t.trigger_id,
    trigger_name: t.trigger_name,
    stage: t.stage,
    conditions: t.conditions,
    once_per_dialog: t.once_per_dialog,
    action: {
      type: t.action_type,
      ...t.action
    }
  };
});
```

Остальной код (buildFlatContext, matchConditions) оставить без изменений.

## Структура workflow после обновления

```
Webhook
   │
   ▼
PostgreSQL Load Triggers  ← NEW
   │
   ▼
Evaluate Triggers  ← UPDATED
   │
   ▼
Respond
```

## Добавление нового триггера

```sql
INSERT INTO elo_v_triggers (code, name, vertical_id, funnel_stage, conditions, action_type, action_data)
VALUES (
    'samsung_original_parts',
    'Samsung original parts info',
    1,
    'presentation',
    '{"device.brand": "Samsung"}',
    'send_message',
    '{"message": "Для Samsung используем оригинальные запчасти с гарантией."}'
);
```

## Тест

```bash
curl -X POST https://n8n.n8nsrv.ru/webhook/triggers \
  -H "Content-Type: application/json" \
  -d '{
    "stage": "presentation",
    "context": {
      "vertical_id": 1,
      "focus_line_id": "line_0",
      "lines": [{
        "id": "line_0",
        "slots": {
          "device": {"brand": "Apple", "model": "iPhone 14"},
          "repair_type": {"code": "display_replaced"},
          "price": {"amount": 15000}
        }
      }],
      "executed_triggers": []
    }
  }'
```

Ожидаемый результат: 2 триггера (apple_display_warranty, high_price_discount)
