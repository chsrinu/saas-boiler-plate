package com.hrms.hrpitch.onboarding.exceptions;

public class InvalidClientCodeException extends RuntimeException{
    public InvalidClientCodeException(String message) {
        super(message);
    }
}
