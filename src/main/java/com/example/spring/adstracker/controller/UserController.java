package com.example.spring.adstracker.controller;

import com.example.spring.adstracker.data.User;
import com.example.spring.adstracker.repository.UserRepo;
import com.example.spring.adstracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Oleksii Zahoruiko
 */

@Controller
@PreAuthorize("hasAuthority('USER')")
public class UserController {
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/userDetails")
    public String showUserDetails(@AuthenticationPrincipal User user, Model model) {
        
        model.addAttribute("user", user);
        
        return "userDetails";
    }

    @GetMapping("/updateUserDetails")
    public String editUserDetails(@AuthenticationPrincipal User user, Model model) {
        
        model.addAttribute("user", user);
        
        return "updateUserDetails";
    }
    
    @PostMapping("/updateUserDetails")
    public String updateUserDetailsData(@AuthenticationPrincipal User user, 
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("password2") String passwordConfirmation,
            @Valid User newUserDetails,
            BindingResult bindingResult,
            Model model) {
        
        if(bindingResult.hasErrors()) {           
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user", newUserDetails);
            return "updateUserDetails";
        
        } else {

            if(StringUtils.isEmpty(currentPassword)) {
                model.addAttribute("currentpasswordError", "Please enter the current password.");
                return "updateUserDetails";
            }
            
            if(StringUtils.isEmpty(passwordConfirmation)) {
                model.addAttribute("password2Error", "Please enter the password one more time.");
                return "updateUserDetails";
            }
            
            if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
                model.addAttribute("currentpasswordError", "The current password is wrong!");
                model.addAttribute("user", user);
                return "updateUserDetails";
            }
            
            if (!newUserDetails.getPassword().equals(passwordConfirmation)) {
                model.addAttribute("passwordError", "The new password and the confirmation are different!");
                model.addAttribute("user", user);
                return "updateUserDetails";
            }
            
            User existUser = userRepo.findByName(newUserDetails.getName());
            
            if (existUser != null && existUser.getId() != user.getId()) {
                model.addAttribute("nameError", "Please enter another name");
                newUserDetails.setName("");
                model.addAttribute("user", newUserDetails);
                return "updateUserDetails";
            }

            existUser = userRepo.findByEmail(newUserDetails.getEmail());
            
            if (existUser != null && existUser.getId() != user.getId()) {
                model.addAttribute("emailError", "Please enter another E-mail");
                newUserDetails.setEmail("");
                model.addAttribute("user", newUserDetails);
                return "updateUserDetails";
            }
            
            if(userService.updateUserDetails(user, 
                                        newUserDetails.getName(), 
                                       newUserDetails.getEmail(),
                                     newUserDetails.getPassword())) {
                
                model.addAttribute("message", "Data is changed");
                System.out.println("User data is changed");
                
            } else {

                model.addAttribute("message", "Data is not changed. Please try later");
                System.out.println("Error: User data is not changed");
            }

            return "redirect:/userDetails";
        }
    }    
}