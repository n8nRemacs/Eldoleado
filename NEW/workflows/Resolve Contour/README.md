# Resolve Contour - Workflows

## Архитектура

Резольверы разделены на 3 независимых модуля для упрощения отладки и тестирования.

---

## Workflows

### 1. ELO_Tenant_Resolver
**Вход:**
```json
{
  "channel": "whatsapp",
  "credential": "wa_session_123"
}
```

**Выход:**
```json
{
  "tenant_id": "11111111-1111-1111-1111-111111111111",
  "channel_account_id": "abc-123",
  "channel_id": 2,
  "channel": "whatsapp",
  "credential": "wa_session_123",
  "trace_id": "tenant_...",
  "_source": "cache|database"
}
```

**Задача:** Определить tenant по channel + credential

---

### 2. ELO_Client_Resolver
**Вход:**
```json
{
  "tenant_id": "11111111-1111-1111-1111-111111111111",
  "channel_id": 2,
  "channel": "whatsapp",
  "external_chat_id": "79997253777@s.whatsapp.net",
  "sender_name": "Дмитрий",
  "sender_username": "@dmitry"
}
```

**Выход:**
```json
{
  "tenant_id": "11111111-1111-1111-1111-111111111111",
  "channel_id": 2,
  "channel": "whatsapp",
  "external_chat_id": "79997253777@s.whatsapp.net",
  "client_id": "aaa-bbb-ccc",
  "is_new_client": false,
  "was_unified": false,
  "trace_id": "client_...",
  "_source": "cache|database|created"
}
```

**Задача:** Определить client_id (с учётом Unifier для WhatsApp/MAX)

---

### 3. ELO_Dialog_Resolver
**Вход:**
```json
{
  "tenant_id": "11111111-1111-1111-1111-111111111111",
  "client_id": "aaa-bbb-ccc",
  "channel_id": 2
}
```

**Выход:**
```json
{
  "tenant_id": "11111111-1111-1111-1111-111111111111",
  "client_id": "aaa-bbb-ccc",
  "channel_id": 2,
  "dialog_id": "xxx-yyy-zzz",
  "last_client_channel_id": 2,
  "is_new_dialog": false,
  "trace_id": "dialog_...",
  "_source": "cache|database|created"
}
```

**Задача:** Определить dialog_id (ONE dialog per client)

---

### 4. ELO_Resolver_v3 (MAIN)
**Вход:**
```json
{
  "channel": "whatsapp",
  "credential": "wa_session_123",
  "external_chat_id": "79997253777@s.whatsapp.net",
  "sender_name": "Дмитрий",
  "text": "Привет"
}
```

**Выход:**
```json
{
  "tenant_id": "11111111-1111-1111-1111-111111111111",
  "client_id": "aaa-bbb-ccc",
  "dialog_id": "xxx-yyy-zzz",
  "channel_id": 2,
  "channel_account_id": "abc-123",
  "channel": "whatsapp",
  "external_chat_id": "79997253777@s.whatsapp.net",
  "last_client_channel_id": 2,
  "is_new_client": false,
  "is_new_dialog": false,
  "was_unified": false,
  "sender_name": "Дмитрий",
  "text": "Привет",
  "trace_id": "resolver_...",
  "_tenant_source": "cache",
  "_client_source": "cache",
  "_dialog_source": "cache",
  "_execution_time_ms": 45
}
```

**Задача:** Главный оркестратор, вызывает 3 резольвера последовательно

---

## Поток данных

```
┌─────────────────────────────────────┐
│  ELO_Resolver_v3                    │
│  (Main Orchestrator)                │
└─────────────────────────────────────┘
              ↓
    ┌──────────────────┐
    │ Prepare Input    │
    └──────────────────┘
              ↓
    ┌──────────────────────────────┐
    │ Call Tenant Resolver         │
    │ → ELO_Tenant_Resolver        │
    └──────────────────────────────┘
              ↓
    tenant_id, channel_account_id, channel_id
              ↓
    ┌──────────────────┐
    │ Prepare Client   │
    └──────────────────┘
              ↓
    ┌──────────────────────────────┐
    │ Call Client Resolver         │
    │ → ELO_Client_Resolver        │
    │   → Call Unifier (if phone)  │
    └──────────────────────────────┘
              ↓
    client_id, is_new_client, was_unified
              ↓
    ┌──────────────────┐
    │ Prepare Dialog   │
    └──────────────────┘
              ↓
    ┌──────────────────────────────┐
    │ Call Dialog Resolver         │
    │ → ELO_Dialog_Resolver        │
    └──────────────────────────────┘
              ↓
    dialog_id, is_new_dialog
              ↓
    ┌──────────────────┐
    │ Build Response   │
    └──────────────────┘
```

---

## Преимущества разделения

### 1. Отладка
- Каждый резольвер тестируется отдельно
- Легко найти ошибку в конкретном модуле
- Trace ID сквозной для всей цепочки

### 2. Переиспользование
- Tenant Resolver можно вызвать отдельно для валидации channel
- Client Resolver можно использовать в других workflow
- Dialog Resolver универсален для любого источника client_id

### 3. Производительность
- Каждый резольвер имеет свой Redis кеш
- TTL настраивается независимо:
  - Tenant: 24 часа (credential меняется редко)
  - Client: 1 час (может измениться после unify)
  - Dialog: 24 часа (стабильно)

### 4. Расширяемость
- Добавить новую логику в Client Resolver (например, проверка блокировки)
- Добавить новую логику в Dialog Resolver (например, автозакрытие старых диалогов)
- Не трогать остальные резольверы

---

## Миграция

### Старый workflow (ELO_Resolver.json)
- 47 нод в одном файле
- Сложно отлаживать
- Запутанные связи между нодами

### Новый workflow (ELO_Resolver_v3.json)
- 4 workflow (3 резольвера + 1 main)
- Каждый резольвер 14-19 нод
- Чистая линейная логика в каждом

### Как перейти
1. Импортировать 4 новых workflow в n8n
2. Обновить ELO_Input_Processor: вызывать `ELO_Resolver_v3` вместо `ELO_Resolver`
3. Протестировать
4. Деактивировать старый `ELO_Resolver`

---

## Redis Keys

| Resolver | Key Pattern | TTL | Example |
|----------|-------------|-----|---------|
| Tenant | `cache:tenant:{channel}:{credential}` | 24h | `cache:tenant:whatsapp:wa_session_123` |
| Client | `cache:client:{tenant_id}:{channel}:{external_chat_id}` | 1h | `cache:client:111...:whatsapp:79997253777@s.whatsapp.net` |
| Dialog | `cache:dialog:{tenant_id}:{client_id}` | 24h | `cache:dialog:111...:aaa-bbb-ccc` |

---

## Тестирование

### 1. Тест Tenant Resolver
```bash
# n8n → ELO_Tenant_Resolver → Execute Workflow Trigger → Test
# Input:
{
  "channel": "whatsapp",
  "credential": "wa_22222222-2222-2222-2222-222222222222_1766570899887"
}
# Expected: tenant_id, channel_account_id, channel_id
```

### 2. Тест Client Resolver
```bash
# Input:
{
  "tenant_id": "11111111-1111-1111-1111-111111111111",
  "channel_id": 2,
  "channel": "whatsapp",
  "external_chat_id": "79997253777@s.whatsapp.net",
  "sender_name": "Дмитрий"
}
# Expected: client_id, is_new_client
```

### 3. Тест Dialog Resolver
```bash
# Input:
{
  "tenant_id": "11111111-1111-1111-1111-111111111111",
  "client_id": "...",
  "channel_id": 2
}
# Expected: dialog_id, is_new_dialog
```

### 4. Тест Main Resolver
```bash
# Input:
{
  "channel": "whatsapp",
  "credential": "wa_session_123",
  "external_chat_id": "79997253777@s.whatsapp.net",
  "sender_name": "Дмитрий",
  "text": "Привет"
}
# Expected: полный результат с tenant_id, client_id, dialog_id
```

---

## Debug

Добавлено поле `_source` в каждый резольвер для отладки:
- `cache` - данные из Redis
- `database` - данные из PostgreSQL
- `created` - новая запись создана

Также добавлен `_execution_time_ms` в главном резольвере для мониторинга производительности.

---

*Последнее обновление: 2025-12-27*
