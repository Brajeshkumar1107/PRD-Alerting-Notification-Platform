package com.brajesh.service;

import com.brajesh.model.UserAlertPreference;

import java.util.List;

public interface UserService {
    List<UserAlertPreference> getUserAlerts(Long userId);
    void markRead(Long userId, Long alertId);
    void markUnread(Long userId, Long alertId);
    void snoozeAlert(Long userId, Long alertId);
}