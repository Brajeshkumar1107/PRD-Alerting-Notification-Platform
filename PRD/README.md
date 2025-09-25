# PRD - Alert & Notification Platform

A comprehensive Spring Boot backend for managing alerts and notifications with role-based access control, reminder scheduling, and analytics.

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Setup Instructions

1. **Clone and Navigate**
   ```bash
   git clone <repository-url>
   cd PRD
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE prd_alerts;
   CREATE USER 'prd_user'@'localhost' IDENTIFIED BY 'prd_password';
   GRANT ALL PRIVILEGES ON prd_alerts.* TO 'prd_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Configure Application**
   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/prd_alerts
   spring.datasource.username=prd_user
   spring.datasource.password=prd_password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Run Application**
   ```bash
   mvn spring-boot:run
   ```
   Server starts at: `http://localhost:8080`

5. **Seed Data** (Optional)
   ```bash
   curl -X POST http://localhost:8080/demo/seed
   ```

## 📋 API Documentation

### Authentication

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "username": "admin@example.com",
  "password": "admin123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Register
```http
POST /auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "teamId": 1
}
```

### Admin APIs

#### 1. Create Alert
```http
POST /api/admin/alerts
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "System Maintenance",
  "message": "Scheduled maintenance on Sunday 2AM-4AM",
  "severity": "WARNING",
  "deliveryType": "IN_APP",
  "startTime": "2024-01-15T09:00:00",
  "expiryTime": "2024-01-20T18:00:00",
  "remindersEnabled": true,
  "teamIds": [1, 2],
  "userIds": [3, 4]
}
```

#### 2. Update Alert
```http
PUT /api/admin/alerts/{alertId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Updated Alert Title",
  "message": "Updated message content",
  "severity": "CRITICAL",
  "deliveryType": "IN_APP",
  "startTime": "2024-01-15T09:00:00",
  "expiryTime": "2024-01-20T18:00:00",
  "remindersEnabled": false,
  "teamIds": [1],
  "userIds": []
}
```

#### 3. List Alerts (with filtering)
```http
GET /api/admin/alerts?severity=WARNING&status=active&teamIds=1,2&userIds=3,4
Authorization: Bearer <token>
```

**Query Parameters:**
- `severity`: INFO, WARNING, CRITICAL
- `status`: active, expired
- `teamIds`: comma-separated team IDs
- `userIds`: comma-separated user IDs

#### 4. Archive Alert
```http
DELETE /api/admin/alerts/{alertId}
Authorization: Bearer <token>
```

#### 5. Create User
```http
POST /api/admin/alerts/users
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Jane Smith",
  "email": "jane@example.com",
  "teamId": 2
}
```

### User APIs

#### 1. Fetch User Alerts
```http
GET /api/users/{userId}/alerts
Authorization: Bearer <token>
```

**Response:**
```json
[
  {
    "alertId": 1,
    "title": "System Maintenance",
    "message": "Scheduled maintenance on Sunday 2AM-4AM",
    "severity": "WARNING",
    "deliveredAt": "2024-01-15T09:00:00",
    "read": false,
    "snoozed": false
  }
]
```

#### 2. Mark Alert as Read
```http
PUT /api/users/{userId}/alerts/{alertId}/read
Authorization: Bearer <token>
```

#### 3. Mark Alert as Unread
```http
PUT /api/users/{userId}/alerts/{alertId}/unread
Authorization: Bearer <token>
```

#### 4. Snooze Alert
```http
PUT /api/users/{userId}/alerts/{alertId}/snooze
Authorization: Bearer <token>
```

### Analytics API

#### Get Analytics Summary
```http
GET /api/analytics/summary
Authorization: Bearer <token>
```

**Response:**
```json
{
  "totalAlerts": 15,
  "activeAlerts": 8,
  "readCount": 45,
  "unreadCount": 12,
  "lastUpdated": "2024-01-15T10:30:00"
}
```

## 🗄️ Database Schema

### Core Tables
- **users**: User information and team association
- **teams**: Team definitions
- **alerts**: Alert definitions with metadata
- **alert_teams**: Many-to-many relationship for team targeting
- **alert_users**: Many-to-many relationship for user targeting
- **user_alert_preferences**: User-specific alert states (read/unread/snoozed)
- **notification_deliveries**: Delivery tracking and timestamps

### Key Relationships
- User belongs to Team (Many-to-One)
- Alert can target multiple Teams (Many-to-Many)
- Alert can target multiple Users (Many-to-Many)
- UserAlertPreference tracks user-specific alert states
- NotificationDelivery logs each alert delivery

## 🔧 Configuration

### Environment Variables
```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=prd_alerts
export DB_USERNAME=prd_user
export DB_PASSWORD=prd_password
export JWT_SECRET=your-secret-key
```

### Application Properties
```properties
# Database
spring.datasource.url=jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:prd_alerts}
spring.datasource.username=${DB_USERNAME:prd_user}
spring.datasource.password=${DB_PASSWORD:prd_password}

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# JWT
jwt.secret=${JWT_SECRET:mySecretKey}
jwt.expiration=86400000

# CORS
cors.allowed-origins=http://localhost:4200
```

## 🚦 Features

### Core Functionality
- ✅ **Alert Management**: Create, update, archive alerts
- ✅ **Visibility Control**: Organization, team, and user-level targeting
- ✅ **Reminder System**: Configurable frequency (default 2 hours)
- ✅ **Snooze Management**: Daily snooze with automatic reset
- ✅ **Read/Unread States**: User preference tracking
- ✅ **Analytics Dashboard**: Comprehensive metrics and reporting

### Technical Features
- ✅ **JWT Authentication**: Secure token-based auth
- ✅ **Role-based Access**: Admin and User roles
- ✅ **CORS Support**: Frontend integration ready
- ✅ **Scheduled Tasks**: Automated reminder delivery
- ✅ **Data Validation**: Bean validation with custom constraints
- ✅ **Extensible Architecture**: Strategy pattern for notification channels

## 🧪 Testing

### Seed Data
The application includes a demo controller for seeding test data:

```bash
# Seed users, teams, and sample alerts
curl -X POST http://localhost:8080/demo/seed
```

### Sample Data Includes
- **3 Teams**: Engineering, Marketing, Sales
- **6 Users**: 2 per team with different roles
- **5 Sample Alerts**: Various severities and visibility levels
- **Pre-configured Preferences**: Realistic user alert states

### Manual Testing
1. **Login** with seeded credentials
2. **Create alerts** targeting different audiences
3. **Test reminders** by waiting 2 hours or manually triggering
4. **Verify snooze** functionality works correctly
5. **Check analytics** for accurate metrics

## 🔄 Reminder System

### How It Works
1. **Scheduler**: Runs every 2 hours (`@Scheduled(fixedRate = 7200000)`)
2. **Targeting**: Identifies users based on alert visibility
3. **Snooze Check**: Respects user snooze preferences
4. **Frequency Check**: Ensures alerts aren't sent too frequently
5. **Delivery**: Uses configured notification channels

### Manual Trigger
```bash
curl -X POST http://localhost:8080/demo/trigger-reminders
```

## 🛠️ Development

### Project Structure
```
src/main/java/com/brajesh/
├── controller/          # REST endpoints
├── service/            # Business logic
├── model/              # JPA entities
├── dto/                # Data transfer objects
├── repository/         # Data access layer
├── security/           # JWT and security config
└── service/scheduler/  # Reminder scheduling
```

### Key Components
- **Controllers**: Handle HTTP requests and responses
- **Services**: Implement business logic and orchestration
- **Repositories**: Data access with Spring Data JPA
- **Models**: JPA entities with relationships
- **Security**: JWT authentication and authorization
- **Scheduler**: Automated reminder delivery

## 📊 Monitoring

### Health Checks
```bash
curl http://localhost:8080/actuator/health
```

### Metrics
- Alert creation rate
- Delivery success rate
- User engagement metrics
- System performance indicators

## 🚀 Deployment

### Docker (Optional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/prd-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Production Considerations
- Use environment variables for sensitive data
- Configure proper database connection pooling
- Set up monitoring and logging
- Implement backup strategies
- Configure load balancing if needed

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

For issues and questions:
1. Check the API documentation above
2. Review the sample requests and responses
3. Check the application logs for error details
4. Verify database connectivity and configuration

---

**Built with ❤️ using Spring Boot, JPA, and MySQL**
