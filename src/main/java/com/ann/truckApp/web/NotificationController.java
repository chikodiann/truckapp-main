package com.ann.truckApp.web;

import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.dto.request.NotificationDTO;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {


    private final NotificationService notificationService;
    @CrossOrigin(origins = "*")
    @GetMapping("/retrieve/{tripId}")
    public ResponseEntity<BaseResponse<List<Notification>>> retrieveNotifications(@PathVariable Long tripId){
        return new ResponseEntity<>(notificationService.retrieveNotifications(tripId), HttpStatus.OK);
    }

}
