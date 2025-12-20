"""Avito monitor for API Canary."""

from typing import Any, Dict, List

from .base import BaseMonitor


class AvitoMonitor(BaseMonitor):
    """Monitor for Avito MCP server."""

    def __init__(
        self,
        base_url: str,
        api_key: str = "",
        user_hash: str = "",
        **kwargs
    ):
        super().__init__(
            channel_name="avito",
            base_url=base_url,
            api_key=api_key,
            **kwargs
        )
        self.user_hash = user_hash

    async def get_endpoints_to_check(self) -> List[Dict[str, Any]]:
        """Return list of endpoints to check."""
        endpoints = [
            {"endpoint": "/health/extended", "method": "GET"},
            {"endpoint": "/accounts", "method": "GET"},
        ]

        # Add user-specific endpoints if user_hash is configured
        if self.user_hash:
            endpoints.extend([
                {
                    "endpoint": "/api/chats",
                    "method": "GET",
                    "params": {"user_hash": self.user_hash, "limit": 1}
                },
                {
                    "endpoint": "/api/chats/unread/count",
                    "method": "GET",
                    "params": {"user_hash": self.user_hash}
                },
                {
                    "endpoint": "/api/token",
                    "method": "GET",
                    "params": {"user_hash": self.user_hash}
                },
            ])

        return endpoints

    async def run_api_checks(self):
        """Run all Avito API checks."""
        endpoints = await self.get_endpoints_to_check()

        for ep in endpoints:
            await self.check_endpoint(
                endpoint=ep["endpoint"],
                method=ep.get("method", "GET"),
                params=ep.get("params"),
            )

        self._update_status()
        return self.status
