package com.ann.truckApp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripDTO {

    private String fromCity;
    private String toCity;
    private Long driverId;
    private String vehicle_type;




}
