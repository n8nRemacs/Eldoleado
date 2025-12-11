# ELO_Core_AI_Extractor

> Calls AI model to extract device info, symptoms, and vertical from message

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `NEW` (to be created) |
| **File** | `NEW/workflows/ELO_Core/ELO_Core_AI_Extractor.json` |
| **Trigger** | Execute Workflow Trigger |
| **Called from** | ELO_Core_Ingest |
| **AI Model** | Qwen3-30B via OpenRouter |

---

## Purpose

Extract structured data from client message:
1. Device info (brand, model, color)
2. Symptoms (what's wrong)
3. Suggested vertical (phone_repair for MVP)

---

## Input Data

```json
{
  "text": "Привет, сколько стоит поменять дисплей на iPhone 14 Pro?",
  "context": {
    "client": {
      "name": "Ivan",
      "total_issues": 0
    },
    "history": {
      "devices": [],
      "symptoms": []
    }
  }
}
```

---

## Output Data

```json
{
  "extractions": {
    "device": {
      "brand": "Apple",
      "model": "iPhone 14 Pro",
      "color": null,
      "confidence": 0.95
    },
    "symptoms": [
      {
        "code": "screen_replacement",
        "text": "замена дисплея",
        "confidence": 0.9
      }
    ],
    "vertical": {
      "code": "phone_repair",
      "confidence": 0.95
    },
    "intent": {
      "type": "price_inquiry",
      "text": "узнать цену"
    }
  },
  "raw_response": "...",
  "model": "qwen/qwen3-30b-a3b",
  "tokens_used": 150
}
```

---

## Nodes

### 1. Execute Workflow Trigger

Entry point — called from ELO_Core_Ingest.

---

### 2. Build AI Prompt

```javascript
const input = $input.first().json;
const context = input.context || {};

const systemPrompt = `Ты — ассистент сервиса по ремонту телефонов.
Твоя задача — извлечь структурированную информацию из сообщения клиента.

Контекст клиента:
- Имя: ${context.client?.name || 'неизвестно'}
- Предыдущие обращения: ${context.client?.total_issues || 0}
- Известные устройства: ${JSON.stringify(context.history?.devices || [])}

Извлеки из сообщения:
1. Устройство (brand, model, color)
2. Симптомы (что не работает/сломано)
3. Намерение клиента (ремонт, цена, консультация)

Ответ ТОЛЬКО в JSON формате:
{
  "device": {"brand": "...", "model": "...", "color": null},
  "symptoms": [{"code": "...", "text": "..."}],
  "vertical": "phone_repair",
  "intent": {"type": "...", "text": "..."}
}

Коды симптомов:
- screen_cracked — разбит экран
- screen_replacement — замена дисплея
- battery_issue — проблема с батареей
- charging_issue — не заряжается
- water_damage — залит водой
- software_issue — программная проблема
- other — другое

Коды намерений:
- repair_request — хочет починить
- price_inquiry — узнать цену
- consultation — консультация
- appointment — записаться`;

return {
  system_prompt: systemPrompt,
  user_message: input.text,
  context: context
};
```

---

### 3. Call OpenRouter API

| Parameter | Value |
|----------|----------|
| **Type** | HTTP Request |
| **Method** | POST |
| **URL** | `https://openrouter.ai/api/v1/chat/completions` |

**Headers:**
```
Authorization: Bearer {{ $env.OPENROUTER_API_KEY }}
Content-Type: application/json
HTTP-Referer: https://eldoleado.ru
X-Title: ELO Core Extractor
```

**Body:**
```json
{
  "model": "qwen/qwen3-30b-a3b",
  "messages": [
    {
      "role": "system",
      "content": "{{ $json.system_prompt }}"
    },
    {
      "role": "user",
      "content": "{{ $json.user_message }}"
    }
  ],
  "temperature": 0.1,
  "max_tokens": 500,
  "response_format": { "type": "json_object" }
}
```

---

### 4. Parse AI Response

```javascript
const response = $json;
const input = $('Build AI Prompt').first().json;

let extractions = {
  device: null,
  symptoms: [],
  vertical: { code: 'phone_repair', confidence: 0.5 },
  intent: null
};

try {
  const content = response.choices?.[0]?.message?.content;
  if (content) {
    const parsed = JSON.parse(content);

    extractions = {
      device: parsed.device ? {
        brand: parsed.device.brand,
        model: parsed.device.model,
        color: parsed.device.color || null,
        confidence: 0.9
      } : null,

      symptoms: (parsed.symptoms || []).map(s => ({
        code: s.code || 'other',
        text: s.text,
        confidence: 0.8
      })),

      vertical: {
        code: parsed.vertical || 'phone_repair',
        confidence: 0.9
      },

      intent: parsed.intent ? {
        type: parsed.intent.type,
        text: parsed.intent.text
      } : null
    };
  }
} catch (e) {
  console.log('Failed to parse AI response:', e.message);
}

return {
  extractions: extractions,
  raw_response: response.choices?.[0]?.message?.content,
  model: response.model,
  tokens_used: response.usage?.total_tokens || 0,
  original_text: input.user_message
};
```

---

## Flow Diagram

```
Execute Trigger
       │
       ▼
┌──────────────────┐
│ Build AI Prompt  │  ← System prompt + user message
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│ Call OpenRouter  │  ← Qwen3-30B
│       API        │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│ Parse Response   │  ← Extract JSON
└────────┬─────────┘
         │
         ▼
   Return extractions
```

---

## Extraction Schema (MVP v0)

### Device

| Field | Type | Example |
|-------|------|---------|
| brand | string | "Apple" |
| model | string | "iPhone 14 Pro" |
| color | string? | "blue" |
| confidence | number | 0.95 |

### Symptom

| Field | Type | Example |
|-------|------|---------|
| code | string | "screen_replacement" |
| text | string | "замена дисплея" |
| confidence | number | 0.9 |

### Vertical

| Field | Type | Example |
|-------|------|---------|
| code | string | "phone_repair" |
| confidence | number | 0.95 |

### Intent

| Field | Type | Example |
|-------|------|---------|
| type | string | "price_inquiry" |
| text | string | "узнать цену" |

---

## Symptom Codes (MVP — phone_repair)

| Code | Description (RU) |
|------|-----------------|
| screen_cracked | Разбит экран |
| screen_replacement | Замена дисплея |
| battery_issue | Проблема с батареей |
| battery_replacement | Замена батареи |
| charging_issue | Не заряжается |
| charging_port | Проблема с разъемом |
| water_damage | Залит водой |
| software_issue | Программная проблема |
| speaker_issue | Проблема с динамиком |
| microphone_issue | Проблема с микрофоном |
| camera_issue | Проблема с камерой |
| button_issue | Не работают кнопки |
| other | Другое |

---

## OpenRouter Configuration

```env
OPENROUTER_API_KEY=sk-or-v1-...
OPENROUTER_MODEL=qwen/qwen3-30b-a3b
```

**Model choice:**
- Qwen3-30B — good extraction, cheap
- Alternative: Claude 3.5 Sonnet (better but expensive)

---

## Error Handling

| Error | Action |
|-------|--------|
| API timeout | Return empty extractions |
| JSON parse error | Log, return partial extractions |
| Rate limit | Wait and retry once |

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| External | OpenRouter API | AI model |
| Env | OPENROUTER_API_KEY | Authentication |
