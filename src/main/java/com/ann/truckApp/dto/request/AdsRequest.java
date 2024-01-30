package com.ann.truckApp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdsRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String truck_type;
    private String email;
    private String from_city;
    private String from_province;
    private String from_neighborhood;
    private String to_city;
    private String to_province;
    private String to_neighborhood;
    private String type_of_load;

}
