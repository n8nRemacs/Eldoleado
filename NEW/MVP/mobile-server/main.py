"""
Mobile Server - Main entry point
Starts all channels and tunnel proxy.
"""

import asyncio
import logging
import signal
import subprocess
import sys
import os
from pathlib import Path

import uvicorn
import yaml

# Add parent to path
sys.path.insert(0, str(Path(__file__).parent))

from tunnel_proxy.proxy import TunnelProxy
from tunnel_proxy.webhook_server import app as webhook_app, set_tunnel_proxy

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s [%(levelname)s] %(name)s: %(message)s'
)
logger = logging.getLogger("MobileServer")


class MobileServer:
    """
    Main server that manages all components:
    - Tunnel Proxy (WebSocket to server)
    - Webhook Server (receives incoming messages from channels)
    - Channel services (WhatsApp, Telegram, etc.)
    """

    def __init__(self, config_path: str = "config.yaml"):
        self.config = self._load_config(config_path)
        self.tunnel_proxy = TunnelProxy()
        self.channel_processes = {}
        self.running = False

    def _load_config(self, path: str) -> dict:
        """Load configuration from YAML"""
        config_path = Path(path)
        if config_path.exists():
            with open(config_path) as f:
                return yaml.safe_load(f)
        return {}

    async def start(self):
        """Start all components"""
        self.running = True
        logger.info("Starting Mobile Server...")

        # Set tunnel proxy reference in webhook server
        set_tunnel_proxy(self.tunnel_proxy)

        # Start channel services
        await self._start_channels()

        # Start webhook server (background)
        webhook_task = asyncio.create_task(self._run_webhook_server())

        # Start tunnel proxy (main loop)
        try:
            await self.tunnel_proxy.start()
        except asyncio.CancelledError:
            pass

        # Cleanup
        webhook_task.cancel()
        await self._stop_channels()

    async def stop(self):
        """Stop all components"""
        logger.info("Stopping Mobile Server...")
        self.running = False
        await self.tunnel_proxy.stop()
        await self._stop_channels()

    async def _run_webhook_server(self):
        """Run webhook server"""
        config = uvicorn.Config(
            webhook_app,
            host="0.0.0.0",
            port=8000,
            log_level="warning"
        )
        server = uvicorn.Server(config)
        await server.serve()

    async def _start_channels(self):
        """Start enabled channel services"""
        channels = self.config.get("channels", {})

        for name, cfg in channels.items():
            if not cfg.get("enabled", True):
                continue

            port = cfg.get("port")
            channel_type = cfg.get("type")

            logger.info(f"Starting channel: {name} (port {port})")

            try:
                process = await self._start_channel(name, port, channel_type)
                if process:
                    self.channel_processes[name] = process
            except Exception as e:
                logger.error(f"Failed to start {name}: {e}")

    async def _start_channel(self, name: str, port: int, channel_type: str):
        """Start a single channel service"""
        channel_dir = Path(__file__).parent / "channels" / name

        if channel_type == "baileys":
            # Node.js service
            if not (channel_dir / "package.json").exists():
                logger.warning(f"Channel {name}: package.json not found")
                return None

            # npm install if needed
            if not (channel_dir / "node_modules").exists():
                logger.info(f"Installing npm packages for {name}...")
                subprocess.run(["npm", "install"], cwd=channel_dir, check=True)

            return subprocess.Popen(
                ["node", "index.js"],
                cwd=channel_dir,
                env={**os.environ, "PORT": str(port)}
            )

        else:
            # Python service
            app_path = channel_dir / "app.py"
            if not app_path.exists():
                logger.warning(f"Channel {name}: app.py not found")
                return None

            return subprocess.Popen(
                [sys.executable, "-m", "uvicorn", "app:app",
                 "--host", "0.0.0.0", "--port", str(port)],
                cwd=channel_dir,
                env={**os.environ, "PORT": str(port)}
            )

    async def _stop_channels(self):
        """Stop all channel services"""
        for name, process in self.channel_processes.items():
            logger.info(f"Stopping channel: {name}")
            process.terminate()
            try:
                process.wait(timeout=5)
            except subprocess.TimeoutExpired:
                process.kill()

        self.channel_processes.clear()


async def main():
    """Main entry point"""
    server = MobileServer()

    # Handle shutdown signals
    loop = asyncio.get_event_loop()

    def shutdown():
        asyncio.create_task(server.stop())

    for sig in (signal.SIGINT, signal.SIGTERM):
        try:
            loop.add_signal_handler(sig, shutdown)
        except NotImplementedError:
            # Windows doesn't support add_signal_handler
            pass

    await server.start()


if __name__ == "__main__":
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        logger.info("Interrupted by user")
