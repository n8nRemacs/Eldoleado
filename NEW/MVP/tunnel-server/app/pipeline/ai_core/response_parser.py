"""
Response Parser - Parses LLM responses and extracts structured data
"""

import json
import logging
import re
from typing import Optional, Dict, Any, List

logger = logging.getLogger("ResponseParser")


class ResponseParser:
    """
    Parses LLM responses.

    Handles:
    - Plain text responses
    - JSON responses (for extraction)
    - Action markers (e.g., [ESCALATE], [SEND_PRICE])
    """

    # Action markers that LLM can include
    ACTION_PATTERNS = {
        r"\[ESCALATE\]": "escalate",
        r"\[SEND_PRICE\]": "send_price",
        r"\[SCHEDULE\]": "schedule",
        r"\[END_DIALOG\]": "end_dialog",
        r"\[NEED_INFO\]": "need_info",
    }

    def parse_response(self, raw_response: str) -> Dict[str, Any]:
        """
        Parse LLM response.

        Returns dict with:
        - text: Clean response text
        - actions: List of detected actions
        - data: Extracted structured data (if any)
        """
        text = raw_response.strip()
        actions = []
        data = None

        # Check for action markers
        for pattern, action in self.ACTION_PATTERNS.items():
            if re.search(pattern, text, re.IGNORECASE):
                actions.append(action)
                # Remove marker from text
                text = re.sub(pattern, "", text, flags=re.IGNORECASE)

        # Try to extract JSON if present
        json_match = re.search(r"```json\s*(.*?)\s*```", text, re.DOTALL)
        if json_match:
            try:
                data = json.loads(json_match.group(1))
                # Remove JSON block from text
                text = re.sub(r"```json\s*.*?\s*```", "", text, flags=re.DOTALL)
            except json.JSONDecodeError:
                logger.warning("Failed to parse JSON in response")

        # Also try plain JSON
        if not data:
            try:
                # Check if entire response is JSON
                if text.strip().startswith("{"):
                    data = json.loads(text)
                    text = ""  # JSON response, no text
            except json.JSONDecodeError:
                pass

        # Clean up text
        text = text.strip()
        text = re.sub(r"\s+", " ", text)  # Normalize whitespace

        return {
            "text": text,
            "actions": actions,
            "data": data
        }

    def extract_entities(self, text: str) -> Dict[str, Any]:
        """
        Extract common entities from text using regex.

        Extracts:
        - phone numbers
        - prices
        - dates/times
        - device mentions
        """
        entities = {}

        # Phone numbers
        phone_pattern = r"[\+]?[78][\s\-]?\(?[0-9]{3}\)?[\s\-]?[0-9]{3}[\s\-]?[0-9]{2}[\s\-]?[0-9]{2}"
        phones = re.findall(phone_pattern, text)
        if phones:
            entities["phones"] = phones

        # Prices (Russian format)
        price_pattern = r"(\d[\d\s]*)\s*(?:руб|₽|рублей|р\.?)"
        prices = re.findall(price_pattern, text, re.IGNORECASE)
        if prices:
            entities["prices"] = [int(p.replace(" ", "")) for p in prices]

        # Common device brands
        brands = ["iphone", "samsung", "xiaomi", "huawei", "honor", "apple", "realme", "poco"]
        found_brands = [b for b in brands if b in text.lower()]
        if found_brands:
            entities["device_brands"] = found_brands

        return entities

    def format_for_channel(
        self,
        text: str,
        channel: str,
        max_length: int = None
    ) -> str:
        """
        Format response text for specific channel.

        Different channels have different limitations:
        - WhatsApp: 65536 chars
        - Telegram: 4096 chars
        - VK: 4096 chars
        - Avito: ~1000 chars recommended
        """
        limits = {
            "whatsapp": 4096,  # Keep shorter for readability
            "telegram": 4096,
            "vk": 4096,
            "avito": 1000,
            "max": 4096
        }

        limit = max_length or limits.get(channel, 4096)

        if len(text) <= limit:
            return text

        # Truncate with ellipsis
        return text[:limit - 3] + "..."
