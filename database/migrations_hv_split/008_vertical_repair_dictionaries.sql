-- ============================================================================
-- H/V SPLIT Migration 008: Vertical Repair - Dictionaries
-- Date: 2025-11-25
-- Description: Brands, models, device types, repair categories, issue types
-- ============================================================================

SET search_path TO vertical_repair, horizontal, public;

-- ============================================================================
-- 1. brands - Device brands
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.brands (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    normalized_name varchar(255),
    logo_url text,

    -- For AI matching
    aliases jsonb DEFAULT '[]',

    is_active boolean DEFAULT true,
    "order" int DEFAULT 0,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_brand_name UNIQUE (tenant_id, name)
);

CREATE INDEX IF NOT EXISTS idx_brands_tenant ON vertical_repair.brands(tenant_id);
CREATE INDEX IF NOT EXISTS idx_brands_tenant_active ON vertical_repair.brands(tenant_id, is_active);
CREATE INDEX IF NOT EXISTS idx_brands_normalized ON vertical_repair.brands(normalized_name);

COMMENT ON TABLE vertical_repair.brands IS 'Device brands (Apple, Samsung, Xiaomi, etc)';
COMMENT ON COLUMN vertical_repair.brands.aliases IS 'Alternative names for AI matching: ["Эппл", "Айфон"]';

-- ============================================================================
-- 2. device_types - Types of devices
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.device_types (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    normalized_name varchar(255),
    icon varchar(255),

    aliases jsonb DEFAULT '[]',

    is_active boolean DEFAULT true,
    "order" int DEFAULT 0,

    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_device_type UNIQUE (tenant_id, name)
);

CREATE INDEX IF NOT EXISTS idx_device_types_tenant ON vertical_repair.device_types(tenant_id);

COMMENT ON TABLE vertical_repair.device_types IS 'Device types (smartphone, tablet, laptop, watch, etc)';

-- ============================================================================
-- 3. models - Device models
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.models (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    brand_id uuid NOT NULL REFERENCES vertical_repair.brands(id) ON DELETE CASCADE,
    device_type_id uuid REFERENCES vertical_repair.device_types(id),

    name varchar(255) NOT NULL,
    normalized_name varchar(255),

    -- Specs
    release_year int,
    specs jsonb,

    -- For AI matching
    aliases jsonb DEFAULT '[]',

    is_active boolean DEFAULT true,
    "order" int DEFAULT 0,

    created_at timestamptz DEFAULT now(),
    updated_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_brand_model UNIQUE (tenant_id, brand_id, name)
);

CREATE INDEX IF NOT EXISTS idx_models_tenant ON vertical_repair.models(tenant_id);
CREATE INDEX IF NOT EXISTS idx_models_brand ON vertical_repair.models(brand_id);
CREATE INDEX IF NOT EXISTS idx_models_tenant_active ON vertical_repair.models(tenant_id, is_active);
CREATE INDEX IF NOT EXISTS idx_models_normalized ON vertical_repair.models(normalized_name);

COMMENT ON TABLE vertical_repair.models IS 'Device models (iPhone 14 Pro Max, Galaxy S24 Ultra, etc)';

-- ============================================================================
-- 4. repair_categories - Repair categories
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.repair_categories (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    description text,
    icon varchar(255),

    -- For AI matching
    aliases jsonb DEFAULT '[]',
    keywords jsonb DEFAULT '[]',

    is_active boolean DEFAULT true,
    "order" int DEFAULT 0,

    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_repair_category UNIQUE (tenant_id, name)
);

CREATE INDEX IF NOT EXISTS idx_repair_categories_tenant ON vertical_repair.repair_categories(tenant_id);
CREATE INDEX IF NOT EXISTS idx_repair_categories_tenant_active ON vertical_repair.repair_categories(tenant_id, is_active);

COMMENT ON TABLE vertical_repair.repair_categories IS 'Repair categories (Display, Battery, Body, etc)';

-- ============================================================================
-- 5. issue_types - Issue types within categories
-- ============================================================================

CREATE TABLE IF NOT EXISTS vertical_repair.issue_types (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id uuid NOT NULL REFERENCES horizontal.tenants(id) ON DELETE CASCADE,
    repair_category_id uuid NOT NULL REFERENCES vertical_repair.repair_categories(id) ON DELETE CASCADE,

    name varchar(255) NOT NULL,
    description text,

    -- For AI matching
    aliases jsonb DEFAULT '[]',
    keywords jsonb DEFAULT '[]',
    symptoms jsonb DEFAULT '[]',

    -- Default pricing (can be overridden)
    default_estimated_time_minutes int,
    default_estimated_cost numeric(10, 2),

    is_active boolean DEFAULT true,
    "order" int DEFAULT 0,

    created_at timestamptz DEFAULT now(),

    CONSTRAINT unique_tenant_category_issue UNIQUE (tenant_id, repair_category_id, name)
);

CREATE INDEX IF NOT EXISTS idx_issue_types_tenant ON vertical_repair.issue_types(tenant_id);
CREATE INDEX IF NOT EXISTS idx_issue_types_category ON vertical_repair.issue_types(repair_category_id);
CREATE INDEX IF NOT EXISTS idx_issue_types_tenant_active ON vertical_repair.issue_types(tenant_id, is_active);

COMMENT ON TABLE vertical_repair.issue_types IS 'Specific issue types within repair categories';

-- ============================================================================
-- 6. Insert default brands (global)
-- ============================================================================

INSERT INTO vertical_repair.brands (tenant_id, name, normalized_name, aliases, "order")
SELECT
    t.id,
    b.name,
    b.normalized_name,
    b.aliases::jsonb,
    b.order_num
FROM horizontal.tenants t
CROSS JOIN (
    VALUES
        ('Apple', 'apple', '["Эппл", "Айфон", "Макбук", "Айпад"]', 1),
        ('Samsung', 'samsung', '["Самсунг", "Галакси"]', 2),
        ('Xiaomi', 'xiaomi', '["Сяоми", "Ксиаоми", "Редми", "Поко"]', 3),
        ('Huawei', 'huawei', '["Хуавей", "Хонор"]', 4),
        ('Honor', 'honor', '["Хонор"]', 5),
        ('Realme', 'realme', '["Реалми"]', 6),
        ('OPPO', 'oppo', '["Оппо"]', 7),
        ('Vivo', 'vivo', '["Виво"]', 8),
        ('OnePlus', 'oneplus', '["ВанПлюс", "Ван Плюс"]', 9),
        ('Google', 'google', '["Гугл", "Пиксель"]', 10),
        ('Sony', 'sony', '["Сони", "Xperia"]', 11),
        ('LG', 'lg', '["ЛЖ", "ЭлДжи"]', 12),
        ('Motorola', 'motorola', '["Моторола"]', 13),
        ('Nokia', 'nokia', '["Нокиа"]', 14),
        ('ASUS', 'asus', '["Асус"]', 15),
        ('Lenovo', 'lenovo', '["Леново"]', 16),
        ('ZTE', 'zte', '["ЗТЕ"]', 17),
        ('Meizu', 'meizu', '["Мейзу"]', 18),
        ('Nothing', 'nothing', '["Нафинг"]', 19),
        ('Other', 'other', '["Другой", "Прочее"]', 100)
) AS b(name, normalized_name, aliases, order_num)
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.brands br
    WHERE br.tenant_id = t.id AND br.name = b.name
);

-- ============================================================================
-- 7. Insert default device types
-- ============================================================================

INSERT INTO vertical_repair.device_types (tenant_id, name, normalized_name, aliases, "order")
SELECT
    t.id,
    dt.name,
    dt.normalized_name,
    dt.aliases::jsonb,
    dt.order_num
FROM horizontal.tenants t
CROSS JOIN (
    VALUES
        ('Smartphone', 'smartphone', '["Телефон", "Смартфон", "Мобильный", "Сотовый"]', 1),
        ('Tablet', 'tablet', '["Планшет", "Таблет", "Айпад", "iPad"]', 2),
        ('Laptop', 'laptop', '["Ноутбук", "Лэптоп", "Макбук", "MacBook"]', 3),
        ('Smartwatch', 'smartwatch', '["Часы", "Смарт-часы", "Apple Watch"]', 4),
        ('Earphones', 'earphones', '["Наушники", "AirPods", "Эирподс"]', 5),
        ('E-Reader', 'ereader', '["Читалка", "Электронная книга", "Kindle"]', 6),
        ('Game Console', 'console', '["Приставка", "Консоль", "PlayStation", "Xbox", "Nintendo"]', 7),
        ('Desktop PC', 'desktop', '["Компьютер", "ПК", "Системный блок", "iMac"]', 8),
        ('Other', 'other', '["Другое", "Прочее"]', 100)
) AS dt(name, normalized_name, aliases, order_num)
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.device_types dtt
    WHERE dtt.tenant_id = t.id AND dtt.name = dt.name
);

-- ============================================================================
-- 8. Insert default repair categories
-- ============================================================================

INSERT INTO vertical_repair.repair_categories (tenant_id, name, description, icon, keywords, "order")
SELECT
    t.id,
    rc.name,
    rc.description,
    rc.icon,
    rc.keywords::jsonb,
    rc.order_num
FROM horizontal.tenants t
CROSS JOIN (
    VALUES
        ('Display', 'Screen, touchscreen, glass replacement', 'monitor', '["экран", "дисплей", "тачскрин", "стекло", "разбит", "треснул", "не показывает", "полосы"]', 1),
        ('Battery', 'Battery replacement', 'battery-full', '["батарея", "аккумулятор", "зарядка", "разряжается", "не держит", "вздулся"]', 2),
        ('Body', 'Body repair, back cover replacement', 'device-mobile', '["корпус", "крышка", "задняя", "рамка", "погнут"]', 3),
        ('Sound', 'Speaker, microphone, headphone jack', 'volume-up', '["звук", "динамик", "микрофон", "не слышно", "тихо", "хрипит"]', 4),
        ('Connectivity', 'Wi-Fi, Bluetooth, cellular, GPS', 'wifi', '["связь", "wifi", "вайфай", "bluetooth", "блютуз", "сеть", "gps", "сим"]', 5),
        ('Camera', 'Main and front camera', 'camera', '["камера", "фото", "фронталка", "основная", "мутное", "не фокусируется"]', 6),
        ('Ports', 'Charging port, USB, Lightning', 'plug', '["разъем", "порт", "зарядка", "usb", "lightning", "type-c", "не заряжается"]', 7),
        ('Motherboard', 'Board repair, chip replacement', 'cpu', '["плата", "материнская", "чип", "микросхема", "не включается"]', 8),
        ('Software', 'Firmware, unlock, setup', 'code', '["прошивка", "софт", "программа", "разблокировка", "icloud", "google", "пароль"]', 9),
        ('Other', 'Other issues', 'dots-horizontal', '["другое", "прочее"]', 100)
) AS rc(name, description, icon, keywords, order_num)
WHERE NOT EXISTS (
    SELECT 1 FROM vertical_repair.repair_categories rcc
    WHERE rcc.tenant_id = t.id AND rcc.name = rc.name
);

-- ============================================================================
-- Migration complete: 008
-- ============================================================================

DO $$
BEGIN
    RAISE NOTICE 'Migration 008 completed: Vertical Repair dictionaries (brands, models, categories)';
END $$;
