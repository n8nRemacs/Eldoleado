"""
Pipeline Orchestrator - Coordinates the full message processing pipeline

Flow:
- Incoming (client): transcribe audio → notify operator
- Outgoing text: normalize → send
- Outgoing audio: transcribe + normalize → approval → send
"""

import logging
from typing import Dict, Any, Optional

from app.models import PostAIResult, MessageDirection

from .pre_ai import PreAIPipeline
from .ai_core import AICore
from .post_ai import PostAIPipeline

logger = logging.getLogger("Orchestrator")


class PipelineOrchestrator:
    """
    Orchestrates the full message processing pipeline:

    INPUT → PRE-AI → [AI CORE] → POST-AI → OUTPUT

    AI Core handles:
    - Client audio: transcription only
    - Operator text: normalization
    - Operator audio: transcription + normalization

    NO auto-responses. All client messages go to operator.
    """

    def __init__(self, pg_pool=None, neo4j_driver=None):
        self.pre_ai = PreAIPipeline(pg_pool, neo4j_driver)
        self.ai_core = AICore()
        self.post_ai = PostAIPipeline(pg_pool)

        self.pg_pool = pg_pool
        self.neo4j_driver = neo4j_driver

    async def process_incoming(
        self,
        server_id: str,
        channel: str,
        data: Dict[str, Any]
    ) -> PostAIResult:
        """
        Process incoming message from client.

        1. Pre-AI: Normalize, build context
        2. AI Core: Transcribe audio (if audio message)
        3. Post-AI: Log message, notify operator
        """
        logger.info(f"Processing incoming: {channel} from {server_id}")

        # Step 1: Pre-AI Pipeline
        pre_ai_result = await self.pre_ai.process(
            server_id=server_id,
            channel=channel,
            data=data
        )

        # Step 2: AI Core (transcription for audio)
        ai_response = await self.ai_core.process(pre_ai_result)

        # Step 3: Post-AI Pipeline (log + notify operator)
        result = await self.post_ai.process(
            pre_ai_result=pre_ai_result,
            ai_response=ai_response
        )

        return result

    async def process_outgoing(
        self,
        tenant_id: str,
        channel: str,
        chat_id: str,
        text: str,
        is_voice: bool = False,
        operator_id: str = None,
        server_id: str = None
    ) -> PostAIResult:
        """
        Process outgoing message from operator.

        Text: normalize → send immediately
        Voice: normalize → return for approval → send after approval
        """
        logger.info(f"Processing outgoing: {channel} to {chat_id}, voice={is_voice}")

        # Pre-AI: Build context
        pre_ai_result = await self.pre_ai.process_outgoing(
            tenant_id=tenant_id,
            channel=channel,
            chat_id=chat_id,
            text=text,
            is_voice=is_voice,
            operator_id=operator_id
        )

        # AI Core: normalize text
        ai_response = await self.ai_core.process(pre_ai_result)

        if is_voice:
            # Voice: hold for approval
            result = await self.post_ai.process_operator_voice(
                pre_ai_result=pre_ai_result,
                ai_response=ai_response
            )
        else:
            # Text: send immediately
            result = await self.post_ai.process_operator_text(
                pre_ai_result=pre_ai_result,
                ai_response=ai_response,
                server_id=server_id
            )

        return result

    async def approve_and_send(
        self,
        message_id: str,
        final_text: str = None,
        server_id: str = None
    ) -> PostAIResult:
        """
        Approve normalized message and send to client.

        Called after operator confirms the text is correct.
        """
        logger.info(f"Approving message: {message_id}")

        return await self.post_ai.approve_and_send(
            message_id=message_id,
            final_text=final_text,
            server_id=server_id
        )

    async def process_webhook(
        self,
        source: str,
        data: Dict[str, Any]
    ) -> Optional[PostAIResult]:
        """
        Process webhook from external source.

        Routes based on source:
        - operator: Operator sending message
        - n8n: Workflow-initiated actions
        - External channels: Treat as incoming
        """
        logger.info(f"Processing webhook from {source}")

        if source == "operator":
            # Operator sending message
            return await self.process_outgoing(
                tenant_id=data.get("tenant_id"),
                channel=data.get("channel"),
                chat_id=data.get("chat_id"),
                text=data.get("text"),
                audio_url=data.get("audio_url"),
                audio_duration=data.get("audio_duration"),
                operator_id=data.get("operator_id")
            )

        elif source == "n8n":
            # n8n workflow action
            action = data.get("action")

            if action == "send_message":
                return await self.process_outgoing(
                    tenant_id=data.get("tenant_id"),
                    channel=data.get("channel"),
                    chat_id=data.get("chat_id"),
                    text=data.get("text")
                )

            elif action == "approve_audio":
                return await self.approve_audio_message(
                    message_id=data.get("message_id"),
                    approved_text=data.get("approved_text")
                )

            logger.warning(f"Unknown n8n action: {action}")
            return None

        else:
            # External channel webhook - treat as incoming
            return await self.process_incoming(
                server_id=f"webhook_{source}",
                channel=source,
                data=data
            )
