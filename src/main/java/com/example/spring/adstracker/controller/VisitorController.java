package com.example.spring.adstracker.controller;

import com.example.spring.adstracker.data.Campaign;
import static com.example.spring.adstracker.security.InputDataCleaner.cleanCampaignCode;
import com.example.spring.adstracker.data.User;
import com.example.spring.adstracker.data.Visitor;
import com.example.spring.adstracker.repository.CampaignRepo;
import com.example.spring.adstracker.repository.VisitorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Controller
public class VisitorController {

    @Autowired
    private CampaignRepo campaignRepo;

    @Autowired
    private VisitorRepo visitorRepo;    
    
    @GetMapping("/visitor_registration/{campaignCode}")
    public ModelAndView registerVisitor(@PathVariable String campaignCode, 
                                        Model model, 
                                        HttpServletRequest request) {
        
        System.out.println("CampaignCode = " + campaignCode);
        
        if(campaignCode==null || campaignCode.isEmpty()) {
            return new ModelAndView("/");
        } else {
            campaignCode = cleanCampaignCode(campaignCode);
            
            if(campaignCode==null || campaignCode.isEmpty()) {
                return new ModelAndView("/");
            } else {
                
                Campaign campaign = campaignRepo.findByCode(campaignCode);        
                
                if(campaign!=null) {

                    String destinationUrl = campaign.getDestinationUrl();

                    if(destinationUrl!=null && !destinationUrl.isEmpty()) {

                        System.out.println("DestinationUrl = " + destinationUrl);

                        Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());

                        String ipAddress = request.getRemoteAddr();
                        if(ipAddress == null)
                            ipAddress = "";

                        String userAgent = request.getHeader("User-Agent");
                        if(userAgent == null)
                            userAgent = "";

                        String referrer = request.getHeader("referer");
                        if(referrer == null)
                            referrer = "";

                        try {

                            Visitor visitor = new Visitor();
                            visitor.setCampaignCode(campaignCode);
                            visitor.setUserIp(ipAddress);
                            visitor.setUserAgent(userAgent);
                            visitor.setReferrerUrl(referrer);
                            visitor.setTimestamp(nowTimestamp);

                            visitorRepo.save(visitor);    

                        } catch (Exception e) {
                           e.printStackTrace();
                        }

                        // Specify the campaign code and the click timestamp
                        // This data is used to set visit results
                        model.addAttribute("campaign_id", campaignCode);
                        model.addAttribute("visit_ts", nowTimestamp);
    
                        return new ModelAndView("redirect:" + destinationUrl);
                    } else {
                        return new ModelAndView("redirect:/");
                    }   
                
                } else {
                    return new ModelAndView("redirect:/");
                }
            }
        }
    }

    
    @GetMapping("/visitorsList")
    public String visitorsList(@AuthenticationPrincipal User user, 
                               Model model, 
                               Pageable pageable) {
        
        Page<Visitor> page = visitorRepo.findByUser(user, pageable);
        model.addAttribute("page", page);
        model.addAttribute("url", "/visitorsList");
        
        return "visitorsList";
    }
    
    
    @GetMapping("/campaignVisitors/{campaignCode}")
    public String showCampaignVisitorsList(@AuthenticationPrincipal User user, 
                                           @PathVariable String campaignCode, 
                                           Model model,
                                           Pageable pageable) {

        Campaign campaign = campaignRepo.findByCode(campaignCode);
        
        if(campaign != null) {
            
            if(campaign.getUser().getId() == user.getId()) {
                
                Page<Visitor> page = visitorRepo.findByCampaignCodeOrderByIdDesc(campaignCode, pageable);

                model.addAttribute("page", page);
                model.addAttribute("url", "/campaignVisitors/" + campaignCode);                

                return "visitorsList";
            }
        }
        
        return "redirect:/campaignsList";
    } 
    
    
    @GetMapping("/setVisitPositiveResult")
    public void setVisitPositiveResult(@RequestParam(name="campaignCode", required=true) String campaignCode, 
                                       @RequestParam(name="visitTimestamp", required=true) Timestamp visitTimestamp, 
                                       Model model) {
        
        // The combination of the unique campaign code and timestamp is known only to the user, 
        // so this data is sufficient to ensure that someone else cannot enter incorrect data
        if(visitorRepo.setVisitPositiveResult(campaignCode, visitTimestamp) > 0)
            System.out.println("The result of the visit was changed");
        else
            System.out.println("The result of the visit was not changed");
    }
}