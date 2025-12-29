import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export interface Session {
  token: string;
  operatorId: string;
  tenantId: string;
  name: string;
  email?: string;
  allowedChannels?: string[];
}

interface AuthState {
  session: Session | null;
  isAuthenticated: boolean;
  login: (session: Session) => void;
  logout: () => void;
  getToken: () => string | null;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set, get) => ({
      session: null,
      isAuthenticated: false,

      login: (session) => {
        set({ session, isAuthenticated: true });
      },

      logout: () => {
        set({ session: null, isAuthenticated: false });
        localStorage.removeItem('device_id');
      },

      getToken: () => {
        return get().session?.token || null;
      },
    }),
    {
      name: 'auth-storage',
    }
  )
);
