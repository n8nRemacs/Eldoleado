import { useEffect, useState } from 'react';
import { RefreshCw, Plus, X, Settings, AlertCircle, CheckCircle, Loader2 } from 'lucide-react';
import { useAuthStore } from '../store';
import {
  channelsApi,
  AVAILABLE_CHANNELS,
  type ChannelInfo,
  type ChannelStatusType,
  type AvailableChannel,
} from '../api';
import { ChannelBadge, getChannelName } from '../components/ui';
import {
  WhatsAppSetupModal,
  TelegramBotSetupModal,
  TelegramUserSetupModal,
  MaxUserSetupModal,
} from '../components/channels';
import type { ChannelType } from '../types';

const statusConfig: Record<ChannelStatusType, { text: string; color: string; icon: React.ReactNode }> = {
  connected: {
    text: 'Подключен',
    color: 'text-green-600',
    icon: <CheckCircle size={16} className="text-green-500" />,
  },
  not_configured: {
    text: 'Не настроен',
    color: 'text-gray-500',
    icon: <Settings size={16} className="text-gray-400" />,
  },
  error: {
    text: 'Ошибка',
    color: 'text-red-600',
    icon: <AlertCircle size={16} className="text-red-500" />,
  },
  checking: {
    text: 'Проверка...',
    color: 'text-yellow-600',
    icon: <Loader2 size={16} className="text-yellow-500 animate-spin" />,
  },
  pending: {
    text: 'Ожидание',
    color: 'text-orange-600',
    icon: <Loader2 size={16} className="text-orange-500" />,
  },
};

// Group channels by type
function groupChannels(channels: ChannelInfo[]): Map<string, ChannelInfo[]> {
  const grouped = new Map<string, ChannelInfo[]>();

  for (const channel of channels) {
    const type = channel.type;
    if (!grouped.has(type)) {
      grouped.set(type, []);
    }
    grouped.get(type)!.push(channel);
  }

  return grouped;
}

// Channel order for display
const CHANNEL_ORDER: ChannelType[] = [
  'whatsapp',
  'telegram_bot',
  'telegram_user',
  'avito_reverse',
  'avito_official',
  'vk',
  'max',
];

export const SettingsPage = () => {
  const { session, logout } = useAuthStore();
  const [channels, setChannels] = useState<ChannelInfo[]>([]);
  const [loading, setLoading] = useState(true);
  const [refreshing, setRefreshing] = useState(false);
  const [showAddModal, setShowAddModal] = useState(false);
  const [showWhatsAppSetup, setShowWhatsAppSetup] = useState(false);
  const [showTelegramBotSetup, setShowTelegramBotSetup] = useState(false);
  const [showTelegramUserSetup, setShowTelegramUserSetup] = useState(false);
  const [showMaxUserSetup, setShowMaxUserSetup] = useState(false);

  const fetchChannelsStatus = async () => {
    try {
      const results = await channelsApi.getAllChannelsStatus();
      setChannels(results);
    } catch (error) {
      console.error('Failed to fetch channels status:', error);
    } finally {
      setLoading(false);
      setRefreshing(false);
    }
  };

  useEffect(() => {
    fetchChannelsStatus();
  }, []);

  const handleRefresh = () => {
    setRefreshing(true);
    fetchChannelsStatus();
  };

  const handleAddChannel = (channelType: AvailableChannel) => {
    setShowAddModal(false);

    switch (channelType.type) {
      case 'whatsapp':
        setShowWhatsAppSetup(true);
        break;
      case 'telegram_bot':
        setShowTelegramBotSetup(true);
        break;
      case 'telegram_user':
        setShowTelegramUserSetup(true);
        break;
      case 'max':
        setShowMaxUserSetup(true);
        break;
      default:
        alert(`Настройка ${channelType.name} будет добавлена в следующей версии.`);
    }
  };

  const handleSetupSuccess = () => {
    // Refresh channels list after successful setup
    fetchChannelsStatus();
  };

  // Filter out not_configured channels - they shouldn't appear in "Connected" section
  const configuredChannels = channels.filter(c => c.status !== 'not_configured');
  const groupedChannels = groupChannels(configuredChannels);

  return (
    <div className="h-full overflow-auto p-6">
      <h1 className="text-xl font-bold text-gray-900 mb-6">Настройки</h1>

      {/* Channels Section */}
      <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-lg font-semibold text-gray-900">Подключенные каналы</h2>
          <div className="flex items-center gap-2">
            <button
              onClick={handleRefresh}
              disabled={refreshing}
              className="p-2 hover:bg-gray-100 rounded-lg transition-colors disabled:opacity-50"
              title="Обновить статусы"
            >
              <RefreshCw
                size={18}
                className={`text-gray-600 ${refreshing ? 'animate-spin' : ''}`}
              />
            </button>
            <button
              onClick={() => setShowAddModal(true)}
              className="flex items-center gap-1 px-3 py-2 text-sm bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
            >
              <Plus size={16} />
              <span>Добавить</span>
            </button>
          </div>
        </div>

        <div className="space-y-6">
          {loading ? (
            <div className="text-center py-8 text-gray-500">
              <Loader2 className="animate-spin mx-auto mb-2" size={24} />
              Загрузка статусов...
            </div>
          ) : channels.length === 0 ? (
            <div className="text-center py-8 text-gray-500">
              <p className="mb-4">Нет подключенных каналов</p>
              <button
                onClick={() => setShowAddModal(true)}
                className="text-blue-600 hover:underline"
              >
                Добавить первый канал
              </button>
            </div>
          ) : (
            // Render grouped channels
            CHANNEL_ORDER.map((channelType) => {
              const channelGroup = groupedChannels.get(channelType);
              if (!channelGroup || channelGroup.length === 0) return null;

              return (
                <div key={channelType} className="space-y-2">
                  {/* Channel type header */}
                  <div className="flex items-center gap-2 text-sm text-gray-500 font-medium">
                    <ChannelBadge channel={channelType} size="sm" />
                    <span>{getChannelName(channelType)}</span>
                    <span className="text-gray-400">({channelGroup.length})</span>
                  </div>

                  {/* Channel accounts */}
                  <div className="space-y-2 pl-8">
                    {channelGroup.map((channel) => {
                      const status = statusConfig[channel.status] || statusConfig.not_configured;

                      return (
                        <div
                          key={channel.id}
                          className="flex items-center justify-between p-3 border border-gray-200 rounded-lg bg-gray-50"
                        >
                          <div className="flex items-center gap-3">
                            {status.icon}
                            <div>
                              <div className="flex items-center gap-2">
                                <span className="font-medium text-gray-900">
                                  {channel.name || channel.account_id || 'Без имени'}
                                </span>
                                {channel.is_official && (
                                  <span className="px-1.5 py-0.5 text-xs bg-blue-100 text-blue-700 rounded">
                                    Official
                                  </span>
                                )}
                              </div>
                              {channel.ip_address && (
                                <p className="text-xs text-gray-500">{channel.ip_address}</p>
                              )}
                            </div>
                          </div>
                          <div className="flex items-center gap-3">
                            <span className={`text-sm ${status.color}`}>
                              {status.text}
                            </span>
                            <button
                              className="p-1.5 hover:bg-gray-200 rounded transition-colors"
                              title="Настройки"
                            >
                              <Settings size={14} className="text-gray-500" />
                            </button>
                          </div>
                        </div>
                      );
                    })}
                  </div>
                </div>
              );
            })
          )}
        </div>
      </section>

      {/* Notifications Section */}
      <section className="bg-white rounded-xl shadow-sm p-6 mb-6">
        <h2 className="text-lg font-semibold text-gray-900 mb-4">Уведомления</h2>
        <div className="space-y-4">
          <div className="flex items-center justify-between">
            <div>
              <p className="font-medium">Browser Notifications</p>
              <p className="text-sm text-gray-500">
                Получать уведомления в браузере
              </p>
            </div>
            <button className="px-4 py-2 text-sm bg-gray-100 hover:bg-gray-200 rounded-lg transition-colors">
              Включить
            </button>
          </div>
        </div>
      </section>

      {/* Account Section */}
      <section className="bg-white rounded-xl shadow-sm p-6">
        <h2 className="text-lg font-semibold text-gray-900 mb-4">Аккаунт</h2>
        <div className="space-y-3">
          <div className="flex justify-between py-2">
            <span className="text-gray-600">Оператор</span>
            <span className="font-medium">{session?.name}</span>
          </div>
          {session?.email && (
            <div className="flex justify-between py-2">
              <span className="text-gray-600">Email</span>
              <span className="font-medium">{session.email}</span>
            </div>
          )}
          <div className="pt-4">
            <button
              onClick={logout}
              className="px-4 py-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors"
            >
              Выйти из аккаунта
            </button>
          </div>
        </div>
      </section>

      {/* Version */}
      <p className="text-center text-gray-400 text-sm mt-8">
        Eldoleado Web v1.0.0
      </p>

      {/* Add Channel Modal */}
      {showAddModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
          <div className="bg-white rounded-xl shadow-xl w-full max-w-md mx-4 max-h-[80vh] overflow-hidden">
            {/* Header */}
            <div className="flex items-center justify-between p-4 border-b">
              <h3 className="text-lg font-semibold">Добавить канал</h3>
              <button
                onClick={() => setShowAddModal(false)}
                className="p-1 hover:bg-gray-100 rounded-lg transition-colors"
              >
                <X size={20} className="text-gray-500" />
              </button>
            </div>

            {/* Channel list */}
            <div className="p-4 space-y-2 overflow-y-auto max-h-[50vh]">
              {AVAILABLE_CHANNELS.map((channel) => (
                <button
                  key={channel.type}
                  onClick={() => handleAddChannel(channel)}
                  className="w-full flex items-center gap-4 p-4 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors text-left"
                >
                  <div className="text-2xl">{channel.icon}</div>
                  <div className="flex-1">
                    <p className="font-medium text-gray-900">{channel.name}</p>
                    <p className="text-sm text-gray-500">{channel.description}</p>
                  </div>
                  <Plus size={20} className="text-gray-400" />
                </button>
              ))}
            </div>

            {/* Footer */}
            <div className="p-4 border-t">
              <button
                onClick={() => setShowAddModal(false)}
                className="w-full py-2 text-gray-600 hover:bg-gray-100 rounded-lg transition-colors"
              >
                Отмена
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Channel Setup Modals */}
      <WhatsAppSetupModal
        isOpen={showWhatsAppSetup}
        onClose={() => setShowWhatsAppSetup(false)}
        onSuccess={handleSetupSuccess}
      />
      <TelegramBotSetupModal
        isOpen={showTelegramBotSetup}
        onClose={() => setShowTelegramBotSetup(false)}
        onSuccess={handleSetupSuccess}
      />
      <TelegramUserSetupModal
        isOpen={showTelegramUserSetup}
        onClose={() => setShowTelegramUserSetup(false)}
        onSuccess={handleSetupSuccess}
      />
      <MaxUserSetupModal
        isOpen={showMaxUserSetup}
        onClose={() => setShowMaxUserSetup(false)}
        onSuccess={handleSetupSuccess}
      />
    </div>
  );
};
