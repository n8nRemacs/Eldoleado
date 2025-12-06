# MCP Docs RAG

Semantic search server for Eldoleado project documentation with integration to existing Knowledge Base.

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    MCP Docs RAG Server                       │
├─────────────────────────────────────────────────────────────┤
│                                                              │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────┐  │
│  │   Search     │    │   Context    │    │  Relations   │  │
│  │   (docs)     │    │ (docs + KB)  │    │   (graph)    │  │
│  └──────────────┘    └──────────────┘    └──────────────┘  │
│          │                  │                   │           │
│          └──────────────────┴───────────────────┘           │
│                           │                                  │
│                    ┌──────┴──────┐                          │
│                    │  PostgreSQL │                          │
│                    │  + pgvector │                          │
│                    └─────────────┘                          │
│                           │                                  │
│    ┌──────────────────────┼──────────────────────┐         │
│    │                      │                       │         │
│    ▼                      ▼                       ▼         │
│ doc_chunks        project_components      workflow_nodes    │
│ (embeddings)      (embeddings)            (details)         │
│                           │                                  │
│                   component_relations                        │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

## Features

- **Semantic Search**: Vector similarity search over documentation
- **KB Integration**: Search and navigate existing Knowledge Base
- **Graph Relations**: Traverse component relationships
- **Hybrid Search**: Combine semantic + keyword search
- **MCP Tools**: Ready for Claude Code integration

## Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/search` | POST | Semantic search over docs |
| `/search/components` | POST | Search KB components |
| `/context` | POST | Get full context (docs + KB + relations) |
| `/relations/{type}/{name}` | GET | Get component relations |
| `/docs` | GET | List indexed documents |
| `/stats` | GET | Indexing statistics |
| `/mcp/ask` | POST | MCP tool: Ask about project |
| `/mcp/navigate/{keyword}` | GET | MCP tool: Navigate to files |

## Quick Start

### 1. Run migration

```sql
-- Run on PostgreSQL
\i supabase/migrations/20251206_docs_rag_embeddings.sql
```

### 2. Index documents

```bash
# Install dependencies
pip install -r requirements.txt

# Set environment
export DATABASE_URL="postgresql://..."
export OPENAI_API_KEY="sk-..."

# Index Plans folder
python indexer.py --path Plans

# Link docs to KB components
python indexer.py --link

# Update component embeddings
python indexer.py --components
```

### 3. Run server

```bash
# Development
python app.py

# Or with Docker
docker-compose up -d
```

## Usage Examples

### Search docs

```bash
curl -X POST http://localhost:8090/search \
  -H "Content-Type: application/json" \
  -d '{"query": "как работает AI extraction", "top_k": 5}'
```

### Get context

```bash
curl -X POST http://localhost:8090/context \
  -H "Content-Type: application/json" \
  -d '{"topic": "обработка сообщений Telegram"}'
```

### MCP Ask

```bash
curl "http://localhost:8090/mcp/ask?query=где%20настраивается%20воронка%20продаж"
```

## Integration with Claude Code

Add to `.claude/settings.json`:

```json
{
  "mcpServers": {
    "docs-rag": {
      "url": "http://localhost:8090",
      "tools": ["search", "context", "navigate"]
    }
  }
}
```

## Full Sync Command

After updating Plans/ folder:

```bash
# Re-index all documents
docker-compose --profile indexer up indexer

# Or manually
python indexer.py
python indexer.py --link
python indexer.py --components
```

## Database Tables

| Table | Description |
|-------|-------------|
| `doc_chunks` | Document chunks with embeddings |
| `doc_links` | Cross-references between docs and KB |
| `search_history` | Search queries for analytics |
| `project_components` | KB components (with embeddings) |
| `component_relations` | Component relationships |
| `workflow_nodes` | Workflow node details |

## Functions

| Function | Description |
|----------|-------------|
| `search_docs()` | Semantic search over docs |
| `search_components()` | Semantic search over KB |
| `hybrid_search()` | Semantic + keyword combined |
| `get_related_context()` | Graph traversal for relations |
