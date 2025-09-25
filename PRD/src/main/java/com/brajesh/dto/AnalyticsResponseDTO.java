package com.brajesh.dto;

import lombok.Data;
import java.util.Map;

@Data
public class AnalyticsResponseDTO {
    private long totalAlerts;
    private long deliveredCount;
    private long readCount;
    private long snoozedCount;
    private Map<String, Long> severityBreakdown; // INFO, WARNING, CRITICAL
}