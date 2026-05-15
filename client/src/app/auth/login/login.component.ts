import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../model/loginrequest';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  username = '';
  password = '';

  captchaId = '';
  captchaQuestion = '';
  captchaAnswer = '';

  loading = false;
  captchaLoading = false;

  errorMessage = '';
  successMessage = '';

  isLoginLocked = false;
  lockCountdown = '';
  private lockTimer: any;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCaptcha();
    this.restoreLockState();
  }

  ngOnDestroy(): void {
    if (this.lockTimer) {
      clearInterval(this.lockTimer);
    }
  }

  loadCaptcha(): void {
    this.captchaLoading = true;
    this.captchaAnswer = '';

    this.authService.getCaptcha().subscribe({
      next: (response) => {
        this.captchaLoading = false;
        this.captchaId = response.captchaId || '';
        this.captchaQuestion = response.question || '';
      },
      error: (error) => {
        this.captchaLoading = false;
        console.log('Captcha error:', error);
        this.errorMessage = 'Unable to load captcha. Please refresh the page.';
      }
    });
  }

  login(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (this.isLoginLocked) {
      this.errorMessage = `Account locked. Try again in ${this.lockCountdown}.`;
      return;
    }

    if (!this.username || !this.password || !this.captchaAnswer) {
      this.errorMessage = 'Username, password and captcha are required.';
      return;
    }

    if (!this.captchaId) {
      this.errorMessage = 'Captcha is not loaded. Please refresh captcha.';
      return;
    }

    const loginRequest: LoginRequest = {
      username: this.username,
      password: this.password,
      captchaId: this.captchaId,
      captchaAnswer: this.captchaAnswer
    };

    this.loading = true;

    this.authService.login(loginRequest).subscribe({
      next: () => {
        this.loading = false;
        this.errorMessage = '';
        this.successMessage = 'Login successful.';
        this.clearLockState();

        setTimeout(() => {
          this.router.navigate(['/dashboard']);
        }, 500);
      },
      error: (error) => {
        this.loading = false;

        console.log('Login error full object:', error);
        console.log('Backend response:', error.error);

        const backendMessage =
          error?.error?.message ||
          error?.message ||
          '';

        if (error.status === 0) {
          this.errorMessage = 'Backend server is not reachable.';
        } else if (error.status === 400) {
          this.errorMessage = backendMessage || 'Invalid captcha. Please try again.';
          this.loadCaptcha();
        } else if (error.status === 401) {
          this.errorMessage = backendMessage || 'Invalid username or password.';
          this.loadCaptcha();
        } else if (error.status === 403) {
          this.errorMessage = backendMessage || 'Login blocked by backend security.';
          this.loadCaptcha();
        } else if (error.status === 423) {
          this.startLoginLock(5);
          this.errorMessage = backendMessage || 'Account is locked. Please try again after 5 minutes.';
          this.loadCaptcha();
        } else if (error.status === 500) {
          this.errorMessage = backendMessage || 'Backend server error during login.';
          this.loadCaptcha();
        } else {
          this.errorMessage = backendMessage || 'Login failed. Please try again.';
          this.loadCaptcha();
        }
      }
    });
  }

  resetForm(): void {
    this.username = '';
    this.password = '';
    this.captchaAnswer = '';
    this.errorMessage = '';
    this.successMessage = '';
    this.loadCaptcha();
  }

  private startLoginLock(minutes: number): void {
    const unlockTime = Date.now() + minutes * 60 * 1000;

    localStorage.setItem('loginLockedUntil', unlockTime.toString());

    this.isLoginLocked = true;
    this.updateCountdown(unlockTime);

    if (this.lockTimer) {
      clearInterval(this.lockTimer);
    }

    this.lockTimer = setInterval(() => {
      this.updateCountdown(unlockTime);
    }, 1000);
  }

  private restoreLockState(): void {
    const lockedUntil = localStorage.getItem('loginLockedUntil');

    if (!lockedUntil) {
      return;
    }

    const unlockTime = Number(lockedUntil);

    if (unlockTime > Date.now()) {
      this.isLoginLocked = true;
      this.updateCountdown(unlockTime);

      this.lockTimer = setInterval(() => {
        this.updateCountdown(unlockTime);
      }, 1000);
    } else {
      this.clearLockState();
    }
  }

  private updateCountdown(unlockTime: number): void {
    const remainingMs = unlockTime - Date.now();

    if (remainingMs <= 0) {
      this.clearLockState();
      this.errorMessage = '';
      this.loadCaptcha();
      return;
    }

    const totalSeconds = Math.ceil(remainingMs / 1000);
    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;

    this.lockCountdown =
      `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

    this.errorMessage = `Account locked. Try again in ${this.lockCountdown}.`;
  }

  private clearLockState(): void {
    this.isLoginLocked = false;
    this.lockCountdown = '';
    localStorage.removeItem('loginLockedUntil');

    if (this.lockTimer) {
      clearInterval(this.lockTimer);
      this.lockTimer = null;
    }
  }
}
``