package com.ann.truckApp.services.impl;

import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.dto.response.GeoLocationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocationService {

    @Value("${geolocation.api.url}")
    private String geolocationApiUrl;

    @Value("${geolocation.api.key}")
    private String geolocationApiKey;

    private final RestTemplate restTemplate;

    public GeolocationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BaseResponse<GeoLocationResponse> getGeoLocation(String cityName) {
        String apiUrl = String.format("%s?q=%s&apiKey=%s", geolocationApiUrl, cityName, geolocationApiKey);
        return new BaseResponse<>(restTemplate.getForObject(apiUrl, GeoLocationResponse.class));
    }
}
