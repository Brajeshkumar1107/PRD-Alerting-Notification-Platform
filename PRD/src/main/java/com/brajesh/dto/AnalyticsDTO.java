package com.brajesh.dto;

import java.time.LocalDateTime;

public class AnalyticsDTO {

    // General alert/notification stats
    private long totalAlertsCreated;
    private long totalNotificationsSent;
    private long activeAlerts;
    private long inactiveAlerts;

    // Notification delivery stats
    private long deliveredNotifications;
    private long readNotifications;
    private long snoozedNotifications;

    // Alert type breakdown
    private long infoAlerts;
    private long warningAlerts;
    private long criticalAlerts;

    private LocalDateTime lastUpdated;

    public AnalyticsDTO() {}

    // Full constructor
    public AnalyticsDTO(long totalAlertsCreated,
                        long totalNotificationsSent,
                        long activeAlerts,
                        long inactiveAlerts,
                        long deliveredNotifications,
                        long readNotifications,
                        long snoozedNotifications,
                        long infoAlerts,
                        long warningAlerts,
                        long criticalAlerts,
                        LocalDateTime lastUpdated) {
        this.totalAlertsCreated = totalAlertsCreated;
        this.totalNotificationsSent = totalNotificationsSent;
        this.activeAlerts = activeAlerts;
        this.inactiveAlerts = inactiveAlerts;
        this.deliveredNotifications = deliveredNotifications;
        this.readNotifications = readNotifications;
        this.snoozedNotifications = snoozedNotifications;
        this.infoAlerts = infoAlerts;
        this.warningAlerts = warningAlerts;
        this.criticalAlerts = criticalAlerts;
        this.lastUpdated = lastUpdated;
    }

    // Simplified constructor (legacy use)
    public AnalyticsDTO(long totalAlerts,
                        long delivered,
                        long read,
                        long snoozed,
                        long infoCount,
                        long warningCount,
                        long criticalCount) {
        this.totalAlertsCreated = totalAlerts;
        this.deliveredNotifications = delivered;
        this.readNotifications = read;
        this.snoozedNotifications = snoozed;
        this.infoAlerts = infoCount;
        this.warningAlerts = warningCount;
        this.criticalAlerts = criticalCount;
        this.lastUpdated = LocalDateTime.now();
    }

    // Frontend-compatible constructor
    public AnalyticsDTO(long totalAlerts, long activeAlerts, long read, long unread) {
        this.totalAlertsCreated = totalAlerts;
        this.activeAlerts = activeAlerts;
        this.readNotifications = read;
        this.snoozedNotifications = unread; // Using snoozed field for unread count
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters & Setters
    public long getTotalAlertsCreated() {
        return totalAlertsCreated;
    }

    public void setTotalAlertsCreated(long totalAlertsCreated) {
        this.totalAlertsCreated = totalAlertsCreated;
    }

    public long getTotalNotificationsSent() {
        return totalNotificationsSent;
    }

    public void setTotalNotificationsSent(long totalNotificationsSent) {
        this.totalNotificationsSent = totalNotificationsSent;
    }

    public long getActiveAlerts() {
        return activeAlerts;
    }

    public void setActiveAlerts(long activeAlerts) {
        this.activeAlerts = activeAlerts;
    }

    public long getInactiveAlerts() {
        return inactiveAlerts;
    }

    public void setInactiveAlerts(long inactiveAlerts) {
        this.inactiveAlerts = inactiveAlerts;
    }

    public long getDeliveredNotifications() {
        return deliveredNotifications;
    }

    public void setDeliveredNotifications(long deliveredNotifications) {
        this.deliveredNotifications = deliveredNotifications;
    }

    public long getReadNotifications() {
        return readNotifications;
    }

    public void setReadNotifications(long readNotifications) {
        this.readNotifications = readNotifications;
    }

    public long getSnoozedNotifications() {
        return snoozedNotifications;
    }

    public void setSnoozedNotifications(long snoozedNotifications) {
        this.snoozedNotifications = snoozedNotifications;
    }

    public long getInfoAlerts() {
        return infoAlerts;
    }

    public void setInfoAlerts(long infoAlerts) {
        this.infoAlerts = infoAlerts;
    }

    public long getWarningAlerts() {
        return warningAlerts;
    }

    public void setWarningAlerts(long warningAlerts) {
        this.warningAlerts = warningAlerts;
    }

    public long getCriticalAlerts() {
        return criticalAlerts;
    }

    public void setCriticalAlerts(long criticalAlerts) {
        this.criticalAlerts = criticalAlerts;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // Frontend-compatible getters
    public long getTotalAlerts() {
        return totalAlertsCreated;
    }

    public long getReadCount() {
        return readNotifications;
    }

    public long getUnreadCount() {
        return snoozedNotifications; // Using this field for unread count
    }
}
