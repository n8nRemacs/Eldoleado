import { apiClient } from './client';
import type { ChannelType } from '../types';

// MCP server endpoints (via n8n proxy to avoid CORS)
const MCP_ENDPOINTS = {
  whatsapp: '/v1/channels/whatsapp',
  telegram_bot: '/v1/channels/telegram-bot',
  telegram_user: '/v1/channels/telegram-user',
  max: '/v1/channels/max-user',
};

// ==================== WhatsApp ====================

export interface WhatsAppSession {
  sessionId: string;
  hash: string;
  status: 'connecting' | 'qr' | 'connected' | 'disconnected';
  qr?: string;
  qrImage?: string;
  phone?: string;
  name?: string;
}

export interface CreateWhatsAppSessionRequest {
  sessionId?: string;
  accountName: string;
  webhookUrl?: string;
}

export const whatsappApi = {
  // Create new WhatsApp session
  createSession: async (request: CreateWhatsAppSessionRequest): Promise<WhatsAppSession> => {
    const response = await apiClient.post<{ success: boolean; data: WhatsAppSession }>(
      `${MCP_ENDPOINTS.whatsapp}/sessions`,
      request
    );
    return response.data.data;
  },

  // Get session status and QR code
  getSessionStatus: async (sessionId: string): Promise<WhatsAppSession> => {
    const response = await apiClient.get<{ success: boolean; data: WhatsAppSession }>(
      `${MCP_ENDPOINTS.whatsapp}/sessions/${sessionId}/qr`
    );
    return response.data.data;
  },

  // Delete session
  deleteSession: async (sessionId: string): Promise<void> => {
    await apiClient.delete(`${MCP_ENDPOINTS.whatsapp}/sessions/${sessionId}`);
  },
};

// ==================== Telegram Bot ====================

export interface TelegramBot {
  id: string;
  botToken: string;
  botUsername: string;
  botName: string;
  status: 'active' | 'error' | 'pending';
}

export interface RegisterTelegramBotRequest {
  botToken: string;
  accountName?: string;
}

export const telegramBotApi = {
  // Register new Telegram bot
  register: async (request: RegisterTelegramBotRequest): Promise<TelegramBot> => {
    const response = await apiClient.post<{ success: boolean; data: TelegramBot }>(
      `${MCP_ENDPOINTS.telegram_bot}/register`,
      request
    );
    return response.data.data;
  },

  // Validate bot token
  validateToken: async (botToken: string): Promise<{ valid: boolean; botInfo?: any }> => {
    const response = await apiClient.post<{ success: boolean; valid: boolean; botInfo?: any }>(
      `${MCP_ENDPOINTS.telegram_bot}/validate`,
      { botToken }
    );
    return { valid: response.data.valid, botInfo: response.data.botInfo };
  },

  // Remove bot
  remove: async (botId: string): Promise<void> => {
    await apiClient.delete(`${MCP_ENDPOINTS.telegram_bot}/${botId}`);
  },
};

// ==================== Telegram User ====================

export interface TelegramUserSession {
  sessionId: string;
  phone: string;
  status: 'pending_code' | 'pending_password' | 'connected' | 'error';
  firstName?: string;
  lastName?: string;
  username?: string;
}

export interface RequestTelegramCodeRequest {
  phone: string;
  accountName?: string;
}

export interface VerifyTelegramCodeRequest {
  sessionId: string;
  code: string;
  password?: string;
}

export const telegramUserApi = {
  // Request verification code
  requestCode: async (request: RequestTelegramCodeRequest): Promise<TelegramUserSession> => {
    const response = await apiClient.post<{ success: boolean; data: TelegramUserSession }>(
      `${MCP_ENDPOINTS.telegram_user}/request-code`,
      request
    );
    return response.data.data;
  },

  // Verify code
  verifyCode: async (request: VerifyTelegramCodeRequest): Promise<TelegramUserSession> => {
    const response = await apiClient.post<{ success: boolean; data: TelegramUserSession }>(
      `${MCP_ENDPOINTS.telegram_user}/verify-code`,
      request
    );
    return response.data.data;
  },

  // Get session status
  getSession: async (sessionId: string): Promise<TelegramUserSession> => {
    const response = await apiClient.get<{ success: boolean; data: TelegramUserSession }>(
      `${MCP_ENDPOINTS.telegram_user}/sessions/${sessionId}`
    );
    return response.data.data;
  },

  // Remove session
  remove: async (sessionId: string): Promise<void> => {
    await apiClient.delete(`${MCP_ENDPOINTS.telegram_user}/sessions/${sessionId}`);
  },
};

// ==================== MAX User ====================

export interface MaxUserSession {
  session_id: string;
  phone: string;
  status: 'pending_code' | 'pending_2fa' | 'connected' | 'error';
  hash: string;
  user_id?: number;
  user_name?: string;
}

export interface MaxUserCreateRequest {
  phone: string;
  webhook_url?: string;
  tenant_id?: number;
}

export interface MaxUserVerifyRequest {
  code: string;
  password_2fa?: string;
}

export const maxUserApi = {
  // Create session and request SMS code
  createSession: async (request: MaxUserCreateRequest): Promise<MaxUserSession> => {
    const response = await apiClient.post<{ success: boolean; data: MaxUserSession }>(
      `${MCP_ENDPOINTS.max}/sessions/create`,
      request
    );
    return response.data.data;
  },

  // Verify SMS code
  verifyCode: async (sessionId: string, request: MaxUserVerifyRequest): Promise<MaxUserSession> => {
    const response = await apiClient.post<{ success: boolean; data: MaxUserSession }>(
      `${MCP_ENDPOINTS.max}/sessions/${sessionId}/verify`,
      request
    );
    return response.data.data;
  },

  // Get session status
  getSession: async (sessionId: string): Promise<MaxUserSession> => {
    const response = await apiClient.get<{ success: boolean; data: MaxUserSession }>(
      `${MCP_ENDPOINTS.max}/sessions/${sessionId}`
    );
    return response.data.data;
  },

  // Delete session
  remove: async (sessionId: string): Promise<void> => {
    await apiClient.delete(`${MCP_ENDPOINTS.max}/sessions/${sessionId}`);
  },
};
