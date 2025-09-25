package com.brajesh.service.impl;


import com.brajesh.model.Alert;
import com.brajesh.model.NotificationDelivery;
import com.brajesh.model.User;
import com.brajesh.repository.NotificationDeliveryRepository;
import com.brajesh.service.NotificationChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("IN_APP")
@RequiredArgsConstructor
public class InAppNotificationChannel implements NotificationChannel {

    private final NotificationDeliveryRepository notificationDeliveryRepository;

    @Override
    public void send(Alert alert, User user) {
        NotificationDelivery delivery = new NotificationDelivery();
        delivery.setAlert(alert);
        delivery.setUser(user);
        delivery.setDeliveredAt(LocalDateTime.now());

        notificationDeliveryRepository.save(delivery);

        System.out.printf("📢 [IN-APP] Delivered alert '%s' to user %s%n",
                alert.getTitle(), user.getName());
    }
}
