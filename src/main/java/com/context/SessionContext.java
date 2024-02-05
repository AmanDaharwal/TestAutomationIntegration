package com.context;

import java.util.HashMap;

public class SessionContext {

    static final String TEST_RUNNER = "testrunner";
    private static final HashMap<String, TestExecutionContext> allTestExecutionContext = new HashMap<>();

    static synchronized void addContext(long threadId, TestExecutionContext testExecutionContext){
        allTestExecutionContext.put(String.valueOf(threadId), testExecutionContext);
    }

    public static synchronized TestExecutionContext getTestExecutionContext(long threadId){
        return allTestExecutionContext.get(String.valueOf(threadId));
    }

    public static synchronized void removeContext(long threadId){
        allTestExecutionContext.remove(String.valueOf(threadId));
    }
}
