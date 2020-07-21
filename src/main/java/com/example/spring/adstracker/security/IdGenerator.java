package com.example.spring.adstracker.security;

import com.example.spring.adstracker.helper.rnd;

/**
 *
 * @author Oleksii Zahoruiko
 */
public class IdGenerator {

    public static String getRandomId(int randomIdLength) {
        
        String[] chars = new String[3]; 
        chars[0] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        chars[1] = "abcdefghijklmnopqrstuvwxyz";
        chars[2] = "0123456789";

        int charType, charIndex;
        
        StringBuilder result = new StringBuilder();          

        for(int i = 0; i < randomIdLength; i++) {
            charType = rnd.getRNDValue(0, chars.length-1);
            charIndex = rnd.getRNDValue(0, chars[charType].length()-1);
            result.append(chars[charType].charAt(charIndex));
        }
       
        return result.toString();
    }
}