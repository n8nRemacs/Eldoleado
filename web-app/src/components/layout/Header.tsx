import { LogOut, Menu } from 'lucide-react';
import { useAuthStore, useUIStore } from '../../store';

export const Header = () => {
  const { session, logout } = useAuthStore();
  const { toggleSidebar } = useUIStore();

  const handleLogout = () => {
    logout();
  };

  return (
    <header className="h-16 bg-white border-b border-gray-200 flex items-center justify-between px-4 flex-shrink-0">
      <div className="flex items-center gap-4">
        <button
          onClick={toggleSidebar}
          className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
          aria-label="Toggle sidebar"
        >
          <Menu size={24} className="text-gray-600" />
        </button>
        <h1 className="text-xl font-bold text-blue-600">Eldoleado</h1>
      </div>

      <div className="flex items-center gap-4">
        <span className="text-gray-600 text-sm hidden sm:block">
          {session?.name}
        </span>
        <button
          onClick={handleLogout}
          className="p-2 hover:bg-gray-100 rounded-lg transition-colors text-gray-600"
          title="Выйти"
        >
          <LogOut size={20} />
        </button>
      </div>
    </header>
  );
};
