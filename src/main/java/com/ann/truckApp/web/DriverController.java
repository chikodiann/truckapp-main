package com.ann.truckApp.web;

import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/drivers")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @CrossOrigin(origins = "*")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<?>> getDriverList(){
        BaseResponse baseResponse = new BaseResponse<>(driverService.getAllDriver());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);

    }
    @CrossOrigin(origins = "*")
    @GetMapping("/accept/{trip_id}")
    public ResponseEntity<BaseResponse<String>> acceptTrip(@PathVariable("trip_id") Long trip_id) {
        BaseResponse baseResponse = new BaseResponse<>(driverService.acceptTrip(trip_id));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);

    }
}
