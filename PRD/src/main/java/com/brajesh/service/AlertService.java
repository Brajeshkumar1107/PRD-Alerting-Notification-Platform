package com.brajesh.service;



import com.brajesh.model.Alert;

import java.util.List;
import java.util.Optional;

public interface AlertService {
    Alert createAlert(Alert alert, List<Long> teamIds, List<Long> userIds);
    Alert updateAlert(Long alertId, Alert alertDetails);
    void archiveAlert(Long alertId);
    List<Alert> getAllAlerts();
    List<Alert> getAlertsBySeverity(String severity);
    Optional<Alert> getAlertById(Long alertId);
    void triggerReminders();
}