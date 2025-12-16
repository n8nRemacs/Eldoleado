"""
Pydantic models for Tunnel Server
"""

from datetime import datetime
from typing import Optional, Dict, Any, List
from enum import Enum
from pydantic import BaseModel, Field


# === Enums ===

class ChannelType(str, Enum):
    WHATSAPP = "whatsapp"
    TELEGRAM = "telegram"
    AVITO = "avito"
    MAX = "max"
    VK = "vk"
    HTTP_PROXY = "http_proxy"


class MessageDirection(str, Enum):
    INCOMING = "incoming"  # From client
    OUTGOING = "outgoing"  # To client


class MessageStatus(str, Enum):
    RECEIVED = "received"  # Incoming message received
    PENDING_APPROVAL = "pending_approval"  # Operator audio waiting for approval
    APPROVED = "approved"  # Operator approved transcription
    SENT = "sent"  # Sent to client
    DELIVERED = "delivered"  # Delivered to client
    READ = "read"  # Read by client
    FAILED = "failed"  # Failed to send


class SenderType(str, Enum):
    CLIENT = "client"
    OPERATOR = "operator"
    AI = "ai"
    SYSTEM = "system"


# === Tunnel Protocol ===

class TunnelCommand(BaseModel):
    """Command from server to phone"""
    id: str
    action: str  # http_request, ping, status
    service: Optional[str] = None
    method: Optional[str] = None
    path: Optional[str] = None
    headers: Optional[Dict[str, str]] = None
    body: Optional[Dict[str, Any]] = None


class TunnelResponse(BaseModel):
    """Response from phone to server"""
    id: str
    status: Optional[int] = None
    headers: Optional[Dict[str, str]] = None
    body: Optional[Any] = None
    error: Optional[str] = None


class TunnelIncoming(BaseModel):
    """Incoming message from phone (client message)"""
    action: str = "incoming"
    service: str
    data: Dict[str, Any]
    timestamp: Optional[str] = None


# === Messages ===

class NormalizedMessage(BaseModel):
    """
    Unified message format for all channels.
    This is what flows through the pipeline.
    """
    # Identifiers
    id: str
    external_id: Optional[str] = None  # Original ID from channel

    # Routing
    tenant_id: str
    channel: ChannelType
    channel_account_id: Optional[str] = None  # Which account sent/received

    # Dialog
    dialog_id: Optional[str] = None
    chat_id: str  # External chat ID from channel

    # Message content
    direction: MessageDirection
    status: Optional[str] = None  # MessageStatus value
    sender_type: SenderType
    sender_id: Optional[str] = None
    sender_name: Optional[str] = None

    text: str = ""
    media_type: Optional[str] = None  # photo, video, document, voice, audio
    media_url: Optional[str] = None
    media_id: Optional[str] = None

    # Audio processing
    is_audio: bool = False
    audio_duration: Optional[int] = None  # seconds
    transcription: Optional[str] = None  # Transcribed text
    normalized_text: Optional[str] = None  # Beautified text (for operator audio)

    # Metadata
    timestamp: datetime = Field(default_factory=datetime.utcnow)
    raw_data: Optional[Dict[str, Any]] = None  # Original message from channel


class MessageContext(BaseModel):
    """
    Context loaded for message processing.
    Passed to AI Core.
    """
    # Tenant
    tenant_id: str
    tenant_name: Optional[str] = None
    tenant_settings: Optional[Dict[str, Any]] = None

    # Client
    client_id: Optional[str] = None
    client_name: Optional[str] = None
    client_phone: Optional[str] = None
    client_history: Optional[List[Dict]] = None  # Previous interactions

    # Dialog
    dialog_id: Optional[str] = None
    dialog_status: Optional[str] = None
    dialog_stage: Optional[str] = None

    # Recent messages
    messages: List[NormalizedMessage] = []

    # Graph data (from Neo4j)
    devices: Optional[List[Dict]] = None
    issues: Optional[List[Dict]] = None

    # AI settings
    ai_freedom_level: int = 50  # 0-100
    operation_mode: str = "assist"  # auto, assist


# === Pipeline Results ===

class PreAIResult(BaseModel):
    """Result of Pre-AI pipeline"""
    message: NormalizedMessage
    context: MessageContext

    # Decision
    needs_ai: bool = True
    skip_reason: Optional[str] = None  # Why AI was skipped

    # Routing
    assigned_operator_id: Optional[str] = None


class AudioProcessingResult(BaseModel):
    """Result of audio processing (transcription + normalization)"""
    transcription: Optional[str] = None  # Raw transcription
    normalized_text: Optional[str] = None  # Beautified text (operator audio only)
    processing_time_ms: Optional[int] = None
    model: Optional[str] = None


class AIResponse(BaseModel):
    """Response from AI Core (audio processing only, no auto-replies)"""
    # Audio processing results
    audio_processed: bool = False
    audio_result: Optional[AudioProcessingResult] = None

    # Extracted data (device info, problem, etc.)
    extracted_data: Optional[Dict[str, Any]] = None

    # Metadata
    processing_time_ms: Optional[int] = None


class PostAIResult(BaseModel):
    """Result of Post-AI pipeline"""
    # Original
    message: NormalizedMessage
    context: MessageContext

    # AI response (if any)
    ai_response: Optional[AIResponse] = None

    # Final response to send
    response_text: Optional[str] = None
    response_media: Optional[Dict] = None

    # Actions taken
    actions_executed: List[str] = []

    # Notifications
    notify_operators: bool = False
    operator_ids: List[str] = []


# === API Models ===

class SendMessageRequest(BaseModel):
    """Request to send message through tunnel"""
    tenant_id: str
    channel: ChannelType
    chat_id: str
    text: str
    media_type: Optional[str] = None
    media_url: Optional[str] = None
    operator_id: Optional[str] = None


class TunnelStatus(BaseModel):
    """Status of a connected tunnel"""
    server_id: str
    connected: bool
    connected_at: Optional[datetime] = None
    services: List[str] = []
    stats: Dict[str, Any] = {}
