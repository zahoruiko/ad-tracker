package com.example.spring.adstracker.repository;

import com.example.spring.adstracker.data.Campaign;
import com.example.spring.adstracker.data.User;
import java.sql.Timestamp;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Oleksii Zahoruiko
 */
public interface CampaignRepo extends JpaRepository<Campaign, Long> {
    
    Campaign findByCode(String code);
    
    Campaign findByUserAndCode(User user, String code);
    
    Page<Campaign> findByUserOrderByCodeDesc(User user, Pageable pageable);
    
    @Transactional
    @Modifying
    @Query("UPDATE Campaign c "
            + "SET c.destination = ?1, "
                + "c.destinationUrl = ?2, " 
                + "c.source = ?3, "
                + "c.medium = ?4, "
                + "c.keywords = ?5, "
                + "c.budget = ?6, "
                + "c.visitCost = ?7, "
                + "c.archived = ?8, "
                + "c.updated = ?9 "
          + "WHERE c.code = ?10 "
            + "AND c.user = ?11")
    int updateCampaignDetails(String destination, String destinationUrl, 
                              String source, String medium, String keywords, 
                              float budget, float visitCost,
                              boolean archived, Timestamp updated, String code,
                              User user);
    
    @Transactional
    @Modifying
    @Query("UPDATE Campaign c "
            + "SET c.archived = ?3 "
          + "WHERE c.user = ?1 "
            + "AND c.code = ?2")
    int updateCampaignStatus(Long userId, String campaigId, boolean archived);
    
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Campaign c "
               + "WHERE c.user = ?1 "
                 + "AND c.code = ?2")
    void deleteCampaignByUserAndCode(User user, String campaignCode);  
}