package com.ann.truckApp.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


public class EmailUtils {

    public static String sendHtmlEmailTemplate(TemplateEngine templateEngine, String typesubject, String email, String value,String value2){
        Context context = new Context();
        String file ="";

            file ="otp";
            context.setVariable("user_name", email);
            context.setVariable("company_name", typesubject);
            context.setVariable("otp", value);

        return templateEngine.process(file, context);
    }
}