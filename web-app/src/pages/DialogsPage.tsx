import { useState, useMemo } from 'react';
import { useQuery } from '@tanstack/react-query';
import { RefreshCw } from 'lucide-react';
import { dialogsApi } from '../api';
import { useAuthStore } from '../store';
import { DialogList, DialogFilters } from '../components/dialogs';
import type { ChannelType } from '../types';

export const DialogsPage = () => {
  const { session } = useAuthStore();
  const [selectedChannel, setSelectedChannel] = useState<ChannelType | 'all'>('all');
  const [showUnreadOnly, setShowUnreadOnly] = useState(false);

  const {
    data,
    isLoading,
    refetch,
    isFetching,
  } = useQuery({
    queryKey: ['dialogs'],
    queryFn: () => dialogsApi.getDialogs(session!.token),
    refetchInterval: 60000, // Refetch every minute
    staleTime: 30000,
  });

  const filteredDialogs = useMemo(() => {
    if (!data?.dialogs) return [];

    return data.dialogs.filter((dialog) => {
      // Filter by channel
      if (selectedChannel !== 'all' && dialog.channel !== selectedChannel) {
        return false;
      }
      // Filter by unread
      if (showUnreadOnly && dialog.unread_count === 0) {
        return false;
      }
      return true;
    });
  }, [data?.dialogs, selectedChannel, showUnreadOnly]);

  const totalUnread = useMemo(() => {
    return data?.dialogs?.reduce((sum, d) => sum + d.unread_count, 0) || 0;
  }, [data?.dialogs]);

  return (
    <div className="h-full flex flex-col">
      <div className="bg-white border-b border-gray-200 p-4 flex items-center justify-between">
        <div>
          <h1 className="text-xl font-bold text-gray-900">Диалоги</h1>
          {totalUnread > 0 && (
            <p className="text-sm text-gray-500">
              {totalUnread} непрочитанных
            </p>
          )}
        </div>
        <button
          onClick={() => refetch()}
          disabled={isFetching}
          className="p-2 hover:bg-gray-100 rounded-lg transition-colors disabled:opacity-50"
          title="Обновить"
        >
          <RefreshCw
            size={20}
            className={`text-gray-600 ${isFetching ? 'animate-spin' : ''}`}
          />
        </button>
      </div>

      <DialogFilters
        selectedChannel={selectedChannel}
        onChannelChange={setSelectedChannel}
        showUnreadOnly={showUnreadOnly}
        onUnreadChange={setShowUnreadOnly}
      />

      <div className="flex-1 overflow-auto bg-white">
        <DialogList dialogs={filteredDialogs} loading={isLoading} />
      </div>
    </div>
  );
};
