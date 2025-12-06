"""Configuration for MCP Docs RAG Server."""

import os
from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """Application settings."""

    # Server
    host: str = "0.0.0.0"
    port: int = 8090

    # PostgreSQL with pgvector
    database_url: str = os.environ.get(
        "DATABASE_URL",
        "postgresql://supabase_admin:Mi31415926pS@185.221.214.83:6544/postgres"
    )

    # Neo4j Graph
    neo4j_uri: str = os.environ.get("NEO4J_URI", "bolt://localhost:7687")
    neo4j_user: str = os.environ.get("NEO4J_USER", "neo4j")
    neo4j_password: str = os.environ.get("NEO4J_PASSWORD", "password")

    # OpenAI for embeddings
    openai_api_key: str = os.environ.get("OPENAI_API_KEY", "")
    embedding_model: str = "text-embedding-3-small"
    embedding_dimensions: int = 1536

    # Docs settings
    docs_path: str = os.environ.get("DOCS_PATH", "/app/docs")
    chunk_size: int = 1000
    chunk_overlap: int = 200

    # Search settings
    top_k: int = 5
    similarity_threshold: float = 0.7

    class Config:
        env_file = ".env"


settings = Settings()
