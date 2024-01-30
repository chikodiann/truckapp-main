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
    private boolean status;
    private LocalDateTime expiration;
    private List<NotificationDTO> notifications;
}

