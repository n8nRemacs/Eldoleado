import { ArrowLeft, Phone } from 'lucide-react';
import { Link } from 'react-router-dom';
import { ChannelBadge } from '../ui';
import type { ChannelType } from '../../types';
import { formatPhone } from '../../utils';

interface ChatHeaderProps {
  clientName: string;
  clientPhone: string;
  channel: ChannelType;
}

export const ChatHeader = ({
  clientName,
  clientPhone,
  channel,
}: ChatHeaderProps) => {
  return (
    <div className="h-16 bg-white border-b border-gray-200 flex items-center gap-4 px-4 flex-shrink-0">
      <Link
        to="/dialogs"
        className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
      >
        <ArrowLeft size={20} className="text-gray-600" />
      </Link>

      <ChannelBadge channel={channel} />

      <div className="flex-1 min-w-0">
        <h2 className="font-medium text-gray-900 truncate">
          {clientName || 'Без имени'}
        </h2>
        {clientPhone && (
          <a
            href={`tel:${clientPhone}`}
            className="text-sm text-blue-600 hover:underline"
          >
            {formatPhone(clientPhone)}
          </a>
        )}
      </div>

      {clientPhone && (
        <a
          href={`tel:${clientPhone}`}
          className="p-2 hover:bg-gray-100 rounded-lg transition-colors"
          title="Позвонить"
        >
          <Phone size={20} className="text-gray-600" />
        </a>
      )}
    </div>
  );
};
