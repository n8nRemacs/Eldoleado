# ELO_Core_Batch_Debouncer

> Waits for silence, merges messages, passes to Client Resolver

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `hwYfaLAKCwaWpoQk` |
| **Файл** | `NEW/workflows/n8n_old/TaskWork/ELO_Core_Batch_Debouncer.json` |
| **Копий** | 10 (for parallel processing) |
| **Триггер** | Execute Workflow Trigger |
| **Вызывается из** | ELO_Core_Queue_Processor |
| **Выход** | → ELO_Core_Client_Resolver |

---

## Purpose

1. Waits for silence (10 seconds without new messages)
2. Merges all batch messages into one text
3. Cleans Redis (queue + lock)
4. Passes to Client Resolver

---

## Debounce Parameters

| Parameter | Value | Description |
|----------|----------|----------|
| `debounce_seconds` | **10** | Silence window to trigger |
| `max_wait_seconds` | 300 | Max wait (safety) |

> **TODO:** Make `debounce_seconds` configurable in tenant settings

---

## Redis keys

| Key | Operation | Purpose |
|------|----------|------------|
| `last_seen:{batch_key}` | GET | Time of last message |
| `queue:batch:{batch_key}` | GET/DELETE | Batch message queue |
| `lock:batch:{batch_key}` | DELETE | Chat processing lock |

---

## Input Data

```json
{
  "batch_key": "telegram:tg_123456",
  "channel": "telegram",
  "external_chat_id": "tg_123456",
  "queue_key": "queue:batch:telegram:tg_123456",
  "lock_key": "lock:batch:telegram:tg_123456",
  "last_seen_key": "last_seen:telegram:tg_123456"
}
```

---

## Output Data (to Client Resolver)

```json
{
  "batch_key": "telegram:tg_123456",
  "channel": "telegram",
  "external_user_id": "tg_123456",
  "external_chat_id": "tg_123456",
  "text": "Привет\n\nРазбил экран\n\niPhone 14",
  "timestamp": "2024-12-10T10:00:01Z",
  "tenant_id": "uuid",
  "client_phone": null,
  "client_name": "Иван",
  "media": {...},
  "meta": {
    "batched": true,
    "batch_size": 3,
    "batch_reason": "silence_reached",
    "original_messages": [
      {"timestamp": "...", "type": "text", "text_length": 6},
      {"timestamp": "...", "type": "text", "text_length": 12},
      {"timestamp": "...", "type": "text", "text_length": 9}
    ],
    "combined_at": "2024-12-10T10:00:15Z"
  }
}
```

---

## Nodes

### 1. Execute Workflow Trigger

Entry point — called from Queue Processor.

---

### 2. Init Debouncer

```javascript
const input = $input.first().json;

return {
  batch_key: input.batch_key,
  channel: input.channel,
  external_chat_id: input.external_chat_id,
  queue_key: input.queue_key || `queue:batch:${input.batch_key}`,
  lock_key: input.lock_key || `lock:batch:${input.batch_key}`,
  last_seen_key: input.last_seen_key || `last_seen:${input.batch_key}`,
  debounce_seconds: 10,  // TODO: from tenant settings
  max_wait_seconds: 300,
  start_time: new Date().toISOString()
};
```

---

### 3. Wait 10s (Debounce)

| Parameter | Value |
|----------|----------|
| **Тип** | Wait |
| **Amount** | 10 секунд |

---

### 4. Get Last Seen

| Parameter | Value |
|----------|----------|
| **Тип** | Redis GET |
| **Key** | `last_seen:{batch_key}` |

---

### 5. Check Silence Duration

```javascript
const lastSeenStr = $json.value;
const initData = $('Init Debouncer').first().json;
const debounceMs = initData.debounce_seconds * 1000;  // 10000ms
const maxWaitMs = initData.max_wait_seconds * 1000;   // 300000ms
const startTime = new Date(initData.start_time).getTime();
const now = Date.now();

let lastSeenTime = 0;
if (lastSeenStr) {
  lastSeenTime = new Date(lastSeenStr).getTime();
}

const silenceDuration = now - lastSeenTime;
const totalWaitDuration = now - startTime;

// Условия для сбора батча:
// 1. Тишина >= debounce_seconds
// 2. ИЛИ общее время >= max_wait_seconds (защита)
const silenceReached = silenceDuration >= debounceMs;
const maxWaitReached = totalWaitDuration >= maxWaitMs;
const readyToProcess = silenceReached || maxWaitReached;

return {
  ...initData,
  last_seen: lastSeenStr,
  silence_duration_ms: silenceDuration,
  total_wait_duration_ms: totalWaitDuration,
  silence_reached: silenceReached,
  max_wait_reached: maxWaitReached,
  ready_to_process: readyToProcess,
  reason: maxWaitReached ? 'max_wait_timeout' :
          (silenceReached ? 'silence_reached' : 'still_active')
};
```

**Логика:**
- Если тишина >= 10 сек → готовы
- Если ждём >= 5 мин → готовы (защита от бесконечного ожидания)
- Иначе → ждём ещё

---

### 6. Ready to Process?

| Условие | Результат |
|---------|-----------|
| `ready_to_process === true` | → Get Batch Queue |
| `ready_to_process === false` | → Wait 10s (loop!) |

**Loop:** Если клиент продолжает писать, ждём ещё 10 сек.

---

### 7. Get Batch Queue

| Параметр | Значение |
|----------|----------|
| **Тип** | Redis GET |
| **Key** | `queue:batch:{batch_key}` |

---

### 8. Combine Messages

```javascript
const queueData = $json.value;
const initData = $('Check Silence Duration').first().json;

let messages = [];

if (queueData) {
  if (Array.isArray(queueData)) {
    for (const item of queueData) {
      try {
        const parsed = typeof item === 'string' ? JSON.parse(item) : item;
        messages.push(parsed);
      } catch (e) {}
    }
  } else if (typeof queueData === 'string') {
    const parts = queueData.includes('|||SEPARATOR|||')
      ? queueData.split('|||SEPARATOR|||')
      : [queueData];

    for (const part of parts) {
      if (part.trim()) {
        try {
          messages.push(JSON.parse(part));
        } catch (e) {}
      }
    }
  }
}

if (messages.length === 0) {
  return { empty: true, batch_key: initData.batch_key };
}

// Сортируем по timestamp
messages.sort((a, b) => {
  const tA = Date.parse(a.timestamp) || 0;
  const tB = Date.parse(b.timestamp) || 0;
  return tA - tB;
});

// Собираем тексты
const texts = messages.map(m =>
  m.media?.voice_transcribed_text
    ? `[Голосовое]: ${m.media.voice_transcribed_text}`
    : (m.text || '')
).filter(t => t.trim());

const combinedText = texts.join('\n\n');

// Берём первое сообщение как базу
const base = messages[0];

return {
  empty: false,
  batch_key: initData.batch_key,
  channel: base.channel,
  external_user_id: base.external_user_id,
  external_chat_id: base.external_chat_id,
  text: combinedText,
  timestamp: base.timestamp,
  client_phone: base.client_phone,
  client_name: base.client_name,
  tenant_id: base.tenant_id,
  media: base.media,
  meta: {
    ...base.meta,
    batched: true,
    batch_size: messages.length,
    batch_reason: initData.reason,
    original_messages: messages.map(m => ({
      timestamp: m.timestamp,
      type: m.meta?.original_media_type || 'text',
      text_length: (m.text || '').length
    })),
    combined_at: new Date().toISOString()
  },
  prefilled_data: base.prefilled_data
};
```

**Склейка:**
- Сортировка по timestamp
- Голосовые с префиксом `[Голосовое]:`
- Разделитель: `\n\n`

---

### 9. Is Batch Empty?

| Условие | Результат |
|---------|-----------|
| `empty === true` | → Delete Queue (Empty) → Release Lock (Empty) |
| `empty === false` | → Delete Queue → Release Lock → Client Resolver |

---

### 10-11. Delete Queue / Release Lock (Empty path)

Очистка Redis при пустом батче (edge case).

---

### 12. Delete Queue

| Параметр | Значение |
|----------|----------|
| **Тип** | Redis DELETE |
| **Key** | `queue:batch:{batch_key}` |

---

### 13. Release Lock

| Параметр | Значение |
|----------|----------|
| **Тип** | Redis DELETE |
| **Key** | `lock:batch:{batch_key}` |

---

### 14. → Client Resolver

| Параметр | Значение |
|----------|----------|
| **Тип** | Execute Workflow |
| **ID** | `OUjTFzd7k4tdoAjh` |

Передаём склеенное сообщение в Client Resolver.

---

## Схема потока

```
Execute Trigger → Init Debouncer → Wait 10s → Get Last Seen → Check Silence
                                       ↑                          │
                                       │                    Ready to Process?
                                       │                     ├── NO → (loop)
                                       │                     └── YES ↓
                                       └─────────────────── Get Batch Queue
                                                                   ↓
                                                           Combine Messages
                                                                   ↓
                                                            Is Empty?
                                                    ├── YES → Cleanup → End
                                                    └── NO → Delete Queue
                                                                   ↓
                                                            Release Lock
                                                                   ↓
                                                         → Client Resolver
```

---

## Почему 10 копий Debouncer?

n8n Wait node блокирует workflow. Один Debouncer = один чат.

| Копия | Файл |
|-------|------|
| Base | ELO_Core_Batch_Debouncer.json |
| 1-10 | ELO_Core_Batch_Debouncer_1.json ... ELO_Core_Batch_Debouncer_10.json |

Queue Processor вызывает копии по очереди или случайно.

---

## Настройка debounce per tenant

**Текущее:** Хардкод 10 секунд.

**TODO:** Брать из tenant settings:
```javascript
// В Init Debouncer
const tenantSettings = input.tenant_config?.settings || {};
const debounce = tenantSettings.debounce_seconds || 10;
```

Это позволит разным тенантам настраивать время ожидания:
- Быстрая поддержка: 5 сек
- Стандарт: 10 сек
- Сложные запросы: 15-20 сек

---

## Зависимости

| Тип | ID | Назначение |
|-----|-----|------------|
| Redis | 7FQcEivUY94atW24 | Очереди и locks |
| Workflow | OUjTFzd7k4tdoAjh | ELO_Core_Client_Resolver |
