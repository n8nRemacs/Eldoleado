"""Configuration settings for Android API Gateway."""

from pydantic_settings import BaseSettings
from typing import Optional


class Settings(BaseSettings):
    """Application settings from environment variables."""

    # Server settings
    server_host: str = "0.0.0.0"
    server_port: int = 8780

    # n8n Integration
    n8n_base_url: str = "https://n8n.n8nsrv.ru/webhook"
    n8n_timeout: int = 30

    # JWT Settings
    jwt_secret: str = "change-me-in-production"
    jwt_algorithm: str = "HS256"
    jwt_expire_hours: int = 24

    # API Security
    api_key: Optional[str] = None

    # Rate Limiting
    rate_limit_per_minute: int = 60

    # Logging
    log_level: str = "INFO"

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        extra = "ignore"


settings = Settings()
