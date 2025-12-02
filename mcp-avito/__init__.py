"""Avito Messenger REST API Server Package."""

from .avito_client import (
    AvitoClient,
    AvitoChat,
    AvitoMessage,
    MessageType,
    AvitoAPIError,
    RateLimitExceeded,
)
from .config import settings

__all__ = [
    "AvitoClient",
    "AvitoChat",
    "AvitoMessage",
    "MessageType",
    "AvitoAPIError",
    "RateLimitExceeded",
    "settings",
]

__version__ = "1.0.0"
