package com.example.demoportflio_tpdevops_github_action_ci.exception.user;

public class ApiRequestException extends  RuntimeException {
    public ApiRequestException(String message){
        super(message);
    }
    public ApiRequestException(String message, Throwable cause){
        super(message, cause );
    }


}