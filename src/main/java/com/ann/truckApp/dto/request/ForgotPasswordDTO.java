package com.ann.truckApp.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ForgotPasswordDTO {
    private String email;
}