package com.ann.truckApp.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class HandleRegisterDTO {
    private String phoneNumber;
    private String email;
    private String password;
    private String type;
    private  String firstName;
    private  String lastName;

}
