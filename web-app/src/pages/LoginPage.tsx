import { useState } from 'react';
import { useNavigate, Navigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { authApi } from '../api';
import { useAuthStore } from '../store';
import { Button, Input } from '../components/ui';
import { getDeviceInfo } from '../utils';

const loginSchema = z.object({
  email: z.string().min(1, 'Введите email').email('Неверный формат email'),
  password: z.string().min(1, 'Введите пароль'),
});

type LoginForm = z.infer<typeof loginSchema>;

export const LoginPage = () => {
  const navigate = useNavigate();
  const { isAuthenticated, login } = useAuthStore();
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginForm>({
    resolver: zodResolver(loginSchema),
  });

  // Redirect if already authenticated
  if (isAuthenticated) {
    return <Navigate to="/dialogs" replace />;
  }

  const onSubmit = async (data: LoginForm) => {
    setLoading(true);
    setError(null);

    try {
      const response = await authApi.login({
        login: data.email,
        password: data.password,
        device_info: getDeviceInfo(),
        app_mode: 'client',
      });

      if (response.success) {
        login({
          token: response.session_token,
          operatorId: response.operator_id,
          tenantId: response.tenant_id,
          name: response.name,
          email: response.email,
          allowedChannels: response.allowed_channels,
        });
        navigate('/dialogs');
      } else {
        setError('Неверный логин или пароль');
      }
    } catch (err) {
      console.error('Login error:', err);
      setError('Ошибка авторизации. Проверьте подключение к интернету.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 p-4">
      <div className="bg-white p-8 rounded-xl shadow-lg w-full max-w-md">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-blue-600">Eldoleado</h1>
          <p className="text-gray-500 mt-2">Вход в систему</p>
        </div>

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <Input
            {...register('email')}
            label="Email"
            type="email"
            placeholder="operator@example.com"
            error={errors.email?.message}
            autoComplete="email"
          />

          <Input
            {...register('password')}
            label="Пароль"
            type="password"
            placeholder="********"
            error={errors.password?.message}
            autoComplete="current-password"
          />

          {error && (
            <div className="p-3 bg-red-50 text-red-600 rounded-lg text-sm">
              {error}
            </div>
          )}

          <Button type="submit" className="w-full" loading={loading}>
            Войти
          </Button>
        </form>

        <p className="text-center text-gray-400 text-sm mt-8">
          Версия: 1.0.0-web
        </p>
      </div>
    </div>
  );
};
