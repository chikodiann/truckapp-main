package com.ann.truckApp.events.listenersEvents;

import com.ann.truckApp.config.MailConfig;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.events.listenersRequest.EventRegister;
import com.ann.truckApp.services.EmailService;
import com.ann.truckApp.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventRegisterListener implements ApplicationListener<EventRegister> {

    private final MailConfig mailConfig;
    private final EmailService emailService;

    @Autowired
    private TemplateEngine templateEngine;
    @Override
    public void onApplicationEvent(EventRegister event) {
        String otp = event.getOtp();
        Users users = event.getUsers();
        log.info("OTP {}", otp);
        log.info("Users {}", users);
        String subject = "Migro - Email Confirmation OTP";
        String mailContent = EmailUtils.sendHtmlEmailTemplate(templateEngine,subject,users.getFirstName(),otp,"MIGRO");
        CompletableFuture<?> future = emailService.sendOTPVerification(users,subject, mailContent);
        future.join();

    }

//    private void sendEmail(Users user,String otp) throws MessagingException, UnsupportedEncodingException {
//        String subject = "Migro - Email Confirmation OTP";
//        String companyName = "MIGRO";
//        MimeMessage mimeMessage = mailConfig.customJavaMailSender().createMimeMessage();
//        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//        messageHelper.setFrom("test@gmail.com", companyName);
//        messageHelper.setSubject(subject);
//        messageHelper.setTo(user.getEmail());
//        String mailContent = EmailUtils.sendHtmlEmailTemplate(templateEngine,subject,user.getFirstName(),otp,"MIGRO");
//        messageHelper.setText(mailContent, true);
//        mailConfig.customJavaMailSender().send(mimeMessage);
//        log.info("Email sent successfully to: " + user.getEmail());
//    }
}
