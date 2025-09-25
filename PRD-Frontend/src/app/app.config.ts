import { ApplicationConfig } from '@angular/core';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { HTTP_INTERCEPTORS, provideHttpClient, withInterceptors } from '@angular/common/http';

import { routes } from './app.routes';
import { apiInterceptor } from './core/api.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes, withComponentInputBinding()),
    provideHttpClient(withInterceptors([apiInterceptor]))
  ]
};
