"""Configuration for Avito API Server."""

from pydantic_settings import BaseSettings
from pydantic import Field


class Settings(BaseSettings):
    """Application settings loaded from environment variables."""

    # Avito API
    avito_client_id: str = Field(default="", description="Avito API Client ID")
    avito_client_secret: str = Field(default="", description="Avito API Client Secret")
    avito_user_id: str = Field(default="", description="Avito User ID for API calls")
    avito_api_base_url: str = Field(
        default="https://api.avito.ru",
        description="Avito API Base URL"
    )

    # Redis
    redis_url: str = Field(default="redis://localhost:6379", description="Redis URL")
    redis_token_key: str = Field(
        default="avito_access_token",
        description="Redis key for access token"
    )

    # n8n Integration
    n8n_webhook_url: str = Field(
        default="",
        description="n8n webhook URL for forwarding messages"
    )

    # Server
    server_host: str = Field(default="0.0.0.0", description="Server Host")
    server_port: int = Field(default=8765, description="Server Port")

    # Rate Limiting
    rate_limit_requests: int = Field(
        default=100,
        description="Max requests per minute to Avito API"
    )
    rate_limit_window: int = Field(
        default=60,
        description="Rate limit window in seconds"
    )

    # Connection Pool
    max_connections: int = Field(
        default=20,
        description="Max concurrent connections to Avito API"
    )
    connection_timeout: float = Field(
        default=30.0,
        description="HTTP connection timeout in seconds"
    )

    # Retry
    max_retries: int = Field(default=3, description="Max retry attempts")
    retry_min_wait: float = Field(default=1.0, description="Min wait between retries (seconds)")
    retry_max_wait: float = Field(default=10.0, description="Max wait between retries (seconds)")

    # API Key for server authentication (optional)
    api_key: str = Field(default="", description="API key for server authentication")

    # Alerting
    alert_telegram_bot_token: str = Field(default="", description="Telegram bot token for alerts")
    alert_telegram_chat_id: str = Field(default="", description="Telegram chat ID for alerts")
    alert_n8n_webhook_url: str = Field(default="", description="n8n webhook URL for alerts")

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"


settings = Settings()
