import os
from dotenv import load_dotenv

load_dotenv()

# Tunnel connection
TUNNEL_URL = os.getenv("TUNNEL_URL", "wss://tunnel.eldoleado.ru/ws")
TUNNEL_SECRET = os.getenv("TUNNEL_SECRET", "")
SERVER_ID = os.getenv("SERVER_ID", "phone_1")

# Reconnection
RECONNECT_DELAY = int(os.getenv("RECONNECT_DELAY", 5))
MAX_RECONNECT_DELAY = int(os.getenv("MAX_RECONNECT_DELAY", 60))

# HTTP client
HTTP_TIMEOUT = int(os.getenv("HTTP_TIMEOUT", 30))

# Local services
SERVICES = {
    "whatsapp": "http://localhost:3001",
    "telegram": "http://localhost:3002",
    "avito": "http://localhost:3003",
    "max": "http://localhost:3004",
    "vk": "http://localhost:3005",
    "http_proxy": "http://localhost:3010",
}

# Logging
LOG_LEVEL = os.getenv("LOG_LEVEL", "INFO")
