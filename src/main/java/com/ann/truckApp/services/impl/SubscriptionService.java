package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.TIER;
import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    @Autowired
    private UserRepository userRepository;

    public BaseResponse<?> updateSubscriptionTier(Long userId, String newSubscriptionTier) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new CustomerNotFoundException("User not found"));
        if(user.getType().equals(Type.ADMIN)) {
            user.setSubscriptionTier(TIER.valueOf(newSubscriptionTier));
            userRepository.save(user);
            return new BaseResponse<>("subscriptiuon updated");

        }else{
            throw new CustomerNotFoundException("User not active");
        }
    }

}
