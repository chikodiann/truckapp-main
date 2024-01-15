package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.model.Ads;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.AdsRepository;
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

@Service
public class AdsServiceImpl {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private RestTemplate restTemplate;

    public BaseResponse<?> addAds(AdsRequest adsRequest){
        Users users = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(()->new ExceptionClass("Could not find"));
        Ads ads = new Ads();
        ads.setEmail(ads.getEmail());
        ads.setUser(users);
        ads.setTo_(adsRequest.getTo_());
        ads.setLastName(ads.getLastName());
        ads.setTypeVehicle(adsRequest.getTypeVehicle());
        adsRepository.save(ads);
        WhatsappMessageRequest whatsappMessageRequest = new WhatsappMessageRequest();
        whatsappMessageRequest.setMessagingProduct("");
        whatsappMessageRequest.setTo(adsRequest.getTo_());
        whatsappMessageRequest.setType("template");
        WhatsappMessageRequest.Template template = new WhatsappMessageRequest.Template();
        template.setName(adsRequest.getLastName());
        WhatsappMessageRequest.Language language = new WhatsappMessageRequest.Language();
        language.setCode("en_US");
        template.setLanguage(language);
        whatsappMessageRequest.setTemplate(template);
        ResponseEntity<WhatsappMessageRequest> response= restTemplate.postForEntity("",whatsappMessageRequest,WhatsappMessageRequest.class);
        System.out.println(response.getBody());
        return new BaseResponse<>(ads);

    }

}
