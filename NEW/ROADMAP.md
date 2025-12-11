# ELO Product Roadmap

> Strategy: Leading with our best. Not MVP, but a full-fledged vertical product with maximum WOW effect.

**Last updated:** 2025-12-10

---

## MVP Vertical

**Domain:** Phone service = Repair + Purchase/Sale (trade-in, used)

**Philosophy:** "People communicate. Machine keeps records."

**Key insight:** B2C service = 15 minutes to solution, response in 1-2 minutes. amoCRM/Bitrix with kanban boards for several days DO NOT WORK.

---

## Competitive Advantage

```
Competitors:                         ELO:
─────────────────────────────────────────────────────────
Channel = 600₽/month                 Channel = 0₽ (smartphone-server)
Manual price list                    Parser + auto-pricing
Missed call = lost client            Call → Graph → Messenger
"Fill out the form"                  AI understands "14 pro max"
Replied in 2 hours                   AI replied at 11pm
Kanban for 3 days                    15 minutes to deal
```

---

## WOW Effect (selected)

**"Don't lose clients"**

> "Client wrote at 11pm. You were sleeping. AI replied, scheduled for tomorrow. How many like this do you lose per month?"

---

## Killer Features

### 1. Smartphone-Server (free channels)

```
Problem:
  WhatsApp Wappi/GreenAPI = 600₽/channel/month
  Avito API = 1500-3000₽/month
  MAX = paid

Solution:
  Application on client's smartphone (tenant)
  ├── Reverse-engineered WhatsApp API
  ├── Reverse-engineered MAX API
  └── Free Avito API

Architecture:
  ┌─────────────────────────────────────────────────┐
  │  Client's smartphone (real IP)                  │
  │  ├── ELO App (hidden functionality)             │
  │  └── Local API server                           │
  └─────────────────────────────────────────────────┘
                      ↓ NAT traversal
  ┌─────────────────────────────────────────────────┐
  │  ELO Backend                                    │
  │  └── Determines: server or application          │
  └─────────────────────────────────────────────────┘

Authorization:
  1. Client enters login/password in application
  2. Backend determines mode:
     - Server (ours) → use paid APIs
     - Application (client's smartphone) → use free APIs

Distribution:
  ├── Google Play (with hidden functionality)
  └── RuStore (with hidden functionality)

Invisibility:
  - Real smartphone IP
  - Real user-agent
  - No signs of automation
```

**ROI for client:** Savings of 600-3000₽/month on channels

---

### 2. Smart price list with parser

```
Sources:
  ├── Parts stores (parsing)
  └── Own price database

Normalization (strict, not fuzzy):
  Input data:
    "Display iPhone 12 copy OLED black"
    "Display iPhone 12 OLED Copy Black"
    "disp. ip12 oled copy"

  Normalized result:
  ┌──────────┬───────────┬──────┬──────┬─────────────┬───────┬────────────┐
  │ Type     │ Model     │ Type │ Color│ Manufacturer│ Price │ Store      │
  ├──────────┼───────────┼──────┼──────┼─────────────┼───────┼────────────┤
  │ Display  │ iPhone 12 │ OLED │ Black│ China Copy  │ 2500₽ │ MobileOpt  │
  │ Display  │ iPhone 12 │ OLED │ Black│ OEM         │ 4500₽ │ iPartsRu   │
  │ Display  │ iPhone 12 │ Orig │ Black│ Apple       │ 8500₽ │ iPartsRu   │
  └──────────┴───────────┴──────┴──────┴─────────────┴───────┴────────────┘

Repair categories:
  ├── Battery
  ├── Display (Original/OEM/Copy/OLED/LCD)
  ├── Back cover
  ├── Flex cables (charging, buttons, camera)
  ├── Camera (front/rear)
  └── Body parts

AI substitution:
  Client: "broke screen on 12"
  AI: "iPhone 12 display replacement:
       • Copy LCD: 1800₽
       • Copy OLED: 2500₽
       • Original: 8500₽
       Which option would you consider?"
```

**ROI for client:** Current prices without manual updates, fast responses

---

### 3. Voice → Graph → Messenger

```
┌─────────────────────────────────────────────────────────┐
│ 1. CALL                                                 │
│    Client calls: "Hello, my iPhone won't charge,        │
│    can you check? I'll come tomorrow at 3"              │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│ 2. RECORDING + TRANSCRIPTION                            │
│    Whisper API → conversation text                      │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│ 3. AI ENTITY EXTRACTION                                 │
│    ├── Device: iPhone (model unknown)                   │
│    ├── Problem: not charging                            │
│    ├── Agreement: tomorrow at 15:00                     │
│    └── Mood: neutral                                    │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│ 4. WRITE TO Neo4j GRAPH                                 │
│    (Client)-[:OWNS]->(Device:iPhone)                    │
│    (Device)-[:HAS_PROBLEM]->(Problem:not_charging)      │
│    (Client)-[:TOUCHPOINT {channel:phone, direction:in}] │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│ 5. NEW CONTEXT LINE                                     │
│    Dialog continues with full call context              │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│ 6. CONTINUATION IN MESSENGER                            │
│    AI in Telegram/WhatsApp:                             │
│    "Good day! You called about an iPhone that           │
│    won't charge. We expect you tomorrow at 15:00.       │
│    Remind you an hour before the visit?"                │
└─────────────────────────────────────────────────────────┘
```

**ROI for client:** Call isn't lost, context preserved, upsells via messenger

---

### 4. QR for client identification

```
Scenario 1: New client in service
  ┌─────────────────────────────────────────────────┐
  │ QR code on counter / business card              │
  │ → Client scans                                  │
  │ → Opens Telegram/WhatsApp                       │
  │ → System creates profile                        │
  │ → Operator sees "new client"                    │
  └─────────────────────────────────────────────────┘

Scenario 2: Returning client
  ┌─────────────────────────────────────────────────┐
  │ QR code on counter                              │
  │ → Client scans                                  │
  │ → System recognizes by phone number             │
  │ → Operator sees entire history:                 │
  │   "Ivan, was 2 weeks ago, replaced display      │
  │    on iPhone 13, warranty until 15.01"          │
  └─────────────────────────────────────────────────┘

Scenario 3: QR on device after repair
  ┌─────────────────────────────────────────────────┐
  │ Sticker with QR on client's phone               │
  │ → Client scans a month later                    │
  │ → Goes to chat with repair history              │
  │ → Can ask question about warranty               │
  └─────────────────────────────────────────────────┘
```

**ROI for client:** Fast identification, loyalty, repeat sales

---

### 5. Integrations with accounting systems

```
Supported systems:
  ├── LiveSklad
  ├── Remonline
  └── Others on request

Two-way synchronization:

  ELO → Accounting:
  ┌─────────────────────────────────────────────────┐
  │ AI collected data from dialog:                  │
  │ • Client: Ivan Petrov, +79991234567             │
  │ • Device: iPhone 14 Pro Max                     │
  │ • Problem: broken display                       │
  │ • Repair type: display replacement              │
  │                                                 │
  │ → Creates order in LiveSklad/Remonline          │
  │   with filled fields                            │
  └─────────────────────────────────────────────────┘

  Accounting → ELO:
  ┌─────────────────────────────────────────────────┐
  │ Technician closed order:                        │
  │ • Actual repair: display + battery replacement  │
  │ • Parts: Display OLED Copy, Battery OEM         │
  │ • Cost: 4500₽                                   │
  │                                                 │
  │ → Data returns to ELO                           │
  │ → AI learns from real repairs                   │
  │ → Graph enriched with actual data               │
  └─────────────────────────────────────────────────┘

AI learning:
  • What clients say vs what actually gets repaired
  • Which parts are most commonly used
  • Average cost by repair types
```

**ROI for client:** No double entry, AI gets smarter

---

### 6. Self-learning + prescribed responses

```
Learning sources:

1. Prescribed responses (tenant sets themselves):
   ┌─────────────────────────────────────────────────┐
   │ Trigger: "warranty"                             │
   │ Response: "30 days warranty on work,            │
   │           90 days on original parts"            │
   ├─────────────────────────────────────────────────┤
   │ Trigger: "how to get there"                     │
   │ Response: "We are located at: 15 Lenin St.,     │
   │           entrance from courtyard. Red door"    │
   └─────────────────────────────────────────────────┘

2. Operator responses (confirmed):
   ┌─────────────────────────────────────────────────┐
   │ AI suggested response                           │
   │ Operator confirmed / corrected                  │
   │ → AI remembers correct version                  │
   └─────────────────────────────────────────────────┘

3. Real repairs (from accounting):
   ┌─────────────────────────────────────────────────┐
   │ Client said: "phone glitching"                  │
   │ Actual repair: battery replacement              │
   │ → AI learns: "glitching" often = battery issue  │
   └─────────────────────────────────────────────────┘

4. Voice responses from operators:
   ┌─────────────────────────────────────────────────┐
   │ Operator dictated response by voice             │
   │ → Transcription                                 │
   │ → Normalization (remove "umm", "well")          │
   │ → Send to client                                │
   │ → Add to knowledge base                         │
   └─────────────────────────────────────────────────┘
```

---

## AI Tools / Tools

> Atomic tools that AI calls to perform tasks

### Tool Overview

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                          AI TOOLS                                            │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  EXTRACTION                       ACTIONS                                    │
│  ├── device_extract               ├── appointment_create                    │
│  ├── issue_extract                ├── appointment_reschedule                │
│  ├── intent_classify              ├── parts_search                          │
│  ├── price_extract                ├── price_lookup                          │
│  ├── appointment_extract          ├── order_create (Remonline/LiveSklad)    │
│  └── sentiment_analyze            └── notification_send                     │
│                                                                              │
│  LOOKUP                           GENERATION                                 │
│  ├── client_lookup                ├── response_generate                     │
│  ├── device_history               ├── summary_generate                      │
│  ├── parts_catalog_search         └── greeting_generate                     │
│  ├── knowledge_lookup                                                       │
│  └── qr_resolve                   INTEGRATIONS (External)                   │
│                                   ├── remonline_sync                        │
│                                   ├── livesklad_sync                        │
│                                   └── voice_transcribe                      │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

### 1. Appointment

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│  TOOL: appointment_extract                                                   │
│  Purpose: Extract date/time/preferences from message                        │
│                                                                              │
│  Input: "Tomorrow at 3, after lunch"                                        │
│  Output: {                                                                   │
│    date: "2025-12-11",                                                       │
│    time: "15:00",                                                            │
│    time_flexible: true,                                                      │
│    notes: "after lunch"                                                      │
│  }                                                                           │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  TOOL: appointment_create                                                    │
│  Purpose: Create entry in calendar/system                                   │
│                                                                              │
│  Input: {                                                                    │
│    client_id: "uuid",                                                        │
│    date: "2025-12-11",                                                       │
│    time: "15:00",                                                            │
│    service_type: "repair",                                                   │
│    device: "iPhone 14",                                                      │
│    issue: "screen"                                                           │
│  }                                                                           │
│                                                                              │
│  Actions:                                                                    │
│  ├── Check available slots                                                  │
│  ├── Create entry in elo_appointments                                       │
│  ├── Create event in Google Calendar (optional)                             │
│  ├── Create order in Remonline/LiveSklad (optional)                         │
│  └── Schedule reminder to client                                            │
│                                                                              │
│  Output: {                                                                   │
│    appointment_id: "uuid",                                                   │
│    confirmed_time: "15:00",                                                  │
│    reminder_scheduled: true                                                  │
│  }                                                                           │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  TOOL: appointment_reschedule                                                │
│  Purpose: Reschedule existing appointment                                   │
│                                                                              │
│  Input: "Can't make it tomorrow, let's do Friday"                           │
│                                                                              │
│  Logic:                                                                      │
│  ├── Find active client appointment                                         │
│  ├── Extract new date/time                                                  │
│  ├── Check availability                                                     │
│  ├── Update appointment                                                     │
│  └── Notify client of confirmation                                          │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘

Table:

CREATE TABLE elo_appointments (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),
  client_id UUID REFERENCES elo_clients(id),
  dialog_id UUID REFERENCES elo_dialogs(id),

  -- Time
  scheduled_date DATE NOT NULL,
  scheduled_time TIME,
  time_flexible BOOLEAN DEFAULT false,
  duration_minutes INT DEFAULT 30,

  -- Type
  service_type VARCHAR,            -- repair, consultation, pickup

  -- Link to device/problem
  device_info JSONB,               -- {brand, model, issue}

  -- Status
  status VARCHAR DEFAULT 'scheduled',  -- scheduled, confirmed, completed, cancelled, no_show

  -- Reminders
  reminder_sent BOOLEAN DEFAULT false,
  reminder_scheduled_at TIMESTAMPTZ,

  -- External systems
  external_ids JSONB,              -- {remonline: "123", google_calendar: "abc"}

  notes TEXT,
  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 2. Parts Search

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│  TOOL: parts_search                                                          │
│  Purpose: Find parts by parameters                                          │
│                                                                              │
│  Input: {                                                                    │
│    device_brand: "Apple",                                                    │
│    device_model: "iPhone 14",                                                │
│    part_type: "display",                                                     │
│    quality: null  // all variants                                           │
│  }                                                                           │
│                                                                              │
│  Output: {                                                                   │
│    parts: [                                                                  │
│      {                                                                       │
│        id: "uuid",                                                           │
│        name: "Display iPhone 14 OLED Copy",                                 │
│        quality: "copy",                                                      │
│        subtype: "oled",                                                      │
│        price: 4500,                                                          │
│        in_stock: true,                                                       │
│        supplier: "mobileboost.ru"                                           │
│      },                                                                      │
│      {                                                                       │
│        id: "uuid",                                                           │
│        name: "Display iPhone 14 Original",                                  │
│        quality: "original",                                                  │
│        price: 12000,                                                         │
│        in_stock: false,                                                      │
│        delivery_days: 3                                                      │
│      }                                                                       │
│    ],                                                                        │
│    tenant_markup: 1.3,                                                       │
│    final_prices: [5850, 15600]                                              │
│  }                                                                           │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘

Usage scenarios:

1. Client asks about price:
   "How much to replace screen on 14?"
   → device_extract → parts_search → response_generate
   → "iPhone 14 display replacement: copy 5850₽, original 15600₽"

2. Operator checks availability:
   Application → parts_search (internal) → show stock status

3. AI suggests alternatives:
   "Original not in stock, 3 day delivery. OLED copy available now."
```

---

### 3. Parts Catalog Block

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│  PRICE ENGINE: Parts Catalog                                                 │
│  Purpose: Create and maintain parts catalog                                 │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  DATA SOURCES                                                               │
│                                                                              │
│  1. Store parsing (automatic):                                              │
│     ├── mobileboost.ru                                                      │
│     ├── all-spares.ru                                                       │
│     ├── gsm-komplekt.ru                                                     │
│     └── opt-mobile.ru                                                       │
│                                                                              │
│  2. Import from tenant (manual):                                            │
│     ├── Excel/CSV file                                                      │
│     ├── Supplier API                                                        │
│     └── Manual entry                                                        │
│                                                                              │
│  3. Sync from accounting:                                                   │
│     ├── LiveSklad → current stock                                           │
│     └── Remonline → current stock                                           │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  NORMALIZATION PROCESS                                                      │
│                                                                              │
│  Raw data:                                                                  │
│  "Disp. iPhone 14 OLED copy black"                                          │
│  "LCD iPhone14 black copy"                                                  │
│  "Screen for Apple iPhone 14 OLED (black) - copy"                           │
│                                                                              │
│                          ↓                                                   │
│                                                                              │
│  Dictionaries:                                                              │
│  ┌────────────────────────────────────────────────────────────────────┐     │
│  │ elo_device_models                                                  │     │
│  │ ├── brand: "Apple"                                                 │     │
│  │ ├── model: "iPhone 14"                                             │     │
│  │ └── aliases: ["iphone 14", "iphone14", "ip14", "14"]              │     │
│  ├────────────────────────────────────────────────────────────────────┤     │
│  │ elo_part_types                                                     │     │
│  │ ├── name: "display"                                                │     │
│  │ ├── display_name: "Display"                                        │     │
│  │ ├── aliases: ["screen", "disp", "lcd", "glass", "touch"]          │     │
│  │ └── subtypes: ["lcd", "oled", "amoled", "incell"]                 │     │
│  ├────────────────────────────────────────────────────────────────────┤     │
│  │ elo_part_qualities                                                 │     │
│  │ ├── name: "copy"                                                   │     │
│  │ ├── display_name: "Copy"                                           │     │
│  │ ├── aliases: ["copy", "replica", "china"]                          │     │
│  │ └── rank: 3  // 1=best, 3=cheapest                                │     │
│  └────────────────────────────────────────────────────────────────────┘     │
│                                                                              │
│                          ↓                                                   │
│                                                                              │
│  Normalized entry:                                                          │
│  {                                                                           │
│    device_model_id: 142,       // → Apple iPhone 14                         │
│    part_type_id: 1,            // → display                                 │
│    part_quality_id: 3,         // → copy                                    │
│    subtype: "oled",                                                         │
│    color: "black",                                                          │
│    price: 4500,                                                             │
│    source: "mobileboost.ru",                                                │
│    raw_title: "Disp. iPhone 14 OLED copy black"                            │
│  }                                                                           │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  CATALOG CREATION WORKFLOW                                                  │
│                                                                              │
│  Step 1: Parsing (daily at 3:00)                                           │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ ELO_Price_Parser                                                    │   │
│  │ ├── Fetch HTML/API from sources                                     │   │
│  │ ├── Extract raw items                                               │   │
│  │ └── Save to elo_price_raw (staging)                                 │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                          ↓                                                   │
│  Step 2: Normalization                                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ ELO_Price_Normalizer                                                │   │
│  │ ├── Match against dictionaries                                      │   │
│  │ ├── AI fallback for unmatched (GPT classify)                       │   │
│  │ ├── Flag uncertain matches for review                               │   │
│  │ └── Save to elo_price_catalog                                       │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                          ↓                                                   │
│  Step 3: Deduplication                                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ ELO_Price_Deduplicator                                              │   │
│  │ ├── Find duplicates (same part, different sources)                  │   │
│  │ ├── Calculate market average                                        │   │
│  │ └── Update elo_price_market_avg                                     │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                          ↓                                                   │
│  Step 4: Apply markups                                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ parts_search (runtime)                                              │   │
│  │ ├── Load market prices                                              │   │
│  │ ├── Apply tenant markup (elo_tenant_pricing)                        │   │
│  │ └── Return final prices                                             │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘

Tables:

-- Raw data (staging)
CREATE TABLE elo_price_raw (
  id UUID PRIMARY KEY,
  source_name VARCHAR NOT NULL,
  source_url VARCHAR,
  raw_title VARCHAR NOT NULL,
  raw_price DECIMAL(10,2),
  raw_data JSONB,
  parsed_at TIMESTAMPTZ DEFAULT NOW(),
  processed BOOLEAN DEFAULT false
);

-- Normalized catalog
CREATE TABLE elo_price_catalog (
  id UUID PRIMARY KEY,
  device_model_id INT REFERENCES elo_device_models(id),
  part_type_id INT REFERENCES elo_part_types(id),
  part_quality_id INT REFERENCES elo_part_qualities(id),
  subtype VARCHAR,
  color VARCHAR,
  price DECIMAL(10,2) NOT NULL,
  source_name VARCHAR,
  source_url VARCHAR,
  raw_title VARCHAR,
  confidence DECIMAL(3,2),    -- 0.00-1.00, normalization confidence
  parsed_at TIMESTAMPTZ DEFAULT NOW(),
  is_active BOOLEAN DEFAULT true
);

-- Market average prices
CREATE TABLE elo_price_market_avg (
  id UUID PRIMARY KEY,
  device_model_id INT,
  part_type_id INT,
  part_quality_id INT,
  subtype VARCHAR,
  avg_price DECIMAL(10,2),
  min_price DECIMAL(10,2),
  max_price DECIMAL(10,2),
  sources_count INT,
  calculated_at TIMESTAMPTZ DEFAULT NOW()
);

-- Tenant markups
CREATE TABLE elo_tenant_pricing (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),
  global_markup DECIMAL(5,2) DEFAULT 1.30,  -- +30%
  markup_by_type JSONB,          -- {"display": 1.4, "battery": 1.2}
  markup_by_quality JSONB,       -- {"original": 1.2, "copy": 1.5}
  fixed_prices JSONB,            -- override specific parts
  updated_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 4. QR code authorization/registration

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│  TOOL: qr_resolve                                                            │
│  Purpose: Identify client/context by QR code                                │
│                                                                              │
│  QR code types:                                                             │
│                                                                              │
│  1. TENANT QR (on counter, business card)                                   │
│     URL: https://elo.do/ABC123                                              │
│     → New client: create profile, start dialog                              │
│     → Existing: recognize by telegram_id/phone, show history                │
│                                                                              │
│  2. DEVICE QR (on device after repair)                                      │
│     URL: https://elo.do/ABC123/d/XYZ789                                     │
│     → Client scans → sees repair history of this device                     │
│     → Can ask warranty question                                             │
│                                                                              │
│  3. REPAIR QR (on receipt)                                                  │
│     URL: https://elo.do/ABC123/r/REP456                                     │
│     → Client scans → sees specific repair details                           │
│     → Status, cost, warranty                                                │
│                                                                              │
│  4. PROMO QR (on advertisement)                                             │
│     URL: https://elo.do/ABC123/p/PROMO1                                     │
│     → Track acquisition source                                              │
│     → Automatically apply discount                                          │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  FLOW: QR → Messenger → Authorization                                       │
│                                                                              │
│  1. Client scans QR                                                         │
│  2. Opens link → redirects to Telegram/WhatsApp                             │
│  3. Autostart: /start ABC123_d_XYZ789                                       │
│  4. Backend parses code:                                                    │
│     ├── tenant_code: ABC123 → tenant_id                                     │
│     ├── type: d (device)                                                    │
│     └── entity_id: XYZ789 → device_id                                       │
│  5. Resolve client:                                                         │
│     ├── By telegram_id → found → load history                               │
│     └── Not found → create profile                                          │
│  6. Load device/repair context                                              │
│  7. AI ready for dialog with full context                                   │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  TOOL: qr_generate                                                           │
│  Purpose: Create QR code                                                    │
│                                                                              │
│  Input: {                                                                    │
│    tenant_id: "uuid",                                                        │
│    type: "device",                                                           │
│    entity_id: "uuid",      // device_id, repair_id, promo_id                │
│    label: "iPhone 14 Ivanov"  // for tracking                               │
│  }                                                                           │
│                                                                              │
│  Output: {                                                                   │
│    qr_id: "uuid",                                                            │
│    code: "ABC123_d_XYZ789",                                                 │
│    url: "https://elo.do/ABC123/d/XYZ789",                                   │
│    qr_image_url: "https://api.elo.do/qr/ABC123_d_XYZ789.png"               │
│  }                                                                           │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘

Table:

CREATE TABLE elo_qr_codes (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  code VARCHAR UNIQUE NOT NULL,    -- ABC123_d_XYZ789
  type VARCHAR NOT NULL,           -- tenant, device, repair, promo

  -- Entity binding
  entity_type VARCHAR,             -- device, repair, promo
  entity_id UUID,

  -- For promo
  promo_config JSONB,              -- {discount: 10, valid_until: "2025-01-01"}

  -- Statistics
  scans_count INT DEFAULT 0,
  unique_clients INT DEFAULT 0,
  last_scanned_at TIMESTAMPTZ,

  -- Metadata
  label VARCHAR,                   -- "Counter at entrance", "Business card Ivan"

  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Scan logging
CREATE TABLE elo_qr_scans (
  id UUID PRIMARY KEY,
  qr_id UUID REFERENCES elo_qr_codes(id),
  client_id UUID REFERENCES elo_clients(id),
  channel VARCHAR,                 -- telegram, whatsapp
  scanned_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### 5. Remonline API

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│  TOOL: remonline_sync                                                        │
│  Purpose: Two-way synchronization with Remonline                            │
│                                                                              │
│  API: https://api.remonline.app/                                            │
│  Docs: https://remonline.app/docs/api/                                      │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  DIRECTION: ELO → Remonline                                                 │
│                                                                              │
│  Trigger: dialog.stage = SCHEDULED or RECEIVED                              │
│                                                                              │
│  Actions:                                                                    │
│  1. POST /orders/ — create order                                            │
│     {                                                                        │
│       "client": {                                                            │
│         "name": "Ivan Petrov",                                              │
│         "phone": "+79991234567"                                             │
│       },                                                                     │
│       "order_type": "repair",                                               │
│       "device_type": "iPhone 14",                                           │
│       "malfunction": "Broken screen",                                       │
│       "estimated_cost": 5000,                                               │
│       "scheduled_for": "2025-12-11T15:00:00"                                │
│     }                                                                        │
│                                                                              │
│  2. Save external_id to elo_dialogs.external_ids.remonline                 │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  DIRECTION: Remonline → ELO (webhook)                                       │
│                                                                              │
│  Endpoint: POST /webhook/remonline                                          │
│                                                                              │
│  Events:                                                                     │
│  ├── order.status_changed → update dialog.stage                            │
│  ├── order.completed → get actual data                                     │
│  └── order.part_used → update learning data                                │
│                                                                              │
│  Example payload (order.completed):                                         │
│  {                                                                           │
│    "event": "order.completed",                                              │
│    "order_id": "123456",                                                    │
│    "actual_repairs": [                                                       │
│      {"type": "Display replacement", "price": 5000}                         │
│    ],                                                                        │
│    "parts_used": [                                                           │
│      {"name": "Display iPhone 14 OLED", "price": 3500, "qty": 1}           │
│    ],                                                                        │
│    "total": 5000,                                                            │
│    "completed_at": "2025-12-11T18:30:00"                                    │
│  }                                                                           │
│                                                                              │
│  Actions:                                                                    │
│  1. Update elo_dialogs.context.actual_repair                               │
│  2. Update Neo4j: Problem.resolved = true                                  │
│  3. Save to elo_learning_examples for AI training                          │
│  4. Send to client: "Your iPhone 14 is ready! Cost: 5000₽"                 │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

### 6. LiveSklad API

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                              │
│  TOOL: livesklad_sync                                                        │
│  Purpose: Two-way synchronization with LiveSklad                            │
│                                                                              │
│  API: https://livesklad.com/api/                                            │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  DIRECTION: ELO → LiveSklad                                                 │
│                                                                              │
│  Trigger: dialog.stage = SCHEDULED or RECEIVED                              │
│                                                                              │
│  Actions:                                                                    │
│  1. POST /api/orders — create order                                         │
│     {                                                                        │
│       "client_name": "Ivan Petrov",                                         │
│       "client_phone": "+79991234567",                                       │
│       "device": "Apple iPhone 14",                                          │
│       "defect": "Broken screen",                                            │
│       "preliminary_price": 5000,                                            │
│       "reception_date": "2025-12-11"                                        │
│     }                                                                        │
│                                                                              │
│  2. POST /api/clients — create/find client (if doesn't exist)              │
│                                                                              │
│  3. Save external_id to elo_dialogs.external_ids.livesklad                 │
│                                                                              │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  DIRECTION: LiveSklad → ELO (webhook)                                       │
│                                                                              │
│  Endpoint: POST /webhook/livesklad                                          │
│                                                                              │
│  Events:                                                                     │
│  ├── order.status → update dialog.stage                                    │
│  │   - "In progress" → IN_PROGRESS                                          │
│  │   - "Ready" → READY                                                      │
│  │   - "Delivered" → DELIVERED                                              │
│  │                                                                           │
│  ├── order.completed → get final data                                      │
│  │   {                                                                       │
│  │     "order_id": "LS-12345",                                              │
│  │     "works": [{"name": "Display replacement", "price": 5000}],          │
│  │     "parts": [{"name": "Display OLED", "price": 3500}],                 │
│  │     "total": 5000                                                        │
│  │   }                                                                       │
│  │                                                                           │
│  └── stock.updated → update parts stock                                    │
│      {                                                                       │
│        "part_id": "P-123",                                                  │
│        "name": "Display iPhone 14 OLED",                                    │
│        "quantity": 5,                                                        │
│        "price": 3500                                                         │
│      }                                                                       │
│                                                                              │
│  Actions on stock.updated:                                                  │
│  1. Update elo_price_catalog (in_stock, local_price)                       │
│  2. Mark parts as "in stock" for parts_search                               │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

### Common integrations table

```sql
CREATE TABLE elo_external_integrations (
  id UUID PRIMARY KEY,
  tenant_id UUID REFERENCES elo_tenants(id),

  provider VARCHAR NOT NULL,       -- remonline, livesklad, google_calendar

  -- Credentials (encrypted)
  api_key_encrypted VARCHAR,
  api_secret_encrypted VARCHAR,
  api_url VARCHAR,

  -- Webhook
  webhook_secret VARCHAR,
  webhook_url VARCHAR,             -- our endpoint for receiving

  -- Settings
  sync_on_stages VARCHAR[],        -- {SCHEDULED, RECEIVED}
  sync_back_enabled BOOLEAN DEFAULT true,
  sync_stock_enabled BOOLEAN DEFAULT false,

  -- Field mapping (customization)
  field_mapping JSONB,             -- {"elo_field": "external_field"}

  -- Status
  is_active BOOLEAN DEFAULT true,
  last_sync_at TIMESTAMPTZ,
  last_error TEXT,

  created_at TIMESTAMPTZ DEFAULT NOW()
);

-- Sync log
CREATE TABLE elo_integration_logs (
  id UUID PRIMARY KEY,
  integration_id UUID REFERENCES elo_external_integrations(id),
  dialog_id UUID REFERENCES elo_dialogs(id),

  direction VARCHAR,               -- outbound, inbound
  event_type VARCHAR,              -- order.create, order.completed, stock.updated

  external_id VARCHAR,
  request_payload JSONB,
  response_payload JSONB,

  status VARCHAR,                  -- success, error, pending
  error_message TEXT,

  created_at TIMESTAMPTZ DEFAULT NOW()
);
```

---

### Tool Matrix by Phases

| Tool | Phase | Priority | Dependencies |
|------|-------|----------|-------------|
| device_extract | MVP | ⭐⭐⭐⭐⭐ | — |
| issue_extract | MVP | ⭐⭐⭐⭐⭐ | — |
| intent_classify | MVP | ⭐⭐⭐⭐ | — |
| response_generate | MVP | ⭐⭐⭐⭐⭐ | — |
| appointment_extract | MVP | ⭐⭐⭐⭐ | — |
| appointment_create | MVP | ⭐⭐⭐⭐ | appointment_extract |
| qr_resolve | Phase 2 | ⭐⭐⭐ | — |
| qr_generate | Phase 2 | ⭐⭐⭐ | — |
| parts_search | Phase 2 | ⭐⭐⭐⭐ | Price Engine |
| knowledge_lookup | Phase 2 | ⭐⭐⭐ | Knowledge Base |
| voice_transcribe | Phase 2 | ⭐⭐⭐⭐⭐ | Whisper API |
| remonline_sync | Phase 3 | ⭐⭐⭐ | Remonline API key |
| livesklad_sync | Phase 3 | ⭐⭐⭐ | LiveSklad API key |
| price_lookup | Phase 2 | ⭐⭐⭐⭐ | Price Engine |

---

## Roadmap by Phases

### Phase 1: MVP (current)

| Component | Status | Description |
|-----------|--------|-------------|
| Omnichannel | 80% | 7 channels (TG, WA, Avito, VK, MAX, Form, Phone) |
| AI auto-reply 24/7 | 70% | Debounce 10s, context understanding |
| AI assist | 70% | Operator suggestions |
| Client history | 60% | Neo4j graph |
| Device/problem identification | 70% | AI extraction |
| Android App | 60% | Notifications, replies |
| Operator Web App | 0% | **BLOCKER** |

### Phase 2: +1-2 months after MVP

| Component | Priority | Description |
|-----------|----------|-------------|
| Voice → Graph → Messenger | ⭐⭐⭐⭐⭐ | Recording, transcription, chat continuation |
| QR identification | ⭐⭐⭐ | On counter, card, device |
| Price parser | ⭐⭐⭐⭐ | Store parsing, normalization |
| Auto price insertion | ⭐⭐⭐⭐ | AI names current prices |
| Prescribed responses | ⭐⭐⭐ | Tenant sets own templates |

### Phase 3: +3-4 months after MVP

| Component | Priority | Description |
|-----------|----------|-------------|
| Smartphone-server | ⭐⭐⭐⭐⭐ | Free WhatsApp/Avito/MAX |
| LiveSklad integration | ⭐⭐⭐ | Two-way synchronization |
| Remonline integration | ⭐⭐⭐ | Two-way synchronization |
| Voice responses | ⭐⭐⭐ | Operator dictates, AI sends text |
| Self-learning | ⭐⭐⭐⭐ | From real repairs and responses |

### Phase 4: 100+ tenants (NOT BUILDING NOW)

| Component | Why postponed |
|-----------|---------------|
| Market analytics | Need statistics from 100+ services |
| Benchmarks | Comparison requires data |
| Price recommendations | Based on other services |
| Tenant ratings | Need client reviews |
| Warming/newsletters | After accumulating base |
| Exchange between tenants | Service network |

---

## Technical Requirements

### Android App (one app — two modes)

```
┌─────────────────────────────────────────────────────────┐
│              ELO Android App                            │
│              (Google Play / RuStore)                    │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  MODE 1: OPERATOR (always active)                       │
│  ├── Push notifications for new messages                │
│  ├── Reply to clients (text, voice)                     │
│  ├── Dialog history                                     │
│  ├── AI suggestions                                     │
│  ├── Client info from graph                             │
│  └── QR scanner for identification                      │
│                                                         │
├─────────────────────────────────────────────────────────┤
│                                                         │
│  MODE 2: SERVER (hidden, activated by flag)             │
│  ├── Foreground Service (not killed by system)          │
│  ├── Local HTTP Server (for API requests)               │
│  ├── WebSocket Client (connection to backend)           │
│  ├── WhatsApp Module (reverse-engineered)               │
│  ├── Avito Module (reverse-engineered)                  │
│  └── MAX Module (reverse-engineered)                    │
│                                                         │
└─────────────────────────────────────────────────────────┘

Requirements for SERVER mode:
  • Android 8.0+
  • Permanent internet (WiFi/4G)
  • Phone on charger 24/7
  • Real IP (or NAT traversal through backend)

Authorization and activation:
  ┌─────────────────────────────────────────────────────┐
  │  1. User enters login/password                       │
  │  2. Backend returns tenant_config:                  │
  │     {                                               │
  │       "operator_mode": true,      // always        │
  │       "server_mode": true/false,  // by tariff     │
  │       "channels": ["whatsapp", "avito", "max"]     │
  │     }                                               │
  │  3. Application enables required modes              │
  └─────────────────────────────────────────────────────┘

Security (for app stores):
  • SERVER mode hidden in UI
  • Activated only through backend flag
  • No obvious signs of automation in code
  • All traffic encrypted

Backend routing logic:
  IF tenant.server_mode AND device.is_online:
      route_via_smartphone(tenant.device_id)
  ELSE:
      route_via_paid_api(tenant.api_keys)
```

### Price Parser

```
Sources (examples):
  • mobileboost.ru
  • all-spares.ru
  • gsm-komplekt.ru
  • opt-mobile.ru

Parsing frequency:
  • Daily (at night)
  • On tenant request

Normalization:
  • Model directory (iPhone 12, iPhone 12 Pro, ...)
  • Type directory (Original, OEM, Copy, OLED, LCD)
  • Color directory
  • Fuzzy matching for names
```

### Voice → Graph

```
Components:
  ├── Call recording (Asterisk/FreePBX or cloud)
  ├── Transcription (Whisper API)
  ├── AI Extraction (device, problem, agreements)
  ├── Neo4j Sync (new nodes and relationships)
  └── Messenger Continuation (Telegram/WhatsApp)

Telephony:
  Option A: Own PBX (Asterisk)
  Option B: Cloud PBX (Mango, Zadarma)
  Option C: Smartphone app (mic recording)
```

---

## Monetization

### Pricing (draft)

| Plan | Price | What's included |
|------|-------|-----------------|
| **Free** | 0₽ | 1 channel, 100 messages/month, 7 day history |
| **Minimal** | 300-500₽ | 3 channels, 1000 messages, AI assist |
| **Basic** | 1500-2000₽ | 7 channels, unlimited, AI auto, price parser |
| **Business** | 4000+₽ | + smartphone-server, integrations, voice |

### Unit economics

```
Costs per 1 tenant (Basic):
  • WhatsApp API: 600₽ (or 0₽ with smartphone-server)
  • AI (OpenAI): ~200-500₽/month
  • Infrastructure: ~100₽/month
  • Total: 400-1200₽/month

Margin:
  • Basic (1500₽): 300-1100₽ (20-70%)
  • Business (4000₽): 2800-3600₽ (70-90%)
```

---

## WOW Demo Scenario

```
For first client (service owner):

1. "Text me in Telegram as a client — 'hi, broke iPhone screen'"
   → AI responds in 10 sec, asks model, names prices

2. "Now call this number and say the same"
   → After call show: recording, transcript, data in graph
   → "Now text in messenger"
   → AI continues with call context

3. "Here's QR — scan it"
   → Chat opened, system recognized you as the caller

4. "Look — prices changed in the store"
   → In 5 minutes AI names new price

5. "And all this — your channels are free. WhatsApp, Avito — 0₽"
   → Show smartphone-server
```

---

## Link to CORE_NEW Architecture

See `CORE_NEW/docs/` for technical documentation:

| Feature | Architecture Blocks |
|---------|-------------------|
| Omnichannel | Channel Layer (7 MCP) |
| AI auto/assist | Core (Dialog Engine + AI Pipeline) |
| Client history | Graph (Neo4j) |
| Voice → Graph | Input Contour + Graph + Core |
| Price parser | New block: Price Engine |
| Smartphone-server | New block: Device Gateway |
| Integrations | New block: External Integrations |

---

## Next Steps

1. **Graph questions** — resolve 4 open questions (Register vs Tracker, direction, enrichment)
2. **Core block** — documentation and adaptation
3. **Operator Web App** — start development (blocker for MVP)
4. **Price parser** — normalization prototype
5. **Voice** — choose telephony (Asterisk vs cloud vs smartphone)
