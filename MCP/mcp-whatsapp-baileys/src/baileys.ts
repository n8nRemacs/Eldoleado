/**
 * Baileys WhatsApp client wrapper
 */

import makeWASocket, {
  DisconnectReason,
  useMultiFileAuthState,
  WASocket,
  proto,
  downloadMediaMessage,
  getContentType,
  generateWAMessageFromContent,
  prepareWAMessageMedia,
  WAMessage,
  BaileysEventMap,
  MessageUpsertType,
} from '@whiskeysockets/baileys';
import { Boom } from '@hapi/boom';
import * as fs from 'fs';
import * as path from 'path';
import axios from 'axios';
import pino from 'pino';
import * as QRCode from 'qrcode';
import * as mime from 'mime-types';

import {
  SessionStatus,
  SessionInfo,
  IncomingMessage,
  IncomingCall,
  WebhookPayload,
  MessageType,
  TextMessageRequest,
  MediaMessageRequest,
  LocationMessageRequest,
  ContactMessageRequest,
  ReactionMessageRequest,
  SendMessageResponse,
  GroupInfo,
} from './types';

const logger = pino({ level: 'info' });

// Format phone to WhatsApp JID
export function formatJid(phone: string): string {
  // Remove all non-digits
  let cleaned = phone.replace(/\D/g, '');

  // If already a JID, return as is
  if (phone.includes('@')) return phone;

  // Add @s.whatsapp.net for personal chats
  return `${cleaned}@s.whatsapp.net`;
}

// Format group ID to JID
export function formatGroupJid(groupId: string): string {
  if (groupId.includes('@')) return groupId;
  return `${groupId}@g.us`;
}

// Extract phone from JID
export function extractPhone(jid: string): string {
  return jid.split('@')[0];
}

export interface BaileysClientOptions {
  sessionId: string;
  sessionsDir: string;
  webhookUrl?: string;
  onQR?: (qr: string, qrImage: string) => void;
  onConnected?: (info: SessionInfo) => void;
  onDisconnected?: (reason: string) => void;
  onMessage?: (message: IncomingMessage) => void;
  onCall?: (call: IncomingCall) => void;
}

export class BaileysClient {
  private socket: WASocket | null = null;
  private sessionId: string;
  private sessionsDir: string;
  private webhookUrl?: string;
  private status: SessionStatus = 'disconnected';
  private qrCode: string = '';
  private qrImage: string = '';
  private phoneNumber?: string;
  private pushName?: string;
  private reconnectAttempts: number = 0;
  private maxReconnectAttempts: number = 5;

  // Callbacks
  private onQR?: (qr: string, qrImage: string) => void;
  private onConnected?: (info: SessionInfo) => void;
  private onDisconnected?: (reason: string) => void;
  private onMessage?: (message: IncomingMessage) => void;
  private onCall?: (call: IncomingCall) => void;

  constructor(options: BaileysClientOptions) {
    this.sessionId = options.sessionId;
    this.sessionsDir = options.sessionsDir;
    this.webhookUrl = options.webhookUrl;
    this.onQR = options.onQR;
    this.onConnected = options.onConnected;
    this.onDisconnected = options.onDisconnected;
    this.onMessage = options.onMessage;
    this.onCall = options.onCall;
  }

  get sessionPath(): string {
    return path.join(this.sessionsDir, this.sessionId);
  }

  getStatus(): SessionStatus {
    return this.status;
  }

  getQRCode(): string {
    return this.qrCode;
  }

  getQRImage(): string {
    return this.qrImage;
  }

  getSessionInfo(): SessionInfo {
    return {
      id: this.sessionId,
      hash: this.generateHash(),
      phone: this.phoneNumber,
      name: this.pushName,
      status: this.status,
      webhookUrl: this.webhookUrl,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    };
  }

  private generateHash(): string {
    const crypto = require('crypto');
    return crypto.createHash('sha256').update(this.sessionId).digest('hex').substring(0, 16);
  }

  async connect(): Promise<void> {
    // Create session directory if not exists
    if (!fs.existsSync(this.sessionPath)) {
      fs.mkdirSync(this.sessionPath, { recursive: true });
    }

    const { state, saveCreds } = await useMultiFileAuthState(this.sessionPath);

    this.socket = makeWASocket({
      auth: state,
      printQRInTerminal: false,
      logger: pino({ level: 'silent' }),
      browser: ['Eldoleado', 'Chrome', '120.0.0'],
      connectTimeoutMs: 60000,
      qrTimeout: 60000,
    });

    // Save credentials on update
    this.socket.ev.on('creds.update', saveCreds);

    // Connection updates
    this.socket.ev.on('connection.update', async (update) => {
      const { connection, lastDisconnect, qr } = update;

      if (qr) {
        this.status = 'qr';
        this.qrCode = qr;
        // Generate QR code image
        try {
          this.qrImage = await QRCode.toDataURL(qr);
        } catch (err) {
          logger.error('Failed to generate QR image:', err);
        }
        this.onQR?.(qr, this.qrImage);
      }

      if (connection === 'close') {
        const statusCode = (lastDisconnect?.error as Boom)?.output?.statusCode;
        const reason = DisconnectReason[statusCode] || 'Unknown';

        logger.info(`Connection closed: ${reason} (${statusCode})`);

        if (statusCode === DisconnectReason.loggedOut) {
          this.status = 'logged_out';
          // Clean up session files
          await this.deleteSession();
          this.onDisconnected?.('logged_out');
        } else if (statusCode !== DisconnectReason.loggedOut && this.reconnectAttempts < this.maxReconnectAttempts) {
          this.status = 'connecting';
          this.reconnectAttempts++;
          logger.info(`Reconnecting... attempt ${this.reconnectAttempts}/${this.maxReconnectAttempts}`);
          setTimeout(() => this.connect(), 3000);
        } else {
          this.status = 'disconnected';
          this.onDisconnected?.(reason);
        }
      }

      if (connection === 'connecting') {
        this.status = 'connecting';
      }

      if (connection === 'open') {
        this.status = 'connected';
        this.reconnectAttempts = 0;
        this.qrCode = '';
        this.qrImage = '';

        // Get user info
        const user = this.socket?.user;
        if (user) {
          this.phoneNumber = extractPhone(user.id);
          this.pushName = user.name;
        }

        logger.info(`Connected as ${this.pushName} (${this.phoneNumber})`);
        this.onConnected?.(this.getSessionInfo());
      }
    });

    // Incoming messages
    this.socket.ev.on('messages.upsert', async ({ messages, type }) => {
      if (type !== 'notify') return;

      for (const msg of messages) {
        if (msg.key.fromMe) continue; // Skip own messages

        const parsed = await this.parseMessage(msg);
        if (parsed) {
          this.onMessage?.(parsed);
          await this.sendWebhook('message', parsed);
        }
      }
    });

    // Incoming calls
    this.socket.ev.on('call', async (calls) => {
      for (const call of calls) {
        const callData: IncomingCall = {
          sessionId: this.sessionId,
          sessionHash: this.generateHash(),
          callId: call.id,
          from: extractPhone(call.from),
          timestamp: Date.now(),
          isVideo: call.isVideo,
          status: call.status === 'ringing' ? 'ringing' :
                  call.status === 'timeout' ? 'timeout' : 'rejected',
        };

        logger.info(`Incoming ${call.isVideo ? 'video' : 'voice'} call from ${callData.from}`);

        this.onCall?.(callData);
        await this.sendWebhook('call', callData);

        // Auto-reject calls (optional - can be configured)
        // await this.rejectCall(call.id, call.from);
      }
    });

    // Presence updates
    this.socket.ev.on('presence.update', async (presence) => {
      await this.sendWebhook('presence', {
        sessionId: this.sessionId,
        jid: presence.id,
        presences: presence.presences,
      });
    });
  }

  private async parseMessage(msg: WAMessage): Promise<IncomingMessage | null> {
    try {
      const key = msg.key;
      const message = msg.message;
      if (!message) return null;

      const isGroup = key.remoteJid?.endsWith('@g.us') || false;
      const from = key.participant || key.remoteJid || '';

      const contentType = getContentType(message);
      let type: MessageType = 'text';
      let text: string | undefined;
      let caption: string | undefined;
      let mediaUrl: string | undefined;
      let mimeType: string | undefined;
      let fileName: string | undefined;
      let latitude: number | undefined;
      let longitude: number | undefined;

      switch (contentType) {
        case 'conversation':
          type = 'text';
          text = message.conversation || '';
          break;

        case 'extendedTextMessage':
          type = 'text';
          text = message.extendedTextMessage?.text || '';
          break;

        case 'imageMessage':
          type = 'image';
          caption = message.imageMessage?.caption;
          mimeType = message.imageMessage?.mimetype;
          break;

        case 'videoMessage':
          type = 'video';
          caption = message.videoMessage?.caption;
          mimeType = message.videoMessage?.mimetype;
          break;

        case 'audioMessage':
          type = 'audio';
          mimeType = message.audioMessage?.mimetype;
          break;

        case 'documentMessage':
          type = 'document';
          fileName = message.documentMessage?.fileName || undefined;
          mimeType = message.documentMessage?.mimetype;
          break;

        case 'stickerMessage':
          type = 'sticker';
          mimeType = message.stickerMessage?.mimetype;
          break;

        case 'locationMessage':
          type = 'location';
          latitude = message.locationMessage?.degreesLatitude;
          longitude = message.locationMessage?.degreesLongitude;
          break;

        case 'contactMessage':
          type = 'contact';
          text = message.contactMessage?.vcard;
          break;

        case 'reactionMessage':
          type = 'reaction';
          text = message.reactionMessage?.text || '';
          break;

        default:
          logger.info(`Unknown message type: ${contentType}`);
          return null;
      }

      // Get quoted message info
      const quotedMsg = message.extendedTextMessage?.contextInfo?.quotedMessage;
      const quotedMessageId = message.extendedTextMessage?.contextInfo?.stanzaId;
      const quotedText = quotedMsg?.conversation ||
                        quotedMsg?.extendedTextMessage?.text;

      // Get mentions
      const mentions = message.extendedTextMessage?.contextInfo?.mentionedJid?.map(extractPhone);

      return {
        sessionId: this.sessionId,
        sessionHash: this.generateHash(),
        messageId: key.id || '',
        from: extractPhone(from),
        fromName: msg.pushName,
        to: extractPhone(key.remoteJid || ''),
        isGroup,
        groupId: isGroup ? key.remoteJid || undefined : undefined,
        timestamp: (msg.messageTimestamp as number) * 1000,
        type,
        text,
        caption,
        mediaUrl,
        mimeType,
        fileName,
        latitude,
        longitude,
        quotedMessageId,
        quotedText,
        mentions,
      };
    } catch (err) {
      logger.error('Failed to parse message:', err);
      return null;
    }
  }

  private async sendWebhook(event: string, data: any): Promise<void> {
    if (!this.webhookUrl) return;

    const payload: WebhookPayload = {
      event: event as any,
      sessionId: this.sessionId,
      sessionHash: this.generateHash(),
      timestamp: Date.now(),
      data,
    };

    try {
      await axios.post(this.webhookUrl, payload, {
        timeout: 10000,
        headers: { 'Content-Type': 'application/json' },
      });
      logger.info(`Webhook sent: ${event}`);
    } catch (err: any) {
      logger.error(`Webhook failed: ${err.message}`);
    }
  }

  // Send text message
  async sendText(request: TextMessageRequest): Promise<SendMessageResponse> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatJid(request.to);
    const result = await this.socket.sendMessage(jid, {
      text: request.text,
    }, {
      quoted: request.quotedMessageId ? { key: { id: request.quotedMessageId } } as any : undefined,
    });

    return {
      messageId: result?.key.id || '',
      timestamp: Date.now(),
      status: 'sent',
    };
  }

  // Send image
  async sendImage(request: MediaMessageRequest): Promise<SendMessageResponse> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatJid(request.to);
    const media = await this.getMediaBuffer(request);

    const result = await this.socket.sendMessage(jid, {
      image: media.buffer,
      caption: request.caption,
      mimetype: request.mimeType || 'image/jpeg',
    });

    return {
      messageId: result?.key.id || '',
      timestamp: Date.now(),
      status: 'sent',
    };
  }

  // Send video
  async sendVideo(request: MediaMessageRequest): Promise<SendMessageResponse> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatJid(request.to);
    const media = await this.getMediaBuffer(request);

    const result = await this.socket.sendMessage(jid, {
      video: media.buffer,
      caption: request.caption,
      mimetype: request.mimeType || 'video/mp4',
    });

    return {
      messageId: result?.key.id || '',
      timestamp: Date.now(),
      status: 'sent',
    };
  }

  // Send audio (voice message)
  async sendAudio(request: MediaMessageRequest): Promise<SendMessageResponse> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatJid(request.to);
    const media = await this.getMediaBuffer(request);

    const result = await this.socket.sendMessage(jid, {
      audio: media.buffer,
      mimetype: request.mimeType || 'audio/ogg; codecs=opus',
      ptt: request.ptt !== false, // Default to voice message
    });

    return {
      messageId: result?.key.id || '',
      timestamp: Date.now(),
      status: 'sent',
    };
  }

  // Send document
  async sendDocument(request: MediaMessageRequest): Promise<SendMessageResponse> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatJid(request.to);
    const media = await this.getMediaBuffer(request);

    const result = await this.socket.sendMessage(jid, {
      document: media.buffer,
      fileName: request.fileName || 'document',
      mimetype: request.mimeType || 'application/octet-stream',
      caption: request.caption,
    });

    return {
      messageId: result?.key.id || '',
      timestamp: Date.now(),
      status: 'sent',
    };
  }

  // Send sticker
  async sendSticker(request: MediaMessageRequest): Promise<SendMessageResponse> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatJid(request.to);
    const media = await this.getMediaBuffer(request);

    const result = await this.socket.sendMessage(jid, {
      sticker: media.buffer,
    });

    return {
      messageId: result?.key.id || '',
      timestamp: Date.now(),
      status: 'sent',
    };
  }

  // Send location
  async sendLocation(request: LocationMessageRequest): Promise<SendMessageResponse> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatJid(request.to);

    const result = await this.socket.sendMessage(jid, {
      location: {
        degreesLatitude: request.latitude,
        degreesLongitude: request.longitude,
        name: request.name,
        address: request.address,
      },
    });

    return {
      messageId: result?.key.id || '',
      timestamp: Date.now(),
      status: 'sent',
    };
  }

  // Send contact
  async sendContact(request: ContactMessageRequest): Promise<SendMessageResponse> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatJid(request.to);

    const vcard = `BEGIN:VCARD
VERSION:3.0
FN:${request.contactName}
TEL;type=CELL;type=VOICE;waid=${request.contactPhone.replace(/\D/g, '')}:${request.contactPhone}
${request.contactOrg ? `ORG:${request.contactOrg}` : ''}
END:VCARD`;

    const result = await this.socket.sendMessage(jid, {
      contacts: {
        displayName: request.contactName,
        contacts: [{ vcard }],
      },
    });

    return {
      messageId: result?.key.id || '',
      timestamp: Date.now(),
      status: 'sent',
    };
  }

  // Send reaction
  async sendReaction(request: ReactionMessageRequest): Promise<SendMessageResponse> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatJid(request.to);

    const result = await this.socket.sendMessage(jid, {
      react: {
        text: request.emoji,
        key: {
          remoteJid: jid,
          id: request.messageId,
        },
      },
    });

    return {
      messageId: result?.key.id || '',
      timestamp: Date.now(),
      status: 'sent',
    };
  }

  // Mark as read
  async markAsRead(jid: string, messageIds: string[]): Promise<void> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    await this.socket.readMessages([{
      remoteJid: formatJid(jid),
      id: messageIds[0],
    }]);
  }

  // Send typing indicator
  async sendTyping(jid: string, composing: boolean = true): Promise<void> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    await this.socket.sendPresenceUpdate(composing ? 'composing' : 'paused', formatJid(jid));
  }

  // Reject incoming call
  async rejectCall(callId: string, callFrom: string): Promise<void> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    await this.socket.rejectCall(callId, callFrom);
    logger.info(`Rejected call ${callId} from ${callFrom}`);
  }

  // Get group info
  async getGroupInfo(groupId: string): Promise<GroupInfo> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const jid = formatGroupJid(groupId);
    const metadata = await this.socket.groupMetadata(jid);

    return {
      id: metadata.id,
      name: metadata.subject,
      description: metadata.desc,
      owner: metadata.owner ? extractPhone(metadata.owner) : undefined,
      creation: metadata.creation,
      participants: metadata.participants.map(p => ({
        id: extractPhone(p.id),
        isAdmin: p.admin === 'admin' || p.admin === 'superadmin',
        isSuperAdmin: p.admin === 'superadmin',
      })),
    };
  }

  // Check if number exists on WhatsApp
  async isOnWhatsApp(phone: string): Promise<boolean> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    const [result] = await this.socket.onWhatsApp(phone);
    return result?.exists || false;
  }

  // Get profile picture
  async getProfilePicture(jid: string): Promise<string | null> {
    if (!this.socket || this.status !== 'connected') {
      throw new Error('Not connected');
    }

    try {
      return await this.socket.profilePictureUrl(formatJid(jid), 'image');
    } catch {
      return null;
    }
  }

  // Helper to get media buffer from URL or base64
  private async getMediaBuffer(request: MediaMessageRequest): Promise<{ buffer: Buffer; mimeType: string }> {
    if (request.mediaBase64) {
      return {
        buffer: Buffer.from(request.mediaBase64, 'base64'),
        mimeType: request.mimeType || 'application/octet-stream',
      };
    }

    if (request.mediaUrl) {
      const response = await axios.get(request.mediaUrl, { responseType: 'arraybuffer' });
      const mimeType = request.mimeType ||
                       response.headers['content-type'] ||
                       mime.lookup(request.mediaUrl) ||
                       'application/octet-stream';
      return {
        buffer: Buffer.from(response.data),
        mimeType,
      };
    }

    throw new Error('No media provided (mediaUrl or mediaBase64 required)');
  }

  // Disconnect
  async disconnect(): Promise<void> {
    if (this.socket) {
      this.socket.end(undefined);
      this.socket = null;
    }
    this.status = 'disconnected';
  }

  // Logout and delete session
  async logout(): Promise<void> {
    if (this.socket) {
      await this.socket.logout();
      this.socket = null;
    }
    await this.deleteSession();
    this.status = 'logged_out';
  }

  // Delete session files
  async deleteSession(): Promise<void> {
    try {
      if (fs.existsSync(this.sessionPath)) {
        fs.rmSync(this.sessionPath, { recursive: true, force: true });
        logger.info(`Session deleted: ${this.sessionId}`);
      }
    } catch (err) {
      logger.error('Failed to delete session:', err);
    }
  }
}
