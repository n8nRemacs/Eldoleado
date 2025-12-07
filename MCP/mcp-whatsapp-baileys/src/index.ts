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
} from './types';

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
  defaultWebhookUrl: process.env.DEFAULT_WEBHOOK_URL,
  apiKey: process.env.API_KEY,
};

// Initialize session manager
const sessionManager = new SessionManager({
  sessionsDir: config.sessionsDir,
  redisUrl: config.redisUrl,
  defaultWebhookUrl: config.defaultWebhookUrl,
});

// Express app
const app = express();
app.use(express.json({ limit: '50mb' }));
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

app.get('/health', (req, res) => {
  res.json({
    status: 'ok',
    service: 'mcp-whatsapp-baileys',
    version: '1.0.0',
    sessions: sessionManager.listSessions().length,
    timestamp: new Date().toISOString(),
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
