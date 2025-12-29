export type ChannelType =
  | 'whatsapp'
  | 'telegram_bot'
  | 'telegram_user'
  | 'telegram'  // legacy, maps to telegram_bot
  | 'avito_reverse'
  | 'avito_official'
  | 'avito'  // legacy, maps to avito_reverse
  | 'vk'
  | 'max'
  | 'form'
  | 'phone';

export interface Dialog {
  id: string;
  client_name: string;
  client_phone: string;
  channel: ChannelType;
  chat_id: string;
  last_message_text: string;
  last_message_time: number;
  last_message_is_voice: boolean;
  unread_count: number;
}

export interface DialogsResponse {
  success: boolean;
  dialogs: Dialog[];
}
