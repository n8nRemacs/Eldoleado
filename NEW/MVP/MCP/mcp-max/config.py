"""Configuration for MAX MCP Server."""

from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    """Application settings from environment variables."""

    # MAX Bot API
    MAX_ACCESS_TOKEN: str = ""  # Bot access token from MasterBot

    # Webhook settings
    MAX_WEBHOOK_SECRET: str = ""  # Secret for webhook signature verification

    # n8n integration
    N8N_WEBHOOK_URL: str = ""  # Webhook URL for BAT IN MAX

    # API security
    API_KEY: str = ""  # API key for protected endpoints

    # Server settings
    SERVER_HOST: str = "0.0.0.0"
    SERVER_PORT: int = 8768

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"
        case_sensitive = True


settings = Settings()
