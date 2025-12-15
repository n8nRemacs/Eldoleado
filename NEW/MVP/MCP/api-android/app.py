"""Android API Gateway Server.

FastAPI server providing HTTP endpoints for Android app.
Proxies requests to n8n webhooks with JWT authentication.
"""

import logging
from typing import Optional
from datetime import datetime
from contextlib import asynccontextmanager

from fastapi import FastAPI, Request, HTTPException, Depends, Query
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware

from config import settings
from n8n_client import N8NClient, N8NClientError
from auth import get_current_operator, create_token, TokenData
from models import (
    LoginRequest, LoginResponse,
    AppealsListRequest, TakeAppealRequest, RejectAppealRequest,
    SendMessageRequest, SendPromoRequest, NormalizeRequest,
    DeviceCreateRequest, DeviceUpdateRequest,
    RepairCreateRequest, RepairUpdateRequest,
    RegisterFCMRequest, UpdateSettingsRequest, UpdateAppealModeRequest,
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


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan - init and cleanup."""
    global n8n_client
    n8n_client = N8NClient()
    await n8n_client.connect()
    logger.info("API Gateway started")
    yield
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
