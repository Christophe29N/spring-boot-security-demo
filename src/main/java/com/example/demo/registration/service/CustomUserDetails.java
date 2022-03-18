package com.example.demo.registration.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.registration.model.Role;
import com.example.demo.registration.model.User;

public class CustomUserDetails implements UserDetails {

    private User user;

    Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
    
    public CustomUserDetails(User user) {
        super();
        this.user = user;
        
        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }        
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        boolean credentialsNonExpired = user.getLastPasswordUpdate().isAfter(LocalDateTime.now().minusMonths(1));
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
    
    public String getFullName() {
        return user.getFirstName()+ " " + user.getLastName();
    }
}
