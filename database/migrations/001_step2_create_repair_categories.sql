-- ============================================================================
-- ШАГ 2: Создание таблицы repair_categories
-- ============================================================================

CREATE TABLE IF NOT EXISTS repair_categories (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id UUID REFERENCES tenants(id),
  name VARCHAR(100) NOT NULL,
  code VARCHAR(50) UNIQUE,
  description TEXT,
  icon VARCHAR(50),
  display_order INT DEFAULT 0,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_repair_categories_tenant ON repair_categories(tenant_id);
CREATE INDEX IF NOT EXISTS idx_repair_categories_active ON repair_categories(is_active);

COMMENT ON TABLE repair_categories IS 'Категории ремонта: Дисплей, АКБ, Корпус, Звук, Связь и т.д.';
COMMENT ON COLUMN repair_categories.code IS 'Уникальный код для программного использования';

SELECT 'Таблица repair_categories создана' as result;
