import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminApiService, AlertRequestDTO } from '../../../services/admin-api.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-alert-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="container">
      <form (ngSubmit)="save()" class="card" style="display:grid; gap: 10px; max-width: 720px;">
        <h2 style="margin:0;">{{ alertId ? 'Edit' : 'Create' }} Alert</h2>
        <p style="color:var(--text-muted); margin:0 0 8px;">Define content, severity, delivery and scheduling.</p>
        <label>Title</label>
        <input [(ngModel)]="model.title" name="title" required />

        <label>Message</label>
        <textarea [(ngModel)]="model.message" name="message" required></textarea>

        <div style="display:grid; grid-template-columns: 1fr 1fr; gap: 12px;">
          <div>
            <label>Severity</label>
            <select [(ngModel)]="model.severity" name="severity">
              <option>INFO</option>
              <option>WARNING</option>
              <option>CRITICAL</option>
            </select>
          </div>
          <div>
            <label>Delivery Type</label>
            <select [(ngModel)]="model.deliveryType" name="deliveryType">
              <option>IN_APP</option>
              <option>EMAIL</option>
              <option>SMS</option>
            </select>
          </div>
        </div>

        <div style="display:grid; grid-template-columns: 1fr 1fr; gap: 12px;">
          <div>
            <label>Start</label>
            <input type="datetime-local" [(ngModel)]="model.startTime" name="startTime" />
          </div>
          <div>
            <label>Expiry</label>
            <input type="datetime-local" [(ngModel)]="model.expiryTime" name="expiryTime" />
          </div>
        </div>

        <label><input type="checkbox" [(ngModel)]="model.remindersEnabled" name="remindersEnabled" /> Reminders enabled</label>

        <div class="actions">
          <button class="button" type="submit">Save</button>
          <button class="button ghost" type="button" (click)="back()">Cancel</button>
        </div>
      </form>
    </div>
  `,
  styles: `
    .actions { display:flex; gap: 8px; }
  `
})
export class AlertFormComponent {
  alertId?: number;
  model: AlertRequestDTO = {
    title: '',
    message: '',
    severity: 'INFO',
    deliveryType: 'IN_APP',
    startTime: '',
    expiryTime: '',
    remindersEnabled: true
  };

  constructor(private adminApi: AdminApiService, private route: ActivatedRoute, private router: Router) {
    const id = this.route.snapshot.paramMap.get('id');
    this.alertId = id ? Number(id) : undefined;
  }

  save() {
    const payload = { ...this.model };
    if (this.alertId) {
      this.adminApi.updateAlert(this.alertId, payload).subscribe(() => this.back());
    } else {
      this.adminApi.createAlert(payload).subscribe(() => this.back());
    }
  }

  back() { this.router.navigateByUrl('/alerts'); }
}
