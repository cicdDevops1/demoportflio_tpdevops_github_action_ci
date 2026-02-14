package com.example.demoportflio_tpdevops_github_action_ci.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(String email);
    String extractUserName(String token);

    boolean validateToken(String token, UserDetails userDetails);
}
