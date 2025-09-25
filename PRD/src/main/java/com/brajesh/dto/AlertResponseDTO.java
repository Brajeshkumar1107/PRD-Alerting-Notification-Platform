package com.brajesh.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlertResponseDTO {
    private Long id;
    private String title;
    private String message;
    private String severity;
    private String deliveryType;
    private LocalDateTime startTime;
    private LocalDateTime expiryTime;
    private boolean active;
}

