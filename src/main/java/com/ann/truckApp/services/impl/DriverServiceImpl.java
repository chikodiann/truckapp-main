package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.repository.NotificationRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.dto.response.DriverResponse;
import com.ann.truckApp.services.DriverService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

        BaseResponse baseResponse;
        List<DriverResponse> driverResponseList = userRepository.findAll()
                .stream()
                .map(value -> modelMapper.map(value, DriverResponse.class))
                .toList();

        baseResponse = new BaseResponse<>();
        baseResponse.setData(driverResponseList);
        baseResponse.setMessage("Driver response");
        baseResponse.setStatusCode(200);
        return baseResponse;
    }


}
