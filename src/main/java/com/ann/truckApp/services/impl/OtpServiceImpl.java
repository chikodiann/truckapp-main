package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.model.OTP;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.OTPRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.events.listenersRequest.EventRegister;
import com.ann.truckApp.exceptions.ExceptionClass;
import com.ann.truckApp.services.OTPService;
import com.ann.truckApp.utils.OtpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
public class OtpServiceImpl implements OTPService {
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Override
    public void sendotp_message(Users users, String otp){
        publisher.publishEvent(new EventRegister(users,otp));
        OTP saveOtp = otpRepository.findByUsers(users).orElse(null);
        if(saveOtp==null) {
            OTP otpEntity = new OTP();
            otpEntity.setUsers(users);
            otpEntity.setOtp(otp);
            otpEntity.setExpiration(LocalDateTime.now().plusMinutes(5));
            otpRepository.save(otpEntity);
        }else{
            saveOtp.setUsers(users);
            saveOtp.setOtp(otp);
            saveOtp.setExpiration(LocalDateTime.now().plusMinutes(5));
            otpRepository.save(saveOtp);
        }
    }



    @Override
    public BaseResponse<String> verify_otp(String email,String otp) {
        Users users = userRepository.findByEmail(email)
                .orElseThrow(()->new ExceptionClass("CUSTOMER_NOT_FOUND"));

        OTP optUser = otpRepository.findByUsers(users)
                .orElseThrow(()-> new RuntimeException("OTP_NOT_FOUND"));
        System.out.println(isValid(optUser));
        if(isValid(optUser)){
            users.setStatus(true);
            userRepository.save(users);
            return new BaseResponse<>(users.getEmail()+" User has been verified");
        }else{
            throw new ExceptionClass("INVALID_OTP has expired");
        }
    }
    private boolean isValid(OTP otpUser){
        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime dateCreated = otpUser.getExpiration();
        Duration duration = Duration.between(dateCreated,dateNow);
        long elapsedTime = duration.toMinutes();
        long minute = 4;
        log.info("elapsedTime{} ",elapsedTime);
        log.info("minutes{} ",minute);
        return   elapsedTime <= minute;
    }

    @Override
    public BaseResponse<String> resendOTP(String email) {
        Users users =userRepository.findByEmail(email)
                .orElseThrow(()-> new ExceptionClass("CUSTOMER_NOT_FOUND"));
        sendotp_message(users, OtpUtils.generateOtp());
        return new BaseResponse<>("OTP_SENT_TO_MAIL");
    }
}
