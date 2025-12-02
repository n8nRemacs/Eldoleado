-- ============================================================================
-- ШАГ 3: Создание таблицы issue_types
-- ============================================================================

CREATE TABLE issue_types (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id UUID REFERENCES tenants(id),
  repair_category_id UUID NOT NULL REFERENCES repair_categories(id) ON DELETE CASCADE,
  name VARCHAR(100) NOT NULL,
  code VARCHAR(50),
  description TEXT,
  default_cost DECIMAL(10,2),
  default_time VARCHAR(50),
  display_order INT DEFAULT 0,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_issue_types_tenant ON issue_types(tenant_id);
CREATE INDEX IF NOT EXISTS idx_issue_types_category ON issue_types(repair_category_id);
CREATE INDEX IF NOT EXISTS idx_issue_types_active ON issue_types(is_active);

CREATE UNIQUE INDEX IF NOT EXISTS idx_issue_types_unique
ON issue_types(tenant_id, repair_category_id, code)
WHERE code IS NOT NULL;

COMMENT ON TABLE issue_types IS 'Конкретные проблемы для каждой категории ремонта';
COMMENT ON COLUMN issue_types.repair_category_id IS 'Привязка к категории (напр. "Разбит" → "Дисплей")';

SELECT 'Таблица issue_types создана' as result;
