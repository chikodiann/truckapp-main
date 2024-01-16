package com.ann.truckApp.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


public class EmailUtils {

    public static String sendHtmlEmailTemplate(TemplateEngine templateEngine, String typesubject, String username, String otp,String value2){
        Context context = new Context();
        String file ="otp.html";

        context.setVariable("user_name", username);
        context.setVariable("subject", typesubject);
            context.setVariable("company_name", value2);
            context.setVariable("otp", otp);

        return templateEngine.process(file, context);
    }
}