package com.example.spring.adstracker.repository;

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
public interface UserRepo extends JpaRepository<User, Long> {
    
    Page<User> findAll(Pageable pageable);
    
    User findByName(String name);
    
    User findByEmail(String email);
    
    User findByNameOrEmail(String name, String email);
    
    User findById(long id);

    User findByActivationCode(String code);
    
    @Transactional
    @Modifying
    @Query("UPDATE User u "
            + "SET u.name = ?2, u.email = ?3, u.password = ?4, u.updated = ?5 " 
          + "WHERE u.id = ?1 ")
    int updateUserDetails(long userId, String name, String eMail, 
                          String password, Timestamp timestamp);
}