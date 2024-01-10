package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Trip;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.TripRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.request.TripDTO;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripService service;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public BaseResponse<?> createTrip(TripDTO tripDTO) {
        BaseResponse baseResponse;
        Users users = userRepository.findById(tripDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Could not find a user"));
        if(users.getType().name().equals(Type.CUSTOMER)){
            var driver  = userRepository.findById(tripDTO.getDriverId());
            if(driver.get().getType().name().equals(Type.DRIVER)){
                Trip trip = new Trip();
                trip.setUser(users);
                trip.setDriverId(driver.get().getId());
                trip.setFromCity(tripDTO.getFromCity());
                trip.setToCity(tripDTO.getToCity());
                trip.setStatus(false);
                tripRepository.save(trip);
                baseResponse = new BaseResponse<>();
                baseResponse.setStatusCode(200);
                baseResponse.setData(trip);
                baseResponse.setMessage("succesfully book a trip with "+ driver.get().getEmail());
            }

        }

        baseResponse = new BaseResponse<>();
        baseResponse.setStatusCode(-999);
        baseResponse.setData("error ocurred");
        baseResponse.setMessage("error ocurred");
        return  baseResponse;
    }
}
