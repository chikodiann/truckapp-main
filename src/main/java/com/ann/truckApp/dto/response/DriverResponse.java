package com.ann.truckApp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("unchecked")
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponse {
    private Long id;

    private String phoneNumber;
    private String email;
}
