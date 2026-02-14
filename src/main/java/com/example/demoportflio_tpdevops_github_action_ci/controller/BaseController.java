package com.example.demoportflio_tpdevops_github_action_ci.controller;


import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demoportflio_tpdevops_github_action_ci.response.ResponseHandler;

import java.util.Locale;

public abstract class BaseController {

    protected MessageSource messageSource;

    protected BaseController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Résout la locale selon le paramètre 'lang'
     */
    protected Locale resolveLocale(String lang) {
        return (lang != null && lang.equalsIgnoreCase("en")) ? Locale.ENGLISH : Locale.FRENCH;
    }

    /**
     * Méthode utilitaire pour construire une réponse standardisée
     */
    protected ResponseEntity<Object> buildResponse(String messageKey, Object data, String lang, HttpStatus status) {
        String message = messageSource.getMessage(messageKey, null, resolveLocale(lang));
        return ResponseHandler.responseBuilder(message, status, data);
    }
}

