"""WhatsApp monitor for API Canary."""

from typing import Any, Dict, List

from .base import BaseMonitor


class WhatsAppMonitor(BaseMonitor):
    """Monitor for WhatsApp Baileys MCP server."""

    def __init__(self, base_url: str, api_key: str = "", session_id: str = "", **kwargs):
        super().__init__(
            channel_name="whatsapp",
            base_url=base_url,
            api_key=api_key,
            **kwargs
        )
        self.session_id = session_id

    async def get_endpoints_to_check(self) -> List[Dict[str, Any]]:
        """Return list of endpoints to check."""
        endpoints = [
            {"endpoint": "/health/extended", "method": "GET"},
            {"endpoint": "/sessions", "method": "GET"},
        ]

        # Add session-specific endpoints if session_id is configured
        if self.session_id:
            endpoints.extend([
                {"endpoint": f"/sessions/{self.session_id}/status", "method": "GET"},
            ])

        return endpoints

    async def run_api_checks(self):
        """Run all WhatsApp API checks."""
        endpoints = await self.get_endpoints_to_check()

        for ep in endpoints:
            await self.check_endpoint(
                endpoint=ep["endpoint"],
                method=ep.get("method", "GET"),
                params=ep.get("params"),
            )

        self._update_status()
        return self.status
