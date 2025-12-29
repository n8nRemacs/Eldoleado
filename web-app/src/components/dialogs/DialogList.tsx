import type { Dialog } from '../../types';
import { DialogItem } from './DialogItem';
import { Spinner } from '../ui';

interface DialogListProps {
  dialogs: Dialog[];
  loading: boolean;
}

export const DialogList = ({ dialogs, loading }: DialogListProps) => {
  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <Spinner size="lg" />
      </div>
    );
  }

  if (dialogs.length === 0) {
    return (
      <div className="text-center py-12 text-gray-500">
        <p className="text-lg">Нет диалогов</p>
        <p className="text-sm mt-2">Новые сообщения появятся здесь</p>
      </div>
    );
  }

  // Sort: unread first, then by time (newest first)
  const sortedDialogs = [...dialogs].sort((a, b) => {
    // Unread dialogs first
    if (a.unread_count > 0 && b.unread_count === 0) return -1;
    if (a.unread_count === 0 && b.unread_count > 0) return 1;
    // Then sort by time
    return b.last_message_time - a.last_message_time;
  });

  return (
    <div className="divide-y divide-gray-100">
      {sortedDialogs.map((dialog) => (
        <DialogItem key={dialog.id} dialog={dialog} />
      ))}
    </div>
  );
};
