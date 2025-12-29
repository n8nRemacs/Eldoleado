import { apiClient } from './client';
import type {
  MessagesResponse,
  SendMessageRequest,
  SendMessageResponse,
  NormalizeRequest,
  NormalizeResponse,
} from '../types';

export const messagesApi = {
  getMessages: async (
    dialogId: string,
    sessionToken: string
  ): Promise<MessagesResponse> => {
    const response = await apiClient.get('/v1/messages', {
      params: {
        dialog_id: dialogId,
        session_token: sessionToken,
      },
    });
    return response.data;
  },

  sendMessage: async (params: SendMessageRequest): Promise<SendMessageResponse> => {
    const response = await apiClient.post('/v1/messages/send', params);
    return response.data;
  },

  normalize: async (params: NormalizeRequest): Promise<NormalizeResponse> => {
    const response = await apiClient.post('/v1/normalize', params);
    return response.data;
  },
};
