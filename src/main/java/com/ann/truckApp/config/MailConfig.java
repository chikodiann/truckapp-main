package com.ann.truckApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.util.Properties;

@Configuration

public class MailConfig {

    @Value("${server.mail.host}")
    private String mailHost;

    @Value("${server.mail.port}")
    private int mailPort;

    @Value("${server.mail.username}")
    private String mailUsername;

    @Value("${server.mail.password}")
    private String mailPassword;

    @Bean
    public JavaMailSender customJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("utf-8");
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties properties = mailSender.getJavaMailProperties();
//        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.connection-timeout", "100000");
//        properties.put("mail.smtp.timeout", "100000");
//        properties.put("mail.smtp.write timeout", "10000");
//        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}