package com.ann.truckApp.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdsRequest {

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String from_city;

    @Column(nullable = false)
    private String from_province;

    @Column(nullable = false)
    private String from_neighborhood;

    @Column(nullable = false)
    private String to_city;

    @Column(nullable = false)
    private String to_province;

    @Column(nullable = false)
    private String to_neighborhood;

    @Column(nullable = false)
    private String typeVehicle;

    @Column(nullable = false)
    private String typeLoad;

}
