package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.model.Ads;
import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.AdsRepository;
import com.ann.truckApp.domain.repository.NotificationRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.request.AdsRequest;
import com.ann.truckApp.dto.request.WhatsappMessageRequest;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.exceptions.ExceptionClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AdsServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private RestTemplate restTemplate;

    public BaseResponse<?> addAds(AdsRequest adsRequest){
        Users users = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(()->new ExceptionClass("Could not find"));
        Ads ads = new Ads();
        ads.setEmail(users.getEmail());
        ads.setUser(users);
        ads.setLastName(users.getLastName());
        ads.setFrom_city(adsRequest.getFrom_city());
        ads.setTo_city(adsRequest.getTo_city());
        ads.setTypeVehicle(adsRequest.getTypeVehicle());
        Notification notification = new Notification();
        notification.setAds(ads);
        notification.setStatus(false);
        notification.setMessage(adsRequest.getFrom_neighborhood());
        if (ads.getNotifications()==null){
            ads.setNotifications(List.of(notification));
        }else{
            ads.getNotifications().add(notification);
        }
        ads.setTypeLoad(adsRequest.getTypeLoad());
        adsRepository.save(ads);
        notificationRepository.save(notification);
        WhatsappMessageRequest whatsappMessageRequest = new WhatsappMessageRequest();
        whatsappMessageRequest.setMessagingProduct("");
        whatsappMessageRequest.setTo(adsRequest.getTo_city());
        whatsappMessageRequest.setType("template");

        WhatsappMessageRequest.Template template = new WhatsappMessageRequest.Template();
        template.setName(adsRequest.getLastName());

        WhatsappMessageRequest.Language language = new WhatsappMessageRequest.Language();
        language.setCode("en_US");
        template.setLanguage(language);

        whatsappMessageRequest.setTemplate(template);

        ResponseEntity<Object> response= restTemplate.postForEntity("https://graph.facebook.com/v18.0/186616854543443/messages",
                whatsappMessageRequest,Object.class);
        System.out.println(response.getBody());
        return new BaseResponse<>(response.getBody());

    }

}
