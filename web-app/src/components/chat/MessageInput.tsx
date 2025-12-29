import { useState, KeyboardEvent, useRef, useEffect } from 'react';
import { Send, Sparkles, X } from 'lucide-react';
import { Button } from '../ui';

interface MessageInputProps {
  onSend: (text: string) => void;
  onNormalize: (text: string) => Promise<string>;
  disabled?: boolean;
}

export const MessageInput = ({
  onSend,
  onNormalize,
  disabled,
}: MessageInputProps) => {
  const [text, setText] = useState('');
  const [normalizing, setNormalizing] = useState(false);
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  // Auto-resize textarea
  useEffect(() => {
    if (textareaRef.current) {
      textareaRef.current.style.height = 'auto';
      textareaRef.current.style.height = `${Math.min(
        textareaRef.current.scrollHeight,
        150
      )}px`;
    }
  }, [text]);

  const handleSend = () => {
    const trimmed = text.trim();
    if (!trimmed) return;
    onSend(trimmed);
    setText('');
  };

  const handleNormalize = async () => {
    const trimmed = text.trim();
    if (!trimmed) return;

    setNormalizing(true);
    try {
      const normalized = await onNormalize(trimmed);
      setText(normalized);
    } finally {
      setNormalizing(false);
    }
  };

  const handleKeyDown = (e: KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && e.ctrlKey) {
      e.preventDefault();
      handleSend();
    }
  };

  const handleClear = () => {
    setText('');
    textareaRef.current?.focus();
  };

  return (
    <div className="border-t border-gray-200 bg-white p-4 flex-shrink-0">
      <div className="relative">
        <textarea
          ref={textareaRef}
          value={text}
          onChange={(e) => setText(e.target.value)}
          onKeyDown={handleKeyDown}
          placeholder="Введите сообщение... (Ctrl+Enter для отправки)"
          className="w-full px-4 py-3 pr-10 border border-gray-300 rounded-lg resize-none focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
          rows={1}
          disabled={disabled}
        />
        {text && (
          <button
            onClick={handleClear}
            className="absolute right-3 top-3 p-1 hover:bg-gray-100 rounded transition-colors"
            title="Очистить"
          >
            <X size={16} className="text-gray-400" />
          </button>
        )}
      </div>

      <div className="flex items-center justify-between mt-3">
        <div className="flex gap-2">
          <Button
            variant="ghost"
            size="sm"
            onClick={handleNormalize}
            loading={normalizing}
            disabled={!text.trim() || disabled}
          >
            <Sparkles size={18} className="mr-1" />
            Нормализовать
          </Button>
        </div>

        <Button
          onClick={handleSend}
          disabled={!text.trim() || disabled}
        >
          <Send size={18} className="mr-1" />
          Отправить
        </Button>
      </div>
    </div>
  );
};
