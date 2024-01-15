package com.ann.truckApp.services;

import com.ann.truckApp.dto.response.BaseResponse;

public interface DriverService {
    BaseResponse<?> getAllDriver();
    BaseResponse<String> acceptTrip(Long tripId);
}
