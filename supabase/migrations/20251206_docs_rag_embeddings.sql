-- Migration: Add RAG embeddings to Knowledge Base
-- Date: 2025-12-06
-- Purpose: Enable semantic search over project documentation with pgvector

-- ============================================
-- 1. Enable pgvector extension
-- ============================================

CREATE EXTENSION IF NOT EXISTS vector;

-- ============================================
-- 2. DOC_CHUNKS - Document chunks with embeddings
-- ============================================

CREATE TABLE IF NOT EXISTS doc_chunks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Source document
    file_path VARCHAR(500) NOT NULL,              -- Plans/Стратегия_Роста.md
    file_name VARCHAR(255) NOT NULL,              -- Стратегия_Роста.md
    doc_type VARCHAR(50) DEFAULT 'plan',          -- plan, flow_doc, readme, code_comment

    -- Chunk info
    section_title VARCHAR(500),                   -- ## Название раздела
    chunk_index INTEGER NOT NULL DEFAULT 0,       -- Order within document
    content TEXT NOT NULL,                        -- Chunk text

    -- Embedding
    embedding vector(1536),                       -- OpenAI text-embedding-3-small

    -- Metadata
    metadata JSONB DEFAULT '{}',                  -- {headers: [], links: [], keywords: []}
    char_count INTEGER,
    token_count INTEGER,

    -- Link to KB component (if exists)
    component_id UUID REFERENCES project_components(id) ON DELETE SET NULL,

    -- Timestamps
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    indexed_at TIMESTAMP WITH TIME ZONE,

    UNIQUE(file_path, chunk_index)
);

-- Index for vector similarity search
CREATE INDEX IF NOT EXISTS idx_doc_chunks_embedding
ON doc_chunks USING ivfflat (embedding vector_cosine_ops)
WITH (lists = 100);

-- Regular indexes
CREATE INDEX IF NOT EXISTS idx_doc_chunks_file ON doc_chunks(file_path);
CREATE INDEX IF NOT EXISTS idx_doc_chunks_type ON doc_chunks(doc_type);
CREATE INDEX IF NOT EXISTS idx_doc_chunks_component ON doc_chunks(component_id);

-- ============================================
-- 3. Add embeddings to project_components
-- ============================================

ALTER TABLE project_components
ADD COLUMN IF NOT EXISTS embedding vector(1536);

ALTER TABLE project_components
ADD COLUMN IF NOT EXISTS summary TEXT;

-- Index for component embeddings
CREATE INDEX IF NOT EXISTS idx_project_components_embedding
ON project_components USING ivfflat (embedding vector_cosine_ops)
WITH (lists = 100);

-- ============================================
-- 4. DOC_LINKS - Cross-references between docs and components
-- ============================================

CREATE TABLE IF NOT EXISTS doc_links (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Source (document chunk)
    chunk_id UUID NOT NULL REFERENCES doc_chunks(id) ON DELETE CASCADE,

    -- Target (can be component or another chunk)
    target_type VARCHAR(50) NOT NULL,             -- component, chunk, external
    target_id UUID,                               -- component_id or chunk_id
    target_ref VARCHAR(500),                      -- For external links

    -- Link info
    link_type VARCHAR(50) NOT NULL,               -- mentions, implements, documents, references
    anchor_text VARCHAR(500),                     -- Text that created the link
    confidence FLOAT DEFAULT 1.0,                 -- Auto-detected links have lower confidence

    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),

    UNIQUE(chunk_id, target_type, COALESCE(target_id::text, target_ref))
);

CREATE INDEX IF NOT EXISTS idx_doc_links_chunk ON doc_links(chunk_id);
CREATE INDEX IF NOT EXISTS idx_doc_links_target ON doc_links(target_id);
CREATE INDEX IF NOT EXISTS idx_doc_links_type ON doc_links(link_type);

-- ============================================
-- 5. SEARCH_HISTORY - Track searches for improvement
-- ============================================

CREATE TABLE IF NOT EXISTS search_history (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    query TEXT NOT NULL,
    query_embedding vector(1536),

    -- Results
    results_count INTEGER,
    top_result_id UUID,
    top_similarity FLOAT,

    -- Context
    session_id VARCHAR(100),
    search_type VARCHAR(50) DEFAULT 'semantic',   -- semantic, keyword, hybrid
    filters JSONB DEFAULT '{}',

    -- Feedback
    was_helpful BOOLEAN,
    selected_result_id UUID,

    searched_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_search_history_date ON search_history(searched_at);

-- ============================================
-- 6. SEMANTIC SEARCH FUNCTION
-- ============================================

CREATE OR REPLACE FUNCTION search_docs(
    query_embedding vector(1536),
    match_count INTEGER DEFAULT 5,
    similarity_threshold FLOAT DEFAULT 0.7
)
RETURNS TABLE (
    chunk_id UUID,
    file_path VARCHAR,
    section_title VARCHAR,
    content TEXT,
    similarity FLOAT,
    metadata JSONB
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        d.id as chunk_id,
        d.file_path,
        d.section_title,
        d.content,
        1 - (d.embedding <=> query_embedding) as similarity,
        d.metadata
    FROM doc_chunks d
    WHERE d.embedding IS NOT NULL
      AND 1 - (d.embedding <=> query_embedding) > similarity_threshold
    ORDER BY d.embedding <=> query_embedding
    LIMIT match_count;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- 7. SEARCH COMPONENTS FUNCTION
-- ============================================

CREATE OR REPLACE FUNCTION search_components(
    query_embedding vector(1536),
    match_count INTEGER DEFAULT 5,
    component_types VARCHAR[] DEFAULT NULL
)
RETURNS TABLE (
    component_id UUID,
    type VARCHAR,
    name VARCHAR,
    description TEXT,
    similarity FLOAT,
    file_path VARCHAR
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        c.id as component_id,
        c.type,
        c.name,
        c.description,
        1 - (c.embedding <=> query_embedding) as similarity,
        c.file_path
    FROM project_components c
    WHERE c.embedding IS NOT NULL
      AND (component_types IS NULL OR c.type = ANY(component_types))
    ORDER BY c.embedding <=> query_embedding
    LIMIT match_count;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- 8. HYBRID SEARCH FUNCTION (semantic + keyword)
-- ============================================

CREATE OR REPLACE FUNCTION hybrid_search(
    query_embedding vector(1536),
    keyword_query TEXT,
    match_count INTEGER DEFAULT 10
)
RETURNS TABLE (
    source_type VARCHAR,
    source_id UUID,
    title VARCHAR,
    content TEXT,
    semantic_score FLOAT,
    keyword_score FLOAT,
    combined_score FLOAT
) AS $$
BEGIN
    RETURN QUERY
    WITH semantic_results AS (
        SELECT
            'doc' as src_type,
            d.id as src_id,
            COALESCE(d.section_title, d.file_name)::VARCHAR as src_title,
            d.content as src_content,
            1 - (d.embedding <=> query_embedding) as sem_score
        FROM doc_chunks d
        WHERE d.embedding IS NOT NULL
        ORDER BY d.embedding <=> query_embedding
        LIMIT match_count * 2
    ),
    keyword_results AS (
        SELECT
            'doc' as src_type,
            d.id as src_id,
            COALESCE(d.section_title, d.file_name)::VARCHAR as src_title,
            d.content as src_content,
            ts_rank(to_tsvector('russian', d.content), plainto_tsquery('russian', keyword_query)) as kw_score
        FROM doc_chunks d
        WHERE to_tsvector('russian', d.content) @@ plainto_tsquery('russian', keyword_query)
        LIMIT match_count * 2
    ),
    combined AS (
        SELECT
            COALESCE(s.src_type, k.src_type) as source_type,
            COALESCE(s.src_id, k.src_id) as source_id,
            COALESCE(s.src_title, k.src_title) as title,
            COALESCE(s.src_content, k.src_content) as content,
            COALESCE(s.sem_score, 0) as semantic_score,
            COALESCE(k.kw_score, 0) as keyword_score,
            (COALESCE(s.sem_score, 0) * 0.7 + COALESCE(k.kw_score, 0) * 0.3) as combined_score
        FROM semantic_results s
        FULL OUTER JOIN keyword_results k ON s.src_id = k.src_id
    )
    SELECT * FROM combined
    ORDER BY combined_score DESC
    LIMIT match_count;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- 9. GET RELATED COMPONENTS FUNCTION
-- ============================================

CREATE OR REPLACE FUNCTION get_related_context(
    p_component_id UUID,
    max_depth INTEGER DEFAULT 2
)
RETURNS TABLE (
    component_id UUID,
    component_type VARCHAR,
    component_name VARCHAR,
    relation_type VARCHAR,
    relation_direction VARCHAR,
    depth INTEGER
) AS $$
BEGIN
    RETURN QUERY
    WITH RECURSIVE related AS (
        -- Direct relations (outgoing)
        SELECT
            r.to_component_id as comp_id,
            c.type as comp_type,
            c.name as comp_name,
            r.relation_type as rel_type,
            'outgoing'::VARCHAR as rel_dir,
            1 as d
        FROM component_relations r
        JOIN project_components c ON c.id = r.to_component_id
        WHERE r.from_component_id = p_component_id

        UNION

        -- Direct relations (incoming)
        SELECT
            r.from_component_id as comp_id,
            c.type as comp_type,
            c.name as comp_name,
            r.relation_type as rel_type,
            'incoming'::VARCHAR as rel_dir,
            1 as d
        FROM component_relations r
        JOIN project_components c ON c.id = r.from_component_id
        WHERE r.to_component_id = p_component_id

        UNION

        -- Recursive (only if depth allows)
        SELECT
            r.to_component_id as comp_id,
            c.type as comp_type,
            c.name as comp_name,
            r.relation_type as rel_type,
            'outgoing'::VARCHAR as rel_dir,
            rel.d + 1 as d
        FROM related rel
        JOIN component_relations r ON r.from_component_id = rel.comp_id
        JOIN project_components c ON c.id = r.to_component_id
        WHERE rel.d < max_depth
    )
    SELECT DISTINCT
        comp_id as component_id,
        comp_type as component_type,
        comp_name as component_name,
        rel_type as relation_type,
        rel_dir as relation_direction,
        d as depth
    FROM related
    WHERE comp_id != p_component_id
    ORDER BY d, comp_type, comp_name;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- 10. COMMENTS
-- ============================================

COMMENT ON TABLE doc_chunks IS 'Document chunks with embeddings for semantic search';
COMMENT ON TABLE doc_links IS 'Cross-references between documentation and KB components';
COMMENT ON TABLE search_history IS 'Search query history for analytics and improvement';

COMMENT ON FUNCTION search_docs IS 'Semantic search over documentation chunks';
COMMENT ON FUNCTION search_components IS 'Semantic search over KB components';
COMMENT ON FUNCTION hybrid_search IS 'Combined semantic + keyword search';
COMMENT ON FUNCTION get_related_context IS 'Get related components via graph traversal';
