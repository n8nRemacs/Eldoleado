"""Configuration for Instagram MCP Server."""

from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """Application settings from environment variables."""

    # Instagram/Facebook API
    INSTAGRAM_ACCESS_TOKEN: str = ""  # Page Access Token with Instagram permissions
    INSTAGRAM_ACCOUNT_ID: str = ""  # Instagram Business Account ID
    FACEBOOK_APP_SECRET: str = ""  # App secret for webhook signature verification

    # Webhook settings
    WEBHOOK_VERIFY_TOKEN: str = ""  # Token for Facebook webhook verification

    # n8n integration
    N8N_WEBHOOK_URL: str = ""  # Webhook URL for BAT IN Instagram

    # API security
    API_KEY: str = ""  # API key for protected endpoints

    # Server settings
    SERVER_HOST: str = "0.0.0.0"
    SERVER_PORT: int = 8769

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        case_sensitive = True


settings = Settings()
