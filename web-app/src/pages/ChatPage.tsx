import { useParams } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import toast from 'react-hot-toast';
import { messagesApi } from '../api';
import { useAuthStore } from '../store';
import { ChatHeader, MessageList, MessageInput } from '../components/chat';
import { Spinner } from '../components/ui';

export const ChatPage = () => {
  const { dialogId } = useParams<{ dialogId: string }>();
  const { session } = useAuthStore();
  const queryClient = useQueryClient();

  const { data, isLoading, error } = useQuery({
    queryKey: ['messages', dialogId],
    queryFn: () => messagesApi.getMessages(dialogId!, session!.token),
    refetchInterval: 10000, // Poll every 10 seconds
    staleTime: 5000,
    enabled: !!dialogId && !!session?.token,
  });

  const sendMutation = useMutation({
    mutationFn: (text: string) =>
      messagesApi.sendMessage({
        session_token: session!.token,
        dialog_id: dialogId!,
        text,
      }),
    onSuccess: () => {
      // Invalidate and refetch
      queryClient.invalidateQueries({ queryKey: ['messages', dialogId] });
      queryClient.invalidateQueries({ queryKey: ['dialogs'] });
      toast.success('Сообщение отправлено');
    },
    onError: (err) => {
      console.error('Send error:', err);
      toast.error('Ошибка отправки сообщения');
    },
  });

  const handleNormalize = async (text: string): Promise<string> => {
    try {
      const response = await messagesApi.normalize({
        session_token: session!.token,
        dialog_id: dialogId!,
        text,
      });
      if (response.normalized_text) {
        toast.success('Текст нормализован');
        return response.normalized_text;
      }
      return text;
    } catch (err) {
      console.error('Normalize error:', err);
      toast.error('Ошибка нормализации');
      return text;
    }
  };

  if (isLoading) {
    return (
      <div className="h-full flex items-center justify-center">
        <Spinner size="lg" />
      </div>
    );
  }

  if (error || !data?.dialog) {
    return (
      <div className="h-full flex items-center justify-center text-gray-500">
        <div className="text-center">
          <p className="text-lg">Диалог не найден</p>
          <p className="text-sm mt-2">Попробуйте обновить страницу</p>
        </div>
      </div>
    );
  }

  return (
    <div className="h-full flex flex-col">
      <ChatHeader
        clientName={data.dialog.client_name}
        clientPhone={data.dialog.client_phone}
        channel={data.dialog.channel}
      />

      <MessageList messages={data.messages || []} loading={isLoading} />

      <MessageInput
        onSend={(text) => sendMutation.mutate(text)}
        onNormalize={handleNormalize}
        disabled={sendMutation.isPending}
      />
    </div>
  );
};
