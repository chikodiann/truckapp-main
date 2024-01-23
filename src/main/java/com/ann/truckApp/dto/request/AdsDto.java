package com.ann.truckApp.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdsDto {
    private Long id;
    private String lastName;
    private String email;
    private String fromCity;
    private String fromProvince;
    private String fromNeighborhood;
    private String toCity;
    private String toProvince;
    private boolean status;
    private String toNeighborhood;
    private String typeVehicle;
    private String typeLoad;
    private LocalDateTime expiration;
    private List<NotificationDTO> notifications;


}

