package com.example.demoportflio_tpdevops_github_action_ci.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class UserPrincipal implements UserDetails {

    private final User users;
    public UserPrincipal(User user) {
        this.users = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  Collections.singleton(new SimpleGrantedAuthority("USER"));

    }





    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getSlug();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
    public User getUser() {
        return users; // si tu veux récupérer directement tout l'objet
    }

}
