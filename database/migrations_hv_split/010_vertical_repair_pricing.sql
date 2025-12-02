-- ============================================================================
-- H/V SPLIT Migration 010: Vertical Repair - Pricing System
-- Date: 2025-11-25
-- Description: Price lists, price items, supplier price mappings
-- ============================================================================

SET search_path TO vertical_repair, horizontal, public;

-- ============================================================================
-- 1. price_lists - Supplier price lists
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.price_lists (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Supplier info
    supplier_name varchar(255) NOT NULL,
    supplier_code varchar(100),
    supplier_url text,
    supplier_contact jsonb,

    -- List info
    name varchar(255) NOT NULL,
    description text,
    currency varchar(10) DEFAULT 'RUB',

    -- Import settings
    import_format varchar(50) DEFAULT 'excel',
    import_mapping jsonb,
    last_import_at timestamptz,
    import_url text,
    auto_import boolean DEFAULT false,
    import_schedule varchar(100),

    -- Status
    is_active boolean DEFAULT true,
    priority int DEFAULT 0,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_supplier_code UNIQUE (tenant_id, supplier_code),
    CONSTRAINT chk_import_format CHECK (import_format IN ('excel', 'csv', 'json', 'xml', 'api'))
);

CREATE INDEX IF NOT EXISTS idx_price_lists_tenant ON vertical_repair.price_lists(tenant_id);
CREATE INDEX IF NOT EXISTS idx_price_lists_tenant_active ON vertical_repair.price_lists(tenant_id, is_active);
CREATE INDEX IF NOT EXISTS idx_price_lists_supplier ON vertical_repair.price_lists(supplier_code);

COMMENT ON TABLE vertical_repair.price_lists IS 'Supplier price lists for parts';
COMMENT ON COLUMN vertical_repair.price_lists.import_mapping IS 'JSON mapping: {column_name: field_name}';
COMMENT ON COLUMN vertical_repair.price_lists.supplier_contact IS 'JSON: {phone, email, manager, address}';

-- ============================================================================
-- 2. price_list_items - Items in price lists
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.price_list_items (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    price_list_id uuid NOT NULL REFERENCES vertical_repair.price_lists(id) ON DELETE CASCADE,
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Part identification
    part_number varchar(255),
    supplier_sku varchar(255),
    barcode varchar(255),

    -- Part info
    name varchar(500) NOT NULL,
    description text,
    category varchar(255),

    -- Device compatibility
    brand_id uuid REFERENCES vertical_repair.brands(id),
    model_id uuid REFERENCES vertical_repair.models(id),
    compatible_models jsonb DEFAULT '[]',

    -- Pricing
    purchase_price numeric(10, 2),
    retail_price numeric(10, 2),
    wholesale_price numeric(10, 2),
    currency varchar(10) DEFAULT 'RUB',

    -- Stock
    in_stock boolean DEFAULT true,
    stock_quantity int,
    min_order_quantity int DEFAULT 1,

    -- Quality
    quality_grade varchar(50),
    warranty_days int,

    -- Availability
    delivery_days int,
    is_available boolean DEFAULT true,

    -- Import tracking
    import_row_hash varchar(64),
    last_import_at timestamptz,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT chk_quality_grade CHECK (quality_grade IS NULL OR quality_grade IN ('original', 'oem', 'compatible', 'refurbished', 'copy'))
);

CREATE INDEX IF NOT EXISTS idx_price_items_price_list ON vertical_repair.price_list_items(price_list_id);
CREATE INDEX IF NOT EXISTS idx_price_items_tenant ON vertical_repair.price_list_items(tenant_id);
CREATE INDEX IF NOT EXISTS idx_price_items_part_number ON vertical_repair.price_list_items(part_number);
CREATE INDEX IF NOT EXISTS idx_price_items_supplier_sku ON vertical_repair.price_list_items(supplier_sku);
CREATE INDEX IF NOT EXISTS idx_price_items_brand_model ON vertical_repair.price_list_items(brand_id, model_id);
CREATE INDEX IF NOT EXISTS idx_price_items_name ON vertical_repair.price_list_items USING gin(to_tsvector('russian', name));
CREATE INDEX IF NOT EXISTS idx_price_items_available ON vertical_repair.price_list_items(is_available) WHERE is_available = true;

COMMENT ON TABLE vertical_repair.price_list_items IS 'Individual parts/items in supplier price lists';
COMMENT ON COLUMN vertical_repair.price_list_items.compatible_models IS 'JSON array of model names for cross-compatibility';
COMMENT ON COLUMN vertical_repair.price_list_items.import_row_hash IS 'Hash for detecting changes during re-import';

-- ============================================================================
-- 3. price_mappings - Map price items to repairs
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.price_mappings (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Source item
    price_list_item_id uuid REFERENCES vertical_repair.price_list_items(id) ON DELETE CASCADE,

    -- Target (what this part is used for)
    repair_category_id uuid REFERENCES vertical_repair.repair_categories(id),
    issue_type_id uuid REFERENCES vertical_repair.issue_types(id),
    model_id uuid REFERENCES vertical_repair.models(id),

    -- Markup
    markup_percent numeric(5, 2) DEFAULT 0,
    markup_fixed numeric(10, 2) DEFAULT 0,
    final_price numeric(10, 2),

    -- Usage
    is_default boolean DEFAULT false,
    priority int DEFAULT 0,

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_price_mappings_tenant ON vertical_repair.price_mappings(tenant_id);
CREATE INDEX IF NOT EXISTS idx_price_mappings_item ON vertical_repair.price_mappings(price_list_item_id);
CREATE INDEX IF NOT EXISTS idx_price_mappings_category ON vertical_repair.price_mappings(repair_category_id);
CREATE INDEX IF NOT EXISTS idx_price_mappings_issue ON vertical_repair.price_mappings(issue_type_id);
CREATE INDEX IF NOT EXISTS idx_price_mappings_model ON vertical_repair.price_mappings(model_id);

COMMENT ON TABLE vertical_repair.price_mappings IS 'Maps price list items to specific repair types/models';

-- ============================================================================
-- 4. repair_prices - Custom repair prices (not from suppliers)
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.repair_prices (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    -- Target
    brand_id uuid REFERENCES vertical_repair.brands(id),
    model_id uuid REFERENCES vertical_repair.models(id),
    repair_category_id uuid REFERENCES vertical_repair.repair_categories(id),
    issue_type_id uuid REFERENCES vertical_repair.issue_types(id),

    -- Pricing
    min_price numeric(10, 2),
    max_price numeric(10, 2),
    default_price numeric(10, 2),

    -- Time
    estimated_minutes int,

    -- Warranty
    warranty_days int DEFAULT 30,

    -- Notes
    notes text,

    is_active boolean DEFAULT true,
    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_repair_price UNIQUE (tenant_id, brand_id, model_id, repair_category_id, issue_type_id)
);

CREATE INDEX IF NOT EXISTS idx_repair_prices_tenant ON vertical_repair.repair_prices(tenant_id);
CREATE INDEX IF NOT EXISTS idx_repair_prices_brand_model ON vertical_repair.repair_prices(brand_id, model_id);
CREATE INDEX IF NOT EXISTS idx_repair_prices_category ON vertical_repair.repair_prices(repair_category_id);

COMMENT ON TABLE vertical_repair.repair_prices IS 'Custom repair prices defined by tenant (not from supplier price lists)';

-- ============================================================================
-- Migration complete: 010
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 010 completed: Vertical Repair pricing system';
END $$;
