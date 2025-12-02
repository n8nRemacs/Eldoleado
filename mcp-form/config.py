"""Configuration settings for Form Submission API."""

from pydantic_settings import BaseSettings
from typing import Optional


class Settings(BaseSettings):
    """Application settings from environment variables."""

    # Server settings
    server_host: str = "0.0.0.0"
    server_port: int = 8770

    # API Security
    api_key: Optional[str] = None

    # n8n Integration
    n8n_webhook_url: Optional[str] = None

    # Rate limiting
    rate_limit_requests: int = 100
    rate_limit_window: int = 60

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        extra = "ignore"


settings = Settings()
