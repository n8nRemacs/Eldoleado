"""Pydantic models for Android API."""

from typing import Optional, List, Any
from datetime import datetime
from pydantic import BaseModel, Field
from enum import Enum


# ========== Auth Models ==========

class LoginRequest(BaseModel):
    phone: str = Field(..., description="Operator phone number")
    pin: str = Field(..., description="PIN code")


class LoginResponse(BaseModel):
    success: bool
    token: Optional[str] = None
    operator: Optional[dict] = None
    error: Optional[str] = None


class LogoutRequest(BaseModel):
    token: str


# ========== Appeal Models ==========

class AppealStatus(str, Enum):
    NEW = "new"
    IN_PROGRESS = "in_progress"
    COMPLETED = "completed"
    REJECTED = "rejected"


class AppealsListRequest(BaseModel):
    operator_id: str
    tenant_id: str
    status: Optional[str] = None
    limit: int = Field(50, ge=1, le=200)
    offset: int = Field(0, ge=0)


class AppealDetailRequest(BaseModel):
    appeal_id: str
    operator_id: str


class TakeAppealRequest(BaseModel):
    operator_id: str


class RejectAppealRequest(BaseModel):
    operator_id: str
    reason: Optional[str] = None


class SendMessageRequest(BaseModel):
    operator_id: str
    text: str
    template_id: Optional[str] = None


class SendPromoRequest(BaseModel):
    operator_id: str
    promo_text: str


class NormalizeRequest(BaseModel):
    operator_id: str


# ========== Device Models ==========

class DeviceCreateRequest(BaseModel):
    appeal_id: str
    brand_id: Optional[str] = None
    model_id: Optional[str] = None
    brand_name: Optional[str] = None
    model_name: Optional[str] = None
    imei: Optional[str] = None
    serial_number: Optional[str] = None
    description: Optional[str] = None


class DeviceUpdateRequest(BaseModel):
    brand_id: Optional[str] = None
    model_id: Optional[str] = None
    brand_name: Optional[str] = None
    model_name: Optional[str] = None
    imei: Optional[str] = None
    serial_number: Optional[str] = None
    description: Optional[str] = None


# ========== Repair Models ==========

class RepairCreateRequest(BaseModel):
    appeal_device_id: str
    repair_category_id: Optional[str] = None
    category_name: Optional[str] = None
    price: Optional[float] = None
    description: Optional[str] = None


class RepairUpdateRequest(BaseModel):
    repair_category_id: Optional[str] = None
    category_name: Optional[str] = None
    price: Optional[float] = None
    status: Optional[str] = None
    description: Optional[str] = None


# ========== Settings Models ==========

class UpdateSettingsRequest(BaseModel):
    operator_id: str
    settings: dict


class RegisterFCMRequest(BaseModel):
    operator_id: str
    fcm_token: str
    device_info: Optional[dict] = None


class UpdateAppealModeRequest(BaseModel):
    appeal_id: str
    mode: str = Field(..., description="ai or manual")


# ========== Generic Response ==========

class APIResponse(BaseModel):
    success: bool
    data: Optional[Any] = None
    error: Optional[str] = None
    message: Optional[str] = None
