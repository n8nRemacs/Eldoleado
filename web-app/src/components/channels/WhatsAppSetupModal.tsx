import { useState, useEffect } from 'react';
import { ArrowLeft, Loader2, CheckCircle, AlertCircle, RefreshCw } from 'lucide-react';
import { whatsappApi, type WhatsAppSession } from '../../api';

interface WhatsAppSetupModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

type SetupStep = 'name' | 'qr' | 'connecting' | 'success' | 'error';

export const WhatsAppSetupModal = ({ isOpen, onClose, onSuccess }: WhatsAppSetupModalProps) => {
  const [step, setStep] = useState<SetupStep>('name');
  const [accountName, setAccountName] = useState('');
  const [session, setSession] = useState<WhatsAppSession | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  // Reset state when modal opens
  useEffect(() => {
    if (isOpen) {
      setStep('name');
      setAccountName('');
      setSession(null);
      setError(null);
      setLoading(false);
    }
  }, [isOpen]);

  // Poll for QR code and connection status
  useEffect(() => {
    if (!session || step !== 'qr') return;

    const pollInterval = setInterval(async () => {
      try {
        const status = await whatsappApi.getSessionStatus(session.sessionId);
        setSession(status);

        if (status.status === 'connected') {
          setStep('success');
          clearInterval(pollInterval);
          setTimeout(() => {
            onSuccess();
            onClose();
          }, 2000);
        }
      } catch (err) {
        console.error('Failed to poll status:', err);
      }
    }, 2000);

    return () => clearInterval(pollInterval);
  }, [session, step, onSuccess, onClose]);

  const handleCreateSession = async () => {
    if (!accountName.trim()) {
      setError('Введите название аккаунта');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const newSession = await whatsappApi.createSession({
        accountName: accountName.trim(),
      });
      setSession(newSession);
      setStep('qr');
    } catch (err: any) {
      setError(err.response?.data?.error || err.message || 'Ошибка создания сессии');
      setStep('error');
    } finally {
      setLoading(false);
    }
  };

  const handleRetry = () => {
    setStep('name');
    setError(null);
    setSession(null);
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
            <span className="text-2xl">💬</span>
            Подключение WhatsApp
          </h3>
        </div>

        {/* Content */}
        <div className="p-6">
          {/* Step: Enter name */}
          {step === 'name' && (
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Название аккаунта
                </label>
                <input
                  type="text"
                  value={accountName}
                  onChange={(e) => setAccountName(e.target.value)}
                  placeholder="Например: Основной WhatsApp"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
                  autoFocus
                />
                {error && (
                  <p className="mt-1 text-sm text-red-600">{error}</p>
                )}
              </div>

              <button
                onClick={handleCreateSession}
                disabled={loading}
                className="w-full py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
              >
                {loading ? (
                  <>
                    <Loader2 size={18} className="animate-spin" />
                    Создание сессии...
                  </>
                ) : (
                  'Подключить'
                )}
              </button>
            </div>
          )}

          {/* Step: Show QR code */}
          {step === 'qr' && session && (
            <div className="text-center space-y-4">
              <p className="text-gray-600">
                Отсканируйте QR-код в приложении WhatsApp
              </p>

              <div className="flex justify-center">
                {session.qrImage ? (
                  <img
                    src={session.qrImage}
                    alt="QR Code"
                    className="w-64 h-64 border rounded-lg"
                  />
                ) : session.qr ? (
                  <div className="w-64 h-64 border rounded-lg flex items-center justify-center bg-gray-50">
                    <div className="text-center">
                      <Loader2 size={32} className="animate-spin mx-auto mb-2 text-gray-400" />
                      <p className="text-sm text-gray-500">Генерация QR...</p>
                    </div>
                  </div>
                ) : (
                  <div className="w-64 h-64 border rounded-lg flex items-center justify-center bg-gray-50">
                    <Loader2 size={32} className="animate-spin text-gray-400" />
                  </div>
                )}
              </div>

              <div className="flex items-center justify-center gap-2 text-sm text-gray-500">
                <Loader2 size={14} className="animate-spin" />
                Ожидание сканирования...
              </div>

              <p className="text-xs text-gray-400">
                WhatsApp → Настройки → Связанные устройства → Привязка устройства
              </p>
            </div>
          )}

          {/* Step: Success */}
          {step === 'success' && (
            <div className="text-center py-8">
              <CheckCircle size={64} className="mx-auto text-green-500 mb-4" />
              <h4 className="text-xl font-semibold text-gray-900 mb-2">
                WhatsApp подключен!
              </h4>
              <p className="text-gray-600">
                Аккаунт "{accountName}" успешно добавлен
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
