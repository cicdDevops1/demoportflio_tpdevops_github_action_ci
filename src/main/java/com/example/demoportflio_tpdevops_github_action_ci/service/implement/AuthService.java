package com.example.demoportflio_tpdevops_github_action_ci.service.implement;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.model.UserPrincipal;



@Service
public class AuthService {

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserPrincipal) {
            return ((UserPrincipal) auth.getPrincipal()).getUser();
        }
        throw new RuntimeException("Utilisateur non connecté !");
    }
}




