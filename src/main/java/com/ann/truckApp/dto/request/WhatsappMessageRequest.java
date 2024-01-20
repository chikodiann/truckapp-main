package com.ann.truckApp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsappMessageRequest {
    private String messaging_product;
    private String to;
    private String type;
    private Template template;




}
