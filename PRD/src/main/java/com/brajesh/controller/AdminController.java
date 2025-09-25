package com.brajesh.controller;

import com.brajesh.dto.AlertRequestDTO;
import com.brajesh.dto.AlertResponseDTO;
import com.brajesh.dto.CreateUserRequest;
import com.brajesh.dto.UserResponseDTO;
import com.brajesh.model.enums.Severity;
import com.brajesh.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/admin/alerts")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // CREATE ALERT
    @PostMapping
    public ResponseEntity<AlertResponseDTO> createAlert(@Valid @RequestBody AlertRequestDTO requestDTO) {
        AlertResponseDTO response = adminService.createAlert(requestDTO);
        return ResponseEntity.ok(response);
    }

    // UPDATE ALERT
    @PutMapping("/{alertId}")
    public ResponseEntity<AlertResponseDTO> updateAlert(@PathVariable Long alertId,
                                                        @Valid @RequestBody AlertRequestDTO requestDTO) {
        AlertResponseDTO response = adminService.updateAlert(alertId, requestDTO);
        return ResponseEntity.ok(response);
    }

    // ARCHIVE ALERT
    @DeleteMapping("/{alertId}")
    public ResponseEntity<String> archiveAlert(@PathVariable Long alertId) {
        adminService.archiveAlert(alertId);
        return ResponseEntity.ok("Alert archived successfully.");
    }

    // LIST ALERTS
    @GetMapping
    public ResponseEntity<List<AlertResponseDTO>> listAlerts(
            @RequestParam(required = false) String severity,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) List<Long> teamIds,
            @RequestParam(required = false) List<Long> userIds) {

        // Convert severity string to enum (if present)
        Severity severityEnum = null;
        if (severity != null) {
            try {
                severityEnum = Severity.valueOf(severity.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build(); // invalid severity value
            }
        }

        // Pass enum to service
        List<AlertResponseDTO> alerts = adminService.listAlerts(severityEnum, status, teamIds, userIds);
        return ResponseEntity.ok(alerts);
    }

    // CREATE USER
    @PostMapping("/users")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(adminService.createUser(request));
    }
}
