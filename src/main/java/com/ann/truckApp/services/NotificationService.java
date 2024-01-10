package com.ann.truckApp.services;

import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.dto.response.BaseResponse;

import java.util.List;

public interface NotificationService {
    BaseResponse<List<Notification>> retrieveNotifications(Long tripId);
}
