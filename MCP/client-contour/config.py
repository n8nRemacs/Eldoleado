import os

# Service URLs
CORE_URL = os.getenv("CORE_URL", "https://n8n.n8nsrv.ru/webhook/elo-core-ingest")
GRAPH_URL = os.getenv("GRAPH_URL", "http://45.144.177.128:8773/query")

# Database
DATABASE_URL = os.getenv("DATABASE_URL", "postgresql://supabase_admin:postgres@185.221.214.83:6544/postgres")

# Neo4j
NEO4J_URI = os.getenv("NEO4J_URI", "bolt+ssc://45.144.177.128:7687")
NEO4J_USER = os.getenv("NEO4J_USER", "neo4j")
NEO4J_PASSWORD = os.getenv("NEO4J_PASSWORD", "Mi31415926pS")

# Redis
REDIS_URL = os.getenv("REDIS_URL", "redis://185.221.214.83:6379/0")

# Cache TTL (seconds)
CACHE_TTL_TENANT = int(os.getenv("CACHE_TTL_TENANT", "86400"))  # 24 hours
CACHE_TTL_CLIENT = int(os.getenv("CACHE_TTL_CLIENT", "3600"))   # 1 hour
CACHE_TTL_DIALOG = int(os.getenv("CACHE_TTL_DIALOG", "1800"))   # 30 min

# Server
HOST = os.getenv("HOST", "0.0.0.0")
PORT = int(os.getenv("PORT", "8772"))

