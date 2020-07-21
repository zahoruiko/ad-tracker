package com.example.spring.adstracker.security;

/**
 *
 * @author Oleksii Zahoruiko
 */
public class PasswordCheck {
    
    public static boolean passwordValidation(String userPassword) {
      
      String goodPasswordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
      
      return userPassword.matches(goodPasswordPattern);
   }
}
