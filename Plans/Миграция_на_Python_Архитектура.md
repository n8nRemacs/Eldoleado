# BattCRM - Архитектура миграции на Python

## Дата: 2 декабря 2025

---

## Проблема

Текущая система на n8n не справится с целевой нагрузкой:

| Метрика | Требуется | n8n (текущий) | Дефицит |
|---------|-----------|---------------|---------|
| **Среднее** | 33 msg/sec | ~1-3 msg/sec | 10-30x |
| **Peak** | 100-170 msg/sec | ~3-5 msg/sec | 30-50x |

### Расчёт нагрузки:
```
1000 клиентов × 30 диалогов × 40 сообщений = 1,200,000 сообщений / 10 часов
1,200,000 / (10 × 3600) = 33.3 msg/sec (среднее)
Peak (×3-5): 100-170 msg/sec
```

### Узкие места n8n:
- AI Worker poll: 5 sec (задача ждёт до 5 сек)
- Task Dispatcher poll: 300ms overhead
- Max 7 AI workers
- OpenAI latency: 500-1500ms

---

## Архитектура слоёв

```
═══════════════════════════════════════════════════════════════════════════
                         CORE PIPELINE (обработка сообщений)
═══════════════════════════════════════════════════════════════════════════

┌─────────────────────────────────────────────────────────────────────────┐
│  TRANSPORT → RESOLUTION → EXTRACTION → GRAPH → RESPONSE → TRANSPORT    │
└─────────────────────────────────────────────────────────────────────────┘


═══════════════════════════════════════════════════════════════════════════
                         НЕЗАВИСИМЫЕ СЛОИ
═══════════════════════════════════════════════════════════════════════════

┌─────────────────────────────────────────────────────────────────────────┐
│                      CRM / TOUCHES LAYER                                 │
│              Воронка, касания, автоматизации                             │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                       MARKETING LAYER                                    │
│           Рекламные кабинеты, постинг, аудитории                         │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                    CRM INTEGRATIONS LAYER                                │
│                  AMO, Bitrix, HubSpot, Pipedrive                         │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                  ACCOUNTING INTEGRATIONS LAYER                           │
│            МойСклад, LiveSklad, RetailCRM, Remonline                     │
└─────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────┐
│                           API LAYER                                      │
│                Android / Operator / Admin / Public                       │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Описание слоёв

### 1. TRANSPORT LAYER
IN/OUT адаптеры мессенджеров. Один блок на всех tenant.

```
┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐
│Telegram │ │   VK    │ │WhatsApp │ │  Avito  │ │   MAX   │
│Adapter  │ │Adapter  │ │Adapter  │ │Adapter  │ │Adapter  │
└─────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘
                              │
                    Unified Message Format
```

### 2. RESOLUTION LAYER
Tenant → Client → Appeal (создать/найти)

### 3. EXTRACTION LAYER
AI извлечение сущностей из текста:
- Тип обращения
- Бренд/Модель
- Тип ремонта
- Чья запчасть

### 4. GRAPH LAYER
Neo4j - связи, контекст, рекомендации:
- История клиента
- Похожие обращения
- Cross-sell рекомендации
- Оценка стоимости

### 5. RESPONSE LAYER
Генерация ответа + выбор действия (auto/operator)

### 6. CRM / TOUCHES LAYER
Независимый слой, работает на событиях:
- **Funnel** - стадии воронки (без UI канбана)
- **Touches** - отложенные касания по времени
- **Automations** - мгновенные реакции на события

Конфигурируется per tenant (конструктор).
Кому нужен канбан → интеграция с AMO/Bitrix.

### 7. MARKETING LAYER
Рекламные платформы и маркетинг:
- **Ad Platforms** - VK Ads, Яндекс.Директ, Google Ads, Meta, MyTarget
- **Posting** - публикации в соцсети, отложенный постинг
- **Audiences** - сбор, сегментация, look-alike, ретаргетинг
- **Analytics** - UTM, конверсии, ROI, атрибуция

### 8. CRM INTEGRATIONS LAYER
Интеграции с CRM системами:
- AMO CRM
- Bitrix24
- HubSpot
- Pipedrive

Направление: Мы → CRM (push сделки, контакты)

### 9. ACCOUNTING INTEGRATIONS LAYER
Интеграции с учётными системами:
- МойСклад
- LiveSklad
- RetailCRM
- Remonline

Направление:
- Учёт → Мы (pull справочники, цены, остатки)
- Мы → Учёт (push заказы на ремонт)

### 10. API LAYER
HTTP endpoints:
- Android API
- Operator API
- Admin API
- Public API

Версионирование (v1, v2...), rate limiting, auth.

---

## Структура проекта

```
battcrm-core/
├── src/
│   │
│   │   # ══════ CORE PIPELINE ══════
│   ├── transport/              # IN/OUT мессенджеры
│   │   ├── base.py
│   │   ├── telegram.py
│   │   ├── vk.py
│   │   ├── whatsapp.py
│   │   ├── avito.py
│   │   └── max.py
│   │
│   ├── resolution/             # Tenant/Client/Appeal
│   │   ├── tenant.py
│   │   ├── client.py
│   │   └── appeal.py
│   │
│   ├── extraction/             # AI извлечение
│   │   ├── dispatcher.py
│   │   ├── worker.py
│   │   ├── tools/
│   │   │   ├── type_tool.py
│   │   │   ├── model_tool.py
│   │   │   ├── repair_tool.py
│   │   │   └── parts_tool.py
│   │   └── prompts/
│   │
│   ├── graph/                  # Neo4j
│   │   ├── client.py
│   │   ├── appeal.py
│   │   ├── pricing.py
│   │   ├── recommendations.py
│   │   └── sync.py
│   │
│   ├── response/               # Генерация ответов
│   │   ├── generator.py
│   │   ├── router.py
│   │   └── templates/
│   │
│   │   # ══════ НЕЗАВИСИМЫЕ СЛОИ ══════
│   ├── crm/                    # Воронка, касания
│   │   ├── funnel/
│   │   ├── touches/
│   │   ├── automations/
│   │   ├── constructor/
│   │   └── event_handler.py
│   │
│   ├── marketing/              # Реклама, постинг
│   │   ├── platforms/
│   │   ├── posting/
│   │   ├── audiences/
│   │   ├── analytics/
│   │   ├── campaigns/
│   │   └── forms/
│   │
│   ├── integrations_crm/       # AMO, Bitrix...
│   │   ├── base.py
│   │   ├── amo.py
│   │   ├── bitrix.py
│   │   ├── hubspot.py
│   │   └── pipedrive.py
│   │
│   ├── integrations_accounting/# МойСклад, Remonline...
│   │   ├── base.py
│   │   ├── moysklad.py
│   │   ├── livesklad.py
│   │   ├── retailcrm.py
│   │   └── remonline.py
│   │
│   ├── api/                    # HTTP API
│   │   ├── deps.py
│   │   ├── v1/
│   │   │   ├── android/
│   │   │   ├── operator/
│   │   │   ├── admin/
│   │   │   └── public/
│   │   └── v2/
│   │
│   │   # ══════ ОБЩЕЕ ══════
│   ├── events/                 # Event Bus
│   │   ├── bus.py
│   │   ├── models.py
│   │   └── handlers.py
│   │
│   ├── models/                 # Pydantic/SQLAlchemy
│   │   ├── message.py
│   │   ├── client.py
│   │   ├── appeal.py
│   │   └── tenant.py
│   │
│   └── infra/                  # Инфраструктура
│       ├── redis.py
│       ├── postgres.py
│       ├── neo4j.py
│       └── openai.py
│
├── workers/                    # Отдельные процессы
│   ├── ai_worker.py
│   ├── touch_scheduler.py
│   ├── posting_scheduler.py
│   ├── crm_sync.py
│   ├── accounting_sync.py
│   └── event_processor.py
│
├── docker-compose.yml
└── pyproject.toml
```

---

## Порядок миграции

| Фаза | Слой | Приоритет | Эффект | Риск |
|------|------|-----------|--------|------|
| **1** | `infra/` + `events/` | Критично | База для всего | Низкий |
| **2** | `extraction/` | Критично | +10-30x throughput | Минимальный |
| **3** | `resolution/` | Высокий | Упрощение | Средний |
| **4** | `graph/` | Средний | Контекст | Низкий |
| **5** | `response/` | Средний | Генерация | Низкий |
| **6** | `transport/` | Средний | Полный контроль | Средний |
| **7** | `crm/` | Параллельно | Касания | Низкий |
| **8** | `marketing/` | Параллельно | Реклама | Низкий |
| **9** | `integrations_crm/` | Параллельно | AMO/Bitrix | Низкий |
| **10** | `integrations_accounting/` | Параллельно | МойСклад | Низкий |
| **11** | `api/` | В конце | HTTP endpoints | Низкий |

---

## Фаза 1: AI Workers (главный bottleneck)

### Что делаем:
Python worker читает из `ai_extraction_queue` (Redis), вызывает OpenAI, сохраняет в PostgreSQL.
n8n Task Dispatcher продолжает работать - просто добавляем workers.

### Ключевой код:

```python
# workers/ai_worker.py

async def main():
    redis = await aioredis.from_url(REDIS_URL)
    pg_pool = await asyncpg.create_pool(DATABASE_URL)
    openai = AsyncOpenAI()

    while True:
        # Блокирующий pop - без polling!
        _, task_json = await redis.brpop('ai_extraction_queue')
        task = json.loads(task_json)

        # AI extraction
        result = await extract(openai, task)

        # Save to same table n8n uses
        await save_result(pg_pool, task['id'], result)
```

### Docker Compose:

```yaml
services:
  ai-worker:
    build: .
    deploy:
      replicas: 50
    environment:
      - REDIS_URL=redis://redis:6379
      - DATABASE_URL=postgresql://...
      - OPENAI_API_KEY=...
```

### Выигрыш:
- **Latency**: 5 sec → instant (brpop вместо polling)
- **Workers**: 7 → 50-100
- **Throughput**: 3 msg/sec → 50+ msg/sec

---

## Ресурсы для 170 msg/sec peak

| Компонент | Instances | vCPU | RAM |
|-----------|-----------|------|-----|
| Redis | 1 | 2 | 4GB |
| Postgres | 1 + replica | 4 | 8GB |
| IN Gateway | 2 | 1 | 512MB |
| Message Processor | 10 | 0.5 | 256MB |
| AI Worker | 100 | 0.25 | 256MB |
| OUT Worker | 25 | 0.25 | 128MB |
| **Total** | | ~40 vCPU | ~40GB |

**Стоимость**: ~$200-300/мес cloud или $100-150/мес dedicated.

---

## Event Bus

Все независимые слои работают через события:

```
                    Message Flow (real-time)
                           │
                           ▼
┌────────────────────────────────────────┐
│  Transport → Resolution → Extraction   │
│       → Graph → Response → Transport   │
└────────────────────────────────────────┘
                           │
                           │ Events (async)
                           ▼
┌────────────────────────────────────────┐
│            EVENT BUS (Redis)           │
│                                        │
│  appeal.created                        │
│  appeal.stage_changed                  │
│  message.received                      │
│  message.sent                          │
│  client.created                        │
└────────────────────────────────────────┘
                           │
       ┌───────────────────┼───────────────────┐
       ▼                   ▼                   ▼
┌────────────┐      ┌────────────┐      ┌────────────┐
│CRM/Touches │      │ Marketing  │      │Integrations│
└────────────┘      └────────────┘      └────────────┘
```

---

## Ключевые библиотеки

```python
# Core
fastapi          # HTTP Gateway
redis[hiredis]   # Queue + cache + pub/sub
asyncpg          # Async Postgres
httpx            # Async HTTP clients

# AI
openai           # OpenAI API (async)
litellm          # Multi-provider LLM

# Graph
neo4j            # Neo4j driver

# Infra
celery           # Task queue (альтернатива)
prometheus_client # Метрики
structlog        # Логирование
```

---

## Принципы архитектуры

1. **Transport отдельно от логики** - меняется API мессенджера, не меняем core
2. **Unified Message Format** - единый формат внутри системы
3. **Event-driven** - независимые слои через события
4. **Per-tenant config** - конструктор для каждого клиента
5. **Horizontal scaling** - AI workers масштабируются горизонтально
6. **Graceful migration** - постепенный перенос с n8n без остановки
