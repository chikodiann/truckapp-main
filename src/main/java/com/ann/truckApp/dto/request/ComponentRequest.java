package com.ann.truckApp.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ComponentRequest {
    private List<Parameter> parameters;
    private String type;
}
