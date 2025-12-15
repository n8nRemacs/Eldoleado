"""MCP Docs RAG Server.

FastAPI server providing semantic search over project documentation.
Integrates with existing Knowledge Base (project_components, workflow_nodes).

Tools:
- search_docs: Semantic search over documentation
- search_components: Search KB components
- get_context: Get full context for a topic (docs + KB + relations)
- get_relations: Get related components via graph
- list_docs: List all indexed documents
"""

import json
import logging
from datetime import datetime
from typing import Optional, List, Dict, Any
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, Query
from fastapi.responses import JSONResponse
from pydantic import BaseModel, Field
import asyncpg
from openai import AsyncOpenAI

from config import settings

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Global connections
db_pool: Optional[asyncpg.Pool] = None
openai_client: Optional[AsyncOpenAI] = None


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Manage app lifecycle."""
    global db_pool, openai_client

    # Startup
    db_pool = await asyncpg.create_pool(
        settings.database_url,
        min_size=2,
        max_size=10
    )
    logger.info("Connected to PostgreSQL")

    if settings.openai_api_key:
        openai_client = AsyncOpenAI(api_key=settings.openai_api_key)
        logger.info("OpenAI client initialized")

    yield

    # Shutdown
    if db_pool:
        await db_pool.close()
        logger.info("Disconnected from PostgreSQL")


app = FastAPI(
    title="MCP Docs RAG",
    description="Semantic search over Eldoleado project documentation",
    version="1.0.0",
    lifespan=lifespan
)


# ============================================
# Models
# ============================================

class SearchRequest(BaseModel):
    query: str = Field(..., description="Search query")
    top_k: int = Field(default=5, description="Number of results")
    doc_type: Optional[str] = Field(default=None, description="Filter by doc type")
    include_relations: bool = Field(default=True, description="Include related components")


class SearchResult(BaseModel):
    source_type: str
    source_id: str
    title: str
    content: str
    file_path: Optional[str]
    similarity: float
    metadata: Dict[str, Any]


class ContextRequest(BaseModel):
    topic: str = Field(..., description="Topic to get context for")
    max_depth: int = Field(default=2, description="Max relation depth")


class ComponentResult(BaseModel):
    id: str
    type: str
    name: str
    description: Optional[str]
    file_path: Optional[str]
    relations: List[Dict[str, Any]]


# ============================================
# Helper Functions
# ============================================

async def get_embedding(text: str) -> Optional[List[float]]:
    """Get embedding for text."""
    if not openai_client:
        return None

    try:
        response = await openai_client.embeddings.create(
            model=settings.embedding_model,
            input=text[:8000],
            dimensions=settings.embedding_dimensions
        )
        return response.data[0].embedding
    except Exception as e:
        logger.error(f"Embedding error: {e}")
        return None


async def log_search(query: str, embedding: List[float], results_count: int,
                     top_result_id: str = None, top_similarity: float = None):
    """Log search for analytics."""
    try:
        await db_pool.execute("""
            INSERT INTO search_history
                (query, query_embedding, results_count, top_result_id, top_similarity)
            VALUES ($1, $2, $3, $4, $5)
        """, query, json.dumps(embedding) if embedding else None,
           results_count, top_result_id, top_similarity)
    except Exception as e:
        logger.warning(f"Failed to log search: {e}")


# ============================================
# API Endpoints
# ============================================

@app.get("/health")
async def health():
    """Health check."""
    return {"status": "ok", "timestamp": datetime.utcnow().isoformat()}


@app.post("/search", response_model=List[SearchResult])
async def search_docs(request: SearchRequest):
    """
    Semantic search over documentation.

    Searches through indexed document chunks using vector similarity.
    Returns relevant sections with similarity scores.
    """
    embedding = await get_embedding(request.query)
    if not embedding:
        raise HTTPException(status_code=500, detail="Failed to generate embedding")

    # Search docs
    rows = await db_pool.fetch("""
        SELECT
            id,
            file_path,
            file_name,
            section_title,
            content,
            metadata,
            1 - (embedding <=> $1::vector) as similarity
        FROM doc_chunks
        WHERE embedding IS NOT NULL
        ORDER BY embedding <=> $1::vector
        LIMIT $2
    """, json.dumps(embedding), request.top_k)

    results = []
    for row in rows:
        if row['similarity'] >= settings.similarity_threshold:
            results.append(SearchResult(
                source_type="doc",
                source_id=str(row['id']),
                title=row['section_title'] or row['file_name'],
                content=row['content'][:500] + "..." if len(row['content']) > 500 else row['content'],
                file_path=row['file_path'],
                similarity=float(row['similarity']),
                metadata=json.loads(row['metadata']) if row['metadata'] else {}
            ))

    # Log search
    await log_search(
        request.query, embedding, len(results),
        results[0].source_id if results else None,
        results[0].similarity if results else None
    )

    return results


@app.post("/search/components", response_model=List[ComponentResult])
async def search_components(request: SearchRequest):
    """
    Search KB components (workflows, tables, etc).

    Uses semantic search over component descriptions.
    """
    embedding = await get_embedding(request.query)
    if not embedding:
        raise HTTPException(status_code=500, detail="Failed to generate embedding")

    rows = await db_pool.fetch("""
        SELECT
            id, type, name, description, file_path, category, metadata,
            1 - (embedding <=> $1::vector) as similarity
        FROM project_components
        WHERE embedding IS NOT NULL AND status = 'active'
        ORDER BY embedding <=> $1::vector
        LIMIT $2
    """, json.dumps(embedding), request.top_k)

    results = []
    for row in rows:
        # Get relations if requested
        relations = []
        if request.include_relations:
            rel_rows = await db_pool.fetch("""
                SELECT
                    r.relation_type,
                    c.type as target_type,
                    c.name as target_name
                FROM component_relations r
                JOIN project_components c ON c.id = r.to_component_id
                WHERE r.from_component_id = $1
                LIMIT 10
            """, row['id'])
            relations = [dict(r) for r in rel_rows]

        results.append(ComponentResult(
            id=str(row['id']),
            type=row['type'],
            name=row['name'],
            description=row['description'],
            file_path=row['file_path'],
            relations=relations
        ))

    return results


@app.post("/context")
async def get_context(request: ContextRequest):
    """
    Get full context for a topic.

    Combines:
    - Semantic search over docs
    - KB component search
    - Graph relations
    - Workflow nodes if applicable
    """
    embedding = await get_embedding(request.topic)
    if not embedding:
        raise HTTPException(status_code=500, detail="Failed to generate embedding")

    # 1. Search docs
    doc_rows = await db_pool.fetch("""
        SELECT
            id, file_path, section_title, content,
            1 - (embedding <=> $1::vector) as similarity
        FROM doc_chunks
        WHERE embedding IS NOT NULL
        ORDER BY embedding <=> $1::vector
        LIMIT 3
    """, json.dumps(embedding))

    # 2. Search components
    comp_rows = await db_pool.fetch("""
        SELECT
            id, type, name, description, category,
            1 - (embedding <=> $1::vector) as similarity
        FROM project_components
        WHERE embedding IS NOT NULL AND status = 'active'
        ORDER BY embedding <=> $1::vector
        LIMIT 5
    """, json.dumps(embedding))

    # 3. Get relations for top components
    relations = []
    if comp_rows:
        top_comp_id = comp_rows[0]['id']
        rel_rows = await db_pool.fetch("""
            SELECT * FROM get_related_context($1, $2)
        """, top_comp_id, request.max_depth)
        relations = [dict(r) for r in rel_rows]

    # 4. Get workflow nodes if applicable
    workflow_nodes = []
    for comp in comp_rows:
        if comp['type'] == 'workflow':
            node_rows = await db_pool.fetch("""
                SELECT node_name, node_type, details
                FROM workflow_nodes
                WHERE workflow_name = $1
                ORDER BY node_name
            """, comp['name'])
            workflow_nodes.extend([dict(r) for r in node_rows])

    return {
        "topic": request.topic,
        "docs": [
            {
                "file": row['file_path'],
                "section": row['section_title'],
                "content": row['content'][:300] + "...",
                "similarity": float(row['similarity'])
            }
            for row in doc_rows
        ],
        "components": [
            {
                "type": row['type'],
                "name": row['name'],
                "description": row['description'],
                "similarity": float(row['similarity'])
            }
            for row in comp_rows
        ],
        "relations": relations[:20],
        "workflow_nodes": workflow_nodes[:20]
    }


@app.get("/relations/{component_type}/{component_name}")
async def get_relations(
    component_type: str,
    component_name: str,
    max_depth: int = Query(default=2, le=5)
):
    """
    Get related components via graph traversal.

    Returns all components connected to the specified component.
    """
    # Find component
    comp = await db_pool.fetchrow("""
        SELECT id FROM project_components
        WHERE type = $1 AND name = $2
    """, component_type, component_name)

    if not comp:
        raise HTTPException(status_code=404, detail="Component not found")

    # Get relations
    rows = await db_pool.fetch("""
        SELECT * FROM get_related_context($1, $2)
    """, comp['id'], max_depth)

    return {
        "component": {"type": component_type, "name": component_name},
        "relations": [dict(r) for r in rows]
    }


@app.get("/docs")
async def list_docs(doc_type: Optional[str] = None):
    """
    List all indexed documents.

    Returns document names with chunk counts.
    """
    query = """
        SELECT
            file_name,
            file_path,
            doc_type,
            COUNT(*) as chunk_count,
            SUM(char_count) as total_chars,
            MAX(indexed_at) as last_indexed
        FROM doc_chunks
    """
    params = []

    if doc_type:
        query += " WHERE doc_type = $1"
        params.append(doc_type)

    query += " GROUP BY file_name, file_path, doc_type ORDER BY file_name"

    rows = await db_pool.fetch(query, *params)

    return {
        "total_docs": len(rows),
        "docs": [
            {
                "name": row['file_name'],
                "path": row['file_path'],
                "type": row['doc_type'],
                "chunks": row['chunk_count'],
                "chars": row['total_chars'],
                "indexed_at": row['last_indexed'].isoformat() if row['last_indexed'] else None
            }
            for row in rows
        ]
    }


@app.get("/stats")
async def get_stats():
    """Get indexing statistics."""
    doc_stats = await db_pool.fetchrow("""
        SELECT
            COUNT(*) as total_chunks,
            COUNT(DISTINCT file_path) as total_docs,
            SUM(char_count) as total_chars,
            AVG(char_count) as avg_chunk_size
        FROM doc_chunks
    """)

    comp_stats = await db_pool.fetchrow("""
        SELECT
            COUNT(*) as total_components,
            COUNT(*) FILTER (WHERE embedding IS NOT NULL) as with_embeddings
        FROM project_components
        WHERE status = 'active'
    """)

    rel_stats = await db_pool.fetchrow("""
        SELECT COUNT(*) as total_relations
        FROM component_relations
    """)

    return {
        "docs": {
            "total_docs": doc_stats['total_docs'],
            "total_chunks": doc_stats['total_chunks'],
            "total_chars": doc_stats['total_chars'],
            "avg_chunk_size": float(doc_stats['avg_chunk_size']) if doc_stats['avg_chunk_size'] else 0
        },
        "components": {
            "total": comp_stats['total_components'],
            "with_embeddings": comp_stats['with_embeddings']
        },
        "relations": rel_stats['total_relations']
    }


# ============================================
# MCP-specific endpoints (for Claude Code integration)
# ============================================

@app.post("/mcp/ask")
async def mcp_ask(query: str = Query(..., description="Question about the project")):
    """
    MCP tool: Ask question about the project.

    Returns relevant documentation and KB context.
    Designed for use as Claude Code MCP tool.
    """
    context = await get_context(ContextRequest(topic=query))

    # Format response for Claude
    response_parts = []

    if context['docs']:
        response_parts.append("## Relevant Documentation\n")
        for doc in context['docs']:
            response_parts.append(f"### {doc['section']} ({doc['file']})")
            response_parts.append(f"Similarity: {doc['similarity']:.2f}")
            response_parts.append(f"{doc['content']}\n")

    if context['components']:
        response_parts.append("\n## Related Components\n")
        for comp in context['components']:
            response_parts.append(f"- **{comp['type']}**: {comp['name']}")
            if comp['description']:
                response_parts.append(f"  {comp['description'][:100]}")

    if context['relations']:
        response_parts.append("\n## Component Relations\n")
        for rel in context['relations'][:10]:
            response_parts.append(
                f"- {rel['component_type']} `{rel['component_name']}` "
                f"({rel['relation_type']}, depth={rel['depth']})"
            )

    return {
        "query": query,
        "response": "\n".join(response_parts),
        "sources": [d['file'] for d in context['docs']]
    }


@app.get("/mcp/navigate/{keyword}")
async def mcp_navigate(keyword: str):
    """
    MCP tool: Navigate to relevant parts of the project.

    Returns file paths and components related to keyword.
    """
    # Search both docs and components
    embedding = await get_embedding(keyword)
    if not embedding:
        return {"error": "Failed to generate embedding"}

    docs = await db_pool.fetch("""
        SELECT DISTINCT file_path, file_name
        FROM doc_chunks
        WHERE embedding IS NOT NULL
        ORDER BY embedding <=> $1::vector
        LIMIT 5
    """, json.dumps(embedding))

    components = await db_pool.fetch("""
        SELECT type, name, file_path, category
        FROM project_components
        WHERE embedding IS NOT NULL AND status = 'active'
        ORDER BY embedding <=> $1::vector
        LIMIT 10
    """, json.dumps(embedding))

    return {
        "keyword": keyword,
        "docs": [{"name": d['file_name'], "path": d['file_path']} for d in docs],
        "components": [
            {
                "type": c['type'],
                "name": c['name'],
                "file": c['file_path'],
                "category": c['category']
            }
            for c in components
        ]
    }


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=settings.host, port=settings.port)
