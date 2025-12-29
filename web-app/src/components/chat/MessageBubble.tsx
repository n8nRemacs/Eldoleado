import type { Message } from '../../types';
import { formatTime } from '../../utils';
import { cn } from '../../utils';

interface MessageBubbleProps {
  message: Message;
}

export const MessageBubble = ({ message }: MessageBubbleProps) => {
  const isOutgoing = message.direction === 'out';

  return (
    <div
      className={cn('flex mb-3', isOutgoing ? 'justify-end' : 'justify-start')}
    >
      <div
        className={cn(
          'max-w-[75%] rounded-2xl px-4 py-2 shadow-sm',
          isOutgoing
            ? 'bg-message-outgoing text-gray-900 rounded-br-sm'
            : 'bg-message-incoming text-gray-900 rounded-bl-sm'
        )}
      >
        <div className="flex items-center gap-2 mb-1">
          <span className="text-xs font-medium text-gray-600">
            {message.sender_name || (isOutgoing ? 'Оператор' : 'Клиент')}
          </span>
          <span className="text-xs text-gray-400">
            {formatTime(message.timestamp)}
          </span>
        </div>
        <div className="whitespace-pre-wrap break-words text-sm">
          {message.text}
        </div>
      </div>
    </div>
  );
};
