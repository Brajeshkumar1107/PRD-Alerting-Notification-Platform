package com.brajesh.service.impl;

import com.brajesh.dto.AnalyticsDTO;
import com.brajesh.model.UserAlertPreference;
import com.brajesh.model.enums.Severity;
import com.brajesh.repository.AlertRepository;
import com.brajesh.repository.NotificationDeliveryRepository;
import com.brajesh.repository.UserAlertPreferenceRepository;
import com.brajesh.service.AnalyticsService;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final AlertRepository alertRepository;
    private final NotificationDeliveryRepository deliveryRepository;
    private final UserAlertPreferenceRepository preferenceRepository;

    public AnalyticsServiceImpl(AlertRepository alertRepository,
                                NotificationDeliveryRepository deliveryRepository,
                                UserAlertPreferenceRepository preferenceRepository) {
        this.alertRepository = alertRepository;
        this.deliveryRepository = deliveryRepository;
        this.preferenceRepository = preferenceRepository;
    }

    @Override
    public AnalyticsDTO getAnalyticsSummary() {
        long totalAlerts = alertRepository.count();
        long delivered = deliveryRepository.count();
        long read = preferenceRepository.findAll().stream().filter(UserAlertPreference::isRead).count();
        long unread = preferenceRepository.findAll().stream().filter(pref -> !pref.isRead()).count();
        
        // Count active alerts (not expired)
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        long activeAlerts = alertRepository.findAll().stream()
                .filter(a -> a.getExpiryTime() == null || a.getExpiryTime().isAfter(now))
                .count();

        System.out.println("Analytics - Total alerts: " + totalAlerts + ", Active: " + activeAlerts + ", Read: " + read + ", Unread: " + unread);

        return new AnalyticsDTO(totalAlerts, activeAlerts, read, unread);
    }
}

