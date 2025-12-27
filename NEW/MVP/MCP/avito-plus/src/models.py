"""Pydantic models for Avito-Plus."""

from datetime import datetime
from enum import Enum
from typing import Optional, List, Dict, Any
from pydantic import BaseModel, Field


class AccountStatus(str, Enum):
    """Account status."""
    CREATED = "created"
    STARTING = "starting"
    RUNNING = "running"
    LOGGED_IN = "logged_in"
    SMS_REQUIRED = "sms_required"
    CAPTCHA_REQUIRED = "captcha_required"
    ERROR = "error"
    STOPPED = "stopped"


class ProxyType(str, Enum):
    """Proxy type."""
    CLIENT_PC = "client_pc"
    CLIENT_ROUTER = "client_router"
    MOBILE = "mobile"
    DATACENTER = "datacenter"


class ProxyConfig(BaseModel):
    """Proxy configuration."""
    id: str
    type: ProxyType
    transport: str = "wireguard"
    wireguard_ip: Optional[str] = None
    socks_port: int = 1080
    host: Optional[str] = None
    port: Optional[int] = None
    username: Optional[str] = None
    password: Optional[str] = None
    client_name: Optional[str] = None
    location: Optional[str] = None
    isp: Optional[str] = None
    priority: int = 1
    enabled: bool = True


class AccountState(BaseModel):
    """Account state."""
    id: str
    status: AccountStatus = AccountStatus.CREATED
    proxy_id: Optional[str] = None
    hash_id: Optional[str] = None
    user_id: Optional[str] = None
    created_at: datetime = Field(default_factory=datetime.now)
    last_activity: Optional[datetime] = None
    error: Optional[str] = None


class LoginRequest(BaseModel):
    """Login request."""
    phone: str
    password: str


class SmsRequest(BaseModel):
    """SMS code request."""
    code: str


class SendMessageRequest(BaseModel):
    """Send message request."""
    chat_id: str
    text: str


class AvitoMessage(BaseModel):
    """Avito message from WebSocket."""
    id: str
    chat_id: str
    author_id: str
    text: Optional[str] = None
    type: str = "text"
    created: datetime
    direction: str  # "in" or "out"
    raw: Optional[Dict[str, Any]] = None


class AvitoChat(BaseModel):
    """Avito chat."""
    id: str
    title: Optional[str] = None
    last_message: Optional[AvitoMessage] = None
    unread_count: int = 0
    item_id: Optional[str] = None
    user_id: Optional[str] = None


class InterceptedTraffic(BaseModel):
    """Intercepted network traffic."""
    timestamp: datetime
    type: str  # "request", "response", "ws_created", "ws_frame"
    url: Optional[str] = None
    method: Optional[str] = None
    status: Optional[int] = None
    headers: Optional[Dict[str, str]] = None
    body: Optional[str] = None


class WebhookPayload(BaseModel):
    """Webhook payload for n8n."""
    event: str  # "new_message", "auth_required", "error"
    account_id: str
    timestamp: datetime = Field(default_factory=datetime.now)
    data: Dict[str, Any] = Field(default_factory=dict)
