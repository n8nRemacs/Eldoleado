/**
 * Metrics tracking for self-healing and monitoring
 * Tracks messages, errors, reconnects per session
 */

export interface SessionMetrics {
  // Counters (last 24h rolling window)
  messagesSent: number;
  messagesReceived: number;
  messagesFailed: number;
  errorsTotal: number;
  reconnectAttempts: number;
  reconnectSuccesses: number;

  // Gauges
  currentStatus: string;
  isConnected: boolean;
  lastActivity: string | null;
  lastError: string | null;
  lastReconnect: string | null;

  // Uptime
  connectedSince: string | null;
  uptimeSeconds: number;
}

export interface AggregatedMetrics {
  // Session stats
  totalSessions: number;
  connectedSessions: number;
  disconnectedSessions: number;

  // Aggregated counters (24h)
  totalMessagesSent: number;
  totalMessagesReceived: number;
  totalMessagesFailed: number;
  totalErrors: number;
  totalReconnects: number;

  // Health
  healthScore: number; // 0-100
  status: 'healthy' | 'degraded' | 'unhealthy';
}

interface MetricEvent {
  type: 'sent' | 'received' | 'failed' | 'error' | 'reconnect_attempt' | 'reconnect_success';
  timestamp: number;
}

const ROLLING_WINDOW_MS = 24 * 60 * 60 * 1000; // 24 hours

export class MetricsCollector {
  private events: Map<string, MetricEvent[]> = new Map(); // sessionId -> events
  private statuses: Map<string, { status: string; connectedSince: string | null }> = new Map();
  private lastActivity: Map<string, string> = new Map();
  private lastError: Map<string, string> = new Map();
  private lastReconnect: Map<string, string> = new Map();

  // Initialize session metrics
  initSession(sessionId: string): void {
    if (!this.events.has(sessionId)) {
      this.events.set(sessionId, []);
    }
    if (!this.statuses.has(sessionId)) {
      this.statuses.set(sessionId, { status: 'disconnected', connectedSince: null });
    }
  }

  // Remove session metrics
  removeSession(sessionId: string): void {
    this.events.delete(sessionId);
    this.statuses.delete(sessionId);
    this.lastActivity.delete(sessionId);
    this.lastError.delete(sessionId);
    this.lastReconnect.delete(sessionId);
  }

  // Record events
  recordMessageSent(sessionId: string): void {
    this.addEvent(sessionId, 'sent');
    this.lastActivity.set(sessionId, new Date().toISOString());
  }

  recordMessageReceived(sessionId: string): void {
    this.addEvent(sessionId, 'received');
    this.lastActivity.set(sessionId, new Date().toISOString());
  }

  recordMessageFailed(sessionId: string): void {
    this.addEvent(sessionId, 'failed');
  }

  recordError(sessionId: string, error: string): void {
    this.addEvent(sessionId, 'error');
    this.lastError.set(sessionId, error);
  }

  recordReconnectAttempt(sessionId: string): void {
    this.addEvent(sessionId, 'reconnect_attempt');
    this.lastReconnect.set(sessionId, new Date().toISOString());
  }

  recordReconnectSuccess(sessionId: string): void {
    this.addEvent(sessionId, 'reconnect_success');
  }

  // Update status
  updateStatus(sessionId: string, status: string): void {
    const current = this.statuses.get(sessionId) || { status: 'disconnected', connectedSince: null };

    if (status === 'connected' && current.status !== 'connected') {
      current.connectedSince = new Date().toISOString();
    } else if (status !== 'connected') {
      current.connectedSince = null;
    }

    current.status = status;
    this.statuses.set(sessionId, current);
  }

  // Get metrics for a session
  getSessionMetrics(sessionId: string): SessionMetrics {
    this.initSession(sessionId);
    this.cleanOldEvents(sessionId);

    const events = this.events.get(sessionId) || [];
    const statusInfo = this.statuses.get(sessionId) || { status: 'disconnected', connectedSince: null };

    const counts = this.countEvents(events);

    let uptimeSeconds = 0;
    if (statusInfo.connectedSince) {
      uptimeSeconds = Math.floor((Date.now() - new Date(statusInfo.connectedSince).getTime()) / 1000);
    }

    return {
      messagesSent: counts.sent,
      messagesReceived: counts.received,
      messagesFailed: counts.failed,
      errorsTotal: counts.error,
      reconnectAttempts: counts.reconnect_attempt,
      reconnectSuccesses: counts.reconnect_success,
      currentStatus: statusInfo.status,
      isConnected: statusInfo.status === 'connected',
      lastActivity: this.lastActivity.get(sessionId) || null,
      lastError: this.lastError.get(sessionId) || null,
      lastReconnect: this.lastReconnect.get(sessionId) || null,
      connectedSince: statusInfo.connectedSince,
      uptimeSeconds,
    };
  }

  // Get aggregated metrics for all sessions
  getAggregatedMetrics(sessionIds: string[]): AggregatedMetrics {
    let connectedSessions = 0;
    let disconnectedSessions = 0;
    let totalMessagesSent = 0;
    let totalMessagesReceived = 0;
    let totalMessagesFailed = 0;
    let totalErrors = 0;
    let totalReconnects = 0;

    for (const sessionId of sessionIds) {
      const metrics = this.getSessionMetrics(sessionId);

      if (metrics.isConnected) {
        connectedSessions++;
      } else {
        disconnectedSessions++;
      }

      totalMessagesSent += metrics.messagesSent;
      totalMessagesReceived += metrics.messagesReceived;
      totalMessagesFailed += metrics.messagesFailed;
      totalErrors += metrics.errorsTotal;
      totalReconnects += metrics.reconnectAttempts;
    }

    const totalSessions = sessionIds.length;

    // Calculate health score (0-100)
    let healthScore = 100;

    if (totalSessions > 0) {
      // Deduct for disconnected sessions
      const disconnectedRatio = disconnectedSessions / totalSessions;
      healthScore -= Math.round(disconnectedRatio * 50);

      // Deduct for errors
      const totalMessages = totalMessagesSent + totalMessagesReceived;
      if (totalMessages > 0) {
        const errorRatio = totalMessagesFailed / totalMessages;
        healthScore -= Math.round(errorRatio * 30);
      }

      // Deduct for reconnects
      if (totalReconnects > totalSessions * 5) {
        healthScore -= 20;
      } else if (totalReconnects > totalSessions * 2) {
        healthScore -= 10;
      }
    }

    healthScore = Math.max(0, Math.min(100, healthScore));

    let status: AggregatedMetrics['status'];
    if (healthScore >= 80) {
      status = 'healthy';
    } else if (healthScore >= 50) {
      status = 'degraded';
    } else {
      status = 'unhealthy';
    }

    return {
      totalSessions,
      connectedSessions,
      disconnectedSessions,
      totalMessagesSent,
      totalMessagesReceived,
      totalMessagesFailed,
      totalErrors,
      totalReconnects,
      healthScore,
      status,
    };
  }

  // Private helpers
  private addEvent(sessionId: string, type: MetricEvent['type']): void {
    this.initSession(sessionId);
    const events = this.events.get(sessionId)!;
    events.push({ type, timestamp: Date.now() });
  }

  private cleanOldEvents(sessionId: string): void {
    const events = this.events.get(sessionId);
    if (!events) return;

    const cutoff = Date.now() - ROLLING_WINDOW_MS;
    const filtered = events.filter(e => e.timestamp > cutoff);
    this.events.set(sessionId, filtered);
  }

  private countEvents(events: MetricEvent[]): Record<MetricEvent['type'], number> {
    const counts: Record<MetricEvent['type'], number> = {
      sent: 0,
      received: 0,
      failed: 0,
      error: 0,
      reconnect_attempt: 0,
      reconnect_success: 0,
    };

    for (const event of events) {
      counts[event.type]++;
    }

    return counts;
  }
}

// Singleton instance
let metricsCollector: MetricsCollector | null = null;

export function getMetricsCollector(): MetricsCollector {
  if (!metricsCollector) {
    metricsCollector = new MetricsCollector();
  }
  return metricsCollector;
}
