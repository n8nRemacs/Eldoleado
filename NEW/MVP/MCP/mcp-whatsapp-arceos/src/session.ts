/**
 * Session Manager - handles multiple WhatsApp sessions
 * With PostgreSQL backup for session recovery
 */

import * as fs from 'fs';
import * as path from 'path';
import * as crypto from 'crypto';
import { execSync } from 'child_process';
import Redis from 'ioredis';
import { Pool } from 'pg';
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
  databaseUrl?: string;
  ipNodeId?: number;
  defaultWebhookUrl?: string;
  defaultProxyUrl?: string;
}

export class SessionManager {
  private sessions: Map<string, BaileysClient> = new Map();
  private sessionsByHash: Map<string, string> = new Map(); // hash -> sessionId
  private sessionCreatedAt: Map<string, number> = new Map(); // sessionId -> timestamp
  private sessionsDir: string;
  private redis: Redis | null = null;
  private pg: Pool | null = null;
  private ipNodeId: number;
  private defaultWebhookUrl?: string;
  private defaultProxyUrl?: string;
  private cleanupInterval: NodeJS.Timeout | null = null;

  // QR session timeout: 10 minutes
  private static readonly QR_TIMEOUT_MS = 10 * 60 * 1000;
  // Cleanup check interval: 60 seconds
  private static readonly CLEANUP_INTERVAL_MS = 60 * 1000;
  // Session archive backup interval: 5 minutes
  private static readonly BACKUP_INTERVAL_MS = 5 * 60 * 1000;

  constructor(options: SessionManagerOptions) {
    this.sessionsDir = options.sessionsDir;
    this.defaultWebhookUrl = options.defaultWebhookUrl;
    this.defaultProxyUrl = options.defaultProxyUrl;
    this.ipNodeId = options.ipNodeId || 1;

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

    // Connect to PostgreSQL if URL provided
    if (options.databaseUrl) {
      this.pg = new Pool({
        connectionString: options.databaseUrl,
        max: 5,
        idleTimeoutMillis: 30000,
        connectionTimeoutMillis: 5000,
      });
      this.pg.on('connect', () => logger.info('PostgreSQL connected'));
      this.pg.on('error', (err) => logger.error('PostgreSQL error:', err.message));
    }

    // Start cleanup interval for stale QR sessions
    this.startCleanupInterval();
  }

  // Start periodic cleanup of stale QR sessions
  private startCleanupInterval(): void {
    this.cleanupInterval = setInterval(() => {
      this.cleanupStaleQRSessions();
    }, SessionManager.CLEANUP_INTERVAL_MS);

    logger.info('QR session cleanup interval started (check every 60s, timeout 10min)');
  }

  // Clean up sessions stuck in QR state for too long
  private cleanupStaleQRSessions(): void {
    const now = Date.now();
    const staleSessionIds: string[] = [];

    for (const [sessionId, client] of this.sessions) {
      const createdAt = this.sessionCreatedAt.get(sessionId);
      if (!createdAt) continue;

      const status = client.getStatus();
      const age = now - createdAt;

      // Only clean up sessions in 'qr' or 'connecting' state
      if ((status === 'qr' || status === 'connecting') && age > SessionManager.QR_TIMEOUT_MS) {
        staleSessionIds.push(sessionId);
      }
    }

    // Delete stale sessions
    for (const sessionId of staleSessionIds) {
      const age = Math.round((now - (this.sessionCreatedAt.get(sessionId) || 0)) / 1000 / 60);
      logger.info(`Removing stale QR session: ${sessionId} (age: ${age} min)`);

      // Remove from maps without calling logout (no credentials to clear)
      const client = this.sessions.get(sessionId);
      if (client) {
        client.disconnect().catch(() => {}); // Ignore errors
      }

      const hash = this.generateHash(sessionId);
      this.sessions.delete(sessionId);
      this.sessionsByHash.delete(hash);
      this.sessionCreatedAt.delete(sessionId);
      this.deleteSessionFromRedis(sessionId).catch(() => {});
    }

    if (staleSessionIds.length > 0) {
      logger.info(`Cleaned up ${staleSessionIds.length} stale QR session(s)`);
    }
  }

  // Archive session folder to base64 tar.gz
  private async createSessionArchive(sessionId: string): Promise<string | null> {
    const sessionPath = path.join(this.sessionsDir, sessionId);
    if (!fs.existsSync(sessionPath)) {
      logger.warn(`Session folder not found: ${sessionPath}`);
      return null;
    }

    try {
      // Create tar.gz archive and encode to base64
      const tarCommand = `cd "${this.sessionsDir}" && tar -czf - "${sessionId}" | base64 -w 0`;
      const archive = execSync(tarCommand, { maxBuffer: 50 * 1024 * 1024 }).toString();
      logger.info(`Created archive for session ${sessionId} (${Math.round(archive.length / 1024)}KB)`);
      return archive;
    } catch (err: any) {
      logger.error(`Failed to create archive for session ${sessionId}:`, err.message);
      return null;
    }
  }

  // Restore session from base64 tar.gz archive
  private async restoreSessionArchive(sessionId: string, archive: string): Promise<boolean> {
    const sessionPath = path.join(this.sessionsDir, sessionId);
    const tempArchivePath = path.join(this.sessionsDir, `${sessionId}.tar.gz.b64`);

    try {
      // Remove existing session folder if exists
      if (fs.existsSync(sessionPath)) {
        fs.rmSync(sessionPath, { recursive: true, force: true });
      }

      // Write base64 archive to temp file (avoid E2BIG for large archives)
      fs.writeFileSync(tempArchivePath, archive);

      // Decode base64 and extract tar.gz from file
      const tarCommand = `cd "${this.sessionsDir}" && base64 -d "${tempArchivePath}" | tar -xzf -`;
      execSync(tarCommand, { maxBuffer: 50 * 1024 * 1024 });

      // Clean up temp file
      fs.unlinkSync(tempArchivePath);

      // Verify creds.json exists
      const credsPath = path.join(sessionPath, 'creds.json');
      if (!fs.existsSync(credsPath)) {
        throw new Error('creds.json not found after extraction');
      }

      logger.info(`Restored archive for session ${sessionId}`);
      return true;
    } catch (err: any) {
      // Clean up temp file on error
      if (fs.existsSync(tempArchivePath)) {
        fs.unlinkSync(tempArchivePath);
      }
      logger.error(`Failed to restore archive for session ${sessionId}:`, err.message);
      return false;
    }
  }

  // Backup session to PostgreSQL
  async backupSessionToDatabase(sessionId: string): Promise<boolean> {
    if (!this.pg) {
      logger.warn('PostgreSQL not configured, skipping backup');
      return false;
    }

    const archive = await this.createSessionArchive(sessionId);
    if (!archive) return false;

    try {
      await this.pg.query(
        `UPDATE elo_t_channel_accounts
         SET session_archive = $1, updated_at = NOW()
         WHERE session_id = $2`,
        [archive, sessionId]
      );
      logger.info(`Backed up session ${sessionId} to PostgreSQL`);
      return true;
    } catch (err: any) {
      logger.error(`Failed to backup session ${sessionId} to PostgreSQL:`, err.message);
      return false;
    }
  }

  // Clear session archive from PostgreSQL (on logout)
  private async clearSessionArchive(sessionId: string): Promise<void> {
    if (!this.pg) return;

    try {
      await this.pg.query(
        `UPDATE elo_t_channel_accounts
         SET session_archive = NULL, session_status = 'disconnected', updated_at = NOW()
         WHERE session_id = $1`,
        [sessionId]
      );
      logger.info(`Cleared session archive for ${sessionId}`);
    } catch (err: any) {
      logger.error(`Failed to clear session archive for ${sessionId}:`, err.message);
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
    const proxyUrl = request.proxyUrl || this.defaultProxyUrl;

    const client = new BaileysClient({
      sessionId,
      sessionsDir: this.sessionsDir,
      webhookUrl,
      proxyUrl,
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
        // Clear createdAt timestamp - session is now connected, no cleanup needed
        this.sessionCreatedAt.delete(sessionId);
        await this.saveSessionToRedis(sessionId, {
          ...info,
          tenantId: request.tenantId,
          lastConnected: new Date().toISOString(),
        });
        // Backup session to PostgreSQL after successful connection
        // Wait a bit for Baileys to write all session files
        setTimeout(async () => {
          await this.backupSessionToDatabase(sessionId);
        }, 5000);
      },
      onDisconnected: async (reason) => {
        logger.info(`Session ${sessionId} disconnected: ${reason}`);
        if (reason === 'logged_out') {
          await this.clearSessionArchive(sessionId);
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
    this.sessionCreatedAt.set(sessionId, Date.now());

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
      this.sessionCreatedAt.delete(sessionId);
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

  // Restore sessions from PostgreSQL on startup
  async restoreSessions(): Promise<void> {
    // First, try to restore from PostgreSQL (source of truth)
    if (this.pg) {
      await this.restoreSessionsFromDatabase();
      return;
    }

    // Fallback: restore from filesystem
    await this.restoreSessionsFromFilesystem();
  }

  // Restore sessions from PostgreSQL database
  private async restoreSessionsFromDatabase(): Promise<void> {
    if (!this.pg) return;

    try {
      // Get all connected sessions for this IP node
      const result = await this.pg.query(
        `SELECT session_id, session_archive, webhook_url
         FROM elo_t_channel_accounts
         WHERE ip_node_id = $1
           AND session_status IN ('connected', 'disconnected')
           AND session_archive IS NOT NULL
           AND channel_id = 2`,  // WhatsApp channel
        [this.ipNodeId]
      );

      logger.info(`Found ${result.rows.length} sessions in PostgreSQL to restore`);

      // If no sessions with archives, fallback to filesystem
      if (result.rows.length === 0) {
        logger.info('No sessions with archives in PostgreSQL, falling back to filesystem');
        await this.restoreSessionsFromFilesystem();
        return;
      }

      for (const row of result.rows) {
        const sessionId = row.session_id;
        const archive = row.session_archive;
        const webhookUrl = row.webhook_url || this.defaultWebhookUrl;

        try {
          // Restore session files from archive
          const restored = await this.restoreSessionArchive(sessionId, archive);
          if (!restored) {
            logger.warn(`Failed to restore archive for ${sessionId}, skipping`);
            continue;
          }

          // Create and connect Baileys client
          await this.startRestoredSession(sessionId, webhookUrl);
          logger.info(`Session ${sessionId} restored from PostgreSQL`);
        } catch (err: any) {
          logger.error(`Failed to restore session ${sessionId}:`, err.message);
        }
      }
    } catch (err: any) {
      logger.error('Failed to query PostgreSQL for sessions:', err.message);
      // Fallback to filesystem
      await this.restoreSessionsFromFilesystem();
    }
  }

  // Restore sessions from filesystem (fallback)
  private async restoreSessionsFromFilesystem(): Promise<void> {
    if (!fs.existsSync(this.sessionsDir)) return;

    const sessionDirs = fs.readdirSync(this.sessionsDir, { withFileTypes: true })
      .filter(d => d.isDirectory())
      .map(d => d.name);

    logger.info(`Found ${sessionDirs.length} sessions on disk to restore`);

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

        await this.startRestoredSession(sessionId, webhookUrl);
        logger.info(`Session ${sessionId} restored from disk`);
      } catch (err: any) {
        logger.error(`Failed to restore session ${sessionId}:`, err.message);
      }
    }
  }

  // Start a restored session with Baileys client
  private async startRestoredSession(sessionId: string, webhookUrl?: string): Promise<void> {
    const hash = this.generateHash(sessionId);

    const client = new BaileysClient({
      sessionId,
      sessionsDir: this.sessionsDir,
      webhookUrl,
      proxyUrl: this.defaultProxyUrl,
      onConnected: async (info) => {
        logger.info(`Restored session ${sessionId} connected`);
        await this.saveSessionToRedis(sessionId, info);
        // Update archive after restore connection
        setTimeout(async () => {
          await this.backupSessionToDatabase(sessionId);
        }, 5000);
      },
      onDisconnected: async (reason) => {
        if (reason === 'logged_out') {
          await this.clearSessionArchive(sessionId);
          await this.deleteSession(sessionId);
        }
      },
      onMessage: async (message) => {
        logger.info(`Message received in restored session ${sessionId} from ${message.from}`);
      },
      onCall: async (call) => {
        logger.info(`Call received in restored session ${sessionId} from ${call.from}`);
      },
    });

    this.sessions.set(sessionId, client);
    this.sessionsByHash.set(hash, sessionId);

    await client.connect();
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

    // Stop cleanup interval
    if (this.cleanupInterval) {
      clearInterval(this.cleanupInterval);
      this.cleanupInterval = null;
      logger.info('QR session cleanup interval stopped');
    }

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
    if (this.pg) {
      await this.pg.end();
      logger.info('PostgreSQL connection closed');
    }
  }
}
