import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { User, Role } from '../../model/user';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  username = '';
  email = '';
  contactNumber: number | null = null;
  password = '';

  role: Role = Role.USER;
  roles = Object.values(Role);

  loading = false;
  submitted = false;

  errorMessage = '';
  successMessage = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  register(): void {
    this.submitted = true;
    this.errorMessage = '';
    this.successMessage = '';

    if (!this.username || !this.email || !this.password || !this.contactNumber || !this.role) {
      this.errorMessage = 'Please fill all required fields.';
      return;
    }

    const user: User = {
      username: this.username,
      email: this.email,
      contactNumber: this.contactNumber,
      password: this.password,
      role: this.role
    };

    this.loading = true;

    this.authService.register(user).subscribe({
      next: () => {
        this.loading = false;
        this.successMessage = 'Registration successful. Please login.';

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 1000);
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Registration failed. Username/email/contact may already exist.';
      }
    });
  }

  resetForm(): void {
    this.username = '';
    this.email = '';
    this.contactNumber = null;
    this.password = '';
    this.role = Role.USER;
    this.submitted = false;
    this.errorMessage = '';
    this.successMessage = '';
  }
}