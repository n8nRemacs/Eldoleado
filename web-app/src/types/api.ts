export interface ApiResponse<T> {
  success: boolean;
  error?: string;
  data?: T;
}

export interface DeviceInfo {
  device_id: string;
  device_model: string;
  os_version: string;
  app_version: string;
}

export interface LoginRequest {
  login: string;
  password: string;
  device_info: DeviceInfo;
  app_mode: 'client' | 'server' | 'both';
}

export interface LoginResponse {
  success: boolean;
  operator_id: string;
  tenant_id: string;
  name: string;
  email?: string;
  session_token: string;
  app_mode: string;
  allowed_channels?: string[];
  channel_statuses?: Record<string, string>;
}

export interface SendMessageRequest {
  session_token: string;
  dialog_id: string;
  text: string;
  response_channel?: string;
}

export interface NormalizeRequest {
  session_token: string;
  dialog_id: string;
  text: string;
}

export interface NormalizeResponse {
  success: boolean;
  normalized_text: string;
}
