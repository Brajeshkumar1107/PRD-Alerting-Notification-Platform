import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserApiService, UserAlertDTO } from '../../services/user-api.service';

@Component({
  selector: 'app-alerts-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="toolbar" style="display:flex; align-items:center; gap:12px; margin-bottom: 12px;">
      <h2 style="margin:0;">My Alerts</h2>
      <span style="margin-left:auto;"><button class="button ghost" (click)="refresh()">Refresh</button></span>
    </div>
    <div class="list" *ngIf="alerts.length; else empty">
      <div class="item card" *ngFor="let a of alerts">
        <div class="title">
          {{ a.title }} <small *ngIf="a.severity" style="color:var(--text-muted)">({{ a.severity }})</small>
        </div>
        <div class="msg" style="margin-top:6px;">{{ a.message }}</div>
        <div class="meta" style="margin-top:6px;">
          <span>Delivered: {{ a.deliveredAt || '—' }}</span>
          <span>Status: {{ a.read ? 'Read' : 'Unread' }}<span *ngIf="a.snoozed">, Snoozed</span></span>
        </div>
        <div class="actions">
          <button class="button success" (click)="markRead(a)" [disabled]="a.read">Mark read</button>
          <button class="button ghost" (click)="markUnread(a)" [disabled]="!a.read">Mark unread</button>
          <button class="button" (click)="snooze(a)">Snooze today</button>
        </div>
      </div>
    </div>
    <ng-template #empty>
      <div class="card" style="text-align:center; padding:24px;">No alerts.</div>
    </ng-template>
  `,
  styles: `
    .actions { display: flex; flex-wrap: wrap; gap: 8px; margin-top: 10px; }
  `
})
export class AlertsListComponent implements OnInit {
  alerts: UserAlertDTO[] = [];
  userId = 1; // demo; replace with actual user context if needed

  constructor(private userApi: UserApiService) {}

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    this.userApi.getAlerts(this.userId).subscribe(alerts => this.alerts = alerts);
  }

  markRead(a: UserAlertDTO) {
    this.userApi.markRead(this.userId, a.alertId).subscribe(() => {
      a.read = true;
    });
  }

  markUnread(a: UserAlertDTO) {
    this.userApi.markUnread(this.userId, a.alertId).subscribe(() => {
      a.read = false;
    });
  }

  snooze(a: UserAlertDTO) {
    this.userApi.snooze(this.userId, a.alertId).subscribe(() => {
      a.snoozed = true;
    });
  }
}
