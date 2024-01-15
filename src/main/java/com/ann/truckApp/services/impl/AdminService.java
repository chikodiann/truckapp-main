package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.TIER;
import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.exceptions.ExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    public BaseResponse<?> updateSubscriptionTier(Long userId, String newSubscriptionTier) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new ExceptionClass("User not found"));
        if(user.getType().equals(Type.ADMIN)) {
            user.setSubscriptionTier(TIER.valueOf(newSubscriptionTier));
            userRepository.save(user);
            return new BaseResponse<>("subscription updated");

        }else{
            throw new ExceptionClass("User not active");
        }
    }

}
