# ELO_Input_Worker

> n8n Polygon — Queue processor with debounce (emulates MCP Input Contour worker)

---

## General Information

| Parameter | Value |
|----------|----------|
| **ID** | `NEW` (to be created) |
| **File** | `NEW/workflows/ELO_Input/ELO_Input_Worker.json` |
| **Trigger** | Schedule Trigger (every 2 seconds) |
| **Called from** | Automatic (schedule) |
| **Calls** | Client Contour (8772 or n8n webhook) |

---

## Purpose

n8n polygon for debugging Input Contour worker logic:

1. Pull messages from Redis queue
2. Group by batch_key (tenant:channel:chat_id)
3. Apply debounce logic (10s silence or 300s max wait)
4. Merge messages and forward to Client Contour

**MCP equivalent:** Python asyncio worker in `MCP/input-contour/main.py`

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  ELO_Input_Worker (n8n Schedule Trigger, every 2s)                          │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │ 1. LPOP queue:incoming (batch of 10)                                   │  │
│  │    Group by batch_key = tenant:channel:chat_id                        │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
│                              │                                               │
│                              ▼                                               │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │ 2. For each batch_key:                                                 │  │
│  │    - Update deadline in ZSET batch:deadlines                          │  │
│  │    - Store first_seen in ZSET batch:first_seen                        │  │
│  │    - Push messages to queue:batch:{key}                               │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
│                              │                                               │
│                              ▼                                               │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │ 3. Check batch:deadlines for due batches                               │  │
│  │    If deadline <= now:                                                 │  │
│  │    - Read all messages from queue:batch:{key}                         │  │
│  │    - Merge texts                                                       │  │
│  │    - POST to Client Contour                                           │  │
│  │    - Cleanup Redis keys                                                │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
                                        │
                                        │ POST /resolve
                                        ▼
                              Client Contour (8772)
```

---

## Debounce Logic

**Goal:** Wait for client to finish typing before processing.

```
10:00:01 — "Привет"           ┐
10:00:03 — "Разбил экран"     ├── batch (waiting)
10:00:05 — "iPhone 14"        ┘

         ↓ silence 10 sec ↓

10:00:15 — Deadline reached → Merge and dispatch
           "Привет\n\nРазбил экран\n\niPhone 14"
```

**Parameters:**

| Parameter | Value | Description |
|----------|-------|-------------|
| `debounce_seconds` | 10 | Wait for silence |
| `max_wait_seconds` | 300 | Max total wait (safety) |

**Deadline calculation:**
```javascript
deadline = Math.min(
  first_seen + max_wait_seconds,  // safety cap
  now + debounce_seconds          // silence deadline
);
```

---

## Redis Keys

| Key | Type | Purpose |
|-----|------|---------|
| `queue:incoming` | List | Global incoming queue |
| `queue:batch:{batch_key}` | List | Per-chat message batch |
| `batch:deadlines` | ZSET | Score = deadline timestamp |
| `batch:first_seen` | ZSET | Score = first message timestamp |
| `dlq:input_contour` | List | Dead letter queue |

**batch_key format:** `{tenant_id}:{channel}:{external_chat_id}`

Example: `a1b2c3d4:telegram:123456789`

---

## Nodes

### 1. Schedule Trigger

| Parameter | Value |
|----------|----------|
| **Type** | Schedule Trigger |
| **Interval** | 2 seconds |

---

### 2. Pop Messages (Code + Redis)

```javascript
// This node coordinates 10 LPOP operations
// n8n limitation: can't do LPOP in batch, so we do 10 sequential

const messages = [];
// Actual LPOP done via Redis nodes below
return { messages: messages };
```

**10 Redis LPOP nodes:**
- Each does LPOP from `queue:incoming`
- Results collected in next node

---

### 3. Collect and Group

```javascript
const allItems = $input.all();
const messages = [];

// Collect non-empty results
for (const item of allItems) {
  const val = item.json?.value;
  if (val && val !== 'null' && val !== '') {
    try {
      const parsed = typeof val === 'string' ? JSON.parse(val) : val;
      messages.push(parsed);
    } catch (e) {
      console.error('Failed to parse:', val);
    }
  }
}

if (messages.length === 0) {
  return { empty: true, groups: {} };
}

// Group by batch_key
const groups = {};
for (const msg of messages) {
  // batch_key = tenant_id:channel:external_chat_id
  const tenant = msg.tenant_id || 'unknown';
  const key = `${tenant}:${msg.channel}:${msg.external_chat_id}`;

  if (!groups[key]) {
    groups[key] = [];
  }
  groups[key].push(msg);
}

return {
  empty: false,
  groups: groups,
  group_keys: Object.keys(groups),
  timestamp: Date.now()
};
```

---

### 4. Is Queue Empty?

| Condition | Result |
|---------|-----------|
| `empty === true` | → Check Deadlines |
| `empty === false` | → Process Groups |

---

### 5. Process Groups (Loop)

For each group in `group_keys`:
1. Calculate deadline
2. Store messages in batch queue
3. Update deadline ZSET

```javascript
const item = $input.first().json;
const key = item.batch_key;
const messages = item.messages;
const now = Date.now() / 1000;

const debounce_seconds = 10;
const max_wait_seconds = 300;

// Calculate deadline
let first_seen = item.first_seen || now;
const deadline = Math.min(
  first_seen + max_wait_seconds,
  now + debounce_seconds
);

return {
  batch_key: key,
  messages_json: messages.map(m => JSON.stringify(m)),
  deadline: deadline,
  first_seen: first_seen
};
```

Then:
- Redis RPUSH `queue:batch:{batch_key}` for each message
- Redis ZADD `batch:deadlines` score=deadline member=batch_key
- Redis ZADD `batch:first_seen` score=first_seen member=batch_key (NX)

---

### 6. Check Deadlines

```javascript
const now = Date.now() / 1000;

// This will be done via Redis ZRANGEBYSCORE
return {
  check_time: now,
  max_score: now
};
```

Then: Redis ZRANGEBYSCORE `batch:deadlines` -inf {now}

---

### 7. Has Due Batches?

| Condition | Result |
|---------|-----------|
| Due batches exist | → Process Due Batches |
| No due batches | → End |

---

### 8. Process Due Batches (Loop)

For each due batch_key:
1. Read all messages from `queue:batch:{key}`
2. Merge texts
3. Send to Client Contour
4. Cleanup Redis

```javascript
const batch_key = $json.member;

// Split batch_key to extract components
const parts = batch_key.split(':');
const tenant_id = parts[0];
const channel = parts[1];
const chat_id = parts.slice(2).join(':'); // handle : in chat_id

return {
  batch_key: batch_key,
  queue_key: `queue:batch:${batch_key}`,
  tenant_id: tenant_id,
  channel: channel,
  external_chat_id: chat_id
};
```

---

### 9. Read Batch Queue

| Parameter | Value |
|----------|----------|
| **Type** | Redis |
| **Operation** | LRANGE |
| **Key** | `queue:batch:{{ $json.batch_key }}` |
| **Start** | 0 |
| **End** | -1 |

---

### 10. Merge Messages

```javascript
const raw_messages = $json.value || [];
const batch_info = $('Process Due Batches').first().json;

const messages = [];
for (const raw of raw_messages) {
  try {
    const msg = typeof raw === 'string' ? JSON.parse(raw) : raw;
    messages.push(msg);
  } catch (e) {}
}

if (messages.length === 0) {
  return { empty: true, batch_key: batch_info.batch_key };
}

// Sort by timestamp
messages.sort((a, b) => {
  const tA = Date.parse(a.timestamp) || 0;
  const tB = Date.parse(b.timestamp) || 0;
  return tA - tB;
});

// Merge texts
const texts = messages.map(m => {
  if (m.media?.voice_transcribed_text) {
    return `[Голосовое]: ${m.media.voice_transcribed_text}`;
  }
  return m.text || '';
}).filter(t => t.trim());

const combined_text = texts.join('\n\n');
const base = messages[0];
const last = messages[messages.length - 1];

return {
  empty: false,
  batch_key: batch_info.batch_key,

  // Payload for Client Contour
  channel: base.channel,
  bot_token: base.bot_token,
  external_user_id: base.external_user_id,
  external_chat_id: base.external_chat_id,
  client_name: base.client_name,
  client_phone: base.client_phone,
  text: combined_text,
  timestamp: base.timestamp,
  message_ids: messages.map(m => m.message_id).filter(Boolean),
  trace_id: base.trace_id,
  media: base.media || {},
  meta: {
    batched: messages.length > 1,
    batch_size: messages.length,
    batch_reason: 'debounce',
    external_chat_id: base.external_chat_id
  }
};
```

---

### 11. Send to Client Contour

| Parameter | Value |
|----------|----------|
| **Type** | HTTP Request |
| **typeVersion** | 4.2 |
| **URL** | `http://45.144.177.128:8772/resolve` |
| **Method** | POST |
| **Options** | `{ continueOnFail: true, timeout: 30000 }` |

**Alternative (n8n polygon):**
- URL: `https://n8n.n8nsrv.ru/webhook/elo-client-resolve`

---

### 12. Cleanup Redis

After successful send:

1. Redis DELETE `queue:batch:{batch_key}`
2. Redis ZREM `batch:deadlines` member=batch_key
3. Redis ZREM `batch:first_seen` member=batch_key

**On failure:**
- Push to DLQ: `dlq:input_contour`

```javascript
const merged = $('Merge Messages').first().json;
const response = $input.first().json;

if (!response || response.error) {
  return {
    dlq: true,
    payload: merged,
    error: response?.error || 'Unknown error'
  };
}

return {
  dlq: false,
  cleanup: true,
  batch_key: merged.batch_key
};
```

---

## Flow Diagram

```
Schedule Trigger (every 2s)
         │
         ▼
┌─────────────────────┐
│ 1. POP Messages     │  ← 10x LPOP queue:incoming
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│ 2. Group by         │  ← batch_key = tenant:channel:chat_id
│    batch_key        │
└──────────┬──────────┘
           │
     Is Empty?
           │
    ├── NO → Process groups (update deadlines)
    │
    └── (both paths continue)
           │
           ▼
┌─────────────────────┐
│ 3. Check Deadlines  │  ← ZRANGEBYSCORE batch:deadlines -inf now
└──────────┬──────────┘
           │
     Has Due?
           │
    ├── NO → End
    │
    └── YES
           │
           ▼
┌─────────────────────┐
│ 4. For each due:    │
│    - Read batch     │  ← LRANGE queue:batch:{key}
│    - Merge texts    │
│    - Send to Client │  ← POST /resolve
│    - Cleanup Redis  │  ← DELETE + ZREM
└─────────────────────┘
```

---

## Error Handling

| Error | Action |
|-------|--------|
| Redis unavailable | Log and retry next cycle |
| Client Contour timeout | Push to DLQ |
| Client Contour 4xx/5xx | Push to DLQ |
| Parse error | Skip message |

**DLQ format:**
```json
{
  "payload": { ... merged message ... },
  "error": "Connection timeout",
  "timestamp": "2024-12-11T10:00:00Z"
}
```

---

## Credentials

| Credential | Purpose |
|------------|---------|
| `ELO_Redis` | Redis for queues and ZSET |

---

## n8n v2.0 Compliance

| Check | Status |
|-------|--------|
| No Python Code Node | ✅ |
| No `process.env` | ✅ |
| Schedule Trigger | ✅ |
| Code typeVersion: 2 | ✅ |
| HTTP Request typeVersion: 4.2 | ✅ |
| Redis uses Credentials | ✅ |

---

## Comparison with MCP

| Feature | MCP (8771) | n8n Polygon |
|---------|------------|-------------|
| Trigger | asyncio loop | Schedule Trigger |
| Queue pop | LPOP in loop | 10x LPOP |
| Deadlines | ZSET batch:deadlines | ZSET batch:deadlines |
| Debounce | 10s silence / 300s max | 10s silence / 300s max |
| Output | POST to Client Contour | POST to Client Contour |

**Logic is identical. Implementation differs (Python vs n8n nodes).**

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Service | Redis | Queue storage |
| Service | Client Contour | Next step |

---

**Document:** ELO_Input_Worker.md
**Date:** 2025-12-11
**Author:** Claude
**Status:** n8n polygon (for debugging before MCP deployment)
