-- ============================================================================
-- Migration 006 Part 4: Pricing Engine (3 tables)
-- Date: 2025-11-23
-- Description: Price lists and automatic cost calculation
-- ============================================================================

-- ============================================================================
-- 1. price_lists — Прайс-листы поставщиков
-- ============================================================================

CREATE TABLE IF NOT EXISTS price_lists (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    supplier_name varchar(255) NOT NULL,
    file_name varchar(255),
    file_url text,
    uploaded_at timestamptz DEFAULT now(),
    valid_from timestamptz,
    valid_until timestamptz,
    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_price_lists_tenant ON price_lists(tenant_id);
CREATE INDEX IF NOT EXISTS idx_price_lists_tenant_active ON price_lists(tenant_id, is_active);

COMMENT ON TABLE price_lists IS 'Прайс-листы от поставщиков запчастей';

-- ============================================================================
-- 2. price_list_items — Позиции прайс-листов
-- ============================================================================

CREATE TABLE IF NOT EXISTS price_list_items (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    price_list_id uuid NOT NULL REFERENCES price_lists(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    part_name varchar(500) NOT NULL,
    part_number varchar(255),
    description text,
    price numeric(10, 2) NOT NULL,
    currency varchar(10) DEFAULT 'RUB',
    availability varchar(50) DEFAULT 'in_stock',
    quantity int,
    raw_data jsonb,
    created_at timestamptz DEFAULT now(),

    CONSTRAINT chk_availability CHECK (availability IN ('in_stock', 'out_of_stock', 'on_order'))
);

CREATE INDEX IF NOT EXISTS idx_price_list_items_list ON price_list_items(price_list_id);
CREATE INDEX IF NOT EXISTS idx_price_list_items_tenant ON price_list_items(tenant_id);
CREATE INDEX IF NOT EXISTS idx_price_list_items_part_name ON price_list_items(part_name);
CREATE INDEX IF NOT EXISTS idx_price_list_items_part_number ON price_list_items(part_number);

COMMENT ON TABLE price_list_items IS 'Детальные позиции из прайс-листов';
COMMENT ON COLUMN price_list_items.raw_data IS 'Сырые данные из прайса в формате JSON';

-- ============================================================================
-- 3. price_mappings — Привязка деталей к устройствам
-- ============================================================================

CREATE TABLE IF NOT EXISTS price_mappings (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    price_list_item_id uuid NOT NULL REFERENCES price_list_items(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES tenants(id) ON DELETE CASCADE,
    brand_id uuid REFERENCES brands(id),
    model_id uuid REFERENCES models(id),
    repair_category_id uuid REFERENCES repair_categories(id),
    issue_type_id uuid REFERENCES issue_types(id),
    retail_markup_percent numeric(5, 2) DEFAULT 50,
    retail_price numeric(10, 2),
    is_verified boolean DEFAULT false,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_price_mappings_tenant ON price_mappings(tenant_id);
CREATE INDEX IF NOT EXISTS idx_price_mappings_brand_model ON price_mappings(brand_id, model_id);
CREATE INDEX IF NOT EXISTS idx_price_mappings_category ON price_mappings(repair_category_id);
CREATE INDEX IF NOT EXISTS idx_price_mappings_issue_type ON price_mappings(issue_type_id);

COMMENT ON TABLE price_mappings IS 'Маппинг деталей из прайсов на бренды/модели/категории для подсказок оператору';
COMMENT ON COLUMN price_mappings.brand_id IS 'NULL = применимо ко всем брендам';
COMMENT ON COLUMN price_mappings.model_id IS 'NULL = применимо ко всем моделям бренда';

-- ============================================================================
-- Migration complete: Part 4
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 006 Part 4 completed successfully: Pricing Engine (3 tables)';
END $$;
