package com.example.demoportflio_tpdevops_github_action_ci.exception.user;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demoportflio_tpdevops_github_action_ci.repository.UserRepository;


@Component


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {



    //private  static UserRepository staticAproposRepository;

    private UserRepository userRepository;

    public UniqueEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) return true; // @NotBlank gère déjà ça
        return !userRepository.existsByEmail(email) ;

    }
}
