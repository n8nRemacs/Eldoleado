"""Configuration for MAX User MCP Server (Multi-tenant WebSocket)."""

from pydantic_settings import BaseSettings
from typing import Optional


class Settings(BaseSettings):
    """Application settings from environment variables."""

    # n8n integration
    N8N_WEBHOOK_URL: str = ""  # Webhook URL for message forwarding

    # API security
    API_KEY: str = ""  # API key for protected endpoints

    # Server settings
    SERVER_HOST: str = "0.0.0.0"
    SERVER_PORT: int = 8771  # Max-user port

    # Database for account storage
    DATABASE_URL: Optional[str] = None  # PostgreSQL for accounts

    # Redis for session tokens
    REDIS_URL: str = "redis://localhost:6379"

    # Humanized delays
    HUMANIZED_ENABLED: bool = True  # Enable humanized delays

    # Alerting
    ALERT_TELEGRAM_BOT_TOKEN: str = ""
    ALERT_TELEGRAM_CHAT_ID: str = ""
    ALERT_N8N_WEBHOOK_URL: str = ""

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        case_sensitive = True


settings = Settings()
