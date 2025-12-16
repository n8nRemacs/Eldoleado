"""
Action Executor - Executes actions (send message, etc.)
"""

import logging
from typing import Optional, Dict, Any, List

from app.models import NormalizedMessage, AIResponse, ChannelType
from app.input.websocket_manager import manager

logger = logging.getLogger("ActionExecutor")


class ActionExecutor:
    """
    Executes actions:
    - Send message through tunnel
    - Create/update records in database
    - Trigger workflows
    """

    def __init__(self, pg_pool=None):
        self.pg_pool = pg_pool

    async def send_message(
        self,
        server_id: str,
        channel: ChannelType,
        chat_id: str,
        text: str,
        media_type: Optional[str] = None,
        media_url: Optional[str] = None
    ) -> Dict[str, Any]:
        """
        Send message through tunnel to channel.

        Returns result with message_id or error.
        """
        if not server_id:
            # Find tunnel that handles this channel
            server_id = await self._find_tunnel_for_channel(channel)

        if not server_id:
            logger.error(f"No tunnel available for {channel}")
            return {"success": False, "error": "No tunnel available"}

        try:
            # Determine endpoint based on media type
            if media_type and media_url:
                path = f"/send/{media_type}"
                body = {
                    "chat_id": chat_id,
                    "url": media_url,
                    "caption": text if text else None
                }
            else:
                path = "/send"
                body = {
                    "chat_id": chat_id,
                    "text": text
                }

            response = await manager.send_to_tunnel(
                server_id=server_id,
                service=channel.value,
                method="POST",
                path=path,
                body=body
            )

            if response.error:
                return {"success": False, "error": response.error}

            return {
                "success": True,
                "message_id": response.body.get("message_id") if response.body else None
            }

        except TimeoutError:
            logger.error(f"Timeout sending to {channel}")
            return {"success": False, "error": "Timeout"}
        except Exception as e:
            logger.error(f"Send error: {e}")
            return {"success": False, "error": str(e)}

    async def _find_tunnel_for_channel(self, channel: ChannelType) -> Optional[str]:
        """Find a tunnel that can handle this channel"""
        # Look for any connected tunnel with this channel service
        for server_id, conn in manager.connections.items():
            if channel.value in conn.services:
                return server_id

        # Fallback: return first available
        if manager.connections:
            return next(iter(manager.connections.keys()))

        return None

    async def execute_actions(
        self,
        actions: List[Dict[str, Any]],
        message: NormalizedMessage,
        ai_response: AIResponse
    ) -> List[str]:
        """
        Execute list of actions suggested by AI.

        Returns list of executed action names.
        """
        executed = []

        for action_dict in actions:
            action = action_dict.get("action", "")

            try:
                if action == "escalate":
                    await self._action_escalate(message)
                    executed.append("escalate")

                elif action == "send_price":
                    await self._action_send_price(message)
                    executed.append("send_price")

                elif action == "schedule":
                    await self._action_schedule(message)
                    executed.append("schedule")

                elif action == "end_dialog":
                    await self._action_end_dialog(message)
                    executed.append("end_dialog")

                elif action == "notify_operator":
                    await self._action_notify_operator(message)
                    executed.append("notify_operator")

            except Exception as e:
                logger.error(f"Action {action} failed: {e}")

        return executed

    async def _action_escalate(self, message: NormalizedMessage):
        """Escalate to human operator"""
        logger.info(f"Escalating dialog {message.dialog_id}")
        # TODO: Update dialog status, notify operator

    async def _action_send_price(self, message: NormalizedMessage):
        """Send price quote"""
        logger.info(f"Sending price for dialog {message.dialog_id}")
        # TODO: Generate and send price quote

    async def _action_schedule(self, message: NormalizedMessage):
        """Schedule appointment"""
        logger.info(f"Scheduling for dialog {message.dialog_id}")
        # TODO: Create appointment

    async def _action_end_dialog(self, message: NormalizedMessage):
        """End/close dialog"""
        logger.info(f"Ending dialog {message.dialog_id}")
        # TODO: Update dialog status to closed

    async def _action_notify_operator(self, message: NormalizedMessage):
        """Notify operator about message"""
        logger.info(f"Notifying operator for dialog {message.dialog_id}")
        # TODO: Send push notification
