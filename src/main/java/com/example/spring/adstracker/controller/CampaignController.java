package com.example.spring.adstracker.controller;

import com.example.spring.adstracker.data.Campaign;
import com.example.spring.adstracker.data.User;
import com.example.spring.adstracker.repository.CampaignRepo;
import com.example.spring.adstracker.repository.VisitorRepo;
import static com.example.spring.adstracker.security.IdGenerator.getRandomId;
import static com.example.spring.adstracker.security.InputDataCleaner.cleanCampaignCode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

/**
 *
 * @author Oleksii Zahoruiko
 */

@Controller
@PreAuthorize("hasAuthority('USER')")
public class CampaignController {
    
    @Autowired
    private CampaignRepo campaignRepo;

    @Autowired
    private VisitorRepo visitorRepo;
    
    @Value("${site.url}")
    private String siteUrl;

    @Value("${visitor.registration.path}")
    private String clickRegistrationPath;
    
    
    @GetMapping("/campaignsList")
    public String campaignsList(@AuthenticationPrincipal User user, 
                                Model model, 
                                Pageable pageable) {
        
        Page<Campaign> page = campaignRepo.findByUserOrderByCodeDesc(user, pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/campaignsList");
        
        return "campaignsList";
    }
    
    
    @GetMapping("/campaignDetails/{code}")
    public String showCampaignDetails(@AuthenticationPrincipal User user, 
            @PathVariable(name="code", required=true) String campaignCode, Model model) {
        
        Campaign campaignDetails = campaignRepo.findByUserAndCode(user, campaignCode);
        
        model.addAttribute("campaign", campaignDetails);
        model.addAttribute("clickRegistrationPath", siteUrl + clickRegistrationPath);
        
	return "campaignDetails";
    }
    
    
    @GetMapping("/addNewCampaign")
    public String showAddNewCampaignForm(@AuthenticationPrincipal User user, Model model) {
        
        model.addAttribute("actionUrl", "/addNewCampaign");        
        Campaign newCampaign = new Campaign();
        model.addAttribute("campaign", newCampaign);
        
        return "campaignDetailsForm";
    }
    
    
    @PostMapping("/addNewCampaign")
    public String saveNewCampaign(@AuthenticationPrincipal User user, 
                                  @Valid Campaign campaign,
                                  BindingResult bindingResult,
                                  Model model) {
        
        if(bindingResult.hasErrors()) {
            
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            
            model.mergeAttributes(errorsMap);
            model.addAttribute("campaign", campaign);
            
            return "campaignDetailsForm";

        } else {
            
            Date date= new Date();
            long time = date.getTime();
            String code = Long.toString(time) + getRandomId(5);

            Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());

            campaign.setCode(code);
            campaign.setUser(user);
            campaign.setArchived(false);
            campaign.setCreated(nowTimestamp);
            campaign.setUpdated(nowTimestamp);
            
            try {

                campaignRepo.save(campaign);

                model.addAttribute("message", "Campaign is added");

                System.out.println("Campaign is added");

            } catch (Exception e) {
                
                e.printStackTrace();

                model.addAttribute("message", "Error: Campaign is not added. Please try later");

                System.out.println("Error: Campaign is not added. Please try later");
                System.out.println("Exception: " + e);    
            }
            
            return "redirect:/campaignsList";
        }   
    }

    
    @GetMapping("/editCampaign/{code}")
    public String showUpdateCampaignForm(@AuthenticationPrincipal User user, 
            @PathVariable(name = "code", required = true) String campaignCode, Model model) {
        
        Campaign campaignDetails = campaignRepo.findByUserAndCode(user, campaignCode);
        model.addAttribute("campaign", campaignDetails);
        model.addAttribute("actionUrl", "/updateCampaign/" + campaignCode);

        return "campaignDetailsForm";
    }
        
                                  
    @Transactional
    @PostMapping("/updateCampaign/{code}")
    public String updateCampaign(@AuthenticationPrincipal User user, 
                                 @Valid Campaign campaign,
                                 BindingResult bindingResult,
                                 Model model) {

        if(bindingResult.hasErrors()) {
            
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            
            model.mergeAttributes(errorsMap);
            model.addAttribute("campaign", campaign);
            
            return "campaignDetailsForm";

        } else { 
        
            try {
                
                int result = campaignRepo.updateCampaignDetails(campaign.getDestination(),
                                                                campaign.getDestinationUrl(), 
                                                                campaign.getSource(),
                                                                campaign.getMedium(),
                                                                campaign.getKeywords(),
                                                                campaign.getBudget(),
                                                                campaign.getVisitCost(),
                                                                false,
                                                                new Timestamp(System.currentTimeMillis()),
                                                                campaign.getCode(),
                                                                user);

                if (result == 1) {
                    model.addAttribute("message", "Campaign is updated");
                    System.out.println("Campaign is updated");
                    return "redirect:/campaignsList";
                } else {
                    model.addAttribute("message", "Error: Campaign is not updated. Please try later");
                    return "redirect:/campaignsList";
                }        
                    
            } catch (Exception e) {
                
                e.printStackTrace();
                
                model.addAttribute("message", "Error: Campaign is not updated. Please try later");
                System.out.println("Error: Campaign is not updated. Please try later");
                System.out.println("Error: " + e);
                return "redirect:/campaignsList";
            }                    
        }
    }                                  
    
    
    @Transactional
    @GetMapping("/deleteCampaign/{code}")
    public String deleteCampaign(@AuthenticationPrincipal User user, 
            @PathVariable(name = "code", required = true) String campaignCode, Model model) {
        
        campaignCode = cleanCampaignCode(campaignCode);
        
        if(campaignCode == null || campaignCode.isEmpty())
           return "redirect:/campaignsList";            
        
        Campaign campaign = campaignRepo.findByUserAndCode(user, campaignCode);

        if(campaign != null) {
           visitorRepo.deleteVisitorsByCapaignCode(campaignCode); 
           campaignRepo.deleteCampaignByUserAndCode(user, campaignCode);
        }

        model.addAttribute("message", "Campaign deleted.");
        
        return "redirect:/campaignsList";
    }
    
    
    @GetMapping("/campaignDeleteConfirmation/{code}")
    public String campaignDeleteConfirmation(@AuthenticationPrincipal User user, 
                            @PathVariable(name = "code", required = true) String campaignCode, 
                            Model model) {
        
        campaignCode = cleanCampaignCode(campaignCode);
        
        if(campaignCode == null || campaignCode.isEmpty())
           return "redirect:/campaignsList";
        
        model.addAttribute("deleteCampaignUrl", "/deleteCampaign/" + campaignCode);
        
        return "campaignDeleteConfirmation";
    }
}