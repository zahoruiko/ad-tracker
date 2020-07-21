package com.example.spring.adstracker.controller;

import com.example.spring.adstracker.data.Article;
import com.example.spring.adstracker.repository.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Oleksii Zahoruiko
 */

@Controller
public class InfoPagesController {

    @Autowired
    private ArticleRepo articleRepo;    
    
    @GetMapping("/info/{theme}")
    public String showInfoPage(@PathVariable String theme, Model model) {
 
        Article article = articleRepo.findFirstByThemeOrderByCreatedDesc(theme);
        
        if(article!=null) {
            model.addAttribute("article", article);
            return "infoPage";
        } else {
            return "redirect:/";
        }
    }
}