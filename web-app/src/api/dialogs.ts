import { apiClient } from './client';
import type { DialogsResponse } from '../types';

export const dialogsApi = {
  getDialogs: async (sessionToken: string): Promise<DialogsResponse> => {
    const response = await apiClient.get('/v1/dialogs', {
      params: { session_token: sessionToken },
    });
    return response.data;
  },
};
