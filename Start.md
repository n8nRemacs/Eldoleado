# Start Session - План на следующую сессию

## Инфраструктура

| Сервер | IP | Сервисы |
|--------|-----|---------|
| **Messenger** | 155.212.221.189 | MCP: telegram :8761, whatsapp :8769, avito :8793 |
| **n8n** | 185.221.214.83 | n8n, PostgreSQL, Redis |
| **HTTPS Gateway** | msg.eldoleado.ru | nginx + Let's Encrypt |

---

## Приоритет 1: Протестировать ELO_Resolver

Workflow полностью переписан, но **не протестирован**.

### Что сделано
- 47 нод с правильным потоком данных
- Merge ноды после Redis/DB операций
- IF ноды с optional chaining: `$json._cached?.id || '' notEquals ''`

### Шаги тестирования

1. Импортировать `NEW/workflows/Resolve Contour/ELO_Resolver.json` в n8n
2. Очистить Redis: `ssh root@185.221.214.83 "docker exec n8n-redis redis-cli FLUSHALL"`
3. Отправить сообщение в WhatsApp
4. Проверить execution в n8n

### Ожидаемый поток (первое сообщение)

```
Validate Input
    ↓
Cache Get Tenant → null
    ↓
Merge Tenant Redis → _tenant_cached: null
    ↓
Tenant Cached? → FALSE
    ↓
DB Get Tenant → {tenant_id, channel_account_id, channel_id}
    ↓
Merge DB Tenant → _db_tenant: {...}
    ↓
Tenant Found? → TRUE
    ↓
Use DB Tenant
    ↓
Cache Tenant (SET)
    ↓
Restore Tenant
    ↓
Cache Get Client → null
    ↓
... (аналогично для Client и Dialog)
    ↓
Build Response
```

### Если ошибка в IF ноде

Проверить execution, найти где данные теряются. Возможно нужно другое условие.

---

## Приоритет 2: Avito Polling

Webhook от Avito не работает без платной подписки.

### Импорт workflow

1. n8n → Import → From File
2. Файл: `NEW/MVP/MCP/mcp-avito-camoufox/n8n-avito-polling.json`
3. Настроить Redis credentials
4. Активировать

---

## Workflows

| Workflow | Файл | Статус |
|----------|------|--------|
| ELO_Resolver | `NEW/workflows/Resolve Contour/ELO_Resolver.json` | Переписан, не тестирован |
| ELO_Unifier | `NEW/workflows/Resolve Contour/ELO_Unifier.json` | Готов |
| ELO_Input_Processor | n8n | Вызывает ELO_Resolver |

---

## Данные в БД

### Клиенты
```
Дмитрий | +79997253777 | 79997253777@s.whatsapp.net
Ремакс  | +79171708077 | 79171708077@s.whatsapp.net
```

### Channel Accounts (WhatsApp)
```
session_id: wa_22222222-2222-2222-2222-222222222222_1766570899887
channel_id: 2
tenant_id: 11111111-1111-1111-1111-111111111111
```

---

## SSH

```bash
ssh root@155.212.221.189  # Messenger
ssh root@185.221.214.83   # n8n
```

---

## Полезные команды

```bash
# Очистить Redis кеш
ssh root@185.221.214.83 "docker exec n8n-redis redis-cli FLUSHALL"

# Проверить кеш
ssh root@185.221.214.83 "docker exec n8n-redis redis-cli KEYS 'cache:*'"

# Посмотреть клиентов
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT id, name, phone FROM elo_t_clients;'"

# Логи WhatsApp MCP
ssh root@155.212.221.189 "docker logs whatsapp-mcp --tail 50"
```

---

## Ключевые файлы

| Файл | Описание |
|------|----------|
| `123.md` | Статус работы, что сделано/не сделано |
| `Stop.md` | Что сделано в прошлой сессии |
| `NEW/workflows/Resolve Contour/ELO_Resolver.json` | Unified resolver (47 нод) |
| `NEW/workflows/Resolve Contour/ELO_Unifier.json` | Модуль объединения клиентов |

---

## Известные баги n8n IF node v2

| Условие | Результат |
|---------|-----------|
| `!!$json.field` boolean equals true | null идёт в TRUE |
| `field exists` | undefined идёт в TRUE |
| `field isEmpty` | непредсказуемо |

**Рабочее решение:** `$json.field?.id || '' notEquals ''`

---

*Последнее обновление: 2025-12-27*
