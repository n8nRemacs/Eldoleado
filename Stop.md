# STOP - Сессия 3 декабря 2025, 21:30 (UTC+4)

> **ВАЖНО:** При обновлении этого файла ВСЕГДА указывай дату И время в формате: `DD месяц YYYY, HH:MM (UTC+4)`

---

## Что сделано в этой сессии

### 1. Система Touchpoints (касаний) — ГОТОВО
Реализована полная система отслеживания касаний с клиентами:

**4 типа касаний:**
- `inbound` — клиент пишет нам
- `outbound` — мы отвечаем (ждём продолжения)
- `promo` — рассылка/поздравление (не ждём ответа)
- `mutual` — диалог состоялся (вычисляется автоматически)

**Логика mutual:**
- `inbound` + `outbound` за период (30 дней) → `mutual`
- `inbound` + `promo` + создаются сущности (appeal, device) → `mutual`
- `inbound` + `promo` без сущностей (просто "спасибо") → остаётся `inbound`

### 2. Созданные workflows

Все в папке `workflows_to_import/`:

| Файл | Описание |
|------|----------|
| `BAT_Neo4j_Touchpoint_Register.json` | Регистрация touchpoints с авто-mutual |
| `BAT_Appeal_Manager_v2.json` | Appeal Manager с touchpoints (inbound) |
| `BAT_OUT_Telegram_v2.json` | OUT Telegram с touchpoints |
| `BAT_OUT_WhatsApp_v2.json` | OUT WhatsApp с touchpoints |
| `BAT_OUT_VK_v2.json` | OUT VK с touchpoints |
| `BAT_OUT_Avito_v2.json` | OUT Avito с touchpoints |
| `BAT_OUT_MAX_v2.json` | OUT MAX с touchpoints |

### 3. API Touchpoint Register

**Endpoint:** `POST https://n8n.n8nsrv.ru/webhook/neo4j/touchpoint/register`

```json
{
  "client_id": "uuid",
  "appeal_id": "uuid",
  "channel": "telegram|whatsapp|vk|avito|max",
  "direction": "inbound|outbound|promo",
  "type": "message|call|visit",
  "tenant_id": "uuid",
  "creates_entity": true|false,
  "mutual_period_days": 30
}
```

**Response:**
```json
{
  "success": true,
  "touchpoint_id": "uuid",
  "requested_direction": "inbound",
  "final_direction": "mutual",
  "was_upgraded_to_mutual": true,
  "creates_entity": true
}
```

---

## Что НЕ сделано (на завтра)

1. **Механика объединения клиентов**
   - Когда два клиента оказываются одним человеком
   - Merge в Neo4j и PostgreSQL
   - UI для оператора

2. **Соц.инжиниринг для enrichment**
   - Как получать дополнительные контакты
   - Telegram → телефон
   - WhatsApp → имя
   - Скрипты для операторов

3. **Импорт workflows в n8n**
   - Загрузить все v2 workflows
   - Заменить старые OUT на новые
   - Активировать Touchpoint Register

---

## Структура touchpoints в Neo4j

```cypher
// Узел Touchpoint
(:Touchpoint {
  id: "uuid",
  timestamp: datetime,
  type: "message|call|visit",
  channel: "telegram|whatsapp|...",
  direction: "inbound|outbound|promo|mutual"
})

// Связи
(t:Touchpoint)-[:FROM]->(c:Client)  // inbound: клиент → нам
(t:Touchpoint)-[:TO]->(c:Client)    // outbound/promo: мы → клиенту
// mutual: обе связи FROM и TO
```

---

## Серверы

| Сервер | IP | Назначение |
|--------|-----|------------|
| n8n | 185.221.214.83 | Workflow automation |
| Neo4j | 45.144.177.128:7474 | Graph database |
| PostgreSQL | 185.221.214.83:6544 | Main database |
| API | 45.144.177.128 | Backend API |

---

## GitHub

- Репозиторий: https://github.com/n8nRemacs/Eldoleado

---

## Для продолжения завтра

1. Прочитать `Start.md`
2. Импортировать workflows из `workflows_to_import/` в n8n
3. Продолжить с механикой merge клиентов
