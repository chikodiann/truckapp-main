package com.ann.truckApp.web;

import com.ann.truckApp.dto.response.ResetPasswordDTO;
import com.ann.truckApp.dto.request.ForgotPasswordDTO;
import com.ann.truckApp.dto.request.HandleLoginDTO;
import com.ann.truckApp.dto.request.HandleRegisterDTO;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${app.reset-password.base-url}")
    private String resetPasswordBaseUrl;

    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<BaseResponse<?>> create(@RequestBody HandleRegisterDTO handleRegisterDTO) {
        return new ResponseEntity<>(userService.handleRegister(handleRegisterDTO), HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<?>> login(@RequestBody HandleLoginDTO handleLoginDTO) {
        return new ResponseEntity<>(userService.handleLogin(handleLoginDTO), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/forgot-password")
    public ResponseEntity<BaseResponse<?>> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        return new ResponseEntity<>(userService.handleForgotPassword(forgotPasswordDTO), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/reset-password")
    public ResponseEntity<BaseResponse<?>> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
            userService.handleResetPassword(resetPasswordDTO.getEmail(), resetPasswordDTO.getOtp(), resetPasswordDTO.getNewPassword());
            return new ResponseEntity<>(new BaseResponse<>("Password reset successfully"), HttpStatus.OK);

    }
}