package com.example.demoportflio_tpdevops_github_action_ci.exception.user;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import com.example.demoportflio_tpdevops_github_action_ci.repository.AproposRepository;


@Component
public class UniquePhoneValidator implements ConstraintValidator<UniquePhone, String> {

    private final AproposRepository aproposRepository;

    public UniquePhoneValidator(AproposRepository aproposRepository) {
        this.aproposRepository = aproposRepository;
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        if (phone == null) return true; // @NotBlank gère déjà ça
        return !aproposRepository.existsByTelephone(phone) ;

    }
}