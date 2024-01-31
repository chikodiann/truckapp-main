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
@RequestMapping("/api/ads")
public class AdsController {
    @Autowired
    private AdsServiceImpl adsService;
    @CrossOrigin(origins = "*")
    @PostMapping("/add")
    public ResponseEntity<?> addAds(@RequestBody AdsRequest adsRequest){
        return new ResponseEntity<>(adsService.addAds(adsRequest), HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAds")
    public ResponseEntity<List<Ads>> getAds(){
        return new ResponseEntity<>(adsService.getAds(),HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/getAdById/{id}")
    public ResponseEntity<?> getAdById(@PathVariable Long id) {
            Ads ad = adsService.getAdById(id);
            return new ResponseEntity<>(ad, HttpStatus.OK);
        }
}
