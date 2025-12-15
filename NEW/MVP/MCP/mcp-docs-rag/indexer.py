"""Document Indexer for RAG.

Indexes markdown documents from Plans/ folder into PostgreSQL with embeddings.
Integrates with existing Knowledge Base (project_components, workflow_nodes).
"""

import os
import re
import json
import hashlib
import asyncio
import logging
from pathlib import Path
from typing import List, Dict, Any, Optional, Tuple
from dataclasses import dataclass

import asyncpg
from openai import AsyncOpenAI

from config import settings

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


@dataclass
class DocChunk:
    """Document chunk for indexing."""
    file_path: str
    file_name: str
    section_title: Optional[str]
    chunk_index: int
    content: str
    metadata: Dict[str, Any]
    char_count: int
    token_count: int


class DocumentIndexer:
    """Index documents with embeddings."""

    def __init__(self, docs_path: str = None):
        self.docs_path = Path(docs_path or settings.docs_path)
        self.openai = AsyncOpenAI(api_key=settings.openai_api_key)
        self.db_pool: Optional[asyncpg.Pool] = None

    async def connect(self):
        """Connect to database."""
        self.db_pool = await asyncpg.create_pool(
            settings.database_url,
            min_size=2,
            max_size=10
        )
        logger.info("Connected to database")

    async def close(self):
        """Close database connection."""
        if self.db_pool:
            await self.db_pool.close()
            logger.info("Disconnected from database")

    def parse_markdown(self, file_path: Path) -> List[DocChunk]:
        """Parse markdown file into chunks by sections."""
        content = file_path.read_text(encoding='utf-8')
        file_name = file_path.name
        rel_path = str(file_path.relative_to(self.docs_path.parent))

        chunks = []
        current_section = None
        current_content = []
        chunk_index = 0

        # Extract headers and links for metadata
        headers = re.findall(r'^(#{1,6})\s+(.+)$', content, re.MULTILINE)
        links = re.findall(r'\[([^\]]+)\]\(([^)]+)\)', content)

        lines = content.split('\n')

        for line in lines:
            # Check for header
            header_match = re.match(r'^(#{1,6})\s+(.+)$', line)

            if header_match:
                # Save previous section
                if current_content:
                    chunk_text = '\n'.join(current_content).strip()
                    if chunk_text and len(chunk_text) > 50:  # Min chunk size
                        chunks.append(DocChunk(
                            file_path=rel_path,
                            file_name=file_name,
                            section_title=current_section,
                            chunk_index=chunk_index,
                            content=chunk_text,
                            metadata={
                                'header_level': len(header_match.group(1)) if current_section else 0,
                                'links': [l for l in links if l[0] in chunk_text or l[1] in chunk_text]
                            },
                            char_count=len(chunk_text),
                            token_count=len(chunk_text) // 4  # Rough estimate
                        ))
                        chunk_index += 1

                current_section = header_match.group(2)
                current_content = [line]
            else:
                current_content.append(line)

        # Don't forget last section
        if current_content:
            chunk_text = '\n'.join(current_content).strip()
            if chunk_text and len(chunk_text) > 50:
                chunks.append(DocChunk(
                    file_path=rel_path,
                    file_name=file_name,
                    section_title=current_section,
                    chunk_index=chunk_index,
                    content=chunk_text,
                    metadata={
                        'header_level': 0,
                        'links': [l for l in links if l[0] in chunk_text or l[1] in chunk_text]
                    },
                    char_count=len(chunk_text),
                    token_count=len(chunk_text) // 4
                ))

        # If no sections found, treat whole file as one chunk
        if not chunks and content.strip():
            chunks.append(DocChunk(
                file_path=rel_path,
                file_name=file_name,
                section_title=None,
                chunk_index=0,
                content=content.strip(),
                metadata={'headers': headers, 'links': links},
                char_count=len(content),
                token_count=len(content) // 4
            ))

        logger.info(f"Parsed {file_name}: {len(chunks)} chunks")
        return chunks

    async def get_embedding(self, text: str) -> List[float]:
        """Get embedding for text using OpenAI."""
        try:
            response = await self.openai.embeddings.create(
                model=settings.embedding_model,
                input=text[:8000],  # Limit to avoid token limits
                dimensions=settings.embedding_dimensions
            )
            return response.data[0].embedding
        except Exception as e:
            logger.error(f"Embedding error: {e}")
            return None

    async def index_chunk(self, chunk: DocChunk) -> bool:
        """Index single chunk with embedding."""
        embedding = await self.get_embedding(chunk.content)
        if not embedding:
            return False

        try:
            await self.db_pool.execute("""
                INSERT INTO doc_chunks
                    (file_path, file_name, section_title, chunk_index,
                     content, embedding, metadata, char_count, token_count, indexed_at)
                VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, NOW())
                ON CONFLICT (file_path, chunk_index) DO UPDATE SET
                    file_name = EXCLUDED.file_name,
                    section_title = EXCLUDED.section_title,
                    content = EXCLUDED.content,
                    embedding = EXCLUDED.embedding,
                    metadata = EXCLUDED.metadata,
                    char_count = EXCLUDED.char_count,
                    token_count = EXCLUDED.token_count,
                    updated_at = NOW(),
                    indexed_at = NOW()
            """,
                chunk.file_path,
                chunk.file_name,
                chunk.section_title,
                chunk.chunk_index,
                chunk.content,
                json.dumps(embedding),  # pgvector accepts JSON array
                json.dumps(chunk.metadata),
                chunk.char_count,
                chunk.token_count
            )
            return True
        except Exception as e:
            logger.error(f"DB insert error for {chunk.file_path}: {e}")
            return False

    async def index_file(self, file_path: Path) -> int:
        """Index single file."""
        chunks = self.parse_markdown(file_path)
        indexed = 0

        for chunk in chunks:
            if await self.index_chunk(chunk):
                indexed += 1
            await asyncio.sleep(0.1)  # Rate limiting

        logger.info(f"Indexed {indexed}/{len(chunks)} chunks from {file_path.name}")
        return indexed

    async def index_all(self, pattern: str = "*.md") -> Dict[str, int]:
        """Index all matching files."""
        results = {}
        files = list(self.docs_path.glob(pattern))

        logger.info(f"Found {len(files)} files to index in {self.docs_path}")

        for file_path in files:
            if file_path.is_file():
                indexed = await self.index_file(file_path)
                results[file_path.name] = indexed

        return results

    async def link_to_components(self):
        """Link doc chunks to existing KB components."""
        # Get all components
        components = await self.db_pool.fetch("""
            SELECT id, type, name, description
            FROM project_components
            WHERE status = 'active'
        """)

        if not components:
            logger.info("No components to link")
            return

        # Get all chunks
        chunks = await self.db_pool.fetch("""
            SELECT id, content, metadata
            FROM doc_chunks
        """)

        links_created = 0

        for chunk in chunks:
            content_lower = chunk['content'].lower()

            for comp in components:
                # Check if component is mentioned in chunk
                if comp['name'].lower() in content_lower:
                    try:
                        await self.db_pool.execute("""
                            INSERT INTO doc_links
                                (chunk_id, target_type, target_id, link_type, anchor_text, confidence)
                            VALUES ($1, 'component', $2, 'mentions', $3, 0.8)
                            ON CONFLICT DO NOTHING
                        """, chunk['id'], comp['id'], comp['name'])
                        links_created += 1
                    except Exception as e:
                        logger.error(f"Link error: {e}")

        logger.info(f"Created {links_created} doc-component links")

    async def update_component_embeddings(self):
        """Add embeddings to project_components."""
        components = await self.db_pool.fetch("""
            SELECT id, type, name, description, purpose
            FROM project_components
            WHERE embedding IS NULL AND status = 'active'
        """)

        logger.info(f"Updating embeddings for {len(components)} components")

        for comp in components:
            # Create text for embedding
            text = f"{comp['type']}: {comp['name']}"
            if comp['description']:
                text += f"\n{comp['description']}"
            if comp['purpose']:
                text += f"\n{comp['purpose']}"

            embedding = await self.get_embedding(text)
            if embedding:
                await self.db_pool.execute("""
                    UPDATE project_components
                    SET embedding = $1
                    WHERE id = $2
                """, json.dumps(embedding), comp['id'])

            await asyncio.sleep(0.1)

        logger.info("Component embeddings updated")


async def main():
    """Main entry point."""
    import argparse

    parser = argparse.ArgumentParser(description="Index documents for RAG")
    parser.add_argument("--path", default="Plans", help="Path to documents")
    parser.add_argument("--file", help="Index single file")
    parser.add_argument("--link", action="store_true", help="Link docs to KB components")
    parser.add_argument("--components", action="store_true", help="Update component embeddings")
    args = parser.parse_args()

    # Resolve path
    base_path = Path(__file__).parent.parent.parent  # MCP/mcp-docs-rag -> project root
    docs_path = base_path / args.path

    indexer = DocumentIndexer(str(docs_path))
    await indexer.connect()

    try:
        if args.file:
            await indexer.index_file(docs_path / args.file)
        elif args.link:
            await indexer.link_to_components()
        elif args.components:
            await indexer.update_component_embeddings()
        else:
            results = await indexer.index_all()
            print("\nIndexing Results:")
            print("-" * 40)
            total = 0
            for name, count in sorted(results.items()):
                print(f"  {name}: {count} chunks")
                total += count
            print("-" * 40)
            print(f"  Total: {total} chunks")
    finally:
        await indexer.close()


if __name__ == "__main__":
    asyncio.run(main())
