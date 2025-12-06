-- Multi-tenant MCP channel accounts storage
-- Created: 2025-12-06

-- Table for storing channel account credentials
CREATE TABLE IF NOT EXISTS channel_accounts (
    id SERIAL PRIMARY KEY,
    channel VARCHAR(50) NOT NULL,          -- 'whatsapp', 'avito', 'vk', 'instagram', 'max'
    account_hash VARCHAR(16) NOT NULL,     -- SHA256[:16] for webhook URL
    credentials JSONB NOT NULL,            -- {"access_token": "...", "group_id": 123, ...}
    metadata JSONB,                        -- bot_info, profile, etc.
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),

    UNIQUE(channel, account_hash)
);

-- Index for fast lookup by channel + hash
CREATE INDEX IF NOT EXISTS idx_channel_accounts_lookup
ON channel_accounts(channel, account_hash)
WHERE is_active = true;

-- Trigger for auto-updating updated_at
CREATE OR REPLACE FUNCTION update_channel_accounts_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS channel_accounts_updated ON channel_accounts;

CREATE TRIGGER channel_accounts_updated
BEFORE UPDATE ON channel_accounts
FOR EACH ROW
EXECUTE FUNCTION update_channel_accounts_timestamp();

-- Comment on table
COMMENT ON TABLE channel_accounts IS 'Multi-tenant MCP channel account credentials storage';
COMMENT ON COLUMN channel_accounts.channel IS 'Channel type: whatsapp, avito, vk, instagram, max, telegram';
COMMENT ON COLUMN channel_accounts.account_hash IS 'SHA256[:16] hash of primary credential for webhook URL';
COMMENT ON COLUMN channel_accounts.credentials IS 'JSON with channel-specific credentials';
COMMENT ON COLUMN channel_accounts.metadata IS 'Additional metadata like bot_info, profile data';
