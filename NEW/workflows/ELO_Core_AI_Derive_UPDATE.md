# ELO_Core_AI_Derive — Обновление на SQL

## Что изменилось

| Было (хардкод) | Стало (SQL) |
|---------------|-------------|
| 9 правил в коде | Таблицы в PostgreSQL |
| 2 бренда цен | Полный прайс-лист |
| Изменения = код | Изменения = БД |

## Новые таблицы

```
elo_symptom_types          — типы симптомов (+uuid)
elo_diagnosis_types        — типы диагнозов (+uuid)
elo_repair_actions         — типы ремонтов (+uuid)
elo_v_symptom_mappings     — aliases для поиска
elo_symptom_diagnosis_links — symptom → diagnosis
elo_diagnosis_repair_links — diagnosis → repair
elo_t_price_list           — цены (+repair_action_id)
```

## Инструкция по обновлению в n8n

### 1. Открыть ELO_Core_AI_Derive

### 2. Заменить ноду "Derive Values" на PostgreSQL

**Удалить** ноду "Derive Values"

**Добавить** ноду "PostgreSQL" с настройками:

- **Operation:** Execute Query
- **Query:** (см. ниже)
- **Query Replacement:** `={{ [$json.symptom_text, $json.tenant_id, $json.device_brand, $json.device_model] }}`

### 3. SQL Query для PostgreSQL ноды

```sql
WITH symptom_match AS (
  SELECT
    st.id as symptom_type_id,
    st.uuid as symptom_type_uuid,
    st.code as symptom_code,
    st.name_ru as symptom_name
  FROM elo_symptom_types st
  JOIN elo_v_symptom_mappings sm ON sm.symptom_type_id = st.id
  WHERE sm.vertical_id = {{ $json.vertical_id }}
    AND sm.is_active = true
    AND EXISTS (
      SELECT 1 FROM jsonb_array_elements_text(sm.aliases) alias
      WHERE $1 LIKE '%' || lower(alias) || '%'
         OR lower(alias) LIKE '%' || $1 || '%'
    )
  LIMIT 1
),
diagnosis_match AS (
  SELECT
    dt.id as diagnosis_type_id,
    dt.uuid as diagnosis_type_uuid,
    dt.code as diagnosis_code,
    dt.name_ru as diagnosis_name,
    sdl.confidence
  FROM symptom_match sm
  JOIN elo_symptom_diagnosis_links sdl ON sdl.symptom_type_id = sm.symptom_type_id
  JOIN elo_diagnosis_types dt ON dt.id = sdl.diagnosis_type_id
  WHERE sdl.is_primary = true AND sdl.is_active = true
  LIMIT 1
),
repair_match AS (
  SELECT
    ra.id as repair_action_id,
    ra.uuid as repair_action_uuid,
    ra.code as repair_code,
    ra.name_ru as repair_name
  FROM diagnosis_match dm
  JOIN elo_diagnosis_repair_links drl ON drl.diagnosis_type_id = dm.diagnosis_type_id
  JOIN elo_repair_actions ra ON ra.id = drl.repair_action_id
  WHERE drl.is_primary = true AND drl.is_active = true
  LIMIT 1
),
price_match AS (
  SELECT
    p.id as price_id,
    p.price_min,
    p.price_max,
    p.currency,
    CASE WHEN p.price_min = p.price_max THEN false ELSE true END as is_estimate
  FROM elo_t_price_list p
  JOIN repair_match rm ON p.repair_action_id = rm.repair_action_id
  WHERE p.is_active = true
    AND (p.tenant_id = $2::uuid OR p.tenant_id IS NULL)
    AND (
      (lower(p.brand) = lower($3) AND lower(p.model) LIKE '%' || lower($4) || '%')
      OR (lower(p.brand) = lower($3) AND p.model IS NULL)
      OR p.brand IS NULL
    )
  ORDER BY
    CASE WHEN p.tenant_id = $2::uuid THEN 0 ELSE 1 END,
    CASE WHEN p.model IS NOT NULL THEN 0 ELSE 1 END
  LIMIT 1
)
SELECT
  sm.symptom_type_uuid,
  sm.symptom_code,
  sm.symptom_name,
  dm.diagnosis_type_uuid,
  dm.diagnosis_code,
  dm.diagnosis_name,
  dm.confidence,
  rm.repair_action_uuid,
  rm.repair_code,
  rm.repair_name,
  pm.price_id,
  pm.price_min,
  pm.price_max,
  pm.currency,
  pm.is_estimate
FROM symptom_match sm
LEFT JOIN diagnosis_match dm ON true
LEFT JOIN repair_match rm ON true
LEFT JOIN price_match pm ON true;
```

### 4. Добавить "Prepare Query" Code ноду перед PostgreSQL

```javascript
const context = $('Filter Lines').first().json.context;
const lines = $('Filter Lines').first().json.lines;
const currentIndex = $itemIndex;
const line = lines[currentIndex];

const symptomText = line?.slots?.symptom?.text || '';
const deviceBrand = line?.slots?.device?.brand || '';
const deviceModel = line?.slots?.device?.model || '';
const tenantId = context.tenant_id;
const verticalId = context.vertical_id || 1;

return {
  line_id: line.id,
  symptom_text: symptomText.toLowerCase(),
  device_brand: deviceBrand,
  device_model: deviceModel,
  tenant_id: tenantId,
  vertical_id: verticalId
};
```

### 5. Добавить "Format Derivation" Code ноду после PostgreSQL

```javascript
const queryResult = $input.first().json;
const prepareData = $('Prepare Query').first().json;

const derivation = {
  line_id: prepareData.line_id,
  derived: {}
};

if (queryResult.symptom_type_uuid) {
  derivation.derived.symptom_type = {
    uuid: queryResult.symptom_type_uuid,
    code: queryResult.symptom_code,
    name: queryResult.symptom_name
  };
}

if (queryResult.diagnosis_type_uuid) {
  derivation.derived.diagnosis = {
    uuid: queryResult.diagnosis_type_uuid,
    code: queryResult.diagnosis_code,
    text: queryResult.diagnosis_name,
    confidence: parseFloat(queryResult.confidence) || 0.8
  };
}

if (queryResult.repair_action_uuid) {
  derivation.derived.repair_type = {
    uuid: queryResult.repair_action_uuid,
    code: queryResult.repair_code,
    text: queryResult.repair_name
  };
}

if (queryResult.price_min) {
  derivation.derived.price = {
    amount: queryResult.price_min === queryResult.price_max
      ? parseFloat(queryResult.price_min)
      : (parseFloat(queryResult.price_min) + parseFloat(queryResult.price_max)) / 2,
    min: parseFloat(queryResult.price_min),
    max: parseFloat(queryResult.price_max),
    currency: queryResult.currency || 'RUB',
    is_estimate: queryResult.is_estimate || false
  };
}

return derivation;
```

## Структура workflow после обновления

```
Webhook
   │
   ▼
Filter Lines
   │
   ▼
Has Lines? ────NO────► Empty Derivations
   │                          │
  YES                         │
   │                          │
   ▼                          │
Split Lines                   │
   │                          │
   ▼                          │
Prepare Query                 │
   │                          │
   ▼                          │
PostgreSQL Derive             │
   │                          │
   ▼                          │
Format Derivation             │
   │                          │
   ▼                          │
Aggregate ◄───────────────────┘
   │
   ▼
Respond
```

## Тест

После обновления отправить POST:

```bash
curl -X POST https://n8n.n8nsrv.ru/webhook/derive \
  -H "Content-Type: application/json" \
  -d '{
    "context": {
      "tenant_id": "11111111-1111-1111-1111-111111111111",
      "vertical_id": 1,
      "lines": [{
        "id": "line_0",
        "slots": {
          "device": {"brand": "Apple", "model": "iPhone 14 Pro"},
          "symptom": {"text": "разбит экран"}
        }
      }]
    }
  }'
```

Ожидаемый результат:

```json
{
  "derivations": [{
    "line_id": "line_0",
    "derived": {
      "symptom_type": {"uuid": "...", "code": "screen_cracked", "name": "Разбит экран"},
      "diagnosis": {"uuid": "...", "code": "display_replacement", "text": "Требуется замена дисплея"},
      "repair_type": {"uuid": "...", "code": "display_replaced", "text": "Заменён дисплей"},
      "price": {"amount": 17500, "min": 15000, "max": 20000, "currency": "RUB"}
    }
  }]
}
```
