import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface UserAlertDTO {
  alertId: number;
  title: string;
  message: string;
  severity: string | null;
  deliveredAt: string | null;
  read: boolean;
  snoozed: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class UserApiService {
  private readonly API_BASE = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  getAlerts(userId: number) {
    return this.http.get<UserAlertDTO[]>(`${this.API_BASE}/${userId}/alerts`);
  }

  markRead(userId: number, alertId: number) {
    return this.http.put<void>(`${this.API_BASE}/${userId}/alerts/${alertId}/read`, {});
  }

  markUnread(userId: number, alertId: number) {
    return this.http.put<void>(`${this.API_BASE}/${userId}/alerts/${alertId}/unread`, {});
  }

  snooze(userId: number, alertId: number) {
    return this.http.put<void>(`${this.API_BASE}/${userId}/alerts/${alertId}/snooze`, {});
  }
}
