package com.brajesh.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.brajesh.model.Alert;
import com.brajesh.model.Team;
import com.brajesh.model.User;
import com.brajesh.model.UserAlertPreference;
import com.brajesh.repository.AlertRepository;
import com.brajesh.repository.UserAlertPreferenceRepository;
import com.brajesh.repository.UserRepository;
import com.brajesh.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserAlertPreferenceRepository preferenceRepository;
    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    public UserServiceImpl(UserAlertPreferenceRepository preferenceRepository,
                           AlertRepository alertRepository,
                           UserRepository userRepository) {
        this.preferenceRepository = preferenceRepository;
        this.alertRepository = alertRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserAlertPreference> getUserAlerts(Long userId) {
        System.out.println("Getting alerts for user ID: " + userId);
        List<UserAlertPreference> existing = preferenceRepository.findByUserId(userId);
        System.out.println("Existing preferences: " + existing.size());
        
        // Always check for missing preferences, even if some exist
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("User not found for ID: " + userId);
            return existing;
        }

        Team team = user.getTeam();
        System.out.println("User team: " + (team != null ? team.getId() : "null"));
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        List<Alert> allAlerts = alertRepository.findAll();
        System.out.println("Total alerts in DB: " + allAlerts.size());
        
        // For demo purposes, include all alerts (including expired ones)
        List<Alert> activeAlerts = allAlerts;
        System.out.println("Active alerts: " + activeAlerts.size());

        // Get existing alert IDs for this user
        Set<Long> existingAlertIds = existing.stream()
                .map(pref -> pref.getAlert().getId())
                .collect(Collectors.toSet());
        System.out.println("Existing alert IDs: " + existingAlertIds);

        int createdCount = 0;
        for (Alert alert : activeAlerts) {
            // Skip if preference already exists for this alert
            if (existingAlertIds.contains(alert.getId())) {
                System.out.println("Skipping alert " + alert.getId() + " - preference already exists");
                continue;
            }
            
            boolean targetedToUser = alert.getUsers() != null && alert.getUsers().stream().anyMatch(u -> u.getId() == userId);
            boolean targetedToTeam = alert.getTeams() != null && team != null && alert.getTeams().stream().anyMatch(t -> t.getId() == team.getId());
            boolean orgWide = (alert.getUsers() == null || alert.getUsers().isEmpty()) && (alert.getTeams() == null || alert.getTeams().isEmpty());
            
            System.out.println("Alert " + alert.getId() + " - targetedToUser: " + targetedToUser + ", targetedToTeam: " + targetedToTeam + ", orgWide: " + orgWide);
            
            if (targetedToUser || targetedToTeam || orgWide) {
                UserAlertPreference pref = new UserAlertPreference();
                pref.setUser(user);
                pref.setAlert(alert);
                pref.setRead(false);
                pref.setSnoozed(false);
                preferenceRepository.save(pref);
                createdCount++;
                System.out.println("Created preference for alert " + alert.getId());
            }
        }
        System.out.println("Created " + createdCount + " preferences");
        return preferenceRepository.findByUserId(userId);
    }

    @Override
    public void markRead(Long userId, Long alertId) {
        UserAlertPreference pref = preferenceRepository.findByUserIdAndAlertId(userId, alertId)
                .orElseThrow(() -> new RuntimeException("Preference not found"));
        pref.setRead(true);
        preferenceRepository.save(pref);
    }

    @Override
    public void markUnread(Long userId, Long alertId) {
        UserAlertPreference pref = preferenceRepository.findByUserIdAndAlertId(userId, alertId)
                .orElseThrow(() -> new RuntimeException("Preference not found"));
        pref.setRead(false);
        preferenceRepository.save(pref);
    }

    @Override
    public void snoozeAlert(Long userId, Long alertId) {
        UserAlertPreference pref = preferenceRepository.findByUserIdAndAlertId(userId, alertId)
                .orElseThrow(() -> new RuntimeException("Preference not found"));
        pref.setSnoozed(true);
        pref.setSnoozedUntil(java.time.LocalDate.now().atTime(23, 59)); // snooze until end of day
        preferenceRepository.save(pref);
    }
}
