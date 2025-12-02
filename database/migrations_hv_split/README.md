# H/V Split Database Migration

## Overview

This migration restructures the database from a flat public schema to a Horizontal/Vertical architecture:

- **horizontal** schema: Platform-level tables shared across all verticals
- **vertical_repair** schema: Tables specific to the phone repair vertical

## Migration Files

| File | Description | Tables |
|------|-------------|--------|
| 001_create_schemas.sql | Create schemas and enum types | - |
| 002_horizontal_core.sql | Core tenant infrastructure | 8 tables |
| 003_horizontal_appeals.sql | Appeals and messaging | 4 tables |
| 004_horizontal_promo_utm.sql | Promo materials, UTM tracking | 7 tables |
| 005_horizontal_booking.sql | Online booking system | 4 tables |
| 006_horizontal_ai_voice.sql | AI and voice infrastructure | 4 tables |
| 007_horizontal_crm_ttl.sql | CRM integration, data retention | 5 tables |
| 008_vertical_repair_dictionaries.sql | Repair vertical dictionaries | 5 tables |
| 009_vertical_repair_appeals.sql | Repair appeal devices/repairs | 3 tables |
| 010_vertical_repair_pricing.sql | Pricing system | 4 tables |
| 011_vertical_repair_config.sql | Vertical config, CRM mappings | 3 tables |
| 012_data_migration.sql | Migrate existing data | - |
| 013_views_functions.sql | Helper views and functions | - |

**Total: 47 tables + views + functions**

## Schema Structure

### Horizontal Schema (~32 tables)

```
horizontal/
├── Core (8)
│   ├── tenants
│   ├── tenant_settings
│   ├── tenant_verticals
│   ├── leads
│   ├── channels
│   ├── operators
│   ├── operator_devices
│   └── sessions
├── Appeals (4)
│   ├── appeal_stages
│   ├── appeals
│   ├── appeal_stage_transitions
│   └── messages_history
├── Promo & UTM (7)
│   ├── promo_materials
│   ├── promo_triggers
│   ├── promo_history
│   ├── utm_sources
│   ├── utm_campaigns
│   ├── utm_ads
│   └── remarketing_audiences
├── Booking (4)
│   ├── booking_settings
│   ├── booking_slots
│   ├── bookings
│   └── booking_reminders
├── AI & Voice (4)
│   ├── ai_chat_memory
│   ├── voice_transcriptions
│   ├── ai_extraction_tasks
│   └── ai_prompts_config
└── CRM & TTL (5)
    ├── crm_integrations
    ├── crm_sync_history
    ├── crm_field_mappings_universal
    ├── data_retention_policies
    └── archived_data
```

### Vertical Repair Schema (~15 tables)

```
vertical_repair/
├── Dictionaries (5)
│   ├── brands
│   ├── device_types
│   ├── models
│   ├── repair_categories
│   └── issue_types
├── Appeal Data (3)
│   ├── appeal_devices
│   ├── appeal_repairs
│   └── appeal_qr_codes
├── Pricing (4)
│   ├── price_lists
│   ├── price_list_items
│   ├── price_mappings
│   └── repair_prices
└── Config (3)
    ├── config
    ├── crm_field_mappings
    └── ai_extraction_prompts
```

## Execution

### New Installation (no existing data)

```bash
cd database/migrations_hv_split
psql -h localhost -U postgres -d batterycrm -f EXECUTE_ALL.sql
```

### Migration from Existing Database

1. **Backup first!**
```bash
pg_dump -h localhost -U postgres batterycrm > backup_before_hv_split.sql
```

2. **Run structure migrations (001-011, 013)**
```bash
psql -h localhost -U postgres -d batterycrm <<EOF
\i 001_create_schemas.sql
\i 002_horizontal_core.sql
\i 003_horizontal_appeals.sql
\i 004_horizontal_promo_utm.sql
\i 005_horizontal_booking.sql
\i 006_horizontal_ai_voice.sql
\i 007_horizontal_crm_ttl.sql
\i 008_vertical_repair_dictionaries.sql
\i 009_vertical_repair_appeals.sql
\i 010_vertical_repair_pricing.sql
\i 011_vertical_repair_config.sql
\i 013_views_functions.sql
EOF
```

3. **Run data migration (012)**
```bash
psql -h localhost -U postgres -d batterycrm -f 012_data_migration.sql
```

## Compatibility

After migration, compatibility views are created in the `public` schema that point to the new tables. Existing code that references `public.tenants`, `public.appeals`, etc. will continue to work.

## Key Changes

### Appeals
- `appeals` now has `vertical_id` to identify which vertical the appeal belongs to
- Device and repair data moved to `vertical_repair.appeal_devices` and `vertical_repair.appeal_repairs`
- QR codes stored in `vertical_repair.appeal_qr_codes`

### AI Extraction
- Generic AI infrastructure in `horizontal` (ai_extraction_tasks, ai_prompts_config)
- Vertical-specific prompts in `vertical_repair.ai_extraction_prompts`
- AI worker queue in `horizontal.ai_extraction_tasks` with Redis integration

### CRM Integration
- CRM connections in `horizontal.crm_integrations`
- Universal field mappings in `horizontal.crm_field_mappings_universal`
- Vertical-specific mappings in `vertical_repair.crm_field_mappings`

### Booking
- All booking functionality in `horizontal` schema
- Vertical-specific slot rules stored in `booking_settings.slot_rules` JSONB

## Helper Functions

### Horizontal Functions
- `horizontal.fn_get_next_stage(tenant_id, current_stage_id)` - Get next funnel stage
- `horizontal.fn_move_appeal_stage(appeal_id, to_stage_id, ...)` - Move appeal with history
- `horizontal.fn_get_or_create_lead(tenant_id, phone, ...)` - Get or create lead

### Vertical Repair Functions
- `vertical_repair.fn_find_brand(tenant_id, brand_text)` - Fuzzy brand match
- `vertical_repair.fn_find_model(tenant_id, brand_id, model_text)` - Fuzzy model match
- `vertical_repair.fn_get_repair_price(...)` - Get estimated repair price
- `vertical_repair.fn_calculate_appeal_total(appeal_id)` - Calculate appeal total

## Views

### Horizontal Views
- `horizontal.v_appeals_full` - Complete appeal info with lead, channel, stage
- `horizontal.v_funnel_stats` - Funnel statistics by stage
- `horizontal.v_operator_workload` - Operator workload metrics

### Vertical Repair Views
- `vertical_repair.v_repair_appeals_full` - Complete repair appeal with devices
- `vertical_repair.v_brand_model_stats` - Brand/model popularity
- `vertical_repair.v_repair_category_stats` - Repair category statistics
