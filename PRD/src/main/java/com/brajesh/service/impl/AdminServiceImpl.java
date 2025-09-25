package com.brajesh.service.impl;

import com.brajesh.dto.AlertRequestDTO;
import com.brajesh.dto.AlertResponseDTO;
import com.brajesh.dto.CreateUserRequest;
import com.brajesh.dto.UserResponseDTO;
import com.brajesh.model.Alert;
import com.brajesh.model.enums.DeliveryType;
import com.brajesh.model.enums.Severity;
import com.brajesh.repository.AlertRepository;
import com.brajesh.repository.TeamRepository;
import com.brajesh.repository.UserRepository;
import com.brajesh.model.Team;
import com.brajesh.model.User;
import com.brajesh.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
    public class AdminServiceImpl implements AdminService {

    private final AlertRepository alertRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Override
    public AlertResponseDTO createAlert(AlertRequestDTO requestDTO) {
        Alert alert = mapToEntity(requestDTO);
        // audience
        if (requestDTO.getTeamIds() != null && !requestDTO.getTeamIds().isEmpty()) {
            alert.setTeams(teamRepository.findAllById(requestDTO.getTeamIds()));
        }
        if (requestDTO.getUserIds() != null && !requestDTO.getUserIds().isEmpty()) {
            alert.setUsers(userRepository.findAllById(requestDTO.getUserIds()));
        }
        alert = alertRepository.save(alert);
        return mapToDTO(alert);
    }

    @Override
    public AlertResponseDTO updateAlert(Long alertId, AlertRequestDTO requestDTO) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));

        alert.setTitle(requestDTO.getTitle());
        alert.setMessage(requestDTO.getMessage());
        alert.setSeverity(Severity.valueOf(requestDTO.getSeverity().toUpperCase()));
        alert.setDeliveryType(DeliveryType.valueOf(requestDTO.getDeliveryType().toUpperCase()));
        alert.setStartTime(requestDTO.getStartTime());
        alert.setExpiryTime(requestDTO.getExpiryTime());
        alert.setRemindersEnabled(requestDTO.isRemindersEnabled());
        if (requestDTO.getTeamIds() != null) {
            alert.setTeams(teamRepository.findAllById(requestDTO.getTeamIds()));
        }
        if (requestDTO.getUserIds() != null) {
            alert.setUsers(userRepository.findAllById(requestDTO.getUserIds()));
        }

        alert = alertRepository.save(alert);
        return mapToDTO(alert);
    }

    @Override
    public void archiveAlert(Long alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new RuntimeException("Alert not found"));
        alert.setExpiryTime(LocalDateTime.now()); // mark as expired
        alertRepository.save(alert);
    }

    // AdminServiceImpl.java
    @Override
    public List<AlertResponseDTO> listAlerts(Severity severity, String status, List<Long> teamIds, List<Long> userIds) {
        List<Alert> alerts;

        if (severity != null) {
            alerts = alertRepository.findBySeverity(severity); // Pass enum
        } else {
            alerts = alertRepository.findAll();
        }

        // filter by status
        LocalDateTime now = LocalDateTime.now();
        if ("active".equalsIgnoreCase(status)) {
            alerts = alerts.stream()
                    .filter(a -> a.getExpiryTime().isAfter(now))
                    .collect(Collectors.toList());
        } else if ("expired".equalsIgnoreCase(status)) {
            alerts = alerts.stream()
                    .filter(a -> a.getExpiryTime().isBefore(now))
                    .collect(Collectors.toList());
        }

        // filter by audience
        if (userIds != null && !userIds.isEmpty()) {
            alerts = alerts.stream()
                    .filter(a -> a.getUsers() != null && a.getUsers().stream().anyMatch(u -> userIds.contains(u.getId())))
                    .collect(Collectors.toList());
        }
        if (teamIds != null && !teamIds.isEmpty()) {
            alerts = alerts.stream()
                    .filter(a -> a.getTeams() != null && a.getTeams().stream().anyMatch(t -> teamIds.contains(t.getId())))
                    .collect(Collectors.toList());
        }

        return alerts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    // ---------------- Helper Mapping ---------------- //
    private Alert mapToEntity(AlertRequestDTO dto) {
        Alert alert = new Alert();
        alert.setTitle(dto.getTitle());
        alert.setMessage(dto.getMessage());
        alert.setSeverity(Severity.valueOf(dto.getSeverity().toUpperCase()));
        alert.setDeliveryType(DeliveryType.valueOf(dto.getDeliveryType().toUpperCase()));
        alert.setStartTime(dto.getStartTime());
        alert.setExpiryTime(dto.getExpiryTime());
        alert.setRemindersEnabled(dto.isRemindersEnabled());
        return alert;
    }

    private AlertResponseDTO mapToDTO(Alert alert) {
        AlertResponseDTO dto = new AlertResponseDTO();
        dto.setId(alert.getId());
        dto.setTitle(alert.getTitle());
        dto.setMessage(alert.getMessage());
        dto.setSeverity(String.valueOf(alert.getSeverity()));
        dto.setDeliveryType(String.valueOf(alert.getDeliveryType()));
        dto.setStartTime(alert.getStartTime());
        dto.setExpiryTime(alert.getExpiryTime());
        dto.setActive(alert.getExpiryTime().isAfter(LocalDateTime.now()));
        return dto;
    }

    @Override
    public UserResponseDTO createUser(CreateUserRequest request) {
        Team team = teamRepository.findById(request.getTeamId()).orElseThrow(() -> new RuntimeException("Team not found"));
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setTeam(team);
        User saved = userRepository.save(user);
        return new UserResponseDTO(saved.getId(), saved.getName(), saved.getEmail(), team.getId(), team.getName());
    }
}
