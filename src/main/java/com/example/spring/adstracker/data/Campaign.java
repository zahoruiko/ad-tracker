package com.example.spring.adstracker.data;

import com.example.spring.adstracker.security.InputDataCleaner;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Entity
@Table(name="campaigns")
public class Campaign {
        
    @Id
    @Column(nullable=false, unique=true)
    private String code;
    
    // The destination
    @Length(min = 1, max = 255, message = "The text must be not empty and no longer than 255 chars")
    @Column(nullable=false, unique=false)
    private String destination;
    
    // The full destination URL
    @Length(min = 3, max = 1024, message = "The text must be not empty and no longer than 1024 chars")
    @Column(nullable=false, unique=false)
    private String destinationUrl;
    
    // The source of visitors: Google, AdvNet, newsletter and etc.
    @Length(min = 1, max = 255, message = "The text must be not empty and no longer than 255 chars")
    @Column(nullable=false, unique=false)
    private String source;

    // Traffic type: serp, cpc, cpa, banner, email
    @Length(min = 1, max = 255, message = "The text must be not empty and no longer than 255 chars")
    @Column(nullable=false, unique=false)
    private String medium;
    
    @Length(max = 255, message = "Text is longer than 255 chars")
    @Column(nullable=false, unique=false)
    private String keywords;
    
    @Min(value = 0, message = "The budget must be positive")
    @Max(value = 1000000, message = "The maximum budget is 1000000")
    @Column(nullable=false, unique=false)
    private float budget;
    
    @Min(value = 0, message = "The cost must be positive")
    @Max(value = 100, message = "The cost of a visit cannot be more than 100")
    @Column(nullable=false, unique=false)
    private float visitCost=0.00f;
    
    @Column(nullable=false, unique=false) 
    private boolean archived = false;
    
    @Column(nullable=false, unique=false)
    private Timestamp created;

    @Column(nullable=false, unique=false)
    private Timestamp updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;
    
    
    public Campaign() {
        this.budget = 0.00f;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        String clearedData = InputDataCleaner.removeHtmlTagsAndSpecialCharsFromString(destination);
        if (!clearedData.equals(destination))
            return;
        
        this.destination = destination;
    }
    
    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        String clearedData = InputDataCleaner.removeHtmlTagsAndSpecialCharsFromString(destinationUrl);
        if (!clearedData.equals(destinationUrl))
            return;
        
        this.destinationUrl = destinationUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        String clearedData = InputDataCleaner.removeHtmlTagsAndSpecialCharsFromString(source);       
        if (!clearedData.equals(source))
            return;
        
        this.source = source;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        String clearedData = InputDataCleaner.removeHtmlTagsAndSpecialCharsFromString(medium);        
        if (!clearedData.equals(medium))
            return;

        this.medium = medium;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        String clearedData = InputDataCleaner.removeHtmlTagsAndSpecialCharsFromString(keywords);
        if (!clearedData.equals(keywords))
            return;
        
        this.keywords = keywords;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getVisitCost() {
        return visitCost;
    }

    public void setVisitCost(float visitCost) {
        this.visitCost = visitCost;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
    
    public String getUserName() {
        return user != null ? user.getUsername() : "<no information>";
    }
}