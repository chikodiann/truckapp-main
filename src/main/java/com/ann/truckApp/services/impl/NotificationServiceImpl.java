package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.domain.repository.NotificationRepository;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Override

    public BaseResponse<List<Notification>> retrieveNotifications(Long tripId) {
        return new BaseResponse<>(notificationRepository.findByTripId(tripId));
    }

}
