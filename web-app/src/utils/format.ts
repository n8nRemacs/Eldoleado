import { format, isToday, isYesterday } from 'date-fns';
import { ru } from 'date-fns/locale';

export const formatMessageTime = (timestamp: number | string | null | undefined): string => {
  if (!timestamp) return '';

  const ts = Number(timestamp);
  if (isNaN(ts)) return '';

  const date = new Date(ts);
  if (isNaN(date.getTime())) return '';

  if (isToday(date)) {
    return format(date, 'HH:mm');
  }

  if (isYesterday(date)) {
    return 'вчера';
  }

  return format(date, 'd MMM', { locale: ru });
};

export const formatTime = (timestamp: number | string | null | undefined): string => {
  if (!timestamp) return '';
  const ts = Number(timestamp);
  if (isNaN(ts)) return '';
  return format(new Date(ts), 'HH:mm');
};

export const formatDate = (timestamp: number | string | null | undefined): string => {
  if (!timestamp) return '';
  const ts = Number(timestamp);
  if (isNaN(ts)) return '';
  return format(new Date(ts), 'd MMMM yyyy', { locale: ru });
};

export const truncateText = (text: string, maxLength: number): string => {
  if (!text) return '';
  if (text.length <= maxLength) return text;
  return text.slice(0, maxLength) + '...';
};

export const formatPhone = (phone: string): string => {
  if (!phone) return '';
  // Simple formatting for Russian phones
  const cleaned = phone.replace(/\D/g, '');
  if (cleaned.length === 11 && cleaned.startsWith('7')) {
    return `+7 ${cleaned.slice(1, 4)} ${cleaned.slice(4, 7)}-${cleaned.slice(7, 9)}-${cleaned.slice(9)}`;
  }
  return phone;
};
