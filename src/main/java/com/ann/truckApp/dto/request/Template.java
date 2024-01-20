package com.ann.truckApp.dto.request;

import lombok.*;

import java.util.List;

@Data
    public  class Template {
        private String name;
        private List<ComponentRequest> components;
        private Language language;




    }