"""
Notifier - Sends notifications to operators
"""

import logging
from typing import Optional, List, Dict, Any

import httpx

from app.models import NormalizedMessage, MessageContext

logger = logging.getLogger("Notifier")


class Notifier:
    """
    Sends notifications to operators:
    - Push notifications (FCM)
    - Webhook to n8n
    """

    def __init__(self, n8n_webhook_url: str = None, fcm_enabled: bool = False):
        self.n8n_webhook_url = n8n_webhook_url
        self.fcm_enabled = fcm_enabled
        # Firebase admin will be initialized if needed
        self._firebase_app = None

    async def notify_new_message(
        self,
        message: NormalizedMessage,
        context: MessageContext,
        operator_ids: List[str] = None
    ):
        """
        Notify operators about new message.

        Sends to:
        - Assigned operator (if any)
        - Or all operators for tenant
        """
        logger.info(f"Notifying operators for dialog {message.dialog_id}")

        # Prepare notification data
        notification = {
            "type": "new_message",
            "dialog_id": message.dialog_id,
            "tenant_id": message.tenant_id,
            "channel": message.channel.value,
            "sender_name": message.sender_name,
            "text_preview": message.text[:100] if message.text else "",
            "timestamp": message.timestamp.isoformat()
        }

        # Send to n8n for processing
        if self.n8n_webhook_url:
            await self._send_to_n8n(notification)

        # Send push notifications
        if self.fcm_enabled and operator_ids:
            await self._send_push_notifications(notification, operator_ids)

    async def notify_ai_response(
        self,
        message: NormalizedMessage,
        ai_response_text: str,
        operator_ids: List[str] = None
    ):
        """Notify operators about AI response (for approval in assist mode)"""
        notification = {
            "type": "ai_response",
            "dialog_id": message.dialog_id,
            "tenant_id": message.tenant_id,
            "channel": message.channel.value,
            "ai_response": ai_response_text[:200],
            "requires_approval": True
        }

        if self.n8n_webhook_url:
            await self._send_to_n8n(notification)

        if self.fcm_enabled and operator_ids:
            await self._send_push_notifications(notification, operator_ids)

    async def notify_escalation(
        self,
        message: NormalizedMessage,
        reason: str = None
    ):
        """Notify about escalation request"""
        notification = {
            "type": "escalation",
            "dialog_id": message.dialog_id,
            "tenant_id": message.tenant_id,
            "channel": message.channel.value,
            "sender_name": message.sender_name,
            "reason": reason or "Client requested human operator"
        }

        if self.n8n_webhook_url:
            await self._send_to_n8n(notification)

    async def _send_to_n8n(self, data: Dict[str, Any]):
        """Send notification to n8n webhook"""
        if not self.n8n_webhook_url:
            return

        try:
            async with httpx.AsyncClient() as client:
                response = await client.post(
                    self.n8n_webhook_url,
                    json=data,
                    timeout=10
                )
                if response.status_code >= 400:
                    logger.error(f"n8n webhook error: {response.status_code}")
        except Exception as e:
            logger.error(f"n8n webhook error: {e}")

    async def _send_push_notifications(
        self,
        data: Dict[str, Any],
        operator_ids: List[str]
    ):
        """Send FCM push notifications"""
        if not self.fcm_enabled:
            return

        # TODO: Implement FCM sending
        # 1. Get FCM tokens for operator_ids from database
        # 2. Send multicast message using firebase-admin

        logger.debug(f"Would send push to operators: {operator_ids}")

    def _init_firebase(self):
        """Initialize Firebase Admin SDK"""
        if self._firebase_app:
            return

        try:
            import firebase_admin
            from firebase_admin import credentials

            cred = credentials.Certificate("firebase-credentials.json")
            self._firebase_app = firebase_admin.initialize_app(cred)
        except Exception as e:
            logger.error(f"Firebase init error: {e}")
            self.fcm_enabled = False
