import { useState, useEffect } from 'react';
import { ArrowLeft, Loader2, CheckCircle, AlertCircle, RefreshCw, ExternalLink } from 'lucide-react';
import { telegramBotApi, type TelegramBot } from '../../api';

interface TelegramBotSetupModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

type SetupStep = 'token' | 'validating' | 'success' | 'error';

export const TelegramBotSetupModal = ({ isOpen, onClose, onSuccess }: TelegramBotSetupModalProps) => {
  const [step, setStep] = useState<SetupStep>('token');
  const [botToken, setBotToken] = useState('');
  const [accountName, setAccountName] = useState('');
  const [botInfo, setBotInfo] = useState<TelegramBot | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  // Reset state when modal opens
  useEffect(() => {
    if (isOpen) {
      setStep('token');
      setBotToken('');
      setAccountName('');
      setBotInfo(null);
      setError(null);
      setLoading(false);
    }
  }, [isOpen]);

  const handleValidateAndRegister = async () => {
    if (!botToken.trim()) {
      setError('Введите токен бота');
      return;
    }

    // Basic token format validation
    if (!botToken.includes(':')) {
      setError('Неверный формат токена. Токен должен содержать ":"');
      return;
    }

    setLoading(true);
    setError(null);
    setStep('validating');

    try {
      // First validate the token
      const validation = await telegramBotApi.validateToken(botToken.trim());

      if (!validation.valid) {
        throw new Error('Недействительный токен бота');
      }

      // Then register the bot
      const bot = await telegramBotApi.register({
        botToken: botToken.trim(),
        accountName: accountName.trim() || validation.botInfo?.first_name,
      });

      setBotInfo(bot);
      setStep('success');

      setTimeout(() => {
        onSuccess();
        onClose();
      }, 2000);
    } catch (err: any) {
      setError(err.response?.data?.error || err.message || 'Ошибка регистрации бота');
      setStep('error');
    } finally {
      setLoading(false);
    }
  };

  const handleRetry = () => {
    setStep('token');
    setError(null);
    setBotInfo(null);
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-md mx-4">
        {/* Header */}
        <div className="flex items-center gap-3 p-4 border-b">
          <button
            onClick={onClose}
            className="p-1 hover:bg-gray-100 rounded-lg transition-colors"
          >
            <ArrowLeft size={20} className="text-gray-500" />
          </button>
          <h3 className="text-lg font-semibold flex items-center gap-2">
            <span className="text-2xl">🤖</span>
            Подключение Telegram Bot
          </h3>
        </div>

        {/* Content */}
        <div className="p-6">
          {/* Step: Enter token */}
          {step === 'token' && (
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Токен бота
                </label>
                <input
                  type="text"
                  value={botToken}
                  onChange={(e) => setBotToken(e.target.value)}
                  placeholder="123456789:ABCdefGHIjklMNOpqrsTUVwxyz"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 font-mono text-sm"
                  autoFocus
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Название (опционально)
                </label>
                <input
                  type="text"
                  value={accountName}
                  onChange={(e) => setAccountName(e.target.value)}
                  placeholder="Будет использовано имя бота"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                />
              </div>

              {error && (
                <p className="text-sm text-red-600">{error}</p>
              )}

              <div className="bg-blue-50 rounded-lg p-3 text-sm text-blue-800">
                <p className="font-medium mb-1">Как получить токен:</p>
                <ol className="list-decimal list-inside space-y-1 text-blue-700">
                  <li>Откройте @BotFather в Telegram</li>
                  <li>Отправьте /newbot или /mybots</li>
                  <li>Скопируйте токен бота</li>
                </ol>
                <a
                  href="https://t.me/BotFather"
                  target="_blank"
                  rel="noopener noreferrer"
                  className="inline-flex items-center gap-1 mt-2 text-blue-600 hover:underline"
                >
                  Открыть @BotFather
                  <ExternalLink size={12} />
                </a>
              </div>

              <button
                onClick={handleValidateAndRegister}
                disabled={loading}
                className="w-full py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
              >
                {loading ? (
                  <>
                    <Loader2 size={18} className="animate-spin" />
                    Проверка токена...
                  </>
                ) : (
                  'Подключить'
                )}
              </button>
            </div>
          )}

          {/* Step: Validating */}
          {step === 'validating' && (
            <div className="text-center py-8">
              <Loader2 size={48} className="animate-spin mx-auto text-blue-500 mb-4" />
              <p className="text-gray-600">Проверка и регистрация бота...</p>
            </div>
          )}

          {/* Step: Success */}
          {step === 'success' && botInfo && (
            <div className="text-center py-8">
              <CheckCircle size={64} className="mx-auto text-green-500 mb-4" />
              <h4 className="text-xl font-semibold text-gray-900 mb-2">
                Бот подключен!
              </h4>
              <p className="text-gray-600">
                @{botInfo.botUsername} успешно добавлен
              </p>
            </div>
          )}

          {/* Step: Error */}
          {step === 'error' && (
            <div className="text-center py-8">
              <AlertCircle size={64} className="mx-auto text-red-500 mb-4" />
              <h4 className="text-xl font-semibold text-gray-900 mb-2">
                Ошибка подключения
              </h4>
              <p className="text-gray-600 mb-4">{error}</p>
              <button
                onClick={handleRetry}
                className="px-4 py-2 bg-gray-100 hover:bg-gray-200 rounded-lg transition-colors flex items-center gap-2 mx-auto"
              >
                <RefreshCw size={16} />
                Попробовать снова
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
