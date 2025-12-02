-- ============================================================================
-- ШАГ 5: Создание таблицы appeal_repairs
-- ============================================================================

CREATE TABLE IF NOT EXISTS appeal_repairs (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  appeal_id UUID NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
  appeal_device_id UUID NOT NULL REFERENCES appeal_devices(id) ON DELETE CASCADE,
  tenant_id UUID NOT NULL REFERENCES tenants(id),
  repair_category_id UUID REFERENCES repair_categories(id),
  repair_category_name VARCHAR(100),
  issue_type_id UUID REFERENCES issue_types(id),
  issue_type_name VARCHAR(100),
  repair_type_id UUID REFERENCES repair_types(id),
  repair_type_name VARCHAR(255),
  parts_owner VARCHAR(50) DEFAULT 'наша',
  estimated_cost DECIMAL(10,2),
  estimated_time VARCHAR(50),
  status VARCHAR(50) DEFAULT 'pending',
  discussion_status VARCHAR(50) DEFAULT 'pending',
  priority INT DEFAULT 1,
  problem_description TEXT,
  notes TEXT,
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_appeal_repairs_appeal ON appeal_repairs(appeal_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_device ON appeal_repairs(appeal_device_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_tenant ON appeal_repairs(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_category ON appeal_repairs(repair_category_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_issue ON appeal_repairs(issue_type_id);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_status ON appeal_repairs(status);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_discussion ON appeal_repairs(discussion_status);
CREATE INDEX IF NOT EXISTS idx_appeal_repairs_priority ON appeal_repairs(priority);

COMMENT ON TABLE appeal_repairs IS 'Ремонты/проблемы для каждого устройства в заявке';
COMMENT ON COLUMN appeal_repairs.discussion_status IS 'Статус обсуждения: pending, discussing, confirmed, rejected';
COMMENT ON COLUMN appeal_repairs.priority IS 'Приоритет обсуждения (1-высокий, 2-средний, 3-низкий)';
COMMENT ON COLUMN appeal_repairs.parts_owner IS 'Чья запчасть: наша, клиентская, не требуется';

SELECT 'Таблица appeal_repairs создана' as result;
