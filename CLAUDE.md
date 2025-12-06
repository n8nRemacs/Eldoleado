# Eldoleado Project Instructions

## Quick Commands

When user says:
- **"фулл синк" / "full sync" / "обнови KB"** -> Run `python scripts/full_sync.py`
- **"быстрый синк" / "quick sync"** -> Run `python scripts/full_sync.py --quick`
- **"обнови документацию"** -> Run `python scripts/update_flow_docs.py --all`
- **"покажи индекс workflow"** -> Run `python scripts/update_flow_docs.py --index`
- **"stop" / "стоп"** -> Kill all background tasks, stop running scripts

## How to Use Knowledge Base System

### When CREATING new workflow/feature:
1. Check existing patterns: `python scripts/trace_flow.py "similar_keyword"`
2. Look at flow docs: `docs/flows/` for similar channels/actions
3. After creating, run: `python scripts/full_sync.py` to update KB
4. Check relations: `python scripts/update_flow_docs.py --index`

### When MODIFYING existing workflow:
1. First, trace current flow: `python scripts/trace_flow.py --workflow "WorkflowName"`
2. Check which docs will be affected: `python scripts/update_flow_docs.py --index | grep "WorkflowName"`
3. Make changes
4. Update only affected docs: `python scripts/update_flow_docs.py --workflow "WorkflowName"`

### When DEBUGGING:
1. Trace the full flow: `python scripts/trace_flow.py "keyword_or_webhook"`
2. See all DB operations: check `docs/flows/action_*.md` or `channel_*.md`
3. Find which tables are affected: look at "Затронутые таблицы" section
4. Check SQL queries in the flow doc
5. Query DB directly to verify data:
   ```sql
   -- Connect via psycopg2 or psql
   SELECT * FROM workflow_nodes WHERE workflow_name = 'WorkflowName';
   SELECT * FROM node_connections WHERE workflow_name = 'WorkflowName';
   ```

### Useful Queries:
```sql
-- Find all workflows that touch a table
SELECT DISTINCT workflow_name FROM workflow_nodes
WHERE details->>'sql' ILIKE '%table_name%';

-- Find workflows by webhook
SELECT name, metadata->>'webhook_path' FROM project_components
WHERE type = 'workflow' AND metadata->>'webhook_path' IS NOT NULL;

-- Find workflow chain
SELECT * FROM component_relations
WHERE relation_type = 'calls'
AND from_component_id IN (SELECT id FROM project_components WHERE name = 'WorkflowName');
```

## Project Structure

- `app/` - Android приложение (Kotlin)
- `n8n_workflows/` - JSON файлы workflow с n8n сервера
- `supabase/migrations/` - SQL миграции
- `scripts/` - Python скрипты для автоматизации
- `docs/flows/` - Автогенерируемая документация потоков

## Knowledge Base Scripts

| Script | Purpose |
|--------|---------|
| `full_sync.py` | Full KB sync (n8n + components + relations + nodes + docs) |
| `sync_and_update_kb.py` | Sync from n8n and update components/relations |
| `populate_workflow_nodes.py` | Extract nodes from workflow JSON |
| `populate_node_connections.py` | Extract node connections |
| `update_flow_docs.py` | Generate/update flow documentation |
| `trace_flow.py` | Trace execution path through workflows |

## Database

- **PostgreSQL**: `185.221.214.83:6544/postgres`
- **Tables**: project_components, component_relations, workflow_nodes, node_connections, channel_accounts

## Servers

### RU Server (45.144.177.128)
| Service | Port | Description |
|---------|------|-------------|
| mcp-avito | 8765 | Avito Messenger API v2.0.0 multi-tenant |
| mcp-vk | 8767 | VK Community API v2.0.0 multi-tenant |
| mcp-max | 8768 | MAX (VK Teams) API v2.0.0 multi-tenant |
| mcp-form | 8770 | Web forms API |
| api-android | 8780 | Android API Gateway |
| Redis (avito-redis) | - | Docker network: avito-api_default |

### Finnish Server (217.145.79.27)
| Service | Port | Description |
|---------|------|-------------|
| mcp-telegram | 8767 | Telegram Bot API v2.0.0 multi-tenant |
| mcp-whatsapp | 8766 | WhatsApp (Wappi.pro) API v2.0.0 multi-tenant |
| Redis (mcp-redis) | 6379 | Docker network: mcp-network |

### Multi-tenant Webhook URLs
```
/webhook/telegram/{bot_hash}
/webhook/avito/{user_hash}
/webhook/vk/{group_hash}
/webhook/max/{token_hash}
/webhook/whatsapp/{profile_hash}
```
Hash = SHA256(primary_credential)[:16]

## n8n Server

- **URL**: https://n8n.n8nsrv.ru
- **Workflows prefix**: BAT (BattCRM/Eldoleado)

### IMPORTANT: n8n is READ-ONLY!

**n8n сервер только для чтения. НЕ пытаться загружать workflows через API.**

- `n8n_workflows/` - только для ЧТЕНИЯ (синхронизация с сервера)
- `workflows_to_import/` - для НОВЫХ и ИЗМЕНЁННЫХ workflows

**Когда создаёшь/изменяешь workflow:**
1. Создай JSON файл в `workflows_to_import/`
2. Пользователь вручную импортирует через n8n UI
3. После импорта запусти `python scripts/full_sync.py` для синхронизации

**Структура workflows_to_import:**
```
workflows_to_import/
├── new/           # Новые workflows
├── modified/      # Изменённые существующие
└── README.md      # Инструкции по импорту
```

## Key Conventions

- Workflow naming: `BAT IN {Channel}` for inbound, `API_Android_{Action}` for API
- All documentation in Russian
- Flow docs have YAML frontmatter with participating workflows for incremental updates
