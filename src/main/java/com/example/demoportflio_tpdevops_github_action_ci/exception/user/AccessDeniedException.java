package com.example.demoportflio_tpdevops_github_action_ci.exception.user;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Cette annotation permet de renvoyer directement le code HTTP 403
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AccessDeniedException extends RuntimeException {



    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
