package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.TIER;
import com.ann.truckApp.domain.model.Admin;
import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.AdminRepository;
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
    @Autowired
    private AdminRepository adminRepository;

    public BaseResponse<?> updateSubscriptionTier(Long adminId, String newSubscriptionTier) {
        Admin admin = adminRepository.findById(adminId).orElseThrow(() -> new ExceptionClass("Admin not found"));
        admin.setSubscriptionTier(TIER.valueOf(newSubscriptionTier));
        adminRepository.save(admin);
        return new BaseResponse<>("Subscription updated");
    }


    public BaseResponse<?> accept(Long adsId) {
        Notification notification = notificationRepository.findById(adsId).orElseThrow(() ->
                new ExceptionClass("Notification not found"));
        Users user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(() ->
                new ExceptionClass("User not found"));
        if (user instanceof Admin) {
            notification.setStatus(true);
            notificationRepository.save(notification);
            return new BaseResponse<>("Notification updated");
        } else {
            throw new ExceptionClass("Notification");
        }
    }
}

