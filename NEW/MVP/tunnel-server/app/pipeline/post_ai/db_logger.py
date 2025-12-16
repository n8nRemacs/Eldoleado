"""
DB Logger - Logs messages and events to database
"""

import logging
from datetime import datetime
from typing import Optional, Dict, Any

from app.models import NormalizedMessage, AIResponse, MessageContext

logger = logging.getLogger("DBLogger")


class DBLogger:
    """
    Logs messages, AI responses, and events to PostgreSQL.

    Tables:
    - elo_messages: All messages
    - elo_ai_suggestions: AI responses
    - elo_events: System events
    """

    def __init__(self, pg_pool=None):
        self.pg_pool = pg_pool

    async def log_message(
        self,
        message: NormalizedMessage,
        context: Optional[MessageContext] = None
    ) -> Optional[str]:
        """
        Log message to elo_messages.

        Returns message ID.
        """
        if not self.pg_pool:
            logger.debug(f"DB logging disabled, skipping message {message.id}")
            return message.id

        try:
            async with self.pg_pool.acquire() as conn:
                row = await conn.fetchrow(
                    """
                    INSERT INTO elo_messages (
                        id, dialog_id, tenant_id, channel,
                        direction, sender_type, sender_id, sender_name,
                        text, media_type, media_url,
                        external_id, raw_data, created_at
                    ) VALUES (
                        $1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14
                    )
                    RETURNING id
                    """,
                    message.id,
                    message.dialog_id,
                    message.tenant_id,
                    message.channel.value,
                    message.direction.value,
                    message.sender_type.value,
                    message.sender_id,
                    message.sender_name,
                    message.text,
                    message.media_type,
                    message.media_url,
                    message.external_id,
                    message.raw_data,
                    message.timestamp
                )
                return row["id"]

        except Exception as e:
            logger.error(f"Failed to log message: {e}")
            return None

    async def log_ai_response(
        self,
        message: NormalizedMessage,
        ai_response: AIResponse,
        sent: bool = False
    ) -> Optional[str]:
        """
        Log AI response to elo_ai_suggestions.

        Returns suggestion ID.
        """
        if not self.pg_pool:
            logger.debug(f"DB logging disabled, skipping AI response")
            return None

        try:
            async with self.pg_pool.acquire() as conn:
                row = await conn.fetchrow(
                    """
                    INSERT INTO elo_ai_suggestions (
                        dialog_id, message_id, tenant_id,
                        response_text, confidence, model,
                        suggested_actions, extracted_data,
                        tokens_used, processing_time_ms,
                        status, created_at
                    ) VALUES (
                        $1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12
                    )
                    RETURNING id
                    """,
                    message.dialog_id,
                    message.id,
                    message.tenant_id,
                    ai_response.text,
                    ai_response.confidence,
                    ai_response.model,
                    ai_response.suggested_actions,
                    ai_response.extracted_data,
                    ai_response.tokens_used,
                    ai_response.processing_time_ms,
                    "sent" if sent else "pending",
                    datetime.utcnow()
                )
                return row["id"]

        except Exception as e:
            logger.error(f"Failed to log AI response: {e}")
            return None

    async def log_event(
        self,
        event_type: str,
        dialog_id: Optional[str],
        tenant_id: str,
        data: Dict[str, Any] = None
    ):
        """
        Log system event to elo_events.

        Event types:
        - dialog_created
        - dialog_closed
        - operator_assigned
        - message_sent
        - ai_response_generated
        - escalation_requested
        """
        if not self.pg_pool:
            logger.debug(f"Event: {event_type} for dialog {dialog_id}")
            return

        try:
            async with self.pg_pool.acquire() as conn:
                await conn.execute(
                    """
                    INSERT INTO elo_events (
                        event_type, dialog_id, tenant_id, data, created_at
                    ) VALUES ($1, $2, $3, $4, $5)
                    """,
                    event_type,
                    dialog_id,
                    tenant_id,
                    data or {},
                    datetime.utcnow()
                )

        except Exception as e:
            logger.error(f"Failed to log event: {e}")

    async def update_dialog_status(
        self,
        dialog_id: str,
        status: str,
        stage: Optional[str] = None
    ):
        """Update dialog status in elo_dialogs"""
        if not self.pg_pool:
            return

        try:
            async with self.pg_pool.acquire() as conn:
                if stage:
                    await conn.execute(
                        """
                        UPDATE elo_dialogs
                        SET status = $2, stage = $3, updated_at = $4
                        WHERE id = $1
                        """,
                        dialog_id, status, stage, datetime.utcnow()
                    )
                else:
                    await conn.execute(
                        """
                        UPDATE elo_dialogs
                        SET status = $2, updated_at = $3
                        WHERE id = $1
                        """,
                        dialog_id, status, datetime.utcnow()
                    )

        except Exception as e:
            logger.error(f"Failed to update dialog: {e}")
