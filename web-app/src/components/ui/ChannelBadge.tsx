import type { ChannelType } from '../../types';
import { cn } from '../../utils';

interface ChannelConfig {
  bg: string;
  label: string;
  name: string;
}

const channelConfig: Record<string, ChannelConfig> = {
  whatsapp: { bg: 'bg-channel-whatsapp', label: 'WA', name: 'WhatsApp' },
  telegram_bot: { bg: 'bg-channel-telegram', label: 'TB', name: 'Telegram Bot' },
  telegram_user: { bg: 'bg-blue-600', label: 'TU', name: 'Telegram User' },
  telegram: { bg: 'bg-channel-telegram', label: 'TG', name: 'Telegram' }, // legacy
  avito_reverse: { bg: 'bg-channel-avito', label: 'AE', name: 'Avito Eldoleado' },
  avito_official: { bg: 'bg-purple-600', label: 'AO', name: 'Avito Official' },
  avito: { bg: 'bg-channel-avito', label: 'AV', name: 'Avito' }, // legacy
  vk: { bg: 'bg-channel-vk', label: 'VK', name: 'VKontakte' },
  max: { bg: 'bg-channel-max', label: 'MX', name: 'MAX' },
  form: { bg: 'bg-gray-600', label: 'WF', name: 'Web Form' },
  phone: { bg: 'bg-green-700', label: 'PH', name: 'Phone' },
};

interface ChannelBadgeProps {
  channel: ChannelType | string;
  size?: 'sm' | 'md' | 'lg';
  className?: string;
}

export const ChannelBadge = ({
  channel,
  size = 'md',
  className,
}: ChannelBadgeProps) => {
  const config = channelConfig[channel] || { bg: 'bg-gray-500', label: '?', name: channel };

  const sizes = {
    sm: 'w-6 h-6 text-xs',
    md: 'w-8 h-8 text-sm',
    lg: 'w-10 h-10 text-base',
  };

  return (
    <div
      className={cn(
        'rounded-full flex items-center justify-center text-white font-bold flex-shrink-0',
        config.bg,
        sizes[size],
        className
      )}
      title={config.name}
    >
      {config.label}
    </div>
  );
};

export const getChannelName = (channel: ChannelType | string): string => {
  return channelConfig[channel]?.name || channel;
};

export const getChannelConfig = (channel: ChannelType | string): ChannelConfig => {
  return channelConfig[channel] || { bg: 'bg-gray-500', label: '?', name: channel };
};
