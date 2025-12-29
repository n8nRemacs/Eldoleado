import { apiClient } from './client';
import type { ChannelType } from '../types';

export type ChannelStatusType = 'connected' | 'not_configured' | 'error' | 'checking' | 'pending';

export interface ChannelInfo {
  id: string;
  type: ChannelType;
  channel_name: string;
  status: ChannelStatusType;
  name?: string;
  account_id?: string;
  is_official?: boolean;
  ip_address?: string;
  error?: string;
}

export interface ChannelsStatusResponse {
  success: boolean;
  channels: ChannelInfo[];
}

// Available channel types for adding new connections
export interface AvailableChannel {
  type: ChannelType;
  name: string;
  description: string;
  icon: string;
  setupType: 'qr' | 'token' | 'oauth' | 'credentials';
}

export const AVAILABLE_CHANNELS: AvailableChannel[] = [
  {
    type: 'whatsapp',
    name: 'WhatsApp',
    description: 'Подключение через QR-код',
    icon: '💬',
    setupType: 'qr',
  },
  {
    type: 'telegram_bot',
    name: 'Telegram Bot',
    description: 'Подключение через токен бота',
    icon: '🤖',
    setupType: 'token',
  },
  {
    type: 'telegram_user',
    name: 'Telegram User',
    description: 'Подключение через код авторизации',
    icon: '👤',
    setupType: 'credentials',
  },
  {
    type: 'avito_reverse',
    name: 'Avito Eldoleado',
    description: 'Авторизация через браузер',
    icon: '🛒',
    setupType: 'credentials',
  },
  {
    type: 'avito_official',
    name: 'Avito Official',
    description: 'Подключение через OAuth API',
    icon: '🏪',
    setupType: 'oauth',
  },
  {
    type: 'vk',
    name: 'VKontakte',
    description: 'Подключение сообщества',
    icon: '💙',
    setupType: 'token',
  },
  {
    type: 'max',
    name: 'MAX User',
    description: 'Подключение через SMS-код',
    icon: '💼',
    setupType: 'credentials',
  },
];

// Group channels by type for display
export function groupChannelsByType(channels: ChannelInfo[]): Map<ChannelType, ChannelInfo[]> {
  const grouped = new Map<ChannelType, ChannelInfo[]>();

  for (const channel of channels) {
    const type = channel.type;
    if (!grouped.has(type)) {
      grouped.set(type, []);
    }
    grouped.get(type)!.push(channel);
  }

  return grouped;
}

export const channelsApi = {
  // Get all connected channels status
  getAllChannelsStatus: async (): Promise<ChannelInfo[]> => {
    try {
      const response = await apiClient.get<ChannelsStatusResponse>('/v1/channels/status', {
        timeout: 15000,
      });

      if (response.data.success && response.data.channels) {
        return response.data.channels;
      }

      return [];
    } catch (error) {
      console.error('Failed to fetch channels status:', error);
      return [];
    }
  },

  // Get available channel types
  getAvailableChannels: (): AvailableChannel[] => {
    return AVAILABLE_CHANNELS;
  },
};
