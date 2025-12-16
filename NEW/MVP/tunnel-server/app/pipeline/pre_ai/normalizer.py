"""
Message Normalizer - Converts channel-specific messages to unified format
"""

import logging
import uuid
from datetime import datetime
from typing import Dict, Any, Optional

from app.models import NormalizedMessage, ChannelType, MessageDirection, SenderType

logger = logging.getLogger("Normalizer")


class MessageNormalizer:
    """
    Normalizes messages from different channels to a unified format.
    Each channel has its own message structure - this converts them all
    to NormalizedMessage.
    """

    AUDIO_TYPES = {"voice", "audio", "ptt"}  # ptt = push-to-talk (WhatsApp)

    def _is_audio(self, data: Dict[str, Any]) -> bool:
        """Check if message is audio/voice"""
        media_type = data.get("media_type", "")
        return media_type in self.AUDIO_TYPES

    def normalize(
        self,
        channel: str,
        data: Dict[str, Any],
        tenant_id: str,
        direction: MessageDirection = MessageDirection.INCOMING
    ) -> NormalizedMessage:
        """
        Normalize message from any channel.

        Args:
            channel: Channel name (whatsapp, telegram, etc.)
            data: Raw message data from channel
            tenant_id: Tenant ID
            direction: Message direction (incoming/outgoing)

        Returns:
            NormalizedMessage
        """
        try:
            channel_type = ChannelType(channel)
        except ValueError:
            channel_type = ChannelType.HTTP_PROXY  # fallback

        # Route to channel-specific normalizer
        if channel_type == ChannelType.WHATSAPP:
            return self._normalize_whatsapp(data, tenant_id, direction)
        elif channel_type == ChannelType.TELEGRAM:
            return self._normalize_telegram(data, tenant_id, direction)
        elif channel_type == ChannelType.AVITO:
            return self._normalize_avito(data, tenant_id, direction)
        elif channel_type == ChannelType.MAX:
            return self._normalize_max(data, tenant_id, direction)
        elif channel_type == ChannelType.VK:
            return self._normalize_vk(data, tenant_id, direction)
        else:
            return self._normalize_generic(data, tenant_id, channel_type, direction)

    def _normalize_whatsapp(
        self,
        data: Dict[str, Any],
        tenant_id: str,
        direction: MessageDirection
    ) -> NormalizedMessage:
        """Normalize WhatsApp (Baileys) message"""
        is_audio = self._is_audio(data)
        return NormalizedMessage(
            id=str(uuid.uuid4()),
            external_id=data.get("id"),
            tenant_id=tenant_id,
            channel=ChannelType.WHATSAPP,
            chat_id=data.get("chat_id", ""),
            direction=direction,
            sender_type=SenderType.CLIENT if direction == MessageDirection.INCOMING else SenderType.OPERATOR,
            sender_id=data.get("from_id"),
            sender_name=data.get("from_name", ""),
            text=data.get("text", ""),
            media_type=data.get("media_type"),
            media_id=data.get("media_id"),
            media_url=data.get("media_url"),
            is_audio=is_audio,
            audio_duration=data.get("duration") if is_audio else None,
            timestamp=datetime.utcnow(),
            raw_data=data
        )

    def _normalize_telegram(
        self,
        data: Dict[str, Any],
        tenant_id: str,
        direction: MessageDirection
    ) -> NormalizedMessage:
        """Normalize Telegram (Pyrogram) message"""
        from_user = data.get("from_user", {}) or {}
        is_audio = self._is_audio(data)

        return NormalizedMessage(
            id=str(uuid.uuid4()),
            external_id=str(data.get("id", "")),
            tenant_id=tenant_id,
            channel=ChannelType.TELEGRAM,
            chat_id=str(data.get("chat_id", "")),
            direction=direction,
            sender_type=SenderType.CLIENT if direction == MessageDirection.INCOMING else SenderType.OPERATOR,
            sender_id=str(from_user.get("id", "")),
            sender_name=from_user.get("first_name", ""),
            text=data.get("text", ""),
            media_type=data.get("media_type"),
            media_id=data.get("media_id"),
            media_url=data.get("media_url"),
            is_audio=is_audio,
            audio_duration=data.get("duration") if is_audio else None,
            timestamp=datetime.fromisoformat(data["date"]) if data.get("date") else datetime.utcnow(),
            raw_data=data
        )

    def _normalize_avito(
        self,
        data: Dict[str, Any],
        tenant_id: str,
        direction: MessageDirection
    ) -> NormalizedMessage:
        """Normalize Avito message"""
        is_audio = self._is_audio(data)
        return NormalizedMessage(
            id=str(uuid.uuid4()),
            external_id=data.get("id"),
            tenant_id=tenant_id,
            channel=ChannelType.AVITO,
            chat_id=data.get("chat_id", ""),
            direction=direction,
            sender_type=SenderType.CLIENT if direction == MessageDirection.INCOMING else SenderType.OPERATOR,
            sender_id=data.get("sender_id"),
            sender_name=data.get("sender_name", ""),
            text=data.get("text", "") or data.get("message", ""),
            media_type=data.get("media_type"),
            media_url=data.get("media_url"),
            is_audio=is_audio,
            audio_duration=data.get("duration") if is_audio else None,
            timestamp=datetime.utcnow(),
            raw_data=data
        )

    def _normalize_max(
        self,
        data: Dict[str, Any],
        tenant_id: str,
        direction: MessageDirection
    ) -> NormalizedMessage:
        """Normalize MAX (VK Teams) message"""
        is_audio = self._is_audio(data)
        return NormalizedMessage(
            id=str(uuid.uuid4()),
            external_id=data.get("id"),
            tenant_id=tenant_id,
            channel=ChannelType.MAX,
            chat_id=data.get("chat_id", ""),
            direction=direction,
            sender_type=SenderType.CLIENT if direction == MessageDirection.INCOMING else SenderType.OPERATOR,
            sender_id=data.get("sender_id"),
            sender_name=data.get("sender_name", ""),
            text=data.get("text", ""),
            media_type=data.get("media_type"),
            media_url=data.get("media_url"),
            is_audio=is_audio,
            audio_duration=data.get("duration") if is_audio else None,
            timestamp=datetime.utcnow(),
            raw_data=data
        )

    def _normalize_vk(
        self,
        data: Dict[str, Any],
        tenant_id: str,
        direction: MessageDirection
    ) -> NormalizedMessage:
        """Normalize VK message"""
        is_audio = self._is_audio(data)
        return NormalizedMessage(
            id=str(uuid.uuid4()),
            external_id=str(data.get("id", "")),
            tenant_id=tenant_id,
            channel=ChannelType.VK,
            chat_id=str(data.get("peer_id", "")),
            direction=direction,
            sender_type=SenderType.CLIENT if direction == MessageDirection.INCOMING else SenderType.OPERATOR,
            sender_id=str(data.get("from_id", "")),
            sender_name=data.get("from_name", ""),
            text=data.get("text", "") or data.get("message", ""),
            media_type=data.get("media_type"),
            media_url=data.get("media_url"),
            is_audio=is_audio,
            audio_duration=data.get("duration") if is_audio else None,
            timestamp=datetime.utcnow(),
            raw_data=data
        )

    def _normalize_generic(
        self,
        data: Dict[str, Any],
        tenant_id: str,
        channel: ChannelType,
        direction: MessageDirection
    ) -> NormalizedMessage:
        """Generic normalizer for unknown channels"""
        is_audio = self._is_audio(data)
        return NormalizedMessage(
            id=str(uuid.uuid4()),
            external_id=data.get("id"),
            tenant_id=tenant_id,
            channel=channel,
            chat_id=str(data.get("chat_id", "") or data.get("peer_id", "")),
            direction=direction,
            sender_type=SenderType.CLIENT if direction == MessageDirection.INCOMING else SenderType.OPERATOR,
            sender_id=data.get("sender_id") or data.get("from_id"),
            sender_name=data.get("sender_name", "") or data.get("from_name", ""),
            text=data.get("text", "") or data.get("message", ""),
            media_type=data.get("media_type"),
            media_url=data.get("media_url"),
            is_audio=is_audio,
            audio_duration=data.get("duration") if is_audio else None,
            timestamp=datetime.utcnow(),
            raw_data=data
        )
