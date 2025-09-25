package com.brajesh.service;

import com.brajesh.model.Alert;
import com.brajesh.model.User;

public interface NotificationService {
    void deliver(Alert alert, User user);
}
