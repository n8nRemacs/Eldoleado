"""
Pre-AI Pipeline - Orchestrates pre-AI processing steps
"""

import logging
from typing import Dict, Any, Optional

from app.models import NormalizedMessage, MessageContext, PreAIResult, MessageDirection

from .normalizer import MessageNormalizer
from .context_builder import ContextBuilder
from .router import MessageRouter
from .decision_engine import DecisionEngine

logger = logging.getLogger("PreAIPipeline")


class PreAIPipeline:
    """
    Pre-AI Pipeline orchestrator.

    Steps:
    1. Route - determine tenant
    2. Normalize - convert to unified format
    3. Build context - load from databases
    4. Decide - should AI process this?
    """

    def __init__(self, pg_pool=None, neo4j_driver=None):
        self.normalizer = MessageNormalizer()
        self.router = MessageRouter(pg_pool)
        self.context_builder = ContextBuilder(pg_pool, neo4j_driver)
        self.decision_engine = DecisionEngine()

    async def process(
        self,
        server_id: str,
        channel: str,
        data: Dict[str, Any]
    ) -> PreAIResult:
        """
        Process incoming message through pre-AI pipeline.

        Args:
            server_id: Tunnel server ID
            channel: Channel name (whatsapp, telegram, etc.)
            data: Raw message data from channel

        Returns:
            PreAIResult with normalized message, context, and decision
        """
        logger.info(f"Pre-AI processing: {channel} from {server_id}")

        # Step 1: Route - determine tenant
        chat_id = data.get("chat_id") or data.get("peer_id") or ""
        tenant_id = await self.router.route_message(server_id, channel, chat_id, data)

        if not tenant_id:
            logger.warning(f"Could not determine tenant for {server_id}/{channel}")
            tenant_id = "default_tenant"

        # Step 2: Normalize message
        message = self.normalizer.normalize(
            channel=channel,
            data=data,
            tenant_id=tenant_id,
            direction=MessageDirection.INCOMING
        )

        logger.debug(f"Normalized message: {message.id} from {message.sender_name}")

        # Step 3: Build context
        context = await self.context_builder.build_context(
            message=message,
            max_messages=50
        )

        # Update message with dialog_id from context
        message.dialog_id = context.dialog_id

        # Step 4: Decision - should use AI?
        needs_ai, skip_reason = self.decision_engine.should_use_ai(message, context)

        # Step 5: Assign operator if needed
        assigned_operator = None
        if not needs_ai or context.operation_mode == "assist":
            assigned_operator = await self.router.assign_operator(
                tenant_id=tenant_id,
                channel=message.channel,
                dialog_id=context.dialog_id
            )

        result = PreAIResult(
            message=message,
            context=context,
            needs_ai=needs_ai,
            skip_reason=skip_reason,
            assigned_operator_id=assigned_operator
        )

        logger.info(
            f"Pre-AI complete: needs_ai={needs_ai}, "
            f"reason={skip_reason}, operator={assigned_operator}"
        )

        return result

    async def process_outgoing(
        self,
        tenant_id: str,
        channel: str,
        chat_id: str,
        text: str,
        is_voice: bool = False,
        operator_id: Optional[str] = None
    ) -> PreAIResult:
        """
        Process outgoing message (from operator).

        All operator messages go through AI for normalization.
        Voice messages need approval before sending.
        """
        data = {
            "chat_id": chat_id,
            "text": text,
            "sender_type": "operator"
        }

        message = self.normalizer.normalize(
            channel=channel,
            data=data,
            tenant_id=tenant_id,
            direction=MessageDirection.OUTGOING
        )

        # Mark if it was voice input (already transcribed by Android)
        message.is_audio = is_voice

        context = await self.context_builder.build_context(
            message=message,
            max_messages=10  # Less context needed for outgoing
        )

        return PreAIResult(
            message=message,
            context=context,
            needs_ai=True,  # Operator messages need AI for normalization
            skip_reason=None,
            assigned_operator_id=operator_id
        )
