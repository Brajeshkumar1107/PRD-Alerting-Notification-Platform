# PRD Frontend - Angular Alert Dashboard

A modern Angular 17 frontend application for the PRD Alert & Notification Platform, featuring a responsive dark theme and comprehensive alert management capabilities.

## 🚀 Quick Start

### Prerequisites
- Node.js 18+ and npm
- Angular CLI 17+
- Backend API running on `http://localhost:8080`

### Setup Instructions

1. **Install Dependencies**
   ```bash
   cd PRD-Frontend
   npm install
   ```

2. **Start Development Server**
   ```bash
   npm start
   # or
   ng serve
   ```
   Application will be available at: `http://localhost:4200`

3. **Build for Production**
   ```bash
   npm run build
   # or
   ng build --configuration production
   ```

## 🎨 Features

### Core Functionality
- ✅ **User Authentication**: Login and registration with JWT
- ✅ **Alert Management**: Create, view, and manage alerts
- ✅ **User Dashboard**: View personal alerts with read/unread states
- ✅ **Snooze Functionality**: Snooze alerts for the current day
- ✅ **Analytics Dashboard**: System-wide metrics and insights
- ✅ **Responsive Design**: Mobile-friendly dark theme

### UI Components
- **Modern Dark Theme**: Professional dark color scheme
- **Responsive Layout**: Works on desktop, tablet, and mobile
- **Interactive Cards**: Clean card-based design for alerts
- **Form Validation**: Real-time form validation with error messages
- **Loading States**: Smooth loading indicators
- **Navigation**: Intuitive navigation with active states

## 📱 Pages & Routes

### Public Routes
- `/login` - User login page
- `/register` - User registration page

### Protected Routes (Require Authentication)
- `/alerts` - User's personal alert dashboard
- `/admin/alerts/new` - Create new alert (Admin/User)
- `/analytics` - Analytics dashboard

## 🔧 Configuration

### API Configuration
The frontend is configured to connect to the backend API at `http://localhost:8080`. To change this:

1. **Update API Base URL** in `src/app/core/auth.service.ts`:
   ```typescript
   private readonly API_BASE = 'http://your-backend-url:8080';
   ```

2. **Update CORS Settings** in backend `SecurityConfig.java` if needed.

### Environment Configuration
Create `src/environments/environment.ts` for different environments:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080',
  appName: 'PRD Alert Platform'
};
```

## 🎯 Usage Guide

### 1. Getting Started
1. **Start Backend**: Ensure the Spring Boot backend is running
2. **Seed Data**: Run `POST http://localhost:8080/demo/seed` to populate test data
3. **Start Frontend**: Run `npm start` in the PRD-Frontend directory
4. **Login**: Use seeded credentials or register a new account

### 2. Test Credentials (After Seeding)
- **Admin**: `admin@example.com` / `password`
- **Users**: `john@example.com`, `jane@example.com`, `alice@example.com`, `bob@example.com`, `charlie@example.com` / `password`

### 3. Navigation Flow
1. **Login** → Enter credentials
2. **Alerts Dashboard** → View your personal alerts
3. **Create Alert** → Create new alerts (if you have permissions)
4. **Analytics** → View system metrics
5. **Logout** → Clear session and return to login

## 🏗️ Project Structure

```
src/
├── app/
│   ├── core/                    # Core services and guards
│   │   ├── auth.service.ts      # Authentication service
│   │   ├── auth.guard.ts        # Route protection
│   │   └── api.interceptor.ts   # HTTP interceptor for JWT
│   ├── features/                # Feature modules
│   │   ├── login/               # Login component
│   │   ├── register/            # Registration component
│   │   ├── alerts-list/         # User alerts dashboard
│   │   ├── admin/               # Admin features
│   │   │   └── alert-form/      # Alert creation form
│   │   └── analytics/           # Analytics dashboard
│   │       └── analytics-dashboard/
│   ├── services/                # API services
│   │   ├── admin-api.service.ts # Admin API calls
│   │   ├── user-api.service.ts  # User API calls
│   │   └── analytics-api.service.ts # Analytics API calls
│   ├── app.component.ts         # Root component
│   ├── app.component.html       # Root template
│   ├── app.routes.ts           # Route configuration
│   └── app.config.ts           # App configuration
├── styles.scss                  # Global styles
└── index.html                  # Main HTML file
```

## 🎨 Styling & Theme

### CSS Variables
The application uses CSS custom properties for theming:

```scss
:root {
  --primary: #4f46e5;
  --primary-600: #4338ca;
  --background: #0b1221;
  --surface: #1a2332;
  --text: #e2e8f0;
  --text-muted: #94a3b8;
  --border: #334155;
  --success: #22c55e;
  --warning: #f59e0b;
  --danger: #ef4444;
}
```

### Component Styles
- **Cards**: Clean card design with subtle shadows
- **Buttons**: Multiple variants (primary, ghost, danger, success)
- **Forms**: Styled inputs with focus states
- **Navigation**: Modern navbar with active states

## 🔌 API Integration

### Services Overview
- **AuthService**: Handles login, registration, and token management
- **AdminApiService**: Admin-specific API calls
- **UserApiService**: User-specific API calls
- **AnalyticsApiService**: Analytics data retrieval

### HTTP Interceptor
Automatically adds JWT token to all authenticated requests:

```typescript
// Automatically adds: Authorization: Bearer <token>
```

### Error Handling
- **401 Unauthorized**: Redirects to login page
- **403 Forbidden**: Shows error message
- **Network Errors**: Displays user-friendly error messages

## 🧪 Testing

### Manual Testing Checklist
1. **Authentication Flow**
   - [ ] Login with valid credentials
   - [ ] Registration creates new user
   - [ ] Invalid credentials show error
   - [ ] Logout clears session

2. **Alert Management**
   - [ ] View personal alerts
   - [ ] Mark alerts as read/unread
   - [ ] Snooze alerts
   - [ ] Create new alerts (if permitted)

3. **Navigation**
   - [ ] Protected routes redirect to login when not authenticated
   - [ ] Navigation links work correctly
   - [ ] Active route highlighting works

4. **Responsive Design**
   - [ ] Works on mobile devices
   - [ ] Cards stack properly on small screens
   - [ ] Forms are usable on touch devices

### Automated Testing
```bash
# Run unit tests
npm test

# Run e2e tests
npm run e2e
```

## 🚀 Deployment

### Build for Production
```bash
npm run build
```

### Docker Deployment (Optional)
```dockerfile
FROM nginx:alpine
COPY dist/prd-frontend /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

### Environment Variables
For production deployment, consider using environment variables:

```bash
# .env.production
VITE_API_URL=https://your-api-domain.com
VITE_APP_NAME=PRD Alert Platform
```

## 🔧 Development

### Adding New Features
1. **Create Component**: `ng generate component features/feature-name`
2. **Add Route**: Update `app.routes.ts`
3. **Create Service**: Add API service if needed
4. **Update Navigation**: Add links to `app.component.html`

### Code Style
- **TypeScript**: Strict mode enabled
- **Angular**: Standalone components
- **SCSS**: BEM methodology for CSS classes
- **Linting**: ESLint and Prettier configured

### Performance Optimization
- **Lazy Loading**: Routes are lazy-loaded
- **OnPush Strategy**: Components use OnPush change detection
- **Tree Shaking**: Unused code is eliminated in production builds

## 🐛 Troubleshooting

### Common Issues

1. **CORS Errors**
   - Ensure backend CORS is configured for `http://localhost:4200`
   - Check `SecurityConfig.java` in backend

2. **Authentication Issues**
   - Verify JWT token is being stored in localStorage
   - Check if token is being sent in requests
   - Ensure backend is running and accessible

3. **API Connection Issues**
   - Verify backend is running on `http://localhost:8080`
   - Check network connectivity
   - Review browser console for errors

4. **Build Issues**
   - Clear node_modules: `rm -rf node_modules && npm install`
   - Update Angular CLI: `npm install -g @angular/cli@17`
   - Check Node.js version compatibility

### Debug Mode
Enable debug logging in `auth.service.ts`:

```typescript
// Add console.log statements for debugging
console.log('Token stored:', token);
console.log('API request to:', url);
```

## 📚 Additional Resources

### Angular Documentation
- [Angular 17 Guide](https://angular.io/guide)
- [Angular CLI Reference](https://angular.io/cli)
- [Angular Material](https://material.angular.io/)

### Backend Integration
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JWT Authentication](https://jwt.io/)
- [REST API Best Practices](https://restfulapi.net/)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Built with ❤️ using Angular 17, TypeScript, and SCSS**