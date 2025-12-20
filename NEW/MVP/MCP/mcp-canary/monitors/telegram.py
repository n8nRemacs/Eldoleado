"""Telegram monitor for API Canary."""

from typing import Any, Dict, List

from .base import BaseMonitor


class TelegramMonitor(BaseMonitor):
    """Monitor for Telegram MCP server."""

    def __init__(
        self,
        base_url: str,
        api_key: str = "",
        bot_token: str = "",
        chat_id: str = "",
        **kwargs
    ):
        super().__init__(
            channel_name="telegram",
            base_url=base_url,
            api_key=api_key,
            **kwargs
        )
        self.bot_token = bot_token
        self.chat_id = chat_id

    async def get_endpoints_to_check(self) -> List[Dict[str, Any]]:
        """Return list of endpoints to check."""
        endpoints = [
            {"endpoint": "/health/extended", "method": "GET"},
            {"endpoint": "/bots", "method": "GET"},
        ]

        # Add bot-specific endpoints if bot_token is configured
        if self.bot_token:
            endpoints.extend([
                {
                    "endpoint": "/api/webhook",
                    "method": "GET",
                    "params": {"token": self.bot_token}
                },
            ])

            if self.chat_id:
                endpoints.append({
                    "endpoint": "/api/chat",
                    "method": "GET",
                    "params": {"token": self.bot_token, "chat_id": self.chat_id}
                })

        return endpoints

    async def run_api_checks(self):
        """Run all Telegram API checks."""
        endpoints = await self.get_endpoints_to_check()

        for ep in endpoints:
            await self.check_endpoint(
                endpoint=ep["endpoint"],
                method=ep.get("method", "GET"),
                params=ep.get("params"),
            )

        self._update_status()
        return self.status
