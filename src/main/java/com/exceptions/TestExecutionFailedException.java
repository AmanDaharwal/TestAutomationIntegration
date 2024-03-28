package com.exceptions;

public class TestExecutionFailedException extends RuntimeException{
    public TestExecutionFailedException(String message){
        super(message);
    }

    public TestExecutionFailedException(String message, Exception exception){
        super(message, exception);
    }
}
