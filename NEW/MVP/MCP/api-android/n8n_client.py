"""HTTP client for n8n webhook calls."""

import logging
from typing import Optional, Any
import httpx

from config import settings

logger = logging.getLogger(__name__)


class N8NClientError(Exception):
    """Error from n8n webhook call."""
    def __init__(self, message: str, status_code: int = 500, response: Any = None):
        self.message = message
        self.status_code = status_code
        self.response = response
        super().__init__(self.message)


class N8NClient:
    """Async HTTP client for n8n webhooks."""

    def __init__(self, base_url: str = None, timeout: int = None):
        self.base_url = (base_url or settings.n8n_base_url).rstrip('/')
        self.timeout = timeout or settings.n8n_timeout
        self._client: Optional[httpx.AsyncClient] = None

    async def connect(self):
        """Initialize HTTP client."""
        self._client = httpx.AsyncClient(
            timeout=httpx.Timeout(self.timeout),
            follow_redirects=True
        )
        logger.info(f"N8N client initialized: {self.base_url}")

    async def close(self):
        """Close HTTP client."""
        if self._client:
            await self._client.aclose()
            logger.info("N8N client closed")

    async def call_webhook(
        self,
        path: str,
        method: str = "POST",
        data: dict = None,
        params: dict = None,
        headers: dict = None
    ) -> dict:
        """Call n8n webhook endpoint.

        Args:
            path: Webhook path (e.g., 'android/auth/login')
            method: HTTP method
            data: JSON body
            params: Query parameters
            headers: Additional headers

        Returns:
            Response JSON

        Raises:
            N8NClientError: If request fails
        """
        if not self._client:
            raise N8NClientError("Client not initialized", 500)

        url = f"{self.base_url}/{path.lstrip('/')}"

        try:
            logger.info(f"Calling n8n: {method} {url}")

            response = await self._client.request(
                method=method,
                url=url,
                json=data,
                params=params,
                headers=headers
            )

            # Log response
            logger.info(f"n8n response: {response.status_code}")

            # Try to parse JSON
            try:
                result = response.json()
            except Exception:
                result = {"raw": response.text}

            # Check for errors
            if response.status_code >= 400:
                raise N8NClientError(
                    message=result.get("error", f"HTTP {response.status_code}"),
                    status_code=response.status_code,
                    response=result
                )

            return result

        except httpx.TimeoutException:
            logger.error(f"Timeout calling n8n: {url}")
            raise N8NClientError("Request timeout", 504)

        except httpx.RequestError as e:
            logger.error(f"Request error calling n8n: {e}")
            raise N8NClientError(f"Connection error: {str(e)}", 502)

    # ========== Auth Endpoints ==========

    async def auth_login(self, phone: str, pin: str) -> dict:
        """Login operator."""
        return await self.call_webhook(
            "android/auth/login",
            data={"phone": phone, "pin": pin}
        )

    async def auth_logout(self, token: str) -> dict:
        """Logout operator."""
        return await self.call_webhook(
            "android/logout",
            data={"token": token}
        )

    # ========== Appeals Endpoints ==========

    async def get_appeals_list(
        self,
        operator_id: str,
        tenant_id: str,
        status: str = None,
        limit: int = 50,
        offset: int = 0
    ) -> dict:
        """Get list of appeals."""
        return await self.call_webhook(
            "api/operator/appeals/list",
            data={
                "operator_id": operator_id,
                "tenant_id": tenant_id,
                "status": status,
                "limit": limit,
                "offset": offset
            }
        )

    async def get_appeal_detail(self, appeal_id: str, operator_id: str) -> dict:
        """Get appeal details."""
        return await self.call_webhook(
            f"api/android/appeals/{appeal_id}",
            data={"operator_id": operator_id}
        )

    async def take_appeal(self, appeal_id: str, operator_id: str) -> dict:
        """Take appeal for processing."""
        return await self.call_webhook(
            f"api/android/appeals/{appeal_id}/take",
            data={"operator_id": operator_id}
        )

    async def reject_appeal(
        self,
        appeal_id: str,
        operator_id: str,
        reason: str = None
    ) -> dict:
        """Reject appeal."""
        return await self.call_webhook(
            f"api/android/appeals/{appeal_id}/reject",
            data={"operator_id": operator_id, "reason": reason}
        )

    async def send_message(
        self,
        appeal_id: str,
        operator_id: str,
        text: str,
        template_id: str = None
    ) -> dict:
        """Send message to client."""
        return await self.call_webhook(
            f"api/android/appeals/{appeal_id}/send",
            data={
                "operator_id": operator_id,
                "text": text,
                "template_id": template_id
            }
        )

    async def send_promo(
        self,
        appeal_id: str,
        operator_id: str,
        promo_text: str
    ) -> dict:
        """Send promo message."""
        return await self.call_webhook(
            f"api/android/appeals/{appeal_id}/promo",
            data={"operator_id": operator_id, "promo_text": promo_text}
        )

    async def normalize_appeal(self, appeal_id: str, operator_id: str) -> dict:
        """Normalize appeal data."""
        return await self.call_webhook(
            f"api/android/appeals/{appeal_id}/normalize",
            data={"operator_id": operator_id}
        )

    async def update_appeal_mode(self, appeal_id: str, mode: str) -> dict:
        """Update appeal mode (ai/manual)."""
        return await self.call_webhook(
            "android-update-appeal-mode",
            data={"appeal_id": appeal_id, "mode": mode}
        )

    # ========== Device Endpoints ==========

    async def create_device(self, data: dict) -> dict:
        """Create device for appeal."""
        return await self.call_webhook(
            "android/appeal-devices",
            data=data
        )

    async def update_device(self, device_id: str, data: dict) -> dict:
        """Update device."""
        return await self.call_webhook(
            f"android/appeal-devices/{device_id}",
            data={**data, "action": "update"}
        )

    async def delete_device(self, device_id: str) -> dict:
        """Delete device."""
        return await self.call_webhook(
            f"android/appeal-devices/{device_id}",
            data={"action": "delete"}
        )

    # ========== Repair Endpoints ==========

    async def create_repair(self, data: dict) -> dict:
        """Create repair for device."""
        return await self.call_webhook(
            "android/appeal-repairs",
            data=data
        )

    async def update_repair(self, repair_id: str, data: dict) -> dict:
        """Update repair."""
        return await self.call_webhook(
            f"android/appeal-repairs/{repair_id}",
            data={**data, "action": "update"}
        )

    async def delete_repair(self, repair_id: str) -> dict:
        """Delete repair."""
        return await self.call_webhook(
            f"android/appeal-repairs/{repair_id}",
            data={"action": "delete"}
        )

    # ========== Settings Endpoints ==========

    async def register_fcm(
        self,
        operator_id: str,
        fcm_token: str,
        device_info: dict = None
    ) -> dict:
        """Register FCM token for push notifications."""
        return await self.call_webhook(
            "android-register-fcm",
            data={
                "operator_id": operator_id,
                "fcm_token": fcm_token,
                "device_info": device_info
            }
        )

    async def update_settings(self, operator_id: str, settings: dict) -> dict:
        """Update operator settings."""
        return await self.call_webhook(
            "android-update-settings",
            data={"operator_id": operator_id, "settings": settings}
        )
