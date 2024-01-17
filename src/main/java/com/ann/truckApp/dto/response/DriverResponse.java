package com.ann.truckApp.dto.response;

import com.ann.truckApp.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@SuppressWarnings("unchecked")
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponse {
    private Long id;

    private String phoneNumber;
    private String email;

    private Type type;
}
