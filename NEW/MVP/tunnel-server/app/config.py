"""
Configuration loader for Tunnel Server
"""

import os
from typing import Optional
from pydantic_settings import BaseSettings
from pydantic import Field


class PostgresSettings(BaseSettings):
    host: str = Field(default="185.221.214.83")
    port: int = Field(default=6544)
    database: str = Field(default="postgres")
    user: str = Field(default="supabase_admin")
    password: str = Field(default="")

    class Config:
        env_prefix = "POSTGRES_"

    @property
    def dsn(self) -> str:
        return f"postgresql+asyncpg://{self.user}:{self.password}@{self.host}:{self.port}/{self.database}"


class Neo4jSettings(BaseSettings):
    uri: str = Field(default="bolt+ssc://45.144.177.128:7687")
    user: str = Field(default="neo4j")
    password: str = Field(default="")

    class Config:
        env_prefix = "NEO4J_"


class RedisSettings(BaseSettings):
    host: str = Field(default="45.144.177.128")
    port: int = Field(default=6379)

    class Config:
        env_prefix = "REDIS_"

    @property
    def url(self) -> str:
        return f"redis://{self.host}:{self.port}"


class AICoreSettings(BaseSettings):
    enabled: bool = Field(default=False)
    transcription_url: str = Field(default="http://localhost:8900/transcribe")
    normalization_url: str = Field(default="http://localhost:8900/normalize")
    timeout: int = Field(default=30)

    class Config:
        env_prefix = "AI_CORE_"


class Settings(BaseSettings):
    # Server
    host: str = Field(default="0.0.0.0")
    port: int = Field(default=8800)
    log_level: str = Field(default="INFO")

    # Database
    postgres: PostgresSettings = Field(default_factory=PostgresSettings)
    neo4j: Neo4jSettings = Field(default_factory=Neo4jSettings)
    redis: RedisSettings = Field(default_factory=RedisSettings)

    # AI Core
    ai_core: AICoreSettings = Field(default_factory=AICoreSettings)

    # Webhooks
    n8n_webhook_url: str = Field(default="https://n8n.n8nsrv.ru/webhook/incoming")

    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"


# Global settings instance
settings = Settings()
