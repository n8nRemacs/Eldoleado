"""Configuration for Telegram MCP Server (Multi-tenant)."""

from pydantic_settings import BaseSettings
from typing import Optional


class Settings(BaseSettings):
    """Application settings from environment variables."""

    # n8n integration
    N8N_WEBHOOK_URL: str = ""  # Webhook URL for BAT IN Telegram Proxy

    # API security
    API_KEY: str = ""  # API key for protected endpoints

    # Server settings
    SERVER_HOST: str = "0.0.0.0"
    SERVER_PORT: int = 8761

    # OpenAI for voice transcription
    OPENAI_API_KEY: str = ""

    # Database for bot registry (optional, for dynamic bot management)
    # If not set, bots are managed via API calls only
    DATABASE_URL: Optional[str] = None

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        case_sensitive = True


settings = Settings()
