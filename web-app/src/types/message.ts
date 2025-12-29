import type { ChannelType } from './dialog';

export interface Message {
  id: string;
  text: string;
  direction: 'in' | 'out';
  sender_type: string;
  sender_name: string;
  timestamp: number;
  media?: MessageMedia;
}

export interface MessageMedia {
  type: 'voice' | 'image' | 'document';
  url?: string;
  data?: string;
}

export interface DialogInfo {
  id: string;
  client_name: string;
  client_phone: string;
  channel: ChannelType;
  chat_id?: string;
}

export interface MessagesResponse {
  success: boolean;
  dialog: DialogInfo;
  messages: Message[];
}

export interface SendMessageResponse {
  success: boolean;
  message?: Message;
  error?: string;
}
