package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.NotificationRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.dto.response.DriverProfileResponse;
import com.ann.truckApp.dto.response.DriverResponse;
import com.ann.truckApp.exceptions.ExceptionClass;
import com.ann.truckApp.services.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;



    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public BaseResponse<?> getAllDriver(){

        BaseResponse baseResponse;
        List<DriverResponse> driverResponseList = userRepository.findAll()
                .stream()
                .filter(driver -> driver.getType().name().equals(Type.DRIVER.name()))
                .map(value -> modelMapper.map(value, DriverResponse.class))
                .toList();

        baseResponse = new BaseResponse<>();
        baseResponse.setData(driverResponseList);
        baseResponse.setMessage("Driver response");
        baseResponse.setStatusCode(200);
        return baseResponse;
    }

    @Override
    public BaseResponse<?> getDriverProfile(Long userId) {
        Optional<Users> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            DriverProfileResponse profileResponse = modelMapper.map(user, DriverProfileResponse.class);
            BaseResponse baseResponse = new BaseResponse<>(profileResponse);
            baseResponse.setMessage("Driver profile response");
            baseResponse.setStatusCode(200);
            return baseResponse;
        } else {
            throw new ExceptionClass("Driver not found");
        }
    }


}
