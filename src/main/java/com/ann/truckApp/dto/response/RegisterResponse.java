package com.ann.truckApp.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    private String email;
    private String status;
    private String message;
    private  String firstName;
    private  String lastName;



}