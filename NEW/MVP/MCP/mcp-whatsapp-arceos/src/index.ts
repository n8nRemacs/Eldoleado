/**
 * MCP WhatsApp Baileys - REST API Server
 *
 * Multi-tenant WhatsApp API using Baileys library
 */

import express, { Request, Response, NextFunction } from 'express';
import multer from 'multer';
import * as path from 'path';

import { SessionManager } from './session';
import {
  ApiResponse,
  CreateSessionRequest,
  TextMessageRequest,
  MediaMessageRequest,
  LocationMessageRequest,
  ContactMessageRequest,
  ReactionMessageRequest,
  HealthResponse,
} from './types';
import { initAlertService } from './alerts';
import { getMetricsCollector } from './metrics';

// Simple logger wrapper
const logger = {
  info: (msg: string, ...args: any[]) => console.log(`[INFO] ${msg}`, ...args),
  error: (msg: string, ...args: any[]) => console.error(`[ERROR] ${msg}`, ...args),
  warn: (msg: string, ...args: any[]) => console.warn(`[WARN] ${msg}`, ...args),
};

// Configuration
const config = {
  port: parseInt(process.env.PORT || '8766'),
  host: process.env.HOST || '0.0.0.0',
  sessionsDir: process.env.SESSIONS_DIR || path.join(__dirname, '../sessions'),
  redisUrl: process.env.REDIS_URL,
  databaseUrl: process.env.DATABASE_URL,
  ipNodeId: parseInt(process.env.IP_NODE_ID || '1'),
  defaultWebhookUrl: process.env.DEFAULT_WEBHOOK_URL || process.env.WEBHOOK_URL,
  defaultProxyUrl: process.env.DEFAULT_PROXY_URL,
  apiKey: process.env.API_KEY,
  // Alert settings
  alertTelegramBotToken: process.env.ALERT_TELEGRAM_BOT_TOKEN,
  alertTelegramChatId: process.env.ALERT_TELEGRAM_CHAT_ID,
  alertN8nWebhookUrl: process.env.ALERT_N8N_WEBHOOK_URL,
};

// Initialize alert service
const alertService = initAlertService({
  telegramBotToken: config.alertTelegramBotToken,
  telegramChatId: config.alertTelegramChatId,
  n8nWebhookUrl: config.alertN8nWebhookUrl,
  enabled: !!(config.alertTelegramBotToken || config.alertN8nWebhookUrl),
});

// Initialize metrics collector
const metricsCollector = getMetricsCollector();

// Track server start time for uptime
const serverStartTime = Date.now();

// Initialize session manager
const sessionManager = new SessionManager({
  sessionsDir: config.sessionsDir,
  redisUrl: config.redisUrl,
  databaseUrl: config.databaseUrl,
  ipNodeId: config.ipNodeId,
  defaultWebhookUrl: config.defaultWebhookUrl,
  defaultProxyUrl: config.defaultProxyUrl,
});

// CP1251 to UTF-8 byte mapping for Cyrillic
const cp1251ToUtf8Bytes: { [key: number]: number[] } = {
  0xC0: [0xD0, 0x90], 0xC1: [0xD0, 0x91], 0xC2: [0xD0, 0x92], 0xC3: [0xD0, 0x93],
  0xC4: [0xD0, 0x94], 0xC5: [0xD0, 0x95], 0xC6: [0xD0, 0x96], 0xC7: [0xD0, 0x97],
  0xC8: [0xD0, 0x98], 0xC9: [0xD0, 0x99], 0xCA: [0xD0, 0x9A], 0xCB: [0xD0, 0x9B],
  0xCC: [0xD0, 0x9C], 0xCD: [0xD0, 0x9D], 0xCE: [0xD0, 0x9E], 0xCF: [0xD0, 0x9F],
  0xD0: [0xD0, 0xA0], 0xD1: [0xD0, 0xA1], 0xD2: [0xD0, 0xA2], 0xD3: [0xD0, 0xA3],
  0xD4: [0xD0, 0xA4], 0xD5: [0xD0, 0xA5], 0xD6: [0xD0, 0xA6], 0xD7: [0xD0, 0xA7],
  0xD8: [0xD0, 0xA8], 0xD9: [0xD0, 0xA9], 0xDA: [0xD0, 0xAA], 0xDB: [0xD0, 0xAB],
  0xDC: [0xD0, 0xAC], 0xDD: [0xD0, 0xAD], 0xDE: [0xD0, 0xAE], 0xDF: [0xD0, 0xAF],
  0xE0: [0xD0, 0xB0], 0xE1: [0xD0, 0xB1], 0xE2: [0xD0, 0xB2], 0xE3: [0xD0, 0xB3],
  0xE4: [0xD0, 0xB4], 0xE5: [0xD0, 0xB5], 0xE6: [0xD0, 0xB6], 0xE7: [0xD0, 0xB7],
  0xE8: [0xD0, 0xB8], 0xE9: [0xD0, 0xB9], 0xEA: [0xD0, 0xBA], 0xEB: [0xD0, 0xBB],
  0xEC: [0xD0, 0xBC], 0xED: [0xD0, 0xBD], 0xEE: [0xD0, 0xBE], 0xEF: [0xD0, 0xBF],
  0xF0: [0xD1, 0x80], 0xF1: [0xD1, 0x81], 0xF2: [0xD1, 0x82], 0xF3: [0xD1, 0x83],
  0xF4: [0xD1, 0x84], 0xF5: [0xD1, 0x85], 0xF6: [0xD1, 0x86], 0xF7: [0xD1, 0x87],
  0xF8: [0xD1, 0x88], 0xF9: [0xD1, 0x89], 0xFA: [0xD1, 0x8A], 0xFB: [0xD1, 0x8B],
  0xFC: [0xD1, 0x8C], 0xFD: [0xD1, 0x8D], 0xFE: [0xD1, 0x8E], 0xFF: [0xD1, 0x8F],
  0xA8: [0xD0, 0x81], 0xB8: [0xD1, 0x91], // Ё, ё
};

// Convert CP1251 bytes to UTF-8
function convertCp1251ToUtf8(buffer: Buffer): Buffer {
  const result: number[] = [];
  let hasCp1251 = false;

  for (let i = 0; i < buffer.length; i++) {
    const byte = buffer[i];
    if (cp1251ToUtf8Bytes[byte]) {
      hasCp1251 = true;
      result.push(...cp1251ToUtf8Bytes[byte]);
    } else {
      result.push(byte);
    }
  }

  return hasCp1251 ? Buffer.from(result) : buffer;
}

// Express app
const app = express();

// Custom JSON parser with CP1251 support
app.use((req: Request, res: Response, next: NextFunction) => {
  if (req.headers['content-type']?.includes('application/json')) {
    let data: Buffer[] = [];
    req.on('data', (chunk: Buffer) => data.push(chunk));
    req.on('end', () => {
      try {
        let rawBuffer = Buffer.concat(data);

        // Try parsing as UTF-8 first
        let jsonStr = rawBuffer.toString('utf8');
        try {
          req.body = JSON.parse(jsonStr);
          return next();
        } catch (e) {
          // If UTF-8 parsing fails, try CP1251 conversion
          const converted = convertCp1251ToUtf8(rawBuffer);
          jsonStr = converted.toString('utf8');
          req.body = JSON.parse(jsonStr);
          return next();
        }
      } catch (err: any) {
        return res.status(400).json({ success: false, error: `JSON parse error: ${err.message}` });
      }
    });
  } else {
    next();
  }
});

app.use(express.urlencoded({ extended: true }));

// Multer for file uploads
const upload = multer({
  storage: multer.memoryStorage(),
  limits: { fileSize: 50 * 1024 * 1024 }, // 50MB
});

// API Key middleware (optional)
const authMiddleware = (req: Request, res: Response, next: NextFunction) => {
  if (!config.apiKey) return next();

  const providedKey = req.headers['x-api-key'] || req.query.apiKey;
  if (providedKey !== config.apiKey) {
    return res.status(401).json({ success: false, error: 'Unauthorized' });
  }
  next();
};

// Response helper
function sendResponse<T>(res: Response, data: T, status: number = 200): void {
  const response: ApiResponse<T> = { success: true, data };
  res.status(status).json(response);
}

function sendError(res: Response, error: string, status: number = 400, code?: string): void {
  const response: ApiResponse = { success: false, error, code };
  res.status(status).json(response);
}

// ==================== HEALTH ====================

// Basic health check (backwards compatible)
app.get('/health', (req, res) => {
  const sessions = sessionManager.listSessions();
  const sessionIds = sessions.map(s => s.id);
  const aggregated = metricsCollector.getAggregatedMetrics(sessionIds);

  res.json({
    status: aggregated.status,
    service: 'mcp-whatsapp-baileys',
    version: '1.1.0',
    sessions: sessions.length,
    timestamp: new Date().toISOString(),
  });
});

// Extended health check with metrics
app.get('/health/extended', (req, res) => {
  const sessions = sessionManager.listSessions();
  const sessionIds = sessions.map(s => s.id);
  const aggregated = metricsCollector.getAggregatedMetrics(sessionIds);
  const uptimeSeconds = Math.floor((Date.now() - serverStartTime) / 1000);

  const response: HealthResponse = {
    status: aggregated.status,
    channel: 'whatsapp',
    version: '1.1.0',
    uptime: uptimeSeconds,
    timestamp: new Date().toISOString(),
    sessions: {
      total: aggregated.totalSessions,
      connected: aggregated.connectedSessions,
      disconnected: aggregated.disconnectedSessions,
    },
    metrics: {
      messagesSent: aggregated.totalMessagesSent,
      messagesReceived: aggregated.totalMessagesReceived,
      messagesFailed: aggregated.totalMessagesFailed,
      errors: aggregated.totalErrors,
      reconnects: aggregated.totalReconnects,
    },
    healthScore: aggregated.healthScore,
  };

  res.json(response);
});

// Per-session health check
app.get('/health/sessions/:sessionId', authMiddleware, (req, res) => {
  const { sessionId } = req.params;
  const client = sessionManager.getSession(sessionId);

  if (!client) {
    return sendError(res, 'Session not found', 404);
  }

  const metrics = metricsCollector.getSessionMetrics(sessionId);
  sendResponse(res, {
    sessionId,
    status: client.getStatus(),
    metrics,
  });
});

// ==================== SESSIONS ====================

// List all sessions
app.get('/sessions', authMiddleware, (req, res) => {
  const sessions = sessionManager.listSessions();
  sendResponse(res, { sessions });
});

// Create new session
app.post('/sessions', authMiddleware, async (req, res) => {
  try {
    const request: CreateSessionRequest = req.body;
    const { sessionId, hash } = await sessionManager.createSession(request);

    sendResponse(res, {
      sessionId,
      hash,
      webhookPath: `/webhook/whatsapp/${hash}`,
      message: 'Session created. Scan QR code to connect.',
    }, 201);
  } catch (err: any) {
    sendError(res, err.message, 400);
  }
});

// Get session info
app.get('/sessions/:sessionId', authMiddleware, async (req, res) => {
  const { sessionId } = req.params;
  const info = await sessionManager.getSessionInfo(sessionId);

  if (!info) {
    return sendError(res, 'Session not found', 404);
  }

  sendResponse(res, info);
});

// Get QR code
app.get('/sessions/:sessionId/qr', authMiddleware, (req, res) => {
  const { sessionId } = req.params;
  const client = sessionManager.getSession(sessionId);

  if (!client) {
    return sendError(res, 'Session not found', 404);
  }

  const qrImage = client.getQRImage();
  const qrCode = client.getQRCode();
  const status = client.getStatus();

  if (status === 'connected') {
    return sendResponse(res, { status: 'connected', message: 'Already connected' });
  }

  if (!qrCode) {
    return sendResponse(res, { status, message: 'QR code not available yet' });
  }

  sendResponse(res, {
    status,
    qr: qrCode,
    qrImage, // base64 data URL
  });
});

// Get session status
app.get('/sessions/:sessionId/status', authMiddleware, (req, res) => {
  const { sessionId } = req.params;
  const client = sessionManager.getSession(sessionId);

  if (!client) {
    return sendError(res, 'Session not found', 404);
  }

  sendResponse(res, {
    sessionId,
    status: client.getStatus(),
    info: client.getSessionInfo(),
  });
});

// Request pairing code (alternative to QR)
app.post('/sessions/:sessionId/pair', authMiddleware, async (req, res) => {
  try {
    const { sessionId } = req.params;
    const { phone } = req.body;

    if (!phone) {
      return sendError(res, 'Phone number is required');
    }

    const client = sessionManager.getSession(sessionId);
    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const code = await client.requestPairingCode(phone);
    sendResponse(res, { code, phone });
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Disconnect session
app.post('/sessions/:sessionId/disconnect', authMiddleware, async (req, res) => {
  try {
    const { sessionId } = req.params;
    await sessionManager.disconnectSession(sessionId);
    sendResponse(res, { message: 'Session disconnected' });
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Reconnect session
app.post('/sessions/:sessionId/reconnect', authMiddleware, async (req, res) => {
  try {
    const { sessionId } = req.params;
    await sessionManager.reconnectSession(sessionId);
    sendResponse(res, { message: 'Reconnecting...' });
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Delete session (logout)
app.delete('/sessions/:sessionId', authMiddleware, async (req, res) => {
  try {
    const { sessionId } = req.params;
    await sessionManager.deleteSession(sessionId);
    sendResponse(res, { message: 'Session deleted' });
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// ==================== MESSAGES ====================

// Send text message
app.post('/messages/text', authMiddleware, async (req, res) => {
  try {
    const request: TextMessageRequest = req.body;
    const client = sessionManager.getSession(request.sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const result = await client.sendText(request);
    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Send image
app.post('/messages/image', authMiddleware, async (req, res) => {
  try {
    const request: MediaMessageRequest = req.body;
    const client = sessionManager.getSession(request.sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const result = await client.sendImage(request);
    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Send video
app.post('/messages/video', authMiddleware, async (req, res) => {
  try {
    const request: MediaMessageRequest = req.body;
    const client = sessionManager.getSession(request.sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const result = await client.sendVideo(request);
    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Send audio (voice message)
app.post('/messages/audio', authMiddleware, async (req, res) => {
  try {
    const request: MediaMessageRequest = req.body;
    const client = sessionManager.getSession(request.sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const result = await client.sendAudio(request);
    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Send document
app.post('/messages/document', authMiddleware, async (req, res) => {
  try {
    const request: MediaMessageRequest = req.body;
    const client = sessionManager.getSession(request.sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const result = await client.sendDocument(request);
    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Send sticker
app.post('/messages/sticker', authMiddleware, async (req, res) => {
  try {
    const request: MediaMessageRequest = req.body;
    const client = sessionManager.getSession(request.sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const result = await client.sendSticker(request);
    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Send location
app.post('/messages/location', authMiddleware, async (req, res) => {
  try {
    const request: LocationMessageRequest = req.body;
    const client = sessionManager.getSession(request.sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const result = await client.sendLocation(request);
    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Send contact
app.post('/messages/contact', authMiddleware, async (req, res) => {
  try {
    const request: ContactMessageRequest = req.body;
    const client = sessionManager.getSession(request.sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const result = await client.sendContact(request);
    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Send reaction
app.post('/messages/reaction', authMiddleware, async (req, res) => {
  try {
    const request: ReactionMessageRequest = req.body;
    const client = sessionManager.getSession(request.sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const result = await client.sendReaction(request);
    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Upload and send media (multipart)
app.post('/messages/upload', authMiddleware, upload.single('file'), async (req, res) => {
  try {
    const { sessionId, to, type, caption, fileName } = req.body;
    const file = req.file;

    if (!file) {
      return sendError(res, 'No file uploaded');
    }

    const client = sessionManager.getSession(sessionId);
    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const mediaBase64 = file.buffer.toString('base64');
    const mimeType = file.mimetype;

    const request: MediaMessageRequest = {
      sessionId,
      to,
      mediaBase64,
      mimeType,
      fileName: fileName || file.originalname,
      caption,
    };

    let result;
    switch (type) {
      case 'image':
        result = await client.sendImage(request);
        break;
      case 'video':
        result = await client.sendVideo(request);
        break;
      case 'audio':
        result = await client.sendAudio(request);
        break;
      case 'document':
        result = await client.sendDocument(request);
        break;
      case 'sticker':
        result = await client.sendSticker(request);
        break;
      default:
        return sendError(res, 'Invalid type. Use: image, video, audio, document, sticker');
    }

    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// ==================== ACTIONS ====================

// Mark as read
app.post('/actions/read', authMiddleware, async (req, res) => {
  try {
    const { sessionId, jid, messageIds } = req.body;
    const client = sessionManager.getSession(sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    await client.markAsRead(jid, messageIds);
    sendResponse(res, { message: 'Marked as read' });
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Send typing indicator
app.post('/actions/typing', authMiddleware, async (req, res) => {
  try {
    const { sessionId, jid, composing = true } = req.body;
    const client = sessionManager.getSession(sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    await client.sendTyping(jid, composing);
    sendResponse(res, { message: composing ? 'Typing...' : 'Stopped typing' });
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Reject call
app.post('/actions/reject-call', authMiddleware, async (req, res) => {
  try {
    const { sessionId, callId, callFrom } = req.body;
    const client = sessionManager.getSession(sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    await client.rejectCall(callId, callFrom);
    sendResponse(res, { message: 'Call rejected' });
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Check if number is on WhatsApp
app.post('/actions/check-number', authMiddleware, async (req, res) => {
  try {
    const { sessionId, phone } = req.body;
    const client = sessionManager.getSession(sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const exists = await client.isOnWhatsApp(phone);
    sendResponse(res, { phone, exists });
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// Get profile picture
app.get('/actions/profile-picture', authMiddleware, async (req, res) => {
  try {
    const { sessionId, jid } = req.query as { sessionId: string; jid: string };
    const client = sessionManager.getSession(sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const url = await client.getProfilePicture(jid);
    sendResponse(res, { jid, profilePictureUrl: url });
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// ==================== GROUPS ====================

// Get group info
app.get('/groups/:groupId', authMiddleware, async (req, res) => {
  try {
    const { groupId } = req.params;
    const { sessionId } = req.query as { sessionId: string };
    const client = sessionManager.getSession(sessionId);

    if (!client) {
      return sendError(res, 'Session not found', 404);
    }

    const info = await client.getGroupInfo(groupId);
    sendResponse(res, info);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// ==================== WEBHOOK (for n8n) ====================

// Webhook endpoint for external triggers
app.post('/webhook/whatsapp/:hash', async (req, res) => {
  const { hash } = req.params;
  const sessionId = sessionManager.getSessionIdByHash(hash);

  if (!sessionId) {
    return sendError(res, 'Session not found', 404);
  }

  // This endpoint can be used by n8n to trigger actions
  // The payload should specify the action to perform
  const { action, ...params } = req.body;

  const client = sessionManager.getSession(sessionId);
  if (!client) {
    return sendError(res, 'Session not connected', 400);
  }

  try {
    let result;
    switch (action) {
      case 'send_text':
        result = await client.sendText({ sessionId, ...params });
        break;
      case 'send_image':
        result = await client.sendImage({ sessionId, ...params });
        break;
      case 'send_video':
        result = await client.sendVideo({ sessionId, ...params });
        break;
      case 'send_audio':
        result = await client.sendAudio({ sessionId, ...params });
        break;
      case 'send_document':
        result = await client.sendDocument({ sessionId, ...params });
        break;
      case 'send_location':
        result = await client.sendLocation({ sessionId, ...params });
        break;
      case 'send_contact':
        result = await client.sendContact({ sessionId, ...params });
        break;
      case 'send_reaction':
        result = await client.sendReaction({ sessionId, ...params });
        break;
      case 'reject_call':
        await client.rejectCall(params.callId, params.callFrom);
        result = { message: 'Call rejected' };
        break;
      default:
        return sendError(res, `Unknown action: ${action}`);
    }

    sendResponse(res, result);
  } catch (err: any) {
    sendError(res, err.message);
  }
});

// ==================== STARTUP ====================

async function start() {
  // Restore existing sessions
  await sessionManager.restoreSessions();

  // Start server
  app.listen(config.port, config.host, () => {
    logger.info(`mcp-whatsapp-baileys running on http://${config.host}:${config.port}`);
    logger.info(`Sessions directory: ${config.sessionsDir}`);
    if (config.redisUrl) {
      logger.info('Redis: connected');
    }
    if (config.databaseUrl) {
      logger.info('PostgreSQL: connected (session backup enabled)');
    }
  });
}

// Graceful shutdown
process.on('SIGTERM', async () => {
  logger.info('Shutting down...');
  await sessionManager.shutdown();
  process.exit(0);
});

process.on('SIGINT', async () => {
  logger.info('Shutting down...');
  await sessionManager.shutdown();
  process.exit(0);
});

// Start
start().catch((err) => {
  logger.error('Failed to start:', err);
  process.exit(1);
});
