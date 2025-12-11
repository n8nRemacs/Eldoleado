# Roadmap Synchronization with CORE_NEW Architecture

> Mapping killer features to 7-layer AI architecture

**Last updated:** 2025-12-10

---

## Overview: 7 Layers + New Blocks

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                         NEW BLOCKS (from roadmap)                            │
│                                                                              │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐         │
│  │   PRICE     │  │   DEVICE    │  │  EXTERNAL   │  │  LEARNING   │         │
│  │   ENGINE    │  │   GATEWAY   │  │  INTEGR.    │  │    ENGINE   │         │
│  │             │  │             │  │             │  │             │         │
│  │ Price Parse │  │ Smartphone  │  │ LiveSklad   │  │ Self-learn  │         │
│  │ Normalize   │  │ Server      │  │ Remonline   │  │ Feedback    │         │
│  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘         │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                     7 LEVELS CORE_NEW (existing)                             │
│                                                                              │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ LEVEL 7: MCP CHANNELS                                                  │ │
│  │ + Telegram, WhatsApp, VK, Avito, MAX, Form, Phone                      │ │
│  │ + [NEW] Device Gateway (smartphone server)                             │ │
│  │ + [NEW] QR Entry Point                                                 │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ LEVEL 6: RESPONSE BUILDER                                              │ │
│  │ + Format per channel                                                   │ │
│  │ + [NEW] Voice response (TTS for operator)                              │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ LEVEL 5: DIALOG ENGINE                                                 │ │
│  │ + Save to elo_dialogs                                                  │ │
│  │ + Sync to Neo4j                                                        │ │
│  │ + [NEW] Sync to LiveSklad/Remonline (External Integrations)           │ │
│  │ + [NEW] Feedback Loop (Learning Engine)                                │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ LEVEL 4: UNIVERSAL TOOLS                                               │ │
│  │ + device_extract, issue_extract, intent_classify                       │ │
│  │ + [NEW] price_lookup_live (from Price Engine)                          │ │
│  │ + [NEW] voice_transcribe (Whisper)                                     │ │
│  │ + [NEW] knowledge_lookup (tenant prescriptive answers)                 │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ LEVEL 3: ORCHESTRATOR                                                  │ │
│  │ + Blind executor                                                       │ │
│  │ + [UNCHANGED]                                                          │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ LEVEL 2: REQUEST BUILDER                                               │ │
│  │ + Stick-Carrot-Stick                                                   │ │
│  │ + [NEW] Connect Price Engine tools                                     │ │
│  │ + [NEW] Connect Knowledge tools                                        │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ LEVEL 1: CONTEXT BUILDER                                               │ │
│  │ + PostgreSQL + Neo4j                                                   │ │
│  │ + [NEW] QR → client_id resolution                                      │ │
│  │ + [NEW] Phone call context (transcript → graph)                        │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                    │                                         │
│                                    ▼                                         │
│  ┌────────────────────────────────────────────────────────────────────────┐ │
│  │ LEVEL 0: DATA                                                          │ │
│  │ + PostgreSQL (elo_* tables)                                            │ │
│  │ + Neo4j (Client, Device, Problem)                                      │ │
│  │ + [NEW] Price Engine DB (normalized prices)                            │ │
│  │ + [NEW] Knowledge Base (prescriptive answers)                          │ │
│  └────────────────────────────────────────────────────────────────────────┘ │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Detailed Mapping of Killer Features

### 1. Smartphone Server (Device Gateway)

**Level:** 7 (MCP Channels) + New Block

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                         DEVICE GATEWAY                                       │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  Android App (tenant smartphone)                                     │   │
│  │  ├── OPERATOR MODE (always)                                          │   │
│  │  │   └── Push, replies, history                                      │   │
│  │  │                                                                    │   │
│  │  └── SERVER MODE (by flag)                                           │   │
│  │      ├── Foreground Service                                          │   │
│  │      ├── Local API Server                                            │   │
│  │      └── WebSocket → ELO Backend                                     │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  ELO Backend (router)                                                │   │
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
│  │  Modules on device                                                   │   │
│  │  ├── WhatsApp Module (reverse-engineered)                            │   │
│  │  ├── Avito Module (reverse-engineered)                               │   │
│  │  └── MAX Module (reverse-engineered)                                 │   │
│  │                                                                       │   │
│  │  Incoming: Webhook Listener → WebSocket → Backend                    │   │
│  │  Outgoing: Backend → WebSocket → Local API → Channel                 │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘

Integration with existing MCP:

  LEVEL 7 (MCP Channels):
  ├── mcp-telegram     (as is, free)
  ├── mcp-whatsapp     (Wappi 600₽ OR Device Gateway 0₽)
  ├── mcp-avito        (API 1500₽ OR Device Gateway 0₽)
  ├── mcp-vk           (as is, free)
  ├── mcp-max          (API OR Device Gateway 0₽)
  └── mcp-device-gw    [NEW] — router to smartphones
```

**New tables:**

```sql
-- Tenant device servers
CREATE TABLE elo_device_gateways (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  device_id VARCHAR,           -- Android device ID
  push_token VARCHAR,          -- FCM token

  is_online BOOLEAN DEFAULT false,
  last_seen TIMESTAMPTZ,

  -- Active channels on device
  channels JSONB,              -- {whatsapp: true, avito: true, max: false}

  -- Statistics
  messages_routed INT DEFAULT 0,

  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 2. Price Parser + Auto-pricing (Price Engine)

**Level:** 0 (Data) + 4 (Universal Tools) + New Block

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                          PRICE ENGINE                                        │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  PARSER (background process)                                         │   │
│  │                                                                       │   │
│  │  Sources:                                                            │   │
│  │  ├── mobileboost.ru                                                  │   │
│  │  ├── all-spares.ru                                                   │   │
│  │  ├── gsm-komplekt.ru                                                 │   │
│  │  └── + others by config                                              │   │
│  │                                                                       │   │
│  │  Schedule: daily at 3:00 AM                                          │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  NORMALIZER                                                          │   │
│  │                                                                       │   │
│  │  Raw input data:                                                     │   │
│  │  "Дисплей айфон 12 копия олед черный"                                │   │
│  │  "Display iPhone 12 OLED Copy Black"                                 │   │
│  │  "дисп. ip12 oled коп."                                              │   │
│  │                                                                       │   │
│  │  Dictionaries:                                                       │   │
│  │  ├── elo_device_models (brand, model, aliases)                       │   │
│  │  ├── elo_part_types (display, battery, cable, aliases)               │   │
│  │  ├── elo_part_qualities (original, oem, copy, aliases)               │   │
│  │  └── elo_colors (black, white, aliases)                              │   │
│  │                                                                       │   │
│  │  Normalized output:                                                  │   │
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
│  │  PRICE STORAGE                                                       │   │
│  │                                                                       │   │
│  │  elo_price_catalog:                                                  │   │
│  │  ├── Normalized records                                              │   │
│  │  ├── Change history                                                  │   │
│  │  └── Market average prices                                           │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  UNIVERSAL TOOL: price_lookup_live                                   │   │
│  │                                                                       │   │
│  │  Input: {brand: "Apple", model: "iPhone 12", issue: "display"}      │   │
│  │                                                                       │   │
│  │  Output:                                                             │   │
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

**New tables:**

```sql
-- Device model dictionary
CREATE TABLE elo_device_models (
  id SERIAL PRIMARY KEY,
  brand VARCHAR NOT NULL,          -- Apple, Samsung
  model VARCHAR NOT NULL,          -- iPhone 12, Galaxy S23
  aliases VARCHAR[],               -- {ip12, айфон 12, 12ка}
  release_year INT,
  is_active BOOLEAN DEFAULT true
);

-- Part types dictionary
CREATE TABLE elo_part_types (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,           -- display, battery, back_cover
  display_name VARCHAR,            -- Display, Battery, Back Cover
  aliases VARCHAR[],               -- {экран, стекло, дисп}
  subtypes VARCHAR[]               -- {lcd, oled, amoled}
);

-- Part quality dictionary
CREATE TABLE elo_part_qualities (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,           -- original, oem, copy
  display_name VARCHAR,            -- Original, OEM, Copy
  aliases VARCHAR[],               -- {ориг, orig, оригинальный}
  rank INT                         -- 1=best, 3=cheapest
);

-- Price catalog (normalized)
CREATE TABLE elo_price_catalog (
  id UUID PRIMARY KEY,

  -- Normalized fields
  device_model_id INT REFERENCES elo_device_models(id),
  part_type_id INT REFERENCES elo_part_types(id),
  part_quality_id INT REFERENCES elo_part_qualities(id),
  subtype VARCHAR,                 -- oled, lcd for display
  color VARCHAR,

  -- Price
  price DECIMAL(10,2) NOT NULL,
  currency VARCHAR DEFAULT 'RUB',

  -- Source
  source_url VARCHAR,
  source_name VARCHAR,             -- mobileboost.ru

  -- Metadata
  raw_title VARCHAR,               -- Original title
  parsed_at TIMESTAMPTZ DEFAULT NOW(),
  is_active BOOLEAN DEFAULT true
);

-- Tenant pricing
CREATE TABLE elo_tenant_pricing (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  -- Global markup
  global_markup DECIMAL(5,2) DEFAULT 1.3,  -- x1.3 = +30%

  -- Markup by type
  markup_by_type JSONB,            -- {display: 1.4, battery: 1.2}

  -- Fixed prices (override market)
  fixed_prices JSONB,              -- {device_model_id: {part_type: price}}

  updated_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 3. Voice → Graph → Messenger

**Levels:** 7 (Phone) → 4 (voice_transcribe) → 1 (Context Builder) → 5 (Dialog Engine) → 7 (Messenger)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                    VOICE → GRAPH → MESSENGER                                │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ STAGE 1: CALL (LEVEL 7)                                              │   │
│  │                                                                       │   │
│  │  Sources:                                                            │   │
│  │  ├── Asterisk (own PBX)                                              │   │
│  │  ├── Cloud PBX (Mango, Zadarma)                                      │   │
│  │  └── Smartphone server (mic recording)                               │   │
│  │                                                                       │   │
│  │  Output: audio_file (wav/mp3) + call_metadata                        │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ STAGE 2: TRANSCRIPTION (LEVEL 4 - NEW TOOL)                          │   │
│  │                                                                       │   │
│  │  Tool: voice_transcribe                                              │   │
│  │  API: Whisper (OpenAI) / Whisper (self-hosted)                       │   │
│  │                                                                       │   │
│  │  Input: {audio_url, language: "ru"}                                  │   │
│  │  Output: {transcript: "...", segments: [...], duration: 45}          │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ STAGE 3: AI EXTRACTION (LEVELS 3-4)                                  │   │
│  │                                                                       │   │
│  │  Standard pipeline:                                                  │   │
│  │  ├── device_extract → {brand, model}                                 │   │
│  │  ├── issue_extract → {category, description}                         │   │
│  │  ├── intent_classify → {intent: REPAIR}                              │   │
│  │  └── [NEW] appointment_extract → {date, time, notes}                 │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │ STAGE 4: WRITE TO GRAPH (LEVELS 1, 5)                                │   │
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
│  │ STAGE 5: CONTINUE IN MESSENGER (LEVELS 6-7)                          │   │
│  │                                                                       │   │
│  │  Response Builder generates follow-up:                               │   │
│  │  "Hello! You called about {device},                                  │   │
│  │   which has {issue}. Waiting for you {appointment}.                  │   │
│  │   Remind you an hour before visit?"                                  │   │
│  │                                                                       │   │
│  │  Send via available channel:                                         │   │
│  │  ├── Telegram (if exists)                                            │   │
│  │  ├── WhatsApp (if exists)                                            │   │
│  │  └── SMS (fallback)                                                  │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**New tools:**

```sql
INSERT INTO ai_tools (name, display_name, description, prompt_template, output_schema) VALUES
('voice_transcribe', 'Voice Transcription',
 'Converts audio to text',
 NULL,  -- not AI, external API
 '{"transcript": "string", "segments": "array", "duration": "number"}'
),
('appointment_extract', 'Appointment Extraction',
 'Extracts appointment information from text',
 'Extract from text information about visit appointment. Date, time, special requests.',
 '{"date": "string", "time": "string", "notes": "string"}'
);
```

---

### 4. QR for Client Identification

**Levels:** 7 (new entry point) → 1 (Context Builder)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                       QR IDENTIFICATION                                      │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  QR CODES (generation)                                               │   │
│  │                                                                       │   │
│  │  Type 1: On desk/business card                                       │   │
│  │  URL: https://elo.do/{tenant_code}                                   │   │
│  │  → Opens deep link in Telegram/WhatsApp                              │   │
│  │  → Message: /start {tenant_code}                                     │   │
│  │                                                                       │   │
│  │  Type 2: On device after repair                                      │   │
│  │  URL: https://elo.do/{tenant_code}/d/{device_id_short}               │   │
│  │  → Opens chat with device binding                                    │   │
│  │  → Message: /start {tenant_code}_{device_id}                         │   │
│  │                                                                       │   │
│  │  Type 3: On receipt/invoice                                          │   │
│  │  URL: https://elo.do/{tenant_code}/r/{repair_id_short}               │   │
│  │  → Opens chat with repair history                                    │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  CONTEXT BUILDER (extension)                                         │   │
│  │                                                                       │   │
│  │  Input: /start {code}                                                │   │
│  │                                                                       │   │
│  │  Parse:                                                              │   │
│  │  ├── code = "ABC123"         → tenant only                           │   │
│  │  ├── code = "ABC123_D456"    → tenant + device                       │   │
│  │  └── code = "ABC123_R789"    → tenant + repair                       │   │
│  │                                                                       │   │
│  │  Resolve client:                                                     │   │
│  │  ├── By telegram_id/whatsapp_phone                                   │   │
│  │  ├── If device_id → load device history                              │   │
│  │  └── If repair_id → load repair history                              │   │
│  │                                                                       │   │
│  │  Output: unified_context with prefilled data                         │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**New table:**

```sql
CREATE TABLE elo_qr_codes (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  code VARCHAR UNIQUE NOT NULL,    -- ABC123
  type VARCHAR NOT NULL,           -- tenant, device, repair

  -- Binding (optional)
  device_id UUID,
  repair_id UUID,

  -- Statistics
  scans_count INT DEFAULT 0,
  last_scanned_at TIMESTAMPTZ,

  -- Metadata
  label VARCHAR,                   -- "Desk at entrance", "Business card Ivan"
  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 5. LiveSklad/Remonline Integrations

**Level:** 5 (Dialog Engine) + New Block External Integrations

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                     EXTERNAL INTEGRATIONS                                    │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  DIALOG ENGINE → INTEGRATION                                         │   │
│  │                                                                       │   │
│  │  Trigger: stage changed to SCHEDULED/RECEIVED                        │   │
│  │                                                                       │   │
│  │  Data to send:                                                       │   │
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
│  │  Response: external_order_id                                         │   │
│  │  Save to: elo_dialogs.external_ids                                   │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  INTEGRATION → DIALOG ENGINE (feedback)                              │   │
│  │                                                                       │   │
│  │  Webhook from LiveSklad/Remonline:                                   │   │
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
│  │  Actions:                                                            │   │
│  │  ├── Update elo_dialogs.context                                      │   │
│  │  ├── Update Neo4j (Problem.resolved = true)                          │   │
│  │  └── Save for AI training                                            │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**New tables:**

```sql
CREATE TABLE elo_external_integrations (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  provider VARCHAR NOT NULL,       -- livesklad, remonline

  -- Credentials (encrypted)
  api_key_encrypted VARCHAR,
  api_url VARCHAR,
  webhook_secret VARCHAR,

  -- Sync settings
  sync_on_stages VARCHAR[],        -- {SCHEDULED, RECEIVED}
  sync_back_enabled BOOLEAN DEFAULT true,

  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Integration logging
CREATE TABLE elo_integration_logs (
  id UUID PRIMARY KEY,
  integration_id UUID REFERENCES elo_external_integrations(id),
  dialog_id UUID REFERENCES elo_dialogs(id),

  direction VARCHAR,               -- outbound, inbound
  external_id VARCHAR,             -- ID in external system

  request_payload JSONB,
  response_payload JSONB,

  status VARCHAR,                  -- success, error
  error_message TEXT,

  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 6. Self-learning + Prescriptive Answers (Learning Engine)

**Levels:** 0 (Knowledge Base) + 4 (knowledge_lookup) + 5 (Feedback Loop)

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                         LEARNING ENGINE                                      │
│                                                                              │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  KNOWLEDGE BASE (tenant prescriptive answers)                        │   │
│  │                                                                       │   │
│  │  Tenant defines:                                                     │   │
│  │  ├── Trigger: "warranty"                                             │   │
│  │  │   Answer: "30 days warranty on labor, 90 days on parts"          │   │
│  │  │                                                                    │   │
│  │  ├── Trigger: "address|how to get|where are you"                     │   │
│  │  │   Answer: "Lenin St 15, entrance from yard, red door"            │   │
│  │  │                                                                    │   │
│  │  └── Trigger: "schedule|hours|until what time"                       │   │
│  │      Answer: "Open daily from 10:00 to 20:00"                        │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  UNIVERSAL TOOL: knowledge_lookup                                    │   │
│  │                                                                       │   │
│  │  Input: {message, tenant_id}                                         │   │
│  │                                                                       │   │
│  │  Logic:                                                              │   │
│  │  1. Check triggers (regex/embedding)                                 │   │
│  │  2. If match → return prescriptive answer                            │   │
│  │  3. If no match → return null (AI generates)                         │   │
│  │                                                                       │   │
│  │  Output: {matched: true, response: "...", source: "knowledge_base"}  │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                              │                                               │
│                              ▼                                               │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                                                                       │   │
│  │  FEEDBACK LOOP (learning)                                            │   │
│  │                                                                       │   │
│  │  Learning sources:                                                   │   │
│  │                                                                       │   │
│  │  1. Confirmed operator answers:                                      │   │
│  │     AI suggested → Operator confirmed/edited → Save                  │   │
│  │                                                                       │   │
│  │  2. Real repairs (from External Integrations):                       │   │
│  │     Client said: "phone glitching"                                   │   │
│  │     Actual repair: battery replacement                               │   │
│  │     → AI learns: "glitching" often = battery problem                 │   │
│  │                                                                       │   │
│  │  3. Operator voice answers:                                          │   │
│  │     Operator dictated → Transcription → Normalization                │   │
│  │     → Add to Knowledge Base (with review)                            │   │
│  │                                                                       │   │
│  │  Storage:                                                            │   │
│  │  ├── elo_learning_examples (examples)                                │   │
│  │  └── elo_learning_feedback (operator ratings)                        │   │
│  │                                                                       │   │
│  └──────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

**New tables:**

```sql
-- Tenant knowledge base (prescriptive answers)
CREATE TABLE elo_knowledge_base (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  -- Trigger (what we search for)
  trigger_type VARCHAR,            -- regex, keywords, embedding
  trigger_pattern VARCHAR,         -- "warranty|warranties"
  trigger_embedding VECTOR(1536),  -- for semantic search

  -- Answer
  response TEXT NOT NULL,
  response_type VARCHAR,           -- text, template

  -- Metadata
  category VARCHAR,                -- faq, policy, location
  priority INT DEFAULT 0,          -- higher = more important

  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Training examples
CREATE TABLE elo_learning_examples (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  -- What client said
  client_message TEXT NOT NULL,
  extracted_entities JSONB,        -- what AI extracted

  -- What actually happened
  actual_repair JSONB,             -- from External Integration
  actual_entities JSONB,           -- real device/problem

  -- Extraction quality
  extraction_correct BOOLEAN,

  source VARCHAR,                  -- integration_callback, operator_feedback
  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Operator feedback
CREATE TABLE elo_learning_feedback (
  id UUID PRIMARY KEY,
  dialog_id UUID REFERENCES elo_dialogs(id),
  operator_id UUID REFERENCES elo_operators(id),

  -- AI suggested
  ai_response TEXT,

  -- Operator did
  operator_action VARCHAR,         -- approved, edited, rejected
  operator_response TEXT,          -- if edited

  -- Rating
  rating INT,                      -- 1-5
  comment TEXT,

  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

## Final Schema

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│                           ELO FULL ARCHITECTURE                              │
│                                                                              │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │                     NEW BLOCKS                                       │    │
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
│  │  7. MCP CHANNELS + Device Gateway + QR                              │    │
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
│  │  0. DATA: PostgreSQL + Neo4j + PriceCatalog + KnowledgeBase         │    │
│  │                                                                      │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## Implementation Order

| # | Block/Feature | Dependencies | Complexity |
|---|--------------|--------------|------------|
| 1 | Knowledge Base + knowledge_lookup | None | Low |
| 2 | QR identification | None | Low |
| 3 | Voice transcribe tool | Whisper API | Low |
| 4 | Phone → Graph → Messenger flow | #3 | Medium |
| 5 | Price Engine (parser) | None | Medium |
| 6 | Price Engine (normalization) | #5 | Medium |
| 7 | price_lookup_live tool | #6 | Low |
| 8 | External Integrations (outbound) | None | Medium |
| 9 | External Integrations (inbound webhook) | #8 | Medium |
| 10 | Learning Engine (feedback) | #8, #9 | Medium |
| 11 | Device Gateway (architecture) | None | High |
| 12 | Device Gateway (WhatsApp module) | #11 | High |
| 13 | Device Gateway (Avito module) | #11 | High |
| 14 | Device Gateway (MAX module) | #11 | High |

---

**Author:** User + Claude Code
**Date:** 2025-12-10
**Version:** 1.0
