package com.ann.truckApp.web;

import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.impl.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    private GeolocationService geolocationService;
    @CrossOrigin(origins = "*")
    @PostMapping("/{cityName}")
    public BaseResponse<?> getGeoLocation(@PathVariable String cityName) {
        return geolocationService.getGeoLocation(cityName);
    }
}
