# API Documentation - PRD Alert Platform

## 🔐 Authentication

All API endpoints (except `/auth/*` and `/demo/*`) require JWT authentication.

### Headers Required
```http
Authorization: Bearer <jwt_token>
Content-Type: application/json
```

---

## 👨‍💼 Admin APIs

### 1. Create Alert
Creates a new alert with specified targeting and configuration.

**Endpoint:** `POST /api/admin/alerts`

**Request Body:**
```json
{
  "title": "System Maintenance Notice",
  "message": "Scheduled maintenance will occur on Sunday from 2AM to 4AM. Please save your work.",
  "severity": "WARNING",
  "deliveryType": "IN_APP",
  "startTime": "2024-01-15T09:00:00",
  "expiryTime": "2024-01-20T18:00:00",
  "remindersEnabled": true,
  "teamIds": [1, 2],
  "userIds": [3, 4]
}
```

**Field Descriptions:**
- `title` (required): Alert title (max 255 chars)
- `message` (required): Alert content
- `severity` (required): INFO, WARNING, or CRITICAL
- `deliveryType` (required): IN_APP, EMAIL, or SMS
- `startTime` (required): When alert becomes active
- `expiryTime` (required): When alert expires
- `remindersEnabled` (optional): Enable 2-hour reminders (default: true)
- `teamIds` (optional): Target specific teams
- `userIds` (optional): Target specific users

**Response:**
```json
{
  "id": 1,
  "title": "System Maintenance Notice",
  "message": "Scheduled maintenance will occur on Sunday from 2AM to 4AM. Please save your work.",
  "severity": "WARNING",
  "deliveryType": "IN_APP",
  "startTime": "2024-01-15T09:00:00",
  "expiryTime": "2024-01-20T18:00:00",
  "active": true
}
```

**Status Codes:**
- `200 OK`: Alert created successfully
- `400 Bad Request`: Invalid input data
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions

---

### 2. Update Alert
Updates an existing alert with new information.

**Endpoint:** `PUT /api/admin/alerts/{alertId}`

**Path Parameters:**
- `alertId`: ID of the alert to update

**Request Body:** (Same as Create Alert)

**Response:** (Same as Create Alert)

**Status Codes:**
- `200 OK`: Alert updated successfully
- `400 Bad Request`: Invalid input data
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: Alert not found

---

### 3. List Alerts
Retrieves alerts with optional filtering capabilities.

**Endpoint:** `GET /api/admin/alerts`

**Query Parameters:**
- `severity` (optional): Filter by severity (INFO, WARNING, CRITICAL)
- `status` (optional): Filter by status (active, expired)
- `teamIds` (optional): Comma-separated team IDs
- `userIds` (optional): Comma-separated user IDs

**Example Requests:**
```http
GET /api/admin/alerts
GET /api/admin/alerts?severity=WARNING&status=active
GET /api/admin/alerts?teamIds=1,2&userIds=3,4
GET /api/admin/alerts?severity=CRITICAL&status=expired&teamIds=1
```

**Response:**
```json
[
  {
    "id": 1,
    "title": "System Maintenance Notice",
    "message": "Scheduled maintenance will occur on Sunday from 2AM to 4AM.",
    "severity": "WARNING",
    "deliveryType": "IN_APP",
    "startTime": "2024-01-15T09:00:00",
    "expiryTime": "2024-01-20T18:00:00",
    "active": true
  },
  {
    "id": 2,
    "title": "Security Update",
    "message": "Please update your passwords immediately.",
    "severity": "CRITICAL",
    "deliveryType": "IN_APP",
    "startTime": "2024-01-14T10:00:00",
    "expiryTime": "2024-01-16T10:00:00",
    "active": false
  }
]
```

**Status Codes:**
- `200 OK`: Alerts retrieved successfully
- `400 Bad Request`: Invalid query parameters
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions

---

### 4. Archive Alert
Archives an alert by setting its expiry time to now.

**Endpoint:** `DELETE /api/admin/alerts/{alertId}`

**Path Parameters:**
- `alertId`: ID of the alert to archive

**Response:**
```json
"Alert archived successfully."
```

**Status Codes:**
- `200 OK`: Alert archived successfully
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: Alert not found

---

### 5. Create User
Creates a new user in the system.

**Endpoint:** `POST /api/admin/alerts/users`

**Request Body:**
```json
{
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "teamId": 2
}
```

**Field Descriptions:**
- `name` (required): User's full name
- `email` (required): User's email address (must be unique)
- `teamId` (required): ID of the team to assign user to

**Response:**
```json
{
  "id": 7,
  "name": "Jane Smith",
  "email": "jane.smith@example.com",
  "teamId": 2,
  "teamName": "Marketing"
}
```

**Status Codes:**
- `200 OK`: User created successfully
- `400 Bad Request`: Invalid input data
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions
- `409 Conflict`: Email already exists

---

## 👤 User APIs

### 1. Fetch User Alerts
Retrieves all alerts visible to a specific user.

**Endpoint:** `GET /api/users/{userId}/alerts`

**Path Parameters:**
- `userId`: ID of the user

**Response:**
```json
[
  {
    "alertId": 1,
    "title": "System Maintenance Notice",
    "message": "Scheduled maintenance will occur on Sunday from 2AM to 4AM.",
    "severity": "WARNING",
    "deliveredAt": "2024-01-15T09:00:00",
    "read": false,
    "snoozed": false
  },
  {
    "alertId": 2,
    "title": "Team Meeting Reminder",
    "message": "Don't forget about the team meeting at 3PM today.",
    "severity": "INFO",
    "deliveredAt": "2024-01-15T14:30:00",
    "read": true,
    "snoozed": false
  }
]
```

**Field Descriptions:**
- `alertId`: Unique identifier for the alert
- `title`: Alert title
- `message`: Alert content
- `severity`: Alert severity level
- `deliveredAt`: When the alert was last delivered to the user
- `read`: Whether the user has marked the alert as read
- `snoozed`: Whether the user has snoozed the alert

**Status Codes:**
- `200 OK`: Alerts retrieved successfully
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: User not found

---

### 2. Mark Alert as Read
Marks a specific alert as read for a user.

**Endpoint:** `PUT /api/users/{userId}/alerts/{alertId}/read`

**Path Parameters:**
- `userId`: ID of the user
- `alertId`: ID of the alert

**Response:** `200 OK` (No body)

**Status Codes:**
- `200 OK`: Alert marked as read successfully
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: User, alert, or preference not found

---

### 3. Mark Alert as Unread
Marks a specific alert as unread for a user.

**Endpoint:** `PUT /api/users/{userId}/alerts/{alertId}/unread`

**Path Parameters:**
- `userId`: ID of the user
- `alertId`: ID of the alert

**Response:** `200 OK` (No body)

**Status Codes:**
- `200 OK`: Alert marked as unread successfully
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: User, alert, or preference not found

---

### 4. Snooze Alert
Snoozes an alert for the current day (until 11:59 PM).

**Endpoint:** `PUT /api/users/{userId}/alerts/{alertId}/snooze`

**Path Parameters:**
- `userId`: ID of the user
- `alertId`: ID of the alert

**Response:** `200 OK` (No body)

**Behavior:**
- Alert is snoozed until end of current day (23:59)
- Reminders will resume the next day if alert is still active
- User can snooze multiple times per day

**Status Codes:**
- `200 OK`: Alert snoozed successfully
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions
- `404 Not Found`: User, alert, or preference not found

---

## 📊 Analytics API

### Get Analytics Summary
Retrieves system-wide analytics and metrics.

**Endpoint:** `GET /api/analytics/summary`

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

**Field Descriptions:**
- `totalAlerts`: Total number of alerts created in the system
- `activeAlerts`: Number of alerts that are currently active (not expired)
- `readCount`: Total number of alert instances marked as read by users
- `unreadCount`: Total number of alert instances that are unread
- `lastUpdated`: Timestamp when analytics were last calculated

**Status Codes:**
- `200 OK`: Analytics retrieved successfully
- `401 Unauthorized`: Invalid or missing JWT token
- `403 Forbidden`: Insufficient permissions

---

## 🔐 Authentication APIs

### Login
Authenticates a user and returns a JWT token.

**Endpoint:** `POST /auth/login`

**Request Body:**
```json
{
  "username": "admin@example.com",
  "password": "admin123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsInJvbGVzIjpbIkFETUlOIl0sImlhdCI6MTY0MjI1OTYwMCwiZXhwIjoxNjQyMzQ2MDAwfQ.signature"
}
```

**Status Codes:**
- `200 OK`: Login successful
- `401 Unauthorized`: Invalid credentials

---

### Register
Registers a new user and returns a JWT token.

**Endpoint:** `POST /auth/register`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "password123",
  "teamId": 1
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsInJvbGVzIjpbIlVTRVIiXSwiaWF0IjoxNjQyMjU5NjAwLCJleHAiOjE2NDIzNDYwMDB9.signature"
}
```

**Status Codes:**
- `200 OK`: Registration successful
- `400 Bad Request`: Invalid input data
- `409 Conflict`: Email already exists

---

## 🧪 Demo APIs

### Seed Data
Populates the database with sample data for testing.

**Endpoint:** `POST /demo/seed`

**Response:**
```json
{
  "message": "Database seeded successfully",
  "usersCreated": 6,
  "teamsCreated": 3,
  "alertsCreated": 5
}
```

**Creates:**
- 3 Teams: Engineering, Marketing, Sales
- 6 Users: 2 per team with different roles
- 5 Sample Alerts: Various severities and visibility levels

**Status Codes:**
- `200 OK`: Data seeded successfully

---

### Trigger Reminders
Manually triggers the reminder system for testing.

**Endpoint:** `POST /demo/trigger-reminders`

**Response:**
```json
{
  "message": "Reminders triggered successfully",
  "alertsProcessed": 3,
  "notificationsSent": 8
}
```

**Status Codes:**
- `200 OK`: Reminders triggered successfully

---

## 📝 Error Responses

### Standard Error Format
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/admin/alerts"
}
```

### Common Error Codes
- `400 Bad Request`: Invalid input data or validation errors
- `401 Unauthorized`: Missing or invalid JWT token
- `403 Forbidden`: Insufficient permissions for the operation
- `404 Not Found`: Resource not found
- `409 Conflict`: Resource already exists (e.g., duplicate email)
- `500 Internal Server Error`: Unexpected server error

---

## 🔄 Rate Limiting

Currently, no rate limiting is implemented. For production deployment, consider implementing rate limiting for:
- Authentication endpoints
- Alert creation endpoints
- Analytics endpoints

---

## 📋 Testing Examples

### cURL Examples

**Login:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin@example.com","password":"admin123"}'
```

**Create Alert:**
```bash
curl -X POST http://localhost:8080/api/admin/alerts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Alert",
    "message": "This is a test alert",
    "severity": "INFO",
    "deliveryType": "IN_APP",
    "startTime": "2024-01-15T09:00:00",
    "expiryTime": "2024-01-20T18:00:00"
  }'
```

**Get User Alerts:**
```bash
curl -X GET http://localhost:8080/api/users/1/alerts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Snooze Alert:**
```bash
curl -X PUT http://localhost:8080/api/users/1/alerts/1/snooze \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

---

**Last Updated:** January 15, 2024  
**API Version:** 1.0  
**Base URL:** `http://localhost:8080`
