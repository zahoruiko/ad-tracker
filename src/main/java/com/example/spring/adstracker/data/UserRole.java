package com.example.spring.adstracker.data;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Oleksii Zahoruiko
 */
public enum UserRole implements GrantedAuthority {

    USER, ADMIN; 

    @Override
    public String getAuthority() {
        return name();
    }
}