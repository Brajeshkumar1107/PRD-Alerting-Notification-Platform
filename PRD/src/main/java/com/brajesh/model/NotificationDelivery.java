package com.brajesh.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "notification_deliveries")
public class NotificationDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime deliveredAt;

    @ManyToOne
    @JoinColumn(name = "alert_id")
    private Alert alert;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
