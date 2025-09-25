package com.brajesh.service;

import com.brajesh.model.Alert;
import com.brajesh.model.User;

public interface NotificationChannel {
    void send(Alert alert, User user);
}
