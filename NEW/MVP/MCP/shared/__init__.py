# MCP Shared Module
"""Shared utilities for MCP servers.

Modules:
- storage: Redis + PostgreSQL storage for accounts
- self_healing: Self-healing mixin with exponential backoff
- watchdog: Connection health monitoring
- health: Unified health check models
- alerts: Telegram + n8n alerting
"""

from .storage import (
    init_storage,
    close_storage,
    save_account,
    load_accounts,
    get_account,
    delete_account,
    update_account_metadata,
    restore_from_postgresql,
    get_credentials_hash,
)

from .self_healing import (
    SelfHealingMixin,
    ConnectionStatus,
    RecoveryStrategy,
    WHATSAPP_ERROR_STRATEGIES,
    TELEGRAM_ERROR_STRATEGIES,
    AVITO_ERROR_STRATEGIES,
    VK_ERROR_STRATEGIES,
    MAX_ERROR_STRATEGIES,
)

from .watchdog import (
    ConnectionWatchdog,
    MultiSessionWatchdog,
)

from .health import (
    HealthStatus,
    HealthResponse,
    HealthChecker,
    SessionStats,
    MessageMetrics,
    HealthMetrics,
    get_health_checker,
)

from .alerts import (
    AlertService,
    AlertConfig,
    AlertPayload,
    AlertType,
    AlertSeverity,
    get_alert_service,
    init_alert_service,
)

__all__ = [
    # Storage
    "init_storage",
    "close_storage",
    "save_account",
    "load_accounts",
    "get_account",
    "delete_account",
    "update_account_metadata",
    "restore_from_postgresql",
    "get_credentials_hash",
    # Self-healing
    "SelfHealingMixin",
    "ConnectionStatus",
    "RecoveryStrategy",
    "WHATSAPP_ERROR_STRATEGIES",
    "TELEGRAM_ERROR_STRATEGIES",
    "AVITO_ERROR_STRATEGIES",
    "VK_ERROR_STRATEGIES",
    "MAX_ERROR_STRATEGIES",
    # Watchdog
    "ConnectionWatchdog",
    "MultiSessionWatchdog",
    # Health
    "HealthStatus",
    "HealthResponse",
    "HealthChecker",
    "SessionStats",
    "MessageMetrics",
    "HealthMetrics",
    "get_health_checker",
    # Alerts
    "AlertService",
    "AlertConfig",
    "AlertPayload",
    "AlertType",
    "AlertSeverity",
    "get_alert_service",
    "init_alert_service",
]
