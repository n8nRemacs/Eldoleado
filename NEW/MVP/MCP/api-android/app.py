"""Android API Gateway Server.

FastAPI server providing HTTP endpoints for Android app.
Proxies requests to n8n webhooks with JWT authentication.
"""

import logging
from typing import Optional
from datetime import datetime
from contextlib import asynccontextmanager

from fastapi import FastAPI, Request, HTTPException, Depends, Query, WebSocket, WebSocketDisconnect
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
import asyncio
import json

from config import settings
from n8n_client import N8NClient, N8NClientError
from db import init_db, close_db, get_pool
from auth import get_current_operator, get_internal_or_operator, create_token, TokenData
from models import (
    LoginRequest, LoginResponse,
    AppealsListRequest, TakeAppealRequest, RejectAppealRequest,
    SendMessageRequest, SendPromoRequest, NormalizeRequest,
    DeviceCreateRequest, DeviceUpdateRequest,
    RepairCreateRequest, RepairUpdateRequest,
    RegisterFCMRequest, UpdateSettingsRequest, UpdateAppealModeRequest,
    AvitoAuthRequest, ChannelAuthResponse, ChannelStatusResponse,
    APIResponse
)

# Configure logging
logging.basicConfig(
    level=getattr(logging, settings.log_level),
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# Global n8n client
n8n_client: Optional[N8NClient] = None


# ========== WebSocket Connection Manager (replaces FCM) ==========

class ConnectionManager:
    """Manages WebSocket connections for push notifications."""

    def __init__(self):
        # operator_id -> WebSocket
        self.active_connections: dict[str, WebSocket] = {}

    async def connect(self, websocket: WebSocket, operator_id: str):
        await websocket.accept()
        # Close existing connection if any
        if operator_id in self.active_connections:
            try:
                await self.active_connections[operator_id].close()
            except:
                pass
        self.active_connections[operator_id] = websocket
        logger.info(f"WebSocket connected: operator={operator_id}, total={len(self.active_connections)}")

    def disconnect(self, operator_id: str):
        if operator_id in self.active_connections:
            del self.active_connections[operator_id]
            logger.info(f"WebSocket disconnected: operator={operator_id}, total={len(self.active_connections)}")

    async def send_to_operator(self, operator_id: str, message: dict):
        """Send push notification to specific operator."""
        if operator_id in self.active_connections:
            try:
                await self.active_connections[operator_id].send_json(message)
                return True
            except Exception as e:
                logger.error(f"Failed to send to operator {operator_id}: {e}")
                self.disconnect(operator_id)
        return False

    async def broadcast_to_tenant(self, tenant_id: str, message: dict):
        """Broadcast to all operators of a tenant (requires DB lookup)."""
        # TODO: Implement tenant-based broadcast
        pass

    def get_connected_operators(self) -> list[str]:
        return list(self.active_connections.keys())


# Global connection manager
ws_manager = ConnectionManager()


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan - init and cleanup."""
    global n8n_client
    n8n_client = N8NClient()
    await n8n_client.connect()
    await init_db()
    logger.info("API Gateway started")
    yield
    await close_db()
    await n8n_client.close()
    logger.info("API Gateway stopped")


app = FastAPI(
    title="Eldoleado Android API",
    description="API Gateway for Android application",
    version="1.0.0",
    lifespan=lifespan
)

# CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# ========== Error Handler ==========

@app.exception_handler(N8NClientError)
async def n8n_error_handler(request: Request, exc: N8NClientError):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "success": False,
            "error": exc.message,
            "details": exc.response
        }
    )


# ========== Health & Info ==========

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": "healthy",
        "service": "android-api",
        "timestamp": datetime.now().isoformat()
    }


@app.get("/info")
async def server_info():
    """Server configuration info."""
    return {
        "service": "android-api",
        "version": "1.0.0",
        "n8n_url": settings.n8n_base_url[:30] + "..."
    }


# ========== Auth Endpoints ==========

@app.post("/api/auth/login", response_model=LoginResponse)
async def login(request: LoginRequest):
    """Login operator with phone and PIN."""
    result = await n8n_client.auth_login(request.phone, request.pin)

    if not result.get("success"):
        return LoginResponse(
            success=False,
            error=result.get("error", "Login failed")
        )

    operator = result.get("operator", {})
    operator_id = operator.get("id")
    tenant_id = operator.get("tenant_id")

    if not operator_id or not tenant_id:
        return LoginResponse(
            success=False,
            error="Invalid operator data"
        )

    # Create JWT token
    token = create_token(
        operator_id=operator_id,
        tenant_id=tenant_id,
        extra_data={
            "name": operator.get("name"),
            "role": operator.get("role")
        }
    )

    return LoginResponse(
        success=True,
        token=token,
        operator=operator
    )


@app.post("/api/auth/logout")
async def logout(operator: TokenData = Depends(get_current_operator)):
    """Logout current operator."""
    # Optionally call n8n to invalidate session
    # await n8n_client.auth_logout(...)
    return {"success": True, "message": "Logged out"}


# ========== Appeals Endpoints ==========

@app.get("/api/appeals")
async def get_appeals(
    status: Optional[str] = Query(None),
    limit: int = Query(50, ge=1, le=200),
    offset: int = Query(0, ge=0),
    operator: TokenData = Depends(get_current_operator)
):
    """Get list of appeals for operator."""
    result = await n8n_client.get_appeals_list(
        operator_id=operator.operator_id,
        tenant_id=operator.tenant_id,
        status=status,
        limit=limit,
        offset=offset
    )
    return result


@app.get("/api/appeals/{appeal_id}")
async def get_appeal(
    appeal_id: str,
    operator: TokenData = Depends(get_current_operator)
):
    """Get appeal details."""
    result = await n8n_client.get_appeal_detail(
        appeal_id=appeal_id,
        operator_id=operator.operator_id
    )
    return result


@app.post("/api/appeals/{appeal_id}/take")
async def take_appeal(
    appeal_id: str,
    operator: TokenData = Depends(get_current_operator)
):
    """Take appeal for processing."""
    result = await n8n_client.take_appeal(
        appeal_id=appeal_id,
        operator_id=operator.operator_id
    )
    return result


@app.post("/api/appeals/{appeal_id}/reject")
async def reject_appeal(
    appeal_id: str,
    request: RejectAppealRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Reject appeal."""
    result = await n8n_client.reject_appeal(
        appeal_id=appeal_id,
        operator_id=operator.operator_id,
        reason=request.reason
    )
    return result


@app.post("/api/appeals/{appeal_id}/send")
async def send_message(
    appeal_id: str,
    request: SendMessageRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Send message to client."""
    result = await n8n_client.send_message(
        appeal_id=appeal_id,
        operator_id=operator.operator_id,
        text=request.text,
        template_id=request.template_id
    )
    return result


@app.post("/api/appeals/{appeal_id}/promo")
async def send_promo(
    appeal_id: str,
    request: SendPromoRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Send promo message."""
    result = await n8n_client.send_promo(
        appeal_id=appeal_id,
        operator_id=operator.operator_id,
        promo_text=request.promo_text
    )
    return result


@app.post("/api/appeals/{appeal_id}/normalize")
async def normalize_appeal(
    appeal_id: str,
    operator: TokenData = Depends(get_current_operator)
):
    """Normalize appeal data."""
    result = await n8n_client.normalize_appeal(
        appeal_id=appeal_id,
        operator_id=operator.operator_id
    )
    return result


@app.patch("/api/appeals/{appeal_id}/mode")
async def update_mode(
    appeal_id: str,
    request: UpdateAppealModeRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Update appeal mode (ai/manual)."""
    result = await n8n_client.update_appeal_mode(
        appeal_id=appeal_id,
        mode=request.mode
    )
    return result


# ========== Device Endpoints ==========

@app.post("/api/appeals/{appeal_id}/devices")
async def create_device(
    appeal_id: str,
    request: DeviceCreateRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Create device for appeal."""
    data = request.model_dump(exclude_none=True)
    data["appeal_id"] = appeal_id
    result = await n8n_client.create_device(data)
    return result


@app.patch("/api/devices/{device_id}")
async def update_device(
    device_id: str,
    request: DeviceUpdateRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Update device."""
    data = request.model_dump(exclude_none=True)
    result = await n8n_client.update_device(device_id, data)
    return result


@app.delete("/api/devices/{device_id}")
async def delete_device(
    device_id: str,
    operator: TokenData = Depends(get_current_operator)
):
    """Delete device."""
    result = await n8n_client.delete_device(device_id)
    return result


# ========== Repair Endpoints ==========

@app.post("/api/devices/{device_id}/repairs")
async def create_repair(
    device_id: str,
    request: RepairCreateRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Create repair for device."""
    data = request.model_dump(exclude_none=True)
    data["appeal_device_id"] = device_id
    result = await n8n_client.create_repair(data)
    return result


@app.patch("/api/repairs/{repair_id}")
async def update_repair(
    repair_id: str,
    request: RepairUpdateRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Update repair."""
    data = request.model_dump(exclude_none=True)
    result = await n8n_client.update_repair(repair_id, data)
    return result


@app.delete("/api/repairs/{repair_id}")
async def delete_repair(
    repair_id: str,
    operator: TokenData = Depends(get_current_operator)
):
    """Delete repair."""
    result = await n8n_client.delete_repair(repair_id)
    return result


# ========== Settings Endpoints ==========

@app.post("/api/fcm/register")
async def register_fcm(
    request: RegisterFCMRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Register FCM token for push notifications."""
    result = await n8n_client.register_fcm(
        operator_id=operator.operator_id,
        fcm_token=request.fcm_token,
        device_info=request.device_info
    )
    return result


@app.patch("/api/settings")
async def update_settings(
    request: UpdateSettingsRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Update operator settings."""
    result = await n8n_client.update_settings(
        operator_id=operator.operator_id,
        settings=request.settings
    )
    return result


# ========== Channel Auth Endpoints ==========

CHANNEL_IDS = {
    "avito": 3,
    "telegram": 1,
    "whatsapp": 2,
    "vk": 4,
    "max": 5,
}


@app.post("/api/channels/avito/auth", response_model=ChannelAuthResponse)
async def avito_auth(
    request: AvitoAuthRequest,
    operator: TokenData = Depends(get_current_operator)
):
    """Save Avito sessid to database."""
    import json

    pool = await get_pool()

    credentials = {
        "sessid": request.sessid,
        "auth": "1"
    }
    if request.user_id:
        credentials["user_id"] = request.user_id
    if request.email:
        credentials["email"] = request.email

    try:
        # Upsert: insert or update on conflict
        result = await pool.fetchrow("""
            INSERT INTO elo_t_channel_accounts
                (tenant_id, channel_id, account_id, account_name, credentials, session_status, is_active)
            VALUES
                ($1, $2, $3, $4, $5, 'active', true)
            ON CONFLICT (tenant_id, channel_id, account_id)
            DO UPDATE SET
                credentials = $5,
                account_name = COALESCE($4, elo_t_channel_accounts.account_name),
                session_status = 'active',
                updated_at = NOW(),
                error_count = 0,
                last_error = NULL
            RETURNING id, session_status
        """,
            operator.tenant_id,
            CHANNEL_IDS["avito"],
            request.user_id or "default",
            request.email,
            json.dumps(credentials)
        )

        logger.info(f"Avito auth saved for tenant {operator.tenant_id}")

        return ChannelAuthResponse(
            success=True,
            channel_account_id=str(result["id"]),
            session_status=result["session_status"]
        )

    except Exception as e:
        logger.error(f"Avito auth error: {e}")
        return ChannelAuthResponse(
            success=False,
            error=str(e)
        )


@app.get("/api/channels/avito/status", response_model=ChannelStatusResponse)
async def avito_status(
    operator: TokenData = Depends(get_current_operator)
):
    """Get Avito channel status."""
    pool = await get_pool()

    try:
        result = await pool.fetchrow("""
            SELECT account_name, session_status, updated_at
            FROM elo_t_channel_accounts
            WHERE tenant_id = $1 AND channel_id = $2 AND is_active = true
            LIMIT 1
        """, operator.tenant_id, CHANNEL_IDS["avito"])

        if not result:
            return ChannelStatusResponse(
                success=True,
                channel="avito",
                session_status="not_configured"
            )

        return ChannelStatusResponse(
            success=True,
            channel="avito",
            session_status=result["session_status"],
            account_name=result["account_name"],
            last_check=result["updated_at"]
        )

    except Exception as e:
        logger.error(f"Avito status error: {e}")
        return ChannelStatusResponse(
            success=False,
            channel="avito",
            error=str(e)
        )


@app.delete("/api/channels/avito/auth")
async def avito_logout(
    operator: TokenData = Depends(get_current_operator)
):
    """Deactivate Avito channel."""
    pool = await get_pool()

    try:
        await pool.execute("""
            UPDATE elo_t_channel_accounts
            SET is_active = false, session_status = 'disconnected', updated_at = NOW()
            WHERE tenant_id = $1 AND channel_id = $2
        """, operator.tenant_id, CHANNEL_IDS["avito"])

        return {"success": True, "message": "Avito disconnected"}

    except Exception as e:
        logger.error(f"Avito logout error: {e}")
        return {"success": False, "error": str(e)}


# ========== WebSocket Endpoints (replaces FCM) ==========

@app.websocket("/ws/operator")
async def websocket_operator(
    websocket: WebSocket,
    operator_id: str = Query(...),
    tenant_id: str = Query(...),
    token: str = Query(None)
):
    """
    WebSocket endpoint for push notifications.
    Replaces FCM for real-time message delivery.

    Query params:
    - operator_id: Operator UUID
    - tenant_id: Tenant UUID
    - token: JWT token (optional, for auth)

    Messages sent to client:
    - {"action": "new_message", "dialog_id": "...", "title": "...", "body": "..."}
    - {"action": "new_dialog", "dialog_id": "...", "title": "...", "body": "..."}
    - {"action": "appeal_update", "dialog_id": "...", "title": "...", "body": "..."}
    - {"action": "draft_ready", "dialog_id": "...", "draft_text": "..."}
    - {"action": "ping"}
    """
    await ws_manager.connect(websocket, operator_id)

    try:
        # Start ping task
        ping_task = asyncio.create_task(send_pings(websocket, operator_id))

        while True:
            # Wait for messages from client (pong, etc.)
            try:
                data = await asyncio.wait_for(websocket.receive_text(), timeout=120)
                message = json.loads(data)
                action = message.get("action", "")

                if action == "pong":
                    logger.debug(f"Pong from operator {operator_id}")
                elif action == "send":
                    # Forward message to n8n for processing
                    # This can be used for operator messages that need normalization
                    logger.info(f"Message from operator {operator_id}: {message}")
                elif action == "approve":
                    # Approve and send message
                    logger.info(f"Approve from operator {operator_id}: {message}")

            except asyncio.TimeoutError:
                # No message received, send ping
                continue

    except WebSocketDisconnect:
        logger.info(f"WebSocket disconnected: {operator_id}")
    except Exception as e:
        logger.error(f"WebSocket error for {operator_id}: {e}")
    finally:
        ping_task.cancel()
        ws_manager.disconnect(operator_id)


async def send_pings(websocket: WebSocket, operator_id: str):
    """Send periodic pings to keep connection alive."""
    while True:
        try:
            await asyncio.sleep(30)
            await websocket.send_json({"action": "ping"})
        except:
            break


@app.post("/api/push/send")
async def send_push(
    request: Request,
    operator: TokenData = Depends(get_internal_or_operator)
):
    """
    Send push notification to operator via WebSocket.
    Called by n8n workflows instead of FCM.

    Body:
    - operator_id: target operator
    - action: new_message, new_dialog, appeal_update, draft_ready
    - dialog_id: related dialog
    - title, body: notification content
    - draft_text: for draft_ready action
    """
    body = await request.json()
    target_operator_id = body.get("operator_id")

    if not target_operator_id:
        return {"success": False, "error": "operator_id required"}

    message = {
        "action": body.get("action", "new_message"),
        "dialog_id": body.get("dialog_id"),
        "title": body.get("title", ""),
        "body": body.get("body", ""),
        "draft_text": body.get("draft_text"),
        "message": body.get("message"),  # Full message object for real-time update
    }

    success = await ws_manager.send_to_operator(target_operator_id, message)

    return {
        "success": success,
        "delivered": success,
        "operator_id": target_operator_id
    }


@app.get("/api/push/connections")
async def get_connections(operator: TokenData = Depends(get_current_operator)):
    """Get list of connected operators."""
    return {
        "success": True,
        "connected": ws_manager.get_connected_operators(),
        "count": len(ws_manager.active_connections)
    }


# ========== Run Server ==========

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host=settings.server_host,
        port=settings.server_port,
        reload=True,
        workers=1
    )
