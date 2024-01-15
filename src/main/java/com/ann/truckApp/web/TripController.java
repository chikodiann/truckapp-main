package com.ann.truckApp.web;

import com.ann.truckApp.domain.model.Trip;
import com.ann.truckApp.dto.request.TripDTO;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;
    @CrossOrigin("*")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createTrip(@RequestBody TripDTO tripDTO) {
        System.out.println(tripDTO);
        return new ResponseEntity<>(tripService.createTrip(tripDTO),HttpStatus.OK);
    }

}
