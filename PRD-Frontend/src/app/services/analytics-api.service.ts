import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface AnalyticsDTO {
  totalAlerts: number;
  activeAlerts: number;
  readCount: number;
  unreadCount: number;
}

@Injectable({
  providedIn: 'root'
})
export class AnalyticsApiService {
  private readonly API_BASE = 'http://localhost:8080/api/analytics';

  constructor(private http: HttpClient) { }

  getSummary() {
    return this.http.get<AnalyticsDTO>(`${this.API_BASE}/summary`);
  }
}
