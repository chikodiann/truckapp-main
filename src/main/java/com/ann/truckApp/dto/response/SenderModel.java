package com.ann.truckApp.dto.response;

import lombok.*;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class SenderModel {
        private String name;
        private String email;
    }
