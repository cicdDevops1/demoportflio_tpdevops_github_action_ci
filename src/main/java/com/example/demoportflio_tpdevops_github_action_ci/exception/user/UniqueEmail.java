package com.example.demoportflio_tpdevops_github_action_ci.exception.user;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Cet email est déjà utilisé";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
