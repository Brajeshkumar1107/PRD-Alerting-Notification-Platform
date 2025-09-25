import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="container" style="display:grid; place-items:center; min-height: calc(100vh - 64px);">
      <form (ngSubmit)="onSubmit()" #f="ngForm" class="card" style="width:360px; display:grid; gap:10px;">
        <h2 style="margin:0;">Sign in</h2>
        <p style="color:var(--text-muted); margin:0 0 8px;">Use your email and password</p>
        <label>Email</label>
        <input name="email" [(ngModel)]="email" type="email" required />
        <label>Password</label>
        <input name="password" [(ngModel)]="password" type="password" required />
        <button class="button" type="submit" [disabled]="loading">{{ loading ? 'Signing in...' : 'Login' }}</button>
        <p style="color:var(--danger);" *ngIf="error">{{ error }}</p>
        <p style="color:var(--text-muted);">New here? <a routerLink="/register">Create an account</a></p>
      </form>
    </div>
  `,
  styles: ``
})
export class LoginComponent {
  email = '';
  password = '';
  loading = false;
  error = '';

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit() {
    if (this.loading) return;
    this.loading = true;
    this.error = '';
    this.auth.login(this.email, this.password).subscribe({
      next: res => {
        this.auth.storeToken(res.token);
        this.router.navigateByUrl('/');
      },
      error: () => {
        this.error = 'Invalid credentials';
        this.loading = false;
      }
    });
  }
}
