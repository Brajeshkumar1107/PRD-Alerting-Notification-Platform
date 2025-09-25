package com.brajesh.controller;

import com.brajesh.dto.AnalyticsDTO;
import com.brajesh.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/summary")
    public ResponseEntity<AnalyticsDTO> getSummary() {
        return ResponseEntity.ok(analyticsService.getAnalyticsSummary());
    }
}


