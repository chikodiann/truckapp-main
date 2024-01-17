package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.TIER;
import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.NotificationRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.exceptions.ExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;

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

    public BaseResponse<?> accept(Long adsId) {
        Notification notification = notificationRepository.findById(adsId).orElseThrow(() ->
                new ExceptionClass("Notification not found"));
        Users user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() ->
                new ExceptionClass("User not found"));
        if(user.getType().equals(Type.ADMIN)){
            notification.setStatus(true);
            notificationRepository.save(notification);
            return new BaseResponse<>("notification updated");
        }else{
            throw new ExceptionClass("notification");
        }
    }
}
