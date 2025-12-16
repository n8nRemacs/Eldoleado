"""
Response Formatter - Formats AI response for specific channel
"""

import logging
from typing import Optional, Dict

from app.models import AIResponse, NormalizedMessage, ChannelType

logger = logging.getLogger("ResponseFormatter")


class ResponseFormatter:
    """
    Formats AI response for specific messenger channel.

    Handles:
    - Text length limits
    - Emoji support
    - Markdown conversion
    - Channel-specific formatting
    """

    # Character limits per channel
    CHAR_LIMITS = {
        ChannelType.WHATSAPP: 4096,
        ChannelType.TELEGRAM: 4096,
        ChannelType.VK: 4096,
        ChannelType.AVITO: 2000,
        ChannelType.MAX: 4096,
    }

    # Markdown support per channel
    MARKDOWN_SUPPORT = {
        ChannelType.WHATSAPP: True,
        ChannelType.TELEGRAM: True,
        ChannelType.VK: False,
        ChannelType.AVITO: False,
        ChannelType.MAX: True,
    }

    def format(
        self,
        ai_response: AIResponse,
        message: NormalizedMessage,
        add_signature: bool = False
    ) -> str:
        """
        Format AI response for channel.

        Args:
            ai_response: Response from AI Core
            message: Original message (for channel info)
            add_signature: Add "Powered by AI" signature

        Returns:
            Formatted text ready to send
        """
        text = ai_response.text
        channel = message.channel

        if not text:
            return ""

        # Convert markdown if not supported
        if not self.MARKDOWN_SUPPORT.get(channel, False):
            text = self._strip_markdown(text)

        # Apply channel-specific formatting
        text = self._apply_channel_format(text, channel)

        # Truncate if needed
        limit = self.CHAR_LIMITS.get(channel, 4096)
        if len(text) > limit:
            text = text[:limit - 3] + "..."

        # Add signature if requested
        if add_signature and ai_response.confidence > 0.7:
            # Only add for high-confidence responses
            pass  # Skip signature for now

        return text

    def _strip_markdown(self, text: str) -> str:
        """Remove markdown formatting"""
        import re

        # Bold **text** or __text__
        text = re.sub(r"\*\*(.*?)\*\*", r"\1", text)
        text = re.sub(r"__(.*?)__", r"\1", text)

        # Italic *text* or _text_
        text = re.sub(r"\*(.*?)\*", r"\1", text)
        text = re.sub(r"_(.*?)_", r"\1", text)

        # Code `text`
        text = re.sub(r"`(.*?)`", r"\1", text)

        # Links [text](url)
        text = re.sub(r"\[(.*?)\]\(.*?\)", r"\1", text)

        return text

    def _apply_channel_format(self, text: str, channel: ChannelType) -> str:
        """Apply channel-specific formatting"""
        if channel == ChannelType.WHATSAPP:
            # WhatsApp uses *bold*, _italic_
            pass

        elif channel == ChannelType.TELEGRAM:
            # Telegram uses **bold**, __italic__ or HTML
            pass

        elif channel == ChannelType.VK:
            # VK has limited formatting
            pass

        elif channel == ChannelType.AVITO:
            # Avito - plain text only
            # Keep it concise
            pass

        return text

    def format_error(self, error: str, channel: ChannelType) -> str:
        """Format error message for channel"""
        # Generic error message
        return "Извините, произошла ошибка. Попробуйте позже или дождитесь ответа оператора."

    def format_handoff(self, channel: ChannelType) -> str:
        """Format handoff to operator message"""
        return "Соединяю вас с оператором. Пожалуйста, подождите."
