/**
 * Alerting system for self-healing notifications
 * Supports Telegram bot and n8n webhook
 */

import axios from 'axios';

// Simple logger wrapper
const logger = {
  info: (msg: string, ...args: any[]) => console.log(`[INFO] ${msg}`, ...args),
  error: (msg: string, ...args: any[]) => console.error(`[ERROR] ${msg}`, ...args),
  warn: (msg: string, ...args: any[]) => console.warn(`[WARN] ${msg}`, ...args),
};

export interface AlertConfig {
  // Telegram bot settings
  telegramBotToken?: string;
  telegramChatId?: string;

  // n8n webhook settings
  n8nWebhookUrl?: string;

  // Alert settings
  enabled: boolean;
  alertOnMaxRetries: boolean;
  alertOnDisconnect: boolean;
  alertOnBan: boolean;
  alertOnError: boolean;

  // Cooldown (prevent spam)
  cooldownMs: number;
}

export type AlertType =
  | 'max_retries_exceeded'
  | 'disconnected'
  | 'banned'
  | 'error'
  | 'connected'
  | 'reconnecting';

export interface AlertPayload {
  type: AlertType;
  sessionId: string;
  channel: string;
  message: string;
  details?: Record<string, any>;
  timestamp: string;
  severity: 'info' | 'warning' | 'critical';
}

export class AlertService {
  private config: AlertConfig;
  private lastAlerts: Map<string, number> = new Map(); // key -> timestamp

  constructor(config: Partial<AlertConfig> = {}) {
    this.config = {
      telegramBotToken: config.telegramBotToken || process.env.ALERT_TELEGRAM_BOT_TOKEN,
      telegramChatId: config.telegramChatId || process.env.ALERT_TELEGRAM_CHAT_ID,
      n8nWebhookUrl: config.n8nWebhookUrl || process.env.ALERT_N8N_WEBHOOK_URL,
      enabled: config.enabled ?? true,
      alertOnMaxRetries: config.alertOnMaxRetries ?? true,
      alertOnDisconnect: config.alertOnDisconnect ?? false, // Only alert on permanent disconnects
      alertOnBan: config.alertOnBan ?? true,
      alertOnError: config.alertOnError ?? true,
      cooldownMs: config.cooldownMs ?? 60000, // 1 minute cooldown
    };
  }

  private shouldAlert(key: string): boolean {
    if (!this.config.enabled) return false;

    const lastAlert = this.lastAlerts.get(key);
    if (lastAlert && Date.now() - lastAlert < this.config.cooldownMs) {
      return false;
    }

    return true;
  }

  private recordAlert(key: string): void {
    this.lastAlerts.set(key, Date.now());
  }

  async sendAlert(payload: AlertPayload): Promise<void> {
    const alertKey = `${payload.type}:${payload.sessionId}`;

    if (!this.shouldAlert(alertKey)) {
      logger.info(`Alert skipped (cooldown): ${alertKey}`);
      return;
    }

    this.recordAlert(alertKey);

    // Send to all configured channels in parallel
    const promises: Promise<void>[] = [];

    if (this.config.telegramBotToken && this.config.telegramChatId) {
      promises.push(this.sendTelegramAlert(payload));
    }

    if (this.config.n8nWebhookUrl) {
      promises.push(this.sendN8nAlert(payload));
    }

    await Promise.allSettled(promises);
  }

  private async sendTelegramAlert(payload: AlertPayload): Promise<void> {
    const { telegramBotToken, telegramChatId } = this.config;
    if (!telegramBotToken || !telegramChatId) return;

    const emoji = this.getEmoji(payload.severity);
    const typeLabel = this.getTypeLabel(payload.type);

    const text = [
      `${emoji} *${typeLabel}*`,
      '',
      `📱 *Канал:* ${payload.channel}`,
      `🔑 *Сессия:* \`${payload.sessionId}\``,
      `📝 *Сообщение:* ${payload.message}`,
      payload.details ? `📋 *Детали:* ${JSON.stringify(payload.details)}` : '',
      '',
      `🕐 ${payload.timestamp}`,
    ].filter(Boolean).join('\n');

    try {
      await axios.post(
        `https://api.telegram.org/bot${telegramBotToken}/sendMessage`,
        {
          chat_id: telegramChatId,
          text,
          parse_mode: 'Markdown',
          disable_notification: payload.severity === 'info',
        },
        { timeout: 10000 }
      );
      logger.info(`Telegram alert sent: ${payload.type}`);
    } catch (err: any) {
      logger.error(`Telegram alert failed: ${err.message}`);
    }
  }

  private async sendN8nAlert(payload: AlertPayload): Promise<void> {
    const { n8nWebhookUrl } = this.config;
    if (!n8nWebhookUrl) return;

    try {
      await axios.post(
        n8nWebhookUrl,
        {
          ...payload,
          source: 'mcp-whatsapp-baileys',
        },
        {
          timeout: 10000,
          headers: { 'Content-Type': 'application/json' },
        }
      );
      logger.info(`n8n alert sent: ${payload.type}`);
    } catch (err: any) {
      logger.error(`n8n alert failed: ${err.message}`);
    }
  }

  private getEmoji(severity: AlertPayload['severity']): string {
    switch (severity) {
      case 'critical': return '🚨';
      case 'warning': return '⚠️';
      case 'info': return 'ℹ️';
      default: return '📢';
    }
  }

  private getTypeLabel(type: AlertType): string {
    switch (type) {
      case 'max_retries_exceeded': return 'Превышено макс. попыток';
      case 'disconnected': return 'Отключен';
      case 'banned': return 'Заблокирован';
      case 'error': return 'Ошибка';
      case 'connected': return 'Подключен';
      case 'reconnecting': return 'Переподключение';
      default: return type;
    }
  }

  // Convenience methods
  async alertMaxRetriesExceeded(sessionId: string, attempts: number): Promise<void> {
    if (!this.config.alertOnMaxRetries) return;

    await this.sendAlert({
      type: 'max_retries_exceeded',
      sessionId,
      channel: 'WhatsApp',
      message: `Не удалось переподключиться после ${attempts} попыток`,
      details: { attempts },
      timestamp: new Date().toISOString(),
      severity: 'critical',
    });
  }

  async alertDisconnected(sessionId: string, reason: string): Promise<void> {
    if (!this.config.alertOnDisconnect) return;

    await this.sendAlert({
      type: 'disconnected',
      sessionId,
      channel: 'WhatsApp',
      message: `Сессия отключена: ${reason}`,
      details: { reason },
      timestamp: new Date().toISOString(),
      severity: 'warning',
    });
  }

  async alertBanned(sessionId: string): Promise<void> {
    if (!this.config.alertOnBan) return;

    await this.sendAlert({
      type: 'banned',
      sessionId,
      channel: 'WhatsApp',
      message: 'Аккаунт заблокирован WhatsApp!',
      timestamp: new Date().toISOString(),
      severity: 'critical',
    });
  }

  async alertError(sessionId: string, error: string): Promise<void> {
    if (!this.config.alertOnError) return;

    await this.sendAlert({
      type: 'error',
      sessionId,
      channel: 'WhatsApp',
      message: `Ошибка: ${error}`,
      details: { error },
      timestamp: new Date().toISOString(),
      severity: 'warning',
    });
  }

  async alertConnected(sessionId: string, phone?: string): Promise<void> {
    await this.sendAlert({
      type: 'connected',
      sessionId,
      channel: 'WhatsApp',
      message: phone ? `Подключен как ${phone}` : 'Успешно подключен',
      details: { phone },
      timestamp: new Date().toISOString(),
      severity: 'info',
    });
  }

  async alertReconnecting(sessionId: string, attempt: number, maxAttempts: number): Promise<void> {
    // Only alert on first attempt and every 3rd attempt
    if (attempt !== 1 && attempt % 3 !== 0) return;

    await this.sendAlert({
      type: 'reconnecting',
      sessionId,
      channel: 'WhatsApp',
      message: `Переподключение: попытка ${attempt}/${maxAttempts}`,
      details: { attempt, maxAttempts },
      timestamp: new Date().toISOString(),
      severity: 'warning',
    });
  }
}

// Singleton instance
let alertService: AlertService | null = null;

export function getAlertService(config?: Partial<AlertConfig>): AlertService {
  if (!alertService) {
    alertService = new AlertService(config);
  }
  return alertService;
}

export function initAlertService(config: Partial<AlertConfig>): AlertService {
  alertService = new AlertService(config);
  return alertService;
}
