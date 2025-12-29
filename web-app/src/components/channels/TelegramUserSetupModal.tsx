import { useState, useEffect } from 'react';
import { ArrowLeft, Loader2, CheckCircle, AlertCircle, RefreshCw, Phone, KeyRound } from 'lucide-react';
import { telegramUserApi, type TelegramUserSession } from '../../api';

interface TelegramUserSetupModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

type SetupStep = 'phone' | 'code' | 'password' | 'connecting' | 'success' | 'error';

export const TelegramUserSetupModal = ({ isOpen, onClose, onSuccess }: TelegramUserSetupModalProps) => {
  const [step, setStep] = useState<SetupStep>('phone');
  const [phone, setPhone] = useState('');
  const [code, setCode] = useState('');
  const [password, setPassword] = useState('');
  const [accountName, setAccountName] = useState('');
  const [session, setSession] = useState<TelegramUserSession | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  // Reset state when modal opens
  useEffect(() => {
    if (isOpen) {
      setStep('phone');
      setPhone('');
      setCode('');
      setPassword('');
      setAccountName('');
      setSession(null);
      setError(null);
      setLoading(false);
    }
  }, [isOpen]);

  const formatPhone = (value: string): string => {
    // Remove non-digits
    const digits = value.replace(/\D/g, '');
    // Ensure starts with country code
    if (digits.startsWith('8')) {
      return '7' + digits.slice(1);
    }
    return digits;
  };

  const handleRequestCode = async () => {
    const formattedPhone = formatPhone(phone);

    if (formattedPhone.length < 10) {
      setError('Введите корректный номер телефона');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const newSession = await telegramUserApi.requestCode({
        phone: '+' + formattedPhone,
        accountName: accountName.trim() || undefined,
      });
      setSession(newSession);
      setStep('code');
    } catch (err: any) {
      setError(err.response?.data?.error || err.message || 'Ошибка отправки кода');
      setStep('error');
    } finally {
      setLoading(false);
    }
  };

  const handleVerifyCode = async () => {
    if (!code.trim() || code.length < 5) {
      setError('Введите код из Telegram');
      return;
    }

    if (!session) return;

    setLoading(true);
    setError(null);
    setStep('connecting');

    try {
      const result = await telegramUserApi.verifyCode({
        sessionId: session.sessionId,
        code: code.trim(),
        password: password || undefined,
      });

      if (result.status === 'pending_password') {
        setSession(result);
        setStep('password');
        setLoading(false);
        return;
      }

      if (result.status === 'connected') {
        setSession(result);
        setStep('success');
        setTimeout(() => {
          onSuccess();
          onClose();
        }, 2000);
      } else {
        throw new Error('Неожиданный статус: ' + result.status);
      }
    } catch (err: any) {
      setError(err.response?.data?.error || err.message || 'Ошибка проверки кода');
      setStep('error');
    } finally {
      setLoading(false);
    }
  };

  const handleRetry = () => {
    setStep('phone');
    setError(null);
    setSession(null);
    setCode('');
    setPassword('');
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
            <span className="text-2xl">👤</span>
            Подключение Telegram User
          </h3>
        </div>

        {/* Content */}
        <div className="p-6">
          {/* Step: Enter phone */}
          {step === 'phone' && (
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Номер телефона
                </label>
                <div className="relative">
                  <Phone size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
                  <input
                    type="tel"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                    placeholder="+7 999 123 45 67"
                    className="w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                    autoFocus
                  />
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Название (опционально)
                </label>
                <input
                  type="text"
                  value={accountName}
                  onChange={(e) => setAccountName(e.target.value)}
                  placeholder="Например: Рабочий Telegram"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                />
              </div>

              {error && (
                <p className="text-sm text-red-600">{error}</p>
              )}

              <div className="bg-yellow-50 rounded-lg p-3 text-sm text-yellow-800">
                <p className="font-medium">Внимание:</p>
                <p className="text-yellow-700">
                  Будет использован ваш личный аккаунт Telegram.
                  Код подтверждения придёт в приложение Telegram.
                </p>
              </div>

              <button
                onClick={handleRequestCode}
                disabled={loading}
                className="w-full py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
              >
                {loading ? (
                  <>
                    <Loader2 size={18} className="animate-spin" />
                    Отправка кода...
                  </>
                ) : (
                  'Подключить'
                )}
              </button>
            </div>
          )}

          {/* Step: Enter code */}
          {step === 'code' && (
            <div className="space-y-4">
              <div className="text-center mb-4">
                <KeyRound size={48} className="mx-auto text-blue-500 mb-2" />
                <p className="text-gray-600">
                  Код отправлен на номер<br />
                  <span className="font-medium">{phone}</span>
                </p>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Код подтверждения
                </label>
                <input
                  type="text"
                  value={code}
                  onChange={(e) => setCode(e.target.value.replace(/\D/g, ''))}
                  placeholder="12345"
                  maxLength={6}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-center text-2xl tracking-widest font-mono"
                  autoFocus
                />
              </div>

              {error && (
                <p className="text-sm text-red-600">{error}</p>
              )}

              <button
                onClick={handleVerifyCode}
                disabled={loading || code.length < 5}
                className="w-full py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
              >
                {loading ? (
                  <>
                    <Loader2 size={18} className="animate-spin" />
                    Проверка...
                  </>
                ) : (
                  'Подтвердить'
                )}
              </button>

              <button
                onClick={handleRetry}
                className="w-full py-2 text-gray-600 hover:bg-gray-100 rounded-lg transition-colors"
              >
                Изменить номер
              </button>
            </div>
          )}

          {/* Step: Enter 2FA password */}
          {step === 'password' && (
            <div className="space-y-4">
              <div className="text-center mb-4">
                <KeyRound size={48} className="mx-auto text-orange-500 mb-2" />
                <p className="text-gray-600">
                  Требуется пароль двухфакторной аутентификации
                </p>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Пароль 2FA
                </label>
                <input
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="Введите пароль"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  autoFocus
                />
              </div>

              {error && (
                <p className="text-sm text-red-600">{error}</p>
              )}

              <button
                onClick={handleVerifyCode}
                disabled={loading || !password}
                className="w-full py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
              >
                {loading ? (
                  <>
                    <Loader2 size={18} className="animate-spin" />
                    Проверка...
                  </>
                ) : (
                  'Войти'
                )}
              </button>
            </div>
          )}

          {/* Step: Connecting */}
          {step === 'connecting' && (
            <div className="text-center py-8">
              <Loader2 size={48} className="animate-spin mx-auto text-blue-500 mb-4" />
              <p className="text-gray-600">Подключение к Telegram...</p>
            </div>
          )}

          {/* Step: Success */}
          {step === 'success' && session && (
            <div className="text-center py-8">
              <CheckCircle size={64} className="mx-auto text-green-500 mb-4" />
              <h4 className="text-xl font-semibold text-gray-900 mb-2">
                Telegram подключен!
              </h4>
              <p className="text-gray-600">
                {session.firstName} {session.lastName}
                {session.username && ` (@${session.username})`}
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
