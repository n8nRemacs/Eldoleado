import type { DeviceInfo } from '../types';

export const getDeviceInfo = (): DeviceInfo => {
  const ua = navigator.userAgent;
  const browser = getBrowser(ua);
  const os = getOS(ua);

  return {
    device_id: getDeviceId(),
    device_model: `${browser} / ${os}`,
    os_version: os,
    app_version: '1.0.0-web',
  };
};

const getDeviceId = (): string => {
  let id = localStorage.getItem('device_id');
  if (!id) {
    id = crypto.randomUUID();
    localStorage.setItem('device_id', id);
  }
  return id;
};

const getBrowser = (ua: string): string => {
  if (ua.includes('Edg')) return 'Edge';
  if (ua.includes('Chrome')) return 'Chrome';
  if (ua.includes('Firefox')) return 'Firefox';
  if (ua.includes('Safari')) return 'Safari';
  if (ua.includes('Opera') || ua.includes('OPR')) return 'Opera';
  if (ua.includes('YaBrowser')) return 'Yandex';
  return 'Browser';
};

const getOS = (ua: string): string => {
  if (ua.includes('Windows NT 10')) return 'Windows 10';
  if (ua.includes('Windows NT 11')) return 'Windows 11';
  if (ua.includes('Windows')) return 'Windows';
  if (ua.includes('Mac OS X')) return 'macOS';
  if (ua.includes('Linux')) return 'Linux';
  if (ua.includes('Android')) return 'Android';
  if (ua.includes('iPhone') || ua.includes('iPad')) return 'iOS';
  return 'Unknown';
};
