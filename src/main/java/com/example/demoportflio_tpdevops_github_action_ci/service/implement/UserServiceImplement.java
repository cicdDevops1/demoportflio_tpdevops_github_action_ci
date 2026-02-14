package com.example.demoportflio_tpdevops_github_action_ci.service.implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demoportflio_tpdevops_github_action_ci.exception.user.ApiExecptionHandler;
import com.example.demoportflio_tpdevops_github_action_ci.exception.user.InvalidCredentialsException;
import com.example.demoportflio_tpdevops_github_action_ci.model.User;
import com.example.demoportflio_tpdevops_github_action_ci.repository.UserRepository;
import com.example.demoportflio_tpdevops_github_action_ci.service.JwtService;
import com.example.demoportflio_tpdevops_github_action_ci.service.UserService;

import java.util.List;

@Service
public class UserServiceImplement implements UserService {
 private final    UserRepository userRepository;
private final BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(14);
     @Autowired
    private  AuthenticationManager authenticationManager;
     @Autowired
     private JwtService jwtService;
    public UserServiceImplement(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository =  userRepository;

    }
    @Autowired
    private MessageSource messageSource;


    @Override
    public User createUser(User user) {
        boolean existsByEmail = userRepository.existsByEmail(user.getEmail());
        if (existsByEmail) {
            throw new ApiExecptionHandler.UserAlreadyExistsException(messageSource.getMessage("user.exists", null, LocaleContextHolder.getLocale()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user = userRepository.saveAndFlush(user);

        user.setSlug(SlugUtils.generateSlug(user.getName(), user.getId()));

        return userRepository.save(user);

    }

    @Override
    public List<User> listUser() {
        return  userRepository.findAll();
    }

    @Override
    public User activeDesactive(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new ApiExecptionHandler.UserNotFoundException(messageSource.getMessage("user.exists", null, LocaleContextHolder.getLocale())));
        existingUser.setActive(!existingUser.isActive());
        return userRepository.save(existingUser);
    }

    @Override
    public User getUserById(Long id) {
      return   userRepository.findById(id).orElseThrow(()-> new ApiExecptionHandler.UserNotFoundException(messageSource.getMessage("user.exists", null, LocaleContextHolder.getLocale())));

    }

    @Override
    public String verify(User user) {
       /* Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
          if (authentication.isAuthenticated()) {
              return jwtService.generateToken(user.getEmail());
          }
          return null;*/
        try {

            User userFound = (User) userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException("Email ou mot de passe invalide."));

            String slug = userFound.getSlug();
            System.out.println("verify " + user.getPassword() + " " + slug + " " + user.getEmail());

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(slug, user.getPassword())
            );
            return jwtService.generateToken(user.getSlug());
        } catch (BadCredentialsException ex) {

            throw new InvalidCredentialsException(messageSource.getMessage("user.login.failed", null, LocaleContextHolder.getLocale()));
        }
    }


}


