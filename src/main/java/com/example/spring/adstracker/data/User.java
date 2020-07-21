package com.example.spring.adstracker.data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Oleksii Zahoruiko
 */
@Entity
@Table(name="users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(nullable=false, unique=true) 
    private long id; // Integer
    
    @Length(min = 1, max = 255, message = "The name must not be empty or longer than 255 characters")
    @Column(nullable=false, unique=true)
    private String name;
    
    @Email(message = "E-mail is not valid")
    @Length(min = 5, max = 255, message = "Enter the correct E-mail address")
    @Column(nullable=false, unique=true)
    private String email;
    
    @Length(min = 3, max = 255, message = "The password must consist of at least 8 and no more than 255 characters")
    @Column(nullable=false, unique=false)
    private String password;
    
    @Column(nullable=false, unique=false)
    private Timestamp created;
    
    @Column(nullable=false, unique=false)
    private Timestamp updated;
    
    @Column(nullable=false, unique=false)
    private boolean blocked = false;

    @Column(nullable=false, unique=false)
    private boolean active = true;
    
    @Column(nullable=false, unique=false)
    private String activationCode;
        
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> userRoles;
    
    @Column(nullable=false, unique=false)
    private boolean rulesAcceptance = false;
    
    
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRole) {
        this.userRoles = userRole;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserRoles();
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
    
    public boolean isAdmin() {
        return userRoles.contains(UserRole.ADMIN);
    }

    public boolean isUser() {
        return userRoles.contains(UserRole.USER);
    }
    
        public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isRulesAcceptance() {
        return rulesAcceptance;
    }

    public void setRulesAcceptance(boolean rulesAcceptance) {
        this.rulesAcceptance = rulesAcceptance;
    }    
}