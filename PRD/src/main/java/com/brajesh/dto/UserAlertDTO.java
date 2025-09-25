package com.brajesh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAlertDTO {
    private Long alertId;
    private String title;
    private String message;
    private String severity;
    private LocalDateTime deliveredAt;
    private boolean read;
    private boolean snoozed;


}
