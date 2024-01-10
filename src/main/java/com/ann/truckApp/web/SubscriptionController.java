package com.ann.truckApp.web;

import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.impl.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/update/{userId}")
    public ResponseEntity<BaseResponse<?>> updateSubscriptionTier(@PathVariable Long userId, @RequestParam String newSubscriptionTier) {
        subscriptionService.updateSubscriptionTier(userId, newSubscriptionTier);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
