import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../model/loginrequest';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  username = '';
  password = '';

  loading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.username || !this.password) {
      this.errorMessage = 'Username and password are required.';
      return;
    }

    const loginRequest: LoginRequest = {
      username: this.username,
      password: this.password
    };

    this.loading = true;

    this.authService.login(loginRequest).subscribe({
      next: () => {
        this.loading = false;
        this.successMessage = 'Login successful.';

        setTimeout(() => {
          this.router.navigate(['/dashboard']);
        }, 500);
      },
      error: (error) => {
        this.loading = false;
        console.log('Login error:', error);

        if (error.status === 0) {
          this.errorMessage = 'Backend server is not reachable.';
        } else if (error.status === 401 || error.status === 403) {
          this.errorMessage = 'Invalid username or password.';
        } else {
          this.errorMessage = 'Login failed. Please try again.';
        }
      }
    });
  }

  resetForm(): void {
    this.username = '';
    this.password = '';
    this.errorMessage = '';
    this.successMessage = '';
  }
}