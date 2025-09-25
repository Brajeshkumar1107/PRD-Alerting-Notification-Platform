package com.brajesh.repository;

import com.brajesh.model.NotificationDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationDeliveryRepository extends JpaRepository<NotificationDelivery, Long> {
    List<NotificationDelivery> findByAlertId(Long alertId);
    List<NotificationDelivery> findByUserId(Long userId);
    
    @Query("SELECT nd FROM NotificationDelivery nd WHERE nd.alert.id = :alertId AND nd.user.id = :userId ORDER BY nd.deliveredAt DESC")
    List<NotificationDelivery> findRecentForUserAndAlert(@Param("alertId") Long alertId, @Param("userId") Long userId);
}
