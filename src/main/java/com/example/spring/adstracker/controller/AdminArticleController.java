package com.example.spring.adstracker.controller;

import com.example.spring.adstracker.data.Article;
import com.example.spring.adstracker.data.User;
import com.example.spring.adstracker.repository.ArticleRepo;
import java.sql.Timestamp;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminArticleController {

    @Autowired
    private ArticleRepo articleRepo;

    @GetMapping("/articlesList")
    public String showArticlesList(Model model, Pageable pageable) {
        Page<Article> page = articleRepo.findAll(pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/admin/articlesList");
        
        return "articlesList";
    }
    
    @GetMapping("/addArticle")
    public String addArticleForm(@AuthenticationPrincipal User user, Model model) {
        Article article = new Article();
        model.addAttribute("article", article);
        model.addAttribute("actionUrl", "/admin/addArticle");
        return "articleForm";
    }

    @PostMapping("/addArticle")
    public String addArticle(@AuthenticationPrincipal User user, 
                            @Valid Article article, 
                            BindingResult bindingResult,
                            Model model) {
        
        if(bindingResult.hasErrors()) {
            
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            
            model.mergeAttributes(errorsMap);
            model.addAttribute("article", article);
            
            return "articleForm";

        } else {
            
            if(article.getTheme().equals("About") || 
               article.getTheme().equals("Contacts") || 
               article.getTheme().equals("F.A.Q.") ||
               article.getTheme().equals("Terms"))
            {
                Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());

                article.setCreated(nowTimestamp);
                article.setUser(user);

                try {

                    articleRepo.save(article);

                    model.addAttribute("message", "Article is added");

                    System.out.println("Article is added");

                } catch (Exception e) {

                    e.printStackTrace();

                    model.addAttribute("message", "Error: Article is not added. Please try later");

                    System.out.println("Error: Article is not added. Please try later");
                    System.out.println("Exception: " + e);    
                }

                return "redirect:/admin/articlesList";            
   
            } else {
                article.setTheme("");
                model.addAttribute("article", article);
                model.addAttribute("themeError", "Please select a theme of the article");                
                return "articleForm";    
            }
        }
    }

    @GetMapping("/editArticle/{articleId}")
    public String editArticle(@PathVariable Long articleId, Model model) {
        
        Article article = articleRepo.getByArticleId(articleId);
        
        if(article != null) {
            model.addAttribute("article", article);
            model.addAttribute("actionUrl", "/admin/updateArticle/" + articleId);
            return "articleForm";            
        } else {
            return "redirect:/admin/articlesList";
        }            
    }
    
    @PostMapping("/updateArticle/{articleId}")
    public String updateArticle(@AuthenticationPrincipal User user, 
                                @PathVariable long articleId, 
                                @Valid Article article,                     
                                BindingResult bindingResult,                    
                                Model model) {
        
        if(bindingResult.hasErrors()) {
            
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            
            model.mergeAttributes(errorsMap);
            model.addAttribute("article", article);
            
            return "articleForm";

        } else {
          
            if(article.getTheme().equals("About") || 
               article.getTheme().equals("Contacts") || 
               article.getTheme().equals("F.A.Q.") ||
               article.getTheme().equals("Terms"))
            {
                Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());

                try {
                    
                    if(articleRepo.updateArticle(user, 
                                                articleId, 
                                                article.getTitle(), 
                                                article.getBody(), 
                                                article.getTheme(), 
                                                nowTimestamp) == 1) {

                        model.addAttribute("message", "Article is updated");

                        System.out.println("Article is added");   
                        
                        return "redirect:/admin/articlesList";

                    } else {
                        
                        model.addAttribute("message", "Article is not updated");
                        
                        System.out.println("Article is not updated. Please try later");                        
                        
                        return "redirect:/admin/articlesList";            
                    }
                } catch (Exception e) {

                    e.printStackTrace();

                    model.addAttribute("message", "Error: Article is not added. Please try later");

                    System.out.println("Error: Article is not added. Please try later");
                    System.out.println("Exception: " + e);    
                }

                return "redirect:/admin/articlesList";            
   
            } else {
                article.setTheme("");
                model.addAttribute("article", article);
                model.addAttribute("themeError", "Please select a theme of the article");                
                return "articleForm";    
            }              
        } 
    }
    
    @GetMapping("/deleteArticle/{articleId}")
    public String deleteArticle(@AuthenticationPrincipal User user, 
                                @PathVariable long articleId, 
                                Model model) {
        
        Article article = articleRepo.getByArticleId(articleId);
        
        if(article!=null) {
            articleRepo.delete(article);    
        }
        
        return "redirect:/admin/articlesList";
    }
    
    @GetMapping("/articleDeleteConfirmation/{articleId}")
    public String campaignDeleteConfirmation(@PathVariable(name = "articleId", required = true) long articleId, 
                                             Model model) {
        if(articleId > 0) {
            model.addAttribute("deleteArticleUrl", "/admin/deleteArticle/" + articleId);
            return "articleDeleteConfirmation";
        } else {
           return "redirect:/admin/articlesList"; 
        }
    }
    
    @GetMapping("/articleDetails/{articleId}")
    public String showInfoPage(@PathVariable long articleId, Model model) {
 
        Article article = articleRepo.getByArticleId(articleId);
        
        if(article!=null) {
            model.addAttribute("article", article);
            return "articleDetails";
        } else {
            return "redirect:/admin/articlesList";
        }
    }
}