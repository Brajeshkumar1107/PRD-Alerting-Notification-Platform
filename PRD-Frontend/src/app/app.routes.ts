import { Routes } from '@angular/router';
import { authGuard } from './core/auth.guard';
import { LoginComponent } from './features/login/login.component';
import { AlertsListComponent } from './features/alerts-list/alerts-list.component';
import { AlertFormComponent } from './features/admin/alert-form/alert-form.component';
import { AnalyticsDashboardComponent } from './features/analytics/analytics-dashboard/analytics-dashboard.component';
import { RegisterComponent } from './features/register/register.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '', pathMatch: 'full', redirectTo: 'alerts' },
  { path: 'alerts', component: AlertsListComponent, canActivate: [authGuard] },
  { path: 'admin/alerts/new', component: AlertFormComponent, canActivate: [authGuard] },
  { path: 'admin/alerts/:id', component: AlertFormComponent, canActivate: [authGuard] },
  { path: 'analytics', component: AnalyticsDashboardComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: 'alerts' }
];
