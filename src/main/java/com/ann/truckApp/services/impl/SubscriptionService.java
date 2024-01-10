package com.ann.truckApp.services.impl;

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
        user.setSubscriptionTier(newSubscriptionTier);
        userRepository.save(user);
        return new BaseResponse<>("subscriptiuon updated");
    }

}
