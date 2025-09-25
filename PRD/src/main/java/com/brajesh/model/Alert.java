package com.brajesh.model;


import com.brajesh.model.enums.DeliveryType;
import com.brajesh.model.enums.Severity;
import com.brajesh.model.enums.VisibilityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;

    @Enumerated(EnumType.STRING)
    private Severity severity; // INFO, WARNING, CRITICAL

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType; // IN_APP, EMAIL, SMS

    private LocalDateTime startTime;
    private LocalDateTime expiryTime;

    private int reminderFrequencyInHours = 2;
    private boolean remindersEnabled = true;

    @Enumerated(EnumType.STRING)
    private VisibilityType visibilityType; // ORG, TEAM, USER

    @ManyToMany
    @JoinTable(
            name = "alert_teams",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams;

    @ManyToMany
    @JoinTable(
            name = "alert_users",
            joinColumns = @JoinColumn(name = "alert_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    @OneToMany(mappedBy = "alert", cascade = CascadeType.ALL)
    private List<NotificationDelivery> deliveries;


}
