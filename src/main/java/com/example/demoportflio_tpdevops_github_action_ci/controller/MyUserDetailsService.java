package com.example.demoportflio_tpdevops_github_action_ci.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.model.UserPrincipal;
import com.example.demoportflio_tpdevops_github_action_ci.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // System.out.println("OUUUUUUUUUU  "+ username);
        User user= userRepository.findBySlug(username)

                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

      //  System.out.println("OUUUUUUUUUUU  "+ user);

        //System.out.println("OUUUUUUUUUU  "+user);
        return new UserPrincipal(user);
    }



}
