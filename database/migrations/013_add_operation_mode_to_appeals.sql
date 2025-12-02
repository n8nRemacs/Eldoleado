-- Migration 013: Add operation_mode to appeals table
-- Description: AI operation mode per appeal (auto/assist)
-- Date: 2025-11-29

-- 1. Add operation_mode column to appeals
ALTER TABLE appeals
ADD COLUMN IF NOT EXISTS operation_mode TEXT DEFAULT NULL;

-- 2. Add comment
COMMENT ON COLUMN appeals.operation_mode IS
'AI operation mode per appeal: auto (AI responds directly) or assist (AI suggests, operator confirms). NULL = inherit from tenant default';

-- 3. Create index for filtering
CREATE INDEX IF NOT EXISTS idx_appeals_operation_mode ON appeals(operation_mode);

-- 4. Backfill existing appeals with tenant default (optional)
-- Uncomment if you want to set mode for all existing appeals:
/*
UPDATE appeals a
SET operation_mode = COALESCE(t.operation_mode, 'assist')
FROM tenants t
WHERE a.tenant_id = t.id
  AND a.operation_mode IS NULL;
*/

-- 5. Verification query (run after migration to check):
-- SELECT operation_mode, COUNT(*) FROM appeals GROUP BY operation_mode;
