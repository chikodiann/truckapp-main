package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.NotificationRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.dto.response.DriverResponse;
import com.ann.truckApp.exceptions.ExceptionClass;
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
    private NotificationRepository notificationRepository;
    @Override
    public BaseResponse<?> getAllDriver(){
        Users user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
if(user == null){
    throw new IllegalStateException("User " + SecurityContextHolder.getContext().getAuthentication().getName() + "is not found");
}
        BaseResponse baseResponse;
        List<DriverResponse> driverResponseList = userRepository.findAll()
                .stream()
                .filter(driver -> driver.getType().name().equals(Type.DRIVER.name()))
                .map(value -> modelMapper.map(value, DriverResponse.class))
                .toList();

        baseResponse = new BaseResponse<>();
        baseResponse.setData(driverResponseList);
        baseResponse.setMessage("Driver response");
        baseResponse.setStatusCode(000);
        return baseResponse;
    }


}
