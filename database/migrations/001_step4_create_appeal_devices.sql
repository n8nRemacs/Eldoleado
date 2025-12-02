-- ============================================================================
-- ШАГ 4: Создание таблицы appeal_devices
-- ============================================================================

CREATE TABLE IF NOT EXISTS appeal_devices (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  appeal_id UUID NOT NULL REFERENCES appeals(id) ON DELETE CASCADE,
  tenant_id UUID NOT NULL REFERENCES tenants(id),
  brand_id UUID REFERENCES brands(id),
  model_id UUID REFERENCES models(id),
  device_type_id UUID REFERENCES device_types(id),
  phone_model VARCHAR(255),
  serial_number VARCHAR(255),
  imei VARCHAR(255),
  status VARCHAR(50) DEFAULT 'active',
  discussion_status VARCHAR(50) DEFAULT 'pending',
  device_order INT DEFAULT 1,
  notes TEXT,
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_appeal_devices_appeal ON appeal_devices(appeal_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_tenant ON appeal_devices(tenant_id);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_status ON appeal_devices(status);
CREATE INDEX IF NOT EXISTS idx_appeal_devices_discussion ON appeal_devices(discussion_status);

COMMENT ON TABLE appeal_devices IS 'Устройства в рамках одной заявки (поддержка нескольких устройств)';
COMMENT ON COLUMN appeal_devices.discussion_status IS 'Статус обсуждения: pending, discussing, discussed, cancelled';
COMMENT ON COLUMN appeal_devices.device_order IS 'Порядок упоминания клиентом (1, 2, 3...)';

SELECT 'Таблица appeal_devices создана' as result;
