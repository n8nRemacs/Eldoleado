"""Configuration settings for MAX.ru API (Wappi.pro)."""

from pydantic_settings import BaseSettings
from typing import Optional


class Settings(BaseSettings):
    """Application settings from environment variables."""

    # Server settings
    server_host: str = "0.0.0.0"
    server_port: int = 8768

    # Wappi.pro credentials for MAX
    wappi_api_token: str = ""
    wappi_profile_id: str = ""

    # API Security
    api_key: Optional[str] = None

    # n8n Integration
    n8n_webhook_url: Optional[str] = None

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        extra = "ignore"


settings = Settings()
