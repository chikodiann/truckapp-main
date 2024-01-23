package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.model.Ads;
import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.domain.repository.AdsRepository;
import com.ann.truckApp.domain.repository.NotificationRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdsServiceImpl {
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
        ads.setFirstName((adsRequest.getFirstName()));
        ads.setLastName(adsRequest.getLastName());
        ads.setFrom_province(adsRequest.getFrom_province());
        ads.setTo_province(adsRequest.getTo_province());
        ads.setFrom_neighborhood(adsRequest.getFrom_neighborhood());
        ads.setTo_neighborhood(adsRequest.getTo_neighborhood());
        ads.setTypeLoad(adsRequest.getTypeLoad());
        ads.setPhoneNumber(adsRequest.getPhoneNumber());
        ads.setFrom_city(adsRequest.getFrom_city());
        ads.setTo_city(adsRequest.getTo_city());
        ads.setTypeVehicle(adsRequest.getTypeVehicle());
        ads.setTypeLoad(adsRequest.getTypeLoad());
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

            parameters.add(new Parameter("text", ads.getFirstName()));
            parameters.add(new Parameter("text", ads.getLastName()));
            parameters.add(new Parameter("text", ads.getPhoneNumber()));
            parameters.add(new Parameter("text", ads.getTypeVehicle()));
            parameters.add(new Parameter("text", ads.getEmail()));
            parameters.add(new Parameter("text", ads.getFrom_city()));
            parameters.add(new Parameter("text", ads.getFrom_province()));
            parameters.add(new Parameter("text", ads.getFrom_neighborhood()));
            parameters.add(new Parameter("text", ads.getTo_city()));
            parameters.add(new Parameter("text", ads.getTo_province()));
            parameters.add(new Parameter("text", ads.getTo_neighborhood()));
            parameters.add(new Parameter("text", ads.getTypeLoad()));



            ComponentRequest componentRequestss = new ComponentRequest();

            componentRequestss.setParameters(parameters);
            componentRequestss.setType("body");

            List<ComponentRequest> componentRequests = new ArrayList<>();
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
        return adsRepository.findAll();
    }


}
