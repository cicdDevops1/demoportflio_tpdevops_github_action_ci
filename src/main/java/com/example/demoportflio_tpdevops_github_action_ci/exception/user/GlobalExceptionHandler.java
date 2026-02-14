package com.example.demoportflio_tpdevops_github_action_ci.exception.user;
/*package com.example.demoportflio.exception.user;
import com.example.demoportflio.response.ResponseHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseHandler.ResponseBuilder(
                "Erreur de validation",
                HttpStatus.BAD_REQUEST,
                errors
        );
    }


    @ExceptionHandler(ApiExecptionHandler.UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(ApiExecptionHandler.UserNotFoundException ex) {
        return ResponseHandler.ResponseBuilder(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                null
        );
    }


    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDatabaseErrors(DataAccessException ex) {
        return ResponseHandler.ResponseBuilder(
                "Erreur interne : problème d'accès aux données",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null
        );
    }


    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Object> handleRestClientErrors(RestClientException ex) {
        return ResponseHandler.ResponseBuilder(
                "Erreur réseau ou service indisponible",
                HttpStatus.SERVICE_UNAVAILABLE,
                null
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return ResponseHandler.ResponseBuilder(
                "Une erreur interne s'est produite",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null
        );
    }
}
*/