"""
Post-AI Pipeline - Message delivery and notifications

Flow:
- Client messages: transcribe (if audio) → notify operator
- Operator text: send to client directly
- Operator audio: transcribe + normalize → show to operator for approval → send after approval
"""

import logging
from typing import Optional, List

from app.models import (
    NormalizedMessage,
    MessageContext,
    PreAIResult,
    AIResponse,
    PostAIResult,
    MessageDirection,
    MessageStatus
)
from app.config import settings

from .response_formatter import ResponseFormatter
from .action_executor import ActionExecutor
from .db_logger import DBLogger
from .notifier import Notifier

logger = logging.getLogger("PostAIPipeline")


class PostAIPipeline:
    """
    Post-AI Pipeline orchestrator.

    NO auto-responses. All client messages go to operator.

    Steps for incoming (client → operator):
    1. Update message with transcription (if audio)
    2. Log to database
    3. Notify operator

    Steps for outgoing text (operator → client):
    1. Send message
    2. Log to database

    Steps for outgoing audio (operator → client):
    1. Transcribe + normalize
    2. Hold for approval (status: pending_approval)
    3. After approval: send normalized text
    4. Log to database
    """

    def __init__(self, pg_pool=None):
        self.formatter = ResponseFormatter()
        self.executor = ActionExecutor(pg_pool)
        self.db_logger = DBLogger(pg_pool)
        self.notifier = Notifier(
            n8n_webhook_url=settings.n8n_webhook_url,
            fcm_enabled=True
        )

    async def process(
        self,
        pre_ai_result: PreAIResult,
        ai_response: AIResponse
    ) -> PostAIResult:
        """
        Process incoming client message.

        All messages go to operator. Audio messages include transcription.
        """
        message = pre_ai_result.message
        context = pre_ai_result.context

        logger.info(f"Processing incoming message for dialog {message.dialog_id}")

        # Update message with transcription if audio was processed
        if ai_response.audio_processed and ai_response.audio_result:
            message.transcription = ai_response.audio_result.transcription

        # Update status
        message.status = MessageStatus.RECEIVED.value

        # Log incoming message
        await self.db_logger.log_message(message, context)

        # Get assigned operator
        operator_ids = []
        if pre_ai_result.assigned_operator_id:
            operator_ids = [pre_ai_result.assigned_operator_id]

        # Notify operator about new message
        await self.notifier.notify_new_message(
            message=message,
            context=context,
            operator_ids=operator_ids
        )

        return PostAIResult(
            message=message,
            context=context,
            ai_response=ai_response,
            actions_executed=["notify_operator"],
            notify_operators=True,
            operator_ids=operator_ids
        )

    async def process_operator_text(
        self,
        pre_ai_result: PreAIResult,
        ai_response: AIResponse,
        server_id: str = None
    ) -> PostAIResult:
        """
        Process operator text message.

        Text is normalized by AI Core before sending.
        """
        message = pre_ai_result.message
        context = pre_ai_result.context

        logger.info(f"Sending operator text to client: {message.chat_id}")

        # Use normalized text if available
        text_to_send = message.text
        if ai_response.audio_result and ai_response.audio_result.normalized_text:
            text_to_send = ai_response.audio_result.normalized_text
            message.normalized_text = text_to_send

        # Send message
        result = await self.executor.send_message(
            server_id=server_id,
            channel=message.channel,
            chat_id=message.chat_id,
            text=text_to_send
        )

        # Update status
        message.status = MessageStatus.SENT.value if result.get("success") else MessageStatus.FAILED.value

        # Log message (with normalized text)
        await self.db_logger.log_message(message, context)

        return PostAIResult(
            message=message,
            context=context,
            ai_response=ai_response,
            response_text=text_to_send,
            actions_executed=["send_response"] if result.get("success") else []
        )

    async def process_operator_voice(
        self,
        pre_ai_result: PreAIResult,
        ai_response: AIResponse
    ) -> PostAIResult:
        """
        Process operator voice message (already transcribed by Android).

        1. Normalize text (done in AI Core)
        2. Return for approval (status: pending_approval)
        3. Operator will call approve_and_send() to send

        Returns message with pending_approval status.
        """
        message = pre_ai_result.message
        context = pre_ai_result.context

        logger.info(f"Processing operator voice for approval: {message.chat_id}")

        # Get normalized text from AI response
        if ai_response.audio_result and ai_response.audio_result.normalized_text:
            message.normalized_text = ai_response.audio_result.normalized_text

        # Set pending approval status
        message.status = MessageStatus.PENDING_APPROVAL.value

        # Log message (not sent yet)
        await self.db_logger.log_message(message, context)

        return PostAIResult(
            message=message,
            context=context,
            ai_response=ai_response,
            response_text=message.normalized_text,
            actions_executed=["pending_approval"]
        )

    async def approve_and_send(
        self,
        message_id: str,
        final_text: str = None,
        server_id: str = None
    ) -> PostAIResult:
        """
        Approve normalized text and send to client.

        Called after operator confirms the text is correct.

        Args:
            message_id: ID of pending message
            final_text: Optional edited text (if operator corrected it)
            server_id: Tunnel to send through
        """
        # TODO: Load message from database
        # message = await self.db_logger.get_message(message_id)

        logger.info(f"Approving and sending message: {message_id}")

        # For now, this is a placeholder that simulates the flow
        # In real implementation:
        # 1. Load message from DB
        # 2. Use final_text if provided, otherwise use normalized_text
        # 3. Send via executor
        # 4. Update status in DB

        # text_to_send = final_text or message.normalized_text
        # result = await self.executor.send_message(
        #     server_id=server_id,
        #     channel=message.channel,
        #     chat_id=message.chat_id,
        #     text=text_to_send
        # )
        # message.status = MessageStatus.SENT.value
        # await self.db_logger.update_message_status(message_id, MessageStatus.SENT.value)

        return PostAIResult(
            message=None,  # Will be loaded from DB
            context=None,
            actions_executed=["send_approved"]
        )
