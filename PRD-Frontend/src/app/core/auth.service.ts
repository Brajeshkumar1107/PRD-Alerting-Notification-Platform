import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface AuthResponse { token: string }
interface RegisterPayload { name: string; email: string; password: string; teamId: number }

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly API_BASE = 'http://localhost:8080';
  private readonly TOKEN_KEY = 'jwt_token';

  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string) {
    return this.http.post<AuthResponse>(`${this.API_BASE}/auth/login`, { username, password });
  }

  register(payload: RegisterPayload) {
    return this.http.post<AuthResponse>(`${this.API_BASE}/auth/register`, payload);
  }

  storeToken(token: string) {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  logout() {
    localStorage.removeItem(this.TOKEN_KEY);
    this.router.navigateByUrl('/login');
  }
}
