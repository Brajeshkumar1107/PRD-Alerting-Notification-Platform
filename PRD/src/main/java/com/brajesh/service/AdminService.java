package com.brajesh.service;

import com.brajesh.dto.AlertRequestDTO;
import com.brajesh.dto.AlertResponseDTO;
import com.brajesh.model.enums.Severity;

import java.util.List;

public interface AdminService {
    AlertResponseDTO createAlert(AlertRequestDTO requestDTO);
    AlertResponseDTO updateAlert(Long alertId, AlertRequestDTO requestDTO);
    void archiveAlert(Long alertId);
    // AdminService.java
    List<AlertResponseDTO> listAlerts(Severity severity, String status, java.util.List<Long> teamIds, java.util.List<Long> userIds);

    com.brajesh.dto.UserResponseDTO createUser(com.brajesh.dto.CreateUserRequest request);

}