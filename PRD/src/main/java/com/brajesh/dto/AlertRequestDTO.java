package com.brajesh.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class AlertRequestDTO {
    @NotBlank
    @Size(max = 255)
    private String title;

    @NotBlank
    private String message;

    @NotBlank
    private String severity;       // INFO, WARNING, CRITICAL

    @NotBlank
    private String deliveryType;   // IN_APP, EMAIL, SMS

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime expiryTime;
    private boolean remindersEnabled = true;
    private List<Long> teamIds;    // visibility for teams
    private List<Long> userIds;    // visibility for users
}

