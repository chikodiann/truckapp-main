package com.ann.truckApp.exceptions.handler;

import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.exceptions.CustomExceptionResponse;
import com.ann.truckApp.exceptions.ExceptionClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptions {
    @ExceptionHandler(ExceptionClass.class)
    public ResponseEntity<BaseResponse<CustomExceptionResponse>> customerException(ExceptionClass e){
        CustomExceptionResponse customExceptionResponse = CustomExceptionResponse.builder()
                .message(e.getMessage())
                .statusCode(HttpStatus.CONFLICT.value())
                .dates("")
                .build();
        BaseResponse<CustomExceptionResponse> apiResponse = new BaseResponse<>(customExceptionResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }



}