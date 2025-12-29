import type { ChannelType } from '../../types';

interface DialogFiltersProps {
  selectedChannel: ChannelType | 'all';
  onChannelChange: (channel: ChannelType | 'all') => void;
  showUnreadOnly: boolean;
  onUnreadChange: (value: boolean) => void;
}

const channels: { value: ChannelType | 'all'; label: string }[] = [
  { value: 'all', label: 'Все каналы' },
  { value: 'whatsapp', label: 'WhatsApp' },
  { value: 'telegram', label: 'Telegram' },
  { value: 'avito', label: 'Avito' },
  { value: 'vk', label: 'ВКонтакте' },
  { value: 'max', label: 'MAX' },
];

export const DialogFilters = ({
  selectedChannel,
  onChannelChange,
  showUnreadOnly,
  onUnreadChange,
}: DialogFiltersProps) => {
  return (
    <div className="flex flex-wrap items-center gap-4 p-4 border-b border-gray-200 bg-white">
      <select
        value={selectedChannel}
        onChange={(e) => onChannelChange(e.target.value as ChannelType | 'all')}
        className="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500"
      >
        {channels.map(({ value, label }) => (
          <option key={value} value={value}>
            {label}
          </option>
        ))}
      </select>

      <label className="flex items-center gap-2 cursor-pointer">
        <input
          type="checkbox"
          checked={showUnreadOnly}
          onChange={(e) => onUnreadChange(e.target.checked)}
          className="w-4 h-4 rounded border-gray-300 text-blue-600 focus:ring-blue-500"
        />
        <span className="text-sm text-gray-700">Только непрочитанные</span>
      </label>
    </div>
  );
};
