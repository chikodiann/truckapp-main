package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.TIER;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.request.ForgotPasswordDTO;
import com.ann.truckApp.dto.request.HandleLoginDTO;
import com.ann.truckApp.dto.request.HandleRegisterDTO;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.dto.response.LoginResponse;
import com.ann.truckApp.dto.response.RegisterResponse;
import com.ann.truckApp.exceptions.ExceptionClass;
import com.ann.truckApp.security.JwtService;
import com.ann.truckApp.services.EmailService;
import com.ann.truckApp.services.OTPService;
import com.ann.truckApp.services.UserService;
import com.ann.truckApp.utils.OtpUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private OTPService otpService;
    @Autowired
    private EmailService emailService;

    @Override
    public BaseResponse<?> handleRegister(HandleRegisterDTO handleRegisterDTO) {
        if(userRepository.existsByEmail(handleRegisterDTO.getEmail())){
            throw new ExceptionClass("CUSTOMER ALREADY EXIST");
        }
        Users saveCustomer = saveUser(handleRegisterDTO);
        String otp = OtpUtils.generateOtp();
        otpService.sendotp_message(saveCustomer,otp);
        RegisterResponse save = modelMapper.map(saveCustomer, RegisterResponse.class);
        return new BaseResponse<>(save);

    }

    @Override
    public BaseResponse<?> handleLogin(HandleLoginDTO handleLoginDTO) {
        Users users = userRepository.findByEmail(handleLoginDTO.getEmail())
                .orElseThrow(()->new ExceptionClass("USER_NOT_FOUND"));
        if (users.isStatus() && (passwordEncoder.matches(handleLoginDTO.getPassword(), users.getPassword())
                || users.getPassword() == null)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(users.getEmail(),users.getPassword());
            String jwtToken = jwtService.generateToken(authentication);
            String refreshtoken = jwtService.generateRefreshToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new BaseResponse<>(loginResponse(jwtToken,refreshtoken,users));
        }
        throw  new ExceptionClass("INVALID_CREDENTIALS OR ACTIVATE YOUR ACCOUNT");
    }

    @Override
    public BaseResponse<?> handleForgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        Users user = userRepository.findByEmail(forgotPasswordDTO.getEmail())
                .orElseThrow(() -> new ExceptionClass("USER_NOT_FOUND"));

        String otp = OtpUtils.generateOtp();

        user.setOtp(otp);
        userRepository.save(user);
        emailService.sendOtpForPasswordReset(user, otp);

        return new BaseResponse<>("Reset password OTP sent successfully");
    }

    @Override
    public BaseResponse<?> handleResetPassword(String email, String otp, String newPassword) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ExceptionClass("USER_NOT_FOUND"));
        if (!user.getOtp().equals(otp)) {
            throw new ExceptionClass("Invalid OTP");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);
        userRepository.save(user);
        return new BaseResponse<>("Password reset successfully");
    }


    private LoginResponse loginResponse(String jwt, String refreshToken, Users customer){
        return LoginResponse.builder()
                .type(customer.getType())
                .accessToken(jwt)
                .refreshAccesstoken(refreshToken)
                .email(customer.getEmail())
                .build();
    }
    private Users saveUser(HandleRegisterDTO customer){
        return userRepository.save(Users.builder()
                .email(customer.getEmail())
                .type(customer.getType())
                .password(passwordEncoder.encode(customer.getPassword()))
                .phoneNumber(customer.getPhoneNumber()).
                lastName(customer.getLastName())
                .firstName(customer.getFirstName())
                .subscriptionTier(TIER.FREE_TIER)

                .status(false)
                .build());
    }
}
