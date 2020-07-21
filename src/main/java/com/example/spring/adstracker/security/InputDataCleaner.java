package com.example.spring.adstracker.security;

import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author Oleksii Zahoruiko
 */
 public class InputDataCleaner {
 
    public static String removeHtmlTagsAndSpecialCharsFromString(String inputString) {
        // Tags are forbidden
        inputString.replaceAll("<.*?>" , "");
        inputString.replaceAll("&.*?;" , "");        
//        inputString.replaceAll("[^a-zA-Zа-яА-Я0-9_.,:;?!-+ ]", ""); 
         
        return inputString;
    }

    public static String cleanStringInt(String value) {
        return value.replaceAll("[^0-9]", "");
    }

    public static String cleanStringFloat(String value) {
        return value.replaceAll("[^0-9.]", "");
    }
    
    public static String cleanName(String name) {
        return name.replaceAll("[^a-zA-Zа-яА-Я0-9]", ""); 
    }
    
    public static String cleanCampaignCode(String campaignCode) {
        return campaignCode.replaceAll("[^a-zA-Zа-яА-Я0-9]", ""); 
    }
    
    public static boolean isValidEmail(String email) {
       EmailValidator validator = EmailValidator.getInstance();
       return validator.isValid(email);
   }
}