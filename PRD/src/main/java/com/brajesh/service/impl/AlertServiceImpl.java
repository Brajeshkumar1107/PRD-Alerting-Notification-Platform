package com.brajesh.service.impl;

import com.brajesh.model.Alert;
import com.brajesh.model.Team;
import com.brajesh.model.User;
import com.brajesh.model.UserAlertPreference;
import com.brajesh.model.enums.Severity;
import com.brajesh.repository.AlertRepository;
import com.brajesh.repository.TeamRepository;
import com.brajesh.repository.UserAlertPreferenceRepository;
import com.brajesh.repository.UserRepository;
import com.brajesh.service.AlertService;
import com.brajesh.service.NotificationService;
import com.brajesh.repository.NotificationDeliveryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final UserAlertPreferenceRepository userAlertPreferenceRepository;
    private final NotificationDeliveryRepository notificationDeliveryRepository;

    public AlertServiceImpl(AlertRepository alertRepository,
                            TeamRepository teamRepository,
                            UserRepository userRepository, NotificationService notificationService, UserAlertPreferenceRepository userAlertPreferenceRepository, NotificationDeliveryRepository notificationDeliveryRepository) {
        this.alertRepository = alertRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.userAlertPreferenceRepository = userAlertPreferenceRepository;
        this.notificationDeliveryRepository = notificationDeliveryRepository;
    }

    @Override
    public Alert createAlert(Alert alert, List<Long> teamIds, List<Long> userIds) {
        // Fetch teams
        if (teamIds != null) {
            List<Team> teams = teamRepository.findAllById(teamIds);
            alert.setTeams(teams);
        }

        // Fetch users
        if (userIds != null) {
            List<User> users = userRepository.findAllById(userIds);
            alert.setUsers(users);
        }

        alert.setRemindersEnabled(true); // default
        return alertRepository.save(alert);
    }

    @Override
    public Alert updateAlert(Long alertId, Alert alertDetails) {
        Alert existing = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        existing.setTitle(alertDetails.getTitle());
        existing.setMessage(alertDetails.getMessage());
        existing.setSeverity(alertDetails.getSeverity());
        existing.setStartTime(alertDetails.getStartTime());
        existing.setExpiryTime(alertDetails.getExpiryTime());
        existing.setRemindersEnabled(alertDetails.isRemindersEnabled());
        return alertRepository.save(existing);
    }

    @Override
    public void archiveAlert(Long alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setRemindersEnabled(false);
        alertRepository.save(alert);
    }

    @Override
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @Override
    public List<Alert> getAlertsBySeverity(String severity) {
        return alertRepository.findBySeverity(Enum.valueOf(Severity.class, severity.toUpperCase()));
    }

    @Override
    public Optional<Alert> getAlertById(Long alertId) {
        return alertRepository.findById(alertId);
    }

    @Override
    @Transactional
    public void triggerReminders() {
        LocalDateTime now = LocalDateTime.now();

        // 1. Fetch all active alerts (not expired, reminders enabled)
        List<Alert> activeAlerts = alertRepository.findByRemindersEnabledTrueAndExpiryTimeAfter(now);

        for (Alert alert : activeAlerts) {
            // 2. Get target users by visibility
            List<User> targetUsers;
            if (alert.getUsers() != null && !alert.getUsers().isEmpty()) {
                targetUsers = alert.getUsers();
            } else if (alert.getTeams() != null && !alert.getTeams().isEmpty()) {
                targetUsers = userRepository.findUsersByTeamIds(alert.getTeams().stream().map(Team::getId).toList());
            } else {
                targetUsers = userRepository.findAll();
            }

            for (User user : targetUsers) {
                UserAlertPreference preference = userAlertPreferenceRepository
                        .findByUserIdAndAlertId(user.getId(), alert.getId())
                        .orElse(null);

                boolean isSnoozedToday = preference != null && preference.isSnoozed() && preference.getSnoozedUntil() != null && preference.getSnoozedUntil().toLocalDate().isEqual(java.time.LocalDate.now());
                boolean shouldRemind = (preference == null || (!isSnoozedToday && !preference.isRead()));

                if (shouldRemind) {
                    // 3. Respect per-alert frequency: if last delivery older than reminderFrequencyInHours
                    var recent = notificationDeliveryRepository.findRecentForUserAndAlert(alert.getId(), user.getId());
                    boolean dueByFrequency = recent.isEmpty() || java.time.Duration.between(recent.get(0).getDeliveredAt(), now).toHours() >= alert.getReminderFrequencyInHours();
                    if (dueByFrequency) {
                        notificationService.deliver(alert, user);
                    }
                }
            }
        }
    }
}

