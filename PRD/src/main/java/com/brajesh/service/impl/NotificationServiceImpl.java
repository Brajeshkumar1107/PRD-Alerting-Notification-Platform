package com.brajesh.service.impl;


import com.brajesh.model.Alert;
import com.brajesh.model.User;
import com.brajesh.service.NotificationChannel;
import com.brajesh.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    // Spring will auto-wire all beans implementing NotificationChannel
    private final Map<String, NotificationChannel> channels;

    @Override
    public void deliver(Alert alert, User user) {
        // Use deliveryType defined in Alert (IN_APP for MVP)
        String channelKey = alert.getDeliveryType().name();

        NotificationChannel channel = channels.get(channelKey);

        if (channel != null) {
            channel.send(alert, user);
        } else {
            throw new UnsupportedOperationException(
                    "No notification channel found for " + channelKey
            );
        }
    }
}