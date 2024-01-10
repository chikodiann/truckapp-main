package com.ann.truckApp.events.listenersEvents;

import com.ann.truckApp.config.MailConfig;
import com.ann.truckApp.domain.model.Users;
import com.ann.truckApp.events.listenersRequest.EventRegister;
import com.ann.truckApp.utils.EmailUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import java.io.UnsupportedEncodingException;

@Component
@Slf4j
public class EventRegisterListener implements ApplicationListener<EventRegister> {
    @Autowired
    private MailConfig mailConfig;

    @Autowired
    private TemplateEngine templateEngine;
    @Override
    public void onApplicationEvent(EventRegister event) {
        String otp = event.getOtp();
        Users users = event.getUsers();
        log.info("OTP {}", otp);
        log.info("Users {}", users);
        try {
            sendEmail(users, otp);

        }
        catch(Exception e){
            log.info(e.getMessage());
        }
    }

    private void sendEmail(Users user,String otp) throws MessagingException, UnsupportedEncodingException {
        String subject = "SIGNUP";
        String companyName = "APP_NAME";
        MimeMessage mimeMessage = mailConfig.customJavaMailSender().createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("test@gmail.com", companyName);
        messageHelper.setSubject(subject);
        messageHelper.setTo(user.getEmail());
        String mailContent = EmailUtils.sendHtmlEmailTemplate(templateEngine,subject,user.getEmail(),otp,"APP_NAME");
        messageHelper.setText(mailContent, true);
        mailConfig.customJavaMailSender().send(mimeMessage);
        log.info("Email sent successfully to: " + user.getEmail());
    }
}
