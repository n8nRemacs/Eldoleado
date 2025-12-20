"""VK monitor for API Canary."""

from typing import Any, Dict, List

from .base import BaseMonitor


class VKMonitor(BaseMonitor):
    """Monitor for VK MCP server."""

    def __init__(
        self,
        base_url: str,
        api_key: str = "",
        group_hash: str = "",
        **kwargs
    ):
        super().__init__(
            channel_name="vk",
            base_url=base_url,
            api_key=api_key,
            **kwargs
        )
        self.group_hash = group_hash

    async def get_endpoints_to_check(self) -> List[Dict[str, Any]]:
        """Return list of endpoints to check."""
        endpoints = [
            {"endpoint": "/health/extended", "method": "GET"},
            {"endpoint": "/accounts", "method": "GET"},
        ]

        # Add group-specific endpoints if group_hash is configured
        if self.group_hash:
            endpoints.extend([
                {
                    "endpoint": "/api/group",
                    "method": "GET",
                    "params": {"group_hash": self.group_hash}
                },
                {
                    "endpoint": "/api/conversations",
                    "method": "GET",
                    "params": {"group_hash": self.group_hash, "count": 1}
                },
            ])

        return endpoints

    async def run_api_checks(self):
        """Run all VK API checks."""
        endpoints = await self.get_endpoints_to_check()

        for ep in endpoints:
            await self.check_endpoint(
                endpoint=ep["endpoint"],
                method=ep.get("method", "GET"),
                params=ep.get("params"),
            )

        self._update_status()
        return self.status
