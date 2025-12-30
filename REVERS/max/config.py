"""MCP MAX Server Configuration."""

import os
from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """Server settings from environment variables."""

    # Server
    HOST: str = "0.0.0.0"
    PORT: int = 8768

    # n8n Integration
    N8N_WEBHOOK_URL: str = ""  # URL to forward incoming messages

    # Redis for session storage
    REDIS_URL: str = "redis://localhost:6379/4"

    # Alerts
    ALERT_TELEGRAM_BOT_TOKEN: str = ""
    ALERT_TELEGRAM_CHAT_ID: str = ""
    ALERT_N8N_WEBHOOK_URL: str = ""

    # Logging
    LOG_LEVEL: str = "INFO"

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"


settings = Settings()
