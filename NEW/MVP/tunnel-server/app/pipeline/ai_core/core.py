"""
AI Core - Audio Processing (Transcription + Normalization)

NO auto-replies. All messages go to operator.
AI only:
1. Transcribes audio messages (both client and operator)
2. Normalizes operator audio (beautifies text)
"""

import logging
import time
from typing import Optional

import httpx

from app.config import settings
from app.models import (
    NormalizedMessage,
    MessageContext,
    PreAIResult,
    AIResponse,
    AudioProcessingResult,
    MessageDirection
)

logger = logging.getLogger("AICore")


class AICore:
    """
    AI Core - Audio processing only.

    - Transcription: audio → text (Whisper or similar)
    - Normalization: raw text → grammatically correct text

    NO response generation. All messages go to operator.
    """

    def __init__(self):
        self.enabled = settings.ai_core.enabled
        self.transcription_url = settings.ai_core.transcription_url
        self.normalization_url = settings.ai_core.normalization_url
        self.timeout = settings.ai_core.timeout

    async def process(self, pre_ai_result: PreAIResult) -> AIResponse:
        """
        Process message through AI.

        Client messages:
        - Text: no processing
        - Audio: transcribe (Whisper API)

        Operator messages:
        - Text: normalize (already typed)
        - Voice: normalize (already transcribed by Android)
        """
        message = pre_ai_result.message
        start_time = time.time()

        is_outgoing = message.direction == MessageDirection.OUTGOING

        # Client text messages - no processing
        if not is_outgoing and not message.is_audio:
            return AIResponse(audio_processed=False)

        logger.info(f"Processing: direction={message.direction}, is_audio={message.is_audio}")

        try:
            transcription = None
            normalized_text = None

            if is_outgoing:
                # Operator message - normalize text
                # (Voice already transcribed by Android, text is ready)
                normalized_text = await self._normalize(message.text)

            elif message.is_audio:
                # Client audio - transcribe via Whisper API
                transcription = await self._transcribe(message)
                if not transcription:
                    logger.warning("Transcription failed or empty")
                    return AIResponse(audio_processed=False)

            processing_time = int((time.time() - start_time) * 1000)

            return AIResponse(
                audio_processed=message.is_audio and not is_outgoing,
                audio_result=AudioProcessingResult(
                    transcription=transcription,
                    normalized_text=normalized_text,
                    processing_time_ms=processing_time
                ) if (transcription or normalized_text) else None,
                processing_time_ms=processing_time
            )

        except Exception as e:
            logger.error(f"AI processing error: {e}")
            return AIResponse(audio_processed=False)

    async def _transcribe(self, message: NormalizedMessage) -> Optional[str]:
        """
        Transcribe audio to text.

        Uses Whisper API or similar service.
        """
        if not self.enabled or not self.transcription_url:
            logger.info("Transcription disabled, using stub")
            return await self._stub_transcribe(message)

        try:
            # Download audio file
            audio_data = await self._download_audio(message.media_url)
            if not audio_data:
                return None

            # Call transcription API
            async with httpx.AsyncClient() as client:
                response = await client.post(
                    self.transcription_url,
                    files={"audio": ("audio.ogg", audio_data, "audio/ogg")},
                    data={"language": "ru"},
                    timeout=self.timeout
                )
                response.raise_for_status()
                result = response.json()

            return result.get("text", "").strip()

        except Exception as e:
            logger.error(f"Transcription API error: {e}")
            return await self._stub_transcribe(message)

    async def _normalize(self, text: str) -> Optional[str]:
        """
        Normalize text - make it grammatically correct and clean.

        Used for operator audio messages.
        """
        if not text:
            return None

        if not self.enabled or not self.normalization_url:
            logger.info("Normalization disabled, using stub")
            return await self._stub_normalize(text)

        try:
            async with httpx.AsyncClient() as client:
                response = await client.post(
                    self.normalization_url,
                    json={
                        "text": text,
                        "mode": "beautify",
                        "language": "ru"
                    },
                    timeout=self.timeout
                )
                response.raise_for_status()
                result = response.json()

            return result.get("text", text).strip()

        except Exception as e:
            logger.error(f"Normalization API error: {e}")
            return await self._stub_normalize(text)

    async def _download_audio(self, url: str) -> Optional[bytes]:
        """Download audio file from URL."""
        if not url:
            return None

        try:
            async with httpx.AsyncClient() as client:
                response = await client.get(url, timeout=30)
                response.raise_for_status()
                return response.content
        except Exception as e:
            logger.error(f"Audio download error: {e}")
            return None

    # === Stubs for testing ===

    async def _stub_transcribe(self, message: NormalizedMessage) -> str:
        """
        Stub transcription for testing.
        Returns placeholder text.
        """
        duration = message.audio_duration or 5
        return f"[Аудио {duration} сек - транскрипция недоступна]"

    async def _stub_normalize(self, text: str) -> str:
        """
        Stub normalization for testing.
        Just cleans up obvious issues.
        """
        # Basic cleanup
        text = text.strip()
        text = " ".join(text.split())  # Remove extra spaces

        # Capitalize first letter
        if text and text[0].islower():
            text = text[0].upper() + text[1:]

        # Ensure ends with punctuation
        if text and text[-1] not in ".!?":
            text += "."

        return text
