/**
 * Session Manager - handles multiple WhatsApp sessions
 */

import * as fs from 'fs';
import * as path from 'path';
import * as crypto from 'crypto';
import Redis from 'ioredis';
import { v4 as uuidv4 } from 'uuid';
import { BaileysClient, BaileysClientOptions } from './baileys';
import {
  SessionInfo,
  SessionStatus,
  CreateSessionRequest,
  IncomingMessage,
  IncomingCall,
} from './types';

// Simple logger wrapper
const logger = {
  info: (msg: string, ...args: any[]) => console.log(`[INFO] ${msg}`, ...args),
  error: (msg: string, ...args: any[]) => console.error(`[ERROR] ${msg}`, ...args),
  warn: (msg: string, ...args: any[]) => console.warn(`[WARN] ${msg}`, ...args),
};

export interface SessionManagerOptions {
  sessionsDir: string;
  redisUrl?: string;
  defaultWebhookUrl?: string;
}

export class SessionManager {
  private sessions: Map<string, BaileysClient> = new Map();
  private sessionsByHash: Map<string, string> = new Map(); // hash -> sessionId
  private sessionsDir: string;
  private redis: Redis | null = null;
  private defaultWebhookUrl?: string;

  constructor(options: SessionManagerOptions) {
    this.sessionsDir = options.sessionsDir;
    this.defaultWebhookUrl = options.defaultWebhookUrl;

    // Create sessions directory
    if (!fs.existsSync(this.sessionsDir)) {
      fs.mkdirSync(this.sessionsDir, { recursive: true });
    }

    // Connect to Redis if URL provided
    if (options.redisUrl) {
      this.redis = new Redis(options.redisUrl, {
        maxRetriesPerRequest: null,
        enableReadyCheck: false,
      });
      this.redis.on('connect', () => logger.info('Redis connected'));
      this.redis.on('error', (err) => logger.error('Redis error:', err.message));
    }
  }

  // Generate hash from session ID
  private generateHash(sessionId: string): string {
    return crypto.createHash('sha256').update(sessionId).digest('hex').substring(0, 16);
  }

  // Get session by ID
  getSession(sessionId: string): BaileysClient | undefined {
    return this.sessions.get(sessionId);
  }

  // Get session by hash (for webhooks)
  getSessionByHash(hash: string): BaileysClient | undefined {
    const sessionId = this.sessionsByHash.get(hash);
    if (sessionId) {
      return this.sessions.get(sessionId);
    }
    return undefined;
  }

  // Get session ID by hash
  getSessionIdByHash(hash: string): string | undefined {
    return this.sessionsByHash.get(hash);
  }

  // List all sessions
  listSessions(): SessionInfo[] {
    const sessions: SessionInfo[] = [];
    for (const [id, client] of this.sessions) {
      sessions.push(client.getSessionInfo());
    }
    return sessions;
  }

  // Create new session
  async createSession(request: CreateSessionRequest): Promise<{ sessionId: string; hash: string }> {
    const sessionId = request.sessionId || uuidv4();
    const hash = this.generateHash(sessionId);

    // Check if session already exists
    if (this.sessions.has(sessionId)) {
      throw new Error(`Session ${sessionId} already exists`);
    }

    const webhookUrl = request.webhookUrl || this.defaultWebhookUrl;

    const client = new BaileysClient({
      sessionId,
      sessionsDir: this.sessionsDir,
      webhookUrl,
      onQR: async (qr, qrImage) => {
        logger.info(`QR code generated for session ${sessionId}`);
        await this.saveSessionToRedis(sessionId, {
          id: sessionId,
          hash,
          status: 'qr',
          webhookUrl,
          tenantId: request.tenantId,
          createdAt: new Date().toISOString(),
          updatedAt: new Date().toISOString(),
        });
      },
      onConnected: async (info) => {
        logger.info(`Session ${sessionId} connected as ${info.phone}`);
        await this.saveSessionToRedis(sessionId, {
          ...info,
          tenantId: request.tenantId,
          lastConnected: new Date().toISOString(),
        });
      },
      onDisconnected: async (reason) => {
        logger.info(`Session ${sessionId} disconnected: ${reason}`);
        if (reason === 'logged_out') {
          await this.deleteSession(sessionId);
        } else {
          await this.updateSessionStatus(sessionId, 'disconnected');
        }
      },
      onMessage: async (message) => {
        logger.info(`Message received in session ${sessionId} from ${message.from}`);
      },
      onCall: async (call) => {
        logger.info(`Call received in session ${sessionId} from ${call.from}`);
      },
    });

    this.sessions.set(sessionId, client);
    this.sessionsByHash.set(hash, sessionId);

    // Start connection
    await client.connect();

    // Save initial session info
    await this.saveSessionToRedis(sessionId, {
      id: sessionId,
      hash,
      status: 'connecting',
      webhookUrl,
      tenantId: request.tenantId,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    });

    return { sessionId, hash };
  }

  // Delete session
  async deleteSession(sessionId: string): Promise<void> {
    const client = this.sessions.get(sessionId);
    if (client) {
      await client.logout();
      const hash = this.generateHash(sessionId);
      this.sessions.delete(sessionId);
      this.sessionsByHash.delete(hash);
      await this.deleteSessionFromRedis(sessionId);
      logger.info(`Session ${sessionId} deleted`);
    }
  }

  // Disconnect session (keep credentials)
  async disconnectSession(sessionId: string): Promise<void> {
    const client = this.sessions.get(sessionId);
    if (client) {
      await client.disconnect();
      await this.updateSessionStatus(sessionId, 'disconnected');
    }
  }

  // Reconnect session
  async reconnectSession(sessionId: string): Promise<void> {
    const client = this.sessions.get(sessionId);
    if (client) {
      await client.connect();
    }
  }

  // Restore sessions from disk on startup
  async restoreSessions(): Promise<void> {
    if (!fs.existsSync(this.sessionsDir)) return;

    const sessionDirs = fs.readdirSync(this.sessionsDir, { withFileTypes: true })
      .filter(d => d.isDirectory())
      .map(d => d.name);

    logger.info(`Found ${sessionDirs.length} sessions to restore`);

    for (const sessionId of sessionDirs) {
      try {
        // Check if session has credentials
        const credsPath = path.join(this.sessionsDir, sessionId, 'creds.json');
        if (!fs.existsSync(credsPath)) {
          logger.info(`Skipping session ${sessionId} - no credentials`);
          continue;
        }

        // Get webhook URL from Redis if available
        let webhookUrl = this.defaultWebhookUrl;
        if (this.redis) {
          const sessionData = await this.redis.get(`whatsapp:session:${sessionId}`);
          if (sessionData) {
            const parsed = JSON.parse(sessionData);
            webhookUrl = parsed.webhookUrl || webhookUrl;
          }
        }

        const hash = this.generateHash(sessionId);

        const client = new BaileysClient({
          sessionId,
          sessionsDir: this.sessionsDir,
          webhookUrl,
          onConnected: async (info) => {
            logger.info(`Restored session ${sessionId} connected`);
            await this.saveSessionToRedis(sessionId, info);
          },
          onDisconnected: async (reason) => {
            if (reason === 'logged_out') {
              await this.deleteSession(sessionId);
            }
          },
        });

        this.sessions.set(sessionId, client);
        this.sessionsByHash.set(hash, sessionId);

        await client.connect();
        logger.info(`Session ${sessionId} restored`);
      } catch (err: any) {
        logger.error(`Failed to restore session ${sessionId}:`, err.message);
      }
    }
  }

  // Redis helpers
  private async saveSessionToRedis(sessionId: string, info: SessionInfo): Promise<void> {
    if (!this.redis) return;
    await this.redis.set(
      `whatsapp:session:${sessionId}`,
      JSON.stringify(info),
      'EX',
      86400 * 30 // 30 days
    );
    // Also save hash mapping
    await this.redis.set(
      `whatsapp:hash:${info.hash}`,
      sessionId,
      'EX',
      86400 * 30
    );
  }

  private async updateSessionStatus(sessionId: string, status: SessionStatus): Promise<void> {
    if (!this.redis) return;
    const data = await this.redis.get(`whatsapp:session:${sessionId}`);
    if (data) {
      const info = JSON.parse(data);
      info.status = status;
      info.updatedAt = new Date().toISOString();
      await this.redis.set(`whatsapp:session:${sessionId}`, JSON.stringify(info));
    }
  }

  private async deleteSessionFromRedis(sessionId: string): Promise<void> {
    if (!this.redis) return;
    const hash = this.generateHash(sessionId);
    await this.redis.del(`whatsapp:session:${sessionId}`);
    await this.redis.del(`whatsapp:hash:${hash}`);
  }

  // Get session info from Redis
  async getSessionInfo(sessionId: string): Promise<SessionInfo | null> {
    const client = this.sessions.get(sessionId);
    if (client) {
      return client.getSessionInfo();
    }

    if (this.redis) {
      const data = await this.redis.get(`whatsapp:session:${sessionId}`);
      if (data) {
        return JSON.parse(data);
      }
    }

    return null;
  }

  // Shutdown all sessions
  async shutdown(): Promise<void> {
    logger.info('Shutting down all sessions...');
    for (const [sessionId, client] of this.sessions) {
      try {
        await client.disconnect();
      } catch (err: any) {
        logger.error(`Failed to disconnect session ${sessionId}:`, err.message);
      }
    }
    if (this.redis) {
      await this.redis.quit();
    }
  }
}
