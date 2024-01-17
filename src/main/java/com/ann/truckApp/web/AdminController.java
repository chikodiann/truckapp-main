package com.ann.truckApp.web;

import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.impl.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class AdminController {

    @Autowired
    private AdminService subscriptionService;
    @CrossOrigin(origins = "*")
    @PostMapping("/update/{userId}")
    public ResponseEntity<BaseResponse<?>> updateSubscriptionTier(@PathVariable Long userId, @RequestParam String newSubscriptionTier) {
        return new ResponseEntity<>(subscriptionService.updateSubscriptionTier(userId, newSubscriptionTier),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/accept/{adsId}")
    public ResponseEntity<BaseResponse<?>> accept(@PathVariable Long adsId) {
        return new ResponseEntity<>(new BaseResponse<>(subscriptionService.accept(adsId)),HttpStatus.OK);
    }

}
