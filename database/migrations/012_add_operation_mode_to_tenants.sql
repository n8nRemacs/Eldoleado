-- Migration: Add operation_mode to tenants table
-- Description: Global AI mode setting per tenant (auto/assist)

ALTER TABLE tenants ADD COLUMN IF NOT EXISTS operation_mode TEXT DEFAULT 'assist';

COMMENT ON COLUMN tenants.operation_mode IS 'AI operation mode: auto (AI responds directly) or assist (AI suggests, operator confirms)';
