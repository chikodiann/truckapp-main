package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.domain.model.Trip;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.NotificationRepository;
import com.ann.truckApp.domain.repository.TripRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.request.TripDTO;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public BaseResponse<Object> createTrip(TripDTO tripDTO) {
        BaseResponse baseResponse;

        Users users = userRepository.findById(tripDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Could not find a user"));

            Users driver  = userRepository.findById(tripDTO.getDriverId()).get();
            if(driver.getType().equals(Type.DRIVER)){
                System.out.println("****** ");
                Trip trip = new Trip();
                trip.setUser(users);
                trip.setDriverId(driver.getId());
                trip.setFromCity(tripDTO.getFromCity());

                trip.setToCity(tripDTO.getToCity());
                trip.setStatus(false);
                Notification notification = new Notification();
                notification.setTrip(trip);
                notification.setMessage("trip");
                notificationRepository.save(notification);
                trip.setNotifications(List.of(notification));
                tripRepository.save(trip);
                baseResponse = new BaseResponse<>();
                baseResponse.setStatusCode(200);
                baseResponse.setData(trip);
                baseResponse.setMessage("succesfully book a trip with "+ driver.getEmail());
                return baseResponse;
            }else{
                throw new IllegalArgumentException("Invalid");
            }


    }
}
