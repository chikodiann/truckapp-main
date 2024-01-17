package com.ann.truckApp.domain.repository;

import com.ann.truckApp.domain.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByAds_Id(Long adsId);
}
