package com.ann.truckApp.dto.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ResetPasswordDTO {
    private String email;
    private String otp;
    private String newPassword;
}
