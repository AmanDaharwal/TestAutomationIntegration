package com.exceptions;

import io.cucumber.java.bs.A;

public class AutomationException extends RuntimeException {

    public AutomationException(String message) {
        super(message);
    }

    public AutomationException(String message, Exception exception) {
        super(message, exception);
    }
}
