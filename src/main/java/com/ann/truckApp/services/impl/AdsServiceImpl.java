package com.ann.truckApp.services.impl;

import com.ann.truckApp.domain.enums.Type;
import com.ann.truckApp.domain.model.Ads;
import com.ann.truckApp.domain.model.Notification;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.domain.repository.AdsRepository;
import com.ann.truckApp.domain.repository.NotificationRepository;
import com.ann.truckApp.domain.repository.UserRepository;
import com.ann.truckApp.dto.request.*;
import com.ann.truckApp.dto.response.BaseResponse;
import com.ann.truckApp.exceptions.ExceptionClass;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdsServiceImpl {
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${whatsap.api.number}")
    private String phone;
    @Value("${token.whatsapp}")
    private String bearerToken;
    @Value("${twilio.accountSid}")
    private String twilioAccountSid;
    @Value("${twilio.authToken}")
    private String twilioAuthToken;
    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;


    @Transactional
    @Scheduled(cron = "0 0 0/24 * * ?")
    public void deleteExpiredAds() {
        log.info("Scheduled task to delete expired ads started");

        LocalDateTime currentDateTime = LocalDateTime.now();
        log.info("{}",adsRepository.findAll());


                    adsRepository.findAll().forEach(ads -> {
                        LocalDateTime creationTime = ads.getCreationTimestamp();
                        LocalDateTime expirationTime = creationTime.plusHours(24);
                        ads.setStatus(false);


                            log.info("Deleting expired ad with ID: {}", ads.getId());
                            log.info("Deleting expired ad with ID: {}", ads.getStatus());
                            adsRepository.save(ads);

                    });




        log.info("Scheduled task to delete expired ads completed");
    }

    @Transactional
    public BaseResponse<?> addAds(AdsRequest adsRequest){
        Ads ads = new Ads();
        ads.setFirstname(adsRequest.getFirstName());
        ads.setLastname(adsRequest.getLastName());
        ads.setPhone(adsRequest.getPhone());
        ads.setTruck_type(adsRequest.getTruck_type());
        ads.setEmail(adsRequest.getEmail());
        ads.setFrom_city(adsRequest.getFrom_city());
        ads.setFrom_province(adsRequest.getFrom_province());
        ads.setFrom_neighborhood(adsRequest.getFrom_neighborhood());
        ads.setTo_city(adsRequest.getTo_city());
        ads.setTo_province(adsRequest.getTo_province());
        ads.setTo_neighborhood(adsRequest.getTo_neighborhood());
        ads.setType_of_load(adsRequest.getType_of_load());
        ads.setCreationTimestamp(LocalDateTime.now());
        ads.setExpiration(LocalDateTime.now().plusMinutes(5));
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
            template.setName("blocks");
            List<Parameter> parameters = new ArrayList<>();

            parameters.add(new Parameter("text", ads.getFirstname()));
            parameters.add(new Parameter("text", ads.getLastname()));
            parameters.add(new Parameter("text", ads.getPhone()));
            parameters.add(new Parameter("text", ads.getTruck_type()));
            parameters.add(new Parameter("text", ads.getEmail()));
            parameters.add(new Parameter("text", ads.getFrom_city()));
            parameters.add(new Parameter("text", ads.getFrom_province()));
            parameters.add(new Parameter("text", ads.getFrom_neighborhood()));
            parameters.add(new Parameter("text", ads.getTo_city()));
            parameters.add(new Parameter("text", ads.getTo_province()));
            parameters.add(new Parameter("text", ads.getTo_neighborhood()));
            parameters.add(new Parameter("text", ads.getType_of_load()));

            for (Parameter parameter : parameters) {
                System.out.println("Parameter: " + parameter.getType() + " - " + parameter.getText());
            }

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
            headers.set("Authorization", "Bearer " + bearerToken);
            HttpEntity<Object> requestEntity = new HttpEntity<>(whatsappMessageRequest, headers);

            ResponseEntity<Object> response = restTemplate.postForEntity("https://graph.facebook.com/v18.0/186616854543443/messages",
                    requestEntity,
                    Object.class
            );
            log.info("WhatsApp message sent successfully: {}", response.getBody());

            List<String> driverPhoneNumbers = getDriversPhoneNumbers();
            sendSMSToPhoneNumbers(driverPhoneNumbers, ads);

            return new BaseResponse<>(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("Error sending WhatsApp message. Status code: {}, Response body: {}", e.getRawStatusCode(), e.getResponseBodyAsString());
            throw new ExceptionClass("Error sending WhatsApp message");
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage());
            throw new ExceptionClass("Unexpected error");
        }
    }

    public Ads getAdById(Long id) {
        return adsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ad not found with id: " + id));
    }


    public List<AdsDto> getAds() {
        return adsRepository.findAll()
                .stream()
                .filter(Ads::getStatus) // Filter only ads with status set to true
                .map(this::mapAdsToAds)
                .collect(Collectors.toList());
    }

    private AdsDto mapAdsToAds(Ads ads){
        AdsDto adds = new AdsDto();
        adds.setEmail(ads.getEmail());
        adds.setPhone(ads.getPhone());
        adds.setFrom_city(ads.getFrom_city());
        adds.setFrom_province(ads.getFrom_province());
        adds.setId(ads.getId());
        adds.setExpiration(ads.getExpiration());
        adds.setNotifications(ads.getNotifications().stream()
                        .map((value)->{
                           return new NotificationDTO(value.getMessage());
                        })
                .collect(Collectors.toList()));
        adds.setFirstName(ads.getFirstname());
        adds.setType_of_load(ads.getType_of_load());
        adds.setFrom_neighborhood(ads.getFrom_neighborhood());
        adds.setTo_city(ads.getTo_city());
        adds.setStatus(ads.getStatus());
        return adds;


    }
    private List<String> getDriversPhoneNumbers() {
        List<Users> activeDrivers = userRepository.findByType(Type.DRIVER)
                .orElse(Collections.emptyList()); // Provide an empty list if no result is present

        return activeDrivers.stream()
                .map(Users::getPhoneNumber)
                .collect(Collectors.toList());
    }

    private String sendSMSToPhoneNumbers(List<String> phoneNumbers, Ads ads) {
        Twilio.init(twilioAccountSid, twilioAuthToken);

        for (String phoneNumber : phoneNumbers) {

            String smsMessage = createSMSMessage(ads); // Create a message based on ad details
            try {
                sendSMS(phoneNumber, smsMessage);
                log.info("SMS sent successfully to phone number: {}", phoneNumber);
            } catch (Exception e) {
                log.error("Error sending SMS to phone number {}: {}", phoneNumber, e.getMessage());
                return "Error sending SMS: " + e.getMessage();
            }
        }
        return "SMS sent successfully to all phone numbers";
    }
    private String createSMSMessage(Ads ads) {
        StringBuilder message = new StringBuilder();
        message.append("New ad created!\n");
        message.append("From: ").append(ads.getFrom_city()).append(", ").append(ads.getFrom_province()).append("\n");
        message.append("To: ").append(ads.getTo_city()).append(", ").append(ads.getTo_province()).append("\n");
        message.append("Load type: ").append(ads.getType_of_load()).append("\n");
        return message.toString();
    }
    private void sendSMS(String toPhoneNumber, String message) {
        Message.creator(
                new com.twilio.type.PhoneNumber(toPhoneNumber),
                new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                message
        ).create();
    }
}
