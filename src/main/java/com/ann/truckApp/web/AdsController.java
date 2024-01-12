package com.ann.truckApp.web;

import com.ann.truckApp.dto.request.AdsRequest;
import com.ann.truckApp.services.impl.AdsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ads")
public class AdsController {
    @Autowired
    private AdsServiceImpl adsService;

    @PostMapping("/add")
    public ResponseEntity<?> addAds(@RequestBody AdsRequest adsRequest){
        return new ResponseEntity<>(adsService.addAds(adsRequest), HttpStatus.OK);
    }

}
