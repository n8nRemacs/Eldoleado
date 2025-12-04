# STOP API - Чеклист завершения сессии

> **ВАЖНО:** При обновлении этого файла ВСЕГДА указывай дату И время в формате: `DD месяц YYYY, HH:MM (UTC+4)`

---

## ОБЯЗАТЕЛЬНО перед закрытием сессии:

### 1. Обновить StartApi.md

**ВАЖНО:** В начало StartApi.md ВСЕГДА добавляй блок синхронизации:

```markdown
## ПЕРВЫМ ДЕЛОМ — Синхронизация

\`\`\`bash
cd "C:/Users/User/Documents/Eldoleado"
git pull
\`\`\`

**После git pull — ПЕРЕЧИТАЙ этот файл заново (StartApi.md), пропустив этот блок синхронизации!**
Иначе будешь работать по устаревшей версии документа.

---
```

Затем обнови секции "Быстрый старт" и "Статус каналов" — добавь всё что изменилось.

### 2. Git sync
```bash
git add -A && git commit -m "API session: краткое описание" && git push
```

---

## Последняя сессия: 3 декабря 2025, 18:30 (UTC+4)

## Что было выполнено в этой сессии

### 1. Создана новая архитектура IN/OUT с Redis очередями

**Проблема:** Узкое место на батчере — синхронные вызовы создавали задержки.

**Решение:** Асинхронная обработка через Redis очереди.

### 2. Созданы воркфлоу для импорта

| Тип | Файлы | Описание |
|-----|-------|----------|
| **IN** | BAT_IN_Avito, BAT_IN_WhatsApp, BAT_IN_VK, BAT_IN_MAX, BAT_IN_Telegram | Входящие сообщения → `queue:incoming` |
| **Core** | BAT_Queue_Processor | Каждые 5 сек забирает из `queue:incoming`, группирует по чату |
| **Core** | BAT_Batch_Debouncer_1..10 | 10 параллельных воркеров, ждут тишины 20 сек, объединяют сообщения |
| **Core** | BAT_Out_Processor | Каждые 3 сек забирает из `queue:outgoing`, роутит по каналам |
| **OUT** | BAT_OUT_Avito, BAT_OUT_WhatsApp, BAT_OUT_VK, BAT_OUT_MAX, BAT_OUT_Telegram | Отправка сообщений |

### 3. Архитектура потока сообщений

```
ВХОД:
Каналы (Avito/VK/WhatsApp/MAX/Telegram)
    ↓ webhook/trigger
BAT_IN_{Channel} → нормализация → RPUSH queue:incoming
    ↓
BAT_Queue_Processor (5 сек) → группировка по chat_id
    ↓
queue:batch:{channel}:{chat_id} + queue:debounce:pending
    ↓
BAT_Batch_Debouncer_1..10 (10 сек) → ждёт тишины 20 сек
    ↓
Client Resolver → Appeal Manager

ВЫХОД:
Appeal Manager / AI Worker
    ↓ RPUSH queue:outgoing
BAT_Out_Processor (3 сек) → роутинг по channel
    ↓
BAT_OUT_{Channel} → отправка в канал
```

### 4. Исправления

- Исправлен баг GET → lRange в Debouncer (для получения списка сообщений)
- Убраны credentials authentication из HTTP Request нод
- Убраны Webhook Trigger и RespondToWebhook из OUT воркфлоу
- Исправлены ссылки `$('Webhook Trigger')` → `$('Execute Workflow Trigger')`

---

## Файлы для импорта

**Папка:** `workflows_to_import/`

```
BAT_IN_Avito.json
BAT_IN_WhatsApp.json
BAT_IN_VK.json
BAT_IN_MAX.json
BAT_IN_Telegram.json
BAT_Queue_Processor.json
BAT_Batch_Debouncer_1.json ... BAT_Batch_Debouncer_10.json
BAT_Out_Processor.json
BAT_OUT_Avito.json
BAT_OUT_WhatsApp.json
BAT_OUT_VK.json
BAT_OUT_MAX.json
BAT_OUT_Telegram.json
```

---

## Redis ключи

| Ключ | Тип | Описание |
|------|-----|----------|
| `queue:incoming` | LIST | Входящие сообщения от всех каналов |
| `queue:outgoing` | LIST | Сообщения на отправку |
| `queue:batch:{channel}:{chat_id}` | LIST | Сообщения одного чата (для батчинга) |
| `queue:debounce:pending` | LIST | Задачи для дебаунсеров |
| `last_seen:{channel}:{chat_id}` | STRING | Время последнего сообщения (для debounce) |
| `lock:batch:{channel}:{chat_id}` | STRING | Lock на батч (TTL 300 сек) |

---

## Параметры батчинга

| Параметр | Значение | Описание |
|----------|----------|----------|
| Silence timeout | 20 сек | Сколько ждать тишины перед отправкой |
| Max wait | 300 сек | Максимальное время ожидания |
| Queue Processor interval | 5 сек | Интервал проверки входящей очереди |
| Debouncer interval | 10 сек | Интервал проверки задач дебаунсера |
| Out Processor interval | 3 сек | Интервал проверки исходящей очереди |
| Debouncer workers | 10 шт | Количество параллельных воркеров |

---

## При импорте заменить

| Placeholder | Что нужно |
|-------------|-----------|
| `TELEGRAM_BOT_CRED_ID` | ID credential для Telegram Bot |
| `OUT_TELEGRAM_WORKFLOW_ID` | ID BAT_OUT_Telegram после импорта |

---

## Серверы и доступы

| Сервер | IP | Назначение |
|--------|-----|-----------|
| API Server | 45.144.177.128 | MCP серверы каналов |
| n8n Server | n8n.n8nsrv.ru | Workflows |
| Database | 185.221.214.83:6544 | PostgreSQL |
| Redis | 185.221.214.83:6379 | Очереди, кэш |

**SSH доступ:**
```
ssh root@45.144.177.128
Password: Mi31415926pSss!
```

---

## Следующие шаги

1. **Импортировать** воркфлоу из `workflows_to_import/`
2. **Заменить** placeholder'ы на реальные ID
3. **Активировать** воркфлоу
4. **Протестировать** полный цикл IN → Batching → OUT
5. **Настроить** Form и Phone входы (отдельная логика)

---

## Команда для продолжения

```
Продолжаем работу над Eldoleado.

Выполнено (03.12.2025):
- Создана новая архитектура IN/OUT с Redis очередями
- 5 входных каналов: Avito, WhatsApp, VK, MAX, Telegram
- Батчинг через 10 параллельных Debouncer воркеров
- 5 выходных каналов через Out Processor

Файлы для импорта в workflows_to_import/:
- BAT_IN_* (5 шт) - входы
- BAT_Queue_Processor, BAT_Batch_Debouncer_1..10 - батчинг
- BAT_Out_Processor, BAT_OUT_* (5 шт) - выходы

Следующие шаги:
1. Импортировать воркфлоу в n8n
2. Протестировать полный цикл
3. Настроить Form/Phone входы

Читай StartApi.md для полного контекста.
```

---

## Синхронизация с GitHub

**Перед завершением сессии ОБЯЗАТЕЛЬНО выполни:**

```bash
scripts\git-sync.bat
```

Или попроси Claude: **"синхронизируй с GitHub"**
