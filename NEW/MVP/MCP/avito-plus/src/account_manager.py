"""
Account Manager - manages Avito accounts with Camoufox browsers.

Each account has:
- Isolated browser instance with unique fingerprint
- CDP interception for traffic monitoring
- Persistent session (cookies, profile)
"""

import asyncio
import json
import logging
from datetime import datetime
from pathlib import Path
from typing import Optional, Dict, Any, Callable

from camoufox.async_api import AsyncCamoufox
from playwright.async_api import Page, BrowserContext

from .models import AccountState, AccountStatus, AvitoMessage, LoginRequest
from .avito_interceptor import AvitoInterceptor
from .fingerprint_manager import FingerprintManager
from .proxy_pool import ProxyPool

logger = logging.getLogger(__name__)


class AvitoAccount:
    """Single Avito account with browser and interception."""

    def __init__(
        self,
        account_id: str,
        data_dir: Path,
        proxy_pool: ProxyPool,
        on_message: Optional[Callable[[str, AvitoMessage], None]] = None
    ):
        self.account_id = account_id
        self.data_dir = data_dir / account_id
        self.proxy_pool = proxy_pool
        self.on_message = on_message

        self.data_dir.mkdir(parents=True, exist_ok=True)

        self.state = AccountState(id=account_id)
        self.fingerprint_manager = FingerprintManager(self.data_dir)

        self.browser: Optional[AsyncCamoufox] = None
        self.context: Optional[BrowserContext] = None
        self.page: Optional[Page] = None
        self.interceptor: Optional[AvitoInterceptor] = None

        self._load_state()

    def _load_state(self) -> None:
        """Load state from disk."""
        state_file = self.data_dir / "state.json"
        if state_file.exists():
            try:
                data = json.loads(state_file.read_text())
                self.state = AccountState(**data)
                self.state.status = AccountStatus.STOPPED  # Reset on load
            except Exception as e:
                logger.warning(f"[{self.account_id}] Error loading state: {e}")

    def _save_state(self) -> None:
        """Save state to disk."""
        state_file = self.data_dir / "state.json"
        try:
            state_file.write_text(self.state.model_dump_json(indent=2))
        except Exception as e:
            logger.error(f"[{self.account_id}] Error saving state: {e}")

    async def start(self, proxy_id: Optional[str] = None) -> None:
        """Start browser for this account."""
        if self.state.status == AccountStatus.RUNNING:
            logger.warning(f"[{self.account_id}] Already running")
            return

        self.state.status = AccountStatus.STARTING
        self._save_state()

        try:
            # Get fingerprint (creates if not exists)
            fingerprint = self.fingerprint_manager.get_or_create()

            # Get proxy config
            proxy_config = None
            if proxy_id:
                proxy = self.proxy_pool.get_proxy(proxy_id)
                if proxy:
                    proxy_config = {
                        "server": f"socks5://{proxy.wireguard_ip}:{proxy.socks_port}"
                    }
                    self.state.proxy_id = proxy_id
                    logger.info(f"[{self.account_id}] Using proxy: {proxy_id}")

            # Start Camoufox browser
            self.browser = AsyncCamoufox(
                headless=True,
                geoip=False,  # We handle geo via proxy
                humanize=True,
            )

            # Create context with fingerprint
            self.context = await self.browser.new_context(
                viewport={"width": fingerprint["screen"]["width"], "height": fingerprint["screen"]["height"]},
                locale=fingerprint["language"],
                timezone_id=fingerprint["timezone"],
                user_agent=fingerprint["userAgent"],
                proxy=proxy_config,
                storage_state=self._get_storage_state_path()
            )

            # Create page
            self.page = await self.context.new_page()

            # Attach interceptor
            self.interceptor = AvitoInterceptor(
                account_id=self.account_id,
                on_message=self._handle_message,
                on_hash_id=self._handle_hash_id,
                debug=True
            )
            await self.interceptor.attach(self.page)

            self.state.status = AccountStatus.RUNNING
            self.state.last_activity = datetime.now()
            self._save_state()

            logger.info(f"[{self.account_id}] Browser started")

        except Exception as e:
            self.state.status = AccountStatus.ERROR
            self.state.error = str(e)
            self._save_state()
            logger.error(f"[{self.account_id}] Error starting browser: {e}")
            raise

    async def stop(self) -> None:
        """Stop browser."""
        if self.interceptor:
            await self.interceptor.detach()
            self.interceptor = None

        if self.context:
            # Save storage state before closing
            try:
                await self.context.storage_state(path=str(self._get_storage_state_path()))
            except Exception as e:
                logger.warning(f"[{self.account_id}] Error saving storage state: {e}")

            await self.context.close()
            self.context = None

        if self.browser:
            await self.browser.close()
            self.browser = None

        self.page = None
        self.state.status = AccountStatus.STOPPED
        self._save_state()

        logger.info(f"[{self.account_id}] Browser stopped")

    def _get_storage_state_path(self) -> Optional[str]:
        """Get storage state file path."""
        storage_file = self.data_dir / "storage_state.json"
        if storage_file.exists():
            return str(storage_file)
        return None

    async def login(self, phone: str, password: str) -> AccountStatus:
        """Login to Avito."""
        if not self.page:
            raise RuntimeError("Browser not started")

        try:
            # Navigate to login page
            await self.page.goto("https://www.avito.ru/profile", wait_until="networkidle")
            await asyncio.sleep(2)

            # Check if already logged in
            if "/profile/items" in self.page.url or await self.page.query_selector("[data-marker='header/username']"):
                logger.info(f"[{self.account_id}] Already logged in")
                self.state.status = AccountStatus.LOGGED_IN
                self._save_state()
                return self.state.status

            # Find login button and click
            login_btn = await self.page.query_selector("[data-marker='header/login-button']")
            if login_btn:
                await login_btn.click()
                await asyncio.sleep(1)

            # Enter phone
            phone_input = await self.page.wait_for_selector("input[name='phone'], input[type='tel']", timeout=10000)
            await phone_input.fill(phone)
            await asyncio.sleep(0.5)

            # Click continue
            continue_btn = await self.page.query_selector("button[type='submit'], [data-marker='auth/continue']")
            if continue_btn:
                await continue_btn.click()
                await asyncio.sleep(2)

            # Enter password
            password_input = await self.page.wait_for_selector("input[name='password'], input[type='password']", timeout=10000)
            await password_input.fill(password)
            await asyncio.sleep(0.5)

            # Click login
            submit_btn = await self.page.query_selector("button[type='submit']")
            if submit_btn:
                await submit_btn.click()
                await asyncio.sleep(3)

            # Check result
            if await self.page.query_selector("[data-marker='sms-code'], input[name='code']"):
                logger.info(f"[{self.account_id}] SMS code required")
                self.state.status = AccountStatus.SMS_REQUIRED
                self._save_state()
                return self.state.status

            if "/profile" in self.page.url:
                logger.info(f"[{self.account_id}] Login successful")
                self.state.status = AccountStatus.LOGGED_IN
                await self.context.storage_state(path=str(self.data_dir / "storage_state.json"))
                self._save_state()
                return self.state.status

            # Check for captcha or error
            error_el = await self.page.query_selector("[data-marker='error'], .error-message")
            if error_el:
                error_text = await error_el.text_content()
                self.state.error = error_text
                self.state.status = AccountStatus.ERROR
                self._save_state()
                return self.state.status

            self.state.status = AccountStatus.ERROR
            self.state.error = "Unknown login state"
            self._save_state()
            return self.state.status

        except Exception as e:
            self.state.status = AccountStatus.ERROR
            self.state.error = str(e)
            self._save_state()
            logger.error(f"[{self.account_id}] Login error: {e}")
            raise

    async def submit_sms(self, code: str) -> AccountStatus:
        """Submit SMS verification code."""
        if not self.page:
            raise RuntimeError("Browser not started")

        try:
            code_input = await self.page.wait_for_selector("input[name='code'], [data-marker='sms-code'] input", timeout=5000)
            await code_input.fill(code)
            await asyncio.sleep(0.5)

            submit_btn = await self.page.query_selector("button[type='submit']")
            if submit_btn:
                await submit_btn.click()
                await asyncio.sleep(3)

            if "/profile" in self.page.url:
                logger.info(f"[{self.account_id}] SMS verification successful")
                self.state.status = AccountStatus.LOGGED_IN
                await self.context.storage_state(path=str(self.data_dir / "storage_state.json"))
                self._save_state()
                return self.state.status

            self.state.status = AccountStatus.ERROR
            self.state.error = "SMS verification failed"
            self._save_state()
            return self.state.status

        except Exception as e:
            self.state.status = AccountStatus.ERROR
            self.state.error = str(e)
            self._save_state()
            raise

    async def go_to_messenger(self) -> None:
        """Navigate to messenger to start receiving messages."""
        if not self.page:
            raise RuntimeError("Browser not started")

        await self.page.goto("https://www.avito.ru/profile/messenger", wait_until="networkidle")
        await asyncio.sleep(2)

        logger.info(f"[{self.account_id}] Navigated to messenger, waiting for WebSocket...")

    def _handle_message(self, message: AvitoMessage) -> None:
        """Handle incoming message from interceptor."""
        self.state.last_activity = datetime.now()
        self._save_state()

        if self.on_message:
            self.on_message(self.account_id, message)

    def _handle_hash_id(self, hash_id: str) -> None:
        """Handle hash_id extraction."""
        self.state.hash_id = hash_id
        self._save_state()


class AccountManager:
    """Manages multiple Avito accounts."""

    def __init__(self, data_dir: Path, proxy_pool: ProxyPool):
        self.data_dir = data_dir
        self.proxy_pool = proxy_pool
        self.accounts: Dict[str, AvitoAccount] = {}
        self.on_message: Optional[Callable[[str, AvitoMessage], None]] = None

    def set_message_handler(self, handler: Callable[[str, AvitoMessage], None]) -> None:
        """Set global message handler."""
        self.on_message = handler

    async def create_account(self, account_id: str) -> AvitoAccount:
        """Create new account."""
        if account_id in self.accounts:
            raise ValueError(f"Account {account_id} already exists")

        account = AvitoAccount(
            account_id=account_id,
            data_dir=self.data_dir,
            proxy_pool=self.proxy_pool,
            on_message=self.on_message
        )
        self.accounts[account_id] = account
        return account

    def get_account(self, account_id: str) -> Optional[AvitoAccount]:
        """Get account by ID."""
        return self.accounts.get(account_id)

    async def start_account(self, account_id: str, proxy_id: Optional[str] = None) -> AvitoAccount:
        """Start account browser."""
        account = self.accounts.get(account_id)
        if not account:
            account = await self.create_account(account_id)

        await account.start(proxy_id=proxy_id)
        return account

    async def stop_account(self, account_id: str) -> None:
        """Stop account browser."""
        account = self.accounts.get(account_id)
        if account:
            await account.stop()

    async def stop_all(self) -> None:
        """Stop all accounts."""
        for account in self.accounts.values():
            await account.stop()

    def list_accounts(self) -> Dict[str, AccountState]:
        """List all accounts with states."""
        return {
            account_id: account.state
            for account_id, account in self.accounts.items()
        }
