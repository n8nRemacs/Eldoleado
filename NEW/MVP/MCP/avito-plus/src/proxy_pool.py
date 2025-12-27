"""
Proxy Pool - manages proxies for Avito accounts.

Supports:
- Client PC (home computers via WireGuard)
- Client Router (GL.iNet devices)
- Mobile (phones with SIM)
- Datacenter (backup)
"""

import yaml
import logging
from pathlib import Path
from typing import Optional, Dict, List
from dataclasses import dataclass

from .models import ProxyConfig, ProxyType

logger = logging.getLogger(__name__)


class ProxyPool:
    """Manages proxy pool."""

    def __init__(self, config_path: Optional[Path] = None):
        self.proxies: Dict[str, ProxyConfig] = {}
        self.wireguard_server: Dict[str, str] = {}

        if config_path and config_path.exists():
            self._load_config(config_path)

    def _load_config(self, config_path: Path) -> None:
        """Load proxies from YAML config."""
        try:
            data = yaml.safe_load(config_path.read_text())

            for proxy_data in data.get("proxies", []):
                proxy = ProxyConfig(**proxy_data)
                self.proxies[proxy.id] = proxy
                logger.info(f"Loaded proxy: {proxy.id} ({proxy.type})")

            self.wireguard_server = data.get("wireguard_server", {})

        except Exception as e:
            logger.error(f"Error loading proxy config: {e}")

    def add_proxy(self, proxy: ProxyConfig) -> None:
        """Add proxy to pool."""
        self.proxies[proxy.id] = proxy

    def remove_proxy(self, proxy_id: str) -> bool:
        """Remove proxy from pool."""
        if proxy_id in self.proxies:
            del self.proxies[proxy_id]
            return True
        return False

    def get_proxy(self, proxy_id: str) -> Optional[ProxyConfig]:
        """Get proxy by ID."""
        return self.proxies.get(proxy_id)

    def get_available_proxies(self, proxy_type: Optional[ProxyType] = None) -> List[ProxyConfig]:
        """Get list of available (enabled) proxies."""
        proxies = [p for p in self.proxies.values() if p.enabled]

        if proxy_type:
            proxies = [p for p in proxies if p.type == proxy_type]

        return sorted(proxies, key=lambda p: p.priority)

    def get_best_proxy(self, proxy_type: Optional[ProxyType] = None) -> Optional[ProxyConfig]:
        """Get best available proxy (lowest priority number = highest priority)."""
        proxies = self.get_available_proxies(proxy_type)
        return proxies[0] if proxies else None

    def get_socks_url(self, proxy_id: str) -> Optional[str]:
        """Get SOCKS5 URL for proxy."""
        proxy = self.get_proxy(proxy_id)
        if not proxy:
            return None

        if proxy.transport == "wireguard":
            return f"socks5://{proxy.wireguard_ip}:{proxy.socks_port}"
        else:
            auth = ""
            if proxy.username and proxy.password:
                auth = f"{proxy.username}:{proxy.password}@"
            return f"socks5://{auth}{proxy.host}:{proxy.port}"

    def generate_wireguard_client_config(self, client_ip: str, client_private_key: str) -> str:
        """Generate WireGuard config for client."""
        if not self.wireguard_server:
            raise ValueError("WireGuard server not configured")

        return f"""[Interface]
PrivateKey = {client_private_key}
Address = {client_ip}/24

[Peer]
PublicKey = {self.wireguard_server.get('public_key', '')}
Endpoint = {self.wireguard_server.get('endpoint', '')}
AllowedIPs = {self.wireguard_server.get('subnet', '10.0.10.0/24')}
PersistentKeepalive = 25
"""

    def list_proxies(self) -> Dict[str, dict]:
        """List all proxies with status."""
        return {
            proxy_id: {
                "id": proxy.id,
                "type": proxy.type.value,
                "enabled": proxy.enabled,
                "priority": proxy.priority,
                "client_name": proxy.client_name,
                "location": proxy.location,
                "socks_url": self.get_socks_url(proxy_id) if proxy.enabled else None,
            }
            for proxy_id, proxy in self.proxies.items()
        }

    async def health_check(self, proxy_id: str) -> bool:
        """Check if proxy is healthy (can reach internet)."""
        # TODO: Implement actual health check
        # For now, just return True if proxy exists and enabled
        proxy = self.get_proxy(proxy_id)
        return proxy is not None and proxy.enabled
