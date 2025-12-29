"""
MAX User MCP Server - Multi-tenant WebSocket User API Proxy.

FastAPI server for MAX User API integration via WebSocket.
Supports multiple user accounts with SMS authentication.
Similar to mcp-whatsapp-baileys.

Architecture:
- Each user authenticates via phone + SMS
- Sessions stored in Redis (cache) and PostgreSQL (persistent)
- Messages forwarded to n8n webhook
- Humanized delays for natural conversation
"""

import logging
from datetime import datetime
from typing import Optional, List
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, Header, Request, Query, BackgroundTasks
from fastapi.responses import JSONResponse
from pydantic import BaseModel, Field

import sys
import os

from config import settings
from session_manager import SessionManager, SessionStatus

# Add shared module to path
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..'))
try:
    from shared.health import get_health_checker
except ImportError:
    # Fallback if shared module not available
    class DummyHealthChecker:
        def __init__(self, name, version):
            self.name = name
            self.version = version
        def get_status(self):
            class Status:
                value = "healthy"
            return Status()
        def calculate_health_score(self):
            return 100
        def to_dict(self):
            return {"status": "healthy", "service": self.name, "version": self.version}
        def record_message_sent(self, *args):
            pass
        def record_message_received(self, *args):
            pass
        def record_error(self, *args):
            pass

    def get_health_checker(name, version):
        return DummyHealthChecker(name, version)

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Initialize health checker
health_checker = get_health_checker("max-user", "2.0.0")

# Session manager (initialized on startup)
session_manager: Optional[SessionManager] = None


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan manager."""
    global session_manager

    logger.info("Starting MAX User MCP Server...")

    # Initialize session manager
    session_manager = SessionManager(
        redis_url=settings.REDIS_URL if settings.REDIS_URL else None,
        database_url=settings.DATABASE_URL if settings.DATABASE_URL else None,
        default_webhook_url=settings.N8N_WEBHOOK_URL if settings.N8N_WEBHOOK_URL else None,
        humanized=settings.HUMANIZED_ENABLED,
    )

    await session_manager.connect()

    # Restore sessions from database
    await session_manager.restore_sessions()

    logger.info("MAX User MCP Server started")

    yield

    # Shutdown
    logger.info("Shutting down MAX User MCP Server...")
    if session_manager:
        await session_manager.close()
    logger.info("MAX User MCP Server stopped")


app = FastAPI(
    title="MAX User MCP Server",
    description="Multi-tenant WebSocket User API integration for MAX messenger",
    version="2.0.0",
    lifespan=lifespan
)


# ==================== Models ====================

class CreateSessionRequest(BaseModel):
    """Request to create a new session."""
    phone: str = Field(..., description="Phone number with country code (+79001234567)")
    webhook_url: Optional[str] = None
    tenant_id: int = 0


class VerifyCodeRequest(BaseModel):
    """Request to verify SMS code."""
    code: str = Field(..., description="6-digit SMS code")
    password_2fa: Optional[str] = None


class SendMessageRequest(BaseModel):
    """Request to send a message."""
    session_id: str
    chat_id: int
    text: str
    reply_to: Optional[str] = None
    skip_delay: bool = False


class SessionResponse(BaseModel):
    """Session information response."""
    session_id: str
    phone: str
    status: str
    hash: str
    user_id: int = 0
    user_name: str = ""
    webhook_url: str = ""
    tenant_id: int = 0
    created_at: Optional[str] = None
    updated_at: Optional[str] = None


# ==================== Helper Functions ====================

def check_api_key(api_key: str = Header(None, alias="X-API-Key")):
    """Validate API key for protected endpoints."""
    if settings.API_KEY and api_key != settings.API_KEY:
        raise HTTPException(status_code=401, detail="Invalid API key")


# ==================== Health & Info ====================

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    sessions = await session_manager.list_sessions() if session_manager else []
    connected = sum(1 for s in sessions if s.get("status") == SessionStatus.CONNECTED)

    return {
        "status": health_checker.get_status().value,
        "service": "max-user-mcp-server",
        "version": "2.0.0",
        "mode": "multi-tenant",
        "sessions_total": len(sessions),
        "sessions_connected": connected,
        "health_score": health_checker.calculate_health_score()
    }


@app.get("/health/extended")
async def health_extended():
    """Extended health check with metrics."""
    return health_checker.to_dict()


# ==================== Session Management ====================

@app.post("/sessions/create", response_model=SessionResponse)
async def create_session(
    request: CreateSessionRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """
    Create new session and start SMS authentication.

    Returns session_id and sends SMS code to the phone.
    """
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    try:
        session = await session_manager.create_session(
            phone=request.phone,
            webhook_url=request.webhook_url,
            tenant_id=request.tenant_id,
        )
        return SessionResponse(**session.to_dict())
    except Exception as e:
        logger.error(f"Failed to create session: {e}")
        raise HTTPException(status_code=400, detail=str(e))


@app.post("/sessions/{session_id}/verify", response_model=SessionResponse)
async def verify_code(
    session_id: str,
    request: VerifyCodeRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """
    Verify SMS code to complete authentication.

    If 2FA is enabled, provide password_2fa.
    """
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    try:
        session = await session_manager.verify_code(
            session_id=session_id,
            code=request.code,
            password_2fa=request.password_2fa,
        )
        return SessionResponse(**session.to_dict())
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))
    except Exception as e:
        logger.error(f"Verification failed: {e}")
        error_detail = str(e)
        if "2FA_REQUIRED" in error_detail:
            raise HTTPException(status_code=400, detail="2FA password required")
        raise HTTPException(status_code=400, detail=error_detail)


@app.post("/sessions/{session_id}/reconnect", response_model=SessionResponse)
async def reconnect_session(
    session_id: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """
    Reconnect session using stored token.

    Use this to restore a disconnected session.
    """
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    try:
        session = await session_manager.reconnect_session(session_id)
        return SessionResponse(**session.to_dict())
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))
    except Exception as e:
        logger.error(f"Reconnect failed: {e}")
        raise HTTPException(status_code=400, detail=str(e))


@app.delete("/sessions/{session_id}")
async def delete_session(
    session_id: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Delete session and logout."""
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    try:
        await session_manager.delete_session(session_id)
        return {"status": "ok", "message": f"Session {session_id} deleted"}
    except Exception as e:
        logger.error(f"Delete failed: {e}")
        raise HTTPException(status_code=400, detail=str(e))


@app.get("/sessions", response_model=List[SessionResponse])
async def list_sessions(api_key: str = Header(None, alias="X-API-Key")):
    """List all sessions."""
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    sessions = await session_manager.list_sessions()
    return [SessionResponse(**s) for s in sessions]


@app.get("/sessions/{session_id}", response_model=SessionResponse)
async def get_session(
    session_id: str,
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get session info."""
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    session = session_manager.get_session(session_id)
    if not session:
        raise HTTPException(status_code=404, detail="Session not found")

    return SessionResponse(**session.to_dict())


# ==================== Messages ====================

@app.post("/api/send")
async def send_message(
    request: SendMessageRequest,
    api_key: str = Header(None, alias="X-API-Key")
):
    """
    Send message via session.

    Uses humanized delays unless skip_delay=true.
    """
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    try:
        result = await session_manager.send_message(
            session_id=request.session_id,
            chat_id=request.chat_id,
            text=request.text,
            reply_to=request.reply_to,
            skip_delay=request.skip_delay,
        )

        # Record metrics
        health_checker.record_message_sent(request.session_id)

        return {
            "success": True,
            "session_id": request.session_id,
            "chat_id": request.chat_id,
            "result": result
        }
    except ValueError as e:
        raise HTTPException(status_code=404, detail=str(e))
    except Exception as e:
        health_checker.record_error(request.session_id, str(e))
        logger.error(f"Send message failed: {e}")
        raise HTTPException(status_code=400, detail=str(e))


# ==================== Chat Operations ====================

@app.get("/api/chats")
async def get_chats(
    session_id: str = Query(...),
    count: int = Query(40),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get list of chats for session."""
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    client = session_manager.get_client(session_id)
    if not client:
        raise HTTPException(status_code=404, detail="Session not found")

    try:
        result = await client.get_chats(count=count)
        return {"success": True, "chats": result}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


@app.get("/api/chat/{chat_id}")
async def get_chat(
    chat_id: int,
    session_id: str = Query(...),
    count: int = Query(50),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Get chat with message history."""
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    client = session_manager.get_client(session_id)
    if not client:
        raise HTTPException(status_code=404, detail="Session not found")

    try:
        result = await client.get_chat(chat_id, count=count)
        return {"success": True, "chat": result}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


@app.post("/api/typing")
async def set_typing(
    session_id: str = Query(...),
    chat_id: int = Query(...),
    typing: bool = Query(True),
    api_key: str = Header(None, alias="X-API-Key")
):
    """Set typing indicator."""
    check_api_key(api_key)

    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    client = session_manager.get_client(session_id)
    if not client:
        raise HTTPException(status_code=404, detail="Session not found")

    try:
        result = await client.set_typing(chat_id, typing)
        return {"success": True, "result": result}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


# ==================== Webhook Handler ====================

@app.post("/webhook/max/{session_hash}")
async def webhook_handler(
    session_hash: str,
    request: Request,
    background_tasks: BackgroundTasks,
):
    """
    Handle incoming MAX messages (for external webhook callbacks).

    Note: Most messages come via WebSocket and are handled internally.
    This endpoint is for compatibility with external systems.
    """
    if not session_manager:
        raise HTTPException(status_code=503, detail="Service not ready")

    session = session_manager.get_session_by_hash(session_hash)
    if not session:
        logger.warning(f"Webhook for unknown session hash: {session_hash}")
        raise HTTPException(status_code=404, detail="Session not found")

    try:
        data = await request.json()
    except Exception as e:
        logger.error(f"Invalid JSON in webhook: {e}")
        raise HTTPException(status_code=400, detail="Invalid JSON")

    logger.info(f"Webhook received for session {session.session_id}")
    health_checker.record_message_received(session.session_id)

    return {"ok": True}


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host=settings.SERVER_HOST,
        port=settings.SERVER_PORT,
        reload=True
    )
