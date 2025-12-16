"""
Prompt Builder - Builds prompts for LLM from context
"""

import logging
from typing import Optional

from app.models import MessageContext, NormalizedMessage

logger = logging.getLogger("PromptBuilder")


class PromptBuilder:
    """
    Builds prompts for LLM based on message context.

    This is where prompt engineering happens.
    Different prompts for different scenarios:
    - Initial greeting
    - Information gathering
    - Problem diagnosis
    - Price quote
    - Closing
    """

    # System prompt template
    SYSTEM_PROMPT = """Ты — AI-ассистент сервисного центра "{tenant_name}".
Твоя задача — помочь клиенту с ремонтом техники.

Правила:
1. Будь вежливым и профессиональным
2. Задавай уточняющие вопросы по одному
3. Не давай цену без понимания проблемы
4. Если не уверен — передай человеку

Уровень свободы: {ai_freedom_level}/100
- 0-30: Только шаблонные ответы
- 30-70: Можешь адаптировать ответы
- 70-100: Полная свобода в формулировках
"""

    # Stage-specific prompts
    STAGE_PROMPTS = {
        "initial": "Клиент только начал диалог. Поприветствуй и узнай, чем можешь помочь.",
        "gathering": "Собираем информацию о проблеме. Задай уточняющий вопрос.",
        "diagnosis": "Достаточно информации для диагноза. Предложи решение или цену.",
        "pricing": "Клиент спрашивает о цене. Дай ориентировочную стоимость если возможно.",
        "closing": "Завершаем диалог. Уточни контакты и время визита."
    }

    def build_prompt(
        self,
        message: NormalizedMessage,
        context: MessageContext
    ) -> str:
        """
        Build full prompt for LLM.

        Returns formatted prompt string.
        """
        # System prompt
        system = self.SYSTEM_PROMPT.format(
            tenant_name=context.tenant_name or "Eldoleado",
            ai_freedom_level=context.ai_freedom_level
        )

        # Stage-specific instruction
        stage = context.dialog_stage or "initial"
        stage_instruction = self.STAGE_PROMPTS.get(stage, "")

        # Context about client
        client_context = self._build_client_context(context)

        # Message history
        history = self._build_history(context)

        # Current message
        current = f"Клиент: {message.text}"

        # Combine all
        prompt = f"""{system}

{stage_instruction}

{client_context}

История диалога:
{history}

{current}

Твой ответ:"""

        return prompt

    def _build_client_context(self, context: MessageContext) -> str:
        """Build context about client"""
        parts = []

        if context.client_name:
            parts.append(f"Имя клиента: {context.client_name}")

        if context.devices:
            devices_str = ", ".join(
                d.get("model", "устройство") for d in context.devices
            )
            parts.append(f"Устройства клиента: {devices_str}")

        if context.issues:
            issues_str = ", ".join(
                i.get("description", "проблема") for i in context.issues
            )
            parts.append(f"Известные проблемы: {issues_str}")

        if not parts:
            return "Информация о клиенте: новый клиент"

        return "Информация о клиенте:\n" + "\n".join(parts)

    def _build_history(self, context: MessageContext, max_messages: int = 10) -> str:
        """Build conversation history"""
        if not context.messages:
            return "(начало диалога)"

        # Take last N messages (excluding current)
        messages = context.messages[:-1][-max_messages:]

        if not messages:
            return "(начало диалога)"

        lines = []
        for msg in messages:
            sender = "Клиент" if msg.sender_type.value == "client" else "Ассистент"
            lines.append(f"{sender}: {msg.text}")

        return "\n".join(lines)

    def build_extraction_prompt(
        self,
        message: NormalizedMessage,
        context: MessageContext
    ) -> str:
        """
        Build prompt for data extraction (device, problem, etc.)
        """
        return f"""Извлеки структурированные данные из сообщения клиента.

Сообщение: {message.text}

Извлеки (если есть):
- device_brand: бренд устройства (Apple, Samsung, Xiaomi...)
- device_model: модель (iPhone 15, Galaxy S24...)
- problem: описание проблемы
- urgency: срочность (high/medium/low)

Ответь в формате JSON."""
