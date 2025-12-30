"""JWT Authentication for Android API."""

import logging
from datetime import datetime, timedelta
from typing import Optional

from fastapi import HTTPException, Header, Depends
from jose import jwt, JWTError

from config import settings

logger = logging.getLogger(__name__)


def create_token(operator_id: str, tenant_id: str, extra_data: dict = None) -> str:
    """Create JWT token for operator.

    Args:
        operator_id: Operator UUID
        tenant_id: Tenant UUID
        extra_data: Additional claims

    Returns:
        JWT token string
    """
    expire = datetime.utcnow() + timedelta(hours=settings.jwt_expire_hours)

    payload = {
        "sub": operator_id,
        "tenant_id": tenant_id,
        "exp": expire,
        "iat": datetime.utcnow()
    }

    if extra_data:
        payload.update(extra_data)

    token = jwt.encode(
        payload,
        settings.jwt_secret,
        algorithm=settings.jwt_algorithm
    )

    return token


def decode_token(token: str) -> dict:
    """Decode and validate JWT token.

    Args:
        token: JWT token string

    Returns:
        Token payload

    Raises:
        HTTPException: If token is invalid or expired
    """
    try:
        payload = jwt.decode(
            token,
            settings.jwt_secret,
            algorithms=[settings.jwt_algorithm]
        )
        return payload

    except JWTError as e:
        logger.warning(f"Invalid token: {e}")
        raise HTTPException(
            status_code=401,
            detail="Invalid or expired token"
        )


class TokenData:
    """Parsed token data."""

    def __init__(self, payload: dict):
        self.operator_id = payload.get("sub")
        self.tenant_id = payload.get("tenant_id")
        self.exp = payload.get("exp")
        self.raw = payload


async def get_current_operator(
    authorization: Optional[str] = Header(None)
) -> TokenData:
    """Dependency to get current operator from JWT token.

    Usage:
        @app.get("/protected")
        async def protected_route(operator: TokenData = Depends(get_current_operator)):
            return {"operator_id": operator.operator_id}
    """
    if not authorization:
        raise HTTPException(
            status_code=401,
            detail="Authorization header required"
        )

    # Support "Bearer <token>" format
    if authorization.startswith("Bearer "):
        token = authorization[7:]
    else:
        token = authorization

    payload = decode_token(token)
    return TokenData(payload)


async def get_optional_operator(
    authorization: Optional[str] = Header(None)
) -> Optional[TokenData]:
    """Dependency to get optional operator (for public endpoints)."""
    if not authorization:
        return None

    try:
        return await get_current_operator(authorization)
    except HTTPException:
        return None


async def get_internal_or_operator(
    authorization: Optional[str] = Header(None),
    x_internal_key: Optional[str] = Header(None, alias="X-Internal-Key")
) -> Optional[TokenData]:
    """Dependency for internal n8n calls or operator auth.

    Accepts either:
    - X-Internal-Key header with internal secret
    - Authorization header with JWT token
    """
    # Check internal key first (for n8n workflows)
    if x_internal_key == settings.internal_api_key:
        # Return a system token for internal calls
        return TokenData({
            "sub": "system",
            "tenant_id": "system",
            "internal": True
        })

    # Fall back to JWT auth
    if authorization:
        return await get_current_operator(authorization)

    raise HTTPException(
        status_code=401,
        detail="Authorization or X-Internal-Key header required"
    )


def require_tenant(tenant_id: str):
    """Dependency factory to require specific tenant.

    Usage:
        @app.get("/tenant/{tenant_id}/data")
        async def tenant_data(
            tenant_id: str,
            operator: TokenData = Depends(require_tenant(tenant_id))
        ):
            ...
    """
    async def check_tenant(
        operator: TokenData = Depends(get_current_operator)
    ) -> TokenData:
        if operator.tenant_id != tenant_id:
            raise HTTPException(
                status_code=403,
                detail="Access denied for this tenant"
            )
        return operator

    return check_tenant
