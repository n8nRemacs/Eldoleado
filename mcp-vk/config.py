"""Configuration settings for VK Community Bot API."""

from pydantic_settings import BaseSettings
from typing import Optional


class Settings(BaseSettings):
    """Application settings from environment variables."""

    # Server settings
    server_host: str = "0.0.0.0"
    server_port: int = 8767

    # VK credentials
    vk_access_token: str = ""
    vk_group_id: int = 0
    vk_confirmation_code: str = ""
    vk_secret_key: Optional[str] = None

    # API Security
    api_key: Optional[str] = None

    # n8n Integration
    n8n_webhook_url: Optional[str] = None

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        extra = "ignore"


settings = Settings()
