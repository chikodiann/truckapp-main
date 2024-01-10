package com.ann.truckApp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripDTO {

    private String fromCity;
    private String toCity;
    private Long userId;
    private Long driverId;




}
