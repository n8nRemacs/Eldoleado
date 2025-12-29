import axios from 'axios';

const API_BASE = import.meta.env.VITE_API_URL || 'https://n8n.n8nsrv.ru/webhook';

export const apiClient = axios.create({
  baseURL: API_BASE,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 30000,
});

// Add session token to requests
apiClient.interceptors.request.use((config) => {
  const sessionData = localStorage.getItem('auth-storage');
  if (sessionData) {
    try {
      const { state } = JSON.parse(sessionData);
      if (state?.session?.token) {
        config.headers.Authorization = `Bearer ${state.session.token}`;
        config.headers['X-Session-Token'] = state.session.token;
      }
    } catch {
      // Invalid session data
    }
  }
  return config;
});

// Handle 401 errors
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('auth-storage');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);
