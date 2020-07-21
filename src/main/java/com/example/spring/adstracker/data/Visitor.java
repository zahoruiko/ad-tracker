package com.example.spring.adstracker.data;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Entity
@Table(name="visitors")
public class Visitor {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, unique=true, columnDefinition = "INT(11) UNSIGNED")
    private long id;

    @Column(nullable=false, unique=false)
    private String campaignCode;
    
    @Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
    @Column(nullable=false, unique=false)
    private String userIp;
    
    @Column(nullable=false, unique=false)
    private String userAgent;
    
    @Column(nullable=false, unique=false)
    private String referrerUrl;
    
    @Column(nullable=false, unique=false)
    private Timestamp timestamp;

    @Column(nullable=false, unique=false)
    private boolean archived = false;
    
    @Column(nullable=false, unique=false)
    private boolean result = false;


    public Visitor() {
    }

    public long getId() {
        return id;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferrerUrl() {
        return referrerUrl;
    }

    public void setReferrerUrl(String referrerUrl) {
        this.referrerUrl = referrerUrl;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getCampaignCode() {
        return campaignCode;
    }

    public void setCampaignCode(String campaignCode) {
        this.campaignCode = campaignCode;
    }
    
    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}