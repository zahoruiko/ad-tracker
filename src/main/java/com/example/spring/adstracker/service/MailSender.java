package com.example.spring.adstracker.service;

import com.example.spring.adstracker.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Service
public class MailSender {
    
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${user.account.activation.link}")
    private String activationLink;
    
    @Value("${spring.mail.username}")
    private String fromAddresse;
    
    public void sendMail(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        
        mailMessage.setFrom(fromAddresse);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        
        javaMailSender.send(mailMessage);   
    }
    
    public boolean sendActivationMail(User user) {
        
        if(!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s! \n "
                    + "Welcome to AdsTracker! \n "
                    + "Please, visit this link: %s/%s",
                    user.getName(), activationLink, user.getActivationCode());

            sendMail(user.getEmail(), "Activation code", message);
            
            return true;
        } else {
            return false;
        }
    }
}