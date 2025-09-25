import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

export interface AlertRequestDTO {
  title: string;
  message: string;
  severity: string;
  deliveryType: string;
  startTime: string;
  expiryTime: string;
  remindersEnabled: boolean;
  teamIds?: number[];
  userIds?: number[];
}

export interface AlertResponseDTO {
  id: number;
  title: string;
  message: string;
  severity: string;
  deliveryType: string;
  startTime: string;
  expiryTime: string;
  active: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class AdminApiService {
  private readonly API_BASE = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient) { }

  createAlert(payload: AlertRequestDTO) {
    return this.http.post<AlertResponseDTO>(`${this.API_BASE}/alerts`, payload);
  }

  updateAlert(alertId: number, payload: AlertRequestDTO) {
    return this.http.put<AlertResponseDTO>(`${this.API_BASE}/alerts/${alertId}`, payload);
  }

  archiveAlert(alertId: number) {
    return this.http.delete(`${this.API_BASE}/alerts/${alertId}`, { responseType: 'text' });
  }

  listAlerts(params: { severity?: string; status?: string; teamIds?: number[]; userIds?: number[] }) {
    let httpParams = new HttpParams();
    if (params.severity) httpParams = httpParams.set('severity', params.severity);
    if (params.status) httpParams = httpParams.set('status', params.status);
    if (params.teamIds?.length) httpParams = httpParams.set('teamIds', params.teamIds.join(','));
    if (params.userIds?.length) httpParams = httpParams.set('userIds', params.userIds.join(','));
    return this.http.get<AlertResponseDTO[]>(`${this.API_BASE}/alerts`, { params: httpParams });
  }
}
