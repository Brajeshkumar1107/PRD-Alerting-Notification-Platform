import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnalyticsApiService, AnalyticsDTO } from '../../../services/analytics-api.service';

@Component({
  selector: 'app-analytics-dashboard',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container">
      <h2 style="margin:0 0 12px;">Analytics</h2>
      <div class="grid" *ngIf="data; else loading">
        <div class="card">Total Alerts: <b>{{ data.totalAlerts }}</b></div>
        <div class="card">Active Alerts: <b>{{ data.activeAlerts }}</b></div>
        <div class="card">Read: <b>{{ data.readCount }}</b></div>
        <div class="card">Unread: <b>{{ data.unreadCount }}</b></div>
      </div>
      <ng-template #loading><div class="card">Loading...</div></ng-template>
    </div>
  `,
  styles: `
    .grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 12px; }
  `
})
export class AnalyticsDashboardComponent implements OnInit {
  data?: AnalyticsDTO;
  constructor(private api: AnalyticsApiService) {}
  ngOnInit() { this.api.getSummary().subscribe(res => this.data = res); }
}
