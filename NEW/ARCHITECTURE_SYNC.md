# Синхронизация Roadmap с Архитектурой CORE_NEW

> Маппинг killer features на 7 уровней AI архитектуры

**Последнее обновление:** 2025-12-10

---

## Обзор: 7 уровней + Новые блоки

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                         НОВЫЕ БЛОКИ (из roadmap)                             │
│                                                                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │   PRICE     │  │   DEVICE    │  │  EXTERNAL   │  │  LEARNING   │         │
│  │   ENGINE    │  │   GATEWAY   │  │  INTEGR.    │  │    ENGINE   │         │
│  │             │  │             │  │             │  │             │         │
│  │ Парсер цен  │  │ Смартфон-   │  │ LiveSklad   │  │ Самообучение│         │
│  │ Нормализ.   │  │ сервер      │  │ Remonline   │  │ Фидбек      │         │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘         │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                     7 УРОВНЕЙ CORE_NEW (существующие)                        │
│                                                                              │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ УРОВЕНЬ 7: MCP КАНАЛЫ                                                  │ │
│  │ + Telegram, WhatsApp, VK, Avito, MAX, Form, Phone                      │ │
│  │ + [NEW] Device Gateway (смартфон-сервер)                               │ │
│  │ + [NEW] QR Entry Point                                                 │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ УРОВЕНЬ 6: RESPONSE BUILDER                                            │ │
│  │ + Форматирование под канал                                             │ │
│  │ + [NEW] Голосовой ответ (TTS для оператора)                            │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ УРОВЕНЬ 5: DIALOG ENGINE                                               │ │
│  │ + Сохранение в elo_dialogs                                             │ │
│  │ + Sync в Neo4j                                                         │ │
│  │ + [NEW] Sync в LiveSklad/Remonline (External Integrations)             │ │
│  │ + [NEW] Feedback Loop (Learning Engine)                                │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ УРОВЕНЬ 4: UNIVERSAL TOOLS                                             │ │
│  │ + device_extract, issue_extract, intent_classify                       │ │
│  │ + [NEW] price_lookup_live (из Price Engine)                            │ │
│  │ + [NEW] voice_transcribe (Whisper)                                     │ │
│  │ + [NEW] knowledge_lookup (предписанные ответы тенанта)                 │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ УРОВЕНЬ 3: ORCHESTRATOR                                                │ │
│  │ + Слепой исполнитель                                                   │ │
│  │ + [UNCHANGED]                                                          │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ УРОВЕНЬ 2: REQUEST BUILDER                                             │ │
│  │ + Кнут-Пряник-Кнут                                                     │ │
│  │ + [NEW] Подключение Price Engine tools                                 │ │
│  │ + [NEW] Подключение Knowledge tools                                    │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ УРОВЕНЬ 1: CONTEXT BUILDER                                             │ │
│  │ + PostgreSQL + Neo4j                                                   │ │
│  │ + [NEW] QR → client_id resolution                                      │ │
│  │ + [NEW] Phone call context (транскрипт → граф)                         │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ УРОВЕНЬ 0: ДАННЫЕ                                                      │ │
│  │ + PostgreSQL (elo_* таблицы)                                           │ │
│  │ + Neo4j (Client, Device, Problem)                                      │ │
│  │ + [NEW] Price Engine DB (нормализованные цены)                         │ │
│  │ + [NEW] Knowledge Base (предписанные ответы)                           │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Детальный маппинг Killer Features

### 1. Смартфон-сервер (Device Gateway)

**Уровень:** 7 (MCP Каналы) + Новый блок

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                         DEVICE GATEWAY                                       │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  Android App (смартфон тенанта)                                      │   │
│  │  ├── РЕЖИМ ОПЕРАТОР (всегда)                                         │   │
│  │  │   └── Push, ответы, история                                       │   │
│  │  │                                                                    │   │
│  │  └── РЕЖИМ СЕРВЕР (по флагу)                                         │   │
│  │      ├── Foreground Service                                          │   │
│  │      ├── Local API Server                                            │   │
│  │      └── WebSocket → ELO Backend                                     │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  ELO Backend (маршрутизатор)                                         │   │
│  │                                                                       │   │
│  │  channel_router(tenant, channel, message):                           │   │
│  │    if tenant.device_gateway_enabled AND device.is_online:            │   │
│  │        → route_via_device(device_id, message)                        │   │
│  │    else:                                                              │   │
│  │        → route_via_paid_api(api_keys, message)                       │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  Модули на устройстве                                                │   │
│  │  ├── WhatsApp Module (reverse-engineered)                            │   │
│  │  ├── Avito Module (reverse-engineered)                               │   │
│  │  └── MAX Module (reverse-engineered)                                 │   │
│  │                                                                       │   │
│  │  Входящие: Webhook Listener → WebSocket → Backend                    │   │
│  │  Исходящие: Backend → WebSocket → Local API → Channel                │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘

Интеграция с существующими MCP:

  УРОВЕНЬ 7 (MCP Каналы):
  ├── mcp-telegram     (как есть, бесплатно)
  ├── mcp-whatsapp     (Wappi 600₽ ИЛИ Device Gateway 0₽)
  ├── mcp-avito        (API 1500₽ ИЛИ Device Gateway 0₽)
  ├── mcp-vk           (как есть, бесплатно)
  ├── mcp-max          (API ИЛИ Device Gateway 0₽)
  └── mcp-device-gw    [NEW] — маршрутизатор на смартфоны
```

**Новые таблицы:**

```sql
-- Устройства-серверы тенантов
CREATE TABLE elo_device_gateways (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  device_id VARCHAR,           -- Android device ID
  push_token VARCHAR,          -- FCM token

  is_online BOOLEAN DEFAULT false,
  last_seen TIMESTAMPTZ,

  -- Какие каналы активны на устройстве
  channels JSONB,              -- {whatsapp: true, avito: true, max: false}

  -- Статистика
  messages_routed INT DEFAULT 0,

  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 2. Прайс-парсер + Автоцены (Price Engine)

**Уровень:** 0 (Данные) + 4 (Universal Tools) + Новый блок

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                          PRICE ENGINE                                        │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  ПАРСЕР (фоновый процесс)                                            │   │
│  │                                                                       │   │
│  │  Источники:                                                          │   │
│  │  ├── mobileboost.ru                                                  │   │
│  │  ├── all-spares.ru                                                   │   │
│  │  ├── gsm-komplekt.ru                                                 │   │
│  │  └── + другие по конфигу                                             │   │
│  │                                                                       │   │
│  │  Расписание: ежедневно 3:00 AM                                       │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  НОРМАЛИЗАТОР                                                        │   │
│  │                                                                       │   │
│  │  Входные данные (сырые):                                             │   │
│  │  "Дисплей айфон 12 копия олед черный"                                │   │
│  │  "Display iPhone 12 OLED Copy Black"                                 │   │
│  │  "дисп. ip12 oled коп."                                              │   │
│  │                                                                       │   │
│  │  Справочники:                                                        │   │
│  │  ├── elo_device_models (бренд, модель, алиасы)                       │   │
│  │  ├── elo_part_types (дисплей, акб, шлейф, алиасы)                    │   │
│  │  ├── elo_part_qualities (original, oem, copy, алиасы)                │   │
│  │  └── elo_colors (черный, белый, алиасы)                              │   │
│  │                                                                       │   │
│  │  Выходные данные (нормализованные):                                  │   │
│  │  {                                                                    │   │
│  │    part_type: "display",                                             │   │
│  │    brand: "Apple",                                                    │   │
│  │    model: "iPhone 12",                                                │   │
│  │    quality: "copy",                                                   │   │
│  │    subtype: "oled",                                                   │   │
│  │    color: "black",                                                    │   │
│  │    price: 2500,                                                       │   │
│  │    source: "mobileboost.ru",                                         │   │
│  │    parsed_at: "2025-12-10T03:15:00Z"                                 │   │
│  │  }                                                                    │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  ХРАНИЛИЩЕ ЦЕН                                                       │   │
│  │                                                                       │   │
│  │  elo_price_catalog:                                                  │   │
│  │  ├── Нормализованные записи                                          │   │
│  │  ├── История изменений                                               │   │
│  │  └── Средние цены по рынку                                           │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  UNIVERSAL TOOL: price_lookup_live                                   │   │
│  │                                                                       │   │
│  │  Вход: {brand: "Apple", model: "iPhone 12", issue: "display"}        │   │
│  │                                                                       │   │
│  │  Выход:                                                               │   │
│  │  {                                                                    │   │
│  │    prices: [                                                          │   │
│  │      {quality: "copy_lcd", price: 1800, source: "market_avg"},       │   │
│  │      {quality: "copy_oled", price: 2500, source: "market_avg"},      │   │
│  │      {quality: "original", price: 8500, source: "market_avg"}        │   │
│  │    ],                                                                 │   │
│  │    tenant_markup: 1.3,                                               │   │
│  │    final_prices: [2340, 3250, 11050]                                 │   │
│  │  }                                                                    │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Новые таблицы:**

```sql
-- Справочник моделей устройств
CREATE TABLE elo_device_models (
  id SERIAL PRIMARY KEY,
  brand VARCHAR NOT NULL,          -- Apple, Samsung
  model VARCHAR NOT NULL,          -- iPhone 12, Galaxy S23
  aliases VARCHAR[],               -- {ip12, айфон 12, 12ка}
  release_year INT,
  is_active BOOLEAN DEFAULT true
);

-- Справочник типов запчастей
CREATE TABLE elo_part_types (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,           -- display, battery, back_cover
  display_name VARCHAR,            -- Дисплей, АКБ, Задняя крышка
  aliases VARCHAR[],               -- {экран, стекло, дисп}
  subtypes VARCHAR[]               -- {lcd, oled, amoled}
);

-- Справочник качества запчастей
CREATE TABLE elo_part_qualities (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,           -- original, oem, copy
  display_name VARCHAR,            -- Оригинал, OEM, Копия
  aliases VARCHAR[],               -- {ориг, orig, оригинальный}
  rank INT                         -- 1=best, 3=cheapest
);

-- Каталог цен (нормализованный)
CREATE TABLE elo_price_catalog (
  id UUID PRIMARY KEY,

  -- Нормализованные поля
  device_model_id INT REFERENCES elo_device_models(id),
  part_type_id INT REFERENCES elo_part_types(id),
  part_quality_id INT REFERENCES elo_part_qualities(id),
  subtype VARCHAR,                 -- oled, lcd для display
  color VARCHAR,

  -- Цена
  price DECIMAL(10,2) NOT NULL,
  currency VARCHAR DEFAULT 'RUB',

  -- Источник
  source_url VARCHAR,
  source_name VARCHAR,             -- mobileboost.ru

  -- Метаданные
  raw_title VARCHAR,               -- Оригинальное название
  parsed_at TIMESTAMPTZ DEFAULT NOW(),
  is_active BOOLEAN DEFAULT true
);

-- Наценки тенантов
CREATE TABLE elo_tenant_pricing (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  -- Глобальная наценка
  global_markup DECIMAL(5,2) DEFAULT 1.3,  -- x1.3 = +30%

  -- Наценки по типам
  markup_by_type JSONB,            -- {display: 1.4, battery: 1.2}

  -- Фиксированные цены (переопределяют рыночные)
  fixed_prices JSONB,              -- {device_model_id: {part_type: price}}

  updated_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 3. Голос → Граф → Мессенджер

**Уровни:** 7 (Phone) → 4 (voice_transcribe) → 1 (Context Builder) → 5 (Dialog Engine) → 7 (Messenger)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                    ГОЛОС → ГРАФ → МЕССЕНДЖЕР                                │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ ЭТАП 1: ЗВОНОК (УРОВЕНЬ 7)                                           │   │
│  │                                                                       │   │
│  │  Источники:                                                          │   │
│  │  ├── Asterisk (своя АТС)                                             │   │
│  │  ├── Облачная АТС (Mango, Zadarma)                                   │   │
│  │  └── Смартфон-сервер (запись через mic)                              │   │
│  │                                                                       │   │
│  │  Выход: audio_file (wav/mp3) + call_metadata                         │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ ЭТАП 2: ТРАНСКРИБАЦИЯ (УРОВЕНЬ 4 - NEW TOOL)                         │   │
│  │                                                                       │   │
│  │  Tool: voice_transcribe                                              │   │
│  │  API: Whisper (OpenAI) / Whisper (self-hosted)                       │   │
│  │                                                                       │   │
│  │  Вход: {audio_url, language: "ru"}                                   │   │
│  │  Выход: {transcript: "...", segments: [...], duration: 45}           │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ ЭТАП 3: AI ИЗВЛЕЧЕНИЕ (УРОВНИ 3-4)                                   │   │
│  │                                                                       │   │
│  │  Стандартный пайплайн:                                               │   │
│  │  ├── device_extract → {brand, model}                                 │   │
│  │  ├── issue_extract → {category, description}                         │   │
│  │  ├── intent_classify → {intent: REPAIR}                              │   │
│  │  └── [NEW] appointment_extract → {date, time, notes}                 │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ ЭТАП 4: ЗАПИСЬ В ГРАФ (УРОВНИ 1, 5)                                  │   │
│  │                                                                       │   │
│  │  Context Builder: resolve client by phone number                     │   │
│  │  Dialog Engine: save + sync to Neo4j                                 │   │
│  │                                                                       │   │
│  │  Neo4j operations:                                                   │   │
│  │  ├── MERGE (Client) by phone                                         │   │
│  │  ├── MERGE (Device) if extracted                                     │   │
│  │  ├── MERGE (Problem) if extracted                                    │   │
│  │  ├── CREATE (Touchpoint {channel: phone, direction: inbound})        │   │
│  │  └── Link all with edges                                             │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ ЭТАП 5: ПРОДОЛЖЕНИЕ В МЕССЕНДЖЕРЕ (УРОВНИ 6-7)                       │   │
│  │                                                                       │   │
│  │  Response Builder генерирует follow-up:                              │   │
│  │  "Добрый день! Вы звонили по поводу {device},                        │   │
│  │   который {issue}. Ждём вас {appointment}.                           │   │
│  │   Напомнить за час до визита?"                                       │   │
│  │                                                                       │   │
│  │  Отправка через доступный канал:                                     │   │
│  │  ├── Telegram (если есть)                                            │   │
│  │  ├── WhatsApp (если есть)                                            │   │
│  │  └── SMS (fallback)                                                  │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Новые tools:**

```sql
INSERT INTO ai_tools (name, display_name, description, prompt_template, output_schema) VALUES
('voice_transcribe', 'Транскрибация голоса',
 'Преобразует аудио в текст',
 NULL,  -- не AI, внешний API
 '{"transcript": "string", "segments": "array", "duration": "number"}'
),
('appointment_extract', 'Извлечение записи',
 'Извлекает договорённости о визите из текста',
 'Извлеки из текста информацию о записи на визит. Дата, время, особые пожелания.',
 '{"date": "string", "time": "string", "notes": "string"}'
);
```

---

### 4. QR для идентификации клиента

**Уровни:** 7 (новый entry point) → 1 (Context Builder)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                       QR ИДЕНТИФИКАЦИЯ                                       │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  QR КОДЫ (генерация)                                                 │   │
│  │                                                                       │   │
│  │  Тип 1: На стойке/визитке                                            │   │
│  │  URL: https://elo.do/{tenant_code}                                   │   │
│  │  → Открывает deep link в Telegram/WhatsApp                           │   │
│  │  → Сообщение: /start {tenant_code}                                   │   │
│  │                                                                       │   │
│  │  Тип 2: На устройстве после ремонта                                  │   │
│  │  URL: https://elo.do/{tenant_code}/d/{device_id_short}               │   │
│  │  → Открывает чат с привязкой к устройству                            │   │
│  │  → Сообщение: /start {tenant_code}_{device_id}                       │   │
│  │                                                                       │   │
│  │  Тип 3: На чеке/квитанции                                            │   │
│  │  URL: https://elo.do/{tenant_code}/r/{repair_id_short}               │   │
│  │  → Открывает чат с историей ремонта                                  │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  CONTEXT BUILDER (расширение)                                        │   │
│  │                                                                       │   │
│  │  Вход: /start {code}                                                 │   │
│  │                                                                       │   │
│  │  Парсинг:                                                            │   │
│  │  ├── code = "ABC123"         → tenant only                           │   │
│  │  ├── code = "ABC123_D456"    → tenant + device                       │   │
│  │  └── code = "ABC123_R789"    → tenant + repair                       │   │
│  │                                                                       │   │
│  │  Резолв клиента:                                                     │   │
│  │  ├── По telegram_id/whatsapp_phone                                   │   │
│  │  ├── Если device_id → подгрузить историю устройства                  │   │
│  │  └── Если repair_id → подгрузить историю ремонта                     │   │
│  │                                                                       │   │
│  │  Выход: unified_context с предзаполненными данными                   │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Новая таблица:**

```sql
CREATE TABLE elo_qr_codes (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  code VARCHAR UNIQUE NOT NULL,    -- ABC123
  type VARCHAR NOT NULL,           -- tenant, device, repair

  -- Привязка (опционально)
  device_id UUID,
  repair_id UUID,

  -- Статистика
  scans_count INT DEFAULT 0,
  last_scanned_at TIMESTAMPTZ,

  -- Метаданные
  label VARCHAR,                   -- "Стойка у входа", "Визитка Иван"
  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 5. Интеграции LiveSklad/Remonline

**Уровень:** 5 (Dialog Engine) + Новый блок External Integrations

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                     EXTERNAL INTEGRATIONS                                    │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  DIALOG ENGINE → ИНТЕГРАЦИЯ                                          │   │
│  │                                                                       │   │
│  │  Триггер: stage изменился на SCHEDULED/RECEIVED                      │   │
│  │                                                                       │   │
│  │  Данные для отправки:                                                │   │
│  │  {                                                                    │   │
│  │    client: {name, phone, source_channel},                            │   │
│  │    device: {brand, model, serial, imei},                             │   │
│  │    issue: {category, description},                                    │   │
│  │    quoted_price: 5000,                                                │   │
│  │    appointment: {date, time}                                          │   │
│  │  }                                                                    │   │
│  │                                                                       │   │
│  │  → API LiveSklad: POST /api/orders                                   │   │
│  │  → API Remonline: POST /api/v1/orders                                │   │
│  │                                                                       │   │
│  │  Ответ: external_order_id                                            │   │
│  │  Сохраняем в: elo_dialogs.external_ids                               │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  ИНТЕГРАЦИЯ → DIALOG ENGINE (обратная связь)                         │   │
│  │                                                                       │   │
│  │  Webhook от LiveSklad/Remonline:                                     │   │
│  │  {                                                                    │   │
│  │    order_id: "ext_123",                                              │   │
│  │    status: "completed",                                               │   │
│  │    actual_repairs: [                                                  │   │
│  │      {type: "display", quality: "original", price: 8500},            │   │
│  │      {type: "battery", quality: "oem", price: 1200}                  │   │
│  │    ],                                                                 │   │
│  │    parts_used: [...],                                                 │   │
│  │    total_price: 9700                                                  │   │
│  │  }                                                                    │   │
│  │                                                                       │   │
│  │  Действия:                                                           │   │
│  │  ├── Обновить elo_dialogs.context                                    │   │
│  │  ├── Обновить Neo4j (Problem.resolved = true)                        │   │
│  │  └── Сохранить для обучения AI                                       │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Новые таблицы:**

```sql
CREATE TABLE elo_external_integrations (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  provider VARCHAR NOT NULL,       -- livesklad, remonline

  -- Credentials (encrypted)
  api_key_encrypted VARCHAR,
  api_url VARCHAR,
  webhook_secret VARCHAR,

  -- Настройки синхронизации
  sync_on_stages VARCHAR[],        -- {SCHEDULED, RECEIVED}
  sync_back_enabled BOOLEAN DEFAULT true,

  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Логирование синхронизации
CREATE TABLE elo_integration_logs (
  id UUID PRIMARY KEY,
  integration_id UUID REFERENCES elo_external_integrations(id),
  dialog_id UUID REFERENCES elo_dialogs(id),

  direction VARCHAR,               -- outbound, inbound
  external_id VARCHAR,             -- ID в внешней системе

  request_payload JSONB,
  response_payload JSONB,

  status VARCHAR,                  -- success, error
  error_message TEXT,

  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 6. Самообучение + Предписанные ответы (Learning Engine)

**Уровни:** 0 (Knowledge Base) + 4 (knowledge_lookup) + 5 (Feedback Loop)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                         LEARNING ENGINE                                      │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  KNOWLEDGE BASE (предписанные ответы тенанта)                        │   │
│  │                                                                       │   │
│  │  Тенант задаёт:                                                      │   │
│  │  ├── Триггер: "гарантия"                                             │   │
│  │  │   Ответ: "Гарантия 30 дней на работу, 90 дней на запчасти"       │   │
│  │  │                                                                    │   │
│  │  ├── Триггер: "адрес|как добраться|где находитесь"                   │   │
│  │  │   Ответ: "ул. Ленина 15, вход со двора, красная дверь"           │   │
│  │  │                                                                    │   │
│  │  └── Триггер: "график|время работы|до скольки"                       │   │
│  │      Ответ: "Работаем ежедневно с 10:00 до 20:00"                    │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  UNIVERSAL TOOL: knowledge_lookup                                    │   │
│  │                                                                       │   │
│  │  Вход: {message, tenant_id}                                          │   │
│  │                                                                       │   │
│  │  Логика:                                                             │   │
│  │  1. Проверить триггеры (regex/embedding)                             │   │
│  │  2. Если match → вернуть предписанный ответ                          │   │
│  │  3. Если нет → вернуть null (AI генерирует)                          │   │
│  │                                                                       │   │
│  │  Выход: {matched: true, response: "...", source: "knowledge_base"}   │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  FEEDBACK LOOP (обучение)                                            │   │
│  │                                                                       │   │
│  │  Источники обучения:                                                 │   │
│  │                                                                       │   │
│  │  1. Подтверждённые ответы операторов:                                │   │
│  │     AI предложил → Оператор подтвердил/исправил → Сохраняем          │   │
│  │                                                                       │   │
│  │  2. Реальные ремонты (из External Integrations):                     │   │
│  │     Клиент сказал: "телефон глючит"                                  │   │
│  │     Реальный ремонт: замена АКБ                                      │   │
│  │     → AI учится: "глючит" часто = проблема с АКБ                     │   │
│  │                                                                       │   │
│  │  3. Голосовые ответы операторов:                                     │   │
│  │     Оператор надиктовал → Транскрибация → Нормализация               │   │
│  │     → Добавление в Knowledge Base (с review)                         │   │
│  │                                                                       │   │
│  │  Хранение:                                                           │   │
│  │  ├── elo_learning_examples (примеры)                                 │   │
│  │  └── elo_learning_feedback (оценки операторов)                       │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**Новые таблицы:**

```sql
-- База знаний тенанта (предписанные ответы)
CREATE TABLE elo_knowledge_base (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  -- Триггер (что ищем)
  trigger_type VARCHAR,            -- regex, keywords, embedding
  trigger_pattern VARCHAR,         -- "гарантия|гарантии|гарантией"
  trigger_embedding VECTOR(1536),  -- для semantic search

  -- Ответ
  response TEXT NOT NULL,
  response_type VARCHAR,           -- text, template

  -- Метаданные
  category VARCHAR,                -- faq, policy, location
  priority INT DEFAULT 0,          -- выше = важнее

  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Примеры для обучения
CREATE TABLE elo_learning_examples (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  -- Что клиент сказал
  client_message TEXT NOT NULL,
  extracted_entities JSONB,        -- что AI извлёк

  -- Что реально оказалось
  actual_repair JSONB,             -- из External Integration
  actual_entities JSONB,           -- реальные устройство/проблема

  -- Качество извлечения
  extraction_correct BOOLEAN,

  source VARCHAR,                  -- integration_callback, operator_feedback
  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Фидбек операторов
CREATE TABLE elo_learning_feedback (
  id UUID PRIMARY KEY,
  dialog_id UUID REFERENCES elo_dialogs(id),
  operator_id UUID REFERENCES elo_operators(id),

  -- AI предложил
  ai_response TEXT,

  -- Оператор сделал
  operator_action VARCHAR,         -- approved, edited, rejected
  operator_response TEXT,          -- если edited

  -- Оценка
  rating INT,                      -- 1-5
  comment TEXT,

  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

## Итоговая схема

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                           ELO FULL ARCHITECTURE                              │
│                                                                              │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │                     НОВЫЕ БЛОКИ                                      │    │
│  │  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐               │    │
│  │  │  PRICE   │ │  DEVICE  │ │ EXTERNAL │ │ LEARNING │               │    │
│  │  │  ENGINE  │ │  GATEWAY │ │  INTEGR. │ │  ENGINE  │               │    │
│  │  └────┬─────┘ └────┬─────┘ └────┬─────┘ └────┬─────┘               │    │
│  │       │            │            │            │                      │    │
│  └───────┼────────────┼────────────┼────────────┼──────────────────────┘    │
│          │            │            │            │                           │
│          ▼            ▼            ▼            ▼                           │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │                                                                      │    │
│  │  7. MCP КАНАЛЫ + Device Gateway + QR                                │    │
│  │     TG | WA | VK | Avito | MAX | Phone | Form | [DeviceGW] | [QR]   │    │
│  │                                                                      │    │
│  │  6. RESPONSE BUILDER + Voice TTS                                    │    │
│  │                                                                      │    │
│  │  5. DIALOG ENGINE + External Sync + Feedback Loop                   │    │
│  │                                                                      │    │
│  │  4. UNIVERSAL TOOLS + price_lookup_live + voice_transcribe          │    │
│  │     + knowledge_lookup + appointment_extract                         │    │
│  │                                                                      │    │
│  │  3. ORCHESTRATOR (unchanged)                                        │    │
│  │                                                                      │    │
│  │  2. REQUEST BUILDER + Price/Knowledge tools routing                 │    │
│  │                                                                      │    │
│  │  1. CONTEXT BUILDER + QR resolution + Phone context                 │    │
│  │                                                                      │    │
│  │  0. ДАННЫЕ: PostgreSQL + Neo4j + PriceCatalog + KnowledgeBase       │    │
│  │                                                                      │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Порядок реализации

| # | Блок/Фича | Зависимости | Сложность |
|---|-----------|-------------|-----------|
| 1 | Knowledge Base + knowledge_lookup | Нет | Низкая |
| 2 | QR идентификация | Нет | Низкая |
| 3 | Voice transcribe tool | Whisper API | Низкая |
| 4 | Phone → Graph → Messenger flow | #3 | Средняя |
| 5 | Price Engine (парсер) | Нет | Средняя |
| 6 | Price Engine (нормализация) | #5 | Средняя |
| 7 | price_lookup_live tool | #6 | Низкая |
| 8 | External Integrations (outbound) | Нет | Средняя |
| 9 | External Integrations (inbound webhook) | #8 | Средняя |
| 10 | Learning Engine (feedback) | #8, #9 | Средняя |
| 11 | Device Gateway (архитектура) | Нет | Высокая |
| 12 | Device Gateway (WhatsApp module) | #11 | Высокая |
| 13 | Device Gateway (Avito module) | #11 | Высокая |
| 14 | Device Gateway (MAX module) | #11 | Высокая |

---

**Автор:** User + Claude Code
**Дата:** 2025-12-10
**Версия:** 1.0
