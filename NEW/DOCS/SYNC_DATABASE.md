# Sync Database Documentation

> Instructions for Claude: regenerate DATABASE_ANALYSIS.md

---

## Command

Say:
```
Regenerate database analysis from CORE_NEW/docs/SYNC_DATABASE.md
```

---

## Steps for Claude

### Step 1: Get all ELO tables

```bash
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c \"
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public'
  AND table_name LIKE 'elo%'
ORDER BY table_name;
\""
```

### Step 2: Get table structures with foreign keys

```bash
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c \"
SELECT
    c.table_name,
    c.column_name,
    c.data_type,
    c.is_nullable,
    c.column_default,
    tc.constraint_type,
    ccu.table_name AS foreign_table
FROM information_schema.columns c
LEFT JOIN information_schema.key_column_usage kcu
    ON c.table_name = kcu.table_name AND c.column_name = kcu.column_name
LEFT JOIN information_schema.table_constraints tc
    ON kcu.constraint_name = tc.constraint_name AND tc.constraint_type = 'FOREIGN KEY'
LEFT JOIN information_schema.constraint_column_usage ccu
    ON tc.constraint_name = ccu.constraint_name
WHERE c.table_schema = 'public'
  AND c.table_name LIKE 'elo_%'
ORDER BY c.table_name, c.ordinal_position;
\""
```

### Step 3: Get reference data

```bash
# Channels
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT * FROM elo_channels;'"

# Verticals
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT * FROM elo_verticals;'"

# IP Nodes
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT * FROM elo_ip_nodes;'"

# Channel accounts
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'SELECT id, tenant_id, channel_id, account_id, session_id, session_status, ip_node_id FROM elo_t_channel_accounts;'"
```

### Step 4: Get views

```bash
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c \"
SELECT table_name, view_definition
FROM information_schema.views
WHERE table_schema = 'public'
  AND table_name LIKE 'elo_v_%';
\""
```

### Step 5: Get functions

```bash
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c \"
SELECT routine_name, routine_definition
FROM information_schema.routines
WHERE routine_schema = 'public'
  AND routine_name LIKE 'elo_%';
\""
```

### Step 6: Analyze and document

1. Create/update `CORE_NEW/docs/DATABASE_ANALYSIS.md`
2. Include:
   - Summary table (count of tables/views)
   - Data flow diagram (ASCII)
   - Each table with columns, types, FKs, description
   - Reference data values
   - Views descriptions
   - Current issues found
   - Key relationships summary

### Step 7: Update timestamp

Update the "Last sync" date at the top of the file.

---

## Database connection

```
Host: 185.221.214.83
Port: 6544
User: postgres
Database: postgres
Container: supabase-db
```

Direct query:
```bash
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c 'YOUR_QUERY'"
```

---

## Table naming conventions

| Prefix | Type | Example |
|--------|------|---------|
| elo_t_ | Transactional table | elo_t_tenants |
| elo_v_ | View | elo_v_ip_usage |
| elo_ | Reference table | elo_channels |

---

## Important tables to check for issues

1. **elo_t_channel_accounts** - session_id should match incoming messages
2. **elo_ip_nodes** - server naming should be consistent
3. **elo_t_operator_channels** - operators should be linked to channel accounts

---

*Last updated: 2025-12-23*
