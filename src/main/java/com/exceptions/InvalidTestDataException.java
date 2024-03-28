package com.exceptions;

import io.cucumber.java.sl.In;

public class InvalidTestDataException extends RuntimeException{

    public InvalidTestDataException(String message){
        super(message);
    }

    public InvalidTestDataException(String message, Exception exception){
        super(message, exception);
    }
}
