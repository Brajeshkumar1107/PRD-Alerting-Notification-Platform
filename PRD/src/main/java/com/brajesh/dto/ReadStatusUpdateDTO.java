package com.brajesh.dto;

import lombok.Data;

@Data
public class ReadStatusUpdateDTO {
    private Long alertId;
    private Long userId;
    private boolean read;
}