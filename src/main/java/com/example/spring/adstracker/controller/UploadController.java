package com.example.spring.adstracker.controller;

import com.example.spring.adstracker.data.User;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Controller
public class UploadController {
    
    @Value("${upload.path}")
    private String uploadPath;
    
    File uploadDir;
    
   @GetMapping("/upload")
    public String showUploadForm(
            @AuthenticationPrincipal User user,
            Model model) throws IOException {
        
        return "fileUpload";
    } 
    

    @PostMapping("/upload")
    public String uploadFile(
            @AuthenticationPrincipal User user,
            @RequestParam("file") MultipartFile file,
            Model model) {
                
        try {
            
            if (file != null 
             && !file.getOriginalFilename().isEmpty()
             && !uploadPath.equals("")) 
            {
                uploadDir = new File(uploadPath);                
                
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                
                String uuidFile = UUID.randomUUID().toString();
                
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                
                model.addAttribute("message", "The file you uploaded.");
                
                return "redirect:/files";        
            
            } else {
                
                model.addAttribute("message", "Error: The file is missing.");
            
                System.out.println("Error: The file is missing.");
                
                return "fileUpload";        
            }
                
        } catch (IOException iOException) {
            
            System.out.println("Error: Unable to upload this file (1). Please try later. " + iOException);
            model.addAttribute("message", "Error: Unable to upload this file. Please try later.");
            
            return "fileUpload";        
            
        } catch (IllegalStateException illegalStateException) {
            
            System.out.println("Error: Unable to upload this file (2). Please try later. " + illegalStateException);
            model.addAttribute("message", "Error: Unable to upload this file. Please try later.");
            
            return "fileUpload";        
        }
    }
    
    @GetMapping("/files")
    public String showFilesList(
            @AuthenticationPrincipal User user,
            Model model) throws IOException { // Map<String, Object> model
                
        try {
            
            uploadDir = new File(uploadPath);            
            
            if (uploadDir.exists()) {
                
                File[] files = uploadDir.listFiles();
                model.addAttribute("files", files);
                
                return "uploadedFiles";
                
            } else {
                model.addAttribute("message", "Error: Directory is not exists");
                return "fileUpload";
            }
            
        } catch (Exception e) {
            model.addAttribute("message", "Error: Unable to get a filelist");
            return "fileUpload";
        }
    } 
}