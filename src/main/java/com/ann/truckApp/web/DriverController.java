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
    @GetMapping("/{userId}/profile")
    public ResponseEntity<BaseResponse<?>> getDriverProfile(@PathVariable Long userId) {
        try {
            BaseResponse baseResponse = driverService.getDriverProfile(userId);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (Exception e) {
            BaseResponse baseResponse = new BaseResponse<>();
            baseResponse.setStatusCode(500);
            baseResponse.setMessage("Error retrieving driver profile");
            return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
