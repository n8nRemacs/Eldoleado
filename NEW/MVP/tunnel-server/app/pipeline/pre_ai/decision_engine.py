"""
Decision Engine - Determines processing requirements for messages

NOTE: All messages go to operator. No auto-responses.
AI only handles: transcription (audio) and normalization (operator messages).
"""

import logging
import re
from typing import Optional, Tuple

from app.models import NormalizedMessage, MessageContext, MessageDirection

logger = logging.getLogger("DecisionEngine")


class DecisionEngine:
    """
    Decides message processing requirements.

    NO auto-responses. All client messages go to operator.
    AI only used for:
    - Client audio: transcription
    - Operator messages: normalization
    """

    # Keywords that indicate higher priority
    URGENCY_KEYWORDS = [
        r"срочно",
        r"urgent",
        r"asap",
        r"быстр",
        r"важно",
        r"помогите",
    ]

    # Keywords indicating client wants human
    ESCALATION_KEYWORDS = [
        r"человек",
        r"оператор",
        r"менеджер",
        r"живой",
        r"позовите",
        r"соедините",
    ]

    def __init__(self):
        self._urgency_pattern = re.compile(
            "|".join(self.URGENCY_KEYWORDS),
            re.IGNORECASE
        )
        self._escalation_pattern = re.compile(
            "|".join(self.ESCALATION_KEYWORDS),
            re.IGNORECASE
        )

    def should_use_ai(
        self,
        message: NormalizedMessage,
        context: MessageContext
    ) -> Tuple[bool, Optional[str]]:
        """
        Decide if AI should process this message.

        AI processes:
        - Audio messages (for transcription)
        - All messages are logged, none are auto-responded

        Returns:
            (needs_ai, reason)
        """
        # Incoming audio needs transcription
        if message.direction == MessageDirection.INCOMING and message.is_audio:
            return True, "Audio transcription"

        # Outgoing (operator) messages need normalization
        if message.direction == MessageDirection.OUTGOING:
            return True, "Operator message normalization"

        # Text messages from client don't need AI processing
        return False, "Client text message"

    def get_priority(
        self,
        message: NormalizedMessage,
        context: MessageContext
    ) -> int:
        """
        Calculate message priority (1-10, 10 highest).

        Higher priority for:
        - Urgency keywords
        - Escalation requests
        - Long response times
        """
        priority = 5  # Default

        text = message.text.lower()

        # Urgency keywords
        if self._urgency_pattern.search(text):
            priority += 2

        # Escalation request
        if self._escalation_pattern.search(text):
            priority += 2

        return min(priority, 10)

    def requests_human(self, text: str) -> bool:
        """Check if client is requesting human operator"""
        return bool(self._escalation_pattern.search(text))
