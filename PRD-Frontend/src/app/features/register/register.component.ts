import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../core/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="container" style="display:grid; place-items:center; min-height: calc(100vh - 64px);">
      <form (ngSubmit)="onSubmit()" class="card" style="width:420px; display:grid; gap:10px;">
        <h2 style="margin:0;">Create account</h2>
        <p style="color:var(--text-muted); margin:0 0 8px;">Sign up to receive alerts</p>
        <label>Name</label>
        <input name="name" [(ngModel)]="name" required />
        <label>Email</label>
        <input name="email" [(ngModel)]="email" type="email" required />
        <label>Password</label>
        <input name="password" [(ngModel)]="password" type="password" required />
        <label>Team ID</label>
        <input name="teamId" [(ngModel)]="teamId" type="number" required />
        <button class="button" type="submit" [disabled]="loading">{{ loading ? 'Creating...' : 'Register' }}</button>
        <p style="color:var(--danger);" *ngIf="error">{{ error }}</p>
        <p style="color:var(--text-muted);">Already have an account? <a routerLink="/login">Login</a></p>
      </form>
    </div>
  `,
  styles: ``
})
export class RegisterComponent {
  name = '';
  email = '';
  password = '';
  teamId: number | null = null;
  loading = false;
  error = '';

  constructor(private auth: AuthService, private router: Router) {}

  onSubmit() {
    if (this.loading || this.teamId === null) return;
    this.loading = true;
    this.error = '';
    this.auth.register({ name: this.name, email: this.email, password: this.password, teamId: this.teamId })
      .subscribe({
        next: res => { this.auth.storeToken(res.token); this.router.navigateByUrl('/'); },
        error: () => { this.error = 'Registration failed'; this.loading = false; }
      });
  }
}
