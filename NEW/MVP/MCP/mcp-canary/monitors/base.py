"""Base monitor class for API Canary."""

import asyncio
import logging
from abc import ABC, abstractmethod
from dataclasses import dataclass, field
from datetime import datetime, timedelta
from enum import Enum
from typing import Any, Dict, List, Optional

import httpx

logger = logging.getLogger(__name__)


class CheckStatus(str, Enum):
    """Status of a health check."""
    OK = "ok"
    WARNING = "warning"
    ERROR = "error"
    TIMEOUT = "timeout"
    UNKNOWN = "unknown"


@dataclass
class CheckResult:
    """Result of a single endpoint check."""
    endpoint: str
    method: str
    status: CheckStatus
    status_code: Optional[int] = None
    latency_ms: float = 0
    error: Optional[str] = None
    response_data: Optional[Dict] = None
    schema_valid: bool = True
    schema_diff: Optional[str] = None
    timestamp: datetime = field(default_factory=datetime.now)

    def to_dict(self) -> Dict:
        return {
            "endpoint": self.endpoint,
            "method": self.method,
            "status": self.status.value,
            "status_code": self.status_code,
            "latency_ms": round(self.latency_ms, 2),
            "error": self.error,
            "schema_valid": self.schema_valid,
            "schema_diff": self.schema_diff,
            "timestamp": self.timestamp.isoformat(),
        }


@dataclass
class ChannelStatus:
    """Overall status of a channel."""
    channel: str
    status: CheckStatus
    health_score: int = 100
    last_check: Optional[datetime] = None
    endpoints: Dict[str, CheckResult] = field(default_factory=dict)
    errors_24h: int = 0
    consecutive_failures: int = 0

    def to_dict(self) -> Dict:
        return {
            "channel": self.channel,
            "status": self.status.value,
            "health_score": self.health_score,
            "last_check": self.last_check.isoformat() if self.last_check else None,
            "endpoints": {k: v.to_dict() for k, v in self.endpoints.items()},
            "errors_24h": self.errors_24h,
            "consecutive_failures": self.consecutive_failures,
        }


class BaseMonitor(ABC):
    """Base class for channel monitors."""

    def __init__(
        self,
        channel_name: str,
        base_url: str,
        api_key: Optional[str] = None,
        proxy_url: Optional[str] = None,
        timeout: float = 30.0,
    ):
        self.channel_name = channel_name
        self.base_url = base_url.rstrip("/")
        self.api_key = api_key
        self.proxy_url = proxy_url
        self.timeout = timeout

        self.status = ChannelStatus(channel=channel_name, status=CheckStatus.UNKNOWN)
        self.check_history: List[CheckResult] = []
        self._error_counts: Dict[str, int] = {}  # endpoint -> consecutive errors

    def _get_headers(self) -> Dict[str, str]:
        """Get request headers including API key if configured."""
        headers = {"Content-Type": "application/json"}
        if self.api_key:
            headers["X-API-Key"] = self.api_key
        return headers

    def _get_client(self) -> httpx.AsyncClient:
        """Get HTTP client with optional proxy."""
        kwargs = {
            "timeout": self.timeout,
            "headers": self._get_headers(),
        }
        if self.proxy_url:
            kwargs["proxy"] = self.proxy_url
        return httpx.AsyncClient(**kwargs)

    async def check_endpoint(
        self,
        endpoint: str,
        method: str = "GET",
        params: Optional[Dict] = None,
        json_data: Optional[Dict] = None,
        expected_status: int = 200,
    ) -> CheckResult:
        """Check a single endpoint."""
        url = f"{self.base_url}{endpoint}"
        start_time = datetime.now()

        try:
            async with self._get_client() as client:
                if method.upper() == "GET":
                    response = await client.get(url, params=params)
                elif method.upper() == "POST":
                    response = await client.post(url, params=params, json=json_data)
                else:
                    response = await client.request(method, url, params=params, json=json_data)

            latency = (datetime.now() - start_time).total_seconds() * 1000

            # Check status code
            if response.status_code == expected_status:
                status = CheckStatus.OK
                self._error_counts[endpoint] = 0
            elif response.status_code >= 500:
                status = CheckStatus.ERROR
                self._error_counts[endpoint] = self._error_counts.get(endpoint, 0) + 1
            elif response.status_code >= 400:
                status = CheckStatus.WARNING
                self._error_counts[endpoint] = self._error_counts.get(endpoint, 0) + 1
            else:
                status = CheckStatus.OK
                self._error_counts[endpoint] = 0

            # Try to parse JSON
            try:
                response_data = response.json()
            except Exception:
                response_data = None

            result = CheckResult(
                endpoint=endpoint,
                method=method,
                status=status,
                status_code=response.status_code,
                latency_ms=latency,
                response_data=response_data,
            )

        except httpx.TimeoutException:
            latency = (datetime.now() - start_time).total_seconds() * 1000
            self._error_counts[endpoint] = self._error_counts.get(endpoint, 0) + 1
            result = CheckResult(
                endpoint=endpoint,
                method=method,
                status=CheckStatus.TIMEOUT,
                latency_ms=latency,
                error="Request timeout",
            )

        except Exception as e:
            latency = (datetime.now() - start_time).total_seconds() * 1000
            self._error_counts[endpoint] = self._error_counts.get(endpoint, 0) + 1
            result = CheckResult(
                endpoint=endpoint,
                method=method,
                status=CheckStatus.ERROR,
                latency_ms=latency,
                error=str(e),
            )

        # Store result
        self.status.endpoints[endpoint] = result
        self.check_history.append(result)

        # Keep only last 24h of history
        cutoff = datetime.now() - timedelta(hours=24)
        self.check_history = [r for r in self.check_history if r.timestamp > cutoff]

        logger.info(
            f"[{self.channel_name}] {method} {endpoint} -> {result.status.value} "
            f"({result.latency_ms:.0f}ms)"
        )

        return result

    def calculate_health_score(self) -> int:
        """Calculate health score (0-100) based on recent checks."""
        if not self.status.endpoints:
            return 100

        total = len(self.status.endpoints)
        ok_count = sum(1 for r in self.status.endpoints.values() if r.status == CheckStatus.OK)
        warning_count = sum(1 for r in self.status.endpoints.values() if r.status == CheckStatus.WARNING)

        # OK = 100%, WARNING = 50%, ERROR/TIMEOUT = 0%
        score = (ok_count * 100 + warning_count * 50) // total
        return max(0, min(100, score))

    def get_overall_status(self) -> CheckStatus:
        """Get overall status based on endpoint statuses."""
        if not self.status.endpoints:
            return CheckStatus.UNKNOWN

        statuses = [r.status for r in self.status.endpoints.values()]

        if CheckStatus.ERROR in statuses or CheckStatus.TIMEOUT in statuses:
            return CheckStatus.ERROR
        if CheckStatus.WARNING in statuses:
            return CheckStatus.WARNING
        if all(s == CheckStatus.OK for s in statuses):
            return CheckStatus.OK
        return CheckStatus.UNKNOWN

    def count_errors_24h(self) -> int:
        """Count errors in the last 24 hours."""
        cutoff = datetime.now() - timedelta(hours=24)
        return sum(
            1 for r in self.check_history
            if r.timestamp > cutoff and r.status in [CheckStatus.ERROR, CheckStatus.TIMEOUT]
        )

    def get_max_consecutive_failures(self) -> int:
        """Get maximum consecutive failures across all endpoints."""
        if not self._error_counts:
            return 0
        return max(self._error_counts.values())

    async def run_health_check(self) -> ChannelStatus:
        """Run health check on /health/extended endpoint."""
        await self.check_endpoint("/health/extended")
        self._update_status()
        return self.status

    async def run_api_checks(self) -> ChannelStatus:
        """Run all API endpoint checks. Override in subclasses."""
        await self.run_health_check()
        self._update_status()
        return self.status

    def _update_status(self):
        """Update overall channel status."""
        self.status.status = self.get_overall_status()
        self.status.health_score = self.calculate_health_score()
        self.status.last_check = datetime.now()
        self.status.errors_24h = self.count_errors_24h()
        self.status.consecutive_failures = self.get_max_consecutive_failures()

    @abstractmethod
    async def get_endpoints_to_check(self) -> List[Dict[str, Any]]:
        """Return list of endpoints to check. Override in subclasses."""
        pass
