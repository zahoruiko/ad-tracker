package com.example.spring.adstracker.controller;

import com.example.spring.adstracker.data.User;
import com.example.spring.adstracker.repository.UserRepo;
import com.example.spring.adstracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import static com.example.spring.adstracker.security.InputDataCleaner.removeHtmlTagsAndSpecialCharsFromString;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Oleksii Zahoruiko
 */

@Controller
public class RegistrationController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepo userRepo;
        
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) { 
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(
            @RequestParam("password2") String passwordConfirmation,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        
        if(bindingResult.hasErrors()) {           
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user", user);
            return "registration";
        
        } else {
                
            if(StringUtils.isEmpty(passwordConfirmation)) {
                model.addAttribute("password2Error", "Please enter password one more time.");
                return "registration";
            }
            
            if (!user.getPassword().equals(passwordConfirmation)) {
                model.addAttribute("passwordError", "Passwords are different!");
                model.addAttribute("user", user);
                return "registration";
            }

            if (userRepo.findByName(user.getName()) != null) {
                model.addAttribute("nameError", "Please enter another name");
                user.setName("");
                model.addAttribute("user", user);
                return "registration";
            }

            if (userRepo.findByEmail(user.getEmail()) != null) {
                model.addAttribute("emailError", "Please enter another E-mail");
                user.setEmail("");
                model.addAttribute("user", user);
                return "registration";
            }

            if (!user.isRulesAcceptance()) {
                model.addAttribute("rulesacceptanceError", "You didn't accept the service's rules");
                model.addAttribute("user", user);
                return "registration";
            }
            
            try {
                
                userService.addNewUser(user);

                model.addAttribute("message", "An Email was sent to you. Click the link to confirm that you can use this email address.");

                System.out.println("User is added");

            } catch (Exception e) {
                
                e.printStackTrace();

                model.addAttribute("message", "Error: User is not added. Please try later");

                System.out.println("Error: User is not added. Please try later");
                System.out.println("Exception: " + e);    
            }
            
            return "redirect:/login";
        }
    }
    
    @GetMapping("/activate/{code}")
    public String activateUserAccount(Model model, @PathVariable String code) {
        
        code = removeHtmlTagsAndSpecialCharsFromString(code);
        if(code == null || code.isEmpty()) {
            model.addAttribute("message", "The activation code is wrong.");
            return "login";
        }
        
        boolean isActivated = userService.activateUser(code);
        
        if(isActivated) {
            model.addAttribute("message", "The account is activated.");
        } else {
            model.addAttribute("message", "The activation code is wrong.");    
        }
        
        return "login";
    }
}