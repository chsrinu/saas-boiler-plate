package com.hrms.hrpitch.onboarding.exceptions;

public class DuplicateClientCodeException extends RuntimeException{
    public DuplicateClientCodeException(String message){
        super(message);
    }
}

