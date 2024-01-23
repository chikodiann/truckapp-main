package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.model.Ads;
import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.domain.repository.AdsRepository;
import com.ann.truckApp.domain.repository.NotificationRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.request.*;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.exceptions.ExceptionClass;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Value("${whatsap.api.number}")
    private String phone;
    @Value("${token.whatsapp}")
    private String bearerToken;

    @Scheduled(fixedRate = 600_000)
    public void deleteExpiredAds() {
        LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
        adsRepository.findAll()
                .forEach(ads -> {
                    LocalDateTime creationTime = ads.getExpiration();
                    if (creationTime.isBefore(twentyFourHoursAgo)) {
                        adsRepository.delete(ads);
                    }
                });
    }
    @Transactional
    public BaseResponse<?> addAds(AdsRequest adsRequest){

        Ads ads = new Ads();
        ads.setEmail(adsRequest.getEmail());

        ads.setFrom_province(adsRequest.getFrom_province());
        ads.setTo_province(adsRequest.getTo_province());
        ads.setFrom_neighborhood(adsRequest.getFrom_neighborhood());
        ads.setTo_neighborhood(adsRequest.getTo_neighborhood());
        ads.setTypeLoad(adsRequest.getTypeLoad());
        ads.setLastName(adsRequest.getLastName());
        ads.setFrom_city(adsRequest.getFrom_city());
        ads.setTo_city(adsRequest.getTo_city());
        ads.setTypeVehicle(adsRequest.getTypeVehicle());
ads.setStatus(true);
        Notification notification = new Notification();
        notification.setAds(ads);
        notification.setStatus(false);
        notification.setMessage(adsRequest.getFrom_neighborhood());
        if (ads.getNotifications()==null){
            ads.setNotifications(List.of(notification));
        }else{
            ads.getNotifications().add(notification);
        }

        adsRepository.save(ads);
        notificationRepository.save(notification);
        try {
            WhatsappMessageRequest whatsappMessageRequest = new WhatsappMessageRequest();
            whatsappMessageRequest.setMessaging_product("whatsapp");
            whatsappMessageRequest.setTo(phone);
            whatsappMessageRequest.setType("template");

            Template template = new Template();
            template.setName("create_ads");
            List<Parameter> parameters = new ArrayList<>();
            ComponentRequest componentRequestss = new ComponentRequest();
            List<ComponentRequest> componentRequests = new ArrayList<>();
            for (int i = 0; i <12;i++){
                parameters.add(new Parameter("text",whatsappMessageRequest.getMessaging_product()));

            }
            componentRequestss.setParameters(parameters);
            componentRequestss.setType("body");
            componentRequests.add(componentRequestss);
            template.setComponents(componentRequests);

            Language language = new Language();
            language.setCode("en_US");
            template.setLanguage(language);

            whatsappMessageRequest.setTemplate(template);
            System.out.println(whatsappMessageRequest);
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(bearerToken);

            HttpEntity<Object> requestEntity = new HttpEntity<>(whatsappMessageRequest, headers);
            ResponseEntity<Object> response = restTemplate.postForEntity("https://graph.facebook.com/v18.0/186616854543443/messages",
                    requestEntity, Object.class);
            System.out.println(response.getBody());
            return new BaseResponse<>(response.getBody());
        }catch (Exception e){
            throw  new ExceptionClass(e.getMessage());
        }
    }


    public List<Ads> getAds(){
        userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(()->new ExceptionClass("Not Authenticated"));

        return adsRepository.findAll()
                .stream()
                .filter(Ads::isStatus)
                .collect(Collectors.toList());
    }


}
