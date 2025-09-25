package com.brajesh.service.impl;

import com.brajesh.model.Alert;
import com.brajesh.model.NotificationDelivery;
import com.brajesh.model.User;
import com.brajesh.repository.NotificationDeliveryRepository;
import com.brajesh.service.NotificationDeliveryService;
import org.springframework.stereotype.Service;

@Service
public class NotificationDeliveryServiceImpl implements NotificationDeliveryService {

    private final NotificationDeliveryRepository deliveryRepository;

    public NotificationDeliveryServiceImpl(NotificationDeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public void sendNotification(Alert alert, User user) {
        // Here we simulate in-app delivery
        NotificationDelivery delivery = new NotificationDelivery();
        delivery.setAlert(alert);
        delivery.setUser(user);
        delivery.setDeliveredAt(java.time.LocalDateTime.now());
        deliveryRepository.save(delivery);

        // Optional: trigger push notification / email / SMS in future
    }
}

