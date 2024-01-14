package com.ann.truckApp.services;

import com.ann.truckApp.domain.model.Trip;
import com.ann.truckApp.dto.request.TripDTO;
import com.ann.truckApp.dto.response.BaseResponse;

public interface TripService {
          BaseResponse<Object> createTrip(TripDTO tripDTO);
}
