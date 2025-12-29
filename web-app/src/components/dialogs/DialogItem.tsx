import { Link } from 'react-router-dom';
import { Mic } from 'lucide-react';
import type { Dialog } from '../../types';
import { ChannelBadge } from '../ui';
import { formatMessageTime, truncateText } from '../../utils';

interface DialogItemProps {
  dialog: Dialog;
}

export const DialogItem = ({ dialog }: DialogItemProps) => {
  const hasUnread = dialog.unread_count > 0;

  return (
    <Link
      to={`/dialogs/${dialog.id}`}
      className="flex items-center gap-3 p-4 hover:bg-gray-50 border-b border-gray-100 transition-colors"
    >
      <ChannelBadge channel={dialog.channel} />

      <div className="flex-1 min-w-0">
        <div className="flex items-center justify-between gap-2">
          <span
            className={`font-medium truncate ${
              hasUnread ? 'text-gray-900' : 'text-gray-700'
            }`}
          >
            {dialog.client_name || 'Без имени'}
          </span>
          <span className="text-sm text-gray-400 flex-shrink-0">
            {formatMessageTime(dialog.last_message_time)}
          </span>
        </div>

        <div className="flex items-center justify-between gap-2 mt-1">
          <span className="text-sm text-gray-500 truncate">
            {dialog.last_message_is_voice ? (
              <span className="flex items-center gap-1">
                <Mic size={14} />
                Голосовое сообщение
              </span>
            ) : (
              truncateText(dialog.last_message_text || '', 50)
            )}
          </span>

          {hasUnread && (
            <span className="bg-red-500 text-white text-xs px-2 py-0.5 rounded-full flex-shrink-0">
              {dialog.unread_count}
            </span>
          )}
        </div>
      </div>
    </Link>
  );
};
