package com.example.demoportflio_tpdevops_github_action_ci.exception.user;



    public class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

