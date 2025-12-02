# Eldoleado: Спецификация графовой архитектуры

## 1. Обзор

### Суть системы

Мультиконтекстный диалоговый движок для работы со сложными клиентскими обращениями. Клиент может упоминать несколько устройств разных людей в одном диалоге, прыгать между темами, возвращаться через дни/недели.

### Ключевые принципы

1. **Граф = контекст** — контекст не хранится отдельно, а вычисляется из связей в графовой БД
2. **Touchpoint (точка касания)** — универсальный узел для любого взаимодействия
3. **Два уровня видимости:** вертикаль видит своё, ядро видит всё
4. **Единая структура для всех вертикалей** — настройки через метаданные

### Технологический стек

- **Neo4j** — графовая БД для связей и навигации
- **Supabase (PostgreSQL)** — детальные данные
- **UUID** — общий идентификатор между базами

---

## 2. Архитектура: два слоя данных

### Принцип разделения

| Neo4j (граф) | Supabase (SQL) |
|--------------|----------------|
| Связи, навигация | Детальные данные |
| Кто с кем связан | Полные атрибуты |
| Быстрый контекст | Тяжёлые данные (фото, тексты) |
| Миллисекунды | Полнота |

### Когда что использовать

| Задача | База | Почему |
|--------|------|--------|
| Найти клиента по телефону | SQL | Точный поиск по индексу |
| Найти устройства клиента | Граф | Навигация по связям |
| Disambiguation | Граф | Нужны связи + время упоминания |
| Детали устройства (IMEI, фото) | SQL | Тяжёлые данные |
| История касаний | Граф | Цепочки связей |
| Полный текст сообщения | SQL | Большие данные |
| Социальные связи | Граф | Рёбра между клиентами |
| Аналитика по вертикали | SQL | Агрегаты, GROUP BY |

---

## 3. Вершины (узлы графа)

### 3.1. Client (Клиент)

**В графе:**
```
Client {
  id: UUID,
  created_at: datetime
}
```

**В SQL:**
```sql
clients (
  id UUID PRIMARY KEY,
  phone TEXT,
  whatsapp TEXT,
  telegram TEXT,
  email TEXT,
  name TEXT,
  fingerprint TEXT,
  notes TEXT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
)
```

---

### 3.2. Device (Устройство/Объект)

**В графе:**
```
Device {
  id: UUID,
  type: TEXT,            -- "phone", "laptop", "shoes", "car"
  brand: TEXT,           -- "Apple", "Samsung", "BMW"
  model: TEXT,           -- "iPhone 12", "Galaxy S21"
  owner_label: TEXT      -- "свой", "сына", "жены", "рабочий"
}
```

**В SQL:**
```sql
devices (
  id UUID PRIMARY KEY,
  client_id UUID,
  type TEXT,
  brand TEXT,
  model TEXT,
  owner_label TEXT,
  serial_number TEXT,
  imei TEXT,
  color TEXT,
  storage TEXT,
  condition TEXT,
  photos JSONB,
  notes TEXT,
  attributes JSONB DEFAULT '{}',  -- расширяемые поля по вертикали
  created_at TIMESTAMP,
  updated_at TIMESTAMP
)
```

**Примечание:** Пара `brand + model` — идентификатор модели. Пара `brand + model + owner_label` — для disambiguation между устройствами клиента.

---

### 3.3. Problem (Поломка/Услуга)

**В графе:**
```
Problem {
  id: UUID,
  status: TEXT           -- "new", "in_progress", "completed", "cancelled"
}
```

**В SQL:**
```sql
problems (
  id UUID PRIMARY KEY,
  device_id UUID,
  problem_type_id UUID,
  status TEXT,
  description TEXT,
  diagnosis TEXT,
  price DECIMAL,
  parts_cost DECIMAL,
  parts_info JSONB,
  warranty_until DATE,
  completed_at TIMESTAMP,
  notes TEXT,
  attributes JSONB DEFAULT '{}',
  created_at TIMESTAMP,
  updated_at TIMESTAMP
)
```

---

### 3.4. ProblemType (Тип поломки)

**В графе:**
```
ProblemType {
  id: UUID,
  code: TEXT,            -- "display", "battery", "charging"
  name: TEXT             -- "Дисплей", "Батарея", "Разъём зарядки"
}
```

**В SQL:**
```sql
problem_types (
  id UUID PRIMARY KEY,
  vertical_type TEXT,
  code TEXT,
  name TEXT,
  icon TEXT,
  default_price DECIMAL,
  attributes_schema JSONB,
  created_at TIMESTAMP
)
```

---

### 3.5. Touchpoint (Точка касания)

**В графе:**
```
Touchpoint {
  id: UUID,
  timestamp: datetime,
  type: TEXT,            -- "message", "call", "visit", "retargeting", "email"
  channel: TEXT,         -- "whatsapp", "telegram", "phone", "in_person", "vk_ads"
  direction: TEXT        -- "inbound", "outbound"
}
```

**В SQL:**
```sql
touchpoints (
  id UUID PRIMARY KEY,
  client_id UUID,
  type TEXT,
  channel TEXT,
  direction TEXT,

  -- Контент
  content TEXT,
  media JSONB,
  raw_payload JSONB,

  -- Для звонков
  call_duration INTEGER,
  call_recording_url TEXT,

  -- Для ретаргетинга
  campaign_id TEXT,
  impressions INTEGER,
  clicks INTEGER,

  -- Мета
  processed BOOLEAN,
  entities_extracted JSONB,

  created_at TIMESTAMP
)
```

---

### 3.6. Vertical (Вертикаль/Бизнес)

**В графе:**
```
Vertical {
  id: UUID,
  type: TEXT,            -- "phone_repair", "beauty", "auto_service"
  name: TEXT             -- "Ремонт iPhone Москва"
}
```

**В SQL:**
```sql
verticals (
  id UUID PRIMARY KEY,
  type TEXT,
  name TEXT,
  owner_id UUID,
  settings JSONB,
  subscription TEXT,
  created_at TIMESTAMP
)
```

---

### 3.7. Channel (Канал связи)

**В графе:**
```
Channel {
  id: UUID,
  type: TEXT,            -- "whatsapp", "telegram", "phone", "email", "fingerprint"
  identifier: TEXT       -- номер/username/email/hash
}
```

**В SQL:**
```sql
channels (
  id UUID PRIMARY KEY,
  client_id UUID,
  type TEXT,
  identifier TEXT,
  verified BOOLEAN,
  primary_channel BOOLEAN,
  created_at TIMESTAMP
)
```

---

### 3.8. Fingerprint (Слепок браузера)

**В графе:**
```
Fingerprint {
  id: UUID,
  hash: TEXT
}
```

**В SQL:**
```sql
fingerprints (
  id UUID PRIMARY KEY,
  client_id UUID,
  hash TEXT,
  device_info JSONB,     -- screen, browser, OS, GPU
  first_seen TIMESTAMP,
  last_seen TIMESTAMP
)
```

---

## 4. Рёбра (связи)

### 4.1. Сводная таблица рёбер

| Ребро | От → К | Атрибуты | Описание |
|-------|--------|----------|----------|
| OWNS | Client → Device | since, primary | Клиент владеет устройством |
| HAS_PROBLEM | Device → Problem | created_at, resolved_at | У устройства есть поломка |
| OF_TYPE | Problem → ProblemType | — | Поломка относится к типу |
| SOCIAL | Client → Client | type, label | Социальная связь (семья/друг/коллега) |
| REFERRED | Client → Client | date, source | Кто кого привёл (реферал) |
| HAS_CHANNEL | Client → Channel | verified, primary | У клиента есть канал связи |
| IDENTIFIED_BY | Client → Fingerprint | — | Клиент идентифицирован по fingerprint |
| CUSTOMER_OF | Client → Vertical | since, last_active, status | Клиент является клиентом вертикали |
| FROM | Touchpoint → Client | — | Точка касания от клиента (входящее) |
| TO | Touchpoint → Client | — | Точка касания к клиенту (исходящее) |
| ABOUT_DEVICE | Touchpoint → Device | confidence, explicit | В сообщении упоминается устройство |
| ABOUT_PROBLEM | Touchpoint → Problem | confidence, explicit | В сообщении обсуждается поломка |
| REFERS_TO | Touchpoint → Touchpoint | type | Ссылка на предыдущее сообщение |
| VIA_CHANNEL | Touchpoint → Channel | — | Через какой канал произошло касание |
| IN_VERTICAL | Touchpoint → Vertical | — | В рамках какой вертикали |

---

### 4.2. Подробное описание рёбер

**OWNS (Client → Device)**
```cypher
(Client)-[:OWNS {
  since: datetime,
  primary: boolean
}]->(Device)
```
Клиент владеет устройством. Основная связь собственности.

**HAS_PROBLEM (Device → Problem)**
```cypher
(Device)-[:HAS_PROBLEM {
  created_at: datetime,
  resolved_at: datetime
}]->(Problem)
```
У устройства есть поломка/запрос на услугу.

**OF_TYPE (Problem → ProblemType)**
```cypher
(Problem)-[:OF_TYPE]->(ProblemType)
```
Поломка относится к типу. Позволяет группировать и искать по категории.

**SOCIAL (Client → Client)**
```cypher
(Client)-[:SOCIAL {
  type: "семья",          -- семья/друг/коллега
  label: "сын"            -- конкретная роль
}]->(Client)
```
Социальная связь между людьми. Позволяет понимать контекст "телефон сына".

**REFERRED (Client → Client)**
```cypher
(Client)-[:REFERRED {
  date: datetime,
  source: "промокод"
}]->(Client)
```
Реферальная связь. Детали (бонусы, статусы) — в SQL таблице `referrals`.

**HAS_CHANNEL (Client → Channel)**
```cypher
(Client)-[:HAS_CHANNEL {
  verified: boolean,
  primary: boolean
}]->(Channel)
```
У клиента есть канал связи.

**IDENTIFIED_BY (Client → Fingerprint)**
```cypher
(Client)-[:IDENTIFIED_BY]->(Fingerprint)
```
Клиент идентифицирован по слепку браузера.

**CUSTOMER_OF (Client → Vertical)**
```cypher
(Client)-[:CUSTOMER_OF {
  since: datetime,
  last_active: datetime,
  status: "active"
}]->(Vertical)
```
Клиент является клиентом вертикали.

**FROM / TO (Touchpoint → Client)**
```cypher
(Touchpoint)-[:FROM]->(Client)  // входящее
(Touchpoint)-[:TO]->(Client)    // исходящее
```
Направление касания.

**ABOUT_DEVICE (Touchpoint → Device)**
```cypher
(Touchpoint)-[:ABOUT_DEVICE {
  confidence: float,
  explicit: boolean
}]->(Device)
```
В сообщении упоминается устройство.

**ABOUT_PROBLEM (Touchpoint → Problem)**
```cypher
(Touchpoint)-[:ABOUT_PROBLEM {
  confidence: float,
  explicit: boolean
}]->(Problem)
```
В сообщении обсуждается поломка.

**REFERS_TO (Touchpoint → Touchpoint)**
```cypher
(Touchpoint)-[:REFERS_TO {
  type: "reply"
}]->(Touchpoint)
```
Ссылка на предыдущее сообщение (reply/quote/continuation).

**VIA_CHANNEL (Touchpoint → Channel)**
```cypher
(Touchpoint)-[:VIA_CHANNEL]->(Channel)
```
Через какой канал произошло касание.

**IN_VERTICAL (Touchpoint → Vertical)**
```cypher
(Touchpoint)-[:IN_VERTICAL]->(Vertical)
```
В рамках какой вертикали. Для фильтрации по уровням видимости.

---

## 5. Метаданные для вертикалей

### Настройки узлов по вертикалям

```sql
vertical_node_settings (
  id UUID PRIMARY KEY,
  vertical_type TEXT,        -- "phone_repair", "beauty"
  node_type TEXT,            -- "Device", "Problem", "Appointment"
  enabled BOOLEAN DEFAULT true,
  display_name TEXT,         -- "Устройство", "Автомобиль"
  icon TEXT,
  attributes_schema JSONB,
  created_at TIMESTAMP
)
```

### Настройки рёбер по вертикалям

```sql
vertical_edge_settings (
  id UUID PRIMARY KEY,
  vertical_type TEXT,
  edge_type TEXT,            -- "OWNS", "HAS_PROBLEM"
  from_node TEXT,
  to_node TEXT,
  enabled BOOLEAN DEFAULT true,
  display_name TEXT,
  attributes_schema JSONB,
  created_at TIMESTAMP
)
```

---

## 6. Граф обогащения профиля

### Концепция

Задача системы — провести клиента от анонимного касания до полного профиля.

### Таблица путей обогащения

```sql
enrichment_paths (
  id UUID PRIMARY KEY,
  from_channel_type TEXT,      -- "avito", "fingerprint", "whatsapp"
  to_channel_type TEXT,        -- "whatsapp", "telegram", "phone"
  mechanism TEXT,              -- "Напишите в WhatsApp: +7..."
  conversion_rate DECIMAL,     -- 0.30 (30%)
  gives_identifier TEXT[],     -- ["phone", "name"]
  vertical_type TEXT,
  enabled BOOLEAN DEFAULT true,
  priority INTEGER,
  created_at TIMESTAMP
)
```

### Карта переходов

| Из | В | Механизм | Конверсия | Даёт |
|----|---|----------|-----------|------|
| avito | whatsapp | "Напишите в WhatsApp для быстрого ответа" | 35% | phone, name |
| avito | call | Клиент сам звонит | 25% | phone |
| whatsapp | fingerprint | Ссылка на полезный контент | 50% | fingerprint |
| whatsapp | telegram | "В Telegram эксклюзивные акции" | 15% | tg_id |
| whatsapp | vk | "Наши работы в группе VK" | 12% | vk_id |
| fingerprint | phone | Лид-форма на сайте | 10% | phone, email |
| retargeting | fingerprint | Клик по баннеру | 2% | fingerprint |
| telegram | phone | Кнопка "Поделиться контактом" | 25% | phone |
| visit | any | Анкета, разговор | 60% | любые данные |

### Логика работы

1. **Оценка профиля:** какие каналы есть, каких нет
2. **Выбор действия:** по приоритету и конверсии
3. **Предложение:** система подсказывает оператору или автоматически
4. **Фиксация:** при переходе создаётся Touchpoint с типом "enrichment"

---

## 7. Disambiguation (уточнение)

### Когда уточнять

| Ситуация | Действие |
|----------|----------|
| 1 устройство у клиента | Не спрашивать |
| Сегодня упоминали только одно | Использовать его |
| Сегодня упоминали несколько | Спрашивать |
| Сегодня не упоминали ни одно | Спрашивать |
| Клиент назвал owner_label | Фильтровать по label |

### Уровни уверенности

| Когда упоминали | Уверенность | Формат ответа |
|-----------------|-------------|---------------|
| < 1 часа назад | Высокая | Без уточнения |
| Сегодня | Средняя | Мягкое ("вы про X, верно?") |
| Вчера | Низкая | Мягкое уточнение |
| Давно / никогда | Нет | Явный вопрос |

### Запрос для disambiguation

```cypher
MATCH (c:Client {id: $clientId})-[:OWNS]->(d:Device)
OPTIONAL MATCH (t:Touchpoint)-[:ABOUT_DEVICE]->(d)
WHERE t.timestamp > datetime() - duration('P1D')
RETURN d, max(t.timestamp) as last_mentioned
ORDER BY last_mentioned DESC
```

### Резервные эвристики

Если базовый алгоритм недостаточен, попробовать:
- Приоритет устройства с активным ремонтом (статус in_progress)
- Приоритет устройства, которое чаще упоминают (частотность)
- Приоритет по давности последней проблемы
- Вес канала (сообщение важнее ретаргетинга)

---

## 8. Интеграция Neo4j + Supabase

### Связь между базами

Общий UUID на каждую сущность:
```
Neo4j:  (Client {id: "550e8400-..."})
SQL:    clients (id: "550e8400-...")
```

### Паттерн запросов

**Шаг 1: Граф — получить структуру и ID**
```cypher
MATCH (c:Client {id: $clientId})-[:OWNS]->(d:Device)
OPTIONAL MATCH (d)-[:HAS_PROBLEM]->(p:Problem {status: "in_progress"})
RETURN d.id, d.brand, d.model, d.owner_label, p.id
```

**Шаг 2: SQL — получить детали**
```sql
SELECT * FROM devices WHERE id IN ('uuid-1', 'uuid-2');
SELECT * FROM problems WHERE id = 'uuid-p1';
```

### Синхронизация

**Создание:** пишем в обе базы (SQL → Neo4j)
**Обновление:** SQL всегда, Neo4j только если изменились поля графа
**Удаление:** Neo4j (DETACH DELETE) → SQL

### Транзакционность

Saga паттерн с компенсацией:
```typescript
try {
  await supabase.insert(...);
  try {
    await neo4j.create(...);
  } catch {
    await supabase.delete(...);  // откат
    throw error;
  }
}
```

Альтернатива: Outbox паттерн через таблицу событий.

### Индексы

**Neo4j:**
```cypher
CREATE INDEX client_id FOR (c:Client) ON (c.id);
CREATE INDEX device_id FOR (d:Device) ON (d.id);
CREATE INDEX touchpoint_timestamp FOR (t:Touchpoint) ON (t.timestamp);
CREATE INDEX device_model FOR (d:Device) ON (d.brand, d.model);
```

**SQL:**
```sql
CREATE INDEX idx_clients_phone ON clients(phone);
CREATE INDEX idx_devices_client ON devices(client_id);
CREATE INDEX idx_touchpoints_client ON touchpoints(client_id);
CREATE INDEX idx_touchpoints_timestamp ON touchpoints(created_at);
```

---

## 9. Парсинг сообщений

### Схема обработки

```
[Сообщение] → [Контекст из графа] → [LLM парсер] → [Сущности] → [Матчинг] → [Граф]
```

### Контекст для LLM

```typescript
async function getParsingContext(clientId: string) {
  return await neo4j.run(`
    MATCH (c:Client {id: $clientId})-[:OWNS]->(d:Device)
    OPTIONAL MATCH (d)-[:HAS_PROBLEM]->(p:Problem)-[:OF_TYPE]->(pt:ProblemType)
    OPTIONAL MATCH (t:Touchpoint)-[:ABOUT_DEVICE]->(d)
    WHERE t.timestamp > datetime() - duration('P7D')
    RETURN d.id, d.brand, d.model, d.owner_label,
           p.id, p.status, pt.code,
           max(t.timestamp) as lastMentioned
  `, { clientId });
}
```

### Формат ответа LLM

```json
{
  "device": {
    "matched_id": "uuid-2",
    "brand": "Apple",
    "model": "iPhone 12",
    "owner_label": "сына",
    "confidence": 0.95,
    "is_new": false
  },
  "problem": {
    "type": "battery",
    "description": "батарея вздулась",
    "is_new": true
  },
  "intent": "price_check",
  "needs_clarification": false
}
```

### Типы намерений (intent)

| Intent | Описание |
|--------|----------|
| price_check | Узнать цену |
| repair_request | Хочет ремонт |
| status_check | Статус ремонта |
| question | Общий вопрос |
| complaint | Жалоба |
| gratitude | Благодарность |

---

## 10. Уровни видимости

### Архитектура доступа

```
┌─────────────────────────────────────────────────────────────┐
│                      ЯДРО ELDOLEADO                          │
│         Видит ВСЁ: все вертикали, все связи                 │
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │ Вертикаль A │  │ Вертикаль B │  │ Вертикаль C │         │
│  │ Видит своё  │  │ Видит своё  │  │ Видит своё  │         │
│  └─────────────┘  └─────────────┘  └─────────────┘         │
└─────────────────────────────────────────────────────────────┘
```

### Запросы с фильтрацией

```cypher
// Запрос от вертикали — видит только своё
MATCH (c:Client)-[:CUSTOMER_OF]->(v:Vertical {id: $verticalId})
MATCH (t:Touchpoint)-[:IN_VERTICAL]->(v)
MATCH (t)-[:FROM|TO]->(c)
RETURN c, t

// Запрос от ядра — видит всё
MATCH (c:Client)
OPTIONAL MATCH (c)-[:CUSTOMER_OF]->(v:Vertical)
OPTIONAL MATCH (t:Touchpoint)-[:FROM|TO]->(c)
RETURN c, v, t
```

---

## 11. Социальные связи и рефералы

### Типы связей

| Тип | Ребро | Атрибуты |
|-----|-------|----------|
| Социальная | SOCIAL | type (семья/друг/коллега), label (сын/жена) |
| Реферальная | REFERRED | date, source |

### Реферальная система в SQL

```sql
referrals (
  id UUID PRIMARY KEY,
  referrer_id UUID,
  referred_id UUID,
  program TEXT,
  reward DECIMAL,
  status TEXT,         -- pending/paid/cancelled
  created_at TIMESTAMP
)
```

Граф знает связь, SQL знает детали выплат.

---

## 12. Расширяемость

### Добавление новых вершин

1. **Граф:** CREATE + связи — 5 минут
2. **SQL:** таблица + JSONB attributes — 10 минут
3. **Метаданные:** строки в vertical_node_settings
4. **Код:** модуль + обновить парсер — 1-2 часа

### Что легко изменить

- Добавить вершину
- Добавить ребро
- Добавить атрибут
- Новая таблица SQL

### Что сложно изменить

- Изменить структуру связей между существующими
- Разделить одну вершину на две
- Переименовать/удалить атрибут с данными
- Миграция данных между таблицами

---

## 13. Следующие шаги

1. ✅ Проектирование схемы графа
2. ✅ Проектирование таблиц SQL
3. ✅ Алгоритм disambiguation
4. ✅ Интеграция Neo4j + Supabase
5. ✅ Алгоритм парсинга сообщений
6. ⬜ Установка Neo4j на сервер
7. ⬜ Реализация базовых CRUD операций
8. ⬜ Интеграция с мессенджерами
9. ⬜ Интерфейс оператора

---

*Документ создан: декабрь 2025*
*Версия: 1.0*
*Проект: Eldoleado — AI-powered CRM для микробизнеса*
