package com.ann.truckApp.dto.request;

import lombok.*;

@Getter
@Setter
    public  class Template {
        private String name;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private String fromCity;
        private String fromProvince;
        private String fromNeighborhood;
        private String toCity;
        private String toProvince;
        private String toNeighborhood;
        private String typeVehicle;
        private String typeLoad;




    }