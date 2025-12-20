"""Configuration for API Canary Service."""

from pydantic_settings import BaseSettings
from typing import Optional


class Settings(BaseSettings):
    """Application settings from environment variables."""

    # Server
    CANARY_HOST: str = "0.0.0.0"
    CANARY_PORT: int = 8790

    # Alerting
    ALERT_TELEGRAM_BOT_TOKEN: str = ""
    ALERT_TELEGRAM_CHAT_ID: str = ""
    ALERT_N8N_WEBHOOK_URL: str = ""

    # Residential Proxy (for external API checks)
    PROXY_URL: str = ""  # e.g., http://user:pass@proxy.example.com:8080
    USE_PROXY_FOR_EXTERNAL: bool = True  # Use proxy for VK, Avito external APIs

    # MCP Server URLs (internal)
    WHATSAPP_URL: str = "http://217.145.79.27:3000"
    TELEGRAM_URL: str = "http://217.145.79.27:8767"
    VK_URL: str = "http://45.144.177.128:8767"
    MAX_URL: str = "http://45.144.177.128:8768"
    AVITO_URL: str = "http://45.144.177.128:8765"

    # API Keys for MCP servers (optional)
    WHATSAPP_API_KEY: str = ""
    TELEGRAM_API_KEY: str = ""
    VK_API_KEY: str = ""
    MAX_API_KEY: str = ""
    AVITO_API_KEY: str = ""

    # Service Accounts - WhatsApp
    WA_SERVICE_SESSION_ID: str = ""
    WA_SERVICE_PHONE: str = ""

    # Service Accounts - Telegram
    TG_SERVICE_BOT_TOKEN: str = ""
    TG_SERVICE_CHAT_ID: str = ""

    # Service Accounts - VK
    VK_SERVICE_GROUP_HASH: str = ""
    VK_SERVICE_ADMIN_ID: str = ""

    # Service Accounts - MAX
    MAX_SERVICE_ACCESS_TOKEN: str = ""
    MAX_SERVICE_CHAT_ID: str = ""

    # Service Accounts - Avito
    AVITO_SERVICE_USER_HASH: str = ""

    # Check Intervals (seconds)
    HEALTH_CHECK_INTERVAL: int = 60      # 1 min
    API_CHECK_INTERVAL: int = 300        # 5 min
    MESSAGE_CHECK_INTERVAL: int = 900    # 15 min (disabled for now)

    # Alert Settings
    ALERT_COOLDOWN: int = 300            # 5 min between same alerts
    CONSECUTIVE_FAILURES_THRESHOLD: int = 3  # Alert after N failures

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        case_sensitive = True


settings = Settings()
