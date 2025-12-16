"""
Message Router - Routes messages to correct tenant/channel/operator
"""

import logging
from typing import Optional, Dict, Any

from app.models import NormalizedMessage, ChannelType

logger = logging.getLogger("Router")


class MessageRouter:
    """
    Routes incoming messages to correct tenant and determines
    which tunnel/channel account should handle them.
    """

    def __init__(self, pg_pool=None):
        self.pg_pool = pg_pool
        # Cache for channel account → tenant mapping
        self._channel_account_cache: Dict[str, str] = {}

    async def route_message(
        self,
        server_id: str,
        channel: str,
        chat_id: str,
        data: Dict[str, Any]
    ) -> Optional[str]:
        """
        Determine tenant_id for incoming message.

        Routing logic:
        1. Look up channel_account by server_id + channel
        2. Get tenant_id from channel_account

        Returns tenant_id or None if not found.
        """
        cache_key = f"{server_id}:{channel}"

        # Check cache
        if cache_key in self._channel_account_cache:
            return self._channel_account_cache[cache_key]

        # Load from database
        tenant_id = await self._lookup_tenant(server_id, channel)

        if tenant_id:
            self._channel_account_cache[cache_key] = tenant_id

        return tenant_id

    async def _lookup_tenant(self, server_id: str, channel: str) -> Optional[str]:
        """Look up tenant from elo_channel_accounts"""
        if not self.pg_pool:
            # Default tenant for development
            return "default_tenant"

        # TODO: Real query
        # SELECT tenant_id FROM elo_channel_accounts
        # WHERE server_id = $1 AND channel = $2

        return "default_tenant"

    async def get_tunnel_for_channel(
        self,
        tenant_id: str,
        channel: ChannelType,
        chat_id: str = None
    ) -> Optional[str]:
        """
        Get server_id (tunnel) that handles given tenant/channel.

        Used for outgoing messages - determines which phone
        should send the message.
        """
        if not self.pg_pool:
            return None  # Will use first available tunnel

        # TODO: Real query
        # SELECT server_id FROM elo_channel_accounts
        # WHERE tenant_id = $1 AND channel = $2

        return None

    async def assign_operator(
        self,
        tenant_id: str,
        channel: ChannelType,
        dialog_id: str
    ) -> Optional[str]:
        """
        Assign operator to dialog.

        Logic:
        1. Check if dialog already has operator
        2. If not, find available operator for tenant
        3. Use round-robin or load balancing
        """
        if not self.pg_pool:
            return None

        # TODO: Implement operator assignment
        # - Check dialog's current operator
        # - Get available operators for tenant
        # - Assign based on workload

        return None

    def clear_cache(self):
        """Clear routing cache"""
        self._channel_account_cache.clear()
