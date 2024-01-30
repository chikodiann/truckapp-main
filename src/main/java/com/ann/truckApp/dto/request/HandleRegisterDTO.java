package com.ann.truckApp.dto.request;

import com.ann.truckApp.domain.enums.Type;
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
    private Type type;
    private  String firstName;
    private  String lastName;

}
