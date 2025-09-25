package com.brajesh.repository;

import com.brajesh.model.UserAlertPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAlertPreferenceRepository extends JpaRepository<UserAlertPreference, Long> {
    List<UserAlertPreference> findByUserId(Long userId);
    List<UserAlertPreference> findByAlertId(Long alertId);
    Optional<UserAlertPreference> findByUserIdAndAlertId(Long userId, Long alertId);

}
