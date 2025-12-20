/**
 * TypeScript types for mcp-whatsapp-baileys
 */

// Session states
export type SessionStatus = 'disconnected' | 'connecting' | 'qr' | 'connected' | 'logged_out';

// Session info stored in Redis
export interface SessionInfo {
  id: string;
  hash: string;
  phone?: string;
  name?: string;
  status: SessionStatus;
  webhookUrl?: string;
  tenantId?: string;
  createdAt: string;
  updatedAt: string;
  lastConnected?: string;
}

// QR Code response
export interface QRCodeResponse {
  sessionId: string;
  qr: string; // base64 image or raw QR string
  expiresIn: number; // seconds
}

// Message types
export type MessageType = 'text' | 'image' | 'video' | 'audio' | 'document' | 'sticker' | 'location' | 'contact' | 'reaction';

// Base message request
export interface BaseMessageRequest {
  sessionId: string;
  to: string; // phone@s.whatsapp.net or group@g.us
  quotedMessageId?: string; // for reply
}

// Text message
export interface TextMessageRequest extends BaseMessageRequest {
  text: string;
}

// Media message (image, video, audio, document, sticker)
export interface MediaMessageRequest extends BaseMessageRequest {
  mediaUrl?: string; // URL to download
  mediaBase64?: string; // or base64 encoded
  mimeType?: string;
  fileName?: string; // for documents
  caption?: string; // for image/video
  ptt?: boolean; // push-to-talk for audio (voice message)
}

// Location message
export interface LocationMessageRequest extends BaseMessageRequest {
  latitude: number;
  longitude: number;
  name?: string;
  address?: string;
}

// Contact message
export interface ContactMessageRequest extends BaseMessageRequest {
  contactName: string;
  contactPhone: string;
  contactOrg?: string;
}

// Reaction message
export interface ReactionMessageRequest extends BaseMessageRequest {
  messageId: string; // message to react to
  emoji: string; // emoji or empty to remove
}

// Incoming message (webhook payload)
export interface IncomingMessage {
  sessionId: string;
  sessionHash: string;
  messageId: string;
  from: string;
  fromName?: string;
  to: string;
  isGroup: boolean;
  groupId?: string;
  groupName?: string;
  timestamp: number;
  type: MessageType;
  text?: string;
  caption?: string;
  mediaUrl?: string;
  mimeType?: string;
  fileName?: string;
  latitude?: number;
  longitude?: number;
  quotedMessageId?: string;
  quotedText?: string;
  mentions?: string[];
}

// Incoming call (webhook payload)
export interface IncomingCall {
  sessionId: string;
  sessionHash: string;
  callId: string;
  from: string;
  fromName?: string;
  timestamp: number;
  isVideo: boolean;
  status: 'ringing' | 'rejected' | 'timeout';
}

// Presence update
export interface PresenceUpdate {
  sessionId: string;
  jid: string;
  presence: 'available' | 'unavailable' | 'composing' | 'recording' | 'paused';
  lastSeen?: number;
}

// Group info
export interface GroupInfo {
  id: string;
  name: string;
  description?: string;
  owner?: string;
  creation?: number;
  participants: GroupParticipant[];
}

export interface GroupParticipant {
  id: string;
  isAdmin: boolean;
  isSuperAdmin: boolean;
}

// API responses
export interface ApiResponse<T = any> {
  success: boolean;
  data?: T;
  error?: string;
  code?: string;
}

export interface SendMessageResponse {
  messageId: string;
  timestamp: number;
  status: 'sent' | 'pending';
}

// Webhook event types
export type WebhookEventType =
  | 'message'
  | 'message.ack'
  | 'call'
  | 'presence'
  | 'group.update'
  | 'connection.update';

export interface WebhookPayload {
  event: WebhookEventType;
  sessionId: string;
  sessionHash: string;
  timestamp: number;
  data: IncomingMessage | IncomingCall | PresenceUpdate | any;
}

// Create session request
export interface CreateSessionRequest {
  sessionId?: string; // optional, will generate if not provided
  webhookUrl?: string;
  tenantId?: string;
  proxyUrl?: string; // socks5://user:pass@host:port
}

// Session with QR
export interface SessionWithQR extends SessionInfo {
  qrCode?: string;
}

// Extended health response for monitoring
export interface HealthResponse {
  status: 'healthy' | 'degraded' | 'unhealthy';
  channel: string;
  version: string;
  uptime: number;
  timestamp: string;

  // Session stats
  sessions: {
    total: number;
    connected: number;
    disconnected: number;
  };

  // Metrics (24h)
  metrics: {
    messagesSent: number;
    messagesReceived: number;
    messagesFailed: number;
    errors: number;
    reconnects: number;
  };

  // Health score
  healthScore: number;
}

// Alert configuration
export interface AlertConfigType {
  telegramBotToken?: string;
  telegramChatId?: string;
  n8nWebhookUrl?: string;
  enabled: boolean;
}
