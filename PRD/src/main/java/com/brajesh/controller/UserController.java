package com.brajesh.controller;

import com.brajesh.dto.UserAlertDTO;
import com.brajesh.model.UserAlertPreference;
import com.brajesh.repository.NotificationDeliveryRepository;
import com.brajesh.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final NotificationDeliveryRepository deliveryRepository;

    public UserController(UserService userService, NotificationDeliveryRepository deliveryRepository) {
        this.userService = userService;
        this.deliveryRepository = deliveryRepository;
    }

    // Get all alerts for a user
    @GetMapping("/{userId}/alerts")
    public ResponseEntity<List<UserAlertDTO>> getAllAlerts(@PathVariable Long userId) {
        System.out.println("Frontend requesting alerts for user ID: " + userId);
        List<UserAlertPreference> alerts = userService.getUserAlerts(userId);
        System.out.println("Service returned " + alerts.size() + " preferences");
        List<UserAlertDTO> alertDTOs = alerts.stream()
                .map(pref -> convertToDTOWithDeliveredAt(pref))
                .collect(Collectors.toList());
        System.out.println("Returning " + alertDTOs.size() + " DTOs to frontend");
        return ResponseEntity.ok(alertDTOs);
    }

    // Mark an alert as read
    @PutMapping("/{userId}/alerts/{alertId}/read")
    public ResponseEntity<Void> markRead(@PathVariable Long userId, @PathVariable Long alertId) {
        userService.markRead(userId, alertId);
        return ResponseEntity.ok().build();
    }

    // Mark an alert as unread
    @PutMapping("/{userId}/alerts/{alertId}/unread")
    public ResponseEntity<Void> markUnread(@PathVariable Long userId, @PathVariable Long alertId) {
        userService.markUnread(userId, alertId);
        return ResponseEntity.ok().build();
    }

    // Snooze an alert
    @PutMapping("/{userId}/alerts/{alertId}/snooze")
    public ResponseEntity<Void> snoozeAlert(@PathVariable Long userId, @PathVariable Long alertId) {
        userService.snoozeAlert(userId, alertId);
        return ResponseEntity.ok().build();
    }


    // Helper method to convert entity to DTO
    private UserAlertDTO convertToDTOWithDeliveredAt(UserAlertPreference pref) {
        var recents = deliveryRepository.findRecentForUserAndAlert(pref.getAlert().getId(), pref.getUser().getId());
        java.time.LocalDateTime deliveredAt = recents.isEmpty() ? null : recents.get(0).getDeliveredAt();
        return new UserAlertDTO(
                pref.getAlert().getId(),
                pref.getAlert().getTitle(),
                pref.getAlert().getMessage(),
                pref.getAlert().getSeverity() != null ? pref.getAlert().getSeverity().name() : null,
                deliveredAt,
                pref.isRead(),
                pref.isSnoozed()
        );
    }

}
