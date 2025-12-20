"""MAX monitor for API Canary."""

from typing import Any, Dict, List

from .base import BaseMonitor


class MaxMonitor(BaseMonitor):
    """Monitor for MAX MCP server."""

    def __init__(
        self,
        base_url: str,
        api_key: str = "",
        access_token: str = "",
        **kwargs
    ):
        super().__init__(
            channel_name="max",
            base_url=base_url,
            api_key=api_key,
            **kwargs
        )
        self.access_token = access_token

    async def get_endpoints_to_check(self) -> List[Dict[str, Any]]:
        """Return list of endpoints to check."""
        endpoints = [
            {"endpoint": "/health/extended", "method": "GET"},
            {"endpoint": "/accounts", "method": "GET"},
        ]

        # Add token-specific endpoints if access_token is configured
        if self.access_token:
            endpoints.extend([
                {
                    "endpoint": "/api/chats",
                    "method": "GET",
                    "params": {"access_token": self.access_token, "count": 1}
                },
            ])

        return endpoints

    async def run_api_checks(self):
        """Run all MAX API checks."""
        endpoints = await self.get_endpoints_to_check()

        for ep in endpoints:
            await self.check_endpoint(
                endpoint=ep["endpoint"],
                method=ep.get("method", "GET"),
                params=ep.get("params"),
            )

        self._update_status()
        return self.status
