import os

# OpenRouter configuration
OPENROUTER_API_KEY = os.getenv("OPENROUTER_API_KEY", "")
OPENROUTER_BASE_URL = os.getenv("OPENROUTER_BASE_URL", "https://openrouter.ai/api/v1")

# Default models
DEFAULT_EXTRACTION_MODEL = os.getenv("DEFAULT_EXTRACTION_MODEL", "qwen/qwen3-30b-a3b")
DEFAULT_CHAT_MODEL = os.getenv("DEFAULT_CHAT_MODEL", "anthropic/claude-3-5-sonnet")

# Service configuration
HOST = os.getenv("HOST", "0.0.0.0")
PORT = int(os.getenv("PORT", "8774"))
