package com.ann.truckApp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhatsappMessageRequest {
    private String messagingProduct;
    private String to;
    private String type;
    private Template template;
    public static class Template {
        private String name;
        private Language language;
        public String getName(){
            return name;
        }
        public Language getLanguage(){
            return language;
        }
        public void setLanguage(Language language){
            this.language=language;
        }
        public void setName(String name){
            this.name=name;
        }


    }

    public static class Language {
        private String code;
        public String getCode(){
            return code;
        }
        public void setCode(String code){
            this.code=code;
        }

    }

}
