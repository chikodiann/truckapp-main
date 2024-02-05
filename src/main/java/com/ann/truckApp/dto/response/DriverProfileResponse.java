package com.ann.truckApp.dto.response;

import com.ann.truckApp.domain.enums.TIER;
import com.ann.truckApp.domain.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private TIER subscriptionTier;
    private Type type;
}
