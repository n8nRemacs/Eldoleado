import { NavLink } from 'react-router-dom';
import { MessageSquare, Settings } from 'lucide-react';
import { useUIStore } from '../../store';
import { cn } from '../../utils';

const navItems = [
  { path: '/dialogs', icon: MessageSquare, label: 'Диалоги' },
  { path: '/settings', icon: Settings, label: 'Настройки' },
];

export const Sidebar = () => {
  const { sidebarOpen } = useUIStore();

  if (!sidebarOpen) return null;

  return (
    <aside className="w-64 bg-slate-800 text-white flex-shrink-0 hidden md:block">
      <nav className="p-4 space-y-2">
        {navItems.map(({ path, icon: Icon, label }) => (
          <NavLink
            key={path}
            to={path}
            className={({ isActive }) =>
              cn(
                'flex items-center gap-3 px-4 py-3 rounded-lg transition-colors',
                isActive
                  ? 'bg-blue-600 text-white'
                  : 'text-gray-300 hover:bg-slate-700 hover:text-white'
              )
            }
          >
            <Icon size={20} />
            <span>{label}</span>
          </NavLink>
        ))}
      </nav>
    </aside>
  );
};
