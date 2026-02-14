package com.example.demoportflio_tpdevops_github_action_ci.exception.user;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;

import java.util.Locale;
import java.util.Map;


//@ControllerAdvice
public class ApiExecptionHandler  {
    private final MessageSource messageSource;

    public ApiExecptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value={ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException= new ApiException(
                e.getMessage(),
                badRequest
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }





    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        Locale locale = request.getLocale();

        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String message = getLocalizedValidationMessage(error, locale);
            errors.put(error.getField(), message);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("message", getLocalizedMessage("error.validation", locale));
        body.put("status", HttpStatus.BAD_REQUEST.toString());
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

   private String getLocalizedValidationMessage(FieldError error, Locale locale) {

    String defaultMessage = error.getDefaultMessage();

    if (defaultMessage != null &&
            defaultMessage.startsWith("{") &&
            defaultMessage.endsWith("}")) {

        String key = defaultMessage.substring(1, defaultMessage.length() - 1);

        try {
            return messageSource.getMessage(key, error.getArguments(), locale);
        } catch (Exception e) {
            return key; // fallback si clé absente
        }
    }

    return defaultMessage != null ? defaultMessage : "Validation error";
}


    private String getLocalizedMessage(String key, Locale locale, Object... args) {
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (Exception e) {
            return key; // fallback si la clé n'existe pas
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        ApiException apiException = new ApiException(
                "Erreur interne du serveur : " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(apiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
