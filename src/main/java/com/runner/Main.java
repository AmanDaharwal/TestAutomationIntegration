package com.runner;

import com.context.SessionContext;
import com.context.TestExecutionContext;

public class Main {

    public Main() {

    }

    public static void main(String[] args){
        System.out.println("Running Main Method  -> ");
        new TestRunner(args);
    }

    public static TestExecutionContext getTestExecutionContext(long threadId){
        return SessionContext.getTestExecutionContext(threadId);
    }
}
