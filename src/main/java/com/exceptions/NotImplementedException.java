package com.exceptions;

public class NotImplementedException extends RuntimeException{
    public NotImplementedException(String message){
        super(message);
    }

    public NotImplementedException(String message, Exception exception){
        super(message, exception);
    }
}
