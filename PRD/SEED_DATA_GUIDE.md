# Seed Data Guide - PRD Alert Platform

This guide explains the seed data available for testing the PRD Alert Platform and how to use it effectively.

## 🌱 Seed Data Overview

The seed data provides a complete testing environment with realistic data for all platform features.

### What Gets Created
- **3 Teams**: Engineering, Marketing, Sales
- **6 Users**: 2 users per team with different roles
- **5 Sample Alerts**: Various severities, visibility levels, and configurations
- **User Alert Preferences**: Realistic read/unread/snoozed states

## 🚀 How to Seed Data

### Method 1: API Endpoint (Recommended)
```bash
curl -X POST http://localhost:8080/demo/seed
```

**Response:**
```json
{
  "message": "Database seeded successfully",
  "teamsCreated": 3,
  "usersCreated": 6,
  "alertsCreated": 5,
  "credentials": {
    "admin": "admin@example.com / password",
    "users": "john@example.com, jane@example.com, alice@example.com, bob@example.com, charlie@example.com / password"
  }
}
```

### Method 2: Frontend Integration
If you have the frontend running, you can create a simple button to trigger seeding:

```typescript
// In your component
seedData() {
  this.http.post('http://localhost:8080/demo/seed', {}).subscribe(
    response => console.log('Data seeded:', response),
    error => console.error('Seeding failed:', error)
  );
}
```

## 👥 User Accounts

### Admin Account
- **Email**: `admin@example.com`
- **Password**: `password`
- **Team**: Engineering
- **Role**: ADMIN
- **Permissions**: Full access to all features

### Regular Users
| Name | Email | Team | Role | Password |
|------|-------|------|------|----------|
| John Developer | john@example.com | Engineering | USER | password |
| Jane Coder | jane@example.com | Engineering | USER | password |
| Alice Marketer | alice@example.com | Marketing | USER | password |
| Bob Promoter | bob@example.com | Marketing | USER | password |
| Charlie Sales | charlie@example.com | Sales | USER | password |

## 🏢 Teams

### Engineering Team
- **Members**: Admin User, John Developer, Jane Coder
- **Focus**: Development, system maintenance, technical alerts
- **Sample Alerts**: System maintenance, team meetings, security updates

### Marketing Team
- **Members**: Alice Marketer, Bob Promoter
- **Focus**: Campaigns, promotions, marketing materials
- **Sample Alerts**: Campaign launches, marketing updates

### Sales Team
- **Members**: Charlie Sales
- **Focus**: Sales targets, customer interactions
- **Sample Alerts**: Sales targets, performance updates

## 📢 Sample Alerts

### 1. System Maintenance (Organization-wide)
```json
{
  "title": "System Maintenance",
  "message": "Scheduled maintenance on Sunday 2AM-4AM. Please save your work.",
  "severity": "WARNING",
  "visibility": "ORGANIZATION",
  "remindersEnabled": true,
  "status": "ACTIVE"
}
```
- **Visible to**: All users
- **Purpose**: Tests organization-wide visibility
- **Reminders**: Every 2 hours until snoozed or expired

### 2. Team Meeting (Engineering Team)
```json
{
  "title": "Team Meeting",
  "message": "Engineering team meeting at 3PM today in Conference Room A.",
  "severity": "INFO",
  "visibility": "TEAM",
  "targetTeams": ["Engineering"],
  "remindersEnabled": true,
  "status": "ACTIVE"
}
```
- **Visible to**: Engineering team only
- **Purpose**: Tests team-level targeting
- **Duration**: 6 hours from creation

### 3. Security Update (Specific Users)
```json
{
  "title": "Security Update Required",
  "message": "Critical security update available. Please update immediately.",
  "severity": "CRITICAL",
  "visibility": "USER",
  "targetUsers": ["admin@example.com", "john@example.com"],
  "remindersEnabled": true,
  "status": "ACTIVE"
}
```
- **Visible to**: Admin and John Developer only
- **Purpose**: Tests user-specific targeting
- **Priority**: Critical severity

### 4. Marketing Campaign (Marketing Team)
```json
{
  "title": "Marketing Campaign Launch",
  "message": "New product campaign launches tomorrow. All marketing materials ready.",
  "severity": "INFO",
  "visibility": "TEAM",
  "targetTeams": ["Marketing"],
  "remindersEnabled": false,
  "status": "ACTIVE"
}
```
- **Visible to**: Marketing team only
- **Purpose**: Tests alerts without reminders
- **Duration**: 2 days from creation

### 5. Sales Target Update (Sales Team)
```json
{
  "title": "Sales Target Update",
  "message": "Q1 sales targets have been updated. Check your dashboard for details.",
  "severity": "INFO",
  "visibility": "TEAM",
  "targetTeams": ["Sales"],
  "remindersEnabled": true,
  "status": "ACTIVE"
}
```
- **Visible to**: Sales team only
- **Purpose**: Tests sales-specific communications
- **Duration**: 7 days from creation

## 🧪 Testing Scenarios

### Scenario 1: Admin Testing
1. **Login** as `admin@example.com`
2. **View All Alerts**: Should see all 5 alerts
3. **Create New Alert**: Test alert creation functionality
4. **Filter Alerts**: Test severity and status filtering
5. **View Analytics**: Check system-wide metrics

### Scenario 2: Engineering User Testing
1. **Login** as `john@example.com` or `jane@example.com`
2. **View Personal Alerts**: Should see 3 alerts (org-wide + team + user-specific)
3. **Mark as Read**: Test read/unread functionality
4. **Snooze Alert**: Test snooze functionality
5. **Create Alert**: Test if user can create alerts (depends on permissions)

### Scenario 3: Marketing User Testing
1. **Login** as `alice@example.com` or `bob@example.com`
2. **View Personal Alerts**: Should see 2 alerts (org-wide + team)
3. **Test Visibility**: Verify they don't see engineering-specific alerts
4. **Test Reminders**: Verify reminder behavior

### Scenario 4: Sales User Testing
1. **Login** as `charlie@example.com`
2. **View Personal Alerts**: Should see 2 alerts (org-wide + team)
3. **Test Team Isolation**: Verify they only see sales-related alerts

## 🔄 Reminder Testing

### Manual Reminder Trigger
```bash
curl -X POST http://localhost:8080/demo/trigger-reminders
```

**Response:**
```json
{
  "message": "Reminders triggered successfully",
  "timestamp": "2024-01-15T10:30:00"
}
```

### Reminder Behavior
- **Frequency**: Every 2 hours (configurable per alert)
- **Snooze Respect**: Won't send if user snoozed for today
- **Expiry Respect**: Won't send if alert has expired
- **Targeting**: Only sends to users the alert is visible to

## 📊 Analytics Testing

### Expected Metrics (After Seeding)
- **Total Alerts**: 5
- **Active Alerts**: 5 (all are active)
- **Read Count**: Varies based on user interactions
- **Unread Count**: Varies based on user interactions

### Testing Analytics
1. **Login** as admin
2. **Navigate** to Analytics dashboard
3. **Verify** metrics are displayed correctly
4. **Interact** with alerts (read/snooze)
5. **Refresh** analytics to see updated counts

## 🗑️ Resetting Data

### Clear All Data
```sql
-- Connect to your MySQL database
USE prd_alerts;

-- Clear all data (in order due to foreign key constraints)
DELETE FROM notification_deliveries;
DELETE FROM user_alert_preferences;
DELETE FROM alert_users;
DELETE FROM alert_teams;
DELETE FROM alerts;
DELETE FROM users;
DELETE FROM teams;
```

### Re-seed Data
After clearing, run the seed endpoint again:
```bash
curl -X POST http://localhost:8080/demo/seed
```

## 🔧 Customizing Seed Data

### Adding More Users
Edit `DemoController.java` and add more users:

```java
User newUser = new User();
newUser.setName("New User");
newUser.setEmail("newuser@example.com");
newUser.setPassword("$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi");
newUser.setTeam(engineering);
userRepository.save(newUser);
```

### Adding More Alerts
Add more alert creation code:

```java
Alert newAlert = new Alert();
newAlert.setTitle("Custom Alert");
newAlert.setMessage("This is a custom alert for testing");
newAlert.setSeverity(Severity.INFO);
newAlert.setDeliveryType(DeliveryType.IN_APP);
newAlert.setStartTime(LocalDateTime.now());
newAlert.setExpiryTime(LocalDateTime.now().plusDays(1));
newAlert.setRemindersEnabled(true);
newAlert.setVisibilityType(VisibilityType.ORGANIZATION);
alertRepository.save(newAlert);
```

## 🐛 Troubleshooting

### Common Issues

1. **Seeding Fails**
   - Check database connection
   - Verify tables exist
   - Check application logs

2. **Users Can't Login**
   - Verify password hashing
   - Check user creation in database
   - Verify JWT configuration

3. **Alerts Not Visible**
   - Check alert targeting logic
   - Verify user-team relationships
   - Check alert expiry times

4. **Reminders Not Working**
   - Verify scheduler is enabled
   - Check reminder frequency settings
   - Test manual trigger endpoint

### Debug Commands

```bash
# Check if backend is running
curl http://localhost:8080/demo/welcome

# Test authentication
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin@example.com","password":"password"}'

# Check user alerts
curl -X GET http://localhost:8080/api/users/1/alerts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 📈 Performance Testing

### Load Testing with Seed Data
1. **Create Multiple Users**: Add more users to test scalability
2. **Create Many Alerts**: Test with 100+ alerts
3. **Test Reminder Performance**: Verify scheduler handles many alerts
4. **Test Analytics Performance**: Check query performance with large datasets

### Database Optimization
- **Indexes**: Ensure proper indexes on foreign keys
- **Query Optimization**: Monitor slow queries
- **Connection Pooling**: Configure appropriate pool sizes

---

**This seed data provides a comprehensive testing environment for all PRD Alert Platform features!** 🚀
