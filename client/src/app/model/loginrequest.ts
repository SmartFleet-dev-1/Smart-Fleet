export interface LoginRequest {
  username?: string;
  password?: string;
  captchaId?: string;
  captchaAnswer?: string;
}