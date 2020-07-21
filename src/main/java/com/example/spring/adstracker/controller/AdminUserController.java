package com.example.spring.adstracker.controller;

import com.example.spring.adstracker.data.User;
import com.example.spring.adstracker.data.UserRole;
import com.example.spring.adstracker.repository.UserRepo;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminUserController {
    
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String usersList(Model model, Pageable pageable) {
        
        Page<User> page = userRepo.findAll(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/admin");
        
        return "usersList";
    }
    
    @GetMapping("/userRole/{userId}")
    public String userRoleEditForm(@PathVariable long userId, Model model) {
        
        User user = userRepo.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("roles", UserRole.values());
        
        return "userRoleEdit";
    }
    
    @PostMapping
    public String saveUserRole(@RequestParam Map<String, String> form,
                           @RequestParam("userId") long userId) {
        
        User user = userRepo.findById(userId);
                
        Set<String> roles = Arrays.stream(UserRole.values())
                                  .map(UserRole::name)
                                  .collect(Collectors.toSet());
        
        user.getUserRoles().clear();
        
        for(String key: form.keySet()) {
            if(roles.contains(key)) {
                user.getUserRoles().add(UserRole.valueOf(key));
            }
        }
        
        userRepo.save(user);
        
        return "redirect:/admin";
    }
}