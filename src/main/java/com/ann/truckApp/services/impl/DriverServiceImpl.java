package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Trip;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.TripRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.dto.response.DriverResponse;
import com.ann.truckApp.services.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TripRepository tripRepository;
    @Override
    public BaseResponse<?> getAllDriver(){
        Users user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
if(user == null){
    throw new IllegalStateException("User " + SecurityContextHolder.getContext().getAuthentication().getName() + "is not found");
}
        BaseResponse baseResponse;
        List<DriverResponse> driverResponseList = userRepository.findUsersByType(Type.DRIVER)
                .stream()
                .map((value)->{
                     return    modelMapper.map(value, DriverResponse.class);


                })
                .toList();
        baseResponse = new BaseResponse<>();
        baseResponse.setData(driverResponseList);
        baseResponse.setMessage("Driver response");
        baseResponse.setStatusCode(000);
        return baseResponse;

    }

    @Override
    public BaseResponse<?> acceptTrip(Long tripId){
        BaseResponse baseResponse;
        Users driver = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(()->new RuntimeException("Could not find"));
        Trip trip = tripRepository.findById(tripId).get();
        if(driver.getType().name().equals(Type.DRIVER)) {
            if (trip == null) {
                throw new IllegalStateException("Trip not found");
            }
            trip.setStatus(true);
            tripRepository.save(trip);
            baseResponse = new BaseResponse<>();
            baseResponse.setMessage("Trip accepted");
            baseResponse.setData(trip);
            baseResponse.setStatusCode(200);
            return baseResponse;
        }

        throw new IllegalStateException("User not authenticated to be a driver");
    }
}
