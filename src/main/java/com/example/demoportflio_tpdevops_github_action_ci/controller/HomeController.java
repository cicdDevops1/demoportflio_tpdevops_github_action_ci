package com.example.demoportflio_tpdevops_github_action_ci.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttributes();
    }


    @GetMapping("/")
    public String home(HttpServletRequest request ) {
         return "home page "+ request.getRequestedSessionId();
    }
}
