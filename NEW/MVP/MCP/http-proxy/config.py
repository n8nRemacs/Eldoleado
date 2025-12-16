import os
from dotenv import load_dotenv

load_dotenv()

# Server
HOST = os.getenv("HOST", "0.0.0.0")
PORT = int(os.getenv("PORT", 3010))

# Limits
MAX_TIMEOUT = int(os.getenv("MAX_TIMEOUT", 60))
MAX_RESPONSE_SIZE = int(os.getenv("MAX_RESPONSE_SIZE", 10 * 1024 * 1024))  # 10MB

# Headless browser (for /render)
BROWSER_ENABLED = os.getenv("BROWSER_ENABLED", "false").lower() == "true"
