package com.ann.truckApp.services;

import com.ann.truckApp.config.RestTemplateConfig;
import com.ann.truckApp.domain.model.TransactionalEmail;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.dto.response.SenderModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final RestTemplateConfig restTemplateConfig;
    public CompletableFuture<?> sendOTPVerification(Users user, String subject, String emailContent ){


        return CompletableFuture.runAsync(()->{
            TransactionalEmail transactionalEmail = TransactionalEmail.builder()
                    .htmlContent(emailContent)
                    .sender(SenderModel.builder()
                            .name("MIGRO")
                            .email("support@migro.com")
                            .build())
                    .to(Set.of(SenderModel.builder()
                            .name(user.getFirstName())
                            .email(user.getEmail())
                            .build()))
                    .subject(subject)
                    .build();

            HttpEntity<TransactionalEmail> httpEntity = new HttpEntity<>(transactionalEmail);
            ResponseEntity<String> response;
            try{
                response = restTemplateConfig.restTemplate().exchange("/smtp/email", HttpMethod.POST,httpEntity, String.class);
                if(response.getStatusCode().is2xxSuccessful()){
                    log.info("Mail sent successfully to {}", user.getEmail());
                }else{
                    throw new RuntimeException("Error in sending mail");
                }
            }catch(Exception e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
    }
}
