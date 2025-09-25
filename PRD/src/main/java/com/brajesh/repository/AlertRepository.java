package com.brajesh.repository;

import com.brajesh.model.Alert;
import com.brajesh.model.enums.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    List<Alert> findBySeverity(Severity severity);
    List<Alert> findByRemindersEnabledTrue();
    List<Alert> findByRemindersEnabledTrueAndExpiryTimeAfter(LocalDateTime now);
    List<Alert> findBySeverity(String severity);
    List<Alert> findByStartTimeBeforeAndExpiryTimeAfter(LocalDateTime start, LocalDateTime end);

}
