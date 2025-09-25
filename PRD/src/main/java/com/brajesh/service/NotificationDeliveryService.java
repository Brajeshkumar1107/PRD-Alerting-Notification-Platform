package com.brajesh.service;

import com.brajesh.model.Alert;
import com.brajesh.model.User;

public interface NotificationDeliveryService {
    void sendNotification(Alert alert, User user);
}
