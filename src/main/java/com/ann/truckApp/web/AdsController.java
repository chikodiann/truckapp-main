package com.ann.truckApp.web;

import com.ann.truckApp.domain.model.Ads;
import com.ann.truckApp.dto.request.AdsRequest;
import com.ann.truckApp.services.impl.AdsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdsController {
    @Autowired
    private AdsServiceImpl adsService;
    @CrossOrigin(origins = "*")
    @PostMapping("/add")
    public ResponseEntity<?> addAds(@RequestBody AdsRequest adsRequest){
        return new ResponseEntity<>(adsService.addAds(adsRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/getAds")
    public ResponseEntity<List<Ads>> getAds(){
        return new ResponseEntity<>(adsService.getAds(),HttpStatus.OK);
    }

}
