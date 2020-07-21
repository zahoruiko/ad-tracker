package com.example.spring.adstracker.repository;

import com.example.spring.adstracker.data.Visitor;
import com.example.spring.adstracker.data.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.sql.Timestamp;
import javax.transaction.Transactional;

/**
 *
 * @author Oleksii Zahoruiko
 */
public interface VisitorRepo  extends JpaRepository<Visitor, Long> {

    Page<Visitor> findAll(Pageable pageable);
    
    Page<Visitor> findByCampaignCodeOrderByIdDesc(String campaignCode, Pageable pageable);
    
    @Query("SELECT v.id, v.campaignCode, v.userIp, v.userAgent, " 
                + "v.referrerUrl, v.timestamp, v.archived, v.result "
           + "FROM Visitor v "
          + "WHERE v.campaignCode = "
        + "(SELECT c.code "
           + "FROM Campaign c "
          + "WHERE c.user=?1"
       + "ORDER BY c.id) "
       + "ORDER BY v.timestamp DESC")        
    Page<Visitor> findByUser(User user, Pageable pageable);
    
    
    @Query("SELECT v.id, v.campaignCode, v.userIp, v.userAgent, "
                + "v.referrerUrl, v.timestamp, v.archived, v.result "
           + "FROM Visitor v "
     + "INNER JOIN Campaign c "
             + "ON v.campaignCode = c.code "
          + "WHERE c.user = ?1 "
            + "AND c.code = ?2 "
       + "ORDER BY v.timestamp DESC")
    Page<Visitor> findByUserAndCampaignCode(User user, String campaignCode, Pageable pageable);
    
    
    // The combination of the campaign code and the timestamp is unique und known only to the user, 
    // so this data is sufficient to ensure that someone else cannot enter incorrect data
    @Transactional
    @Modifying
    @Query("UPDATE Visitor v "
            + "SET v.result='true' "
          + "WHERE v.campaignCode=?1 "
            + "AND v.timestamp=?2")
    int setVisitPositiveResult(String campaignCode, Timestamp visitTimestamp);
    
    
    @Transactional
    @Modifying
    @Query("DELETE FROM Visitor v "
               + "WHERE v.campaignCode = ?1")
    void deleteVisitorsByCapaignCode(String code);    
}