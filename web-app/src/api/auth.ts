import { apiClient } from './client';
import type { LoginRequest, LoginResponse } from '../types';

export const authApi = {
  login: async (params: LoginRequest): Promise<LoginResponse> => {
    const response = await apiClient.post('/v1/auth/login', params);
    return response.data;
  },

  logout: async (sessionToken: string): Promise<{ success: boolean }> => {
    const response = await apiClient.post('/v1/auth/logout', {
      session_token: sessionToken,
    });
    return response.data;
  },
};
