package com.ann.truckApp.web;

import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/list")
    public ResponseEntity<BaseResponse<?>> getDriverList(){
        BaseResponse baseResponse = new BaseResponse<>(driverService.getAllDriver());
        return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);

    }

    @GetMapping("/accept/{tripiD}")
    public ResponseEntity<BaseResponse<?>> acceptTrip(@PathVariable Long tripId) {
        BaseResponse baseResponse = new BaseResponse<>(driverService.acceptTrip(tripId));
        return new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);

    }
}
