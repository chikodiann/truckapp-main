package com.ann.truckApp.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class WhatsappMessageRequest {
    private String messaging_product;
    private String to;
    private String type;
    private Template template;




}
