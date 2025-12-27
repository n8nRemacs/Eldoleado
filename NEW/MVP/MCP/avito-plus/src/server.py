"""
Avito-Plus Server - FastAPI REST API.

Provides endpoints for:
- Account management (create, start, stop, login)
- Message streaming
- Proxy management
- Debug/monitoring
"""

import asyncio
import logging
import yaml
from pathlib import Path
from typing import Optional, Dict, Any, List
from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, WebSocket, WebSocketDisconnect
from fastapi.responses import JSONResponse

from .models import (
    AccountState, AccountStatus, LoginRequest, SmsRequest,
    SendMessageRequest, AvitoMessage, ProxyConfig
)
from .account_manager import AccountManager
from .proxy_pool import ProxyPool
from .message_router import MessageRouter

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(name)s: %(message)s"
)
logger = logging.getLogger(__name__)

# Configuration
CONFIG_DIR = Path(__file__).parent.parent / "config"
DATA_DIR = Path("/data/avito-plus")

# Global instances
proxy_pool: Optional[ProxyPool] = None
account_manager: Optional[AccountManager] = None
message_router: Optional[MessageRouter] = None


def load_settings() -> Dict[str, Any]:
    """Load settings from YAML."""
    settings_file = CONFIG_DIR / "settings.yaml"
    if settings_file.exists():
        return yaml.safe_load(settings_file.read_text())
    return {}


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan handler."""
    global proxy_pool, account_manager, message_router

    settings = load_settings()

    # Initialize proxy pool
    proxy_pool = ProxyPool(CONFIG_DIR / "proxies.yaml")

    # Initialize message router
    webhook_config = settings.get("webhook", {})
    message_router = MessageRouter(
        webhook_url=webhook_config.get("url") if webhook_config.get("enabled") else None
    )
    await message_router.start()

    # Initialize account manager
    data_dir = Path(settings.get("data_dir", "/data/avito-plus"))
    data_dir.mkdir(parents=True, exist_ok=True)

    account_manager = AccountManager(data_dir, proxy_pool)

    # Set message handler
    def on_message(account_id: str, message: AvitoMessage):
        asyncio.create_task(message_router.route_message(account_id, message))

    account_manager.set_message_handler(on_message)

    logger.info("Avito-Plus server started")

    yield

    # Cleanup
    await account_manager.stop_all()
    await message_router.stop()

    logger.info("Avito-Plus server stopped")


app = FastAPI(
    title="Avito-Plus",
    description="CDP Interception service for Avito",
    version="0.1.0",
    lifespan=lifespan
)


# === Health ===

@app.get("/health")
async def health():
    """Health check."""
    return {"status": "ok", "service": "avito-plus"}


# === Accounts ===

@app.get("/accounts")
async def list_accounts() -> Dict[str, Any]:
    """List all accounts."""
    return {"accounts": account_manager.list_accounts()}


@app.post("/account/{account_id}/create")
async def create_account(account_id: str) -> AccountState:
    """Create new account."""
    try:
        account = await account_manager.create_account(account_id)
        return account.state
    except ValueError as e:
        raise HTTPException(status_code=400, detail=str(e))


@app.post("/account/{account_id}/start")
async def start_account(account_id: str, proxy_id: Optional[str] = None) -> AccountState:
    """Start account browser."""
    try:
        account = await account_manager.start_account(account_id, proxy_id=proxy_id)
        return account.state
    except Exception as e:
        logger.error(f"Error starting account {account_id}: {e}")
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/account/{account_id}/stop")
async def stop_account(account_id: str) -> Dict[str, str]:
    """Stop account browser."""
    await account_manager.stop_account(account_id)
    return {"status": "stopped"}


@app.get("/account/{account_id}/status")
async def get_account_status(account_id: str) -> AccountState:
    """Get account status."""
    account = account_manager.get_account(account_id)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")
    return account.state


@app.post("/account/{account_id}/login")
async def login(account_id: str, request: LoginRequest) -> AccountState:
    """Login to Avito."""
    account = account_manager.get_account(account_id)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    if account.state.status != AccountStatus.RUNNING:
        raise HTTPException(status_code=400, detail="Account not running")

    try:
        await account.login(request.phone, request.password)
        return account.state
    except Exception as e:
        logger.error(f"Login error for {account_id}: {e}")
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/account/{account_id}/sms")
async def submit_sms(account_id: str, request: SmsRequest) -> AccountState:
    """Submit SMS verification code."""
    account = account_manager.get_account(account_id)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    if account.state.status != AccountStatus.SMS_REQUIRED:
        raise HTTPException(status_code=400, detail="SMS not required")

    try:
        await account.submit_sms(request.code)
        return account.state
    except Exception as e:
        logger.error(f"SMS error for {account_id}: {e}")
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/account/{account_id}/messenger")
async def go_to_messenger(account_id: str) -> Dict[str, Any]:
    """Navigate to messenger to start receiving messages."""
    account = account_manager.get_account(account_id)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    if account.state.status != AccountStatus.LOGGED_IN:
        raise HTTPException(status_code=400, detail="Not logged in")

    try:
        await account.go_to_messenger()
        return {
            "status": "ok",
            "hash_id": account.state.hash_id,
            "message": "Navigated to messenger, WebSocket interception active"
        }
    except Exception as e:
        logger.error(f"Messenger error for {account_id}: {e}")
        raise HTTPException(status_code=500, detail=str(e))


# === Interception ===

@app.get("/account/{account_id}/hash-id")
async def get_hash_id(account_id: str) -> Dict[str, Any]:
    """Get extracted hash_id."""
    account = account_manager.get_account(account_id)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    return {
        "hash_id": account.state.hash_id,
        "user_id": account.interceptor.user_id if account.interceptor else None
    }


@app.get("/account/{account_id}/traffic")
async def get_traffic(account_id: str, limit: int = 100) -> Dict[str, Any]:
    """Get intercepted traffic log."""
    account = account_manager.get_account(account_id)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    if not account.interceptor:
        return {"traffic": []}

    traffic = account.interceptor.get_traffic_log(limit)
    return {
        "traffic": [t.model_dump(mode="json") for t in traffic]
    }


@app.get("/account/{account_id}/cookies")
async def get_cookies(account_id: str) -> Dict[str, Any]:
    """Get account cookies."""
    account = account_manager.get_account(account_id)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    if not account.interceptor:
        return {"cookies": []}

    cookies = await account.interceptor.get_cookies()
    return {"cookies": cookies}


# === Proxies ===

@app.get("/proxies")
async def list_proxies() -> Dict[str, Any]:
    """List all proxies."""
    return {"proxies": proxy_pool.list_proxies()}


@app.get("/proxy/{proxy_id}/status")
async def get_proxy_status(proxy_id: str) -> Dict[str, Any]:
    """Get proxy status."""
    proxy = proxy_pool.get_proxy(proxy_id)
    if not proxy:
        raise HTTPException(status_code=404, detail="Proxy not found")

    healthy = await proxy_pool.health_check(proxy_id)
    return {
        "proxy": proxy.model_dump(),
        "healthy": healthy
    }


@app.post("/proxy/{proxy_id}/enable")
async def enable_proxy(proxy_id: str) -> Dict[str, str]:
    """Enable proxy."""
    proxy = proxy_pool.get_proxy(proxy_id)
    if not proxy:
        raise HTTPException(status_code=404, detail="Proxy not found")

    proxy.enabled = True
    return {"status": "enabled"}


@app.post("/proxy/{proxy_id}/disable")
async def disable_proxy(proxy_id: str) -> Dict[str, str]:
    """Disable proxy."""
    proxy = proxy_pool.get_proxy(proxy_id)
    if not proxy:
        raise HTTPException(status_code=404, detail="Proxy not found")

    proxy.enabled = False
    return {"status": "disabled"}


# === Run server ===

def main():
    """Run server."""
    import uvicorn

    settings = load_settings()
    server_config = settings.get("server", {})

    uvicorn.run(
        "src.server:app",
        host=server_config.get("host", "0.0.0.0"),
        port=server_config.get("port", 8794),
        reload=True
    )


if __name__ == "__main__":
    main()
