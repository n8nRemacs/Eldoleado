import os
from dotenv import load_dotenv

load_dotenv()

# Telegram API credentials (from my.telegram.org)
API_ID = os.getenv("TELEGRAM_API_ID")
API_HASH = os.getenv("TELEGRAM_API_HASH")
PHONE_NUMBER = os.getenv("TELEGRAM_PHONE_NUMBER")

# Session
SESSION_NAME = os.getenv("SESSION_NAME", "telegram_user")
SESSION_DIR = os.getenv("SESSION_DIR", "./sessions")

# Server
HOST = os.getenv("HOST", "0.0.0.0")
PORT = int(os.getenv("PORT", 3002))

# Webhook for incoming messages
WEBHOOK_URL = os.getenv("WEBHOOK_URL", "")
