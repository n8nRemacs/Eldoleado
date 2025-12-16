"""
Context Builder - Loads context from databases for AI processing
"""

import logging
from typing import Optional, List, Dict, Any

from app.models import NormalizedMessage, MessageContext

logger = logging.getLogger("ContextBuilder")


class ContextBuilder:
    """
    Builds context for message processing.
    Loads data from PostgreSQL and Neo4j.
    """

    def __init__(self, pg_pool=None, neo4j_driver=None):
        self.pg_pool = pg_pool
        self.neo4j_driver = neo4j_driver

    async def build_context(
        self,
        message: NormalizedMessage,
        max_messages: int = 50
    ) -> MessageContext:
        """
        Build full context for message.

        Loads:
        - Tenant settings
        - Client info
        - Dialog history
        - Graph data (devices, issues)
        """
        context = MessageContext(
            tenant_id=message.tenant_id,
            messages=[message]
        )

        try:
            # Load tenant
            tenant = await self._load_tenant(message.tenant_id)
            if tenant:
                context.tenant_name = tenant.get("name")
                context.tenant_settings = tenant.get("settings", {})
                context.ai_freedom_level = tenant.get("ai_freedom_level", 50)

            # Load/create dialog
            dialog = await self._load_or_create_dialog(message)
            if dialog:
                context.dialog_id = dialog.get("id")
                context.dialog_status = dialog.get("status")
                context.dialog_stage = dialog.get("stage")
                context.operation_mode = dialog.get("operation_mode", "assist")

            # Load client
            client = await self._load_client(message)
            if client:
                context.client_id = client.get("id")
                context.client_name = client.get("name")
                context.client_phone = client.get("phone")

            # Load message history
            if context.dialog_id:
                history = await self._load_message_history(
                    context.dialog_id,
                    limit=max_messages
                )
                context.messages = history + [message]

            # Load graph data from Neo4j
            if context.client_id:
                graph_data = await self._load_graph_data(context.client_id)
                context.devices = graph_data.get("devices", [])
                context.issues = graph_data.get("issues", [])

        except Exception as e:
            logger.error(f"Error building context: {e}")

        return context

    async def _load_tenant(self, tenant_id: str) -> Optional[Dict]:
        """Load tenant from PostgreSQL"""
        if not self.pg_pool:
            # Return stub data
            return {
                "id": tenant_id,
                "name": "Default Tenant",
                "ai_freedom_level": 50,
                "settings": {}
            }

        # TODO: Real query
        # async with self.pg_pool.acquire() as conn:
        #     row = await conn.fetchrow(
        #         "SELECT * FROM elo_tenants WHERE id = $1",
        #         tenant_id
        #     )
        #     return dict(row) if row else None

        return {
            "id": tenant_id,
            "name": "Default Tenant",
            "ai_freedom_level": 50,
            "settings": {}
        }

    async def _load_or_create_dialog(self, message: NormalizedMessage) -> Optional[Dict]:
        """Load existing dialog or create new one"""
        if not self.pg_pool:
            # Return stub
            return {
                "id": f"dialog_{message.chat_id}",
                "status": "active",
                "stage": "initial",
                "operation_mode": "assist"
            }

        # TODO: Real query to find or create dialog
        # Look up by tenant_id + channel + chat_id

        return {
            "id": f"dialog_{message.chat_id}",
            "status": "active",
            "stage": "initial",
            "operation_mode": "assist"
        }

    async def _load_client(self, message: NormalizedMessage) -> Optional[Dict]:
        """Load client info"""
        if not self.pg_pool:
            return {
                "id": f"client_{message.sender_id or message.chat_id}",
                "name": message.sender_name or "Unknown",
                "phone": None
            }

        # TODO: Real query

        return {
            "id": f"client_{message.sender_id or message.chat_id}",
            "name": message.sender_name or "Unknown",
            "phone": None
        }

    async def _load_message_history(
        self,
        dialog_id: str,
        limit: int = 50
    ) -> List[NormalizedMessage]:
        """Load recent messages from dialog"""
        if not self.pg_pool:
            return []

        # TODO: Real query to elo_messages
        return []

    async def _load_graph_data(self, client_id: str) -> Dict[str, List]:
        """Load graph data from Neo4j"""
        if not self.neo4j_driver:
            return {"devices": [], "issues": []}

        # TODO: Real Neo4j query
        # MATCH (c:Client {id: $client_id})-[:HAS_DEVICE]->(d:Device)
        # OPTIONAL MATCH (d)-[:HAS_ISSUE]->(i:Issue)
        # RETURN d, collect(i) as issues

        return {"devices": [], "issues": []}
