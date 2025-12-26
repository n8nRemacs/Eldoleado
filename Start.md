# Start Session - План на следующую сессию

## Инфраструктура

| Сервер | IP | Сервисы |
|--------|-----|---------|
| **Messenger** | 155.212.221.189 | MCP: telegram :8761, whatsapp :8769, avito :8793 |
| **n8n** | 185.221.214.83 | n8n, PostgreSQL, Redis |
| **HTTPS Gateway** | msg.eldoleado.ru | nginx + Let's Encrypt |

---

## Приоритет 1: Исправить IF nodes в ELO_Resolver

n8n IF node v2 неправильно обрабатывает `undefined`. Нужно изменить условия.

### Текущая проблема

```
Данные: { tenant_id: undefined }
Условие: tenant_id exists
Результат: TRUE (должен быть FALSE)
```

### Решение

Изменить все IF nodes на использование `!!` в expression:

```json
{
  "leftValue": "={{ !!$json.tenant_id }}",
  "rightValue": true,
  "operator": { "type": "boolean", "operation": "equals" }
}
```

### IF nodes для исправления

| Node | Условие | Файл |
|------|---------|------|
| `Tenant Cached?` | `!!$json.tenant_id === true` | ELO_Resolver_v2.json |
| `Client Cached?` | `!!$json.client_id === true` | ELO_Resolver_v2.json |
| `Need Create?` | `!!$json.client_id === false` | ELO_Resolver_v2.json |
| `Dialog Cached?` | `!!$json.dialog_id === true` | ELO_Resolver_v2.json |
| `Need Dialog?` | `!!$json.dialog_id === false` | ELO_Resolver_v2.json |

### Тестирование

После исправления:
1. Очистить Redis: `docker exec n8n-redis redis-cli FLUSHALL`
2. Отправить сообщение в WhatsApp
3. Проверить что поток идёт: Validate → Redis Get → Check Cache → **DB Get** → Merge → Redis Set

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
| ELO_Resolver | `NEW/workflows/ELO_Resolver_v2.json` | Импортирован, IF nodes баг |
| ELO_Unifier | `NEW/workflows/ELO_Unifier.json` | Готов к импорту |
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
| `123.md` | Подробный анализ проблемы с IF nodes |
| `Stop.md` | Что сделано в прошлой сессии |
| `NEW/workflows/ELO_Resolver_v2.json` | Unified resolver |
| `NEW/workflows/ELO_Unifier.json` | Модуль объединения клиентов |

---

*Последнее обновление: 2025-12-26*
