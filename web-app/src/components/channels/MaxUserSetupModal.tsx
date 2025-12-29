import { useState, useEffect } from 'react';
import { ArrowLeft, Loader2, CheckCircle, AlertCircle, RefreshCw, Phone, KeyRound } from 'lucide-react';
import { maxUserApi, type MaxUserSession } from '../../api';

interface MaxUserSetupModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

type SetupStep = 'phone' | 'code' | 'password_2fa' | 'connecting' | 'success' | 'error';

export const MaxUserSetupModal = ({ isOpen, onClose, onSuccess }: MaxUserSetupModalProps) => {
  const [step, setStep] = useState<SetupStep>('phone');
  const [phone, setPhone] = useState('');
  const [code, setCode] = useState('');
  const [password2fa, setPassword2fa] = useState('');
  const [session, setSession] = useState<MaxUserSession | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  // Reset state when modal opens
  useEffect(() => {
    if (isOpen) {
      setStep('phone');
      setPhone('');
      setCode('');
      setPassword2fa('');
      setSession(null);
      setError(null);
      setLoading(false);
    }
  }, [isOpen]);

  const formatPhone = (value: string): string => {
    const digits = value.replace(/\D/g, '');
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
      const newSession = await maxUserApi.createSession({
        phone: '+' + formattedPhone,
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
      setError('Введите код из SMS');
      return;
    }

    if (!session) return;

    setLoading(true);
    setError(null);
    setStep('connecting');

    try {
      const result = await maxUserApi.verifyCode(session.session_id, {
        code: code.trim(),
        password_2fa: password2fa || undefined,
      });

      if (result.status === 'pending_2fa') {
        setSession(result);
        setStep('password_2fa');
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
      const errorMsg = err.response?.data?.error || err.response?.data?.detail || err.message;
      if (errorMsg?.includes('2FA')) {
        setStep('password_2fa');
        setLoading(false);
        return;
      }
      setError(errorMsg || 'Ошибка проверки кода');
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
    setPassword2fa('');
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
            <span className="text-2xl">💼</span>
            Подключение MAX User
          </h3>
        </div>

        {/* Content */}
        <div className="p-6">
          {/* Step: Enter phone */}
          {step === 'phone' && (
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Номер телефона MAX
                </label>
                <div className="relative">
                  <Phone size={18} className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
                  <input
                    type="tel"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                    placeholder="+7 999 123 45 67"
                    className="w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                    autoFocus
                  />
                </div>
              </div>

              {error && (
                <p className="text-sm text-red-600">{error}</p>
              )}

              <div className="bg-purple-50 rounded-lg p-3 text-sm text-purple-800">
                <p className="font-medium">MAX (VK Teams)</p>
                <p className="text-purple-700">
                  Код подтверждения будет отправлен по SMS на указанный номер.
                  Используйте номер, привязанный к вашему аккаунту MAX.
                </p>
              </div>

              <button
                onClick={handleRequestCode}
                disabled={loading}
                className="w-full py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
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

          {/* Step: Enter SMS code */}
          {step === 'code' && (
            <div className="space-y-4">
              <div className="text-center mb-4">
                <KeyRound size={48} className="mx-auto text-purple-500 mb-2" />
                <p className="text-gray-600">
                  Код отправлен на<br />
                  <span className="font-medium">{phone}</span>
                </p>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Код из SMS
                </label>
                <input
                  type="text"
                  value={code}
                  onChange={(e) => setCode(e.target.value.replace(/\D/g, ''))}
                  placeholder="123456"
                  maxLength={6}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500 text-center text-2xl tracking-widest font-mono"
                  autoFocus
                />
              </div>

              {error && (
                <p className="text-sm text-red-600">{error}</p>
              )}

              <button
                onClick={handleVerifyCode}
                disabled={loading || code.length < 5}
                className="w-full py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
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
          {step === 'password_2fa' && (
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
                  value={password2fa}
                  onChange={(e) => setPassword2fa(e.target.value)}
                  placeholder="Введите пароль"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-purple-500"
                  autoFocus
                />
              </div>

              {error && (
                <p className="text-sm text-red-600">{error}</p>
              )}

              <button
                onClick={handleVerifyCode}
                disabled={loading || !password2fa}
                className="w-full py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
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
              <Loader2 size={48} className="animate-spin mx-auto text-purple-500 mb-4" />
              <p className="text-gray-600">Подключение к MAX...</p>
            </div>
          )}

          {/* Step: Success */}
          {step === 'success' && session && (
            <div className="text-center py-8">
              <CheckCircle size={64} className="mx-auto text-green-500 mb-4" />
              <h4 className="text-xl font-semibold text-gray-900 mb-2">
                MAX подключен!
              </h4>
              <p className="text-gray-600">
                {session.user_name || phone}
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
