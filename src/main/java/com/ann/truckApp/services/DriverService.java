package com.ann.truckApp.services;

import com.ann.truckApp.dto.response.BaseResponse;

public interface DriverService {
    BaseResponse<?> getAllDriver();
    BaseResponse<?> acceptTrip(Long tripId);
}
