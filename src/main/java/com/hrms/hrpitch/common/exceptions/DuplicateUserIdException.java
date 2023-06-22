package com.hrms.hrpitch.common.exceptions;

public class DuplicateUserIdException extends RuntimeException{

    public DuplicateUserIdException(String message){
        super(message);
    }
}
