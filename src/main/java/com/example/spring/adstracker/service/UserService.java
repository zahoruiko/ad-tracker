package com.example.spring.adstracker.service;

import com.example.spring.adstracker.data.User;
import com.example.spring.adstracker.data.UserRole;
import com.example.spring.adstracker.repository.UserRepo;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Service
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepo userRepo;
//    public UserService(UserRepo userRepo) {
//        this.userRepo = userRepo;
//    }
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MailSender mailSender;
        
    
    @Override
    public UserDetails loadUserByUsername(String userStringame) throws UsernameNotFoundException {        
        
        return userRepo.findByName(userStringame);
    }
    
    
    public boolean addNewUser(User user) {
        
        if(addUserInDb(user)) {
//            mailSender.sendActivationMail(user);
            System.out.println("User is added.");
            return true;
        
        } else {
            System.out.println("This user is not added.");
            return false;
        }
    }
    
    
    public boolean addUserInDb(User user) {

        Timestamp timeNow = new Timestamp(System.currentTimeMillis());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setCreated(timeNow);
        user.setUpdated(timeNow);
        user.setBlocked(false);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setUserRoles(Collections.singleton(UserRole.USER));
        
        try {
            
            userRepo.save(user);
            return true;
            
        } catch (Exception e) {
            System.out.println("Exception (addUserInDb): " + e);    
            return false;
        } 
    }
    

    public boolean updateUserDetails(User user, String name, String email, String password) {
        
        Timestamp timeNow = new Timestamp(System.currentTimeMillis());
        
        try {
                userRepo.updateUserDetails(user.getId(), 
                                                 name, 
                                            email, 
                                          passwordEncoder.encode(password),
                                         timeNow);
//                if(!user.getEmail().equals(email))
//                   mailSender.sendActivationMail(user);
                return true;
        } catch (Exception e) {
            System.out.println("Exception (updateUserDetails): " + e);    
            return false;
        } 
    }

    
    public boolean activateUser(String code) {
        
        User user = userRepo.findByActivationCode(code);
        
        if(user == null) {
            return false;
        }
        
        user.setActivationCode(null);
        
        userRepo.save(user);
        
        return true;
    }
}